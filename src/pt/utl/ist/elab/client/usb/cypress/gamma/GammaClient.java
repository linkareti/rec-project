/*
 * RadioactividadeClient.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.usb.cypress.gamma;

import com.linkare.rec.impl.baseUI.*;
import javax.swing.JFrame;

/**
 *
 * @author  jp
 */
public class GammaClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("ELAB_GAMMA_V1.0");
	ui.show();
    }    
}
