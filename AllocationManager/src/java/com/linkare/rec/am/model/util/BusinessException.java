package com.linkare.rec.am.model.util;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Joao
 */
public final class BusinessException extends Exception {

    private static final String NOT_FOUND = "???";

    private String id;

    private String bundle;

    private Number code;

    private String message;

    private Collection<Exception> errorList = new ArrayList<Exception>();

    private static long nextUniqueId = 0;

    private String[] params;

    /**
     *
     * @param e SQLException
     */
    public BusinessException(SQLException e) {
        setCode(e.getErrorCode());
        setMessage(e.getMessage());
        getErrorList().add(e);
        setId(getUniqueId());
    }

    /**
     *
     * @param e Exception
     */
    public BusinessException(Exception e) {
        setMessage(e.getMessage());
        getErrorList().add(e);
        setId(getUniqueId());
    }

    /**
     *
     * @param message message
     * @param e Exception
     */
    public BusinessException(Exception e, String message) {
        setMessage(message);
        getErrorList().add(e);
        setId(getUniqueId());
    }


    /**
     *
     * @param code code
     * @param message message
     */
    public BusinessException(Number code, String message) {

        setCode(code);
        setMessage(message);
        setId(getUniqueId());

    }

    /**
     *
     * @param message message
     */
    public BusinessException(String message) {

        setMessage(message);
        setId(getUniqueId());

    }

    /**
     *
     * @param bundle Message Bundle
     * @param code code
     * @param message message
     */
    public BusinessException(String bundle, Number code, String message) {
        setBundle(bundle);
        setCode(code);
        setMessage(message);
        setId(getUniqueId());
    }

    /**
     *
     * @param code error code
     * @param bundle message bundle
     * @param bundleID message id
     */
    public BusinessException(Number code, ResourceBundle bundle, String bundleID) {

        String detail = "";

        if (bundleID != null) {
            try {
                detail = bundle.getString(bundleID);
            } catch (Exception e) {
                detail = NOT_FOUND + bundleID + NOT_FOUND;
            }
        }
        setCode(code);
        setMessage(detail);
        setId(getUniqueId());
    }

    public BusinessException(Number code, String bundle, String[] params) {
        setBundle(bundle);
        setCode(code);
        setMessage(bundle);
        setId(getUniqueId());
        setParams(params);
    }

    public BusinessException(Number code, ResourceBundle bundle, Locale locale, String bundleID, String[] params) {

        String detail = "";

        if (bundleID != null) {
            try {
                detail = bundle.getString(bundleID);
            } catch (Exception e) {
                detail = NOT_FOUND + bundleID + NOT_FOUND;
            }

            if (params != null) {
                MessageFormat mf = new MessageFormat(detail, locale);
                detail = mf.format(params);
            }
        }
        setCode(code);
        setMessage(detail);
        setId(getUniqueId());
        setParams(params);
    }

    private static synchronized String getUniqueId() {
        if (nextUniqueId == 0) {
            nextUniqueId = Math.abs(Calendar.getInstance().getTimeInMillis());
        }
        return Long.toHexString(nextUniqueId++).toUpperCase();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setErrorList(Collection<Exception> errorList) {
        this.errorList = errorList;
    }

    public Collection<Exception> getErrorList() {
        return errorList;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getBundle() {
        return bundle;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public Number getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("\n");
        buf.append(getClass().getName());
        buf.append("\nCode: ");
        buf.append(getCode());
        buf.append("\nId: ");
        buf.append(getId());
        if (getMessage() != null) {
            buf.append("\nMessage  : ");
            buf.append(getMessage().toString());
        }
        buf.append("\n");
        buf.append(super.toString());
        return buf.toString();
    }

    /**
     * @return StackTrace
     */
    @Override
    public StackTraceElement[] getStackTrace() {
        StackTraceElement[] ret = null;
        if (getErrorList().size() > 0) {
            ret = ((Exception) getErrorList().iterator().next()).getStackTrace();
        } else {
            ret = super.getStackTrace();
        }
        return ret;
    }

    public void setParams(String[] params) {
        this.params = params.clone();
    }

    public String[] getParams() {
        return params;
    }
}
