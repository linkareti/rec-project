package com.linkare.rec.am.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.management.NotificationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.mbean.MBeanObjectNameFactory;
import com.linkare.rec.am.model.Laboratory;

/**
 * 
 * @author Artur Correia - Linkare - TI
 * 
 */
public final class LaboratoriesMonitor {

    private final static Logger LOG = LoggerFactory.getLogger(LaboratoriesMonitor.class);

    private final ConcurrentMap<String, LabJMXConnetionHandler> labsJMXConnectionHandler;

    private volatile boolean destroy = false;

    private final ScheduledExecutorService executorService;

    private final LabsNotificationManager liveLaboratoriesManager;

    private final Future<?> monitorTaskFuture;

    private static final LaboratoriesMonitor INSTANCE = new LaboratoriesMonitor();

    private LaboratoriesMonitor() {
	LOG.info("Starting LaboratoriesMonitor");
	labsJMXConnectionHandler = new ConcurrentHashMap<String, LabJMXConnetionHandler>();

	initJMXConnectionHandlersMap();
	connectWithLabs();

	liveLaboratoriesManager = new LabsNotificationManager(getMbeanProxies());

	final ThreadFactory labsThreadFactory = new ThreadFactory() {

	    @Override
	    public Thread newThread(Runnable r) {
		final Thread t = new Thread(r, "Rec-LabsMonitor");
		if (!t.isDaemon()) {
		    t.setDaemon(true);
		}
		return t;
	    }
	};

	executorService = Executors.newSingleThreadScheduledExecutor(labsThreadFactory);

	final Runnable labsMonitorRunnable = new Runnable() {

	    @Override
	    public void run() {
		try {
		    connectWithLabs();
		} catch (Throwable e) {
		    LOG.error(e.getMessage(), e);
		}
	    }
	};

	monitorTaskFuture = executorService.submit(labsMonitorRunnable);
    }

    public static LaboratoriesMonitor getInstance() {
	return INSTANCE;
    }

    private void initJMXConnectionHandlersMap() {

	final Collection<Laboratory> laboratories = getLaboratoriesToMonitor();

	for (final Laboratory laboratory : laboratories) {
	    labsJMXConnectionHandler.put(laboratory.getName(), new LabJMXConnetionHandler(laboratory, new JMXConnectionHandler(laboratory)));
	}
    }

    private void connectWithLabs() {

	for (final Entry<String, LabJMXConnetionHandler> lab : labsJMXConnectionHandler.entrySet()) {

	    if (isToCancel()) {
		return;
	    }

	    final JMXConnectionHandler jmxConnectionHandler = lab.getValue().getJmxConnectionHandler();

	    jmxConnectionHandler.initJMXConnectorIfPossible();
	}
    }

    //TODO:
    private Collection<Laboratory> getLaboratoriesToMonitor() {
	return Collections.emptyList();
    }

    public void destroy() {
	LOG.debug("destroy called");

	try {
	    destroy = true;

	    monitorTaskFuture.cancel(true);

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

    public void registerNotificationListener(final Laboratory lab, final NotificationListener notificationListener) {
	final JMXConnectionHandler jmxConnectionHandler = labsJMXConnectionHandler.get(lab.getName()).getJmxConnectionHandler();

	if (jmxConnectionHandler == null) {
	    throw new IllegalArgumentException("Lab not found for name: " + lab.getName());
	}
	jmxConnectionHandler.registerNotificationListener(notificationListener, MBeanObjectNameFactory.getMultiCastControllerObjectName(), null, null);
    }

    public Collection<MultiThreadLaboratoryWrapper> getLiveLabs() {
	Collection<MultiThreadLaboratoryWrapper> labs = Collections.emptyList();
	if (!labsJMXConnectionHandler.isEmpty()) {
	    labs = new ArrayList<MultiThreadLaboratoryWrapper>(labsJMXConnectionHandler.size());
	    for (final Entry<String, LabJMXConnetionHandler> jmxHandler : labsJMXConnectionHandler.entrySet()) {
		if (jmxHandler.getValue().getJmxConnectionHandler().isConnected()) {
		    //		    labs.add(jmxHandler.getValue().getLaboratory());
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
		final IMultiCastControllerMXBean proxy = entry.getValue()
								   .getJmxConnectionHandler()
								   .getMbeanProxy(MBeanObjectNameFactory.getMultiCastControllerObjectName(),
											     IMultiCastControllerMXBean.class);
		if (proxy != null) {
		    result.add(new MbeanProxy<IMultiCastControllerMXBean, Laboratory>(entry.getValue().getLaboratory(), proxy));
		}
	    }
	}

	return result;
    }

}
