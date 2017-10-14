package com.linkare.rec.web.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.validator.EmailValidator;
import org.primefaces.event.FlowEvent;

import com.linkare.commons.dao.security.LoginDAO;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.EqualityUtils;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.aop.ReCWebExceptionHandler;
import com.linkare.rec.web.aop.ExceptionHandle;
import com.linkare.rec.web.aop.ExceptionHandleCase;
import com.linkare.rec.web.model.Person;
import com.linkare.rec.web.service.UserService;
import com.linkare.rec.web.service.UserServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "userWizard")
@ViewScoped
public class UserWizard implements Serializable {

    private static final long serialVersionUID = -1668168683856714667L;

    private boolean skip;

    private String effect = "fade";

    private User user;

    private UIInput passwordInput;

    private UIInput confirmPasswordInput;

    private boolean isNew;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    /**
     * @return the passwordInput
     */
    public UIInput getPasswordInput() {
	return passwordInput;
    }

    /**
     * @param passwordInput
     *            the passwordInput to set
     */
    public void setPasswordInput(UIInput passwordInput) {
	this.passwordInput = passwordInput;
    }

    /**
     * @return the confirmPasswordInput
     */
    public UIInput getConfirmPasswordInput() {
	return confirmPasswordInput;
    }

    /**
     * @param confirmPasswordInput
     *            the confirmPasswordInput to set
     */
    public void setConfirmPasswordInput(UIInput confirmPasswordInput) {
	this.confirmPasswordInput = confirmPasswordInput;
    }

    /**
     * @return the isNew
     */
    public boolean isNew() {
	return isNew;
    }

    /**
     * @param isNew
     *            the isNew to set
     */
    public void setNew(boolean isNew) {
	this.isNew = isNew;
    }

    /**
     * @return the user
     */
    public User getUser() {
	if (user == null) {
	    final String userId = JsfUtil.getRequestParameter("userId");
	    if (userId == null) {
		isNew = true;
		user = new User();
	    } else {
		user = userService.find(Long.valueOf(userId));
	    }
	}
	if (user.getSubject() == null) {
	    user.setSubject(new Person());
	}
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.user = user;
    }

    @ExceptionHandle(@ExceptionHandleCase(exceptionHandler = ReCWebExceptionHandler.class))
    public String save() {
	if (isNew()) {
	    userService.create(getUser());
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_CREATE_KEY);
	    return ConstantUtils.CREATE;
	} else {
	    userService.edit(getUser());
	    setUser(userService.find(getUser().getIdInternal()));
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_UPDATE_KEY);
	    return ConstantUtils.VIEW;
	}
    }

    public String getEffect() {
	return effect;
    }

    public void setEffect(String effect) {
	this.effect = effect;
    }

    public boolean isSkip() {
	return skip;
    }

    public void setSkip(boolean skip) {
	this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
	if (skip) {
	    return "confirm";
	} else {
	    return event.getNewStep();
	}
    }

    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) {
	String email = (String) value;

	if (!EmailValidator.getInstance().isValid(email)) {
	    ((UIInput) toValidate).setValid(false);
	    JsfUtil.addErrorMessage(ConstantUtils.BUNDLE, toValidate.getClientId(context), ConstantUtils.LABEL_ERROR_KEY, "error.email.invalidFormat");
	}
    }

    public void validatePasswords(FacesContext context, UIComponent toValidate, Object value) {
	final String password = JsfUtil.getRequestParameter(getPasswordInput().getClientId());
	final String confirmPassword = JsfUtil.getRequestParameter(getConfirmPasswordInput().getClientId());

	if (!EqualityUtils.equals(password, confirmPassword)) {
	    getConfirmPasswordInput().setValid(false);
	    JsfUtil.addErrorMessage(ConstantUtils.BUNDLE, getConfirmPasswordInput().getClientId(context), ConstantUtils.LABEL_ERROR_KEY,
				    LoginDAO.ERROR_PASSWORD_AND_CONFIRM_PASSWORD_ARE_DIFFERENT);
	}
    }

    public boolean isValidPasswords() {
	return EqualityUtils.equals(getUser().getLogin().getPassword(), getUser().getLogin().getConfirmPassword());
    }
}