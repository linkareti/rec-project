package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class TableSoundVelocityModelProxy extends com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy {

	/**
	 * Returns the number of columns in the model. A <code>JTable</code> uses
	 * this method to determine how many columns it should create and display by
	 * default.
	 * 
	 * @return the number of columns in the model
	 * @see #getRowCount
	 */
	public int getColumnCount() {
		if (colArray == null) {
			return 1;
		}
		return colArray.length;
	}

	/**
	 * Returns the name of the column at <code>columnIndex</code>. This is used
	 * to initialize the table's column header name. Note: this name does not
	 * need to be unique; two columns in a table can have the same name.
	 * 
	 * @param columnIndex the index of the column
	 * @return the name of the column
	 */
	public String getColumnName(int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			if (columnIndex == 0) {
				return ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.nodata", "No data available...");
			} else {
				return null;
			}
		}
		switch (columnIndex) {
		case 0:
			return ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.sampleNumber");
		case 1:
			return ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.acquisitionTime");
		case 2:
			return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.3.name");
		case 3:
			return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.4.name");
		case 4:
			return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.0.name");
		case 5:
			return ReCResourceBundle.findString("rec.exp.statsound.hardwareinfo.channel.1.name");
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return null;
		}
		if (columnIndex == 0) {
			// sample number
			return String.valueOf(rowIndex + 1);
		} else if (columnIndex == 1) {
			// acquisition time
			float time = 0;
			int i = 0;
			do {
				time += 1f / 11000;
			} while (++i <= rowIndex);
			// turn it into seconds
			time *= 1000;
			return String.valueOf(time);
		}
		PhysicsValue value = expDataModel.getValueAt(rowIndex, getColAtArray(columnIndex));
		if (value == null) {
			return null;
		}
		return value.getValue().toString();
	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
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
	public Class getColumnClass(int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return null;
		}
		return String.class;
	}
}