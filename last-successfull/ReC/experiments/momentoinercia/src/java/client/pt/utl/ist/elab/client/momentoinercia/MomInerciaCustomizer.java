/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */

package pt.utl.ist.elab.client.momentoinercia;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André
 */

public class MomInerciaCustomizer  extends AbstractCustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -106003589418199806L;
	private final DecimalFormat df = new DecimalFormat();

	/** Creates new form RadioactividadeCustomizer */
	public MomInerciaCustomizer() {
		initComponents();
		final Hashtable<Integer, JLabel> slidersLabels = new Hashtable<Integer, JLabel>(10);
		slidersLabels.put(new Integer(0), new JLabel("0.0"));
		slidersLabels.put(new Integer(10000), new JLabel("10.0"));
		slidersLabels.put(new Integer(20000), new JLabel("20.0"));
		slidersLabels.put(new Integer(30000), new JLabel("30.0"));
		slidersLabels.put(new Integer(40000), new JLabel("40.0"));
		slidersLabels.put(new Integer(50000), new JLabel("50.0"));
		slidersLabels.put(new Integer(60000), new JLabel("60.0"));
		slidersLabels.put(new Integer(70000), new JLabel("70.0"));
		slidersLabels.put(new Integer(80000), new JLabel("80.0"));
		slidersLabels.put(new Integer(90000), new JLabel("90.0"));

		jSliderLaunch.setLabelTable(slidersLabels);
		jSliderStop.setLabelTable(slidersLabels);

		df.setMaximumFractionDigits(1);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel7 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jPanel8 = new javax.swing.JPanel();
		jSliderLaunch = new javax.swing.JSlider();
		jTextFieldLaunch = new javax.swing.JTextField();
		jCheckBoxLaunch = new javax.swing.JCheckBox();
		jPanel5 = new javax.swing.JPanel();
		jSliderStop = new javax.swing.JSlider();
		jTextFieldStop = new javax.swing.JTextField();
		jCheckStop = new javax.swing.JCheckBox();
		jPanel1 = new javax.swing.JPanel();
		jSliderTBS = new javax.swing.JSlider();
		jTextFieldTBS = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		jSliderNumSamples = new javax.swing.JSlider();
		jTextFieldSamples = new javax.swing.JTextField();
		jLabelInfo = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btnDefaults = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(460, 460));
		setPreferredSize(new java.awt.Dimension(500, 500));
		jPanel7.setLayout(new java.awt.BorderLayout());

		jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

		jPanel3.setMinimumSize(new java.awt.Dimension(350, 160));
		jPanel8.setLayout(new java.awt.GridBagLayout());

		jPanel8.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.launch","momentoinercia$rec.exp.discos.lbl.launch")));
		jPanel8.setMinimumSize(new java.awt.Dimension(250, 80));
		jPanel8.setPreferredSize(new java.awt.Dimension(250, 80));
		jSliderLaunch.setMajorTickSpacing(10000);
		jSliderLaunch.setMaximum(90000);
		jSliderLaunch.setPaintLabels(true);
		jSliderLaunch.setPaintTicks(true);
		jSliderLaunch.setValue(2500);
		jSliderLaunch.setMinimumSize(new java.awt.Dimension(380, 200));
		jSliderLaunch.setPreferredSize(new java.awt.Dimension(380, 200));
		jSliderLaunch.setOpaque(false);
		jSliderLaunch.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderLaunchStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel8.add(jSliderLaunch, gridBagConstraints);

		jTextFieldLaunch.setColumns(3);
		jTextFieldLaunch.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldLaunch.setText("2.5");
		jTextFieldLaunch.setMinimumSize(new java.awt.Dimension(33, 16));
		jTextFieldLaunch.setPreferredSize(new java.awt.Dimension(37, 16));
		jTextFieldLaunch.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldLaunchFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		jPanel8.add(jTextFieldLaunch, gridBagConstraints);

		jCheckBoxLaunch.setSelected(true);
		jCheckBoxLaunch.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(final java.awt.event.ItemEvent evt) {
				jCheckBoxLaunchItemStateChanged(evt);
			}
		});

		jPanel8.add(jCheckBoxLaunch, new java.awt.GridBagConstraints());

		jPanel3.add(jPanel8);

		jPanel5.setLayout(new java.awt.GridBagLayout());

		jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.break","momentoinercia$rec.exp.discos.lbl.break")));
		jPanel5.setMinimumSize(new java.awt.Dimension(250, 80));
		jPanel5.setPreferredSize(new java.awt.Dimension(250, 80));
		jSliderStop.setMajorTickSpacing(10000);
		jSliderStop.setMaximum(90000);
		jSliderStop.setPaintLabels(true);
		jSliderStop.setPaintTicks(true);
		jSliderStop.setValue(5000);
		jSliderStop.setMinimumSize(new java.awt.Dimension(380, 200));
		jSliderStop.setPreferredSize(new java.awt.Dimension(380, 200));
		jSliderStop.setOpaque(false);
		jSliderStop.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderStopStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(jSliderStop, gridBagConstraints);

		jTextFieldStop.setColumns(3);
		jTextFieldStop.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldStop.setText("5");
		jTextFieldStop.setMinimumSize(new java.awt.Dimension(33, 16));
		jTextFieldStop.setPreferredSize(new java.awt.Dimension(37, 16));
		jTextFieldStop.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldStopFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel5.add(jTextFieldStop, gridBagConstraints);

		jCheckStop.setSelected(true);
		jCheckStop.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(final java.awt.event.ItemEvent evt) {
				jCheckStopItemStateChanged(evt);
			}
		});

		jPanel5.add(jCheckStop, new java.awt.GridBagConstraints());

		jPanel3.add(jPanel5);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.tbs","momentoinercia$rec.exp.discos.lbl.tbs")));
		jPanel1.setMinimumSize(new java.awt.Dimension(250, 80));
		jPanel1.setPreferredSize(new java.awt.Dimension(250, 80));
		jSliderTBS.setMajorTickSpacing(200);
		jSliderTBS.setMaximum(1000);
		jSliderTBS.setMinimum(100);
		jSliderTBS.setPaintLabels(true);
		jSliderTBS.setPaintTicks(true);
		jSliderTBS.setMaximumSize(new java.awt.Dimension(1000, 32767));
		jSliderTBS.setMinimumSize(new java.awt.Dimension(400, 200));
		jSliderTBS.setPreferredSize(new java.awt.Dimension(400, 200));
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

		jTextFieldTBS.setColumns(4);
		jTextFieldTBS.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldTBS.setText("100");
		jTextFieldTBS.setMaximumSize(new java.awt.Dimension(35, 16));
		jTextFieldTBS.setMinimumSize(new java.awt.Dimension(35, 16));
		jTextFieldTBS.setPreferredSize(new java.awt.Dimension(48, 16));
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
				.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.nsamples","momentoinercia$rec.exp.discos.lbl.nsamples")));
		jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
		jPanel6.setMinimumSize(new java.awt.Dimension(250, 80));
		jPanel6.setPreferredSize(new java.awt.Dimension(250, 80));
		jSliderNumSamples.setMajorTickSpacing(100);
		jSliderNumSamples.setMaximum(900);
		jSliderNumSamples.setPaintLabels(true);
		jSliderNumSamples.setPaintTicks(true);
		jSliderNumSamples.setValue(100);
		jSliderNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
		jSliderNumSamples.setMinimumSize(new java.awt.Dimension(400, 200));
		jSliderNumSamples.setPreferredSize(new java.awt.Dimension(400, 200));
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
		jTextFieldSamples.setText("100");
		jTextFieldSamples.setMaximumSize(new java.awt.Dimension(48, 16));
		jTextFieldSamples.setMinimumSize(new java.awt.Dimension(35, 16));
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
		jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.expdur","momentoinercia$rec.exp.discos.lbl.expdur"));
		jLabelInfo.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel6.add(jLabelInfo, gridBagConstraints);

		jPanel3.add(jPanel6);

		jPanel7.add(jPanel3, java.awt.BorderLayout.CENTER);

		add(jPanel7, java.awt.BorderLayout.CENTER);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.ok","momentoinercia$rec.exp.discos.lbl.ok"));
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

		btnCancel.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.cancel","momentoinercia$rec.exp.discos.lbl.cancel"));
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

		btnDefaults.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.dftcfg.momentoInercia.title.1","momentoinercia$rec.exp.dftcfg.momentoInercia.title.1"));
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

		add(jPanel2, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	private void jTextFieldLaunchFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldLaunchFocusLost
		final String strLaunch = jTextFieldLaunch.getText();

		if (strLaunch.trim().equals("")) {
			return;
		}
		try {
			final float Launch = Float.parseFloat(strLaunch) * 1000;
			if (Launch <= jSliderLaunch.getMaximum() && Launch >= jSliderLaunch.getMinimum()) {
				jSliderLaunch.setValue((int) Launch);
			} else {
				jTextFieldLaunch.setText(df.format(jSliderLaunch.getValue() / 1000f));
			}

		} catch (final Exception e) {
			jTextFieldLaunch.setText(df.format(jSliderLaunch.getValue() / 1000f));
		}
	}// GEN-LAST:event_jTextFieldLaunchFocusLost

	private void jSliderLaunchStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderLaunchStateChanged
		jTextFieldLaunch.setText(df.format(jSliderLaunch.getValue() / 1000f));
		checkIterLaunch();
	}// GEN-LAST:event_jSliderLaunchStateChanged

	private void jCheckBoxLaunchItemStateChanged(final java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jCheckBoxLaunchItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			jSliderLaunch.setEnabled(true);
			jTextFieldLaunch.setEditable(true);
		} else {
			jSliderLaunch.setEnabled(false);
			jTextFieldLaunch.setEditable(false);
		}
		checkIterLaunch();
	}// GEN-LAST:event_jCheckBoxLaunchItemStateChanged

	private void jCheckStopItemStateChanged(final java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jCheckStopItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			jSliderStop.setEnabled(true);
			jTextFieldStop.setEditable(true);
		} else {
			jSliderStop.setEnabled(false);
			jTextFieldStop.setEditable(false);
		}
		checkIterStop();
	}// GEN-LAST:event_jCheckStopItemStateChanged

	private void jTextFieldStopFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStopFocusLost
		final String strStop = jTextFieldStop.getText();

		if (strStop.trim().equals("")) {
			return;
		}
		try {
			final float Stop = Float.parseFloat(strStop) * 1000;
			if (Stop <= jSliderStop.getMaximum() && Stop >= jSliderStop.getMinimum()) {
				jSliderStop.setValue((int) Stop);
			} else {
				jTextFieldStop.setText(df.format(jSliderStop.getValue() / 1000f));
			}

		} catch (final Exception e) {
			jTextFieldStop.setText(df.format(jSliderStop.getValue() / 1000f));
		}
	}// GEN-LAST:event_jTextFieldStopFocusLost

	private void jTextFieldSamplesFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSamplesFocusLost
		final String strNumSamples = jTextFieldSamples.getText();
		if (strNumSamples.trim().equals("")) {
			return;
		}
		try {
			final int numSamples = Integer.parseInt(strNumSamples);
			if (numSamples <= jSliderNumSamples.getMaximum() && numSamples >= jSliderNumSamples.getMinimum()) {
				jSliderNumSamples.setValue(numSamples);
			} else {
				jTextFieldSamples.setText("" + jSliderNumSamples.getValue());
			}
		} catch (final Exception e) {
			jTextFieldSamples.setText("" + jSliderNumSamples.getValue());
		}
	}// GEN-LAST:event_jTextFieldSamplesFocusLost

	private void jTextFieldTBSFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldTBSFocusLost
		final String strFreq = jTextFieldTBS.getText();
		if (strFreq.trim().equals("")) {
			return;
		}
		try {
			final int Freq = Integer.parseInt(strFreq);
			if (Freq <= jSliderTBS.getMaximum() && Freq >= jSliderTBS.getMinimum()) {
				jSliderTBS.setValue(Freq);
			} else {
				jTextFieldTBS.setText("" + jSliderTBS.getValue());
			}
		} catch (final Exception e) {
			jTextFieldTBS.setText("" + jSliderTBS.getValue());
		}
	}// GEN-LAST:event_jTextFieldTBSFocusLost

	private void jSliderStopStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStopStateChanged
		jTextFieldStop.setText(df.format(jSliderStop.getValue() / 1000f));
		checkIterStop();
	}// GEN-LAST:event_jSliderStopStateChanged

	private void jSliderTBSStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderTBSStateChanged
		if (jSliderTBS.getValue() == 0) {
			jSliderTBS.setValue(1);
		}
		jTextFieldTBS.setText("" + jSliderTBS.getValue());
		checkMaxNumSamples();
		checkIterLaunch();
		checkIterStop();
	}// GEN-LAST:event_jSliderTBSStateChanged

	private void jSliderNumSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderNumSamplesStateChanged

		if (jSliderNumSamples.getValue() == 0) {
			jSliderNumSamples.setValue(1);

		}
		jTextFieldSamples.setText("" + jSliderNumSamples.getValue());

		checkMaxNumSamples();
		if (checkIterLaunch()) {
			checkIterStop();
		}
	}// GEN-LAST:event_jSliderNumSamplesStateChanged

	public void checkMaxNumSamples() {
		final float duration = jSliderTBS.getValue() / 1000f * jSliderNumSamples.getValue();

		if (duration > 90) {
			jLabelInfo.setEnabled(true);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.maxsamples","momentoinercia$rec.exp.discos.lbl.maxsamples")
					+ (90000 / jSliderTBS.getValue()));
			btnOK.setEnabled(false);
		} else {
			jLabelInfo.setEnabled(false);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.expdur","momentoinercia$rec.exp.discos.lbl.expdur")
					+ df.format(duration) + " s");
			btnOK.setEnabled(true);
		}
	}

	public boolean checkIterLaunch() {
		boolean ret = false;
		final int launch = jSliderLaunch.getValue() / jSliderTBS.getValue();
		if (launch > jSliderNumSamples.getValue() && jCheckBoxLaunch.isSelected()) {
			jLabelInfo.setEnabled(true);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.lbl.discos.invlaunch","momentoinercia$rec.exp.lbl.discos.invlaunch"));
			btnOK.setEnabled(false);
			ret = false;
		} else {
			checkMaxNumSamples();
			ret = true;
		}
		return ret;
	}

	public boolean checkIterStop() {
		boolean ret = false;
		final int stop = jSliderStop.getValue() / jSliderTBS.getValue();
		if (stop > jSliderNumSamples.getValue() && jCheckStop.isSelected()) {
			jLabelInfo.setEnabled(true);
			jLabelInfo.setText(ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.discos.lbl.invstop","momentoinercia$rec.exp.discos.lbl.invstop"));
			btnOK.setEnabled(false);
			ret = false;
		} else {
			checkMaxNumSamples();
			ret = true;
		}
		return ret;
	}

	private void btnDefaultsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		jSliderNumSamples.setValue(100);
		jTextFieldSamples.setText("100");
		jSliderStop.setValue(5000);
		jTextFieldStop.setText("5");
		jSliderTBS.setValue(100);
		jTextFieldTBS.setText("100");
		jSliderLaunch.setValue(2500);
		jTextFieldLaunch.setText("2.5");
		jCheckBoxLaunch.setSelected(true);
		jCheckStop.setSelected(true);
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		getAcquisitionConfig().setTotalSamples(jSliderNumSamples.getValue());

		final int launch = jSliderLaunch.getValue() / jSliderTBS.getValue();

		final int stop = jSliderStop.getValue() / jSliderTBS.getValue();

		getAcquisitionConfig().getSelectedHardwareParameter("Launch Iteration").setParameterValue("" + launch);
		getAcquisitionConfig().getSelectedHardwareParameter("Stop Iteration").setParameterValue("" + stop);

		if (jCheckBoxLaunch.isSelected() && jCheckStop.isSelected()) {
			getAcquisitionConfig().getSelectedHardwareParameter("Iteration").setParameterValue("Both");
		} else if (jCheckBoxLaunch.isSelected()) {
			getAcquisitionConfig().getSelectedHardwareParameter("Iteration").setParameterValue("Launch");
		} else if (jCheckStop.isSelected()) {
			getAcquisitionConfig().getSelectedHardwareParameter("Iteration").setParameterValue("Stop");
		} else {
			getAcquisitionConfig().getSelectedHardwareParameter("Iteration").setParameterValue("None");
		}
		getAcquisitionConfig().setSelectedFrequency(new Frequency(jSliderTBS.getValue(), getHardwareInfo().getHardwareFrequencies(0)
				.getMinimumFrequency().getMultiplier(), getHardwareInfo().getHardwareFrequencies(0).getMinimumFrequency()
				.getFrequencyDefType()));
		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.JCheckBox jCheckBoxLaunch;
	private javax.swing.JCheckBox jCheckStop;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelInfo;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JSlider jSliderLaunch;
	private javax.swing.JSlider jSliderNumSamples;
	private javax.swing.JSlider jSliderStop;
	private javax.swing.JSlider jSliderTBS;
	private javax.swing.JTextField jTextFieldLaunch;
	private javax.swing.JTextField jTextFieldSamples;
	private javax.swing.JTextField jTextFieldStop;
	private javax.swing.JTextField jTextFieldTBS;
	// End of variables declaration//GEN-END:variables

	
	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			jSliderNumSamples.setValue(acqConfig.getTotalSamples());
			jTextFieldSamples.setText("" + acqConfig.getTotalSamples());

			final int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			jSliderTBS.setValue(freq);
			jTextFieldTBS.setText("" + freq);

			final int tLaunch = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Launch Iteration"));
			jSliderLaunch.setValue(tLaunch * freq);
			jTextFieldLaunch.setText(df.format(tLaunch * freq / 1000f));

			final int tStop = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Stop Iteration"));
			jSliderStop.setValue((tStop * freq));
			jTextFieldStop.setText(df.format(tStop * freq / 1000f));

			final String iteration = acqConfig.getSelectedHardwareParameterValue("Iteration");

			System.out.println("Iteration = " + iteration);

			if (iteration.equalsIgnoreCase("Both")) {
				jCheckBoxLaunch.setSelected(true);
				jCheckStop.setSelected(true);
			} else if (iteration.equalsIgnoreCase("Launch")) {
				jCheckBoxLaunch.setSelected(true);
				jCheckStop.setSelected(false);
			} else if (iteration.equalsIgnoreCase("Stop")) {
				jCheckBoxLaunch.setSelected(false);
				jCheckStop.setSelected(true);
			} else {
				jCheckBoxLaunch.setSelected(false);
				jCheckStop.setSelected(false);
			}
		}
	}

	
	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/momentoinercia/resources/cd_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("momentoinercia$rec.exp.customizer.title.discos","momentoinercia$rec.exp.customizer.title.discos");
	}


	public static void main(final String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				final javax.swing.JFrame jf = new javax.swing.JFrame();
				final MomInerciaCustomizer mom = new MomInerciaCustomizer();
				jf.getContentPane().add(mom, java.awt.BorderLayout.CENTER);
				jf.pack();
				jf.setVisible(true);
			}
		});
		
	}
}