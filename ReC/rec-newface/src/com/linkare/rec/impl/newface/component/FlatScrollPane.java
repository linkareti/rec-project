package com.linkare.rec.impl.newface.component;

import java.awt.Component;

import javax.swing.JScrollPane;

public class FlatScrollPane extends JScrollPane {

	private static final String uiClassID = "FlatScrollPaneUI";

	public FlatScrollPane(Component view, int vertical_scrollbar_as_needed, int horizontal_scrollbar_as_needed) {
		 super(view, vertical_scrollbar_as_needed, horizontal_scrollbar_as_needed);
    }
	
    public FlatScrollPane(Component view) {
        super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    public FlatScrollPane() {
    	
    }
	
    public String getUIClassID() {
        return uiClassID;
    }
}
