/*
 * ApparatusComboBoxModel.java created on Apr 29, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;

import com.linkare.rec.am.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusComboBoxModel extends DefaultComboBoxModel {

	/** Generated UID */
	private static final long serialVersionUID = -4195398474801265740L;

	/** Holds the apparatus location key -> apparatus mapping */
	protected Map<String, Apparatus> apparatusMap = new HashMap<String, Apparatus>();

	public ApparatusComboBoxModel() {
		super();
	}

	public ApparatusComboBoxModel(final List<Apparatus> apparatusList) {
		super(apparatusList.toArray(new Apparatus[apparatusList.size()]));

		for (final Apparatus apparatus : apparatusList) {
			apparatusMap.put(apparatus.getLocation(), apparatus);
		}
	}

	public void addApparatus(final Apparatus apparatus) {
		apparatusMap.put(apparatus.getLocation(), apparatus);

		super.addElement(apparatus);
	}

	public void addApparatusList(final List<Apparatus> apparatusList) {
		for (final Apparatus apparatus : apparatusList) {
			addApparatus(apparatus);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addElement(final Object anObject) {
		if (anObject instanceof Apparatus) {
			addApparatus((Apparatus) anObject);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllElements() {
		apparatusMap.clear();
		super.removeAllElements();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeElement(final Object anObject) {
		if (anObject instanceof Apparatus) {
			final Apparatus apparatus = (Apparatus) anObject;
			apparatusMap.remove(apparatus.getLocation());

			super.removeElement(anObject);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeElementAt(final int index) {
		final Apparatus apparatus = (Apparatus) getElementAt(index);
		if (apparatus != null) {
			apparatusMap.remove(apparatus.getLocation());

			super.removeElementAt(index);
		}
	}

	public Apparatus getApparatus(final String locationKey) {
		return apparatusMap.get(locationKey);
	}

	public Set<String> getApparatusHardwareUniqueID() {
		return apparatusMap.keySet();
	}

	public void fireContentsChanged(final Object source) {
		super.fireContentsChanged(source, 0, getSize());
	}

	public void setAllApparatusEnabled(final boolean enabled) {
		for (final Entry<String, Apparatus> entry : apparatusMap.entrySet()) {
			entry.getValue().setEnabled(enabled);
		}
		fireContentsChanged(this);
	}

}
