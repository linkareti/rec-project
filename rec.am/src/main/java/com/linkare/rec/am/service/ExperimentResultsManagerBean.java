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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    private static final Log LOG = LogFactory.getLog(ExperimentResultsManagerBean.class);

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager entityManager;

    @Override
    public void persistExperimentResults(DataProducerDTO experimentResult) throws RemoteException {
	final DataProducer entity;
	if (LOG.isInfoEnabled()) {
	    final long start = System.currentTimeMillis();
	    try {
		entity = DozerBeanMapperSingletonWrapper.getInstance().map(experimentResult, DataProducer.class);
	    } finally {
		final long time = System.currentTimeMillis() - start;
		LOG.info(new StringBuilder("time spent mapping DataProducer entity").append(" :").append(time).toString());
	    }

	} else {
	    entity = DozerBeanMapperSingletonWrapper.getInstance().map(experimentResult, DataProducer.class);
	}

	entityManager.persist(entity);
    }

    //only for test purposes
    public DataProducerDTO getExperimentResultByID(final Long id) {

	if (id == null) {
	    throw new NullPointerException("id cannot be null");
	}

	final DataProducer dataProducer = entityManager.find(DataProducer.class, id);

	final DataProducerDTO dto = DozerBeanMapperSingletonWrapper.getInstance().map(dataProducer, DataProducerDTO.class);

	return dto;
    }

}
