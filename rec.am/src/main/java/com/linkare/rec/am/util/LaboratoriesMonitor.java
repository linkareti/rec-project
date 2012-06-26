package com.linkare.rec.am.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.mbean.MBeanObjectNameFactory;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.LaboratoryService;
import com.linkare.rec.am.web.util.JndiHelper;

/**
 * 
 * This class is responsible for the monitoring of jmx connections
 * 
 * @author Artur Correia - Linkare - TI
 * 
 */
public final class LaboratoriesMonitor {

    private final static Logger LOG = LoggerFactory.getLogger(LaboratoriesMonitor.class);

    //FIXME: system property?
    private static final int TIME_BETWEEN_MONITORING_EVENTS_SECONDS = 10;

    private final ConcurrentMap<String, LabJMXConnetionHandler> labsJMXConnectionHandler;

    private volatile boolean destroy = false;

    private final ScheduledExecutorService executorService;

    private final LabsNotificationListener labsNotificationListener;

    private LaboratoryService laboratoryService;

    private static final LaboratoriesMonitor INSTANCE = new LaboratoriesMonitor();

    private LaboratoriesMonitor() {

	try {

	    LOG.info("Starting LaboratoriesMonitor");
	    labsJMXConnectionHandler = new ConcurrentHashMap<String, LabJMXConnetionHandler>();
	    laboratoryService = JndiHelper.getLaboratoryService();

	    initJMXConnectionHandlersMap();
	    connectWithLabs(false);

	    labsNotificationListener = new LabsNotificationListener(getMbeanProxies());

	    executorService = Executors.newSingleThreadScheduledExecutor(getThreadFactory());

	    executorService.scheduleAtFixedRate(getLabsMonitorTask(), TIME_BETWEEN_MONITORING_EVENTS_SECONDS, TIME_BETWEEN_MONITORING_EVENTS_SECONDS,
						TimeUnit.SECONDS);

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public static LaboratoriesMonitor getInstance() {
	return INSTANCE;
    }

    private ThreadFactory getThreadFactory() {
	return new ThreadFactory() {

	    @Override
	    public Thread newThread(Runnable r) {
		final Thread t = new Thread(r, "Rec-LabsMonitor");
		if (!t.isDaemon()) {
		    t.setDaemon(true);
		}
		return t;
	    }
	};
    }

    private Runnable getLabsMonitorTask() {
	return new Runnable() {

	    @Override
	    public void run() {
		try {
		    connectWithLabs(true);
		} catch (Throwable e) {
		    LOG.error(e.getMessage(), e);
		}
	    }
	};
    }

    private void initJMXConnectionHandlersMap() {

	for (final Laboratory laboratory : getLaboratoriesToMonitor()) {
	    labsJMXConnectionHandler.put(laboratory.getName(), createLabJMXConnectionHandler(laboratory));
	}
    }

    private LabJMXConnetionHandler createLabJMXConnectionHandler(final Laboratory laboratory) {
	return new LabJMXConnetionHandler(laboratory, new JMXConnectionHandler(laboratory.getJmxURL(), laboratory.getJmxUser(), laboratory.getJmxPass()));
    }

    private void connectWithLabs(final boolean isToRegisterNotifListener) {

	for (final Entry<String, LabJMXConnetionHandler> lab : labsJMXConnectionHandler.entrySet()) {

	    if (isToCancel()) {
		return;
	    }

	    final JMXConnectionHandler jmxConnectionHandler = lab.getValue().getJmxConnectionHandler();

	    if (jmxConnectionHandler.initJMXConnectorIfNotAlreadyRegistered() && isToRegisterNotifListener) {
		final MBeanNotificationListenerInfo notificationListener = new MBeanNotificationListenerInfo(
													     labsNotificationListener.getNotificationListener(),
													     MBeanObjectNameFactory.getMultiCastControllerObjectName(),
													     null);

		labsNotificationListener.tryInitLab(getMBeanProxy(lab.getValue()));
		jmxConnectionHandler.registerNotifListenerIfNotAlreadyRegistered(notificationListener);

	    }
	}
    }

    private Collection<Laboratory> getLaboratoriesToMonitor() {
	return laboratoryService.findAllActive();
    }

    @PreDestroy
    public void destroy() {
	LOG.debug("destroy called");

	try {
	    destroy = true;

	    labsNotificationListener.destroy();

	    executorService.shutdownNow();

	    for (final Entry<String, LabJMXConnetionHandler> jmxHandler : labsJMXConnectionHandler.entrySet()) {
		jmxHandler.getValue().getJmxConnectionHandler().closeJMXConnector();
	    }
	} catch (Exception e) {
	    LOG.error("Error on LaboratoriesManager shutdown", e);
	}

    }

    private boolean isToCancel() {
	return destroy;
    }

    public Map<Long, Boolean> getLiveLabs() {
	Map<Long, Boolean> labs = Collections.emptyMap();
	if (!labsJMXConnectionHandler.isEmpty()) {
	    labs = new HashMap<Long, Boolean>(labsJMXConnectionHandler.size());
	    for (final Entry<String, LabJMXConnetionHandler> jmxHandler : labsJMXConnectionHandler.entrySet()) {
		labs.put(jmxHandler.getValue().getLaboratory().getIdInternal(), Boolean.valueOf(jmxHandler.getValue().getJmxConnectionHandler().isConnected()));
	    }
	}
	return labs;
    }

    private Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> getMbeanProxies() {
	Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> result = Collections.emptyList();

	if (!labsJMXConnectionHandler.isEmpty()) {
	    result = new ArrayList<MbeanProxy<IMultiCastControllerMXBean, Laboratory>>(labsJMXConnectionHandler.size());
	    for (final Entry<String, LabJMXConnetionHandler> entry : labsJMXConnectionHandler.entrySet()) {
		final MbeanProxy<IMultiCastControllerMXBean, Laboratory> mBeanProxy = getMBeanProxy(entry.getValue());
		if (mBeanProxy != null) {
		    result.add(mBeanProxy);
		}
	    }
	}

	return result;
    }

    private MbeanProxy<IMultiCastControllerMXBean, Laboratory> getMBeanProxy(final LabJMXConnetionHandler labJMXConnetionHandler) {
	final IMultiCastControllerMXBean proxy = labJMXConnetionHandler.getJmxConnectionHandler()
								       .getMbeanProxy(MBeanObjectNameFactory.getMultiCastControllerObjectName(),
										      IMultiCastControllerMXBean.class);
	return proxy != null ? new MbeanProxy<IMultiCastControllerMXBean, Laboratory>(labJMXConnetionHandler.getLaboratory(), proxy) : null;
    }

    public IMultiCastControllerMXBean getMbeanInterfaceProxy(final String labID) {
	IMultiCastControllerMXBean result = null;
	if (labID != null) {
	    final LabJMXConnetionHandler labJMXConnetionHandler = labsJMXConnectionHandler.get(labID);

	    if (labJMXConnetionHandler != null) {
		result = labJMXConnetionHandler.getJmxConnectionHandler().getMbeanProxy(MBeanObjectNameFactory.getMultiCastControllerObjectName(),
											IMultiCastControllerMXBean.class);
	    }
	}
	return result;
    }

    public LabsNotificationListener getLabsNotificationManager() {
	return labsNotificationListener;
    }

    public void addNewLaboratory(final Laboratory lab) {
	if (!lab.getState().isActive()) {
	    labsJMXConnectionHandler.put(lab.getName(), createLabJMXConnectionHandler(lab));
	}
    }

    public void removeLaboratory(final Laboratory lab) {
	if (lab != null) {
	    labsJMXConnectionHandler.remove(lab.getName());
	}
    }

}
