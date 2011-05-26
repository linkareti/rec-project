/*
 * ImageStorePanel.java
 *
 * Created on 8 de Julho de 2003, 17:12
 */

package pt.utl.ist.elab.driver.aleatorio.Utils;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class ImageStorePanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6793177765317408124L;
	private java.awt.Image image = null;

	/** Creates a new instance of ImageStorePanel */
	public ImageStorePanel() {
	}

	public ImageStorePanel(final java.awt.Image image) {
		this.image = image;
	}

	@Override
	public void paintComponent(final java.awt.Graphics g) {
		super.paintComponent(g); // paint background

		final java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (final InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		g.drawImage(image, 0, 0, this); // Draw image at its natural size.
	}// paintComponent(Graphics g)

	public int[] imageSize() {
		final int[] size = { 0, 0 };

		if (image != null) {
			size[0] = image.getWidth(this);
			size[1] = image.getHeight(this);
		} else {
			size[0] = -1;
			size[1] = -1;
		}
		return (size);
	}// imageSize()

	public void setImage(final java.awt.Image image) {
		this.image = image;
	}// setImage(java.awt.Image image)

}
