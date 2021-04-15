/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatBox.java
 *
 * Created on 20/Abr/2009, 11:44:28
 */

package com.linkare.rec.impl.newface.component;

import java.util.ResourceBundle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

import com.linkare.rec.impl.newface.AboutBoxDialog;

import javax.swing.GroupLayout;

/**
 * 
 * @author Pedro Matrola
 */
public class CompanyLogo extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2937212386156434551L;

	/** Creates new form CompanyLogo */
	public CompanyLogo() {
		initComponents();
	}
	
	public JLabel getErasmus_image() {
		return erasmus_image;
	}
	
	public JLabel getWpa_image() {
		return wpa_image;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setName("Form");
		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(CompanyLogo.class);
		
		erasmus_image = new JLabel("");
		wpa_image = new JLabel("");
		//wpa_image.setIcon(resourceMap.getIcon("wpa_image.icon"));
		// FIXME - Images should come from resourceMap instead of classpath
		erasmus_image.setIcon(new ImageIcon(CompanyLogo.class.getResource("/com/linkare/rec/impl/newface/resources/erasmus_plus_custom.png")));
		wpa_image.setIcon(new ImageIcon(CompanyLogo.class.getResource("/com/linkare/rec/impl/newface/resources/world_pendulum_alliance_custom2.png")));

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addComponent(erasmus_image)
				.addComponent(wpa_image)
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addComponent(erasmus_image)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wpa_image))
		);

		//setBackground(Color.RED);

		// setLayout(layout);
		// layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		// 		layout.createSequentialGroup()
		// 			.addContainerGap()
		// 			.addGroup(
		// 					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// 							.addComponent(erasmus_image, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
		// 							.addComponent(wpa_image))
		// 							.addContainerGap()));
									
		// layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		// 			layout.createSequentialGroup()
		// 				.addComponent(wpa_image, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
		// 				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// 				.addComponent(erasmus_image)
		// 				.addContainerGap()));

	}// </editor-fold>//GEN-END:initComponents
	
	private JLabel erasmus_image;
	private JLabel wpa_image;
	// End of variables declaration//GEN-END:variables
}