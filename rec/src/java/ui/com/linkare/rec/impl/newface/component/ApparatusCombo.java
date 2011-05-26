/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import java.util.logging.Logger;

import javax.swing.JComboBox;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusCombo extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3156287676979725717L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ApparatusCombo.class.getName());

	public ApparatusCombo() {
		super();
		setRenderer(new ApparatusRenderer());
	}

}
