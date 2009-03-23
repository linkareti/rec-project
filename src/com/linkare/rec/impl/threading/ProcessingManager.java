package com.linkare.rec.impl.threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for managing threading pool scheduling and
 * priorities
 * 
 * @author Andr� Nuno Calado Leit�o - Linkare TI
 * @version 0.1
 * 
 */
public final class ProcessingManager {

    private static final int MAX_POOL_SIZE_MAX_PRIORITY = 10;
    private static final int MIN_POOL_SIZE_MAX_PRIORITY = 1;

    private ThreadPoolExecutor threadPoolMaxPriority = new ThreadPoolExecutor(MIN_POOL_SIZE_MAX_PRIORITY,
	    MAX_POOL_SIZE_MAX_PRIORITY, 10L, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
    private ThreadPoolExecutor threadPoolMinPriority = new ThreadPoolExecutor(10, 100, 10L, TimeUnit.NANOSECONDS,
	    new LinkedBlockingQueue<Runnable>());
    private ThreadPoolExecutor threadPoolMediumPriority = new ThreadPoolExecutor(10, 100, 10L, TimeUnit.NANOSECONDS,
	    new LinkedBlockingQueue<Runnable>());

    /**
     * Singleton instance A constru��o do objecto ProcessingManager de imediato
     * permite evitar as quest�es de multithreaded access
     */
    private final static ProcessingManager instance = new ProcessingManager();

    /**
     * Singleton constructor private to invalidate creation of instances from
     * this class.
     */
    private ProcessingManager() {
    }

    /**
     * Este m�todo permite aceder � inst�ncia singlet�o do Processing Manager
     * 
     * @return A inst�ncia �nica de ProcessingManager
     */

    public static ProcessingManager getInstance() {
	return instance;
    }

    
    public void executeWorkUnit(WorkUnit task) {
	switch (task.getPriority()) {
	case MAXIMUM:
	    this.threadPoolMaxPriority.execute(task);
	    break;
	case MEDIUM:
	    this.threadPoolMediumPriority.execute(task);
	    break;
	case MINIMUM:
	    this.threadPoolMinPriority.execute(task);
	    break;
	default:
	    System.out.println("ERRO, prioridade n�o reconhecida.....");
	    break;
	}
    }
    
    public void queueWorkUnit(WorkUnit task) {
	switch (task.getPriority()) {
	case MAXIMUM:
	    this.threadPoolMaxPriority.getQueue().add(task);
	    break;
	case MEDIUM:
	    this.threadPoolMediumPriority.getQueue().add(task);
	    break;
	case MINIMUM:
	    this.threadPoolMinPriority.getQueue().add(task);
	    break;
	default:
	    System.out.println("ERRO, prioridade n�o reconhecida.....");
	    break;
	}
    }
}
