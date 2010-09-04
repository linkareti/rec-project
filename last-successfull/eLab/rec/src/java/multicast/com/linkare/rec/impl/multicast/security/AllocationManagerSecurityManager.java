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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.linkare.rec.am.AllocationDTO;
import com.linkare.rec.am.AllocationManager;
import com.linkare.rec.am.UnknownDomainException;
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
		Logger l = LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	/**
	 * System property key defining the ID of the laboratory as defined in the
	 * project AllocationManager (rec.am)
	 */
	public static final String SYSPROP_LABORATORY_ID = "ReC.MultiCast.LabID";

	public static final String LABORATORY_ID;

	static {
		String localInetAddress = "localhost";
		try {
			localInetAddress = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
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

	private static final int INTERVAL_TIME_LAP_MINUTES = 0;
	private static final int NEAR_TIME_LAP_MINUTES = 5;
	private static final int REFRESH_TIME_LAP_MINUTES = 15;

	/**
	 * Creates the <code>AllocationManagerSecurityManager</code>.
	 * 
	 * @throws NamingException
	 */
	public AllocationManagerSecurityManager() throws NamingException {
		
		LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO, "Instatiating the allocation security manager.");

		lookupAllocationManager();
		
		ExecutorScheduler.scheduleAtFixedRate(new AllocationsRefreshScheduledUnit(), 1, REFRESH_TIME_LAP_MINUTES,
				TimeUnit.MINUTES);
		
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
		List<AllocationDTO> allocations = findAllocationFor(hardwareId);
		if (allocations.isEmpty()) {
			return true;
		}
		return checkUserOrOwner(allocations, username);
	}

	private List<AllocationDTO> findAllocationFor(String hardwareId) {
		List<AllocationDTO> retAllocations = new ArrayList<AllocationDTO>();
		final Calendar currentTime = Calendar.getInstance();
		List<AllocationDTO> hardwareAllocations = allocationsMap.get(hardwareId);
		for (AllocationDTO allocation : hardwareAllocations) {
			if (isInIntervalOrNear(currentTime, allocation)) {
				retAllocations.add(allocation);
			}
		}
		return retAllocations;
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
	 * @param allocation
	 * @return
	 */
	private boolean isInIntervalOrNear(Calendar currentTime, AllocationDTO allocation) {
		Calendar nearTime = Calendar.getInstance();
		nearTime.setTimeInMillis(currentTime.getTimeInMillis());
		nearTime.add(Calendar.MINUTE, INTERVAL_TIME_LAP_MINUTES);

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
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
					"Autenticating user [" + username + "]");
			return allocationManager.authenticate(username, password);
		} catch (RemoteException e) {
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER)
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
		List<AllocationDTO> currentAllocation = findAllocationFor(resource.getProperties().get(
				resource.getResourceType().getPropertyKey()));

		if (!currentAllocation.isEmpty()) {
			if (op.getOperation() == IOperation.OP_LOCK || op.getOperation() == IOperation.OP_CONFIG
					|| op.getOperation() == IOperation.OP_START || op.getOperation() == IOperation.OP_START_OUTPUT) {
				return checkUserAsOwner(currentAllocation, user.getUserName());
			}
		}
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
		
//		Calendar currentTime = Calendar.getInstance();
//		
//		// clear old allocations
//		Set<String> experimentsId = allocationsMap.keySet();
//		for (String key : experimentsId) {
//			List<AllocationDTO> newExperimentAllocations = new ArrayList<AllocationDTO>();
//			
//			// check if the allocations are still in time
//			List<AllocationDTO> experimentAllocations = allocationsMap.get(key);
//			for (AllocationDTO allocation : experimentAllocations) {
//				if (!allocation.getEnd().before(currentTime.getTime())) {
//					newExperimentAllocations.add(allocation);
//				}
//			}
//			
//			if (newExperimentAllocations.size() > 0) {
//				allocationsMap.put(key, newExperimentAllocations);
//			} else {
//				allocationsMap.remove(key);
//			}
//		}
//		
//		// add new allocations
//		for (AllocationDTO newAllocation : newAllocations) {
//			List<AllocationDTO> experimentAllocations = allocationsMap.get(newAllocation.getExperimentId());
//			if (experimentAllocations != null) {
//				List<AllocationDTO> interceptedExperiment = new ArrayList<AllocationDTO>();
//				for (AllocationDTO currentAllocation : experimentAllocations) {
//					if (newAllocation.getBegin().before(currentAllocation.getEnd()) && newAllocation.getEnd().after(currentAllocation.getBegin())) {
//						interceptedExperiment.add(currentAllocation);
//					}
//				}
//				experimentAllocations.removeAll(interceptedExperiment);
//				experimentAllocations.add(newAllocation);
//			} else {
//				experimentAllocations = new ArrayList<AllocationDTO>();
//				experimentAllocations.add(newAllocation);
//				allocationsMap.put(newAllocation.getExperimentId(), experimentAllocations);
//			}
//		}
	}
	
	/**
	 * @param begin
	 * @param end
	 */
	private void refreshAllocations(Date begin, Date end) {
		LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
				"Refresh allocation with Begin [" + begin + "] End [" + end + "]");
		try {
			List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end, LABORATORY_ID);
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"Received " + newAllocations.size() + " allocations.");

			mergeAllocations(newAllocations);
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINEST,
					"Merged allocations " + allocationsMap);
		} catch (Exception e) {
			try {
				lookupAllocationManager();
				List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end, LABORATORY_ID);
				mergeAllocations(newAllocations);
			} catch (Exception e2) {
				// TODO - What if I can't lookup it up again??? Down for
				// long??? No allocations available... Ooops!
			}
		}
	}

	private AllocationManager allocationManager = null;

	private Map<String, List<AllocationDTO>> allocationsMap = new HashMap<String, List<AllocationDTO>>();
	
	public class AllocationsRefreshScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
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

}
