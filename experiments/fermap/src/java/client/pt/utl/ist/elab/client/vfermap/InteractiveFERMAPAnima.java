/*
 * InteractiveFERMAPAnima.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.client.vfermap;

import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveBox;
import org.opensourcephysics.displayejs.InteractiveElement;
import org.opensourcephysics.displayejs.InteractiveSphere;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class InteractiveFERMAPAnima extends FERMAPAnima implements InteractionListener, Runnable {

	private PopupMenu editPopMenu;

	/** Creates a new instance of InteractiveSTDMAPExp */
	public InteractiveFERMAPAnima() {
		super();
		buildInteractiveSTDMAPAnima();
	}

	public InteractiveFERMAPAnima(InteractiveMenu par) {
		super(par);
		buildInteractiveSTDMAPAnima();
	}

	private void buildInteractiveSTDMAPAnima() {
		bola.setEnabled(InteractiveElement.TARGET_POSITION, true);
		wall.setEnabled(InteractiveElement.TARGET_POSITION, true);
		vel.setEnabled(InteractiveElement.TARGET_SIZE, true);

		setListener(this);

		editPopMenu = new PopupMenu(par);
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.1"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.1"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.2"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.2"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.3"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.3"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.4"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.4"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.5"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.5"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.6"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.6"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.7"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.7"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.8"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.8"));
		editPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.title.9"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.customizer.editMenu.tip.9"));
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

	public void setWallFreq(double wFreq) {
		terminate();
		this.wallFreq = wFreq;

		animaThread = new Thread(this);
		animaThread.start();
	}

	public void setPsi(double psi) {
		setWallX(dWall + wallAmp * Math.sin(psi - 3 * Math.PI / 2));
		this.psi = psi;
	}

	public double getPsi() {
		return this.psi;
	}

	private long timer = 0;
	private Thread animaThread;

	public void run() {
		while (animaThread == Thread.currentThread()) {
			wall.setY(this.dWall - 10 + bolaRadius + Math.sin(psi + timer * .25 * Math.PI * wallFreq) * wallAmp);
			repaint();
			timer++;
			try {
				animaThread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public void config(double x, double xDot, double psi, double d, double wAmp, double wFreq) {
		config(x, xDot, psi, d, wAmp);
		this.psi = psi;
		setWallFreq(wFreq);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
	}

	public void interactionPerformed(InteractionEvent _event) {
		Object eventObj = _event.getSource();
		java.awt.event.MouseEvent e = _event.getMouseEvent();

		if (eventObj instanceof InteractiveSphere) {
			bola.getGroup().setX(0);
			bola.getGroup().setZ(0);
			if (bola.getGroup().getY() < 0)
				bola.getGroup().setY(0);
			else if (bola.getGroup().getY() > dWall - wallAmp - .5 - bolaRadius)
				bola.getGroup().setY(dWall - wallAmp - .5 - bolaRadius);
		} else if (eventObj instanceof InteractiveArrow) {
			if (Math.abs(vel.getSizeY()) > 200)
				vel.setSizeY(200 * (vel.getSizeY() / Math.abs(vel.getSizeY())));
			vel.setSizeX(0);
			vel.setSizeZ(0);
		} else if (eventObj instanceof InteractiveBox && e.getButton() != java.awt.event.MouseEvent.NOBUTTON) {
			if (wall.getY() < 0.5)
				wall.setY(0.5);
			else if (wall.getY() > 90.5)
				wall.setY(90.5);
			setD(wall.getY() + 9.5);
			setPsi(Math.PI / 2);
			if (bola.getGroup().getY() > dWall - wallAmp - .5 - bolaRadius)
				bola.getGroup().setY(dWall - wallAmp - .5 - bolaRadius);
			wall.setX(0);
			wall.setZ(0);
		}
		updateGUI();

		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			editPopMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

}
