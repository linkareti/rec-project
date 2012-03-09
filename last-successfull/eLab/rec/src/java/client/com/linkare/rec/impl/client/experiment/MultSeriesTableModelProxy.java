/*
 * DefaultTableModelProxy.java
 *
 * Created on 7 de Maio de 2003, 17:17
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Andr�
 */
public class MultSeriesTableModelProxy extends javax.swing.table.DefaultTableModel implements ExpDataModelListener,
		com.linkare.rec.impl.client.experiment.ExpDataModelContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5884746425826302252L;

	/** Holds value of property expDataModel. */
	protected ExpDataModel expDataModel = null;

	protected int[] colArray;

	@Override
	public void dataModelStoped() {
		// BIG SILENT NOOP
	}

	@Override
	public void dataModelEnded() {
		// BIG SILENT NOOP
	}

	@Override
	public void dataModelError() {
		// BIG SILENT NOOP
	}

	@Override
	public void dataModelStarted() {
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	@Override
	public void dataModelStartedNoData() {
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	@Override
	public void dataModelWaiting() {
		// BIG SILENT NOOP
	}

	/**
	 * Returns the most specific superclass for all the cell values in the
	 * column. This is used by the <code>JTable</code> to set up a default
	 * renderer and editor for the column.
	 * 
	 * @param columnIndex the index of the column
	 * @return the common ancestor class of the object values in the model.
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if (columnIndex > 0) {
			return Integer.class;
		} else {
			return String.class;
		}
	}

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
		return colArray.length * 2;
	}

	/**
	 * Returns the name of the column at <code>columnIndex</code>. This is used
	 * to initialize the table's column header name. Note: this name does not
	 * need to be unique; two columns in a table can have the same name.
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

		final int channelIndex = getColAtArray(columnIndex / 2);

		if (columnIndex % 2 == 0) {
			final String ch_name = ReCResourceBundle.findString(expDataModel.getChannelConfig(channelIndex)
					.getChannelName());
			final String multiplier = expDataModel.getChannelConfig(channelIndex).getSelectedScale().getMultiplier()
					.toString();
			final String ph_unit_symbol = expDataModel.getChannelConfig(channelIndex).getSelectedScale()
					.getPhysicsUnitSymbol();
			return ch_name + "[" + multiplier + ph_unit_symbol + "]";
		} else {
			if ((columnIndex / 2) >= 0 && expDataModel.getChannelConfig(channelIndex) != null) {
				final String ch_name = ReCResourceBundle.findString(expDataModel.getChannelConfig(channelIndex)
						.getChannelName());
				return "\u03B5 " + ch_name;
			}
		}

		return null;
	}

	/**
	 * Returns the number of rows in the model. A <code>JTable</code> uses this
	 * method to determine how many rows it should display. This method should
	 * be quick, as it is called frequently during rendering.
	 * 
	 * @return the number of rows in the model
	 * @see #getColumnCount
	 */

	@Override
	public int getRowCount() {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 0;
		}
		if (expDataModel.getTotalSamples() == -1) {
			return 0;
		}
		// return lastnewsamples;
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
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return null;
		}

		final PhysicsValue value = expDataModel.getValueAt(rowIndex, getColAtArray(columnIndex / 2));
		if (value == null) {
			// removeRow(rowIndex);
			return null;
		}

		if (columnIndex % 2 == 0) {
			return value.getValue().toString();
		} else {
			return value.getError().toString();
		}
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
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	private int lastnewsamples = 0;

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(), lastnewsamples), evt.getSamplesEndIndex());
		lastnewsamples = evt.getSamplesEndIndex();

		/*
		 * for(int i=evt.getSamplesStartIndex(); i<evt.getSamplesEndIndex();
		 * i++) { for(int j=0; j<colArray.length; j++) {
		 * if(expDataModel.getValueAt(i, colArray[j]) != null) {
		 * fireTableRowsInserted(lastnewsamples, lastnewsamples);
		 * lastnewsamples++; break; } } }
		 */
	}

	/**
	 * Getter for property expDataModel.
	 * 
	 * @return Value of property expDataModel.
	 */
	@Override
	public ExpDataModel getExpDataModel() {
		return expDataModel;
	}

	/**
	 * Setter for property expDataModel.
	 * 
	 * @param expDataModel New value of property expDataModel.
	 */
	@Override
	public void setExpDataModel(final ExpDataModel expDataModel) {
		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
			fireTableStructureChanged();
			fireTableDataChanged();
		}
	}

	/**
	 * Setter for property channelDisplayY.
	 * @param col 
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 * @return 
	 */
	public int getColAtArray(final int col) {
		return colArray[col];
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int[] getColArray() {
		return colArray;
	}

	/**
	 * Setter for property channelDisplayY.
	 * @param colArray 
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public void setColArray(final int[] colArray) {
		this.colArray = colArray;
	}

}
