/*
 * MMCustomizer.java
 *
 * Created on October 16, 2004, 11:32 AM
 */

package pt.utl.ist.elab.client.vmm;

/**
 *
 * @author André Neto - LEFT - IST
 *
 *  1- Desenhar o GUI! Não esquecer de ir logo internacionalizando...
 *
 *
 */

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class MMCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/** Creates new form MMCustomizer */
	public MMCustomizer() {
		initComponents();
		// Maneira classica de alterar os labels do slider
		java.util.Hashtable htK = new java.util.Hashtable(10);
		for (int i = 0; i < 21; i += 2) {
			htK.put(new Integer(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderK.setLabelTable(htK);

		java.util.Hashtable htM = new java.util.Hashtable(20);
		for (int i = 0; i < 51; i += 5) {
			htM.put(new Integer(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderM.setLabelTable(htM);

		java.util.Hashtable htFric = new java.util.Hashtable(10);
		for (int i = 0; i < 11; i++) {
			htFric.put(new Integer(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderFric.setLabelTable(htFric);

		java.util.Hashtable htX0 = new java.util.Hashtable(10);
		for (int i = -20; i < 21; i += 5) {
			htX0.put(new Integer(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderX0.setLabelTable(htX0);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		jPanelK = new javax.swing.JPanel();
		jSliderK = new javax.swing.JSlider();
		jTextFieldK = new javax.swing.JTextField();
		jPanelM = new javax.swing.JPanel();
		jSliderM = new javax.swing.JSlider();
		jTextFieldM = new javax.swing.JTextField();
		jPanelFric = new javax.swing.JPanel();
		jSliderFric = new javax.swing.JSlider();
		jTextFieldFric = new javax.swing.JTextField();
		jPanelX0 = new javax.swing.JPanel();
		jSliderX0 = new javax.swing.JSlider();
		jTextFieldX0 = new javax.swing.JTextField();
		jPanel6 = new javax.swing.JPanel();
		jPanelTBS = new javax.swing.JPanel();
		jSliderTBS = new javax.swing.JSlider();
		jTextFieldTBS = new javax.swing.JTextField();
		jPanelSamples = new javax.swing.JPanel();
		jSliderSamples = new javax.swing.JSlider();
		jTextFieldSamples = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		btnDefaults = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		setPreferredSize(new java.awt.Dimension(550, 350));
		jPanel1.setLayout(new java.awt.BorderLayout());

		jPanel5.setLayout(new java.awt.GridLayout(4, 0, 5, 0));

		jPanelK.setLayout(new java.awt.GridBagLayout());

		jPanelK.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.1", "Spring constant (N/m)")));
		jPanelK.setPreferredSize(new java.awt.Dimension(270, 65));
		jSliderK.setMajorTickSpacing(2);
		jSliderK.setMaximum(20);
		jSliderK.setMinorTickSpacing(2);
		jSliderK.setPaintLabels(true);
		jSliderK.setPaintTicks(true);
		jSliderK.setValue(10);
		jSliderK.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderKStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		jPanelK.add(jSliderK, gridBagConstraints);

		jTextFieldK.setColumns(4);
		jTextFieldK.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldK.setText("1.0");
		jTextFieldK.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldKFocusLost(evt);
			}
		});

		jPanelK.add(jTextFieldK, new java.awt.GridBagConstraints());

		jPanel5.add(jPanelK);

		jPanelM.setLayout(new java.awt.GridBagLayout());

		jPanelM.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.2", "Mass (Kg)")));
		jSliderM.setMajorTickSpacing(10);
		jSliderM.setMaximum(50);
		jSliderM.setMinorTickSpacing(5);
		jSliderM.setPaintLabels(true);
		jSliderM.setPaintTicks(true);
		jSliderM.setValue(10);
		jSliderM.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderMStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		jPanelM.add(jSliderM, gridBagConstraints);

		jTextFieldM.setColumns(4);
		jTextFieldM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldM.setText("1.0");
		jTextFieldM.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldMFocusLost(evt);
			}
		});

		jPanelM.add(jTextFieldM, new java.awt.GridBagConstraints());

		jPanel5.add(jPanelM);

		jPanelFric.setLayout(new java.awt.GridBagLayout());

		jPanelFric.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.3", "Friction constant (Kg/s)")));
		jSliderFric.setMajorTickSpacing(1);
		jSliderFric.setMaximum(10);
		jSliderFric.setMinorTickSpacing(1);
		jSliderFric.setPaintLabels(true);
		jSliderFric.setPaintTicks(true);
		jSliderFric.setValue(0);
		jSliderFric.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderFricStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		jPanelFric.add(jSliderFric, gridBagConstraints);

		jTextFieldFric.setColumns(4);
		jTextFieldFric.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldFric.setText("0.0");
		jTextFieldFric.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldFricFocusLost(evt);
			}
		});

		jPanelFric.add(jTextFieldFric, new java.awt.GridBagConstraints());

		jPanel5.add(jPanelFric);

		jPanelX0.setLayout(new java.awt.GridBagLayout());

		jPanelX0.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.4", "Initial position (m)")));
		jSliderX0.setMajorTickSpacing(10);
		jSliderX0.setMaximum(20);
		jSliderX0.setMinimum(-20);
		jSliderX0.setMinorTickSpacing(5);
		jSliderX0.setPaintLabels(true);
		jSliderX0.setPaintTicks(true);
		jSliderX0.setValue(10);
		jSliderX0.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderX0StateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		jPanelX0.add(jSliderX0, gridBagConstraints);

		jTextFieldX0.setColumns(4);
		jTextFieldX0.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldX0.setText("1.0");
		jTextFieldX0.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldX0FocusLost(evt);
			}
		});

		jPanelX0.add(jTextFieldX0, new java.awt.GridBagConstraints());

		jPanel5.add(jPanelX0);

		jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

		jPanel6.setLayout(new java.awt.GridBagLayout());

		jPanelTBS.setLayout(new java.awt.GridBagLayout());

		jPanelTBS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.tbs", "TBS")));
		jPanelTBS.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.tip.tbs",
				"Time between samples"));
		jPanelTBS.setPreferredSize(new java.awt.Dimension(82, 224));
		jSliderTBS.setMajorTickSpacing(100);
		jSliderTBS.setMaximum(500);
		jSliderTBS.setMinimum(50);
		jSliderTBS.setMinorTickSpacing(50);
		jSliderTBS.setOrientation(javax.swing.JSlider.VERTICAL);
		jSliderTBS.setPaintLabels(true);
		jSliderTBS.setPaintTicks(true);
		jSliderTBS.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.tip.tbs",
				"Time between samples"));
		jSliderTBS.setValue(100);
		jSliderTBS.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderTBSStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanelTBS.add(jSliderTBS, gridBagConstraints);

		jTextFieldTBS.setColumns(4);
		jTextFieldTBS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldTBS.setText("100");
		jTextFieldTBS.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldTBSFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanelTBS.add(jTextFieldTBS, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
		jPanel6.add(jPanelTBS, gridBagConstraints);

		jPanelSamples.setLayout(new java.awt.GridBagLayout());

		jPanelSamples.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpMM$rec.exp.customizer.title.samples", "N Samples")));
		jPanelSamples.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.tip.samples",
				"Number of samples"));
		jPanelSamples.setPreferredSize(new java.awt.Dimension(82, 224));
		jSliderSamples.setMajorTickSpacing(100);
		jSliderSamples.setMaximum(500);
		jSliderSamples.setMinimum(1);
		jSliderSamples.setMinorTickSpacing(50);
		jSliderSamples.setOrientation(javax.swing.JSlider.VERTICAL);
		jSliderSamples.setPaintLabels(true);
		jSliderSamples.setPaintTicks(true);
		jSliderSamples.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.tip.samples",
				"Number of samples"));
		jSliderSamples.setValue(100);
		jSliderSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderSamplesStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanelSamples.add(jSliderSamples, gridBagConstraints);

		jTextFieldSamples.setColumns(4);
		jTextFieldSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldSamples.setText("100");
		jTextFieldSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldSamplesFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanelSamples.add(jTextFieldSamples, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
		jPanel6.add(jPanelSamples, gridBagConstraints);

		jPanel1.add(jPanel6, java.awt.BorderLayout.EAST);

		add(jPanel1, java.awt.BorderLayout.CENTER);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
		jPanel3.setMinimumSize(new java.awt.Dimension(143, 25));
		btnOK.setText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.title.ok", "OK"));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		jPanel3.add(btnOK);

		btnCancel.setText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.title.cancel", "Cancel"));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		jPanel3.add(btnCancel);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		jPanel2.add(jPanel3, gridBagConstraints);

		jPanel4.setMinimumSize(new java.awt.Dimension(136, 25));
		btnDefaults.setText(ReCResourceBundle.findStringOrDefault("ReCExpMM$rec.exp.customizer.title.dfc",
				"Default config"));
		btnDefaults.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDefaultsActionPerformed(evt);
			}
		});

		jPanel4.add(btnDefaults);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel2.add(jPanel4, gridBagConstraints);

		add(jPanel2, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		// Colocar nas configurações predefinidas...tipo:
		jSliderSamples.setValue(100);
		// etc...
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		// Sempre igual
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		// OK o utilizador quer enviar as informacoes, vamos colocar os valores
		// nos canais!!!
		acqConfig.setTotalSamples(jSliderSamples.getValue());

		acqConfig.setSelectedFrequency(new Frequency((double) jSliderTBS.getValue(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));

		acqConfig.getSelectedHardwareParameter("kmola").setParameterValue("" + jSliderK.getValue() / 10F);
		acqConfig.getSelectedHardwareParameter("massa").setParameterValue("" + jSliderM.getValue() / 10F);
		acqConfig.getSelectedHardwareParameter("atrito").setParameterValue("" + jSliderFric.getValue() / 10F);
		acqConfig.getSelectedHardwareParameter("xini").setParameterValue("" + jSliderX0.getValue() / 10F);
		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	private void jTextFieldSamplesFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldSamplesFocusLost
	{// GEN-HEADEREND:event_jTextFieldSamplesFocusLost
		adjustSlider(jSliderSamples, jTextFieldSamples);
	}// GEN-LAST:event_jTextFieldSamplesFocusLost

	private void jSliderSamplesStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderSamplesStateChanged
	{// GEN-HEADEREND:event_jSliderSamplesStateChanged
		jTextFieldSamples.setText("" + jSliderSamples.getValue());
	}// GEN-LAST:event_jSliderSamplesStateChanged

	private void jTextFieldTBSFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldTBSFocusLost
	{// GEN-HEADEREND:event_jTextFieldTBSFocusLost
		adjustSlider(jSliderTBS, jTextFieldTBS);
	}// GEN-LAST:event_jTextFieldTBSFocusLost

	private void jSliderTBSStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderTBSStateChanged
	{// GEN-HEADEREND:event_jSliderTBSStateChanged
		jTextFieldTBS.setText("" + jSliderTBS.getValue());
	}// GEN-LAST:event_jSliderTBSStateChanged

	private void jTextFieldX0FocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldX0FocusLost
	{// GEN-HEADEREND:event_jTextFieldX0FocusLost
		adjustSlider2(jSliderX0, jTextFieldX0);
	}// GEN-LAST:event_jTextFieldX0FocusLost

	private void jSliderX0StateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderX0StateChanged
	{// GEN-HEADEREND:event_jSliderX0StateChanged
		jTextFieldX0.setText("" + jSliderX0.getValue() / 10F);
	}// GEN-LAST:event_jSliderX0StateChanged

	private void jTextFieldFricFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFricFocusLost
	{// GEN-HEADEREND:event_jTextFieldFricFocusLost
		adjustSlider2(jSliderFric, jTextFieldFric);
	}// GEN-LAST:event_jTextFieldFricFocusLost

	private void jSliderFricStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderFricStateChanged
	{// GEN-HEADEREND:event_jSliderFricStateChanged
		jTextFieldFric.setText("" + jSliderFric.getValue() / 10F);
	}// GEN-LAST:event_jSliderFricStateChanged

	private void jTextFieldMFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldMFocusLost
	{// GEN-HEADEREND:event_jTextFieldMFocusLost
		adjustSlider2(jSliderM, jTextFieldM);
	}// GEN-LAST:event_jTextFieldMFocusLost

	private void jSliderMStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderMStateChanged
	{// GEN-HEADEREND:event_jSliderMStateChanged
		jTextFieldM.setText("" + jSliderM.getValue() / 10F);
	}// GEN-LAST:event_jSliderMStateChanged

	private void jTextFieldKFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldKFocusLost
	{// GEN-HEADEREND:event_jTextFieldKFocusLost
		adjustSlider2(jSliderK, jTextFieldK);
	}// GEN-LAST:event_jTextFieldKFocusLost

	private void jSliderKStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_jSliderKStateChanged
	{// GEN-HEADEREND:event_jSliderKStateChanged
		jTextFieldK.setText("" + jSliderK.getValue() / 10F);
	}// GEN-LAST:event_jSliderKStateChanged

	public static void main(String args[]) {
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new MMCustomizer());
		dummy.pack();
		dummy.show();
	}

	// Metodo que verifica a validade do que foi introduzido na text field
	private void adjustSlider(javax.swing.JSlider slider, javax.swing.JTextField field) {
		int num = 0;
		try {
			num = Integer.parseInt(field.getText().trim());
		} catch (NumberFormatException nfe) {
			field.setText("" + slider.getValue());
		}
		if (num > slider.getMaximum() || num < slider.getMinimum())
			field.setText("" + slider.getValue());
		else
			slider.setValue(num);
	}

	private void adjustSlider2(javax.swing.JSlider slider, javax.swing.JTextField field) {
		int num = 0;
		try {
			num = (int) (Float.parseFloat(field.getText().trim()) * 10);
		} catch (NumberFormatException nfe) {
			field.setText("" + slider.getValue() / 10F);
			return;
		}
		if (num > slider.getMaximum() || num < slider.getMinimum())
			field.setText("" + slider.getValue() / 10F);
		else
			slider.setValue(num);
	}

	// ****************************REC********************************************/

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

	// ESTE É PARA ALTERAR
	public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
		// Aqui são fornecidos parametros do ultimo utilizador que fez a exp, e'
		// bom manter!
		this.acqConfig = acqConfig;
		if (acqConfig != null) {
			int xini = (int) (Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("xini")));
			jSliderX0.setValue(xini);
			// etc...
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
				"/pt/utl/ist/elab/virtual/client/mm/resources/mm_iconified.png"));
	}

	// ESTE É PARA ALTERAR
	public String getCustomizerTitle() {
		return "Harmonic Oscillator Experiment Configuration Utility";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanelFric;
	private javax.swing.JPanel jPanelK;
	private javax.swing.JPanel jPanelM;
	private javax.swing.JPanel jPanelSamples;
	private javax.swing.JPanel jPanelTBS;
	private javax.swing.JPanel jPanelX0;
	private javax.swing.JSlider jSliderFric;
	private javax.swing.JSlider jSliderK;
	private javax.swing.JSlider jSliderM;
	private javax.swing.JSlider jSliderSamples;
	private javax.swing.JSlider jSliderTBS;
	private javax.swing.JSlider jSliderX0;
	private javax.swing.JTextField jTextFieldFric;
	private javax.swing.JTextField jTextFieldK;
	private javax.swing.JTextField jTextFieldM;
	private javax.swing.JTextField jTextFieldSamples;
	private javax.swing.JTextField jTextFieldTBS;
	private javax.swing.JTextField jTextFieldX0;
	// End of variables declaration//GEN-END:variables

}
