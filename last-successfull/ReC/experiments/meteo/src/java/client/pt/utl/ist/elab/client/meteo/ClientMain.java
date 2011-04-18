/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.meteo;

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
		ui.setApparatusAutoConnectID("ELAB_METEO_V01");
		ui.setVisible(true);
	}
}
