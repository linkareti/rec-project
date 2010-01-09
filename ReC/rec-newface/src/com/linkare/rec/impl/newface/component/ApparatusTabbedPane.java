/*
 * ApparatusTabbedPane.java created on May 20, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author hfernandes
 */
public class ApparatusTabbedPane extends javax.swing.JPanel implements ChangeListener {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ApparatusTabbedPane.class.getName());

	public static final int TAB_DESCRIPTION = 0;

	public static final int TAB_CUSTOMIZER = 1;

	public static final int TAB_RESULTS = 2;

	public static final int TAB_USERS = 3;

	private boolean lastSelectedTabResults = false;

	/** Creates new form ApparatusTabbedPane */
	public ApparatusTabbedPane() {
		initComponents();

		resultsActionBar = new ResultsActionBar();
		resultsActionBar.setName("resultsActionBar");
		resultsActionBar.setMinimumSize(experimentActionBar.getMinimumSize());

		setTabIndexEnabled(TAB_RESULTS, false);

		tabbedPane.addChangeListener(this);
	}

	public ApparatusDescriptionPane getDescriptionPane() {
		return descriptionPane;
	}

	public ApparatusUserList getApparatusUserList() {
		return apparatusUserList;
	}

	public ExperimentActionBar getExperimentActionBar() {
		return experimentActionBar;
	}

	public StatusActionBar getExperimentStatusActionBar() {
		return statusActionBar;
	}

	public JPanel getResultsHolderPane() {
		return resultsHolderPane;
	}

	public void addCustomizerComponent(JComponent controller) {
		controllerHolderScrollPane.setViewportView(controller);
	}

	public void setSelectedTabIndex(int index) {
		tabbedPane.setSelectedIndex(index);
	}

	public void setTabIndexEnabled(int index, boolean enabled) {
		tabbedPane.setEnabledAt(index, enabled);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (TAB_RESULTS == tabbedPane.getSelectedIndex()) {

			actionBarHolderPane.remove(experimentActionBar);
			actionBarHolderPane.add(resultsActionBar, BorderLayout.LINE_START);
			lastSelectedTabResults = true;
			revalidate();
			repaint();

		} else if (lastSelectedTabResults) {
			lastSelectedTabResults = false;
			actionBarHolderPane.remove(resultsActionBar);
			actionBarHolderPane.add(experimentActionBar, BorderLayout.LINE_START);
			revalidate();
			repaint();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		actionBarHolderPane = new javax.swing.JPanel();
		experimentActionBar = new com.linkare.rec.impl.newface.component.ExperimentActionBar();
		statusActionBar = new com.linkare.rec.impl.newface.component.StatusActionBar();
		tabbedPane = new javax.swing.JTabbedPane();
		descriptionPane = new com.linkare.rec.impl.newface.component.ApparatusDescriptionPane();
		controllerHolderPane = new javax.swing.JPanel();
		controllerHolderScrollPane = new javax.swing.JScrollPane();
		resultsHolderPane = new javax.swing.JPanel();
		userListHolderPane = new javax.swing.JPanel();
		apparatusUserList = new com.linkare.rec.impl.newface.component.ApparatusUserList();

		setName("Form"); // NOI18N
		setOpaque(false);
		setLayout(new java.awt.BorderLayout());

		actionBarHolderPane.setName("actionBarHolderPane"); // NOI18N
		actionBarHolderPane.setPreferredSize(new java.awt.Dimension(514, 34));
		actionBarHolderPane.setLayout(new java.awt.BorderLayout());

		experimentActionBar.setName("experimentActionBar"); // NOI18N
		actionBarHolderPane.add(experimentActionBar, java.awt.BorderLayout.LINE_START);

		statusActionBar.setName("statusActionBar"); // NOI18N
		actionBarHolderPane.add(statusActionBar, java.awt.BorderLayout.CENTER);

		add(actionBarHolderPane, java.awt.BorderLayout.SOUTH);

		tabbedPane.setName("tabbedPane"); // NOI18N

		descriptionPane.setName("descriptionPane"); // NOI18N
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
				com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ApparatusTabbedPane.class);
		tabbedPane.addTab(resourceMap.getString("descriptionPane.TabConstraints.tabTitle"), resourceMap
				.getIcon("descriptionPane.TabConstraints.tabIcon"), descriptionPane); // NOI18N

		controllerHolderPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
		controllerHolderPane.setName("controllerHolderPane"); // NOI18N
		controllerHolderPane.setLayout(new java.awt.BorderLayout());

		controllerHolderScrollPane.setBorder(null);
		controllerHolderScrollPane.setName("controllerHolderScrollPane"); // NOI18N
		controllerHolderScrollPane.setOpaque(false);
		controllerHolderPane.add(controllerHolderScrollPane, java.awt.BorderLayout.CENTER);

		tabbedPane.addTab(resourceMap.getString("controllerHolderPane.TabConstraints.tabTitle"), controllerHolderPane); // NOI18N

		resultsHolderPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
		resultsHolderPane.setName("resultsHolderPane"); // NOI18N
		resultsHolderPane.setLayout(new java.awt.BorderLayout());
		tabbedPane.addTab(resourceMap.getString("resultsHolderPane.TabConstraints.tabTitle"), resultsHolderPane); // NOI18N

		userListHolderPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
		userListHolderPane.setName("userListHolderPane"); // NOI18N
		userListHolderPane.setLayout(new javax.swing.BoxLayout(userListHolderPane, javax.swing.BoxLayout.LINE_AXIS));

		apparatusUserList.setName("apparatusUserList"); // NOI18N
		userListHolderPane.add(apparatusUserList);

		tabbedPane.addTab(resourceMap.getString("userListHolderPane.TabConstraints.tabTitle"), userListHolderPane); // NOI18N

		add(tabbedPane, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel actionBarHolderPane;
	private com.linkare.rec.impl.newface.component.ApparatusUserList apparatusUserList;
	private javax.swing.JPanel controllerHolderPane;
	private javax.swing.JScrollPane controllerHolderScrollPane;
	private com.linkare.rec.impl.newface.component.ApparatusDescriptionPane descriptionPane;
	private com.linkare.rec.impl.newface.component.ExperimentActionBar experimentActionBar;
	private javax.swing.JPanel resultsHolderPane;
	private com.linkare.rec.impl.newface.component.StatusActionBar statusActionBar;
	private javax.swing.JTabbedPane tabbedPane;
	private javax.swing.JPanel userListHolderPane;
	// End of variables declaration//GEN-END:variables

	private ResultsActionBar resultsActionBar;
}
