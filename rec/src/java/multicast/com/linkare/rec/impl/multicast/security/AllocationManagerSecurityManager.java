package com.linkare.rec.impl.multicast.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.commons.utils.Pair;
import com.linkare.rec.am.AllocationDTO;
import com.linkare.rec.am.AllocationManager;
import com.linkare.rec.am.UnknownDomainException;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.locator.BusinessServiceEnum;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocator;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocatorException;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class AllocationManagerSecurityManager implements ISecurityManager {

	private static final Logger LOGGER = Logger.getLogger(AllocationManagerSecurityManager.class.getName());

	/**
	 * System property key defining the ID of the laboratory as defined in the
	 * project AllocationManager (rec.am)
	 */
	public static final String SYSPROP_LABORATORY_ID = "rec.multicast.labid";

	public static final String SYSPROP_INTERVAL_TIME_LAP_MINUTES = "rec.multicast.securitymanager.interval.lap.time.minutes";
	public static final String SYSPROP_NEAR_TIME_LAP_MINUTES = "rec.multicast.securitymanager.near.lap.time.minutes";
	public static final String SYSPROP_REFRESH_TIME_LAP_MINUTES = "rec.multicast.securitymanager.refresh.lap.time.minutes";

	public static final String LABORATORY_ID;

	static {
		String localInetAddress = "localhost";
		try {
			localInetAddress = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (final UnknownHostException e) {
			LOGGER.log(java.util.logging.Level.FINEST, "Unable to find local host name " + e.getMessage());
		}
		LABORATORY_ID = Defaults.defaultIfEmpty(
				System.getProperty(AllocationManagerSecurityManager.SYSPROP_LABORATORY_ID), localInetAddress);
	}

	private static final int INTERVAL_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(
			System.getProperty(SYSPROP_INTERVAL_TIME_LAP_MINUTES), 0);
	private static final int NEAR_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(
			System.getProperty(SYSPROP_NEAR_TIME_LAP_MINUTES), 5);
	private static final int REFRESH_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(
			System.getProperty(SYSPROP_REFRESH_TIME_LAP_MINUTES), 15);
	private static final int REFRESH_TIME_ALLOCATIONS_CLIENT_QUEUE_MINUTES = 1;

	private List<ReCMultiCastHardware> multiCastHardwares = null;

	private ISecurityCommunicator communicator = null;

	/**
	 * Creates the <code>AllocationManagerSecurityManager</code>.
	 * 
	 * @throws BusinessServiceLocatorException
	 * 
	 */
	public AllocationManagerSecurityManager() throws BusinessServiceLocatorException {

		LOGGER.log(Level.INFO, "Instatiating the allocation security manager.");

		lookupAllocationManager();

		ExecutorScheduler.scheduleAtFixedRate(new AllocationsRefreshScheduledUnit(), 1,
				AllocationManagerSecurityManager.REFRESH_TIME_LAP_MINUTES, TimeUnit.MINUTES);

		ExecutorScheduler.scheduleAtFixedRate(new ClientsAllocationCheckScheduledUnit(), 1,
				AllocationManagerSecurityManager.REFRESH_TIME_ALLOCATIONS_CLIENT_QUEUE_MINUTES, TimeUnit.MINUTES);

		// force update the allocations list because the scheduler might take
		// time until it starts running
		initializeAllocations();
	}

	/**
	 * @throws BusinessServiceLocatorException
	 */
	private void lookupAllocationManager() throws BusinessServiceLocatorException {
		allocationManager = BusinessServiceLocator.getInstance().getBusinessInterface(
				BusinessServiceEnum.ALLOCATION_MANAGER);
	}

	/**
	 * Update the allocations from the current time. This method should only be
	 * invoked in the contructor!
	 */
	private void initializeAllocations() {
		new Thread() {
			@Override
			public void run() {
				final Calendar nearTime = Calendar.getInstance();
				final Calendar farTime = Calendar.getInstance();
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
	public boolean authenticate(final IResource resource, final IUser user) {
		switch (resource.getResourceType()) {
		case MCCONTROLLER:
			return checkLogin(user.getUserName(), user.getAuth());
		case MCHARDWARE:
			return checkAllocationsEnter(resource.getProperties().get(resource.getResourceType().getPropertyKey()),
					user.getUserName());
		case DATAPRODUCER:
			final IResource hardwareResource = resource.getEnclosingResource();
			return checkAllocationsEnter(
					hardwareResource.getProperties().get(hardwareResource.getResourceType().getPropertyKey()),
					user.getUserName());
		default:
			return true;
		}

	}

	/**
	 * @param hardwareId
	 * @param username
	 * @return
	 */
	private boolean checkAllocationsEnter(final String hardwareId, final String username) {
		LOGGER.log(Level.FINE, "Check allocations for user [" + username + "] hardware [" + hardwareId + "]");

		final List<AllocationDTO> allocations = findAllocationFor(hardwareId);
		if (allocations.isEmpty()) {
			LOGGER.log(Level.FINEST, "There aren't allocations.");
			return true;
		}

		final boolean checkUserOrOwner = checkUserOrOwner(allocations, username);
		LOGGER.log(Level.FINEST, "Check allocations is [" + checkUserOrOwner + "]");
		return checkUserOrOwner;
	}

	private List<AllocationDTO> findAllocationFor(final String hardwareId, final int interval) {
		final List<AllocationDTO> retAllocations = new ArrayList<AllocationDTO>();
		final Calendar currentTime = Calendar.getInstance();
		final List<AllocationDTO> hardwareAllocations = allocationsMap.get(hardwareId);
		if (hardwareAllocations != null) {
			for (final AllocationDTO allocation : hardwareAllocations) {
				if (isInIntervalOrNear(currentTime, interval, allocation)) {
					retAllocations.add(allocation);
				}
			}
		}
		return retAllocations;
	}

	private List<AllocationDTO> findAllocationFor(final String hardwareId) {
		return findAllocationFor(hardwareId, AllocationManagerSecurityManager.INTERVAL_TIME_LAP_MINUTES);
	}

	/**
	 * @param allocations
	 * @param username
	 * @return
	 */
	private boolean checkUserOrOwner(final List<AllocationDTO> allocations, final String username) {
		return checkUserAsOwner(allocations, username) || checkUser(allocations, username);
	}

	/**
	 * @param allocations
	 * @param username
	 * @return
	 */
	private boolean checkUserAsOwner(final List<AllocationDTO> allocations, final String username) {
		for (final AllocationDTO allocation : allocations) {
			final Set<String> ownersList = allocation.getOwners() == null ? allocation.getUsers() : allocation
					.getOwners();
			final boolean contains = ownersList.contains(username);
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
	private boolean checkUser(final List<AllocationDTO> allocations, final String username) {
		for (final AllocationDTO allocation : allocations) {
			final boolean contains = allocation.getUsers().contains(username);
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
	private boolean isInIntervalOrNear(final Calendar currentTime, final int interval, final AllocationDTO allocation) {
		final Calendar nearTime = Calendar.getInstance();
		nearTime.setTimeInMillis(currentTime.getTimeInMillis());
		nearTime.add(Calendar.MINUTE, interval);

		return !nearTime.getTime().before(allocation.getBegin()) && !currentTime.getTime().after(allocation.getEnd());
	}

	/**
	 * @param username
	 * @param auth
	 * @return
	 */
	private boolean checkLogin(final String username, final byte[] auth) {
		final String password = new String(auth);
		try {
			LOGGER.log(Level.FINE, "Autenticating user [" + username + "]");
			final boolean authenticate = allocationManager.authenticate(username, password);
			LOGGER.log(Level.FINEST, "Authentication is [" + authenticate + "]");
			return authenticate;
		} catch (final UnknownDomainException e) {
			return true;
		} catch (final Exception e) {
			LOGGER.log(java.util.logging.Level.WARNING,
					"Exception while comunicating with allocation manager " + e.getMessage(), e);
			LOGGER.log(java.util.logging.Level.INFO, "Reconnecting ");
			try {
				lookupAllocationManager();
				final boolean authenticate = allocationManager.authenticate(username, password);
				LOGGER.log(Level.FINEST, "Authentication is [" + authenticate + "]");
				return authenticate;
			} catch (final Exception e2) {
				return false;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authorize(final IResource resource, final IUser user, final IOperation op) {
		if (user == null || user.getUserName() == null || resource == null || resource.getResourceType() == null
				|| resource.getProperties().get(resource.getResourceType().getPropertyKey()) == null || op == null) {
			LOGGER.log(Level.WARNING, "Invalid parameters in authorize method");
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
	private boolean checkDataProducerOperations(final IResource resource, final IUser user, final IOperation op) {
		LOGGER.log(Level.FINE,
				"Checking data producer operations for user [" + user.getUserName() + "] resource ["
						+ resource.getProperties().get(resource.getResourceType().getPropertyKey()) + "] operation ["
						+ op.getOperation() + "]");

		final IResource hardwareResource = resource.getEnclosingResource();
		final List<AllocationDTO> currentAllocation = findAllocationFor(hardwareResource.getProperties().get(
				hardwareResource.getResourceType().getPropertyKey()));

		if (!currentAllocation.isEmpty()) {
			if (op.getOperation() == OperationType.OP_GET_DATAPRODUCER
					|| op.getOperation() == OperationType.OP_GET_DATAPRODUCERSTATE
					|| op.getOperation() == OperationType.OP_GET_SAMPLES || op.getOperation() == OperationType.OP_STOP) {
				if (op.getOperation() == OperationType.OP_STOP) {
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
	private boolean checkMCHardwareOperations(final IResource resource, final IUser user, final IOperation op) {
		LOGGER.log(Level.FINE, "Checking multicast hardware operations for user [" + user.getUserName()
				+ "] resource [" + resource.getProperties().get(resource.getResourceType().getPropertyKey())
				+ "] operation [" + op.getOperation() + "]");

		final List<AllocationDTO> currentAllocation = findAllocationFor(resource.getProperties().get(
				resource.getResourceType().getPropertyKey()));

		if (!currentAllocation.isEmpty()) {
			if (op.getOperation() == OperationType.OP_LOCK || op.getOperation() == OperationType.OP_CONFIG
					|| op.getOperation() == OperationType.OP_START
					|| op.getOperation() == OperationType.OP_START_OUTPUT) {
				final boolean checkUserAsOwner = checkUserAsOwner(currentAllocation, user.getUserName());
				LOGGER.log(Level.FINEST, "Hardware operations is [" + checkUserAsOwner + "]");
				return checkUserAsOwner;
			}
		} else {
			LOGGER.log(Level.FINEST, "There aren't allocations.");
		}
		LOGGER.log(Level.FINEST, "Hardware operations is [true]");
		return true;
	}

	/**
	 * @param newAllocations
	 */
	private void mergeAllocations(final List<AllocationDTO> newAllocations) {
		final Map<String, List<AllocationDTO>> newAllocationsMap = new HashMap<String, List<AllocationDTO>>();
		for (final AllocationDTO allocation : newAllocations) {
			List<AllocationDTO> experimentAllocations = newAllocationsMap.get(allocation.getExperimentId());
			if (experimentAllocations == null) {
				experimentAllocations = new ArrayList<AllocationDTO>();
				newAllocationsMap.put(allocation.getExperimentId(), experimentAllocations);
			}
			experimentAllocations.add(allocation);
		}
		allocationsMap = newAllocationsMap;
	}

	private void cleanNotifiedUsers(final List<AllocationDTO> newAllocations) {
		final Set<Long> allocationsId = new HashSet<Long>();
		for (final AllocationDTO allocationDTO : newAllocations) {
			allocationsId.add(allocationDTO.getId());
		}
		// remove old allocations from the users notification list
		for (final Long id : notifiedUsersNotInAllocation.keySet()) {
			if (!allocationsId.contains(id)) {
				notifiedUsersNotInAllocation.remove(id);
			}
		}
		// add the new allocations
		for (final AllocationDTO allocationDTO : newAllocations) {
			if (!notifiedUsersNotInAllocation.containsKey(allocationDTO.getId())) {
				notifiedUsersNotInAllocation.put(allocationDTO.getId(), new HashSet<String>());
			}
		}
	}

	/**
	 * @param begin
	 * @param end
	 */
	private void refreshAllocations(final Date begin, final Date end) {
		LOGGER.log(Level.FINE, "Refresh allocation with Begin [" + begin + "] End [" + end + "]");
		try {
			final List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end,
					AllocationManagerSecurityManager.LABORATORY_ID);
			LOGGER.log(Level.FINEST, "Received " + newAllocations.size() + " allocations.");

			mergeAllocations(newAllocations);
			cleanNotifiedUsers(newAllocations);
			LOGGER.log(Level.FINEST, "Merged allocations " + allocationsMap);
		} catch (final Exception e) {
			try {
				lookupAllocationManager();
				final List<AllocationDTO> newAllocations = allocationManager.getBy(begin, end,
						AllocationManagerSecurityManager.LABORATORY_ID);
				mergeAllocations(newAllocations);
				cleanNotifiedUsers(newAllocations);
			} catch (final Exception e2) {
				// What if I can't lookup it up again??? Down for
				// long??? No allocations available... Ooops!

			}
		}
	}

	private AllocationManager allocationManager = null;

	private Map<String, List<AllocationDTO>> allocationsMap = new HashMap<String, List<AllocationDTO>>();

	private final Map<Long, Set<String>> notifiedUsersNotInAllocation = new Hashtable<Long, Set<String>>();

	private static final SimpleDateFormat notificationTimeFormatter = new SimpleDateFormat("HH:mm");

	public class AllocationsRefreshScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			LOGGER.log(Level.INFO, "AllocationsRefreshScheduledUnit - Going to refresh the allocations.");

			final Calendar nearTime = Calendar.getInstance();
			final Calendar farTime = Calendar.getInstance();
			farTime.setTimeInMillis(nearTime.getTimeInMillis());
			farTime.add(Calendar.MINUTE, AllocationManagerSecurityManager.NEAR_TIME_LAP_MINUTES
					+ AllocationManagerSecurityManager.REFRESH_TIME_LAP_MINUTES);

			refreshAllocations(nearTime.getTime(), farTime.getTime());
		}

	}

	public class ClientsAllocationCheckScheduledUnit extends ScheduledWorkUnit {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			LOGGER.log(Level.INFO, "ClientsAllocationScheduledUnit - Going to check clients allocations.");

			LOGGER.log(Level.FINE, "Are there allocations [" + (!allocationsMap.isEmpty())
					+ "] and multicast hardwares [" + (multiCastHardwares != null) + "]");

			int kickedClients = 0;
			int notifiedClients = 0;

			if (multiCastHardwares != null && !allocationsMap.isEmpty()) {
				final Map<String, List<AllocationDTO>> nearAllocationsMap = findNearAllocations();
				if (!nearAllocationsMap.isEmpty()) {
					final Map<String, List<AllocationDTO>> currentAllocationsMap = findCurrentAllocations();

					synchronized (multiCastHardwares) {
						final Collection<ReCMultiCastHardware> iterateHardwares = Collections
								.unmodifiableCollection(multiCastHardwares);
						for (final ReCMultiCastHardware rmch : iterateHardwares) {

							// kick users that are connected to the hardware but
							// aren't in the current allocations
							final Set<String> usernamesToKick = findHardwareUsersNotInAllocations(
									currentAllocationsMap, rmch);
							if (!usernamesToKick.isEmpty()) {
								LOGGER.log(
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
							final Set<String> usernamesNotInAllocations = findHardwareUsersNotInAllocations(
									nearAllocationsMap, rmch);
							if (!usernamesNotInAllocations.isEmpty()) {
								final List<AllocationDTO> hardwareAllocations = nearAllocationsMap.get(rmch
										.getHardwareUniqueId());
								final Set<Pair<String, Date>> usernamesToNotify = new HashSet<Pair<String, Date>>();
								for (final String username : usernamesNotInAllocations) {
									for (final AllocationDTO allocationDTO : hardwareAllocations) {
										// check if the user wasn't already
										// notified
										if (notifiedUsersNotInAllocation.containsKey(allocationDTO.getId())
												&& !notifiedUsersNotInAllocation.get(allocationDTO.getId()).contains(
														username)) {
											if (!checkUserOrOwner(Collections.singletonList(allocationDTO), username)) {
												usernamesToNotify.add(new Pair<String, Date>(username, allocationDTO
														.getBegin()));
												notifiedUsersNotInAllocation.get(allocationDTO.getId()).add(username);
											}
										}
									}
								}
								if (!usernamesToNotify.isEmpty()) {
									LOGGER.log(
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
					LOGGER.log(Level.FINE, "There are no near allocations at this time");
				}
			}

			LOGGER.log(Level.INFO, "Client allocations terminated and kicked [" + kickedClients + "] clients");
			LOGGER.log(Level.INFO, "Client allocations notified [" + notifiedClients + "] clients");
		}

		private Set<String> findHardwareUsersNotInAllocations(final Map<String, List<AllocationDTO>> allocationsMap,
				final ReCMultiCastHardware rmch) {
			final Set<String> usernames = new HashSet<String>();
			if (allocationsMap.containsKey(rmch.getHardwareUniqueId())) {
				final List<AllocationDTO> hardwareAllocations = allocationsMap.get(rmch.getHardwareUniqueId());
				final List<String> clientUsernames = rmch.getClientUsernames();
				for (final String username : clientUsernames) {
					// there is a allocation for this hardware so
					// the user must be in the allocation
					if (!checkUserOrOwner(hardwareAllocations, username)) {
						usernames.add(username);
					}
				}
			}
			return usernames;
		}

	}

	private Map<String, List<AllocationDTO>> findCurrentAllocations() {
		return findAllocations(AllocationManagerSecurityManager.INTERVAL_TIME_LAP_MINUTES);
	}

	private Map<String, List<AllocationDTO>> findNearAllocations() {
		return findAllocations(AllocationManagerSecurityManager.NEAR_TIME_LAP_MINUTES);
	}

	private Map<String, List<AllocationDTO>> findAllocations(final int interval) {
		final Map<String, List<AllocationDTO>> currentAllocationsMap = new HashMap<String, List<AllocationDTO>>();
		for (final String key : allocationsMap.keySet()) {
			final List<AllocationDTO> allocations = findAllocationFor(key, interval);
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
	public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
		this.multiCastHardwares = multiCastHardwares;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
		this.communicator = communicator;
	}

	/**
	 * Sends a message to the client saying that he is going to be shutdown from
	 * the experiment.
	 * 
	 * @param clientTo
	 */
	private void sendKickMessage(final String clientTo) {
		if (communicator != null) {
			communicator.sendMulticastMessage(clientTo, ChatMessageEvent.SECURITY_COMMUNICATION_MSG_ON_KICK_KEY);
		}
	}

	/**
	 * Sends a message warning the client that he is going to be shutdown from
	 * the experiment in some time.
	 * 
	 * @param clientTo
	 */
	private void sendWarningMessage(final Pair<String, Date> clientTo) {
		if (communicator != null) {
			final String message = ChatMessageEvent.SECURITY_COMMUNICATION_MSG_BEFORE_KICK_KEY
					+ ChatMessageEvent.SECURITY_COMMUNICATION_MSG_SEPARATOR
					+ AllocationManagerSecurityManager.notificationTimeFormatter.format(clientTo.getValue());
			communicator.sendMulticastMessage(clientTo.getKey(), message);
		}
	}

	/**
	 * @param usernames
	 */
	private void sendKickMessage(final Set<String> usernames) {
		for (final String clientTo : usernames) {
			sendKickMessage(clientTo);
		}
	}

	/**
	 * @param usernames
	 */
	private void sendWarningMessage(final Set<Pair<String, Date>> usernames) {
		for (final Pair<String, Date> clientTo : usernames) {
			sendWarningMessage(clientTo);
		}
	}

	public static String getLaboratoryID() {
		return LABORATORY_ID;
	}

}
