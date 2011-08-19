package pt.utl.ist.elab.client.statsound.displays;

import pt.utl.ist.elab.client.statsound.TypeOfExperiment;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class StatsoundTableModelProxy extends com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy {

	private static final long serialVersionUID = 1258510618135751233L;

	private TypeOfExperiment typeOfExperiment;

	private static final String EXPERIMENT_TYPE = "experiment.type";

	/**
	 * Returns the number of columns in the model. A <code>JTable</code> uses
	 * this method to determine how many columns it should create and display by
	 * default.
	 * 
	 * @return the number of columns in the model
	 * @see #getRowCount
	 */
	@Override
	public int getColumnCount() {
		if (colArray == null) {
			return 1;
		}
		return colArray.length;
	}

	/**
	 * Returns the name of the column at <code>columnIndex</code>. This is used
	 * to initialise the table's column header.
	 * 
	 * Note: this name does not need to be unique! Two columns in a table can
	 * have the same name.
	 * 
	 * @param columnIndex the index of the column
	 * @return the name of the column
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			if (columnIndex == 0) {
				return ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.nodata", "No data available...");
			} else {
				return null;
			}
		}
		final String experimentTypeParameter = getExpDataModel().getAcquisitionConfig()
				.getSelectedHardwareParameterValue(EXPERIMENT_TYPE);
		typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);
		switch (columnIndex) {
		case 0:
			return ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.sampleNumber");
		case 1:
			return ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.acquisitionTime");
		case 2:
			switch (typeOfExperiment) {
			case SOUND_VELOCITY:
				// wave1
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.3.name");
			case STATSOUND_VARY_FREQUENCY:
				// vrms1
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.1.name");
			case STATSOUND_VARY_PISTON:
				// position
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.0.name");
			default:
				return "Unknown for " + typeOfExperiment + " in index 2";
			}
		case 3:
			switch (typeOfExperiment) {
			case SOUND_VELOCITY:
				// wave2
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.4.name");
			case STATSOUND_VARY_FREQUENCY:
				// vrms2
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.2.name");
			case STATSOUND_VARY_PISTON:
				// vrms1
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.1.name");
			default:
				return "Unknown for " + typeOfExperiment + " in index 3";
			}
		case 4:
			switch (typeOfExperiment) {
			case STATSOUND_VARY_FREQUENCY:
				// frequency
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.5.name");
			case STATSOUND_VARY_PISTON:
				// vrms2
				return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.2.name");
			default:
				return "Unknown for " + typeOfExperiment + " in index 4";
			}
		}
		return "?";
	}

	/**
	 * Returns the value for the cell at <code>columnIndex</code> and
	 * <code>rowIndex</code>.
	 * 
	 * @param rowIndex the row whose value is to be queried
	 * @param columnIndex the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return null;
		}
		if (columnIndex == 0) {
			// sample number
			return String.valueOf(rowIndex + 1);
		} else if (columnIndex == 1) {
			return getAcquisitionTimeInMicros(rowIndex);
		}
		final PhysicsValue value = expDataModel.getValueAt(rowIndex, getColAtArray(columnIndex));
		if (value == null) {
			return null;
		}
		return value.getValue().toString();
	}

	private long getAcquisitionTimeInMicros(final int rowIndex) {
		return expDataModel.getTimeStamp(0).getElapsedTimeInMicros(expDataModel.getTimeStamp(rowIndex));
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		fireTableStructureChanged();
		// super doesn't have this method defined
	}

	@Override
	public void dataModelStarted() {
		fireTableStructureChanged();
	}

	@Override
	public void dataModelStartedNoData() {
		fireTableStructureChanged();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class getColumnClass(final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return null;
		}
		return String.class;
	}

	/**
	 * @return the typeOfExperiment
	 */
	public TypeOfExperiment getTypeOfExperiment() {
		return typeOfExperiment;
	}

	/**
	 * @param typeOfExperiment the typeOfExperiment to set
	 */
	public void setTypeOfExperiment(TypeOfExperiment typeOfExperiment) {
		this.typeOfExperiment = typeOfExperiment;
	}
}