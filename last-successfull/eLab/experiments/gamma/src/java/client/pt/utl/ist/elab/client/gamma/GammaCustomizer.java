/*
 * GammaCustomizer.java
 *
 * Created on September 21, 2004, 3:16 PM
 */

package pt.utl.ist.elab.client.gamma;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class GammaCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 508731691742556600L;

	/** Creates new form GammaCustomizer */
	public GammaCustomizer() {
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

		jPanel2 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btnDefaults = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jPanel9 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jSliderVolume = new javax.swing.JSlider();
		jPanel4 = new javax.swing.JPanel();
		jTextFieldVolume = new javax.swing.JTextField();
		jPanel5 = new javax.swing.JPanel();
		jSliderSTime = new javax.swing.JSlider();
		jPanel6 = new javax.swing.JPanel();
		jTextFieldSTime = new javax.swing.JTextField();
		jPanel7 = new javax.swing.JPanel();
		jSliderNPoints = new javax.swing.JSlider();
		jPanel8 = new javax.swing.JPanel();
		jTextFieldNPoints = new javax.swing.JTextField();
		jPanel10 = new javax.swing.JPanel();

		setLayout(new java.awt.BorderLayout());

		setPreferredSize(new java.awt.Dimension(500, 263));
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		btnOK.setText(ReCResourceBundle.findStringOrDefault("gamma$rec.exp.lbl.gamma.ok","gamma$rec.exp.lbl.gamma.ok"));
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

		btnCancel.setText(ReCResourceBundle.findStringOrDefault("gamma$rec.exp.lbl.gamma.cancel","gamma$rec.exp.lbl.gamma.cancel"));
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

		btnDefaults.setText(ReCResourceBundle.findStringOrDefault("gamma$rec.exp.lbl.gamma.dftcfg","gamma$rec.exp.lbl.gamma.dftcfg"));
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

		jPanel1.setLayout(new java.awt.BorderLayout());

		jPanel9.setLayout(new java.awt.GridLayout(3, 0));

		jPanel3.setLayout(new java.awt.BorderLayout());

		jPanel3.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("gamma$rec.exp.lbl.gamma.Volume","gamma$rec.exp.lbl.gamma.Volume")));
		jPanel3.setPreferredSize(new java.awt.Dimension(400, 66));
		jSliderVolume.setMajorTickSpacing(1);
		jSliderVolume.setMaximum(20);
		jSliderVolume.setMinimum(5);
		jSliderVolume.setMinorTickSpacing(1);
		jSliderVolume.setPaintLabels(true);
		jSliderVolume.setPaintTicks(true);
		jSliderVolume.setSnapToTicks(true);
		jSliderVolume.setValue(13);
		jSliderVolume.setMinimumSize(new java.awt.Dimension(300, 41));
		jSliderVolume.setPreferredSize(new java.awt.Dimension(300, 41));
		jSliderVolume.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderVolumeStateChanged(evt);
			}
		});

		jPanel3.add(jSliderVolume, java.awt.BorderLayout.CENTER);

		jTextFieldVolume.setColumns(4);
		jTextFieldVolume.setText("13");
		jTextFieldVolume.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldVolumeFocusLost(evt);
			}
		});

		jPanel4.add(jTextFieldVolume);

		jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

		jPanel9.add(jPanel3);

		jPanel5.setLayout(new java.awt.BorderLayout());

		jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("gamma$rec.exp.lbl.gamma.STime","gamma$rec.exp.lbl.gamma.STime")));
		jSliderSTime.setMajorTickSpacing(5);
		jSliderSTime.setMaximum(50);
		jSliderSTime.setMinimum(1);
		jSliderSTime.setPaintLabels(true);
		jSliderSTime.setPaintTicks(true);
		jSliderSTime.setSnapToTicks(true);
		jSliderSTime.setValue(10);
		jSliderSTime.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderSTimeStateChanged(evt);
			}
		});

		jPanel5.add(jSliderSTime, java.awt.BorderLayout.CENTER);

		jTextFieldSTime.setColumns(4);
		jTextFieldSTime.setText("10");
		jTextFieldSTime.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldSTimeFocusLost(evt);
			}
		});

		jPanel6.add(jTextFieldSTime);

		jPanel5.add(jPanel6, java.awt.BorderLayout.EAST);

		jPanel9.add(jPanel5);

		jPanel7.setLayout(new java.awt.BorderLayout());

		jPanel7.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle
				.findStringOrDefault("gamma$rec.exp.lbl.gamma.NPoints","gamma$rec.exp.lbl.gamma.NPoints")));
		jSliderNPoints.setMajorTickSpacing(50);
		jSliderNPoints.setMaximum(1000);
		jSliderNPoints.setMinimum(1);
		jSliderNPoints.setPaintLabels(true);
		jSliderNPoints.setPaintTicks(true);
		jSliderNPoints.setSnapToTicks(true);
		jSliderNPoints.setValue(500);
		jSliderNPoints.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderNPointsStateChanged(evt);
			}
		});

		jPanel7.add(jSliderNPoints, java.awt.BorderLayout.CENTER);

		jTextFieldNPoints.setColumns(4);
		jTextFieldNPoints.setText("500");
		jTextFieldNPoints.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldNPointsFocusLost(evt);
			}
		});

		jPanel8.add(jTextFieldNPoints);

		jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

		jPanel9.add(jPanel7);

		jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);

		jPanel10.setLayout(new java.awt.GridBagLayout());

		jPanel1.add(jPanel10, java.awt.BorderLayout.SOUTH);

		add(jPanel1, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	private void btnDefaultsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		jSliderSTime.setValue(1);
		jSliderNPoints.setValue(200);
		jSliderVolume.setValue(10);
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		acqConfig.getSelectedHardwareParameter("Volume").setParameterValue("" + jSliderVolume.getValue());

		acqConfig.setSelectedFrequency(new Frequency(jSliderSTime.getValue(), hardwareInfo.getHardwareFrequencies(0)
				.getMinimumFrequency().getMultiplier(), hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency()
				.getFrequencyDefType()));

		acqConfig.setTotalSamples(jSliderNPoints.getValue());

		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	private void jTextFieldNPointsFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldNPointsFocusLost
	{// GEN-HEADEREND:event_jTextFieldNPointsFocusLost
		adjustSlider(jSliderNPoints, jTextFieldNPoints);
	}// GEN-LAST:event_jTextFieldNPointsFocusLost

	private void jTextFieldSTimeFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldSTimeFocusLost
	{// GEN-HEADEREND:event_jTextFieldSTimeFocusLost
		adjustSlider(jSliderSTime, jTextFieldSTime);
	}// GEN-LAST:event_jTextFieldSTimeFocusLost

	private void jTextFieldVolumeFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldVolumeFocusLost
	{// GEN-HEADEREND:event_jTextFieldVolumeFocusLost
		adjustSlider(jSliderVolume, jTextFieldVolume);
	}// GEN-LAST:event_jTextFieldVolumeFocusLost

	private void jSliderNPointsStateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderNPointsStateChanged
	{// GEN-HEADEREND:event_jSliderNPointsStateChanged
		jTextFieldNPoints.setText("" + jSliderNPoints.getValue());
	}// GEN-LAST:event_jSliderNPointsStateChanged

	private void jSliderSTimeStateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderSTimeStateChanged
	{// GEN-HEADEREND:event_jSliderSTimeStateChanged
		jTextFieldSTime.setText("" + jSliderSTime.getValue());
	}// GEN-LAST:event_jSliderSTimeStateChanged

	private void jSliderVolumeStateChanged(final javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderVolumeStateChanged
	{// GEN-HEADEREND:event_jSliderVolumeStateChanged
		jTextFieldVolume.setText("" + jSliderVolume.getValue());
	}// GEN-LAST:event_jSliderVolumeStateChanged

	private void adjustSlider(final javax.swing.JSlider slider, final javax.swing.JTextField field) {
		int num = 0;
		try {
			num = Integer.parseInt(field.getText().trim());
		} catch (final NumberFormatException nfe) {
			field.setText("" + slider.getValue());
		}
		if (num > slider.getMaximum() || num < slider.getMinimum()) {
			field.setText("" + slider.getValue());
		} else {
			slider.setValue(num);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String args[]) {
		final javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new GammaCustomizer(), java.awt.BorderLayout.CENTER);
		dummy.pack();
		dummy.show();
	}

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
			final int vol = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Volume"));
			jSliderVolume.setValue(vol);
			final int STime = (int) acqConfig.getSelectedFrequency().getFrequency();
			jSliderSTime.setValue(STime);
			final int nsamples = acqConfig.getTotalSamples();
			jSliderNPoints.setValue(nsamples);
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
				"/pt/utl/ist/elab/client/gamma/resources/gamma_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return "Gamma Experiment Configuration Utility";
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JSlider jSliderSTime;
	private javax.swing.JSlider jSliderNPoints;
	private javax.swing.JSlider jSliderVolume;
	private javax.swing.JTextField jTextFieldSTime;
	private javax.swing.JTextField jTextFieldNPoints;
	private javax.swing.JTextField jTextFieldVolume;
	// End of variables declaration//GEN-END:variables

}
