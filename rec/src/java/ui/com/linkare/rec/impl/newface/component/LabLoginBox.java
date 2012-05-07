/*
 * SimpleLoginBox.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;

import javax.swing.Icon;

import org.jdesktop.application.Action;

import com.linkare.rec.impl.newface.ReCApplication;
import com.linkare.rec.impl.newface.component.ProgressCicle.State;
import com.linkare.rec.impl.newface.config.Lab;
import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;
import java.util.prefs.Preferences;

/**
 * 
 * @author npadriano
 */
public class LabLoginBox extends GradientPane {

	/** Generated UID */
	private static final long serialVersionUID = -918799173616242126L;

	private final ReCApplication recApplication = ReCApplication.getApplication();
        
	/** Creates new form SimpleLoginBox */
	public LabLoginBox() {
		super();
		setContainer(container);
		initComponents();
		txtUsername.setText(System.getProperty("user.name"));
                checkBoxComputerType.setSelected(!Preferences.userRoot().getBoolean("ElabPrivateComputer", true));
	}

	public void setLoginProgressVisible(final boolean visible) {
		progressCicle.setVisible(visible);
		progressCicle.setState(visible ? State.RUNNING : State.IDLE);
	}

	public void setIdleIcon(final Icon idleIcon) {
		progressCicle.setIdleIcon(idleIcon);
	}

	public void setBusyIcons(final Icon[] busyIcons) {
		progressCicle.setBusyIcons(busyIcons);
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

        labelUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        progressCicle = new com.linkare.rec.impl.newface.component.ProgressCicle();
        btnGo = new com.linkare.rec.impl.newface.component.FlatButton();
        labelPassword = new javax.swing.JLabel();
        labCombo = new javax.swing.JComboBox();
        labelPassword1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnQuit = new com.linkare.rec.impl.newface.component.FlatButton();
        checkBoxComputerType = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(360, 280));

        labelUsername.setFont(labelUsername.getFont().deriveFont(labelUsername.getFont().getStyle() | java.awt.Font.BOLD));
        labelUsername.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(LabLoginBox.class);
        labelUsername.setText(resourceMap.getString("labelUsername.text")); // NOI18N
        labelUsername.setName("labelUsername"); // NOI18N

        txtUsername.setText(resourceMap.getString("txtUsername.text")); // NOI18N
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(LabLoginBox.class, this);
        txtUsername.setAction(actionMap.get("next")); // NOI18N
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txtUsername.setName("txtUsername"); // NOI18N
        txtUsername.setPreferredSize(new java.awt.Dimension(215, 28));

        progressCicle.setText(resourceMap.getString("progressCicle.text")); // NOI18N
        progressCicle.setIdleIcon(resourceMap.getIcon("progressCicle.idleIcon")); // NOI18N
        progressCicle.setName("progressCicle"); // NOI18N

        btnGo.setAction(actionMap.get("next")); // NOI18N
        btnGo.setFont(btnGo.getFont().deriveFont(btnGo.getFont().getStyle() | java.awt.Font.BOLD));
        btnGo.setName("btnGo"); // NOI18N

        labelPassword.setFont(labelPassword.getFont().deriveFont(labelPassword.getFont().getStyle() | java.awt.Font.BOLD));
        labelPassword.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        labelPassword.setText(resourceMap.getString("labelPassword.text")); // NOI18N
        labelPassword.setName("labelPassword"); // NOI18N

        labCombo.setModel(getRecApplication().getLabComboBoxModel());
        labCombo.setName("labCombo"); // NOI18N
        labCombo.setRenderer(new com.linkare.rec.impl.newface.component.LabRenderer());

        labelPassword1.setFont(labelPassword1.getFont().deriveFont(labelPassword1.getFont().getStyle() | java.awt.Font.BOLD));
        labelPassword1.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        labelPassword1.setText(resourceMap.getString("labelPassword1.text")); // NOI18N
        labelPassword1.setName("labelPassword1"); // NOI18N

        txtPassword.setText(resourceMap.getString("txtPassword.text")); // NOI18N
        txtPassword.setAction(actionMap.get("next")); // NOI18N
        txtPassword.setName("txtPassword"); // NOI18N

        btnQuit.setAction(actionMap.get("quit")); // NOI18N
        btnQuit.setForeground(resourceMap.getColor("btnQuit.foreground")); // NOI18N
        btnQuit.setText(resourceMap.getString("btnQuit.text")); // NOI18N
        btnQuit.setToolTipText(resourceMap.getString("btnQuit.toolTipText")); // NOI18N
        btnQuit.setBorderColor(resourceMap.getColor("btnQuit.borderColor")); // NOI18N
        btnQuit.setFont(btnQuit.getFont().deriveFont(btnQuit.getFont().getStyle() | java.awt.Font.BOLD));
        btnQuit.setGradientBottom(resourceMap.getColor("btnQuit.gradientBottom")); // NOI18N
        btnQuit.setGradientTop(resourceMap.getColor("btnQuit.gradientTop")); // NOI18N
        btnQuit.setName("btnQuit"); // NOI18N

        checkBoxComputerType.setBackground(resourceMap.getColor("checkBoxComputerType.background")); // NOI18N
        checkBoxComputerType.setFont(checkBoxComputerType.getFont().deriveFont(checkBoxComputerType.getFont().getSize()-1f));
        checkBoxComputerType.setForeground(LAFConnector.getColor(SpecialELabProperties.SELECTION_FOREGROUND_ON_DARK));
        checkBoxComputerType.setSelected(true);
        checkBoxComputerType.setText(resourceMap.getString("checkBoxComputerType.text")); // NOI18N
        checkBoxComputerType.setBorder(null);
        checkBoxComputerType.setContentAreaFilled(false);
        checkBoxComputerType.setName("checkBoxComputerType"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelUsername)
                        .addComponent(labelPassword)
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addComponent(labelPassword1)
                        .addComponent(labCombo, 0, 246, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                            .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkBoxComputerType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(progressCicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(labelPassword)
                        .addGap(6, 6, 6)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(labelPassword1)
                        .addGap(6, 6, 6)
                        .addComponent(labCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxComputerType))
                    .addComponent(progressCicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	@Action
	public void next() {
		setLoginProgressVisible(true);
                
		recApplication.setUserInfo(getUsername(), getPassword());

		// set lab with selected from lab combo
		final Lab lab = (Lab) labCombo.getSelectedItem();
		recApplication.setCurrentLab(lab);

		// Connect
		recApplication.connect();

                //set the user preference
                Preferences.userRoot().putBoolean("ElabPrivateComputer", !checkBoxComputerType.isSelected());
	}

	public String getUsername() {
		return txtUsername.getText();
	}

	public String getPassword() {
		return new String(txtPassword.getPassword());
	}

	public void reset() {
		if (labCombo.getModel().getSize() > 0) {
			labCombo.setSelectedIndex(0);
		}
		txtPassword.setText("");
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.newface.component.FlatButton btnGo;
    private com.linkare.rec.impl.newface.component.FlatButton btnQuit;
    private javax.swing.JCheckBox checkBoxComputerType;
    private javax.swing.JComboBox labCombo;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPassword1;
    private javax.swing.JLabel labelUsername;
    private com.linkare.rec.impl.newface.component.ProgressCicle progressCicle;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}
