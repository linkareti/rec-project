package com.linkare.irn.nascimento.web.controller;

import javax.annotation.PostConstruct;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.util.ApplicationMessageUtils;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class ApplicationListBean<DM extends GenericLazyDataModel<T, DAO>, DAO extends GenericDAO<T>, T extends DomainObject> extends BaseController {

    private static final long serialVersionUID = -4728997062425178564L;

    @Inject
    private Logger logger;

    private DM dataModel;

    private T entityToRemove;

    private int rows = 10;

    private int first;

    private DAO dao;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	dao = initDAO();
	dataModel = initDataModel();
    }

    protected abstract DM initDataModel();

    protected abstract DAO initDAO();

    protected final DAO getDAO() {
	return dao;
    }

    protected abstract void addFilters();

    protected T deleteEntity(T entity) throws DomainException {
	getDAO().remove(entity);
	return entity;
    }

    public void setEntityToRemove(T entity) {
	this.entityToRemove = entity;
    }

    public void removeEntity() {
	try {
	    if (entityToRemove != null) {
		deleteEntity(entityToRemove);
		entityToRemove = null;
		dataModel.doCount();
	    }
	} catch (Exception e) {
	    logger.error("Problems removing entity", e);
	    JsfUtils.addGlobalErrorMessage(ApplicationMessageUtils.getMessage("message.oopsOperation"));
	}
    }

    /**
     * @return the dataModel
     */
    public DM getDataModel() {
	return dataModel;
    }

    public String search() {
	resetPagination();
	dataModel.clearPageFilters();
	addFilters();
	return null;
    }

    public void clear() {
	dataModel.clearPageFilters();
    }

    /**
     * @return the rows
     */
    public int getRows() {
	return rows;
    }

    /**
     * @param rows
     *            the rows to set
     */
    public void setRows(int rows) {
	this.rows = rows;
    }

    /**
     * @return the first
     */
    public int getFirst() {
	return first;
    }

    /**
     * @param first
     *            the first to set
     */
    public void setFirst(int first) {
	this.first = first;
    }

    public void resetPagination() {
	final UIData dataList = ((UIData) JsfUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), getUIDataComponentId()));
	if (dataList != null) {
	    this.first = 0;
	    dataList.setFirst(this.first);
	}
    }

    protected abstract String getUIDataComponentId();
}