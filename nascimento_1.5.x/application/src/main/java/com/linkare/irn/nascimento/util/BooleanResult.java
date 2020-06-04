package com.linkare.irn.nascimento.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public final class BooleanResult {

    public static final String GENERAL_ERROR_CODE = "Unspecified";

    public static final String NOT_ALLOWED_BY_DEFAULT = "Not allowed by default";

    public static final BooleanResult TRUE = new BooleanResult();

    public static final BooleanResult FALSE = new BooleanResult(false, NOT_ALLOWED_BY_DEFAULT, GENERAL_ERROR_CODE);

    private final boolean ok;

    private final String code;

    private final String why;

    public BooleanResult() {
	this(true, null, GENERAL_ERROR_CODE);
    }

    public BooleanResult(final boolean result) {
	this(result, null, GENERAL_ERROR_CODE);
    }

    public BooleanResult(final boolean result, final String why) {
	this(result, why, GENERAL_ERROR_CODE);
    }

    public BooleanResult(final boolean result, final String why, final String code) {
	super();
	this.ok = result;
	this.why = why;
	this.code = StringUtils.isBlank(code) ? GENERAL_ERROR_CODE : code;
    }

    /**
     * @return the result
     */
    public boolean isOk() {
	return ok;
    }

    /**
     * @return the why
     */
    public String getWhy() {
	return why;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }
}