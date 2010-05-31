/*
 * ExpHistoryEntryPane.java created on Feb 21, 2010
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.linkare.rec.impl.newface.ReCApplication;

/**
 * 
 * @author Henrique Fernandes
 */
public class ExpHistoryEntryPane extends JPanel {

	private ExperimentHistoryUINode expHistoryUINode;

	private JComponent parentContainer;

	private static final Color LOCALLY_OWNED_BORDER_COLOR = new Color(252, 246, 140);

	private static final Border LOCALLY_OWNED_BORDER = BorderFactory.createLineBorder(LOCALLY_OWNED_BORDER_COLOR, 3);

	private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();

	/** Creates new form ExpHistoryEntryPane */
    public ExpHistoryEntryPane() {
        initComponents();
    }

	public ExpHistoryEntryPane(ExperimentHistoryUINode expHist, JComponent container) {
		this.expHistoryUINode = expHist;
		initComponents();
		updateInterfaceFromModel();
		this.parentContainer = container;
	}

	private void updateInterfaceFromModel() {
		lblExperimentName.setText(expHistoryUINode.getApparatusName());
		lblImage.setIcon(expHistoryUINode.getApparatusIcon());
		lblImage.setToolTipText(ReCApplication.getInstance().getContext().getResourceMap(ExpHistoryEntryPane.class).getString(
				"lblImage.toolTipText", expHistoryUINode.getOwnerUserName()));
		lblImage.setBorder(expHistoryUINode.isLocallyOwned() ? LOCALLY_OWNED_BORDER : EMPTY_BORDER);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        lblExperimentName = new javax.swing.JLabel();
        lblLinkOpen = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblLinkConfig = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblLinkRemove = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ExpHistoryEntryPane.class);
        setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        setMaximumSize(new java.awt.Dimension(231, 82));
        setName("Form"); // NOI18N

        lblImage.setText(resourceMap.getString("lblImage.text")); // NOI18N
        lblImage.setToolTipText(resourceMap.getString("lblImage.toolTipText")); // NOI18N
        lblImage.setName("lblImage"); // NOI18N

        lblExperimentName.setFont(lblExperimentName.getFont().deriveFont(lblExperimentName.getFont().getStyle() | java.awt.Font.BOLD, 11));
        lblExperimentName.setText(resourceMap.getString("lblExperimentName.text")); // NOI18N
        lblExperimentName.setName("lblExperimentName"); // NOI18N

        lblLinkOpen.setFont(lblLinkOpen.getFont().deriveFont((float)11));
        lblLinkOpen.setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        lblLinkOpen.setText(resourceMap.getString("lblLinkOpen.text")); // NOI18N
        lblLinkOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLinkOpen.setName("lblLinkOpen"); // NOI18N
        lblLinkOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLinkOpenMouseClicked(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        lblLinkConfig.setFont(lblLinkConfig.getFont().deriveFont((float)11));
        lblLinkConfig.setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        lblLinkConfig.setText(resourceMap.getString("lblLinkConfig.text")); // NOI18N
        lblLinkConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLinkConfig.setName("lblLinkConfig"); // NOI18N
        lblLinkConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLinkConfigMouseClicked(evt);
            }
        });

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        lblLinkRemove.setFont(lblLinkRemove.getFont().deriveFont((float)11));
        lblLinkRemove.setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        lblLinkRemove.setText(resourceMap.getString("lblLinkRemove.text")); // NOI18N
        lblLinkRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLinkRemove.setName("lblLinkRemove"); // NOI18N
        lblLinkRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLinkRemoveMouseClicked(evt);
            }
        });

        separator.setForeground(resourceMap.getColor("separator.foreground")); // NOI18N
        separator.setName("separator"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblImage)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLinkOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel4)
                                .addGap(8, 8, 8)
                                .addComponent(lblLinkConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel7)
                                .addGap(8, 8, 8)
                                .addComponent(lblLinkRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblExperimentName)))
                    .addComponent(separator, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage)
                    .addComponent(lblExperimentName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLinkOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lblLinkConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lblLinkRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblLinkConfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLinkConfigMouseClicked
		expHistoryUINode.showExperimentHeader();
    }//GEN-LAST:event_lblLinkConfigMouseClicked

    private void lblLinkRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLinkRemoveMouseClicked
		if (this.parentContainer != null) {
			parentContainer.remove(this);
			parentContainer.revalidate();
			parentContainer.repaint();
		}
    }//GEN-LAST:event_lblLinkRemoveMouseClicked

    private void lblLinkOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLinkOpenMouseClicked
		expHistoryUINode.startExperiment();
	}//GEN-LAST:event_lblLinkOpenMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblExperimentName;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblLinkConfig;
    private javax.swing.JLabel lblLinkOpen;
    private javax.swing.JLabel lblLinkRemove;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables

}
