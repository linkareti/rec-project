/*
 * DefaultLabApparatusTreeModel.java
 *
 * Created on 08 May 2003, 23:18
 */

package com.linkare.rec.impl.client.apparatus;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class DefaultLabApparatusTreeModel extends javax.swing.tree.DefaultTreeModel implements
		com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7299653052710611555L;

	private static final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Laboratory", true);

	/** Holds value of property apparatusListSource. */
	private ApparatusListSource apparatusListSource;

	/** Creates a new instance of DefaultLabApparatusTreeModel */
	public DefaultLabApparatusTreeModel() {
		super(DefaultLabApparatusTreeModel.rootNode);
	}

	/**
	 * Getter for property apparatusListSource.
	 * 
	 * @return Value of property apparatusListSource.
	 */
	public ApparatusListSource getApparatusListSource() {
		return apparatusListSource;
	}

	/**
	 * Setter for property apparatusListSource.
	 * 
	 * @param apparatusListSource New value of property apparatusListSource.
	 */
	public void setApparatusListSource(final ApparatusListSource apparatusListSource) {
		if (this.apparatusListSource != null) {
			this.apparatusListSource.removeApparatusListSourceListener(this);
		}
		this.apparatusListSource = apparatusListSource;
		if (apparatusListSource != null) {
			apparatusListSource.addApparatusListSourceListener(this);
		}
	}

	@Override
	public void apparatusListChanged(final ApparatusListChangeEvent newApparatusListEvt) {
		DefaultLabApparatusTreeModel.rootNode.removeAllChildren();

		// this.reload();

		if (newApparatusListEvt == null) {
			return;
		}

		final Apparatus[] newApparatusList = newApparatusListEvt.getApparatus();

		if (newApparatusList == null) {
			return;
		}

		for (final Apparatus apparatus : newApparatusList) {
			if (apparatus != null) {
				final DefaultMutableTreeNode node = new DefaultMutableTreeNode(apparatus, false);
				DefaultLabApparatusTreeModel.rootNode.add(node);
			}
		}

		this.reload();
	}

}
