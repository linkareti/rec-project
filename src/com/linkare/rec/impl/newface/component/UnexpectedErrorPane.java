/*
 * UnexpectedErrorPane.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * @author Henrique Fernandes
 */
public class UnexpectedErrorPane extends AbstractContentPane {

    private static final long serialVersionUID = -6644614851974160202L;

    public static final int ACTION_QUIT = 0;
    
    public static final int ACTION_ALERT = 1;
    
    protected Exception error;
    
    private int actionValue;

    /** Creates new form UnexpectedErrorFriendlyPane */
    public UnexpectedErrorPane(Exception error) {
        this.error = error;
        initComponents();
    }

    /**
     * @return the actionValue
     */
    public int getActionValue() {
        return actionValue;
    }

    /**
     * @param actionValue the actionValue to set
     */
    public void setActionValue(int actionValue) {
        changeSupport.firePropertyChange("actionValue", this.actionValue, this.actionValue = actionValue);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        btnSendAlert = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        lblOops = new javax.swing.JLabel();
        lblGuideText = new javax.swing.JLabel();

        setOpaque(false);

        btnExit.setText("Saír");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnSendAlert.setText("Alertar");
        btnSendAlert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendAlertActionPerformed(evt);
            }
        });

        lblTitle.setText("Foi detectada uma indisponibilidade temporária.");

        lblOops.setFont(new java.awt.Font("Lucida Grande", 0, 24));
        lblOops.setText("Oops!");

        lblGuideText.setText("<html>\nPoderá alertar para a indisponibilidade detectada ou voltar mais tarde.<br>\nPedimos desculpa pelo incómodo.\n</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(lblOops)
                    .addComponent(lblGuideText))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(238, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSendAlert)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOops)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addComponent(lblGuideText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExit)
                    .addComponent(btnSendAlert))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
	setActionValue(ACTION_QUIT);
	fireContentPaneCloseEvent(evt);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSendAlertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendAlertActionPerformed
        // TODO send allert...
	setActionValue(ACTION_ALERT);
	fireContentPaneCloseEvent(evt);
    }//GEN-LAST:event_btnSendAlertActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSendAlert;
    private javax.swing.JLabel lblGuideText;
    private javax.swing.JLabel lblOops;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables

}
