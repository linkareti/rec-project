/*
 * Chart3.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author nomead
 */
public class Chart3 extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	private StaticChart chart;

	/** Creates a new instance of Chart3 */
	public Chart3() {
		super();
	}

	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		String graph3 = header.getSelectedHardwareParameterValue("graph3");

		if (!graph3.equalsIgnoreCase("")) { // grafico activo
			if (header.getSelectedHardwareParameterValue("graph3Med").trim().equals("1") ? true : false) {
				byte[] channels = Chart1.nameToChannels(graph3);
				setChannelDisplayX(channels[0] - 1); // acerto para o XML
				setChannelDisplayY(channels[1] - 1); // acerto para o XML
				super.dataModelStartedNoData();
			} else {
				setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints gridBagConstraints;
				gridBagConstraints = new java.awt.GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.gridheight = 1;
				gridBagConstraints.gridwidth = 1;
				gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
				gridBagConstraints.weightx = 1;
				gridBagConstraints.weighty = 1;

				chart = new StaticChart(Integer.parseInt(header.getSelectedHardwareParameterValue("w")), Integer
						.parseInt(header.getSelectedHardwareParameterValue("h")));
				removeAll();
				add(chart, gridBagConstraints);
				updateUI();
			}
		} else {
			setLayout(new java.awt.GridBagLayout());
			java.awt.GridBagConstraints gridBagConstraints;
			gridBagConstraints = new java.awt.GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;

			javax.swing.JLabel label = new javax.swing.JLabel(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
					"rec.exp.displays.inactive"), javax.swing.JLabel.CENTER);
			removeAll();
			add(label, gridBagConstraints);
			updateUI();
		}
	}

	public void newSamples(NewExpDataEvent evt) {
		if (chart != null) {
			for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++)
				if (model.getValueAt(i, 20) != null)
					chart.makeImage(model.getValueAt(i, 20).getValue().getByteArrayValue().getData());
		} else
			super.newSamples(evt);
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		super.setExpDataModel(model);
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}

}
