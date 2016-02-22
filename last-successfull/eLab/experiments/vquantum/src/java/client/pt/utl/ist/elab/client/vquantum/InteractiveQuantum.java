/*
 * InteractiveQuantum.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.client.vquantum;

import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;

/**
 * 
 * @author nomead
 */
public class InteractiveQuantum extends Quantum implements InteractiveMouseHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7670919537499065010L;
	private Bounded focusInter;
	private final InteractiveMenu par;
	private final pt.utl.ist.elab.client.virtual.guipack.PopupMenu potentialMenu;

	public InteractiveQuantum(final InteractiveMenu parent) {
		super();

		par = parent;

		final JMenuItem item = new JMenuItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vquantum/resources/messages").getString(
				"rec.exp.customizer.potentialMenu.title.1"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.customizer.potentialMenu.tip.1"));
		item.addActionListener(par);
		popupmenu.add(item, 0);

		potentialMenu = new pt.utl.ist.elab.client.virtual.guipack.PopupMenu(par);
		potentialMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.tip.1"));
		potentialMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.tip.2"));
		potentialMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.potentialMenu.tip.3"));
	}

	private double xDrag, yDrag;
	private double xPressed;

	@Override
	public void handleMouseAction(final InteractivePanel panel, final MouseEvent evt) {
		if (actionStr != null && SwingUtilities.isRightMouseButton(evt)) {
			actionStr = null;
			repaint();
		}
		switch (panel.getMouseAction()) {
		case InteractivePanel.MOUSE_PRESSED:
			if ((enableZoom && evt.isShiftDown()) || zoomMode) {
				zoomBox.startZoom(evt.getX(), evt.getY());
			}
			final Interactive inter = getInteractive();
			if (inter == null) {
				if (SwingUtilities.isRightMouseButton(evt) && popupmenu != null && popupmenu.isEnabled()) {
					popupmenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
				if (enableZoom && evt.isControlDown()) {
					setPreferredMinMax(panel.getMouseX() - (xmaxPreferred - xminPreferred) / 2, panel.getMouseX()
							+ (xmaxPreferred - xminPreferred) / 2, panel.getMouseY() - (ymaxPreferred - yminPreferred)
							/ 2, panel.getMouseY() + (ymaxPreferred - yminPreferred) / 2);
					repaint();
				}
				return;
			}
			if (SwingUtilities.isRightMouseButton(evt)) {
				if (inter instanceof Potential) {
					potentialMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				} else {
					gaussMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
			double xx = panel.getMouseX();
			double yy = panel.getMouseY();
			if (evt.getX() < 1 + leftGutter) {
				xx = panel.pixToX(1 + leftGutter);
			}
			if (evt.getX() > panel.getWidth() - 1 - rightGutter) {
				xx = panel.pixToX(panel.getWidth() - 1 - rightGutter);
			}
			if (evt.getY() < 1 + topGutter) {
				yy = panel.pixToY(1 + topGutter);
			}
			if (evt.getY() > panel.getHeight() - 1 - bottomGutter) {
				yy = panel.pixToY(panel.getHeight() - 1 - bottomGutter);
			}
			xPressed = inter.getX();
			inter.getY();
			xDrag = xx;
			yDrag = yy;
			break;
		case InteractivePanel.MOUSE_DRAGGED:
			if (zoomBox.isVisible()) {
				zoomBox.drag(evt.getX(), evt.getY());
			}
			if (iaDragable == null) {
				return;
			}
			double x = panel.getMouseX();
			double y = panel.getMouseY();
			if (evt.getX() < 1 + leftGutter) {
				x = panel.pixToX(1 + leftGutter);
			}
			if (evt.getX() > panel.getWidth() - 1 - rightGutter) {
				x = panel.pixToX(panel.getWidth() - 1 - rightGutter);
			}
			if (evt.getY() < 1 + topGutter) {
				y = panel.pixToY(1 + topGutter);
			}
			if (evt.getY() > panel.getHeight() - 1 - bottomGutter) {
				y = panel.pixToY(panel.getHeight() - 1 - bottomGutter);
			}
			iaDragable.setXY(x - xDrag, y - yDrag);
			xDrag = x;
			yDrag = y;
			panel.repaint();
			break;
		case InteractivePanel.MOUSE_MOVED:
			if (getInteractive() == null) {
				if (focusInter != null && focusInter.getPaintColor() == focusInter.getHighLightColor()) {
					focusInter.mouseOut(this);
				}
				focusInter = null;
				return;
			}
			if (focusInter != null && focusInter != getInteractive()) {
				focusInter.mouseOut(this);
			}
			focusInter = (Bounded) getInteractive();
			if (focusInter.getPaintColor() != focusInter.getHighLightColor()) {
				focusInter.mouseOver(this);
			}
			break;
		case InteractivePanel.MOUSE_RELEASED:
			if (zoomBox.isVisible()) {
				zoomBox.endZoom(evt.getX(), evt.getY());
			}
			if (iaDragable == null) {
				return;
			}
			double xxx = panel.getMouseX();
			if (evt.getX() < 1 + leftGutter) {
				xxx = panel.pixToX(1 + leftGutter);
			}
			if (evt.getX() > panel.getWidth() - 1 - rightGutter) {
				xxx = panel.pixToX(panel.getWidth() - 1 - rightGutter);
			}
			if (evt.getY() < 1 + topGutter) {
			}
			if (evt.getY() > panel.getHeight() - 1 - bottomGutter) {
			}
			@SuppressWarnings("unchecked")
			final java.util.ArrayList<Drawable> tmpArray = (java.util.ArrayList<Drawable>) drawableList.clone();
			tmpArray.remove(iaDragable);
			final java.util.Iterator<Drawable> it = tmpArray.iterator();

			while (it.hasNext()) {
				final java.lang.Object obj = it.next();
				if (obj instanceof Bounded && ((Bounded) obj).intersect(((Bounded) iaDragable).getBounds(xxx - xDrag))) {
					iaDragable.setX(xPressed);
					panel.repaint();
					return;
				}
			}
			par.update();
			break;
		}
	}

	public void updateGUI() {
		par.update();
	}

	public Bounded getFocusOwner() {
		return focusInter;
	}

	public void setFocusOwner(final Bounded _focusInter) {
		focusInter = _focusInter;
	}

	public void removeFocusOwner() {
		removeDrawable(focusInter);
		focusInter = null;
		repaint();
	}

	public void preAddPotential(final Potential pot) {
		addDrawable(pot);
		repaint();
	}

	public void addPotential(final double xI, final double vWidth, final String function, final boolean enabled,
			final boolean isMedio) {
		final Potential tmp = new Potential(xI, vWidth, function, enabled, isMedio);
		if (!checkBounds(tmp)) {
			addDrawable(tmp);
		}
	}

	public void removePotential(final Potential pot) {
		removeDrawable(pot);
		repaint();
	}

	private boolean checkBounds(final Bounded element) {
		@SuppressWarnings("unchecked")
		final java.util.ArrayList<Drawable> tmpArray = (java.util.ArrayList<Drawable>) drawableList.clone();
		tmpArray.remove(tmpArray.size() - 1);
		final java.util.Iterator<Drawable> it = tmpArray.iterator();

		while (it.hasNext()) {
			final java.lang.Object obj = it.next();
			if (obj instanceof Bounded && ((Bounded) obj).intersect(element.getBounds(0))) {
				return true;
			}
		}
		return false;
	}

	public boolean checkBounds(final Bounded element, final double transl) {
		@SuppressWarnings("unchecked")
		final java.util.ArrayList<Drawable> tmpArray = (java.util.ArrayList<Drawable>) drawableList.clone();
		tmpArray.remove(element);
		final java.util.Iterator<Drawable> it = tmpArray.iterator();

		while (it.hasNext()) {
			final java.lang.Object obj = it.next();
			if (obj instanceof Bounded && ((Bounded) obj).intersect(element.getBounds(transl))) {
				return true;
			}
		}
		return false;
	}

	public boolean checkBoundsWidth(final Bounded pot, final double dx) {
		@SuppressWarnings("unchecked")
		final java.util.ArrayList<Drawable> tmpArray = (java.util.ArrayList<Drawable>) drawableList.clone();
		tmpArray.remove(pot);
		final java.util.Iterator<Drawable> it = tmpArray.iterator();

		while (it.hasNext()) {
			final java.lang.Object obj = it.next();
			if (obj instanceof Bounded
					&& ((Bounded) obj).intersect(new java.awt.geom.Rectangle2D.Double(pot.getX(), -1, dx, 2))) {
				return true;
			}
		}
		return false;
	}

}