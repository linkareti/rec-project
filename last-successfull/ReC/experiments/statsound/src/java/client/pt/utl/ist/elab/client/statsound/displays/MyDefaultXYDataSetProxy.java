/*
 * MyDefaultXYDataSetProxy.java
 *
 * Created on October 16, 2003, 11:48 AM
 */

package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class MyDefaultXYDataSetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6464972666758490386L;

	private int[] channelDisplayYArray;

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplayX;

	/** Holds value of property channelDisplayY. */
	private int channelDisplayY;

	/** Creates a new instance of MyDefaultXYDataSetProxy */
	public MyDefaultXYDataSetProxy() {
	}

	public void dataModelRunning() {
		fireDatasetChanged();
	}

	@Override
	public void dataModelStoped() {
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
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
		if (getChannelDisplayYArray().length == 0) {
			return 1;
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
	public Comparable getSeriesKey(final int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return null;
		}

		final String multiplierX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getMultiplier().toString();
		final String ph_unit_symbolX = expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale()
				.getPhysicsUnitSymbol();
		final String ch_nameX = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplayX())
				.getChannelName());

		String multiplierY;
		String ph_unit_symbolY;
		String ch_nameY;

		if (getChannelDisplayYArray().length == 0) {
			multiplierY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale().getMultiplier()
					.toString();
			ph_unit_symbolY = expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale()
					.getPhysicsUnitSymbol();
			ch_nameY = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplayY())
					.getChannelName());
		} else {
			multiplierY = expDataModel.getChannelConfig(getChannelDisplayAtYArray(series)).getSelectedScale()
					.getMultiplier().toString();
			ph_unit_symbolY = expDataModel.getChannelConfig(getChannelDisplayAtYArray(series)).getSelectedScale()
					.getPhysicsUnitSymbol();
			ch_nameY = ReCResourceBundle.findString(expDataModel.getChannelConfig(getChannelDisplayAtYArray(series))
					.getChannelName());
		}
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
		if (expDataModel.getValueAt(item, getChannelDisplayX()) == null) {
			// return new Double(0);
			return 0;
		}
		return expDataModel.getValueAt(item, getChannelDisplayX()).getValueNumber().doubleValue();
	}

	@Override
	public double getYValue(final int series, final int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()
				|| expDataModel.getValueAt(item, getChannelDisplayX()) == null) {
			return 0;
		}
		if (getChannelDisplayYArray().length == 0) {
			if (expDataModel.getValueAt(item, getChannelDisplayY()) == null) {
				// return new Double(0);
				return 0;
			}
			return expDataModel.getValueAt(item, getChannelDisplayY()).getValueNumber().doubleValue();
		} else {
			if (expDataModel.getValueAt(item, getChannelDisplayAtYArray(series)) == null) {
				// return new Double(0);
				return 0;
			}
			return expDataModel.getValueAt(item, getChannelDisplayAtYArray(series)).getValueNumber().doubleValue();
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
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayYArray(final int[] channelDisplayYArray) {
		this.channelDisplayYArray = channelDisplayYArray;
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		if (expDataModel != null) {
			headerAvailable(expDataModel.getAcquisitionConfig());
		}
		fireDatasetChanged();
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelWaiting() {
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
		return org.jfree.data.DomainOrder.ASCENDING;
	}

}
