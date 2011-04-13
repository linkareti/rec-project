package com.linkare.rec.am;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.linkare.rec.am.experiment.DataProducerDTO;

public interface ExperimentResultsManager extends Remote {

    public void persistExperimentResults(DataProducerDTO experimentResult) throws RemoteException;

    public DataProducerDTO getExperimentResultByID(final Long id);

}
