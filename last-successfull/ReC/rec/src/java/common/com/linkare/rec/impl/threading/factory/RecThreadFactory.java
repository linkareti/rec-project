package com.linkare.rec.impl.threading.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public class RecThreadFactory implements ThreadFactory {
	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	public RecThreadFactory(final String prefix) {
		final SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = new StringBuilder("RecPool-").append(RecThreadFactory.poolNumber.getAndIncrement()).append("-")
				.append(prefix).append("-").toString();
	}

	@Override
	public Thread newThread(final Runnable r) {
		final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		t.setDaemon(true);
		
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

		return t;
	}

}