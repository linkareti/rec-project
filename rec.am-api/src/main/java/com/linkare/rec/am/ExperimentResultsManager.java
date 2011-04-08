package com.linkare.rec.am;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.linkare.rec.am.experiment.DataProducerDTO;

public interface ExperimentResultsManager extends Remote {

    public void mergeExperimentResults(DataProducerDTO experimentResult) throws RemoteException;

}
