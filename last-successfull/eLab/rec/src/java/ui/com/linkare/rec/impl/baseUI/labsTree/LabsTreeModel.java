/*
 * LabsTreeModel.java
 *
 * Created on 22 de Janeiro de 2004, 1:08
 */

package com.linkare.rec.impl.baseUI.labsTree;

import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.DisplayNode;
import com.linkare.rec.impl.baseUI.config.Lab;
import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.baseUI.config.WebResource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class LabsTreeModel extends DefaultTreeModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8281716289400134678L;

	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	// The tree root, static so it can be accessed by outside classes
	public static DefaultMutableTreeNode treeRoot = null;

	/** Creates a new instance of LabsTreeModel 
	 * @param userObject 
	 * @param allowsChildren */
	public LabsTreeModel(final Object userObject, final boolean allowsChildren) {
		super(new DefaultMutableTreeNode(userObject), true);
	}

	public void populateTree(final ReCBaseUIConfig config) {
		final DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();
		LabsTreeModel.treeRoot = root;
		DefaultMutableTreeNode labroot = (DefaultMutableTreeNode) getRoot();
		DefaultMutableTreeNode approot = (DefaultMutableTreeNode) getRoot();
		DefaultMutableTreeNode node = null;
		WebResource[] wr = config.getWebResource();

		for (final WebResource element : wr) {
			element.setEnabled(true);
			node = new DefaultMutableTreeNode(element);
			node.setAllowsChildren(false);
			root.add(node);
		}

		final Lab[] labs = config.getLab();
		for (final Lab lab : labs) {
			labroot = new DefaultMutableTreeNode(lab);
			root.add(labroot);
			addPropChangeListener(lab);

			wr = lab.getWebResource();
			for (final WebResource element : wr) {
				element.setEnabled(true);
				labroot.add(new DefaultMutableTreeNode(element));
			}

			final Apparatus[] apparatus = lab.getApparatus();
			for (final Apparatus apparatu : apparatus) {
				addPropChangeListener(apparatu);

				approot = new DefaultMutableTreeNode(apparatu);
				labroot.add(approot);

				wr = apparatu.getWebResource();
				for (final WebResource element : wr) {
					element.setEnabled(true);
					node = new DefaultMutableTreeNode(element);
					node.setAllowsChildren(false);
					approot.add(node);
				}

				final DefaultAcquisitionConfig[] configs = apparatu.getDefaultAcquisitionConfig();
				for (final DefaultAcquisitionConfig config2 : configs) {
					addPropChangeListener(config2);
					node = new DefaultMutableTreeNode(config2);
					node.setAllowsChildren(false);
					approot.add(node);
				}

				final Display[] displays = apparatu.getDisplay();
				for (final Display display : displays) {
					if (display.getOfflineCapable()) {
						display.setEnabled(true);
					}
					addPropChangeListener(display);
					node = new DefaultMutableTreeNode(display);
					node.setAllowsChildren(false);
					approot.add(node);
				}
			}
		}

		reload();
	}

	public Apparatus getApparatus(final String uniqueID) {
		final java.util.Enumeration<DefaultMutableTreeNode> allChild = ((DefaultMutableTreeNode) root).breadthFirstEnumeration();
		while (allChild.hasMoreElements()) {
			final Object currentNode = allChild.nextElement().getUserObject();
			if (currentNode instanceof Apparatus) {
				if (((Apparatus) currentNode).getLocation().equals(uniqueID)) {
					return (Apparatus) currentNode;
				}
			}
		}
		return null;
	}

	public void addPropChangeListener(final DisplayNode node) {
		// needed to change the default add method name, because, lab,
		// apparatus, etc. were overriding the addPropertyChageListener
		node.addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener() {
			@Override
			public void propertyChange(final java.beans.PropertyChangeEvent evt) {
				displayNodePropertyChanged(evt);
			}
		});

	}

	private void displayNodePropertyChanged(final java.beans.PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("enable")) {
			firePropertyChangeListenerPropertyChange(new java.beans.PropertyChangeEvent(this, "reload", Boolean.TRUE,
					Boolean.TRUE));
		}
	}

	/**
	 * Registers PropertyChangeListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addPropertyChangeListener(final java.beans.PropertyChangeListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(java.beans.PropertyChangeListener.class, listener);
	}

	/**
	 * Removes PropertyChangeListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removePropertyChangeListener(final java.beans.PropertyChangeListener listener) {
		listenerList.remove(java.beans.PropertyChangeListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void firePropertyChangeListenerPropertyChange(final java.beans.PropertyChangeEvent event) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == java.beans.PropertyChangeListener.class) {
				((java.beans.PropertyChangeListener) listeners[i + 1]).propertyChange(event);
			}
		}
	}

}
