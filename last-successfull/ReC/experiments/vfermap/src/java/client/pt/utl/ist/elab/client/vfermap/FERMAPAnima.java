/*
 * FERMAPAnima.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.client.vfermap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.Group;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveBox;
import org.opensourcephysics.displayejs.InteractiveSphere;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class FERMAPAnima extends DrawingPanel3D implements ActionListener, MouseListener, ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255578035874029432L;
	private PopupMenu viewPopMenu;
	protected InteractiveMenu par;

	protected InteractiveSphere bola;

	private InteractiveArrow force;
	protected InteractiveArrow vel;

	protected double psi = Math.PI / 2;
	protected double wallAmp = 2;
	protected double wallFreq = .1;
	protected double dWall = 20;
	private InteractiveBox stWall;
	protected InteractiveBox wall;

	protected double bolaRadius = .5;

	public FERMAPAnima(final InteractiveMenu par) {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildSTDMAPAnima();
		this.par = par;
	}

	public FERMAPAnima() {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildSTDMAPAnima();
	}

	private void buildSTDMAPAnima() {
		setPreferredMinMax(-10, 10, -10, 10, -10, 10);

		addComponentListener(this);
		addMouseListener(this);

		stWall = new InteractiveBox();
		stWall.setXYZ(0, -10.5, 0);
		stWall.setSizeXYZ(10, 1, 10);
		stWall.getStyle().setFillPattern(java.awt.Color.DARK_GRAY);
		stWall.getStyle().setEdgeColor(java.awt.Color.DARK_GRAY);

		wall = new InteractiveBox();
		wall.setXYZ(0, 10.5, 0);
		wall.setSizeXYZ(10, 1, 10);
		wall.getStyle().setFillPattern(java.awt.Color.LIGHT_GRAY);
		wall.getStyle().setEdgeColor(java.awt.Color.LIGHT_GRAY);

		bola = new InteractiveSphere();
		bola.setXYZ(0, -10 + bolaRadius, 0);
		bola.setSizeXYZ(2 * bolaRadius, 2 * bolaRadius, 2 * bolaRadius);
		bola.getStyle().setFillPattern(java.awt.Color.ORANGE);
		bola.getStyle().setEdgeColor(java.awt.Color.ORANGE);

		force = new InteractiveArrow();
		force.setXYZ(0, -10 + bolaRadius, 0);
		force.setSizeXYZ(0, -5, 0);
		force.getStyle().setFillPattern(java.awt.Color.RED);
		force.getStyle().setEdgeColor(java.awt.Color.RED);
		force.setVisible(false);

		vel = new InteractiveArrow();
		vel.setXYZ(0, -10 + bolaRadius, 0);
		vel.setSizeXYZ(0, 10, 0);
		vel.getStyle().setFillPattern(java.awt.Color.GREEN);
		vel.getStyle().setEdgeColor(java.awt.Color.GREEN);

		final Group group = new Group();
		bola.setGroup(group);
		force.setGroup(group);
		vel.setGroup(group);

		addDrawable(stWall);
		addDrawable(wall);
		addDrawable(bola);
		addDrawable(force);
		addDrawable(vel);

		repaint();
		buildPopupMenu();
	}

	private void buildPopupMenu() {
		viewPopMenu = new PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.1"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.2"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.3"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.4"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.5"));
	}

	public void setListener(final InteractionListener list) {
		bola.addListener(list);
		wall.addListener(list);
		vel.addListener(list);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		// TODO code application logic here
	}

	public void move(final double x, final double xWall) {
		bola.getGroup().setY(x);
		wall.setY(xWall - 9.5);
		repaint();
	}

	public double getD() {
		return dWall;
	}

	public void setD(final double d) {
		dWall = d;
		checkLimits();
		setWallX(d + wallAmp * Math.sin(psi));
		repaint();
	}

	public void setWallX(final double wX) {
		wall.setY(wX - 9.5);
		repaint();
	}

	public void setWallAmp(final double wAmp) {
		wallAmp = wAmp;
		checkLimits();
	}

	private void checkLimits() {
		if (bola.getGroup().getY() > dWall - wallAmp - .5 - bolaRadius) {
			bola.getGroup().setY(dWall - wallAmp - .5 - bolaRadius);
		}
	}

	public double getWallAmp() {
		return wallAmp;
	}

	public double getWallFreq() {
		return wallFreq;
	}

	public void setSysX(final double x) {
		bola.getGroup().setY(x);
		repaint();
	}

	public double getSysX() {
		return bola.getGroup().getY();
	}

	public double getSysXMax() {
		return dWall - wallAmp - .5 - bolaRadius;
	}

	public void setVel(final double xDot) {
		vel.setSizeY(xDot / 10d);
		repaint();
	}

	public double getVel() {
		return vel.getSizeY() * 10;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"))) {
			setAlphaAndBeta(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"))) {
			setZoom(1);
			setPan(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"))) {
			setZoom(.2);
			setPan((int) Math.round(-.4 * getWidth()), 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_YZ);
			repaint();
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vfermap/resources/messages").getString(
					"rec.exp.customizer.viewMenu.title.6"));
			((javax.swing.JMenuItem) e.getSource())
					.setToolTipText(java.util.ResourceBundle.getBundle(
							"pt/utl/ist/elab/client/vfermap/resources/messages").getString(
							"rec.exp.customizer.viewMenu.tip.6"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
			repaint();
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vfermap/resources/messages").getString(
					"rec.exp.customizer.viewMenu.title.4"));
			((javax.swing.JMenuItem) e.getSource())
					.setToolTipText(java.util.ResourceBundle.getBundle(
							"pt/utl/ist/elab/client/vfermap/resources/messages").getString(
							"rec.exp.customizer.viewMenu.tip.4"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.5"))) {
			snapshot();
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
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

	protected void updateGUI() {
		par.update();
	}

	public void config(final double x, final double xDot, final double psi, final double d, final double wAmp) {
		bola.getGroup().setY(x);
		vel.setSizeY(xDot / 10d);
		dWall = d;
		wallAmp = wAmp;
		checkLimits();
		setWallX(d + wAmp * Math.sin(psi));
	}

	@Override
	public void componentHidden(final ComponentEvent e) {
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		if (getPan().x != 0) {
			setZoom(.2);
			setPan((int) Math.round(-.4 * getWidth()), 0);
			repaint();
		}
	}

	@Override
	public void componentShown(final ComponentEvent e) {
	}

}