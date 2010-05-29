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
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
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
	private static final String ALLOCATION_MANAGER_GLOBAL_JNDI_NAME = "java:global/rec.am/AllocationManager!com.linkare.rec.am.model.AllocationManager";

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

	private static final int NEAR_TIME_LAP_MINUTES = 5;
	private static final int REFRESH_TIME_LAP_MINUTES = 15;

	/**
	 * Creates the <code>AllocationManagerSecurityManager</code>.
	 * 
	 * @throws NamingException
	 */
	public AllocationManagerSecurityManager() throws NamingException {

		lookupAllocationManager();

		ExecutorScheduler.scheduleAtFixedRate(new AllocationsRefreshScheduledUnit(), 1, REFRESH_TIME_LAP_MINUTES,
				TimeUnit.MINUTES);
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
		AllocationDTO allocation = findAllocationFor(hardwareId);
		if (allocation == null)
			return true;
		return checkUserOrOwner(allocation, username);
	}

	private AllocationDTO findAllocationFor(String hardwareId) {
		final Calendar currentTime = Calendar.getInstance();

		final List<AllocationDTO> allocations = currentKnownAllocations;

		for (AllocationDTO allocation : allocations) {
			if (hardwareId.equals(allocation.getExperimentId()) && isInIntervalOrNear(currentTime, allocation)) {
				return allocation;
			}
		}

		return null;
	}

	/**
	 * @param allocation
	 * @param username
	 * @return
	 */
	private boolean checkUserOrOwner(AllocationDTO allocation, String username) {
		return checkUserAsOwner(allocation, username) || checkUser(allocation, username);
	}

	/**
	 * @param allocation
	 * @param username
	 * @return
	 */
	private boolean checkUserAsOwner(AllocationDTO allocation, String username) {
		List<String> ownersList = allocation.getOwners() == null ? allocation.getUsers() : allocation.getOwners();
		for (String ownerUser : ownersList) {
			if (username.equals(ownerUser)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param allocation
	 * @param username
	 */
	private boolean checkUser(AllocationDTO allocation, String username) {
		for (String allocationUser : allocation.getUsers()) {
			if (username.equals(allocationUser)) {
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
		nearTime.add(Calendar.MINUTE, NEAR_TIME_LAP_MINUTES);

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
		AllocationDTO currentAllocation = findAllocationFor(hardwareResource.getProperties().get(
				hardwareResource.getResourceType().getPropertyKey()));

		if (currentAllocation != null) {

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
		AllocationDTO currentAllocation = findAllocationFor(resource.getProperties().get(
				resource.getResourceType().getPropertyKey()));

		if (currentAllocation != null) {
			if (op.getOperation() == IOperation.OP_LOCK || op.getOperation() == IOperation.OP_CONFIG
					|| op.getOperation() == IOperation.OP_START || op.getOperation() == IOperation.OP_START_OUTPUT) {
				return checkUserAsOwner(currentAllocation, user.getUserName());
			}
		}
		return true;
	}

	private AllocationManager allocationManager = null;

	private List<AllocationDTO> currentKnownAllocations = null;

	public class AllocationsRefreshScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Calendar nearTime = Calendar.getInstance();
			nearTime.add(Calendar.MINUTE, AllocationManagerSecurityManager.NEAR_TIME_LAP_MINUTES);
			Calendar farTime = Calendar.getInstance();
			farTime.setTimeInMillis(nearTime.getTimeInMillis());
			farTime.add(Calendar.MINUTE, AllocationManagerSecurityManager.REFRESH_TIME_LAP_MINUTES);

			try {
				List<AllocationDTO> newAllocations = allocationManager.getBy(nearTime.getTime(), farTime.getTime(),
						LABORATORY_ID);
				currentKnownAllocations = newAllocations;
			} catch (Exception e) {
				try {
					lookupAllocationManager();
					List<AllocationDTO> newAllocations = allocationManager.getBy(nearTime.getTime(), farTime.getTime(),
							LABORATORY_ID);
					currentKnownAllocations = newAllocations;
				} catch (Exception e2) {
					// TODO - What if I can't lookup it up again??? Down for
					// long??? No allocations available... Ooops!

				}
			}
		}

	}

}
