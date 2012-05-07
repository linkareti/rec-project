package com.linkare.rec.am.service;

import java.rmi.RemoteException;
import java.util.List;

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

import com.linkare.rec.am.RepositoryFacade;
import com.linkare.rec.am.model.DataProducer;
import com.linkare.rec.am.model.util.converter.DozerBeanMapperSingletonWrapper;
import com.linkare.rec.am.repository.DataProducerDTO;
import com.linkare.rec.am.service.interceptor.TracingInterceptor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote(RepositoryFacade.class)
@Interceptors({ TracingInterceptor.class })
public class RepositoryFacadeBean implements RepositoryFacade {

    private static final Log LOG = LogFactory.getLog(RepositoryFacadeBean.class);

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager entityManager;

    @Override
    public void persistExperimentResults(DataProducerDTO experimentResult) throws RemoteException {

	if (experimentResult == null) {
	    throw new NullPointerException("experiment cannot be null");
	}

	//if already persisted just return	
	if (getExperimentResultByOID(experimentResult.getOid()) != null) {
	    return;
	}

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

    public DataProducerDTO getExperimentResultByOID(final String oid) throws RemoteException {

	if (oid == null) {
	    throw new NullPointerException("oid cannot be null");
	}

	final javax.persistence.Query query = entityManager.createNamedQuery(DataProducer.FIND_BY_OID_QUERYNAME)
							   .setParameter(DataProducer.QUERY_PARAM_OID, oid);

	@SuppressWarnings("unchecked")
	final List<DataProducer> dataProducers = (List<DataProducer>) query.getResultList();

	if (dataProducers.size() > 0) {
	    return DozerBeanMapperSingletonWrapper.getInstance().map(dataProducers.get(0), DataProducerDTO.class);
	} else {
	    return null;
	}

    }
}