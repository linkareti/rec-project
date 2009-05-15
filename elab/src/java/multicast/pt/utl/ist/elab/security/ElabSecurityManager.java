/*
 * CFNSecurityManager.java
 *
 * Created on October 20, 2004, 3:34 PM
 */

package pt.utl.ist.elab.security;

/**
 *
 * @author André Neto - LEFT - IST
 *
 * It may support sql authentication, but first I will use the CFN mail authentication...
 */

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.ISecurityManager;
import com.linkare.rec.impl.multicast.security.IUser;
import com.linkare.rec.impl.multicast.security.ResourceType;

public class ElabSecurityManager implements ISecurityManager
{
    private static String ELAB_SECURITY_MANAGER_LOGGER = "ELABSecurity.Logger";
    static
    {
        Logger l=LogManager.getLogManager().getLogger(ELAB_SECURITY_MANAGER_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER));
        }
    }
    
    private File logins = null;
    private FileWriter fw = null;
    
    /** Creates a new instance of CFNSecurityModel */
    public ElabSecurityManager()
    {
        logins = new File("logins.txt");
    }
    
    private String LS = System.getProperty("line.separator");
    
    public boolean authenticate(IResource resource, IUser user)
    {
        Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER).log(Level.INFO,"Authenticating "+ user.getUserName());
        
        //First try to authenticate from the members db...
        //String userName = user.getUserName();
        //String pass = "" + new String(user.getAuth()).trim().hashCode();
        
        /*GregorianCalendar cal = new GregorianCalendar();
        try
        {
            if(resource.getResourceType() == resource.RES_MCCONTROLLER)
            {
                fw = new FileWriter(logins, true);
                fw.write(userName + " logged in to lab in: " + cal.get(cal.DAY_OF_MONTH) + "/" +
                (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR) + " at " +
                cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + LS
                );
                fw.close();
            }
        }
        catch(Exception e)
        {
            LoggerUtil.logThrowable("Error writting to file...", e, Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER));
        }*/
        return true;
    }
    
    
    public boolean authorize(com.linkare.rec.impl.multicast.security.IResource resource, com.linkare.rec.impl.multicast.security.IUser user, com.linkare.rec.impl.multicast.security.IOperation op)
    {
        String userName = user.getUserName();
        if(op.getOperation() == IOperation.OP_START)
        {
            try
            {
                GregorianCalendar cal = new GregorianCalendar();
                fw = new FileWriter(logins, true);
                resource.getResourceType();
				fw.write(userName + " started: " + (String)resource.getProperties().get(ResourceType.MCCONTROLLER.getPropertyKey()) + " in: " + cal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " at " +
                cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + LS
                );
                fw.close();
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable("Error writting to file...", e, Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER));
            }
        }
        
        return true;
    }
    
    public static void main(String args[])
    {
        String userName = "andre";
        GregorianCalendar cal = new GregorianCalendar();
        System.out.println( userName + "logged in to lab in: " + cal.get(Calendar.DAY_OF_MONTH) + "/" +
        (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " at " +
        cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)                   );
    }
}
