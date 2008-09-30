/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */


package pt.utl.ist.elab.client.serial.stamp.scuba;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ScubaCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {
    
    
    /** Creates new form RadioactividadeCustomizer */
    public ScubaCustomizer() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDefaults = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        checkCalib = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        sldNumSamples = new javax.swing.JSlider();
        tfNumSamples = new javax.swing.JTextField();
        lblErrorSamplesTooHigh = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        sldPos1 = new javax.swing.JSlider();
        sldPos2 = new javax.swing.JSlider();
        lblErrorDeepsAreEqual = new javax.swing.JLabel();
        tfPos1 = new javax.swing.JTextField();
        tfPos2 = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        setMinimumSize(new java.awt.Dimension(350, 460));
        setPreferredSize(new java.awt.Dimension(350, 460));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
        btnOK.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.scuba.lbl.ok"));
        btnOK.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOKActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnOK, gridBagConstraints);

        btnCancel.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.scuba.lbl.cancel"));
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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

        btnDefaults.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.dftcfg.scuba.title.1"));
        btnDefaults.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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
        jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.1")));
        checkCalib.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.1.check.1"));
        jPanel1.add(checkCalib);

        jPanel3.add(jPanel1);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.2")));
        jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel6.setMinimumSize(new java.awt.Dimension(350, 100));
        jPanel6.setPreferredSize(new java.awt.Dimension(350, 100));
        sldNumSamples.setMajorTickSpacing(100);
        sldNumSamples.setMaximum(500);
        sldNumSamples.setMinorTickSpacing(50);
        sldNumSamples.setPaintLabels(true);
        sldNumSamples.setPaintTicks(true);
        sldNumSamples.setPaintTrack(false);
        sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamples.setMinimumSize(new java.awt.Dimension(250, 60));
        sldNumSamples.setPreferredSize(new java.awt.Dimension(250, 60));
        sldNumSamples.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
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
        tfNumSamples.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                tfNumSamplesKeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(tfNumSamples, gridBagConstraints);

        lblErrorSamplesTooHigh.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorSamplesTooHigh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.2.label.1"));
        lblErrorSamplesTooHigh.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(lblErrorSamplesTooHigh, gridBagConstraints);

        jPanel3.add(jPanel6);

        add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.3")));
        jPanel5.setMinimumSize(new java.awt.Dimension(250, 350));
        jPanel5.setPreferredSize(new java.awt.Dimension(250, 350));
        sldPos1.setMajorTickSpacing(150);
        sldPos1.setMaximum(900);
        sldPos1.setMinorTickSpacing(10);
        sldPos1.setOrientation(javax.swing.JSlider.VERTICAL);
        sldPos1.setPaintLabels(true);
        sldPos1.setPaintTicks(true);
        sldPos1.setPaintTrack(false);
        sldPos1.setSnapToTicks(true);
        sldPos1.setValue(900);
        sldPos1.setMaximumSize(new java.awt.Dimension(100, 32767));
        sldPos1.setMinimumSize(new java.awt.Dimension(100, 250));
        sldPos1.setPreferredSize(new java.awt.Dimension(100, 250));
        sldPos1.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sldPos1StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 10.0;
        jPanel5.add(sldPos1, gridBagConstraints);

        sldPos2.setMajorTickSpacing(150);
        sldPos2.setMaximum(900);
        sldPos2.setMinorTickSpacing(10);
        sldPos2.setOrientation(javax.swing.JSlider.VERTICAL);
        sldPos2.setPaintLabels(true);
        sldPos2.setPaintTicks(true);
        sldPos2.setPaintTrack(false);
        sldPos2.setSnapToTicks(true);
        sldPos2.setValue(0);
        sldPos2.setMaximumSize(new java.awt.Dimension(100, 32767));
        sldPos2.setMinimumSize(new java.awt.Dimension(100, 250));
        sldPos2.setPreferredSize(new java.awt.Dimension(100, 250));
        sldPos2.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sldPos2StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 10.0;
        jPanel5.add(sldPos2, gridBagConstraints);

        lblErrorDeepsAreEqual.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorDeepsAreEqual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorDeepsAreEqual.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.3.label.1"));
        lblErrorDeepsAreEqual.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel5.add(lblErrorDeepsAreEqual, gridBagConstraints);

        tfPos1.setColumns(3);
        tfPos1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfPos1.setText("900");
        tfPos1.setMinimumSize(new java.awt.Dimension(33, 20));
        tfPos1.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfPos1FocusLost(evt);
            }
        });
        tfPos1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                tfPos1KeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(tfPos1, gridBagConstraints);

        tfPos2.setColumns(3);
        tfPos2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfPos2.setText("0");
        tfPos2.setMinimumSize(new java.awt.Dimension(33, 20));
        tfPos2.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfPos2FocusLost(evt);
            }
        });
        tfPos2.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                tfPos2KeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel5.add(tfPos2, gridBagConstraints);

        add(jPanel5, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    private void tfPos2FocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfPos2FocusLost
    {//GEN-HEADEREND:event_tfPos2FocusLost
        String strPos2=tfPos2.getText();
        if(strPos2.trim().equals("")) return;
        try {
            int Pos2=Integer.parseInt(strPos2);
            if(Pos2<=sldPos2.getMaximum() && Pos2>sldPos2.getMinimum())
                sldPos2.setValue(Pos2);
            else
                tfPos2.setText(""+sldPos2.getValue());
        }catch(Exception e) {
            tfPos2.setText(""+sldPos2.getValue());
        }
        checkPosOverlap();
        checkMaxNumSamples();
    }//GEN-LAST:event_tfPos2FocusLost
    
    private void tfPos1FocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfPos1FocusLost
    {//GEN-HEADEREND:event_tfPos1FocusLost
        String strPos1=tfPos1.getText();
        
        if(strPos1.trim().equals("")) return;
        try {
            int Pos1=Integer.parseInt(strPos1);
            if(Pos1<=sldPos1.getMaximum() && Pos1>sldPos1.getMinimum())
                sldPos1.setValue(Pos1);
            else
                tfPos1.setText(""+sldPos1.getValue());
            
        }catch(Exception e) {
            tfPos1.setText(""+sldPos1.getValue());
        }
        checkPosOverlap();
        checkMaxNumSamples();
    }//GEN-LAST:event_tfPos1FocusLost
    
    private void tfPos1KeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_tfPos1KeyReleased
    {//GEN-HEADEREND:event_tfPos1KeyReleased
        
    }//GEN-LAST:event_tfPos1KeyReleased
    
    private void sldPos1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldPos1StateChanged
    {//GEN-HEADEREND:event_sldPos1StateChanged
        
        tfPos1.setText(""+sldPos1.getValue());
        checkPosOverlap();
        checkMaxNumSamples();
        
    }//GEN-LAST:event_sldPos1StateChanged
    
    private void tfPos2KeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_tfPos2KeyReleased
    {//GEN-HEADEREND:event_tfPos2KeyReleased
        
    }//GEN-LAST:event_tfPos2KeyReleased
    
    private void sldPos2StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldPos2StateChanged
    {//GEN-HEADEREND:event_sldPos2StateChanged
        
        tfPos2.setText(""+sldPos2.getValue());
        checkPosOverlap();
        checkMaxNumSamples();
        
    }//GEN-LAST:event_sldPos2StateChanged
    
    private void tfNumSamplesKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_tfNumSamplesKeyReleased
    {//GEN-HEADEREND:event_tfNumSamplesKeyReleased
        String strNumSamples=tfNumSamples.getText();
        if(strNumSamples.trim().equals("")) return;
        try {
            int numSamples=Integer.parseInt(strNumSamples);
            if(numSamples<=sldNumSamples.getMaximum() && numSamples>sldNumSamples.getMinimum())
                sldNumSamples.setValue(numSamples);
            else
                tfNumSamples.setText(""+sldNumSamples.getValue());
        }catch(Exception e) {
            tfNumSamples.setText(""+sldNumSamples.getValue());
        }
        checkMaxNumSamples();
    }//GEN-LAST:event_tfNumSamplesKeyReleased
    
    private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldNumSamplesStateChanged
    {//GEN-HEADEREND:event_sldNumSamplesStateChanged
        
        if(sldNumSamples.getValue()==0) {
            sldNumSamples.setValue(1);
            
        }
        tfNumSamples.setText(""+sldNumSamples.getValue());
        
        checkMaxNumSamples();
    }//GEN-LAST:event_sldNumSamplesStateChanged
    
    private void checkPosOverlap() {
        lblErrorDeepsAreEqual.setEnabled(sldPos1.getValue()==sldPos2.getValue());
        btnOK.setEnabled(!lblErrorDeepsAreEqual.isEnabled() && !lblErrorSamplesTooHigh.isEnabled());
    }
    
    private void checkMaxNumSamples() {
        lblErrorSamplesTooHigh.setEnabled((float)Math.abs(sldPos2.getValue()-sldPos1.getValue())*4.5<(float)sldNumSamples.getValue());
        btnOK.setEnabled(!lblErrorDeepsAreEqual.isEnabled() && !lblErrorSamplesTooHigh.isEnabled());
        lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpScuba$rec.exp.customizer.title.2.label.1") + Math.min(500,(int)Math.floor((float)Math.abs(sldPos2.getValue()-sldPos1.getValue())*4.5)));
    }
    
	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDefaultsActionPerformed
	{//GEN-HEADEREND:event_btnDefaultsActionPerformed
            sldNumSamples.setValue(150);
            tfNumSamples.setText("150");
            sldPos1.setValue(900);
            tfPos1.setText("900");
            sldPos2.setValue(0);
            tfPos2.setText("0");
	}//GEN-LAST:event_btnDefaultsActionPerformed
        
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
	{//GEN-HEADEREND:event_btnCancelActionPerformed
            fireICustomizerListenerCanceled();
	}//GEN-LAST:event_btnCancelActionPerformed
        
	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOKActionPerformed
	{//GEN-HEADEREND:event_btnOKActionPerformed
            acqConfig.setTotalSamples(sldNumSamples.getValue()==0?1:sldNumSamples.getValue());
            acqConfig.getSelectedHardwareParameter("xini").setParameterValue(""+sldPos1.getValue());
            acqConfig.getSelectedHardwareParameter("xfin").setParameterValue(""+sldPos2.getValue());
            if(checkCalib.isSelected())
                acqConfig.getSelectedHardwareParameter("calib").setParameterValue("1");
            else
                acqConfig.getSelectedHardwareParameter("calib").setParameterValue("0");
            
            fireICustomizerListenerDone();
	}//GEN-LAST:event_btnOKActionPerformed
        
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefaults;
    private javax.swing.JButton btnOK;
    private javax.swing.JCheckBox checkCalib;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblErrorDeepsAreEqual;
    private javax.swing.JLabel lblErrorSamplesTooHigh;
    private javax.swing.JSlider sldNumSamples;
    private javax.swing.JSlider sldPos1;
    private javax.swing.JSlider sldPos2;
    private javax.swing.JTextField tfNumSamples;
    private javax.swing.JTextField tfPos1;
    private javax.swing.JTextField tfPos2;
    // End of variables declaration//GEN-END:variables
        
        
        /** Utility field used by event firing mechanism. */
        private javax.swing.event.EventListenerList listenerList =  null;
        
        
        /** Registers ICustomizerListener to receive events.
         * @param listener The listener to register.
         */
        public synchronized void addICustomizerListener(ICustomizerListener listener) {
            if (listenerList == null ) {
                listenerList = new javax.swing.event.EventListenerList();
            }
            listenerList.add(ICustomizerListener.class, listener);
        }
        
        /** Removes ICustomizerListener from the list of listeners.
         * @param listener The listener to remove.
         */
        public synchronized void removeICustomizerListener(ICustomizerListener listener) {
            listenerList.remove(ICustomizerListener.class, listener);
        }
        
        /** Notifies all registered listeners about the event.
         *
         * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
         */
        private void fireICustomizerListenerCanceled() {
            if (listenerList == null) return;
            Object[] listeners = listenerList.getListenerList();
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==ICustomizerListener.class) {
                    ((ICustomizerListener)listeners[i+1]).canceled();
                }
            }
        }
        
        /** Notifies all registered listeners about the event.
         *
         * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
         */
        private void fireICustomizerListenerDone() {
            if (listenerList == null) return;
            Object[] listeners = listenerList.getListenerList();
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==ICustomizerListener.class) {
                    
                    ((ICustomizerListener)listeners[i+1]).done();
                }
            }
        }
        
        
        private HardwareInfo hardwareInfo=null;
        private HardwareAcquisitionConfig acqConfig=null;
        
        public HardwareAcquisitionConfig getAcquisitionConfig() {
            return acqConfig;
        }
        
        public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
            this.acqConfig=acqConfig;
            if(acqConfig!=null) {
                sldNumSamples.setValue(acqConfig.getTotalSamples());
                tfNumSamples.setText(""+acqConfig.getTotalSamples());
                
                
                int xini=Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("xini"));
                sldPos1.setValue(xini);
                tfPos1.setText(""+xini);
                
                int xfin=Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("xfin"));
                sldPos2.setValue(xfin);
                tfPos2.setText(""+xfin);
                
                
                int calib=Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("calib"));
                if(calib==1)
                    checkCalib.setSelected(true);
                else
                    checkCalib.setSelected(false);
            }
        }
        
        public void setHardwareInfo(HardwareInfo hardwareInfo) {
            this.hardwareInfo=hardwareInfo;
        }
        
        protected HardwareInfo getHardwareInfo() {
            return this.hardwareInfo;
        }
        
        public javax.swing.JComponent getCustomizerComponent() {
            return this;
        }
        
        public javax.swing.ImageIcon getCustomizerIcon() {
            return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/scuba/resources/scuba_iconified.gif"));
        }
        
        public String getCustomizerTitle() {
            return "Scuba Experiment Configuration Utility";
        }
        
        public javax.swing.JMenuBar getMenuBar() {
            return null;
        }
        
        /** Inseri um m�todo main para testar o customizer
         *  Teste Ok
         *  17-10-2004
         * @author Sergio Pino
         */
        public static void main(String args[])
        {
            javax.swing.JFrame jf = new javax.swing.JFrame();
            ScubaCustomizer sc = new ScubaCustomizer();
            jf.getContentPane().add(sc,java.awt.BorderLayout.CENTER);
            jf.pack();
            jf.show();
        }

}

