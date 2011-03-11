package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.baseUI.table.MultSeriesTable;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class TableSoundVelocityModelProxy extends MultSeriesTable {

	/**
	 * Returns the most specific superclass for all the cell values in the
	 * column. This is used by the <code>JTable</code> to set up a default
	 * renderer and editor for the column.
	 * 
	 * @param columnIndex the index of the column
	 * @return the common ancestor class of the object values in the model.
	 */
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) {
		if (getExpDataModel() == null || !getExpDataModel().isDataAvailable()) {
			if (columnIndex == 0)
				return String.class;
			return null;
		}
		return String.class;
	}

	/**
	 * Returns the number of columns in the model. A <code>JTable</code> uses
	 * this method to determine how many columns it should create and display by
	 * default.
	 * 
	 * @return the number of columns in the model
	 * @see #getRowCount
	 */
	public int getColumnCount() {
		if (getExpDataModel() == null || !getExpDataModel().isDataAvailable()) {
			return 1;
		}

		// 2 because of the added sample number and the time
		return getExpDataModel().getChannelCount() + 2;
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
		if (getExpDataModel() == null || !getExpDataModel().isDataAvailable()) {
			if (columnIndex == 0) {
				return ReCResourceBundle.findString("ReCBaseUI$rec.bui.lbl.nodata");
			}
			return null;
		}
		if (columnIndex == 2) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.sample");
		} else if (columnIndex == 6) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.sample");
		}

		int channelIndex = columnIndex - 1;

		String ch_name = ReCResourceBundle
				.findString(getExpDataModel().getChannelConfig(channelIndex).getChannelName());
		String multiplier = getExpDataModel().getChannelConfig(channelIndex).getSelectedScale().getMultiplier()
				.toString();
		String ph_unit_symbol = getExpDataModel().getChannelConfig(channelIndex).getSelectedScale()
				.getPhysicsUnitSymbol();

		return ch_name + " [" + multiplier + ph_unit_symbol + "]";
	}

	/**
	 * Returns the number of rows in the model. A <code>JTable</code> uses this
	 * method to determine how many rows it should display. This method should
	 * be quick, as it is called frequently during rendering.
	 * 
	 * @return the number of rows in the model
	 * @see #getColumnCount
	 */
	public int getRowCount() {
		if (getExpDataModel() == null || !getExpDataModel().isDataAvailable()) {
			return 0;
		}

		if (getExpDataModel().getTotalSamples() == -1)
			return 0;
		return getExpDataModel().getTotalSamples();
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
		if (getExpDataModel() == null || !getExpDataModel().isDataAvailable())
			return null;

		if (columnIndex == 2) {
			return String.valueOf(rowIndex + 1);
		} else if (columnIndex == 6) {
			float time = 0f;
			for (int i = 1; i < rowIndex; i++) {
				time += (1 / 11000);
			}
			// turn it into miliseconds
			time *= 1000;
			return String.valueOf(time);
		}
		int channelIndex = columnIndex - 1;
		PhysicsValue value = getExpDataModel().getValueAt(rowIndex, channelIndex);
		return value.getValue().toEngineeringNotation();
	}
}
