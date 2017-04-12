/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ColisioneCustomizerPanel.java
 *
 * Created on 09-Nov-2010, 11:29:48
 */
package pt.utl.ist.elab.client.colisione;

import javax.swing.JFormattedTextField;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author npadriano
 */
public class ColisioneCustomizerPanel extends AbstractCustomizer {

	/** Generated UID */
	private static final long serialVersionUID = -6367415208236048245L;

	public static final int NUMBER_OF_SAMPLES_FOR_SERIES = 1625;
	public static final int PROTOCOL_1_WAIT_MS = 20000;
	public static final int PROTOCOL_2_SAMPLING_INTERVAL_MS = 44;

	/** Creates new form ColisioneCustomizerPanel */
	public ColisioneCustomizerPanel() {
		initComponents();

		initComponentsManual();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelColisione = new javax.swing.JPanel();
        jPanelColisioneLaunchPower1 = new javax.swing.JPanel();
        jSliderColisioneLaunchPower1 = new javax.swing.JSlider();
        jFormattedTextFieldColisioneLaunchPower1 = new javax.swing.JFormattedTextField();
        jPanelColisioneLaunchPower2 = new javax.swing.JPanel();
        jSliderColisioneLaunchPower2 = new javax.swing.JSlider();
        jFormattedTextFieldColisioneLaunchPower2 = new javax.swing.JFormattedTextField();
        jPanelColisioneCamFollow = new javax.swing.JPanel();
        jRadioButton0ColisioneCamFollow = new javax.swing.JRadioButton();
        jRadioButton1ColisioneCamFollow = new javax.swing.JRadioButton();
        jRadioButton2ColisioneCamFollow = new javax.swing.JRadioButton();
        jRadioButton3ColisioneCamFollow = new javax.swing.JRadioButton();
        jPanelControlButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonDefaultConfig = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(350, 490));
        setName("OpticaCustomizer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(350, 490));
        setLayout(new java.awt.BorderLayout());

        jPanelColisione.setName("jPanelColisione"); // NOI18N
        jPanelColisione.setPreferredSize(new java.awt.Dimension(350, 372));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(ColisioneCustomizerPanel.class);
        jPanelColisioneLaunchPower1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanelColisioneLaunchPower1.border.title"))); // NOI18N
        jPanelColisioneLaunchPower1.setName("jPanelColisioneLaunchPower1"); // NOI18N

        jSliderColisioneLaunchPower1.setMajorTickSpacing(10);
        jSliderColisioneLaunchPower1.setMinorTickSpacing(10);
        jSliderColisioneLaunchPower1.setPaintLabels(true);
        jSliderColisioneLaunchPower1.setPaintTicks(true);
        jSliderColisioneLaunchPower1.setName("jSliderColisioneLaunchPower1"); // NOI18N
        jSliderColisioneLaunchPower1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderColisioneLaunchPower1StateChanged(evt);
            }
        });

        jFormattedTextFieldColisioneLaunchPower1.setText(resourceMap.getString("jFormattedTextFieldColisioneLaunchPower1.text")); // NOI18N
        jFormattedTextFieldColisioneLaunchPower1.setName("jFormattedTextFieldColisioneLaunchPower1"); // NOI18N
        jFormattedTextFieldColisioneLaunchPower1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldColisioneLaunchPower1FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelColisioneLaunchPower1Layout = new javax.swing.GroupLayout(jPanelColisioneLaunchPower1);
        jPanelColisioneLaunchPower1.setLayout(jPanelColisioneLaunchPower1Layout);
        jPanelColisioneLaunchPower1Layout.setHorizontalGroup(
            jPanelColisioneLaunchPower1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneLaunchPower1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSliderColisioneLaunchPower1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldColisioneLaunchPower1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelColisioneLaunchPower1Layout.setVerticalGroup(
            jPanelColisioneLaunchPower1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneLaunchPower1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jSliderColisioneLaunchPower1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelColisioneLaunchPower1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jFormattedTextFieldColisioneLaunchPower1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanelColisioneLaunchPower2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanelColisioneLaunchPower2.border.title"))); // NOI18N
        jPanelColisioneLaunchPower2.setName("jPanelColisioneLaunchPower2"); // NOI18N

        jSliderColisioneLaunchPower2.setMajorTickSpacing(10);
        jSliderColisioneLaunchPower2.setMinorTickSpacing(10);
        jSliderColisioneLaunchPower2.setPaintLabels(true);
        jSliderColisioneLaunchPower2.setPaintTicks(true);
        jSliderColisioneLaunchPower2.setName("jSliderColisioneLaunchPower2"); // NOI18N
        jSliderColisioneLaunchPower2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderColisioneLaunchPower2StateChanged(evt);
            }
        });

        jFormattedTextFieldColisioneLaunchPower2.setText(resourceMap.getString("jFormattedTextFieldColisioneLaunchPower2.text")); // NOI18N
        jFormattedTextFieldColisioneLaunchPower2.setName("jFormattedTextFieldColisioneLaunchPower2"); // NOI18N
        jFormattedTextFieldColisioneLaunchPower2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldColisioneLaunchPower2FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelColisioneLaunchPower2Layout = new javax.swing.GroupLayout(jPanelColisioneLaunchPower2);
        jPanelColisioneLaunchPower2.setLayout(jPanelColisioneLaunchPower2Layout);
        jPanelColisioneLaunchPower2Layout.setHorizontalGroup(
            jPanelColisioneLaunchPower2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneLaunchPower2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSliderColisioneLaunchPower2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldColisioneLaunchPower2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelColisioneLaunchPower2Layout.setVerticalGroup(
            jPanelColisioneLaunchPower2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneLaunchPower2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jSliderColisioneLaunchPower2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelColisioneLaunchPower2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jFormattedTextFieldColisioneLaunchPower2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanelColisioneCamFollow.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanelColisioneCamFollow.border.title"))); // NOI18N
        jPanelColisioneCamFollow.setName("jPanelColisioneCamFollow"); // NOI18N

        buttonGroup1.add(jRadioButton0ColisioneCamFollow);
        jRadioButton0ColisioneCamFollow.setText(resourceMap.getString("jRadioButton0ColisioneCamFollow.text")); // NOI18N
        jRadioButton0ColisioneCamFollow.setName("jRadioButton0ColisioneCamFollow"); // NOI18N

        buttonGroup1.add(jRadioButton1ColisioneCamFollow);
        jRadioButton1ColisioneCamFollow.setText(resourceMap.getString("jRadioButton1ColisioneCamFollow.text")); // NOI18N
        jRadioButton1ColisioneCamFollow.setName("jRadioButton1ColisioneCamFollow"); // NOI18N

        buttonGroup1.add(jRadioButton2ColisioneCamFollow);
        jRadioButton2ColisioneCamFollow.setText(resourceMap.getString("jRadioButton2ColisioneCamFollow.text")); // NOI18N
        jRadioButton2ColisioneCamFollow.setName("jRadioButton2ColisioneCamFollow"); // NOI18N

        buttonGroup1.add(jRadioButton3ColisioneCamFollow);
        jRadioButton3ColisioneCamFollow.setSelected(true);
        jRadioButton3ColisioneCamFollow.setText(resourceMap.getString("jRadioButton3ColisioneCamFollow.text")); // NOI18N
        jRadioButton3ColisioneCamFollow.setName("jRadioButton3ColisioneCamFollow"); // NOI18N

        javax.swing.GroupLayout jPanelColisioneCamFollowLayout = new javax.swing.GroupLayout(jPanelColisioneCamFollow);
        jPanelColisioneCamFollow.setLayout(jPanelColisioneCamFollowLayout);
        jPanelColisioneCamFollowLayout.setHorizontalGroup(
            jPanelColisioneCamFollowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneCamFollowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColisioneCamFollowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2ColisioneCamFollow)
                    .addComponent(jRadioButton3ColisioneCamFollow)
                    .addComponent(jRadioButton1ColisioneCamFollow)
                    .addComponent(jRadioButton0ColisioneCamFollow))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanelColisioneCamFollowLayout.setVerticalGroup(
            jPanelColisioneCamFollowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneCamFollowLayout.createSequentialGroup()
                .addComponent(jRadioButton0ColisioneCamFollow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1ColisioneCamFollow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2ColisioneCamFollow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3ColisioneCamFollow))
        );

        javax.swing.GroupLayout jPanelColisioneLayout = new javax.swing.GroupLayout(jPanelColisione);
        jPanelColisione.setLayout(jPanelColisioneLayout);
        jPanelColisioneLayout.setHorizontalGroup(
            jPanelColisioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelColisioneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColisioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelColisioneCamFollow, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColisioneLaunchPower1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColisioneLaunchPower2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelColisioneLayout.setVerticalGroup(
            jPanelColisioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColisioneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelColisioneLaunchPower1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelColisioneLaunchPower2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelColisioneCamFollow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        add(jPanelColisione, java.awt.BorderLayout.WEST);

        jPanelControlButtons.setName("jPanelControlButtons"); // NOI18N
        jPanelControlButtons.setPreferredSize(new java.awt.Dimension(350, 37));

        jButtonOk.setText(resourceMap.getString("Ok.label")); // NOI18N
        jButtonOk.setName("Ok"); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText(resourceMap.getString("Cancel.label")); // NOI18N
        jButtonCancel.setName("Cancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonDefaultConfig.setText(resourceMap.getString("DefaultConfig.label")); // NOI18N
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
                .addGap(18, 18, 18)
                .addComponent(jButtonDefaultConfig)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanelControlButtonsLayout.setVerticalGroup(
            jPanelControlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelControlButtonsLayout.createSequentialGroup()
                .addGroup(jPanelControlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonDefaultConfig))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButtonOk.getAccessibleContext().setAccessibleName(resourceMap.getString("Ok.AccessibleContext.accessibleName")); // NOI18N
        jButtonCancel.getAccessibleContext().setAccessibleName(resourceMap.getString("jButtonCancel.AccessibleContext.accessibleName")); // NOI18N
        jButtonDefaultConfig.getAccessibleContext().setAccessibleName(resourceMap.getString("jButtonDefaultConfig.AccessibleContext.accessibleName")); // NOI18N

        add(jPanelControlButtons, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

        private void jSliderColisioneLaunchPower1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderColisioneLaunchPower1StateChanged
            sliderChanged(jSliderColisioneLaunchPower1, jFormattedTextFieldColisioneLaunchPower1);
        }//GEN-LAST:event_jSliderColisioneLaunchPower1StateChanged

        private void jSliderColisioneLaunchPower2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderColisioneLaunchPower2StateChanged
            sliderChanged(jSliderColisioneLaunchPower2, jFormattedTextFieldColisioneLaunchPower2);
        }//GEN-LAST:event_jSliderColisioneLaunchPower2StateChanged

        private void jFormattedTextFieldColisioneLaunchPower1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldColisioneLaunchPower1FocusLost
            formattedTextChanged(jSliderColisioneLaunchPower1, jFormattedTextFieldColisioneLaunchPower1);
}//GEN-LAST:event_jFormattedTextFieldColisioneLaunchPower1FocusLost

        private void jFormattedTextFieldColisioneLaunchPower2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldColisioneLaunchPower2FocusLost
            formattedTextChanged(jSliderColisioneLaunchPower2, jFormattedTextFieldColisioneLaunchPower2);
}//GEN-LAST:event_jFormattedTextFieldColisioneLaunchPower2FocusLost

	private void jButtonCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_jButtonCancelActionPerformed

	private void jButtonDefaultConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDefaultConfigActionPerformed
                jSliderColisioneLaunchPower1.setValue(50);
		jFormattedTextFieldColisioneLaunchPower1.setText("50");
                jSliderColisioneLaunchPower2.setValue(50);
		jFormattedTextFieldColisioneLaunchPower2.setText("50");
                jRadioButton0ColisioneCamFollow.setSelected(false);
                jRadioButton1ColisioneCamFollow.setSelected(false);
                jRadioButton2ColisioneCamFollow.setSelected(false);
                jRadioButton3ColisioneCamFollow.setSelected(true);
	}// GEN-LAST:event_jButtonDefaultConfigActionPerformed

	private void jButtonOkActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonOkActionPerformed

		int selectCheckBoxCount = 0;
		int nsamples = 0;
		Frequency freq = null;

		// inicializar parametros
		int poder_acel1 = 50;
                int poder_acel2 = 50;
		int cam_segue = 3;

		poder_acel1 = (int) jSliderColisioneLaunchPower1.getValue();
		poder_acel2 = (int) jSliderColisioneLaunchPower2.getValue();

                if (jRadioButton0ColisioneCamFollow.isSelected()) {
			cam_segue = 0;
		} else if (jRadioButton1ColisioneCamFollow.isSelected()) {
			cam_segue = 1;
		} else if (jRadioButton2ColisioneCamFollow.isSelected()) {
			cam_segue = 2;
		} else if (jRadioButton3ColisioneCamFollow.isSelected()) {
			cam_segue = 3;
                } else {
			cam_segue = 0;
		}
                
                //COMENTEI PARA COMPILAR
                //final int time = (int) Math.ceil((OpticaCustomizerPanel.PROTOCOL_1_WAIT_MS + nsamples * delay) / nsamples);
                //freq = new Frequency(time, Multiplier.mili, FrequencyDefType.SamplingIntervalType);
                
                //COMENTEI PARA COMPILAR
		getAcquisitionConfig().setTotalSamples(8);
		getAcquisitionConfig().getSelectedHardwareParameter("poder_acel1").setParameterValue(String.valueOf(poder_acel1));
		getAcquisitionConfig().getSelectedHardwareParameter("poder_acel2").setParameterValue(String.valueOf(poder_acel2));
		getAcquisitionConfig().getSelectedHardwareParameter("cam_segue").setParameterValue(String.valueOf(cam_segue));

                //COMENTEI PARA COMPILAR
		getAcquisitionConfig().setSelectedFrequency(new Frequency(1));

		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonOkActionPerformed

	//private void jTabbedPaneColisioneStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jTabbedPaneColisioneStateChanged
	//	validateScreen();
	//}// GEN-LAST:event_jTabbedPaneColisioneStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDefaultConfig;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JFormattedTextField jFormattedTextFieldColisioneLaunchPower1;
    private javax.swing.JFormattedTextField jFormattedTextFieldColisioneLaunchPower2;
    private javax.swing.JPanel jPanelColisione;
    private javax.swing.JPanel jPanelColisioneCamFollow;
    private javax.swing.JPanel jPanelColisioneLaunchPower1;
    private javax.swing.JPanel jPanelColisioneLaunchPower2;
    private javax.swing.JPanel jPanelControlButtons;
    private javax.swing.JRadioButton jRadioButton0ColisioneCamFollow;
    private javax.swing.JRadioButton jRadioButton1ColisioneCamFollow;
    private javax.swing.JRadioButton jRadioButton2ColisioneCamFollow;
    private javax.swing.JRadioButton jRadioButton3ColisioneCamFollow;
    private javax.swing.JSlider jSliderColisioneLaunchPower1;
    private javax.swing.JSlider jSliderColisioneLaunchPower2;
    // End of variables declaration//GEN-END:variables

	

	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			System.out.println("ColisioneCustomizer.setHardwareAcquisitionConfig(HardwareAcquisitionConfig  acqConfig)");
			System.out.println("acqConfig: [" + acqConfig + "]");

			// TODO
			// int nsamples = acqConfig.getTotalSamples();
			// sldNumSamples.setValue(nsamples);
			// tfNumSamples.setText("" + nsamples);
			//
			// int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			// sldFreq.setValue(freq);
			// tfFreq.setText("" + freq);
			//
			// float pos1f =
			// Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("UserPosLow"));
			// int pos1 = (int) Math.floor(pos1f * 1000.F);
			// sldPos1.setValue(pos1);
			// tfPos1.setValue(new Float(pos1f));
			//
			// float pos2f =
			// Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("UserPosHigh"));
			// int pos2 = (int) Math.floor(pos2f * 1000.F);
			// sldPos2.setValue(pos2);
			// tfPos2.setValue(new Float(pos2f));
		}
	}


	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/colisione/resources/colisione_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("colisione$rec.exp.colisione.customizer.title","colisione$rec.exp.colisione.customizer.title");
	}


	private void initComponentsManual() {
		// TODO
		// checkMaxNumSamples();
		// checkMaxTime();
		// checkPosOverlap();
	}

	private void validateScreen() {
		boolean valid = true;
		jButtonOk.setEnabled(valid);
	}

	private void sliderMultipliedChanged(final javax.swing.JSlider slider,
			final javax.swing.JFormattedTextField textField) {
		textField.setText(Float.toString((float) slider.getValue() / 10.F));
	}

	private void sliderChanged(final javax.swing.JSlider slider, final javax.swing.JFormattedTextField textField) {
		textField.setText(Integer.toString(slider.getValue()));
	}

	private void formattedMultipliedTextChanged(final javax.swing.JSlider slider,
			final javax.swing.JFormattedTextField textField) {
		String strPos1 = textField.getText();
		if (strPos1.trim().equals("")) {
			return;
		}
		strPos1 = strPos1.replace(",", ".");
		try {
			final int pos1 = (int) (Float.parseFloat(strPos1) * 10.F);
			if (pos1 <= slider.getMaximum() && pos1 >= slider.getMinimum()) {
				// slider.setValue(pos1 - slider.getMinimum());
				slider.setValue(pos1);
			} else {
				textField.setValue(Float.toString((float) slider.getValue() / 10.F));
			}
		} catch (final Exception e) {
			textField.setValue(Float.toString((float) slider.getValue() / 10.F));
		}
	}

	private void formattedTextChanged(final javax.swing.JSlider slider, final javax.swing.JFormattedTextField textField) {
		String strPos1 = textField.getText();
		if (strPos1.trim().equals("")) {
			return;
		}
		strPos1 = strPos1.replace(",", ".");
		try {
			final int pos1 = (int) Integer.parseInt(strPos1);
			if (pos1 <= slider.getMaximum() && pos1 >= slider.getMinimum()) {
				// slider.setValue(pos1 - slider.getMinimum());
				slider.setValue(pos1);
			} else {
				textField.setValue(slider.getValue());
			}
		} catch (final Exception e) {
			textField.setValue(slider.getValue());
		}
	}
}
