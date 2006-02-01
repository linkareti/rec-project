/*
 * PolaroidCustomizer.java
 *
 * Created on September 21, 2004, 3:16 PM
 */

package pt.utl.ist.elab.client.usb.cypress.polaroid;

/**
 *
 * @author  andre
 */


import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.impl.i18n.*;
import com.linkare.rec.data.synch.*;

public class PolaroidCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer
{
    
    /** Creates new form PolaroidCustomizer */
    public PolaroidCustomizer()
    {
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
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSliderPolFixo = new javax.swing.JSlider();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldPolFixo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jSliderPolVarIni = new javax.swing.JSlider();
        jPanel6 = new javax.swing.JPanel();
        jTextFieldPolVarIni = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jSliderPolVarEnd = new javax.swing.JSlider();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldPolVarEnd = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jCheckBoxLightPol = new javax.swing.JCheckBox();
        jCheckBoxCalib = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
        btnOK.setText(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.ok"));
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

        btnCancel.setText(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.cancel"));
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

        btnDefaults.setText(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.dftcfg.polaroid.title.1"));
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

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.GridLayout(3, 0));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.fixedpol")));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 66));
        jSliderPolFixo.setMajorTickSpacing(20);
        jSliderPolFixo.setMaximum(180);
        jSliderPolFixo.setPaintLabels(true);
        jSliderPolFixo.setPaintTicks(true);
        jSliderPolFixo.setMinimumSize(new java.awt.Dimension(300, 41));
        jSliderPolFixo.setPreferredSize(new java.awt.Dimension(300, 41));
        jSliderPolFixo.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSliderPolFixoStateChanged(evt);
            }
        });

        jPanel3.add(jSliderPolFixo, java.awt.BorderLayout.CENTER);

