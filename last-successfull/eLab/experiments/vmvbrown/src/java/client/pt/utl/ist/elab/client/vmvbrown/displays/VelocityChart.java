/*
 * VelocityChart.java
 *
 * Created on 20 de Mar�o de 2005, 15:21
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import javax.swing.SwingConstants;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author nomead
 */
public class VelocityChart extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5989131163269581980L;

	/** Creates a new instance of VelocityChart */
	public VelocityChart() {
		super();
	}

	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		final boolean vx = header.getSelectedHardwareParameterValue("vx").trim().equals("1") ? true : false;
		final boolean vy = header.getSelectedHardwareParameterValue("vy").trim().equals("1") ? true : false;
		final boolean vz = header.getSelectedHardwareParameterValue("vz").trim().equals("1") ? true : false;
		final boolean velModulus = header.getSelectedHardwareParameterValue("velModulus").trim().equals("1") ? true
				: false;

		if (vx || vy || vz || velModulus) { // grafico activo
			setChannelDisplayX(17); // t
			final int[] channels = getYChannels(vx, vy, vz, velModulus,
					header.getSelectedHardwareParameterValue("velQuad").trim().equals("1") ? true : false, false);
			if (channels != null) {
				for (int i = 0; i < channels.length; i++) {
					channels[i]--; // acerto para o XML
				}
				setChannelDisplayYArray(channels);
			}
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

			final javax.swing.JLabel label = new javax.swing.JLabel(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vmvbrown/resources/messages").getString("rec.exp.displays.inactive"),
					SwingConstants.CENTER);
			removeAll();
			add(label, gridBagConstraints);
			updateUI();
		}
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		super.setExpDataModel(model);
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}

	}

	private int[] getYChannels(final boolean _vx, final boolean _vy, final boolean _vz, final boolean _vMod,
			final boolean _vQuad, final boolean _vAbs) {
		int[] channels;

		int k = 0;
		if (_vx) {
			k++;
		}
		if (_vy) {
			k++;
		}
		if (_vz) {
			k++;
		}
		if (_vMod) {
			k++;
		}
		channels = new int[k];

		if (!_vAbs && !_vQuad) {
			int i = 0;
			if (_vx) {
				channels[i++] = Chart1.nameToChannels("t vs Vx")[1];
			}
			if (_vy) {
				channels[i++] = Chart1.nameToChannels("t vs Vy")[1];
			}
			if (_vz) {
				channels[i++] = Chart1.nameToChannels("t vs Vz")[1];
			}
			if (_vMod) {
				channels[i++] = Chart1.nameToChannels("t vs | v |")[1];
			}
			return channels;
		}
		if (!_vAbs) {
			int i = 0;
			if (_vx) {
				channels[i++] = Chart1.nameToChannels("t vs Vx^2")[1];
			}
			if (_vy) {
				channels[i++] = Chart1.nameToChannels("t vs Vy^2")[1];
			}
			if (_vz) {
				channels[i++] = Chart1.nameToChannels("t vs Vz^2")[1];
			}
			if (_vMod) {
				channels[i++] = Chart1.nameToChannels("t vs | v |^2")[1];
			}
			return channels;
		}
		if (!_vQuad) {
			int i = 0;
			if (_vx) {
				channels[i++] = Chart1.nameToChannels("t vs | Vx |")[1];
			}
			if (_vy) {
				channels[i++] = Chart1.nameToChannels("t vs | Vy |")[1];
			}
			if (_vz) {
				channels[i++] = Chart1.nameToChannels("t vs | Vz |")[1];
			}
			if (_vMod) {
				channels[i++] = Chart1.nameToChannels("t vs | v |")[1];
			}
			return channels;
		}
		return null;
	}
}