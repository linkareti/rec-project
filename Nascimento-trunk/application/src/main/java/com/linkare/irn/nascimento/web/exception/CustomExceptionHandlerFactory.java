package com.linkare.irn.nascimento.web.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
	this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
	ExceptionHandler result = new CustomExceptionHandler(parent.getExceptionHandler());
	return result;
    }
}