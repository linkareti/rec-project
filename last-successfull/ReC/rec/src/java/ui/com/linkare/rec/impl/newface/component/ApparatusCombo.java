/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

import javax.swing.JComboBox;

import com.linkare.rec.impl.newface.ReCApplication;
import com.linkare.rec.web.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusCombo extends JComboBox {

	private Color defaultListBackgroundColor;
	private Color defaultVirtualExperimentListBackgroundColor;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3156287676979725717L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ApparatusCombo.class.getName());

	public ApparatusCombo() {
		super();
		defaultListBackgroundColor = this.getBackground();
		defaultVirtualExperimentListBackgroundColor = ReCApplication.getApplication().getContext().getResourceMap()
				.getColor("virtualExperiments.background");
		setRenderer(new ApparatusRenderer());
		this.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem() != null && e.getItem() instanceof Apparatus) {
					ApparatusCombo.this.setBackground(defaultVirtualExperimentListBackgroundColor);
				} else {
					ApparatusCombo.this.setBackground(defaultListBackgroundColor);
				}
			}
		});
	}

}
