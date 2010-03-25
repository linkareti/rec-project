package com.linkare.rec.am.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;

import com.linkare.rec.am.model.ResourceAllocationFacadeInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import org.junit.Test;

/**
 * @author nuno
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({})
public class TestResourceAllocatorEJB {

    private ResourceAllocationFacadeInterface theBean;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        InitialContext initialContext = new InitialContext();
        Object remoteStub = initialContext.lookup("AllocationManager/ResourceAllocationFacade/remote");
        theBean = (ResourceAllocationFacadeInterface) javax.rmi.PortableRemoteObject.narrow(remoteStub, ResourceAllocationFacadeInterface.class);
        theBean.initData();
    }

    /**
     * Test method for {@link com.linkare.rec.am.action.ResourceAllocationFacade#getReservations(com.linkare.rec.am.model.Laboratory, java.util.Date, java.util.Date)}.
     */
    @Test
    public void testGetReservations() {

        List<String> resposta = null;

        try {
            Calendar dataInicio = Calendar.getInstance();
            dataInicio.set(2001, 11, 12);
            resposta = theBean.getReservations("ola", "", dataInicio.getTime(), new Date());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed with a BUMMM!");
        }

        assertNotNull(resposta);
        assertEquals(0, resposta.size());
        //fail("Not yet implemented");
    }

    @After
    public void tearDown() throws Exception {
    }
}
