package com.linkare.rec.impl.newface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines the ReC Application Navigation Workflow.
 * 
 * @author Henrique Fernandes
 */
public enum NavigationWorkflow {

	// State declaration
	DISCONNECTED_OFFLINE, LAB_CONNECT_PERFORMED, CONNECTED_TO_LAB, APPARATUS_CONNECT_PERFORMED, CONNECTED_TO_APPARATUS, APPARATUS_CONFIGURED, APPARATUS_LOCKED, APPARATUS_STARTED, APPARATUS_DISCONNECT_PERFORMED, LAB_DISCONNECT_PERFORMED;
	// State declaration end

	private static final Logger log = Logger.getLogger(NavigationWorkflow.class.getName());

	private static Map<NavigationWorkflow, Set<NavigationWorkflow>> availableTransitions;

	private static Set<NavigationWorkflow> transitions(final NavigationWorkflow... transitions) {
		final HashSet<NavigationWorkflow> result = new HashSet<NavigationWorkflow>();
		for (final NavigationWorkflow transition : transitions) {
			result.add(transition);
		}
		return result;
	}

	// Workflow definition
	static {
		NavigationWorkflow.availableTransitions = new HashMap<NavigationWorkflow, Set<NavigationWorkflow>>();
		NavigationWorkflow.availableTransitions.put(DISCONNECTED_OFFLINE,
				NavigationWorkflow.transitions(LAB_CONNECT_PERFORMED));

		NavigationWorkflow.availableTransitions.put(CONNECTED_TO_LAB,
				NavigationWorkflow.transitions(APPARATUS_CONNECT_PERFORMED, LAB_DISCONNECT_PERFORMED));

		// if there is error connecting to apparatus should return no previous
		// state
		NavigationWorkflow.availableTransitions.put(APPARATUS_CONNECT_PERFORMED,
				NavigationWorkflow.transitions(CONNECTED_TO_LAB, LAB_DISCONNECT_PERFORMED));

		NavigationWorkflow.availableTransitions.put(CONNECTED_TO_APPARATUS, NavigationWorkflow.transitions(
				APPARATUS_CONFIGURED, LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED /*
																								 * returns
																								 * to
																								 * CONNECTED_TO_LAB
																								 */));

		NavigationWorkflow.availableTransitions.put(APPARATUS_CONFIGURED, NavigationWorkflow.transitions(
				APPARATUS_LOCKED, LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED /*
																							 * returns
																							 * to
																							 * CONNECTED_TO_LAB
																							 */));

		NavigationWorkflow.availableTransitions.put(APPARATUS_LOCKED,
				NavigationWorkflow.transitions(LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED));
		// returns to CONNECTED_TO_LAB

		NavigationWorkflow.availableTransitions.put(APPARATUS_STARTED,
				NavigationWorkflow.transitions(LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED));
		// returns to CONNECTED_TO_LAB

		NavigationWorkflow.availableTransitions.put(LAB_DISCONNECT_PERFORMED,
				NavigationWorkflow.transitions(DISCONNECTED_OFFLINE));
	}

	// Workflow definition end

	public boolean canGoTo(final NavigationWorkflow newState) {
		boolean result = true;
		final Set<NavigationWorkflow> currentStateTransitions = NavigationWorkflow.availableTransitions.get(this);
		if (currentStateTransitions == null) {
			if (NavigationWorkflow.log.isLoggable(Level.WARNING)) {
				NavigationWorkflow.log.warning(this + " was not configured on available transitions map.");
			}
			result = false;
		}

		result = result && currentStateTransitions.contains(newState);

		if (NavigationWorkflow.log.isLoggable(Level.FINE)) {
			NavigationWorkflow.log.fine("Transition " + this + " => " + newState + " allowed? " + result);
		}

		return result;
	}

	public boolean matches(final NavigationWorkflow state) {
		return this == state;
	}
}
