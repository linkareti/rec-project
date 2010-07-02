/*
 * SimpleLoginBox.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.Icon;

import org.jdesktop.application.Action;

import com.linkare.rec.impl.newface.ReCApplication;
import com.linkare.rec.impl.newface.component.ProgressCicle.State;
import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;

/**
 * 
 * @author hfernandes
 */
public class SimpleLoginBox extends GradientPane {

	private static final Logger log = Logger.getLogger(SimpleLoginBox.class.getName());

	private ReCApplication recApplication = ReCApplication.getApplication();

	/** Creates new form SimpleLoginBox */
	public SimpleLoginBox() {
		super();
		setContainer(container);
		initComponents();
		txtUsername.setText(System.getProperty("user.name"));
	}

	public void setLoginProgressVisible(boolean visible) {
		progressCicle.setVisible(visible);
		progressCicle.setState(visible ? State.RUNNING : State.IDLE);
	}

	public void setIdleIcon(Icon idleIcon) {
		this.progressCicle.setIdleIcon(idleIcon);
	}

	public void setBusyIcons(Icon[] busyIcons) {
		this.progressCicle.setBusyIcons(busyIcons);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		labelUsername = new javax.swing.JLabel();
		txtUsername = new javax.swing.JTextField();
		progressCicle = new com.linkare.rec.impl.newface.component.ProgressCicle();
		flatButton1 = new com.linkare.rec.impl.newface.component.FlatButton();

		setName("Form"); // NOI18N
		setPreferredSize(new java.awt.Dimension(360, 164));

		labelUsername.setFont(labelUsername.getFont().deriveFont(labelUsername.getFont().getStyle() | java.awt.Font.BOLD));
		labelUsername.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
				com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(SimpleLoginBox.class);
		labelUsername.setText(resourceMap.getString("labelUsername.text")); // NOI18N
		labelUsername.setName("labelUsername"); // NOI18N

		txtUsername.setText(resourceMap.getString("txtUsername.text")); // NOI18N
		javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(
				com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(SimpleLoginBox.class, this);
		txtUsername.setAction(actionMap.get("next")); // NOI18N
		txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK),
				javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
		txtUsername.setName("txtUsername"); // NOI18N
		txtUsername.setPreferredSize(new java.awt.Dimension(215, 28));

		progressCicle.setText(resourceMap.getString("progressCicle.text")); // NOI18N
		progressCicle.setIdleIcon(resourceMap.getIcon("progressCicle.idleIcon")); // NOI18N
		progressCicle.setName("progressCicle"); // NOI18N

		flatButton1.setAction(actionMap.get("next")); // NOI18N
		flatButton1.setFont(flatButton1.getFont().deriveFont(flatButton1.getFont().getStyle() | java.awt.Font.BOLD));
		flatButton1.setName("flatButton1"); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(74, 74, 74).addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								layout.createSequentialGroup().addComponent(flatButton1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(progressCicle,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(labelUsername).addComponent(txtUsername,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(71, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(40, 40, 40).addComponent(labelUsername).addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtUsername,
						javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(progressCicle,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(flatButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(43, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	@Action
	public void next() {
		setLoginProgressVisible(true);

		recApplication.setUserInfo(getUsername());

		// Connect
		recApplication.connect();
	}

	public String getUsername() {
		return txtUsername.getText();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.newface.component.FlatButton flatButton1;
	private javax.swing.JLabel labelUsername;
	private com.linkare.rec.impl.newface.component.ProgressCicle progressCicle;
	private javax.swing.JTextField txtUsername;
	// End of variables declaration//GEN-END:variables

}
