package com.linkare.rec.am;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.linkare.rec.am.repository.DataProducerDTO;

public interface RepositoryFacade extends Remote {

    public void persistExperimentResults(DataProducerDTO experimentResult) throws RemoteException;

    public DataProducerDTO getExperimentResultByOID(final String oid) throws RemoteException;

}
