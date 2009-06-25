/*
 * ApparatusTabbedPane.java created on May 19, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.ReCApplication;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jdesktop.application.ResourceMap;

/**
 * @author Henrique Fernandes
 * @deprecated use ApparatusTabbedPane
 */
public class ApparatusTabbedXPane extends JTabbedPane {

	private static final long serialVersionUID = -935436319100632299L;

    private static ResourceMap resourceMap = ReCApplication.getApplication().getContext().getResourceMap(ApparatusTabbedXPane.class);
	
    private static final ImageIcon vertSpacerImg = resourceMap.getImageIcon("verticalSpacerImage");
	
    private ApparatusDescriptionPane descriptionPane;
	
    // TODO
    
	private JPanel controllerPane = new JPanel();

    private JPanel resultsPane = new JPanel();

    private JPanel usersPane = new JPanel();
    

	public ApparatusTabbedXPane(ApparatusDescriptionPane descriptionPane) {
		super();
        this.descriptionPane = descriptionPane;
		addTab(resourceMap.getString("descriptionTab.title"), vertSpacerImg, descriptionPane);
        addTab(resourceMap.getString("controllerTab.title"), controllerPane);
        addTab(resourceMap.getString("resultsTab.title"), resultsPane);
        addTab(resourceMap.getString("usersTab.title"), usersPane); 
       
	}
	
}
