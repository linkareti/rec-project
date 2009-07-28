/*
 * ClientMain.java
 *
 * Created on 2 de Abril de 2005, 19:52
 */

package pt.utl.ist.elab.virtual.client.di;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  Pedro Queiro'
 */
public class ClientMain {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	    //Este codigo e' sempre igual, so' altera o nome, ou melhor, o ID da exp, que neste caso vai ser DISCOS_INERCIA_V1.0
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui=new ReCBaseUI();
		ui.setApparatusAutoConnectID("DISCOS_INERCIA_V1.0");
		ui.show();
    }        
}