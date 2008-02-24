/*
 * CFNSecurityManager.java
 *
 * Created on October 20, 2004, 3:34 PM
 */

package pt.utl.ist.elab.security;

/**
 *
 * @author  andre
 *
 * It may support sql authentication, but first I will use the CFN mail authentication...
 */

import com.linkare.rec.impl.multicast.security.ISecurityManager;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.IUser;
import com.linkare.rec.impl.logging.LoggerUtil;
import java.util.logging.*;
import java.sql.*;
import java.util.*;
import java.io.*;

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
    
    private Connection conn = null;
    private Properties props = null;
    private File logins = null;
    private FileWriter fw = null;
    
    /** Creates a new instance of CFNSecurityModel */
    public ElabSecurityManager()
    {
        logins = new File("logins.txt");
    }
    
    //Aparentemente o REC chama este método duas vezes e eu só quero incrementar o nlogin uma vez por autenticação
    private boolean alreadyAuthenticated = false;
    private IUser user = null;
    private boolean dbAuth = false;
    private String LS = System.getProperty("line.separator");
    
    public boolean authenticate(IResource resource, IUser user)
    {
        Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER).log(Level.INFO,"Authenticating "+ user.getUserName());
        
        //First try to authenticate from the members db...
        String userName = user.getUserName();
        String pass = "" + new String(user.getAuth()).trim().hashCode();
        
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
        if(op.getOperation() == op.OP_START)
        {
            try
            {
                GregorianCalendar cal = new GregorianCalendar();
                fw = new FileWriter(logins, true);
                fw.write(userName + " started: " + (String)resource.getProperties().get(resource.getResourceType().PROPKEY_MCHARDWARE_ID) + " in: " + cal.get(cal.DAY_OF_MONTH) + "/" +
                (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR) + " at " +
                cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + LS
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
        System.out.println( userName + "logged in to lab in: " + cal.get(cal.DAY_OF_MONTH) + "/" +
        (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR) + " at " +
        cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE)                   );
    }
}
