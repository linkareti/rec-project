/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira
 */
public class DefaultXYDatasetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5409998226905524387L;

	private int updateFrequency = 1;

	private HardwareAcquisitionConfig header = null;

	/** Creates a new instance of DefaultXYDatasetProxy */
	public DefaultXYDatasetProxy() {

	}

	public void dataModelRunning() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelStoped() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelEnded() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelError() {
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
	public void dataModelWaiting() {
	}

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
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 0;
		}

		return 1;
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

		String channelNameKey = expDataModel.getChannelConfig(getChannelDisplayX()).getChannelName();
		final String ch_nameX = ReCResourceBundle.findStringOrDefault(channelNameKey, channelNameKey);
		final String multiplierX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getPhysicsUnitSymbol();

		channelNameKey = expDataModel.getChannelConfig(getChannelDisplayY()).getChannelName();
		final String ch_nameY = ReCResourceBundle.findStringOrDefault(channelNameKey, channelNameKey);
		final String multiplierY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale()
				.getPhysicsUnitSymbol();

		return ch_nameX + " [" + multiplierX + ph_unit_symbolX + "] vs " + ch_nameY + " [" + multiplierY
				+ ph_unit_symbolY + "]";
	}

	/**
	 * Returns the number of items in a series.
	 * 
	 * @param series the series (zero-based index).
	 * 
	 * @return the number of items within the series.
	 */
	@Override
	public int getItemCount(final int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}

		if (expDataModel.getTotalSamples() == -1) {
			return 0;
		}
		return expDataModel.getTotalSamples();
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

		return (expDataModel.getValueAt(item, getChannelDisplayX()) != null) ? expDataModel
				.getValueAt(item, getChannelDisplayX()).getValueNumber().doubleValue() : 0;
	}

	/**
	 * Returns the y-value for an item within a series.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item (zero-based index).
	 * 
	 * @return the y-value.
	 */
	@Override
	public double getYValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}

		return (expDataModel.getValueAt(item, getChannelDisplayY()) != null) ? expDataModel
				.getValueAt(item, getChannelDisplayY()).getValueNumber().doubleValue() : 0;
	}

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplayX;

	/** Holds value of property channelDisplayY. */
	private int channelDisplayY;

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
	 * 
	 * @param channelDisplayX
	 * 
	 * @param channelDisplay New value of property channelDisplay.
	 */
	public void setChannelDisplayX(final int channelDisplayX) {
		this.channelDisplayX = channelDisplayX;
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
	}

	@Override
	public Number getX(final int series, final int item) {
		return Double.valueOf(getXValue(series, item));
	}

	@Override
	public Number getY(final int series, final int item) {
		return Double.valueOf(getYValue(series, item));
	}

	@Override
	public org.jfree.data.DomainOrder getDomainOrder() {
		return org.jfree.data.DomainOrder.NONE;
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
}
