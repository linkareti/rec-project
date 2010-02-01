/*
 * ClientMain.java
 *
 * Created on 27 de Fevereiro de 2005, 03:34 AM
 */

package pt.utl.ist.elab.client.vpend2m;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("PENDULO_DUPLO_MOTORIZADO_V1.0");
		ui.show();
	}
}
