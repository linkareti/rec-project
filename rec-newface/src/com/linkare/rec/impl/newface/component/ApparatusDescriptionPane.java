/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ApparatusPane.java
 *
 * Created on 20/Abr/2009, 14:00:55
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusDescriptionPane extends AbstractContentPane {

    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(ApparatusDescriptionPane.class.getName());

    /** Creates new form ApparatusPane */
    public ApparatusDescriptionPane() {
	initComponents();
    }

    public void setFieldsVisible(boolean visible) {
	for (Component component : getComponents()) {
	    component.setVisible(visible);
	}
    }

    public void setApparatusConfig(Apparatus apparatusConfig) {

	if (apparatusConfig == null) {
	    // TODO clear selection
	    return;
	} else {
	    if (apparatusConfig.getDesktopLocationBundleKey() != null) {
		lblApparatusName.setText(ReCResourceBundle.findString(apparatusConfig.getDisplayStringBundleKey()));

		lblApparatusImg.setIcon(ReCResourceBundle.findImageIconOrDefault(apparatusConfig
			.getDesktopLocationBundleKey(), new ImageIcon())); // FIXME Set default icon
	    }
	}

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	lblApparatusName = new javax.swing.JLabel();
	lblApparatusImg = new javax.swing.JLabel();
	btnLink = new javax.swing.JButton();
	scrollApparatusDescription = new javax.swing.JScrollPane();
	jTextArea1 = new javax.swing.JTextArea();

	setMaximumSize(new java.awt.Dimension(32767, 500));
	setMinimumSize(new java.awt.Dimension(388, 398));
	setName("Form"); // NOI18N

	lblApparatusName.setFont(lblApparatusName.getFont().deriveFont(
		lblApparatusName.getFont().getStyle() | java.awt.Font.BOLD, 16));
	org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
		com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(
		ApparatusDescriptionPane.class);
	lblApparatusName.setText(resourceMap.getString("lblApparatusName.text")); // NOI18N
	lblApparatusName.setName("lblApparatusName"); // NOI18N

	lblApparatusImg.setIcon(resourceMap.getIcon("lblApparatusImg.icon")); // NOI18N
	lblApparatusImg.setName("lblApparatusImg"); // NOI18N

	btnLink.setBackground(resourceMap.getColor("btnLink.background")); // NOI18N
	btnLink.setFont(btnLink.getFont().deriveFont((float) 11));
	btnLink.setForeground(resourceMap.getColor("btnLink.foreground")); // NOI18N
	btnLink.setText(resourceMap.getString("btnLink.text")); // NOI18N
	btnLink.setBorderPainted(false);
	btnLink.setName("btnLink"); // NOI18N

	scrollApparatusDescription.setBackground(resourceMap.getColor("scrollApparatusDescription.background")); // NOI18N
	scrollApparatusDescription.setBorder(null);
	scrollApparatusDescription.setName("scrollApparatusDescription"); // NOI18N
	scrollApparatusDescription.setOpaque(false);

	jTextArea1.setEditable(false);
	jTextArea1.setFont(jTextArea1.getFont());
	jTextArea1.setLineWrap(true);
	jTextArea1.setRows(5);
	jTextArea1.setTabSize(4);
	jTextArea1.setText(resourceMap.getString("jTextArea1.text")); // NOI18N
	jTextArea1.setName("jTextArea1"); // NOI18N
	jTextArea1.setOpaque(false);
	scrollApparatusDescription.setViewportView(jTextArea1);

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	this.setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addContainerGap().addGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				scrollApparatusDescription).addComponent(lblApparatusImg)
				.addComponent(lblApparatusName).addComponent(btnLink,
					javax.swing.GroupLayout.PREFERRED_SIZE, 206,
					javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addContainerGap().addComponent(lblApparatusName).addGap(18, 18, 18)
			.addComponent(lblApparatusImg, javax.swing.GroupLayout.PREFERRED_SIZE, 132,
				javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
				javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
				scrollApparatusDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnLink)
			.addContainerGap()));

	lblApparatusName.getAccessibleContext().setAccessibleName(
		resourceMap.getString("lblApparatusName.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLink;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblApparatusImg;
    private javax.swing.JLabel lblApparatusName;
    private javax.swing.JScrollPane scrollApparatusDescription;
    // End of variables declaration//GEN-END:variables

}
