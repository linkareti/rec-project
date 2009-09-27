/*
 * ClientMain.java
 *
 * Created on 18 de Mar�o de 2005, 11:28
 */

package pt.utl.ist.elab.virtual.client.cargas3d;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author n0dP2
 */
public class ClientMain {

	/** Creates a new instance of ClientMain */
	public ClientMain() {

	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("CARGAS3D_V1.0");
		ui.show();
	}
}
