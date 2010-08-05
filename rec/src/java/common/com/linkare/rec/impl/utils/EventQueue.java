/*

 * EventQueue.java

 *

 * Created on 28 de Outubro de 2002, 14:15

 */

package com.linkare.rec.impl.utils;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 */

public class EventQueue {
	private QueueLogger logger = null;
	private EventQueueDispatcher dispatcher = null;
	private EventQueueThread threadedQueueDispatcher = null;
	private ArrayList<Object> levts = null;
	private volatile boolean stopdispatching = false;

	/**
	 * Creates a new instance of EventQueue
	 * 
	 * @param dispatcher
	 * @param threadName 
	 */
	public EventQueue(EventQueueDispatcher dispatcher, String threadName) {
		this.dispatcher = dispatcher;
		levts = new ArrayList<Object>(1000);
		threadedQueueDispatcher = new EventQueueThread(threadName);
		threadedQueueDispatcher.setPriority(dispatcher.getPriority());
		stopdispatching = false;
		threadedQueueDispatcher.start();
	}
	
	/**
	 * Creates the <code>EventQueue</code>.
	 * @param dispatcher
	 * @param threadName
	 * @param logger
	 */
	public EventQueue(EventQueueDispatcher dispatcher, String threadName, QueueLogger logger) {
		this(dispatcher, threadName);
		
		this.logger = logger;
	}

	public void addEvent(Object evt) {
		levts.add(evt);
		synchronized (levts) {
			levts.notify();
		}
	}

	public boolean hasEvents() {
		return !levts.isEmpty();
	}

	public void shutdown() {
		log(Level.FINE, "Event queue received shutdown. Queue size = " + levts.size());
		
		stopdispatching = true;
		levts.clear();
		synchronized (levts) {
			levts.notify();
		}
		try {
			if (threadedQueueDispatcher.isAlive()) {
				threadedQueueDispatcher.join(20000);
			}
		} catch (InterruptedException ignored) {
		}

	}
	
	public boolean isStopdispatching() {
		return stopdispatching;
	}
	
	public boolean isEmpty() {
		return levts.size() == 0;
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

	private class EventQueueThread extends Thread {
		
		public EventQueueThread(String threadName) {
			super();
			setName(getName() + " - " + threadName);
		}

		public void run() {
			log(Level.INFO, "Thread " + getName() + " started.");
			
			try {
				Object evt = null;
				while (!stopdispatching) {
					evt = null;
					try {
						synchronized (levts) {
							if (levts.size() > 0)
								evt = levts.remove(0);
						}
					} catch (IndexOutOfBoundsException ignored) {
						// System.out.println("Accessing array at position 0 was a big problem... But I solved it...");
					}
					if (evt == null) {
						try {
							while (levts.size() == 0 && !stopdispatching) {
								synchronized (levts) {
									levts.wait(10);
								}
							}
						} catch (InterruptedException e) {
							// System.out.println("EventQueueDispatchingThread received an interruptedException waiting for notifies...");
						}
					} else {
						if (!stopdispatching) {
							if (evt instanceof IntersectableEvent) {
								IntersectableEvent intersectableEvent = (IntersectableEvent) evt;
								for (int i = levts.size() - 1; i >= 0 && !stopdispatching; i--) {
									Object eventAfter = levts.get(i);
									if (eventAfter instanceof IntersectableEvent) {
										IntersectableEvent intersectableEventAfter = (IntersectableEvent) eventAfter;
										if (intersectableEvent.intersectTo(intersectableEventAfter)) {
											levts.remove(i);
										}
									}
								}
							}
							if (!stopdispatching) {
								dispatcher.dispatchEvent(evt);
							}
						}
						try {
							synchronized (levts) {
								if (!stopdispatching) {
									levts.wait(20);
								}
							}
						} catch (InterruptedException e) {
							// System.out.println("EventQueueDispatchingThread received an interruptedException waiting for notifies...");
						}
					}
				}
			} catch (Exception e) {
				logThrowable("Exception ocorred in event queue thread " + getName(), e);
			}
			
			stopdispatching = true;
			log(Level.INFO, "Event queue thread " + getName() + " ended.");
		}
	}
}
