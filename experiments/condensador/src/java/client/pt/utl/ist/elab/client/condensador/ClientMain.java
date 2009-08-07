/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

//TODO Estado verificar ApparatusAutoConnectID

package pt.utl.ist.elab.client.condensador;

import com.linkare.rec.impl.baseUI.*;
import javax.swing.JFrame;

/**
 *
 * @author  jp
 */
public class ClientMain
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("ELAB_CONDENSADOR_STAMP_V02");
	ui.show();
    }    
}
