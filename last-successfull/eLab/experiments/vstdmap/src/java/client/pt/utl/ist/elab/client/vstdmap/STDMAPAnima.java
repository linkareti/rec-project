/*
 * STDMAPAnima.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.client.vstdmap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.Group;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveSphere;
import org.opensourcephysics.displayejs.InteractiveTrace;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class STDMAPAnima extends DrawingPanel3D implements ActionListener, MouseListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7576858186892391385L;
	private PopupMenu viewPopMenu;
	protected InteractiveMenu par;

	private double mass = 100; // Kg/100

	protected InteractiveTrace fio; // length dm
	protected InteractiveSphere bola;
	private InteractiveSphere point;

	protected InteractiveArrow force; // N/10
	protected InteractiveArrow vel;

	private int forceDt = 1000; // ms

	public STDMAPAnima(final InteractiveMenu par) {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildSTDMAPAnima();
		this.par = par;
	}

	public STDMAPAnima() {
		super(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
		buildSTDMAPAnima();
	}

	private void buildSTDMAPAnima() {
		setPreferredMinMax(-10, 10, -10, 10, -10, 10);

		addMouseListener(this);

		fio = new InteractiveTrace();
		fio.addPoint(0, 0, 0);
		fio.addPoint(0, 10, 0);

		bola = new InteractiveSphere();
		bola.setXYZ(0, 10, 0);
		bola.setSizeXYZ(2, 2, 2);

		point = new InteractiveSphere();
		point.setXYZ(0, 0, 0);
		point.setSizeXYZ(.3, .3, .3);
		point.getStyle().setFillPattern(new java.awt.Color(.9f, 0, .1f));
		point.getStyle().setEdgeColor(new java.awt.Color(.9f, 0, .1f));

		force = new InteractiveArrow();
		force.setXYZ(0, 10, 0);
		force.setSizeXYZ(0, 0, -5);
		force.getStyle().setFillPattern(java.awt.Color.RED);
		force.getStyle().setEdgeColor(java.awt.Color.RED);
		force.setVisible(false);

		vel = new InteractiveArrow();
		vel.setXYZ(0, 10, 0);
		vel.setSizeXYZ(0, 0, 5);
		vel.getStyle().setFillPattern(java.awt.Color.GREEN);
		vel.getStyle().setEdgeColor(java.awt.Color.GREEN);

		final Group group = new Group();
		bola.setGroup(group);
		force.setGroup(group);
		vel.setGroup(group);

		addDrawable(fio);
		addDrawable(bola);
		addDrawable(point);
		addDrawable(force);
		addDrawable(vel);

		repaint();
		buildPopupMenu();
	}

	public void start() {
		animaThread = new Thread(this);
		animaThread.start();
	}

	private void buildPopupMenu() {
		viewPopMenu = new PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.1"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.2"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.3"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.4"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.tip.5"));
	}

	public void setListener(final InteractionListener list) {
		bola.addListener(list);
		force.addListener(list);
		vel.addListener(list);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		// TODO code application logic here
	}

	public void setMass(final double m) {
		mass = m;
	}

	public double getMass() {
		return mass;
	}

	public void setLength(final double len) {
		/*
		 * double ang = getTheta(); bola.setXYZ(0, Math.cos(ang)*len,
		 * Math.sin(ang)*len); force.setXYZ(0, bola.getY(), bola.getZ());
		 * vel.setXYZ(0, bola.getY(), bola.getZ()); fio.clear(); fio.addPoint(0,
		 * 0, 0); fio.addPoint(0, bola.getY(), bola.getZ()); repaint();
		 */
		final double ang = getTheta();
		bola.setXYZ(0, Math.sin(ang) * len, Math.cos(ang) * len);
		force.setXYZ(0, bola.getY(), bola.getZ());
		vel.setXYZ(0, bola.getY(), bola.getZ());
		fio.clear();
		fio.addPoint(0, 0, 0);
		fio.addPoint(0, bola.getY(), bola.getZ());
		repaint();
	}

	public double getLength() {
		return Math.sqrt(Math.pow(fio.getYMax() - fio.getYMin(), 2) + Math.pow(fio.getZMax() - fio.getZMin(), 2));
	}

	public void setThetaVecVel(final double vel) {
		/*
		 * this.vel.setSizeY(vel*Math.sin(getTheta()));
		 * this.vel.setSizeZ(vel*Math.cos(getTheta())); repaint();
		 */
		this.vel.setSizeY(vel * Math.cos(getTheta()));
		this.vel.setSizeZ(-vel * Math.sin(getTheta()));
		repaint();
	}

	public double getThetaVecVel() {
		/*
		 * if
		 * (-Math.cos(getTheta())*vel.getSizeZ()-Math.sin(getTheta())*vel.getSizeY
		 * () > 0) return -Math.sqrt(Math.pow(vel.getSizeZ(),
		 * 2)+Math.pow(vel.getSizeY(), 2)); else return
		 * Math.sqrt(Math.pow(vel.getSizeZ(), 2)+Math.pow(vel.getSizeY(), 2));
		 */
		if (-Math.cos(getTheta()) * vel.getSizeY() + Math.sin(getTheta()) * vel.getSizeZ() > 0) {
			return -Math.sqrt(Math.pow(vel.getSizeZ(), 2) + Math.pow(vel.getSizeY(), 2));
		} else {
			return Math.sqrt(Math.pow(vel.getSizeZ(), 2) + Math.pow(vel.getSizeY(), 2));
		}
	}

	public void move(final double ang, final double vel) {
		animaThread = new Thread(this);

		this.vel.setSizeY(vel * Math.cos(ang));
		this.vel.setSizeZ(-vel * Math.sin(ang));
		bola.setXYZ(0, Math.sin(ang) * getLength(), Math.cos(ang) * getLength());
		fio.clear();
		fio.addPoint(0, 0, 0);
		fio.addPoint(0, bola.getY(), bola.getZ());
		force.setXYZ(0, bola.getY(), bola.getZ());
		this.vel.setXYZ(0, bola.getY(), bola.getZ());

		force.setVisible(true);
		repaint();

		try {
			Thread.sleep(100);
		} catch (final InterruptedException e) {
		}

		terminate();
		force.setVisible(false);
		repaint();
	}

	public void setTheta(final double ang) {
		/*
		 * bola.setXYZ(0, Math.cos(ang)*getLength(), Math.sin(ang)*getLength());
		 * fio.clear(); fio.addPoint(0, 0, 0); fio.addPoint(0, bola.getY(),
		 * bola.getZ()); force.setXYZ(0, bola.getY(), bola.getZ());
		 * vel.setXYZ(0, bola.getY(), bola.getZ());
		 * setThetaVecVel(getThetaVecVel());
		 */
		bola.setXYZ(0, Math.sin(ang) * getLength(), Math.cos(ang) * getLength());
		fio.clear();
		fio.addPoint(0, 0, 0);
		fio.addPoint(0, bola.getY(), bola.getZ());
		force.setXYZ(0, bola.getY(), bola.getZ());
		vel.setXYZ(0, bola.getY(), bola.getZ());
		setThetaVecVel(getThetaVecVel());
	}

	public double getTheta() {
		// int n = (int) (bola.getZ()/Math.abs(bola.getZ()));
		// return -n*Math.acos(bola.getY()/getLength());
		final int n = (int) (bola.getY() / Math.abs(bola.getY()));
		return n * Math.acos(bola.getZ() / getLength());
	}

	public void setForce(final double f) {
		force.setSizeZ(-f);
		repaint();
	}

	public double getForce() {
		return -force.getSizeZ();
	}

	public void terminate() {
		final Thread runningThread = animaThread;
		animaThread = null;
		try {
			if (runningThread != null) {
				runningThread.interrupt();
				runningThread.join();
			}
		} catch (final InterruptedException e) {
		}
	}

	public void setForceDt(final int fDt) {
		terminate();

		forceDt = fDt;

		start();
	}

	public int getForceDt() {
		return forceDt;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.1"))) {
			setAlphaAndBeta(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.2"))) {
			setZoom(1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.3"))) {
			setZoom(.5);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.4"))) {
			setDisplayMode(DISPLAY_PLANAR_YZ);
			repaint();
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
					"rec.exp.customizer.viewMenu.title.6"));
			((javax.swing.JMenuItem) e.getSource())
					.setToolTipText(java.util.ResourceBundle.getBundle(
							"pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
							"rec.exp.customizer.viewMenu.tip.6"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.viewMenu.title.6"))) {
			setDisplayMode(DISPLAY_NO_PERSPECTIVE);
			repaint();
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
					"rec.exp.customizer.viewMenu.title.4"));
			((javax.swing.JMenuItem) e.getSource())
					.setToolTipText(java.util.ResourceBundle.getBundle(
							"pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
							"rec.exp.customizer.viewMenu.tip.4"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
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

	public void config(final double len, final double theta, final double thetaDot, final double mass,
			final double force, final int forceDt) {
		this.mass = mass;
		bola.setXYZ(0, Math.sin(theta) * len, Math.cos(theta) * len);
		this.force.setXYZ(0, bola.getY(), bola.getZ());
		this.force.setSizeXYZ(0, 0, -force);
		vel.setXYZ(0, bola.getY(), bola.getZ());
		fio.clear();
		fio.addPoint(0, 0, 0);
		fio.addPoint(0, bola.getY(), bola.getZ());
		setThetaVecVel(thetaDot);
		setForceDt(forceDt);
	}

	private boolean mod = true;
	private Thread animaThread;

	@Override
	public void run() {
		while (animaThread == Thread.currentThread()) {
			force.setVisible(mod);
			repaint();
			mod = !mod;
			try {
				if (!mod) {
					Thread.sleep(100);
				} else {
					Thread.sleep(forceDt);
				}
			} catch (final InterruptedException e) {
			}
		}
	}

}
