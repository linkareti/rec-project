package com.linkare.rec.impl.baseUI.graph;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Icon;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.MultSeriesXYInBlockDataSetProxy;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 */
public class MultSeriesXYInBlockExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay,
		ExpDataModelListener {

	/** Generated UID */
	private static final long serialVersionUID = 3162657571661485941L;

	private static final double DEFAULT_UPDATE_FREQUENCY_WEIGTH = 0.1;

	/**
	 * Creates the <code>MultSeriesXYInBlockExperimentGraph</code>.
	 * 
	 * @param blockSize
	 */
	public MultSeriesXYInBlockExperimentGraph(final int blockSize) {
		this.blockSize = blockSize;

		initComponents();
	}

	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		final Logger l = LogManager.getLogManager().getLogger(MultSeriesXYInBlockExperimentGraph.UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MultSeriesXYInBlockExperimentGraph.UI_CLIENT_LOGGER));
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		defaultXYDatasetProxy = new MultSeriesXYInBlockDataSetProxy(blockSize);
		scrollPane = new javax.swing.JScrollPane();
		labelWaitData = new javax.swing.JLabel();

		defaultXYDatasetProxy.setChannelDisplayY(1);

		setLayout(new java.awt.BorderLayout());

		labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelWaitData.setText("waiting for data...");
		scrollPane.setViewportView(labelWaitData);

		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	// Variables declaration - do not modify
	private MultSeriesXYInBlockDataSetProxy defaultXYDatasetProxy;
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JLabel labelWaitData;
	// End of variables declaration

	/** Holds value of property channelX. */
	private int channelX;

	/** Holds value of property channelY. */
	private int channelY;

	private final int blockSize;

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
	}

	ExpDataModel expDataModel = null;

	@Override
	public void setExpDataModel(final ExpDataModel expDataModel) {

		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
			defaultXYDatasetProxy.setExpDataModel(expDataModel);
		}
	}

	@Override
	public String getName() {
		return "Chart";
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
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
		defaultXYDatasetProxy.dataModelStarted();
		if (header == null) {
			headerAvailable(expDataModel.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelStartedNoData() {
		if (header == null) {
			headerAvailable(expDataModel.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelWaiting() {// BIG SILENT NOOP
	}

	private HardwareAcquisitionConfig header = null;

	private void headerAvailable(final HardwareAcquisitionConfig header) {
		if (header == null) {
			return;
		}

		this.header = header;
		final Scale scaleX = header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayX()).getSelectedScale();
		final String chnX = ReCResourceBundle.findString(header.getChannelsConfig(
				defaultXYDatasetProxy.getChannelDisplayX()).getChannelName());
		final String pusX = scaleX.getPhysicsUnitSymbol();
		final String multiplierX = scaleX.getMultiplier().toString();

		final Scale scaleY = header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayY()).getSelectedScale();
		final String chnY = ReCResourceBundle.findString(header.getChannelsConfig(
				defaultXYDatasetProxy.getChannelDisplayY()).getChannelName());
		final String pusY = scaleY.getPhysicsUnitSymbol();
		final String multiplierY = scaleY.getMultiplier().toString();

		final NumberAxis xAxis = new NumberAxis(chnX + " [" + multiplierX + pusX + "]");
		xAxis.setAutoRange(true);
		xAxis.setAutoRangeStickyZero(false);
		xAxis.setAutoRangeIncludesZero(false);

		final NumberAxis yAxis = new NumberAxis(chnY + " [" + multiplierY + pusY + "]");
		yAxis.setAutoRange(true);
		yAxis.setAutoRangeStickyZero(false);
		yAxis.setAutoRangeIncludesZero(false);

		final XYPlot plot = new XYPlot(defaultXYDatasetProxy, xAxis, yAxis, new StandardXYItemRenderer(
				StandardXYItemRenderer.SHAPES_AND_LINES, new StandardXYToolTipGenerator()));
		plot.setRenderer(new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES,
				new StandardXYToolTipGenerator()));

		chart = new JFreeChart(header.getFamiliarName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		final ChartPanel panel = new ChartPanel(chart);

		panel.setPreferredSize(new java.awt.Dimension(350, 250));
		panel.setMouseZoomable(true, false);

		scrollPane.remove(labelWaitData);
		scrollPane.setViewportView(panel);

		if (getUpdateFrequency() == null) {
			setUpdateFrequency((int) (header.getTotalSamples() * MultSeriesXYInBlockExperimentGraph.DEFAULT_UPDATE_FREQUENCY_WEIGTH));
		}
	}

	private final boolean isScaleSet = false;

	private JFreeChart chart = null;

	@Override
	public void newSamples(final NewExpDataEvent evt) {

	}

	/**
	 * Getter for property channelDisplayX.
	 * 
	 * @return Value of property channelDisplayX.
	 */
	public int getChannelDisplayX() {
		return defaultXYDatasetProxy.getChannelDisplayX();
	}

	/**
	 * Setter for property channelDisplayX.
	 * 
	 * @param channelDisplayX New value of property channelDisplayX.
	 */
	public void setChannelDisplayX(final int channelDisplayX) {
		defaultXYDatasetProxy.setChannelDisplayX(channelDisplayX);
	}

	/**
	 * Getter for property channelDisplayY.
	 * 
	 * @return Value of property channelDisplayY.
	 */
	public int getChannelDisplayY() {
		return defaultXYDatasetProxy.getChannelDisplayY();
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayY(final int channelDisplayY) {
		defaultXYDatasetProxy.setChannelDisplayY(channelDisplayY);
	}

	public Integer getUpdateFrequency() {
		return defaultXYDatasetProxy.getUpdateFrequency();
	}

	/** Update from updateFrequency to updateFrequency points */
	public void setUpdateFrequency(final int updateFrequency) {
		defaultXYDatasetProxy.setUpdateFrequency(updateFrequency);
	}
}
