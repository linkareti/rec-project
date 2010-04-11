/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.web.controller;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import com.linkare.rec.am.model.Facade;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.web.util.PaginationHelper;

/**
 * 
 * This class presents a common interface for the entity controllers, avoiding lots of code duplication.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class AbstractController<Entity, EntityFacade extends Facade<Entity>> implements Serializable {

    protected int selectedItemIndex;
    protected PaginationHelper pagination;
    protected Entity current;
    protected DataModel items = null;
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

    public PaginationHelper getPagination() {
	if (pagination == null) {
	    pagination = new PaginationHelper(DEFAULT_PAGE_SIZE) {

		@Override
		public int getItemsCount() {
		    return getFacade().count();
		}

		@Override
		public DataModel createPageDataModel() {
		    return new ListDataModel(getFacade().findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
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
	current = (Entity) getItems().getRowData();
	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
	return VIEW;
    }

    public String prepareEdit() {
	current = (Entity) getItems().getRowData();
	selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
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

    public DataModel getItems() {
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

    public SelectItem[] getItemsAvailableSelectMany() {
	return JsfUtil.getSelectItems(getFacade().findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
	return JsfUtil.getSelectItems(getFacade().findAll(), true);
    }
}