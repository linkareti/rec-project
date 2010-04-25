/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import com.linkare.commons.jpa.Identifiable;
import com.linkare.rec.am.model.Facade;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.web.util.PaginationHelper;

/**
 * 
 * This class presents a common interface for the entity controllers, avoiding lots of code duplication.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class AbstractController<Entity extends Identifiable<?>, EntityFacade extends Facade<Entity>> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected PaginationHelper<Entity, EntityFacade> pagination;

    protected Entity current;

    protected DataModel<Entity> items = null;

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String BUNDLE = "/Bundle";

    public static final String VIEW = "View";

    public static final String CREATE = "Create";

    public static final String LIST = "List";

    public static final String EDIT = "Edit";

    public abstract Entity getSelected();

    protected abstract EntityFacade getFacade();

    public abstract String prepareCreate();

    public abstract String create();

    public abstract String update();

    public PaginationHelper<Entity, EntityFacade> getPagination() {
	if (pagination == null) {
	    pagination = new PaginationHelper<Entity, EntityFacade>(getFacade(), DEFAULT_PAGE_SIZE);
	}
	return pagination;
    }

    public String prepareList() {
	recreateModel();
	return LIST;
    }

    public String prepareView() {
	initCurrent();
	return VIEW;
    }

    private void initCurrent() {
	current = getFacade().find(((Entity) getItems().getRowData()).id());
    }

    public String prepareEdit() {
	initCurrent();
	return EDIT;
    }

    public String destroyAndView() {
	performDestroy();
	recreateModel();
	return LIST;
    }

    protected abstract void performDestroy();

    public DataModel<Entity> getItems() {
	if (items == null) {
	    // No need to fetch only a small subset since we deal with a small amount of data
	    items = new ListDataModel<Entity>(getFacade().findAll());
	}
	return items;
    }

    protected void recreateModel() {
	items = null;
    }

    public String destroy() {
	current = getItems().getRowData();
	performDestroy();
	recreateModel();
	return LIST;
    }

    public List<Entity> getAll() {
	return getFacade().findAll();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
	return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
	return JsfUtil.getSelectItems(getFacade().findAll(), true);
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