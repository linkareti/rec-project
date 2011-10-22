package com.linkare.rec.am.aop;

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