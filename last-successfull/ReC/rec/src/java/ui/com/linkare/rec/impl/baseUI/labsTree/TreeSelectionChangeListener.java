/*
 * ApparatusSelectionChangeListener.java
 *
 * Created on 19/07/04 at 18:09 pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 * 
 * @author André
 */
public interface TreeSelectionChangeListener extends java.util.EventListener {
	void apparatusSelectionChange(ApparatusSelectionEvent selectevent);

	void defaultConfigSelectionChange(DefaultConfigSelectionEvent selectevent);

	void displaySelectionChange(DisplaySelectionEvent selectevent);

	void webResourceSelectionChange(WebResourceSelectionEvent selectevent);

	void labSelectionChange(LabSelectionEvent selectevent);
}
