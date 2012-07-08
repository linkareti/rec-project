/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Jos� Pedro Pereira
 */
public class DefaultDatasetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6749791719295215655L;

	/** Creates a new instance of DefaultXYDatasetProxy */
	public DefaultDatasetProxy() {

	}

	@Override
	public void dataModelWaiting() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelStoped() {
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
		fireDatasetChanged();
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		fireDatasetChanged();
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

		final String multiplier = expDataModel.getChannelConfig(getChannelDisplay()).getSelectedScale().getMultiplier()
				.toString();
		final String ph_unit_symbol = expDataModel.getChannelConfig(getChannelDisplay()).getSelectedScale()
				.getPhysicsUnitSymbol();
		final String ch_name = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplay())
				.getChannelName());

		return ch_name + " [" + multiplier + ph_unit_symbol + "]";
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
		if (expDataModel.getAcquisitionConfig() == null || expDataModel == null || !expDataModel.isDataAvailable()
				|| series >= expDataModel.getChannelCount()) {
			return 0;
		}

		return expDataModel.getAcquisitionConfig().getTimeStart()
				.getElapsedTimeInMillis(expDataModel.getTimeStamp(item));
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

		return expDataModel.getValueAt(item, getChannelDisplay()).getValueNumber().doubleValue();
	}

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplay;

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
	public int getChannelDisplay() {
		return channelDisplay;
	}

	/**
	 * Setter for property channelDisplay.
	 * 
	 * @param channelDisplay New value of property channelDisplay.
	 */
	public void setChannelDisplay(final int channelDisplay) {
		this.channelDisplay = channelDisplay;
	}

	/**
	 * Returns the ending X value for the specified series and item.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item within a series (zero-based index).
	 * 
	 * @return the ending X value for the specified series and item.
	 */
	public double getEndXValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()
				|| expDataModel.getAcquisitionConfig() == null) {
			return 0;
		}
		long half_step_millis = 0;
		final Frequency sel_freq = expDataModel.getAcquisitionConfig().getSelectedFrequency();
		if (sel_freq.getFrequencyDefType() == FrequencyDefType.SamplingIntervalType) {
			half_step_millis = (long) Math.floor(sel_freq.getFrequency() * sel_freq.getMultiplier().getExpValue()
					/ Multiplier.mili.getExpValue() / 2.);
		} else {
			half_step_millis = (long) Math.floor((1. / (sel_freq.getFrequency() * sel_freq.getMultiplier()
					.getExpValue())) / Multiplier.mili.getExpValue() / 2.);
		}

		final DateTime timeStamp = expDataModel.getTimeStamp(item);
		timeStamp.addMillis(+half_step_millis);
		return expDataModel.getAcquisitionConfig().getTimeStart().getElapsedTimeInMillis(timeStamp);

	}

	/**
	 * Returns the ending Y value for the specified series and item.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item within a series (zero-based index).
	 * 
	 * @return the ending Y value for the specified series and item.
	 */
	public double getEndYValue(final int series, final int item) {
		return getYValue(series, item);
	}

	/**
	 * Returns the starting X value for the specified series and item.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item within a series (zero-based index).
	 * 
	 * @return the starting X value for the specified series and item.
	 */
	public double getStartXValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()
				|| expDataModel.getAcquisitionConfig() == null) {
			return 0;
		}
		long half_step_millis = 0;
		final Frequency sel_freq = expDataModel.getAcquisitionConfig().getSelectedFrequency();
		if (sel_freq.getFrequencyDefType() == FrequencyDefType.SamplingIntervalType) {
			half_step_millis = (long) Math.floor(sel_freq.getFrequency() * sel_freq.getMultiplier().getExpValue()
					/ Multiplier.mili.getExpValue() / 2.);
		} else {
			half_step_millis = (long) Math.floor((1. / (sel_freq.getFrequency() * sel_freq.getMultiplier()
					.getExpValue())) / Multiplier.mili.getExpValue() / 2.);
		}

		final DateTime timeStamp = expDataModel.getTimeStamp(item);
		timeStamp.addMillis(-half_step_millis);
		return expDataModel.getAcquisitionConfig().getTimeStart().getElapsedTimeInMillis(timeStamp);
	}

	/**
	 * Returns the starting Y value for the specified series and item.
	 * 
	 * @param series the series (zero-based index).
	 * @param item the item within a series (zero-based index).
	 * 
	 * @return starting Y value for the specified series and item.
	 */
	public double getStartYValue(final int series, final int item) {
		return 0;
	}

	public Number getEndX(final int series, final int item) {
		return new Double(getEndXValue(series, item));
	}

	public Number getEndY(final int series, final int item) {
		return new Double(getEndYValue(series, item));
	}

	public Number getStartX(final int series, final int item) {
		return new Double(getStartXValue(series, item));
	}

	public Number getStartY(final int series, final int item) {
		return new Double(getStartYValue(series, item));
	}

	@Override
	public Number getX(final int series, final int item) {
		return new Double(getXValue(series, item));
	}

	@Override
	public Number getY(final int series, final int item) {
		return new Double(getYValue(series, item));
	}

	@Override
	public org.jfree.data.DomainOrder getDomainOrder() {
		return org.jfree.data.DomainOrder.NONE;
	}

}
