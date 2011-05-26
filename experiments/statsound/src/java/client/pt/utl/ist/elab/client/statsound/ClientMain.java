/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.statsound;

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
		final ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("ELAB_STATSOUND_STAMP_V01");
		ui.setVisible(true);
	}
}
