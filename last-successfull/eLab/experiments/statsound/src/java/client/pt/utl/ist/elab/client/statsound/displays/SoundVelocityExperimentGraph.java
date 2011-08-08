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
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class SoundVelocityExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	private static final long serialVersionUID = -3005704584221537484L;

	private JFreeChart chart;

	private ExpDataModel model;

	private SoundVelocityDataSetProxy datasetProxy;

	private javax.swing.JScrollPane scrollPane;

	private javax.swing.JLabel labelWaitData;

	public SoundVelocityExperimentGraph() {
		initComponents();
	}

	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		final Logger l = LogManager.getLogManager().getLogger(SoundVelocityExperimentGraph.UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(SoundVelocityExperimentGraph.UI_CLIENT_LOGGER));
		}
	}

	private void initComponents() {
		datasetProxy = new SoundVelocityDataSetProxy();
		scrollPane = new javax.swing.JScrollPane();
		labelWaitData = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelWaitData.setText("waiting for data...");
		scrollPane.setViewportView(labelWaitData);

		add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
	}

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		datasetProxy.setExpDataModel(model);
		model.addExpDataModelListener(this);
		this.model = model;
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

	public void dataModelRunning() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelStoped() {// BIG SILENT NOOP
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		// Scale scaleX =
		// header.getChannelsConfig(datasetProxy.getChannelDisplayX()).getSelectedScale();
		final String chnX = ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.acquisitionTime");

		Scale scaleY;
		String chnY;
		String pusY;
		String multiplierY;

		if (getChannelDisplayYArray().length == 0) {
			scaleY = header.getChannelsConfig(datasetProxy.getChannelDisplayY()).getSelectedScale();
			chnY = ReCResourceBundle.findString(header.getChannelsConfig(datasetProxy.getChannelDisplayY())
					.getChannelName());
			pusY = scaleY.getPhysicsUnitSymbol();
			multiplierY = scaleY.getMultiplier().toString();
		} else {
			scaleY = header.getChannelsConfig(datasetProxy.getChannelDisplayAtYArray(0)).getSelectedScale();
			chnY = ReCResourceBundle.findString(header.getChannelsConfig(datasetProxy.getChannelDisplayAtYArray(0))
					.getChannelName());
			pusY = scaleY.getPhysicsUnitSymbol();
			multiplierY = scaleY.getMultiplier().toString();
		}

		final NumberAxis xAxis = new NumberAxis(chnX);
		xAxis.setAutoRange(true);
		xAxis.setAutoRangeStickyZero(false);
		xAxis.setAutoRangeIncludesZero(false);

		final NumberAxis yAxis = new NumberAxis(chnY + " [" + multiplierY + pusY + "]");
		yAxis.setAutoRange(true);
		yAxis.setAutoRangeStickyZero(false);
		yAxis.setAutoRangeIncludesZero(false);

		final XYToolTipGenerator tooltipGenerator = new StandardXYToolTipGenerator();

		final XYPlot plot = new XYPlot(datasetProxy, xAxis, yAxis, new StandardXYItemRenderer(
				StandardXYItemRenderer.SHAPES_AND_LINES, tooltipGenerator));

		chart = new JFreeChart(getChartName(header), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		final ChartPanel panel = new ChartPanel(chart);

		panel.setPreferredSize(new java.awt.Dimension(350, 250));
		panel.setMouseZoomable(true, false);

		scrollPane.remove(labelWaitData);
		scrollPane.setViewportView(panel);
	}

	/**
	 * This method is protected so that each subclass may override it and choose
	 * a different name to be presented as the chart title.
	 * 
	 * @param header the HardwareAcquisitionConfig element
	 * @return the name of the chart to be instantiated
	 */
	protected String getChartName(final HardwareAcquisitionConfig header) {
		return header.getFamiliarName();
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {

	}

	/**
	 * Getter for property channelDisplayX.
	 * 
	 * @return Value of property channelDisplayX.
	 */
	public int getChannelDisplayX() {
		return datasetProxy.getChannelDisplayX();
	}

	/**
	 * Setter for property channelDisplayX.
	 * 
	 * @param channelDisplayX New value of property channelDisplayX.
	 */
	public void setChannelDisplayX(final int channelDisplayX) {
		datasetProxy.setChannelDisplayX(channelDisplayX);
	}

	/**
	 * Getter for property channelDisplayY.
	 * 
	 * @return Value of property channelDisplayY.
	 */
	public int getChannelDisplayY() {
		return datasetProxy.getChannelDisplayY();
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayY(final int channelDisplayY) {
		datasetProxy.setChannelDisplayY(channelDisplayY);
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int getChannelDisplayAtYArray(final int channel) {
		return datasetProxy.getChannelDisplayAtYArray(channel);
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int[] getChannelDisplayYArray() {
		return datasetProxy.getChannelDisplayYArray();
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setChannelDisplayYArray(final int[] channelDisplayYArray) {
		datasetProxy.setChannelDisplayYArray(channelDisplayYArray);
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		if (model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelWaiting() {
	}
}
