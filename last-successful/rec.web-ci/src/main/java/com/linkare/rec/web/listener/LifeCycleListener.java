package com.linkare.rec.web.listener;

import java.util.logging.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

/**
 * 
 * @author Joao
 */
public class LifeCycleListener implements javax.faces.event.PhaseListener {

    private static final long serialVersionUID = 5958322638117167569L;
	
    private static final Logger LOGGER = Logger.getLogger(LifeCycleListener.class.getName());

    
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