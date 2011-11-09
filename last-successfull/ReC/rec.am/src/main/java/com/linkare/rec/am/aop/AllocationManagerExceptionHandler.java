package com.linkare.rec.am.aop;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AllocationManagerExceptionHandler extends LoggerExceptionHandler {

    @Override
    public Object execute(final Object target, final Object methodResult, Throwable throwable) {
	super.execute(target, methodResult, throwable);
	if (throwable.getCause() instanceof DomainException) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, throwable.getCause().getMessage());
	} else if (throwable instanceof AuthenticationException) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_LOGIN_FAILED_KEY);
	} else {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_PERSISTENCE_KEY);
	}
	return null;
    }
}