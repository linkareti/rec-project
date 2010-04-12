/*
 * InteractivePend2M.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.client.vpend2m;

import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveElement;
import org.opensourcephysics.displayejs.InteractiveSphere;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */
public class InteractivePend2M extends Pend2M implements InteractionListener, Runnable {

	private PopupMenu editPopMenu;
	private InteractiveSphere freq;

	/** Creates a new instance of InteractivePend2MExp */
	public InteractivePend2M() {
		super();
		buildInteractivePend2M();
	}

	public InteractivePend2M(InteractiveMenu par) {
		super(par);
		buildInteractivePend2M();
	}

	private void buildInteractivePend2M() {

		freq = new InteractiveSphere();
		freq.setXYZ(0, 0, 0);
		freq.getStyle().setEdgeColor(new java.awt.Color(.9f, 0, .1f));
		freq.getStyle().setFillPattern(new java.awt.Color(.9f, 0, .1f));

		addDrawable(freq);

		bar.setEnabled(InteractiveElement.TARGET_SIZE, true);

		bola[0].setEnabled(InteractiveElement.TARGET_ROTATION, true);
		bola[0].setRotationAxis(new javax.vecmath.Vector3d(1, 0, 0));
		bola[0].setRotationPoint(new javax.vecmath.Vector3d(0, 0, 0));

		bola[1].setEnabled(InteractiveElement.TARGET_ROTATION, true);
		bola[1].setRotationAxis(new javax.vecmath.Vector3d(1, 0, 0));
		bola[1].setRotationPoint(new javax.vecmath.Vector3d(0, -4, -4));

		point.setEnabled(InteractiveElement.TARGET_POSITION, true);

		vel[0].setEnabled(InteractiveElement.TARGET_SIZE, true);
		vel[1].setEnabled(InteractiveElement.TARGET_SIZE, true);

		setListener(this);

		editPopMenu = new PopupMenu(par);
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.1"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.1"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.2"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.2"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.3"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.3"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.4"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.4"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.5"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.5"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.6"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.6"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.7"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.7"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.8"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.8"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.title.9"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.editMenu.tip.9"));
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
	}

	public void start() {
		animaThread = new Thread(this);
		animaThread.start();
	}

	public void terminate() {
		Thread runningThread = animaThread;
		animaThread = null;
		try {
			if (runningThread != null) {
				runningThread.interrupt();
				runningThread.join();
			}
		} catch (InterruptedException e) {
		}
	}

	public void setFreq(double w) {
		this.w = w;
	}

	public double getFreq() {
		return w;
	}

	protected double w = .1;
	private int timer = 0;
	private Thread animaThread;

	public void run() {
		while (animaThread == Thread.currentThread()) {
			freq.setY(Math.sin(fase + timer * .25 * Math.PI * w) * bar.getSizeY() / 2);
			repaint();
			timer++;
			try {
				animaThread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public void interactionPerformed(InteractionEvent _event) {
		Object eventObj = _event.getSource();

		if (eventObj instanceof InteractiveSphere) {

			if (((InteractiveSphere) eventObj) == point) {
				if (point.getGroup().getZ() != 0)
					point.getGroup().setZ(0);
				if (point.getGroup().getX() != 0)
					point.getGroup().setX(0);
				if (Math.abs(point.getGroup().getY()) > Math.abs(bar.getY()))
					point.getGroup().setY(
							Math.abs(bar.getY()) * point.getGroup().getY() / Math.abs(point.getGroup().getY()));
				fase = Math.asin(2 * point.getGroup().getY() / bar.getSizeY());
			} else {
				if (((InteractiveSphere) eventObj) == bola[0]) {

					fio[0].clear();
					fio[0].addPoint(0, point.getY(), point.getZ());
					fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());

					keepDistances();

					bola[1].setRotationPoint(new javax.vecmath.Vector3d(0, bola[0].getY(), bola[0].getZ()));
					vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
					setThetaVecVel(getThetaVecVel());
				}
				fio[1].clear();
				fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
				fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());
				vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
				setPhiVecVel(getPhiVecVel());
			}
		} else if (eventObj instanceof InteractiveArrow && ((InteractiveArrow) eventObj) == bar) {
			if (bar.getSizeZ() != 0)
				bar.setSizeZ(0);
			if (bar.getSizeX() != 0)
				bar.setSizeX(0);

			if (bar.getSizeY() > 30) {
				bar.setY(-15);
				bar.setSizeY(30);
			} else if (bar.getSizeY() < -30) {
				bar.setY(15);
				bar.setSizeY(-30);
			} else
				bar.setY(-bar.getSizeY() / 2);

			if (Math.abs(point.getGroup().getY()) > Math.abs(bar.getY()))
				point.getGroup().setY(
						Math.abs(bar.getY()) * point.getGroup().getY() / Math.abs(point.getGroup().getY()));
			point.getGroup().setY(bar.getSizeY() * Math.sin(fase) / 2);
		} else if (((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.GREEN) {
			if (getThetaVecVel() > 2 * Math.PI)
				setThetaVecVel(2 * Math.PI);
			else if (getThetaVecVel() < -2 * Math.PI)
				setThetaVecVel(-2 * Math.PI);
			else
				setThetaVecVel(getThetaVecVel());

			if (getPhiVecVel() > 2 * Math.PI)
				setPhiVecVel(2 * Math.PI);
			else if (getPhiVecVel() < -2 * Math.PI)
				setPhiVecVel(-2 * Math.PI);
			else
				setPhiVecVel(getPhiVecVel());
		}

		updateGUI();

		java.awt.event.MouseEvent e = _event.getMouseEvent();
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			editPopMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void config(double theta, double phi, double thetaDot, double phiDot, double l1, double l2, double amp,
			double fase, double w) {
		this.w = w;

		config(theta, phi, thetaDot, phiDot, l1, l2, amp, fase);
	}
}
