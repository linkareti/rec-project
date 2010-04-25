/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.client;

import com.linkare.rec.am.ws.AllocationManagerWSInterface;
import com.linkare.rec.am.ws.AllocationManagerWSService;
import com.linkare.rec.am.ws.Laboratory;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Joao
 */
public class WSClient {

    private static Logger logger = Logger.getLogger(WSClient.class.getName());

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }
    private static AllocationManagerWSService service = new com.linkare.rec.am.ws.AllocationManagerWSService();

    private static AllocationManagerWSInterface port = service.getAllocationManagerWSPort();

    public static void main(String[] args) {

        try {
            getLogger().info("Before Service Call");
//            getAllLaboratories();
            echo("SLB");
            find(Long.valueOf("1"));
        } catch (Exception ex) {
            getLogger().severe(ex.getMessage());
        }

    }

    private static void getAllLaboratories() {
        getLogger().info("getAllLabs");
        List<Laboratory> result = getPort().getAllLaboratories();

        for (Laboratory laboratory : result) {
            getLogger().info("Laboratory name: " + laboratory.getName());
        }
    }

    private static void echo(String value) {
        getLogger().info("echo");
        String result = getPort().echo(value);
        getLogger().info(result);
    }

    private static void find(Long value) {
        getLogger().info("find");
        String result = getPort().find(value);
        getLogger().info(result);
    }

    /**
     * @return the service
     */
    public static AllocationManagerWSService getService() {
        return service;
    }

    /**
     * @return the port
     */
    public static AllocationManagerWSInterface getPort() {
        return port;
    }
}
