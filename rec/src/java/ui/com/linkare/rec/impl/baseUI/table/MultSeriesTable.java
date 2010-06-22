package com.linkare.rec.impl.baseUI.table;

import com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class MultSeriesTable extends DefaultExperimentDataTable {
	
	/** Generated UID */
	private static final long serialVersionUID = -8194014315892089352L;
	
	private MultSeriesTableModelProxy defaultTableModelProxy = null;
	
	/** Creates new form DefaultExperimentDataTable */
	public MultSeriesTable() {
		this(new MultSeriesTableModelProxy());
	}
	
	/**
	 * Creates the <code>MultSeriesTable</code>.
	 * @param defaultTableModelProxy
	 */
	public MultSeriesTable(MultSeriesTableModelProxy defaultTableModelProxy) {
		super();
		
		this.defaultTableModelProxy = defaultTableModelProxy;
		setActualTableModel(this.defaultTableModelProxy);
		setExpDataModelContainer(this.defaultTableModelProxy);
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param col 
	 * @param channelDisplayY New value of property channelDisplayY.
	 * @return 
	 */
	public int getColAtArray(int col) {
		return defaultTableModelProxy.getColAtArray(col);
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 * @return 
	 */
	public int[] getColArray() {
		return defaultTableModelProxy.getColArray();
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param colArray 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setColArray(int[] colArray) {
		defaultTableModelProxy.setColArray(colArray);
	}
	
}
