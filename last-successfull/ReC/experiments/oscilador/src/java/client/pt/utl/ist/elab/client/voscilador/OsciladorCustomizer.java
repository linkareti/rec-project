/*
 * OsciladorCustomizer.java
 *
 * Created on 30 de Mar�o de 2005, 19:10
 */

package pt.utl.ist.elab.client.voscilador;

/**
 *
 * @author  RF
 */
import javax.swing.JOptionPane;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class OsciladorCustomizer extends javax.swing.JPanel implements
		com.linkare.rec.impl.client.customizer.ICustomizer {

	/** Creates new form OsciladorCustomizer */
	public OsciladorCustomizer() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		jPanelControl = new javax.swing.JPanel();
		jPanelButtons = new javax.swing.JPanel();
		jButtonDafaultConfig = new javax.swing.JButton();
		jButtonStop = new javax.swing.JButton();
		jButtonStart = new javax.swing.JButton();
		jPanelSliders = new javax.swing.JPanel();
		jSliderTBS = new javax.swing.JSlider();
		jSliderNSamples = new javax.swing.JSlider();
		jPanelInput = new javax.swing.JPanel();
		jTextFieldA = new javax.swing.JTextField();
		jTextFieldFrequencia = new javax.swing.JTextField();
		jTextFieldAltura = new javax.swing.JTextField();
		jTextFieldG = new javax.swing.JTextField();
		jLabelA = new javax.swing.JLabel();
		jLabelFrequencia = new javax.swing.JLabel();
		jLabelAltura = new javax.swing.JLabel();
		jLabelG = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		setMaximumSize(new java.awt.Dimension(600, 300));
		setMinimumSize(new java.awt.Dimension(600, 300));
		setPreferredSize(new java.awt.Dimension(600, 300));
		getAccessibleContext().setAccessibleName("teste");
		jPanelControl.setLayout(new java.awt.GridBagLayout());

		jPanelControl.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.control", "Control")));
		jPanelControl.setMaximumSize(new java.awt.Dimension(600, 200));
		jPanelControl.setMinimumSize(new java.awt.Dimension(600, 200));
		jPanelControl.setPreferredSize(new java.awt.Dimension(600, 200));
		jPanelButtons.setLayout(new java.awt.GridBagLayout());

		jPanelButtons.setMaximumSize(new java.awt.Dimension(140, 160));
		jPanelButtons.setMinimumSize(new java.awt.Dimension(140, 160));
		jPanelButtons.setPreferredSize(new java.awt.Dimension(140, 160));
		jButtonDafaultConfig.setText(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.default", "Default Settings"));
		jButtonDafaultConfig.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jButtonDafaultConfig.setMaximumSize(new java.awt.Dimension(100, 40));
		jButtonDafaultConfig.setMinimumSize(new java.awt.Dimension(100, 40));
		jButtonDafaultConfig.setPreferredSize(new java.awt.Dimension(100, 40));
		jButtonDafaultConfig.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonDafaultConfigActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		jPanelButtons.add(jButtonDafaultConfig, gridBagConstraints);

		jButtonStop.setText(ReCResourceBundle.findStringOrDefault("ReCExpOscilador$rec.exp.customizer.title.cancel",
				"Cancel"));
		jButtonStop.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jButtonStop.setMaximumSize(new java.awt.Dimension(100, 40));
		jButtonStop.setMinimumSize(new java.awt.Dimension(100, 40));
		jButtonStop.setPreferredSize(new java.awt.Dimension(100, 40));
		jButtonStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonStopActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanelButtons.add(jButtonStop, gridBagConstraints);

		jButtonStart.setText(ReCResourceBundle.findStringOrDefault("ReCExpOscilador$rec.exp.customizer.title.ok",
				"Start"));
		jButtonStart.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jButtonStart.setMaximumSize(new java.awt.Dimension(100, 40));
		jButtonStart.setMinimumSize(new java.awt.Dimension(100, 40));
		jButtonStart.setPreferredSize(new java.awt.Dimension(100, 40));
		jButtonStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonStartActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jPanelButtons.add(jButtonStart, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanelControl.add(jPanelButtons, gridBagConstraints);

		jPanelSliders.setLayout(new java.awt.BorderLayout());

		jPanelSliders.setMaximumSize(new java.awt.Dimension(450, 160));
		jPanelSliders.setMinimumSize(new java.awt.Dimension(450, 160));
		jPanelSliders.setPreferredSize(new java.awt.Dimension(450, 160));
		jSliderTBS.setMajorTickSpacing(25);
		jSliderTBS.setMaximum(250);
		jSliderTBS.setMinimum(50);
		jSliderTBS.setMinorTickSpacing(25);
		jSliderTBS.setPaintLabels(true);
		jSliderTBS.setPaintTicks(true);
		jSliderTBS.setSnapToTicks(true);
		jSliderTBS.setValue(100);
		jSliderTBS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.tbs", "Time Between Samples")));
		jSliderTBS.setMaximumSize(new java.awt.Dimension(300, 80));
		jSliderTBS.setMinimumSize(new java.awt.Dimension(300, 80));
		jSliderTBS.setPreferredSize(new java.awt.Dimension(300, 80));
		jPanelSliders.add(jSliderTBS, java.awt.BorderLayout.CENTER);

		jSliderNSamples.setMajorTickSpacing(50);
		jSliderNSamples.setMaximum(500);
		jSliderNSamples.setMinimum(50);
		jSliderNSamples.setMinorTickSpacing(50);
		jSliderNSamples.setPaintLabels(true);
		jSliderNSamples.setPaintTicks(true);
		jSliderNSamples.setSnapToTicks(true);
		jSliderNSamples.setValue(100);
		jSliderNSamples.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.nSamples", "Number of Samples")));
		jSliderNSamples.setMaximumSize(new java.awt.Dimension(300, 80));
		jSliderNSamples.setMinimumSize(new java.awt.Dimension(300, 80));
		jSliderNSamples.setPreferredSize(new java.awt.Dimension(300, 80));
		jPanelSliders.add(jSliderNSamples, java.awt.BorderLayout.SOUTH);

		jPanelControl.add(jPanelSliders, new java.awt.GridBagConstraints());

		add(jPanelControl, java.awt.BorderLayout.CENTER);

		jPanelInput.setLayout(new java.awt.GridBagLayout());

		jPanelInput.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.config", "Parameters")));
		jPanelInput.setMaximumSize(new java.awt.Dimension(600, 100));
		jPanelInput.setMinimumSize(new java.awt.Dimension(600, 100));
		jPanelInput.setPreferredSize(new java.awt.Dimension(600, 100));
		jTextFieldA.setText("1.0");
		jTextFieldA.setMaximumSize(new java.awt.Dimension(50, 20));
		jTextFieldA.setMinimumSize(new java.awt.Dimension(50, 20));
		jTextFieldA.setPreferredSize(new java.awt.Dimension(50, 20));
		jTextFieldA.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldAFocusLost(evt);
			}
		});
		jTextFieldA.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextFieldAKeyReleased(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanelInput.add(jTextFieldA, gridBagConstraints);

		jTextFieldFrequencia.setText("0.5");
		jTextFieldFrequencia.setMaximumSize(new java.awt.Dimension(50, 20));
		jTextFieldFrequencia.setMinimumSize(new java.awt.Dimension(50, 20));
		jTextFieldFrequencia.setPreferredSize(new java.awt.Dimension(50, 20));
		jTextFieldFrequencia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFieldFrequenciaActionPerformed(evt);
			}
		});
		jTextFieldFrequencia.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldFrequenciaFocusLost(evt);
			}
		});
		jTextFieldFrequencia.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextFieldFrequenciaKeyReleased(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		jPanelInput.add(jTextFieldFrequencia, gridBagConstraints);

		jTextFieldAltura.setText("0.2");
		jTextFieldAltura.setMaximumSize(new java.awt.Dimension(50, 20));
		jTextFieldAltura.setMinimumSize(new java.awt.Dimension(50, 20));
		jTextFieldAltura.setPreferredSize(new java.awt.Dimension(50, 20));
		jTextFieldAltura.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldAlturaFocusLost(evt);
			}
		});
		jTextFieldAltura.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextFieldAlturaKeyReleased(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		jPanelInput.add(jTextFieldAltura, gridBagConstraints);

		jTextFieldG.setText("9.8");
		jTextFieldG.setMaximumSize(new java.awt.Dimension(50, 20));
		jTextFieldG.setMinimumSize(new java.awt.Dimension(50, 20));
		jTextFieldG.setPreferredSize(new java.awt.Dimension(50, 20));
		jTextFieldG.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldGFocusLost(evt);
			}
		});
		jTextFieldG.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextFieldGKeyReleased(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		jPanelInput.add(jTextFieldG, gridBagConstraints);

		jLabelA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelA.setText(ReCResourceBundle.findStringOrDefault("ReCExpOscilador$rec.exp.customizer.title.a",
				"Parabola Width"));
		jLabelA.setMaximumSize(new java.awt.Dimension(300, 15));
		jLabelA.setMinimumSize(new java.awt.Dimension(300, 15));
		jLabelA.setPreferredSize(new java.awt.Dimension(300, 15));
		jPanelInput.add(jLabelA, new java.awt.GridBagConstraints());

		jLabelFrequencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelFrequencia.setText(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.frequencia", "Rotation Frequency"));
		jLabelFrequencia.setMaximumSize(new java.awt.Dimension(300, 15));
		jLabelFrequencia.setMinimumSize(new java.awt.Dimension(300, 15));
		jLabelFrequencia.setPreferredSize(new java.awt.Dimension(300, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		jPanelInput.add(jLabelFrequencia, gridBagConstraints);

		jLabelAltura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelAltura.setText(ReCResourceBundle.findStringOrDefault(
				"ReCExpOscilador$rec.exp.customizer.title.alturaInicial", "Starting Position"));
		jLabelAltura.setMaximumSize(new java.awt.Dimension(300, 15));
		jLabelAltura.setMinimumSize(new java.awt.Dimension(300, 15));
		jLabelAltura.setPreferredSize(new java.awt.Dimension(300, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		jPanelInput.add(jLabelAltura, gridBagConstraints);

		jLabelG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelG.setText(ReCResourceBundle.findStringOrDefault("ReCExpOscilador$rec.exp.customizer.title.g",
				"Gravitic Acceleration"));
		jLabelG.setMaximumSize(new java.awt.Dimension(300, 15));
		jLabelG.setMinimumSize(new java.awt.Dimension(300, 15));
		jLabelG.setPreferredSize(new java.awt.Dimension(300, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		jPanelInput.add(jLabelG, gridBagConstraints);

		add(jPanelInput, java.awt.BorderLayout.NORTH);

	}// GEN-END:initComponents

	private void jTextFieldFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldFrequenciaActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextFieldFrequenciaActionPerformed

	private void jTextFieldFrequenciaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldFrequenciaKeyReleased
		if (evt.getKeyCode() == 10) { // Enter pressionado
			jPanelControl.requestFocusInWindow();
		}
	}// GEN-LAST:event_jTextFieldFrequenciaKeyReleased

	private void jTextFieldFrequenciaFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldFrequenciaFocusLost
		try {
			// Guarda o valor da frequencia
			frequencia = Double.parseDouble(jTextFieldFrequencia.getText().trim());
			if (frequencia < 0) { // Frequencias negativas
				frequencia = 0.5;
				// Mensagem de erro
				JOptionPane.showMessageDialog(this, "O valor introduzido � negativo!", "Erro no Input",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			frequencia = 0.5; // Apanhou uma excepcao
		}
		jTextFieldFrequencia.setText("" + frequencia); // Actualiza o jTextField
	}// GEN-LAST:event_jTextFieldFrequenciaFocusLost

	private void jTextFieldAlturaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldAlturaKeyReleased
		if (evt.getKeyCode() == 10) { // Enter pressionado
			jPanelControl.requestFocusInWindow();
		}
	}// GEN-LAST:event_jTextFieldAlturaKeyReleased

	private void jTextFieldAlturaFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldAlturaFocusLost
		try {
			// Guarda o valor da altura inicial
			alturaInicial = Double.parseDouble(jTextFieldAltura.getText().trim());
			if (alturaInicial < 0) { // Altura inicial negativa
				alturaInicial = 0.2;
				// Mensagem de erro
				JOptionPane.showMessageDialog(this, "O valor introduzido � negativo!", "Erro no Input",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			alturaInicial = 0.2; // Apanhou uma excepcao
		}
		jTextFieldAltura.setText("" + alturaInicial); // Actualiza o jTextField
	}// GEN-LAST:event_jTextFieldAlturaFocusLost

	private void jTextFieldGKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldGKeyReleased
		if (evt.getKeyCode() == 10) { // Enter pressionado
			jPanelControl.requestFocusInWindow();
		}
	}// GEN-LAST:event_jTextFieldGKeyReleased

	private void jTextFieldGFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldGFocusLost
		try {
			// Guarda o valor da altura inicial
			g = Double.parseDouble(jTextFieldG.getText().trim());
			if (g < 0) { // Constantes graviticas negativas
				g = 9.8;
				// Mensagem de erro
				JOptionPane.showMessageDialog(this, "O valor introduzido � negativo!", "Erro no Input",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			g = 9.8; // Apanhou uma excepcao
		}
		jTextFieldG.setText("" + g); // Actualiza o jTextField
	}// GEN-LAST:event_jTextFieldGFocusLost

	private void jTextFieldAKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldAKeyReleased
		if (evt.getKeyCode() == 10) { // Enter pressionado
			jPanelControl.requestFocusInWindow();
		}
	}// GEN-LAST:event_jTextFieldAKeyReleased

	private void jTextFieldAFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldAFocusLost
		try {
			// Guarda o valor de a
			a = Double.parseDouble(jTextFieldA.getText().trim());
			if (a <= 0) { // a negativo
				a = 1;
				// Mensagem de erro
				JOptionPane.showMessageDialog(this, "O valor deste par�metro tem de ser positivo!", "Erro no Input",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			a = 1; // Apanhou uma excepcao
		}
		jTextFieldA.setText("" + a); // Actualiza o jTextField

	}// GEN-LAST:event_jTextFieldAFocusLost

	private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonStartActionPerformed
		acqConfig.setTotalSamples(jSliderNSamples.getValue());

		acqConfig.setSelectedFrequency(new Frequency((double) jSliderTBS.getValue(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
		acqConfig.getSelectedHardwareParameter("alturaInicial").setParameterValue("" + alturaInicial);
		acqConfig.getSelectedHardwareParameter("g").setParameterValue("" + g);
		acqConfig.getSelectedHardwareParameter("a").setParameterValue("" + a);
		acqConfig.getSelectedHardwareParameter("frequencia").setParameterValue("" + frequencia);
		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonStartActionPerformed

	private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonStopActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_jButtonStopActionPerformed

	private void jButtonDafaultConfigActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDafaultConfigActionPerformed
		jSliderNSamples.setValue(100);
		jSliderTBS.setValue(100);
		jButtonStop.setEnabled(false);
		jButtonStart.setEnabled(true);
		jTextFieldAltura.setText("" + 0.2);
		jTextFieldA.setText("" + 1);
		jTextFieldFrequencia.setText("" + 0.5);
		jTextFieldG.setText("" + 9.8);
		jTextFieldAltura.setEnabled(true);
		// jFrameInformacao.setVisible(false);

	}// GEN-LAST:event_jButtonDafaultConfigActionPerformed

	public static void main(String args[]) {
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new OsciladorCustomizer());
		dummy.pack();
		dummy.show();
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
				"/pt/utl/ist/elab/virtual/client/oscilador/resources/oscilador_iconified.png"));
	}

	// ESTE É PARA ALTERAR
	public String getCustomizerTitle() {
		return "Forced Oscillator Experiment Configuration Utility";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	double a = 1.0, g = 9.8, alturaInicial = 0.2, frequencia = 5.0;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonDafaultConfig;
	private javax.swing.JButton jButtonStart;
	private javax.swing.JButton jButtonStop;
	private javax.swing.JLabel jLabelA;
	private javax.swing.JLabel jLabelAltura;
	private javax.swing.JLabel jLabelFrequencia;
	private javax.swing.JLabel jLabelG;
	private javax.swing.JPanel jPanelButtons;
	private javax.swing.JPanel jPanelControl;
	private javax.swing.JPanel jPanelInput;
	private javax.swing.JPanel jPanelSliders;
	private javax.swing.JSlider jSliderNSamples;
	private javax.swing.JSlider jSliderTBS;
	private javax.swing.JTextField jTextFieldA;
	private javax.swing.JTextField jTextFieldAltura;
	private javax.swing.JTextField jTextFieldFrequencia;
	private javax.swing.JTextField jTextFieldG;
	// End of variables declaration//GEN-END:variables

}
