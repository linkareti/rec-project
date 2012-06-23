package com.linkare.rec.am.util;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.linkare.rec.am.model.DeployedExperiment;
import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.HardwareState;

public class MultiThreadDeployedExperimentWrapper {

    private final DeployedExperiment underlyingExperiment;

    private final Lock readLock;
    private final Lock writeLock;

    public MultiThreadDeployedExperimentWrapper(final DeployedExperiment experiment) {
	this.underlyingExperiment = new DeployedExperiment(experiment);

	final ReadWriteLock lock = new ReentrantReadWriteLock();
	this.readLock = lock.readLock();
	this.writeLock = lock.writeLock();
    }

    public HardwareState getState() {
	readLock.lock();
	try {
	    return underlyingExperiment.getState();
	} finally {
	    readLock.unlock();
	}
    }

    public Collection<String> getUsersConnected() {
	readLock.lock();
	try {
	    return Collections.unmodifiableCollection(underlyingExperiment.getUsersConnected());
	} finally {
	    readLock.unlock();
	}
    }

    public int getNumberOfUsers() {
	readLock.lock();
	try {
	    return underlyingExperiment.getNumberOfUsers();
	} finally {
	    readLock.unlock();
	}
    }

    public void refreshState(final byte newState) {
	final HardwareState state = HardwareState.valueOfCode(newState);
	writeLock.lock();
	try {
	    underlyingExperiment.setState(state);
	} finally {
	    writeLock.unlock();
	}
    }

    public boolean addNewClient(final String userName) {
	writeLock.lock();
	try {
	    return underlyingExperiment.getUsersConnected().add(userName);
	} finally {
	    writeLock.unlock();
	}
    }

    public boolean removeClient(final String userName) {
	writeLock.lock();
	try {
	    return underlyingExperiment.getUsersConnected().remove(userName);
	} finally {
	    writeLock.unlock();
	}
    }

    private Experiment getExperiment() {
	return underlyingExperiment.getExperiment();
    }

    public String getExternalId() {
	return getExperiment().getExternalId();
    }

    public String getDescription() {
	return getExperiment().getDescription();
    }

    public Long getIdInternal() {
	return getExperiment().getIdInternal();
    }

    public String getName() {
	return getExperiment().getName();
    }

    public String getPresentationName() {
	return getExperiment().getPresentationName();
    }

}
