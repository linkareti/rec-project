/* 
 * FunctorDataProxyDataSet.java created on 25 Aug 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf;

import javax.media.Player;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;

import com.linkare.rec.jmf.media.protocol.function.FunctorDataControl;
import com.linkare.rec.jmf.media.protocol.function.FunctorDataControl.FunctorDataControlListener;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FunctorDataProxyDataSet implements FunctorDataControlListener, XYDataset {

	private XYPlot xyPlot;
	private long[] yValues = new long[1024];
	private long[] yValuesTemp = new long[yValues.length];
	private EventListenerList listeners = new EventListenerList();
	private DatasetGroup datasetGroup;
	private volatile boolean start = false;

	/**
	 * Creates the <code>FunctorDataProxyDataSet</code>.
	 * 
	 * @param player
	 * @param xyPlot
	 */
	public FunctorDataProxyDataSet(Player player, XYPlot xyPlot) {
		this.xyPlot = xyPlot;
		this.xyPlot.setDataset(this);
		FunctorDataControl control = (FunctorDataControl) player.getControl(FunctorDataControl.class.getName());
		control.setListener(this);
		for (int i = 0; i < yValues.length; i++) {
			yValues[i] = 0L;
			yValuesTemp[i] = 0L;
		}
	}

	private int countOfNewValues = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newValue(long nanoTime, long value) {
		if (start) {
			yValuesTemp[countOfNewValues] = value;
			countOfNewValues++;

			if (countOfNewValues == yValuesTemp.length) {
				countOfNewValues = 0;
				synchronized (yValues) {
					yValues = yValuesTemp;
					yValuesTemp = new long[yValues.length];
				}
				final DatasetChangeEvent fireEvent = new DatasetChangeEvent(this, this);
				for (final DatasetChangeListener listener : this.listeners.getListeners(DatasetChangeListener.class)) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							synchronized (yValues) {
								listener.datasetChanged(fireEvent);
							}
						}
					});
				}
			}
		}

	}

	@Override
	public int getSeriesCount() {
		return 1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable getSeriesKey(int series) {
		return new String("Onda gerada");
	}

	@Override
	public int indexOf(@SuppressWarnings("rawtypes") Comparable seriesKey) {
		return 0;
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
		return yValues.length;
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
		return yValues[sample];
	}

	@Override
	public double getYValue(int series, int sample) {
		return yValues[sample];
	}

	/**
	 * 
	 */
	public void start() {
		start = true;
	}

}
