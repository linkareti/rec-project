/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */

package pt.utl.ist.elab.client.pendulo;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PenduloCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/** Creates new form RadioactividadeCustomizer */
	public PenduloCustomizer() {
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

		buttonGroupStatus = new javax.swing.ButtonGroup();
		jPanel2 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btnDefaults = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		sldAngle = new javax.swing.JSlider();
		tfAngle = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		sldNumSamples = new javax.swing.JSlider();
		tfNumSamples = new javax.swing.JTextField();
		lblErrorSamplesTooHigh = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		sldFreq = new javax.swing.JSlider();
		tfFreq = new javax.swing.JTextField();
		jPanel51 = new javax.swing.JPanel();
		sldHeight = new javax.swing.JSlider();
		tfHeight = new javax.swing.JTextField();
		jPanel511 = new javax.swing.JPanel();
		radioStatus00 = new javax.swing.JRadioButton();
		radioStatus01 = new javax.swing.JRadioButton();
		radioStatus10 = new javax.swing.JRadioButton();
		radioStatus11 = new javax.swing.JRadioButton();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(420, 400));
		setPreferredSize(new java.awt.Dimension(420, 400));
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.ok"));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanel2.add(btnOK, gridBagConstraints);

		btnCancel.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.cancel"));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
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

		btnDefaults.setText(ReCResourceBundle.findString("pendulo$rec.exp.dftcfg.pendulo.title.1"));
		btnDefaults.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
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
		jPanel5.setLayout(new java.awt.GridBagLayout());

		jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("pendulo$rec.exp.pendulo.lbl.angini")));
		jPanel5.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel5.setPreferredSize(new java.awt.Dimension(350, 80));
		sldAngle.setMajorTickSpacing(5);
		sldAngle.setMaximum(10);
		sldAngle.setMinorTickSpacing(1);
		sldAngle.setPaintLabels(true);
		sldAngle.setPaintTicks(true);
		sldAngle.setPaintTrack(false);
		sldAngle.setSnapToTicks(true);
		sldAngle.setMinimumSize(new java.awt.Dimension(250, 42));
		sldAngle.setPreferredSize(new java.awt.Dimension(250, 42));
		sldAngle.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sldAngleStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(sldAngle, gridBagConstraints);

		tfAngle.setColumns(2);
		tfAngle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		tfAngle.setText("5");
		tfAngle.setMaximumSize(new java.awt.Dimension(30, 16));
		tfAngle.setMinimumSize(new java.awt.Dimension(30, 16));
		tfAngle.setPreferredSize(new java.awt.Dimension(26, 16));
		tfAngle.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				tfAngleFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel5.add(tfAngle, gridBagConstraints);

		jPanel3.add(jPanel5);

		jPanel6.setLayout(new java.awt.GridBagLayout());

		jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("pendulo$rec.exp.pendulo.lbl.nsamples")));
		jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
		jPanel6.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel6.setPreferredSize(new java.awt.Dimension(350, 80));
		sldNumSamples.setMajorTickSpacing(100);
		sldNumSamples.setMaximum(500);
		sldNumSamples.setMinorTickSpacing(50);
		sldNumSamples.setPaintLabels(true);
		sldNumSamples.setPaintTicks(true);
		sldNumSamples.setPaintTrack(false);
		sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
		sldNumSamples.setMinimumSize(new java.awt.Dimension(250, 42));
		sldNumSamples.setPreferredSize(new java.awt.Dimension(250, 42));
		sldNumSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
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
		tfNumSamples.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		tfNumSamples.setText("50");
		tfNumSamples.setMaximumSize(new java.awt.Dimension(30, 16));
		tfNumSamples.setMinimumSize(new java.awt.Dimension(30, 16));
		tfNumSamples.setPreferredSize(new java.awt.Dimension(37, 16));
		tfNumSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
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
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.maxsamples.1"));
		lblErrorSamplesTooHigh.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel6.add(lblErrorSamplesTooHigh, gridBagConstraints);

		jPanel3.add(jPanel6);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("pendulo$rec.exp.pendulo.lbl.freq")));
		jPanel1.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel1.setPreferredSize(new java.awt.Dimension(350, 80));
		sldFreq.setMajorTickSpacing(25);
		sldFreq.setMaximum(125);
		sldFreq.setMinimum(25);
		sldFreq.setMinorTickSpacing(5);
		sldFreq.setPaintLabels(true);
		sldFreq.setPaintTicks(true);
		sldFreq.setPaintTrack(false);
		sldFreq.setValue(20);
		sldFreq.setMaximumSize(new java.awt.Dimension(1000, 32767));
		sldFreq.setMinimumSize(new java.awt.Dimension(255, 80));
		sldFreq.setPreferredSize(new java.awt.Dimension(255, 80));
		sldFreq.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sldFreqStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		gridBagConstraints.weighty = 10.0;
		jPanel1.add(sldFreq, gridBagConstraints);

		tfFreq.setColumns(3);
		tfFreq.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		tfFreq.setText("50");
		tfFreq.setMaximumSize(new java.awt.Dimension(30, 16));
		tfFreq.setMinimumSize(new java.awt.Dimension(30, 16));
		tfFreq.setPreferredSize(new java.awt.Dimension(37, 16));
		tfFreq.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				tfFreqFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel1.add(tfFreq, gridBagConstraints);

		jPanel3.add(jPanel1);

		add(jPanel3, java.awt.BorderLayout.CENTER);

		jPanel51.setLayout(new java.awt.GridBagLayout());

		jPanel51.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("pendulo$rec.exp.pendulo.lbl.hini")));
		jPanel51.setMinimumSize(new java.awt.Dimension(100, 350));
		jPanel51.setPreferredSize(new java.awt.Dimension(100, 350));
		sldHeight.setMajorTickSpacing(10);
		sldHeight.setMaximum(90);
		sldHeight.setMinimum(50);
		sldHeight.setMinorTickSpacing(2);
		sldHeight.setOrientation(javax.swing.JSlider.VERTICAL);
		sldHeight.setPaintLabels(true);
		sldHeight.setPaintTicks(true);
		sldHeight.setPaintTrack(false);
		sldHeight.setValue(60);
		sldHeight.setMaximumSize(new java.awt.Dimension(1000, 32767));
		sldHeight.setMinimumSize(new java.awt.Dimension(50, 250));
		sldHeight.setPreferredSize(new java.awt.Dimension(50, 250));
		sldHeight.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sldHeightStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel51.add(sldHeight, gridBagConstraints);

		tfHeight.setColumns(3);
		tfHeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		tfHeight.setText("60");
		tfHeight.setMaximumSize(new java.awt.Dimension(30, 16));
		tfHeight.setMinimumSize(new java.awt.Dimension(30, 16));
		tfHeight.setPreferredSize(new java.awt.Dimension(37, 16));
		tfHeight.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				tfHeightFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel51.add(tfHeight, gridBagConstraints);

		add(jPanel51, java.awt.BorderLayout.WEST);

		jPanel511.setLayout(new java.awt.GridBagLayout());

		jPanel511.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("pendulo$rec.exp.pendulo.lbl.friction.tittle")));
		jPanel511.setMinimumSize(new java.awt.Dimension(150, 50));
		jPanel511.setPreferredSize(new java.awt.Dimension(150, 50));
		radioStatus00.setSelected(true);
		radioStatus00.setText("no friction");
		buttonGroupStatus.add(radioStatus00);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel511.add(radioStatus00, gridBagConstraints);

		radioStatus01.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.friction.25"));
		buttonGroupStatus.add(radioStatus01);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel511.add(radioStatus01, gridBagConstraints);

		radioStatus10.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.friction.17"));
		buttonGroupStatus.add(radioStatus10);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel511.add(radioStatus10, gridBagConstraints);

		radioStatus11.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.friction.12"));
		buttonGroupStatus.add(radioStatus11);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel511.add(radioStatus11, gridBagConstraints);

		add(jPanel511, java.awt.BorderLayout.NORTH);

	}// GEN-END:initComponents

	private void tfAngleFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfAngleFocusLost
	{// GEN-HEADEREND:event_tfAngleFocusLost
		String strAngle = tfAngle.getText();
		if (strAngle.trim().equals(""))
			return;
		try {
			int angle = Integer.parseInt(strAngle);
			if (angle <= sldAngle.getMaximum() && angle > sldAngle.getMinimum())
				sldAngle.setValue(angle);
			else
				tfAngle.setText("" + sldAngle.getValue());
		} catch (Exception e) {
			tfAngle.setText("" + sldAngle.getValue());
		}
	}// GEN-LAST:event_tfAngleFocusLost

	private void tfNumSamplesFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfNumSamplesFocusLost
	{// GEN-HEADEREND:event_tfNumSamplesFocusLost
		String strNumSamples = tfNumSamples.getText();
		if (strNumSamples.trim().equals(""))
			return;
		try {
			int numSamples = Integer.parseInt(strNumSamples);
			if (numSamples <= sldNumSamples.getMaximum() && numSamples > sldNumSamples.getMinimum())
				sldNumSamples.setValue(numSamples);
			else
				tfNumSamples.setText("" + sldNumSamples.getValue());
		} catch (Exception e) {
			tfNumSamples.setText("" + sldNumSamples.getValue());
		}
		checkMaxNumSamples();

	}// GEN-LAST:event_tfNumSamplesFocusLost

	private void tfFreqFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfFreqFocusLost
	{// GEN-HEADEREND:event_tfFreqFocusLost
		String strFreq = tfFreq.getText();
		if (strFreq.trim().equals(""))
			return;
		try {
			int Freq = Integer.parseInt(strFreq);
			if (Freq <= sldFreq.getMaximum() && Freq > sldFreq.getMinimum())
				sldFreq.setValue(Freq);
			else
				tfFreq.setText("" + sldFreq.getValue());
		} catch (Exception e) {
			tfFreq.setText("" + sldFreq.getValue());
		}
		checkMaxNumSamples();
	}// GEN-LAST:event_tfFreqFocusLost

	private void tfHeightFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfHeightFocusLost
	{// GEN-HEADEREND:event_tfHeightFocusLost
		String strHeight = tfHeight.getText();
		if (strHeight.trim().equals(""))
			return;
		try {
			int height = Integer.parseInt(strHeight);
			if (height <= sldHeight.getMaximum() && height > sldHeight.getMinimum())
				sldHeight.setValue(height);
			else
				tfHeight.setText("" + sldHeight.getValue());
		} catch (Exception e) {
			tfHeight.setText("" + sldHeight.getValue());
		}
	}// GEN-LAST:event_tfHeightFocusLost

	private void sldHeightStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldHeightStateChanged
	{// GEN-HEADEREND:event_sldHeightStateChanged
		tfHeight.setText("" + sldHeight.getValue());
	}// GEN-LAST:event_sldHeightStateChanged

	private void sldAngleStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldAngleStateChanged
	{// GEN-HEADEREND:event_sldAngleStateChanged
		tfAngle.setText("" + sldAngle.getValue());
	}// GEN-LAST:event_sldAngleStateChanged

	private void sldFreqStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldFreqStateChanged
	{// GEN-HEADEREND:event_sldFreqStateChanged
		if (sldFreq.getValue() == 0) {
			sldFreq.setValue(1);

		}
		tfFreq.setText("" + sldFreq.getValue());

		checkMaxNumSamples();
	}// GEN-LAST:event_sldFreqStateChanged

	private void checkMaxNumSamples() {
		lblErrorSamplesTooHigh.setEnabled(sldNumSamples.getValue() > 4 * sldFreq.getValue());
		btnOK.setEnabled(!lblErrorSamplesTooHigh.isEnabled());
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("pendulo$rec.exp.pendulo.lbl.maxsamples.2")
				+ 4 * sldFreq.getValue());
	}

	private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldNumSamplesStateChanged
	{// GEN-HEADEREND:event_sldNumSamplesStateChanged

		if (sldNumSamples.getValue() == 0) {
			sldNumSamples.setValue(1);

		}
		tfNumSamples.setText("" + sldNumSamples.getValue());
		checkMaxNumSamples();

	}// GEN-LAST:event_sldNumSamplesStateChanged

	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		sldNumSamples.setValue(50);
		tfNumSamples.setText("50");
		sldAngle.setValue(5);
		tfAngle.setText("" + 5);
		sldFreq.setValue(50);
		tfFreq.setText("" + 50);
		radioStatus00.setSelected(true);
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed

		acqConfig.setTotalSamples(sldNumSamples.getValue() == 0 ? 1 : sldNumSamples.getValue());
		acqConfig.getSelectedHardwareParameter("Altura").setParameterValue("" + sldHeight.getValue());
		acqConfig.getSelectedHardwareParameter("Amplitude inicial").setParameterValue("" + sldAngle.getValue());
		String atrito = "";
		if (radioStatus00.isSelected())
			atrito = "Sem atrito";
		else if (radioStatus01.isSelected())
			atrito = "25 Ohm";
		else if (radioStatus10.isSelected())
			atrito = "17 Ohm";
		else if (radioStatus11.isSelected())
			atrito = "12 Ohm";
		acqConfig.getSelectedHardwareParameter("Atrito").setParameterValue("" + atrito);
		acqConfig.setSelectedFrequency(new Frequency((double) sldFreq.getValue(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier()));
		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	public static void main(String args[]) {
		ReCResourceBundle.loadResourceBundle("pendulo",
				"recresource:///pt/utl/ist/elab/client/pendulo/resources/messages");
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new PenduloCustomizer());
		dummy.pack();
		dummy.show();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.ButtonGroup buttonGroupStatus;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel51;
	private javax.swing.JPanel jPanel511;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JLabel lblErrorSamplesTooHigh;
	private javax.swing.JRadioButton radioStatus00;
	private javax.swing.JRadioButton radioStatus01;
	private javax.swing.JRadioButton radioStatus10;
	private javax.swing.JRadioButton radioStatus11;
	private javax.swing.JSlider sldAngle;
	private javax.swing.JSlider sldFreq;
	private javax.swing.JSlider sldHeight;
	private javax.swing.JSlider sldNumSamples;
	private javax.swing.JTextField tfAngle;
	private javax.swing.JTextField tfFreq;
	private javax.swing.JTextField tfHeight;
	private javax.swing.JTextField tfNumSamples;
	// End of variables declaration//GEN-END:variables

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addICustomizerListener(ICustomizerListener listener) {
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
	public synchronized void removeICustomizerListener(ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireICustomizerListenerCanceled() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {

				((ICustomizerListener) listeners[i + 1]).done();
			}
		}
	}

	private HardwareInfo hardwareInfo = null;
	private HardwareAcquisitionConfig acqConfig = null;

	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
	}

	public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
		this.acqConfig = acqConfig;
		if (acqConfig != null) {
			sldNumSamples.setValue(acqConfig.getTotalSamples());
			tfNumSamples.setText("" + acqConfig.getTotalSamples());
			int height = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Altura"));
			sldHeight.setValue(height);
			tfHeight.setText("" + height);
			int teta = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Amplitude inicial"));
			sldAngle.setValue(teta);
			tfAngle.setText("" + teta);

			String atrito = acqConfig.getSelectedHardwareParameterValue("Atrito");
			if (atrito.equals("Sem atrito"))
				radioStatus00.setSelected(true);
			else if (atrito.equals("25 Ohm"))
				radioStatus01.setSelected(true);
			else if (atrito.equals("17 Ohm"))
				radioStatus10.setSelected(true);
			if (atrito.equals("12 Ohm"))
				radioStatus11.setSelected(true);

			int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			sldFreq.setValue(freq);
			tfFreq.setText("" + freq);

		}
	}

	public void setHardwareInfo(HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo() {
		return this.hardwareInfo;
	}

	public javax.swing.JComponent getCustomizerComponent() {
		return this;
	}

	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/pendulo/resources/pendulo_iconified.gif"));
	}

	public String getCustomizerTitle() {
		return ReCResourceBundle.findString("pendulo$rec.exp.pendulo.customizer.title");
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

}
