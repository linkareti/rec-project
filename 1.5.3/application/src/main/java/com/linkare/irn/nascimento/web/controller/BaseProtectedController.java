package com.linkare.irn.nascimento.web.controller;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.linkare.irn.nascimento.web.auth.LoginBean;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseProtectedController extends BaseController {

    private static final long serialVersionUID = -1952540487632977702L;

    @Inject
    private Instance<LoginBean> loginBean;

    public void checkAccess() throws UnauthorizedException {
    }

    protected LoginBean getLoginBean() {
	return loginBean.get();
    }
}