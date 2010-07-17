/*
 * SoundTester.java
 *
 * Created on November 3, 2005, 2:55 PM
 */

package pt.utl.ist.elab.driver.doppler.sound;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.AbstractXYDataset;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class SoundTester extends javax.swing.JFrame {
	private SoundStudio studio = null;
	/*
	 * private GraphPlot gpfft = null; private GraphPlot gpacq = null;
	 */

	private AcqXYDataSet acqDataSet = null;
	private SpecXYDataSet specDataSet = null;

	private int sampleRate = 16000;

	/** Creates new form SoundTester */
	public SoundTester() {
		initComponents();

		studio = new SoundStudio();

		/*
		 * gpacq = new GraphPlot(GraphPlot.SIGNAL); gpacq.setPreferredSize(new
		 * Dimension(300,200)); gpacq.setBorder(new
		 * javax.swing.border.TitledBorder("Signal"));
		 * 
		 * gpfft = new GraphPlot(GraphPlot.SPECTRUM); gpfft.setPreferredSize(new
		 * Dimension(300,200)); gpfft.setBorder(new
		 * javax.swing.border.TitledBorder("FFT"));
		 * 
		 * jPanelCharts.add(gpacq); jPanelCharts.add(gpfft);
		 */

		StandardXYToolTipGenerator tooltipGen = new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, java.text.NumberFormat.getInstance(),
				java.text.NumberFormat.getInstance());

		acqDataSet = new AcqXYDataSet();
		XYPlot acqPlot = new XYPlot(acqDataSet, new NumberAxis(""), new NumberAxis(""), new StandardXYItemRenderer());

		specDataSet = new SpecXYDataSet();
		XYPlot specPlot = new XYPlot(specDataSet, new NumberAxis(""), new NumberAxis(""), new StandardXYItemRenderer());

		jPanelCharts.add(new ChartPanel(new JFreeChart(acqPlot)));
		jPanelCharts.add(new ChartPanel(new JFreeChart(specPlot)));

		jPanelCharts.setPreferredSize(new Dimension(400, 600));

		pack();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		jPanel1 = new javax.swing.JPanel();
		jButtonPlay = new javax.swing.JButton();
		jButtonStop = new javax.swing.JButton();
		jButtonPlayAcq = new javax.swing.JButton();
		jButtonShow = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jComboBoxWaves = new javax.swing.JComboBox();
		jLabel2 = new javax.swing.JLabel();
		jTextFieldFreq = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jPanelCharts = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jButtonPlay.setText("Play & Acquire");
		jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPlayActionPerformed(evt);
			}
		});

		jPanel1.add(jButtonPlay);

		jButtonStop.setText("Stop");
		jButtonStop.setEnabled(false);
		jButtonStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonStopActionPerformed(evt);
			}
		});

		jPanel1.add(jButtonStop);

		jButtonPlayAcq.setText("Play Acquired");
		jButtonPlayAcq.setEnabled(false);
		jButtonPlayAcq.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPlayAcqActionPerformed(evt);
			}
		});

		jPanel1.add(jButtonPlayAcq);

		jButtonShow.setText("Show");
		jButtonShow.setEnabled(false);
		jButtonShow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonShowActionPerformed(evt);
			}
		});

		jPanel1.add(jButtonShow);

		getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

		jLabel1.setText("Wave:");
		jPanel2.add(jLabel1);

		jComboBoxWaves.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sine", "Cosine", "Square",
				"Triangle", "Sawtooth" }));
		jPanel2.add(jComboBoxWaves);

		jLabel2.setText("Frequency:");
		jPanel2.add(jLabel2);

		jTextFieldFreq.setColumns(5);
		jTextFieldFreq.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldFreq.setText("440");
		jPanel2.add(jTextFieldFreq);

		jLabel3.setText("Hz");
		jPanel2.add(jLabel3);

		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

		jPanelCharts.setLayout(new java.awt.GridLayout(2, 1));

		getContentPane().add(jPanelCharts, java.awt.BorderLayout.SOUTH);

		pack();
	}

	// </editor-fold>//GEN-END:initComponents

	private void jButtonShowActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonShowActionPerformed
	{// GEN-HEADEREND:event_jButtonShowActionPerformed

		short[] acq = studio.getAcquiredBytesAsShort();

		System.out.println(acq.length);

		if (acq != null) {
			/*
			 * float[] acqf = new float[acq.length]; for(int i=0; i<acqf.length;
			 * i++) acqf[i] = (float)acq[i];
			 * 
			 * float[][] data = new float[2][acq.length]; for(int i=0;
			 * i<data[0].length; i++) data[0][i] = i;
			 * 
			 * data[1] = acqf;
			 * 
			 * FastScatterPlot fsp = new FastScatterPlot(data, new
			 * NumberAxis(""), new NumberAxis(""));
			 * 
			 * jPanelCharts.add(new ChartPanel(new JFreeChart("", fsp)));
			 */

			/*
			 * for(int i=0; i<acq.length; i++)
			 * acqSeries.add(i/(double)sampleRate, (double)acq[i]);
			 */

			acqDataSet.setAcquired(acq);
			specDataSet.setAcquired(fftMag(acq));
		}
	}// GEN-LAST:event_jButtonShowActionPerformed

	private void jButtonPlayAcqActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonPlayAcqActionPerformed
	{// GEN-HEADEREND:event_jButtonPlayAcqActionPerformed
		studio.playSound(studio.getAcquiredBytes(), sampleRate);
		jButtonStop.setEnabled(true);
		jButtonPlay.setEnabled(false);
		jButtonPlayAcq.setEnabled(false);
		jButtonShow.setEnabled(false);
	}// GEN-LAST:event_jButtonPlayAcqActionPerformed

	private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonStopActionPerformed
	{// GEN-HEADEREND:event_jButtonStopActionPerformed
		studio.stopAcquiring();
		studio.stopPlaying();

		jButtonStop.setEnabled(false);
		jButtonPlay.setEnabled(true);
		jButtonPlayAcq.setEnabled(true);
		jButtonShow.setEnabled(true);
	}// GEN-LAST:event_jButtonStopActionPerformed

	private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonPlayActionPerformed
	{// GEN-HEADEREND:event_jButtonPlayActionPerformed
		float freq = 440f;
		try {
			freq = Float.parseFloat(jTextFieldFreq.getText().trim());
		} catch (Exception e) {
			jTextFieldFreq.setText("" + 440);
			e.printStackTrace();
		}

		if (jComboBoxWaves.getSelectedIndex() == 0)
			studio.playAndAcquire(SoundWaves.getSineWave((byte) 127, sampleRate, freq), sampleRate, 2 * sampleRate);
		else if (jComboBoxWaves.getSelectedIndex() == 1)
			studio.playAndAcquire(SoundWaves.getCosWave((byte) 127, sampleRate, freq), sampleRate, 2 * sampleRate);
		else if (jComboBoxWaves.getSelectedIndex() == 2)
			studio.playAndAcquire(SoundWaves.getSquareWave((byte) 127, sampleRate, freq), sampleRate, 2 * sampleRate);
		else if (jComboBoxWaves.getSelectedIndex() == 3)
			studio.playAndAcquire(SoundWaves.getTriangleWave((byte) 127, sampleRate, freq), sampleRate, 2 * sampleRate);
		else if (jComboBoxWaves.getSelectedIndex() == 4)
			studio.playAndAcquire(SoundWaves.getSawtoothWave((byte) 127, sampleRate, freq), sampleRate, 2 * sampleRate);

		jButtonStop.setEnabled(true);
		jButtonPlay.setEnabled(false);
		jButtonPlayAcq.setEnabled(false);
		jButtonShow.setEnabled(false);
	}// GEN-LAST:event_jButtonPlayActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SoundTester().setVisible(true);
			}
		});
	}

	private int n, nu;

	private int bitrev(int j) {
		int j2;
		int j1 = j;
		int k = 0;
		for (int i = 1; i <= nu; i++) {
			j2 = j1 / 2;
			k = 2 * k + j1 - 2 * j2;
			j1 = j2;
		}
		return k;
	}

	public final float[] fftMag(short[] x) {
		float[] temp = new float[x.length];
		for (int i = 0; i < temp.length; i++)
			temp[i] = (float) x[i];

		return fftMag(temp);
	}

	public final float[] fftMag(float[] values) {
		int tempN = values.length;

		// must be power of 2
		while (!powerOf2(tempN))
			tempN++;
		float[] x = new float[tempN];
		System.arraycopy(values, 0, x, 0, values.length);

		System.out.println(x.length);

		n = x.length;
		nu = (int) (Math.log(n) / Math.log(2));
		int n2 = n / 2;
		int nu1 = nu - 1;
		float[] xre = new float[n];
		float[] xim = new float[n];
		float[] mag = new float[n2];
		float tr, ti, p, arg, c, s;
		for (int i = 0; i < n; i++) {
			xre[i] = x[i];
			xim[i] = 0.0f;
		}
		int k = 0;

		for (int l = 1; l <= nu; l++) {
			while (k < n) {
				for (int i = 1; i <= n2; i++) {
					p = bitrev(k >> nu1);
					arg = 2 * (float) Math.PI * p / n;
					c = (float) Math.cos(arg);
					s = (float) Math.sin(arg);
					tr = xre[k + n2] * c + xim[k + n2] * s;
					ti = xim[k + n2] * c - xre[k + n2] * s;
					xre[k + n2] = xre[k] - tr;
					xim[k + n2] = xim[k] - ti;
					xre[k] += tr;
					xim[k] += ti;
					k++;
				}
				k += n2;
			}
			k = 0;
			nu1--;
			n2 = n2 / 2;
		}
		k = 0;
		int r;
		while (k < n) {
			r = bitrev(k);
			if (r > k) {
				tr = xre[k];
				ti = xim[k];
				xre[k] = xre[r];
				xim[k] = xim[r];
				xre[r] = tr;
				xim[r] = ti;
			}
			k++;
		}

		mag[0] = (float) (Math.sqrt(xre[0] * xre[0] + xim[0] * xim[0])) / n;
		for (int i = 1; i < n / 2; i++) {
			mag[i] = 2 * (float) (Math.sqrt(xre[i] * xre[i] + xim[i] * xim[i])) / n;
		}

		return mag;
	}

	private boolean powerOf2(int number) {
		if (number < 1) {
			return false;
		}
		if (number == 1) {
			return true;
		}
		if ((number & 1) == 1) {
			return false;
		}
		return powerOf2(number >> 1);
	}

	private class AcqXYDataSet extends AbstractXYDataset {
		private short[] acquired = null;

		public int getItemCount(int param) {
			if (acquired == null)
				return 0;

			return acquired.length;
		}

		public int getSeriesCount() {
			return 1;
		}

		public String getSeriesName(int param) {
			return "";
		}

		public Number getX(int param, int param1) {
			return new Double(getXValue(param, param1));
		}

		public double getXValue(int param, int param1) {
			return param1 / (double) sampleRate;
		}

		public Number getY(int param, int param1) {
			return new Double(getYValue(param, param1));
		}

		public double getYValue(int param, int param1) {
			if (acquired == null)
				return 0;

			return acquired[param1];
		}

		public void setAcquired(short[] acquired) {
			this.acquired = acquired;
			fireDatasetChanged();
		}

		@Override
		public Comparable getSeriesKey(int arg0) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

	}

	private class SpecXYDataSet extends AbstractXYDataset {
		private float[] acquired = null;

		public int getItemCount(int param) {
			if (acquired == null)
				return 0;

			return acquired.length;
		}

		public int getSeriesCount() {
			return 1;
		}

		public String getSeriesName(int param) {
			return "";
		}

		public Number getX(int param, int param1) {
			return new Double(getXValue(param, param1));
		}

		public double getXValue(int param, int param1) {
			if (acquired == null)
				return 0;

			return 0.5 * sampleRate * param1 / acquired.length;
		}

		public Number getY(int param, int param1) {
			return new Double(getYValue(param, param1));
		}

		public double getYValue(int param, int param1) {
			if (acquired == null)
				return 0;

			return acquired[param1];
		}

		public void setAcquired(float[] acquired) {
			this.acquired = acquired;
			fireDatasetChanged();
		}

		@Override
		public Comparable getSeriesKey(int arg0) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonPlay;
	private javax.swing.JButton jButtonPlayAcq;
	private javax.swing.JButton jButtonShow;
	private javax.swing.JButton jButtonStop;
	private javax.swing.JComboBox jComboBoxWaves;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanelCharts;
	private javax.swing.JTextField jTextFieldFreq;
	// End of variables declaration//GEN-END:variables

}
