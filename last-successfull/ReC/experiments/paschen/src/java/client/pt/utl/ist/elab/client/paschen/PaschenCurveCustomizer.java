/*
 * PaschenCurveCustomizer.java
 *
 * Created on 17 de Fevereiro de 2005, 5:07
 */

package pt.utl.ist.elab.client.paschen;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author jloureiro
 */
public class PaschenCurveCustomizer extends AbstractCustomizer {



	/**
	 * 
	 */
	private static final long serialVersionUID = 9034443753516543496L;

	/** Creates new form PaschenCurveCustomizer */
	public PaschenCurveCustomizer() {
		initComponents();
		initComponentsManual();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        sldSig1 = new javax.swing.JSlider();
        tfSig1 = new javax.swing.JFormattedTextField();
        
        jPanel2 = new javax.swing.JPanel();
        sldSig2 = new javax.swing.JSlider();
        tfSig2 = new javax.swing.JFormattedTextField();
        jLabelSig2Error = new javax.swing.JLabel();
        
        jPanel3 = new javax.swing.JPanel();
        sldSig3 = new javax.swing.JSlider();
        tfSig3 = new javax.swing.JFormattedTextField();
        jLabelSig3Error = new javax.swing.JLabel();
        
        jPanel4 = new javax.swing.JPanel();
        sldSig4 = new javax.swing.JSlider();
        tfSig4 = new javax.swing.JFormattedTextField();
        jLabelSig4Error = new javax.swing.JLabel();
        
        btnOK = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnDefConfig = new javax.swing.JButton();

        //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //setTitle("Paschen Curve Customizer");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title2","paschen$rec.exp.paschen.customizer.lbl.title2")));
        jPanel1.setToolTipText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title2","paschen$rec.exp.paschen.customizer.lbl.title2"));
        jPanel1.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 160));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        sldSig1.setMajorTickSpacing(500);
        sldSig1.setMaximum(3000);
        sldSig1.setMinorTickSpacing(250);
        sldSig1.setPaintLabels(true);
        sldSig1.setPaintTicks(true);
        sldSig1.setPaintTrack(false);
        sldSig1.setValue(0);
        sldSig1.setMinimumSize(new java.awt.Dimension(250, 42));
        sldSig1.setPreferredSize(new java.awt.Dimension(250, 42));
        sldSig1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSig1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 10.0;
        jPanel1.add(sldSig1, gridBagConstraints);

        tfSig1.setText("0.0");
        tfSig1.setPreferredSize( new java.awt.Dimension( 40, 20 ) );
        tfSig1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSig1ActionPerformed(evt);
            }
        });
        tfSig1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSig1FocusLost(evt);
            }
        });
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(tfSig1, new java.awt.GridBagConstraints());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title3","paschen$rec.exp.paschen.customizer.lbl.title3")));
        jPanel2.setToolTipText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title3","paschen$rec.exp.paschen.customizer.lbl.title3"));
        jPanel2.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 160));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        sldSig2.setMajorTickSpacing(500);
        sldSig2.setMaximum(3000);
        sldSig2.setMinorTickSpacing(250);
        sldSig2.setPaintLabels(true);
        sldSig2.setPaintTicks(true);
        sldSig2.setPaintTrack(false);
        sldSig2.setValue(300);
        sldSig2.setMinimumSize(new java.awt.Dimension(250, 42));
        sldSig2.setPreferredSize(new java.awt.Dimension(250, 42));
        sldSig2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSig2StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 10.0;
        jPanel2.add(sldSig2, gridBagConstraints);

        tfSig2.setText("0.3");
        tfSig2.setPreferredSize( new java.awt.Dimension( 40, 20 ) );
        tfSig2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSig2ActionPerformed(evt);
            }
        });
        
        tfSig2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSig2FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(tfSig2, gridBagConstraints);
        
        jLabelSig2Error.setForeground(java.awt.Color.RED);
        jLabelSig2Error.setText((ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.warning2","paschen$rec.exp.paschen.customizer.lbl.warning2"))); // NOI18N
        jLabelSig2Error.setVisible(false);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabelSig2Error, gridBagConstraints);
       

        btnOK.setText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.ok","paschen$rec.exp.paschen.customizer.lbl.ok"));
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnClose.setText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.cancel","paschen$rec.exp.paschen.customizer.lbl.cancel"));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title1","paschen$rec.exp.paschen.customizer.lbl.title1")));
        jPanel3.setToolTipText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title1","paschen$rec.exp.paschen.customizer.lbl.title1"));
        jPanel3.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 160));
        jPanel3.setRequestFocusEnabled(false);
        jPanel3.setVerifyInputWhenFocusTarget(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        sldSig3.setMajorTickSpacing(20);
        sldSig3.setMinorTickSpacing(10);
        sldSig3.setPaintLabels(true);
        sldSig3.setPaintTicks(true);
        sldSig3.setPaintTrack(false);
        sldSig3.setValue(10);
        sldSig3.setMinimumSize(new java.awt.Dimension(250, 42));
        sldSig3.setPreferredSize(new java.awt.Dimension(250, 42));
        sldSig3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSig3StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 10.0;
        jPanel3.add(sldSig3, gridBagConstraints);

        tfSig3.setText("0.5");
        tfSig3.setPreferredSize( new java.awt.Dimension( 40, 20 ) );
        tfSig3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSig3ActionPerformed(evt);
            }
        });
        tfSig3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSig3FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(tfSig3, gridBagConstraints);
        
        jLabelSig3Error.setForeground(java.awt.Color.RED);
        jLabelSig3Error.setText((ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.warning1","paschen$rec.exp.paschen.customizer.lbl.warning1"))); // NOI18N
        jLabelSig3Error.setVisible(false);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jLabelSig3Error, gridBagConstraints);

        btnDefConfig.setText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.dftcfg","paschen$rec.exp.paschen.customizer.lbl.dftcfg"));
        btnDefConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefConfigActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title4","paschen$rec.exp.paschen.customizer.lbl.title4")));
        jPanel4.setToolTipText(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title4","paschen$rec.exp.paschen.customizer.lbl.title4"));
        jPanel4.setMinimumSize(new java.awt.Dimension(350, 160));
        jPanel4.setPreferredSize(new java.awt.Dimension(350, 160));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setVerifyInputWhenFocusTarget(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        sldSig4.setMajorTickSpacing(10);
        sldSig4.setMaximum(50);
        sldSig4.setMinimum(0);
        sldSig4.setMinorTickSpacing(5);
        sldSig4.setPaintLabels(true);
        sldSig4.setPaintTicks(true);
        sldSig4.setPaintTrack(false);
        sldSig4.setToolTipText("");
        sldSig4.setValue(1);
        sldSig4.setMinimumSize(new java.awt.Dimension(250, 42));
        sldSig4.setPreferredSize(new java.awt.Dimension(250, 42));
        sldSig4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSig4StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 10.0;
        jPanel4.add(sldSig4, gridBagConstraints);

        tfSig4.setText("1");
        tfSig4.setPreferredSize( new java.awt.Dimension( 40, 20 ) );
        tfSig4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSig4ActionPerformed(evt);
            }
        });
        tfSig4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSig4FocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(tfSig4, gridBagConstraints);

        jLabelSig4Error.setForeground(java.awt.Color.RED);
        jLabelSig4Error.setText((ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.warning1","paschen$rec.exp.paschen.customizer.lbl.warning1"))); // NOI18N
        jLabelSig4Error.setVisible(false);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel4.add(jLabelSig4Error, gridBagConstraints);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnOK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDefConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                /*.addGroup(layout.createSequentialGroup()
            	.addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())*/
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnClose)
                    .addComponent(btnDefConfig))
                .addContainerGap())
        );
         
        
        jPanel1.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title2","paschen$rec.exp.paschen.customizer.lbl.title2"));
        jPanel2.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title3","paschen$rec.exp.paschen.customizer.lbl.title3"));
        jPanel2.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title3","paschen$rec.exp.paschen.customizer.lbl.title3"));
        btnClose.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.cancel","paschen$rec.exp.paschen.customizer.lbl.cancel"));
        jPanel3.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title1","paschen$rec.exp.paschen.customizer.lbl.title1"));
        jPanel3.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title1","paschen$rec.exp.paschen.customizer.lbl.title1"));
        jPanel4.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title4","paschen$rec.exp.paschen.customizer.lbl.title4"));
        jPanel4.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("paschen$rec.exp.paschen.customizer.lbl.title4","paschen$rec.exp.paschen.customizer.lbl.title4"));

	}// GEN-END:initComponents

    private void sldSig1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSig1StateChanged
        tfSig1.setText(Float.toString(sldSig1.getValue()/1000.F));
        
        if(sldSig1.getValue() > sldSig2.getValue()){     
            btnOK.setEnabled(false);
            jLabelSig2Error.setVisible(true);
        }
        
        else if(sldSig1.getValue() < sldSig2.getValue()){
            btnOK.setEnabled(true);
            jLabelSig2Error.setVisible(false);
        }
        
    }//GEN-LAST:event_sldSig1StateChanged

    private void tfSig1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSig1FocusLost
    	int parsed = (int) (Double.parseDouble(tfSig1.getText())*1000);
    	if(parsed > sldSig1.getMaximum()){parsed = sldSig1.getMaximum();}
    	if(parsed < sldSig1.getMinimum()){parsed = sldSig1.getMinimum();}	
    	sldSig1.setValue(parsed); 
    }//GEN-LAST:event_tfSig1FocusLost

    private void sldSig2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSig2StateChanged
        tfSig2.setText(Float.toString(sldSig2.getValue()/1000.F));
        
        if(sldSig2.getValue() <= sldSig1.getValue()){
            btnOK.setEnabled(false);
            jLabelSig2Error.setVisible(true);
        }
        else if(sldSig2.getValue() > sldSig1.getValue()){
            btnOK.setEnabled(true);
            jLabelSig2Error.setVisible(false);
        }
        
    }//GEN-LAST:event_sldSig2StateChanged

    private void tfSig2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSig2FocusLost
    	int parsed = (int) (Double.parseDouble(tfSig2.getText())*1000);
    	if(parsed > sldSig2.getMaximum()){parsed = sldSig2.getMaximum();}
    	if(parsed < sldSig2.getMinimum()){parsed = sldSig2.getMinimum();}	
    	sldSig2.setValue(parsed);
    }//GEN-LAST:event_tfSig2FocusLost

    @SuppressWarnings("static-access")//TODO: Watch out for static access
	private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
	
		final double volt_ini = sldSig1.getValue();
		final double volt_fin = sldSig2.getValue();
		final double volt_step = sldSig4.getValue();
		final double press_set = sldSig3.getValue();

		getAcquisitionConfig().setSelectedFrequency(new Frequency(100d, getHardwareInfo().getHardwareFrequencies(0).getMinimumFrequency()
				.getMultiplier(), getHardwareInfo().getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
		getAcquisitionConfig().setTotalSamples(3000+((int) ((volt_fin-volt_ini)/volt_step)));

		getAcquisitionConfig().getSelectedHardwareParameter("volt_ini").setParameterValue("" + (float) volt_ini);
		getAcquisitionConfig().getSelectedHardwareParameter("volt_fin").setParameterValue("" + (float) volt_fin);
		getAcquisitionConfig().getSelectedHardwareParameter("volt_step").setParameterValue("" + (float) volt_step);
		getAcquisitionConfig().getSelectedHardwareParameter("press_set").setParameterValue("" + (float) press_set);

		for (int i = 0; i < getAcquisitionConfig().getSelectedHardwareParameters().length; i++) {
			System.out.println(getAcquisitionConfig().getSelectedHardwareParameters(i).getParameterName() + " = "
					+ getAcquisitionConfig().getSelectedHardwareParameters(i).getParameterValue());
		}

		fireICustomizerListenerDone();

    }//GEN-LAST:event_btnOKActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        
		fireICustomizerListenerCanceled();
        
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tfSig1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSig1ActionPerformed
    	int parsed = (int) (Double.parseDouble(tfSig1.getText())*1000);
    	if(parsed > sldSig1.getMaximum()){parsed = sldSig1.getMaximum();}
    	if(parsed < sldSig1.getMinimum()){parsed = sldSig1.getMinimum();}	
    	sldSig1.setValue(parsed);
    }//GEN-LAST:event_tfSig1ActionPerformed

    private void sldSig3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSig3StateChanged
        tfSig3.setText(Float.toString(sldSig3.getValue()/20.F));
        
        if(sldSig3.getValue() == 0){
            btnOK.setEnabled(false);
            jLabelSig3Error.setVisible(true);
        }
        else if(sldSig3.getValue() > 0){
            btnOK.setEnabled(true);
            jLabelSig3Error.setVisible(false);
        }
        
    }//GEN-LAST:event_sldSig3StateChanged

    private void tfSig3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSig3FocusLost
    	int parsed = (int) (Double.parseDouble(tfSig3.getText())*20);
    	if(parsed > sldSig3.getMaximum()){parsed = sldSig3.getMaximum();}
    	if(parsed < sldSig3.getMinimum()){parsed = sldSig3.getMinimum();}	
    	sldSig3.setValue(parsed);   
    }//GEN-LAST:event_tfSig3FocusLost

    private void tfSig3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSig3ActionPerformed
    	int parsed = (int) (Double.parseDouble(tfSig3.getText())*20);
    	if(parsed > sldSig3.getMaximum()){parsed = sldSig3.getMaximum();}
    	if(parsed < sldSig3.getMinimum()){parsed = sldSig3.getMinimum();}	
    	sldSig3.setValue(parsed);      
    }//GEN-LAST:event_tfSig3ActionPerformed

    private void btnDefConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefConfigActionPerformed
        
        sldSig1.setValue(0);
        tfSig1.setText("0");
        sldSig2.setValue(800);
        tfSig2.setText("0.800");
        sldSig3.setValue(20);
        tfSig3.setText("1.0");
        sldSig4.setValue(10);
        tfSig4.setText("10");
        
        
    }//GEN-LAST:event_btnDefConfigActionPerformed

    private void tfSig2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSig2ActionPerformed
    	int parsed = (int) (Double.parseDouble(tfSig2.getText())*1000);
    	if(parsed > sldSig2.getMaximum()){parsed = sldSig2.getMaximum();}
    	if(parsed < sldSig2.getMinimum()){parsed = sldSig2.getMinimum();}	
    	sldSig2.setValue(parsed);
    }//GEN-LAST:event_tfSig2ActionPerformed

    private void sldSig4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSig4StateChanged
        tfSig4.setText(Integer.toString(sldSig4.getValue()));
        
        if(sldSig4.getValue() == 0){
            btnOK.setEnabled(false);
            jLabelSig4Error.setVisible(true);
        }
        else if(sldSig4.getValue() > 0){
            btnOK.setEnabled(true);
            jLabelSig4Error.setVisible(false);
        }
    }//GEN-LAST:event_sldSig4StateChanged

    private void tfSig4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSig4ActionPerformed
    	int parsed = (int) (Double.parseDouble(tfSig4.getText()));
    	if(parsed > sldSig4.getMaximum()){parsed = sldSig4.getMaximum();}
    	if(parsed < sldSig4.getMinimum()){parsed = sldSig4.getMinimum();}	
    	sldSig4.setValue(parsed);
    }//GEN-LAST:event_tfSig4ActionPerformed

    private void tfSig4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSig4FocusLost
    	int parsed = (int) (Double.parseDouble(tfSig4.getText()));
    	if(parsed > sldSig4.getMaximum()){parsed = sldSig4.getMaximum();}
    	if(parsed < sldSig4.getMinimum()){parsed = sldSig4.getMinimum();}	
    	sldSig4.setValue(parsed);
    }//GEN-LAST:event_tfSig4FocusLost	

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
		test.getContentPane().add(new PaschenCurveCustomizer());
		test.pack();
		test.setVisible(true);
	}

	// ****************************REC********************************************/

	

	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			final double volt_ini = Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("volt_ini"));
			final double volt_fin = Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("volt_fin"));
			final double volt_step = Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("volt_step"));
			final double press_set = Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("press_set"));			

			System.out.println("PaschenCurveCustomizer.setHardwareAcquisitionConfig(HardwareAcquisitionConfig  acqConfig)");
			System.out.println("acqConfig: [" + acqConfig + "]");


		}
	}

	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/paschen/resources/paschen_iconified.PNG"));
	}

	@Override
	public String getCustomizerTitle() {
		return "Shot Configuration Utility";
	}
	
	private void initComponentsManual() {

		final Dictionary<Integer, JLabel> slidersVoltageLabels = new Hashtable<Integer, JLabel>();
		slidersVoltageLabels.put(new Integer(0), new JLabel("0.0"));
		slidersVoltageLabels.put(new Integer(500), new JLabel("0.5"));
		slidersVoltageLabels.put(new Integer(1000), new JLabel("1.0"));
		slidersVoltageLabels.put(new Integer(1500), new JLabel("1.5"));
		slidersVoltageLabels.put(new Integer(2000), new JLabel("2.0"));
		slidersVoltageLabels.put(new Integer(2500), new JLabel("2.5"));
		slidersVoltageLabels.put(new Integer(3000), new JLabel("3.0"));
		
		sldSig1.setLabelTable(slidersVoltageLabels);
		sldSig2.setLabelTable(slidersVoltageLabels);

		final Dictionary<Integer, JLabel> slidersPressLabels = new Hashtable<Integer, JLabel>();
		slidersPressLabels.put(new Integer(0), new JLabel("0.0"));
		slidersPressLabels.put(new Integer(20), new JLabel("1.0"));
		slidersPressLabels.put(new Integer(40), new JLabel("2.0"));
		slidersPressLabels.put(new Integer(60), new JLabel("3.0"));
		slidersPressLabels.put(new Integer(80), new JLabel("4.0"));
		slidersPressLabels.put(new Integer(100), new JLabel("5.0"));
		
		sldSig3.setLabelTable(slidersPressLabels);

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDefConfig;
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider sldSig1;
    private javax.swing.JSlider sldSig2;
    private javax.swing.JSlider sldSig3;
    private javax.swing.JSlider sldSig4;
    private javax.swing.JFormattedTextField tfSig1;
    private javax.swing.JFormattedTextField tfSig2;
    private javax.swing.JFormattedTextField tfSig3;
    private javax.swing.JFormattedTextField tfSig4;
    private javax.swing.JLabel jLabelSig2Error;
    private javax.swing.JLabel jLabelSig3Error;
    private javax.swing.JLabel jLabelSig4Error;
    
	// End of variables declaration//GEN-END:variables

}
