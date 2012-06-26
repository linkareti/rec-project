package com.linkare.rec.am.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.am.RegisteredHardwareDTO;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.mbean.NotificationTypeEnum;
import com.linkare.rec.am.model.DeployedExperiment;
import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.HardwareState;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.ExperimentService;
import com.linkare.rec.am.web.util.JndiHelper;

public class MultiThreadLaboratoryWrapper {

    private final static Logger LOGGER = LoggerFactory.getLogger(MultiThreadLaboratoryWrapper.class);

    private final Laboratory underlyingLaboratory;

    private final ConcurrentMap<String, MultiThreadDeployedExperimentWrapper> deployedExperimentsMap;

    private final Set<String> usersSet;

    private final ExperimentService experimentService;

    private volatile long lastNotifReceived;

    private volatile long uptime;

    private IMultiCastControllerMXBean mbeanProxy;

    public MultiThreadLaboratoryWrapper(final MbeanProxy<IMultiCastControllerMXBean, Laboratory> labMBeanPRoxy) throws NamingException {
	this.underlyingLaboratory = labMBeanPRoxy.getEntity();
	this.mbeanProxy = labMBeanPRoxy.getMbeanInterface();
	this.experimentService = JndiHelper.getExperimentService();
	this.deployedExperimentsMap = new ConcurrentHashMap<String, MultiThreadDeployedExperimentWrapper>();
	this.usersSet = new ConcurrentSkipListSet<String>();
	init();
    }

    private void init() {
	synchronized (this) {
	    final long uptime = mbeanProxy.getUpTimeInMillis();

	    final List<ClientInfoDTO> labUsers = mbeanProxy.getClients();

	    final Map<String, RegisteredHardwareDTO> registeredHardwares = mbeanProxy.getRegisteredHardwaresInfo(null);

	    initDeployedExperimentsMap(registeredHardwares.values());
	    initUsersSet(labUsers);

	    this.lastNotifReceived = -1;
	    this.uptime = uptime;
	}
    }

    private void initUsersSet(List<ClientInfoDTO> labUsers) {
	this.usersSet.addAll(getUsersAsStringSet(labUsers));
    }

    private Set<String> getUsersAsStringSet(final List<ClientInfoDTO> labUsers) {
	Set<String> usersSet = Collections.emptySet();

	if (!labUsers.isEmpty()) {
	    usersSet = new HashSet<String>(labUsers.size());

	    for (final ClientInfoDTO clientInfoDTO : labUsers) {
		usersSet.add(clientInfoDTO.getUserName());
	    }
	}
	return usersSet;
    }

    private void initDeployedExperimentsMap(final Collection<RegisteredHardwareDTO> registeredHardwares) {

	if (!registeredHardwares.isEmpty()) {
	    for (final RegisteredHardwareDTO registeredHardwareDTO : registeredHardwares) {
		initMultiThreadDeployedExperimentWrapperIfNotAlreadyInCache(registeredHardwareDTO);
	    }
	}
    }

    private boolean removeUserFromHardware(final String experimentID, final String userName) {
	boolean result = false;
	final MultiThreadDeployedExperimentWrapper deployedExperimentWrapper = deployedExperimentsMap.get(experimentID);
	if (deployedExperimentWrapper != null) {
	    result = deployedExperimentWrapper.removeClient(userName);
	}
	return result;
    }

    private void addUserToHardware(final String experimentID, final String userName) {

	MultiThreadDeployedExperimentWrapper deployedExperimentWrapper = deployedExperimentsMap.get(experimentID);

	if (deployedExperimentWrapper == null) {
	    addHardware(getRemoteHardware(experimentID));
	    deployedExperimentWrapper = deployedExperimentsMap.get(experimentID);
	}

	if (deployedExperimentWrapper != null) {
	    deployedExperimentWrapper.addNewClient(userName);
	}
    }

    private void removeHardware(final String externalID) {
	deployedExperimentsMap.remove(externalID);
    }

