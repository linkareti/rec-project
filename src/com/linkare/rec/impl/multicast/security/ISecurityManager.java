/*
 * ISecurityManager.java
 *
 * Created on 2 de Janeiro de 2004, 15:20
 */

package com.linkare.rec.impl.multicast.security;

import com.linkare.rec.acquisition.*;

/**
 *
 * @author  Administrator
 */
public interface ISecurityManager
{
    
    boolean authorize(IResource resource, IUser user, IOperation op);
    
    boolean authenticate(IResource resource, IUser user);
    
}
