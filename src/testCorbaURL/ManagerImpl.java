/*
 * ManagerImpl.java
 *
 * Created on 9 de Janeiro de 2004, 15:12
 */

package testCorbaURL;

import com.linkare.rec.impl.utils.*;
/**
 *
 * @author  Administrator
 */
public class ManagerImpl implements ManagerOperations
{
    
    /** Creates a new instance of ManagerImpl */
    public ManagerImpl()
    {
    }
    
    public void sayHello(String nome)
    {
	System.out.println("Hi, my name is " + nome);
    }
    
    
    public static void main(String[] args)
    {
	try
	{
	    ManagerImpl manager=new ManagerImpl();
	    ManagerPOATie managerPoaTie=(ManagerPOATie)ORBBean.getORBBean().registerRootPOAServant(Manager.class,manager,"Manager".getBytes());
	    String corbaURL=ORBBean.getORBBean().bindObjectToCorbalocService("Manager", ORBBean.getORBBean().getRootPOA().servant_to_reference(managerPoaTie) );
	    System.out.println("corba URL=" + corbaURL);
	    Thread.currentThread().join();
	}catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void register()
    {
	
    }
}
