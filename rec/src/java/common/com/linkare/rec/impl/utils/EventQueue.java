/*

 * EventQueue.java

 *

 * Created on 28 de Outubro de 2002, 14:15

 */

package com.linkare.rec.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

import com.linkare.rec.impl.threading.PriorityRunnable;
import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 */

public class EventQueue {

	private QueueLogger logger = null;
	private final EventQueueDispatcher dispatcher;
	private final ArrayList<Object> levts;
	private volatile boolean stopdispatching = false;

	/**
	 * 
	 * Runnable to execute on ProcessingManager
	 * 
	 */
	private final PriorityRunnable priorityRunnable;

	/**
	 * Permits cancel all tasks if someone call shutdown on this queue
	 * 
	 */
	private final Collection<Future<?>> tasks;

	/**
	 * main lock for concurrency.
	 * 
	 */
	private final ReadWriteLock mainLock;

	/**
	 * Creates a new instance of EventQueue
	 * 
	 * @param dispatcher
	 * @param threadName
	 */
	public EventQueue(EventQueueDispatcher dispatcher, String threadName) {
		this.dispatcher = dispatcher;
		levts = new ArrayList<Object>(1000);
		stopdispatching = false;
		priorityRunnable = new PriorityRunnableImpl();
		tasks = new LinkedList<Future<?>>();
		mainLock = new ReentrantReadWriteLock();

	}

	/**
	 * Creates the <code>EventQueue</code>.
	 * 
	 * @param dispatcher
	 * @param threadName
	 * @param logger
	 */
	public EventQueue(EventQueueDispatcher dispatcher, String threadName, QueueLogger logger) {
		this(dispatcher, threadName);

		this.logger = logger;
	}

	public void addEvent(Object evt) {
		log(Level.FINEST, "EventQueue add event " + evt);

		final Lock writeLock = this.mainLock.writeLock();
		writeLock.lock();
		try {
			levts.add(evt);

			cleanDoneTasks();

			// add task to execute in processing manager
			tasks.add(ProcessingManager.getInstance().submit(priorityRunnable));
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * clean done tasks from list.
	 * 
	 */
	private void cleanDoneTasks() {

		// avoiding ConcurrentModificationException
		final Iterator<Future<?>> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			final Future<?> future = iterator.next();
			if (future.isDone()) {
				iterator.remove();
			}
		}
	}

	/**
	 * cancel all tasks if not already done
	 * 
	 */
	private void cancelAllTasks() {
		for (final Future<?> future : tasks) {
			if (!future.isDone()) {
				future.cancel(true);
			}
		}
	}

	/**
	 * 
	 * 
	 * @return true if
	 */
	public boolean hasEvents() {

		final Lock readLock = this.mainLock.readLock();
		readLock.lock();
		try {
			return !levts.isEmpty();
		} finally {
			readLock.unlock();
		}

	}

	public void shutdown() {
		log(Level.FINE, "EventQueue received shutdown. Queue size = " + levts.size());

		stopdispatching = true;
		final Lock writeLock = this.mainLock.writeLock();
		writeLock.lock();
		try {
			cancelAllTasks();
			tasks.clear();
			levts.clear();
		} finally {
			writeLock.unlock();
		}

	}

	public boolean isStopdispatching() {
		return stopdispatching;
	}

	public boolean isEmpty() {
		final Lock readLock = this.mainLock.readLock();
		readLock.lock();
		try {
			return !levts.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	public void log(Level debugLevel, String message) {
		if (logger != null) {
			logger.log(debugLevel, message);
		}
	}

	public void logThrowable(String message, Throwable t) {
		if (logger != null) {
			logger.logThrowable(message, t);
		}
	}

	/**
	 * 
	 * 
	 * This runnable should respond as soon as possible to interrupts.
	 * 
	 * 
	 * @author artur
	 */

	private class PriorityRunnableImpl implements PriorityRunnable {

		private boolean isInterrupted() {
			return Thread.currentThread().isInterrupted();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {

			try {

				final Object evt = getEvent();

				if (evt != null && !isInterrupted()) {

					if (evt instanceof IntersectableEvent) {
						intersectEvent((IntersectableEvent) evt);
					}

					if (!isInterrupted()) {
						log(Level.FINER, "EventQueue dispatching the event " + evt);
						dispatcher.dispatchEvent(evt);
					} else {
						log(Level.WARNING, "EventQueue isn't dispatching the event " + evt
								+ " because the this runnable has been interrupted ");
					}

				}
			} catch (InterruptedException e) {
				log(Level.FINER, "This runnable has been interrupted " + e.toString());
			} catch (Exception e) {
				logThrowable("Exception ocorred in event queue thread ", e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public EnumPriority getPriority() {
			return EnumPriority.valueOfFromInt(dispatcher.getPriority());
		}

		private Object getEvent() throws InterruptedException {

			final Lock writeLock = EventQueue.this.mainLock.writeLock();
			writeLock.lockInterruptibly();
			try {
				if (!levts.isEmpty()) {
					return levts.remove(0);
				}
				return null;
			} finally {
				writeLock.unlock();
			}
		}

		private void intersectEvent(final IntersectableEvent intersectableEvent) throws InterruptedException {

			final Lock writeLock = EventQueue.this.mainLock.writeLock();

			writeLock.lockInterruptibly();
			try {
				for (int i = levts.size() - 1; i >= 0 && !isInterrupted(); i--) {

					final Object eventAfter = levts.get(i);

					if (eventAfter instanceof IntersectableEvent) {

						final IntersectableEvent intersectableEventAfter = (IntersectableEvent) eventAfter;

						log(Level.FINEST, "EventQueue the event " + intersectableEvent + " might intersect "
								+ eventAfter);

						if (intersectableEvent.intersectTo(intersectableEventAfter)) {
							log(Level.FINEST, "EventQueue removed the event at the index " + i);
							levts.remove(i);
						}
					}
				}
			} finally {
				writeLock.unlock();
			}
		}

	}

}
