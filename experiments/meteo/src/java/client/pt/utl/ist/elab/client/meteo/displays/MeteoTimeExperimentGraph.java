/*
 * MeteoTimeExperimentGraph.java
 *
 * Created on 27 January 2004, 16:18
 */

package pt.utl.ist.elab.client.meteo.displays;

/**
 *
 * @author  Andr�
 */

import java.text.SimpleDateFormat;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.Scale;

public class MeteoTimeExperimentGraph extends DefaultTimeExperimentGraph {
	/** Creates a new instance of MeteoTimeExperimentGraph */
	public MeteoTimeExperimentGraph() {
		super();
	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
		if (header == null)
			return;

		DateAxis dAxis = new DateAxis("Tempo");
		dAxis.setAutoRange(true);

		String resolution = header.getSelectedHardwareParameterValue("Resolution");

		if (resolution.trim().equalsIgnoreCase("Hourly")) {
			dAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 1, new SimpleDateFormat("HH dd-MMM-yyyy")));
		} else if (resolution.trim().equalsIgnoreCase("Daily")) {
			dAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1, new SimpleDateFormat("dd-MMM-yyyy")));
		} else if (resolution.trim().equalsIgnoreCase("Monthly")) {
			dAxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1, new SimpleDateFormat("MMM-yyyy")));
		} else {
			dAxis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, 1, new SimpleDateFormat("yyyy")));
		}
		dAxis.setVerticalTickLabels(true);

		Scale scaleY = header.getChannelsConfig(getDefaultTimeDatasetProxy().getChannelDisplayY()).getSelectedScale();
		String chnY = header.getChannelsConfig(getDefaultTimeDatasetProxy().getChannelDisplayY()).getChannelName();
		String pusY = scaleY.getPhysicsUnitSymbol();
		String multiplierY = scaleY.getMultiplier().toString();

		NumberAxis yAxis = new NumberAxis(chnY + " [" + multiplierY + pusY + "]");
		yAxis.setAutoRange(true);
		yAxis.setAutoRangeStickyZero(false);
		yAxis.setAutoRangeIncludesZero(false);

		XYToolTipGenerator tooltipGenerator = new StandardXYToolTipGenerator();

		XYPlot plot = new XYPlot(getDefaultTimeDatasetProxy(), dAxis, yAxis, new StandardXYItemRenderer(
				StandardXYItemRenderer.SHAPES_AND_LINES, tooltipGenerator));

		JFreeChart chart = new JFreeChart(header.getFamiliarName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		ChartPanel panel = new ChartPanel(chart);

		panel.setPreferredSize(new java.awt.Dimension(350, 300));
		// panel.setMinimumSize(panel.getPreferredSize());
		// panel.setSize(panel.getPreferredSize());

		panel.setMouseZoomable(true, false);

		getScrollPane().remove(getLabel());
		getScrollPane().setViewportView(panel);
	}
}
