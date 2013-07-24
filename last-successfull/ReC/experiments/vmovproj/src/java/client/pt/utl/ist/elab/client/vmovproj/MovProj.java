/*
 * MovProj.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.client.vmovproj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.Group;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveElement;
import org.opensourcephysics.displayejs.InteractivePlane;
import org.opensourcephysics.displayejs.InteractiveSphere;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author nomead
 */
public class MovProj extends DrawingPanel3D implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1419964671253995291L;
	protected PopupMenu viewPopMenu;
	protected InteractiveMenu par;

	protected InteractiveSphere bola;

	protected InteractiveArrow acel;
	protected InteractiveArrow vel;
	protected InteractiveArrow spin;

	private InteractivePlane floor;

	public MovProj(final InteractiveMenu par) {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildMovProj();
		this.par = par;
	}

	public MovProj() {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildMovProj();
	}

	private void buildMovProj() {
		setAlphaAndBeta(-Math.PI / 2, Math.PI / 2);
		setPreferredMinMax(-20, 20, 0, 25, -20, 20);
		setDecorationType(DrawingPanel3D.DECORATION_NONE);

		addMouseListener(this);

		bola = new InteractiveSphere();
		bola.setXYZ(0, 0, 0);
		bola.setSizeXYZ(2, 2, 2);
		bola.getStyle().setFillPattern(java.awt.Color.WHITE);
		bola.getStyle().setEdgeColor(java.awt.Color.WHITE);

		vel = new InteractiveArrow();
		vel.setXYZ(bola.getX(), bola.getY(), bola.getZ());
		vel.setSizeXYZ(10, 10, 0);
		vel.getStyle().setFillPattern(java.awt.Color.ORANGE);
		vel.getStyle().setEdgeColor(java.awt.Color.ORANGE);

		spin = new InteractiveArrow();
		spin.setXYZ(bola.getX(), bola.getY(), bola.getZ());
		spin.setSizeXYZ(0, 10, 0);
		spin.getStyle().setFillPattern(java.awt.Color.BLUE);
		spin.getStyle().setEdgeColor(java.awt.Color.BLUE);

		acel = new InteractiveArrow();
		acel.setXYZ(bola.getX(), bola.getY(), bola.getZ());
		acel.setSizeXYZ(0, -9.8, 0);
		acel.getStyle().setFillPattern(java.awt.Color.RED);
		acel.getStyle().setEdgeColor(java.awt.Color.RED);

		final Group group = new Group();
		bola.setGroup(group);
		vel.setGroup(group);
		spin.setGroup(group);
		acel.setGroup(group);

		group.setY(1);

		floor = new InteractivePlane();
		floor.setVectorU(1, 0, 0);
		floor.setVectorV(0, 0, 1);
		floor.setSizeXYZ(50, 50, 0);
		floor.getStyle().setFillPattern(new java.awt.Color(.1f, .9f, .1f, .3f));

		addDrawable(bola);
		addDrawable(vel);
		addDrawable(spin);
		addDrawable(acel);
		addDrawable(floor);

		repaint();
		buildPopupMenu();
	}

	private void buildPopupMenu() {
		viewPopMenu = new PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.1"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.2"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.3"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.4"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.15"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.15"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.16"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.16"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.6"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.5"));
	}

	public void setListener(final InteractionListener list) {
		bola.addListener(list);
		vel.addListener(list);
		spin.addListener(list);
		acel.addListener(list);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		// TODO code application logic here
	}

	public void move(final double x, final double y, final double z, final double vx, final double vy, final double vz,
			final double ax, final double ay, final double az) {
		bola.getGroup().setXYZ(x, y + bola.getSizeX() / 2, z);
		vel.setSizeXYZ(vx, vy, vz);
		acel.setSizeXYZ(ax, ay, az);

		floor.setSizeXYZ(bola.getGroup().getX() * 2, bola.getGroup().getZ() * 2, 0);
	}

	public void setX(final double x) {
		bola.getGroup().setX(x);
		repaint();
	}

	public double getPosX() {
		return bola.getGroup().getX();
	}

	public void setY(final double y) {
		bola.getGroup().setY(y + bola.getSizeX() / 2);
		repaint();
	}

	public double getPosY() {
		return bola.getGroup().getY() - bola.getSizeX() / 2;
	}

	public void setZ(final double z) {
		bola.getGroup().setZ(z);
		repaint();
	}

	public double getPosZ() {
		return bola.getGroup().getZ();
	}

	public static double getLength(final InteractiveElement elm) {
		return Math.sqrt(Math.pow(elm.getSizeX(), 2) + Math.pow(elm.getSizeY(), 2) + Math.pow(elm.getSizeZ(), 2));
	}

	public static void setLength(final InteractiveElement elm, final double len) {
		final double theta = MovProj.getTheta(elm);
		final double phi = MovProj.getPhi(elm);
		elm.setSizeXYZ(len * Math.cos(theta) * Math.sin(phi), len * Math.sin(theta) * Math.sin(phi),
				len * Math.cos(phi));
	}

	public static void setTheta(final InteractiveElement elm, final double theta) {
		final double len = MovProj.getLength(elm);
		final double phi = MovProj.getPhi(elm);
		elm.setSizeXY(len * Math.cos(theta) * Math.sin(phi), len * Math.sin(theta) * Math.sin(phi));
	}

	public static void setPhi(final InteractiveElement elm, final double phi) {
		final double len = MovProj.getLength(elm);
		final double theta = MovProj.getTheta(elm);
		elm.setSizeXYZ(len * Math.cos(theta) * Math.sin(phi), len * Math.sin(theta) * Math.sin(phi),
				len * Math.cos(phi));
	}

	public static void setAngles(final InteractiveElement elm, final double theta, final double phi) {
		final double len = MovProj.getLength(elm);
		elm.setSizeXYZ(len * Math.cos(theta) * Math.sin(phi), len * Math.sin(theta) * Math.sin(phi),
				len * Math.cos(phi));
	}

	public static double getTheta(final InteractiveElement elm) {
		return Math.atan2(elm.getSizeY(), elm.getSizeX());
	}

	public static double getPhi(final InteractiveElement elm) {
		return Math.acos(elm.getSizeZ() / MovProj.getLength(elm));
	}

	public void setRadius(final double r) {
		bola.setSizeXYZ(r * 2, r * 2, r * 2);
		if (bola.getGroup().getY() < bola.getSizeX() / 2) {
			bola.getGroup().setY(bola.getSizeX() / 2);
		} else if (bola.getGroup().getY() > 50 + bola.getSizeX() / 2) {
			bola.getGroup().setY(50 + bola.getSizeX() / 2);
		}
		repaint();
	}

	public double getRadius() {
		return bola.getSizeX() / 2;
	}

	public void setVel(final double v) {
		MovProj.setLength(vel, v);
		repaint();
	}

	public double getVel() {
		return MovProj.getLength(vel);
	}

	public void setThetaVel(final double tVel) {
		MovProj.setTheta(vel, tVel);
		repaint();
	}

	public double getThetaVel() {
		return MovProj.getTheta(vel);
	}

	public void setPhiVel(final double pVel) {
		MovProj.setPhi(vel, pVel);
		repaint();
	}

	public void setAngsVel(final double tVel, final double pVel) {
		MovProj.setAngles(vel, tVel, pVel);
		repaint();
	}

	public double getPhiVel() {
		return MovProj.getPhi(vel);
	}

	public void setSpin(final double sp) {
		MovProj.setLength(spin, sp);
		repaint();
	}

	public double getSpin() {
		return MovProj.getLength(spin);
	}

	public void setThetaSpin(final double tSp) {
		MovProj.setTheta(spin, tSp);
		repaint();
	}

	public double getThetaSpin() {
		return MovProj.getTheta(spin);
	}

	public void setPhiSpin(final double pSp) {
		MovProj.setPhi(spin, pSp);
		repaint();
	}

	public double getPhiSpin() {
		return MovProj.getPhi(spin);
	}

	public void setAngsSpin(final double tSp, final double pSp) {
		MovProj.setAngles(spin, tSp, pSp);
		repaint();
	}

	public void setAcel(final double acl) {
		MovProj.setLength(acel, acl);
		repaint();
	}

	public double getAcel() {
		return MovProj.getLength(acel);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"))) {
			setAlphaAndBeta(-Math.PI / 2, Math.PI / 2);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"))) {
			if (getZoom() != 1) {
				setZoom(1);
				repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"))) {
			if (getZoom() != .5) {
				setZoom(.5);
				repaint();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_XY);
			setPan(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.15"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_XZ);
			setPan(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.16"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_PLANAR_YZ);
			setPan(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"))) {
			setDisplayMode(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
			setPan(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmovproj/resources/messages").getString(
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

	public void config(final double x, final double y, final double z, final double vel, final double thetaVel,
			final double phiVel, final double spin, final double thetaSpin, final double phiSpin, final double acel,
			final double radius) {
		bola.setSizeXYZ(radius * 2, radius * 2, radius * 2);
		bola.getGroup().setXYZ(x, y + bola.getSizeX() / 2, z);

		this.vel.setSizeXYZ(vel * Math.cos(thetaVel) * Math.sin(phiVel), vel * Math.sin(thetaVel) * Math.sin(phiVel),
				vel * Math.cos(phiVel));

		this.spin.setSizeXYZ(spin * Math.cos(thetaSpin) * Math.sin(phiSpin),
				spin * Math.sin(thetaSpin) * Math.sin(phiSpin), spin * Math.cos(phiSpin));

		setAcel(acel);
	}

}
