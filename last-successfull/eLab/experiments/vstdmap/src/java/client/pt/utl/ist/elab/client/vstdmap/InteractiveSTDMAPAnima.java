/*
 * InteractiveSTDMAPAnima.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.client.vstdmap;

import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveElement;
import org.opensourcephysics.displayejs.InteractiveSphere;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class InteractiveSTDMAPAnima extends STDMAPAnima implements InteractionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6238070682636593988L;
	private PopupMenu editPopMenu;

	/** Creates a new instance of InteractiveSTDMAPExp */
	public InteractiveSTDMAPAnima() {
		super();
		buildInteractiveSTDMAPAnima();
	}

	public InteractiveSTDMAPAnima(final InteractiveMenu par) {
		super(par);
		buildInteractiveSTDMAPAnima();
	}

	private void buildInteractiveSTDMAPAnima() {
		bola.setEnabled(InteractiveElement.TARGET_ROTATION, true);
		bola.setRotationAxis(new javax.vecmath.Vector3d(1, 0, 0));
		bola.setRotationPoint(new javax.vecmath.Vector3d(0, 0, 0));

		force.setEnabled(InteractiveElement.TARGET_SIZE, true);
		vel.setEnabled(InteractiveElement.TARGET_SIZE, true);

		setListener(this);

		editPopMenu = new PopupMenu(par);
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.1"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.2"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.3"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.4"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.5"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.6"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.6"));
		editPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.title.7"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages").getString(
						"rec.exp.customizer.editMenu.tip.7"));
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		// TODO code application logic here
	}

	@Override
	public void interactionPerformed(final InteractionEvent _event) {
		final Object eventObj = _event.getSource();

		if (eventObj instanceof InteractiveSphere) {
			fio.clear();
			fio.addPoint(0, 0, 0);
			fio.addPoint(0, bola.getY(), bola.getZ());
			force.setXYZ(0, bola.getY(), bola.getZ());
			vel.setXYZ(0, bola.getY(), bola.getZ());
			setThetaVecVel(getThetaVecVel());
		} else if (((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.GREEN) {
			if (getThetaVecVel() > 8 * Math.PI) {
				setThetaVecVel(8 * Math.PI);
			} else if (getThetaVecVel() < -8 * Math.PI) {
				setThetaVecVel(-8 * Math.PI);
			} else {
				setThetaVecVel(getThetaVecVel());
			}
		} else {
			if (((InteractiveArrow) eventObj).getSizeZ() > 25) {
				((InteractiveArrow) eventObj).setSizeZ(25);
			} else if (((InteractiveArrow) eventObj).getSizeZ() < -25) {
				((InteractiveArrow) eventObj).setSizeZ(-25);
			}
			((InteractiveArrow) eventObj).setSizeX(0);
			((InteractiveArrow) eventObj).setSizeY(0);
		}
		updateGUI();

		final java.awt.event.MouseEvent mouseEvent = _event.getMouseEvent();
		if (mouseEvent!=null && javax.swing.SwingUtilities.isRightMouseButton(mouseEvent)) {
			editPopMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
		}
	}

}