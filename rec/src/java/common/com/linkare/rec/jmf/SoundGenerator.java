package com.linkare.rec.jmf;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.media.Control;
import javax.media.IncompatibleSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;

import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

public class SoundGenerator {

	/**
	 * @throws NoPlayerException
	 * @throws IOException
	 * @throws IncompatibleSourceException
	 */
	public SoundGenerator() throws NoPlayerException, IOException, IncompatibleSourceException {

		ReCJMFUtils.initReCJMFPackages();

		String locatorString = "function://44100/16/sin/373.431";
		// String locatorString = "function://44100/16/silence";
		// String locatorString = "function://44100/16/triangle/440";
		// String locatorString = "function://44100/16/pulse/100/0.10";
		// String locatorString = "function://44100/16/pinknoise";
		// String locatorString = "function://44100/16/whitenoise";

		final Player player = ReCJMFUtils.createAndStartPlayer(locatorString);

		final JFrame window = new JFrame("Function Sound Player");
		window.getContentPane().setLayout(new BorderLayout());
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				player.stop();
				System.exit(0);
			}
		});

		Control control = player.getControl(FunctorTypeControl.class.getName());
		if (control != null && control.getControlComponent() != null) {
			window.getContentPane().add(control.getControlComponent(), BorderLayout.NORTH);
		}

		XYPlot xyPlot = new XYPlot();
		FunctorDataProxyDataSet functorDataProxyDataSet = new FunctorDataProxyDataSet(player, xyPlot);
		// combinedPlot.setDataset(new SinkProxyDataSet(handler));
		xyPlot.setNoDataMessage("No data yet");
		xyPlot.setOutlineStroke(new BasicStroke(1));
		xyPlot.setOutlineVisible(true);
		xyPlot.setRenderer(new XYDotRenderer());

		NumberAxis rangeAxis = new NumberAxis("time [nanos]");
		rangeAxis.setAutoRange(true);
		xyPlot.setDomainAxis(rangeAxis);

		NumberAxis yAxis = new NumberAxis("Amplitude");
		yAxis.setAutoRange(false);
		yAxis.setRange(Short.MIN_VALUE, Short.MAX_VALUE);
		xyPlot.setRangeAxis(yAxis);

		JFreeChart chart = new JFreeChart("Sound Data", xyPlot);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		hints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
		hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setRenderingHints(hints);
		ChartPanel panel = new ChartPanel(chart, false);
		panel.setPreferredSize(new Dimension(500, 300));

		window.getContentPane().add(panel, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);

		functorDataProxyDataSet.start();
	}

	public static void main(String[] args) throws NoPlayerException, IOException, IncompatibleSourceException {
		new SoundGenerator();
	}

}
