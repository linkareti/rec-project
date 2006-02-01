/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */


package pt.utl.ist.elab.client.serial.stamp.pv;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.data.synch.*;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import com.linkare.rec.impl.i18n.*;
/**
 *
 * @author  jp
 */
public class PVCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer
{
    
    
    /** Creates new form RadioactividadeCustomizer */
    public PVCustomizer()
    {
	initComponents();
	
	Hashtable slidersPosLabels=new Hashtable(4);
	slidersPosLabels.put(new Integer(2000),new JLabel("2.0"));
	slidersPosLabels.put(new Integer(3000),new JLabel("3.0"));
	slidersPosLabels.put(new Integer(4000),new JLabel("4.0"));
	slidersPosLabels.put(new Integer(5000),new JLabel("5.0"));
	
	sldPos1.setLabelTable(slidersPosLabels);
	sldPos2.setLabelTable(slidersPosLabels);
	DecimalFormat format=new DecimalFormat("0.0");
	format.setDecimalSeparatorAlwaysShown(true);
	format.setGroupingUsed(false);
	format.setMinimumFractionDigits(1);
	NumberFormatter formatterUserPos1=new NumberFormatter(format);
	NumberFormatter formatterUserPos2=new NumberFormatter(format);
	
	//formatterUserPos1.setAllowsInvalid(false);
	//formatterUserPos2.setAllowsInvalid(false);
	
	formatterUserPos1.setCommitsOnValidEdit(true);
	formatterUserPos2.setCommitsOnValidEdit(true);
	
	formatterUserPos1.setOverwriteMode(true);
	formatterUserPos2.setOverwriteMode(true);
	
	formatterUserPos1.install(this.tfPos1);
	formatterUserPos2.install(this.tfPos2);
        
        checkMaxNumSamples();
        checkMaxTime();
        checkPosOverlap();
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
        jPanel6 = new javax.swing.JPanel();
        sldNumSamples = new javax.swing.JSlider();
        tfNumSamples = new javax.swing.JTextField();
        lblErrorSamplesTooHigh = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        sldFreq = new javax.swing.JSlider();
        tfFreq = new javax.swing.JTextField();
        lblSamplingIntervalTooHigh = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        sldPos1 = new javax.swing.JSlider();
        sldPos2 = new javax.swing.JSlider();
        lblErrorVolsAreEqua = new javax.swing.JLabel();
        tfPos1 = new javax.swing.JFormattedTextField();
        tfPos2 = new javax.swing.JFormattedTextField();

        setLayout(new java.awt.BorderLayout());

        setMinimumSize(new java.awt.Dimension(350, 460));
        setPreferredSize(new java.awt.Dimension(350, 460));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(350, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 42));
        btnOK.setText("OK");
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

        btnCancel.setText("Cancel");
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

        btnDefaults.setText(ReCResourceBundle.findStringOrDefault("rec.exp.dftcfg.pv.title.1","Default Config"));
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
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.title2")));
        jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel6.setMinimumSize(new java.awt.Dimension(350, 80));
        jPanel6.setPreferredSize(new java.awt.Dimension(350, 80));
        sldNumSamples.setMajorTickSpacing(40);
        sldNumSamples.setMaximum(240);
        sldNumSamples.setMinorTickSpacing(23);
        sldNumSamples.setPaintLabels(true);
        sldNumSamples.setPaintTicks(true);
        sldNumSamples.setPaintTrack(false);
        sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamples.setMinimumSize(new java.awt.Dimension(250, 42));
        sldNumSamples.setPreferredSize(new java.awt.Dimension(250, 42));
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
        tfNumSamples.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfNumSamplesFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(tfNumSamples, gridBagConstraints);

        lblErrorSamplesTooHigh.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorSamplesTooHigh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.label2"));
        lblErrorSamplesTooHigh.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(lblErrorSamplesTooHigh, gridBagConstraints);

