/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */

package pt.utl.ist.elab.client.g;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/** Creates new form RadioactividadeCustomizer */
	public GCustomizer() {
		initComponents();
		checkMaxNumSamples();
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
		jPanel5 = new javax.swing.JPanel();
		sldPos1 = new javax.swing.JSlider();
		tfPos1 = new javax.swing.JTextField();
		jPanel1 = new javax.swing.JPanel();
		sldFreq = new javax.swing.JSlider();
		tfFreq = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		sldNumSamples = new javax.swing.JSlider();
		tfNumSamples = new javax.swing.JTextField();
		lblErrorSamplesTooHigh = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(350, 460));
		setPreferredSize(new java.awt.Dimension(350, 460));
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText(ReCResourceBundle.findString("ReCExpG$rec.exp.g.lbl.ok"));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanel2.add(btnOK, gridBagConstraints);

		btnCancel.setText(ReCResourceBundle.findString("ReCExpG$rec.exp.g.lbl.cancel"));
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

		btnDefaults.setText(ReCResourceBundle.findString("ReCExpG$rec.exp.g.lbl.dftcfg"));
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

		jPanel5.setBorder(new javax.swing.border.TitledBorder("Launching Power"));
		jPanel5.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel5.setPreferredSize(new java.awt.Dimension(350, 80));
		sldPos1.setMajorTickSpacing(10);
		sldPos1.setMinorTickSpacing(5);
		sldPos1.setPaintLabels(true);
		sldPos1.setPaintTicks(true);
		sldPos1.setSnapToTicks(true);
		sldPos1.setMinimumSize(new java.awt.Dimension(250, 42));
		sldPos1.setPreferredSize(new java.awt.Dimension(250, 42));
		sldPos1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sldPos1StateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weighty = 10.0;
		jPanel5.add(sldPos1, gridBagConstraints);

		tfPos1.setColumns(3);
		tfPos1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		tfPos1.setText("50");
		tfPos1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				tfPos1FocusLost(evt);
			}
		});

		jPanel5.add(tfPos1, new java.awt.GridBagConstraints());

		jPanel3.add(jPanel5);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1
				.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
						.findString("ReCExpG$rec.exp.g.lbl.tbs")));
		jPanel1.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel1.setPreferredSize(new java.awt.Dimension(350, 80));
		sldFreq.setMajorTickSpacing(40);
		sldFreq.setMaximum(220);
		sldFreq.setMinimum(20);
		sldFreq.setMinorTickSpacing(20);
		sldFreq.setPaintLabels(true);
		sldFreq.setPaintTicks(true);
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

		jPanel6.setLayout(new java.awt.GridBagLayout());

		jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findString("ReCExpG$rec.exp.g.lbl.nsamples")));
		jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
		jPanel6.setMinimumSize(new java.awt.Dimension(350, 80));
		jPanel6.setPreferredSize(new java.awt.Dimension(350, 80));
		sldNumSamples.setMajorTickSpacing(98);
		sldNumSamples.setMaximum(500);
		sldNumSamples.setMinimum(10);
		sldNumSamples.setMinorTickSpacing(49);
		sldNumSamples.setPaintLabels(true);
		sldNumSamples.setPaintTicks(true);
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
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpG$rec.exp.g.lbl.maxsamples"));
		lblErrorSamplesTooHigh.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel6.add(lblErrorSamplesTooHigh, gridBagConstraints);

		jPanel3.add(jPanel6);

		add(jPanel3, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

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

	private void tfPos1FocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfPos1FocusLost
	{// GEN-HEADEREND:event_tfPos1FocusLost
		String strPos1 = tfPos1.getText();

		if (strPos1.trim().equals(""))
			return;
		try {
			int Pos1 = Integer.parseInt(strPos1);
			if (Pos1 <= sldPos1.getMaximum() && Pos1 > sldPos1.getMinimum())
				sldPos1.setValue(Pos1);
			else
				tfPos1.setText("" + sldPos1.getValue());

		} catch (Exception e) {
			tfPos1.setText("" + sldPos1.getValue());
		}
	}// GEN-LAST:event_tfPos1FocusLost

	private void sldPos1StateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldPos1StateChanged
	{// GEN-HEADEREND:event_sldPos1StateChanged
		tfPos1.setText("" + sldPos1.getValue());
	}// GEN-LAST:event_sldPos1StateChanged

	private void sldFreqStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldFreqStateChanged
	{// GEN-HEADEREND:event_sldFreqStateChanged

		tfFreq.setText("" + sldFreq.getValue());

		checkMaxNumSamples();
	}// GEN-LAST:event_sldFreqStateChanged

	private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldNumSamplesStateChanged
	{// GEN-HEADEREND:event_sldNumSamplesStateChanged

		if (sldNumSamples.getValue() == 0) {
			sldNumSamples.setValue(1);

		}
		tfNumSamples.setText("" + sldNumSamples.getValue());

		checkMaxNumSamples();
	}// GEN-LAST:event_sldNumSamplesStateChanged

	public void checkMaxNumSamples() {
		float maxValue = Math.min(500.F, 500.F - ((float) sldFreq.getValue() - 1.F) * 480.F / 249.F);
		lblErrorSamplesTooHigh.setEnabled(sldNumSamples.getValue() > maxValue);
		btnOK.setEnabled(!lblErrorSamplesTooHigh.isEnabled());
		lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpG$rec.exp.g.lbl.maxsamples")
				+ (int) maxValue);
	}

	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		sldNumSamples.setValue(70);
		tfNumSamples.setText("70");
		sldPos1.setValue(60);
		tfPos1.setText("60");
		sldFreq.setValue(30);
		tfFreq.setText("" + 30);
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		acqConfig.setTotalSamples(sldNumSamples.getValue() == 0 ? 1 : sldNumSamples.getValue());
		acqConfig.getSelectedHardwareParameter("power").setParameterValue("" + sldPos1.getValue());
		acqConfig.setSelectedFrequency(new Frequency((double) sldFreq.getValue(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
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
	private javax.swing.JSlider sldFreq;
	private javax.swing.JSlider sldNumSamples;
	private javax.swing.JSlider sldPos1;
	private javax.swing.JTextField tfFreq;
	private javax.swing.JTextField tfNumSamples;
	private javax.swing.JTextField tfPos1;
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

			int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			sldFreq.setValue(freq);
			tfFreq.setText("" + freq);

			int power = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("power"));
			sldPos1.setValue(power);
			tfPos1.setText("" + power);

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
		return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/g/resources/g_iconified.gif"));
	}

	public String getCustomizerTitle() {
		return ReCResourceBundle.findString("ReCExpG$rec.exp.g.customizer.title");
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

}