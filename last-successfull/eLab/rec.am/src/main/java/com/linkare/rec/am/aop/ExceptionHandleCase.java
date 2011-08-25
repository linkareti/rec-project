package com.linkare.rec.am.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandleCase {

    Class<? extends Throwable> exceptionType() default Throwable.class;

    Class<? extends LoggerExceptionHandler> exceptionHandler() default LoggerExceptionHandler.class;
}