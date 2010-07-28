/*

 * EventQueue.java

 *

 * Created on 28 de Outubro de 2002, 14:15

 */

package com.linkare.rec.impl.utils;

import java.util.ArrayList;

import com.linkare.rec.impl.events.NewPoisonSamplesEvent;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 */

public class EventQueue {
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

	private class EventQueueThread extends Thread {
		
		public EventQueueThread(String threadName) {
			super();
			setName(getName() + " - " + threadName);
		}

		public void run() {
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
						
//						// verificar se e' um evento de paragem da thread
//						if ( evt instanceof NewPoisonSamplesEvent ) {
//							shutdown();
//						}
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
		}
	}
}