    private void addUser(final String userName) {
	usersSet.add(userName);
    }

    private void removeUser(final ClientInfoDTO clientInfoDTO) {
	usersSet.remove(clientInfoDTO.getUserName());
    }

    private void addHardware(final RegisteredHardwareDTO deployedExperiment) {
	if (deployedExperiment != null) {
	    initMultiThreadDeployedExperimentWrapperIfNotAlreadyInCache(deployedExperiment);
	}
    }

    private void hardwareStateChange(final String experimentExternalID, final byte newStateCode) {
	MultiThreadDeployedExperimentWrapper deployedExperiment = deployedExperimentsMap.get(experimentExternalID);
	
	if (deployedExperiment == null) {
	    addHardware(getRemoteHardware(experimentExternalID));
	    deployedExperiment = deployedExperimentsMap.get(experimentExternalID);
	}

	if (deployedExperiment != null) {
	    deployedExperiment.refreshState(newStateCode);
	}
    }

    private RegisteredHardwareDTO getRemoteHardware(final String experimentID) {

	synchronized (this) {
	    RegisteredHardwareDTO result = null;

	    final MultiThreadDeployedExperimentWrapper deployedExperimentWrapper = deployedExperimentsMap.get(experimentID);

	    if (deployedExperimentWrapper == null) {
		final Map<String, RegisteredHardwareDTO> registeredHardwaresInfo = mbeanProxy.getRegisteredHardwaresInfo(Arrays.asList(new String[] { experimentID }));
		if (registeredHardwaresInfo != null) {
		    result = registeredHardwaresInfo.get(experimentID);
		}
	    }
	    return result;
	}

    }

    private DeployedExperiment getDeployedExperimentFrom(final RegisteredHardwareDTO registeredHardwareDTO) {
	final DeployedExperiment deployedExperiment = new DeployedExperiment();
	deployedExperiment.setState(HardwareState.valueOfCode(registeredHardwareDTO.getStateCode()));
	deployedExperiment.setUsersConnected(registeredHardwareDTO.getUsersConnected());
	return deployedExperiment;
    }

    private MultiThreadDeployedExperimentWrapper initMultiThreadDeployedExperimentWrapperIfNotAlreadyInCache(final RegisteredHardwareDTO registeredHardware) {

	final String externalID = registeredHardware.getHardwareUniqueID();
	MultiThreadDeployedExperimentWrapper multiThreadDeployedExperimentWrapper = deployedExperimentsMap.get(externalID);

	if (multiThreadDeployedExperimentWrapper == null) {
	    loadExperimentFromBD(externalID, getDeployedExperimentFrom(registeredHardware));
	}

	return deployedExperimentsMap.get(externalID);
    }

    private void loadExperimentFromBD(final String externalID, final DeployedExperiment deployedExperiment) {

	synchronized (this) {
	    MultiThreadDeployedExperimentWrapper experiment = deployedExperimentsMap.get(externalID);
	    if (experiment == null) {
		final Experiment findByExternalID = getExperimentFromBD(externalID);
		if (findByExternalID != null) {
		    deployedExperiment.setExperiment(findByExternalID);
		    deployedExperimentsMap.put(externalID, new MultiThreadDeployedExperimentWrapper(deployedExperiment));
		}
	    }
	}
    }

    private void refresh() {
	init();
    }

