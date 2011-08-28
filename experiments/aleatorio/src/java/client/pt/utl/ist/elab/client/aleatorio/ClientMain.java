/*
 * ClientMain.java
 *
 * Created on 12 de Junho de 2003, 13:49
 */

package pt.utl.ist.elab.client.aleatorio;

import javax.swing.SwingUtilities;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class ClientMain {

	private static String NAME = "ELAB_ALEATORIO_V02";

	// private static String PATH =
	// "pt.utl.ist.elab.client.aleatorio.displays.";

	/** Creates a new instance of ClientMain */
	public ClientMain() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				final ReCBaseUI ui = new ReCBaseUI();
				ui.setApparatusAutoConnectID(ClientMain.NAME);
				ui.pack();
				ui.setVisible(true);
			}
		});

	}
}
