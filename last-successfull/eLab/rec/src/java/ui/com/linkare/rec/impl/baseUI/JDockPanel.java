/*
 * DockPanel.java
 *
 * Created on July 21, 2004, 3:44 PM
 */

package com.linkare.rec.impl.baseUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Hashtable;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class JDockPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7603814220406790623L;
	private Component lastComponent = null;
	private final Hashtable<Component, Double> lastPositionOfHidden = new Hashtable<Component, Double>();
	private int dividerSize = 4;

	/** Creates new form DockPanel */
	public JDockPanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{

		setLayout(new java.awt.BorderLayout());

	}// GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

	@Override
	public Component add(final Component comp) {
		addDockable(comp, JSplitPane.RIGHT);
		return comp;
	}

	@Override
	public Component add(final Component comp, final int index) {
		addDockable(comp, JSplitPane.RIGHT);
		return comp;
	}

	@Override
	public void add(final Component comp, final Object constraints) {
		String orientation = JSplitPane.RIGHT;
		if (constraints != null && constraints instanceof String) {
			if (constraints.equals(BorderLayout.WEST)) {
				orientation = JSplitPane.LEFT;
			} else if (constraints.equals(BorderLayout.NORTH)) {
				orientation = JSplitPane.TOP;
			} else if (constraints.equals(BorderLayout.SOUTH)) {
				orientation = JSplitPane.BOTTOM;
			} else if (constraints.equals(JSplitPane.TOP) || constraints.equals(JSplitPane.LEFT)
					|| constraints.equals(JSplitPane.RIGHT) || constraints.equals(JSplitPane.BOTTOM)) {
				orientation = (String) constraints;
			}

		}
		addDockable(comp, orientation);

	}

	public void addDockable(Component dc, final String splitPaneLocation) {
		mySelfRemoving = true;

		if (dc != null) {
			dc.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentHidden(final ComponentEvent evt) {
					// This way we lock the swing thread and it has to wait for
					// us to finish first
					// This only works inside the jdockpanel if it himself who
					// is removing, to work
					// with the other components they must do this code by their
					// own :(
					final ComponentEvent event = evt;
					try {
						final Thread t = new Thread() {
							@Override
							public void run() {
								hiddenComponent(event.getSource());
							}
						};
						t.start();
						t.join();
					} catch (final Exception ignored) {
					}
				}

				@Override
				public void componentShown(final ComponentEvent evt) {
					showedComponent(evt.getSource());
				}

			});
		}

		/*
		 * dc.addFocusListener(new java.awt.event.FocusAdapter() { public void
		 * focusGained(FocusEvent e) { System.out.println(e.getComponent());
		 * Component[] c = getComponents(); for(int i=0; i<c.length; i++) {
		 * if(c[i] != e.getComponent()) { c[i].setFocusable(false);
		 * c[i].setFocusable(true); } } } } );
		 */

		if (dc instanceof JInternalFrame) {
			final JDesktopPane temp = new JDesktopPane();
			temp.setPreferredSize(dc.getPreferredSize());
			temp.setMinimumSize(dc.getMinimumSize());
			temp.setMaximumSize(dc.getMaximumSize());
			final JInternalFrame frame = (JInternalFrame) dc;

			temp.add(frame);
			try {
				frame.setMaximum(true);
			} catch (final Exception ignored) {
			}

			dc = temp;
		}

		if (lastComponent == null) {
			super.add(dc, BorderLayout.CENTER);
		} else {
			final Container parent = lastComponent.getParent();
			if (parent == null) {
				// System.out.println("Bosta da grande... o last component nunca pode ter um parent null ...  no mínimo seria this !");
				mySelfRemoving = false;
				return;
			}

			/*
			 * boolean onTop=false; if(parent instanceof JSplitPane)
			 * if(lastComponent==((JSplitPane)parent).getTopComponent())
			 * onTop=true;
			 */

			JSplitPane splitPane = null;
			if (splitPaneLocation.equals(JSplitPane.LEFT) || splitPaneLocation.equals(JSplitPane.RIGHT)) {
				splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			} else {
				splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			}

			splitPane.setDividerSize(getDividerSize());

			splitPane.addContainerListener(new ContainerAdapter() {
				@Override
				public void componentRemoved(final ContainerEvent evt) {
					componentWasRemoved(evt);
				}
			});

			/*
			 * splitPane.addPropertyChangeListener(splitPane.
			 * DIVIDER_LOCATION_PROPERTY , new
			 * java.beans.PropertyChangeListener() { public void
			 * propertyChange(java.beans.PropertyChangeEvent evt) {
			 * splitDividerChanged(evt); } } );
			 */

			if (parent == this) {

				parent.remove(lastComponent);
				// Install current dc on it's position
				// Install old lastComponent on the other position
				if (splitPaneLocation.equals(JSplitPane.LEFT) || splitPaneLocation.equals(JSplitPane.TOP)) {
					splitPane.setTopComponent(dc);
					splitPane.setBottomComponent(lastComponent);
				} else {
					splitPane.setBottomComponent(dc);
					splitPane.setTopComponent(lastComponent);
				}
			} else {
				// Install current dc on it's position
				// Install lastcomponent.parent on the other position
				if (splitPaneLocation.equals(JSplitPane.LEFT) || splitPaneLocation.equals(JSplitPane.TOP)) {
					splitPane.setTopComponent(dc);
					splitPane.setBottomComponent(parent);
				} else {
					splitPane.setBottomComponent(dc);
					splitPane.setTopComponent(parent);
				}

			}

			super.add(splitPane);
		}

		lastComponent = dc;

		mySelfRemoving = false;
	}

	/*
	 * private void splitDividerChanged(java.beans.PropertyChangeEvent evt) {
	 * JSplitPane parentSplit = (JSplitPane)evt.getSource();
	 * 
	 * Component top = parentSplit.getTopComponent(); Component bottom =
	 * parentSplit.getBottomComponent();
	 * 
	 * if(top != null && lastPositionOfHidden.get(top) != null &&
	 * !top.isVisible()) lastPositionOfHidden.remove(top); if(bottom != null &&
	 * lastPositionOfHidden.get(bottom) != null && !bottom.isVisible())
	 * lastPositionOfHidden.remove(bottom);
	 * 
	 * int dividerLoc = Integer.parseInt(evt.getOldValue().toString());
	 * 
	 * if(parentSplit.getOrientation() == parentSplit.VERTICAL_SPLIT) { if(top
	 * != null && !top.isVisible()) lastPositionOfHidden.put(top, new
	 * Double(dividerLoc/(double)(parentSplit.getHeight()))); if(bottom != null
	 * && !bottom.isVisible()) lastPositionOfHidden.put(bottom, new Double(1 -
	 * (dividerLoc/(double)(parentSplit.getHeight())))); } else { if(top != null
	 * && !top.isVisible()) lastPositionOfHidden.put(top, new
	 * Double(dividerLoc/(double)(parentSplit.getWidth()))); if(bottom != null
	 * && !bottom.isVisible()) lastPositionOfHidden.put(bottom, new Double(1 -
	 * (dividerLoc/(double)(parentSplit.getWidth())))); } }
	 */

	private boolean mySelfRemoving = false;

	public void removeDockable(final Container parent, Component dc) {
		if (dc instanceof JInternalFrame) {
			dc = dc.getParent();
			dc.setVisible(false);
		}

		mySelfRemoving = true;
		if (parent == null) {
			// System.out.println("Bosta da grande... o component nunca pode ter um parent null ...  no mínimo seria this !");
			mySelfRemoving = false;
			return;
		}
		if (parent == this) {
			lastComponent = null;
			mySelfRemoving = false;
			return;
		}

		final Container grandParent = parent.getParent();

		if (grandParent == null) {
			// System.out.println("Bosta da grande... o parent nunca pode ter um grandparent null ...  no mínimo seria this !");
			mySelfRemoving = false;
			return;
		}

		if (grandParent == this) {
			if (parent instanceof JSplitPane) {
				Component otherComp = null;
				if (((JSplitPane) parent).getTopComponent() == null) {
					otherComp = ((JSplitPane) parent).getBottomComponent();
				} else {
					otherComp = ((JSplitPane) parent).getTopComponent();
				}

				if (otherComp != null) {
					((JSplitPane) parent).remove(otherComp);

					grandParent.remove(parent);
					super.add(otherComp, BorderLayout.CENTER);

					if (lastComponent == dc) {
						lastComponent = otherComp;
					}
				} else {
					// System.out.println("Bosta da grande... o other component não pode ser null !");
					mySelfRemoving = false;
					return;
				}
			} else {
				// System.out.println("Bosta da grande... o parent tinha que ser JSplitPane !");
				mySelfRemoving = false;
				return;
			}

			mySelfRemoving = false;
			return;
		}

		if (grandParent instanceof JSplitPane) {
			Component otherComp = null;
			if (((JSplitPane) parent).getTopComponent() == null) {
				otherComp = ((JSplitPane) parent).getBottomComponent();
			} else {
				otherComp = ((JSplitPane) parent).getTopComponent();
			}

			if (otherComp != null) {
				((JSplitPane) parent).remove(otherComp);

				boolean onTop = false;
				if (((JSplitPane) grandParent).getTopComponent() == parent) {
					onTop = true;
				}

				final int lastDividerLocation = ((JSplitPane) grandParent).getDividerLocation();

				grandParent.remove(parent);

				if (onTop) {
					((JSplitPane) grandParent).setTopComponent(otherComp);
				} else {
					((JSplitPane) grandParent).setBottomComponent(otherComp);
				}

				((JSplitPane) grandParent).setDividerLocation(lastDividerLocation);

				if (lastComponent == dc) {
					lastComponent = otherComp;
				}
			} else {
				// System.out.println("Bosta da grande... o other component não pode ser null !");
				mySelfRemoving = false;
				return;
			}
		} else {
			// System.out.println("Bosta da grande... o grandParent tinha que ser JSplitPane - as outras já testámos antes!");
			mySelfRemoving = false;
			return;
		}

		mySelfRemoving = false;
	}

	public void componentWasRemoved(final ContainerEvent evt) {

		if (evt.getSource() != null) {
			if (mySelfRemoving) {
				return;
			}
			removeDockable((Container) evt.getSource(), evt.getChild());
		}
	}

	public void hiddenComponent(Object source) {
		if (source instanceof Component) {
			if (source instanceof JInternalFrame && ((JInternalFrame) source).getParent() instanceof JDesktopPane) {
				source = ((JInternalFrame) source).getParent();
				((JDesktopPane) source).setVisible(false);
			}

			final Component c = (Component) source;

			if (c.getParent() != null && c.getParent() instanceof JSplitPane) {
				// Store the divider has a percentage of the split pane
				// dimension

				final JSplitPane parentSplit = (JSplitPane) c.getParent();

				/*
				 * if(lastPositionOfHidden.get(source) != null)
				 * lastPositionOfHidden.remove(source);
				 */
				final double dividerLocH = parentSplit.getDividerLocation() / (double) parentSplit.getHeight();
				final double dividerLocW = parentSplit.getDividerLocation() / (double) parentSplit.getWidth();

				// If a component last position was 1 or 0, when it's redrawed
				// it will get over the other component in the tab pane
				/*
				 * if(dividerLocH < 0.005 || dividerLocH > 0.98) dividerLocH =
				 * 0.5; if(dividerLocW < 0.005 || dividerLocW > 0.98)
				 * dividerLocW = 0.5;
				 */

				final Component top = parentSplit.getTopComponent();
				final Component bot = parentSplit.getBottomComponent();

				if ((bot == c && top.isVisible()) || (top == c && bot.isVisible())) {
					if (parentSplit.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
						lastPositionOfHidden.put(top, new Double(dividerLocH));
						lastPositionOfHidden.put(bot, new Double(dividerLocH));
					} else {
						lastPositionOfHidden.put(top, new Double(dividerLocW));
						lastPositionOfHidden.put(bot, new Double(dividerLocW));
					}
				}
				parentSplit.setDividerSize(0);

				if (top.isVisible() && !bot.isVisible()) {
					parentSplit.setDividerLocation(1.);
				} else if (!top.isVisible() && bot.isVisible()) {
					parentSplit.setDividerLocation(0.);
				} else {
					final Container c2 = parentSplit.getParent();
					if (c2 instanceof JSplitPane) {
						final JSplitPane grandParentSplit = (JSplitPane) c2;
						if (grandParentSplit.getTopComponent() == parentSplit) {
							grandParentSplit.setDividerLocation(0.);
						} else if (grandParentSplit.getBottomComponent() == parentSplit) {
							grandParentSplit.setDividerLocation(1.);
						}
					} else if (c2 == this) {
						setVisible(false);
					}
				}
			}
		}
	}

	public void showedComponent(Object source) {
		if (source instanceof Component) {
			Component c = (Component) source;
			Container parent = c.getParent();

			if (parent instanceof JDesktopPane) {
				((JDesktopPane) parent).setVisible(true);
				// to search in the lastPositionOfHidden.get(c)
				c = parent;
				source = c;
				//

				parent = parent.getParent();
			}

			if (parent == this) {
				// BIG SILENT NOOP... for now!
			} else if (parent != null && parent instanceof JSplitPane) {
				final JSplitPane parentSplit = (JSplitPane) parent;
				if (lastPositionOfHidden.get(c) != null) {
					final Component top = parentSplit.getTopComponent();
					final Component bot = parentSplit.getBottomComponent();

					if (top.isVisible() && !bot.isVisible()) {
						parentSplit.setDividerLocation(1.0);
						parentSplit.setDividerSize(0);
					} else if (!top.isVisible() && bot.isVisible()) {
						parentSplit.setDividerLocation(0.0);
						parentSplit.setDividerSize(0);
					} else {
						parentSplit.setDividerLocation((lastPositionOfHidden.get(source)).doubleValue());
						parentSplit.setDividerSize(getDividerSize());
						lastPositionOfHidden.remove(top);
						lastPositionOfHidden.remove(bot);
					}
				}
			}
			setVisible(true);
		}
	}

	/**
	 * Getter for property dividerSize.
	 * 
	 * @return Value of property dividerSize.
	 * 
	 */
	public int getDividerSize() {
		return dividerSize;
	}

	/**
	 * Setter for property dividerSize.
	 * 
	 * @param dividerSize New value of property dividerSize.
	 * 
	 */
	public void setDividerSize(final int dividerSize) {
		this.dividerSize = dividerSize;
	}

}
