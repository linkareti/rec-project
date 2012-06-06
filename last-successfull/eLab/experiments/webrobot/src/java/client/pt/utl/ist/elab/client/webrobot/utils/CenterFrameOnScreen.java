/*
 * CenterFrameOnScreen.java
 *
 * Created on 12 de Dezembro de 2002, 17:50
 */

package pt.utl.ist.elab.client.webrobot.utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class CenterFrameOnScreen {

	/** Creates a new instance of CenterFrameOnScreen 
	 * @param jFrame */
	public CenterFrameOnScreen(final javax.swing.JFrame jFrame) {
		final java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		final java.awt.Dimension screenDim = toolkit.getScreenSize();
		jFrame.setLocation((screenDim.getSize().width - jFrame.getSize().width) / 2,
				(screenDim.getSize().height - jFrame.getSize().height) / 2);
	}

	public CenterFrameOnScreen(final javax.swing.JDialog jDialog) {
		final java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		final java.awt.Dimension screenDim = toolkit.getScreenSize();
		jDialog.setLocation((screenDim.getSize().width - jDialog.getSize().width) / 2,
				(screenDim.getSize().height - jDialog.getSize().height) / 2);
	}
}
