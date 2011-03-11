/*
 * NewUnexpectedErrorPane.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author hfernandes
 */
public class UnexpectedErrorPane extends AbstractContentPane {

	protected Throwable errorCause;

	/** Creates new form NewUnexpectedErrorPane */
	public UnexpectedErrorPane() {
		this(null, null);
	}

	public UnexpectedErrorPane(Throwable errorCause) {
		this(errorCause, null);
	}

	public UnexpectedErrorPane(Throwable errorCause, Window container) {
		super(container);
		initComponents();
		this.errorCause = errorCause;
	}

	public Throwable getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(Throwable errorCause) {
		this.errorCause = errorCause;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitle = new javax.swing.JLabel();
        labelMessage1 = new javax.swing.JLabel();
        labelMessage2 = new javax.swing.JLabel();
        btnFinish = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(450, 170));

        labelTitle.setFont(labelTitle.getFont().deriveFont((float)24));
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(UnexpectedErrorPane.class);
        labelTitle.setText(resourceMap.getString("labelTitle.text")); // NOI18N
        labelTitle.setName("labelTitle"); // NOI18N

        labelMessage1.setText(resourceMap.getString("labelMessage1.text")); // NOI18N
        labelMessage1.setName("labelMessage1"); // NOI18N

        labelMessage2.setText(resourceMap.getString("labelMessage2.text")); // NOI18N
        labelMessage2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelMessage2.setName("labelMessage2"); // NOI18N

        btnFinish.setText(resourceMap.getString("btnFinish.text")); // NOI18N
        btnFinish.setToolTipText(resourceMap.getString("btnFinish.toolTipText")); // NOI18N
        btnFinish.setName("btnFinish"); // NOI18N
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishActionPerformed(evt);
            }
        });

        btnDetail.setBackground(null);
        btnDetail.setIcon(resourceMap.getIcon("btnDetail.icon")); // NOI18N
        btnDetail.setToolTipText(resourceMap.getString("btnDetail.toolTipText")); // NOI18N
        btnDetail.setBorderPainted(false);
        btnDetail.setName("btnDetail"); // NOI18N
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMessage2, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addComponent(labelMessage1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                        .addComponent(btnFinish))
                    .addComponent(labelTitle))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMessage1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelMessage2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinish)
                    .addComponent(btnDetail))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
	
	private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
		closeContainer();
	}//GEN-LAST:event_btnFinishActionPerformed

	private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
		Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection sel = new StringSelection(getStackTrace(errorCause));
		system.setContents(sel, sel);
	}//GEN-LAST:event_btnDetailActionPerformed

	public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        sw.flush();
        pw.close();
        return sw.toString();
    }
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnFinish;
    private javax.swing.JLabel labelMessage1;
    private javax.swing.JLabel labelMessage2;
    private javax.swing.JLabel labelTitle;
    // End of variables declaration//GEN-END:variables

}
