/*
 * RadioactividadeClient.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.usb.cypress.polaroid;

import com.linkare.rec.impl.baseUI.*;
import javax.swing.JFrame;

/**
 *
 * @author  jp
 */
public class PolaroidClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("ELAB_POLAROID_V1.3");
	ui.show();
    }    
}
