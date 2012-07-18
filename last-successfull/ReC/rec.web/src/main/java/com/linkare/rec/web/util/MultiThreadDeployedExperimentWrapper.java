package com.linkare.rec.web.util;

import com.linkare.rec.web.RecChatMessageDTO;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.model.HardwareState;

public class MultiThreadDeployedExperimentWrapper {

    private final DeployedExperiment underlyingDeployedExperiment;
    private final Experiment experiment;

    private final Lock readLock;
    private final Lock writeLock;

    public MultiThreadDeployedExperimentWrapper(final DeployedExperiment experiment) {
	this.underlyingDeployedExperiment = new DeployedExperiment(experiment);
	this.experiment = underlyingDeployedExperiment.getExperiment();

	final ReadWriteLock lock = new ReentrantReadWriteLock();
	this.readLock = lock.readLock();
	this.writeLock = lock.writeLock();
    }

    public HardwareState getState() {
	readLock.lock();
	try {
	    return underlyingDeployedExperiment.getState();
	} finally {
	    readLock.unlock();
	}
    }

    public Set<String> getUsersConnected() {
	readLock.lock();
	try {
	    return Collections.unmodifiableSet(underlyingDeployedExperiment.getUsersConnected());
	} finally {
	    readLock.unlock();
	}
    }

    public int getNumberOfUsers() {
	readLock.lock();
	try {
	    return underlyingDeployedExperiment.getNumberOfUsers();
	} finally {
	    readLock.unlock();
	}
    }

    public void refreshState(final byte newState) {
	final HardwareState state = HardwareState.valueOfCode(newState);
	writeLock.lock();
	try {
	    underlyingDeployedExperiment.setState(state);
	} finally {
	    writeLock.unlock();
	}
    }

    public boolean addNewClient(final String userName) {
	writeLock.lock();
	try {
	    return underlyingDeployedExperiment.getUsersConnected().add(userName);
	} finally {
	    writeLock.unlock();
	}
    }

    public boolean removeClient(final String userName) {
	writeLock.lock();
	try {
	    return underlyingDeployedExperiment.getUsersConnected().remove(userName);
	} finally {
	    writeLock.unlock();
	}
    }
    
    public boolean isLabRunning() {
	readLock.lock();
	try {
	    return underlyingDeployedExperiment.isLabRunning();
	} finally {
	    readLock.unlock();
	}
    }
    
    public boolean addNewRecChatMessage(final RecChatMessageDTO recChatMessageDTO) {
	writeLock.lock();
	try {
	    return underlyingDeployedExperiment.getRecChatMessages().add(recChatMessageDTO);
                    
	} finally {
	    writeLock.unlock();
	}
    }
 
    public String getStateLabel() {
	return experiment.getState().getLabel();
    }

    public String getExternalId() {
	return experiment.getExternalId();
    }

    public String getDescription() {
	return experiment.getDescription();
    }

    public Long getIdInternal() {
	return experiment.getIdInternal();
    }

    public String getName() {
	return experiment.getName();
    }

    public String getPresentationName() {
	return experiment.getPresentationName();
    }

}
