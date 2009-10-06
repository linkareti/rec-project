/*
 * ResultsPane.java created on Sep 23, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author hfernandes
 */
public class ResultsPane extends javax.swing.JPanel implements ExpDataModelListener, ActionListener {

	private static final Logger log = Logger.getLogger(ResultsPane.class.getName());

	private class DisplaySelector {
		private Icon icon;
		private String name;

		private DisplaySelector() {
		}

		public Icon getIcon() {
			return icon;
		}

		public String getName() {
			return name;
		}

		public void setIcon(Icon icon) {
			this.icon = icon;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
		
	}
	
	private static class DisplaySelectorRenderer extends JLabel implements ListCellRenderer {

		private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 2, 1, 2);
		
	    private DisplaySelector displaySelector;

	    public DisplaySelectorRenderer() {
	    	super();
	        setOpaque(true);
	        setBorder(NO_FOCUS_BORDER);
	    }

	    @Override
	    public Component getListCellRendererComponent(JList list, Object value,
	            int index, boolean isSelected, boolean cellHasFocus) {

	        // Value must be an Apparatus
	    	displaySelector = (DisplaySelector) value;

			if (displaySelector != null) {

				if (isSelected) {
		            setBackground(list.getSelectionBackground());
		            setForeground(list.getSelectionForeground());
		        }
		        else {
		            setBackground(list.getBackground());
		            setForeground(list.getForeground());
		        }
				
				// Font
		        setFont(list.getFont());

				// Icon
				setIcon(displaySelector.getIcon());

				// Text
				setText(displaySelector.getName());

				// State
				setEnabled(true);
	        }

	        return this;
	    }

	}
	
	private static final String RUNNING_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.running", "Running");
    private static final String STATUS_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.experiment", "Experiment status");
    private static final String PAUSED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.paused", "Paused");
    private static final String STOPED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.stoped", "Stopped");
    private static final String WAITING_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.waitingData", "Waiting for data...");
    private static final String NODATA_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.nodata", "The experiment started but no data was available to fetch");
    private static final String STARTED_NODATA_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.startedNoData", "Started, no data available yet...");
    private static final String DATA_ENDED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.ended", "Data Ended");
    private static final String DISPLAY_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.display", "Display");
    private static final String DATA_MODEL_ERROR1_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.error.dataModel", "Data Model error!");
    private static final String DATA_MODEL_ERROR2_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.error.dataModel.2", "Serious error in the data model, data was lost!");

	private static final int BUTTON_LIMIT = 2;

    /** Holds value of property expDataModel. */
	private ExpDataModel experimentDataModel;
	
	/** Utility field holding list of ExpDataModelListeners. */
    private transient List<ExpDataDisplay> experimentDataDisplays;
    
    private Map<String, Integer> displaySelectorNames = new HashMap<String, Integer>();
    
    private Map<String, JComponent> displayMap = new HashMap<String, JComponent>();
    
    private JComboBox comboDisplaySelector;

    /** Creates new form ResultsPane */
    public ResultsPane() {
        initComponents();
    }

    /** Getter for property expDataModel.
     * @return Value of property expDataModel.
     */
    public ExpDataModel getExperimentDataModel()
    {
        return this.experimentDataModel;
    }
    
    /** Setter for property expDataModel.
     * @param expDataModel New value of property expDataModel.
     */
    public void setExperimentDataModel(ExpDataModel expDataModel)
    {
        if(expDataModel != null) {
            expDataModel.removeExpDataModelListener(this);
        }
        this.experimentDataModel = expDataModel;
        if(expDataModel != null) {
            expDataModel.addExpDataModelListener(this);
        }
        if(experimentDataDisplays != null) {
            for(ExpDataDisplay display : experimentDataDisplays) {
                display.setExpDataModel(expDataModel);
            }
        }
    }
    
