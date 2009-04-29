/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.JComboBox;

/**
 *
 * @author Henrique Fernandes
 */
public class ApparatusCombo extends JComboBox {

	public ApparatusCombo() {
		super();
		setRenderer(new ApparatusRenderer());
	}
	
}
