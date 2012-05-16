/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources;
import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources.FlatLAFImageResourcesEnum;

/**
 *
 */
public class FlatFocusRenderer implements PropertyChangeListener {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FlatFocusRenderer.class.getName());

	private static FlatFocusRenderer instance;

	private final KeyboardFocusManager focusManager;
	private final Map<Window, FocusPainter> focusComponentMap = new HashMap<Window, FocusPainter>();

	public static void install() {
		if (FlatFocusRenderer.instance == null) {
			FlatFocusRenderer.instance = new FlatFocusRenderer();
		}
	}

	private FlatFocusRenderer() {
		focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		focusManager.addPropertyChangeListener("focusOwner", this);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getNewValue() != null) {
			// a new component has received focus; get the window corresponding
			// to the component
			// in order to install a FocusComponent in it
			final Window activeWindow = focusManager.getActiveWindow();

			// install a FocusPainter in the window's layered pane
			// (or retrieve the instance already installed)
			final FocusPainter focusPainter = getFocusPainter(activeWindow);

			if (focusPainter != null) {
				// tell the FocusPainter to paint the focus
				focusPainter.setCurrentlyFocusedComponent(focusManager.getFocusOwner());
			}
		}
	}

	private FocusPainter getFocusPainter(final Window window) {
		FocusPainter focusPainter = focusComponentMap.get(window);

		if (focusPainter == null) {
			JLayeredPane layeredPane = null;
			if (window instanceof JFrame) {
				layeredPane = ((JFrame) window).getLayeredPane();
			} else if (window instanceof JDialog) {
				layeredPane = ((JDialog) window).getLayeredPane();
			}
			focusPainter = new FocusPainter(layeredPane);
			layeredPane.add(focusPainter, Integer.valueOf(250));

			focusComponentMap.put(window, focusPainter);
		}
		return focusPainter;
	}

	private static class FocusPainter extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5636961149080993539L;

		private static final BufferedImage squareFocusImg = FlatLAFResources
				.getImage(FlatLAFImageResourcesEnum.SQUARE_FOCUS);

		private static final Insets squareFocusInsets = FlatLAFImageResourcesEnum.SQUARE_FOCUS.getInsets();

		private Component currentlyFocusedComponent;

		public FocusPainter(final JLayeredPane windowLayeredPane) {
			setBounds(0, 0, windowLayeredPane.getWidth(), windowLayeredPane.getHeight());
			// track size window component resize
			windowLayeredPane.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(final ComponentEvent e) {
					setBounds(0, 0, windowLayeredPane.getWidth(), windowLayeredPane.getHeight());
				}
			});
		}

		public void setCurrentlyFocusedComponent(final Component currentlyFocusedComponent) {
			this.currentlyFocusedComponent = currentlyFocusedComponent;
			repaint();
		}

		/**
		 * Paint focus
		 */
		@Override
		protected void paintComponent(final Graphics g) {
			if (currentlyFocusedComponent != null) {
				paintSquareFocus(g);
			}
		}

		protected void paintSquareFocus(final Graphics g) {
			if (currentlyFocusedComponent instanceof JComponent) {
				final JComponent component = (JComponent) currentlyFocusedComponent;

				if (component instanceof JButton) {

					if (!((AbstractButton) component).isFocusPainted()) {
						return; // Respect component state
					}

					// Focus focus =
					// component.getClass().getAnnotation(Focus.class);
					// if (focus != null && !focus.display())
					// return;

					Rectangle bounds = new Rectangle(0, 0, component.getSize().width, component.getSize().height);

					// Get bounds relative to this (FocusPainter) component
					bounds = SwingUtilities.convertRectangle(component, bounds, this);

					PaintUtils.tileStretchPaint(g, component, FocusPainter.squareFocusImg,
							FocusPainter.squareFocusInsets, bounds.getLocation());
				}
			}
		}

	}

}
