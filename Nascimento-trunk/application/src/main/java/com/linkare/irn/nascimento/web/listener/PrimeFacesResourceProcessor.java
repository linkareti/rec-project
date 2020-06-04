package com.linkare.irn.nascimento.web.listener;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.primefaces.config.PrimeConfiguration;
import org.primefaces.context.RequestContext;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class PrimeFacesResourceProcessor implements PhaseListener {

    private static final long serialVersionUID = 1L;

    @Override
    public PhaseId getPhaseId() {
	return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
	FacesContext context = event.getFacesContext();
	PrimeConfiguration primeConfiguration = RequestContext.getCurrentInstance().getApplicationContext().getConfig();

	String theme = "aristo";
	String themeParamValue = primeConfiguration.getTheme();

	if (themeParamValue != null) {
	    ELContext elContext = context.getELContext();
	    ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
	    ValueExpression ve = expressionFactory.createValueExpression(elContext, themeParamValue, String.class);
	    theme = (String) ve.getValue(elContext);
	}

	if (theme != null && !theme.equals("none")) {
	    addCSS(context, "primefaces-" + theme, "theme.css");
	}

	if (primeConfiguration.isFontAwesomeEnabled()) {
	    addCSS(context, "primefaces", "fa/font-awesome.css");
	}

	if (primeConfiguration.isClientSideValidationEnabled()) {
	    addJS(context, "primefaces", "validation/validation.js");

	    if (primeConfiguration.isBeanValidationAvailable()) {
		addJS(context, "primefaces", "validation/beanvalidation.js");
	    }
	}
    }

    @Override
    public void afterPhase(PhaseEvent event) {
	// NOOP.
    }

    private void addCSS(FacesContext context, String library, String name) {
	UIOutput css = new UIOutput();
	css.setRendererType("javax.faces.resource.Stylesheet");
	css.getAttributes().put("library", library);
	css.getAttributes().put("name", name);
	context.getViewRoot().addComponentResource(context, css, "head");
    }

    private void addJS(FacesContext context, String library, String name) {
	UIOutput js = new UIOutput();
	js.setRendererType("javax.faces.resource.Script");
	js.getAttributes().put("library", library);
	js.getAttributes().put("name", name);
	context.getViewRoot().addComponentResource(context, js, "head");
    }

}