        jPanel3.add(jPanel6);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.title3")));
        jPanel1.setMinimumSize(new java.awt.Dimension(350, 80));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 80));
        sldFreq.setMajorTickSpacing(400);
        sldFreq.setMaximum(2000);
        sldFreq.setMinorTickSpacing(200);
        sldFreq.setPaintLabels(true);
        sldFreq.setPaintTicks(true);
        sldFreq.setPaintTrack(false);
        sldFreq.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldFreq.setMinimumSize(new java.awt.Dimension(255, 80));
        sldFreq.setPreferredSize(new java.awt.Dimension(255, 80));
        sldFreq.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sldFreqStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 10.0;
        jPanel1.add(sldFreq, gridBagConstraints);

        tfFreq.setColumns(4);
        tfFreq.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfFreq.setText("50");
        tfFreq.setMaximumSize(new java.awt.Dimension(30, 16));
        tfFreq.setMinimumSize(new java.awt.Dimension(30, 16));
        tfFreq.setPreferredSize(new java.awt.Dimension(48, 16));
        tfFreq.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfFreqFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(tfFreq, gridBagConstraints);

        lblSamplingIntervalTooHigh.setForeground(new java.awt.Color(255, 0, 0));
        lblSamplingIntervalTooHigh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSamplingIntervalTooHigh.setText(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.label3"));
        lblSamplingIntervalTooHigh.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(lblSamplingIntervalTooHigh, gridBagConstraints);
        lblSamplingIntervalTooHigh.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.label3","The time between samples"));

        jPanel3.add(jPanel1);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.title1")));
        jPanel5.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel5.setPreferredSize(new java.awt.Dimension(350, 160));
        sldPos1.setMajorTickSpacing(1000);
        sldPos1.setMaximum(5000);
        sldPos1.setMinimum(2000);
        sldPos1.setMinorTickSpacing(100);
        sldPos1.setPaintLabels(true);
        sldPos1.setPaintTicks(true);
        sldPos1.setPaintTrack(false);
        sldPos1.setSnapToTicks(true);
        sldPos1.setMinimumSize(new java.awt.Dimension(250, 42));
        sldPos1.setPreferredSize(new java.awt.Dimension(250, 42));
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 10.0;
        jPanel5.add(sldPos1, gridBagConstraints);

        sldPos2.setMajorTickSpacing(1000);
        sldPos2.setMaximum(5000);
        sldPos2.setMinimum(2000);
        sldPos2.setMinorTickSpacing(100);
        sldPos2.setPaintLabels(true);
        sldPos2.setPaintTicks(true);
        sldPos2.setPaintTrack(false);
        sldPos2.setSnapToTicks(true);
        sldPos2.setValue(5000);
        sldPos2.setMinimumSize(new java.awt.Dimension(250, 42));
        sldPos2.setPreferredSize(new java.awt.Dimension(250, 42));
        sldPos2.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sldPos2StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 10.0;
        jPanel5.add(sldPos2, gridBagConstraints);

        lblErrorVolsAreEqua.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorVolsAreEqua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorVolsAreEqua.setText(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.label1"));
        lblErrorVolsAreEqua.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel5.add(lblErrorVolsAreEqua, gridBagConstraints);

        tfPos1.setText("2.0");
        tfPos1.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfPos1FocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(tfPos1, gridBagConstraints);

        tfPos2.setText("5.0");
        tfPos2.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfPos2FocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(tfPos2, gridBagConstraints);

        add(jPanel5, java.awt.BorderLayout.NORTH);

    }//GEN-END:initComponents

    private void tfFreqFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfFreqFocusLost
    {//GEN-HEADEREND:event_tfFreqFocusLost
	String strFreq=tfFreq.getText();
	if(strFreq.trim().equals("")) return;
	try
	{
	    int Freq=Integer.parseInt(strFreq);
	    if(Freq<=sldFreq.getMaximum() && Freq>sldFreq.getMinimum())
		sldFreq.setValue(Freq);
	    else
		tfFreq.setText(""+sldFreq.getValue());
	}catch(Exception e)
	{
	    tfFreq.setText(""+sldFreq.getValue());
	}
	checkMaxTime();
    }//GEN-LAST:event_tfFreqFocusLost

    private void tfNumSamplesFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfNumSamplesFocusLost
    {//GEN-HEADEREND:event_tfNumSamplesFocusLost
	String strNumSamples=tfNumSamples.getText();
	if(strNumSamples.trim().equals("")) return;
	try
	{
	    int numSamples=Integer.parseInt(strNumSamples);
	    if(numSamples<=sldNumSamples.getMaximum() && numSamples>sldNumSamples.getMinimum())
		sldNumSamples.setValue(numSamples);
	    else
		tfNumSamples.setText(""+sldNumSamples.getValue());
	}catch(Exception e)
	{
	    tfNumSamples.setText(""+sldNumSamples.getValue());
	}
	checkMaxNumSamples();
	checkMaxTime();
    }//GEN-LAST:event_tfNumSamplesFocusLost

    private void tfPos2FocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfPos2FocusLost
    {//GEN-HEADEREND:event_tfPos2FocusLost
	String strPos2=tfPos2.getText();
	if(strPos2.trim().equals("")) return;
	try
	{
	    int Pos2=(int)(Float.parseFloat(strPos2)*1000.F);
	    if(Pos2<=sldPos2.getMaximum() && Pos2>sldPos2.getMinimum())
		sldPos2.setValue(Pos2);
	    else
		tfPos2.setValue(new Float(((float)sldPos2.getValue()/1000.F)));
	}catch(Exception e)
	{
	    tfPos2.setValue(new Float(((float)sldPos2.getValue()/1000.F)));
	}
	checkPosOverlap();
	checkMaxNumSamples();
    }//GEN-LAST:event_tfPos2FocusLost

    private void tfPos1FocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_tfPos1FocusLost
    {//GEN-HEADEREND:event_tfPos1FocusLost
	String strPos1=tfPos1.getText();
	
	if(strPos1.trim().equals("")) return;
	try
	{
	    int Pos1=(int)(Float.parseFloat(strPos1)*1000.F);
	    if(Pos1<=sldPos1.getMaximum() && Pos1>sldPos1.getMinimum())
		sldPos1.setValue(Pos1);
	    else
		tfPos1.setValue(new Float(((float)sldPos1.getValue()/1000.F)));
	    
	}catch(Exception e)
	{
	    tfPos1.setValue(new Float(((float)sldPos1.getValue()/1000.F)));
	}
	checkPosOverlap();
	checkMaxNumSamples();
	
    }//GEN-LAST:event_tfPos1FocusLost
        
    private void sldPos1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldPos1StateChanged
    {//GEN-HEADEREND:event_sldPos1StateChanged
	
	tfPos1.setValue(new Float(((float)sldPos1.getValue()/1000.F)));
	checkPosOverlap();
	checkMaxNumSamples();
	
    }//GEN-LAST:event_sldPos1StateChanged
        
    private void sldPos2StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldPos2StateChanged
    {//GEN-HEADEREND:event_sldPos2StateChanged
	
	tfPos2.setValue(new Float(((float)sldPos2.getValue()/1000.F)));
	checkPosOverlap();
	checkMaxNumSamples();
	
    }//GEN-LAST:event_sldPos2StateChanged
        
    private void sldFreqStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldFreqStateChanged
    {//GEN-HEADEREND:event_sldFreqStateChanged
	
	if(sldFreq.getValue()==0)
	{
	    sldFreq.setValue(1);
	}
	tfFreq.setText(""+sldFreq.getValue());
	
	checkMaxTime();
    }//GEN-LAST:event_sldFreqStateChanged
        
    private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldNumSamplesStateChanged
    {//GEN-HEADEREND:event_sldNumSamplesStateChanged
	
	if(sldNumSamples.getValue()==0)
	{
	    sldNumSamples.setValue(1);
	    
	}
	tfNumSamples.setText(""+sldNumSamples.getValue());
	
	checkMaxNumSamples();
	checkMaxTime();
    }//GEN-LAST:event_sldNumSamplesStateChanged
    
    private void checkPosOverlap()
    {
	lblErrorVolsAreEqua.setEnabled(sldPos1.getValue()==sldPos2.getValue());
	btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled() && !lblSamplingIntervalTooHigh.isEnabled());
    }
    
    private void checkMaxNumSamples()
    {
	lblErrorSamplesTooHigh.setEnabled((float)Math.abs(sldPos2.getValue()-sldPos1.getValue())*80./1000.F<sldNumSamples.getValue());
	btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled() && !lblSamplingIntervalTooHigh.isEnabled());
	lblErrorSamplesTooHigh.setText(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.label2") + (int)Math.floor((float)Math.abs(sldPos2.getValue()-sldPos1.getValue())*80./1000.F));
    }
    
    public void checkMaxTime()
    {
	float maxValue=Math.min((float)sldFreq.getMaximum(),72000.F/(float)sldNumSamples.getValue());
	lblSamplingIntervalTooHigh.setEnabled(sldFreq.getValue()>maxValue);
	btnOK.setEnabled(!lblErrorVolsAreEqua.isEnabled() && !lblErrorSamplesTooHigh.isEnabled() && !lblSamplingIntervalTooHigh.isEnabled());
	lblSamplingIntervalTooHigh.setText(ReCResourceBundle.findString("ReCExpPV$rec.exp.customizer.label2") + (int)maxValue);
    }
	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDefaultsActionPerformed
	{//GEN-HEADEREND:event_btnDefaultsActionPerformed
	    sldNumSamples.setValue(150);
	    tfNumSamples.setText("150");
	    sldPos1.setValue(2000);
	    tfPos1.setValue(new Float(2.0));
	    sldPos2.setValue(5000);
	    tfPos2.setValue(new Float(5.0));
	    sldFreq.setValue(150);
	    tfFreq.setText(""+150);
	}//GEN-LAST:event_btnDefaultsActionPerformed
	
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
	{//GEN-HEADEREND:event_btnCancelActionPerformed
	    fireICustomizerListenerCanceled();
	}//GEN-LAST:event_btnCancelActionPerformed
	
	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOKActionPerformed
	{//GEN-HEADEREND:event_btnOKActionPerformed
	    int nsamples = sldNumSamples.getValue()==0?1:sldNumSamples.getValue();
            acqConfig.setTotalSamples(nsamples + 1);
	    acqConfig.getSelectedHardwareParameter("UserPosLow").setParameterValue(""+((float)sldPos1.getValue()/1000.F));
	    acqConfig.getSelectedHardwareParameter("UserPosHigh").setParameterValue(""+((float)sldPos2.getValue()/1000.F));
	    acqConfig.setSelectedFrequency(new Frequency((double)sldFreq.getValue(),hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
	    fireICustomizerListenerDone();
	}//GEN-LAST:event_btnOKActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDefaults;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblErrorSamplesTooHigh;
    private javax.swing.JLabel lblErrorVolsAreEqua;
    private javax.swing.JLabel lblSamplingIntervalTooHigh;
    private javax.swing.JSlider sldFreq;
    private javax.swing.JSlider sldNumSamples;
    private javax.swing.JSlider sldPos1;
    private javax.swing.JSlider sldPos2;
    private javax.swing.JTextField tfFreq;
    private javax.swing.JTextField tfNumSamples;
    private javax.swing.JFormattedTextField tfPos1;
    private javax.swing.JFormattedTextField tfPos2;
    // End of variables declaration//GEN-END:variables
	
	
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
                int nsamples = acqConfig.getTotalSamples() - 1; 
		sldNumSamples.setValue(nsamples);
		tfNumSamples.setText("" + nsamples);
		
		int freq=(int)acqConfig.getSelectedFrequency().getFrequency();
		sldFreq.setValue(freq);
		tfFreq.setText(""+freq);
		
		float pos1f=Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("UserPosLow"));
		int pos1=(int)Math.floor(pos1f*1000.F);
		sldPos1.setValue(pos1);
		tfPos1.setValue(new Float(pos1f));
		
		float pos2f=Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("UserPosHigh"));
		int pos2=(int)Math.floor(pos2f*1000.F);
		sldPos2.setValue(pos2);
		tfPos2.setValue(new Float(pos2f));
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
	    return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/pv/resources/pv_iconified.gif"));
	}
	
	public String getCustomizerTitle()
	{
	    return ReCResourceBundle.findString("ReCExpPV$rec.exp.pv.customizer.title");
	}
	
	public javax.swing.JMenuBar getMenuBar()
	{
	    return null;
	}
	
}

