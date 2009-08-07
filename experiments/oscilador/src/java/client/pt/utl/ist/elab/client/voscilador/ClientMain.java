/*
 * ClientMain.java
 *
 * Created on 29 de Mar�o de 2005, 1:15
 */

package pt.utl.ist.elab.client.voscilador;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  RF
 */
public class ClientMain
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //Este código é sempre igual, só altera o nome, ou melhor, o ID da exp, que neste caso vai ser MASSA_MOLA_V1.0
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("OSCILADOR_V1.0");
	ui.show();
    }        
}
