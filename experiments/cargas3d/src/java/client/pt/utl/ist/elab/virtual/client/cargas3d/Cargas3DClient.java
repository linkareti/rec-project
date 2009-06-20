/*
 * Cargas3DClient.java
 *
 * Created on 18 de Março de 2005, 11:28
 */

package pt.utl.ist.elab.virtual.client.cargas3d;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author n0dP2
 */
public class Cargas3DClient {
    
    /** Creates a new instance of Cargas3DClient */
    public Cargas3DClient() {
        
    }
    
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("CARGAS3D_V1.0");
	ui.show();
    }        
}
