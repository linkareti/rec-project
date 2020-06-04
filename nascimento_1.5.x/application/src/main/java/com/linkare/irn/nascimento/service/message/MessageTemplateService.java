package com.linkare.irn.nascimento.service.message;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.linkare.irn.nascimento.dao.message.MessageTemplateDAO;
import com.linkare.irn.nascimento.model.message.MessageTemplate;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
public class MessageTemplateService extends BaseStatelessServiceImpl<MessageTemplate, MessageTemplateDAO> {

    @Override
    public MessageTemplateDAO getDAO() {
	return new MessageTemplateDAO(getEntityManager());
    }

    public MessageTemplate findByCode(final String code) {
	return getDAO().findByCode(code);
    }
}