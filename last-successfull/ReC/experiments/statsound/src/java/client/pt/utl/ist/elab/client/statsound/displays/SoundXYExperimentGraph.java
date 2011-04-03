/*
 * MyDefaultXYExperimentGraph.java
 *
 * Created on October 16, 2003, 12:06 PM
 */

package pt.utl.ist.elab.client.statsound.displays;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Icon;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class SoundXYExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	/** Creates a new instance of MyDefaultXYExperimentGraph */
	public SoundXYExperimentGraph() {
		initComponents();
	}

	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		Logger l = LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		defaultXYDatasetProxy = new SoundXYDataSetProxy();
		scrollPane = new javax.swing.JScrollPane();
		labelWaitData = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelWaitData.setText("waiting for data...");
		scrollPane.setViewportView(labelWaitData);

		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	// Variables declaration - do not modify
	private SoundXYDataSetProxy defaultXYDatasetProxy;
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JLabel labelWaitData;
	// End of variables declaration

	/** Holds value of property channelX. */
	private int channelX;

	/** Holds value of property channelY. */
	private int channelY;

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
	}

	private ExpDataModel model;

	public void setExpDataModel(ExpDataModel model) {
		defaultXYDatasetProxy.setExpDataModel(model);
		model.addExpDataModelListener(this);
		this.model = model;
	}

	public String getName() {
		return "Chart";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
		NumberAxis xAxis = new NumberAxis("Time (s)");
		xAxis.setAutoRange(true);
		xAxis.setAutoRangeStickyZero(false);
		xAxis.setAutoRangeIncludesZero(false);

		NumberAxis yAxis = new NumberAxis("");
		yAxis.setAutoRange(true);
		yAxis.setAutoRangeStickyZero(false);
		yAxis.setAutoRangeIncludesZero(false);

		XYToolTipGenerator tooltipGenerator = new StandardXYToolTipGenerator();

		XYPlot plot = new XYPlot(defaultXYDatasetProxy, xAxis, yAxis, new StandardXYItemRenderer(
				StandardXYItemRenderer.SHAPES_AND_LINES, tooltipGenerator));

		chart = new JFreeChart(getChartName(header), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		ChartPanel panel = new ChartPanel(chart);

		panel.setPreferredSize(new java.awt.Dimension(350, 250));
		panel.setMouseZoomable(true, false);

		scrollPane.remove(labelWaitData);
		scrollPane.setViewportView(panel);
	}
	
	protected String getChartName(final HardwareAcquisitionConfig header) {
		return header.getFamiliarName();
	}

	/*
	 * public void setSeriesVisible(int series, boolean visible) {
	 * if(getChannelDisplayYArray().length==0) { return; } if(visible) {
	 * defaultXYDatasetProxy.addSeries(getChannelDisplayAtYArray(series)); }
	 * else {
	 * defaultXYDatasetProxy.removeSeries(getChannelDisplayAtYArray(series)); }
	 * }
	 */

	private boolean isScaleSet = false;

	private JFreeChart chart = null;

	public void newSamples(NewExpDataEvent evt) {

	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
		if (model != null)
			headerAvailable(model.getAcquisitionConfig());
	}

	public void dataModelStartedNoData() {
	}

	public void dataModelStoped() {// BIG SILENT NOOP
	}

	public void dataModelWaiting() {
	}

}
