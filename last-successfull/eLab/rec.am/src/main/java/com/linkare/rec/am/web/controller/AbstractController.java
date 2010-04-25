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

    protected int selectedItemIndex;

    protected PaginationHelper<Entity> pagination;

    protected Entity current;

    protected DataModel<Entity> items = null;

    public static final int DEFAULT_PAGE_SIZE = 10;

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

    public PaginationHelper<Entity> getPagination() {
	if (pagination == null) {
	    pagination = new PaginationHelper<Entity>(DEFAULT_PAGE_SIZE) {

		@Override
		public int getItemsCount() {
		    return getFacade().count();
		}

		@Override
		public DataModel<Entity> createPageDataModel() {
		    return new ListDataModel<Entity>(getFacade().findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
		}
	    };
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
	if (current != null) {
	    selectedItemIndex = 0;
	} else {
	    current = getFacade().find(getItems().getRowData().id());
	    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
	}
    }

    public String prepareEdit() {
	initCurrent();
	return EDIT;
    }

    public String destroyAndView() {
	performDestroy();
	recreateModel();
	updateCurrentItem();
	if (selectedItemIndex >= 0) {
	    return VIEW;
	}
	recreateModel();
	return LIST;
    }

    protected abstract void performDestroy();

    protected void updateCurrentItem() {
	int count = getFacade().count();
	if (selectedItemIndex >= count) {
	    // selected index cannot be bigger than number of items:
	    selectedItemIndex = count - 1;
	    // go to previous page if last page disappeared:
	    if (pagination.getPageFirstItem() >= count) {
		pagination.previousPage();
	    }
	}
	if (selectedItemIndex >= 0) {
	    current = getFacade().findRange(new int[] { selectedItemIndex, selectedItemIndex + 1 }).get(0);
	}
    }

    public DataModel<Entity> getItems() {
	if (items == null) {
	    items = getPagination().createPageDataModel();
	}
	return items;
    }

    protected void recreateModel() {
	items = null;
    }

    public String destroy() {
	current = (Entity) getItems().getRowData();
	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
	performDestroy();
	recreateModel();
	return LIST;
    }

    public String next() {
	getPagination().nextPage();
	recreateModel();
	return LIST;
    }

    public String previous() {
	getPagination().previousPage();
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
}