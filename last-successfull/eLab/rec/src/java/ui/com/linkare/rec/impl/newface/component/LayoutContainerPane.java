/*
 * LayoutContainerPane.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * 
 * @author hfernandes
 */
public class LayoutContainerPane extends GradientPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385242553945558687L;

	private ApparatusTabbedPane apparatusTabbedPane;

	private boolean apparatusTabbedPaneEnabled = false;

	/** Creates new form LayoutContainerPane */
	public LayoutContainerPane() {
		super(GradientStyle.VERTICAL_LINEAR_LIGHT_TO_DARK);
		initComponents();
	}

	public NavigationPane getNavigationPane() {
		return navigationPane;
	}

	/**
	 * @return Either the single ApparatusDescriptionPane or the description
	 *         pane inside the tabs when these are enabled.
	 */
	public ApparatusDescriptionPane getApparatusDescriptionPane() {
		if (isApparatusTabbedPaneEnabled()) {
			return apparatusTabbedPane.getDescriptionPane();
		}
		return apparatusDescriptionPane;
	}

	public ApparatusUserList getApparatusUserList() {
		return apparatusTabbedPane.getApparatusUserList();
	}

	public ApparatusTabbedPane getApparatusTabbedPane() {
		return apparatusTabbedPane;
	}

	public MediaPane getMediaPane() {
		return mediaPane;
	}

	public void enableApparatusTabbedPane() {
		if (apparatusTabbedPane == null) {
			apparatusTabbedPane = new ApparatusTabbedPane();
		}
		apparatusTabbedPaneEnabled = true;
		splitLeft.setRightComponent(apparatusTabbedPane);
	}

	public void disableApparatusTabbedPane() {
		apparatusTabbedPaneEnabled = false;
		splitLeft.setRightComponent(apparatusDescriptionPane);
	}

	/**
	 * @return the apparatusTabbedPaneEnabled
	 */
	public boolean isApparatusTabbedPaneEnabled() {
		return apparatusTabbedPaneEnabled;
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

		splitRight = new javax.swing.JSplitPane();
		splitLeft = new javax.swing.JSplitPane();
		navigationPane = new com.linkare.rec.impl.newface.component.NavigationPane();
		apparatusDescriptionPane = new com.linkare.rec.impl.newface.component.ApparatusDescriptionPane();
		mediaPane = new com.linkare.rec.impl.newface.component.MediaPane();

		setName("Form"); // NOI18N
		setLayout(new java.awt.BorderLayout());

		splitRight.setBorder(null);
		splitRight.setDividerSize(6);
		splitRight.setResizeWeight(1.0);
		splitRight.setName("splitRight"); // NOI18N
		splitRight.setOpaque(false);

		splitLeft.setBorder(null);
		splitLeft.setDividerSize(6);
		splitLeft.setName("splitLeft"); // NOI18N
		splitLeft.setOpaque(false);

		navigationPane.setMaximumSize(new java.awt.Dimension(212, 32767));
		navigationPane.setMinimumSize(new java.awt.Dimension(212, 400));
		navigationPane.setName("navigationPane"); // NOI18N
		navigationPane.setPreferredSize(new java.awt.Dimension(212, 404));
		splitLeft.setLeftComponent(navigationPane);

		apparatusDescriptionPane.setMinimumSize(new java.awt.Dimension(388, 400));
		apparatusDescriptionPane.setName("apparatusDescriptionPane"); // NOI18N
		apparatusDescriptionPane.setPreferredSize(new java.awt.Dimension(400, 404));
		splitLeft.setRightComponent(apparatusDescriptionPane);

		splitRight.setLeftComponent(splitLeft);

		mediaPane.setMinimumSize(new java.awt.Dimension(212, 400));
		mediaPane.setName("mediaPane"); // NOI18N
		mediaPane.setPreferredSize(new java.awt.Dimension(212, 404));
		splitRight.setRightComponent(mediaPane);

		add(splitRight, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.newface.component.ApparatusDescriptionPane apparatusDescriptionPane;
	private com.linkare.rec.impl.newface.component.MediaPane mediaPane;
	private com.linkare.rec.impl.newface.component.NavigationPane navigationPane;
	private javax.swing.JSplitPane splitLeft;
	private javax.swing.JSplitPane splitRight;
	// End of variables declaration//GEN-END:variables

}