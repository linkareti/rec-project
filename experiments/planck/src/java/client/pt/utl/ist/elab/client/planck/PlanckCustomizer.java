package pt.utl.ist.elab.client.planck;

import java.text.DecimalFormat;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 */
public class PlanckCustomizer extends AbstractCustomizer {

	/** Generated UID */
	private static final long serialVersionUID = 6341409723545193412L;

	/** Creates new form RadioactividadeCustomizer */
	public PlanckCustomizer() {
		initComponents();

		final Dictionary<Integer, JLabel> slidersPosLabels = new Hashtable<Integer, JLabel>(4);
		slidersPosLabels.put(Integer.valueOf(0), new JLabel("0.0"));
		slidersPosLabels.put(Integer.valueOf(900), new JLabel("90.0"));
		slidersPosLabels.put(Integer.valueOf(1800), new JLabel("180.0"));
		slidersPosLabels.put(Integer.valueOf(2700), new JLabel("270.0"));
		slidersPosLabels.put(Integer.valueOf(3600), new JLabel("360.0"));

		sldPos1.setLabelTable(slidersPosLabels);
		sldPos2.setLabelTable(slidersPosLabels);
		final DecimalFormat format = new DecimalFormat("0.0");
		format.setDecimalSeparatorAlwaysShown(true);
		format.setGroupingUsed(false);
		format.setMinimumFractionDigits(1);
		final NumberFormatter formatterUserPos1 = new NumberFormatter(format);
		final NumberFormatter formatterUserPos2 = new NumberFormatter(format);

		formatterUserPos1.setCommitsOnValidEdit(true);
		formatterUserPos2.setCommitsOnValidEdit(true);

		formatterUserPos1.setOverwriteMode(true);
		formatterUserPos2.setOverwriteMode(true);

		formatterUserPos1.install(tfPos1);
		formatterUserPos2.install(tfPos2);

		checkMaxNumSamples();
		checkMaxTime();
		checkPosOverlap();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel2 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btnDefaults = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jPanel6 = new javax.swing.JPanel();
		sldNumSamples = new javax.swing.JSlider();
		tfNumSamples = new javax.swing.JTextField();
		lblErrorSamplesTooHigh = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		sldFreq = new javax.swing.JSlider();
		tfFreq = new javax.swing.JTextField();
		lblSamplingIntervalTooHigh = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel();
		sldPos1 = new javax.swing.JSlider();
		sldPos2 = new javax.swing.JSlider();
		lblErrorVolsAreEqua = new javax.swing.JLabel();
		tfPos1 = new javax.swing.JFormattedTextField();
		tfPos2 = new javax.swing.JFormattedTextField();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(350, 460));
		setPreferredSize(new java.awt.Dimension(350, 460));
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanel2.add(btnOK, gridBagConstraints);

