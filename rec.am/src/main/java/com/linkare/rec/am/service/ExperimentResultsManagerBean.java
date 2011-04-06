package com.linkare.rec.am.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.linkare.rec.am.ExperimentResultsManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote(ExperimentResultsManager.class)
public class ExperimentResultsManagerBean implements ExperimentResultsManager {

}
