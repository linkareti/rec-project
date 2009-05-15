package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;



public class ExecutorScheduler {
    
    private static final ScheduledExecutorService scheduler = newScheduledThreadPool(1);

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize, new ReCThreadFactory());
    }
    
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, int threadPriority) {
        return new ScheduledThreadPoolExecutor(corePoolSize, new ReCThreadFactory(threadPriority));
    }
    
    public static void scheduleAtFixedRate(ScheduledWorkUnit work, long initialDelay, long period, TimeUnit unit) {
        ScheduledFuture<?> shutdownHandler = scheduler.scheduleWithFixedDelay(new ScheduledRunnable(work), initialDelay, period, unit);
        work.setShutdownHandler(shutdownHandler);
    }
    
    private static class ScheduledRunnable implements Runnable {

        ScheduledWorkUnit work;
                
        ScheduledRunnable(ScheduledWorkUnit work) {
            this.work = work;
        }

        public void run() {
            if(work != null) {
                work.run();
            }
        }
        
    }
    
    private static class ReCThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        final int priority;

        ReCThreadFactory() {
            this(Thread.NORM_PRIORITY);
        }
        
        ReCThreadFactory(int priority) {
            this.priority = priority;
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = "rec-pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if(t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != priority)
                t.setPriority(priority);
            return t;
        }
    }    

}
