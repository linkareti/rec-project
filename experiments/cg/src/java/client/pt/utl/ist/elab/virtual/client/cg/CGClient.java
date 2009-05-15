/*
 * CGClient.java
 *
 * Created on October 27, 2004, 03:34 AM
 */

package pt.utl.ist.elab.virtual.client.cg;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */
public class CGClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("CAVENDISH_G_V1.0");
	ui.show();
    }        
}
