/* 
 * OpticaTableModelProxy.java created on 6 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.wpprague;

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
public class WorldPendulumTableModelProxy extends DefaultTableModel implements ExpDataModelListener,
		com.linkare.rec.impl.client.experiment.ExpDataModelContainer {

	/** Generated UID */
	private static final long serialVersionUID = 9080219184984549795L;

	/** Holds value of property expDataModel. */
	private ExpDataModel expDataModel = null;

	// BIG SILENT NOOP
	@Override
	public void dataModelWaiting() {
		//fireTableStructureChanged();
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
	@SuppressWarnings("unchecked")
	public Class<String> getColumnClass(final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			if (columnIndex == 0) {
				return String.class;
			}
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
	@Override
	public int getColumnCount() {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 1;
		}

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
	@Override
	public String getColumnName(final int columnIndex) {
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			if (columnIndex == 0) {
				return ReCResourceBundle.findStringOrDefault("ReCUI$rec.ui.lbl.nodata","ReCUI$rec.ui.lbl.nodata");
			}
			return null;
		}	 
		
		int channelIndex = columnIndex;
		
		/*
		* Jump "Chanel 2" (gravity value)
		* Col 1 = samples num; col 2 = Period; col 3 = Period Error (instead of gravity)
		* 
		* Also: Add the velocity error
		* col 3 = Vel; col 4 = Vel error; col 5 = Temperature
		*/
		if (columnIndex == 2 || columnIndex > 3) channelIndex = columnIndex - 1;

		final String channelNameKey = expDataModel.getChannelConfig(channelIndex).getChannelName();
		final String ch_name = ReCResourceBundle.findStringOrDefault(channelNameKey,channelNameKey);
				
		final String multiplier = expDataModel.getChannelConfig(channelIndex).getSelectedScale().getMultiplier()
				.toString();
		
		final String ph_unit_symbol = expDataModel.getChannelConfig(channelIndex).getSelectedScale()
				.getPhysicsUnitSymbol();
		
				
		final String retorna = ch_name + " [" + multiplier + ph_unit_symbol + "]";
		
		if (columnIndex == 2 || columnIndex == 4) {
			return "\u03B5 " + retorna;
		} else {
			return retorna;
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

//  É usado o primeiro canal que tem sempre o número das contagens no World Pendulum.
//		if (columnIndex == 0) {
//			return String.valueOf(rowIndex + 1);
//		}
		
		
		//
		//Jump "Chanel 2" (gravity value)
		//Col 1 = samples num; col 2 = Period; col 3 = Period Error (instead of gravity)
		//
		//Also: Add the velocity error
		//col 3 = Vel; col 4 = Vel error; col 5 = Temperature
		//
		
		int channelIndex = columnIndex;
		if (columnIndex == 2 || columnIndex > 3) channelIndex = columnIndex-1;

		
		final PhysicsValue value = expDataModel.getValueAt(rowIndex, channelIndex);
		
		
		//O primeiro valor pode aparecer arredondado, 
		//  os outros dá mais jeito aparecer com várias casas decimais 
		if (columnIndex == 0){
			return value.getValue().toEngineeringNotation();
		}
		
		if (columnIndex == 2 || columnIndex == 4){
			return value.getError();
		}
		else{
			return value.getValue();
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
		fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(), lastnewsamples),
				(lastnewsamples = evt.getSamplesEndIndex()));
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

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		fireTableStructureChanged();
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
	}

	@Override
	public void dataModelStartedNoData() {
	}
}
