/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AboutBoxDialog.java
 *
 * Created on 25-Oct-2010, 10:24:29
 */

package com.linkare.rec.impl.newface;

import java.awt.Dimension;

/**
 * 
 * @author npadriano
 */
public class AboutBoxDialog extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5985709257873497833L;

	public AboutBoxDialog(final java.awt.Frame parent) {
		this(parent, true);
	}

	/** Creates new form AboutBoxDialog 
	 * @param parent 
	 * @param modal */
	public AboutBoxDialog(final java.awt.Frame parent, final boolean modal) {
		super(parent, modal);
		initComponents();
        this.setMinimumSize(new Dimension(750, 345));
        this.setMaximumSize(new Dimension(750, 345));
        this.setLocationRelativeTo(null);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jScrollPaneElab = new javax.swing.JScrollPane();
        about_image = new javax.swing.JLabel();
        jScrollPaneSobreElab = new javax.swing.JScrollPane();
        jTextPaneSobreElab = new javax.swing.JTextPane();
        jScrollPaneIST = new javax.swing.JScrollPane();
        jTextPaneIST = new javax.swing.JTextPane();
        jScrollPaneME = new javax.swing.JScrollPane();
        jTextPaneME = new javax.swing.JTextPane();
        jScrollPaneLinkare = new javax.swing.JScrollPane();
        jTextPaneLinkare = new javax.swing.JTextPane();
        jScrollPaneLicence = new javax.swing.JScrollPane();
        jTextPaneLicence = new javax.swing.JTextPane();

        setModal(true);
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        tabs.setMaximumSize(new java.awt.Dimension(730, 309));
        tabs.setMinimumSize(new java.awt.Dimension(730, 309));
        tabs.setName("tabs"); // NOI18N
        tabs.setPreferredSize(new java.awt.Dimension(730, 309));

        jScrollPaneElab.setName("jScrollPaneElab"); // NOI18N

        about_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(AboutBoxDialog.class);
        about_image.setIcon(resourceMap.getIcon("about_image.icon")); // NOI18N
        about_image.setText(resourceMap.getString("about_image.text")); // NOI18N
        about_image.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        about_image.setMaximumSize(new java.awt.Dimension(350, 260));
        about_image.setMinimumSize(new java.awt.Dimension(350, 260));
        about_image.setName("about_image"); // NOI18N
        about_image.setPreferredSize(new java.awt.Dimension(350, 260));
        jScrollPaneElab.setViewportView(about_image);

        tabs.addTab(resourceMap.getString("jScrollPaneElab.TabConstraints.tabTitle"), jScrollPaneElab); // NOI18N

        jScrollPaneSobreElab.setName("jScrollPaneSobreElab"); // NOI18N

        jTextPaneSobreElab.setContentType(resourceMap.getString("jTextPaneSobreElab.contentType")); // NOI18N
        jTextPaneSobreElab.setText(resourceMap.getString("jTextPaneSobreElab.text")); // NOI18N
        jTextPaneSobreElab.setMinimumSize(new java.awt.Dimension(730, 309));
        jTextPaneSobreElab.setName("jTextPaneSobreElab"); // NOI18N
        jScrollPaneSobreElab.setViewportView(jTextPaneSobreElab);

        tabs.addTab(resourceMap.getString("jScrollPaneSobreElab.TabConstraints.tabTitle"), jScrollPaneSobreElab); // NOI18N

        jScrollPaneIST.setName("jScrollPaneIST"); // NOI18N

        jTextPaneIST.setContentType(resourceMap.getString("jTextPaneIST.contentType")); // NOI18N
        jTextPaneIST.setText(resourceMap.getString("jTextPaneIST.text")); // NOI18N
        jTextPaneIST.setMinimumSize(new java.awt.Dimension(730, 309));
        jTextPaneIST.setName("jTextPaneIST"); // NOI18N
        jScrollPaneIST.setViewportView(jTextPaneIST);

        tabs.addTab(resourceMap.getString("jScrollPaneIST.TabConstraints.tabTitle"), jScrollPaneIST); // NOI18N

        jScrollPaneME.setName("jScrollPaneME"); // NOI18N

        jTextPaneME.setContentType(resourceMap.getString("jTextPaneME.contentType")); // NOI18N
        jTextPaneME.setText(resourceMap.getString("jTextPaneME.text")); // NOI18N
        jTextPaneME.setMinimumSize(new java.awt.Dimension(730, 309));
        jTextPaneME.setName("jTextPaneME"); // NOI18N
        jScrollPaneME.setViewportView(jTextPaneME);

        tabs.addTab(resourceMap.getString("jScrollPaneME.TabConstraints.tabTitle"), jScrollPaneME); // NOI18N

        jScrollPaneLinkare.setName("jScrollPaneLinkare"); // NOI18N

        jTextPaneLinkare.setContentType(resourceMap.getString("jTextPaneLinkare.contentType")); // NOI18N
        jTextPaneLinkare.setText(resourceMap.getString("jTextPaneLinkare.text")); // NOI18N
        jTextPaneLinkare.setMinimumSize(new java.awt.Dimension(730, 309));
        jTextPaneLinkare.setName("jTextPaneLinkare"); // NOI18N
        jScrollPaneLinkare.setViewportView(jTextPaneLinkare);

        tabs.addTab(resourceMap.getString("jScrollPaneLinkare.TabConstraints.tabTitle"), jScrollPaneLinkare); // NOI18N

        jScrollPaneLicence.setName("jScrollPaneLicence"); // NOI18N

        jTextPaneLicence.setContentType(resourceMap.getString("jTextPaneLicence.contentType")); // NOI18N
        jTextPaneLicence.setText(resourceMap.getString("jTextPaneLicence.text")); // NOI18N
        jTextPaneLicence.setMinimumSize(new java.awt.Dimension(730, 309));
        jTextPaneLicence.setName("jTextPaneLicence"); // NOI18N
        jScrollPaneLicence.setViewportView(jTextPaneLicence);

        tabs.addTab(resourceMap.getString("jScrollPaneLicence.TabConstraints.tabTitle"), jScrollPaneLicence); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.getAccessibleContext().setAccessibleName(resourceMap.getString("tabs.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void formWindowActivated(final java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
		tabs.setSelectedIndex(0);
	}// GEN-LAST:event_formWindowActivated

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final AboutBoxDialog dialog = new AboutBoxDialog(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(final java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel about_image;
    private javax.swing.JScrollPane jScrollPaneElab;
    private javax.swing.JScrollPane jScrollPaneIST;
    private javax.swing.JScrollPane jScrollPaneLicence;
    private javax.swing.JScrollPane jScrollPaneLinkare;
    private javax.swing.JScrollPane jScrollPaneME;
    private javax.swing.JScrollPane jScrollPaneSobreElab;
    private javax.swing.JTextPane jTextPaneIST;
    private javax.swing.JTextPane jTextPaneLicence;
    private javax.swing.JTextPane jTextPaneLinkare;
    private javax.swing.JTextPane jTextPaneME;
    private javax.swing.JTextPane jTextPaneSobreElab;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

}
