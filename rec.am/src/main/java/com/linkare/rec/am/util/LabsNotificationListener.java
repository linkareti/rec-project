package com.linkare.rec.am.util;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.model.Laboratory;

/**
 * 
 * This class is responsible for receiving jmx notifications and doing the correct forwarding of it.
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class LabsNotificationListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(LabsNotificationListener.class);

    private final ConcurrentMap<String, MultiThreadLaboratoryWrapper> labs;

    private volatile boolean destroy;

    public LabsNotificationListener(final Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies) throws NamingException {
	this.labs = new ConcurrentHashMap<String, MultiThreadLaboratoryWrapper>();
	initLabs(labProxies);
	this.destroy = false;
    }

    private void initLabs(Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies) {

	for (final MbeanProxy<IMultiCastControllerMXBean, Laboratory> mbeanProxy : labProxies) {
	    initLab(mbeanProxy);
	}
    }

    public void initLab(final MbeanProxy<IMultiCastControllerMXBean, Laboratory> labProxy) {
	if (labProxy != null) {
	    try {
		labs.put(labProxy.getEntity().getName(), new MultiThreadLaboratoryWrapper(labProxy));
	    } catch (Exception e) {
		LOGGER.error("Error creating wrapper for laboratory: " + labProxy.getEntity().getName(), e);
	    }
	}
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

    public MultiThreadLaboratoryWrapper getLaboratory(final String labID) {
	return labs.get(labID);
    }

}
