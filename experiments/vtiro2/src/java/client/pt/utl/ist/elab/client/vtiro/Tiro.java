/*
 * Tiro.java
 *
 * Created on 16 de Fevereiro de 2005, 0:04
 */

package pt.utl.ist.elab.client.vtiro;

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
import javax.swing.JOptionPane;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.Group;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveParticle;
import org.opensourcephysics.displayejs.InteractiveTrace;

import pt.utl.ist.elab.client.virtual.guipack.GUtils;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author nomead
 */
public class Tiro extends DrawingPanel3D implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806000950201101209L;
	protected String statusStr;
	private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages")
			.getString("rec.exp.displays.animation.actionStr");

	private PopupMenu viewPopMenu;

	private InteractiveArrow altura;
	private InteractiveArrow comp;
	protected InteractiveArrow vel;

	protected InteractiveParticle bullet;
	protected InteractiveParticle target;

	private InteractiveTrace linha;

	public void setListener(final InteractionListener list) {
		vel.addListener(list);
		target.addListener(list);
	}

	/** Creates a new instance of BalancaTorcao */
	public Tiro() {
		super(DrawingPanel3D.DISPLAY_PLANAR_XY);
		buildTiro();
	}

	public void buildTiro() {
		setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
				"rec.exp.customizer.tip.1"));
		setDecorationType(DrawingPanel3D.DECORATION_AXES);
		setSquareAspect(true);
		setPreferredMinMax(0, 30, 0, 15);
		setCursorMode(DrawingPanel3D.CURSOR_CROSSHAIR);
		mouseInspection = false;
		addMouseListener(this);

		bullet = new InteractiveParticle();
		bullet.setXYZ(0, 0, 0);
		bullet.setSizeXYZ(.5, .5, .5);
		bullet.setShapeType(InteractiveParticle.ELLIPSE);
		bullet.getStyle().setEdgeColor(java.awt.Color.BLUE);
		bullet.getStyle().setFillPattern(java.awt.Color.BLUE);

		target = new InteractiveParticle();
		target.setXYZ(10, 10, 0);
		target.setSizeXYZ(.5, .5, .5);
		target.setShapeType(InteractiveParticle.ELLIPSE);
		target.getStyle().setEdgeColor(java.awt.Color.YELLOW);
		target.getStyle().setFillPattern(java.awt.Color.ORANGE);

		comp = new InteractiveArrow(InteractiveArrow.SEGMENT);
		comp.setSizeXYZ(10, 0, 0);
		comp.setXYZ(0, 0, 0);
		comp.getStyle().setEdgeColor(java.awt.Color.RED);
		comp.getStyle().setFillPattern(java.awt.Color.RED);

		altura = new InteractiveArrow(InteractiveArrow.SEGMENT);
		altura.setSizeXYZ(0, 10, 0);
		altura.setXYZ(10, 0, 0);
		altura.getStyle().setEdgeColor(java.awt.Color.RED);
		altura.getStyle().setFillPattern(java.awt.Color.RED);

		vel = new InteractiveArrow(InteractiveArrow.ARROW);
		vel.setXYZ(0, 0, 0);
		vel.setSizeXYZ(6, 3, 0);
		vel.getStyle().setEdgeColor(java.awt.Color.BLUE);
		vel.getStyle().setFillPattern(java.awt.Color.BLUE);

		final Group group = new Group();
		vel.setGroup(group);
		bullet.setGroup(group);

		linha = new InteractiveTrace();
		linha.addPoint(0, 0);
		linha.addPoint(target.getX(), getIntersectionY());

		addDrawable(bullet);
		addDrawable(target);
		addDrawable(altura);
		addDrawable(comp);
		addDrawable(vel);
		addDrawable(linha);

		repaint();
		buildPopupMenu();
	}

	public void config(final double w, final double h, final double v, final double theta) {
		target.setXY(w, h);
		vel.setSizeXY(v * Math.cos(theta), v * Math.sin(theta));
		updateLinha();
		updateDistances();
	}

	public void move(final double x, final double y, final double vx, final double vy, final double yTarget) {
		bullet.getGroup().setXY(x, y);
		vel.setSizeXY(vx, vy);
		target.setY(yTarget);
		yminPreferred = Math.min(y, Math.min(yTarget, yminPreferred));
		ymaxPreferred = Math.max(y, Math.max(yTarget, ymaxPreferred));
		updateLinha();
		updateDistances();
	}

	public double getW() {
		return target.getX();
	}

	public double getH() {
		return target.getY();
	}

	private double getIntersectionY() {
		return (target.getX() - bullet.getGroup().getX()) * vel.getSizeY() / vel.getSizeX() + bullet.getGroup().getY();
	}

	public void setVel(final double v) {
		vel.setSizeXY(v * Math.cos(getTheta()), v * Math.sin(getTheta()));
		repaint();
	}

	public void setTheta(final double ang) {
		vel.setSizeXY(getVel() * Math.cos(ang), getVel() * Math.sin(ang));
		repaint();
	}

	public double getTheta() {
		return Math.atan2(vel.getSizeY(), vel.getSizeX());
	}

	public double getVel() {
		return Math.sqrt(Math.pow(vel.getSizeX(), 2) + Math.pow(vel.getSizeY(), 2));
	}

	protected void updateDistances() {
		altura.setX(target.getX());
		altura.setSizeY(target.getY());
		comp.setX(bullet.getGroup().getX());
		comp.setSizeX(target.getX() - bullet.getGroup().getX());
		repaint();
	}

	protected void updateLinha() {
		linha.clear();
		linha.addPoint(bullet.getGroup().getX(), bullet.getGroup().getY());
		linha.addPoint(target.getX(), getIntersectionY());
		repaint();
	}

	private void buildPopupMenu() {
		viewPopMenu = new PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.save"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.save.tip"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.print"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.print.tip"));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.save"))) {
			try {
				doSaveAs();
			} catch (final IOException io) {
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString(
						"rec.exp.displays.print"))) {
			createChartPrintJob();
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
			if (actionStr != null) {
				actionStr = null;
				repaint();
			}
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
	protected void paintEverything(final java.awt.Graphics g) {
		if (dimensionSetter != null) {
			final java.awt.Dimension interiorDimension = dimensionSetter.getInterior(this);
			if (interiorDimension != null) {
				squareAspect = false;
				leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
				topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
			}
		}
		@SuppressWarnings("unchecked")
		final java.util.ArrayList<Drawable> tempList = getDrawables();
		scale(tempList);
		setPixelScale();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(java.awt.Color.black);
		paintDrawableList(g, tempList);
		if (statusStr != null) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(7, 18, g.getFontMetrics().stringWidth(statusStr) + 3, g.getFontMetrics().getHeight());

			g.setColor(java.awt.Color.BLACK);
			g.drawString(statusStr, 10, 29);
		}
		if (actionStr != null) {
			g.setColor(new java.awt.Color(.6f, .12f, .3f));
			g.drawString(actionStr, 5, getHeight() - 10);
		}

		g.setColor(new java.awt.Color(.2f, .12f, .3f));

		final double[] pix = new double[3];
		project(new double[] { target.getX() + 1, target.getY() / 2, target.getZ() }, pix);
		final String h = "h : " + GUtils.trimDecimalN(target.getY(), 2) + " m";
		g.drawString(h, (int) pix[0], (int) pix[1] + g.getFontMetrics().getHeight() / 2);

		project(new double[] { bullet.getGroup().getX() + (target.getX() - bullet.getGroup().getX()) / 2, -1,
				target.getZ() }, pix);
		final String w = "w : " + GUtils.trimDecimalN(target.getX() - bullet.getGroup().getX(), 2) + " m";
		g.drawString(w, (int) pix[0] - g.getFontMetrics().stringWidth(h) / 2, (int) pix[1]);

		final String v = GUtils.trimDecimalN(getVel(), 2) + " m/s";
		project(new double[] { bullet.getGroup().getX(), bullet.getGroup().getY() + 1, target.getZ() }, pix);
		g.drawString(v, (int) pix[0] - g.getFontMetrics().stringWidth(v) / 2, (int) pix[1]);

		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
		final double theta = getTheta();
		final String thetaStr = GUtils.trimDecimalN(Math.toDegrees(theta), 2) + "�";
		project(new double[] { bullet.getGroup().getX() + (target.getX() - bullet.getGroup().getX()) / 2,
				linha.getYMin() + (linha.getYMax() - linha.getYMin()) / 2, 0 }, pix);
		g2.rotate(-theta, pix[0] - Math.cos(theta) * g.getFontMetrics().stringWidth(thetaStr) / 2,
				pix[1] + Math.sin(theta) * g.getFontMetrics().getHeight() / 2);
		g2.drawString(thetaStr,
				(int) pix[0] - (int) Math.round(Math.cos(theta) * g.getFontMetrics().stringWidth(thetaStr) / 2),
				(int) pix[1] + (int) Math.round(Math.sin(theta) * g.getFontMetrics().getHeight() / 2));
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
		test.getContentPane().add(new Tiro());
		test.pack();
		test.setVisible(true);
	}

	public void doSaveAs() throws IOException {

		final JFileChooser fileChooser = new JFileChooser();
		final org.jfree.ui.ExtensionFileFilter filter = new org.jfree.ui.ExtensionFileFilter(java.util.ResourceBundle
				.getBundle("pt/utl/ist/elab/client/vtiro/resources/messages").getString("PNG_Image_Files"), ".png");
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