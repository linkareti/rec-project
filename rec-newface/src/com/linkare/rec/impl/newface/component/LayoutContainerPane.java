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
	 * @return Either the single ApparatusDescriptionPane or the description pane
	 *         inside the tabs when these are enabled.
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
    	splitRight.setRightComponent(apparatusTabbedPane);
	}
    
    public void disableApparatusTabbedPane() {
    	apparatusTabbedPaneEnabled = false;
    	splitRight.setRightComponent(apparatusDescriptionPane);
    }

    /**
	 * @return the apparatusTabbedPaneEnabled
	 */
	public boolean isApparatusTabbedPaneEnabled() {
		return apparatusTabbedPaneEnabled;
	}

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitLeft = new javax.swing.JSplitPane();
        splitRight = new javax.swing.JSplitPane();
        navigationPane = new com.linkare.rec.impl.newface.component.NavigationPane();
        apparatusDescriptionPane = new com.linkare.rec.impl.newface.component.ApparatusDescriptionPane();
        mediaPane = new com.linkare.rec.impl.newface.component.MediaPane();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        splitLeft.setBorder(null);
        splitLeft.setDividerSize(6);
        splitLeft.setName("splitLeft"); // NOI18N
        splitLeft.setOpaque(false);

        splitRight.setBorder(null);
        splitRight.setDividerSize(6);
        splitRight.setName("splitRight"); // NOI18N
        splitRight.setOpaque(false);

        navigationPane.setName("navigationPane"); // NOI18N
        splitRight.setLeftComponent(navigationPane);

        apparatusDescriptionPane.setName("apparatusDescriptionPane"); // NOI18N
        splitRight.setRightComponent(apparatusDescriptionPane);

        splitLeft.setLeftComponent(splitRight);

        mediaPane.setName("mediaPane"); // NOI18N
        splitLeft.setRightComponent(mediaPane);

        add(splitLeft, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.newface.component.ApparatusDescriptionPane apparatusDescriptionPane;
    private com.linkare.rec.impl.newface.component.MediaPane mediaPane;
    private com.linkare.rec.impl.newface.component.NavigationPane navigationPane;
    private javax.swing.JSplitPane splitLeft;
    private javax.swing.JSplitPane splitRight;
    // End of variables declaration//GEN-END:variables



}
