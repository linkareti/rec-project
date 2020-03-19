/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WorldPendulumCustomizerPanel.java
 *
 * Created on 09-Nov-2010, 11:29:48
 */
package pt.utl.ist.elab.client.wpbarcelona;

//import java.util.Dictionary;
//import java.util.Hashtable;

//import javax.swing.JFormattedTextField;
//import javax.swing.JLabel;

import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
//import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
//import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author npadriano
 */
public class WorldPendulumCustomizerPanel extends AbstractCustomizer {

	/** Generated UID */
	private static final long serialVersionUID = -6367415208236048245L;

	public static final int NUMBER_OF_SAMPLES_FOR_SERIES = 1000;
	public static final float PERIOD = 3.3f;

        public static String labelBegin;
        public static String labelEnd;
	
        /** Creates new form WorldPendulumCustomizerPanel */
	public WorldPendulumCustomizerPanel() {
		labelBegin = ReCResourceBundle.findStringOrDefault("wpbarcelona$jLabelEstimatedTime.text.beggining","Estimated time is");
                labelEnd = ReCResourceBundle.findStringOrDefault("wpbarcelona$jLabelEstimatedTime.text.end","minutes");
                
                initComponents();		
                validateScreen();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneWorldPendulum = new javax.swing.JTabbedPane();
        jPanelPendulumConfig = new javax.swing.JPanel();
        jPanelPenduloDisplacement = new javax.swing.JPanel();
        jSliderDisplacement = new javax.swing.JSlider();
        jFormattedTextFieldDisplacement = new javax.swing.JFormattedTextField();
        jPanelNrPoints = new javax.swing.JPanel();
        jSliderNrPoints = new javax.swing.JSlider();
        jFormattedTextFieldNrPoints = new javax.swing.JFormattedTextField();
        jLabelEstimatedTime = new javax.swing.JLabel();
        jPanelControlButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonDefaultConfig = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(350, 350));
        setName("WorldPendulumCustomizer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(350, 350));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.BorderLayout());

        jTabbedPaneWorldPendulum.setName("jTabbedPaneWorldPendulum"); // NOI18N
        jTabbedPaneWorldPendulum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneWorldPendulumStateChanged(evt);
            }
        });

        jPanelPendulumConfig.setName("jPanelPendulumConfig"); // NOI18N
        jPanelPendulumConfig.setPreferredSize(new java.awt.Dimension(350, 360));

        jPanelPenduloDisplacement.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelPenduloDisplacement.border.title","Initial Displacement")));
        jPanelPenduloDisplacement.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelPenduloDisplacement.setName("jPanelPenduloDisplacement"); // NOI18N
        jPanelPenduloDisplacement.setPreferredSize(new java.awt.Dimension(300, 126));

        jSliderDisplacement.setMajorTickSpacing(5);
        jSliderDisplacement.setMaximum(20);
        jSliderDisplacement.setMinimum(5);
        jSliderDisplacement.setMinorTickSpacing(1);
        jSliderDisplacement.setPaintLabels(true);
        jSliderDisplacement.setPaintTicks(true);
        jSliderDisplacement.setToolTipText(ReCResourceBundle.findStringOrDefault("wpbarcelona$jSliderDisplacement.toolTipText","Initial Displacement"));
        jSliderDisplacement.setMinimumSize(new java.awt.Dimension(250, 16));
        jSliderDisplacement.setName("jSliderDisplacement"); // NOI18N
        jSliderDisplacement.setPreferredSize(new java.awt.Dimension(250, 42));
        jSliderDisplacement.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderDisplacementStateChanged(evt);
            }
        });

        jFormattedTextFieldDisplacement.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFormattedTextFieldDisplacement.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class);
        jFormattedTextFieldDisplacement.setText(resourceMap.getString("jFormattedTextFieldDisplacement.text")); // NOI18N
        jFormattedTextFieldDisplacement.setToolTipText(resourceMap.getString("jFormattedTextFieldDisplacement.toolTipText")); // NOI18N
        jFormattedTextFieldDisplacement.setMaximumSize(new java.awt.Dimension(40, 19));
        jFormattedTextFieldDisplacement.setMinimumSize(new java.awt.Dimension(40, 19));
        jFormattedTextFieldDisplacement.setName("jFormattedTextFieldDisplacement"); // NOI18N
        jFormattedTextFieldDisplacement.setPreferredSize(new java.awt.Dimension(40, 19));
        jFormattedTextFieldDisplacement.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldDisplacementFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelPenduloDisplacementLayout = new javax.swing.GroupLayout(jPanelPenduloDisplacement);
        jPanelPenduloDisplacement.setLayout(jPanelPenduloDisplacementLayout);
        jPanelPenduloDisplacementLayout.setHorizontalGroup(
            jPanelPenduloDisplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPenduloDisplacementLayout.createSequentialGroup()
                .addComponent(jSliderDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPenduloDisplacementLayout.setVerticalGroup(
            jPanelPenduloDisplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPenduloDisplacementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPenduloDisplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSliderDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jSliderDisplacement.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jSliderDisplacement.toolTipText","Deslocamento"));
        jSliderDisplacement.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jSliderDisplacement.toolTipText","Deslocamento"));
        jFormattedTextFieldDisplacement.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jSliderDisplacement.toolTipText","Deslocamento"));

        jPanelNrPoints.setBorder(javax.swing.BorderFactory.createTitledBorder(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelNrPoints.border.title","Samples")));
        jPanelNrPoints.setName("jPanelNrPoints"); // NOI18N
        jPanelNrPoints.setPreferredSize(new java.awt.Dimension(300, 126));

        jSliderNrPoints.setMajorTickSpacing(80);
        jSliderNrPoints.setMaximum(500);
        jSliderNrPoints.setMinimum(10);
        jSliderNrPoints.setMinorTickSpacing(40);
        jSliderNrPoints.setPaintLabels(true);
        jSliderNrPoints.setPaintTicks(true);
        jSliderNrPoints.setValue(100);
        jSliderNrPoints.setMinimumSize(new java.awt.Dimension(250, 16));
        jSliderNrPoints.setName("jSliderNrPoints"); // NOI18N
        jSliderNrPoints.setPreferredSize(new java.awt.Dimension(250, 42));
        jSliderNrPoints.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderNrPointsStateChanged(evt);
            }
        });

        jFormattedTextFieldNrPoints.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFormattedTextFieldNrPoints.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFormattedTextFieldNrPoints.setText(resourceMap.getString("jFormattedTextFieldNrPoints.text")); // NOI18N
        jFormattedTextFieldNrPoints.setToolTipText(resourceMap.getString("jFormattedTextFieldNrPoints.toolTipText")); // NOI18N
        jFormattedTextFieldNrPoints.setMaximumSize(new java.awt.Dimension(45, 19));
        jFormattedTextFieldNrPoints.setMinimumSize(new java.awt.Dimension(40, 19));
        jFormattedTextFieldNrPoints.setName("jFormattedTextFieldNrPoints"); // NOI18N
        jFormattedTextFieldNrPoints.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldNrPointsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelNrPointsLayout = new javax.swing.GroupLayout(jPanelNrPoints);
        jPanelNrPoints.setLayout(jPanelNrPointsLayout);
        jPanelNrPointsLayout.setHorizontalGroup(
            jPanelNrPointsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNrPointsLayout.createSequentialGroup()
                .addComponent(jSliderNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelNrPointsLayout.setVerticalGroup(
            jPanelNrPointsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNrPointsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNrPointsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSliderNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jSliderNrPoints.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jFormattedTextFieldNrPoints.toolTipText","Amostras"));
        jFormattedTextFieldNrPoints.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jFormattedTextFieldNrPoints.toolTipText","Amostras"));

        jLabelEstimatedTime.setFont(resourceMap.getFont("jLabelEstimatedTime.font")); // NOI18N
        jLabelEstimatedTime.setForeground(resourceMap.getColor("jLabelEstimatedTime.foreground")); // NOI18N
        jLabelEstimatedTime.setText(labelBegin + " " + labelEnd);
        jLabelEstimatedTime.setToolTipText(resourceMap.getString("jLabelEstimatedTime.toolTipText")); // NOI18N
        jLabelEstimatedTime.setEnabled(false);
        jLabelEstimatedTime.setName("jLabelEstimatedTime"); // NOI18N

        javax.swing.GroupLayout jPanelPendulumConfigLayout = new javax.swing.GroupLayout(jPanelPendulumConfig);
        jPanelPendulumConfig.setLayout(jPanelPendulumConfigLayout);
        jPanelPendulumConfigLayout.setHorizontalGroup(
            jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPendulumConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEstimatedTime)
                    .addComponent(jPanelPenduloDisplacement, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(jPanelNrPoints, 0, 318, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelPendulumConfigLayout.setVerticalGroup(
            jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPendulumConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPenduloDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelEstimatedTime)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanelPenduloDisplacement.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelPenduloDisplacement.border.title","Initial Displacement"));
        jPanelPenduloDisplacement.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelPenduloDisplacement.border.title","Initial Displacement"));
        jPanelNrPoints.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelNrPoints.border.title","Samples"));
        jPanelNrPoints.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelNrPoints.border.title","Samples"));
        jLabelEstimatedTime.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jLabelEstimatedTime.text","Tempo Estimado"));
        jLabelEstimatedTime.getAccessibleContext().setAccessibleDescription(ReCResourceBundle.findStringOrDefault("wpbarcelona$jLabelEstimatedTime.text","Tempo Estimado"));

        jTabbedPaneWorldPendulum.addTab(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelPendulumConfig.TabConstraints.tabTitle","Configure"), null, jPanelPendulumConfig, resourceMap.getString("jPanelPendulumConfig.TabConstraints.tabToolTip")); // NOI18N

        add(jTabbedPaneWorldPendulum, java.awt.BorderLayout.CENTER);
        jTabbedPaneWorldPendulum.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jPanelPendulumConfig.TabConstraints.tabTitle","Configure"));

        jPanelControlButtons.setName("jPanelControlButtons"); // NOI18N
        jPanelControlButtons.setPreferredSize(new java.awt.Dimension(350, 37));

        jButtonOk.setLabel(resourceMap.getString("Ok.label")); // NOI18N
        jButtonOk.setName("Ok"); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText(ReCResourceBundle.findStringOrDefault("wpbarcelona$Cancel.label","Cancel"));
        jButtonCancel.setName("Cancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonDefaultConfig.setText(ReCResourceBundle.findStringOrDefault("wpbarcelona$DefaultConfig.label","Default Config"));
        jButtonDefaultConfig.setName("DefaultConfig"); // NOI18N
        jButtonDefaultConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDefaultConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelControlButtonsLayout = new javax.swing.GroupLayout(jPanelControlButtons);
        jPanelControlButtons.setLayout(jPanelControlButtonsLayout);
        jPanelControlButtonsLayout.setHorizontalGroup(
            jPanelControlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlButtonsLayout.createSequentialGroup()
                .addComponent(jButtonOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jButtonDefaultConfig)
                .addContainerGap())
        );
        jPanelControlButtonsLayout.setVerticalGroup(
            jPanelControlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlButtonsLayout.createSequentialGroup()
                .addGroup(jPanelControlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonDefaultConfig))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonOk.getAccessibleContext().setAccessibleName(resourceMap.getString("Ok.AccessibleContext.accessibleName")); // NOI18N
        jButtonCancel.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jButtonCancel.AccessibleContext.accessibleName","Cancel"));
        jButtonDefaultConfig.getAccessibleContext().setAccessibleName(ReCResourceBundle.findStringOrDefault("wpbarcelona$jButtonDefaultConfig.AccessibleContext.accessibleName","Default Config"));

        add(jPanelControlButtons, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

private void jTabbedPaneWorldPendulumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneWorldPendulumStateChanged
    validateScreen();
}//GEN-LAST:event_jTabbedPaneWorldPendulumStateChanged

private void jFormattedTextFieldDisplacementFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldDisplacementFocusLost
    formattedMultipliedTextChanged(jSliderDisplacement, jFormattedTextFieldDisplacement);
    validateScreen();
}//GEN-LAST:event_jFormattedTextFieldDisplacementFocusLost

private void jSliderDisplacementStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderDisplacementStateChanged
    sliderMultipliedChanged(jSliderDisplacement, jFormattedTextFieldDisplacement);
    validateScreen();
}//GEN-LAST:event_jSliderDisplacementStateChanged

private void jSliderNrPointsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderNrPointsStateChanged
    sliderMultipliedChanged(jSliderNrPoints, jFormattedTextFieldNrPoints);
    validateScreen();
}//GEN-LAST:event_jSliderNrPointsStateChanged

private void jFormattedTextFieldNrPointsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldNrPointsFocusLost
    formattedMultipliedTextChanged(jSliderNrPoints, jFormattedTextFieldNrPoints);
}//GEN-LAST:event_jFormattedTextFieldNrPointsFocusLost

	private void jButtonCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_jButtonCancelActionPerformed

	private void jButtonDefaultConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDefaultConfigActionPerformed
		// tab Snell
		jSliderDisplacement.setValue(10);
		jFormattedTextFieldDisplacement.setText("10");
		jSliderNrPoints.setValue(100);
		jFormattedTextFieldNrPoints.setText("100");
                validateScreen();

	}// GEN-LAST:event_jButtonDefaultConfigActionPerformed

        
	private void jButtonOkActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonOkActionPerformed

		int nsamples = 0;
		Frequency freq = null;                
                float displacement = 10;
                
            displacement = (float) jSliderDisplacement.getValue();
            nsamples = (int) jSliderNrPoints.getValue();        
            
            final int time = (int) Math.ceil((WorldPendulumCustomizerPanel.PERIOD * nsamples*(1.1) / nsamples));
            freq = new Frequency(time, Multiplier.none, FrequencyDefType.SamplingIntervalType);

		
            getAcquisitionConfig().setTotalSamples(nsamples);
                
            getAcquisitionConfig().getSelectedHardwareParameter("wpbarcelona$parameter.displacement").setParameterValue(String.valueOf(displacement));
            getAcquisitionConfig().getSelectedHardwareParameter("wpbarcelona$parameter.nrpoints").setParameterValue(String.valueOf(nsamples));        
        
            getAcquisitionConfig().setSelectedFrequency(freq);

		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDefaultConfig;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JFormattedTextField jFormattedTextFieldDisplacement;
    private javax.swing.JFormattedTextField jFormattedTextFieldNrPoints;
    private javax.swing.JLabel jLabelEstimatedTime;
    private javax.swing.JPanel jPanelControlButtons;
    private javax.swing.JPanel jPanelNrPoints;
    private javax.swing.JPanel jPanelPenduloDisplacement;
    private javax.swing.JPanel jPanelPendulumConfig;
    private javax.swing.JSlider jSliderDisplacement;
    private javax.swing.JSlider jSliderNrPoints;
    private javax.swing.JTabbedPane jTabbedPaneWorldPendulum;
    // End of variables declaration//GEN-END:variables


	
	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		
                // Requires a proper LOGGER output
                if (acqConfig != null) {
			System.out.println("WorldPendulumCustomizer.setHardwareAcquisitionConfig(HardwareAcquisitionConfig  acqConfig)");
			System.out.println("acqConfig: [" + acqConfig + "]");

		}
	}
	
	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/wpbarcelona/resources/wpbarcelona_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("wpbarcelona$rec.exp.wpbarcelona.customizer.title","wpbarcelona$rec.exp.wpbarcelona.customizer.title");
	}


	private void validateScreen() {
		boolean valid = true;
                
                jLabelEstimatedTime.setText(labelBegin + " " + Integer.toString((int) Math.ceil(((float) jSliderNrPoints.getValue()) * PERIOD / 60.)) + " " + labelEnd );
                
		jButtonOk.setEnabled(valid);
	}

	private void sliderMultipliedChanged(final javax.swing.JSlider slider,
			final javax.swing.JFormattedTextField textField) {
		textField.setText(Integer.toString((int) slider.getValue()));
	}

	private void formattedMultipliedTextChanged(final javax.swing.JSlider slider,
			final javax.swing.JFormattedTextField textField) {
		String strPos1 = textField.getText();
		if (strPos1.trim().equals("")) {
			return;
		}
		strPos1 = strPos1.replace(",", ".");
		try {
			final int pos1 = (int) (Float.parseFloat(strPos1));
			if (pos1 <= slider.getMaximum() && pos1 >= slider.getMinimum()) {
				// slider.setValue(pos1 - slider.getMinimum());
				slider.setValue(pos1);
			} else {
				textField.setValue(Integer.toString((int) slider.getValue()));
			}
		} catch (final Exception e) {
			textField.setValue(Integer.toString((int) slider.getValue()));
		}
	}

        
}
