/*
 * LoginBox.java created on Mar 10, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.UIManager;

/**
 *
 * @author Henrique Fernandes
 */
public class MessageBox extends GradientPane {

    private static final long serialVersionUID = 698114786085470559L;
    
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(MessageBox.class.getName());
    
	public static final String LOGIN_BOX_DO_LOGIN_EVENT = "LOGIN_BOX_DO_LOGIN_EVENT";

	/** Creates new form LoginBox */
	public MessageBox() {
		super(GradientStyle.VERTICAL_LINEAR_DARK_TO_LIGHT);

		initComponents();
		customInit();
	}

	private void customInit() {
		Color fgColor = UIManager.getColor(SpecialLAFProperties.ENABLED_FOREGROUND_ON_DARK.getName());
		if (fgColor != null) {
			lblMessage.setForeground(fgColor);
		}
	}
	
	/**
	 * @return the display message
	 */
	public String getMessage() {
		return lblMessage.getText();
	}

    public void setMessage(String message) {
        lblMessage.setText(message);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMessage = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        lblMessage.setText("Mensagem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMessage;
    // End of variables declaration//GEN-END:variables

}
