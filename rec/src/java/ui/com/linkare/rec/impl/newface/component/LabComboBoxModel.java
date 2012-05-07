/* 
 * LabComboBoxModel.java created on 6 Oct 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.linkare.rec.am.config.Lab;

/**
 * 
 * @author npadriano
 */
public class LabComboBoxModel extends DefaultComboBoxModel {

	/** Generated UID */
	private static final long serialVersionUID = -6839802903970136273L;

	/**
	 * Lab list for the lab login combo
	 */
	protected List<Lab> labList = new ArrayList<Lab>();

	/**
	 * Creates the <code>LabComboBoxModel</code>.
	 */
	public LabComboBoxModel() {
		super();
	}

	/**
	 * Creates the <code>LabComboBoxModel</code>.
	 * 
	 * @param labList
	 */
	public LabComboBoxModel(final List<Lab> labList) {
		super();
		this.labList = labList;
	}

	/**
	 * @param index
	 * @return
	 */
	public Lab getLab(final int index) {
		return labList.get(index);
	}

	/**
	 * @param lab
	 */
	public void addLab(final Lab lab) {
		labList.add(lab);
		addElement(lab);
	}

	/**
	 * @param labList
	 */
	public void addLabList(final List<Lab> labList) {
		for (final Lab lab : labList) {
			addLab(lab);
		}
	}

}
