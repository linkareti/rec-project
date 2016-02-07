/*
 * ISecurityManager.java
 *
 * Created on 2 de Janeiro de 2004, 15:20
 */

package com.linkare.rec.impl.multicast.security;

import java.util.List;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ISecurityManager {

	boolean authorize(IResource resource, IUser user, IOperation op);

	boolean authenticate(IResource resource, IUser user);

	void registerMultiCastHardware(List<ReCMultiCastHardware> multiCastHardwares);

	void registerSecurityCommunicator(ISecurityCommunicator communicator);

}
