/*
 * ImageStorePanel.java
 *
 * Created on 8 de Julho de 2003, 17:12
 */

package pt.utl.ist.elab.client.aleatorio.displays;

import java.awt.Dimension;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class ImageStorePanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779812912136097153L;
	private java.awt.Image image = null;

	/** Creates a new instance of ImageStorePanel */
	public ImageStorePanel() {
		setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.red));
	}

	public ImageStorePanel(final java.awt.Image image) {
		this.image = image;
	}

	/*
	 * int width=0, height = 0; public void setDisplaySize(Dimension size) {
	 * this.width = size.width; this.height = size.height;
	 * setPreferredSize(size); setMinimumSize(new Dimension(width/2,height/2));
	 * setMaximumSize(new Dimension(width*2,height*2)); //sim, foi uma escolha
	 * totalmente arbitraria!! }//setDisplaySize
	 */
	@Override
	public void paintComponent(final java.awt.Graphics g) {
		// System.out.println("PaintComponent called...");
		super.paintComponent(g); // paint background
		if (image == null) {
			return;
			// java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
			/*
			 * Graphics2D g2D=(Graphics2D)g;
			 * g2D.drawImage(image,AffineTransform.getScaleInstance
			 * (getWidth()/image.
			 * getWidth(this),getHeight()/image.getHeight(this)),this);
			 */
			// isto era so para te mostrar como se faz um scaling, mas agora nao
			// importa muito...
			// tracker.addImage(image,0);
			/*
			 * try { tracker.waitForAll(); }catch(InterruptedException e) {
			 * LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage(),e); throw new RuntimeException(e); }
			 */
		}

		final int WIDTH = image.getWidth(this);
		final int HEIGHT = image.getHeight(this);
		final double width = this.getSize().getWidth();
		final double height = this.getSize().getHeight();
		final double scaleX = width / WIDTH;
		final double scaleY = height / HEIGHT;
		double scale = Math.min(scaleX, scaleY);
		scale = Math.min(1, scale);
		final double newWidth = scale * WIDTH;
		final double newHeight = scale * HEIGHT;
		final double topZ = (width - newWidth) / 2;
		final double topY = (height - newHeight) / 2;

		/*
		 * if (width == 0 || height == 0) { g.drawImage(image, 0, 0, this);
		 * //Draw image at its natural size.
		 * //System.out.println("Drwaing image in it's natural size for video : ["
		 * + image.getWidth(null) +","+image.getHeight(null)+"]"); } else {
		 * g.drawImage(image, 0, 0, width, height, this); //draw image with the
		 * specified size.
		 * //System.out.println("Drwaing image resized for video : [" +
		 * width+","+height+"]"); //onde tens os printouts das frames de video?
		 * }
		 */
		g.drawImage(image, (int) topZ, (int) topY, (int) newWidth, (int) newHeight, this);
	}// paintComponent(Graphics g)

	public Dimension imageSize() {
		final Dimension size = new Dimension(0, 0);

		if (image != null) {
			size.setSize(image.getWidth(this), image.getHeight(this));
		} else {
			size.setSize(-1, -1);
		}
		return size;
	}// imageSize()

	public void setImage(final java.awt.Image image) {
		this.image = image;
		if (image != null) {
			/*
			 * int imageWidth = image.getWidth(this); double
			 * scaleX=imageWidth/WIDTH, scaleY=height/HEIGHT,
			 * scale=min(scaleX,scaleY); this.setDisplaySize(size
			 */
			revalidate();
			repaint();
			/*
			 * try { javax.swing.SwingUtilities.invokeAndWait( new Runnable(){
			 * public void run() {
			 * //System.out.println("Requiring a paintImmediately...");
			 * //System.out.println("Bounds for paint are: "+getBounds());
			 * paintImmediately(getBounds()); } } ); }catch(Exception e){}
			 */
		}

	}// setImage(java.awt.Image image)

}
