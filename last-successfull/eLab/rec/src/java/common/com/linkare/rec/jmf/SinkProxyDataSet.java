package com.linkare.rec.jmf;

import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;

import com.linkare.rec.jmf.media.datasink.capture.ChannelDataFrame;
import com.linkare.rec.jmf.media.datasink.capture.Handler;

public class SinkProxyDataSet implements XYDataset, DataSinkListener {

	private Handler handler;

	private EventListenerList listeners = new EventListenerList();
	private ChannelDataFrame frame = null;
	private DatasetGroup datasetGroup;

	private XYPlot xyPlot;

	public SinkProxyDataSet(Handler handler, XYPlot xyPlot) {
		this.handler = handler;
		this.handler.addDataSinkListener(this);
		this.xyPlot = xyPlot;
		this.xyPlot.setDataset(this);
	}

	@Override
	public int getSeriesCount() {
		return frame == null ? -1 : frame.getNumChannels();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable getSeriesKey(int series) {
		return new String("" + series);
	}

	@Override
	public int indexOf(@SuppressWarnings("rawtypes") Comparable seriesKey) {
		return Integer.parseInt(seriesKey.toString());
	}

	@Override
	public void addChangeListener(DatasetChangeListener datasetChangeListener) {
		listeners.add(DatasetChangeListener.class, datasetChangeListener);
	}

	@Override
	public DatasetGroup getGroup() {
		return datasetGroup;
	}

	@Override
	public void removeChangeListener(DatasetChangeListener datasetChengeListener) {
		listeners.remove(DatasetChangeListener.class, datasetChengeListener);
	}

	@Override
	public void setGroup(DatasetGroup datasetGroup) {
		this.datasetGroup = datasetGroup;
	}

	@Override
	public DomainOrder getDomainOrder() {
		return DomainOrder.ASCENDING;
	}

	@Override
	public int getItemCount(int series) {
		return frame == null ? 0 : frame.getChannelData(series).length;
	}

	@Override
	public Number getX(int series, int sample) {
		return sample;
	}

	@Override
	public double getXValue(int series, int sample) {
		return sample;
	}

	@Override
	public Number getY(int series, int sample) {
		return frame == null ? 0 : frame.getChannelData(series)[sample];
	}

	@Override
	public double getYValue(int series, int sample) {
		return frame == null ? 0 : frame.getChannelData(series)[sample];
	}

	@Override
	public void dataSinkUpdate(DataSinkEvent event) {
		frame = this.handler.captureFrame(11025/4, 11025., true);

		final DatasetChangeEvent fireEvent = new DatasetChangeEvent(this, this);
		for (final DatasetChangeListener listener : this.listeners.getListeners(DatasetChangeListener.class)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					listener.datasetChanged(fireEvent);
				}
			});
		}
	}

}
