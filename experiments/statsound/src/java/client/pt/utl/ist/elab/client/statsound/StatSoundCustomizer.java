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

    public static final int MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG = 100000;
    /** Init vars */
    // Sound velocity tab
    private final int DEFAULT_SOUND_VELOCITY_NSAMPLES = 500;
    private final int DEFAULT_SOUND_VELOCITY_PISTON = 1300;
    private final int DEFAULT_SOUND_VELOCITY_FREQUENCY = 250;
    // Statsound I tab
    private final int DEFAULT_STATSOUND_I_NSAMPLES = 50;
    private final int DEFAULT_STATSOUND_I_FREQUENCY = 250;
    private final int DEFAULT_STATSOUND_I_PISTON_INITIAL = 1300;
    private final int DEFAULT_STATSOUND_I_PISTON_END = 1400;
    // Statsound II tab
    private final int DEFAULT_STATSOUND_II_NSAMPLES = 400;
    private final int DEFAULT_STATSOUND_II_FREQUENCY_INITIAL = 250;
    private final int DEFAULT_STATSOUND_II_FREQUENCY_END = 1000;
    private final int DEFAULT_STATSOUND_II_PISTON = 1300;
//    private int nSamples;
//    private int frequencyInitial;
//    private int frequencyEnd;
//    private int pistonPositionInitial;
//    private int pistonPositionEnd;
    private static final String MIN_SAMPLES_STR = ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.minsamples");
    private static final String MAX_SAMPLES_STR = ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.maxsamples");
    /** REC */
    private HardwareInfo hardwareInfo = null;
    private HardwareAcquisitionConfig acqConfig = null;
    private final javax.swing.ImageIcon ICON = new javax.swing.ImageIcon(getClass().getResource(
            "/pt/utl/ist/elab/client/statsound/resources/sound.gif"));
    private final String TITLE = ReCResourceBundle.findString("statsound$rec.exp.statsoud.customizer.title");

    ;

    /** Creates new form NewJPanel */
    public StatSoundCustomizer() {
        initComponents();

        String pink = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.pink");
        String pulse = ReCResourceBundle.findString("statsound$rec.exp.statsound.lbl.pulse");
        jComboBoxSoundVelocityWaveForm.addItem(pink);
        jComboBoxSoundVelocityWaveForm.addItem(pulse);

        defaultConfig();
    }

    private void defaultConfig() {
        int protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        jPanelSoundSpeed.setLayout(null);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(StatSoundCustomizer.class); // NOI18N
        jPanelSoundVelocityMics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.channels"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSoundVelocityMics.border.titleFont"), resourceMap.getColor("jPanelSoundVelocityMics.border.titleColor"))); // NOI18N
        jPanelSoundVelocityMics.setName("jPanelSoundVelocityMics"); // NOI18N
        jPanelSoundVelocityMics.setPreferredSize(new java.awt.Dimension(408, 95));

        jLabelSoundVelocityMic1.setText(resourceMap.getString("jLabelSoundVelocityMic1.text")); // NOI18N
        jLabelSoundVelocityMic1.setName("jLabelSoundVelocityMic1"); // NOI18N

        jLabelSoundVelocityMic2.setText(resourceMap.getString("jLabelSoundVelocityMic2.text")); // NOI18N
        jLabelSoundVelocityMic2.setName("jLabelSoundVelocityMic2"); // NOI18N

        jLabelSoundVelocityMic3.setText(resourceMap.getString("jLabelSoundVelocityMic3.text")); // NOI18N
        jLabelSoundVelocityMic3.setName("jLabelSoundVelocityMic3"); // NOI18N

        jLabelSoundVelocityMic4.setText(resourceMap.getString("jLabelSoundVelocityMic4.text")); // NOI18N
        jLabelSoundVelocityMic4.setName("jLabelSoundVelocityMic4"); // NOI18N

        jLabelSoundVelocityCH1.setText(resourceMap.getString("jLabelSoundVelocityCH1.text")); // NOI18N
        jLabelSoundVelocityCH1.setName("jLabelSoundVelocityCH1"); // NOI18N

        jLabelSoundVelocityCH2.setText(resourceMap.getString("jLabelSoundVelocityCH2.text")); // NOI18N
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
        jPanelSoundVelocityMicsLayout.setHorizontalGroup(
            jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityMicsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSoundVelocityMicsLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabelSoundVelocityMic1)
                        .addGap(59, 59, 59)
                        .addComponent(jLabelSoundVelocityMic2)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelSoundVelocityMic3)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelSoundVelocityMic4))
                    .addGroup(jPanelSoundVelocityMicsLayout.createSequentialGroup()
                        .addComponent(jLabelSoundVelocityCH1)
                        .addGap(20, 20, 20)
                        .addComponent(jRadioButtonSoundVelocityMic1)
                        .addGap(24, 24, 24)
                        .addComponent(jLabelSoundVelocityCH2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonSoundVelocityMic2)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonSoundVelocityMic3)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonSoundVelocityMic4)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxSoundVelocityHeat))))
        );
        jPanelSoundVelocityMicsLayout.setVerticalGroup(
            jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityMicsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSoundVelocityMic1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSoundVelocityMic2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSoundVelocityMic3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSoundVelocityMic4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSoundVelocityCH1)
                    .addComponent(jRadioButtonSoundVelocityMic1)
                    .addComponent(jLabelSoundVelocityCH2)
                    .addComponent(jRadioButtonSoundVelocityMic2)
                    .addComponent(jRadioButtonSoundVelocityMic3)
                    .addGroup(jPanelSoundVelocityMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jCheckBoxSoundVelocityHeat)
                        .addComponent(jRadioButtonSoundVelocityMic4)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanelSoundSpeed.add(jPanelSoundVelocityMics);
        jPanelSoundVelocityMics.setBounds(12, 12, 410, 95);

        jPanelSoundVelocityNSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.nsamples"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSoundVelocityNSamples.border.titleFont"), resourceMap.getColor("jPanelSoundVelocityNSamples.border.titleColor"))); // NOI18N
        jPanelSoundVelocityNSamples.setName("jPanelSoundVelocityNSamples"); // NOI18N

        jTextFieldSoundVelocityNSamples.setColumns(4);
        jTextFieldSoundVelocityNSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSoundVelocityNSamples.setText(resourceMap.getString("jTextFieldSoundVelocityNSamples.text")); // NOI18N
        jTextFieldSoundVelocityNSamples.setName("jTextFieldSoundVelocityNSamples"); // NOI18N

        jLabelSoundVelocityNSamplesAlert.setForeground(resourceMap.getColor("jLabelSoundVelocityNSamplesAlert.foreground")); // NOI18N
        jLabelSoundVelocityNSamplesAlert.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
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

        javax.swing.GroupLayout jPanelSoundVelocityNSamplesLayout = new javax.swing.GroupLayout(jPanelSoundVelocityNSamples);
        jPanelSoundVelocityNSamples.setLayout(jPanelSoundVelocityNSamplesLayout);
        jPanelSoundVelocityNSamplesLayout.setHorizontalGroup(
            jPanelSoundVelocityNSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityNSamplesLayout.createSequentialGroup()
                .addComponent(jSliderSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelSoundVelocityNSamplesLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabelSoundVelocityNSamplesAlert))
        );
        jPanelSoundVelocityNSamplesLayout.setVerticalGroup(
            jPanelSoundVelocityNSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityNSamplesLayout.createSequentialGroup()
                .addGroup(jPanelSoundVelocityNSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelSoundVelocityNSamplesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldSoundVelocityNSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabelSoundVelocityNSamplesAlert))
        );

        jPanelSoundSpeed.add(jPanelSoundVelocityNSamples);
        jPanelSoundVelocityNSamples.setBounds(432, 12, 408, 95);

        jPanelSoundVelocityPiston.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.pistonstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSoundVelocityPiston.border.titleFont"), resourceMap.getColor("jPanelSoundVelocityPiston.border.titleColor"))); // NOI18N
        jPanelSoundVelocityPiston.setForeground(resourceMap.getColor("jPanelSoundVelocityPiston.foreground")); // NOI18N
        jPanelSoundVelocityPiston.setName("jPanelSoundVelocityPiston"); // NOI18N

        jSliderSoundVelocityPiston.setMajorTickSpacing(40);
        jSliderSoundVelocityPiston.setMaximum(1484);
        jSliderSoundVelocityPiston.setMinimum(1264);
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

        jTextFieldSoundVelocityPiston.setColumns(4);
        jTextFieldSoundVelocityPiston.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSoundVelocityPiston.setText(resourceMap.getString("jTextFieldSoundVelocityPiston.text")); // NOI18N
        jTextFieldSoundVelocityPiston.setName("jTextFieldSoundVelocityPiston"); // NOI18N

        javax.swing.GroupLayout jPanelSoundVelocityPistonLayout = new javax.swing.GroupLayout(jPanelSoundVelocityPiston);
        jPanelSoundVelocityPiston.setLayout(jPanelSoundVelocityPistonLayout);
        jPanelSoundVelocityPistonLayout.setHorizontalGroup(
            jPanelSoundVelocityPistonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityPistonLayout.createSequentialGroup()
                .addComponent(jSliderSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSoundVelocityPistonLayout.setVerticalGroup(
            jPanelSoundVelocityPistonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelSoundVelocityPistonLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldSoundVelocityPiston, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelSoundSpeed.add(jPanelSoundVelocityPiston);
        jPanelSoundVelocityPiston.setBounds(12, 113, 410, 80);

        jPanelSoundVelocityWaveForm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.nsamples"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSoundVelocityWaveForm.border.titleFont"), resourceMap.getColor("jPanelSoundVelocityWaveForm.border.titleColor"))); // NOI18N
        jPanelSoundVelocityWaveForm.setName("jPanelSoundVelocityWaveForm"); // NOI18N
        jPanelSoundVelocityWaveForm.setPreferredSize(new java.awt.Dimension(408, 200));

        jLabelSoundVelocityWaveForm.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.waveform")); // NOI18N
        jLabelSoundVelocityWaveForm.setName("jLabelSoundVelocityWaveForm"); // NOI18N

        jComboBoxSoundVelocityWaveForm.setName("jComboBoxSoundVelocityWaveForm"); // NOI18N

        javax.swing.GroupLayout jPanelSoundVelocityWaveFormLayout = new javax.swing.GroupLayout(jPanelSoundVelocityWaveForm);
        jPanelSoundVelocityWaveForm.setLayout(jPanelSoundVelocityWaveFormLayout);
        jPanelSoundVelocityWaveFormLayout.setHorizontalGroup(
            jPanelSoundVelocityWaveFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityWaveFormLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabelSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSoundVelocityWaveFormLayout.setVerticalGroup(
            jPanelSoundVelocityWaveFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityWaveFormLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelSoundVelocityWaveFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSoundVelocityWaveForm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanelSoundSpeed.add(jPanelSoundVelocityWaveForm);
        jPanelSoundVelocityWaveForm.setBounds(432, 113, 408, 80);

        jPanelSoundVelocityFrequency.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.freqstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelSoundVelocityFrequency.border.titleFont"), resourceMap.getColor("jPanelSoundVelocityFrequency.border.titleColor"))); // NOI18N
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

        jTextFieldSoundVelocityFrequency.setColumns(4);
        jTextFieldSoundVelocityFrequency.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSoundVelocityFrequency.setText(resourceMap.getString("jTextFieldSoundVelocityFrequency.text")); // NOI18N
        jTextFieldSoundVelocityFrequency.setName("jTextFieldSoundVelocityFrequency"); // NOI18N

        javax.swing.GroupLayout jPanelSoundVelocityFrequencyLayout = new javax.swing.GroupLayout(jPanelSoundVelocityFrequency);
        jPanelSoundVelocityFrequency.setLayout(jPanelSoundVelocityFrequencyLayout);
        jPanelSoundVelocityFrequencyLayout.setHorizontalGroup(
            jPanelSoundVelocityFrequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSoundVelocityFrequencyLayout.createSequentialGroup()
                .addComponent(jSliderSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSoundVelocityFrequencyLayout.setVerticalGroup(
            jPanelSoundVelocityFrequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelSoundVelocityFrequencyLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldSoundVelocityFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelSoundSpeed.add(jPanelSoundVelocityFrequency);
        jPanelSoundVelocityFrequency.setBounds(12, 199, 410, 80);

        jTabbedPaneStatSound.addTab(resourceMap.getString("jPanelSoundSpeed.TabConstraints.tabTitle"), jPanelSoundSpeed); // NOI18N

        jPanelStatSoundI.setName("jPanelStatSoundI"); // NOI18N
        jPanelStatSoundI.setLayout(null);

        jPanelStatSoundIMics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.channels"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIMics.border.titleFont"), resourceMap.getColor("jPanelStatSoundIMics.border.titleColor"))); // NOI18N
        jPanelStatSoundIMics.setName("jPanelStatSoundIMics"); // NOI18N

        jLabelStatSoundIMic1.setText(resourceMap.getString("jLabelStatSoundIMic1.text")); // NOI18N
        jLabelStatSoundIMic1.setName("jLabelStatSoundIMic1"); // NOI18N

        jLabelStatSoundIMic2.setText(resourceMap.getString("jLabelStatSoundIMic2.text")); // NOI18N
        jLabelStatSoundIMic2.setName("jLabelStatSoundIMic2"); // NOI18N

        jLabelStatSoundIMic3.setText(resourceMap.getString("jLabelStatSoundIMic3.text")); // NOI18N
        jLabelStatSoundIMic3.setName("jLabelStatSoundIMic3"); // NOI18N

        jLabelStatSoundIMic4.setText(resourceMap.getString("jLabelStatSoundIMic4.text")); // NOI18N
        jLabelStatSoundIMic4.setName("jLabelStatSoundIMic4"); // NOI18N

        jLabelStatSoundICH1.setText(resourceMap.getString("jLabelStatSoundICH1.text")); // NOI18N
        jLabelStatSoundICH1.setName("jLabelStatSoundICH1"); // NOI18N

        jLabelStatSoundICH2.setText(resourceMap.getString("jLabelStatSoundICH2.text")); // NOI18N
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
        jPanelStatSoundIMicsLayout.setHorizontalGroup(
            jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIMicsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStatSoundIMicsLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabelStatSoundIMic1)
                        .addGap(59, 59, 59)
                        .addComponent(jLabelStatSoundIMic2)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelStatSoundIMic3)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelStatSoundIMic4))
                    .addGroup(jPanelStatSoundIMicsLayout.createSequentialGroup()
                        .addComponent(jLabelStatSoundICH1)
                        .addGap(20, 20, 20)
                        .addComponent(jRadioButtonStatSoundIMic1)
                        .addGap(24, 24, 24)
                        .addComponent(jLabelStatSoundICH2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonStatSoundIMic2)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonStatSoundIMic3)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonStatSoundIMic4)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxStatSoundIHeat))))
        );
        jPanelStatSoundIMicsLayout.setVerticalGroup(
            jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIMicsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatSoundIMic1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIMic2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIMic3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIMic4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatSoundICH1)
                    .addComponent(jRadioButtonStatSoundIMic1)
                    .addComponent(jLabelStatSoundICH2)
                    .addComponent(jRadioButtonStatSoundIMic2)
                    .addComponent(jRadioButtonStatSoundIMic3)
                    .addGroup(jPanelStatSoundIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jCheckBoxStatSoundIHeat)
                        .addComponent(jRadioButtonStatSoundIMic4)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanelStatSoundI.add(jPanelStatSoundIMics);
        jPanelStatSoundIMics.setBounds(12, 12, 410, 95);

        jPanelStatSoundINSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.nsamples"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundINSamples.border.titleFont"), resourceMap.getColor("jPanelStatSoundINSamples.border.titleColor"))); // NOI18N
        jPanelStatSoundINSamples.setName("jPanelStatSoundINSamples"); // NOI18N

        jTextFieldStatSoundINSamples.setColumns(4);
        jTextFieldStatSoundINSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundINSamples.setText(resourceMap.getString("jTextFieldStatSoundINSamples.text")); // NOI18N
        jTextFieldStatSoundINSamples.setName("jTextFieldStatSoundINSamples"); // NOI18N

        jLabelStatSoundINSamplesAlert.setForeground(resourceMap.getColor("jLabelStatSoundINSamplesAlert.foreground")); // NOI18N
        jLabelStatSoundINSamplesAlert.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
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
        jPanelStatSoundINSamplesLayout.setHorizontalGroup(
            jPanelStatSoundINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundINSamplesLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelStatSoundINSamplesLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabelStatSoundINSamplesAlert))
        );
        jPanelStatSoundINSamplesLayout.setVerticalGroup(
            jPanelStatSoundINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundINSamplesLayout.createSequentialGroup()
                .addGroup(jPanelStatSoundINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelStatSoundINSamplesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldStatSoundINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabelStatSoundINSamplesAlert))
        );

        jPanelStatSoundI.add(jPanelStatSoundINSamples);
        jPanelStatSoundINSamples.setBounds(432, 12, 408, 95);

        jPanelStatSoundPistonIInitial.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.pistonstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundPistonIInitial.border.titleFont"), resourceMap.getColor("jPanelStatSoundPistonIInitial.border.titleColor"))); // NOI18N
        jPanelStatSoundPistonIInitial.setName("jPanelStatSoundPistonIInitial"); // NOI18N

        jSliderStatSoundIPistonInitial.setMajorTickSpacing(40);
        jSliderStatSoundIPistonInitial.setMaximum(1484);
        jSliderStatSoundIPistonInitial.setMinimum(1264);
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

        jTextFieldStatSoundIPistonInitial.setColumns(4);
        jTextFieldStatSoundIPistonInitial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIPistonInitial.setText(resourceMap.getString("jTextFieldStatSoundIPistonInitial.text")); // NOI18N
        jTextFieldStatSoundIPistonInitial.setName("jTextFieldStatSoundIPistonInitial"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundPistonIInitialLayout = new javax.swing.GroupLayout(jPanelStatSoundPistonIInitial);
        jPanelStatSoundPistonIInitial.setLayout(jPanelStatSoundPistonIInitialLayout);
        jPanelStatSoundPistonIInitialLayout.setHorizontalGroup(
            jPanelStatSoundPistonIInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundPistonIInitialLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundPistonIInitialLayout.setVerticalGroup(
            jPanelStatSoundPistonIInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundPistonIInitialLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIPistonInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundI.add(jPanelStatSoundPistonIInitial);
        jPanelStatSoundPistonIInitial.setBounds(12, 113, 410, 80);

        jPanelStatSoundIPistonEnd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.pistonend"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIPistonEnd.border.titleFont"), resourceMap.getColor("jPanelStatSoundIPistonEnd.border.titleColor"))); // NOI18N
        jPanelStatSoundIPistonEnd.setName("jPanelStatSoundIPistonEnd"); // NOI18N

        jSliderStatSoundIPistonEnd.setMajorTickSpacing(40);
        jSliderStatSoundIPistonEnd.setMaximum(1484);
        jSliderStatSoundIPistonEnd.setMinimum(1264);
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

        jTextFieldStatSoundIPistonEnd.setColumns(4);
        jTextFieldStatSoundIPistonEnd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIPistonEnd.setText(resourceMap.getString("jTextFieldStatSoundIPistonEnd.text")); // NOI18N
        jTextFieldStatSoundIPistonEnd.setName("jTextFieldStatSoundIPistonEnd"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundIPistonEndLayout = new javax.swing.GroupLayout(jPanelStatSoundIPistonEnd);
        jPanelStatSoundIPistonEnd.setLayout(jPanelStatSoundIPistonEndLayout);
        jPanelStatSoundIPistonEndLayout.setHorizontalGroup(
            jPanelStatSoundIPistonEndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIPistonEndLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundIPistonEndLayout.setVerticalGroup(
            jPanelStatSoundIPistonEndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundIPistonEndLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIPistonEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundI.add(jPanelStatSoundIPistonEnd);
        jPanelStatSoundIPistonEnd.setBounds(432, 113, 408, 80);

        jPanelStatSoundIFrequency.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.freqstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIFrequency.border.titleFont"), resourceMap.getColor("jPanelStatSoundIFrequency.border.titleColor"))); // NOI18N
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

        jTextFieldStatSoundIFrequency.setColumns(4);
        jTextFieldStatSoundIFrequency.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIFrequency.setText(resourceMap.getString("jTextFieldStatSoundIFrequency.text")); // NOI18N
        jTextFieldStatSoundIFrequency.setName("jTextFieldStatSoundIFrequency"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundIFrequencyLayout = new javax.swing.GroupLayout(jPanelStatSoundIFrequency);
        jPanelStatSoundIFrequency.setLayout(jPanelStatSoundIFrequencyLayout);
        jPanelStatSoundIFrequencyLayout.setHorizontalGroup(
            jPanelStatSoundIFrequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIFrequencyLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundIFrequencyLayout.setVerticalGroup(
            jPanelStatSoundIFrequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundIFrequencyLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundI.add(jPanelStatSoundIFrequency);
        jPanelStatSoundIFrequency.setBounds(12, 199, 410, 80);

        jTabbedPaneStatSound.addTab(resourceMap.getString("jPanelStatSoundI.TabConstraints.tabTitle"), jPanelStatSoundI); // NOI18N

        jPanelStatSoundII.setName("jPanelStatSoundII"); // NOI18N
        jPanelStatSoundII.setLayout(null);

        jPanelStatSoundIIMics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.channels"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIIMics.border.titleFont"), resourceMap.getColor("jPanelStatSoundIIMics.border.titleColor"))); // NOI18N
        jPanelStatSoundIIMics.setName("jPanelStatSoundIIMics"); // NOI18N

        jLabelStatSoundIIMic1.setText(resourceMap.getString("jLabelStatSoundIIMic1.text")); // NOI18N
        jLabelStatSoundIIMic1.setName("jLabelStatSoundIIMic1"); // NOI18N

        jLabelStatSoundIIMic2.setText(resourceMap.getString("jLabelStatSoundIIMic2.text")); // NOI18N
        jLabelStatSoundIIMic2.setName("jLabelStatSoundIIMic2"); // NOI18N

        jLabelStatSoundIIMic3.setText(resourceMap.getString("jLabelStatSoundIIMic3.text")); // NOI18N
        jLabelStatSoundIIMic3.setName("jLabelStatSoundIIMic3"); // NOI18N

        jLabelStatSoundIIMic4.setText(resourceMap.getString("jLabelStatSoundIIMic4.text")); // NOI18N
        jLabelStatSoundIIMic4.setName("jLabelStatSoundIIMic4"); // NOI18N

        jLabelStatSoundIICH1.setText(resourceMap.getString("jLabelStatSoundIICH1.text")); // NOI18N
        jLabelStatSoundIICH1.setName("jLabelStatSoundIICH1"); // NOI18N

        jLabelStatSoundIICH2.setText(resourceMap.getString("jLabelStatSoundIICH2.text")); // NOI18N
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
        jPanelStatSoundIIMicsLayout.setHorizontalGroup(
            jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIIMicsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStatSoundIIMicsLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabelStatSoundIIMic1)
                        .addGap(59, 59, 59)
                        .addComponent(jLabelStatSoundIIMic2)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelStatSoundIIMic3)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelStatSoundIIMic4))
                    .addGroup(jPanelStatSoundIIMicsLayout.createSequentialGroup()
                        .addComponent(jLabelStatSoundIICH1)
                        .addGap(20, 20, 20)
                        .addComponent(jRadioButtonStatSoundIIMic1)
                        .addGap(24, 24, 24)
                        .addComponent(jLabelStatSoundIICH2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonStatSoundIIMic2)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonStatSoundIIMic3)
                        .addGap(27, 27, 27)
                        .addComponent(jRadioButtonStatSoundIIMic4)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxStatSoundIIHeat))))
        );
        jPanelStatSoundIIMicsLayout.setVerticalGroup(
            jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIIMicsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatSoundIIMic1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIIMic2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIIMic3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatSoundIIMic4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatSoundIICH1)
                    .addComponent(jRadioButtonStatSoundIIMic1)
                    .addComponent(jLabelStatSoundIICH2)
                    .addComponent(jRadioButtonStatSoundIIMic2)
                    .addComponent(jRadioButtonStatSoundIIMic3)
                    .addGroup(jPanelStatSoundIIMicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jCheckBoxStatSoundIIHeat)
                        .addComponent(jRadioButtonStatSoundIIMic4)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanelStatSoundII.add(jPanelStatSoundIIMics);
        jPanelStatSoundIIMics.setBounds(12, 12, 410, 95);

        jPanelStatSoundIINSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.nsamples"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIINSamples.border.titleFont"), resourceMap.getColor("jPanelStatSoundIINSamples.border.titleColor"))); // NOI18N
        jPanelStatSoundIINSamples.setName("jPanelStatSoundIINSamples"); // NOI18N

        jTextFieldStatSoundIINSamples.setColumns(4);
        jTextFieldStatSoundIINSamples.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIINSamples.setText(resourceMap.getString("jTextFieldStatSoundIINSamples.text")); // NOI18N
        jTextFieldStatSoundIINSamples.setName("jTextFieldStatSoundIINSamples"); // NOI18N

        jLabelStatSoundIINSamplesAlert.setForeground(resourceMap.getColor("jLabelStatSoundIINSamplesAlert.foreground")); // NOI18N
        jLabelStatSoundIINSamplesAlert.setText(ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.maxsamples")); // NOI18N
        jLabelStatSoundIINSamplesAlert.setEnabled(false);
        jLabelStatSoundIINSamplesAlert.setName("jLabelStatSoundIINSamplesAlert"); // NOI18N

        jSliderStatSoundIINSamples.setMajorTickSpacing(100);
        jSliderStatSoundIINSamples.setMaximum(600);
        jSliderStatSoundIINSamples.setMinimum(1);
        jSliderStatSoundIINSamples.setMinorTickSpacing(50);
        jSliderStatSoundIINSamples.setPaintLabels(true);
        jSliderStatSoundIINSamples.setPaintTicks(true);
        jSliderStatSoundIINSamples.setSnapToTicks(true);
        jSliderStatSoundIINSamples.setValue(400);
        jSliderStatSoundIINSamples.setName("jSliderStatSoundIINSamples"); // NOI18N
        jSliderStatSoundIINSamples.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderStatSoundIINSamplesStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelStatSoundIINSamplesLayout = new javax.swing.GroupLayout(jPanelStatSoundIINSamples);
        jPanelStatSoundIINSamples.setLayout(jPanelStatSoundIINSamplesLayout);
        jPanelStatSoundIINSamplesLayout.setHorizontalGroup(
            jPanelStatSoundIINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIINSamplesLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelStatSoundIINSamplesLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabelStatSoundIINSamplesAlert))
        );
        jPanelStatSoundIINSamplesLayout.setVerticalGroup(
            jPanelStatSoundIINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIINSamplesLayout.createSequentialGroup()
                .addGroup(jPanelStatSoundIINSamplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelStatSoundIINSamplesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldStatSoundIINSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabelStatSoundIINSamplesAlert))
        );

        jLabelStatSoundIINSamplesAlert.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabelStatSoundIINSamplesAlert.AccessibleContext.accessibleName")); // NOI18N

        jPanelStatSoundII.add(jPanelStatSoundIINSamples);
        jPanelStatSoundIINSamples.setBounds(432, 12, 408, 95);

        jPanelStatSoundIIPiston.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.pistonstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIIPiston.border.titleFont"), resourceMap.getColor("jPanelStatSoundIIPiston.border.titleColor"))); // NOI18N
        jPanelStatSoundIIPiston.setName("jPanelStatSoundIIPiston"); // NOI18N

        jSliderStatSoundIIPiston.setMajorTickSpacing(40);
        jSliderStatSoundIIPiston.setMaximum(1484);
        jSliderStatSoundIIPiston.setMinimum(1264);
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

        jTextFieldStatSoundIIPiston.setColumns(4);
        jTextFieldStatSoundIIPiston.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIIPiston.setText(resourceMap.getString("jTextFieldStatSoundIIPiston.text")); // NOI18N
        jTextFieldStatSoundIIPiston.setName("jTextFieldStatSoundIIPiston"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundIIPistonLayout = new javax.swing.GroupLayout(jPanelStatSoundIIPiston);
        jPanelStatSoundIIPiston.setLayout(jPanelStatSoundIIPistonLayout);
        jPanelStatSoundIIPistonLayout.setHorizontalGroup(
            jPanelStatSoundIIPistonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIIPistonLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundIIPistonLayout.setVerticalGroup(
            jPanelStatSoundIIPistonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundIIPistonLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIIPiston, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundII.add(jPanelStatSoundIIPiston);
        jPanelStatSoundIIPiston.setBounds(12, 113, 410, 80);

        jPanelStatSoundIIFrequencyInitial.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.freqstart"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIIFrequencyInitial.border.titleFont"), resourceMap.getColor("jPanelStatSoundIIFrequencyInitial.border.titleColor"))); // NOI18N
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

        jTextFieldStatSoundIIFrequencyInitial.setColumns(4);
        jTextFieldStatSoundIIFrequencyInitial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIIFrequencyInitial.setText(resourceMap.getString("jTextFieldStatSoundIIFrequencyInitial.text")); // NOI18N
        jTextFieldStatSoundIIFrequencyInitial.setName("jTextFieldStatSoundIIFrequencyInitial"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundIIFrequencyInitialLayout = new javax.swing.GroupLayout(jPanelStatSoundIIFrequencyInitial);
        jPanelStatSoundIIFrequencyInitial.setLayout(jPanelStatSoundIIFrequencyInitialLayout);
        jPanelStatSoundIIFrequencyInitialLayout.setHorizontalGroup(
            jPanelStatSoundIIFrequencyInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIIFrequencyInitialLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIIFrequencyInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIIFrequencyInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundIIFrequencyInitialLayout.setVerticalGroup(
            jPanelStatSoundIIFrequencyInitialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIIFrequencyInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundIIFrequencyInitialLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIIFrequencyInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundII.add(jPanelStatSoundIIFrequencyInitial);
        jPanelStatSoundIIFrequencyInitial.setBounds(12, 199, 410, 80);

        jPanelStatSoundIIFrequencyEnd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ReCResourceBundle.findString("statsound$rec.exp.statsoud.lbl.efreq"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanelStatSoundIIFrequencyEnd.border.titleFont"), resourceMap.getColor("jPanelStatSoundIIFrequencyEnd.border.titleColor"))); // NOI18N
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

        jTextFieldStatSoundIIFrequencyEnd.setColumns(4);
        jTextFieldStatSoundIIFrequencyEnd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldStatSoundIIFrequencyEnd.setText(resourceMap.getString("jTextFieldStatSoundIIFrequencyEnd.text")); // NOI18N
        jTextFieldStatSoundIIFrequencyEnd.setName("jTextFieldStatSoundIIFrequencyEnd"); // NOI18N

        javax.swing.GroupLayout jPanelStatSoundIIFrequencyEndLayout = new javax.swing.GroupLayout(jPanelStatSoundIIFrequencyEnd);
        jPanelStatSoundIIFrequencyEnd.setLayout(jPanelStatSoundIIFrequencyEndLayout);
        jPanelStatSoundIIFrequencyEndLayout.setHorizontalGroup(
            jPanelStatSoundIIFrequencyEndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatSoundIIFrequencyEndLayout.createSequentialGroup()
                .addComponent(jSliderStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelStatSoundIIFrequencyEndLayout.setVerticalGroup(
            jPanelStatSoundIIFrequencyEndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStatSoundIIFrequencyEndLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTextFieldStatSoundIIFrequencyEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelStatSoundII.add(jPanelStatSoundIIFrequencyEnd);
        jPanelStatSoundIIFrequencyEnd.setBounds(432, 199, 408, 80);

        jTabbedPaneStatSound.addTab(resourceMap.getString("jPanelStatSoundII.TabConstraints.tabTitle"), jPanelStatSoundII); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneStatSound, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanelSend, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPaneStatSound, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDefaultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDefaultsActionPerformed
        defaultConfig();
}//GEN-LAST:event_jButtonDefaultsActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        fireICustomizerListenerCanceled();
}//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed

        int protocol = 1;
        protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;

        switch (protocol) {
            case 1:
                acqConfig.setTotalSamples(jSliderSoundVelocityNSamples.getValue());
                acqConfig.getSelectedHardwareParameter("Type of experiment").setParameterValue("Sound Vel");
                acqConfig.getSelectedHardwareParameter("Piston start").setParameterValue("" + jSliderSoundVelocityPiston.getValue());
                acqConfig.getSelectedHardwareParameter("Piston end").setParameterValue("" + jSliderSoundVelocityPiston.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency start").setParameterValue("" + jSliderSoundVelocityFrequency.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency end").setParameterValue("" + jSliderSoundVelocityFrequency.getValue());
                acqConfig.getSelectedHardwareParameter("Wave form").setParameterValue("" + jComboBoxSoundVelocityWaveForm.getSelectedIndex());
                break;
            case 2:
                acqConfig.setTotalSamples(jSliderStatSoundINSamples.getValue());
                acqConfig.getSelectedHardwareParameter("Type of experiment").setParameterValue("Vary Piston");
                acqConfig.getSelectedHardwareParameter("Piston start").setParameterValue("" + jSliderStatSoundIPistonInitial.getValue());
                acqConfig.getSelectedHardwareParameter("Piston end").setParameterValue("" + jSliderStatSoundIPistonEnd.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency start").setParameterValue("" + jSliderStatSoundIFrequency.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency end").setParameterValue("" + jSliderStatSoundIFrequency.getValue());
                acqConfig.getSelectedHardwareParameter("Wave form").setParameterValue("" + 0);
                break;
            case 3:
                acqConfig.setTotalSamples(jSliderStatSoundIINSamples.getValue());
                acqConfig.getSelectedHardwareParameter("Type of experiment").setParameterValue("Vary Freq");
                acqConfig.getSelectedHardwareParameter("Piston start").setParameterValue("" + jSliderStatSoundIIPiston.getValue());
                acqConfig.getSelectedHardwareParameter("Piston end").setParameterValue("" + jSliderStatSoundIIPiston.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency start").setParameterValue("" + jSliderStatSoundIIFrequencyInitial.getValue());
                acqConfig.getSelectedHardwareParameter("Frequency end").setParameterValue("" + jSliderStatSoundIIFrequencyEnd.getValue());
                acqConfig.getSelectedHardwareParameter("Wave form").setParameterValue("" + 0);
                break;
            default:
                return;
        }

        String status = "";
        if (jRadioButtonSoundVelocityMic2.isSelected()) {
            status += "1";
        } else {
            status += "0";
        }
        if (jRadioButtonSoundVelocityMic3.isSelected()) {
            status += "1";
        } else {
            status += "0";
        }
        if (jRadioButtonSoundVelocityMic4.isSelected()) {
            status += "1";
        } else {
            status += "0";
        }
        if (jCheckBoxSoundVelocityHeat.isSelected()) {
            status += "1";
        } else {
            status += "0";
        }
        acqConfig.getSelectedHardwareParameter("Status").setParameterValue(status);
        acqConfig.getSelectedHardwareParameter("Calibration").setParameterValue("1");
        for (int i = 0; i < acqConfig.getSelectedHardwareParameters().length; i++) {
            System.out.println(acqConfig.getSelectedHardwareParameters(i).getParameterName() + "="
                    + acqConfig.getSelectedHardwareParameters(i).getParameterValue());
        }
        fireICustomizerListenerDone();
}//GEN-LAST:event_jButtonOKActionPerformed

    private void jSliderSoundVelocityNSamplesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSoundVelocityNSamplesStateChanged
        jTextFieldSoundVelocityNSamples.setText("" + jSliderSoundVelocityNSamples.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderSoundVelocityNSamplesStateChanged

    private void jSliderStatSoundINSamplesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundINSamplesStateChanged
        jTextFieldStatSoundINSamples.setText("" + jSliderStatSoundINSamples.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundINSamplesStateChanged

    private void jSliderStatSoundIINSamplesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIINSamplesStateChanged
        jTextFieldStatSoundIINSamples.setText("" + jSliderStatSoundIINSamples.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundIINSamplesStateChanged

    private void jSliderSoundVelocityPistonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSoundVelocityPistonStateChanged
        jTextFieldSoundVelocityPiston.setText("" + jSliderSoundVelocityPiston.getValue());
    }//GEN-LAST:event_jSliderSoundVelocityPistonStateChanged

    private void jSliderStatSoundIPistonInitialStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIPistonInitialStateChanged
        jTextFieldStatSoundIPistonInitial.setText("" + jSliderStatSoundIPistonInitial.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundIPistonInitialStateChanged

    private void jSliderStatSoundIPistonEndStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIPistonEndStateChanged
        jTextFieldStatSoundIPistonEnd.setText("" + jSliderStatSoundIPistonEnd.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundIPistonEndStateChanged

    private void jSliderStatSoundIFrequencyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIFrequencyStateChanged
        jTextFieldStatSoundIFrequency.setText("" + jSliderStatSoundIFrequency.getValue());
    }//GEN-LAST:event_jSliderStatSoundIFrequencyStateChanged

    private void jSliderStatSoundIIPistonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIIPistonStateChanged
        jTextFieldStatSoundIIPiston.setText("" + jSliderStatSoundIIPiston.getValue());
    }//GEN-LAST:event_jSliderStatSoundIIPistonStateChanged

    private void jSliderStatSoundIIFrequencyInitialStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIIFrequencyInitialStateChanged
        jTextFieldStatSoundIIFrequencyInitial.setText("" + jSliderStatSoundIIFrequencyInitial.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundIIFrequencyInitialStateChanged

    private void jSliderStatSoundIIFrequencyEndStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStatSoundIIFrequencyEndStateChanged
        jTextFieldStatSoundIIFrequencyEnd.setText("" + jSliderStatSoundIIFrequencyEnd.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderStatSoundIIFrequencyEndStateChanged

    private void jSliderSoundVelocityFrequencyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderSoundVelocityFrequencyStateChanged
        jTextFieldSoundVelocityFrequency.setText("" + jSliderSoundVelocityFrequency.getValue());
        checkMaxSamples();
    }//GEN-LAST:event_jSliderSoundVelocityFrequencyStateChanged

    private void analyseTextField(javax.swing.JTextField jtf, javax.swing.JSlider js) {
        String strValue = jtf.getText().trim();
        int value = 0;
        try {
            value = Integer.parseInt(strValue);
            if (value < js.getMinimum() || value > js.getMaximum()) {
                jtf.setText("" + js.getValue());
                return;
            }
            js.setValue(value);
        } catch (NumberFormatException nfe) {
            jtf.setText("" + js.getValue());
        }
    }

    private void checkMaxSamples() {
        int maxSamples = 0;
        int minSamples = 0;
        int protocol = jTabbedPaneStatSound.getSelectedIndex() + 1;

        if (maxSamples == 0) {
            maxSamples = 1;
        }
        switch (protocol) {
            case 1:
                minSamples = Math.round(MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG / jSliderSoundVelocityNSamples.getValue());
                if ((jSliderSoundVelocityNSamples.getValue() * jSliderSoundVelocityFrequency.getValue()) > MIN_VALUE_FOR_VALID_SOUND_VELOCITY_CONFIG) {
                    jLabelSoundVelocityNSamplesAlert.setEnabled(false);
                    jButtonOK.setEnabled(true);
                } else {
                    jLabelSoundVelocityNSamplesAlert.setEnabled(true);
                    jButtonOK.setEnabled(false);
                }
                jLabelSoundVelocityNSamplesAlert.setText(MIN_SAMPLES_STR + minSamples);
                break;
            case 2:
                maxSamples = (int) (Math.abs(jSliderStatSoundIPistonInitial.getValue() - jSliderStatSoundIPistonEnd.getValue()) / 1.69231);
                if (jSliderStatSoundINSamples.getValue() > maxSamples) {
                    jLabelStatSoundINSamplesAlert.setEnabled(true);
                    jButtonOK.setEnabled(false);
                } else {
                    jLabelStatSoundINSamplesAlert.setEnabled(false);
                    jButtonOK.setEnabled(true);
                }
                jLabelStatSoundINSamplesAlert.setText(MAX_SAMPLES_STR + maxSamples);
                break;
            case 3:
                minSamples = Math.round((jSliderStatSoundIIFrequencyEnd.getValue() - jSliderStatSoundIIFrequencyInitial.getValue()) / 2);
                if (jSliderStatSoundIINSamples.getValue() >= minSamples) {
                    jLabelStatSoundIINSamplesAlert.setEnabled(false);
                    jButtonOK.setEnabled(true);
                } else {
                    jLabelStatSoundIINSamplesAlert.setEnabled(true);
                    jButtonOK.setEnabled(false);
                }
                jLabelStatSoundIINSamplesAlert.setText(MIN_SAMPLES_STR + minSamples);
                break;
            default:
                return;
        }
    }

    public static void main(String args[]) {
        javax.swing.JFrame jf = new javax.swing.JFrame();
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
        if (listenerList == null) {
            return;
        }
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
        if (listenerList == null) {
            return;
        }
        Object[] listeners = listenerList.getListenerList();
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

    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
        this.acqConfig = acqConfig;
        if (acqConfig == null) {
            return;
        }

    }

    public void setHardwareInfo(HardwareInfo hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }
}
