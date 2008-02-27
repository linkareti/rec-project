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
    IResource getEnclosingResource();
    
    Map<String, String> getProperties();
    
    ResourceType getResourceType();
}
