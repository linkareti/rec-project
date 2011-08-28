/*
 * MAPanel.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.client.vstdmap.displays;

/*
 * @author  Antonio Jose Rodrigues Figueiredo
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;

import javax.swing.JMenuItem;
import javax.swing.JViewport;

import org.opensourcephysics.display.Dimensioned;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.axes.AxisFactory;

public class MAPanel extends PlottingPanel implements ActionListener, Printable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3902724457660525948L;
	protected double xmaxImg, xminImg, ymaxImg, yminImg;
	protected double pcor;
	protected BufferedImage mapImg;
	protected int iter, nn;
	protected float[] mData;
	protected String statusStr = java.util.ResourceBundle
			.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
					"rec.exp.displays.statusStr.recImage");
	protected int dataCounter = 0;
	protected Graphics g;
	protected boolean staticImg;

	protected byte pixSize = 0;
	protected double xMaxBound, yMaxBound;

	/** Creates a new instance of Animation */
	public MAPanel(final String strX, final String strY, final String title, final double xMinBound,
			final double xMaxBound, final double yMinBound, final double yMaxBound) {
		super(strX, strY, title);

		axes = AxisFactory.createAxesType2(this);
		axes.setXLabel(strX, null);
		axes.setYLabel(strY, null);
		axes.setTitle(title, null);

		this.xMaxBound = xMaxBound;
		this.yMaxBound = yMaxBound;

		xmaxPreferred = xmaxImg = xMaxBound;
		xminPreferred = xminImg = xMinBound;
		ymaxPreferred = ymaxImg = yMaxBound;
		yminPreferred = yminImg = yMinBound;
		setAutoscaleX(false);
		setAutoscaleY(false);
	}

	// TESTE
	public void drawImageNonStatic(final float theta, final float iMapa) {
		g.drawOval((int) Math.round(theta * mapImg.getWidth() / (xmaxPreferred - xminPreferred)), mapImg.getHeight()
				- (int) Math.round(iMapa * mapImg.getHeight() / (ymaxPreferred - yminPreferred)), pixSize, pixSize);
	}

	// TESTE
	public void setData(final float theta, final float iMapa) {
		mData[dataCounter++] = theta;
		mData[dataCounter++] = iMapa;
	}

	protected void updateImage() {
		generateImage();
		repaint();
	}

	protected void makeImage(final byte[] b) {
		mapImg.getGraphics().drawImage(
				java.awt.Toolkit.getDefaultToolkit().createImage(
						new java.awt.image.MemoryImageSource(mapImg.getWidth(), mapImg.getWidth(),
								new BufferedImage(mapImg.getWidth(), mapImg.getHeight(),
										BufferedImage.TYPE_BYTE_INDEXED).getColorModel(), b, 0, mapImg.getWidth())), 0,
				0, null);
	}

	private void generateImage() {
		mapImg = new BufferedImage(getWidth() - leftGutter - rightGutter, getHeight() - bottomGutter - topGutter,
				BufferedImage.TYPE_INT_RGB);

		g = mapImg.getGraphics();

		int z = 0;
		for (int i = 0; i < nn; i++) {
			final float c1 = Math.abs((mData[z++] + mData[z]) % 1);
			final float c2 = (float) Math.abs((mData[z--] + pcor * Math.sin(mData[z])) % 1);
			final float c3 = (c1 + c2) % 1;

			g.setColor(new java.awt.Color(c1, c2, c3));
			for (int j = 0; j < iter; j++) {
				g.drawOval(
						(int) Math.round((mData[z++] - xminPreferred) * mapImg.getWidth()
								/ (xmaxPreferred - xminPreferred)),
						mapImg.getHeight()
								- (int) Math.round((mData[z++] - yminPreferred) * mapImg.getHeight()
										/ (ymaxPreferred - yminPreferred)), pixSize, pixSize);
			}
		}
		xmaxImg = xmaxPreferred;
		xminImg = xminPreferred;
		ymaxImg = ymaxPreferred;
		yminImg = yminPreferred;
	}

	@Override
	protected void buildPopupmenu() {
		popupmenu.setEnabled(true);
		final ActionListener listener = this;
		JMenuItem item = new JMenuItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vstdmap/resources/messages").getString("rec.exp.displays.mapanel.menu.title.1"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.tip.1"));
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.title.2"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.tip.2"));
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.title.3"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.tip.3"));
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.title.4"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.displays.mapanel.menu.tip.4"));
		item.addActionListener(listener);
		popupmenu.add(item);
	}

	@Override
	protected void paintEverything(final Graphics g) {
		viewRect = null;
		Container c = getParent();
		while (c != null) {
			if (c instanceof JViewport) {
				viewRect = ((JViewport) c).getViewRect();
				break;
			}
			c = c.getParent();
		}

		Dimension interiorDimension = null;
		if (dimensionSetter != null) {
			interiorDimension = dimensionSetter.getInterior(this);
		}
		if (axes instanceof Dimensioned) {
			interiorDimension = ((Dimensioned) axes).getInterior(this);
		}
		if (interiorDimension != null) {
			squareAspect = false;
			leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
			topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
		}
		final java.util.ArrayList<Drawable> tempList = getDrawables();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		setPixelScale();
		axes.draw(this, g);

		if (!statusStr.equalsIgnoreCase(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vstdmap/resources/messages").getString("rec.exp.displays.statusStr.recImage"))) {
			if (!staticImg
					&& (xmaxImg != xmaxPreferred || ymaxImg != ymaxPreferred || xminImg != xminPreferred || yminImg != yminPreferred)) {
				generateImage();
			}

			g.drawImage(mapImg, leftGutter, topGutter, getWidth() - leftGutter - rightGutter, getHeight()
					- bottomGutter - topGutter, this);
		}

		if (!statusStr.equalsIgnoreCase("")) {
			g.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 26));

			final int x = (int) Math.round(getWidth() / 2d)
					- (int) Math.round((double) g.getFontMetrics().stringWidth(statusStr) / 2);
			final int y = (int) Math.round(getHeight() / 2d) - g.getFontMetrics().getHeight() + 5;

			g.setColor(new java.awt.Color(.6f, .12f, .3f));
			g.drawString(statusStr, x, (int) Math.round(getHeight() / 2d));

			g.setColor(new java.awt.Color(0, 0, 0, .4f));
			g.fillRect(0, y, getWidth(), g.getFontMetrics().getHeight());

			g.setColor(java.awt.Color.WHITE);
			g.drawLine(0, y + 2, getWidth(), y + 2);
			g.drawLine(0, y + g.getFontMetrics().getHeight() - 2, getWidth(), y + g.getFontMetrics().getHeight() - 2);
		}
	}

	@Override
	public void actionPerformed(final java.awt.event.ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.displays.mapanel.menu.title.3"))) {
			xminPreferred = 0;
			xmaxPreferred = xMaxBound;
			yminPreferred = 0;
			ymaxPreferred = yMaxBound;
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.displays.mapanel.menu.title.1"))) {
			snapshot(null, java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
					.getString("rec.exp.displays.mapanel.menu.title.1"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.displays.mapanel.menu.title.2"))) {
			snapshot(mapImg, java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
					.getString("rec.exp.displays.mapanel.menu.title.2"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.displays.mapanel.menu.title.4"))) {
			updateImage();
		}
	}

	public void snapshot(final BufferedImage bufImg, final String str) {
		final DrawingPanel panel = new DrawingPanel();
		final DrawingFrame frame = new DrawingFrame(panel);
		frame.setKeepHidden(false);
		panel.setSquareAspect(false);
		Drawable mi;
		final int w = (isVisible()) ? getWidth() : getPreferredSize().width;
		final int h = (isVisible()) ? getHeight() : getPreferredSize().height;
		if (bufImg == null) {
			if (w == 0 || h == 0) {
				return;
			}
			final BufferedImage snapimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			render(snapimage);
			mi = new org.opensourcephysics.display.MeasuredImage(snapimage, pixToX(0), pixToX(w), pixToY(h), pixToY(0));
			panel.setPreferredSize(new Dimension(w, h));
			panel.setPreferredMinMax(pixToX(0), pixToX(w), pixToY(h), pixToY(0));
		} else {
			mi = new MeasuredImage(mapImg);
			panel.setPreferredSize(new Dimension(mapImg.getWidth(), mapImg.getHeight()));
			panel.setPreferredMinMax(0, xMaxBound, 0, yMaxBound);
		}
		panel.addDrawable(mi);
		frame.setTitle(str);
		frame.pack();
		frame.show();
	}

	class MeasuredImage implements Drawable {

		private final BufferedImage buf;

		public MeasuredImage(final BufferedImage buff) {
			buf = buff;
		}

		@Override
		public void draw(final DrawingPanel panel, final Graphics g) {
			g.drawImage(buf, 0, 0, panel.getWidth(), panel.getHeight(), panel);
		}

	}

	public void config(final boolean staticImg, final byte pixSize, final int w, final int h, final int nn,
			final int iter) {
		this.staticImg = staticImg;
		this.pixSize = pixSize;
		mapImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		if (staticImg) {
			popupmenu.getComponent(2).setEnabled(false);
			popupmenu.getComponent(3).setEnabled(false);
		} else {
			this.nn = nn;
			this.iter = iter;
		}
		setZoom(!staticImg);
		g = mapImg.getGraphics();
	}

	public void setStatus(final String str) {
		statusStr = str;
	}

}
