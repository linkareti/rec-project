package com.linkare.irn.nascimento.web.controller.backoffice.upload;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.ext.mapping.Importable;
import com.linkare.irn.nascimento.model.ext.mapping.MappingProcessEntry;
import com.linkare.irn.nascimento.model.ext.mapping.MappingProcessResult;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.service.mapping.exception.CSVFileParsingException;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;
import com.linkare.irn.nascimento.web.controller.BaseProtectedController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseFileUploadController<T extends DomainObject, I extends Importable<T>, DAO extends GenericDAO<T>> extends BaseProtectedController {

    private static final long serialVersionUID = 495493524692823072L;

    private int first = 0;

    private int rows = 55;

    public abstract long getNumberOfExistingRecords() throws SystemException;

    public abstract GenericLazyDataModel<T, DAO> getDataModel();

    public abstract String getSpecificModuleGlobalMessagesClientId();

    public abstract IMapperProcessor<T, I, DAO> getMapperProcessor();

    public abstract String getModuleName();

    @Override
    public void checkAccess() throws UnauthorizedException {
	if (!getLoginBean().getHasAdminRole()) {
	    throw new UnauthorizedException("User " + getLoginBean().getUsername() + " has no access to the uploads");
	}
    }

    public void handleFileUpload(final FileUploadEvent event) {

	try {
	    InputStream in = null;
	    try {
		System.out.println(event.getFile().getFileName());
		in = event.getFile().getInputstream();

		final MappingProcessResult result = getMapperProcessor().load(in);

		final String statsMsg = createImportStatMsg(result);
		final String summary = getMessage("stats.summaryMessage", getModuleName());
		final UIComponent component = JsfUtils.findChildComponent(FacesContext.getCurrentInstance().getViewRoot(),
									  getSpecificModuleGlobalMessagesClientId());
		JsfUtils.addMessage(FacesMessage.SEVERITY_INFO, component == null ? null : component.getClientId(), summary, statsMsg);
	    } finally {
		if (in != null) {
		    in.close();
		}
	    }
	} catch (final CSVFileParsingException e) {
	    JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, getSpecificModuleGlobalMessagesClientId(), getMessage("message.error"),
				ExceptionUtils.getRootCauseMessage(e));
	} catch (Exception ex) {
	    JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, getSpecificModuleGlobalMessagesClientId(), getMessage("message.error"),
				getMessage("message.error.unexpectedImportException"));
	    JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, getSpecificModuleGlobalMessagesClientId(), getMessage("message.error"), getStackTrace(ex));
	    ex.printStackTrace();
	}
    }

    private String createImportStatMsg(final MappingProcessResult result) {
	final String message = getMessage("stats.result", result.getTotalCount(), result.getSuccessesCount(), result.getSkipsCount(),
					  result.getDuplicatesCount(), result.getErrorsCount());
	createErrorStatMsg(result);
	return message;
    }

    private String createErrorStatMsg(final MappingProcessResult result) {
	int nrErrors = 0;
	final List<MappingProcessEntry> errors = result.getErrors();
	//final UIComponent component = JsfUtils.findChildComponent(FacesContext.getCurrentInstance().getViewRoot(), getSpecificModuleGlobalMessagesClientId());

	for (final MappingProcessEntry error : errors) {
	    JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, getSpecificModuleGlobalMessagesClientId(), getMessage("message.error"),
				"Linha " + error.getLineNumber() + ": " + error.getMessage());
	}
	return String.valueOf(nrErrors);
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

    public void clearFilters() {
	resetPagination();
	getDataModel().clearPageFilters();
    }

    public void resetPagination() {
	final DataTable dataTable = ((DataTable) JsfUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), getDataListComponentId()));
	if (dataTable != null) {
	    first = 0;
	    dataTable.setFirst(first);
	}
    }

    public static String getStackTrace(Throwable aThrowable) {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PrintStream ps = new PrintStream(baos);
	aThrowable.printStackTrace(ps);
	return baos.toString().replace("\n", "<br />");
    }

    protected abstract String getDataListComponentId();
}