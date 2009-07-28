/*
 * ClientMain.java
 *
 * Created on October 27, 2004, 03:34 AM
 */

package pt.utl.ist.elab.virtual.client.cartpole;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */
public class ClientMain
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("CART_POLE_V1.0");
	ui.show();
    }        
}
