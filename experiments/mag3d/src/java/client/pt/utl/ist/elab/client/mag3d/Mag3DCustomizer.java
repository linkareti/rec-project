/*
 * Mag3DCustomizer.java
 *
 * Created on 13 de Outubro de 2017
 */
package pt.utl.ist.elab.client.mag3d;

import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author André Sancho Duarte - IPFN
 */
public class Mag3DCustomizer extends AbstractCustomizer {

    /**
     *
     */
    private static final long serialVersionUID = -775367629564418034L;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0");

    static {
        decimalFormat.setDecimalSeparatorAlwaysShown(true);
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMinimumFractionDigits(1);
    }

    /**
     * Creates new form RadioactividadeCustomizer
     */
    public Mag3DCustomizer() {
        initComponents();

        Hashtable<Integer, JLabel> slidersPosLabels = new Hashtable<Integer, JLabel>(4);
        slidersPosLabels.put(new Integer(3000), new JLabel("3.0"));
        slidersPosLabels.put(new Integer(5000), new JLabel("5.0"));
        slidersPosLabels.put(new Integer(6000), new JLabel("6.0"));
        slidersPosLabels.put(new Integer(7500), new JLabel("7.5"));

        sldPos1.setLabelTable(slidersPosLabels);
        sldPos2.setLabelTable(slidersPosLabels);

        NumberFormatter formatterUserPos1 = new NumberFormatter(decimalFormat);
        NumberFormatter formatterUserPos2 = new NumberFormatter(decimalFormat);

        // formatterUserPos1.setAllowsInvalid(false);
        // formatterUserPos2.setAllowsInvalid(false);
        formatterUserPos1.setCommitsOnValidEdit(true);
        formatterUserPos2.setCommitsOnValidEdit(true);

        formatterUserPos1.setOverwriteMode(true);
        formatterUserPos2.setOverwriteMode(true);

        formatterUserPos1.install(this.tfPos1);
        formatterUserPos2.install(this.tfPos2);

        checkPosOverlap();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDefaults = new javax.swing.JButton();
        slidersPanel = new javax.swing.JPanel();
        positionPanel = new javax.swing.JPanel();
        sldPos1 = new javax.swing.JSlider();
        sldPos2 = new javax.swing.JSlider();
        lblErrorPosOverlap = new javax.swing.JLabel();
        tfPos1 = new javax.swing.JFormattedTextField();
        tfPos2 = new javax.swing.JFormattedTextField();
        samplesPanel = new javax.swing.JPanel();
        sldNumSamples = new javax.swing.JSlider();
        tfNumSamples = new javax.swing.JTextField();
        lblErrorTooManySamples = new javax.swing.JLabel();
        coilCurrentPanel = new javax.swing.JPanel();
        sldCoilCurrent = new javax.swing.JSlider();
        tfCoilCurrent = new javax.swing.JTextField();
        anglePanel = new javax.swing.JPanel();
        sldAngle = new javax.swing.JSlider();
        tfAngle = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(350, 460));
        setPreferredSize(new java.awt.Dimension(350, 460));
        setLayout(null);

