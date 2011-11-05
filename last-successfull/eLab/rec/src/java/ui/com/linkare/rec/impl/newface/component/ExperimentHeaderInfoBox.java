/*
 * ExperimentHeaderInfoBox.java created on Mar 3, 2010
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.util.logging.Logger;

/**
 * 
 * @author hfernandes
 */
public class ExperimentHeaderInfoBox extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8076617391211166322L;

	private static final Logger log = Logger.getLogger(ExperimentHeaderInfoBox.class.getName());

	public static final String CLOSE_ME = "close me";

	/** Creates new form ExperimentHeaderInfoBox */
	public ExperimentHeaderInfoBox() {
		initComponents();
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

		scrollInfo = new javax.swing.JScrollPane();
		txtInfo = new javax.swing.JTextArea();
		btnClose = new javax.swing.JButton();

		setName("Form"); // NOI18N

		scrollInfo.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollInfo.setName("scrollInfo"); // NOI18N

		txtInfo.setColumns(20);
		txtInfo.setEditable(false);
		txtInfo.setLineWrap(true);
		txtInfo.setRows(5);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setName("txtInfo"); // NOI18N
		scrollInfo.setViewportView(txtInfo);

		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ExperimentHeaderInfoBox.class);
		btnClose.setIcon(resourceMap.getIcon("btnClose.icon")); // NOI18N
		btnClose.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		btnClose.setFocusPainted(false);
		btnClose.setName("btnClose"); // NOI18N
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCloseActionPerformed(evt);
			}
		});

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(scrollInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 530,
												Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(btnClose)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scrollInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
						.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void btnCloseActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCloseActionPerformed
		firePropertyChange(ExperimentHeaderInfoBox.CLOSE_ME, false, true);
	}// GEN-LAST:event_btnCloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnClose;
	private javax.swing.JScrollPane scrollInfo;
	private javax.swing.JTextArea txtInfo;

	// End of variables declaration//GEN-END:variables

	public void setText(final String info) {
		txtInfo.setText(info);
	}

	public String getText() {
		return txtInfo.getText();
	}

}
