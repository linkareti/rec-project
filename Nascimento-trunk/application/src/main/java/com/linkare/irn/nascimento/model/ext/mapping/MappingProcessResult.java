package com.linkare.irn.nascimento.model.ext.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MappingProcessResult implements Serializable {

    private static final int MAX_ERRORS_TO_SHOW = 40;

    private static final long serialVersionUID = 3236811481686923152L;

    private int totalCount;

    private Integer successesCount;

    private Integer duplicatesCount;

    private Integer skipsCount;

    private Integer errorsCount;

    private List<MappingProcessEntry> errors = new ArrayList<>();

    private transient final Map<MappingProcessEntryType, List<MappingProcessEntry>> details = new LinkedHashMap<>();

    private void addEntry(final MappingProcessEntry entry) {
	final List<MappingProcessEntry> entries = details.containsKey(entry.getType()) ? details.get(entry.getType()) : new ArrayList<>();
	entries.add(entry);
	details.put(entry.getType(), entries);
	this.totalCount++;
    }

    public void addSuccess(final int lineNumber, final String message) {
	addEntry(new MappingProcessEntry(lineNumber, MappingProcessEntryType.SUCCESS, message));
    }

    public void addDuplicate(final int lineNumber, final String message) {
	addEntry(new MappingProcessEntry(lineNumber, MappingProcessEntryType.DUPLICATED, message));
    }

    public void addSkip(final int lineNumber, final String message) {
	addEntry(new MappingProcessEntry(lineNumber, MappingProcessEntryType.SKIP, message));
    }

    public void addError(final int lineNumber, final String message) {
	addEntry(new MappingProcessEntry(lineNumber, MappingProcessEntryType.ERROR, message));
    }

    private int getNumberOf(final MappingProcessEntryType type) {
	final List<MappingProcessEntry> result = details.get(type);
	return result == null ? 0 : result.size();
    }

    public int getTotalCount() {
	return totalCount;
    }

    public int getSuccessesCount() {
	if (successesCount == null) {
	    successesCount = getNumberOf(MappingProcessEntryType.SUCCESS);
	}
	return successesCount;
    }

    public int getDuplicatesCount() {
	if (duplicatesCount == null) {
	    duplicatesCount = getNumberOf(MappingProcessEntryType.DUPLICATED);
	}
	return duplicatesCount;
    }

    public int getSkipsCount() {
	if (skipsCount == null) {
	    skipsCount = getNumberOf(MappingProcessEntryType.SKIP);
	}
	return skipsCount;
    }

    public int getErrorsCount() {
	if (errorsCount == null) {
	    errorsCount = getNumberOf(MappingProcessEntryType.ERROR);
	}
	return errorsCount;
    }

    public List<MappingProcessEntry> getErrors() {
	if (getErrorsCount() > 0 && errors.isEmpty()) {
	    errors.addAll(details.get(MappingProcessEntryType.ERROR).subList(0, getErrorsCount() > MAX_ERRORS_TO_SHOW ? 40 : getErrorsCount()));
	}
	return errors;
    }
}