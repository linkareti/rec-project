package com.linkare.rec.am.web;

import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.web.util.PaginationHelper;
import com.linkare.rec.am.model.ReservationFacade;
import java.io.Serializable;
import java.util.Calendar;

import java.util.ResourceBundle;
import java.util.StringTokenizer;
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

@ManagedBean(name = "reservationController")
@RequestScoped
public class ReservationController implements Serializable {

    private Reservation current;

    private DataModel items = null;

    @EJB
    private com.linkare.rec.am.model.ReservationFacade ejbFacade;

    private PaginationHelper pagination;

    private int selectedItemIndex;

    private final String BUNDLE = "/Bundle";

    private final String VIEW = "View";

    private final String CREATE = "Create";

    private final String LIST = "List";

    private final String EDIT = "Edit";

    public ReservationController() {
    }

    public Reservation getSelected() {
        if (current == null) {
            current = new Reservation();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReservationFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

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

    public String prepareList() {
        recreateModel();
        return LIST;
    }

    public String prepareView() {
        current = (Reservation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return VIEW;
    }

    public String prepareCreate() {
        current = new Reservation();
        selectedItemIndex = -1;
        return CREATE;
    }

    public String create() {
        try {
            processEndDateAndEndTimeSlot();
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void processEndDateAndEndTimeSlot() {
        String timeSlot = current.getStartTimeSlot();
        StringTokenizer st = new StringTokenizer(timeSlot, ":");
        Calendar cal = Calendar.getInstance();
        cal.setTime(current.getStartDate());
        cal.set(Calendar.HOUR, Integer.parseInt(st.nextToken()));
        cal.set(Calendar.MINUTE, Integer.parseInt(st.nextToken()));
        current.setStartDate(cal.getTime());
        current.setStartTimeSlot(timeSlot);
        if (cal.get(Calendar.MINUTE) == 30) {
            cal.roll(Calendar.HOUR, true);
            cal.set(Calendar.MINUTE, 0);
        } else if (cal.get(Calendar.MINUTE) == 0) {
            cal.set(Calendar.MINUTE, 30);
        }

        current.setEndDate(cal.getTime());
        current.setEndTimeSlot(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
    }

    public String prepareEdit() {
        current = (Reservation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return EDIT;
    }

    public String update() {
        try {
            processEndDateAndEndTimeSlot();
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationUpdated"));
            return VIEW;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Reservation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return LIST;
    }

    public String destroyAndView() {
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return LIST;
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return LIST;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getTimeSlotSelectOne() {
        return JsfUtil.getTimeSlotItems();
    }

    @FacesConverter(forClass = Reservation.class)
    public static class ReservationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReservationController controller = (ReservationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reservationController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Reservation) {
                Reservation o = (Reservation) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Reservation.class.getName());
            }
        }
    }
}
