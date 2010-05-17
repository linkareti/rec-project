/*
 * ClientMain.java
 *
 * Created on 6 de Junho de 2003, 20:34
 */

package pt.utl.ist.elab.client.webrobot;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Andr�
 */
public class ClientMain {

	/** Creates a new instance of ClientMain */
	public ClientMain() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("ELAB_WEBROBOT_V01");
		ui.show();
	}
}
