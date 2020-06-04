package com.linkare.irn.nascimento.web.datamodel.security;

import com.linkare.irn.nascimento.dao.security.UserDAO;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class UserLazyDataModel extends GenericLazyDataModel<User, UserDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public UserLazyDataModel(UserDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public UserLazyDataModel(UserDAO dao) {
	super(dao);
    }
}