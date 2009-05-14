/*
 * ManagerClient.java  --  client side implementation
 *
 * Created on 9 de Janeiro de 2004, 16:02 for JDK 1.4 ORB
 * with Naming Service binding.
 */

package testCorbaURL;

import com.linkare.rec.impl.utils.ORBBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 * @version
 */
public class ManagerClient
{
    
    /** Creates new ManagerClient */
    public ManagerClient()
    {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
	
	try
	{
	    //System.out.println("Please give me the server location...");
	    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	    //String corbaurl=br.readLine();
	    Manager mng=ManagerHelper.narrow(ORBBean.getORBBean().getORB().resolve_initial_references("Manager"));
	    System.out.println("Please tell me your name...");
	    String name=br.readLine();
	    mng.sayHello(name);
	    System.out.println("All ok...");
	    
	} catch (Exception ex)
	{
	    ex.printStackTrace();
	}
    }
}


