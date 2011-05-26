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
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultTableModelProxy extends javax.swing.table.DefaultTableModel implements ExpDataModelListener,
		ExpDataModelContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 283913067438836151L;
	/** Holds value of property expDataModel. */
	private ExpDataModel expDataModel = null;

	// BIG SILENT NOOP
	@Override
	public void dataModelWaiting() {
		// fireTableStructureChanged();
	}

	// BIG SILENT NOOP
	@Override
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
	@Override
	public Class getColumnClass(final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			if (columnIndex == 0) {
				return String.class;
			}
			return null;
		}

		/*
		 * if(columnIndex==0) return DateTime.class;
		 * 
		 * return PhysicsValue.class;
		 */
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
	@Override
	public int getColumnCount() {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 1;
		}

		return expDataModel.getChannelCount() * 2 + 4;
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
				return ReCResourceBundle.findString("ReCBaseUI$rec.bui.lbl.nodata");
			}
			return null;
		}
		if (columnIndex == 0) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.sample");
		}
		if (columnIndex == 1) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.date");
		}
		if (columnIndex == 2) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.time");
		}
		if (columnIndex == 3) {
			return ReCResourceBundle.findString("ReCBaseUI$rec.bui.table.model.column.milliseconds");
		}

		final int channelIndex = (int) Math.floor((columnIndex - 4.) / 2.);

		final String ch_name = ReCResourceBundle.findString(expDataModel.getChannelConfig(channelIndex)
				.getChannelName());
		final String multiplier = expDataModel.getChannelConfig(channelIndex).getSelectedScale().getMultiplier()
				.toString();
		final String ph_unit_symbol = expDataModel.getChannelConfig(channelIndex).getSelectedScale()
				.getPhysicsUnitSymbol();

		final String retorna = ch_name + " [" + multiplier + ph_unit_symbol + "]";

		if (columnIndex % 2 == 0) {
			return retorna;
		} else {
			return "\u03B5 " + retorna;
		}
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

		if (columnIndex == 0) {
			return "" + (rowIndex + 1);
		}
		if (columnIndex == 1) {
			return expDataModel.getTimeStamp(rowIndex).getDate().toString();
		}
		if (columnIndex == 2) {
			return expDataModel.getTimeStamp(rowIndex).getTime().toSimpleTimeString();
		}
		if (columnIndex == 3) {
			return "" + expDataModel.getTimeStamp(rowIndex).getTime().getMilis();
		}

		final int channelIndex = (int) Math.floor((columnIndex - 4.) / 2.);

		final PhysicsValue value = expDataModel.getValueAt(rowIndex, channelIndex);

		if (columnIndex % 2 == 0) {
			return value.getValue().toEngineeringNotation();
		} else {
			return value.getError().toEngineeringNotation();
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
		int oldLastNewSamples = lastnewsamples;
		lastnewsamples = evt.getSamplesEndIndex();
		fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(), oldLastNewSamples), lastnewsamples);
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

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	@Override
	public void dataModelStartedNoData() {
		fireTableDataChanged();
	}
}
