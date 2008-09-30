/*
 * MultiCastControllerMain.java
 *
 * Created on 19 de Agosto de 2002, 15:57
 */

package com.linkare.rec.impl.multicast.startup;

import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerPOATie;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.ORBBean;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastControllerMain
{
    public static final String SYSPROP_MULTICAST_BIND_NAME = "ReC.MultiCastController.BindName";
    public static final String MULTICAST_BIND_NAME = Defaults.defaultIfEmpty(System.getProperty(SYSPROP_MULTICAST_BIND_NAME), "MultiCastController");
    public static final String SYSPROP_MULTICAST_INIT_REF = "ReC.MultiCastController.InitRef";
    public static final String MULTICAST_INIT_REF = Defaults.defaultIfEmpty(System.getProperty(SYSPROP_MULTICAST_INIT_REF), "MultiCastController");
    
    /** Creates a new instance of MultiCastControllerMain */
    public MultiCastControllerMain()
    {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ReCMultiCastController mcc=null;
        try
        {            
            ORBBean.getORBBean(args);
            try
            {
                mcc=new ReCMultiCastController();        
                MultiCastControllerPOATie mccpoatie=(MultiCastControllerPOATie)ORBBean.getORBBean().registerRootPOAServant(MultiCastController.class,mcc,MULTICAST_INIT_REF.getBytes());
                String corbaURL=ORBBean.getORBBean().bindObjectToCorbalocService(MULTICAST_BIND_NAME, ORBBean.getORBBean().getRootPOA().servant_to_reference(mccpoatie) );
                System.out.println("MultiCastController listening for requests at URL " +corbaURL);                
            }
            catch(Exception e)
            {
                //REMOVE THIS TRY CATCH BLOCK AFTER FINISHING THE TEST PHASE...
                e.printStackTrace();
            }
            //DirectoryCreator dirCreator=new DirectoryCreator();
            //dirCreator.addDirectory("com/linkare/rec/hardwares");
            
            //ReferenceBinder refBinder=new ReferenceBinder();
            //refBinder.addReference("com/linkare/rec/MultiCastController",mccpoatie._this());
            
            
            Thread.currentThread().join();
            
            
        }catch(Exception e)
        {
            System.out.println("Abnormal exit...");
            ORBBean.getORBBean(args).killORB();
            mcc.shutdown();
            mcc=null;
            e.printStackTrace();
        }
        
    }
    
}
