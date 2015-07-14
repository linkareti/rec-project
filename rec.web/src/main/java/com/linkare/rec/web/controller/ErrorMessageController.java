/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.controller;

import com.linkare.rec.web.model.ErrorMessage;
import com.linkare.rec.web.service.ErrorMessageService;
import com.linkare.rec.web.service.ErrorMessageServiceLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
@ManagedBean(name = "errorMessageController")
@RequestScoped
public class ErrorMessageController extends AbstractController<Long, ErrorMessage, ErrorMessageService>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 614461993908462103L;
	@EJB(beanInterface = ErrorMessageServiceLocal.class)
    private ErrorMessageService service;
        
    @Override
    public ErrorMessage getSelected() {
        if (getCurrent() == null) {
            setCurrent(new ErrorMessage());
        }
        return getCurrent();
    }

    @Override
    protected ErrorMessageService getService() {
        return service;
    }

    @Override
    public String prepareCreate() {
        return null;
    }
    
}
