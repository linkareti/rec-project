/*
 * NewUnexpectedErrorPane.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 * 
 * @author hfernandes
 */
public class UnexpectedErrorPane extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427215116347234737L;
	protected Throwable errorCause;
	private boolean detailMessageShowing = false;

	
	/** Creates new form NewUnexpectedErrorPane */
	public UnexpectedErrorPane() {
		this(null, null);
	}

	public UnexpectedErrorPane(final Throwable errorCause) {
		this(errorCause, null);
	}

	public UnexpectedErrorPane(final Throwable errorCause, final Window container) {
		super(container);
		initComponents();
		this.errorCause = errorCause;
	}

	public Throwable getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(final Throwable errorCause) {
		this.errorCause = errorCause;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrlPaneMessage = new javax.swing.JScrollPane();
        txtStack = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        labelMessage1 = new javax.swing.JLabel();
        labelMessage2 = new javax.swing.JLabel();
        jPnlContainerMessage = new javax.swing.JPanel();
        btnFinish = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();

        jScrlPaneMessage.setName("jScrlPaneMessage"); // NOI18N

        txtStack.setColumns(20);
        txtStack.setRows(5);
        txtStack.setName("txtStack"); // NOI18N
        jScrlPaneMessage.setViewportView(txtStack);

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(450, 170));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);

        labelTitle.setFont(labelTitle.getFont().deriveFont((float)24));
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(UnexpectedErrorPane.class);
        labelTitle.setText(resourceMap.getString("labelTitle.text")); // NOI18N
        labelTitle.setFocusable(false);
        labelTitle.setName("labelTitle"); // NOI18N

        labelMessage1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMessage1.setText(resourceMap.getString("labelMessage1.text")); // NOI18N
        labelMessage1.setName("labelMessage1"); // NOI18N

        labelMessage2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMessage2.setText(resourceMap.getString("labelMessage2.text")); // NOI18N
        labelMessage2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelMessage2.setName("labelMessage2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelMessage1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
            .addComponent(labelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
            .addComponent(labelMessage2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMessage1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMessage2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPnlContainerMessage.setName("jPnlContainerMessage"); // NOI18N
        jPnlContainerMessage.setOpaque(false);
        jPnlContainerMessage.setPreferredSize(new java.awt.Dimension(16, 0));
        jPnlContainerMessage.setLayout(new java.awt.BorderLayout());

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
                    .addComponent(jPnlContainerMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
                        .addComponent(btnFinish))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPnlContainerMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 8, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFinish, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void btnFinishActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnFinishActionPerformed
		closeContainer();
	}// GEN-LAST:event_btnFinishActionPerformed

	/**
	 * @param evt
	 */
	private void btnDetailActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDetailActionPerformed
		final Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
		String stackTrace = UnexpectedErrorPane.getStackTrace(errorCause);
		final StringSelection sel = new StringSelection(stackTrace);
		system.setContents(sel, sel);
		System.err.println(stackTrace);
		txtStack.setText(stackTrace);
		
		Component rootComponent=SwingUtilities.getRoot(this);
		Dimension rootComponentDimension=rootComponent.getSize();
		if (!detailMessageShowing) {
			jPnlContainerMessage.add(jScrlPaneMessage, BorderLayout.CENTER);
			rootComponent.setSize(rootComponentDimension.width,rootComponentDimension.height+300);
			jPnlContainerMessage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createEmptyBorder(2, 2,2,2)));
		} else {
			jPnlContainerMessage.removeAll();
			rootComponent.setSize(rootComponentDimension.width,rootComponentDimension.height-300);
			jPnlContainerMessage.setBorder(null);
		}
		detailMessageShowing=!detailMessageShowing;
	}// GEN-LAST:event_btnDetailActionPerformed

	
	

	public static String getStackTrace(final Throwable t) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		if (t != null) {
			t.printStackTrace(pw);
		} else {
			new RuntimeException("No more info available").printStackTrace(pw);
		}
		sw.flush();
		pw.close();
		return sw.toString();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnFinish;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPnlContainerMessage;
    private javax.swing.JScrollPane jScrlPaneMessage;
    private javax.swing.JLabel labelMessage1;
    private javax.swing.JLabel labelMessage2;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTextArea txtStack;
    // End of variables declaration//GEN-END:variables

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		final JDialog frame = new JDialog();
		frame.getContentPane().add(new UnexpectedErrorPane());
		SwingUtilities.invokeAndWait(new Runnable() {
			
			@Override
			public void run() {
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
