/*
 * ClientMain.java
 *
 * Created on 17 de Fevereiro de 2005, 11:44
 */

package pt.utl.ist.elab.client.vm3;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author n0dP2
 */
public class ClientMain {
    
    /** Creates a new instance of ClientMain */
    public ClientMain() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("MOLAS3_V1.0");
	ui.show();
        // TODO code application logic here
    }
    
}
