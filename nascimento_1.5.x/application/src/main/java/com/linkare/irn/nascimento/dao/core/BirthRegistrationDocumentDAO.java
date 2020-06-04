package com.linkare.irn.nascimento.dao.core;

import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.core.BirthRegistrationDocument;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BirthRegistrationDocumentDAO extends GenericDAO<BirthRegistrationDocument> {


    /**
     * @param entityManager
     */
    public BirthRegistrationDocumentDAO(EntityManager entityManager) {
	super(entityManager);
    }

  
}