package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 */
public class MultSeriesXYInBlockDataSetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/** Generated UID */
	private static final long serialVersionUID = -6192584022782583188L;

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplayX;

	/** Holds value of property channelDisplayY. */
	private int channelDisplayY;

	private final int blockSize;

	private int seriesCount;

	private Integer updateFrequency = null;

	/**
	 * Creates the <code>MultSeriesXYInBlockDataSetProxy</code>.
	 * 
	 * @param blockSize
	 */
	public MultSeriesXYInBlockDataSetProxy(final int blockSize) {
		this.blockSize = blockSize;
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
			seriesCount = header.getTotalSamples() / blockSize;
		}
		fireDatasetChanged();
	}

	@Override
	public void dataModelStartedNoData() {
		if (header == null && expDataModel != null) {
			header = expDataModel.getAcquisitionConfig();
			seriesCount = header.getTotalSamples() / blockSize;
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
		if (expDataModel == null || !expDataModel.isDataAvailable()) {
			return 0;
		} else {
			return seriesCount;
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
	@SuppressWarnings("unchecked")
	public Comparable getSeriesKey(final int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series > (seriesCount - 1)) {
			return null;
		}

		final String multiplierX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getPhysicsUnitSymbol();
		final String ch_nameX = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplayX())
				.getChannelName());

		final String multiplierY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale()
				.getPhysicsUnitSymbol();
		final String ch_nameY = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplayY())
				.getChannelName());

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
	private int itemCount = 0;

	@Override
	public int getItemCount(final int series) {
		itemCount = blockSize;
		return blockSize;
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
		if (expDataModel == null || !expDataModel.isDataAvailable() || series > (seriesCount - 1)
				|| !(expDataModel.getTotalSamples() > item + series * blockSize)
				|| expDataModel.getValueAt(item + series * blockSize, getChannelDisplayX()) == null) {
			return 0;
		}

		Number x = null;

		try {
			x = expDataModel.getValueAt(item + series * blockSize, getChannelDisplayX()).getValueNumber();
		} catch (final Exception e) {
			if (item > 0) {
				x = expDataModel.getValueAt(item - 1, getChannelDisplayX()).getValueNumber();
				itemCount--;
			} else {
				x = new Double(0);
			}
		}

		return x.doubleValue();
	}

	@Override
	public double getYValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series > (seriesCount - 1)
				|| !(expDataModel.getTotalSamples() > item + series * blockSize)
				|| expDataModel.getValueAt(item + series * blockSize, getChannelDisplayX()) == null) {
			return 0;
		} else {
			if (expDataModel.getValueAt(item + series * blockSize, getChannelDisplayY()) == null) {
				return 0;
			}

			// I need to know if there was some error in the conversion to
			// number...because if it jfreechart will probably crash...
			Number y = null;

			try {
				y = expDataModel.getValueAt(item + series * blockSize, getChannelDisplayY()).getValueNumber();
			} catch (final Exception e) {
				if (item > 0) {
					y = expDataModel.getValueAt(item + series * blockSize, getChannelDisplayY()).getValueNumber();
					itemCount--;
				} else {
					y = new Double(0);
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
	 * 
	 * @param channelDisplayX
	 * 
	 * @param channelDisplay New value of property channelDisplay.
	 */
	public void setChannelDisplayX(final int channelDisplayX) {
		this.channelDisplayX = channelDisplayX;
	}

	public Integer getUpdateFrequency() {
		return updateFrequency;
	}

	/**
	 * Update from updateFrequency to updateFrequency points
	 * 
	 * @param updateFrequency
	 */
	public void setUpdateFrequency(int updateFrequency) {
		if (updateFrequency < 1) {
			updateFrequency = 1;
		}
		this.updateFrequency = updateFrequency;
	}

	@Override
	public Number getX(final int param, final int param1) {
		return new Double(getXValue(param, param1));
	}

	@Override
	public Number getY(final int param, final int param1) {
		return new Double(getYValue(param, param1));
	}

	@Override
	public org.jfree.data.DomainOrder getDomainOrder() {
		return org.jfree.data.DomainOrder.NONE;
	}
}
