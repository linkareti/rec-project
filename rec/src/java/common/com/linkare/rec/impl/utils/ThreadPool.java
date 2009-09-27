package com.linkare.rec.impl.utils;

import java.util.LinkedList;

public class ThreadPool {
	private final int nThreads;
	private final PoolWorkerThread[] threads;
	private final LinkedList<Runnable> queue;

	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList<Runnable>();
		threads = new PoolWorkerThread[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorkerThread();
			threads[i].start();
		}
	}

	public void execute(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	private class PoolWorkerThread extends Thread {
		public void run() {
			Runnable r;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}

					r = queue.removeFirst();
				}

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					r.run();
				} catch (RuntimeException e) {
					// You might want to log something here
				}
			}
		}
	}
}
