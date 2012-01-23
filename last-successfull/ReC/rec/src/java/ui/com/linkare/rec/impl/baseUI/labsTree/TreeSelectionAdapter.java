/*
 * TreeSelectionAdapter.java
 *
 * Created on July 19, 2004, 6:14 PM
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public abstract class TreeSelectionAdapter implements TreeSelectionChangeListener {

	@Override
	public void apparatusSelectionChange(final ApparatusSelectionEvent selectevent) {
	}

	@Override
	public void defaultConfigSelectionChange(final DefaultConfigSelectionEvent selectevent) {
	}

	@Override
	public void displaySelectionChange(final DisplaySelectionEvent selectevent) {
	}

	@Override
	public void labSelectionChange(final LabSelectionEvent selectevent) {
	}

	@Override
	public void webResourceSelectionChange(final WebResourceSelectionEvent selectevent) {
	}

}
