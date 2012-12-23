/*
 * Animation3D.java
 *
 * Created on 24 de Mar�o de 2005, 3:10
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

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.InteractiveParticle;
import org.opensourcephysics.displayejs.InteractiveSphere;
import org.opensourcephysics.displayejs.InteractiveTrace;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;

/**
 * 
 * @author nomead
 */
public class Animation3D extends DrawingPanel3D implements ActionListener, MouseListener, BrownMovement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8767728989605971607L;

	private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages")
			.getString("rec.exp.displays.animation.actionStr");

	private pt.utl.ist.elab.client.virtual.guipack.PopupMenu viewPopMenu;

	private final boolean isConnectedTrail = false, closeUp = false;

	private int numPart;

	private InteractiveTrace avgPos;
	private InteractiveSphere[] parts;

	/** Creates a new instance of Animation3D 
	 * @param display */
	public Animation3D(final int display) {
		super(display);
		setDecorationType(DrawingPanel3D.DECORATION_AXES);
		setPreferredMinMax(-5, 5, -5, 5, -5, 5);
		addMouseListener(this);
		buildPopupMenu();
	}

	private void buildPopupMenu() {
		viewPopMenu = new pt.utl.ist.elab.client.virtual.guipack.PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.1"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.2"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.3"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.4"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.15"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.15"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.16"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.16"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/movproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.6"));
		viewPopMenu.addCheckBoxItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.7"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.7"), true);
		viewPopMenu.addCheckBoxItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.9"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.9"), true);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.10"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.10"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save.tip"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print.tip"));
		((JCheckBoxMenuItem) viewPopMenu.getComponent(8)).setEnabled(false);
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
		final java.util.ArrayList<Drawable> tempList = getDrawables();
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
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"))) {
			setAlphaAndBeta(-Math.PI / 2, Math.PI / 2);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"))) {
			if (getZoom() != 1) {
				setZoom(1);
				repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"))) {
			if (getZoom() != .5) {
				setZoom(.5);
				repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_XY);
			setPan(0, 0);
			setZoom(1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.15"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_XZ);
			setPan(0, 0);
			setZoom(1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.16"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_YZ);
			setPan(0, 0);
			setZoom(1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
			setPan(0, 0);
			setZoom(1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.7"))) {
			setParticlesVisibility(((javax.swing.JMenuItem) e.getSource()).isSelected());
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.9"))) {
			avgPos.setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.10"))) {
			avgPos.clear();
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

	private void setParticlesVisibility(final boolean visible) {
		for (final InteractiveSphere part : parts) {
			part.setVisible(visible);
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			actionStr = "";
			viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void moves(final byte[] mv) {
		final float[] mvf = ByteUtil.byteArrayToFloatArray(mv);

		final double[] med = new double[3];

		if (avgPos.isVisible() || (parts != null && parts[0].isVisible())) {
			for (int i = 0; i < numPart; i++) {
				if (parts != null && parts[0].isVisible()) {
					parts[i].setXYZ(mvf[i] / 10d, mvf[i + 1] / 10d, mvf[i + 2] / 10d);
				}
				if (avgPos.isVisible()) {
					med[0] += mvf[i];
					med[1] += mvf[i + 1];
					med[2] += mvf[i + 2];
				}
			}
		}
		if (avgPos.isVisible()) {
			avgPos.addPoint(med[0] / numPart, med[1] / numPart, med[2] / numPart);
		}

		repaint();

	}

	@Override
	public void config(final int _numPart, final byte _animaRadius, final java.awt.Color _cor) {
		if (_numPart <= 30) {
			parts = new InteractiveSphere[_numPart];

			for (int i = 0; i < parts.length; i++) {
				parts[i] = new InteractiveSphere();
				parts[i].getStyle().setEdgeColor(_cor);
				parts[i].getStyle().setFillPattern(null);
				parts[i].setSizeXYZ(_animaRadius / 10d, _animaRadius / 10d, _animaRadius / 10d);
				addDrawable(parts[i]);
			}
			((JCheckBoxMenuItem) viewPopMenu.getComponent(8)).setEnabled(true);
		}
		numPart = _numPart;
		avgPos = new InteractiveTrace();
		avgPos.getStyle().setEdgeColor(java.awt.Color.RED);
		avgPos.getStyle().setFillPattern(java.awt.Color.RED);
		avgPos.setShapeType(InteractiveParticle.ROUND_RECTANGLE);
		avgPos.setShapeSize(3);
		addDrawable(avgPos);
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
