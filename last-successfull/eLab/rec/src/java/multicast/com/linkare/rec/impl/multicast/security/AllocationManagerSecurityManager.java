/* 
 * AllocationManagerSecurityManager.java created on 7 May 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.linkare.commons.utils.Pair;
import com.linkare.rec.am.AllocationDTO;
import com.linkare.rec.am.AllocationManager;
import com.linkare.rec.am.UnknownDomainException;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class AllocationManagerSecurityManager implements ISecurityManager {

	/**
	 * 
	 */
	private static final String ALLOCATION_MANAGER_GLOBAL_JNDI_NAME = "java:global/rec.am/AllocationManager!com.linkare.rec.am.AllocationManager";
	// jndi name used in develpment tests
//	private static final String ALLOCATION_MANAGER_GLOBAL_JNDI_NAME = "java:global/rec.am/AllocationManager";

	// public static final String
	// MCCONTROLLER_SECURITYMANAGER_LOGGER=ReCMultiCastController.MCCONTROLLER_LOGGER;
	public static final String MCCONTROLLER_SECURITYMANAGER_LOGGER = "ReC.MultiCast.SecurityManager.Logger";

	static {
		Logger l = Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	/**
	 * System property key defining the ID of the laboratory as defined in the
	 * project AllocationManager (rec.am)
	 */
	public static final String SYSPROP_LABORATORY_ID = "ReC.MultiCast.LabID";

	public static final String SYSPROP_INTERVAL_TIME_LAP_MINUTES = "ReC.MultiCast.SecurityManager.Interval.Lap.Time.Minutes";
	public static final String SYSPROP_NEAR_TIME_LAP_MINUTES = "ReC.MultiCast.SecurityManager.Near.Lap.Time.Minutes";
	public static final String SYSPROP_REFRESH_TIME_LAP_MINUTES = "ReC.MultiCast.SecurityManager.Refresh.Lap.Time.Minutes";

	public static final String LABORATORY_ID;

	static {
		String localInetAddress = "localhost";
		try {
			localInetAddress = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					java.util.logging.Level.FINEST, "Unable to find local host name " + e.getMessage());
		}
		LABORATORY_ID = Defaults.defaultIfEmpty(System.getProperty(SYSPROP_LABORATORY_ID), localInetAddress);
	}

	public static final String SYSPROP_ALLOCATIONMANAGER_HOST = "ReC.MultiCast.AllocationManagerHost";
	public static final String SYSPROP_ALLOCATIONMANAGER_PORT = "ReC.MultiCast.AllocationManagerPort";

	public static final String NAMING_FACTORY = Defaults.defaultIfEmpty(System
			.getProperty(InitialContext.INITIAL_CONTEXT_FACTORY),
			"com.sun.enterprise.naming.impl.SerialInitContextFactory");
	public static final String NAMING_URL_PKGS = Defaults.defaultIfEmpty(System
			.getProperty(InitialContext.URL_PKG_PREFIXES), "com.sun.enterprise.naming");
	public static final String NAMING_STATE = Defaults
			.defaultIfEmpty(System.getProperty(InitialContext.STATE_FACTORIES),
					"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	public static final String ORB_ENV_HOST = Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_ALLOCATIONMANAGER_HOST), "localhost");
	public static final String ORB_ENV_PORT = Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_ALLOCATIONMANAGER_PORT), "3700");

	private static final String ORG_OMG_CORBA_ORB_INITIAL_PORT = "org.omg.CORBA.ORBInitialPort";
	private static final String ORG_OMG_CORBA_ORB_INITIAL_HOST = "org.omg.CORBA.ORBInitialHost";

	private static final int INTERVAL_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_INTERVAL_TIME_LAP_MINUTES), 0);
	private static final int NEAR_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_NEAR_TIME_LAP_MINUTES), 5);
	private static final int REFRESH_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(System
			.getProperty(SYSPROP_REFRESH_TIME_LAP_MINUTES), 15);
	private static final int REFRESH_TIME_ALLOCATIONS_CLIENT_QUEUE_MINUTES = 1;
	
	private List<ReCMultiCastHardware> multiCastHardwares = null;
	
	private ISecurityCommunicator communicator = null;

	/**
	 * Creates the <code>AllocationManagerSecurityManager</code>.
	 * 
	 * @throws NamingException
	 */
	public AllocationManagerSecurityManager() throws NamingException {
		
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO, "Instatiating the allocation security manager.");

		lookupAllocationManager();
		
		ExecutorScheduler.scheduleAtFixedRate(new AllocationsRefreshScheduledUnit(), 1, REFRESH_TIME_LAP_MINUTES,
				TimeUnit.MINUTES);
		
		ExecutorScheduler.scheduleAtFixedRate(new ClientsAllocationCheckScheduledUnit(), 1,
				REFRESH_TIME_ALLOCATIONS_CLIENT_QUEUE_MINUTES, TimeUnit.MINUTES);
		
		// force update the allocations list because the scheduler might take time until it starts running
		initializeAllocations();
	}

	/**
	 * @throws NamingException
	 */
	private void lookupAllocationManager() throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put(InitialContext.INITIAL_CONTEXT_FACTORY, NAMING_FACTORY);
		jndiProps.put(InitialContext.URL_PKG_PREFIXES, NAMING_URL_PKGS);
		jndiProps.put(InitialContext.STATE_FACTORIES, NAMING_STATE);
		jndiProps.setProperty(ORG_OMG_CORBA_ORB_INITIAL_HOST, ORB_ENV_HOST);
		jndiProps.setProperty(ORG_OMG_CORBA_ORB_INITIAL_PORT, ORB_ENV_PORT);
		InitialContext ctx = new InitialContext(jndiProps);
		Object allocationManagerStub = ctx.lookup(ALLOCATION_MANAGER_GLOBAL_JNDI_NAME);
		allocationManager = (AllocationManager) PortableRemoteObject.narrow(allocationManagerStub,
				AllocationManager.class);
	}
	
	/**
	 * Update the allocations from the current time.
	 * This method should only be invoked in the contructor!
	 */
	private void initializeAllocations() {
		new Thread() {
			public void run() {
				Calendar nearTime = Calendar.getInstance();
				Calendar farTime = Calendar.getInstance();
				farTime.setTimeInMillis(nearTime.getTimeInMillis());
				farTime.add(Calendar.MINUTE, AllocationManagerSecurityManager.NEAR_TIME_LAP_MINUTES
						+ AllocationManagerSecurityManager.REFRESH_TIME_LAP_MINUTES);

				refreshAllocations(nearTime.getTime(), farTime.getTime());
			}
		}.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticate(IResource resource, IUser user) {
		switch (resource.getResourceType()) {
		case MCCONTROLLER:
			return checkLogin(user.getUserName(), user.getAuth());
		case MCHARDWARE:
			return checkAllocationsEnter(resource.getProperties().get(resource.getResourceType().getPropertyKey()),
					user.getUserName());
		case DATAPRODUCER:
			IResource hardwareResource = resource.getEnclosingResource();
			return checkAllocationsEnter(hardwareResource.getProperties().get(
					hardwareResource.getResourceType().getPropertyKey()), user.getUserName());
		default:
			return true;
		}

	}

	/**
	 * @param hardwareId
	 * @param username
	 * @return
	 */
	private boolean checkAllocationsEnter(String hardwareId, String username) {
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
				"Check allocations for user [" + username + "] hardware ["+hardwareId+"]");
		
		List<AllocationDTO> allocations = findAllocationFor(hardwareId);
		if (allocations.isEmpty()) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"There aren't allocations.");
			return true;
		}
		
		boolean checkUserOrOwner = checkUserOrOwner(allocations, username);
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
				"Check allocations is [" + checkUserOrOwner + "]");
		return checkUserOrOwner;
	}

	private List<AllocationDTO> findAllocationFor(String hardwareId, int interval) {
		List<AllocationDTO> retAllocations = new ArrayList<AllocationDTO>();
		final Calendar currentTime = Calendar.getInstance();
		List<AllocationDTO> hardwareAllocations = allocationsMap.get(hardwareId);
		if (hardwareAllocations != null) {
			for (AllocationDTO allocation : hardwareAllocations) {
				if (isInIntervalOrNear(currentTime, interval, allocation)) {
					retAllocations.add(allocation);
				}
			}
		}
		return retAllocations;
	}
	
	private List<AllocationDTO> findAllocationFor(String hardwareId) {
		return findAllocationFor(hardwareId, INTERVAL_TIME_LAP_MINUTES);
	}

	/**
	 * @param allocations
	 * @param username
	 * @return
	 */
	private boolean checkUserOrOwner(List<AllocationDTO> allocations, String username) {
		return checkUserAsOwner(allocations, username) || checkUser(allocations, username);
	}

	/**
	 * @param allocations
	 * @param username
	 * @return
	 */
	private boolean checkUserAsOwner(List<AllocationDTO> allocations, String username) {
		for (AllocationDTO allocation: allocations) {
			Set<String> ownersList = allocation.getOwners() == null ? allocation.getUsers() : allocation.getOwners();
			boolean contains = ownersList.contains(username);
			if (contains) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param allocations
	 * @param username
	 */
	private boolean checkUser(List<AllocationDTO> allocations, String username) {
		for (AllocationDTO allocation : allocations) {
			boolean contains = allocation.getUsers().contains(username);
			if (contains) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param currentTime
	 * @param interval
	 * @param allocation
	 * @return
	 */
	private boolean isInIntervalOrNear(Calendar currentTime, int interval, AllocationDTO allocation) {
		Calendar nearTime = Calendar.getInstance();
		nearTime.setTimeInMillis(currentTime.getTimeInMillis());
		nearTime.add(Calendar.MINUTE, interval);

		return !nearTime.getTime().before(allocation.getBegin()) && !currentTime.getTime().after(allocation.getEnd());
	}

	/**
	 * @param username
	 * @param auth
	 * @return
	 */
	private boolean checkLogin(String username, byte[] auth) {
		String password = new String(auth);
		try {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
					"Autenticating user [" + username + "]");
			boolean authenticate = allocationManager.authenticate(username, password);
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"Authentication is [" + authenticate + "]");
			return authenticate;
		} catch (RemoteException e) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER)
					.log(java.util.logging.Level.FINEST,
							"Unable to comunicate with allocation manager " + e.getMessage(), e);
			return false;
		} catch (UnknownDomainException e) {
			return true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authorize(IResource resource, IUser user, IOperation op) {
		if (user == null || user.getUserName() == null || resource == null || resource.getResourceType() == null
				|| resource.getProperties().get(resource.getResourceType().getPropertyKey()) == null || op == null) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.WARNING, "Invalid parameters in authorize method");
			throw new RuntimeException("Invalid parameters in authorize method");
		}
		switch (resource.getResourceType()) {
		case MCHARDWARE:
			return checkMCHardwareOperations(resource, user, op);
		case DATAPRODUCER:
			return checkDataProducerOperations(resource, user, op);
		default:
			return true;
		}
	}

	/**
	 * @param resource
	 * @param user
	 * @param op
	 * @return
	 */
	private boolean checkDataProducerOperations(IResource resource, IUser user, IOperation op) {
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
				Level.FINE,
				"Checking data producer operations for user [" + user.getUserName() + "] resource ["
						+ resource.getProperties().get(resource.getResourceType().getPropertyKey()) + "] operation ["
						+ op.getOperation() + "]");
		
		IResource hardwareResource = resource.getEnclosingResource();
		List<AllocationDTO> currentAllocation = findAllocationFor(hardwareResource.getProperties().get(
				hardwareResource.getResourceType().getPropertyKey()));

		if (!currentAllocation.isEmpty()) {
			if (op.getOperation() == IOperation.OP_GET_DATAPRODUCER
					|| op.getOperation() == IOperation.OP_GET_DATAPRODUCERSTATE
					|| op.getOperation() == IOperation.OP_GET_SAMPLES || op.getOperation() == IOperation.OP_STOP) {
				if (op.getOperation() == IOperation.OP_STOP) {
					return checkUserAsOwner(currentAllocation, user.getUserName());
				} else {
					return checkUserOrOwner(currentAllocation, user.getUserName());
				}
			}
		}

		return true;
	}

	/**
	 * @param resource
	 * @param user
	 * @param op
	 * @return
	 */
	private boolean checkMCHardwareOperations(IResource resource, IUser user, IOperation op) {
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
				Level.FINE,
				"Checking multicast hardware operations for user [" + user.getUserName() + "] resource ["
						+ resource.getProperties().get(resource.getResourceType().getPropertyKey()) + "] operation ["
						+ op.getOperation() + "]");

		List<AllocationDTO> currentAllocation = findAllocationFor(resource.getProperties().get(
				resource.getResourceType().getPropertyKey()));

		if (!currentAllocation.isEmpty()) {
			if (op.getOperation() == IOperation.OP_LOCK || op.getOperation() == IOperation.OP_CONFIG
					|| op.getOperation() == IOperation.OP_START || op.getOperation() == IOperation.OP_START_OUTPUT) {
				boolean checkUserAsOwner = checkUserAsOwner(currentAllocation, user.getUserName());
				Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
						"Hardware operations is [" + checkUserAsOwner + "]");
				return checkUserAsOwner;
			}
		} else {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"There aren't allocations.");
		}
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
				"Hardware operations is [true]");
		return true;
	}
	
	/**
	 * @param newAllocations
	 */
	private void mergeAllocations(List<AllocationDTO> newAllocations) {
		Map<String, List<AllocationDTO>> newAllocationsMap = new HashMap<String, List<AllocationDTO>>();
		for (AllocationDTO allocation : newAllocations) {
			List<AllocationDTO> experimentAllocations = newAllocationsMap.get(allocation.getExperimentId());
			if (experimentAllocations == null) {
				experimentAllocations = new ArrayList<AllocationDTO>();
				newAllocationsMap.put(allocation.getExperimentId(), experimentAllocations);
			}
			experimentAllocations.add(allocation);
		}
		allocationsMap = newAllocationsMap;
	}
	
	private void cleanNotifiedUsers(List<AllocationDTO> newAllocations) {
		Set<Long> allocationsId = new HashSet<Long>();
		for (AllocationDTO allocationDTO : newAllocations) {
			allocationsId.add(allocationDTO.getId());
		}
		// remove old allocations from the users notification list
		for (Long id : notifiedUsersNotInAllocation.keySet()) {
			if (!allocationsId.contains(id)) {
				notifiedUsersNotInAllocation.remove(id);
			}
		}
		// add the new allocations
		for (AllocationDTO allocationDTO : newAllocations) {
			if (!notifiedUsersNotInAllocation.containsKey(allocationDTO.getId())) {
				notifiedUsersNotInAllocation.put(allocationDTO.getId(), new HashSet<String>());
			}
		}
	}
	
	/**
	 * @param begin
	 * @param end
	 */
	private void refreshAllocations(Date begin, Date end) {
		Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
				"Refresh allocation with Begin [" + begin + "] End [" + end + "]");
		try {
			List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end, LABORATORY_ID);
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"Received " + newAllocations.size() + " allocations.");

			mergeAllocations(newAllocations);
			cleanNotifiedUsers(newAllocations);
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"Merged allocations " + allocationsMap);
		} catch (Exception e) {
			try {
				lookupAllocationManager();
				List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end, LABORATORY_ID);
				mergeAllocations(newAllocations);
				cleanNotifiedUsers(newAllocations);
			} catch (Exception e2) {
				// What if I can't lookup it up again??? Down for
				// long??? No allocations available... Ooops!
			}
		}
	}

	private AllocationManager allocationManager = null;

	private Map<String, List<AllocationDTO>> allocationsMap = new HashMap<String, List<AllocationDTO>>();
	
	private Map<Long, Set<String>> notifiedUsersNotInAllocation = new Hashtable<Long, Set<String>>();
	
	private static final SimpleDateFormat notificationTimeFormatter = new SimpleDateFormat("HH:mm");
	
	public class AllocationsRefreshScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"AllocationsRefreshScheduledUnit - Going to refresh the allocations.");
			
			Calendar nearTime = Calendar.getInstance();
			Calendar farTime = Calendar.getInstance();
			farTime.setTimeInMillis(nearTime.getTimeInMillis());
			farTime.add(Calendar.MINUTE, AllocationManagerSecurityManager.NEAR_TIME_LAP_MINUTES
					+ AllocationManagerSecurityManager.REFRESH_TIME_LAP_MINUTES);

			refreshAllocations(nearTime.getTime(), farTime.getTime());
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void logThrowable(String message, Throwable throwable) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.SEVERE, message, throwable);
		}
	}
	
	public class ClientsAllocationCheckScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"ClientsAllocationScheduledUnit - Going to check clients allocations.");
			
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.FINE,
					"Are there allocations [" + (!allocationsMap.isEmpty()) + "] and multicast hardwares ["
							+ (multiCastHardwares != null) + "]");
			
			int kickedClients = 0;
			int notifiedClients = 0;
			
			if (multiCastHardwares != null && !allocationsMap.isEmpty()) {
				Map<String, List<AllocationDTO>> nearAllocationsMap = findNearAllocations();
				if (!nearAllocationsMap.isEmpty()) {
					Map<String, List<AllocationDTO>> currentAllocationsMap = findCurrentAllocations();

					synchronized (multiCastHardwares) {
						Collection<ReCMultiCastHardware> iterateHardwares = Collections
								.unmodifiableCollection(multiCastHardwares);
						for (ReCMultiCastHardware rmch : iterateHardwares) {

							// kick users that are connected to the hardware but
							// aren't in the current allocations
							Set<String> usernamesToKick = findHardwareUsersNotInAllocations(currentAllocationsMap, rmch);
							if (!usernamesToKick.isEmpty()) {
								Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
										Level.INFO,
										"The users [" + usernamesToKick
												+ "] aren't in the current allocation for the hardware ["
												+ rmch.getHardwareUniqueId() + "] and are going to be kicked.");

								sendKickMessage(usernamesToKick);
								rmch.kickUsers(usernamesToKick);
								kickedClients += usernamesToKick.size();
							}

							// notify users that are connected to the hardware
							// but aren't in the near allocations and heren't
							// already notified
							Set<String> usernamesNotInAllocations = findHardwareUsersNotInAllocations(
									nearAllocationsMap, rmch);
							if (!usernamesNotInAllocations.isEmpty()) {
								List<AllocationDTO> hardwareAllocations = nearAllocationsMap.get(rmch
										.getHardwareUniqueId());
								Set<Pair<String, Date>> usernamesToNotify = new HashSet<Pair<String, Date>>();
								for (String username : usernamesNotInAllocations) {
									for (AllocationDTO allocationDTO : hardwareAllocations) {
										// check if the user wasn't already
										// notified
										if (notifiedUsersNotInAllocation.containsKey(allocationDTO.getId())
												&& !notifiedUsersNotInAllocation.get(allocationDTO.getId()).contains(
														username)) {
											if (!checkUserOrOwner(Collections.singletonList(allocationDTO), username)) {
												usernamesToNotify.add(new Pair<String, Date>(username, allocationDTO.getBegin()));
												notifiedUsersNotInAllocation.get(allocationDTO.getId()).add(username);
											}
										}
									}
								}
								if (!usernamesToNotify.isEmpty()) {
									Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
											Level.INFO,
											"The users [" + usernamesToNotify
													+ "] aren't in the current allocation for the hardware ["
													+ rmch.getHardwareUniqueId() + "] and are going to be notified.");

									notifiedClients += usernamesToNotify.size();
									sendWarningMessage(usernamesToNotify);
								}
							}
						}
					}
				} else {
					Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
							"There are no near allocations at this time");
				}
			}

			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"Client allocations terminated and kicked [" + kickedClients + "] clients");
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"Client allocations notified [" + notifiedClients + "] clients");
		}
		
		private Set<String> findHardwareUsersNotInAllocations(Map<String, List<AllocationDTO>> allocationsMap, ReCMultiCastHardware rmch) {
			Set<String> usernames = new HashSet<String>();
			if (allocationsMap.containsKey(rmch.getHardwareUniqueId())) {
				List<AllocationDTO> hardwareAllocations = allocationsMap.get(rmch.getHardwareUniqueId());
				List<String> clientUsernames = rmch.getClientUsernames();
				for (String username : clientUsernames) {
					// there is a allocation for this hardware so
					// the user must be in the allocation
					if (!checkUserOrOwner(hardwareAllocations, username)) {
						usernames.add(username);
					}
				}
			}
			return usernames;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void logThrowable(String message, Throwable throwable) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.SEVERE, message, throwable);
		}
	}
	
	private Map<String, List<AllocationDTO>> findCurrentAllocations() {
		return findAllocations(INTERVAL_TIME_LAP_MINUTES);
	}
	
	private Map<String, List<AllocationDTO>> findNearAllocations() {
		return findAllocations(NEAR_TIME_LAP_MINUTES);
	}
	
	private Map<String, List<AllocationDTO>> findAllocations(int interval) {
		Map<String, List<AllocationDTO>> currentAllocationsMap = new HashMap<String, List<AllocationDTO>>();
		for (String key : allocationsMap.keySet()) {
			List<AllocationDTO> allocations = findAllocationFor(key, interval);
			if (!allocations.isEmpty()) {
				currentAllocationsMap.put(key, allocations);
			}
		}
		return currentAllocationsMap;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerMultiCastHardware(List<ReCMultiCastHardware> multiCastHardwares) {
		this.multiCastHardwares = multiCastHardwares;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(ISecurityCommunicator communicator) {
		this.communicator = communicator;
	}
	
	/**
	 * Sends a message to the client saying that he is going to be shutdown from the experiment.
	 * 
	 * @param clientTo
	 */
	private void sendKickMessage(String clientTo) {
		if (communicator != null) {
			communicator.sendMulticastMessage(clientTo, ChatMessageEvent.SECURITY_COMMUNICATION_MSG_ON_KICK_KEY);
		}
	}
	
	/**
	 * Sends a message warning the client that he is going to be shutdown from the experiment in some time.
	 * 
	 * @param clientTo
	 */
	private void sendWarningMessage(Pair<String, Date> clientTo) {
		if (communicator != null) {
			String message = ChatMessageEvent.SECURITY_COMMUNICATION_MSG_BEFORE_KICK_KEY
					+ ChatMessageEvent.SECURITY_COMMUNICATION_MSG_SEPARATOR
					+ notificationTimeFormatter.format(clientTo.getValue());
			communicator.sendMulticastMessage(clientTo.getKey(), message); 
		}
	}
	
	/**
	 * @param usernames
	 */
	private void sendKickMessage(Set<String> usernames) {
		for (String clientTo : usernames) {
			sendKickMessage(clientTo);
		}
	}
	
	/**
	 * @param usernames
	 */
	private void sendWarningMessage(Set<Pair<String, Date>> usernames) {
		for (Pair<String, Date> clientTo : usernames) {
			sendWarningMessage(clientTo);
		}
	}

}
