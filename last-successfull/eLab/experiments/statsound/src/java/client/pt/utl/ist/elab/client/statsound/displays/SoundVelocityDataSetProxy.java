package pt.utl.ist.elab.client.statsound.displays;

import java.math.BigDecimal;
import java.math.MathContext;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class SoundVelocityDataSetProxy extends org.jfree.data.xy.AbstractXYDataset implements
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	private static final MathContext MATH_CONTEXT = new MathContext(2);

	private int[] channelDisplayYArray;

	private ExpDataModel expDataModel;

	/** Holds value of property channelDisplay. */
	private int channelDisplayX;

	/** Holds value of property channelDisplayY. */
	private int channelDisplayY;

	/** Creates a new instance of MyDefaultXYDataSetProxy */
	public SoundVelocityDataSetProxy() {
	}

	public void dataModelRunning() {
		fireDatasetChanged();
	}

	public void dataModelStoped() {
	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
		fireDatasetChanged();
	}

	public void newSamples(NewExpDataEvent evt) {
		fireDatasetChanged();
	}

	/**
	 * Returns the number of series in the dataset.
	 * 
	 * @return the series count.
	 */
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
	public Comparable getSeriesKey(int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return null;
		}

		String ch_nameX = ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.acquisitionTime");

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
			if (series == 0) {
				ch_nameY = ReCResourceBundle.findString("statsound$rec.exp.statsound.hardwareinfo.channel.3.name");
			} else {
				ch_nameY = ReCResourceBundle.findString("statsound$rec.exp.statsound.hardwareinfo.channel.4.name");
			}
		}
		return ch_nameX + " vs " + ch_nameY;
	}

	/**
	 * Returns the number of items in a series.
	 * 
	 * @param series the series (zero-based index).
	 * 
	 * @return the number of items within the series.
	 */
	public int getItemCount(int series) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}

		if (expDataModel.getTotalSamples() == -1)
			return 0;
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
	public double getXValue(int series, int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()) {
			return 0;
		}
		final BigDecimal time = new BigDecimal(((double) series + 1) / ((double) 11.025));
		time.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		return time.round(MATH_CONTEXT).doubleValue();
	}

	public double getYValue(int series, int item) {
		if (expDataModel == null || !expDataModel.isDataAvailable() || series >= expDataModel.getChannelCount()
				|| expDataModel.getValueAt(item, getChannelDisplayY()) == null) {
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
		return this.channelDisplayY;
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayY(int channelDisplayY) {
		this.channelDisplayY = channelDisplayY;
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
			fireDatasetChanged();
		}

	}

	/**
	 * Getter for property channelDisplay.
	 * 
	 * @return Value of property channelDisplay.
	 */
	public int getChannelDisplayX() {
		return this.channelDisplayX;
	}

	/**
	 * Setter for property channelDisplay.
	 * 
	 * @param channelDisplay New value of property channelDisplay.
	 */
	public void setChannelDisplayX(int channelDisplayX) {
		this.channelDisplayX = channelDisplayX;
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int getChannelDisplayAtYArray(int series) {
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
	public void setChannelDisplayYArray(int[] channelDisplayYArray) {
		this.channelDisplayYArray = channelDisplayYArray;
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
		if (expDataModel != null)
			headerAvailable(expDataModel.getAcquisitionConfig());
		fireDatasetChanged();
	}

	public void dataModelStartedNoData() {
	}

	public void dataModelWaiting() {
	}

	public Number getX(int param, int param1) {
		return new Double(getXValue(param, param1));
	}

	public Number getY(int param, int param1) {
		return new Double(getYValue(param, param1));
	}

	public org.jfree.data.DomainOrder getDomainOrder() {
		return org.jfree.data.DomainOrder.ASCENDING;
	}

}