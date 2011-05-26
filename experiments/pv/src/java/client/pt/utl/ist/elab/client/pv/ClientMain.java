/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.pv;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		final ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("ELAB_PV_STAMP_V02");
		ui.setVisible(true);
	}
}
