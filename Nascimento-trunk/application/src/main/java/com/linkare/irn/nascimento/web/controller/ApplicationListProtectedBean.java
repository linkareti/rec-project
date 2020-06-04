package com.linkare.irn.nascimento.web.controller;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.web.auth.LoginBean;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class ApplicationListProtectedBean<DM extends GenericLazyDataModel<T, DAO>, DAO extends GenericDAO<T>, T extends DomainObject>
	extends ApplicationListBean<DM, DAO, T> {

    private static final long serialVersionUID = 700819087630294425L;

    @Inject
    private Instance<LoginBean> loginBean;

    public void checkAccess() throws UnauthorizedException {
    }

    protected LoginBean getLoginBean() {
	return loginBean.get();
    }
}