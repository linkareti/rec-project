package pt.utl.ist.elab.client.condensadorcilindrico;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.ChannelParameter;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.SamplesNumScale;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import java.awt.Color;
import java.text.MessageFormat;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class CCCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

    private static final long serialVersionUID = 1374781526147471266L;
    public static int MINIMUM_NUMBER_OF_POINTS;
    private static ChannelParameter startPositionChannelParam;
    private static ChannelParameter finalPositionChannelParam;
    private static SamplesNumScale numPointsSamplingScale;
    private static final int INIT_POS_DEFAULT_VALUE = 50;
    private static final int FINAL_POS_DEFAULT_VALUE = 150;
    private static final int NUM_POINTS_DEFAULT_VALUE = 10;

    /** Creates new form RadioactividadeCustomizer */
    public CCCustomizer() {
        initComponents();
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

        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnDefaultConfig = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanelInitialPosition = new javax.swing.JPanel();
        sldInitPos = new javax.swing.JSlider();
        tfInitPos = new javax.swing.JTextField();
        jPanelFinalPosition = new javax.swing.JPanel();
        sldFinalPos = new javax.swing.JSlider();
        tfFinalPos = new javax.swing.JTextField();
        jPanelNumberOfPoints = new javax.swing.JPanel();
        sldNumPoints = new javax.swing.JSlider();
        tfNumPoints = new javax.swing.JTextField();
        jLStatus = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(350, 460));
        setPreferredSize(new java.awt.Dimension(350, 460));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/condensadorcilindrico/resources/messages"); // NOI18N
        btnOK.setText(bundle.getString("rec.exp.cc.customizer.btn.OK")); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnOK, gridBagConstraints);

        btnCancel.setText(bundle.getString("rec.exp.cc.customizer.btn.cancel")); // NOI18N
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

        jLabel3.setText("    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jLabel3, gridBagConstraints);

        btnDefaultConfig.setText(bundle.getString("rec.exp.cc.customizer.btn.default")); // NOI18N
        btnDefaultConfig.setToolTipText("Set the default values for this application");
        btnDefaultConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultConfigActionPerfomed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnDefaultConfig, gridBagConstraints);
        btnDefaultConfig.getAccessibleContext().setAccessibleDescription("");

        add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanelInitialPosition.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findString("condensadorcilindrico$rec.exp.customizer.title1"))); // NOI18N
        jPanelInitialPosition.setMinimumSize(new java.awt.Dimension(350, 106));
        jPanelInitialPosition.setPreferredSize(new java.awt.Dimension(350, 106));
        jPanelInitialPosition.setLayout(new java.awt.GridBagLayout());

        sldInitPos.setMajorTickSpacing(50);
        sldInitPos.setMaximum(0);
        sldInitPos.setMinorTickSpacing(20);
        sldInitPos.setPaintLabels(true);
        sldInitPos.setPaintTicks(true);
        sldInitPos.setMinimumSize(new java.awt.Dimension(250, 42));
        sldInitPos.setPreferredSize(new java.awt.Dimension(250, 42));
        sldInitPos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldInitPosStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 17, 30, 0);
        jPanelInitialPosition.add(sldInitPos, gridBagConstraints);

        tfInitPos.setColumns(3);
        tfInitPos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfInitPos.setText("50");
        tfInitPos.setMaximumSize(new java.awt.Dimension(30, 16));
        tfInitPos.setMinimumSize(new java.awt.Dimension(30, 16));
        tfInitPos.setPreferredSize(new java.awt.Dimension(37, 16));
        tfInitPos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfInitPosFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 6, 0, 17);
        jPanelInitialPosition.add(tfInitPos, gridBagConstraints);

        jPanel3.add(jPanelInitialPosition);
        jPanelInitialPosition.getAccessibleContext().setAccessibleName(bundle.getString("rec.exp.customizer.title1")); // NOI18N

        jPanelFinalPosition.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findString("condensadorcilindrico$rec.exp.customizer.title2"))); // NOI18N
        jPanelFinalPosition.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelFinalPosition.setMinimumSize(new java.awt.Dimension(350, 106));
        jPanelFinalPosition.setPreferredSize(new java.awt.Dimension(350, 106));
        jPanelFinalPosition.setLayout(new java.awt.GridBagLayout());

        sldFinalPos.setMajorTickSpacing(50);
        sldFinalPos.setMaximum(240);
        sldFinalPos.setMinorTickSpacing(23);
        sldFinalPos.setPaintLabels(true);
        sldFinalPos.setPaintTicks(true);
        sldFinalPos.setMinimumSize(new java.awt.Dimension(250, 42));
        sldFinalPos.setPreferredSize(new java.awt.Dimension(250, 42));
        sldFinalPos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldFinalPosStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 17, 30, 0);
        jPanelFinalPosition.add(sldFinalPos, gridBagConstraints);

        tfFinalPos.setColumns(3);
        tfFinalPos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfFinalPos.setText("50");
        tfFinalPos.setMaximumSize(new java.awt.Dimension(30, 16));
        tfFinalPos.setMinimumSize(new java.awt.Dimension(30, 16));
        tfFinalPos.setPreferredSize(new java.awt.Dimension(37, 16));
        tfFinalPos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfFinalPosFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 6, 0, 17);
        jPanelFinalPosition.add(tfFinalPos, gridBagConstraints);

        jPanel3.add(jPanelFinalPosition);
        jPanelFinalPosition.getAccessibleContext().setAccessibleName(bundle.getString("rec.exp.customizer.title2")); // NOI18N

        jPanelNumberOfPoints.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findString("condensadorcilindrico$rec.exp.customizer.title3"))); // NOI18N
        jPanelNumberOfPoints.setMinimumSize(new java.awt.Dimension(350, 106));
        jPanelNumberOfPoints.setPreferredSize(new java.awt.Dimension(350, 106));
        jPanelNumberOfPoints.setLayout(new java.awt.GridBagLayout());

        sldNumPoints.setMajorTickSpacing(100);
        sldNumPoints.setMaximum(2000);
        sldNumPoints.setMinorTickSpacing(200);
        sldNumPoints.setPaintLabels(true);
        sldNumPoints.setPaintTicks(true);
        sldNumPoints.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNumPoints.setPreferredSize(new java.awt.Dimension(250, 42));
        sldNumPoints.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumPointsStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 17, 30, 0);
        jPanelNumberOfPoints.add(sldNumPoints, gridBagConstraints);

        tfNumPoints.setColumns(3);
        tfNumPoints.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNumPoints.setText("50");
        tfNumPoints.setMaximumSize(new java.awt.Dimension(30, 16));
        tfNumPoints.setMinimumSize(new java.awt.Dimension(30, 16));
        tfNumPoints.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNumPointsFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 6, 0, 17);
        jPanelNumberOfPoints.add(tfNumPoints, gridBagConstraints);

        jPanel3.add(jPanelNumberOfPoints);
        jPanelNumberOfPoints.getAccessibleContext().setAccessibleName(bundle.getString("rec.exp.customizer.title3")); // NOI18N

        jLStatus.setForeground(new java.awt.Color(238, 238, 238));
        jLStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLStatus.setText("THIS TEXT is important for layout purposes do not delete this");
        jLStatus.setAlignmentX(0.5F);
        jLStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLStatus);
        jLStatus.getAccessibleContext().setAccessibleName("JLStatus");

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void sldFinalPosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldFinalPosStateChanged
        tfFinalPos.setText(String.valueOf(sldFinalPos.getValue()));
        ensureNumberOfPointsSmallerThanPosDiff();
    }//GEN-LAST:event_sldFinalPosStateChanged

    private void sldInitPosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldInitPosStateChanged
        tfInitPos.setText(String.valueOf(sldInitPos.getValue()));
        ensureNumberOfPointsSmallerThanPosDiff();
    }//GEN-LAST:event_sldInitPosStateChanged

    private void sldNumPointsStateChanged(javax.swing.event.ChangeEvent evt) {
        if (sldNumPoints.getValue() < MINIMUM_NUMBER_OF_POINTS) {
            sldNumPoints.setValue(MINIMUM_NUMBER_OF_POINTS);
        }
        tfNumPoints.setText(String.valueOf(sldNumPoints.getValue()));
        ensureNumberOfPointsSmallerThanPosDiff();
    }

    private void defaultConfigActionPerfomed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultConfigActionPerfomed
        sldInitPos.setValue(INIT_POS_DEFAULT_VALUE);
        sldFinalPos.setValue(FINAL_POS_DEFAULT_VALUE);
        sldNumPoints.setValue(NUM_POINTS_DEFAULT_VALUE);
    }//GEN-LAST:event_defaultConfigActionPerfomed

    private void tfInitPosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfInitPosFocusLost
        validateAndAssignInputtedValue(tfInitPos, sldInitPos);
        ensureNumberOfPointsSmallerThanPosDiff();
    }//GEN-LAST:event_tfInitPosFocusLost

    private void tfFinalPosFocusLost(final java.awt.event.FocusEvent evt) {
        validateAndAssignInputtedValue(tfFinalPos, sldFinalPos);
        ensureNumberOfPointsSmallerThanPosDiff();
    }

    private void tfNumPointsFocusLost(java.awt.event.FocusEvent evt) {
        validateAndAssignInputtedValue(tfNumPoints, sldNumPoints);
        ensureNumberOfPointsSmallerThanPosDiff();
    }

    private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt) {
        fireICustomizerListenerCanceled();
    }

    private void btnOKActionPerformed(final java.awt.event.ActionEvent evt) {
        if (!ensureNumberOfPointsSmallerThanPosDiff()) {
            return;
        }

        final ParameterConfig hwStartPosition = acqConfig.getSelectedHardwareParameter("StartPosition");
        hwStartPosition.setParameterValue(String.valueOf(sldInitPos.getValue()));

        final ParameterConfig hwEndPosition = acqConfig.getSelectedHardwareParameter("EndPosition");
        hwEndPosition.setParameterValue(String.valueOf(sldFinalPos.getValue()));

        acqConfig.setTotalSamples(sldNumPoints.getValue());
        
        fireICustomizerListenerDone();
    }

    private boolean ensureNumberOfPointsSmallerThanPosDiff() {
        final float initialPos = (670f/228f)*Float.parseFloat(tfInitPos.getText());
        final float finalPos = (670f/228f)*Float.parseFloat(tfFinalPos.getText());
        final int numPoints = Integer.parseInt(tfNumPoints.getText());
        
        int maxNumPoints=(int)(Math.abs(finalPos - initialPos));
        
        final boolean valid = numPoints <= maxNumPoints;
        
        
        
        if (!valid) {
            String errorMsg = ReCResourceBundle.findString("condensadorcilindrico$rec.exp.cc.customizer.validation.numberOfPointsSmallerThanPosDiff");
            int minNumPoints= MINIMUM_NUMBER_OF_POINTS;
            int maxNumPointsInterval = Math.max(maxNumPoints, minNumPoints);
            errorMsg = MessageFormat.format(errorMsg, minNumPoints, maxNumPointsInterval);
            jLStatus.setText(errorMsg);
            jLStatus.setForeground(Color.RED);
            btnOK.setEnabled(false);
        } else {
            btnOK.setEnabled(true);
            jLStatus.setForeground(new Color(238, 238, 238));
        }
        return valid;
    }

    private void validateAndAssignInputtedValue(final JTextField tfThatChangedValue, final JSlider sldAssociatedWithTf) {
        final String strNewValue = tfThatChangedValue.getText();
        if (strNewValue.trim().isEmpty()) {
            return;
        }
        try {
            final int value = Integer.parseInt(strNewValue);
            if (value <= sldAssociatedWithTf.getMaximum() && value >= sldAssociatedWithTf.getMinimum()) {
                sldAssociatedWithTf.setValue(value);
            } else {
                tfThatChangedValue.setText(String.valueOf(sldAssociatedWithTf.getValue()));
            }
        } catch (final Exception e) {
            tfThatChangedValue.setText(String.valueOf(sldAssociatedWithTf.getValue()));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefaultConfig;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelFinalPosition;
    private javax.swing.JPanel jPanelInitialPosition;
    private javax.swing.JPanel jPanelNumberOfPoints;
    private javax.swing.JSlider sldFinalPos;
    private javax.swing.JSlider sldInitPos;
    private javax.swing.JSlider sldNumPoints;
    private javax.swing.JTextField tfFinalPos;
    private javax.swing.JTextField tfInitPos;
    private javax.swing.JTextField tfNumPoints;
    // End of variables declaration//GEN-END:variables
    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList = null;
    private HardwareInfo hardwareInfo = null;
    private HardwareAcquisitionConfig acqConfig = null;

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

    @Override
    public HardwareAcquisitionConfig getAcquisitionConfig() {
        return acqConfig;
    }

    @Override
    public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
        this.acqConfig = acqConfig;

        if (acqConfig != null) {

            final int currentStartPosition = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("StartPosition"));
            sldInitPos.setValue(currentStartPosition);
            tfInitPos.setText("" + currentStartPosition);

            final int currentFinalPosition = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("EndPosition"));
            sldFinalPos.setValue(currentFinalPosition);
            tfFinalPos.setText(String.valueOf(currentFinalPosition));

            final int freq = (int) acqConfig.getTotalSamples();
            sldNumPoints.setValue(freq);
            tfNumPoints.setText(String.valueOf(freq));

        }
    }

    @Override
    public void setHardwareInfo(final HardwareInfo hardwareInfo) {
        this.hardwareInfo = hardwareInfo;

        if (hardwareInfo != null) {
            
            startPositionChannelParam = hardwareInfo.getHardwareParameter("StartPosition");
            finalPositionChannelParam = hardwareInfo.getHardwareParameter("EndPosition");
            numPointsSamplingScale = hardwareInfo.getSamplingScale();


            //Setting the initial value for the text field next to the slider
            final String initialValueForInitialPos = startPositionChannelParam.getSelectedParameterValue();
            tfInitPos.setText(initialValueForInitialPos);
            sldInitPos.setValue(Integer.valueOf(initialValueForInitialPos));

            // Setting the slider for the inital position and its label values
            final Integer maxInitPos = Integer.parseInt(startPositionChannelParam.getParameterSelectionList(1));
           
            sldInitPos.setMaximum(maxInitPos);
            sldInitPos.setMajorTickSpacing(Math.round(maxInitPos / 4));
            sldInitPos.getLabelTable().put(maxInitPos, new JLabel(maxInitPos.toString()));
            sldInitPos.setLabelTable(sldInitPos.getLabelTable());


            //Setting the initial value for the text field next to the slider
            final String initialValueForFinalPos = finalPositionChannelParam.getSelectedParameterValue();
            tfFinalPos.setText(initialValueForFinalPos);
            sldFinalPos.setValue(Integer.valueOf(initialValueForFinalPos));

            // Setting the slider for the final position and its label values
            final Integer maxFinalPos = Integer.parseInt(finalPositionChannelParam.getParameterSelectionList(1));
    
            sldFinalPos.setMaximum(maxFinalPos);
            sldFinalPos.setMajorTickSpacing(Math.round(maxFinalPos / 4));
            sldFinalPos.getLabelTable().put(maxFinalPos, new JLabel(maxFinalPos.toString()));
            sldFinalPos.setLabelTable(sldFinalPos.getLabelTable());


            // Setting the slider for the number of points and its label values
            final Integer maxNumPointsPos = numPointsSamplingScale.getMaxSamples();
            final Integer minNumPointsPos = numPointsSamplingScale.getMinSamples();

            // Setting the minumum number of points as a global variable so that it can be checked while validating the user input
            MINIMUM_NUMBER_OF_POINTS = minNumPointsPos;

            sldNumPoints.setMaximum(maxNumPointsPos);
            sldNumPoints.setMajorTickSpacing(Math.round(maxNumPointsPos / 4));
            sldNumPoints.getLabelTable().put(maxNumPointsPos, new JLabel(maxNumPointsPos.toString()));
            sldNumPoints.setLabelTable(sldNumPoints.getLabelTable());

            // Setting the initial value for the text field next to the number of points - note that we cannot retrieve this from the XML so we assume the minimum value is OK
            tfNumPoints.setText(String.valueOf(minNumPointsPos));
            
        }
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
        return new javax.swing.ImageIcon(ReCResourceBundle.findString("condensadorcilindrico$rec.exp.icon.cc"));
    }

    @Override
    public String getCustomizerTitle() {
        return ReCResourceBundle.findString("condensadorcilindrico$rec.exp.cc.customizer.title");
    }

    @Override
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
}