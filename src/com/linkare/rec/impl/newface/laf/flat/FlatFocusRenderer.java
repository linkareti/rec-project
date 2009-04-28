/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.laf.flat;

import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources;
import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources.FlatLAFImageResourcesEnum;
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

/**
 *
 */
public class FlatFocusRenderer implements PropertyChangeListener {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FlatFocusRenderer.class.getName());

    private static FlatFocusRenderer instance;
    
    private KeyboardFocusManager focusManager;
    private Map<Window, FocusPainter> focusComponentMap = new HashMap<Window, FocusPainter>();
    

    public static void install() {
        if (instance == null) {
            instance = new FlatFocusRenderer();
        }
    }

    private FlatFocusRenderer() {
        focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.addPropertyChangeListener("focusOwner", this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() != null) {
            // a new component has received focus; get the window corresponding to the component
            // in order to install a FocusComponent in it
            Window activeWindow = focusManager.getActiveWindow();

            // install a FocusPainter in the window's layered pane
            // (or retrieve the instance already installed)
            FocusPainter focusPainter = getFocusPainter(activeWindow);

            if (focusPainter != null) {
                // tell the FocusPainter to paint the focus
                focusPainter.setCurrentlyFocusedComponent(focusManager.getFocusOwner());
            }
        }
    }

    private FocusPainter getFocusPainter(Window window) {
        FocusPainter focusPainter = focusComponentMap.get(window);

        if (focusPainter == null) {
            JLayeredPane layeredPane = null;
            if (window instanceof JFrame) {
                layeredPane = ((JFrame) window).getLayeredPane();
            } else if (window instanceof JDialog) {
                layeredPane = ((JDialog) window).getLayeredPane();
            }
            focusPainter = new FocusPainter(layeredPane);
            layeredPane.add(focusPainter, new Integer(250));

            focusComponentMap.put(window, focusPainter);
        }
        return focusPainter;
    }

    private static class FocusPainter extends JComponent {

        private static final BufferedImage squareFocusImg =
                FlatLAFResources.getImage(FlatLAFImageResourcesEnum.SQUARE_FOCUS);

        private static final Insets squareFocusInsets =
                FlatLAFImageResourcesEnum.SQUARE_FOCUS.getInsets();

        private Component currentlyFocusedComponent;

        public FocusPainter(final JLayeredPane windowLayeredPane) {
            setBounds(0, 0, windowLayeredPane.getWidth(), windowLayeredPane.getHeight());
            // track size window component resize
            windowLayeredPane.addComponentListener(new ComponentAdapter() {
                @Override public void componentResized(ComponentEvent e) {
                    setBounds(0, 0, windowLayeredPane.getWidth(), windowLayeredPane.getHeight());
                }
            });
        }

        public void setCurrentlyFocusedComponent(Component currentlyFocusedComponent) {
            this.currentlyFocusedComponent = currentlyFocusedComponent;
            repaint();
        }

        /**
         * Paint focus
         */
        @Override
        protected void paintComponent(Graphics g) {
            if (currentlyFocusedComponent != null) {            
            	paintSquareFocus(g);
            }
        }

        protected void paintSquareFocus(Graphics g) {
        	if (currentlyFocusedComponent instanceof JComponent) {
        		JComponent component = (JComponent) currentlyFocusedComponent;

        		if (component instanceof JButton) {
        			
        			if ( !((AbstractButton)component).isFocusPainted() ) {
        				return; // Respect component state
        			}

        			// Focus focus = component.getClass().getAnnotation(Focus.class);
        			// if (focus != null && !focus.display())
        			// return;

        			Rectangle bounds = new Rectangle(0, 0, 
        					component.getSize().width, component.getSize().height);

        			// Get bounds relative to this (FocusPainter) component
        			bounds = SwingUtilities.convertRectangle(component, bounds, this);

        			PaintUtils.tileStretchPaint(g, component, squareFocusImg, squareFocusInsets, bounds.getLocation());
        		}
        	}
        }

    }

}