		btnCancel.setText("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		jPanel2.add(btnCancel, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 10.0;
		jPanel2.add(jLabel1, gridBagConstraints);

		btnDefaults.setText(ReCResourceBundle.findStringOrDefault("rec.exp.dftcfg.planck.title.1", "Default Config"));
		btnDefaults.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnDefaultsActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		jPanel2.add(btnDefaults, gridBagConstraints);

		jLabel3.setText("    ");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1.0;
		jPanel2.add(jLabel3, gridBagConstraints);

		add(jPanel2, java.awt.BorderLayout.SOUTH);

		jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

		jPanel3.setMinimumSize(new java.awt.Dimension(350, 160));
		jPanel6.setLayout(new java.awt.GridBagLayout());

		jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"planck$rec.exp.customizer.title2", "planck$rec.exp.customizer.title2")));
		jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
		jPanel6.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel6.setPreferredSize(new java.awt.Dimension(350, 80));
		sldNumSamples.setMajorTickSpacing(40);
		sldNumSamples.setMaximum(240);
		sldNumSamples.setMinorTickSpacing(23);
		sldNumSamples.setPaintLabels(true);
		sldNumSamples.setPaintTicks(true);
		sldNumSamples.setPaintTrack(false);
		sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
		sldNumSamples.setMinimumSize(new java.awt.Dimension(250, 42));
		sldNumSamples.setPreferredSize(new java.awt.Dimension(250, 42));
		sldNumSamples.setValue(1);
		sldNumSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				sldNumSamplesStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.weighty = 10.0;
		jPanel6.add(sldNumSamples, gridBagConstraints);

		tfNumSamples.setColumns(3);
		tfNumSamples.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNumSamples.setText("50");
		tfNumSamples.setMaximumSize(new java.awt.Dimension(30, 16));
		tfNumSamples.setMinimumSize(new java.awt.Dimension(30, 16));
		tfNumSamples.setPreferredSize(new java.awt.Dimension(37, 16));
		tfNumSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				tfNumSamplesFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel6.add(tfNumSamples, gridBagConstraints);

		lblErrorSamplesTooHigh.setForeground(new java.awt.Color(255, 0, 0));
		lblErrorSamplesTooHigh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findStringOrDefault("planck$rec.exp.customizer.label2",
				"planck$rec.exp.customizer.label2"));
		lblErrorSamplesTooHigh.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel6.add(lblErrorSamplesTooHigh, gridBagConstraints);

		jPanel3.add(jPanel6);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"planck$rec.exp.customizer.title3", "planck$rec.exp.customizer.title3")));
		jPanel1.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel1.setPreferredSize(new java.awt.Dimension(350, 80));
		sldFreq.setMajorTickSpacing(400);
		sldFreq.setMaximum(2000);
		sldFreq.setMinorTickSpacing(200);
		sldFreq.setPaintLabels(true);
		sldFreq.setPaintTicks(true);
		sldFreq.setPaintTrack(false);
		sldFreq.setMaximumSize(new java.awt.Dimension(1000, 32767));
		sldFreq.setMinimumSize(new java.awt.Dimension(255, 80));
		sldFreq.setPreferredSize(new java.awt.Dimension(255, 80));
		sldFreq.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				sldFreqStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		gridBagConstraints.weighty = 10.0;
		jPanel1.add(sldFreq, gridBagConstraints);

		tfFreq.setColumns(4);
		tfFreq.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFreq.setText("50");
		tfFreq.setMaximumSize(new java.awt.Dimension(30, 16));
		tfFreq.setMinimumSize(new java.awt.Dimension(30, 16));
		tfFreq.setPreferredSize(new java.awt.Dimension(48, 16));
		tfFreq.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				tfFreqFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel1.add(tfFreq, gridBagConstraints);

		lblSamplingIntervalTooHigh.setForeground(new java.awt.Color(255, 0, 0));
		lblSamplingIntervalTooHigh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblSamplingIntervalTooHigh.setText(ReCResourceBundle.findStringOrDefault("planck$rec.exp.customizer.label3",
				"planck$rec.exp.customizer.label3"));
		lblSamplingIntervalTooHigh.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel1.add(lblSamplingIntervalTooHigh, gridBagConstraints);
		lblSamplingIntervalTooHigh.getAccessibleContext().setAccessibleName(
				ReCResourceBundle.findStringOrDefault("rec.exp.customizer.label3", "The time between samples"));

		jPanel3.add(jPanel1);

		add(jPanel3, java.awt.BorderLayout.CENTER);

		jPanel5.setLayout(new java.awt.GridBagLayout());

		jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"planck$rec.exp.customizer.title1", "planck$rec.exp.customizer.title1")));
		jPanel5.setMinimumSize(new java.awt.Dimension(350, 160));
		jPanel5.setPreferredSize(new java.awt.Dimension(350, 160));
		sldPos1.setMajorTickSpacing(900);
		sldPos1.setMaximum(3600);
		sldPos1.setMinimum(0);
		sldPos1.setMinorTickSpacing(100);
		sldPos1.setPaintLabels(true);
		sldPos1.setPaintTicks(true);
		sldPos1.setPaintTrack(false);
		sldPos1.setSnapToTicks(true);
		sldPos1.setValue(0);
		sldPos1.setMinimumSize(new java.awt.Dimension(250, 42));
		sldPos1.setPreferredSize(new java.awt.Dimension(250, 42));
		sldPos1.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				sldPos1StateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(sldPos1, gridBagConstraints);

		sldPos2.setMajorTickSpacing(900);
		sldPos2.setMaximum(3600);
		sldPos2.setMinimum(0);
		sldPos2.setMinorTickSpacing(100);
		sldPos2.setPaintLabels(true);
		sldPos2.setPaintTicks(true);
		sldPos2.setPaintTrack(false);
		sldPos2.setSnapToTicks(true);
		sldPos2.setValue(3600);
		sldPos2.setMinimumSize(new java.awt.Dimension(250, 42));
		sldPos2.setPreferredSize(new java.awt.Dimension(250, 42));
		sldPos2.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				sldPos2StateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(sldPos2, gridBagConstraints);

		lblErrorVolsAreEqua.setForeground(new java.awt.Color(255, 0, 0));
		lblErrorVolsAreEqua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblErrorVolsAreEqua.setText(ReCResourceBundle.findStringOrDefault("planck$rec.exp.customizer.label1",
				"planck$rec.exp.customizer.label1"));
		lblErrorVolsAreEqua.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel5.add(lblErrorVolsAreEqua, gridBagConstraints);

		tfPos1.setText("0.0");
		tfPos1.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				tfPos1FocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel5.add(tfPos1, gridBagConstraints);

		tfPos2.setText("360.0");
		tfPos2.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				tfPos2FocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel5.add(tfPos2, gridBagConstraints);

		add(jPanel5, java.awt.BorderLayout.NORTH);

	}// GEN-END:initComponents

	private void tfFreqFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfFreqFocusLost
	{// GEN-HEADEREND:event_tfFreqFocusLost

		// TODO
		// String strFreq = tfFreq.getText();
		// if (strFreq.trim().equals(""))
		// return;
		// try {
		// int Freq = Integer.parseInt(strFreq);
		// if (Freq <= sldFreq.getMaximum() && Freq > sldFreq.getMinimum())
		// sldFreq.setValue(Freq);
		// else
		// tfFreq.setText("" + sldFreq.getValue());
		// } catch (Exception e) {
		// tfFreq.setText("" + sldFreq.getValue());
		// }
		// checkMaxTime();

	}// GEN-LAST:event_tfFreqFocusLost

	private void tfNumSamplesFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfNumSamplesFocusLost
	{// GEN-HEADEREND:event_tfNumSamplesFocusLost

		// TODO
		// String strNumSamples = tfNumSamples.getText();
		// if (strNumSamples.trim().equals(""))
		// return;
		// try {
		// int numSamples = Integer.parseInt(strNumSamples);
		// if (numSamples <= sldNumSamples.getMaximum() && numSamples >
		// sldNumSamples.getMinimum())
		// sldNumSamples.setValue(numSamples);
		// else
		// tfNumSamples.setText("" + sldNumSamples.getValue());
		// } catch (Exception e) {
		// tfNumSamples.setText("" + sldNumSamples.getValue());
		// }
		// checkMaxNumSamples();
		// checkMaxTime();

	}// GEN-LAST:event_tfNumSamplesFocusLost

	private void tfPos2FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfPos2FocusLost
	{// GEN-HEADEREND:event_tfPos2FocusLost

		// TODO
		final String strPos2 = tfPos2.getText();
		if (strPos2.trim().equals("")) {
			return;
		}
		try {
			final int Pos2 = (int) (Float.parseFloat(strPos2) * 10.F);
			if (Pos2 <= sldPos2.getMaximum() && Pos2 > sldPos2.getMinimum()) {
				sldPos2.setValue(Pos2);
			} else {
				tfPos2.setValue(Float.valueOf((sldPos2.getValue() / 10.F)));
			}
		} catch (final Exception e) {
			tfPos2.setValue(Float.valueOf((sldPos2.getValue() / 10.F)));
		}
		// checkPosOverlap();
		// checkMaxNumSamples();

	}// GEN-LAST:event_tfPos2FocusLost

	private void tfPos1FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfPos1FocusLost
	{// GEN-HEADEREND:event_tfPos1FocusLost

		// TODO
		final String strPos1 = tfPos1.getText();

		if (strPos1.trim().equals("")) {
			return;
		}
		try {
			final int Pos1 = (int) (Float.parseFloat(strPos1) * 10.F);
			if (Pos1 <= sldPos1.getMaximum() && Pos1 > sldPos1.getMinimum()) {
				sldPos1.setValue(Pos1);
			} else {
				tfPos1.setValue(Float.valueOf((sldPos1.getValue() / 10.F)));
			}

		} catch (final Exception e) {
			tfPos1.setValue(Float.valueOf((sldPos1.getValue() / 10.F)));
		}
		// checkPosOverlap();
		// checkMaxNumSamples();

	}// GEN-LAST:event_tfPos1FocusLost

	private void sldPos1StateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldPos1StateChanged
	{// GEN-HEADEREND:event_sldPos1StateChanged

		// TODO
		tfPos1.setValue(Float.valueOf((sldPos1.getValue() / 10.F)));
		// checkPosOverlap();
		// checkMaxNumSamples();

	}// GEN-LAST:event_sldPos1StateChanged

	private void sldPos2StateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldPos2StateChanged
	{// GEN-HEADEREND:event_sldPos2StateChanged

		// TODO
		tfPos2.setValue(Float.valueOf((sldPos2.getValue() / 10.F)));
		// checkPosOverlap();
		// checkMaxNumSamples();

	}// GEN-LAST:event_sldPos2StateChanged

	private void sldFreqStateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldFreqStateChanged
	{// GEN-HEADEREND:event_sldFreqStateChanged

		if (sldFreq.getValue() == 0) {
			sldFreq.setValue(1);
		}
		tfFreq.setText("" + sldFreq.getValue());

		// TODO
		// checkMaxTime();

	}// GEN-LAST:event_sldFreqStateChanged

	private void sldNumSamplesStateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldNumSamplesStateChanged
	{// GEN-HEADEREND:event_sldNumSamplesStateChanged

		if (sldNumSamples.getValue() == 0) {
			sldNumSamples.setValue(1);

		}
		tfNumSamples.setText("" + sldNumSamples.getValue());

		// TODO
		// checkMaxNumSamples();
		// checkMaxTime();

	}// GEN-LAST:event_sldNumSamplesStateChanged

	private void checkPosOverlap() {
		lblErrorVolsAreEqua.setEnabled(sldPos1.getValue() == sldPos2.getValue());
		btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled()
				&& !lblSamplingIntervalTooHigh.isEnabled());
	}

	private void checkMaxNumSamples() {
		lblErrorSamplesTooHigh
				.setEnabled(Math.abs(sldPos2.getValue() - sldPos1.getValue()) * 80. / 1000.F < sldNumSamples.getValue());
		btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled()
				&& !lblSamplingIntervalTooHigh.isEnabled());
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findStringOrDefault("planck$rec.exp.customizer.label2",
				"planck$rec.exp.customizer.label2")
				+ (int) Math.floor(Math.abs(sldPos2.getValue() - sldPos1.getValue()) * 80. / 1000.F));
	}

	public void checkMaxTime() {
		final float maxValue = Math.min(sldFreq.getMaximum(), 72000.F / sldNumSamples.getValue());
		lblSamplingIntervalTooHigh.setEnabled(sldFreq.getValue() > maxValue);
		btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled()
				&& !lblSamplingIntervalTooHigh.isEnabled());
		lblSamplingIntervalTooHigh.setText(ReCResourceBundle.findStringOrDefault("planck$rec.exp.customizer.label2",
				"planck$rec.exp.customizer.label2") + (int) maxValue);
	}

	private void btnDefaultsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		sldNumSamples.setValue(18);
		tfNumSamples.setText("18");
		sldPos1.setValue(0);
		tfPos1.setValue(Float.valueOf(0.0));
		sldPos2.setValue(3600);
		tfPos2.setValue(Float.valueOf(360.0));
		sldFreq.setValue(150);
		tfFreq.setText("150");
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed

		final int nsamples = sldNumSamples.getValue() < 10 ? 10 : sldNumSamples.getValue();
		getAcquisitionConfig().setTotalSamples(nsamples);

		getAcquisitionConfig().getSelectedHardwareParameter("protocolo").setParameterValue("1");
		getAcquisitionConfig().getSelectedHardwareParameter("ang1_min").setParameterValue("" + (sldPos1.getValue() / 10.F));
		getAcquisitionConfig().getSelectedHardwareParameter("ang1_max").setParameterValue("" + (sldPos2.getValue() / 10.F));
		getAcquisitionConfig().getSelectedHardwareParameter("delta_ang1").setParameterValue("10");
		getAcquisitionConfig().getSelectedHardwareParameter("delay").setParameterValue("2");

		// acqConfig.setSelectedFrequency(new Frequency((double)
		// sldFreq.getValue(), hardwareInfo
		// .getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),
		// hardwareInfo
		// .getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));

		fireICustomizerListenerDone();

	}// GEN-LAST:event_btnOKActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JLabel lblErrorSamplesTooHigh;
	private javax.swing.JLabel lblErrorVolsAreEqua;
	private javax.swing.JLabel lblSamplingIntervalTooHigh;
	private javax.swing.JSlider sldFreq;
	private javax.swing.JSlider sldNumSamples;
	private javax.swing.JSlider sldPos1;
	private javax.swing.JSlider sldPos2;
	private javax.swing.JTextField tfFreq;
	private javax.swing.JTextField tfNumSamples;
	private javax.swing.JFormattedTextField tfPos1;
	private javax.swing.JFormattedTextField tfPos2;

	// End of variables declaration//GEN-END:variables

	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			System.out.println("PlanckCustomizer.setHardwareAcquisitionConfig(HardwareAcquisitionConfig  acqConfig)");
			System.out.println("acqConfig: [" + acqConfig + "]");

			// TODO
			// int nsamples = acqConfig.getTotalSamples();
			// sldNumSamples.setValue(nsamples);
			// tfNumSamples.setText("" + nsamples);
			//
			// int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			// sldFreq.setValue(freq);
			// tfFreq.setText("" + freq);
			//
			// float pos1f =
			// Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("UserPosLow"));
			// int pos1 = (int) Math.floor(pos1f * 1000.F);
			// sldPos1.setValue(pos1);
		}
	}

	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/planck/resources/planck_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("planck$rec.exp.planck.customizer.title",
				"planck$rec.exp.planck.customizer.title");
	}

}
