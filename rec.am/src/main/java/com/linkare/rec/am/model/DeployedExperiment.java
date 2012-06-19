package com.linkare.rec.am.model;

import java.util.List;

import com.linkare.commons.jpa.security.User;

/**
 * Represents an experiment deployed on a laboratory. Contains all the information about the experiment, its current state, users currently connected and other
 * information regarding an experiment registered on a multicast.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
public class DeployedExperiment {

    private Experiment experiment;
    private HardwareState state;
    private List<User> usersConnected;
    private int numberOfUsers;

    public Experiment getExperiment() {
	return experiment;
    }

    public void setExperiment(Experiment experiment) {
	this.experiment = experiment;
    }

    public HardwareState getState() {
	return state;
    }

    public void setState(HardwareState state) {
	this.state = state;
    }

    public List<User> getUsersConnected() {
	return usersConnected;
    }

    public void setUsersConnected(List<User> usersConnected) {
	this.usersConnected = usersConnected;
    }

    public int getNumberOfUsers() {
	if (usersConnected != null && usersConnected.size() > 0) {
	    numberOfUsers = usersConnected.size();
	}
	return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
	this.numberOfUsers = numberOfUsers;
    }
    
    public boolean isLabRunning() {
	return state == HardwareState.STARTED;
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((experiment == null) ? 0 : experiment.hashCode());
	result = prime * result + numberOfUsers;
	result = prime * result + ((state == null) ? 0 : state.hashCode());
	result = prime * result + ((usersConnected == null) ? 0 : usersConnected.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DeployedExperiment other = (DeployedExperiment) obj;
	if (experiment == null) {
	    if (other.experiment != null)
		return false;
	} else if (!experiment.equals(other.experiment))
	    return false;
	if (numberOfUsers != other.numberOfUsers)
	    return false;
	if (state != other.state)
	    return false;
	if (usersConnected == null) {
	    if (other.usersConnected != null)
		return false;
	} else if (!usersConnected.equals(other.usersConnected))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "DeployedExperiment [experiment=" + experiment + ", state=" + state + ", usersConnected=" + usersConnected + ", numberOfUsers=" + numberOfUsers
		+ "]";
    }
}