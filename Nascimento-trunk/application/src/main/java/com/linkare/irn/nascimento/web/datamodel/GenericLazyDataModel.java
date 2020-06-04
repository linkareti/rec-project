package com.linkare.irn.nascimento.web.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class GenericLazyDataModel<ENTITY extends DomainObject, DAO extends GenericDAO<ENTITY>> extends LazyDataModel<ENTITY> {

    private static final long serialVersionUID = 790076950055767590L;

    public static final int DEFAULT_PAGE_SIZE = 25;

    private final DAO dao;

    private Map<String, Object> pageFilters;

    private SortOrder sortOrder;

    private String sortField;

    public GenericLazyDataModel(final DAO dao) {
	this(dao, DEFAULT_PAGE_SIZE);
    }

    public GenericLazyDataModel(final DAO dao, final int pageSize) {
	super();
	this.dao = dao;

	super.setPageSize(pageSize);
    }

    /**
     * @return the pageFilters
     */
    public Map<String, Object> getPageFilters() {
	return Collections.unmodifiableMap(pageFilters());
    }

    private Map<String, Object> pageFilters() {
	if (pageFilters == null) {
	    pageFilters = new HashMap<String, Object>();
	}
	return pageFilters;
    }

    public void clearPageFilters() {
	pageFilters().clear();
    }

    public void addPageFilter(final String property, final Object value) {
	pageFilters().put(property, value);
    }

    /**
     * @return the sortField
     */
    public String getSortField() {
	return sortField;
    }

    /**
     * @param sortField
     *            the sortField to set
     */
    public void setSortField(String sortField) {
	this.sortField = sortField;
    }

    /**
     * @return the sortOrder
     */
    public SortOrder getSortOrder() {
	if (sortOrder == null) {
	    sortOrder = SortOrder.ASCENDING;
	}
	return sortOrder;
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(SortOrder sortOrder) {
	this.sortOrder = sortOrder;
    }

    public void clearSortFields() {
	this.sortField = null;
	this.sortOrder = null;
    }

    @Override
    public void setRowIndex(int rowIndex) {
	/*
	 * The following is in ancestor (LazyDataModel): this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
	 */
	if (rowIndex == -1 || getPageSize() == 0) {
	    super.setRowIndex(-1);
	} else {
	    super.setRowIndex(rowIndex % getPageSize());
	}
    }

    @Override
    public ENTITY getRowData(String rowKey) {
	return dao.find(Long.valueOf(rowKey));
    }

    @Override
    public Object getRowKey(ENTITY object) {
	return object.getId();
    }

    @Override
    public List<ENTITY> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
	final long count = doCount();
	final SelectionRange selectionRange = count >= pageSize ? new SelectionRange(first, pageSize) : new SelectionRange(0, (int) (count % pageSize));
	return dao.findRange(selectionRange);
    }

    @Override
    public List<ENTITY> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	final long count = doCount();
	final SelectionRange selectionRange = count >= pageSize ? new SelectionRange(first, pageSize) : new SelectionRange(0, (int) (count % pageSize));
	return dao.findRange(selectionRange);
    }

    public final long doCount() {
	setRowCount((int) getNumberOfRecords());
	return getRowCount();
    }

    public boolean getIsEmpty() {
	return getRowCount() == 0;
    }

    protected long getNumberOfRecords() {
	return dao.count();
    }

    /**
     * @return the service
     */
    public DAO getDAO() {
	return dao;
    }
}