        jTextFieldPolFixo.setColumns(4);
        jTextFieldPolFixo.setText("50");
        jTextFieldPolFixo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                jTextFieldPolFixoFocusLost(evt);
            }
        });

        jPanel4.add(jTextFieldPolFixo);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel9.add(jPanel3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.movepolini")));
        jSliderPolVarIni.setMajorTickSpacing(20);
        jSliderPolVarIni.setMaximum(180);
        jSliderPolVarIni.setPaintLabels(true);
        jSliderPolVarIni.setPaintTicks(true);
        jSliderPolVarIni.setValue(20);
        jSliderPolVarIni.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSliderPolVarIniStateChanged(evt);
            }
        });

        jPanel5.add(jSliderPolVarIni, java.awt.BorderLayout.CENTER);

        jTextFieldPolVarIni.setColumns(4);
        jTextFieldPolVarIni.setText("20");
        jTextFieldPolVarIni.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                jTextFieldPolVarIniFocusLost(evt);
            }
        });

        jPanel6.add(jTextFieldPolVarIni);

        jPanel5.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel9.add(jPanel5);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel7.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.movepolend")));
        jSliderPolVarEnd.setMajorTickSpacing(20);
        jSliderPolVarEnd.setMaximum(180);
        jSliderPolVarEnd.setPaintLabels(true);
        jSliderPolVarEnd.setPaintTicks(true);
        jSliderPolVarEnd.setValue(120);
        jSliderPolVarEnd.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSliderPolVarEndStateChanged(evt);
            }
        });

        jPanel7.add(jSliderPolVarEnd, java.awt.BorderLayout.CENTER);

        jTextFieldPolVarEnd.setColumns(4);
        jTextFieldPolVarEnd.setText("120");
        jTextFieldPolVarEnd.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                jTextFieldPolVarEndFocusLost(evt);
            }
        });

        jPanel8.add(jTextFieldPolVarEnd);

        jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

        jPanel9.add(jPanel7);

        jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel10.setLayout(new java.awt.GridBagLayout());

        jCheckBoxLightPol.setText(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.pollight"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel10.add(jCheckBoxLightPol, gridBagConstraints);

        jCheckBoxCalib.setText(ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.lbl.calib"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel10.add(jCheckBoxCalib, gridBagConstraints);

        jPanel1.add(jPanel10, java.awt.BorderLayout.SOUTH);

        add(jPanel1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDefaultsActionPerformed
    {//GEN-HEADEREND:event_btnDefaultsActionPerformed
        jSliderPolFixo.setValue(90);
        jSliderPolVarIni.setValue(0);
        jSliderPolVarEnd.setValue(180);
        jCheckBoxLightPol.setSelected(false);
        jCheckBoxCalib.setSelected(false);
    }//GEN-LAST:event_btnDefaultsActionPerformed
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        fireICustomizerListenerCanceled();
    }//GEN-LAST:event_btnCancelActionPerformed
    
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOKActionPerformed
    {//GEN-HEADEREND:event_btnOKActionPerformed
        acqConfig.getSelectedHardwareParameter("PosIniPolMovel").setParameterValue("" + jSliderPolVarIni.getValue());
        acqConfig.getSelectedHardwareParameter("PosEndPolMovel").setParameterValue("" + jSliderPolVarEnd.getValue());
        acqConfig.getSelectedHardwareParameter("PosFixo").setParameterValue("" + jSliderPolFixo.getValue());
        acqConfig.getSelectedHardwareParameter("LuzPol").setParameterValue("" + ((jCheckBoxLightPol.isSelected()) ? 1 : 0));
        acqConfig.getSelectedHardwareParameter("Calib").setParameterValue("" + ((jCheckBoxCalib.isSelected()) ? 1 : 0));
        int nSamples = Math.abs(((jSliderPolVarEnd.getValue() - jSliderPolVarIni.getValue()) * 155) / 180);
        if(nSamples == 0)
        {
            nSamples = 1;
            acqConfig.getSelectedHardwareParameter("PosEndPolMovel").setParameterValue("" + jSliderPolVarEnd.getValue() + 2);
        }
        acqConfig.setTotalSamples(Math.abs(jSliderPolVarEnd.getValue() - jSliderPolVarIni.getValue()));
        fireICustomizerListenerDone();
    }//GEN-LAST:event_btnOKActionPerformed
    
    private void jTextFieldPolVarEndFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldPolVarEndFocusLost
    {//GEN-HEADEREND:event_jTextFieldPolVarEndFocusLost
        adjustSlider(jSliderPolVarEnd, jTextFieldPolVarEnd);
    }//GEN-LAST:event_jTextFieldPolVarEndFocusLost
    
    private void jTextFieldPolVarIniFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldPolVarIniFocusLost
    {//GEN-HEADEREND:event_jTextFieldPolVarIniFocusLost
        adjustSlider(jSliderPolVarIni, jTextFieldPolVarIni);
    }//GEN-LAST:event_jTextFieldPolVarIniFocusLost
    
    private void jTextFieldPolFixoFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldPolFixoFocusLost
    {//GEN-HEADEREND:event_jTextFieldPolFixoFocusLost
        adjustSlider(jSliderPolFixo, jTextFieldPolFixo);
    }//GEN-LAST:event_jTextFieldPolFixoFocusLost
    
    private void jSliderPolVarEndStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderPolVarEndStateChanged
    {//GEN-HEADEREND:event_jSliderPolVarEndStateChanged
        jTextFieldPolVarEnd.setText("" + jSliderPolVarEnd.getValue());
    }//GEN-LAST:event_jSliderPolVarEndStateChanged
    
    private void jSliderPolVarIniStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderPolVarIniStateChanged
    {//GEN-HEADEREND:event_jSliderPolVarIniStateChanged
        jTextFieldPolVarIni.setText("" + jSliderPolVarIni.getValue());
    }//GEN-LAST:event_jSliderPolVarIniStateChanged
    
    private void jSliderPolFixoStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderPolFixoStateChanged
    {//GEN-HEADEREND:event_jSliderPolFixoStateChanged
        jTextFieldPolFixo.setText("" + jSliderPolFixo.getValue());
    }//GEN-LAST:event_jSliderPolFixoStateChanged
    
    
    private void adjustSlider(javax.swing.JSlider slider, javax.swing.JTextField field)
    {
        int num = 0;
        try
        {
            num = Integer.parseInt(field.getText().trim());
        }
        catch(NumberFormatException nfe)
        {
            field.setText("" + slider.getValue());
        }
        if(num > slider.getMaximum() || num < slider.getMinimum())
            field.setText("" + slider.getValue());
        else
            slider.setValue(num);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        javax.swing.JFrame dummy = new javax.swing.JFrame();
        dummy.getContentPane().add(new PolaroidCustomizer(), java.awt.BorderLayout.CENTER);
        dummy.pack();
        dummy.show();
    }
    
    
    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    
    /** Registers ICustomizerListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addICustomizerListener(ICustomizerListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(ICustomizerListener.class, listener);
    }
    
    /** Removes ICustomizerListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeICustomizerListener(ICustomizerListener listener)
    {
        listenerList.remove(ICustomizerListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerCanceled()
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==ICustomizerListener.class)
            {
                ((ICustomizerListener)listeners[i+1]).canceled();
            }
        }
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerDone()
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==ICustomizerListener.class)
            {
                
                ((ICustomizerListener)listeners[i+1]).done();
            }
        }
    }
    
    
    private HardwareInfo hardwareInfo=null;
    private HardwareAcquisitionConfig acqConfig=null;
    
    public HardwareAcquisitionConfig getAcquisitionConfig()
    {
        return acqConfig;
    }
    
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig)
    {
        this.acqConfig=acqConfig;
        if(acqConfig!=null)
        {
            int posini = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("PosIniPolMovel"));
            jSliderPolVarIni.setValue(posini);
            int posend = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("PosEndPolMovel"));
            jSliderPolVarIni.setValue(posend);
            int posfixo = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("PosFixo"));
            jSliderPolVarIni.setValue(posfixo);
        }
    }
    
    public void setHardwareInfo(HardwareInfo hardwareInfo)
    {
        this.hardwareInfo=hardwareInfo;
    }
    
    protected HardwareInfo getHardwareInfo()
    {
        return this.hardwareInfo;
    }
    
    public javax.swing.JComponent getCustomizerComponent()
    {
        return this;
    }
    
    public javax.swing.ImageIcon getCustomizerIcon()
    {
        return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/usb/cypress/polaroid/resources/polaroid_iconified.png"));
    }
    
    public String getCustomizerTitle()
    {
        return ReCResourceBundle.findString("ReCExpPolaroid$rec.exp.polaroid.customizer.title");
    }
    
    public javax.swing.JMenuBar getMenuBar()
    {
        return null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefaults;
    private javax.swing.JButton btnOK;
    private javax.swing.JCheckBox jCheckBoxCalib;
    private javax.swing.JCheckBox jCheckBoxLightPol;
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
    private javax.swing.JSlider jSliderPolFixo;
    private javax.swing.JSlider jSliderPolVarEnd;
    private javax.swing.JSlider jSliderPolVarIni;
    private javax.swing.JTextField jTextFieldPolFixo;
    private javax.swing.JTextField jTextFieldPolVarEnd;
    private javax.swing.JTextField jTextFieldPolVarIni;
    // End of variables declaration//GEN-END:variables
    
}
