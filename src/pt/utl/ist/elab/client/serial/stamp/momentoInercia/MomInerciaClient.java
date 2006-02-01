/*
 * RadioactividadeClient.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.serial.stamp.momentoInercia;

import com.linkare.rec.impl.baseUI.*;
import javax.swing.JFrame;

/**
 *
 * @author  jp
 */
public class MomInerciaClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("EXP_ANGULAR_STAMP_V1.0");
	ui.show();
    }    
}
