package com.linkare.rec.web.model.util;

import java.util.List;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.web.model.DeployedExperiment;

/**
 * Interface with utility operations for monitoring laboratories and experiments registered in those laboratories, users connected and other important
 * information regarding the laboratories. It also contains management operations.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
public interface ILaboratoryManager {

    /**
     * Checks if the lab is available.
     * 
     * @param labId
     *            The unique identifier of the lab.
     * 
     * @return true is it is possible to connect to the multicast controller, false otherwise.
     */
    boolean isLabAvailable(String labId);

    /**
     * For the specified labId, returns the information about the experiment, such as the users connected, or the number of users connected. It may also return
     * the state.
     * 
     * @param labId
     *            The identification of the laboratory in which the experiments are registered.
     * @param experimentIds
     *            The unique identifier of all the experiments that are to refresh. If no experiments id are passed, all experiments are refreshed.
     * @return
     */
    List<DeployedExperiment> getDeployedExperimentsInfo(String labId, String... experimentIds);

    /**
     * Kicks the given user from the lab and experiment.
     * 
     * @param labId
     * @param experimentId
     * @param userToKick
     */
    void kickUserFromExperiment(String labId, String experimentId, User userToKick);

}