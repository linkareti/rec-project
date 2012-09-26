/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.paschen;

/**
 *
 * @author Joao Loureiro - MEFT - IST
 */

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 */
public class PaschenCurveCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

    /**
     * Creates new form PaschenCurveCustomizer
     */
    public PaschenCurveCustomizer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanelNeedle1 = new javax.swing.JPanel();
        sldNeedle1 = new javax.swing.JSlider();
        tfNeedle1 = new javax.swing.JTextField();
        lblErrorPress1 = new javax.swing.JLabel();
        jPanelNeedle2 = new javax.swing.JPanel();
        sldNeedle2 = new javax.swing.JSlider();
        tfNeedle2 = new javax.swing.JTextField();
        lblErrorPress2 = new javax.swing.JLabel();
        jPanelVoltage1 = new javax.swing.JPanel();
        sldVoltage1 = new javax.swing.JSlider();
        tfVoltage1 = new javax.swing.JTextField();
        lblErrorVoltage1 = new javax.swing.JLabel();
        jPanelVoltage2 = new javax.swing.JPanel();
        sldVoltage2 = new javax.swing.JSlider();
        tfVoltage2 = new javax.swing.JTextField();
        lblErrorVoltage2 = new javax.swing.JLabel();
        jButtonDftcfg = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jPanelNumSamplesV = new javax.swing.JPanel();
        sldNumSamplesV = new javax.swing.JSlider();
        tfNumSamplesV = new javax.swing.JTextField();
        lblErrorSamplesTooHighV = new javax.swing.JLabel();
        jPanelNumSamplesP = new javax.swing.JPanel();
        sldNumSamplesP = new javax.swing.JSlider();
        tfNumSamplesP = new javax.swing.JTextField();
        lblErrorSamplesTooHighP = new javax.swing.JLabel();

        jPanelNeedle1.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.title1","pv$rec.exp.paschen.customizer.lbl.title1"))); // NOI18N
        jPanelNeedle1.setLayout(new java.awt.GridBagLayout());

        sldNeedle1.setMajorTickSpacing(20);
        sldNeedle1.setMinorTickSpacing(4);
        sldNeedle1.setPaintLabels(true);
        sldNeedle1.setPaintTicks(true);
        sldNeedle1.setValue(10);
        sldNeedle1.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNeedle1.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNeedle1.setPreferredSize(new java.awt.Dimension(250, 42));
        sldNeedle1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNeedle1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelNeedle1.add(sldNeedle1, gridBagConstraints);

        tfNeedle1.setColumns(3);
        tfNeedle1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNeedle1.setMaximumSize(new java.awt.Dimension(30, 18));
        tfNeedle1.setMinimumSize(new java.awt.Dimension(30, 18));
        tfNeedle1.setPreferredSize(new java.awt.Dimension(40, 25));
        tfNeedle1.setRequestFocusEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldNeedle1, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfNeedle1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfNeedle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNeedle1ActionPerformed(evt);
            }
        });
        tfNeedle1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNeedle1FocusLost(evt);
            }
        });
        jPanelNeedle1.add(tfNeedle1, new java.awt.GridBagConstraints());

        lblErrorPress1.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorPress1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorPress1.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.warning2","pv$rec.exp.paschen.customizer.lbl.warning2")); // NOI18N
        lblErrorPress1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelNeedle1.add(lblErrorPress1, gridBagConstraints);

        jPanelNeedle2.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.title5","pv$rec.exp.paschen.customizer.lbl.title5"))); // NOI18N
        jPanelNeedle2.setVerifyInputWhenFocusTarget(false);
        jPanelNeedle2.setLayout(new java.awt.GridBagLayout());

        sldNeedle2.setMajorTickSpacing(20);
        sldNeedle2.setMinorTickSpacing(4);
        sldNeedle2.setPaintLabels(true);
        sldNeedle2.setPaintTicks(true);
        sldNeedle2.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNeedle2.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNeedle2.setPreferredSize(new java.awt.Dimension(250, 42));
        sldNeedle2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNeedle2StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelNeedle2.add(sldNeedle2, gridBagConstraints);

        tfNeedle2.setColumns(3);
        tfNeedle2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNeedle2.setMaximumSize(new java.awt.Dimension(30, 18));
        tfNeedle2.setMinimumSize(new java.awt.Dimension(30, 18));
        tfNeedle2.setPreferredSize(new java.awt.Dimension(40, 25));
        tfNeedle2.setRequestFocusEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldNeedle2, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfNeedle2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfNeedle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNeedle2ActionPerformed(evt);
            }
        });
        tfNeedle2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNeedle2FocusLost(evt);
            }
        });
        jPanelNeedle2.add(tfNeedle2, new java.awt.GridBagConstraints());

        lblErrorPress2.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorPress2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorPress2.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.customizer.label2","pv$rec.exp.customizer.label2")); // NOI18N
        lblErrorPress2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelNeedle2.add(lblErrorPress2, gridBagConstraints);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("tenta/resourses/messages"); // NOI18N
        jPanelVoltage1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("rec.exp.paschen.customizer.lbl.title2"))); // NOI18N
        jPanelVoltage1.setLayout(new java.awt.GridBagLayout());

        sldVoltage1.setMajorTickSpacing(400);
        sldVoltage1.setMaximum(2000);
        sldVoltage1.setMinorTickSpacing(80);
        sldVoltage1.setPaintLabels(true);
        sldVoltage1.setPaintTicks(true);
        sldVoltage1.setValue(0);
        sldVoltage1.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldVoltage1.setMinimumSize(new java.awt.Dimension(250, 42));
        sldVoltage1.setPreferredSize(new java.awt.Dimension(250, 42));
        sldVoltage1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldVoltage1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelVoltage1.add(sldVoltage1, gridBagConstraints);

        tfVoltage1.setColumns(3);
        tfVoltage1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfVoltage1.setMaximumSize(new java.awt.Dimension(30, 16));
        tfVoltage1.setMinimumSize(new java.awt.Dimension(30, 16));
        tfVoltage1.setPreferredSize(new java.awt.Dimension(40, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldVoltage1, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfVoltage1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfVoltage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfVoltage1ActionPerformed(evt);
            }
        });
        tfVoltage1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfVoltage1FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelVoltage1.add(tfVoltage1, gridBagConstraints);

        lblErrorVoltage1.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorVoltage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorVoltage1.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.warning2","pv$rec.exp.paschen.customizer.lbl.warning2")); // NOI18N
        lblErrorVoltage1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelVoltage1.add(lblErrorVoltage1, gridBagConstraints);

        jPanelVoltage2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("rec.exp.paschen.customizer.lbl.title3"))); // NOI18N
        jPanelVoltage2.setLayout(new java.awt.GridBagLayout());

        sldVoltage2.setMajorTickSpacing(400);
        sldVoltage2.setMaximum(2000);
        sldVoltage2.setMinorTickSpacing(80);
        sldVoltage2.setPaintLabels(true);
        sldVoltage2.setPaintTicks(true);
        sldVoltage2.setValue(500);
        sldVoltage2.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldVoltage2.setMinimumSize(new java.awt.Dimension(250, 42));
        sldVoltage2.setPreferredSize(new java.awt.Dimension(250, 42));
        sldVoltage2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldVoltage2StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelVoltage2.add(sldVoltage2, gridBagConstraints);

        tfVoltage2.setColumns(3);
        tfVoltage2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfVoltage2.setMaximumSize(new java.awt.Dimension(30, 16));
        tfVoltage2.setMinimumSize(new java.awt.Dimension(30, 16));
        tfVoltage2.setPreferredSize(new java.awt.Dimension(40, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldVoltage2, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfVoltage2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfVoltage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfVoltage2ActionPerformed(evt);
            }
        });
        tfVoltage2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfVoltage2FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelVoltage2.add(tfVoltage2, gridBagConstraints);

        lblErrorVoltage2.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorVoltage2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorVoltage2.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.warning1","pv$rec.exp.paschen.customizer.lbl.warning1")); // NOI18N
        lblErrorVoltage2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelVoltage2.add(lblErrorVoltage2, gridBagConstraints);

        jButtonDftcfg.setText(bundle.getString("rec.exp.paschen.customizer.lbl.dftcfg")); // NOI18N

        jButtonCancel.setText(bundle.getString("rec.exp.paschen.customizer.lbl.cancel")); // NOI18N

        jButtonOk.setText(bundle.getString("rec.exp.paschen.customizer.lbl.ok")); // NOI18N

        jPanelNumSamplesV.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("rec.exp.paschen.customizer.lbl.title4"))); // NOI18N
        jPanelNumSamplesV.setLayout(new java.awt.GridBagLayout());

        sldNumSamplesV.setMajorTickSpacing(500);
        sldNumSamplesV.setMaximum(2000);
        sldNumSamplesV.setMinorTickSpacing(100);
        sldNumSamplesV.setPaintLabels(true);
        sldNumSamplesV.setPaintTicks(true);
        sldNumSamplesV.setValue(500);
        sldNumSamplesV.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamplesV.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNumSamplesV.setPreferredSize(new java.awt.Dimension(250, 42));
        sldNumSamplesV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumSamplesVStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelNumSamplesV.add(sldNumSamplesV, gridBagConstraints);

        tfNumSamplesV.setColumns(3);
        tfNumSamplesV.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNumSamplesV.setMaximumSize(new java.awt.Dimension(30, 16));
        tfNumSamplesV.setMinimumSize(new java.awt.Dimension(30, 16));
        tfNumSamplesV.setPreferredSize(new java.awt.Dimension(40, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldNumSamplesV, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfNumSamplesV, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfNumSamplesV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNumSamplesVActionPerformed(evt);
            }
        });
        tfNumSamplesV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNumSamplesVFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelNumSamplesV.add(tfNumSamplesV, gridBagConstraints);

        lblErrorSamplesTooHighV.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorSamplesTooHighV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorSamplesTooHighV.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.warning3","pv$rec.exp.paschen.customizer.lbl.warning3")); // NOI18N
        lblErrorSamplesTooHighV.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelNumSamplesV.add(lblErrorSamplesTooHighV, gridBagConstraints);

        jPanelNumSamplesP.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("rec.exp.paschen.customizer.lbl.title6"))); // NOI18N
        jPanelNumSamplesP.setLayout(new java.awt.GridBagLayout());

        sldNumSamplesP.setMajorTickSpacing(10);
        sldNumSamplesP.setMaximum(50);
        sldNumSamplesP.setMinorTickSpacing(2);
        sldNumSamplesP.setPaintLabels(true);
        sldNumSamplesP.setPaintTicks(true);
        sldNumSamplesP.setToolTipText("");
        sldNumSamplesP.setValue(10);
        sldNumSamplesP.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamplesP.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNumSamplesP.setPreferredSize(new java.awt.Dimension(250, 42));
        sldNumSamplesP.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumSamplesPStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanelNumSamplesP.add(sldNumSamplesP, gridBagConstraints);

        tfNumSamplesP.setColumns(3);
        tfNumSamplesP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNumSamplesP.setMaximumSize(new java.awt.Dimension(30, 16));
        tfNumSamplesP.setMinimumSize(new java.awt.Dimension(30, 16));
        tfNumSamplesP.setPreferredSize(new java.awt.Dimension(40, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldNumSamplesP, org.jdesktop.beansbinding.ELProperty.create("${value}"), tfNumSamplesP, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tfNumSamplesP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNumSamplesPActionPerformed(evt);
            }
        });
        tfNumSamplesP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNumSamplesPFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelNumSamplesP.add(tfNumSamplesP, gridBagConstraints);

        lblErrorSamplesTooHighP.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorSamplesTooHighP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorSamplesTooHighP.setText(ReCResourceBundle.findStringOrDefault("pv$rec.exp.paschen.customizer.lbl.warning4","pv$rec.exp.paschen.customizer.lbl.warning4")); // NOI18N
        lblErrorSamplesTooHighP.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelNumSamplesP.add(lblErrorSamplesTooHighP, gridBagConstraints);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(54, 54, 54)
                        .add(jButtonDftcfg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelNeedle1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelNumSamplesV, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(jPanelVoltage1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanelNumSamplesP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanelVoltage2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanelNeedle2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 350, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanelNeedle1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelNeedle2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanelVoltage1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelVoltage2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelNumSamplesV, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelNumSamplesP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonOk)
                    .add(jButtonCancel)
                    .add(jButtonDftcfg))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void sldNeedle1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNeedle1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldNeedle1StateChanged

    private void tfNeedle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNeedle1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNeedle1ActionPerformed

    private void tfNeedle1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNeedle1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNeedle1FocusLost

    private void sldNeedle2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNeedle2StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldNeedle2StateChanged

    private void tfNeedle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNeedle2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNeedle2ActionPerformed

    private void tfNeedle2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNeedle2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNeedle2FocusLost

    private void sldVoltage1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldVoltage1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldVoltage1StateChanged

    private void tfVoltage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfVoltage1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfVoltage1ActionPerformed

    private void tfVoltage1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfVoltage1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfVoltage1FocusLost

    private void sldVoltage2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldVoltage2StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldVoltage2StateChanged

    private void tfVoltage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfVoltage2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfVoltage2ActionPerformed

    private void tfVoltage2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfVoltage2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfVoltage2FocusLost

    private void sldNumSamplesVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNumSamplesVStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldNumSamplesVStateChanged

    private void tfNumSamplesVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNumSamplesVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumSamplesVActionPerformed

    private void tfNumSamplesVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNumSamplesVFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumSamplesVFocusLost

    private void sldNumSamplesPStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNumSamplesPStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sldNumSamplesPStateChanged

    private void tfNumSamplesPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNumSamplesPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumSamplesPActionPerformed

    private void tfNumSamplesPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNumSamplesPFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumSamplesPFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDftcfg;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JPanel jPanelNeedle1;
    private javax.swing.JPanel jPanelNeedle2;
    private javax.swing.JPanel jPanelNumSamplesP;
    private javax.swing.JPanel jPanelNumSamplesV;
    private javax.swing.JPanel jPanelVoltage1;
    private javax.swing.JPanel jPanelVoltage2;
    private javax.swing.JLabel lblErrorPress1;
    private javax.swing.JLabel lblErrorPress2;
    private javax.swing.JLabel lblErrorSamplesTooHighP;
    private javax.swing.JLabel lblErrorSamplesTooHighV;
    private javax.swing.JLabel lblErrorVoltage1;
    private javax.swing.JLabel lblErrorVoltage2;
    private javax.swing.JSlider sldNeedle1;
    private javax.swing.JSlider sldNeedle2;
    private javax.swing.JSlider sldNumSamplesP;
    private javax.swing.JSlider sldNumSamplesV;
    private javax.swing.JSlider sldVoltage1;
    private javax.swing.JSlider sldVoltage2;
    private javax.swing.JTextField tfNeedle1;
    private javax.swing.JTextField tfNeedle2;
    private javax.swing.JTextField tfNumSamplesP;
    private javax.swing.JTextField tfNumSamplesV;
    private javax.swing.JTextField tfVoltage1;
    private javax.swing.JTextField tfVoltage2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

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
    

    public synchronized void removeICustomizerListener(final ICustomizerListener listener) {
            listenerList.remove(ICustomizerListener.class, listener);
    }

    public javax.swing.JComponent getCustomizerComponent() {
        return this;
    }

    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }

    private HardwareInfo hardwareInfo = null;
    private HardwareAcquisitionConfig acqConfig = null;
    
    public void setHardwareInfo(final HardwareInfo hardwareInfo) {
        this.HardwareInfo = hardwareInfo;
    }
     

    public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
            this.acqConfig = acqConfig; 
            if (acqConfig != null) {
            		
            	final int vsamples = acqConfig.getTotalSamples();
    			sldNumSamplesV.setValue(vsamples);
    			tfNumSamplesV.setText("" + vsamples);
    			
    			final int psamples = acqConfig.getTotalSamples();
    			sldNumSamplesP.setValue(psamples);
    			tfNumSamplesP.setText("" + psamples);
            	
    			final int volt1 = acqConfig.getTotalSamples();
    			sldVoltage1.setValue(volt1);
    			tfVoltage1.setText("" + volt1);
    			
    			final int volt2 = acqConfig.getTotalSamples();
    			sldVoltage2.setValue(volt2);
    			tfVoltage2.setText("" + volt2);
    			
    			final int press1 = acqConfig.getTotalSamples();
    			sldNeedle1.setValue(press1);
    			tfNeedle1.setText("" + press1);
    			
    			final int press2 = acqConfig.getTotalSamples();
    			sldNeedle2.setValue(press2);
    			tfNeedle2.setText("" + press2);
    			
            }
            
            String h1CurrentSelectedValue = acqConfig.getSelectedHardwareParameterValue("h1");
            heigthFallValue1.setText(h1CurrentSelectedValue);
            sliderHeigthFallOne.setValue(Integer.valueOf(heigthFallValue1.getText()));
            //...more code after this ...
    }    
    
    public HardwareAcquisitionConfig getAcquisitionConfig() {
    	return acqConfig;    
    }

    public javax.swing.ImageIcon getCustomizerIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/paschen/resources/icon.gif"));
    }

    public java.lang.String getCustomizerTitle() {
        return ReCResourceBundle.findStringOrDefault("paschen$rec.exp.pachen.customizer.title","paschen$rec.exp.paschen.customizer.title");
    }




}
