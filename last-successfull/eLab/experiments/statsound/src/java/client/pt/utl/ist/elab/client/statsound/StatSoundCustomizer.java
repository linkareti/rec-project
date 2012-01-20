/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatSoundCustomizer.java
 *
 * Created on 25-Feb-2011, 22:57:40
 */
package pt.utl.ist.elab.client.statsound;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class StatSoundCustomizer extends javax.swing.JPanel implements
		com.linkare.rec.impl.client.customizer.ICustomizer {

	private static final long serialVersionUID = -5352646489640019503L;
	// types of experiments
	private static final String VARY_FREQ = "Vary Freq";
	private static final String VARY_PISTON = "Vary Piston";
	private static final String SOUND_VEL = "Sound Vel";
	// parameters to send to the driver
	private static final String WAVE_FORM = "wave.form";
	private static final String FREQUENCY_END = "frequency.end";
	private static final String FREQUENCY_START = "frequency.start";
	private static final String PISTON_END = "piston.end";
	private static final String PISTON_START = "piston.start";
	private static final String EXPERIMENT_TYPE = "experiment.type";
	public static final int MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG = 100000;
	/** Init vars */
	// default values. Do not make it final because it should be set dynamically
	// according to the HardwareInfo config.
	private static int MAX_PISTON_POSITION = 1478;
	private static int MIN_PISTON_POSITION = 1271;
	private static final int INDEX_OF_MIN_POSITION_IN_PARAMETER_PISTON_START = 0;
	private static final int INDEX_OF_MAX_POSITION_IN_PARAMETER_PISTON_START = 1;
	private static final int INDEX_OF_STEP_IN_PARAMETER_PISTON_START = 2;
	private static final double DEFAULT_VALUE_OF_PISTON_STEP = 1.69231;
	// Sound velocity tab
	private static final int DEFAULT_SOUND_VELOCITY_NSAMPLES = 500;
	private static final int DEFAULT_SOUND_VELOCITY_PISTON = 1300;
	private static final int DEFAULT_SOUND_VELOCITY_FREQUENCY = 250;
	// Statsound I tab
	private static final int DEFAULT_STATSOUND_I_NSAMPLES = 50;
	private static final int DEFAULT_STATSOUND_I_FREQUENCY = 250;
	private static final int DEFAULT_STATSOUND_I_PISTON_INITIAL = 1300;
	private static final int DEFAULT_STATSOUND_I_PISTON_END = 1400;
	// Statsound II tab
	private static final int DEFAULT_STATSOUND_II_NSAMPLES = 400;
	private static final int DEFAULT_STATSOUND_II_FREQUENCY_INITIAL = 250;
	private static final int DEFAULT_STATSOUND_II_FREQUENCY_END = 1000;
	private static final int DEFAULT_STATSOUND_II_PISTON = 1300;
	private static final String MIN_SAMPLES_STR = ReCResourceBundle
			.findString("statsound$rec.exp.statsoud.lbl.minsamples");
	private static final String MAX_SAMPLES_STR = ReCResourceBundle
			.findString("statsound$rec.exp.statsoud.lbl.maxsamples");
	private static final String SOUND_SPEED_NSAMPLES_MESSAGES_STR = ReCResourceBundle
			.findString("statsound$rec.exp.statsoud.lbl.statsound.nsamplesMessage");

	/** REC */
	private HardwareInfo hardwareInfo = null;
	private HardwareAcquisitionConfig acqConfig = null;
	private final javax.swing.ImageIcon ICON = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/statsound/resources/sound.gif"));
	private final String TITLE = ReCResourceBundle.findString("statsound$rec.exp.statsoud.customizer.title");

	/** Creates new form NewJPanel */
	public StatSoundCustomizer() {
		initComponents();

		final String sin = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.sin");
		final String triangular = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.triangular");
		final String pinkNoise = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.pinkNoise");
		final String whiteNoise = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.whiteNoise");
		final String pulse = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.pulse");

		jComboBoxSoundVelocityWaveForm.addItem(sin);
		jComboBoxSoundVelocityWaveForm.addItem(triangular);
		jComboBoxSoundVelocityWaveForm.addItem(whiteNoise);
		jComboBoxSoundVelocityWaveForm.addItem(pinkNoise);
		jComboBoxSoundVelocityWaveForm.addItem(pulse);

		jLabelSoundVelocityNSamplesAlert.setVisible(false);
		jLabelStatSoundINSamplesAlert.setVisible(false);
		jLabelStatSoundIINSamplesAlert.setVisible(false);

		defaultConfig();
	}

	private void defaultConfig() {
		final int protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;
		switch (protocol) {
		case 1:
			jSliderSoundVelocityNSamples.setValue(DEFAULT_SOUND_VELOCITY_NSAMPLES);
			jTextFieldSoundVelocityNSamples.setText("" + jSliderSoundVelocityNSamples.getValue());
			jSliderSoundVelocityFrequency.setValue(DEFAULT_SOUND_VELOCITY_FREQUENCY);
			jTextFieldSoundVelocityFrequency.setText("" + jSliderSoundVelocityFrequency.getValue());
			jSliderSoundVelocityPiston.setValue(DEFAULT_SOUND_VELOCITY_PISTON);
			jTextFieldSoundVelocityPiston.setText("" + jSliderSoundVelocityPiston.getValue());
			jRadioButtonSoundVelocityMic2.setSelected(true);
			jRadioButtonSoundVelocityMic3.setSelected(false);
			jRadioButtonSoundVelocityMic4.setSelected(false);
			jCheckBoxSoundVelocityHeat.setSelected(false);
			break;
		case 2:
			jSliderStatSoundINSamples.setValue(DEFAULT_STATSOUND_I_NSAMPLES);
			jTextFieldStatSoundINSamples.setText("" + jSliderStatSoundINSamples.getValue());
			jSliderStatSoundIFrequency.setValue(DEFAULT_STATSOUND_I_FREQUENCY);
			jTextFieldStatSoundIFrequency.setText("" + jSliderStatSoundIFrequency.getValue());
			jSliderStatSoundIPistonInitial.setValue(DEFAULT_STATSOUND_I_PISTON_INITIAL);
			jTextFieldStatSoundIPistonInitial.setText("" + jSliderStatSoundIPistonInitial.getValue());
			jSliderStatSoundIPistonEnd.setValue(DEFAULT_STATSOUND_I_PISTON_END);
			jTextFieldStatSoundIPistonEnd.setText("" + jSliderStatSoundIPistonEnd.getValue());
			jRadioButtonStatSoundIMic2.setSelected(true);
			jRadioButtonStatSoundIMic3.setSelected(false);
			jRadioButtonStatSoundIMic4.setSelected(false);
			jCheckBoxStatSoundIHeat.setSelected(false);
			break;
		case 3:
			jSliderStatSoundIINSamples.setValue(DEFAULT_STATSOUND_II_NSAMPLES);
			jTextFieldStatSoundIINSamples.setText("" + jSliderStatSoundIINSamples.getValue());
			jSliderStatSoundIIFrequencyInitial.setValue(DEFAULT_STATSOUND_II_FREQUENCY_INITIAL);
			jTextFieldStatSoundIIFrequencyInitial.setText("" + jSliderStatSoundIIFrequencyInitial.getValue());
			jSliderStatSoundIIFrequencyEnd.setValue(DEFAULT_STATSOUND_II_FREQUENCY_END);
			jTextFieldStatSoundIIFrequencyEnd.setText("" + jSliderStatSoundIIFrequencyEnd.getValue());
			jSliderStatSoundIIPiston.setValue(DEFAULT_STATSOUND_II_PISTON);
			jTextFieldStatSoundIIPiston.setText("" + jSliderStatSoundIIPiston.getValue());
			jRadioButtonStatSoundIIMic2.setSelected(true);
			jRadioButtonStatSoundIIMic3.setSelected(false);
			jRadioButtonStatSoundIIMic4.setSelected(false);
			jCheckBoxStatSoundIIHeat.setSelected(false);
			break;
		default:
			return;
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		soundVelocityMicGroup = new javax.swing.ButtonGroup();
		statSoundIMicGroup = new javax.swing.ButtonGroup();
		statSoundIIMicGroup = new javax.swing.ButtonGroup();
		jPanelSend = new javax.swing.JPanel();
		jButtonOK = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jButtonDefaults = new javax.swing.JButton();
		jTabbedPaneStatSound = new javax.swing.JTabbedPane();
		jPanelSoundSpeed = new javax.swing.JPanel();
		jPanelSoundVelocityMics = new javax.swing.JPanel();
		jLabelSoundVelocityMic1 = new javax.swing.JLabel();
		jLabelSoundVelocityMic2 = new javax.swing.JLabel();
		jLabelSoundVelocityMic3 = new javax.swing.JLabel();
		jLabelSoundVelocityMic4 = new javax.swing.JLabel();
		jLabelSoundVelocityCH1 = new javax.swing.JLabel();
		jLabelSoundVelocityCH2 = new javax.swing.JLabel();
		jRadioButtonSoundVelocityMic1 = new javax.swing.JRadioButton();
		jRadioButtonSoundVelocityMic2 = new javax.swing.JRadioButton();
		jRadioButtonSoundVelocityMic3 = new javax.swing.JRadioButton();
		jRadioButtonSoundVelocityMic4 = new javax.swing.JRadioButton();
		jCheckBoxSoundVelocityHeat = new javax.swing.JCheckBox();
		jPanelSoundVelocityNSamples = new javax.swing.JPanel();
		jTextFieldSoundVelocityNSamples = new javax.swing.JTextField();
		jLabelSoundVelocityNSamplesAlert = new javax.swing.JLabel();
		jSliderSoundVelocityNSamples = new javax.swing.JSlider();
		jPanelSoundVelocityPiston = new javax.swing.JPanel();
		jSliderSoundVelocityPiston = new javax.swing.JSlider();
		jTextFieldSoundVelocityPiston = new javax.swing.JTextField();
		jPanelSoundVelocityWaveForm = new javax.swing.JPanel();
		jLabelSoundVelocityWaveForm = new javax.swing.JLabel();
		jComboBoxSoundVelocityWaveForm = new javax.swing.JComboBox();
		jPanelSoundVelocityFrequency = new javax.swing.JPanel();
		jSliderSoundVelocityFrequency = new javax.swing.JSlider();
		jTextFieldSoundVelocityFrequency = new javax.swing.JTextField();
		jPanelStatSoundI = new javax.swing.JPanel();
		jPanelStatSoundIMics = new javax.swing.JPanel();
		jLabelStatSoundIMic1 = new javax.swing.JLabel();
		jLabelStatSoundIMic2 = new javax.swing.JLabel();
		jLabelStatSoundIMic3 = new javax.swing.JLabel();
		jLabelStatSoundIMic4 = new javax.swing.JLabel();
		jLabelStatSoundICH1 = new javax.swing.JLabel();
		jLabelStatSoundICH2 = new javax.swing.JLabel();
		jRadioButtonStatSoundIMic1 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIMic2 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIMic3 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIMic4 = new javax.swing.JRadioButton();
		jCheckBoxStatSoundIHeat = new javax.swing.JCheckBox();
		jPanelStatSoundINSamples = new javax.swing.JPanel();
		jTextFieldStatSoundINSamples = new javax.swing.JTextField();
		jLabelStatSoundINSamplesAlert = new javax.swing.JLabel();
		jSliderStatSoundINSamples = new javax.swing.JSlider();
		jPanelStatSoundPistonIInitial = new javax.swing.JPanel();
		jSliderStatSoundIPistonInitial = new javax.swing.JSlider();
		jTextFieldStatSoundIPistonInitial = new javax.swing.JTextField();
		jPanelStatSoundIPistonEnd = new javax.swing.JPanel();
		jSliderStatSoundIPistonEnd = new javax.swing.JSlider();
		jTextFieldStatSoundIPistonEnd = new javax.swing.JTextField();
		jPanelStatSoundIFrequency = new javax.swing.JPanel();
		jSliderStatSoundIFrequency = new javax.swing.JSlider();
		jTextFieldStatSoundIFrequency = new javax.swing.JTextField();
		jPanelStatSoundII = new javax.swing.JPanel();
		jPanelStatSoundIIMics = new javax.swing.JPanel();
		jLabelStatSoundIIMic1 = new javax.swing.JLabel();
		jLabelStatSoundIIMic2 = new javax.swing.JLabel();
		jLabelStatSoundIIMic3 = new javax.swing.JLabel();
		jLabelStatSoundIIMic4 = new javax.swing.JLabel();
		jLabelStatSoundIICH1 = new javax.swing.JLabel();
		jLabelStatSoundIICH2 = new javax.swing.JLabel();
		jRadioButtonStatSoundIIMic1 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIIMic2 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIIMic3 = new javax.swing.JRadioButton();
		jRadioButtonStatSoundIIMic4 = new javax.swing.JRadioButton();
		jCheckBoxStatSoundIIHeat = new javax.swing.JCheckBox();
		jPanelStatSoundIINSamples = new javax.swing.JPanel();
		jTextFieldStatSoundIINSamples = new javax.swing.JTextField();
		jLabelStatSoundIINSamplesAlert = new javax.swing.JLabel();
		jSliderStatSoundIINSamples = new javax.swing.JSlider();
		jPanelStatSoundIIPiston = new javax.swing.JPanel();
		jSliderStatSoundIIPiston = new javax.swing.JSlider();
		jTextFieldStatSoundIIPiston = new javax.swing.JTextField();
		jPanelStatSoundIIFrequencyInitial = new javax.swing.JPanel();
		jSliderStatSoundIIFrequencyInitial = new javax.swing.JSlider();
		jTextFieldStatSoundIIFrequencyInitial = new javax.swing.JTextField();
		jPanelStatSoundIIFrequencyEnd = new javax.swing.JPanel();
		jSliderStatSoundIIFrequencyEnd = new javax.swing.JSlider();
		jTextFieldStatSoundIIFrequencyEnd = new javax.swing.JTextField();

		setName("Form"); // NOI18N

		jPanelSend.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanelSend.setName("jPanelSend"); // NOI18N
		jPanelSend.setPreferredSize(new java.awt.Dimension(350, 42));
		jPanelSend.setLayout(new java.awt.GridBagLayout());

		jButtonOK.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.ok")); // NOI18N
		jButtonOK.setName("jButtonOK"); // NOI18N
		jButtonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonOKActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanelSend.add(jButtonOK, gridBagConstraints);

		jButtonCancel.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.cancel")); // NOI18N
		jButtonCancel.setName("jButtonCancel"); // NOI18N
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCancelActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		jPanelSend.add(jButtonCancel, gridBagConstraints);

		jLabel1.setName("jLabel1"); // NOI18N
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 10.0;
		jPanelSend.add(jLabel1, gridBagConstraints);

		jButtonDefaults.setText(ReCResourceBundle.findString("statsound$rec.exp.dftcfg.statsound.title.1")); // NOI18N
		jButtonDefaults.setName("jButtonDefaults"); // NOI18N
		jButtonDefaults.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonDefaultsActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		jPanelSend.add(jButtonDefaults, gridBagConstraints);

		jTabbedPaneStatSound.setName("jTabbedPaneStatSound"); // NOI18N

		jPanelSoundSpeed.setName("jPanelSoundSpeed"); // NOI18N
		jPanelSoundSpeed.setPreferredSize(new java.awt.Dimension(1000, 637));

		jPanelSoundVelocityMics.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.channels"))); // NOI18N
		jPanelSoundVelocityMics.setName("jPanelSoundVelocityMics"); // NOI18N
		jPanelSoundVelocityMics.setPreferredSize(new java.awt.Dimension(408, 95));

		jLabelSoundVelocityMic1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityMic1.text")); // NOI18N
		jLabelSoundVelocityMic1.setName("jLabelSoundVelocityMic1"); // NOI18N

		jLabelSoundVelocityMic2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityMic2.text")); // NOI18N
		jLabelSoundVelocityMic2.setName("jLabelSoundVelocityMic2"); // NOI18N

		jLabelSoundVelocityMic3.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityMic3.text")); // NOI18N
		jLabelSoundVelocityMic3.setName("jLabelSoundVelocityMic3"); // NOI18N

		jLabelSoundVelocityMic4.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityMic4.text")); // NOI18N
		jLabelSoundVelocityMic4.setName("jLabelSoundVelocityMic4"); // NOI18N

		jLabelSoundVelocityCH1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityCH1.text")); // NOI18N
		jLabelSoundVelocityCH1.setName("jLabelSoundVelocityCH1"); // NOI18N

		jLabelSoundVelocityCH2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelSoundVelocityCH2.text")); // NOI18N
		jLabelSoundVelocityCH2.setName("jLabelSoundVelocityCH2"); // NOI18N

		jRadioButtonSoundVelocityMic1.setSelected(true);
		jRadioButtonSoundVelocityMic1.setEnabled(false);
		jRadioButtonSoundVelocityMic1.setName("jRadioButtonSoundVelocityMic1"); // NOI18N

		soundVelocityMicGroup.add(jRadioButtonSoundVelocityMic2);
		jRadioButtonSoundVelocityMic2.setSelected(true);
		jRadioButtonSoundVelocityMic2.setName("jRadioButtonSoundVelocityMic2"); // NOI18N

		soundVelocityMicGroup.add(jRadioButtonSoundVelocityMic3);
		jRadioButtonSoundVelocityMic3.setName("jRadioButtonSoundVelocityMic3"); // NOI18N

		soundVelocityMicGroup.add(jRadioButtonSoundVelocityMic4);
		jRadioButtonSoundVelocityMic4.setName("jRadioButtonSoundVelocityMic4"); // NOI18N

		jCheckBoxSoundVelocityHeat.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.heating")); // NOI18N
		jCheckBoxSoundVelocityHeat.setName("jCheckBoxSoundVelocityHeat"); // NOI18N

		javax.swing.GroupLayout jPanelSoundVelocityMicsLayout = new javax.swing.GroupLayout(jPanelSoundVelocityMics);
		jPanelSoundVelocityMics.setLayout(jPanelSoundVelocityMicsLayout);
		jPanelSoundVelocityMicsLayout.setHorizontalGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityMicsLayout
						.createSequentialGroup()
						.addContainerGap(15, Short.MAX_VALUE)
						.addGroup(
								jPanelSoundVelocityMicsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelSoundVelocityMicsLayout.createSequentialGroup()
														.addGap(44, 44, 44).addComponent(jLabelSoundVelocityMic1)
														.addGap(59, 59, 59).addComponent(jLabelSoundVelocityMic2)
														.addGap(15, 15, 15).addComponent(jLabelSoundVelocityMic3)
														.addGap(15, 15, 15).addComponent(jLabelSoundVelocityMic4))
										.addGroup(
												jPanelSoundVelocityMicsLayout.createSequentialGroup()
														.addComponent(jLabelSoundVelocityCH1).addGap(20, 20, 20)
														.addComponent(jRadioButtonSoundVelocityMic1).addGap(24, 24, 24)
														.addComponent(jLabelSoundVelocityCH2).addGap(18, 18, 18)
														.addComponent(jRadioButtonSoundVelocityMic2).addGap(27, 27, 27)
														.addComponent(jRadioButtonSoundVelocityMic3).addGap(27, 27, 27)
														.addComponent(jRadioButtonSoundVelocityMic4).addGap(18, 18, 18)
														.addComponent(jCheckBoxSoundVelocityHeat)))));
		jPanelSoundVelocityMicsLayout.setVerticalGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelSoundVelocityMicsLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addGroup(
										jPanelSoundVelocityMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelSoundVelocityMic1,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelSoundVelocityMic2,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelSoundVelocityMic3,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelSoundVelocityMic4,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(2, 2, 2)
								.addGroup(
										jPanelSoundVelocityMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelSoundVelocityCH1)
												.addComponent(jRadioButtonSoundVelocityMic1)
												.addComponent(jLabelSoundVelocityCH2)
												.addComponent(jRadioButtonSoundVelocityMic2)
												.addComponent(jRadioButtonSoundVelocityMic3)
												.addGroup(
														jPanelSoundVelocityMicsLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(jCheckBoxSoundVelocityHeat)
																.addComponent(jRadioButtonSoundVelocityMic4)))
								.addContainerGap(16, Short.MAX_VALUE)));

		jPanelSoundVelocityNSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.nsamples"))); // NOI18N
		jPanelSoundVelocityNSamples.setName("jPanelSoundVelocityNSamples"); // NOI18N

		jTextFieldSoundVelocityNSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldSoundVelocityNSamples.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldSoundVelocityNSamples.text")); // NOI18N
		jTextFieldSoundVelocityNSamples.setName("jTextFieldSoundVelocityNSamples"); // NOI18N
		jTextFieldSoundVelocityNSamples.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldSoundVelocityNSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldSoundVelocityNSamplesFocusLost(evt);
			}
		});

		jLabelSoundVelocityNSamplesAlert.setForeground(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getColor("jLabelSoundVelocityNSamplesAlert.foreground")); // NOI18N
		jLabelSoundVelocityNSamplesAlert.setText(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
		jLabelSoundVelocityNSamplesAlert.setEnabled(false);
		jLabelSoundVelocityNSamplesAlert.setName("jLabelSoundVelocityNSamplesAlert"); // NOI18N

		jSliderSoundVelocityNSamples.setMajorTickSpacing(500);
		jSliderSoundVelocityNSamples.setMaximum(5000);
		jSliderSoundVelocityNSamples.setMinimum(500);
		jSliderSoundVelocityNSamples.setMinorTickSpacing(500);
		jSliderSoundVelocityNSamples.setPaintLabels(true);
		jSliderSoundVelocityNSamples.setPaintTicks(true);
		jSliderSoundVelocityNSamples.setName("jSliderSoundVelocityNSamples"); // NOI18N
		jSliderSoundVelocityNSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderSoundVelocityNSamplesStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jPanelSoundVelocityNSamplesLayout = new javax.swing.GroupLayout(
				jPanelSoundVelocityNSamples);
		jPanelSoundVelocityNSamples.setLayout(jPanelSoundVelocityNSamplesLayout);
		jPanelSoundVelocityNSamplesLayout.setHorizontalGroup(jPanelSoundVelocityNSamplesLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelSoundVelocityNSamplesLayout
								.createSequentialGroup()
								.addComponent(jSliderSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE,
										350, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextFieldSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						jPanelSoundVelocityNSamplesLayout.createSequentialGroup().addGap(87, 87, 87)
								.addComponent(jLabelSoundVelocityNSamplesAlert)));
		jPanelSoundVelocityNSamplesLayout.setVerticalGroup(jPanelSoundVelocityNSamplesLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityNSamplesLayout
						.createSequentialGroup()
						.addGroup(
								jPanelSoundVelocityNSamplesLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSliderSoundVelocityNSamples,
												javax.swing.GroupLayout.PREFERRED_SIZE, 55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanelSoundVelocityNSamplesLayout
														.createSequentialGroup()
														.addGap(6, 6, 6)
														.addComponent(jTextFieldSoundVelocityNSamples,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addComponent(jLabelSoundVelocityNSamplesAlert)));

		jPanelSoundVelocityPiston.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.piston"))); // NOI18N
		jPanelSoundVelocityPiston.setForeground(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getColor("jPanelSoundVelocityPiston.foreground")); // NOI18N
		jPanelSoundVelocityPiston.setName("jPanelSoundVelocityPiston"); // NOI18N

		jSliderSoundVelocityPiston.setMajorTickSpacing(40);
		jSliderSoundVelocityPiston.setMaximum(MAX_PISTON_POSITION);
		jSliderSoundVelocityPiston.setMinimum(MIN_PISTON_POSITION);
		jSliderSoundVelocityPiston.setMinorTickSpacing(20);
		jSliderSoundVelocityPiston.setPaintLabels(true);
		jSliderSoundVelocityPiston.setPaintTicks(true);
		jSliderSoundVelocityPiston.setValue(1300);
		jSliderSoundVelocityPiston.setName("jSliderSoundVelocityPiston"); // NOI18N
		jSliderSoundVelocityPiston.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderSoundVelocityPistonStateChanged(evt);
			}
		});

		jTextFieldSoundVelocityPiston.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldSoundVelocityPiston.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldSoundVelocityPiston.text")); // NOI18N
		jTextFieldSoundVelocityPiston.setName("jTextFieldSoundVelocityPiston"); // NOI18N
		jTextFieldSoundVelocityPiston.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldSoundVelocityPiston.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldSoundVelocityPistonFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelSoundVelocityPistonLayout = new javax.swing.GroupLayout(jPanelSoundVelocityPiston);
		jPanelSoundVelocityPiston.setLayout(jPanelSoundVelocityPistonLayout);
		jPanelSoundVelocityPistonLayout.setHorizontalGroup(jPanelSoundVelocityPistonLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityPistonLayout
						.createSequentialGroup()
						.addComponent(jSliderSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelSoundVelocityPistonLayout.setVerticalGroup(jPanelSoundVelocityPistonLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelSoundVelocityPistonLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelSoundVelocityWaveForm.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.waveform"))); // NOI18N
		jPanelSoundVelocityWaveForm.setName("jPanelSoundVelocityWaveForm"); // NOI18N
		jPanelSoundVelocityWaveForm.setPreferredSize(new java.awt.Dimension(408, 200));

		jLabelSoundVelocityWaveForm.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.waveform")); // NOI18N
		jLabelSoundVelocityWaveForm.setName("jLabelSoundVelocityWaveForm"); // NOI18N

		jComboBoxSoundVelocityWaveForm.setName("jComboBoxSoundVelocityWaveForm"); // NOI18N
		jComboBoxSoundVelocityWaveForm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBoxSoundVelocityWaveFormActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanelSoundVelocityWaveFormLayout = new javax.swing.GroupLayout(
				jPanelSoundVelocityWaveForm);
		jPanelSoundVelocityWaveForm.setLayout(jPanelSoundVelocityWaveFormLayout);
		jPanelSoundVelocityWaveFormLayout.setHorizontalGroup(jPanelSoundVelocityWaveFormLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityWaveFormLayout
						.createSequentialGroup()
						.addGap(15, 15, 15)
						.addComponent(jLabelSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jComboBoxSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
								javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelSoundVelocityWaveFormLayout.setVerticalGroup(jPanelSoundVelocityWaveFormLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityWaveFormLayout
						.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(
								jPanelSoundVelocityWaveFormLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabelSoundVelocityWaveForm,
												javax.swing.GroupLayout.PREFERRED_SIZE, 20,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jComboBoxSoundVelocityWaveForm,
												javax.swing.GroupLayout.PREFERRED_SIZE, 20,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(25, Short.MAX_VALUE)));

		jPanelSoundVelocityFrequency.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.frequency"))); // NOI18N
		jPanelSoundVelocityFrequency.setName("jPanelSoundVelocityFrequency"); // NOI18N

		jSliderSoundVelocityFrequency.setMajorTickSpacing(200);
		jSliderSoundVelocityFrequency.setMaximum(2000);
		jSliderSoundVelocityFrequency.setMinimum(100);
		jSliderSoundVelocityFrequency.setMinorTickSpacing(100);
		jSliderSoundVelocityFrequency.setPaintLabels(true);
		jSliderSoundVelocityFrequency.setPaintTicks(true);
		jSliderSoundVelocityFrequency.setValue(250);
		jSliderSoundVelocityFrequency.setName("jSliderSoundVelocityFrequency"); // NOI18N
		jSliderSoundVelocityFrequency.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderSoundVelocityFrequencyStateChanged(evt);
			}
		});

		jTextFieldSoundVelocityFrequency.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldSoundVelocityFrequency.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldSoundVelocityFrequency.text")); // NOI18N
		jTextFieldSoundVelocityFrequency.setName("jTextFieldSoundVelocityFrequency"); // NOI18N
		jTextFieldSoundVelocityFrequency.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldSoundVelocityFrequency.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldSoundVelocityFrequencyFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelSoundVelocityFrequencyLayout = new javax.swing.GroupLayout(
				jPanelSoundVelocityFrequency);
		jPanelSoundVelocityFrequency.setLayout(jPanelSoundVelocityFrequencyLayout);
		jPanelSoundVelocityFrequencyLayout.setHorizontalGroup(jPanelSoundVelocityFrequencyLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundVelocityFrequencyLayout
						.createSequentialGroup()
						.addComponent(jSliderSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelSoundVelocityFrequencyLayout.setVerticalGroup(jPanelSoundVelocityFrequencyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelSoundVelocityFrequencyLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout jPanelSoundSpeedLayout = new javax.swing.GroupLayout(jPanelSoundSpeed);
		jPanelSoundSpeed.setLayout(jPanelSoundSpeedLayout);
		jPanelSoundSpeedLayout.setHorizontalGroup(jPanelSoundSpeedLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundSpeedLayout
						.createSequentialGroup()
						.addGap(12, 12, 12)
						.addGroup(
								jPanelSoundSpeedLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelSoundSpeedLayout
														.createSequentialGroup()
														.addComponent(jPanelSoundVelocityMics,
																javax.swing.GroupLayout.PREFERRED_SIZE, 410,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(10, 10, 10)
														.addComponent(jPanelSoundVelocityNSamples,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanelSoundSpeedLayout
														.createSequentialGroup()
														.addComponent(jPanelSoundVelocityPiston,
																javax.swing.GroupLayout.PREFERRED_SIZE, 410,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(10, 10, 10)
														.addComponent(jPanelSoundVelocityWaveForm,
																javax.swing.GroupLayout.PREFERRED_SIZE, 400,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(jPanelSoundVelocityFrequency,
												javax.swing.GroupLayout.PREFERRED_SIZE, 410,
												javax.swing.GroupLayout.PREFERRED_SIZE))));
		jPanelSoundSpeedLayout.setVerticalGroup(jPanelSoundSpeedLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelSoundSpeedLayout
						.createSequentialGroup()
						.addGap(12, 12, 12)
						.addGroup(
								jPanelSoundSpeedLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanelSoundVelocityMics, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jPanelSoundVelocityNSamples,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(6, 6, 6)
						.addGroup(
								jPanelSoundSpeedLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanelSoundVelocityPiston,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jPanelSoundVelocityWaveForm,
												javax.swing.GroupLayout.PREFERRED_SIZE, 80,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(6, 6, 6)
						.addComponent(jPanelSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		jTabbedPaneStatSound.addTab(
				org.jdesktop.application.Application.getInstance().getContext()
						.getResourceMap(StatSoundCustomizer.class)
						.getString("jPanelSoundSpeed.TabConstraints.tabTitle"), jPanelSoundSpeed); // NOI18N

		jPanelStatSoundI.setName("jPanelStatSoundI"); // NOI18N
		jPanelStatSoundI.setLayout(null);

		jPanelStatSoundIMics.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.channels"))); // NOI18N
		jPanelStatSoundIMics.setName("jPanelStatSoundIMics"); // NOI18N

		jLabelStatSoundIMic1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIMic1.text")); // NOI18N
		jLabelStatSoundIMic1.setName("jLabelStatSoundIMic1"); // NOI18N

		jLabelStatSoundIMic2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIMic2.text")); // NOI18N
		jLabelStatSoundIMic2.setName("jLabelStatSoundIMic2"); // NOI18N

		jLabelStatSoundIMic3.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIMic3.text")); // NOI18N
		jLabelStatSoundIMic3.setName("jLabelStatSoundIMic3"); // NOI18N

		jLabelStatSoundIMic4.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIMic4.text")); // NOI18N
		jLabelStatSoundIMic4.setName("jLabelStatSoundIMic4"); // NOI18N

		jLabelStatSoundICH1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundICH1.text")); // NOI18N
		jLabelStatSoundICH1.setName("jLabelStatSoundICH1"); // NOI18N

		jLabelStatSoundICH2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundICH2.text")); // NOI18N
		jLabelStatSoundICH2.setName("jLabelStatSoundICH2"); // NOI18N

		jRadioButtonStatSoundIMic1.setSelected(true);
		jRadioButtonStatSoundIMic1.setEnabled(false);
		jRadioButtonStatSoundIMic1.setName("jRadioButtonStatSoundIMic1"); // NOI18N

		statSoundIMicGroup.add(jRadioButtonStatSoundIMic2);
		jRadioButtonStatSoundIMic2.setSelected(true);
		jRadioButtonStatSoundIMic2.setName("jRadioButtonStatSoundIMic2"); // NOI18N

		statSoundIMicGroup.add(jRadioButtonStatSoundIMic3);
		jRadioButtonStatSoundIMic3.setName("jRadioButtonStatSoundIMic3"); // NOI18N

		statSoundIMicGroup.add(jRadioButtonStatSoundIMic4);
		jRadioButtonStatSoundIMic4.setName("jRadioButtonStatSoundIMic4"); // NOI18N

		jCheckBoxStatSoundIHeat.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.heating")); // NOI18N
		jCheckBoxStatSoundIHeat.setName("jCheckBoxStatSoundIHeat"); // NOI18N

		javax.swing.GroupLayout jPanelStatSoundIMicsLayout = new javax.swing.GroupLayout(jPanelStatSoundIMics);
		jPanelStatSoundIMics.setLayout(jPanelStatSoundIMicsLayout);
		jPanelStatSoundIMicsLayout.setHorizontalGroup(jPanelStatSoundIMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIMicsLayout
						.createSequentialGroup()
						.addContainerGap(15, Short.MAX_VALUE)
						.addGroup(
								jPanelStatSoundIMicsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelStatSoundIMicsLayout.createSequentialGroup().addGap(44, 44, 44)
														.addComponent(jLabelStatSoundIMic1).addGap(59, 59, 59)
														.addComponent(jLabelStatSoundIMic2).addGap(15, 15, 15)
														.addComponent(jLabelStatSoundIMic3).addGap(15, 15, 15)
														.addComponent(jLabelStatSoundIMic4))
										.addGroup(
												jPanelStatSoundIMicsLayout.createSequentialGroup()
														.addComponent(jLabelStatSoundICH1).addGap(20, 20, 20)
														.addComponent(jRadioButtonStatSoundIMic1).addGap(24, 24, 24)
														.addComponent(jLabelStatSoundICH2).addGap(18, 18, 18)
														.addComponent(jRadioButtonStatSoundIMic2).addGap(27, 27, 27)
														.addComponent(jRadioButtonStatSoundIMic3).addGap(27, 27, 27)
														.addComponent(jRadioButtonStatSoundIMic4).addGap(18, 18, 18)
														.addComponent(jCheckBoxStatSoundIHeat)))));
		jPanelStatSoundIMicsLayout.setVerticalGroup(jPanelStatSoundIMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelStatSoundIMicsLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addGroup(
										jPanelStatSoundIMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelStatSoundIMic1,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIMic2,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIMic3,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIMic4,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(2, 2, 2)
								.addGroup(
										jPanelStatSoundIMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelStatSoundICH1)
												.addComponent(jRadioButtonStatSoundIMic1)
												.addComponent(jLabelStatSoundICH2)
												.addComponent(jRadioButtonStatSoundIMic2)
												.addComponent(jRadioButtonStatSoundIMic3)
												.addGroup(
														jPanelStatSoundIMicsLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(jCheckBoxStatSoundIHeat)
																.addComponent(jRadioButtonStatSoundIMic4)))
								.addContainerGap(16, Short.MAX_VALUE)));

		jPanelStatSoundI.add(jPanelStatSoundIMics);
		jPanelStatSoundIMics.setBounds(12, 12, 410, 95);

		jPanelStatSoundINSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.nsamples"))); // NOI18N
		jPanelStatSoundINSamples.setName("jPanelStatSoundINSamples"); // NOI18N

		jTextFieldStatSoundINSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundINSamples.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundINSamples.text")); // NOI18N
		jTextFieldStatSoundINSamples.setName("jTextFieldStatSoundINSamples"); // NOI18N
		jTextFieldStatSoundINSamples.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundINSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundINSamplesFocusLost(evt);
			}
		});

		jLabelStatSoundINSamplesAlert.setForeground(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getColor("jLabelStatSoundINSamplesAlert.foreground")); // NOI18N
		jLabelStatSoundINSamplesAlert
				.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
		jLabelStatSoundINSamplesAlert.setEnabled(false);
		jLabelStatSoundINSamplesAlert.setName("jLabelStatSoundINSamplesAlert"); // NOI18N

		jSliderStatSoundINSamples.setMajorTickSpacing(40);
		jSliderStatSoundINSamples.setMaximum(130);
		jSliderStatSoundINSamples.setMinimum(1);
		jSliderStatSoundINSamples.setMinorTickSpacing(20);
		jSliderStatSoundINSamples.setPaintLabels(true);
		jSliderStatSoundINSamples.setPaintTicks(true);
		jSliderStatSoundINSamples.setName("jSliderStatSoundINSamples"); // NOI18N
		jSliderStatSoundINSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundINSamplesStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundINSamplesLayout = new javax.swing.GroupLayout(jPanelStatSoundINSamples);
		jPanelStatSoundINSamples.setLayout(jPanelStatSoundINSamplesLayout);
		jPanelStatSoundINSamplesLayout.setHorizontalGroup(jPanelStatSoundINSamplesLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelStatSoundINSamplesLayout
								.createSequentialGroup()
								.addComponent(jSliderStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextFieldStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						jPanelStatSoundINSamplesLayout.createSequentialGroup().addGap(87, 87, 87)
								.addComponent(jLabelStatSoundINSamplesAlert)));
		jPanelStatSoundINSamplesLayout.setVerticalGroup(jPanelStatSoundINSamplesLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundINSamplesLayout
						.createSequentialGroup()
						.addGroup(
								jPanelStatSoundINSamplesLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSliderStatSoundINSamples,
												javax.swing.GroupLayout.PREFERRED_SIZE, 55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanelStatSoundINSamplesLayout
														.createSequentialGroup()
														.addGap(6, 6, 6)
														.addComponent(jTextFieldStatSoundINSamples,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addComponent(jLabelStatSoundINSamplesAlert)));

		jPanelStatSoundI.add(jPanelStatSoundINSamples);
		jPanelStatSoundINSamples.setBounds(432, 12, 400, 95);

		jPanelStatSoundPistonIInitial.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.pistonstart"))); // NOI18N
		jPanelStatSoundPistonIInitial.setName("jPanelStatSoundPistonIInitial"); // NOI18N

		jSliderStatSoundIPistonInitial.setMajorTickSpacing(40);
		jSliderStatSoundIPistonInitial.setMaximum(MAX_PISTON_POSITION);
		jSliderStatSoundIPistonInitial.setMinimum(MIN_PISTON_POSITION);
		jSliderStatSoundIPistonInitial.setMinorTickSpacing(20);
		jSliderStatSoundIPistonInitial.setPaintLabels(true);
		jSliderStatSoundIPistonInitial.setPaintTicks(true);
		jSliderStatSoundIPistonInitial.setValue(1300);
		jSliderStatSoundIPistonInitial.setName("jSliderStatSoundIPistonInitial"); // NOI18N
		jSliderStatSoundIPistonInitial.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIPistonInitialStateChanged(evt);
			}
		});

		jTextFieldStatSoundIPistonInitial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIPistonInitial.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIPistonInitial.text")); // NOI18N
		jTextFieldStatSoundIPistonInitial.setName("jTextFieldStatSoundIPistonInitial"); // NOI18N
		jTextFieldStatSoundIPistonInitial.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIPistonInitial.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIPistonInitialFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundPistonIInitialLayout = new javax.swing.GroupLayout(
				jPanelStatSoundPistonIInitial);
		jPanelStatSoundPistonIInitial.setLayout(jPanelStatSoundPistonIInitialLayout);
		jPanelStatSoundPistonIInitialLayout.setHorizontalGroup(jPanelStatSoundPistonIInitialLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundPistonIInitialLayout
						.createSequentialGroup()
						.addComponent(jSliderStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundPistonIInitialLayout.setVerticalGroup(jPanelStatSoundPistonIInitialLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundPistonIInitialLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIPistonInitial,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundI.add(jPanelStatSoundPistonIInitial);
		jPanelStatSoundPistonIInitial.setBounds(12, 113, 410, 80);

		jPanelStatSoundIPistonEnd.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.pistonend"))); // NOI18N
		jPanelStatSoundIPistonEnd.setName("jPanelStatSoundIPistonEnd"); // NOI18N

		jSliderStatSoundIPistonEnd.setMajorTickSpacing(40);
		jSliderStatSoundIPistonEnd.setMaximum(MAX_PISTON_POSITION);
		jSliderStatSoundIPistonEnd.setMinimum(MIN_PISTON_POSITION);
		jSliderStatSoundIPistonEnd.setMinorTickSpacing(20);
		jSliderStatSoundIPistonEnd.setPaintLabels(true);
		jSliderStatSoundIPistonEnd.setPaintTicks(true);
		jSliderStatSoundIPistonEnd.setValue(1400);
		jSliderStatSoundIPistonEnd.setName("jSliderStatSoundIPistonEnd"); // NOI18N
		jSliderStatSoundIPistonEnd.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIPistonEndStateChanged(evt);
			}
		});

		jTextFieldStatSoundIPistonEnd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIPistonEnd.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIPistonEnd.text")); // NOI18N
		jTextFieldStatSoundIPistonEnd.setName("jTextFieldStatSoundIPistonEnd"); // NOI18N
		jTextFieldStatSoundIPistonEnd.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIPistonEnd.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIPistonEndFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIPistonEndLayout = new javax.swing.GroupLayout(jPanelStatSoundIPistonEnd);
		jPanelStatSoundIPistonEnd.setLayout(jPanelStatSoundIPistonEndLayout);
		jPanelStatSoundIPistonEndLayout.setHorizontalGroup(jPanelStatSoundIPistonEndLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIPistonEndLayout
						.createSequentialGroup()
						.addComponent(jSliderStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundIPistonEndLayout.setVerticalGroup(jPanelStatSoundIPistonEndLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundIPistonEndLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundI.add(jPanelStatSoundIPistonEnd);
		jPanelStatSoundIPistonEnd.setBounds(432, 113, 400, 80);

		jPanelStatSoundIFrequency.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.frequency"))); // NOI18N
		jPanelStatSoundIFrequency.setName("jPanelStatSoundIFrequency"); // NOI18N

		jSliderStatSoundIFrequency.setMajorTickSpacing(200);
		jSliderStatSoundIFrequency.setMaximum(2000);
		jSliderStatSoundIFrequency.setMinimum(100);
		jSliderStatSoundIFrequency.setMinorTickSpacing(100);
		jSliderStatSoundIFrequency.setPaintLabels(true);
		jSliderStatSoundIFrequency.setPaintTicks(true);
		jSliderStatSoundIFrequency.setValue(250);
		jSliderStatSoundIFrequency.setName("jSliderStatSoundIFrequency"); // NOI18N
		jSliderStatSoundIFrequency.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIFrequencyStateChanged(evt);
			}
		});

		jTextFieldStatSoundIFrequency.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIFrequency.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIFrequency.text")); // NOI18N
		jTextFieldStatSoundIFrequency.setName("jTextFieldStatSoundIFrequency"); // NOI18N
		jTextFieldStatSoundIFrequency.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIFrequency.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIFrequencyFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIFrequencyLayout = new javax.swing.GroupLayout(jPanelStatSoundIFrequency);
		jPanelStatSoundIFrequency.setLayout(jPanelStatSoundIFrequencyLayout);
		jPanelStatSoundIFrequencyLayout.setHorizontalGroup(jPanelStatSoundIFrequencyLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIFrequencyLayout
						.createSequentialGroup()
						.addComponent(jSliderStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundIFrequencyLayout.setVerticalGroup(jPanelStatSoundIFrequencyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundIFrequencyLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundI.add(jPanelStatSoundIFrequency);
		jPanelStatSoundIFrequency.setBounds(12, 199, 410, 80);

		jTabbedPaneStatSound.addTab(
				org.jdesktop.application.Application.getInstance().getContext()
						.getResourceMap(StatSoundCustomizer.class)
						.getString("jPanelStatSoundI.TabConstraints.tabTitle"), jPanelStatSoundI); // NOI18N

		jPanelStatSoundII.setName("jPanelStatSoundII"); // NOI18N
		jPanelStatSoundII.setLayout(null);

		jPanelStatSoundIIMics.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.channels"))); // NOI18N
		jPanelStatSoundIIMics.setName("jPanelStatSoundIIMics"); // NOI18N

		jLabelStatSoundIIMic1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIIMic1.text")); // NOI18N
		jLabelStatSoundIIMic1.setName("jLabelStatSoundIIMic1"); // NOI18N

		jLabelStatSoundIIMic2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIIMic2.text")); // NOI18N
		jLabelStatSoundIIMic2.setName("jLabelStatSoundIIMic2"); // NOI18N

		jLabelStatSoundIIMic3.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIIMic3.text")); // NOI18N
		jLabelStatSoundIIMic3.setName("jLabelStatSoundIIMic3"); // NOI18N

		jLabelStatSoundIIMic4.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIIMic4.text")); // NOI18N
		jLabelStatSoundIIMic4.setName("jLabelStatSoundIIMic4"); // NOI18N

		jLabelStatSoundIICH1.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIICH1.text")); // NOI18N
		jLabelStatSoundIICH1.setName("jLabelStatSoundIICH1"); // NOI18N

		jLabelStatSoundIICH2.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jLabelStatSoundIICH2.text")); // NOI18N
		jLabelStatSoundIICH2.setName("jLabelStatSoundIICH2"); // NOI18N

		jRadioButtonStatSoundIIMic1.setSelected(true);
		jRadioButtonStatSoundIIMic1.setEnabled(false);
		jRadioButtonStatSoundIIMic1.setName("jRadioButtonStatSoundIIMic1"); // NOI18N

		statSoundIIMicGroup.add(jRadioButtonStatSoundIIMic2);
		jRadioButtonStatSoundIIMic2.setSelected(true);
		jRadioButtonStatSoundIIMic2.setName("jRadioButtonStatSoundIIMic2"); // NOI18N

		statSoundIIMicGroup.add(jRadioButtonStatSoundIIMic3);
		jRadioButtonStatSoundIIMic3.setName("jRadioButtonStatSoundIIMic3"); // NOI18N

		statSoundIIMicGroup.add(jRadioButtonStatSoundIIMic4);
		jRadioButtonStatSoundIIMic4.setName("jRadioButtonStatSoundIIMic4"); // NOI18N

		jCheckBoxStatSoundIIHeat.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.heating")); // NOI18N
		jCheckBoxStatSoundIIHeat.setName("jCheckBoxStatSoundIIHeat"); // NOI18N

		javax.swing.GroupLayout jPanelStatSoundIIMicsLayout = new javax.swing.GroupLayout(jPanelStatSoundIIMics);
		jPanelStatSoundIIMics.setLayout(jPanelStatSoundIIMicsLayout);
		jPanelStatSoundIIMicsLayout.setHorizontalGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIIMicsLayout
						.createSequentialGroup()
						.addContainerGap(15, Short.MAX_VALUE)
						.addGroup(
								jPanelStatSoundIIMicsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelStatSoundIIMicsLayout.createSequentialGroup().addGap(44, 44, 44)
														.addComponent(jLabelStatSoundIIMic1).addGap(59, 59, 59)
														.addComponent(jLabelStatSoundIIMic2).addGap(15, 15, 15)
														.addComponent(jLabelStatSoundIIMic3).addGap(15, 15, 15)
														.addComponent(jLabelStatSoundIIMic4))
										.addGroup(
												jPanelStatSoundIIMicsLayout.createSequentialGroup()
														.addComponent(jLabelStatSoundIICH1).addGap(20, 20, 20)
														.addComponent(jRadioButtonStatSoundIIMic1).addGap(24, 24, 24)
														.addComponent(jLabelStatSoundIICH2).addGap(18, 18, 18)
														.addComponent(jRadioButtonStatSoundIIMic2).addGap(27, 27, 27)
														.addComponent(jRadioButtonStatSoundIIMic3).addGap(27, 27, 27)
														.addComponent(jRadioButtonStatSoundIIMic4).addGap(18, 18, 18)
														.addComponent(jCheckBoxStatSoundIIHeat)))));
		jPanelStatSoundIIMicsLayout.setVerticalGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelStatSoundIIMicsLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addGroup(
										jPanelStatSoundIIMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelStatSoundIIMic1,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIIMic2,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIIMic3,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabelStatSoundIIMic4,
														javax.swing.GroupLayout.PREFERRED_SIZE, 23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(2, 2, 2)
								.addGroup(
										jPanelStatSoundIIMicsLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelStatSoundIICH1)
												.addComponent(jRadioButtonStatSoundIIMic1)
												.addComponent(jLabelStatSoundIICH2)
												.addComponent(jRadioButtonStatSoundIIMic2)
												.addComponent(jRadioButtonStatSoundIIMic3)
												.addGroup(
														jPanelStatSoundIIMicsLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(jCheckBoxStatSoundIIHeat)
																.addComponent(jRadioButtonStatSoundIIMic4)))
								.addContainerGap(16, Short.MAX_VALUE)));

		jPanelStatSoundII.add(jPanelStatSoundIIMics);
		jPanelStatSoundIIMics.setBounds(12, 12, 410, 95);

		jPanelStatSoundIINSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.nsamples"))); // NOI18N
		jPanelStatSoundIINSamples.setName("jPanelStatSoundIINSamples"); // NOI18N

		jTextFieldStatSoundIINSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIINSamples.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIINSamples.text")); // NOI18N
		jTextFieldStatSoundIINSamples.setName("jTextFieldStatSoundIINSamples"); // NOI18N
		jTextFieldStatSoundIINSamples.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIINSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIINSamplesFocusLost(evt);
			}
		});

		jLabelStatSoundIINSamplesAlert.setForeground(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getColor("jLabelStatSoundIINSamplesAlert.foreground")); // NOI18N
		jLabelStatSoundIINSamplesAlert.setText(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
		jLabelStatSoundIINSamplesAlert.setEnabled(false);
		jLabelStatSoundIINSamplesAlert.setName("jLabelStatSoundIINSamplesAlert"); // NOI18N

		jSliderStatSoundIINSamples.setMajorTickSpacing(100);
		jSliderStatSoundIINSamples.setMaximum(600);
		jSliderStatSoundIINSamples.setMinimum(1);
		jSliderStatSoundIINSamples.setMinorTickSpacing(50);
		jSliderStatSoundIINSamples.setPaintLabels(true);
		jSliderStatSoundIINSamples.setPaintTicks(true);
		jSliderStatSoundIINSamples.setValue(400);
		jSliderStatSoundIINSamples.setName("jSliderStatSoundIINSamples"); // NOI18N
		jSliderStatSoundIINSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIINSamplesStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIINSamplesLayout = new javax.swing.GroupLayout(jPanelStatSoundIINSamples);
		jPanelStatSoundIINSamples.setLayout(jPanelStatSoundIINSamplesLayout);
		jPanelStatSoundIINSamplesLayout.setHorizontalGroup(jPanelStatSoundIINSamplesLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelStatSoundIINSamplesLayout
								.createSequentialGroup()
								.addComponent(jSliderStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextFieldStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						jPanelStatSoundIINSamplesLayout.createSequentialGroup().addGap(87, 87, 87)
								.addComponent(jLabelStatSoundIINSamplesAlert)));
		jPanelStatSoundIINSamplesLayout.setVerticalGroup(jPanelStatSoundIINSamplesLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIINSamplesLayout
						.createSequentialGroup()
						.addGroup(
								jPanelStatSoundIINSamplesLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSliderStatSoundIINSamples,
												javax.swing.GroupLayout.PREFERRED_SIZE, 55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanelStatSoundIINSamplesLayout
														.createSequentialGroup()
														.addGap(6, 6, 6)
														.addComponent(jTextFieldStatSoundIINSamples,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addComponent(jLabelStatSoundIINSamplesAlert)));

		jLabelStatSoundIINSamplesAlert.getAccessibleContext().setAccessibleName(
				org.jdesktop.application.Application.getInstance().getContext()
						.getResourceMap(StatSoundCustomizer.class)
						.getString("jLabelStatSoundIINSamplesAlert.AccessibleContext.accessibleName")); // NOI18N

		jPanelStatSoundII.add(jPanelStatSoundIINSamples);
		jPanelStatSoundIINSamples.setBounds(432, 12, 400, 95);

		jPanelStatSoundIIPiston.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.piston"))); // NOI18N
		jPanelStatSoundIIPiston.setName("jPanelStatSoundIIPiston"); // NOI18N

		jSliderStatSoundIIPiston.setMajorTickSpacing(40);
		jSliderStatSoundIIPiston.setMaximum(MAX_PISTON_POSITION);
		jSliderStatSoundIIPiston.setMinimum(MIN_PISTON_POSITION);
		jSliderStatSoundIIPiston.setMinorTickSpacing(20);
		jSliderStatSoundIIPiston.setPaintLabels(true);
		jSliderStatSoundIIPiston.setPaintTicks(true);
		jSliderStatSoundIIPiston.setValue(1300);
		jSliderStatSoundIIPiston.setName("jSliderStatSoundIIPiston"); // NOI18N
		jSliderStatSoundIIPiston.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIIPistonStateChanged(evt);
			}
		});

		jTextFieldStatSoundIIPiston.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIIPiston.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIIPiston.text")); // NOI18N
		jTextFieldStatSoundIIPiston.setName("jTextFieldStatSoundIIPiston"); // NOI18N
		jTextFieldStatSoundIIPiston.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIIPiston.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIIPistonFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIIPistonLayout = new javax.swing.GroupLayout(jPanelStatSoundIIPiston);
		jPanelStatSoundIIPiston.setLayout(jPanelStatSoundIIPistonLayout);
		jPanelStatSoundIIPistonLayout.setHorizontalGroup(jPanelStatSoundIIPistonLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIIPistonLayout
						.createSequentialGroup()
						.addComponent(jSliderStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundIIPistonLayout.setVerticalGroup(jPanelStatSoundIIPistonLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundIIPistonLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundII.add(jPanelStatSoundIIPiston);
		jPanelStatSoundIIPiston.setBounds(12, 113, 410, 80);

		jPanelStatSoundIIFrequencyInitial.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.freqstart"))); // NOI18N
		jPanelStatSoundIIFrequencyInitial.setName("jPanelStatSoundIIFrequencyInitial"); // NOI18N

		jSliderStatSoundIIFrequencyInitial.setMajorTickSpacing(200);
		jSliderStatSoundIIFrequencyInitial.setMaximum(2000);
		jSliderStatSoundIIFrequencyInitial.setMinimum(100);
		jSliderStatSoundIIFrequencyInitial.setMinorTickSpacing(100);
		jSliderStatSoundIIFrequencyInitial.setPaintLabels(true);
		jSliderStatSoundIIFrequencyInitial.setPaintTicks(true);
		jSliderStatSoundIIFrequencyInitial.setValue(250);
		jSliderStatSoundIIFrequencyInitial.setName("jSliderStatSoundIIFrequencyInitial"); // NOI18N
		jSliderStatSoundIIFrequencyInitial.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIIFrequencyInitialStateChanged(evt);
			}
		});

		jTextFieldStatSoundIIFrequencyInitial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIIFrequencyInitial.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIIFrequencyInitial.text")); // NOI18N
		jTextFieldStatSoundIIFrequencyInitial.setName("jTextFieldStatSoundIIFrequencyInitial"); // NOI18N
		jTextFieldStatSoundIIFrequencyInitial.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIIFrequencyInitial.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIIFrequencyInitialFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIIFrequencyInitialLayout = new javax.swing.GroupLayout(
				jPanelStatSoundIIFrequencyInitial);
		jPanelStatSoundIIFrequencyInitial.setLayout(jPanelStatSoundIIFrequencyInitialLayout);
		jPanelStatSoundIIFrequencyInitialLayout.setHorizontalGroup(jPanelStatSoundIIFrequencyInitialLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						jPanelStatSoundIIFrequencyInitialLayout
								.createSequentialGroup()
								.addComponent(jSliderStatSoundIIFrequencyInitial,
										javax.swing.GroupLayout.PREFERRED_SIZE, 350,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextFieldStatSoundIIFrequencyInitial,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundIIFrequencyInitialLayout.setVerticalGroup(jPanelStatSoundIIFrequencyInitialLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIIFrequencyInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundIIFrequencyInitialLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIIFrequencyInitial,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundII.add(jPanelStatSoundIIFrequencyInitial);
		jPanelStatSoundIIFrequencyInitial.setBounds(12, 199, 410, 80);

		jPanelStatSoundIIFrequencyEnd.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle
				.findString("statsound$rec.exp.statsoud.lbl.efreq"))); // NOI18N
		jPanelStatSoundIIFrequencyEnd.setName("jPanelStatSoundIIFrequencyEnd"); // NOI18N

		jSliderStatSoundIIFrequencyEnd.setMajorTickSpacing(200);
		jSliderStatSoundIIFrequencyEnd.setMaximum(2000);
		jSliderStatSoundIIFrequencyEnd.setMinimum(100);
		jSliderStatSoundIIFrequencyEnd.setMinorTickSpacing(100);
		jSliderStatSoundIIFrequencyEnd.setPaintLabels(true);
		jSliderStatSoundIIFrequencyEnd.setPaintTicks(true);
		jSliderStatSoundIIFrequencyEnd.setValue(1000);
		jSliderStatSoundIIFrequencyEnd.setName("jSliderStatSoundIIFrequencyEnd"); // NOI18N
		jSliderStatSoundIIFrequencyEnd.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderStatSoundIIFrequencyEndStateChanged(evt);
			}
		});

		jTextFieldStatSoundIIFrequencyEnd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldStatSoundIIFrequencyEnd.setText(org.jdesktop.application.Application.getInstance().getContext()
				.getResourceMap(StatSoundCustomizer.class).getString("jTextFieldStatSoundIIFrequencyEnd.text")); // NOI18N
		jTextFieldStatSoundIIFrequencyEnd.setName("jTextFieldStatSoundIIFrequencyEnd"); // NOI18N
		jTextFieldStatSoundIIFrequencyEnd.setPreferredSize(new java.awt.Dimension(40, 19));
		jTextFieldStatSoundIIFrequencyEnd.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextFieldStatSoundIIFrequencyEndFocusLost(evt);
			}
		});

		javax.swing.GroupLayout jPanelStatSoundIIFrequencyEndLayout = new javax.swing.GroupLayout(
				jPanelStatSoundIIFrequencyEnd);
		jPanelStatSoundIIFrequencyEnd.setLayout(jPanelStatSoundIIFrequencyEndLayout);
		jPanelStatSoundIIFrequencyEndLayout.setHorizontalGroup(jPanelStatSoundIIFrequencyEndLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelStatSoundIIFrequencyEndLayout
						.createSequentialGroup()
						.addComponent(jSliderStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelStatSoundIIFrequencyEndLayout.setVerticalGroup(jPanelStatSoundIIFrequencyEndLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSliderStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(
						jPanelStatSoundIIFrequencyEndLayout
								.createSequentialGroup()
								.addGap(6, 6, 6)
								.addComponent(jTextFieldStatSoundIIFrequencyEnd,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanelStatSoundII.add(jPanelStatSoundIIFrequencyEnd);
		jPanelStatSoundIIFrequencyEnd.setBounds(432, 199, 400, 80);

		jTabbedPaneStatSound.addTab(
				org.jdesktop.application.Application.getInstance().getContext()
						.getResourceMap(StatSoundCustomizer.class)
						.getString("jPanelStatSoundII.TabConstraints.tabTitle"), jPanelStatSoundII); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jTabbedPaneStatSound, javax.swing.GroupLayout.PREFERRED_SIZE,
												855, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												layout.createSequentialGroup()
														.addGap(12, 12, 12)
														.addComponent(jPanelSend, javax.swing.GroupLayout.DEFAULT_SIZE,
																843, Short.MAX_VALUE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jTabbedPaneStatSound, javax.swing.GroupLayout.PREFERRED_SIZE, 320,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanelSend, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void jTextFieldStatSoundINSamplesFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundINSamplesFocusLost
		handleFocusLost(jTextFieldStatSoundINSamples, jSliderStatSoundINSamples);
	}// GEN-LAST:event_jTextFieldStatSoundINSamplesFocusLost

	private void jTextFieldStatSoundIPistonEndFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIPistonEndFocusLost
		handleFocusLost(jTextFieldStatSoundIPistonEnd, jSliderStatSoundIPistonEnd);
	}// GEN-LAST:event_jTextFieldStatSoundIPistonEndFocusLost

	private void jTextFieldStatSoundIFrequencyFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIFrequencyFocusLost
		handleFocusLost(jTextFieldStatSoundIFrequency, jSliderStatSoundIFrequency);
	}// GEN-LAST:event_jTextFieldStatSoundIFrequencyFocusLost

	private void jTextFieldStatSoundIPistonInitialFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIPistonInitialFocusLost
		handleFocusLost(jTextFieldStatSoundIPistonInitial, jSliderStatSoundIPistonInitial);
	}// GEN-LAST:event_jTextFieldStatSoundIPistonInitialFocusLost

	private void jTextFieldSoundVelocityPistonFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSoundVelocityPistonFocusLost
		handleFocusLost(jTextFieldSoundVelocityPiston, jSliderSoundVelocityPiston);
	}// GEN-LAST:event_jTextFieldSoundVelocityPistonFocusLost

	private void jTextFieldSoundVelocityNSamplesFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSoundVelocityNSamplesFocusLost
		handleFocusLost(jTextFieldSoundVelocityNSamples, jSliderSoundVelocityNSamples);
	}// GEN-LAST:event_jTextFieldSoundVelocityNSamplesFocusLost

	private void jTextFieldSoundVelocityFrequencyFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSoundVelocityFrequencyFocusLost
		handleFocusLost(jTextFieldSoundVelocityFrequency, jSliderSoundVelocityFrequency);
	}// GEN-LAST:event_jTextFieldSoundVelocityFrequencyFocusLost

	private void jTextFieldStatSoundIINSamplesFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIINSamplesFocusLost
		handleFocusLost(jTextFieldStatSoundIINSamples, jSliderStatSoundIINSamples);
	}// GEN-LAST:event_jTextFieldStatSoundIINSamplesFocusLost

	private void jTextFieldStatSoundIIPistonFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIIPistonFocusLost
		handleFocusLost(jTextFieldStatSoundIIPiston, jSliderStatSoundIIPiston);
	}// GEN-LAST:event_jTextFieldStatSoundIIPistonFocusLost

	private void jTextFieldStatSoundIIFrequencyInitialFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIIFrequencyInitialFocusLost
		handleFocusLost(jTextFieldStatSoundIIFrequencyInitial, jSliderStatSoundIIFrequencyInitial);
	}// GEN-LAST:event_jTextFieldStatSoundIIFrequencyInitialFocusLost

	private void jTextFieldStatSoundIIFrequencyEndFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldStatSoundIIFrequencyEndFocusLost
		handleFocusLost(jTextFieldStatSoundIIFrequencyEnd, jSliderStatSoundIIFrequencyEnd);
	}// GEN-LAST:event_jTextFieldStatSoundIIFrequencyEndFocusLost

	private void handleFocusLost(final JTextField textField, final JSlider slider) {
		final String stringFromTextField = textField.getText();
		if (stringFromTextField.trim().equals("")) {
			return;
		}
		try {
			final int valueOfTextField = Integer.parseInt(stringFromTextField);
			if (valueOfTextField <= slider.getMaximum() && valueOfTextField >= slider.getMinimum()) {
				slider.setValue(valueOfTextField);
			} else {
				textField.setText("" + slider.getValue());
			}
		} catch (final Exception e) {
			textField.setText("" + slider.getValue());
		}
	}

	private void jComboBoxSoundVelocityWaveFormActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxSoundVelocityWaveFormActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jComboBoxSoundVelocityWaveFormActionPerformed

	private void jButtonDefaultsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDefaultsActionPerformed
		defaultConfig();
	}// GEN-LAST:event_jButtonDefaultsActionPerformed

	private void jButtonCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_jButtonCancelActionPerformed

	private String getStatusFromSelectedOptions(final JRadioButton mic2, final JRadioButton mic3,
			final JRadioButton mic4, final JCheckBox heating) {
		final StringBuilder status = new StringBuilder("");
		if (mic2.isSelected()) {
			status.append("1");
		} else {
			status.append("0");
		}
		if (mic3.isSelected()) {
			status.append("1");
		} else {
			status.append("0");
		}
		if (mic4.isSelected()) {
			status.append("1");
		} else {
			status.append("0");
		}
		if (heating.isSelected()) {
			status.append("1");
		} else {
			status.append("0");
		}
		return status.toString();
	}

	private void jButtonOKActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonOKActionPerformed

		int protocol = 1;
		protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;
		String waveToSend = null;
		String status = "";
		switch (protocol) {
		// Sound velocity
		case 1:
			int selectedWave = jComboBoxSoundVelocityWaveForm.getSelectedIndex();
			switch (selectedWave) {
			case 1:
				waveToSend = "TRIANGULAR";
				break;
			case 2:
				waveToSend = "WHITE_NOISE";
				break;
			case 3:
				waveToSend = "PINK_NOISE";
				break;
			case 4:
				waveToSend = "PULSE";
				break;
			default:
				waveToSend = "SIN";
				break;
			}
			acqConfig.setTotalSamples(jSliderSoundVelocityNSamples.getValue());
			acqConfig.getSelectedHardwareParameter(EXPERIMENT_TYPE).setParameterValue(SOUND_VEL);
			acqConfig.getSelectedHardwareParameter(PISTON_START).setParameterValue(
					"" + jSliderSoundVelocityPiston.getValue());
			acqConfig.getSelectedHardwareParameter(PISTON_END).setParameterValue(
					"" + jSliderSoundVelocityPiston.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_START).setParameterValue(
					"" + jSliderSoundVelocityFrequency.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_END).setParameterValue(
					"" + jSliderSoundVelocityFrequency.getValue());
			acqConfig.getSelectedHardwareParameter(WAVE_FORM).setParameterValue(waveToSend);
			status = getStatusFromSelectedOptions(jRadioButtonSoundVelocityMic2, jRadioButtonSoundVelocityMic3,
					jRadioButtonSoundVelocityMic4, jCheckBoxSoundVelocityHeat);
			break;
		// Vary piston
		case 2:
			acqConfig.setTotalSamples(jSliderStatSoundINSamples.getValue());
			acqConfig.getSelectedHardwareParameter(EXPERIMENT_TYPE).setParameterValue(VARY_PISTON);
			acqConfig.getSelectedHardwareParameter(PISTON_START).setParameterValue(
					"" + jSliderStatSoundIPistonInitial.getValue());
			acqConfig.getSelectedHardwareParameter(PISTON_END).setParameterValue(
					"" + jSliderStatSoundIPistonEnd.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_START).setParameterValue(
					"" + jSliderStatSoundIFrequency.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_END).setParameterValue(
					"" + jSliderStatSoundIFrequency.getValue());
			waveToSend = "SIN";
			acqConfig.getSelectedHardwareParameter(WAVE_FORM).setParameterValue(waveToSend);
			status = getStatusFromSelectedOptions(jRadioButtonStatSoundIMic2, jRadioButtonStatSoundIMic3,
					jRadioButtonStatSoundIMic4, jCheckBoxStatSoundIHeat);
			break;
		// Vary frequency
		case 3:
			acqConfig.setTotalSamples(jSliderStatSoundIINSamples.getValue());
			acqConfig.getSelectedHardwareParameter(EXPERIMENT_TYPE).setParameterValue(VARY_FREQ);
			acqConfig.getSelectedHardwareParameter(PISTON_START).setParameterValue(
					"" + jSliderStatSoundIIPiston.getValue());
			acqConfig.getSelectedHardwareParameter(PISTON_END).setParameterValue(
					"" + jSliderStatSoundIIPiston.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_START).setParameterValue(
					"" + jSliderStatSoundIIFrequencyInitial.getValue());
			acqConfig.getSelectedHardwareParameter(FREQUENCY_END).setParameterValue(
					"" + jSliderStatSoundIIFrequencyEnd.getValue());
			waveToSend = "SIN";
			acqConfig.getSelectedHardwareParameter(WAVE_FORM).setParameterValue(waveToSend);
			status = getStatusFromSelectedOptions(jRadioButtonStatSoundIIMic2, jRadioButtonStatSoundIIMic3,
					jRadioButtonStatSoundIIMic4, jCheckBoxStatSoundIIHeat);
			break;
		default:
			return;
		}
		acqConfig.getSelectedHardwareParameter("status").setParameterValue(status);
		acqConfig.getSelectedHardwareParameter("calibration").setParameterValue("1");
		for (int i = 0; i < acqConfig.getSelectedHardwareParameters().length; i++) {
			System.out.println(acqConfig.getSelectedHardwareParameters(i).getParameterName() + "="
					+ acqConfig.getSelectedHardwareParameters(i).getParameterValue());
		}
		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonOKActionPerformed

	private void jSliderSoundVelocityNSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderSoundVelocityNSamplesStateChanged
		jTextFieldSoundVelocityNSamples.setText("" + jSliderSoundVelocityNSamples.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderSoundVelocityNSamplesStateChanged

	private void jSliderStatSoundINSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundINSamplesStateChanged
		jTextFieldStatSoundINSamples.setText("" + jSliderStatSoundINSamples.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundINSamplesStateChanged

	private void jSliderStatSoundIINSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIINSamplesStateChanged
		jTextFieldStatSoundIINSamples.setText("" + jSliderStatSoundIINSamples.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundIINSamplesStateChanged

	private void jSliderSoundVelocityPistonStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderSoundVelocityPistonStateChanged
		jTextFieldSoundVelocityPiston.setText("" + jSliderSoundVelocityPiston.getValue());
	}// GEN-LAST:event_jSliderSoundVelocityPistonStateChanged

	private void jSliderStatSoundIPistonInitialStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIPistonInitialStateChanged
		jTextFieldStatSoundIPistonInitial.setText("" + jSliderStatSoundIPistonInitial.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundIPistonInitialStateChanged

	private void jSliderStatSoundIPistonEndStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIPistonEndStateChanged
		jTextFieldStatSoundIPistonEnd.setText("" + jSliderStatSoundIPistonEnd.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundIPistonEndStateChanged

	private void jSliderStatSoundIFrequencyStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIFrequencyStateChanged
		jTextFieldStatSoundIFrequency.setText("" + jSliderStatSoundIFrequency.getValue());
	}// GEN-LAST:event_jSliderStatSoundIFrequencyStateChanged

	private void jSliderStatSoundIIPistonStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIIPistonStateChanged
		jTextFieldStatSoundIIPiston.setText("" + jSliderStatSoundIIPiston.getValue());
	}// GEN-LAST:event_jSliderStatSoundIIPistonStateChanged

	private void jSliderStatSoundIIFrequencyInitialStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIIFrequencyInitialStateChanged
		jTextFieldStatSoundIIFrequencyInitial.setText("" + jSliderStatSoundIIFrequencyInitial.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundIIFrequencyInitialStateChanged

	private void jSliderStatSoundIIFrequencyEndStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderStatSoundIIFrequencyEndStateChanged
		jTextFieldStatSoundIIFrequencyEnd.setText("" + jSliderStatSoundIIFrequencyEnd.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderStatSoundIIFrequencyEndStateChanged

	private void jSliderSoundVelocityFrequencyStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderSoundVelocityFrequencyStateChanged
		jTextFieldSoundVelocityFrequency.setText("" + jSliderSoundVelocityFrequency.getValue());
		checkMaxSamples();
	}// GEN-LAST:event_jSliderSoundVelocityFrequencyStateChanged

	private void checkMaxSamples() {
		int maxSamples = 0;
		int minSamples = 0;
		final int protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;

		if (maxSamples == 0) {
			maxSamples = 1;
		}
		switch (protocol) {
		case 1:
			if ((jSliderSoundVelocityNSamples.getValue() * jSliderSoundVelocityFrequency.getValue()) > StatSoundCustomizer.MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG) {
				jLabelSoundVelocityNSamplesAlert.setEnabled(false);
				jLabelSoundVelocityNSamplesAlert.setVisible(false);
				jButtonOK.setEnabled(true);
			} else {
				jLabelSoundVelocityNSamplesAlert.setEnabled(true);
				jLabelSoundVelocityNSamplesAlert.setVisible(true);
				jButtonOK.setEnabled(false);
			}
			jLabelSoundVelocityNSamplesAlert.setText(SOUND_SPEED_NSAMPLES_MESSAGES_STR
					+ StatSoundCustomizer.MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG);
			break;
		case 2:
			double step = hardwareInfo == null ? DEFAULT_VALUE_OF_PISTON_STEP : Double.valueOf(hardwareInfo
					.getHardwareParameter(PISTON_START).getParameterSelectionList(
							INDEX_OF_STEP_IN_PARAMETER_PISTON_START));
			maxSamples = (int) (Math.abs(jSliderStatSoundIPistonInitial.getValue()
					- jSliderStatSoundIPistonEnd.getValue()) / step);
			if (jSliderStatSoundINSamples.getValue() > maxSamples) {
				jLabelStatSoundINSamplesAlert.setEnabled(true);
				jLabelStatSoundINSamplesAlert.setVisible(true);
				jButtonOK.setEnabled(false);
			} else {
				jLabelStatSoundINSamplesAlert.setEnabled(false);
				jLabelStatSoundINSamplesAlert.setVisible(false);
				jButtonOK.setEnabled(true);
			}
			jLabelStatSoundINSamplesAlert.setText(StatSoundCustomizer.MAX_SAMPLES_STR + maxSamples);
			break;
		case 3:
			minSamples = Math.round((jSliderStatSoundIIFrequencyEnd.getValue() - jSliderStatSoundIIFrequencyInitial
					.getValue()) / 2);
			if (jSliderStatSoundIINSamples.getValue() >= minSamples) {
				jLabelStatSoundIINSamplesAlert.setEnabled(false);
				jLabelStatSoundIINSamplesAlert.setVisible(false);
				jButtonOK.setEnabled(true);
			} else {
				jLabelStatSoundIINSamplesAlert.setEnabled(true);
				jLabelStatSoundIINSamplesAlert.setVisible(true);
				jButtonOK.setEnabled(false);
			}
			jLabelStatSoundIINSamplesAlert.setText(StatSoundCustomizer.MIN_SAMPLES_STR + minSamples);
			break;
		default:
			return;
		}
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.getContentPane().add(new StatSoundCustomizer());
		jf.pack();
		jf.show();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonDefaults;
	private javax.swing.JButton jButtonOK;
	private javax.swing.JCheckBox jCheckBoxSoundVelocityHeat;
	private javax.swing.JCheckBox jCheckBoxStatSoundIHeat;
	private javax.swing.JCheckBox jCheckBoxStatSoundIIHeat;
	private javax.swing.JComboBox jComboBoxSoundVelocityWaveForm;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabelSoundVelocityCH1;
	private javax.swing.JLabel jLabelSoundVelocityCH2;
	private javax.swing.JLabel jLabelSoundVelocityMic1;
	private javax.swing.JLabel jLabelSoundVelocityMic2;
	private javax.swing.JLabel jLabelSoundVelocityMic3;
	private javax.swing.JLabel jLabelSoundVelocityMic4;
	private javax.swing.JLabel jLabelSoundVelocityNSamplesAlert;
	private javax.swing.JLabel jLabelSoundVelocityWaveForm;
	private javax.swing.JLabel jLabelStatSoundICH1;
	private javax.swing.JLabel jLabelStatSoundICH2;
	private javax.swing.JLabel jLabelStatSoundIICH1;
	private javax.swing.JLabel jLabelStatSoundIICH2;
	private javax.swing.JLabel jLabelStatSoundIIMic1;
	private javax.swing.JLabel jLabelStatSoundIIMic2;
	private javax.swing.JLabel jLabelStatSoundIIMic3;
	private javax.swing.JLabel jLabelStatSoundIIMic4;
	private javax.swing.JLabel jLabelStatSoundIINSamplesAlert;
	private javax.swing.JLabel jLabelStatSoundIMic1;
	private javax.swing.JLabel jLabelStatSoundIMic2;
	private javax.swing.JLabel jLabelStatSoundIMic3;
	private javax.swing.JLabel jLabelStatSoundIMic4;
	private javax.swing.JLabel jLabelStatSoundINSamplesAlert;
	private javax.swing.JPanel jPanelSend;
	private javax.swing.JPanel jPanelSoundSpeed;
	private javax.swing.JPanel jPanelSoundVelocityFrequency;
	private javax.swing.JPanel jPanelSoundVelocityMics;
	private javax.swing.JPanel jPanelSoundVelocityNSamples;
	private javax.swing.JPanel jPanelSoundVelocityPiston;
	private javax.swing.JPanel jPanelSoundVelocityWaveForm;
	private javax.swing.JPanel jPanelStatSoundI;
	private javax.swing.JPanel jPanelStatSoundIFrequency;
	private javax.swing.JPanel jPanelStatSoundII;
	private javax.swing.JPanel jPanelStatSoundIIFrequencyEnd;
	private javax.swing.JPanel jPanelStatSoundIIFrequencyInitial;
	private javax.swing.JPanel jPanelStatSoundIIMics;
	private javax.swing.JPanel jPanelStatSoundIINSamples;
	private javax.swing.JPanel jPanelStatSoundIIPiston;
	private javax.swing.JPanel jPanelStatSoundIMics;
	private javax.swing.JPanel jPanelStatSoundINSamples;
	private javax.swing.JPanel jPanelStatSoundIPistonEnd;
	private javax.swing.JPanel jPanelStatSoundPistonIInitial;
	private javax.swing.JRadioButton jRadioButtonSoundVelocityMic1;
	private javax.swing.JRadioButton jRadioButtonSoundVelocityMic2;
	private javax.swing.JRadioButton jRadioButtonSoundVelocityMic3;
	private javax.swing.JRadioButton jRadioButtonSoundVelocityMic4;
	private javax.swing.JRadioButton jRadioButtonStatSoundIIMic1;
	private javax.swing.JRadioButton jRadioButtonStatSoundIIMic2;
	private javax.swing.JRadioButton jRadioButtonStatSoundIIMic3;
	private javax.swing.JRadioButton jRadioButtonStatSoundIIMic4;
	private javax.swing.JRadioButton jRadioButtonStatSoundIMic1;
	private javax.swing.JRadioButton jRadioButtonStatSoundIMic2;
	private javax.swing.JRadioButton jRadioButtonStatSoundIMic3;
	private javax.swing.JRadioButton jRadioButtonStatSoundIMic4;
	private javax.swing.JSlider jSliderSoundVelocityFrequency;
	private javax.swing.JSlider jSliderSoundVelocityNSamples;
	private javax.swing.JSlider jSliderSoundVelocityPiston;
	private javax.swing.JSlider jSliderStatSoundIFrequency;
	private javax.swing.JSlider jSliderStatSoundIIFrequencyEnd;
	private javax.swing.JSlider jSliderStatSoundIIFrequencyInitial;
	private javax.swing.JSlider jSliderStatSoundIINSamples;
	private javax.swing.JSlider jSliderStatSoundIIPiston;
	private javax.swing.JSlider jSliderStatSoundINSamples;
	private javax.swing.JSlider jSliderStatSoundIPistonEnd;
	private javax.swing.JSlider jSliderStatSoundIPistonInitial;
	private javax.swing.JTabbedPane jTabbedPaneStatSound;
	private javax.swing.JTextField jTextFieldSoundVelocityFrequency;
	private javax.swing.JTextField jTextFieldSoundVelocityNSamples;
	private javax.swing.JTextField jTextFieldSoundVelocityPiston;
	private javax.swing.JTextField jTextFieldStatSoundIFrequency;
	private javax.swing.JTextField jTextFieldStatSoundIIFrequencyEnd;
	private javax.swing.JTextField jTextFieldStatSoundIIFrequencyInitial;
	private javax.swing.JTextField jTextFieldStatSoundIINSamples;
	private javax.swing.JTextField jTextFieldStatSoundIIPiston;
	private javax.swing.JTextField jTextFieldStatSoundINSamples;
	private javax.swing.JTextField jTextFieldStatSoundIPistonEnd;
	private javax.swing.JTextField jTextFieldStatSoundIPistonInitial;
	private javax.swing.ButtonGroup soundVelocityMicGroup;
	private javax.swing.ButtonGroup statSoundIIMicGroup;
	private javax.swing.ButtonGroup statSoundIMicGroup;
	// End of variables declaration//GEN-END:variables
	/** REC impl */
	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
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

	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
	}

	public javax.swing.JComponent getCustomizerComponent() {
		return this;
	}

	public javax.swing.ImageIcon getCustomizerIcon() {
		return ICON;
	}

	public String getCustomizerTitle() {
		return TITLE;
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		this.acqConfig = acqConfig;
		if (acqConfig == null) {
			return;
		}

	}

	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
		if (this.hardwareInfo != null) {
			MIN_PISTON_POSITION = Integer.valueOf(this.hardwareInfo.getHardwareParameter(PISTON_START)
					.getParameterSelectionList(INDEX_OF_MIN_POSITION_IN_PARAMETER_PISTON_START));
			MAX_PISTON_POSITION = Integer.valueOf(this.hardwareInfo.getHardwareParameter(PISTON_START)
					.getParameterSelectionList(INDEX_OF_MAX_POSITION_IN_PARAMETER_PISTON_START));
		}
	}
}
