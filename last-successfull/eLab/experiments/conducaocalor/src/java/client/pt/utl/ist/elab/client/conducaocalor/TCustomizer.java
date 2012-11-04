/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */

package pt.utl.ist.elab.client.conducaocalor;

import javax.swing.SwingConstants;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class TCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5942185880915532975L;

	/** Creates new form RadioactividadeCustomizer */
	public TCustomizer() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		buttonGroup1 = new javax.swing.ButtonGroup();
		jPanel7 = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jRadioButtonOsc = new javax.swing.JRadioButton();
		jRadioButtonEst = new javax.swing.JRadioButton();
		jPanel3 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		jSliderAqc = new javax.swing.JSlider();
		jTextFieldAqc = new javax.swing.JTextField();
		jPanel1 = new javax.swing.JPanel();
		jSliderTBS = new javax.swing.JSlider();
		jTextFieldTBS = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		jSliderNumSamples = new javax.swing.JSlider();
		jTextFieldSamples = new javax.swing.JTextField();
		jLabelInfo = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jSliderTMax = new javax.swing.JSlider();
		jTextFieldTMax = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btnDefaults = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(520, 460));
		setPreferredSize(new java.awt.Dimension(520, 460));
		jPanel7.setLayout(new java.awt.BorderLayout());

		jPanel4.setLayout(new java.awt.GridBagLayout());

		jPanel4.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("conducaocalor$rec.exp.t.lbl.mode","conducaocalor$rec.exp.t.lbl.mode")));
		jRadioButtonOsc.setSelected(true);
		jRadioButtonOsc.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.osc","conducaocalor$rec.exp.t.lbl.osc"));
		buttonGroup1.add(jRadioButtonOsc);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel4.add(jRadioButtonOsc, gridBagConstraints);

		jRadioButtonEst.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.stat","conducaocalor$rec.exp.t.lbl.stat"));
		buttonGroup1.add(jRadioButtonEst);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel4.add(jRadioButtonEst, gridBagConstraints);

		jPanel7.add(jPanel4, java.awt.BorderLayout.NORTH);

		jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

		jPanel3.setMinimumSize(new java.awt.Dimension(350, 160));
		jPanel5.setLayout(new java.awt.GridBagLayout());

		jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("conducaocalor$rec.exp.t.lbl.heattime","conducaocalor$rec.exp.t.lbl.heattime")));
		jPanel5.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel5.setPreferredSize(new java.awt.Dimension(350, 80));
		jSliderAqc.setMajorTickSpacing(20);
		jSliderAqc.setMaximum(150);
		jSliderAqc.setMinimum(10);
		jSliderAqc.setMinorTickSpacing(10);
		jSliderAqc.setPaintLabels(true);
		jSliderAqc.setPaintTicks(true);
		jSliderAqc.setMinimumSize(new java.awt.Dimension(250, 42));
		jSliderAqc.setPreferredSize(new java.awt.Dimension(250, 42));
		jSliderAqc.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderAqcStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(jSliderAqc, gridBagConstraints);

		jTextFieldAqc.setColumns(3);
		jTextFieldAqc.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldAqc.setText("50");
		jTextFieldAqc.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldAqcFocusLost(evt);
			}
		});

		jPanel5.add(jTextFieldAqc, new java.awt.GridBagConstraints());

		jPanel3.add(jPanel5);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("conducaocalor$rec.exp.t.lbl.tbs","conducaocalor$rec.exp.t.lbl.tbs")));
		jPanel1.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel1.setPreferredSize(new java.awt.Dimension(350, 80));
		jSliderTBS.setMajorTickSpacing(1);
		jSliderTBS.setMaximum(10);
		jSliderTBS.setMinimum(1);
		jSliderTBS.setPaintLabels(true);
		jSliderTBS.setPaintTicks(true);
		jSliderTBS.setValue(1);
		jSliderTBS.setMaximumSize(new java.awt.Dimension(1000, 32767));
		jSliderTBS.setMinimumSize(new java.awt.Dimension(255, 80));
		jSliderTBS.setPreferredSize(new java.awt.Dimension(255, 80));
		jSliderTBS.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderTBSStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		gridBagConstraints.weighty = 10.0;
		jPanel1.add(jSliderTBS, gridBagConstraints);

		jTextFieldTBS.setColumns(3);
		jTextFieldTBS.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldTBS.setText("1");
		jTextFieldTBS.setMaximumSize(new java.awt.Dimension(30, 16));
		jTextFieldTBS.setMinimumSize(new java.awt.Dimension(30, 16));
		jTextFieldTBS.setPreferredSize(new java.awt.Dimension(37, 16));
		jTextFieldTBS.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldTBSFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel1.add(jTextFieldTBS, gridBagConstraints);

		jPanel3.add(jPanel1);

		jPanel6.setLayout(new java.awt.GridBagLayout());

		jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("conducaocalor$rec.exp.t.lbl.nsamples","conducaocalor$rec.exp.t.lbl.nsamples")));
		jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
		jPanel6.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel6.setPreferredSize(new java.awt.Dimension(350, 80));
		jSliderNumSamples.setMajorTickSpacing(300);
		jSliderNumSamples.setMaximum(1500);
		jSliderNumSamples.setPaintLabels(true);
		jSliderNumSamples.setPaintTicks(true);
		jSliderNumSamples.setValue(200);
		jSliderNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
		jSliderNumSamples.setMinimumSize(new java.awt.Dimension(250, 42));
		jSliderNumSamples.setPreferredSize(new java.awt.Dimension(1000, 42));
		jSliderNumSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderNumSamplesStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weighty = 10.0;
		jPanel6.add(jSliderNumSamples, gridBagConstraints);

		jTextFieldSamples.setColumns(4);
		jTextFieldSamples.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldSamples.setText("200");
		jTextFieldSamples.setMaximumSize(new java.awt.Dimension(30, 16));
		jTextFieldSamples.setMinimumSize(new java.awt.Dimension(30, 16));
		jTextFieldSamples.setPreferredSize(new java.awt.Dimension(48, 16));
		jTextFieldSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldSamplesFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel6.add(jTextFieldSamples, gridBagConstraints);

		jLabelInfo.setForeground(new java.awt.Color(255, 0, 0));
		jLabelInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.exptime","conducaocalor$rec.exp.t.lbl.exptime"));
		jLabelInfo.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel6.add(jLabelInfo, gridBagConstraints);

		jPanel3.add(jPanel6);

		jPanel7.add(jPanel3, java.awt.BorderLayout.CENTER);

		jPanel8.setLayout(new java.awt.GridBagLayout());

		jPanel8.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("conducaocalor$rec.exp.t.lbl.maxtemp","conducaocalor$rec.exp.t.lbl.maxtemp")));
		jPanel8.setMinimumSize(new java.awt.Dimension(170, 85));
		jPanel8.setPreferredSize(new java.awt.Dimension(170, 85));
		jSliderTMax.setMajorTickSpacing(10);
		jSliderTMax.setMaximum(75);
		jSliderTMax.setMinimum(25);
		jSliderTMax.setMinorTickSpacing(5);
		jSliderTMax.setOrientation(SwingConstants.VERTICAL);
		jSliderTMax.setPaintLabels(true);
		jSliderTMax.setPaintTicks(true);
		jSliderTMax.setSnapToTicks(true);
		jSliderTMax.setMinimumSize(new java.awt.Dimension(250, 42));
		jSliderTMax.setPreferredSize(new java.awt.Dimension(50, 42));
		jSliderTMax.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderTMaxStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel8.add(jSliderTMax, gridBagConstraints);

		jTextFieldTMax.setColumns(3);
		jTextFieldTMax.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldTMax.setText("50");
		jTextFieldTMax.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldTMaxFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanel8.add(jTextFieldTMax, gridBagConstraints);

		jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

		add(jPanel7, java.awt.BorderLayout.EAST);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.ok","conducaocalor$rec.exp.t.lbl.ok"));
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

		btnCancel.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.cancel","conducaocalor$rec.exp.t.lbl.cancel"));
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

		btnDefaults.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.dftacq","conducaocalor$rec.exp.t.lbl.dftacq"));
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

	}// GEN-END:initComponents

	private void jTextFieldTMaxFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldTMaxFocusLost
		final String strTMax = jTextFieldTMax.getText();

		if (strTMax.trim().equals("")) {
			return;
		}
		try {
			final int TMax = Integer.parseInt(strTMax);
			if (TMax <= jSliderTMax.getMaximum() && TMax > jSliderTMax.getMinimum()) {
				jSliderTMax.setValue(TMax);
			} else {
				jTextFieldTMax.setText("" + jSliderTMax.getValue());
			}

		} catch (final Exception e) {
			jTextFieldTMax.setText("" + jSliderTMax.getValue());
		}
	}// GEN-LAST:event_jTextFieldTMaxFocusLost

	private void jSliderTMaxStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderTMaxStateChanged
		jTextFieldTMax.setText("" + jSliderTMax.getValue());
	}// GEN-LAST:event_jSliderTMaxStateChanged

	private void jTextFieldSamplesFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSamplesFocusLost
		final String strNumSamples = jTextFieldSamples.getText();
		if (strNumSamples.trim().equals("")) {
			return;
		}
		try {
			final int numSamples = Integer.parseInt(strNumSamples);
			if (numSamples <= jSliderNumSamples.getMaximum() && numSamples > jSliderNumSamples.getMinimum()) {
				jSliderNumSamples.setValue(numSamples);
			} else {
				jTextFieldSamples.setText("" + jSliderNumSamples.getValue());
			}
		} catch (final Exception e) {
			jTextFieldSamples.setText("" + jSliderNumSamples.getValue());
		}
		checkMaxNumSamples();
	}// GEN-LAST:event_jTextFieldSamplesFocusLost

	private void jTextFieldTBSFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldTBSFocusLost
		final String strFreq = jTextFieldTBS.getText();
		if (strFreq.trim().equals("")) {
			return;
		}
		try {
			final int Freq = Integer.parseInt(strFreq);
			if (Freq <= jSliderTBS.getMaximum() && Freq > jSliderTBS.getMinimum()) {
				jSliderTBS.setValue(Freq);
			} else {
				jTextFieldTBS.setText("" + jSliderTBS.getValue());
			}
		} catch (final Exception e) {
			jTextFieldTBS.setText("" + jSliderTBS.getValue());
		}
		checkMaxNumSamples();
	}// GEN-LAST:event_jTextFieldTBSFocusLost

	private void jTextFieldAqcFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldAqcFocusLost
		final String strPos1 = jTextFieldAqc.getText();

		if (strPos1.trim().equals("")) {
			return;
		}
		try {
			final int Pos1 = Integer.parseInt(strPos1);
			if (Pos1 <= jSliderAqc.getMaximum() && Pos1 > jSliderAqc.getMinimum()) {
				jSliderAqc.setValue(Pos1);
			} else {
				jTextFieldAqc.setText("" + jSliderAqc.getValue());
			}

		} catch (final Exception e) {
			jTextFieldAqc.setText("" + jSliderAqc.getValue());
		}
	}// GEN-LAST:event_jTextFieldAqcFocusLost

	private void jSliderAqcStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderAqcStateChanged
		jTextFieldAqc.setText("" + jSliderAqc.getValue());
		checkHeat();
	}// GEN-LAST:event_jSliderAqcStateChanged

	private void jSliderTBSStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderTBSStateChanged
		jTextFieldTBS.setText("" + jSliderTBS.getValue());
		checkHeat();
		checkMaxNumSamples();
	}// GEN-LAST:event_jSliderTBSStateChanged

	private void jSliderNumSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderNumSamplesStateChanged

		if (jSliderNumSamples.getValue() == 0) {
			jSliderNumSamples.setValue(1);

		}
		jTextFieldSamples.setText("" + jSliderNumSamples.getValue());

		checkMaxNumSamples();
	}// GEN-LAST:event_jSliderNumSamplesStateChanged

	public void checkMaxNumSamples() {
		final int duration = jSliderTBS.getValue() * jSliderNumSamples.getValue();

		if (duration > 1500) {
			jLabelInfo.setEnabled(true);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.maxsamples","conducaocalor$rec.exp.t.lbl.maxsamples") + " <= "
					+ (1500 / jSliderTBS.getValue()));
			btnOK.setEnabled(false);
		} else {
			jLabelInfo.setEnabled(false);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.lbl.exptime","conducaocalor$rec.exp.t.lbl.exptime") + " " + duration
					+ " s");
			btnOK.setEnabled(true);
		}
	}

	private boolean up = true;

	private void checkHeat() {
		int adjustValue = jSliderAqc.getValue();

		while ((adjustValue % (2 * jSliderTBS.getValue())) != 0) {
			/*
			 * if(up) { adjustValue++; } else {
			 */
			adjustValue--;
			// }
			jSliderAqc.setValue(adjustValue);
		}

		while (adjustValue < 10) {
			adjustValue += 2 * jSliderTBS.getValue();
		}

		up = !up;

		jSliderAqc.setValue(adjustValue);
	}

	private void btnDefaultsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		jSliderNumSamples.setValue(200);
		jTextFieldSamples.setText("200");
		jSliderAqc.setValue(50);
		jTextFieldAqc.setText("50");
		jSliderTBS.setValue(1);
		jTextFieldTBS.setText("1");
		jSliderTMax.setValue(50);
		jTextFieldTMax.setText("50");
		jRadioButtonOsc.setSelected(true);
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		acqConfig.setTotalSamples(jSliderNumSamples.getValue());
		acqConfig.getSelectedHardwareParameter("Temperatura maxima").setParameterValue("" + jSliderTMax.getValue());
		acqConfig.getSelectedHardwareParameter("Tempo aquecimento").setParameterValue("" + jSliderAqc.getValue());
		if (jRadioButtonOsc.isSelected()) {
			acqConfig.getSelectedHardwareParameter("Modo").setParameterValue("Oscilatorio");
		} else {
			acqConfig.getSelectedHardwareParameter("Modo").setParameterValue("Estacionario");
		}

		acqConfig.setSelectedFrequency(new Frequency(jSliderTBS.getValue(), hardwareInfo.getHardwareFrequencies(0)
				.getMinimumFrequency().getMultiplier(), hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency()
				.getFrequencyDefType()));
		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabelInfo;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JRadioButton jRadioButtonEst;
	private javax.swing.JRadioButton jRadioButtonOsc;
	private javax.swing.JSlider jSliderAqc;
	private javax.swing.JSlider jSliderNumSamples;
	private javax.swing.JSlider jSliderTBS;
	private javax.swing.JSlider jSliderTMax;
	private javax.swing.JTextField jTextFieldAqc;
	private javax.swing.JTextField jTextFieldSamples;
	private javax.swing.JTextField jTextFieldTBS;
	private javax.swing.JTextField jTextFieldTMax;
	// End of variables declaration//GEN-END:variables

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addICustomizerListener(final ICustomizerListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(ICustomizerListener.class, listener);
	}

	/**
	 * Removes ICustomizerListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeICustomizerListener(final ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireICustomizerListenerCanceled() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {
				((ICustomizerListener) listeners[i + 1]).canceled();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireICustomizerListenerDone() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {

				((ICustomizerListener) listeners[i + 1]).done();
			}
		}
	}

	private HardwareInfo hardwareInfo = null;
	private HardwareAcquisitionConfig acqConfig = null;

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
	}

	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		this.acqConfig = acqConfig;
		if (acqConfig != null) {
			jSliderNumSamples.setValue(acqConfig.getTotalSamples());
			jTextFieldSamples.setText("" + acqConfig.getTotalSamples());

			final int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			jSliderTBS.setValue(freq);
			jTextFieldTBS.setText("" + freq);

			final int tAqc = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Tempo aquecimento"));
			jSliderAqc.setValue(tAqc);
			jTextFieldAqc.setText("" + tAqc);

			final int tMax = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Temperatura maxima"));
			jSliderTMax.setValue(tMax);
			jTextFieldTMax.setText("" + tMax);

			final String sMode = acqConfig.getSelectedHardwareParameterValue("Modo");
			if (sMode.equalsIgnoreCase("Oscilatorio")) {
				jRadioButtonOsc.setSelected(true);
			} else {
				jRadioButtonEst.setSelected(true);
			}
		}
	}

	@Override
	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	@Override
	public javax.swing.JComponent getCustomizerComponent() {
		return this;
	}

	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/conducaocalor/resources/t2_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("conducaocalor$rec.exp.t.customizer.title","conducaocalor$rec.exp.t.customizer.title");
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame jf = new javax.swing.JFrame();
		final TCustomizer tc = new TCustomizer();
		jf.getContentPane().add(tc, java.awt.BorderLayout.CENTER);
		jf.pack();
		jf.show();
	}
}
