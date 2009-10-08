/*
 * ApparatusComboBoxModel.java created on Apr 29, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.config.Apparatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusComboBoxModel extends DefaultComboBoxModel {

    /** Holds the apparatus location key -> apparatus mapping */
    protected Map<String, Apparatus> apparatusMap = new HashMap<String, Apparatus>();

    public ApparatusComboBoxModel() {
	super();
    }

    public ApparatusComboBoxModel(List<Apparatus> apparatusList) {
	super((Apparatus[]) apparatusList.toArray(new Apparatus[apparatusList.size()]));

	for (Apparatus apparatus : apparatusList) {
	    apparatusMap.put(apparatus.getLocation(), apparatus);
	}
    }

    public Apparatus getApparatus(String locationKey) {
	return apparatusMap.get(locationKey);
    }

    public void fireContentsChanged(Object source) {
	super.fireContentsChanged(source, 0, getSize());
    }

    public void setAllApparatusEnabled(boolean enabled) {
	for (Entry<String, Apparatus> entry : apparatusMap.entrySet()) {
	    entry.getValue().setEnabled(enabled);
	}
	fireContentsChanged(this);
    }

}
