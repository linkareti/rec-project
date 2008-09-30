/*
 * RadioactividadeCustomizer.java
 *
 * Created on 16 de Maio de 2003, 10:11
 */


package pt.utl.ist.elab.client.serial.stamp.radioactividade;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class RadioactividadeCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer
{
    
    /** Creates new form RadioactividadeCustomizer */
    public RadioactividadeCustomizer()
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

        buttonGroupMode = new javax.swing.ButtonGroup();
        buttonGroupMaterial = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDefaults = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnModeCounter = new javax.swing.JRadioButton();
        btnModeTimer = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        sldNumSamples = new javax.swing.JSlider();
        tfNumSamples = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        sldDetectorHeight = new javax.swing.JSlider();
        jPanel5 = new javax.swing.JPanel();
        btnWood = new javax.swing.JToggleButton();
        btnCork = new javax.swing.JToggleButton();
        btnCopper2 = new javax.swing.JToggleButton();
        btnCopper4 = new javax.swing.JToggleButton();
        btnCopper8 = new javax.swing.JToggleButton();
        btnCopper16 = new javax.swing.JToggleButton();
        btnCopper32 = new javax.swing.JToggleButton();
        btnAir = new javax.swing.JToggleButton();
        btnLead = new javax.swing.JToggleButton();
        btnBrick = new javax.swing.JToggleButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnOK.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.ok"));
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

        btnCancel.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.cancel"));
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

        btnDefaults.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.dftcfg.radio.title.1"));
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

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(new javax.swing.border.TitledBorder(null, ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.mode"), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 80));
        jPanel4.setMinimumSize(new java.awt.Dimension(170, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(170, 50));
        btnModeCounter.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.counts"));
        buttonGroupMode.add(btnModeCounter);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(btnModeCounter, gridBagConstraints);

        btnModeTimer.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.tinter"));
        buttonGroupMode.add(btnModeTimer);
        btnModeTimer.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(btnModeTimer, gridBagConstraints);

        jPanel3.add(jPanel4);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.X_AXIS));

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jPanel6.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.nsamples")));
        jPanel6.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel6.setMinimumSize(new java.awt.Dimension(100, 80));
        jPanel6.setPreferredSize(new java.awt.Dimension(140, 80));
        sldNumSamples.setMajorTickSpacing(10);
        sldNumSamples.setMinorTickSpacing(1);
        sldNumSamples.setOrientation(javax.swing.JSlider.VERTICAL);
        sldNumSamples.setPaintLabels(true);
        sldNumSamples.setPaintTicks(true);
        sldNumSamples.setMaximumSize(new java.awt.Dimension(1000, 32767));
        sldNumSamples.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sldNumSamplesStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 10.0;
        jPanel6.add(sldNumSamples, gridBagConstraints);

        tfNumSamples.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNumSamples.setText("50");
        tfNumSamples.setMaximumSize(new java.awt.Dimension(30, 16));
        tfNumSamples.setMinimumSize(new java.awt.Dimension(30, 16));
        tfNumSamples.setPreferredSize(new java.awt.Dimension(30, 16));
        tfNumSamples.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                tfNumSamplesFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel6.add(tfNumSamples, gridBagConstraints);

        jPanel8.add(jPanel6);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.dheight")));
        jPanel1.setMinimumSize(new java.awt.Dimension(90, 225));
        jPanel1.setPreferredSize(new java.awt.Dimension(90, 225));
        sldDetectorHeight.setMajorTickSpacing(10);
        sldDetectorHeight.setOrientation(javax.swing.JSlider.VERTICAL);
        sldDetectorHeight.setPaintLabels(true);
        sldDetectorHeight.setPaintTicks(true);
        sldDetectorHeight.setSnapToTicks(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 10.0;
        jPanel1.add(sldDetectorHeight, gridBagConstraints);

        jPanel8.add(jPanel1);

        jPanel3.add(jPanel8);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.material")));
        btnWood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/wood.gif")));
        btnWood.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.wood"));
        buttonGroupMaterial.add(btnWood);
        btnWood.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 1, 5);
        jPanel5.add(btnWood, gridBagConstraints);

        btnCork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/corticite.gif")));
        btnCork.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.cork"));
        buttonGroupMaterial.add(btnCork);
        btnCork.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCork, gridBagConstraints);

        btnCopper2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/cobre2.gif")));
        btnCopper2.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.copper1"));
        buttonGroupMaterial.add(btnCopper2);
        btnCopper2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCopper2, gridBagConstraints);

        btnCopper4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/cobre4.gif")));
        btnCopper4.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.copper2"));
        buttonGroupMaterial.add(btnCopper4);
        btnCopper4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCopper4, gridBagConstraints);

        btnCopper8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/cobre8.gif")));
        btnCopper8.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.copper3"));
        buttonGroupMaterial.add(btnCopper8);
        btnCopper8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCopper8, gridBagConstraints);

        btnCopper16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/cobre16.gif")));
        btnCopper16.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.copper4"));
        buttonGroupMaterial.add(btnCopper16);
        btnCopper16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCopper16, gridBagConstraints);

        btnCopper32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/cobre32.gif")));
        btnCopper32.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.copper5"));
        buttonGroupMaterial.add(btnCopper32);
        btnCopper32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnCopper32, gridBagConstraints);

        btnAir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/air.gif")));
        btnAir.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.air"));
        buttonGroupMaterial.add(btnAir);
        btnAir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnAir, gridBagConstraints);

        btnLead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/chumbo.gif")));
        btnLead.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.lead"));
        buttonGroupMaterial.add(btnLead);
        btnLead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 5, 5);
        jPanel5.add(btnLead, gridBagConstraints);

        btnBrick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/brick.gif")));
        btnBrick.setText(ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.lbl.brick"));
        buttonGroupMaterial.add(btnBrick);
        btnBrick.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 5);
        jPanel5.add(btnBrick, gridBagConstraints);

        add(jPanel5, java.awt.BorderLayout.EAST);

    }//GEN-END:initComponents

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
    }//GEN-LAST:event_tfNumSamplesFocusLost
		
	private void sldNumSamplesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sldNumSamplesStateChanged
	{//GEN-HEADEREND:event_sldNumSamplesStateChanged
	    if(sldNumSamples.getValueIsAdjusting())
	    {
		if(sldNumSamples.getValue()==0)
		{
		    sldNumSamples.setValue(1);
		    
		}
		tfNumSamples.setText(""+sldNumSamples.getValue());
	    }
	}//GEN-LAST:event_sldNumSamplesStateChanged
	
	private void btnDefaultsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDefaultsActionPerformed
	{//GEN-HEADEREND:event_btnDefaultsActionPerformed
	    btnWood.setSelected(true);
	    btnModeCounter.setSelected(true);
	    sldNumSamples.setValue(50);
	    tfNumSamples.setText("50");
	    sldDetectorHeight.setValue(50);
	}//GEN-LAST:event_btnDefaultsActionPerformed
	
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
	{//GEN-HEADEREND:event_btnCancelActionPerformed
	    fireICustomizerListenerCanceled();
	}//GEN-LAST:event_btnCancelActionPerformed
	
	private void btnOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOKActionPerformed
	{//GEN-HEADEREND:event_btnOKActionPerformed
	    
	    acqConfig.setTotalSamples(sldNumSamples.getValue()==0?1:sldNumSamples.getValue());
	    acqConfig.getSelectedHardwareParameter("Altura do detector").setParameterValue(""+sldDetectorHeight.getValue());
	    String strMaterial=null;
	    if(btnWood.isSelected())
		strMaterial="Madeira [10mm]";
	    else if(btnCork.isSelected())
		strMaterial="Corticite [10mm]";
	    else if(btnBrick.isSelected())
		strMaterial="Tijolo [10mm]";
	    else if(btnCopper2.isSelected())
		strMaterial="Cobre [0.2mm]";
	    else if(btnCopper4.isSelected())
		strMaterial="Cobre [0.4mm]";
	    else if(btnCopper8.isSelected())
		strMaterial="Cobre [0.8mm]";
	    else if(btnCopper16.isSelected())
		strMaterial="Cobre [1.6mm]";
	    else if(btnCopper32.isSelected())
		strMaterial="Cobre [3.2mm]";
	    else if(btnAir.isSelected())
		strMaterial="Janela de controlo (Ar)";
	    else if(btnLead.isSelected())
		strMaterial="Chumbo (isolante)";
	    
	    acqConfig.getSelectedHardwareParameter("Material").setParameterValue(strMaterial);
	    
	    acqConfig.getSelectedHardwareParameter("Modo de aquisicao").setParameterValue("Contagens");
	    
	    fireICustomizerListenerDone();
	}//GEN-LAST:event_btnOKActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAir;
    private javax.swing.JToggleButton btnBrick;
    private javax.swing.JButton btnCancel;
    private javax.swing.JToggleButton btnCopper16;
    private javax.swing.JToggleButton btnCopper2;
    private javax.swing.JToggleButton btnCopper32;
    private javax.swing.JToggleButton btnCopper4;
    private javax.swing.JToggleButton btnCopper8;
    private javax.swing.JToggleButton btnCork;
    private javax.swing.JButton btnDefaults;
    private javax.swing.JToggleButton btnLead;
    private javax.swing.JRadioButton btnModeCounter;
    private javax.swing.JRadioButton btnModeTimer;
    private javax.swing.JButton btnOK;
    private javax.swing.JToggleButton btnWood;
    private javax.swing.ButtonGroup buttonGroupMaterial;
    private javax.swing.ButtonGroup buttonGroupMode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSlider sldDetectorHeight;
    private javax.swing.JSlider sldNumSamples;
    private javax.swing.JTextField tfNumSamples;
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
		sldNumSamples.setValue(acqConfig.getTotalSamples());
		tfNumSamples.setText(""+acqConfig.getTotalSamples());
		sldDetectorHeight.setValue(Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("Altura do detector")));
		btnModeCounter.setSelected(true);
		String strMaterial=acqConfig.getSelectedHardwareParameterValue("Material");
		if(strMaterial.equals("Madeira [10mm]"))
		    btnWood.setSelected(true);
		else if(strMaterial.equals("Corticite [10mm]"))
		    btnCork.setSelected(true);
		else if(strMaterial.equals("Tijolo [10mm]"))
		    btnBrick.setSelected(true);
		else if(strMaterial.equals("Cobre [0.2mm]"))
		    btnCopper2.setSelected(true);
		else if(strMaterial.equals("Cobre [0.4mm]"))
		    btnCopper4.setSelected(true);
		else if(strMaterial.equals("Cobre [0.8mm]"))
		    btnCopper8.setSelected(true);
		else if(strMaterial.equals("Cobre [1.6mm]"))
		    btnCopper16.setSelected(true);
		else if(strMaterial.equals("Cobre [3.2mm]"))
		    btnCopper32.setSelected(true);
		else if(strMaterial.equals("Janela de controlo (Ar)"))
		    btnAir.setSelected(true);
		else if(strMaterial.equals("Chumbo (isolante)"))
		    btnLead.setSelected(true);
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
	    return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/serial/stamp/radioactividade/resources/radioactividade_iconified.gif"));
	}
	
	public String getCustomizerTitle()
	{
	    return ReCResourceBundle.findString("ReCExpRadio$rec.exp.radio.customizer.title");
	}
	
	public javax.swing.JMenuBar getMenuBar()
	{
	    return null;
	}
	
}
