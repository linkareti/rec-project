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

import javax.swing.JPanel;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class GraphPanel extends JPanel {

	private int imageWidth = 324; // each column has 4 pixels (71*4 = 284)
	private int columnWidth = 4;
	private int leftMargin = 10;
	private int rightMargin = 30;
	private int imageHeight = 140; // maximum height of column = 100 pixels; the
	// rest is for the axes
	private int graphHeight = 100;
	private int topMargin = 15;
	private int bottomMargin = 15;
	private int extraMargin = 5;
	private BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
	private int imageArea = imageWidth * imageHeight;
	private int[] pixels = new int[imageArea];

	private int[] x;
	private int[] y;
	private int maxY = 0;
	private double[] params = new double[4];

	/** Creates a new instance of GraphPanel */
	public GraphPanel(double[] x, double[] y, double mu, double sigma, double y0, double Amp) {

		buildGraphPanel(x, y, mu, sigma, y0, Amp);
	}

	public GraphPanel() {
	}

	public GraphPanel buildGraphPanel(double[] x, double[] y, double mu, double sigma, double y0, double Amp) {
		this.x = new int[x.length];
		this.y = new int[y.length];

		this.imageWidth = x.length * columnWidth + leftMargin + rightMargin;

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
			int temp = this.y[index];
			if (temp > maxY)
				maxY = temp;
		}
		if (maxY == 0)
			maxY = 1;
		drawAxes();
		drawData();
		drawFunction();
		return this;
	}// buildGraphPanel()

	public void updateGraph(double[] x, double[] y, double mu, double sigma, double y0, double Amp) {
		this.x = new int[x.length];
		this.y = new int[y.length];
		this.imageWidth = x.length * columnWidth + leftMargin + rightMargin;

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
			int temp = this.y[index];
			if (temp > maxY)
				maxY = temp;
		}
		drawAxes();
		drawData();
		drawFunction();
	}// updateGraph(double[] x, double[] y, double mu, double sigma, double y0,

	// double Amp)

	public java.awt.Image getImage() {
		return (java.awt.Image) image;
	}// getImage

	public int[] imageSize() {
		// super.addNotify();

		int[] size = { 0, 0 };

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
		Graphics g = image.createGraphics();

		int fontSize = 8;
		g.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		g.fillRect(0, 0, imageWidth, imageHeight);
		g.setColor(Color.black);
		// drawing Y axes
		g.drawLine(leftMargin, topMargin, leftMargin, imageHeight - bottomMargin);
		g.drawLine(imageWidth - rightMargin, topMargin, imageWidth - rightMargin, imageHeight - bottomMargin);

		for (int index = (bottomMargin + extraMargin); index < imageHeight - (topMargin - extraMargin); index += 2 * extraMargin) {
			g.drawString(String.valueOf(index - (bottomMargin + extraMargin)), 1, imageHeight - index + fontSize / 2);
			double absValue = (double) ((index - (bottomMargin + extraMargin)) * maxY) / 100.;
			g.drawString(String.valueOf(absValue), imageWidth - (rightMargin - extraMargin), imageHeight - index
					+ fontSize / 2);
		}
		g.drawString("%", extraMargin, fontSize);
		g.drawString("Abs", imageWidth - rightMargin, fontSize);

		// drawing the X axes
		g.drawLine(leftMargin - extraMargin, imageHeight - (bottomMargin + extraMargin), imageWidth
				- (rightMargin - extraMargin), imageHeight - (bottomMargin + extraMargin));

		for (int index = leftMargin; index < imageWidth - rightMargin; index += columnWidth) {
			if (x[(index - leftMargin) / columnWidth] % 5 == 0) // only draws
				// string every
				// 5 columns
				g.drawString(String.valueOf(x[(index - leftMargin) / columnWidth]), index, imageHeight - leftMargin);
			// else
			// g.drawString(String.valueOf(x[(index-10)/4]),index,imageHeight-2);
		}

	}// drawAxes

	/**
	 *Draws rectangles on the graph. These rectangles' height is related to the
	 * y[] values
	 */
	private void drawData() {
		Graphics g = image.createGraphics();
		g.setColor(Color.gray);

		for (int index = 0; index < x.length; index++) {
			int altura = y[index] * graphHeight / maxY;
			g.drawRect(index * columnWidth + leftMargin, imageHeight - (bottomMargin + extraMargin) - altura,
					columnWidth, altura);
		}
	}// drawData

	/**
	 *Draws the Gaussian Function on the graph with the previously fitted
	 * parameters
	 */

	private void drawFunction() {
		Graphics g = image.createGraphics();
		g.setColor(Color.red);
		for (int index = leftMargin; index < imageWidth - rightMargin; index++) {
			double xValue = (double) index / (double) columnWidth + (double) leftMargin + 1.;
			double yValue = pt.utl.ist.elab.client.aleatorio.utils.LMFit.gaussFunc(xValue, params[0], params[1],
					params[2], params[3]);
			int yPoint = (int) Math.round((yValue * (-(double) graphHeight)) / maxY) + imageHeight
					- (bottomMargin + extraMargin);
			g.drawLine(index, yPoint, index, yPoint);
		}
	}// drawFunction()

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // paint background

		java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Draw image at its natural size.
		g.drawImage(image, 0, 0, this);
	}

	/*
	 * public Image getImage() { return image; }
	 */

}
