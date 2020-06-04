package com.linkare.irn.nascimento.web.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class TimePhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TimePhaseListener.class.getName());

    private static final ThreadLocal<Long> TIME_COUNTER = new ThreadLocal<Long>();

    private static final ThreadLocal<Long> TOTAL_COUNTER = new ThreadLocal<Long>();

    @Override
    public void afterPhase(PhaseEvent event) {
	if (FacesContext.getCurrentInstance().getApplication().getProjectStage() == ProjectStage.Development) {
	    TIME_COUNTER.set(System.currentTimeMillis() - TIME_COUNTER.get());

	    if (logger.isLoggable(Level.INFO)) {
		PhaseId phaseId = event.getPhaseId();

		String viewId = null;
		UIViewRoot uiViewRoot = event.getFacesContext().getViewRoot();

		if (uiViewRoot != null) {
		    viewId = uiViewRoot.getViewId();
		}

		if (event.getPhaseId() != PhaseId.RENDER_RESPONSE) {

		    logger.log(Level.INFO, "AFTER phaseId=[{0}] viewId=[{1}] phaseTime=[{2}]ms",
			       new Object[] { phaseId.toString(), viewId, TIME_COUNTER.get() });
		} else {

		    TOTAL_COUNTER.set(System.currentTimeMillis() - TOTAL_COUNTER.get());

		    logger.log(Level.INFO, "AFTER phaseId=[{0}] viewId=[{1}] phaseTime=[{2}]ms totalTime=[{3}]ms",
			       new Object[] { phaseId.toString(), viewId, TIME_COUNTER.get(), TOTAL_COUNTER.get() });
		}
	    }
	}
    }

    @Override
    public void beforePhase(PhaseEvent event) {
	if (FacesContext.getCurrentInstance().getApplication().getProjectStage() == ProjectStage.Development) {
	    TIME_COUNTER.set(System.currentTimeMillis());

	    if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
		TOTAL_COUNTER.set(System.currentTimeMillis());
	    }

	    if (logger.isLoggable(Level.INFO)) {
		PhaseId phaseId = event.getPhaseId();

		String viewId = null;
		UIViewRoot uiViewRoot = event.getFacesContext().getViewRoot();

		if (uiViewRoot != null) {
		    viewId = uiViewRoot.getViewId();
		}

		logger.log(Level.INFO, "BEFORE phaseId=[{0}] viewId=[{1}]", new Object[] { phaseId.toString(), viewId });
	    }
	}
    }

    @Override
    public PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }
}