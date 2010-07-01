/*
 * ClientMain.java
 *
 * Created on October 27, 2004, 03:34 AM
 */

package pt.utl.ist.elab.client.vstdmap;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("STANDARD_MAP_V1.0");
		ui.setVisible(true);
	}
}
