package com.linkare.rec.am.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
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

    private static final int TIME_BETWEEN_MONITORING_EVENTS_SECONDS = Integer.class.cast(SystemPropertiesEnum.TIME_BETWEEN_MONITORING_EVENTS_SECONDS.getValue());

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
	    labsNotificationListener = new LabsNotificationListener();
	    laboratoryService = JndiHelper.getLaboratoryService();

	    initJMXConnectionHandlersMap();

	    executorService = Executors.newSingleThreadScheduledExecutor(getThreadFactory());

	    executorService.scheduleAtFixedRate(getLabsMonitorTask(), 0, TIME_BETWEEN_MONITORING_EVENTS_SECONDS,
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
		    connectWithLabs();
		} catch (Throwable e) {
		    LOG.error(e.getMessage(), e);
		}
	    }
	};
    }

    private void initJMXConnectionHandlersMap() {

	for (final Laboratory laboratory : getLaboratoriesToMonitor()) {
	    if (laboratory.getJmxURL() != null) {
		labsJMXConnectionHandler.put(laboratory.getName(), createLabJMXConnectionHandler(laboratory));
	    }
	}
    }

    private LabJMXConnetionHandler createLabJMXConnectionHandler(final Laboratory laboratory) {
	return new LabJMXConnetionHandler(laboratory, new JMXConnectionHandler(laboratory.getJmxURL(), laboratory.getJmxUser(), laboratory.getJmxPass()));
    }

    private void connectWithLabs() {

	for (final Entry<String, LabJMXConnetionHandler> lab : labsJMXConnectionHandler.entrySet()) {

	    if (isToCancel()) {
		return;
	    }

	    final JMXConnectionHandler jmxConnectionHandler = lab.getValue().getJmxConnectionHandler();

	    if (jmxConnectionHandler.initJMXConnectorIfNotAlreadyRegistered(getNotifListener())) {
		labsNotificationListener.initLab(getMBeanProxy(lab.getValue()));
	    }
	}
    }

    private MBeanNotificationListenerInfo getNotifListener(){
	return new MBeanNotificationListenerInfo(
					     labsNotificationListener.getNotificationListener(),
					     MBeanObjectNameFactory.getMultiCastControllerObjectName(),
						 null);
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

    public List<MultiThreadLaboratoryWrapper> getActiveLabs() {
	List<MultiThreadLaboratoryWrapper> labs = Collections.emptyList();
	if (!labsJMXConnectionHandler.isEmpty()) {
	    labs = new ArrayList<MultiThreadLaboratoryWrapper>(labsJMXConnectionHandler.size());
	    for (final Entry<String, LabJMXConnetionHandler> jmxHandler : labsJMXConnectionHandler.entrySet()) {
		final MultiThreadLaboratoryWrapper laboratory = labsNotificationListener.getLaboratory(jmxHandler.getValue().getLaboratory().getName());
		if (laboratory != null) {
		    laboratory.setAvailable(jmxHandler.getValue().getJmxConnectionHandler().isConnected());
		    labs.add(laboratory);
		}
	    }
	}
	return labs;
    }

    private Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> getMbeanProxies() {
	Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> result = Collections.emptyList();

	if (!labsJMXConnectionHandler.isEmpty()) {
	    result = new ArrayList<MbeanProxy<IMultiCastControllerMXBean, Laboratory>>(labsJMXConnectionHandler.size());
	    for (final Entry<String, LabJMXConnetionHandler> entry : labsJMXConnectionHandler.entrySet()) {
		result.add(getMBeanProxy(entry.getValue()));
	    }
	}

	return result;
    }

    private MbeanProxy<IMultiCastControllerMXBean, Laboratory> getMBeanProxy(final LabJMXConnetionHandler labJMXConnetionHandler) {

	final IMultiCastControllerMXBean proxy = labJMXConnetionHandler.getJmxConnectionHandler()
								       .getMbeanProxy(MBeanObjectNameFactory.getMultiCastControllerObjectName(),
										      IMultiCastControllerMXBean.class);
	return new MbeanProxy<IMultiCastControllerMXBean, Laboratory>(labJMXConnetionHandler.getLaboratory(), proxy);
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

    public void addOrUpdateLaboratory(final Laboratory lab) {
	if (lab != null && lab.getState().isActive()) {
	    synchronized (this) {
		if (needRefresh(lab)) {
		    labsJMXConnectionHandler.put(lab.getName(), createLabJMXConnectionHandler(lab));
		}
	    }
	}
    }

    private boolean needRefresh(final Laboratory lab) {
	boolean result = false;
	final LabJMXConnetionHandler labJMXConnetionHandler = labsJMXConnectionHandler.get(lab.getName());

	if (labJMXConnetionHandler != null) {

	    //validate version we can received an out of order request to change
	    if (lab.getVersion() > labJMXConnetionHandler.getLaboratory().getVersion()) {
		final String jmxUrl = labJMXConnetionHandler.getJmxConnectionHandler().getJmxURL();
		final String jmxUser = labJMXConnetionHandler.getJmxConnectionHandler().getJmxUser();
		final String jmxPass = labJMXConnetionHandler.getJmxConnectionHandler().getJmxPass();

		final boolean hasJMXUrlChanged = !StringUtils.equalsIgnoreCase(jmxUrl, lab.getJmxURL());
		final boolean hasJMXUserChanged = !StringUtils.equalsIgnoreCase(jmxUser, lab.getJmxUser());
		final boolean hasJMXPassChanged = !StringUtils.equalsIgnoreCase(jmxPass, lab.getJmxPass());

		result = hasJMXUrlChanged || hasJMXUserChanged || hasJMXPassChanged;
	    }

	} else {
	    result = true;
	}

	return result;
    }

    public void removeLaboratory(final Laboratory lab) {
	if (lab != null) {
	    labsJMXConnectionHandler.remove(lab.getName());
	}
    }

    public void forceConnection() {
	final Future<?> task = executorService.submit(getLabsMonitorTask());

	try {
	    task.get(60, TimeUnit.SECONDS);
	} catch (Exception e) {
	    LOG.error(e.getMessage(), e);
	    throw new RuntimeException(e);
	}
    }

    public MultiThreadLaboratoryWrapper getLaboratory(final String labID) {
	return labsNotificationListener.getLaboratory(labID);
    }

}
