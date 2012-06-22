/* 
 * NotificationManagerImpl.java created on Jun 15, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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

	private static final NotificationManager INSTANCE = new NotificationManager();

	private final NotificationListener notificationListener;
	private final NotificationBroadcasterSupport notificationBroadcaster;

	private final AtomicLong sequenceNumber = new AtomicLong(1);

	private final Runnable sendNotifRunnable;
	private final BlockingQueue<Notification> notificationQueue;

	private volatile boolean shutdown = false;

	private NotificationManager() {

		notificationBroadcaster = new NotificationBroadcasterSupport();

		sendNotifRunnable = new SendNotificationRunnable();

		notificationListener = new NotificationListener() {

			public void handleNotification(final Notification notification, final Object handback) {
				sendAsynNotification(notification);
			}
		};

		notificationQueue = new LinkedBlockingQueue<Notification>();
	}

	public static NotificationManager getInstance() {
		return INSTANCE;
	}

	private void sendNotification(final Notification notification) {

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

		NotificationManager.getInstance().sendAsynNotification(notif);
	}

	public void sendAsynNotification(final Notification notification) {
		notificationQueue.offer(notification);
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

						if (notification != null) {
							// updateNotificationData(notification,
							// notificationType);

							if (!shutdown) {
								NotificationManager.this.sendNotification(notification);
							}
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

}
