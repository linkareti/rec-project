package com.linkare.irn.nascimento.service.core;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.linkare.irn.nascimento.dao.core.BirthRegistrationDocumentDAO;
import com.linkare.irn.nascimento.model.core.BirthRegistrationDocument;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationDocumentService extends BaseStatelessServiceImpl<BirthRegistrationDocument, BirthRegistrationDocumentDAO> {


    @Override
    public BirthRegistrationDocumentDAO getDAO() {
	return new BirthRegistrationDocumentDAO(getEntityManager());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public BirthRegistrationDocument getFileContent(final long id) {
    	return getDAO().find(id);
	
    }

  
}