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
public class ExperimentActionBar extends javax.swing.JPanel {

    /** Creates new form ExperimentActionBar */
    public ExperimentActionBar() {
        initComponents();
    }

    public void setPlayStopButtonEnabled(boolean enabled) {
        btnPlayStop.setEnabled(enabled);
    }
    
    public boolean isPlayStopButtonEnabled() {
    	return btnPlayStop.isEnabled();
    }

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPlayStop = new javax.swing.JButton();
        checkAutoPlay = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(32767, 40));
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(ExperimentActionBar.class, this);
        btnPlayStop.setAction(actionMap.get("play")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ExperimentActionBar.class);
        btnPlayStop.setIcon(resourceMap.getIcon("btnPlayStop.icon")); // NOI18N
        btnPlayStop.setText(resourceMap.getString("btnPlayStop.text")); // NOI18N
        btnPlayStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnPlayStop.setBorderPainted(false);
        btnPlayStop.setEnabled(false);
        btnPlayStop.setName("btnPlayStop"); // NOI18N

        checkAutoPlay.setText(resourceMap.getString("checkAutoPlay.text")); // NOI18N
        checkAutoPlay.setName("checkAutoPlay"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnPlayStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkAutoPlay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPlayStop)
            .addComponent(checkAutoPlay, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlayStop;
    private javax.swing.JCheckBox checkAutoPlay;
    // End of variables declaration//GEN-END:variables

}
