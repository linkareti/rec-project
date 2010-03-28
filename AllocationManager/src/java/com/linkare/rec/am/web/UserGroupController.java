package com.linkare.rec.am.web;

import com.linkare.rec.am.model.UserGroup;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.web.util.PaginationHelper;
import com.linkare.rec.am.model.UserGroupFacade;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "userGroupController")
@RequestScoped
public class UserGroupController implements Serializable {

    private UserGroup current;

    private DataModel items = null;

    @EJB
    private com.linkare.rec.am.model.UserGroupFacade ejbFacade;

    private PaginationHelper pagination;

    private int selectedItemIndex;

    private static Logger logger = Logger.getLogger("UserGroupController");

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final String BUNDLE = "/Bundle";

    private static final String VIEW = "View";

    private static final String CREATE = "Create";

    private static final String LIST = "List";

    private static final String EDIT = "Edit";

    public UserGroupController() {
    }

    public final UserGroup getSelected() {
        if (current == null) {
            current = new UserGroup();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserGroupFacade getFacade() {
        return ejbFacade;
    }

    public final PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(DEFAULT_PAGE_SIZE) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public final String prepareList() {
        recreateModel();
        return LIST;
    }

    public final String prepareView() {
        current = (UserGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return VIEW;
    }

    public final String prepareCreate() {
        current = new UserGroup();
        selectedItemIndex = -1;
        return CREATE;
    }

    public final String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stacktrace = sw.toString();
            getLogger().severe("Stack Trace: \n" + stacktrace);

            return null;
        }
    }

    public final String prepareEdit() {
        current = (UserGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return EDIT;
    }

    public final String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupUpdated"));
            return VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public final String destroy() {
        current = (UserGroup) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return LIST;
    }

    public final String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return VIEW;
        } else {
            // all items were removed - go back to list
            recreateModel();
            return LIST;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
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
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public final DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public final String next() {
        getPagination().nextPage();
        recreateModel();
        return LIST;
    }

    public final String previous() {
        getPagination().previousPage();
        recreateModel();
        return LIST;
    }

    public final SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public final SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = UserGroup.class)
    public static class UserGroupControllerConverter implements Converter {

        @Override
        public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserGroupController controller = (UserGroupController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userGroupController");
            return controller.ejbFacade.find(getKey(value));
        }

        private String getKey(String value) {
            return value;
        }

        private String getStringKey(String value) {
            return value;
        }

        @Override
        public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserGroup) {
                UserGroup o = (UserGroup) object;
                return getStringKey(o.getName());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserGroup.class.getName());
            }
        }
    }

    /**
     * @return the logger
     */
    public static final Logger getLogger() {
        return logger;
    }
}