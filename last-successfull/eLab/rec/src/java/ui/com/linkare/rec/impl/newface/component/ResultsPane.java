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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.application.Action;

import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.utils.DataConfigInternationalization;

/**
 * 
 * @author hfernandes
 */
public class ResultsPane extends AbstractContentPane implements ExpDataModelListener, ActionListener {

	private static final Logger log = Logger.getLogger(ResultsPane.class.getName());

	private class DisplaySelector {

		private Icon icon;

		private String name;

		private DisplaySelector() {
			super();
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
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

			// Value must be an Apparatus
			displaySelector = (DisplaySelector) value;

			if (displaySelector != null) {

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
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

	private static final String DISPLAY_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.display", "Display");

	private static final int BUTTON_LIMIT = 2;

	/** Holds value of property expDataModel. */
	private ExpDataModel experimentDataModel;

	private Map<String, Integer> displaySelectorNames = new HashMap<String, Integer>();

	private Map<String, ExpDataDisplay> displayMap = new HashMap<String, ExpDataDisplay>();

	private JComboBox comboDisplaySelector;

	private JTextArea experimentInfoTextArea;

	private ExperimentHistoryUINode experimentHistoryUI;

	private JScrollPane experimentInfoScrollPane;

	private ResultsActionBar resultsActionBar;

	/**
	 * Creates new form ResultsPane
	 */
	public ResultsPane() {
		initComponents();
	}

	/**
	 * Creates new form ResultsPane
	 * 
	 * @param resultsActionBar
	 */
	public ResultsPane(ResultsActionBar resultsActionBar) {
		initComponents();
		this.resultsActionBar = resultsActionBar;
	}

	public void setExperimentResults(ExperimentHistoryUINode historyUINode, ExpDataModel expDataModel,
			final List<ExpDataDisplay> expDataDisplays) {
		setExperimentHistoryUI(historyUINode);
		setExperimentDataModel(expDataModel);
		addExperimentDataDisplays(expDataDisplays);
		revalidate();
		repaint();
	}

	private void setExperimentHistoryUI(ExperimentHistoryUINode experimentHistoryUI) {
		this.experimentHistoryUI = experimentHistoryUI;
	}

	/**
	 * Getter for property expDataModel.
	 * 
	 * @return Value of property expDataModel.
	 */
	public ExpDataModel getExperimentDataModel() {
		return this.experimentDataModel;
	}

	/**
	 * Setter for property expDataModel.
	 * 
	 * @param expDataModel
	 *            New value of property expDataModel.
	 */
	private void setExperimentDataModel(ExpDataModel expDataModel) {
		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}
		this.experimentDataModel = expDataModel;
		if (expDataModel != null) {
			expDataModel.addExpDataModelListener(this);
		}
//		resultsActionBar.setExpDataModel(experimentDataModel); // FIXME
	}

	private void addExperimentDataDisplays(List<ExpDataDisplay> displays) {
		String firstDisplay = null;
		for (ExpDataDisplay display : displays) {
			addExperimentDataDisplay(display);
			if (firstDisplay == null) {
				firstDisplay = display.getName();
			}
		}
		// TODO check first display name
		showSelectedDisplay(firstDisplay);
	}

	public void clearExperimentResults() {
		if (experimentHistoryUI != null) {
			log.fine("Clearing last experiment results");

			displayPane.removeAll();
			displaySelectorPane.removeAll();

			displaySelectorNames.clear();
			displayMap.clear();

			experimentHistoryUI = null;
			comboDisplaySelector = null;
		}
	}

	private void addExperimentDataDisplay(ExpDataDisplay dataDisplay) {
		try {
			//final Icon icon = dataDisplay.getIcon();
			String displayName = dataDisplay.getName();
			if (displayName == null) {
				displayName = DISPLAY_STR;
			}
			String uniquedisplayName = getUniqueDisplayName(displayName);
			addDataDisplay(uniquedisplayName, dataDisplay);
			dataDisplay.setExpDataModel(experimentDataModel);
			
		} catch (Exception e) {
			log.log(Level.SEVERE, "Couldn't add DataDisplay Component " + dataDisplay, e);
		}
	}

	private String getUniqueDisplayName(String displayName) {
		String displayNameFinal;

		if (displaySelectorNames.keySet().contains(displayName)) {
			Integer count = displaySelectorNames.get(displayName);
			displaySelectorNames.put(displayName, ++count);
			displayNameFinal = displayName + count;
		} else {
			displaySelectorNames.put(displayName, Integer.valueOf(0));
			displayNameFinal = displayName;
		}
		return displayNameFinal;
	}

	private void addDataDisplay(String displayName, ExpDataDisplay dataDisplay) {
		displayMap.put(displayName, dataDisplay);
		addDisplaySelector(displayName, dataDisplay);
	}

	private void addDisplaySelector(String displayName, ExpDataDisplay dataDisplay) {
		if (displayMap.keySet().size() <= BUTTON_LIMIT) {
			// Add Button Selector 
			log.fine("Adding button selector");
			JButton displayBtnSelector = createButtonDisplaySelector(displayName, dataDisplay.getIcon());
			displaySelectorPane.add(displayBtnSelector);
		} else {
			// Add Combo Entry Selector
			log.fine("Adding combo entry selector");
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
			comboDisplaySelector.addActionListener(this);
			displaySelectorPane.add(comboDisplaySelector);
		}
		return comboDisplaySelector;
	}

	@Action
	public void showExperimentInfo() {
		JTextArea infoTextArea = getExperimentInfoTextArea();
		try {
			String acquisitionInfo = "Acquisition info is not available";
			if (experimentHistoryUI.getProducerWrapper() != null && experimentHistoryUI.getProducerWrapper().getAcquisitionHeader() != null) {
				acquisitionInfo = DataConfigInternationalization.toString(experimentHistoryUI.getProducerWrapper().getAcquisitionHeader());
//				acquisitionInfo = experimentHistoryUI.getProducerWrapper().getAcquisitionHeader().toString();
			}
			infoTextArea.setText(acquisitionInfo);
		} catch (NotAvailableException e) {
			String msg = "AcquisitionHeader is not Available.";
			log.log(Level.SEVERE, msg, e);
			infoTextArea.setText(msg);
		}
		setDisplayPane(getExperimentInfoScrollPane(infoTextArea));
	}

	private void showSelectedDisplay(String displayName) {
		JComponent display = displayMap.get(displayName).getDisplay();
		// FIXME
//		resultsActionBar.setActiveExpDataDisplay(display);
		setDisplayPane(display);
	}

	private void setDisplayPane(JComponent component) {
		displayPane.removeAll();
		displayPane.add(component);
		revalidate();
		repaint();
	}

	private JTextArea getExperimentInfoTextArea() {
		if (experimentInfoTextArea == null) {
			experimentInfoTextArea = new JTextArea();
		}
		return experimentInfoTextArea;
	}

	private JScrollPane getExperimentInfoScrollPane(Component component) {
		if (experimentInfoScrollPane == null) {
			experimentInfoScrollPane = new JScrollPane();
		}
		experimentInfoScrollPane.setViewportView(component);
		return experimentInfoScrollPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String display = "";
		if (source instanceof JButton) {
			display = e.getActionCommand();
		} else if (source instanceof JComboBox) {
			display = ((JComboBox) source).getSelectedItem().toString();
		}
		showSelectedDisplay(display);
	}

	//	private static final String RUNNING_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.running", "Running");
	//	private static final String STATUS_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.experiment",
	//	"Experiment status");
	//	private static final String PAUSED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.paused", "Paused");
	//	private static final String STOPED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.stoped", "Stopped");
	//	private static final String WAITING_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.waitingData",
	//	"Waiting for data...");
	//	private static final String NODATA_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.nodata",
	//	"The experiment started but no data was available to fetch");
	//	private static final String STARTED_NODATA_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.startedNoData",
	//	"Started, no data available yet...");
	//	private static final String DATA_ENDED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.ended", "Data Ended");
	//	private static final String DATA_MODEL_ERROR1_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.error.dataModel",
	//	"Data Model error!");
	//	private static final String DATA_MODEL_ERROR2_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.error.dataModel.2",
	//	"Serious error in the data model, data was lost!");
	//	private static final String EXPERIMENT_INFO = "$experiment-info";

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelStoped() {
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public void newSamples(NewExpDataEvent evt) {
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displaySelectorPane = new com.linkare.rec.impl.newface.component.ResultsSelectorPane();
        displayPane = new javax.swing.JPanel();
        btnInfo = new javax.swing.JButton();

        setName("Form"); // NOI18N

        displaySelectorPane.setName("displaySelectorPane"); // NOI18N
        displaySelectorPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        displayPane.setName("displayPane"); // NOI18N
        displayPane.setLayout(new java.awt.BorderLayout());

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(ResultsPane.class, this);
        btnInfo.setAction(actionMap.get("showExperimentInfo")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ResultsPane.class);
        btnInfo.setBackground(resourceMap.getColor("btnInfo.background")); // NOI18N
        btnInfo.setIcon(resourceMap.getIcon("btnInfo.icon")); // NOI18N
        btnInfo.setText(resourceMap.getString("btnInfo.text")); // NOI18N
        btnInfo.setBorderPainted(false);
        btnInfo.setName("btnInfo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(displaySelectorPane, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(displayPane, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(displaySelectorPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInfo;
    private javax.swing.JPanel displayPane;
    private com.linkare.rec.impl.newface.component.ResultsSelectorPane displaySelectorPane;
    // End of variables declaration//GEN-END:variables

}
