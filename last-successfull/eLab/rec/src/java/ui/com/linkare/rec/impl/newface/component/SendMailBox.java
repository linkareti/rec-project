/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SendMailBox.java
 *
 * Created on 7/Mai/2012, 15:12:44
 */
package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class SendMailBox extends GradientPane {

    /** Creates new form SendMailBox */
    public SendMailBox() {
        initComponents();
        setFocusTraversalPolicyProvider(true);
        this.setFocusTraversalPolicy(new FocusTraversalPolicy() {
            private final Component[] COMPONENT_ORDER = new Component[]{txtToMail, btnSend, btnCancel};

            @Override
            public Component getLastComponent(Container aContainer) {
                return COMPONENT_ORDER[COMPONENT_ORDER.length - 1];
            }

            @Override
            public Component getFirstComponent(Container aContainer) {
                return COMPONENT_ORDER[0];
            }

            @Override
            public Component getDefaultComponent(Container aContainer) {
                return COMPONENT_ORDER[0];
            }

            @Override
            public Component getComponentBefore(Container aContainer, Component aComponent) {
                for (int i = 0; i < COMPONENT_ORDER.length; i++) {
                    if (aComponent == COMPONENT_ORDER[i]) {
                        return COMPONENT_ORDER[i - 1 < 0 ? i - 1 + COMPONENT_ORDER.length : i - 1];
                    }
                }
                return null;
            }

            @Override
            public Component getComponentAfter(Container aContainer, Component aComponent) {
                for (int i = 0; i < COMPONENT_ORDER.length; i++) {
                    if (aComponent == COMPONENT_ORDER[i]) {
                        return COMPONENT_ORDER[i + 1 > COMPONENT_ORDER.length ? i + 1 - COMPONENT_ORDER.length : i + 1];
                    }
                }
                return null;
            }
        });
    }

    public JTextField getTxtToMail() {
        return txtToMail;
    }

    public JButton getBtnSend() {
        return btnSend;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelToMail = new javax.swing.JLabel();
        txtToMail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSend = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(431, 147));

        labelToMail.setFont(labelToMail.getFont().deriveFont(labelToMail.getFont().getStyle() | java.awt.Font.BOLD));
        labelToMail.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/linkare/rec/impl/newface/component/resources/SendMailBox"); // NOI18N
        labelToMail.setText(bundle.getString("labelToMail.text")); // NOI18N
        labelToMail.setName("labelToMail"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(SendMailBox.class);
        txtToMail.setToolTipText(resourceMap.getString("txtToMail.toolTipText")); // NOI18N
        txtToMail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txtToMail.setName("txtToMail"); // NOI18N
        txtToMail.setPreferredSize(new java.awt.Dimension(215, 28));

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        jLabel1.setText(bundle.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        btnSend.setText(resourceMap.getString("btnSend.text")); // NOI18N
        btnSend.setToolTipText(resourceMap.getString("btnSend.toolTipText")); // NOI18N
        btnSend.setLabel(bundle.getString("btnSend.text")); // NOI18N
        btnSend.setName("btnSend"); // NOI18N

        btnCancel.setText(bundle.getString("btnCancel.text")); // NOI18N
        btnCancel.setToolTipText(resourceMap.getString("btnCancel.toolTipText")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelToMail)
                    .addComponent(txtToMail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSend)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelToMail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtToMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSend))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(37, 37, 37))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.txtToMail.setText("");
    }//GEN-LAST:event_btnCancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelToMail;
    private javax.swing.JTextField txtToMail;
    // End of variables declaration//GEN-END:variables
}