    public void addExperimentDataDisplay(ExpDataDisplay dataDisplay)
    {
        if (experimentDataDisplays == null ) {
        	experimentDataDisplays = new ArrayList<ExpDataDisplay>();
        }
        
        experimentDataDisplays.add(dataDisplay);
        
        try {
            final Icon icon = dataDisplay.getIcon();
            String displayName = dataDisplay.getName();
            
            if(displayName == null) { 
            	displayName = DISPLAY_STR;
            }
            
            String displayNameFinal;
            
            if (displaySelectorNames.keySet().contains(displayName)) {
            	Integer count = displaySelectorNames.get(displayName);
            	displaySelectorNames.put(displayName, ++count);
            	displayNameFinal = displayName + count;
            } else {
            	displaySelectorNames.put(displayName, Integer.valueOf(0));
            	displayNameFinal = displayName;
            }
            
            addDataDisplay(displayNameFinal, dataDisplay);
            
        }catch(Exception e) {
        	log.log(Level.SEVERE, "Couldn't add DataDisplay Component " + dataDisplay + " to ExperimentInternalFrame!", e);
        }                        
        dataDisplay.setExpDataModel(experimentDataModel);            
    }
    
    public void finishedAddingDataDisplays() {
		comboDisplaySelector.addActionListener(this);
    }
    
    private void addDataDisplay(String displayName, ExpDataDisplay dataDisplay) {
    	displayMap.put(displayName, dataDisplay.getDisplay());
    	addDisplaySelector(displayName, dataDisplay);
	}
    
    private void addDisplaySelector(String displayName, ExpDataDisplay dataDisplay) {
    	if (displayMap.keySet().size() <= BUTTON_LIMIT) {
    		JButton displayBtnSelector = createButtonDisplaySelector(displayName, dataDisplay.getIcon());
    		displaySelectorPane.add(displayBtnSelector);
    		if (displayMap.keySet().size() == 1) {
    			showSelectedDisplay(displayName);
    		}
    	} else {
    		DisplaySelector selector = new DisplaySelector();
    		selector.setName(displayName);
    		selector.setIcon(dataDisplay.getIcon());
    		getComboDisplaySelector().addItem(selector);
    	}
	}

	private JButton createButtonDisplaySelector(String displayName, Icon icon) {
		JButton btnSelector = new JButton(displayName, icon);
		btnSelector.setActionCommand(displayName);
		btnSelector.addActionListener(this);
		return btnSelector;
	}
	
	private JComboBox getComboDisplaySelector() {
		if (comboDisplaySelector == null) {
			comboDisplaySelector = new JComboBox();
			comboDisplaySelector.setRenderer(new DisplaySelectorRenderer());
			displaySelectorPane.add(comboDisplaySelector);
		}
		return comboDisplaySelector;
	}

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displaySelectorPane = new com.linkare.rec.impl.newface.component.ResultsSelectorPane();
        displayPane = new javax.swing.JPanel();
        btnInfo = new javax.swing.JButton();

        setName("Form"); // NOI18N

        displaySelectorPane.setName("displaySelectorPane"); // NOI18N
        displaySelectorPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        displayPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        displayPane.setName("displayPane"); // NOI18N
        displayPane.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ResultsPane.class);
        btnInfo.setIcon(resourceMap.getIcon("btnInfo.icon")); // NOI18N
        btnInfo.setText(resourceMap.getString("btnInfo.text")); // NOI18N
        btnInfo.setBorderPainted(false);
        btnInfo.setName("btnInfo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPane, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(displaySelectorPane, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnInfo))
                    .addComponent(displaySelectorPane, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayPane, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInfo;
    private javax.swing.JPanel displayPane;
    private com.linkare.rec.impl.newface.component.ResultsSelectorPane displaySelectorPane;
    // End of variables declaration//GEN-END:variables


    @Override
	public void actionPerformed(ActionEvent e) {
    	if (log.isLoggable(Level.FINE)) {
			log.fine(e.toString());
		}
    	Object source = e.getSource();
    	String display = "";
		if (source instanceof JButton) {
			display = e.getActionCommand();
		} else if (source instanceof JComboBox) {
			display = ((JComboBox)source).getSelectedItem().toString();
		}
		showSelectedDisplay(display);
	}

	private void showSelectedDisplay(String displayName) {
		displayPane.removeAll();
		displayPane.add(displayMap.get(displayName));
		revalidate();
		repaint();
	}
    
	@Override
	public void dataModelEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataModelError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataModelStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataModelStartedNoData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataModelStoped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataModelWaiting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newSamples(NewExpDataEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
