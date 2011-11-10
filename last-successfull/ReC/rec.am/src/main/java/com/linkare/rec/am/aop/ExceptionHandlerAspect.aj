package com.linkare.rec.am.aop;

import com.linkare.pointcuts.JavaPointcuts;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public abstract aspect ExceptionHandlerAspect {

    public pointcut exceptionHandleExecutions() : 
	execution(@ExceptionHandle * *..*.*(..)) || execution(@ExceptionHandle *..*.new(..));

    public pointcut exceptionHandleMethodExecutionsWithContext(final ExceptionHandle exceptionHandle) : 
	JavaPointcuts.methodExecution() && @annotation(exceptionHandle);

    public pointcut exceptionHandleConstructorExecutionsWithContext(final ExceptionHandle exceptionHandle) : 
	JavaPointcuts.constructorExecution() && @annotation(exceptionHandle);

    public abstract pointcut context();

    Object around(final ExceptionHandle exceptionHandle) :
	exceptionHandleMethodExecutionsWithContext(exceptionHandle) &&
	context() {

	final ExceptionHandleCase[] exceptionHandleCases = exceptionHandle.value();
	Object result = null;
	try {
	    result = proceed(exceptionHandle);
	} catch (Throwable e) {
	    return handleThrowable(thisJoinPoint.getTarget(), exceptionHandleCases, result, e);
	}
	return result;
    }

    Object around(final ExceptionHandle exceptionHandle) :
	exceptionHandleConstructorExecutionsWithContext(exceptionHandle) &&
	context() {

	final ExceptionHandleCase[] exceptionHandleCases = exceptionHandle.value();
	Object result = null;
	try {
	    result = proceed(exceptionHandle);
	} catch (Throwable e) {
	    return handleThrowable(thisJoinPoint.getTarget(), exceptionHandleCases, result, e);
	}
	return result;
    }

    private Object handleThrowable(final Object target, final ExceptionHandleCase[] exceptionHandleCases, Object result, Throwable e) {
	for (final ExceptionHandleCase exceptionHandleCase : exceptionHandleCases) {
	    if (exceptionHandleCase.exceptionType().isAssignableFrom(e.getClass())) {
		try {
		    return exceptionHandleCase.exceptionHandler().newInstance().execute(target, result, e);
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	    }
	}
	throw new RuntimeException(e);
    }
}