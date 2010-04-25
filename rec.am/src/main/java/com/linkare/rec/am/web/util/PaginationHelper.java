package com.linkare.rec.am.web.util;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.linkare.commons.jpa.Identifiable;
import com.linkare.rec.am.model.Facade;

public class PaginationHelper<Entity extends Identifiable<?>, EntityFacade extends Facade<Entity>> {

    private int pageSize;

    private int page;

    private EntityFacade facade;

    public PaginationHelper(final EntityFacade facade, int pageSize) {
	this.facade = facade;
	this.pageSize = pageSize;
    }

    public int getItemsCount() {
	return facade.count();
    }

    public LazyDataModel<Entity> createPageDataModel() {
	return new LazyDataModel<Entity>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public List<Entity> fetchLazyData(int first, int pageSize) {
		return facade.findRange(new int[] { getPageFirstItem(), getPageSize() });
	    }
	};
    }

    public final int getPageFirstItem() {
	return page * pageSize;
    }

    public int getPageLastItem() {
	int i = getPageFirstItem() + pageSize - 1;
	int count = getItemsCount() - 1;
	if (i > count) {
	    i = count;
	}
	if (i < 0) {
	    i = 0;
	}
	return i;
    }

    public boolean getHasNextPage() {
	return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
	if (getHasNextPage()) {
	    page++;
	}
    }

    public boolean getHasPreviousPage() {
	return page > 0;
    }

    public void previousPage() {
	if (getHasPreviousPage()) {
	    page--;
	}
    }

    public int getPageSize() {
	return pageSize;
    }

    /**
     * @return the page
     */
    public int getPage() {
	return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(int page) {
	this.page = page;
    }
}