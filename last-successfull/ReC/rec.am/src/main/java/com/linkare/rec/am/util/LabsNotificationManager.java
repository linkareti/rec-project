package com.linkare.rec.am.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.model.Laboratory;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public final class LabsNotificationManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(LabsNotificationManager.class);

    //    private static final Predicate ALL_EXECUTIONS_PREDICATE = new Predicate() {
    //
    //	public boolean evaluate(Object arg0) {
    //	    return true;
    //	}
    //    };

    private final ConcurrentMap<String, MultiThreadLaboratoryWrapper> labs;

    private volatile boolean destroy;

    public LabsNotificationManager(final Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies) {
	labs = new ConcurrentHashMap<String, MultiThreadLaboratoryWrapper>();
	initLabs(labProxies);
	destroy = false;
    }

    private void initLabs(final Collection<MbeanProxy<IMultiCastControllerMXBean, Laboratory>> labProxies) {

	for (final MbeanProxy<IMultiCastControllerMXBean, Laboratory> proxy : labProxies) {

	    final List<HardwareInfoDTO> hardwares = proxy.getMbeanInterface().getHardwares();
	    final List<ClientInfoDTO> users = proxy.getMbeanInterface().getClients();

	    final MultiThreadLaboratoryWrapper multiThreadLaboratoryWrapper = new MultiThreadLaboratoryWrapper(proxy.getEntity());
	    multiThreadLaboratoryWrapper.addHardwares(hardwares);
	    multiThreadLaboratoryWrapper.addUsers(users);

	    labs.put(multiThreadLaboratoryWrapper.getName(), multiThreadLaboratoryWrapper);
	}
    }

    public NotificationListener getNotificationListener() {
	return new NotificationListener() {

	    public void handleNotification(Notification notification, Object handback) {

		try {

		    if (isToCancel()) {
			return;
		    }

		    if (notification.getUserData() instanceof CompositeData) {

			//			if (ExecutionNotificationInfoVO.composityTypeInformation
			//				.isValue(notification.getUserData())) {
			//
			//			    final  MultiCastControllerNotifInfoDTO notifInfo = ExecutionNotificationInfoVO
			//				    .from((CompositeData) notification.getUserData());

			final MultiCastControllerNotifInfoDTO notifInfo = null;

			final MultiThreadLaboratoryWrapper lab = labs.get(notifInfo.getLabID());

			if (isToCancel()) {
			    return;
			}

			lab.refreshFrom(notifInfo);

		    }

		} catch (Throwable e) {
		    LOGGER.error(e.getMessage(), e);
		}

	    }
	};
    }

    //    public Collection<MultiThreadExecutionWrapper> getExecutionsByBatchID(final long batchID) {
    //
    //	final Collection<MultiThreadExecutionWrapper> executionsByPredicate = getExecutionsByPredicate(new Predicate() {
    //
    //	    public boolean evaluate(Object arg0) {
    //
    //		final MultiThreadExecutionWrapper executionWrapper = (MultiThreadExecutionWrapper) arg0;
    //
    //		if (executionWrapper.getBatchID() == batchID) {
    //		    return true;
    //		}
    //		return false;
    //	    }
    //	});
    //
    //	return getExecutionOrderedByInitDate(executionsByPredicate);
    //
    //    }

    //    @Observer(value = { "JMXCONNECTOR_FAILED", "JMXCONNECTOR_CLOSED" }, create = false)
    //    public void markExecutionsStateToUnknown(final ServidorFrab engine) {
    //
    //	final Collection<MultiThreadExecutionWrapper> executionsByPredicate = getExecutionsByPredicate(new Predicate() {
    //
    //	    public boolean evaluate(Object arg0) {
    //
    //		final MultiThreadExecutionWrapper executionWrapper = (MultiThreadExecutionWrapper) arg0;
    //
    //		if (executionWrapper.getServerID() == engine.getIdentificacaoServidor()) {
    //		    return true;
    //		}
    //		return false;
    //	    }
    //	});
    //
    //	for (final MultiThreadExecutionWrapper multiThreadExecutionWrapper : executionsByPredicate) {
    //	    multiThreadExecutionWrapper.markStateAsUnknownIfPossible();
    //	}
    //    }

    //    public Collection<MultiThreadLaboratoryWrapper> getAllLAboratories() {
    //	final Collection<MultiThreadLaboratoryWrapper> executionsByPredicate = getExecutionsByPredicate(ALL_EXECUTIONS_PREDICATE);
    //	return getExecutionOrderedByInitDate(executionsByPredicate);
    //    }
    //
    //    private Collection<MultiThreadLaboratoryWrapper> getExecutionsByPredicate(
    //	    final Predicate predicate) {
    //	if (liveLabs.size() == 0) {
    //	    return Collections.emptyList();
    //	}
    //
    //	final Collection<MultiThreadLaboratoryWrapper> result = new ArrayList<MultiThreadLaboratoryWrapper>(liveLabs.values());
    //	CollectionUtils.filter(result, predicate);
    //
    //	return result;
    //    }
    //
    //    private List<MultiThreadLaboratoryWrapper> getExecutionOrderedByInitDate(final Collection<MultiThreadLaboratoryWrapper> executions) {
    //
    //	if (executions.size() == 0) {
    //	    return Collections.emptyList();
    //	}
    //
    //	final List<MultiThreadLaboratoryWrapper> result = new ArrayList<MultiThreadLaboratoryWrapper>(
    //		executions);
    //
    //	Collections.sort(result, MultiThreadLaboratoryWrapper.INITDATE_COMPARATOR_DESC);
    //
    //	return result;
    //
    //    }

    //    public void cleanLabsInFinalOrUnknownState() {
    //
    //	final Iterator<Entry<String, MultiThreadExecutionWrapper>> iterator = runningExecutions
    //		.entrySet().iterator();
    //
    //	final long currentTimeMillis = System.currentTimeMillis();
    //
    //	while (iterator.hasNext()) {
    //	    final Entry<String, MultiThreadExecutionWrapper> execution = iterator.next();
    //	    final MultiThreadExecutionWrapper value = execution.getValue();
    //	    if (value.isToClean(currentTimeMillis, TIME_AFTER_EXECUTIOND_ENDED)) {
    //		iterator.remove();
    //	    }
    //	}
    //    }

    public void destroy() {
	destroy = true;
    }

    private boolean isToCancel() {
	return destroy;
    }

}
