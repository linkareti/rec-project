package com.linkare.irn.nascimento.model.ext.mapping;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MappingProcessEntry {

    private int lineNumber;

    private MappingProcessEntryType type;

    private String message;

    /**
     * 
     */
    public MappingProcessEntry() {
	super();
    }

    /**
     * @param lineNumber
     *            the lineNumber the mapping process entry concerns to
     * @param type
     *            the type of the message
     * @param message
     *            the description of the entry message
     */
    public MappingProcessEntry(int lineNumber, MappingProcessEntryType type, String message) {
	super();
	this.lineNumber = lineNumber;
	this.type = type;
	this.message = message;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
	return lineNumber;
    }

    /**
     * @return the type
     */
    public MappingProcessEntryType getType() {
	return type;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }
}