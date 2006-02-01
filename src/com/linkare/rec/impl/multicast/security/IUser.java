/*
 * IUser.java
 *
 * Created on 2 de Janeiro de 2004, 15:25
 */

package com.linkare.rec.impl.multicast.security;

import java.util.*;
/**
 *
 * @author  Administrator
 */
public interface IUser extends java.io.Serializable
{
    String getUserName();
    
    public byte[] getAuth();
    
    public Map getProperties();
    
}
