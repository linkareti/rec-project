/*
 * MyDefaultXYDataSetProxy.java
 *
 * Created on October 16, 2003, 11:48 AM
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class MultSeriesXYDataSetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2476365483222546333L;

	private int[] channelDisplayYArray;

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplayX;

	/** Holds value of property channelDisplayY. */
	private int channelDisplayY;

	private boolean errorOccurred = false;

	/** Creates a new instance of MyDefaultXYDataSetProxy */
	public MultSeriesXYDataSetProxy() {
	}

	public void dataModelRunning() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelStoped() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelEnded() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelError() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelStarted() {
		if (header == null && expDataModel != null) {
			header = expDataModel.getAcquisitionConfig();
		}
		fireDatasetChanged();
	}

	@Override
	public void dataModelStartedNoData() {
		if (header == null && expDataModel != null) {
			header = expDataModel.getAcquisitionConfig();
		}
		fireDatasetChanged();
	}

	@Override
	public void dataModelWaiting() {// BIG SILENT NOOP
	}

	private HardwareAcquisitionConfig header = null;

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			if (i % updateFrequency == 0 || i == (header.getTotalSamples() - 1)) {
				fireDatasetChanged();
				break;
			}
		}
	}

	/**
	 * Returns the number of series in the dataset.
	 * 
	 * @return the series count.
	 */
	@Override
	public int getSeriesCount() {
		if (expDataModel == null || !expDataModel.isDataAvailable() || getChannelDisplayYArray() == null) {
			return 0;
		} else {
			return getChannelDisplayYArray().length;
		}
	}

	/**
	 * Returns the name of a series.
	 * 
	 * @param series the series (zero-based index).
	 * 
	 * @return the name of the series.
	 */
	@Override
	public Comparable<?> getSeriesKey(final int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return null;
		}

		final String multiplierX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getPhysicsUnitSymbol();
		String channelNameKey = expDataModel.getChannelConfig(getChannelDisplayX())
				.getChannelName();
		final String ch_nameX = ReCResourceBundle.findStringOrDefault(channelNameKey,channelNameKey);

		final String multiplierY = expDataModel.getChannelConfig(getChannelDisplayYArray()[series]).getSelectedScale()
				.getMultiplier().toString(); 
		final String ph_unit_symbolY = expDataModel.getChannelConfig(getChannelDisplayYArray()[series]).getSelectedScale()
				.getPhysicsUnitSymbol();
		
		channelNameKey = expDataModel.getChannelConfig(getChannelDisplayYArray()[series])
				.getChannelName();
		final String ch_nameY = ReCResourceBundle.findStringOrDefault(channelNameKey,channelNameKey);

		return ch_nameX + " [" + multiplierX + ph_unit_symbolX + "] vs " + ch_nameY + " [" + multiplierY
				+ ph_unit_symbolY + "]";
	}

	private int updateFrequency = 1;

	/**
	 * Returns the number of items in a series.
	 * 
	 * @param series the series (zero-based index).
	 * 
	 * @return the number of items within the series.
	 */
	private int itemCount = 0;

	@Override
	public int getItemCount(final int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}

		if (expDataModel.getTotalSamples() == -1) {
			return 0;
		}

		/*
		 * if(header != null) { updateFrequency =
		 * header.getTotalSamples()/updatePercentage; }
		 * 
		 * if(updateFrequency < 1) { updateFrequency = 1; }
		 */

		if (!errorOccurred) {
			itemCount = expDataModel.getTotalSamples();
		}

		return itemCount;
	}

	/**
	 * Returns the x-value for an item within a series.
	 * <P>
	 * The implementation is responsible for ensuring that the x-values are
	 * presented in ascending order.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item (zero-based index).
	 * 
	 * @return the x-value.
	 */
	@Override
	public double getXValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}
		if (expDataModel.getValueAt(item, getChannelDisplayX()) == null) {
			return 0;
		}

		Number x = null;

		try {
			x = expDataModel.getValueAt(item, getChannelDisplayX()).getValueNumber();
		} catch (final Exception e) {
			errorOccurred = true;
			if (item > 0) {
				x = expDataModel.getValueAt(item - 1, getChannelDisplayX()).getValueNumber();
				itemCount--;
			} else {
				x = Double.valueOf(0);
			}
		}

		return x.doubleValue();
	}

	@Override
	public double getYValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()
				|| expDataModel.getValueAt(item, getChannelDisplayX()) == null) {
			return 0;
		} else {
			if (expDataModel.getValueAt(item, getChannelDisplayAtYArray(series)) == null) {
				return 0;
			}

			// I need to know if there was some error in the conversion to
			// number...because if it jfreechart will probably crash...
			Number y = null;

			try {
				y = expDataModel.getValueAt(item, getChannelDisplayAtYArray(series)).getValueNumber();
			} catch (final Exception e) {
				errorOccurred = true;
				if (item > 0) {
					y = expDataModel.getValueAt(item, getChannelDisplayAtYArray(series)).getValueNumber();
					itemCount--;
				} else {
					y = Double.valueOf(0);
				}
			}

			return y.doubleValue();
		}
	}

	/**
	 * Getter for property channelDisplayY.
	 * 
	 * @return Value of property channelDisplayY.
	 */
	public int getChannelDisplayY() {
		return channelDisplayY;
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayY(final int channelDisplayY) {
		this.channelDisplayY = channelDisplayY;
		setChannelDisplayYArray(new int[] { channelDisplayY });
	}

	/**
	 * Getter for property expDataModel.
	 * 
	 * @return Value of property expDataModel.
	 */
	public ExpDataModel getExpDataModel() {
		return expDataModel;
	}

	/**
	 * Setter for property expDataModel.
	 * 
	 * @param expDataModel New value of property expDataModel.
	 */
	public void setExpDataModel(final ExpDataModel expDataModel) {
		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
			fireDatasetChanged();
		}

	}

	/**
	 * Getter for property channelDisplay.
	 * 
	 * @return Value of property channelDisplay.
	 */
	public int getChannelDisplayX() {
		return channelDisplayX;
	}

	/**
	 * Setter for property channelDisplay.
	 * @param channelDisplayX 
	 * 
	 * @param channelDisplay New value of property channelDisplay.
	 */
	public void setChannelDisplayX(final int channelDisplayX) {
		this.channelDisplayX = channelDisplayX;
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int getChannelDisplayAtYArray(final int series) {
		return channelDisplayYArray[series];
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int[] getChannelDisplayYArray() {
		return channelDisplayYArray;
	}

	/**
	 * Setter for property channelDisplayY.
	 * @param channelDisplayYArray 
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public void setChannelDisplayYArray(final int[] channelDisplayYArray) {
		this.channelDisplayYArray = channelDisplayYArray;
	}

	private final int updatePercentage = 10;

	/** Deprecated!! Use getUpdateFrequency */
	public int getUpdatePercentage() {
		return updateFrequency;
	}

	/** Deprecated!! Use setUpdateFrequency */
	public void setUpdatePercentage(final int updatePercentage) {
		// this.updatePercentage = updatePercentage;
		setUpdateFrequency(updatePercentage);
	}

	public int getUpdateFrequency() {
		return updateFrequency;
	}

	/** Update from updateFrequency to updateFrequency points */
	public void setUpdateFrequency(int updateFrequency) {
		if (updateFrequency < 1) {
			updateFrequency = 1;
		}
		this.updateFrequency = updateFrequency;
	}

	@Override
	public Number getX(final int param, final int param1) {
		return Double.valueOf(getXValue(param, param1));
	}

	@Override
	public Number getY(final int param, final int param1) {
		return Double.valueOf(getYValue(param, param1));
	}

	@Override
	public org.jfree.data.DomainOrder getDomainOrder() {
		return org.jfree.data.DomainOrder.NONE;
	}
}
