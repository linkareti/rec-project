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

	/**
	 * 
	 */
	private static final long serialVersionUID = 5260943360273559600L;
	private PopupMenu editPopMenu;

	/** Creates a new instance of InteractiveSTDMAPExp */
	public InteractiveFERMAPAnima() {
		super();
		buildInteractiveSTDMAPAnima();
	}

	public InteractiveFERMAPAnima(final InteractiveMenu par) {
		super(par);
		buildInteractiveSTDMAPAnima();
	}

	private void buildInteractiveSTDMAPAnima() {
		bola.setEnabled(InteractiveElement.TARGET_POSITION, true);
		wall.setEnabled(InteractiveElement.TARGET_POSITION, true);
		vel.setEnabled(InteractiveElement.TARGET_SIZE, true);

		setListener(this);

		editPopMenu = new PopupMenu(par);
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.1"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.2"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.3"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.4"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.5"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.6"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.6"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.7"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.7"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.8"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.8"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.9"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.9"));
	}

	public void start() {
		animaThread = new Thread(this);
		animaThread.start();
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

	public void setWallFreq(final double wFreq) {
		terminate();
		wallFreq = wFreq;

		animaThread = new Thread(this);
		animaThread.start();
	}

	public void setPsi(final double psi) {
		setWallX(dWall + wallAmp * Math.sin(psi - 3 * Math.PI / 2));
		this.psi = psi;
	}

	public double getPsi() {
		return psi;
	}

	private long timer = 0;
	private Thread animaThread;

	@Override
	public void run() {
		while (animaThread == Thread.currentThread()) {
			wall.setY(dWall - 10 + bolaRadius + Math.sin(psi + timer * .25 * Math.PI * wallFreq) * wallAmp);
			repaint();
			timer++;
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {
			}
		}
	}

	public void config(final double x, final double xDot, final double psi, final double d, final double wAmp,
			final double wFreq) {
		config(x, xDot, psi, d, wAmp);
		this.psi = psi;
		setWallFreq(wFreq);
	}

	@Override
	public void interactionPerformed(final InteractionEvent _event) {
		
		final Object eventObj = _event.getSource();
		final java.awt.event.MouseEvent mouseEvent = _event.getMouseEvent();

		if (eventObj instanceof InteractiveSphere) {
			bola.getGroup().setX(0);
			bola.getGroup().setZ(0);
			if (bola.getGroup().getY() < 0) {
				bola.getGroup().setY(0);
			} else if (bola.getGroup().getY() > dWall - wallAmp - .5 - bolaRadius) {
				bola.getGroup().setY(dWall - wallAmp - .5 - bolaRadius);
			}
		} else if (eventObj instanceof InteractiveArrow) {
			if (Math.abs(vel.getSizeY()) > 200) {
				vel.setSizeY(200 * (vel.getSizeY() / Math.abs(vel.getSizeY())));
			}
			vel.setSizeX(0);
			vel.setSizeZ(0);
		} else if (eventObj instanceof InteractiveBox && mouseEvent!=null && mouseEvent.getButton() != java.awt.event.MouseEvent.NOBUTTON) {
			if (wall.getY() < 0.5) {
				wall.setY(0.5);
			} else if (wall.getY() > 90.5) {
				wall.setY(90.5);
			}
			setD(wall.getY() + 9.5);
			setPsi(Math.PI / 2);
			if (bola.getGroup().getY() > dWall - wallAmp - .5 - bolaRadius) {
				bola.getGroup().setY(dWall - wallAmp - .5 - bolaRadius);
			}
			wall.setX(0);
			wall.setZ(0);
		}
		updateGUI();
		
		if (mouseEvent!=null && javax.swing.SwingUtilities.isRightMouseButton(mouseEvent)) {
			editPopMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}

}
