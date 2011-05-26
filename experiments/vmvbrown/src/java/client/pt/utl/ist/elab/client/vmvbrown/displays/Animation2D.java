/*
 * Animation2D.java
 *
 * Created on 24 de Mar�o de 2005, 3:09
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;

import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

/**
 * 
 * @author nomead
 */
public class Animation2D extends DrawingPanel implements Drawable, ActionListener, MouseListener, BrownMovement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7967529626688879814L;

	private Dataset avgPos;

	private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
			.getString("rec.exp.displays.animation.actionStr");

	/** Creates a new instance of Animation2D */
	public Animation2D() {
		super();
		addMouseListener(this);
		setPreferredMinMax(-50, 50, -50, 50);
		setSquareAspect(true);
	}

	@Override
	protected void buildPopupmenu() {
		popupmenu.setEnabled(true);
		final ActionListener listener = this;
		JMenuItem item = new JMenuItem("Zoom In");
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem("Zoom Out");
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
				.getString("rec.exp.displays.save"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
				.getString("rec.exp.displays.save.tip"));
		item.addActionListener(listener);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
				.getString("rec.exp.displays.print"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
				.getString("rec.exp.displays.print.tip"));
		item.addActionListener(listener);
		popupmenu.add(item);
	}

	@Override
	public void config(final int _numPart, final byte _animaRadius, final java.awt.Color _cor) {
		state = new double[_numPart][2]; // (x,y)
		numPart = _numPart;
		cor = _cor;
		raio = _animaRadius;
		addDrawable(this);
		avgPos = new Dataset(java.awt.Color.RED, java.awt.Color.RED, true);
		addDrawable(avgPos);
		repaint();
	}

	private final boolean isMedVisible = true;
	private final boolean isVisible = true;
	private int numPart;
	private double[][] state;

	private java.awt.Color cor;
	private byte raio;

	@Override
	public void draw(final DrawingPanel panel, final java.awt.Graphics g) {
		if (isVisible) {
			int xpix = 0;
			int ypix = 0;
			g.setColor(cor);

			for (int i = 0; i < numPart; i++) {
				xpix = panel.xToPix(state[i][0]) - raio;
				ypix = panel.yToPix(state[i][1]) - raio;
				g.fillOval(xpix, ypix, 2 * raio, 2 * raio);
			}
		}
	}

	@Override
	protected void paintEverything(final java.awt.Graphics g) {
		if (dimensionSetter != null) {
			final java.awt.Dimension interiorDimension = dimensionSetter.getInterior(this);
			if (interiorDimension != null) {
				squareAspect = false;
				leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
				topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
			}
		}
		final java.util.ArrayList tempList = getDrawables();
		scale(tempList);
		setPixelScale();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(java.awt.Color.black);
		paintDrawableList(g, tempList);

		g.setColor(new java.awt.Color(.6f, .12f, .3f));
		g.drawString(actionStr, 5, getHeight() - 10);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Zoom In")) {
			zoomIn();
		} else if (e.getActionCommand().equalsIgnoreCase("Zoom Out")) {
			zoomOut();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save"))) {
			try {
				doSaveAs();
			} catch (final IOException io) {
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print"))) {
			createChartPrintJob();
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			actionStr = "";
			repaint();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void moves(final byte[] mv) {
		final float[] mvf = ByteUtil.byteArrayToFloatArray(mv);

		final double[] med = new double[2];
		if (isVisible || isMedVisible) {
			if (mvf.length == 2 * numPart) { // dim 2
				for (int i = 0; i < state.length; i++) {
					if (isVisible) {
						state[i][0] = mvf[i];
						state[i][1] = mvf[i + 1];
					}
					if (isMedVisible) {
						med[0] += mvf[i];
						med[1] += mvf[i + 1];
					}
				}
				if (isMedVisible) {
					avgPos.append(med[0] / numPart, med[1] / numPart);
				}
			} else { // dim 1
				for (int i = 0; i < state.length; i++) {
					if (isVisible) {
						state[i][0] = mvf[i];
					}
					if (isMedVisible) {
						med[0] += mvf[i];
					}
				}
				if (isMedVisible) {
					avgPos.append(med[0] / numPart, 0);
				}
			}
		}
		repaint();
	}

	public void doSaveAs() throws IOException {

		final JFileChooser fileChooser = new JFileChooser();
		final org.jfree.ui.ExtensionFileFilter filter = new org.jfree.ui.ExtensionFileFilter(java.util.ResourceBundle
				.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString("PNG_Image_Files"), ".png");
		fileChooser.addChoosableFileFilter(filter);

		final int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getPath();

			if (!filename.endsWith(".png")) {
				filename = filename + ".png";
			}

			final OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));

			EncoderUtil.writeBufferedImage(render(), ImageFormat.PNG, out);
			out.close();
		}

	}

	public void createChartPrintJob() {
		final PrinterJob job = PrinterJob.getPrinterJob();
		final PageFormat pf = job.defaultPage();
		final PageFormat pf2 = job.pageDialog(pf);
		if (pf2 != pf) {
			job.setPrintable(this, pf2);
			if (job.printDialog()) {
				try {
					job.print();
				} catch (final PrinterException e) {
					JOptionPane.showMessageDialog(this, e);
				}
			}
		}
	}

}
