/*
 * ExperimentActionBar.java created on May 20, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.io.File;
import java.util.logging.Logger;

/**
 * 
 * @author hfernandes
 */
public class ResultsActionBar extends javax.swing.JPanel {

	private static final Logger log = Logger.getLogger(ResultsActionBar.class.getName());
	
	/** Creates new form ExperimentActionBar */
	public ResultsActionBar() {
		initComponents();
	}

	public void setPlayStopButtonEnabled(boolean enabled) {
		btnPlayStop.setEnabled(enabled);
	}

        public void setPlayStopButtonVisible(boolean visible) {
		btnPlayStop.setVisible(visible);
	}

	public boolean isPlayStopButtonEnabled() {
		return btnPlayStop.isEnabled();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPlayStop = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnSelectAll = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(32767, 40));
        setName("Form"); // NOI18N
        setOpaque(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ResultsActionBar.class);
        btnPlayStop.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
        btnPlayStop.setIcon(resourceMap.getIcon("btnPlayStop.icon")); // NOI18N
        btnPlayStop.setText(resourceMap.getString("btnPlayStop.text")); // NOI18N
        btnPlayStop.setToolTipText(resourceMap.getString("btnPlayStop.toolTipText")); // NOI18N
        btnPlayStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnPlayStop.setBorderPainted(false);
        btnPlayStop.setName("btnPlayStop"); // NOI18N

        btnPrint.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
        btnPrint.setIcon(resourceMap.getIcon("btnPrint.icon")); // NOI18N
        btnPrint.setToolTipText(resourceMap.getString("btnPrint.toolTipText")); // NOI18N
        btnPrint.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnPrint.setBorderPainted(false);
        btnPrint.setName("btnPrint"); // NOI18N
        btnPrint.setPressedIcon(resourceMap.getIcon("btnPrint.pressedIcon")); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnSave.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setToolTipText(resourceMap.getString("btnSave.toolTipText")); // NOI18N
        btnSave.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnSave.setBorderPainted(false);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPressedIcon(resourceMap.getIcon("btnSave.pressedIcon")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCopy.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
        btnCopy.setIcon(resourceMap.getIcon("btnCopy.icon")); // NOI18N
        btnCopy.setToolTipText(resourceMap.getString("btnCopy.toolTipText")); // NOI18N
        btnCopy.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnCopy.setBorderPainted(false);
        btnCopy.setName("btnCopy"); // NOI18N
        btnCopy.setPressedIcon(resourceMap.getIcon("btnCopy.pressedIcon")); // NOI18N
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        btnSelectAll.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
        btnSelectAll.setIcon(resourceMap.getIcon("btnSelectAll.icon")); // NOI18N
        btnSelectAll.setToolTipText(resourceMap.getString("btnSelectAll.toolTipText")); // NOI18N
        btnSelectAll.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnSelectAll.setBorderPainted(false);
        btnSelectAll.setName("btnSelectAll"); // NOI18N
        btnSelectAll.setPressedIcon(resourceMap.getIcon("btnSelectAll.pressedIcon")); // NOI18N
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnPlayStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCopy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelectAll)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(btnPlayStop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(btnCopy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(btnSelectAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
	}//GEN-LAST:event_btnPrintActionPerformed

	// TODO The folowing actions must be implemented. Check the experiments code.
	// @see GDataTable

	private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
	}//GEN-LAST:event_btnSaveActionPerformed

	public void saveTable(File saveFile, boolean append) {
	}

	private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
	}//GEN-LAST:event_btnCopyActionPerformed

	private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
	}//GEN-LAST:event_btnSelectAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnPlayStop;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelectAll;
    // End of variables declaration//GEN-END:variables

}
