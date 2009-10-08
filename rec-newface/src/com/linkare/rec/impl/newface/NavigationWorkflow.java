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

    private static Set<NavigationWorkflow> transitions(NavigationWorkflow... transitions) {
	HashSet<NavigationWorkflow> result = new HashSet<NavigationWorkflow>();
	for (NavigationWorkflow transition : transitions)
	    result.add(transition);
	return result;
    }

    // Workflow definition
    static {
	availableTransitions = new HashMap<NavigationWorkflow, Set<NavigationWorkflow>>();
	availableTransitions.put(DISCONNECTED_OFFLINE, transitions(LAB_CONNECT_PERFORMED));

	availableTransitions.put(CONNECTED_TO_LAB, transitions(APPARATUS_CONNECT_PERFORMED, LAB_DISCONNECT_PERFORMED));

	availableTransitions.put(CONNECTED_TO_APPARATUS, transitions(APPARATUS_CONFIGURED, LAB_DISCONNECT_PERFORMED,
		APPARATUS_DISCONNECT_PERFORMED /* returns to CONNECTED_TO_LAB */));

	availableTransitions.put(APPARATUS_CONFIGURED, transitions(APPARATUS_LOCKED, LAB_DISCONNECT_PERFORMED,
		APPARATUS_DISCONNECT_PERFORMED /* returns to CONNECTED_TO_LAB */));

	availableTransitions
		.put(APPARATUS_LOCKED, transitions(LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED /*
													     * returns
													     * to
													     * CONNECTED_TO_LAB
													     */));

	// CRITICAL Confirm this APPARATUS_STARTE transitions (LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED
	// Será que podemos fazer esta transição. Será que podemos desligar antes de chegar ao estado STOPED?
	availableTransitions.put(APPARATUS_STARTED, transitions(LAB_DISCONNECT_PERFORMED,
		APPARATUS_DISCONNECT_PERFORMED /* returns to CONNECTED_TO_LAB */));
	// CRITICAL Confirm this APPARATUS_STARTE transitions (LAB_DISCONNECT_PERFORMED, APPARATUS_DISCONNECT_PERFORMED

	availableTransitions.put(LAB_DISCONNECT_PERFORMED, transitions(DISCONNECTED_OFFLINE));
    }

    // Workflow definition end

    public boolean canGoTo(NavigationWorkflow newState) {
	boolean result = true;
	Set<NavigationWorkflow> currentStateTransitions = availableTransitions.get(this);
	if (currentStateTransitions == null) {
	    if (log.isLoggable(Level.WARNING)) {
		log.warning(this + " was not configured on available transitions map.");
	    }
	    result = false;
	}

	result = (result == false) ? false : currentStateTransitions.contains(newState);

	if (log.isLoggable(Level.FINE)) {
	    log.fine("Transition " + this + " => " + newState + " allowed? " + result);
	}

	return result;
    }

    public boolean matches(NavigationWorkflow state) {
	return this == state;
    }
}
