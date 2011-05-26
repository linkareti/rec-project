/*
 * ClientMain.java
 *
 * Created on 16 de Outubro de 2004, 11:29
 */

package pt.utl.ist.elab.client.vpoisson;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author André
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		// Este código é sempre igual, só altera o nome, ou melhor, o ID da exp,
		// que neste caso vai ser MASSA_MOLA_V1.0
		JFrame.setDefaultLookAndFeelDecorated(true);
		final ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("POISSON_V1.0");
		ui.setVisible(true);
	}
}
