package com.linkare.rec.jmf;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.media.IncompatibleSourceException;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;

import com.linkare.rec.jmf.media.datasink.capture.Handler;

public class SoundRecorder {

	private static final int NUM_CHANNELS_DEFAULT = 2;
	private static final int BITS_PER_CHANNEL_DEFAULT = 16;

	private int numChannels = NUM_CHANNELS_DEFAULT;
	private int bitsPerChannel = BITS_PER_CHANNEL_DEFAULT;
	private int endian = javax.media.format.AudioFormat.LITTLE_ENDIAN;
	private double sampleRate = 44100;
	private int signed = AudioFormat.SIGNED;

	private long desiredBufferLength = 16L;

	public SoundRecorder() throws NoPlayerException, IOException, IncompatibleSourceException, NoDataSourceException,
			NoDataSinkException {

		ReCJMFUtils.initReCJMFPackages();
		ReCJMFUtils.autoDetectJavaSoundDevices();
		
		String deviceLocation = ReCJMFUtils.locateCaptureDeviceForParameters(sampleRate, bitsPerChannel, numChannels,
				endian, signed);

		if (deviceLocation != null) {
			DataSource dataSource = ReCJMFUtils.locateDataSource(deviceLocation, desiredBufferLength);
			final Handler handler = ReCJMFUtils.createCaptureDeviceFor(dataSource, (int) sampleRate, bitsPerChannel,
					numChannels);

			SwingUtilities.invokeLater(new Runnable() {
				/**
				 * {@inheritDoc}
				 */
				@Override
				public void run() {
					XYPlot xyPlot = new XYPlot();
					new SinkProxyDataSet(handler, xyPlot);
					// combinedPlot.setDataset(new SinkProxyDataSet(handler));
					xyPlot.setNoDataMessage("No data yet");
					xyPlot.setOutlineStroke(new BasicStroke(1));
					xyPlot.setOutlineVisible(true);
					xyPlot.setRenderer(new XYDotRenderer());

					NumberAxis rangeAxis = new NumberAxis("amostra [nr]");
					rangeAxis.setAutoRange(true);
					rangeAxis.setRange(0, 11025/4);
					xyPlot.setDomainAxis(rangeAxis);

					NumberAxis yAxis = new NumberAxis("Sinal");
					yAxis.setAutoRange(false);
					yAxis.setRange(-1, 1);
					xyPlot.setRangeAxis(yAxis);

					JFreeChart chart = new JFreeChart("Sound Data", xyPlot);
					RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_OFF);
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

					JFrame frame = new JFrame();
					frame.setLayout(new BorderLayout());
					frame.getContentPane().add(panel, BorderLayout.CENTER);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
				}
			});
			

			// handler.addDataSinkListener(new DataSinkListener() {
			//
			// @Override
			// public void dataSinkUpdate(DataSinkEvent arg0) {
			// handler.captureFrame(11025/4, 11025., true);
			// }
			// });			

			ReCJMFUtils.startCapturing(dataSource, handler);
			
		}
	}

	public static void main(String[] args) throws NoPlayerException, IOException, IncompatibleSourceException,
			NoDataSourceException, NoDataSinkException {
		new SoundRecorder();
	}

}