        buttonPanel.setMinimumSize(new java.awt.Dimension(350, 42));
        buttonPanel.setPreferredSize(new java.awt.Dimension(350, 42));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 5));

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        buttonPanel.add(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        buttonPanel.add(btnCancel);

        jLabel1.setText("   ");
        buttonPanel.add(jLabel1);

        btnDefaults.setText(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.dftcfg.mag3d.title.1","mag3d$rec.exp.dftcfg.mag3d.title.1")); // NOI18N
        btnDefaults.setActionCommand("<rec.exp.dftcfg.mag3d.title.1>");
        btnDefaults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefaultsActionPerformed(evt);
            }
        });
        buttonPanel.add(btnDefaults);

        add(buttonPanel);
        buttonPanel.setBounds(0, 412, 372, 42);

        slidersPanel.setMinimumSize(new java.awt.Dimension(700, 320));
        slidersPanel.setLayout(new java.awt.GridLayout(2, 2));

        positionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.customizer.title1","mag3d$rec.exp.customizer.title1"))); // NOI18N
        positionPanel.setMinimumSize(new java.awt.Dimension(350, 180));
        positionPanel.setPreferredSize(new java.awt.Dimension(350, 180));
        positionPanel.setLayout(new java.awt.GridBagLayout());

        sldPos1.setMajorTickSpacing(61);
        sldPos1.setMaximum(320);
        sldPos1.setMinimum(15);
        sldPos1.setPaintLabels(true);
        sldPos1.setPaintTicks(true);
        sldPos1.setPaintTrack(false);
        sldPos1.setValue(15);
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
        positionPanel.add(sldPos1, gridBagConstraints);

        sldPos2.setMajorTickSpacing(61);
        sldPos2.setMaximum(320);
        sldPos2.setMinimum(15);
        sldPos2.setPaintLabels(true);
        sldPos2.setPaintTicks(true);
        sldPos2.setPaintTrack(false);
        sldPos2.setToolTipText("");
        sldPos2.setValue(120);
        sldPos2.setMinimumSize(new java.awt.Dimension(250, 42));
        sldPos2.setPreferredSize(new java.awt.Dimension(250, 42));
        sldPos2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldPos2StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 10.0;
        positionPanel.add(sldPos2, gridBagConstraints);

        lblErrorPosOverlap.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorPosOverlap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorPosOverlap.setText(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.customizer.label1","mag3d$rec.exp.customizer.label1")); // NOI18N
        lblErrorPosOverlap.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        positionPanel.add(lblErrorPosOverlap, gridBagConstraints);

        tfPos1.setText("15");
        tfPos1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfPos1FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        positionPanel.add(tfPos1, gridBagConstraints);

        tfPos2.setText("120");
        tfPos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPos2ActionPerformed(evt);
            }
        });
        tfPos2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfPos2FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        positionPanel.add(tfPos2, gridBagConstraints);

        slidersPanel.add(positionPanel);

        samplesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.customizer.title4","mag3d$rec.exp.customizer.title4"))); // NOI18N
        samplesPanel.setMinimumSize(new java.awt.Dimension(350, 80));
        samplesPanel.setPreferredSize(new java.awt.Dimension(350, 180));
        samplesPanel.setLayout(new java.awt.GridBagLayout());

        sldNumSamples.setMajorTickSpacing(200);
        sldNumSamples.setMaximum(800);
        sldNumSamples.setMinorTickSpacing(50);
        sldNumSamples.setPaintLabels(true);
        sldNumSamples.setPaintTicks(true);
        sldNumSamples.setPaintTrack(false);
        sldNumSamples.setToolTipText("");
        sldNumSamples.setValue(300);
        sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamples.setMinimumSize(new java.awt.Dimension(255, 80));
        sldNumSamples.setPreferredSize(new java.awt.Dimension(255, 80));
        sldNumSamples.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumSamplesStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 10.0;
        samplesPanel.add(sldNumSamples, gridBagConstraints);

        tfNumSamples.setColumns(4);
        tfNumSamples.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNumSamples.setText("300");
        tfNumSamples.setMaximumSize(new java.awt.Dimension(30, 16));
        tfNumSamples.setMinimumSize(new java.awt.Dimension(30, 16));
        tfNumSamples.setPreferredSize(new java.awt.Dimension(48, 16));
        tfNumSamples.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNumSamplesActionPerformed(evt);
            }
        });
        tfNumSamples.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNumSamplesFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        samplesPanel.add(tfNumSamples, gridBagConstraints);

        lblErrorTooManySamples.setForeground(new java.awt.Color(255, 0, 0));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/mag3d/resources/messages"); // NOI18N
        lblErrorTooManySamples.setText(bundle.getString("rec.exp.customizer.label2")); // NOI18N
        lblErrorTooManySamples.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        samplesPanel.add(lblErrorTooManySamples, gridBagConstraints);

        slidersPanel.add(samplesPanel);
        samplesPanel.getAccessibleContext().setAccessibleName(bundle.getString("rec.exp.customizer.title4")); // NOI18N

        coilCurrentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.customizer.title2","mag3d$rec.exp.customizer.title2"))); // NOI18N
        coilCurrentPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        coilCurrentPanel.setMinimumSize(new java.awt.Dimension(350, 180));
        coilCurrentPanel.setPreferredSize(new java.awt.Dimension(350, 180));
        coilCurrentPanel.setLayout(new java.awt.GridBagLayout());

        sldCoilCurrent.setMajorTickSpacing(20);
        sldCoilCurrent.setMinorTickSpacing(5);
        sldCoilCurrent.setPaintLabels(true);
        sldCoilCurrent.setPaintTicks(true);
        sldCoilCurrent.setPaintTrack(false);
        sldCoilCurrent.setToolTipText("");
        sldCoilCurrent.setValue(100);
        sldCoilCurrent.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldCoilCurrent.setMinimumSize(new java.awt.Dimension(250, 42));
        sldCoilCurrent.setPreferredSize(new java.awt.Dimension(250, 42));
        sldCoilCurrent.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldCoilCurrentStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        coilCurrentPanel.add(sldCoilCurrent, gridBagConstraints);

        tfCoilCurrent.setColumns(3);
        tfCoilCurrent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfCoilCurrent.setText("100");
        tfCoilCurrent.setToolTipText("");
        tfCoilCurrent.setMaximumSize(new java.awt.Dimension(30, 16));
        tfCoilCurrent.setMinimumSize(new java.awt.Dimension(30, 16));
        tfCoilCurrent.setPreferredSize(new java.awt.Dimension(37, 16));
        tfCoilCurrent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfCoilCurrentFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        coilCurrentPanel.add(tfCoilCurrent, gridBagConstraints);

        slidersPanel.add(coilCurrentPanel);

        anglePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.customizer.title3","mag3d$rec.exp.customizer.title3"))); // NOI18N
        anglePanel.setMinimumSize(new java.awt.Dimension(350, 80));
        anglePanel.setPreferredSize(new java.awt.Dimension(350, 180));
        anglePanel.setLayout(new java.awt.GridBagLayout());

        sldAngle.setMajorTickSpacing(15);
        sldAngle.setMaximum(90);
        sldAngle.setMinorTickSpacing(5);
        sldAngle.setPaintLabels(true);
        sldAngle.setPaintTicks(true);
        sldAngle.setPaintTrack(false);
        sldAngle.setToolTipText("");
        sldAngle.setValue(0);
        sldAngle.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldAngle.setMinimumSize(new java.awt.Dimension(255, 80));
        sldAngle.setPreferredSize(new java.awt.Dimension(255, 80));
        sldAngle.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldAngleStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 10.0;
        anglePanel.add(sldAngle, gridBagConstraints);

        tfAngle.setColumns(4);
        tfAngle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfAngle.setText("0");
        tfAngle.setMaximumSize(new java.awt.Dimension(30, 16));
        tfAngle.setMinimumSize(new java.awt.Dimension(30, 16));
        tfAngle.setPreferredSize(new java.awt.Dimension(48, 16));
        tfAngle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAngleActionPerformed(evt);
            }
        });
        tfAngle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfAngleFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        anglePanel.add(tfAngle, gridBagConstraints);

        slidersPanel.add(anglePanel);

        add(slidersPanel);
        slidersPanel.setBounds(0, 0, 790, 360);
    }// </editor-fold>//GEN-END:initComponents

    private void tfAngleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAngleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAngleActionPerformed

    private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNumSamplesStateChanged
        if (sldNumSamples.getValue() == 0) { // checks if the number of samples is 0
            sldNumSamples.setValue(1);
        }
        tfNumSamples.setText("" + sldNumSamples.getValue());
        checkNsamples();
    }//GEN-LAST:event_sldNumSamplesStateChanged

    private void tfNumSamplesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNumSamplesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNumSamplesActionPerformed

    private void tfNumSamplesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNumSamplesFocusLost
        String strNsamples = tfNumSamples.getText();
        if (strNsamples.trim().equals("")) {
            return;
        }
        try {
            int nsamples = Integer.parseInt(strNsamples);
            if (nsamples <= sldNumSamples.getMaximum() && nsamples > 0) {
                sldNumSamples.setValue(nsamples);
            } else {
                tfNumSamples.setText("" + sldNumSamples.getValue());
            }
        } catch (Exception e) {
            tfNumSamples.setText("" + sldNumSamples.getValue());
        }
    }//GEN-LAST:event_tfNumSamplesFocusLost

    private void tfPos2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tfPos2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_tfPos2ActionPerformed

    private void tfAngleFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfAngleFocusLost
    {// GEN-HEADEREND:event_tfAngleFocusLost
        String strAngle = tfAngle.getText();
        if (strAngle.trim().equals("")) {
            return;
        }
        try {
            int angle = Integer.parseInt(strAngle);
            if (angle <= sldAngle.getMaximum() && angle >= sldAngle.getMinimum()) {
                sldAngle.setValue(angle);
            } else {
                tfAngle.setText("" + sldAngle.getValue());
            }
        } catch (Exception e) {
            tfAngle.setText("" + sldAngle.getValue());
        }
    }// GEN-LAST:event_tfAngleFocusLost

    private void tfCoilCurrentFocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfCoilCurrentFocusLost
    {// GEN-HEADEREND:event_tfCoilCurrentFocusLost
        String strCoilCurr = tfCoilCurrent.getText();
        if (strCoilCurr.trim().equals("")) {
            return;
        }
        try {
            int coilCurrent = Integer.parseInt(strCoilCurr);
            if (coilCurrent <= sldCoilCurrent.getMaximum() && coilCurrent > sldCoilCurrent.getMinimum()) {
                sldCoilCurrent.setValue(coilCurrent);
            } else {
                tfCoilCurrent.setText("" + sldCoilCurrent.getValue());
            }
        } catch (Exception e) {
            tfCoilCurrent.setText("" + sldCoilCurrent.getValue());
        }
    }// GEN-LAST:event_tfCoilCurrentFocusLost

    private void tfPos2FocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfPos2FocusLost
    {// GEN-HEADEREND:event_tfPos2FocusLost
        String strPos2 = tfPos2.getText();
        if (strPos2.trim().equals("")) {
            return;
        }
        try {
            int Pos2 = Integer.parseInt(strPos2);
            if (Pos2 <= sldPos2.getMaximum() && Pos2 >= sldPos2.getMinimum()) {
                sldPos2.setValue(Pos2);
            } else {
                tfPos2.setValue(sldPos2.getValue());
            }
        } catch (Exception e) {
            tfPos2.setValue(sldPos2.getValue());
        }
        checkPosOverlap();

    }// GEN-LAST:event_tfPos2FocusLost

    private void tfPos1FocusLost(java.awt.event.FocusEvent evt)// GEN-FIRST:event_tfPos1FocusLost
    {// GEN-HEADEREND:event_tfPos1FocusLost
        String strPos1 = tfPos1.getText();

        if (strPos1.trim().equals("")) {
            return;
        }
        try {
            int Pos1 = Integer.parseInt(strPos1);
            if (Pos1 <= sldPos1.getMaximum() && Pos1 >= sldPos1.getMinimum()) {
                sldPos1.setValue(Pos1);
            } else {
                tfPos1.setValue(sldPos1.getValue());
            }

        } catch (Exception e) {
            tfPos1.setValue(sldPos1.getValue());
        }
        checkPosOverlap();

    }// GEN-LAST:event_tfPos1FocusLost

    private void sldPos1StateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldPos1StateChanged
    {// GEN-HEADEREND:event_sldPos1StateChanged

        tfPos1.setValue(sldPos1.getValue());
        checkPosOverlap();

    }// GEN-LAST:event_sldPos1StateChanged

    private void sldPos2StateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldPos2StateChanged
    {// GEN-HEADEREND:event_sldPos2StateChanged

        tfPos2.setValue(sldPos2.getValue());
        checkPosOverlap();
    }// GEN-LAST:event_sldPos2StateChanged

    private void sldAngleStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldAngleStateChanged
    {// GEN-HEADEREND:event_sldAngleStateChanged
        tfAngle.setText("" + sldAngle.getValue());

    }// GEN-LAST:event_sldAngleStateChanged

    private void sldCoilCurrentStateChanged(javax.swing.event.ChangeEvent evt)// GEN-FIRST:event_sldCoilCurrentStateChanged
    {// GEN-HEADEREND:event_sldCoilCurrentStateChanged

        if (sldCoilCurrent.getValue() == 0) {
            sldCoilCurrent.setValue(1);
        }
        tfCoilCurrent.setText("" + sldCoilCurrent.getValue());

    }// GEN-LAST:event_sldCoilCurrentStateChanged

    private void checkPosOverlap() {
        lblErrorPosOverlap.setEnabled(sldPos1.getValue() > sldPos2.getValue()); // checks if the initial postion is greater than the final
        btnOK.setEnabled(!lblErrorPosOverlap.isEnabled());
        checkNsamples(); // since the maximum number of samples depends on the positions, we need to always check 
    }

    private void checkNsamples() {
        lblErrorTooManySamples.setEnabled(sldNumSamples.getValue() > (sldPos2.getValue() - sldPos1.getValue()) * 3 + 1); // checks if the number of samples is too big 
        btnOK.setEnabled(!lblErrorTooManySamples.isEnabled());
    }

    private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
    {// GEN-HEADEREND:event_btnDefaultsActionPerformed
        sldPos1.setValue(15);
        tfPos1.setValue(15);
        sldPos2.setValue(120);
        tfPos2.setValue(120);
        sldCoilCurrent.setValue(100);
        tfCoilCurrent.setText("100");
        sldAngle.setValue(0);
        tfAngle.setText("0");
        sldNumSamples.setValue(100);
        tfNumSamples.setText("100");
    }// GEN-LAST:event_btnDefaultsActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
    {// GEN-HEADEREND:event_btnCancelActionPerformed
        fireICustomizerListenerCanceled();
    }// GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
    {// GEN-HEADEREND:event_btnOKActionPerformed
        int posInit=sldPos1.getValue();
        int posFinal=sldPos2.getValue();
        int nsamples=sldNumSamples.getValue();
        double tbs=(double)((posFinal-posInit)*3.0+1.0)/(double)nsamples;
        
        getAcquisitionConfig().getSelectedHardwareParameter("protocol").setParameterValue("1"); // experiment expects protocol identifier, but there is only one for now
        getAcquisitionConfig().getSelectedHardwareParameter("current").setParameterValue("" + sldCoilCurrent.getValue());
        getAcquisitionConfig().getSelectedHardwareParameter("angle").setParameterValue("" + sldAngle.getValue());
        getAcquisitionConfig().getSelectedHardwareParameter("pos_init").setParameterValue("" + posInit);
        getAcquisitionConfig().getSelectedHardwareParameter("pos_final").setParameterValue("" + posFinal);
        getAcquisitionConfig().getSelectedHardwareParameter("n_samples").setParameterValue("" + sldNumSamples.getValue());
        getAcquisitionConfig().setTotalSamples(sldNumSamples.getValue());

        getAcquisitionConfig().setSelectedFrequency(
                new Frequency( tbs, getHardwareInfo().getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), getHardwareInfo().getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
        fireICustomizerListenerDone();
    }// GEN-LAST:event_btnOKActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel anglePanel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefaults;
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel coilCurrentPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblErrorPosOverlap;
    private javax.swing.JLabel lblErrorTooManySamples;
    private javax.swing.JPanel positionPanel;
    private javax.swing.JPanel samplesPanel;
    private javax.swing.JSlider sldAngle;
    private javax.swing.JSlider sldCoilCurrent;
    private javax.swing.JSlider sldNumSamples;
    private javax.swing.JSlider sldPos1;
    private javax.swing.JSlider sldPos2;
    private javax.swing.JPanel slidersPanel;
    private javax.swing.JTextField tfAngle;
    private javax.swing.JTextField tfCoilCurrent;
    private javax.swing.JTextField tfNumSamples;
    private javax.swing.JFormattedTextField tfPos1;
    private javax.swing.JFormattedTextField tfPos2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
        super.setHardwareAcquisitionConfig(acqConfig);
        if (acqConfig != null) {
            int nsamples = acqConfig.getTotalSamples();
            sldNumSamples.setValue(nsamples);
            tfNumSamples.setText("" + nsamples);
//
//            int freq = (int) acqConfig.getSelectedFrequency().getFrequency();

            int ccurr = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("current"));
            sldCoilCurrent.setValue(ccurr);
            tfCoilCurrent.setText("" + ccurr);

            int angle = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("angle"));
            sldAngle.setValue(angle);
            tfAngle.setText("" + angle);

            int pos1 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("pos_init"));
            sldPos1.setValue(pos1);
            tfPos1.setValue(pos1);

            int pos2 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("pos_final"));
            sldPos2.setValue(pos2);
            tfPos2.setValue(pos2);
        }
    }

    public javax.swing.ImageIcon getCustomizerIcon() {
        return new javax.swing.ImageIcon(getClass().getResource(
                "/pt/utl/ist/elab/client/mag3d/resources/mag3d_iconified.gif"));
    }

    public String getCustomizerTitle() {
        return ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.mag3d.customizer.title",
                "mag3d$rec.exp.mag3d.customizer.title");
    }
}