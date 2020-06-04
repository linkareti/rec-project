package com.linkare.irn.nascimento.web.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.linkare.irn.nascimento.model.listener.audit.AuditInfo;
import com.linkare.irn.nascimento.model.listener.audit.AuditInfoThreadLocal;
import com.linkare.irn.nascimento.web.auth.LoginBean;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AuditListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    @Override
    public final PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }

    @Override
    public final void beforePhase(PhaseEvent event) {

	if (event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
	    if (loginBean != null && loginBean.isLoggedIn()) {
		final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		AuditInfoThreadLocal.set(new AuditInfo(loginBean.getUsername(), request.getRequestURI(), System.currentTimeMillis()));
	    }
	}
    }

    @Override
    public final void afterPhase(PhaseEvent event) {
	if (event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
	    if (AuditInfoThreadLocal.get().isPresent()) {
		AuditInfoThreadLocal.remove();
	    }
	}
    }
}