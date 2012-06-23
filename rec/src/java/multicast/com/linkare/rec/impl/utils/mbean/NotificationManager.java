/* 
 * NotificationManagerImpl.java created on Jun 15, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.mbean.MBeanObjectNameFactory;
import com.linkare.rec.am.mbean.NotificationTypeEnum;
import com.linkare.rec.impl.threading.ProcessingManager;

/**
 * Singleton to manage the jmx notifications
 * 
 * @author Artur Correia - Linkare TI
 */
public final class NotificationManager {

	// system property?
	private static final int MAX_NOTIFICATIONS_IN_MEMORY = 1000;

	private static final NotificationManager INSTANCE = new NotificationManager();

	private final NotificationListener notificationListener;
	private final NotificationBroadcasterSupport notificationBroadcaster;

	private final AtomicLong sequenceNumber = new AtomicLong(0);

	private final Runnable sendNotifRunnable;
	private final BlockingQueue<Notification> notificationQueue;

	private volatile boolean shutdown = false;

	private final long uptime;

	private NotificationManager() {

		notificationBroadcaster = new NotificationBroadcasterSupport();

		sendNotifRunnable = new SendNotificationRunnable();

		notificationListener = new NotificationListener() {

			public void handleNotification(final Notification notification, final Object handback) {
				sendAsynNotification(notification);
			}
		};

		notificationQueue = new ArrayBlockingQueue<Notification>(MAX_NOTIFICATIONS_IN_MEMORY);
		uptime = System.currentTimeMillis();
	}

	public static NotificationManager getInstance() {
		return INSTANCE;
	}

	private void sendNotification(final Notification notification) {

		if (notification.getUserData() instanceof MultiCastControllerNotifInfoDTO) {
			final MultiCastControllerNotifInfoDTO userData = (MultiCastControllerNotifInfoDTO) notification
					.getUserData();
			userData.setUptime(uptime);
		}

		notification.setSequenceNumber(sequenceNumber.getAndIncrement());

		notificationBroadcaster.sendNotification(notification);

	}

	private boolean getHasNotifications() {
		return !notificationQueue.isEmpty();
	}

	public MBeanNotificationInfo[] getNotificationInfo() {
		return NotificationTypeEnum.getMBeanNotificationInfo();
	}

	public NotificationListener getNotificationListener() {
		return notificationListener;
	}

	public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws ListenerNotFoundException {
		notificationBroadcaster.removeNotificationListener(listener, filter, handback);
	}

	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws IllegalArgumentException {
		notificationBroadcaster.addNotificationListener(listener, filter, handback);
	}

	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		notificationBroadcaster.removeNotificationListener(listener);
	}

	public void sendAsynNotification(final NotificationTypeEnum notificationType,
			final MultiCastControllerNotifInfoDTO userData) {
		final Notification notif = notificationType.createNotif(MBeanObjectNameFactory
				.getMultiCastControllerObjectName());

		// FIXME: convert to compositedata
		notif.setUserData(userData);

		sendAsynNotification(notif);
	}

	/**
	 * Following the best practices describe in
	 * 
	 * http://weblogs.java.net/blog/emcmanus/archive/2007/08/when_can_jmx_no.
	 * html
	 * 
	 * 
	 * @param notification
	 */
	private void sendAsynNotification(final Notification notification) {

		if (!notificationQueue.offer(notification)) {
			// TODO: inform system administrator that we are discarding
			// notifications because we reach the max number of notifications in
			// memory
			// discarding oldest notification
			synchronized (this) {
				notificationQueue.poll();
				notificationQueue.offer(notification);
			}
		}
		ProcessingManager.getInstance().execute(sendNotifRunnable);
	}

	private class SendNotificationRunnable implements Runnable {

		private final Lock runningLock = new ReentrantLock();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			if (runningLock.tryLock()) {
				try {

					while (getHasNotifications()) {

						final Notification notification = notificationQueue.poll();

						if (notification != null && !shutdown) {
							NotificationManager.this.sendNotification(notification);
						}
					}

				} catch (Exception e) {
					// TODO: replace with a logger
					e.printStackTrace();
				} finally {
					runningLock.unlock();

					if (getHasNotifications() && !shutdown) {
						ProcessingManager.getInstance().execute(sendNotifRunnable);
					}
				}
			}
		}
	}

	public void shutdown() {
		shutdown = true;
	}

	public long getUptime() {
		return uptime;
	}
}
