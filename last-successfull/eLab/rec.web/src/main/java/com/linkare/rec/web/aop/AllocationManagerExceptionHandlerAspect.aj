package com.linkare.rec.web.aop;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public aspect AllocationManagerExceptionHandlerAspect extends ExceptionHandlerAspect {

    declare soft : Exception : exceptionHandleExecutions();

    public pointcut context() :
	AllocationManagerPointcuts.inWebLayer();
}