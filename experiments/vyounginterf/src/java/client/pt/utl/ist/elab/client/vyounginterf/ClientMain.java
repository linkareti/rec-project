/*
 * ClientMain.java
 *
 * Created on 16 de Outubro de 2004, 11:29
 */

package pt.utl.ist.elab.client.vyounginterf;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Ean
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		final ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("INTERFERENCIA_YOUNG_V1.0");
		ui.setVisible(true);
	}
}
