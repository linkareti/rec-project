package com.linkare.rec.am.web.util;

import java.util.ResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class ConstantUtils {

    public static final String BUNDLE = "/Bundle";

    public static final String USER_VIEW_SESSION_KEY = "userView";

    public static final String LABEL_INFO_KEY = "label.info";
    public static final String LABEL_INFO = ResourceBundle.getBundle(BUNDLE).getString(LABEL_INFO_KEY);

    public static final String LABEL_WARN_KEY = "label.warn";
    public static final String LABEL_WARN = ResourceBundle.getBundle(BUNDLE).getString(LABEL_WARN_KEY);

    public static final String LABEL_ERROR_KEY = "label.error";
    public static final String LABEL_ERROR = ResourceBundle.getBundle(BUNDLE).getString(LABEL_ERROR_KEY);

    public static final String INFO_CREATE_KEY = "info.create";

    public static final String INFO_UPDATE_KEY = "info.update";

    public static final String INFO_REMOVE_KEY = "info.remove";

    public static final String ERROR_PERSISTENCE_KEY = "error.persistence";

    public static final String VIEW = "View";

    public static final String CREATE = "Create";

    public static final String LIST = "List";

    public static final String EDIT = "Edit";

    public static final String INTERNAL_DOMAIN_NAME = "internal";

    public static final int DEFAULT_PAGE_SIZE = 20;

    private ConstantUtils() {
    }
}