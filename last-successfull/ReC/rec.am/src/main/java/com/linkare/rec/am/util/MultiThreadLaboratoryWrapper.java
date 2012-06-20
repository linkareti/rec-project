package com.linkare.rec.am.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.HardwareStateChangeDTO;
import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.mbean.NotificationTypeEnum;
import com.linkare.rec.am.model.DeployedExperiment;
import com.linkare.rec.am.model.HardwareState;
import com.linkare.rec.am.model.Laboratory;

public class MultiThreadLaboratoryWrapper {

    private static final String PERSISTENCE_UNIT_NAME = "AllocationManagerPU";

    private final Laboratory underlyingLaboratory;

    private final ConcurrentMap<String, DeployedExperiment> experimentsMap;

    private final ConcurrentMap<String, User> usersMap;

    private final EntityManagerFactory entityManagerFactory;

    private final Lock readlock;
    private final Lock writelock;

    public MultiThreadLaboratoryWrapper(final Laboratory lab) {
	this.underlyingLaboratory = lab;
	final ReadWriteLock lock = new ReentrantReadWriteLock();
	readlock = lock.readLock();
	writelock = lock.writeLock();

	experimentsMap = new ConcurrentHashMap<String, DeployedExperiment>();
	usersMap = new ConcurrentHashMap<String, User>();

	entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void refreshFrom(final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO) {
	if (multiCastControllerNotifInfoDTO == null) {
	    return;
	}

	final NotificationTypeEnum notificationType = getNotificationTypeEnum(multiCastControllerNotifInfoDTO);

	if (notificationType == null) {
	    return;
	}

	switch (notificationType) {
	case HARDWARE_STATE_CHANGE:
	    hardwareStateChange(multiCastControllerNotifInfoDTO.getHardwareStateChangeDTO());
	    break;
	case REGISTER_NEW_HARDWARE:
	    addHardware(multiCastControllerNotifInfoDTO.getHardwareInfoDTO());
	    break;
	case REGISTER_NEW_CLIENT_MC:
	    addClient(multiCastControllerNotifInfoDTO.getClientInfoDTO());
	    break;
	case REGISTER_NEW_CLIENT_HARDWARE:
	    addClient(multiCastControllerNotifInfoDTO.getClientInfoDTO());
	    break;
	case UNREGISTER_HARDWARE:
	    removeHardware(multiCastControllerNotifInfoDTO.getHardwareInfoDTO());
	    break;
	case UNREGISTER_CLIENT_MC:
	    removeUser(multiCastControllerNotifInfoDTO.getClientInfoDTO());
	    break;
	case UNREGISTER_CLIENT_HARDWARE:
	    removeUser(multiCastControllerNotifInfoDTO.getClientInfoDTO());
	    break;
	default:
	    throw new UnsupportedOperationException();
	}

    }

    private NotificationTypeEnum getNotificationTypeEnum(final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO) {
	return NotificationTypeEnum.fromType(multiCastControllerNotifInfoDTO.getNotificationType());
    }

    public String getName() {
	return underlyingLaboratory.getName();
    }

    private void removeHardware(final HardwareInfoDTO hardwareInfoDTO) {
	experimentsMap.remove(hardwareInfoDTO.getHardwareUniqueID());
    }

    private void removeUser(final ClientInfoDTO clientInfoDTO) {
	usersMap.remove(clientInfoDTO.getUserName());
    }

    public void addHardwares(final List<HardwareInfoDTO> hardwares) {
	for (final HardwareInfoDTO hardwareInfoDTO : hardwares) {
	    addHardware(hardwareInfoDTO);
	}
    }

    private void addHardware(final HardwareInfoDTO hardwareInfoDTO) {

	final DeployedExperiment deployedExperiment = getDeployedExperiment(hardwareInfoDTO.getHardwareUniqueID());

	experimentsMap.putIfAbsent(deployedExperiment.getExperiment().getExternalId(), deployedExperiment);
    }

    private DeployedExperiment getDeployedExperiment(final String externalID) {

	DeployedExperiment experiment = experimentsMap.get(externalID);

	if (experiment == null) {
	    experiment = loadExperimentFromBD(externalID);
	}
	return experiment;
    }

    private DeployedExperiment loadExperimentFromBD(final String externalID) {
	//TODO: find the best way to do this 
	return null;
    }

    public void addUsers(final List<ClientInfoDTO> clients) {
	for (final ClientInfoDTO user : clients) {
	    addClient(user);
	}
    }

    private void addClient(final ClientInfoDTO clientInfoDTO) {

	User user = usersMap.get(clientInfoDTO.getUserName());

	if (user == null) {
	    user = loadUserFromBD(clientInfoDTO.getUserName());
	}

	usersMap.putIfAbsent(user.getUsername(), user);

    }

    private User loadUserFromBD(final String username) {
	return null;
    }

    private void hardwareStateChange(final HardwareStateChangeDTO hardwareStateDTO) {

	final DeployedExperiment deployedExperiment = getDeployedExperiment(hardwareStateDTO.getHardwareUniqueID());

	final HardwareState newState = getHardwareState(hardwareStateDTO.getNewState());

	if (newState == null) {
	    return;
	}

	writelock.lock();
	try {
	    deployedExperiment.setState(newState);
	} finally {
	    writelock.unlock();
	}
    }

    private HardwareState getHardwareState(final String stateAsStr) {
	HardwareState state = null;
	try {
	    state = HardwareState.valueOf(stateAsStr);
	} catch (IllegalArgumentException e) {
	    state = null;
	}
	return state;
    }

    public List<DeployedExperiment> getLiveExperiments() {
	try {

	} finally {

	}
	
	return Collections.emptyList();
    }

}
