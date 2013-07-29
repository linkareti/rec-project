package com.linkare.rec.web.model;

import com.linkare.rec.web.RecChatMessageDTO;
import java.util.HashSet;
import java.util.Set;

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
    private Set<String> usersConnected;
    private RecChatMessageDTO recChatMessages;

    public DeployedExperiment(final DeployedExperiment experiment) {
        this.experiment = experiment.getExperiment();
        this.state = experiment.getState();
        this.usersConnected = new HashSet<String>(experiment.getUsersConnected());
        this.recChatMessages = experiment.recChatMessages;
    }

    public DeployedExperiment() {
    }

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

    public Set<String> getUsersConnected() {
        return usersConnected;
    }

    public void setUsersConnected(final Set<String> usersConnected) {
        this.usersConnected = usersConnected;
    }

    public int getNumberOfUsers() {
        return usersConnected != null ? usersConnected.size() : 0;
    }

    public boolean isLabRunning() {
        return (state != null);
    }

    public RecChatMessageDTO getRecChatMessages() {
           return recChatMessages;
    }

    public void setRecChatMessages(RecChatMessageDTO recChatMessages) {
        this.recChatMessages = recChatMessages;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((experiment == null) ? 0 : experiment.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((usersConnected == null) ? 0 : usersConnected.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DeployedExperiment other = (DeployedExperiment) obj;
        if (experiment == null) {
            if (other.experiment != null) {
                return false;
            }
        } else if (!experiment.equals(other.experiment)) {
            return false;
        }
        if (state != other.state) {
            return false;
        }
        if (usersConnected == null) {
            if (other.usersConnected != null) {
                return false;
            }
        } else if (!usersConnected.equals(other.usersConnected)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeployedExperiment [experiment=" + experiment + ", state=" + state + ", usersConnected=" + usersConnected + ", numberOfUsers="
                + getNumberOfUsers()
                + "]";
    }
}