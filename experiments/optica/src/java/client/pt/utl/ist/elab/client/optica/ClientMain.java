
package pt.utl.ist.elab.client.optica;

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
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("ELAB_OPTICA_DSPIC_V1.0");
		ui.setVisible(true);
	}
}
