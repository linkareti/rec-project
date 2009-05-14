/*
 * IResource.java
 *
 * Created on 2 de Janeiro de 2004, 15:22
 */

package com.linkare.rec.impl.multicast.security;

import java.util.Map;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface IOperation extends java.io.Serializable
{
    
    public static final short OP_ENUM_HARDWARES = 0;
    public static final short OP_LIST_HARDWARE = 1;
    public static final short OP_SEND_MESSAGE = 2;
    public static final short OP_CONFIG = 3;
    public static final short OP_START = 4;
    public static final short OP_START_OUTPUT = 5;
    public static final short OP_STOP = 6;
    public static final short OP_RESET = 7;
    public static final short OP_ENUM_USERS = 8;
    public static final short OP_LIST_USER = 9;
    public static final short OP_LOCK = 10;
    public static final short OP_GET_DATAPRODUCER= 11;
    public static final short OP_GET_SAMPLES = 12;
    public static final short OP_GET_HARDWARESTATE = 13;
    public static final short OP_GET_DATAPRODUCERSTATE = 14;
    public static final short OP_GET_HARDWAREINFO=15;
    
    public static final String PROPKEY_ACQ_CONFIG = "MCHardware.AcquisitionConfig";
    public static final String PROPKEY_USERID_OTHER = "MC.UserID.Other";
    public static final String PROPKEY_HARDWARESTATE = "MCHardware.HardwareState";
    public static final String PROPKEY_REMOTE_DATAPRODUCER = "MCHardware.RemoteDataProducer";
    public static final String PROPKEY_HARDWARE_ID = "MCHardware.UniqueID";
    public static final String PROPKEY_HARDWAREINFO = "MCHardware.HardwareInfo";
    short getOperation();
    Map<String, Object> getProperties();
    
}
