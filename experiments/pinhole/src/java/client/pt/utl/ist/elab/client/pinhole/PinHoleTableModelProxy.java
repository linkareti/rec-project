/* 
 * PinHoleTableModelProxy.java created on 6 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.pinhole;

import javax.swing.table.DefaultTableModel;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author npadriano
 */
public class PinHoleTableModelProxy extends DefaultTableModel implements ExpDataModelListener, com.linkare.rec.impl.client.experiment.ExpDataModelContainer {

	/** Generated UID */
	private static final long serialVersionUID = 9080219184984549795L;
	
	/** Holds value of property expDataModel. */
	private ExpDataModel expDataModel = null;

	// BIG SILENT NOOP
	public void dataModelWaiting() {
		fireTableStructureChanged();
	}

	// BIG SILENT NOOP
	public void dataModelStoped() {
		// fireTableDataChanged();
	}

	/**
	 * Returns the most specific superclass for all the cell values in the
	 * column. This is used by the <code>JTable</code> to set up a default
	 * renderer and editor for the column.
	 * 
	 * @param columnIndex the index of the column
	 * @return the common ancestor class of the object values in the model.
	 */
	@SuppressWarnings("unchecked")
	public Class<String> getColumnClass(int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
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
		if (expDataModel == null || !expDataModel.isDataAvailable())
			return 1;

		return expDataModel.getChannelCount() + 1;
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
				return ReCResourceBundle.findStringOrDefault("ReCUI$rec.ui.lbl.nodata","ReCUI$rec.ui.lbl.nodata");
			}
			return null;
		}
		if (columnIndex == 0) {
			return ReCResourceBundle.findStringOrDefault("ReCUI$rec.ui.table.model.column.sample","ReCUI$rec.ui.table.model.column.sample");
		}
		
		int channelIndex = columnIndex - 1;

		String multiplier = expDataModel.getChannelConfig(channelIndex).getSelectedScale().getMultiplier().toString();
		String ph_unit_symbol = expDataModel.getChannelConfig(channelIndex).getSelectedScale().getPhysicsUnitSymbol();
		String ch_name = expDataModel.getChannelConfig(channelIndex).getChannelName();

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
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 0;
		}

		if (expDataModel.getTotalSamples() == -1)
			return 0;
		return expDataModel.getTotalSamples();
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
		if (expDataModel == null || !expDataModel.isDataAvailable())
			return null;

		if (columnIndex == 0) {
			return String.valueOf(rowIndex + 1);
		}
		int channelIndex = columnIndex - 1;
		PhysicsValue value = expDataModel.getValueAt(rowIndex, channelIndex);
		return value.getValue().toEngineeringNotation();
	}

	/**
	 * Returns true if the cell at <code>rowIndex</code> and
	 * <code>columnIndex</code> is editable. Otherwise, <code>setValueAt</code>
	 * on the cell will not change the value of that cell.
	 * 
	 * @param rowIndex the row whose value to be queried
	 * @param columnIndex the column whose value to be queried
	 * @return true if the cell is editable
	 * @see #setValueAt
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	private int lastnewsamples = 0;

	public void newSamples(NewExpDataEvent evt) {
		fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(), lastnewsamples), (lastnewsamples = evt
				.getSamplesEndIndex()));
	}

	/**
	 * Getter for property expDataModel.
	 * 
	 * @return Value of property expDataModel.
	 */
	public ExpDataModel getExpDataModel() {
		return this.expDataModel;
	}

	/**
	 * Setter for property expDataModel.
	 * 
	 * @param expDataModel New value of property expDataModel.
	 */
	public void setExpDataModel(ExpDataModel expDataModel) {
		if (expDataModel != null)
			expDataModel.removeExpDataModelListener(this);

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
			fireTableStructureChanged();
			fireTableDataChanged();
		}

	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
		fireTableStructureChanged();
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
		fireTableStructureChanged();
	}

	public void dataModelStartedNoData() {
	}
}
