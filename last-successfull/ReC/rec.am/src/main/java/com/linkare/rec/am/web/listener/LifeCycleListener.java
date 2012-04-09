package com.linkare.rec.am.web.listener;

import java.util.logging.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * 
 * @author Joao
 */
public class LifeCycleListener implements PhaseListener {

    private static Logger logger = Logger.getLogger("LifeCycleListener");

    /**
     * @return the logger
     */
    public static final Logger getLogger() {
	return logger;
    }

    @Override
    public final PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }

    @Override
    public final void beforePhase(PhaseEvent event) {
	//        getLogger().info("START PHASE " + event.getPhaseId());
    }

    @Override
    public final void afterPhase(PhaseEvent event) {
	//        getLogger().info("END PHASE " + event.getPhaseId());
    }
}