    private void processNotification(final MultiCastControllerNotifInfoDTO notifInfo, final NotificationTypeEnum notificationType,
	    final long notifSequenceNumber) {
	synchronized (this) {

	    if (notifSequenceNumber < lastNotifReceived) {
		LOGGER.warn("Discarding old notification of type {}", notificationType);
		return;
	    }

	    switch (notificationType) {
	    case HARDWARE_STATE_CHANGE:
		hardwareStateChange(notifInfo.getHardwareStateChangeDTO().getHardwareUniqueID(), notifInfo.getHardwareStateChangeDTO().getNewStateCode());
		break;
	    case REGISTER_NEW_HARDWARE:
		addHardware(notifInfo.getRegisteredHardwareDTO());
		break;
	    case REGISTER_NEW_CLIENT_MC:
		addUser(notifInfo.getClientInfoDTO().getUserName());
		break;
	    case REGISTER_NEW_CLIENT_HARDWARE:
		addUserToHardware(notifInfo.getRegisteredHardwareDTO().getHardwareUniqueID(), notifInfo.getClientInfoDTO().getUserName());
		break;
	    case UNREGISTER_HARDWARE:
		removeHardware(notifInfo.getRegisteredHardwareDTO().getHardwareUniqueID());
		break;
	    case UNREGISTER_CLIENT_MC:
		removeUser(notifInfo.getClientInfoDTO());
		break;
	    case UNREGISTER_CLIENT_HARDWARE:
		removeUserFromHardware(notifInfo.getRegisteredHardwareDTO().getHardwareUniqueID(), notifInfo.getClientInfoDTO().getUserName());
		break;
	    default:
		throw new UnsupportedOperationException();
	    }

	    //refresh lastNotifReceived with the current notification sequence number
	    lastNotifReceived = notifSequenceNumber;

	}
    }

    public void refreshFromNotif(final MultiCastControllerNotifInfoDTO notifInfo, final long notifSequenceNumber) {

	if (notifInfo == null) {
	    return;
	}

	final NotificationTypeEnum notificationType = getNotificationTypeEnum(notifInfo);

	if (notificationType == null) {
	    LOGGER.warn("NotificationType null or with unknow value [valid values: {}]. Discarding notification",
			Arrays.deepToString(NotificationTypeEnum.values()));
	    return;
	}

	if (notifInfo.getUptime() > uptime) {
	    synchronized (this) {

		if (notifInfo.getUptime() > uptime) {
		    LOGGER.warn("Uptime in notification is greater than local uptime, it could be caused by a restart of the MultiCastController/Laboratory. The laboratory information will be refreshed ");
		    try {
			final IMultiCastControllerMXBean mbeanInterfaceProxy = LaboratoriesMonitor.getInstance().getMbeanInterfaceProxy(getName());

			if (mbeanInterfaceProxy != null) {
			    mbeanProxy = mbeanInterfaceProxy;
			}
			refresh();
		    } catch (Exception e) {
			//FIXME: what can we do in this situation? maybe retry?
			LOGGER.error("Error when trying to refresh laboratory state.", e);
			return;
		    }
		}
	    }
	}

	if (notifInfo.getUptime() < uptime) {
	    LOGGER.warn("Uptime in notification is less than local uptime. This notification will be discarded because it arrived too late!");
	}

	if (notifSequenceNumber < lastNotifReceived) {
	    LOGGER.warn("Discarding old notification of type {}", notificationType);
	    return;
	}

	try {
	    processNotification(notifInfo, notificationType, notifSequenceNumber);
	} catch (Exception e) {
	    LOGGER.error("Error processing notification. notificationType: {}", notificationType);
	    LOGGER.error(e.getMessage(), e);
	}

    }

    private NotificationTypeEnum getNotificationTypeEnum(final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO) {
	return NotificationTypeEnum.fromType(multiCastControllerNotifInfoDTO.getNotificationType());
    }

    private Experiment getExperimentFromBD(final String externalID) {
	return experimentService.findByExternalID(externalID);
    }

    public Collection<MultiThreadDeployedExperimentWrapper> getLiveExperiments() {
	return Collections.unmodifiableCollection(deployedExperimentsMap.values());
    }

    public Collection<String> getConnectedUsers() {
	return Collections.unmodifiableCollection(usersSet);
    }

    public String getDescription() {
	return underlyingLaboratory.getDescription();
    }

    public Long getIdInternal() {
	return underlyingLaboratory.getIdInternal();
    }

    public String getName() {
	return underlyingLaboratory.getName();
    }

    public boolean isAvailable() {
	return underlyingLaboratory.isAvailable();
    }
}
