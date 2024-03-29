/*
 * DefaultExperimentGraph.java
 *
 * Created on 7 de Maio de 2003, 18:47
 */

package com.linkare.rec.impl.ui.graph;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
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
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277296277292084591L;


	/** Creates new form DefaultExperimentGraph */
	public DefaultExperimentGraph() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()//GEN-BEGIN:initComponents
	{
		defaultDatasetProxy = new com.linkare.rec.impl.client.experiment.DefaultDatasetProxy();
		scrollPane = new javax.swing.JScrollPane();
		labelWaitData = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelWaitData.setText("waiting for data...");
		scrollPane.setViewportView(labelWaitData);

		add(scrollPane, java.awt.BorderLayout.CENTER);

	}//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane scrollPane;
	private com.linkare.rec.impl.client.experiment.DefaultDatasetProxy defaultDatasetProxy;
	private javax.swing.JLabel labelWaitData;
	// End of variables declaration//GEN-END:variables

//	/** Holds value of property channelX. */
//	private int channelX;

//	/** Holds value of property channelY. */
//	private int channelY;

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/newface/resources/legacy/chart16.gif"));
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		this.model = model;
		defaultDatasetProxy.setExpDataModel(model);
		model.addExpDataModelListener(this);
	}

	@Override
	public String getName() {
		return "Time Series Chart";
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
	public void dataModelWaiting() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelStoped() {
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelEnded() {
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	@Override
	public void dataModelStartedNoData() {
		if (header == null && model != null) {
			headerAvailable(model.getAcquisitionConfig());
		}
	}

	private HardwareAcquisitionConfig header = null;

	private void headerAvailable(final HardwareAcquisitionConfig header) {
		if (header == null) {
			return;
		}

		this.header = header;
		final NumberAxis timeAxis = new NumberAxis("Elapsed Time [ms]");
		timeAxis.setAutoRange(true);
		timeAxis.setAutoRangeStickyZero(false);
		timeAxis.setAutoRangeIncludesZero(false);
		final NumberAxis valueAxis = new NumberAxis("Acquisition Channels");
		valueAxis.setAutoRange(true);
		valueAxis.setAutoRangeStickyZero(false);
		valueAxis.setAutoRangeIncludesZero(false);

		final XYToolTipGenerator tooltipGenerator = new StandardXYToolTipGenerator();

		final XYPlot plot = new XYPlot(defaultDatasetProxy, timeAxis, valueAxis, new StandardXYItemRenderer(
				StandardXYItemRenderer.SHAPES_AND_LINES, tooltipGenerator));

		chart = new JFreeChart(header.getFamiliarName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new java.awt.Dimension(350, 300));
		// panel.setMinimumSize(new java.awt.Dimension(350,300));
		// panel.setSize(new java.awt.Dimension(350,300));
		panel.setMouseZoomable(true, false);

		scrollPane.remove(labelWaitData);
		scrollPane.setViewportView(panel);
	}

//	private final boolean isScaleSet = false;

	private JFreeChart chart = null;

    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.CHART;
    }
}
