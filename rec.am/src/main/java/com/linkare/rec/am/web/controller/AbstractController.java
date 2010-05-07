package com.linkare.rec.am.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import com.linkare.commons.jpa.Deletable;
import com.linkare.commons.jpa.Identifiable;
import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.rec.am.service.BusinessService;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

/**
 * 
 * This class presents a common interface for the entity controllers, avoiding lots of code duplication.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class AbstractController<ID extends Serializable, Entity extends Identifiable<ID> & Deletable, EntityFacade extends BusinessService<Entity, ID>>
	implements Serializable {

    private static final long serialVersionUID = 1L;

    private Entity current;

    protected DataModel<Entity> items = null;

    public abstract Entity getSelected();

    protected abstract EntityFacade getService();

    public abstract String prepareCreate();

    public String create() {
	try {
	    getService().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.INFO_CREATE_KEY));
	    return prepareCreate();
	} catch (Exception e) {
	    if (e.getCause() instanceof DomainException) {
		JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(e.getCause().getMessage()));
	    } else {
		JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
	    }
	    return null;
	}
    }

    public String update() {
	try {
	    getService().edit(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.INFO_UPDATE_KEY));
	    return ConstantUtils.VIEW;
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
	    return null;
	}
    }

    public String prepareList() {
	recreateModel();
	return ConstantUtils.LIST;
    }

    public String prepareView() {
	initCurrent();
	return ConstantUtils.VIEW;
    }

    private void initCurrent() {
	current = getService().find(getItems().getRowData().id());
    }

    public String prepareEdit() {
	initCurrent();
	return ConstantUtils.EDIT;
    }

    protected void performDestroy() {
	try {
	    getService().remove(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.INFO_REMOVE_KEY));
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
	}
    }

    public DataModel<Entity> getItems() {
	if (items == null) {
	    // No need to fetch only a small subset since we deal with a small amount of data
	    items = new ListDataModel<Entity>(getService().findAll());
	}
	return items;
    }

    protected void recreateModel() {
	items = null;
    }

    public String destroy() {
	current = getItems().getRowData();
	performDestroy();
	return prepareList();
    }

    public void destroy(ActionEvent event) {
	current = getItems().getRowData();
	performDestroy();
	recreateModel();
    }

    public List<Entity> getAll() {
	return getService().findAll();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
	return JsfUtil.getSelectItems(getService().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
	return JsfUtil.getSelectItems(getService().findAll(), true);
    }

    /**
     * @return the current
     */
    public Entity getCurrent() {
	return current;
    }

    /**
     * @param current
     *            the current to set
     */
    public void setCurrent(Entity current) {
	this.current = current;
    }
}