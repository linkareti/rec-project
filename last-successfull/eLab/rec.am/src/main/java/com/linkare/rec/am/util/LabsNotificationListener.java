package com.linkare.rec.am.util;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.ExperimentServiceLocal;

/**
 * 
 * This class is responsible for receiving jmx notifications and do the correct forwarding of it.
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class LabsNotificationListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(LabsNotificationListener.class);

    private final ConcurrentMap<String, MultiThreadLaboratoryWrapper> labs;

    private volatile boolean destroy;

    private final ExperimentServiceLocal experimentService;

    public LabsNotificationListener(final Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies,
	    final ExperimentServiceLocal experimentService) {
	this.labs = new ConcurrentHashMap<String, MultiThreadLaboratoryWrapper>();
	this.experimentService = experimentService;
	initLabs(labProxies);
	this.destroy = false;
    }

    private void initLabs(Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies) {

	for (final MbeanProxy<IMultiCastControllerMXBean, Laboratory> mbeanProxy : labProxies) {
	    tryInitLab(mbeanProxy);
	}
    }

    public boolean tryInitLab(final MbeanProxy<IMultiCastControllerMXBean, Laboratory> labProxy) {
	boolean result = false;
	try {
	    result = (labs.putIfAbsent(labProxy.getEntity().getName(), new MultiThreadLaboratoryWrapper(labProxy, experimentService)) == null);
	} catch (Exception e) {
	    LOGGER.error("Error creating wrapper for laboratory: " + labProxy.getEntity().getName(), e);
	}
	return result;
    }

    public NotificationListener getNotificationListener() {
	return new NotificationListener() {

	    public void handleNotification(Notification notification, Object handback) {

		try {

		    if (isToCancel()) {
			return;
		    }

		    if (notification.getUserData() instanceof MultiCastControllerNotifInfoDTO) {

			final MultiCastControllerNotifInfoDTO notifInfo = (MultiCastControllerNotifInfoDTO) notification.getUserData();

			if (isToCancel()) {
			    return;
			}

			LabsNotificationListener.this.handleNotification(notifInfo, notification.getSequenceNumber());

		    }

		} catch (Throwable e) {
		    LOGGER.error(e.getMessage(), e);
		}
	    }
	};
    }

    public void handleNotification(final MultiCastControllerNotifInfoDTO notifInfo, final long notifSequenceNumber) {
	if (notifInfo == null) {
	    return;
	}

	final MultiThreadLaboratoryWrapper lab = labs.get(notifInfo.getLabID());

	if (lab == null) {
	    LOGGER.warn("Lab not found for labID: {}. Discarding notification received!", notifInfo.getLabID());
	    return;
	}

	lab.refreshFromNotif(notifInfo, notifSequenceNumber);
    }

    public void destroy() {
	destroy = true;
    }

    private boolean isToCancel() {
	return destroy;
    }

}
