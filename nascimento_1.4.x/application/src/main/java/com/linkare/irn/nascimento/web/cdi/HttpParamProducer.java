package com.linkare.irn.nascimento.web.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class HttpParamProducer {

    @Inject
    private FacesContext facesContext;

    @Produces
    @HttpParam
    public String getHttpParameter(InjectionPoint ip) {
	String name = ip.getAnnotated().getAnnotation(HttpParam.class).value();
	if ("".equals(name))
	    name = ip.getMember().getName();
	return facesContext.getExternalContext().getRequestParameterMap().get(name);
    }
}