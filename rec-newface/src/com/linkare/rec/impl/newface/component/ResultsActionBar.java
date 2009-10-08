/*
 * ExperimentActionBar.java created on May 20, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * 
 * @author hfernandes
 */
public class ResultsActionBar extends javax.swing.JPanel {

    /** Creates new form ExperimentActionBar */
    public ResultsActionBar() {
	initComponents();
    }

    public void setPlayStopButtonEnabled(boolean enabled) {
	btnPlayStop.setEnabled(enabled);
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
	btnPlayStop1 = new javax.swing.JButton();
	btnPlayStop2 = new javax.swing.JButton();
	btnPlayStop3 = new javax.swing.JButton();
	btnPlayStop4 = new javax.swing.JButton();

	setMaximumSize(new java.awt.Dimension(32767, 40));
	setName("Form"); // NOI18N

	org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
		com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ResultsActionBar.class);
	btnPlayStop.setIcon(resourceMap.getIcon("btnPlayStop.icon")); // NOI18N
	btnPlayStop.setText(resourceMap.getString("btnPlayStop.text")); // NOI18N
	btnPlayStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
	btnPlayStop.setBorderPainted(false);
	btnPlayStop.setName("btnPlayStop"); // NOI18N

	btnPlayStop1.setIcon(resourceMap.getIcon("btnPlayStop1.icon")); // NOI18N
	btnPlayStop1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
	btnPlayStop1.setBorderPainted(false);
	btnPlayStop1.setName("btnPlayStop1"); // NOI18N

	btnPlayStop2.setIcon(resourceMap.getIcon("btnPlayStop2.icon")); // NOI18N
	btnPlayStop2.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
	btnPlayStop2.setBorderPainted(false);
	btnPlayStop2.setName("btnPlayStop2"); // NOI18N

	btnPlayStop3.setIcon(resourceMap.getIcon("btnPlayStop3.icon")); // NOI18N
	btnPlayStop3.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
	btnPlayStop3.setBorderPainted(false);
	btnPlayStop3.setName("btnPlayStop3"); // NOI18N

	btnPlayStop4.setIcon(resourceMap.getIcon("btnPlayStop4.icon")); // NOI18N
	btnPlayStop4.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
	btnPlayStop4.setBorderPainted(false);
	btnPlayStop4.setName("btnPlayStop4"); // NOI18N

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	this.setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addComponent(btnPlayStop).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnPlayStop1).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnPlayStop2).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnPlayStop3).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnPlayStop4).addContainerGap(
			javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		btnPlayStop2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE).addComponent(btnPlayStop1,
		javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
		.addComponent(btnPlayStop, javax.swing.GroupLayout.Alignment.TRAILING,
			javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addComponent(btnPlayStop3, javax.swing.GroupLayout.Alignment.TRAILING,
			javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE).addComponent(btnPlayStop4,
			javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34,
			Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlayStop;
    private javax.swing.JButton btnPlayStop1;
    private javax.swing.JButton btnPlayStop2;
    private javax.swing.JButton btnPlayStop3;
    private javax.swing.JButton btnPlayStop4;
    // End of variables declaration//GEN-END:variables

}
