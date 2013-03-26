package com.linkare.rec.web.aop;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.ex.AuthenticationException;
import com.linkare.rec.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ReCWebExceptionHandler extends LoggerExceptionHandler {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1694892040664288073L;

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