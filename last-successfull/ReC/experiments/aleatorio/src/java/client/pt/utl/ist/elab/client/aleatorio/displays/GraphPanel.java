package pt.utl.ist.elab.client.aleatorio.displays;

/*
 * GraphPanel.java
 *
 * Created on 2 de Junho de 2003, 11:54
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class GraphPanel extends JPanel {

	private static final Logger LOGGER=Logger.getLogger(GraphPanel.class.getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9019825017990093105L;
	private int imageWidth = 324; // each column has 4 pixels (71*4 = 284)
	private final int columnWidth = 4;
	private final int leftMargin = 10;
	private final int rightMargin = 30;
	private final int imageHeight = 140; // maximum height of column = 100
											// pixels; the
	// rest is for the axes
	private final int graphHeight = 100;
	private final int topMargin = 15;
	private final int bottomMargin = 15;
	private final int extraMargin = 5;
	private final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//	private final int imageArea = imageWidth * imageHeight;
//	private final int[] pixels = new int[imageArea];

	private int[] x;
	private int[] y;
	private int maxY = 0;
	private final double[] params = new double[4];

	/** Creates a new instance of GraphPanel 
	 * @param x 
	 * @param y 
	 * @param mu 
	 * @param sigma 
	 * @param y0 
	 * @param Amp */
	public GraphPanel(final double[] x, final double[] y, final double mu, final double sigma, final double y0,
			final double Amp) {

		buildGraphPanel(x, y, mu, sigma, y0, Amp);
	}

	public GraphPanel() {
	}

	public GraphPanel buildGraphPanel(final double[] x, final double[] y, final double mu, final double sigma,
			final double y0, final double Amp) {
		this.x = new int[x.length];
		this.y = new int[y.length];

		imageWidth = x.length * columnWidth + leftMargin + rightMargin;

		for (int index = 0; index < y.length; index++) {
			this.x[index] = (int) x[index];
			this.y[index] = (int) y[index];
		}
		maxY = this.y[0];

		params[0] = mu;
		params[1] = sigma;
		params[2] = y0;
		params[3] = Amp;

		// finds the maximum of y[]... will be used to draw the axes
		for (int index = 0; index < y.length; index++) {
			final int temp = this.y[index];
			if (temp > maxY) {
				maxY = temp;
			}
		}
		if (maxY == 0) {
			maxY = 1;
		}
		drawAxes();
		drawData();
		drawFunction();
		return this;
	}// buildGraphPanel()

	public void updateGraph(final double[] x, final double[] y, final double mu, final double sigma, final double y0,
			final double Amp) {
		this.x = new int[x.length];
		this.y = new int[y.length];
		imageWidth = x.length * columnWidth + leftMargin + rightMargin;

		for (int index = 0; index < y.length; index++) {
			this.x[index] = (int) x[index];
			this.y[index] = (int) y[index];
		}
		maxY = this.y[0];

		params[0] = mu;
		params[1] = sigma;
		params[2] = y0;
		params[3] = Amp;

		// finds the maximum of y[]... will be used to draw the axes
		for (int index = 0; index < y.length; index++) {
			final int temp = this.y[index];
			if (temp > maxY) {
				maxY = temp;
			}
		}
		drawAxes();
		drawData();
		drawFunction();
	}// updateGraph(double[] x, double[] y, double mu, double sigma, double y0,

	// double Amp)

	public java.awt.Image getImage() {
		return image;
	}// getImage

	public int[] imageSize() {
		// super.addNotify();

		final int[] size = { 0, 0 };

		if (image != null) {
			size[0] = image.getWidth(this);
			size[1] = image.getHeight(this);
		} else {
			size[0] = -1;
			size[1] = -1;
		}

		return (size);
	}

	private void drawAxes() {
		final Graphics g = image.createGraphics();

		final int fontSize = 8;
		g.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		g.fillRect(0, 0, imageWidth, imageHeight);
		g.setColor(Color.black);
		// drawing Y axes
		g.drawLine(leftMargin, topMargin, leftMargin, imageHeight - bottomMargin);
		g.drawLine(imageWidth - rightMargin, topMargin, imageWidth - rightMargin, imageHeight - bottomMargin);

		for (int index = (bottomMargin + extraMargin); index < imageHeight - (topMargin - extraMargin); index += 2 * extraMargin) {
			g.drawString(String.valueOf(index - (bottomMargin + extraMargin)), 1, imageHeight - index + fontSize / 2);
			final double absValue = ((index - (bottomMargin + extraMargin)) * maxY) / 100.;
			g.drawString(String.valueOf(absValue), imageWidth - (rightMargin - extraMargin), imageHeight - index
					+ fontSize / 2);
		}
		g.drawString("%", extraMargin, fontSize);
		g.drawString("Abs", imageWidth - rightMargin, fontSize);

		// drawing the X axes
		g.drawLine(leftMargin - extraMargin, imageHeight - (bottomMargin + extraMargin), imageWidth
				- (rightMargin - extraMargin), imageHeight - (bottomMargin + extraMargin));

		for (int index = leftMargin; index < imageWidth - rightMargin; index += columnWidth) {
			if (x[(index - leftMargin) / columnWidth] % 5 == 0) {
				// string every
				// 5 columns
				g.drawString(String.valueOf(x[(index - leftMargin) / columnWidth]), index, imageHeight - leftMargin);
				// else
				// g.drawString(String.valueOf(x[(index-10)/4]),index,imageHeight-2);
			}
		}

	}// drawAxes

	/**
	 * Draws rectangles on the graph. These rectangles' height is related to the
	 * y[] values
	 */
	private void drawData() {
		final Graphics g = image.createGraphics();
		g.setColor(Color.gray);

		for (int index = 0; index < x.length; index++) {
			final int altura = y[index] * graphHeight / maxY;
			g.drawRect(index * columnWidth + leftMargin, imageHeight - (bottomMargin + extraMargin) - altura,
					columnWidth, altura);
		}
	}// drawData

	/**
	 * Draws the Gaussian Function on the graph with the previously fitted
	 * parameters
	 */

	private void drawFunction() {
		final Graphics g = image.createGraphics();
		g.setColor(Color.red);
		for (int index = leftMargin; index < imageWidth - rightMargin; index++) {
			final double xValue = (double) index / (double) columnWidth + leftMargin + 1.;
			final double yValue = pt.utl.ist.elab.client.aleatorio.utils.LMFit.gaussFunc(xValue, params[0], params[1],
					params[2], params[3]);
			final int yPoint = (int) Math.round((yValue * (-(double) graphHeight)) / maxY) + imageHeight
					- (bottomMargin + extraMargin);
			g.drawLine(index, yPoint, index, yPoint);
		}
	}// drawFunction()

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g); // paint background

		final java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (final InterruptedException e) {
			LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage(),e);
			throw new RuntimeException(e);
		}
		// Draw image at its natural size.
		g.drawImage(image, 0, 0, this);
	}

	/*
	 * public Image getImage() { return image; }
	 */

}
