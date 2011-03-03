/*
 * DefaultTableModelProxy.java
 *
 * Created on 7 de Maio de 2003, 17:17
 */

package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André
 */
public class MyTableModelProxy extends com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy {

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
		String ch_name = ReCResourceBundle.findString(expDataModel.getChannelConfig(getColAtArray(columnIndex))
				.getChannelName());
		return ch_name + "["
				+ expDataModel.getChannelConfig(getColAtArray(columnIndex)).getSelectedScale().getPhysicsUnitSymbol()
				+ "]";
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

	public void dataModelStarted() {
		fireTableStructureChanged();
	}

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
