/*
 * ClientMain.java
 *
 * Created on 2 de Abril de 2005, 19:52
 */

package pt.utl.ist.elab.client.vbs;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author Pedro Queiro'
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// Este codigo e' sempre igual, so' altera o nome, ou melhor, o ID da
		// exp, que neste caso vai ser BIOT_SAVART_V1.0
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("BIOT_SAVART_V1.0");
		ui.show();
	}
}