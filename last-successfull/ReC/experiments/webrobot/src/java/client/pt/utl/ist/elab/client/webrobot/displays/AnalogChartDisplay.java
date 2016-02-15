/*
 * AnalogChartDisplay.java
 *
 * Created on 6 de Junho de 2003, 19:12
 */

package pt.utl.ist.elab.client.webrobot.displays;

import pt.utl.ist.elab.client.webrobot.displays.proxys.DefaultChartModelProxy;
import pt.utl.ist.elab.client.webrobot.utils.PrintComponent;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;

/**
 * 
 * @author Andr�
 */
public class AnalogChartDisplay extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -458370520622782420L;

	/** Creates new form AnalogChartDisplay */
	public AnalogChartDisplay() {
		initComponents();
		dataAnalog = new DefaultChartModelProxy(16, 4, false);
		final org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createXYLineChart(name, "Tempo(s)",
				"Valor", dataAnalog, org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new org.jfree.chart.ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
		add(chartPanel, java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		jToolBarChart = new javax.swing.JToolBar();
		jButtonA1 = new javax.swing.JButton();
		jButtonA2 = new javax.swing.JButton();
		jButtonA3 = new javax.swing.JButton();
		jButtonA4 = new javax.swing.JButton();
		jButtonPrint = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		jButtonA1.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jButtonA1.setForeground(new java.awt.Color(0, 51, 153));
		jButtonA1.setText("A1");
		jButtonA1.setToolTipText("Ver A1?");
		jButtonA1.setMaximumSize(new java.awt.Dimension(30, 28));
		jButtonA1.setMinimumSize(new java.awt.Dimension(30, 28));
		jButtonA1.setPreferredSize(new java.awt.Dimension(30, 28));
		jButtonA1.setEnabled(false);
		jButtonA1.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonA1MousePressed(evt);
			}
		});

		jToolBarChart.add(jButtonA1);

		jButtonA2.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jButtonA2.setForeground(new java.awt.Color(0, 51, 153));
		jButtonA2.setText("A2");
		jButtonA2.setToolTipText("Ver A2?");
		jButtonA2.setMaximumSize(new java.awt.Dimension(30, 28));
		jButtonA2.setMinimumSize(new java.awt.Dimension(30, 28));
		jButtonA2.setPreferredSize(new java.awt.Dimension(30, 28));
		jButtonA2.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonA2MousePressed(evt);
			}
		});

		jToolBarChart.add(jButtonA2);

		jButtonA3.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jButtonA3.setForeground(new java.awt.Color(0, 51, 153));
		jButtonA3.setText("A3");
		jButtonA3.setToolTipText("Ver A3?");
		jButtonA3.setMaximumSize(new java.awt.Dimension(30, 28));
		jButtonA3.setMinimumSize(new java.awt.Dimension(30, 28));
		jButtonA3.setPreferredSize(new java.awt.Dimension(30, 28));
		jButtonA3.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonA3MousePressed(evt);
			}
		});

		jToolBarChart.add(jButtonA3);

		jButtonA4.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jButtonA4.setForeground(new java.awt.Color(0, 51, 153));
		jButtonA4.setText("A4");
		jButtonA4.setToolTipText("Ver A4?");
		jButtonA4.setMaximumSize(new java.awt.Dimension(30, 28));
		jButtonA4.setMinimumSize(new java.awt.Dimension(30, 28));
		jButtonA4.setPreferredSize(new java.awt.Dimension(30, 28));
		jButtonA4.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonA4MousePressed(evt);
			}
		});

		jToolBarChart.add(jButtonA4);

		jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/displays/resources/Print16.gif")));
		jButtonPrint.setToolTipText("Imprimir gr\u00e1fico...");
		jButtonPrint.setMaximumSize(new java.awt.Dimension(30, 28));
		jButtonPrint.setMinimumSize(new java.awt.Dimension(30, 28));
		jButtonPrint.setPreferredSize(new java.awt.Dimension(30, 28));
		jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonPrintActionPerformed(evt);
			}
		});

		jToolBarChart.add(jButtonPrint);

		add(jToolBarChart, java.awt.BorderLayout.NORTH);

	}// GEN-END:initComponents

	private void jButtonA4MousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonA4MousePressed
		if (jButtonA4.isEnabled()) {
			dataAnalog.setSeriesVisible(true, 3);
			jButtonA4.setEnabled(false);
		} else {
			dataAnalog.setSeriesVisible(false, 3);
			jButtonA4.setEnabled(true);
		}
	}// GEN-LAST:event_jButtonA4MousePressed

	private void jButtonA3MousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonA3MousePressed
		if (jButtonA3.isEnabled()) {
			dataAnalog.setSeriesVisible(true, 2);
			jButtonA3.setEnabled(false);
		} else {
			dataAnalog.setSeriesVisible(false, 2);
			jButtonA3.setEnabled(true);
		}
	}// GEN-LAST:event_jButtonA3MousePressed

	private void jButtonA2MousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonA2MousePressed
		if (jButtonA2.isEnabled()) {
			dataAnalog.setSeriesVisible(true, 1);
			jButtonA2.setEnabled(false);
		} else {
			dataAnalog.setSeriesVisible(false, 1);
			jButtonA2.setEnabled(true);
		}
	}// GEN-LAST:event_jButtonA2MousePressed

	private void jButtonA1MousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonA1MousePressed
		if (jButtonA1.isEnabled()) {
			dataAnalog.setSeriesVisible(true, 0);
			jButtonA1.setEnabled(false);
		} else {
			dataAnalog.setSeriesVisible(false, 0);
			jButtonA1.setEnabled(true);
		}
	}// GEN-LAST:event_jButtonA1MousePressed

	private void jButtonPrintActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonPrintActionPerformed
		printChart();
	}// GEN-LAST:event_jButtonPrintActionPerformed

	public void printChart() {
		new PrintComponent(chartPanel);
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setExpDataModel(final ExpDataModel expDataModel) {
		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
			dataAnalog.setExpDataModel(expDataModel);
		}
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void dataModelRunning() {
		dataAnalog.dataModelRunning();
	}

	@Override
	public void dataModelStoped() {
		dataAnalog.dataModelStoped();
	}

	public void headerAvailable(final com.linkare.rec.data.config.HardwareAcquisitionConfig hardwareAcquisitionConfig) {
		dataAnalog.headerAvailable(hardwareAcquisitionConfig);
	}

	@Override
	public void newSamples(final com.linkare.rec.impl.client.experiment.NewExpDataEvent newExpDataEvent) {
		dataAnalog.newSamples(newExpDataEvent);
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		headerAvailable(expDataModel.getAcquisitionConfig());
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelWaiting() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonPrint;
	private javax.swing.JButton jButtonA4;
	private javax.swing.JButton jButtonA3;
	private javax.swing.JButton jButtonA2;
	private javax.swing.JToolBar jToolBarChart;
	private javax.swing.JButton jButtonA1;
	// End of variables declaration//GEN-END:variables
	private ExpDataModel expDataModel;
	private final javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/displays/resources/ChartA16.gif"));
	private final String name = "Entradas anal�gicas";
	private final DefaultChartModelProxy dataAnalog;
	private final org.jfree.chart.ChartPanel chartPanel;

	@Override
	public DataDisplayEnum getDisplayType() {
		return DataDisplayEnum.CHART;
	}
}