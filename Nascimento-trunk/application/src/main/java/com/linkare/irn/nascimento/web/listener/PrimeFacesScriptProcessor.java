package com.linkare.irn.nascimento.web.listener;

import javax.faces.application.ProjectStage;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.primefaces.config.PrimeConfiguration;
import org.primefaces.context.RequestContext;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class PrimeFacesScriptProcessor implements SystemEventListener {

    @Override
    public boolean isListenerForSource(Object source) {
	return source instanceof UIViewRoot;
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
	FacesContext context = FacesContext.getCurrentInstance();
	ProjectStage projectStage = context.getApplication().getProjectStage();
	PrimeConfiguration primeConfiguration = RequestContext.getCurrentInstance().getApplicationContext().getConfig();

	StringBuilder script = new StringBuilder();

	script.append("if(window.PrimeFaces){");
	script.append("PrimeFaces.settings.locale='").append(context.getViewRoot().getLocale()).append("';");

	if (primeConfiguration.isClientSideValidationEnabled()) {
	    script.append("PrimeFaces.settings.validateEmptyFields=").append(primeConfiguration.isValidateEmptyFields()).append(";");
	    script.append("PrimeFaces.settings.considerEmptyStringNull=").append(primeConfiguration.isInterpretEmptyStringAsNull()).append(";");
	}

	if (primeConfiguration.isLegacyWidgetNamespace()) {
	    script.append("PrimeFaces.settings.legacyWidgetNamespace=true;");
	}

	if (primeConfiguration.isEarlyPostParamEvaluation()) {
	    script.append("PrimeFaces.settings.earlyPostParamEvaluation=true;");
	}

	if (!projectStage.equals(ProjectStage.Production)) {
	    script.append("PrimeFaces.settings.projectStage='").append(projectStage.toString()).append("';");
	}

	script.append("}");

	addJS(context, script.toString());
    }

    private void addJS(FacesContext context, String script) {
	UIOutput js = new UIOutput();
	js.setRendererType("javax.faces.resource.Script");
	UIOutput content = new UIOutput();
	content.setValue(script);
	js.getChildren().add(content);
	context.getViewRoot().addComponentResource(context, js);
    }
}