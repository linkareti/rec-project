/*
 * M3Customizer.java
 *
 * Created on 17 de Fevereiro de 2005, 12:35
 */

package pt.utl.ist.elab.client.vm3;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author n0dP2
 */
public class M3Customizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/** Creates new form M3Customizer */
	public M3Customizer() {
		initComponents();
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

	public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
		this.acqConfig = acqConfig;
		if (acqConfig != null) {
			textX.setText(acqConfig.getSelectedHardwareParameterValue("x0"));
			textY.setText(acqConfig.getSelectedHardwareParameterValue("y0"));
			sliderK1.setValue((int) (Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("k1")) * 100));
			sliderK2.setValue((int) (Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("k2")) * 100));
			sliderK3.setValue((int) (Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("k2")) * 100));
			sliderMassa.setValue((int) (Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("massa")) * 100));
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
				"/pt/utl/ist/elab/client/vm3/resources/m3_iconified.png"));
	}

	// ESTE � PARA ALTERAR
	public String getCustomizerTitle() {
		return "3 Strings Experiment Configuration Utility";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	// ****************************REC********************************************/

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		panelMassa = new javax.swing.JPanel();
		sliderMassa = new javax.swing.JSlider();
		textMassa = new javax.swing.JTextField();
		labelMassa = new javax.swing.JLabel();
		panelK1 = new javax.swing.JPanel();
		sliderK1 = new javax.swing.JSlider();
		textK1 = new javax.swing.JTextField();
		labelK1 = new javax.swing.JLabel();
		panelK2 = new javax.swing.JPanel();
		sliderK2 = new javax.swing.JSlider();
		textK2 = new javax.swing.JTextField();
		labelK2 = new javax.swing.JLabel();
		panelK3 = new javax.swing.JPanel();
		sliderK3 = new javax.swing.JSlider();
		textK3 = new javax.swing.JTextField();
		labelK3 = new javax.swing.JLabel();
		panelTBS = new javax.swing.JPanel();
		sliderTBS = new javax.swing.JSlider();
		textTBS = new javax.swing.JTextField();
		labelTBS = new javax.swing.JLabel();
		panelNS = new javax.swing.JPanel();
		sliderNS = new javax.swing.JSlider();
		textNS = new javax.swing.JTextField();
		panelMolas = new javax.swing.JPanel();
		panelDefault = new javax.swing.JPanel();
		buttonOK = new javax.swing.JButton();
		buttonCancel = new javax.swing.JButton();
		buttonDefault = new javax.swing.JButton();
		panelXY = new javax.swing.JPanel();
		textX = new javax.swing.JTextField();
		textY = new javax.swing.JTextField();
		labelX = new javax.swing.JLabel();
		labelY = new javax.swing.JLabel();
		labelXm = new javax.swing.JLabel();
		labelYm = new javax.swing.JLabel();
		labelInf1 = new javax.swing.JLabel();
		labelInf2 = new javax.swing.JLabel();
		labelT = new javax.swing.JLabel();

		setLayout(new java.awt.GridBagLayout());

		panelMassa.setLayout(new java.awt.GridBagLayout());

		panelMassa.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.1", "Mass")));
		panelMassa.setMinimumSize(new java.awt.Dimension(220, 50));
		panelMassa.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderMassa.setMajorTickSpacing(1);
		sliderMassa.setMaximum(1000);
		sliderMassa.setMinimum(1);
		sliderMassa.setMinorTickSpacing(1);
		sliderMassa.setValue(1);
		sliderMassa.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderMassa.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderMassa.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderMassaStateChanged(evt);
			}
		});

		panelMassa.add(sliderMassa, new java.awt.GridBagConstraints());

		textMassa.setText("0.01");
		textMassa.setMinimumSize(new java.awt.Dimension(35, 20));
		textMassa.setPreferredSize(new java.awt.Dimension(35, 20));
		textMassa.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textMassaFocusLost(evt);
			}
		});

		panelMassa.add(textMassa, new java.awt.GridBagConstraints());

		labelMassa.setText("kg");
		labelMassa.setMaximumSize(new java.awt.Dimension(25, 15));
		labelMassa.setMinimumSize(new java.awt.Dimension(25, 15));
		labelMassa.setPreferredSize(new java.awt.Dimension(25, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
		panelMassa.add(labelMassa, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(panelMassa, gridBagConstraints);

		panelK1.setLayout(new java.awt.GridBagLayout());

		panelK1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.2", "Spring 1 Constant")));
		panelK1.setMinimumSize(new java.awt.Dimension(220, 50));
		panelK1.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderK1.setMaximum(1000);
		sliderK1.setValue(100);
		sliderK1.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderK1.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderK1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderK1StateChanged(evt);
			}
		});

		panelK1.add(sliderK1, new java.awt.GridBagConstraints());

		textK1.setText("1.00");
		textK1.setMinimumSize(new java.awt.Dimension(35, 20));
		textK1.setPreferredSize(new java.awt.Dimension(35, 20));
		textK1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textK1FocusLost(evt);
			}
		});

		panelK1.add(textK1, new java.awt.GridBagConstraints());

		labelK1.setText("N/m");
		labelK1.setMaximumSize(new java.awt.Dimension(25, 15));
		labelK1.setMinimumSize(new java.awt.Dimension(25, 15));
		labelK1.setPreferredSize(new java.awt.Dimension(25, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
		panelK1.add(labelK1, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(panelK1, gridBagConstraints);

		panelK2.setLayout(new java.awt.GridBagLayout());

		panelK2.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.3", "Spring 2 Constant")));
		panelK2.setMinimumSize(new java.awt.Dimension(220, 50));
		panelK2.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderK2.setMaximum(1000);
		sliderK2.setValue(100);
		sliderK2.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderK2.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderK2.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderK2StateChanged(evt);
			}
		});

		panelK2.add(sliderK2, new java.awt.GridBagConstraints());

		textK2.setText("1.00");
		textK2.setMinimumSize(new java.awt.Dimension(35, 20));
		textK2.setPreferredSize(new java.awt.Dimension(35, 20));
		textK2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textK2FocusLost(evt);
			}
		});

		panelK2.add(textK2, new java.awt.GridBagConstraints());

		labelK2.setText("N/m");
		labelK2.setMaximumSize(new java.awt.Dimension(25, 15));
		labelK2.setMinimumSize(new java.awt.Dimension(25, 15));
		labelK2.setPreferredSize(new java.awt.Dimension(25, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
		panelK2.add(labelK2, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		add(panelK2, gridBagConstraints);

		panelK3.setLayout(new java.awt.GridBagLayout());

		panelK3.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.4", "Spring 3 Constant")));
		panelK3.setMaximumSize(new java.awt.Dimension(220, 50));
		panelK3.setMinimumSize(new java.awt.Dimension(220, 50));
		panelK3.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderK3.setMaximum(1000);
		sliderK3.setValue(100);
		sliderK3.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderK3.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderK3.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderK3StateChanged(evt);
			}
		});

		panelK3.add(sliderK3, new java.awt.GridBagConstraints());

		textK3.setText("1.00");
		textK3.setMinimumSize(new java.awt.Dimension(35, 20));
		textK3.setPreferredSize(new java.awt.Dimension(35, 20));
		textK3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textK3FocusLost(evt);
			}
		});

		panelK3.add(textK3, new java.awt.GridBagConstraints());

		labelK3.setText("N/m");
		labelK3.setMaximumSize(new java.awt.Dimension(25, 15));
		labelK3.setMinimumSize(new java.awt.Dimension(25, 15));
		labelK3.setPreferredSize(new java.awt.Dimension(25, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
		panelK3.add(labelK3, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		add(panelK3, gridBagConstraints);

		panelTBS.setLayout(new java.awt.GridBagLayout());

		panelTBS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.tbs", "Time Between Samples")));
		panelTBS.setMaximumSize(new java.awt.Dimension(220, 50));
		panelTBS.setMinimumSize(new java.awt.Dimension(220, 50));
		panelTBS.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderTBS.setMaximum(50000);
		sliderTBS.setMinimum(5000);
		sliderTBS.setValue(10000);
		sliderTBS.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderTBS.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderTBS.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderTBSStateChanged(evt);
			}
		});

		panelTBS.add(sliderTBS, new java.awt.GridBagConstraints());

		textTBS.setText("100");
		textTBS.setMinimumSize(new java.awt.Dimension(35, 20));
		textTBS.setPreferredSize(new java.awt.Dimension(35, 20));
		textTBS.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textTBSFocusLost(evt);
			}
		});

		panelTBS.add(textTBS, new java.awt.GridBagConstraints());

		labelTBS.setText("ms");
		labelTBS.setMaximumSize(new java.awt.Dimension(25, 15));
		labelTBS.setMinimumSize(new java.awt.Dimension(25, 15));
		labelTBS.setPreferredSize(new java.awt.Dimension(25, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
		panelTBS.add(labelTBS, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		add(panelTBS, gridBagConstraints);

		panelNS.setLayout(new java.awt.GridBagLayout());

		panelNS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"ReCExpM3$rec.exp.customizer.title.samples", "Number of Samples")));
		panelNS.setMaximumSize(new java.awt.Dimension(220, 50));
		panelNS.setMinimumSize(new java.awt.Dimension(220, 50));
		panelNS.setPreferredSize(new java.awt.Dimension(220, 50));
		sliderNS.setMaximum(50000);
		sliderNS.setMinimum(100);
		sliderNS.setValue(10000);
		sliderNS.setMinimumSize(new java.awt.Dimension(140, 24));
		sliderNS.setPreferredSize(new java.awt.Dimension(140, 24));
		sliderNS.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				sliderNSStateChanged(evt);
			}
		});

		panelNS.add(sliderNS, new java.awt.GridBagConstraints());

		textNS.setText("100");
		textNS.setMinimumSize(new java.awt.Dimension(35, 20));
		textNS.setPreferredSize(new java.awt.Dimension(35, 20));
		textNS.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textNSFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 25);
		panelNS.add(textNS, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridheight = 3;
		add(panelNS, gridBagConstraints);

		panelMolas = new DrawableMolas(this);
		panelMolas.setBackground(null);
		panelMolas.setForeground(new java.awt.Color(255, 0, 51));
		panelMolas.setMaximumSize(new java.awt.Dimension(250, 250));
		panelMolas.setMinimumSize(new java.awt.Dimension(250, 250));
		panelMolas.setPreferredSize(new java.awt.Dimension(250, 250));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		add(panelMolas, gridBagConstraints);

		panelDefault.setLayout(new java.awt.GridBagLayout());

		panelDefault.setMinimumSize(new java.awt.Dimension(400, 25));
		panelDefault.setPreferredSize(new java.awt.Dimension(400, 25));
		buttonOK.setText("OK");
		buttonOK.setMaximumSize(new java.awt.Dimension(140, 23));
		buttonOK.setMinimumSize(new java.awt.Dimension(140, 23));
		buttonOK.setPreferredSize(new java.awt.Dimension(140, 23));
		buttonOK.setEnabled(false);
		buttonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonOKActionPerformed(evt);
			}
		});

		panelDefault.add(buttonOK, new java.awt.GridBagConstraints());

		buttonCancel.setText(ReCResourceBundle
				.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.cancel", "Cancel"));
		buttonCancel.setMaximumSize(new java.awt.Dimension(140, 23));
		buttonCancel.setMinimumSize(new java.awt.Dimension(140, 23));
		buttonCancel.setPreferredSize(new java.awt.Dimension(140, 23));
		buttonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonCancelActionPerformed(evt);
			}
		});

		panelDefault.add(buttonCancel, new java.awt.GridBagConstraints());

		buttonDefault.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.dfc",
				"Default Configuration"));
		buttonDefault.setMaximumSize(new java.awt.Dimension(160, 23));
		buttonDefault.setMinimumSize(new java.awt.Dimension(160, 23));
		buttonDefault.setPreferredSize(new java.awt.Dimension(160, 23));
		buttonDefault.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonDefaultActionPerformed(evt);
			}
		});

		panelDefault.add(buttonDefault, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(panelDefault, gridBagConstraints);

		panelXY.setLayout(new java.awt.GridBagLayout());

		panelXY.setMaximumSize(new java.awt.Dimension(100, 40));
		panelXY.setMinimumSize(new java.awt.Dimension(100, 40));
		panelXY.setPreferredSize(new java.awt.Dimension(100, 40));
		textX.setText("5.0");
		textX.setPreferredSize(new java.awt.Dimension(40, 20));
		textX.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textXFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		panelXY.add(textX, gridBagConstraints);

		textY.setText("5.0");
		textY.setPreferredSize(new java.awt.Dimension(40, 20));
		textY.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				textYFocusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		panelXY.add(textY, gridBagConstraints);

		labelX.setText("X:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		panelXY.add(labelX, gridBagConstraints);

		labelY.setText("Y:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		panelXY.add(labelY, gridBagConstraints);

		labelXm.setText("m");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		panelXY.add(labelXm, gridBagConstraints);

		labelYm.setText("m");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		panelXY.add(labelYm, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(panelXY, gridBagConstraints);

		labelInf1.setFont(new java.awt.Font("Arial", 1, 11));
		labelInf1.setForeground(java.awt.Color.red);
		labelInf1.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.9",
				"Please disturb the system"));
		labelInf1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
		labelInf1.setMaximumSize(new java.awt.Dimension(100, 20));
		labelInf1.setMinimumSize(new java.awt.Dimension(100, 20));
		labelInf1.setPreferredSize(new java.awt.Dimension(100, 14));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(labelInf1, gridBagConstraints);

		labelInf2.setFont(new java.awt.Font("Arial", 1, 11));
		labelInf2.setForeground(java.awt.Color.red);
		labelInf2.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.10",
				"by dragging the mass."));
		labelInf2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		labelInf2.setMaximumSize(new java.awt.Dimension(100, 20));
		labelInf2.setMinimumSize(new java.awt.Dimension(100, 20));
		labelInf2.setPreferredSize(new java.awt.Dimension(100, 14));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(labelInf2, gridBagConstraints);

		labelT.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.12", "Simulation Time")
				+ ": 10 s");
		labelT.setMaximumSize(new java.awt.Dimension(130, 12));
		labelT.setMinimumSize(new java.awt.Dimension(130, 12));
		labelT.setPreferredSize(new java.awt.Dimension(130, 12));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		add(labelT, gridBagConstraints);

	}// GEN-END:initComponents

	private void sliderNSStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderNSStateChanged
		textNS.setText(String.valueOf((int) QMethods.arredondar(sliderNS.getValue() * 0.01, 0)));
		labelT.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.12", "Simulation Time")
				+ ": " + String.valueOf((int) (sliderTBS.getValue() * 1E-7 * sliderNS.getValue())) + " s");
	}// GEN-LAST:event_sliderNSStateChanged

	private void sliderTBSStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderTBSStateChanged
		textTBS.setText(String.valueOf((int) QMethods.arredondar(sliderTBS.getValue() * 0.01, 0)));
		labelT.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.12", "Simulation Time")
				+ ": " + String.valueOf((int) (sliderTBS.getValue() * 1E-7 * sliderNS.getValue())) + " s");
	}// GEN-LAST:event_sliderTBSStateChanged

	private void textNSFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textNSFocusLost
		checkFloat(sliderNS, textNS, 1f, 500f);
	}// GEN-LAST:event_textNSFocusLost

	private void textTBSFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textTBSFocusLost
		checkFloat(sliderTBS, textTBS, 50f, 500f);
	}// GEN-LAST:event_textTBSFocusLost

	private void buttonDefaultActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonDefaultActionPerformed
		textX.setText("5.0");
		textY.setText("5.0");
		sliderMassa.setValue(1);
		sliderK1.setValue(100);
		sliderK2.setValue(100);
		sliderK3.setValue(100);
		sliderNS.setValue(10000);
		sliderTBS.setValue(10000);
		checkFloat(null, textY, -30f, 30f);
	}// GEN-LAST:event_buttonDefaultActionPerformed

	private void textYFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textYFocusLost
		checkFloat(null, textY, -30f, 30f);
	}// GEN-LAST:event_textYFocusLost

	private void textXFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textXFocusLost
		checkFloat(null, textX, -30f, 30f);
	}// GEN-LAST:event_textXFocusLost

	private void sliderK3StateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderK3StateChanged
		textK3.setText(String.valueOf(QMethods.arredondar(sliderK3.getValue() * 0.01, 2)));
	}// GEN-LAST:event_sliderK3StateChanged

	private void textK3FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textK3FocusLost
		checkFloat(sliderK3, textK3, 0f, 10f);
	}// GEN-LAST:event_textK3FocusLost

	private void textK2FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textK2FocusLost
		checkFloat(sliderK2, textK2, 0f, 10f);
	}// GEN-LAST:event_textK2FocusLost

	private void sliderK2StateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderK2StateChanged
		textK2.setText(String.valueOf(QMethods.arredondar(sliderK2.getValue() * 0.01, 2)));
	}// GEN-LAST:event_sliderK2StateChanged

	private void textK1FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textK1FocusLost
		checkFloat(sliderK1, textK1, 0f, 10f);
	}// GEN-LAST:event_textK1FocusLost

	private void sliderK1StateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderK1StateChanged
		textK1.setText(String.valueOf(QMethods.arredondar(sliderK1.getValue() * 0.01, 2)));
	}// GEN-LAST:event_sliderK1StateChanged

	private void textMassaFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_textMassaFocusLost
		checkFloat(sliderMassa, textMassa, 0.01f, 10f);
	}// GEN-LAST:event_textMassaFocusLost

	private void sliderMassaStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_sliderMassaStateChanged
		textMassa.setText(String.valueOf(QMethods.arredondar(sliderMassa.getValue() * 0.01, 2)));
	}// GEN-LAST:event_sliderMassaStateChanged

	private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonOKActionPerformed
		acqConfig.setTotalSamples((int) (sliderNS.getValue() / 100));

		acqConfig.setSelectedFrequency(new Frequency((double) sliderTBS.getValue() * 0.01, hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), hardwareInfo
				.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));

		acqConfig.getSelectedHardwareParameter("massa").setParameterValue("" + sliderMassa.getValue() * 0.01F);
		acqConfig.getSelectedHardwareParameter("k1").setParameterValue("" + sliderK1.getValue() * 0.01F);
		acqConfig.getSelectedHardwareParameter("k2").setParameterValue("" + sliderK2.getValue() * 0.01F);
		acqConfig.getSelectedHardwareParameter("k3").setParameterValue("" + sliderK3.getValue() * 0.01F);
		acqConfig.getSelectedHardwareParameter("x0").setParameterValue(textX.getText());
		acqConfig.getSelectedHardwareParameter("y0").setParameterValue(textY.getText());
		fireICustomizerListenerDone();
	}// GEN-LAST:event_buttonOKActionPerformed

	private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_buttonCancelActionPerformed

	private void checkFloat(javax.swing.JSlider slider, javax.swing.JTextField text, float min, float max) {

		boolean panel = false;
		String test = text.getText();
		float num;
		try {
			num = Float.parseFloat(test);
		} catch (NumberFormatException e) {
			num = min;
		}

		if (num > max || num < min) {
			num = min;
		}
		try {
			slider.setValue((int) (100 * QMethods.arredondar(num, 2)));
		} catch (NullPointerException e) {
			if (num == min)
				num = 5f;
			panel = true;
		}
		text.setText(Float.toString(num));
		if (textX.getText().equals("5.0") && textY.getText().equals("5.0")) {
			labelInf2.setForeground(java.awt.Color.red);
			labelInf1.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.9",
					"Please disturb the system"));
			labelInf2.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.10",
					"by dragging the mass."));
			buttonOK.setEnabled(false);
		} else {
			labelInf1.setText("");
			labelInf2.setForeground(new java.awt.Color(0, 125, 0));
			labelInf2.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.11", "Ready."));
			buttonOK.setEnabled(true);
		}
		if (panel == true) {
			((DrawableMolas) panelMolas).fazMexer(Double.parseDouble(textX.getText()), Double.parseDouble(textY
					.getText()));
		}
	}

	public static void main(String[] args) {
		java.util.Locale loc = new java.util.Locale("pt", "PT");
		java.util.Locale.setDefault(loc);

		System.out.println(java.util.Locale.getDefault());

		ReCResourceBundle.loadResourceBundle("ReCExpM3",
				"recresource:///pt/utl/ist/elab/client/vm3/resources/ReCExpM3");

		System.out.println(ReCResourceBundle.findString("ReCExpM3$rec.exp.display.m3.tip.3"));
		javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.getContentPane().add(new M3Customizer());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		frame.show();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton buttonCancel;
	private javax.swing.JButton buttonDefault;
	public javax.swing.JButton buttonOK;
	public javax.swing.JLabel labelInf1;
	public javax.swing.JLabel labelInf2;
	private javax.swing.JLabel labelK1;
	private javax.swing.JLabel labelK2;
	private javax.swing.JLabel labelK3;
	private javax.swing.JLabel labelMassa;
	private javax.swing.JLabel labelT;
	private javax.swing.JLabel labelTBS;
	private javax.swing.JLabel labelX;
	private javax.swing.JLabel labelXm;
	private javax.swing.JLabel labelY;
	private javax.swing.JLabel labelYm;
	private javax.swing.JPanel panelDefault;
	private javax.swing.JPanel panelK1;
	private javax.swing.JPanel panelK2;
	private javax.swing.JPanel panelK3;
	private javax.swing.JPanel panelMassa;
	private javax.swing.JPanel panelMolas;
	private javax.swing.JPanel panelNS;
	private javax.swing.JPanel panelTBS;
	private javax.swing.JPanel panelXY;
	private javax.swing.JSlider sliderK1;
	private javax.swing.JSlider sliderK2;
	private javax.swing.JSlider sliderK3;
	private javax.swing.JSlider sliderMassa;
	private javax.swing.JSlider sliderNS;
	private javax.swing.JSlider sliderTBS;
	private javax.swing.JTextField textK1;
	private javax.swing.JTextField textK2;
	private javax.swing.JTextField textK3;
	private javax.swing.JTextField textMassa;
	private javax.swing.JTextField textNS;
	private javax.swing.JTextField textTBS;
	public javax.swing.JTextField textX;
	public javax.swing.JTextField textY;
	// End of variables declaration//GEN-END:variables

}
