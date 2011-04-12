package com.linkare.rec.am.service;

import java.rmi.RemoteException;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linkare.rec.am.ExperimentResultsManager;
import com.linkare.rec.am.experiment.DataProducerDTO;
import com.linkare.rec.am.model.DataProducer;
import com.linkare.rec.am.model.util.converter.DozerBeanMapperSingletonWrapper;
import com.linkare.rec.am.service.interceptor.TracingInterceptor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote(ExperimentResultsManager.class)
@Interceptors({ TracingInterceptor.class })
public class ExperimentResultsManagerBean implements ExperimentResultsManager {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager entityManager;

    @Override
    public void persistExperimentResults(DataProducerDTO experimentResult) throws RemoteException {
	entityManager.persist(DozerBeanMapperSingletonWrapper.getInstance().map(experimentResult, DataProducer.class));
    }

}
