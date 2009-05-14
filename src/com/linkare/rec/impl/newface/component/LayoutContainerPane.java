/*
 * LayoutContainerPane.java created on Apr 16, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Dialog;

import com.linkare.rec.impl.newface.ReCFrameView;

/**
 *
 * @author hfernandes
 */
public class LayoutContainerPane extends GradientPane {

    /** Creates new form LayoutContainerPane */
    public LayoutContainerPane() {
        super(GradientStyle.VERTICAL_LINEAR_LIGHT_TO_DARK);
        initComponents();
    }
    
    public NavigationPane getNavigationPane() {
		return navigationPane;
	}
    
    public ApparatusDescriptionPane getApparatusDescriptionPane() {
		return apparatusDescriptionPane;
	}
    
    public MediaPane getMediaPane() {
		return mediaPane;
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
