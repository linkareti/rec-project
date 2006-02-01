/*
 * IResource.java
 *
 * Created on 2 de Janeiro de 2004, 15:22
 */

package com.linkare.rec.impl.multicast.security;

import java.util.Map;
/**
 *
 * @author  Administrator
 */
public interface IResource extends java.io.Serializable
{
    
    public static final short RES_MCCONTROLLER = 0;
    public static final short RES_MCHARDWARE = 1;
    public static final short RES_DATAPRODUCER = 2;
    public static final short RES_CHANNEL = 3;
    public static final short RES_DATASAMPLES = 4;
    
    public static final String PROPKEY_MCCONTROLLER_LOCATION = "MCController.Location";
    public static final String PROPKEY_MCHARDWARE_ID = "MCHardware.UniqueID";
    public static final String PROPKEY_DATAPRODUCER_ID = "DataProducer.UniqueID";
    public static final String PROPKEY_CHANNEL_NAME = "Channel.Name";
    public static final String PROPKEY_DATASAMPLE_NR = "DataSample.Number";
    
    IResource getEnclosingResource();
    
    Map getProperties();
    
    short getResourceType();
    
}
