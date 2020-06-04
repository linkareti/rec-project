package com.linkare.irn.nascimento.web.datamodel.core;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.linkare.irn.nascimento.dao.core.BirthRegistrationDAO;
import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.ext.search.core.BirthRegistrationSearch;
import com.linkare.irn.nascimento.util.SelectionRange;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BirthRegistrationLazyDataModel extends GenericLazyDataModel<BirthRegistration, BirthRegistrationDAO> {

    private static final long serialVersionUID = 1L;

    public static final String EXAMPLE = "example";

    /**
     * @param dao
     * @param pageSize
     */
    public BirthRegistrationLazyDataModel(BirthRegistrationDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public BirthRegistrationLazyDataModel(BirthRegistrationDAO dao) {
	super(dao);
    }

    protected BirthRegistrationSearch getExampleFilter() {
	return (BirthRegistrationSearch) getPageFilters().get(EXAMPLE);
    }

    @Override
    protected long getNumberOfRecords() {
	final BirthRegistrationSearch example = getExampleFilter();
	return getDAO().countByExample(example);
    }

    @Override
    public List<BirthRegistration> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
	final long count = doCount();
	final SelectionRange selectionRange = count >= pageSize ? new SelectionRange(first, pageSize) : new SelectionRange(0, (int) (count % pageSize));

	final BirthRegistrationSearch example = getExampleFilter();
	return getDAO().findByExample(example, selectionRange);
    }

    @Override
    public List<BirthRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	final long count = doCount();
	final SelectionRange selectionRange = count >= pageSize ? new SelectionRange(first, pageSize) : new SelectionRange(0, (int) (count % pageSize));

	final BirthRegistrationSearch example = getExampleFilter();
	return getDAO().findByExample(example, selectionRange);
    }

}