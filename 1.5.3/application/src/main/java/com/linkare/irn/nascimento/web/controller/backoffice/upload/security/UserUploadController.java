package com.linkare.irn.nascimento.web.controller.backoffice.upload.security;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.security.UserDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.security.UserDTO;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.service.security.UserService;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.security.UserLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class UserUploadController extends BaseFileUploadController<User, UserDTO, UserDAO> {

    private static final long serialVersionUID = -560744945272669439L;

    private GenericLazyDataModel<User, UserDAO> dataModel;

    @Inject
    private UserService userService;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.dataModel = new UserLazyDataModel(userService.getDAO());
    }

    @Override
    public long getNumberOfExistingRecords() {
	return userService.count();
    }

    @Override
    public String getSpecificModuleGlobalMessagesClientId() {
	return "globalUserSpecificMessages";
    }

    @Override
    public GenericLazyDataModel<User, UserDAO> getDataModel() {
	return dataModel;
    }

    public void onRowEdit(RowEditEvent event) throws DomainException {
	final User user = (User) event.getObject();
	userService.update(user);
	JsfUtils.addGlobalInfoMessage(getMessage("message.info.userUpdated", user.getUsername()));
    }

    @Override
    protected String getDataListComponentId() {
	return "usersTable";
    }

    @Override
    public IMapperProcessor<User, UserDTO, UserDAO> getMapperProcessor() {
	return userService;
    }

    @Override
    public String getModuleName() {
	return User.class.getSimpleName();
    }
}