/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MessagePane.java
 *
 * Created on 13/Abr/2009, 15:48:55
 */

package com.linkare.rec.impl.newface.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * 
 * @author Henrique Fernandes
 */
public class MessagePane extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7869824404854832253L;
	public static final String CLOSE_ME = "close me";

	/** Creates new form MessagePane */
	public MessagePane() {
		initComponents();
	}

	@Override
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;

		// gets the current clipping area
		final Rectangle clip = g.getClipBounds();

		// sets a translucent composite with current opacity
		final AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.71f);
		final Composite composite = g2.getComposite();
		g2.setComposite(alpha);

		// background fill
		g2.setColor(getBackground());
		g2.fillRect(clip.x, clip.y, clip.width, clip.height);

		// get back to normal state
		g2.setComposite(composite);
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

		btnClose = new javax.swing.JButton();
		lblMessage = new javax.swing.JLabel();

		setBackground(new Color(0xABDAEF));
		setName("Form"); // NOI18N

		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(MessagePane.class);
		btnClose.setIcon(resourceMap.getIcon("btnClose.icon")); // NOI18N
		btnClose.setText(resourceMap.getString("btnClose.text")); // NOI18N
		btnClose.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		btnClose.setFocusPainted(false);
		btnClose.setName("btnClose"); // NOI18N
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCloseActionPerformed(evt);
			}
		});

		lblMessage.setText(resourceMap.getString("lblMessage.text")); // NOI18N
		lblMessage.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		lblMessage.setName("lblMessage"); // NOI18N

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
						.addGap(18, 18, 18).addComponent(btnClose).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(btnClose))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void btnCloseActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCloseActionPerformed
		firePropertyChange(MessagePane.CLOSE_ME, false, true);
	}// GEN-LAST:event_btnCloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnClose;
	private javax.swing.JLabel lblMessage;
	// End of variables declaration//GEN-END:variables

}
