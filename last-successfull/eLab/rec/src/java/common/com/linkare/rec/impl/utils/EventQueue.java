/*

 * EventQueue.java

 *

 * Created on 28 de Outubro de 2002, 14:15

 */

package com.linkare.rec.impl.utils;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.events.Prioritazible;
import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 * @author Artur Correia - Linkare TI
 */

public class EventQueue {

	private static final Logger LOGGER = Logger.getLogger(EventQueue.class.getName());

	private final EventQueueDispatcher dispatcher;
	// private final List<Prioritazible> levts;
	private EnumMap<EnumPriority, List<Prioritazible>> levts;
	private volatile boolean stoppedDispatching = false;

	private int countEvts = 0;

	/**
	 * Permits cancel the task if someone call shutdown on this queue
	 * 
	 */
	private Future<?> task;

	/**
	 * main lock for concurrency.
	 * 
	 */
	private final ReadWriteLock mainLock;

	private final EventQueueRunnableImpl eventQueueRunnable;

	/**
	 * Creates a new instance of EventQueue
	 * 
	 * @param dispatcher
	 * @param threadName
	 */
	public EventQueue(final EventQueueDispatcher dispatcher, final String threadName) {
		this.dispatcher = dispatcher;
		levts = new EnumMap<EnumPriority, List<Prioritazible>>(EnumPriority.class);
		for (EnumPriority priority : EnumPriority.values()) {
			levts.put(priority, new LinkedList<Prioritazible>());
		}
		stoppedDispatching = false;
		task = null;
		mainLock = new ReentrantReadWriteLock();
		eventQueueRunnable = new EventQueueRunnableImpl();
	}

	public void addEvent(final Prioritazible evt) {
		LOGGER.log(Level.FINEST, "EventQueue add event " + evt);

		if (stoppedDispatching) {
			return;
		}

		final Lock writeLock = mainLock.writeLock();
		writeLock.lock();
		try {
			if (stoppedDispatching) {
				return;
			}

			countEvts++;
			levts.get(evt.getPriority()).add(evt);

			// add task to execute in processing manager
			if (countEvts == 1) {
				submitSignalTask();
			}

		} finally {
			writeLock.unlock();
		}
	}

	private void submitSignalTask() {
		task = ProcessingManager.getInstance().submit(eventQueueRunnable);
	}

	/**
	 * cancel all tasks if not already done
	 * 
	 */
	private void cancelTask() {
		if (task != null && !task.isDone()) {
			task.cancel(true);
		}
	}

	/**
	 * 
	 * 
	 * @return true if
	 */
	public boolean hasEvents() {

		final Lock readLock = mainLock.readLock();
		readLock.lock();
		try {
			return countEvts != 0;
		} finally {
			readLock.unlock();
		}

	}

	public void shutdown() {
		LOGGER.log(Level.FINE, "EventQueue received shutdown. Queue size = " + levts.size());

		final Lock writeLock = mainLock.writeLock();
		writeLock.lock();
		try {
			stoppedDispatching = true;

			cancelTask();
			task = null;
			for (EnumPriority priority : levts.keySet()) {
				levts.get(priority).clear();
			}
			countEvts = 0;
		} finally {
			writeLock.unlock();
		}

	}

	public boolean isStopdispatching() {
		return stoppedDispatching;
	}

	/**
	 * 
	 * 
	 * This runnable should respond as soon as possible to interrupts.
	 * 
	 * 
	 * @author Artur Correia - Linkare TI
	 */

	private class EventQueueRunnableImpl implements Runnable {

		private EventQueueRunnableImpl() {
		}

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

				if (evt != null) {
					if (!isInterrupted()) {
						LOGGER.log(Level.FINER, "EventQueue dispatching the event " + evt);
						dispatcher.dispatchEvent(evt);
					} else {
						LOGGER.log(Level.WARNING, "EventQueue isn't dispatching the event " + evt
								+ " because the this runnable has been interrupted ");
					}
				}

			} catch (final InterruptedException e) {
				LOGGER.log(Level.FINER, "This runnable has been interrupted " + e.toString());
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Exception ocorred in event queue thread ", e);
			} finally {
				if (hasEvents() && !isInterrupted()) {
					submitSignalTask();
				}
			}
		}

		private Object getEvent() throws InterruptedException {

			final Lock writeLock = mainLock.writeLock();
			writeLock.lockInterruptibly();
			try {
				if (hasEvents()) {
					for (EnumPriority priority : EnumPriority.valuesOrderedByPriority()) {
						if (!levts.get(priority).isEmpty()) {
							countEvts--;
							return intersectEvent(levts.get(priority), levts.get(priority).remove(0));
						}
					}
				}
				return null;
			} finally {
				writeLock.unlock();
			}
		}

		private Object intersectEvent(final List<Prioritazible> eventList, final Object event)
				throws InterruptedException {

			if (event instanceof IntersectableEvent) {
				final IntersectableEvent intersectableEvent = (IntersectableEvent) event;

				final Lock writeLock = mainLock.writeLock();

				writeLock.lockInterruptibly();
				try {
					for (int i = eventList.size() - 1; i >= 0 && !isInterrupted(); i--) {

						final Object eventAfter = eventList.get(i);

						if (eventAfter instanceof IntersectableEvent) {

							final IntersectableEvent intersectableEventAfter = (IntersectableEvent) eventAfter;

							LOGGER.log(Level.FINEST, "EventQueue the event " + intersectableEvent + " might intersect "
									+ eventAfter);

							if (intersectableEvent.intersectTo(intersectableEventAfter)) {
								LOGGER.log(Level.FINEST, "EventQueue removed the event at the index " + i);
								eventList.remove(i);
								countEvts--;
							}
						}
					}
				} finally {
					writeLock.unlock();
				}
			}
			return event;
		}

	}

}
