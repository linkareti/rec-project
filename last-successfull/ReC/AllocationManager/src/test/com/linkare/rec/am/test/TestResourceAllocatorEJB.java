/**
 * 
 */
package com.linkare.rec.am.test;

import static org.junit.Assert.*;

import java.util.Date;

import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import com.linkare.rec.am.action.ResourceAllocationFacadeInterface;
import com.linkare.rec.am.model.Laboratory;
import com.sun.corba.se.impl.javax.rmi.PortableRemoteObject;

/**
 * @author nuno
 *
 */
public class TestResourceAllocatorEJB {

	private ResourceAllocationFacadeInterface theBean;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		InitialContext initialContext = new InitialContext();
		Object remoteStub = initialContext.lookup("AllocationManager/ResourceAllocationFacadeBean/remote");
		theBean = (ResourceAllocationFacadeInterface) javax.rmi.PortableRemoteObject
				.narrow(remoteStub, ResourceAllocationFacadeInterface.class);
		theBean.initData();
	}

	/**
	 * Test method for {@link com.linkare.rec.am.action.ResourceAllocationFacadeBean#getReservations(com.linkare.rec.am.model.Laboratory, java.util.Date, java.util.Date)}.
	 */
	@Test
	public void testGetReservations() {
		
		try {
			theBean.getReservations("ola", new Date(), new Date());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with a BUMMM!");
		}
		
		//fail("Not yet implemented");
	}

}
