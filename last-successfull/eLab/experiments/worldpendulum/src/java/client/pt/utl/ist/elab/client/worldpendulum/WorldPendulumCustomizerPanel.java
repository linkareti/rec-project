/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WorldPendulumCustomizerPanel.java
 *
 * Created on 09-Nov-2010, 11:29:48
 */
package pt.utl.ist.elab.client.worldpendulum;

//import java.util.Dictionary;
//import java.util.Hashtable;

import javax.swing.JFormattedTextField;
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
	// public static final int PROTOCOL_2_SAMPLING_INTERVAL_MS = 44;
	// public static final int PROTOCOL_3_SAMPLING_INTERVAL_MS = 43;
	// public static final int PROTOCOL_4_SAMPLING_INTERVAL_MS = 45;
	// public static final int PROTOCOL_5_SAMPLING_INTERVAL_MS = 60000;

	/** Creates new form WorldPendulumCustomizerPanel */
	public WorldPendulumCustomizerPanel() {
		initComponents();

		//initComponentsManual();
		
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
        jFormattedTextFieldEstimatedTime = new javax.swing.JFormattedTextField();
        jLabelEstimatedTime = new javax.swing.JLabel();
        jPanelControlButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonDefaultConfig = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(350, 490));
        setName("WorldPendulumCustomizer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(350, 490));
        setLayout(new java.awt.BorderLayout());

        jTabbedPaneWorldPendulum.setName("jTabbedPaneWorldPendulum"); // NOI18N
        jTabbedPaneWorldPendulum.setPreferredSize(new java.awt.Dimension(350, 417));
        jTabbedPaneWorldPendulum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneWorldPendulumStateChanged(evt);
            }
        });

        jPanelPendulumConfig.setName("jPanelPendulumConfig"); // NOI18N
        jPanelPendulumConfig.setPreferredSize(new java.awt.Dimension(350, 372));

        jPanelPenduloDisplacement.setBorder(javax.swing.BorderFactory.createTitledBorder(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jPanelPenduloDisplacement.border.title"))); // NOI18N
        jPanelPenduloDisplacement.setName("jPanelPenduloDisplacement"); // NOI18N
        jPanelPenduloDisplacement.setPreferredSize(new java.awt.Dimension(330, 126));

        jSliderDisplacement.setMajorTickSpacing(5);
        jSliderDisplacement.setMaximum(20);
        jSliderDisplacement.setMinimum(5);
        jSliderDisplacement.setMinorTickSpacing(1);
        jSliderDisplacement.setPaintLabels(true);
        jSliderDisplacement.setPaintTicks(true);
        jSliderDisplacement.setToolTipText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jSliderDisplacement.toolTipText")); // NOI18N
        jSliderDisplacement.setMinimumSize(new java.awt.Dimension(250, 16));
        jSliderDisplacement.setName("jSliderDisplacement"); // NOI18N
        jSliderDisplacement.setPreferredSize(new java.awt.Dimension(250, 42));
        jSliderDisplacement.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderDisplacementStateChanged(evt);
            }
        });

        jFormattedTextFieldDisplacement.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jFormattedTextFieldDisplacement.text")); // NOI18N
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

        jPanelNrPoints.setBorder(javax.swing.BorderFactory.createTitledBorder(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jPanelNrPoints.border.title"))); // NOI18N
        jPanelNrPoints.setName("jPanelNrPoints"); // NOI18N
        jPanelNrPoints.setPreferredSize(new java.awt.Dimension(330, 126));

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

        jFormattedTextFieldNrPoints.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jFormattedTextFieldNrPoints.text")); // NOI18N
        jFormattedTextFieldNrPoints.setToolTipText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jFormattedTextFieldNrPoints.toolTipText")); // NOI18N
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
                .addComponent(jFormattedTextFieldNrPoints, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
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

        jFormattedTextFieldEstimatedTime.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jFormattedTextFieldEstimatedTime.text")); // NOI18N
        jFormattedTextFieldEstimatedTime.setName("jFormattedTextFieldEstimatedTime"); // NOI18N

        jLabelEstimatedTime.setForeground(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getColor("jLabelEstimatedTime.foreground")); // NOI18N
        jLabelEstimatedTime.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jLabelEstimatedTime.text")); // NOI18N
        jLabelEstimatedTime.setEnabled(false);
        jLabelEstimatedTime.setName("jLabelEstimatedTime"); // NOI18N

        javax.swing.GroupLayout jPanelPendulumConfigLayout = new javax.swing.GroupLayout(jPanelPendulumConfig);
        jPanelPendulumConfig.setLayout(jPanelPendulumConfigLayout);
        jPanelPendulumConfigLayout.setHorizontalGroup(
            jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPendulumConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelPendulumConfigLayout.createSequentialGroup()
                        .addComponent(jLabelEstimatedTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextFieldEstimatedTime, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelPenduloDisplacement, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(jPanelNrPoints, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelPendulumConfigLayout.setVerticalGroup(
            jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPendulumConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPenduloDisplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelNrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(jPanelPendulumConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEstimatedTime)
                    .addComponent(jFormattedTextFieldEstimatedTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );

        jTabbedPaneWorldPendulum.addTab(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jPanelPendulumConfig.TabConstraints.tabTitle"), jPanelPendulumConfig); // NOI18N

        add(jTabbedPaneWorldPendulum, java.awt.BorderLayout.CENTER);

        jPanelControlButtons.setName("jPanelControlButtons"); // NOI18N
        jPanelControlButtons.setPreferredSize(new java.awt.Dimension(350, 37));

        jButtonOk.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("Ok.label")); // NOI18N
        jButtonOk.setName("Ok"); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("Cancel.label")); // NOI18N
        jButtonCancel.setName("Cancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonDefaultConfig.setText(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("DefaultConfig.label")); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
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

        jButtonOk.getAccessibleContext().setAccessibleName(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("Ok.AccessibleContext.accessibleName")); // NOI18N
        jButtonCancel.getAccessibleContext().setAccessibleName(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jButtonCancel.AccessibleContext.accessibleName")); // NOI18N
        jButtonDefaultConfig.getAccessibleContext().setAccessibleName(org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorldPendulumCustomizerPanel.class).getString("jButtonDefaultConfig.AccessibleContext.accessibleName")); // NOI18N

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
		jFormattedTextFieldDisplacement.setText("10.0");
		jSliderNrPoints.setValue(100);
		jFormattedTextFieldNrPoints.setText("100");
		//jLabelEstimatedTime.setEnabled(true);
                validateScreen();

	}// GEN-LAST:event_jButtonDefaultConfigActionPerformed

	/* código antigo óptica
        private void jFormattedTextFieldSnellAngleVarationMaxFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jFormattedTextFieldSnellAngleVarationMaxFocusLost
		formattedMultipliedTextChanged(jSliderNumeroPontos, jFormattedTextFieldNumeroPontos);
	}// GEN-LAST:event_jFormattedTextFieldSnellAngleVarationMaxFocusLost

	private void jFormattedTextFieldSnellAngleVarationMinFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jFormattedTextFieldSnellAngleVarationMinFocusLost
		formattedMultipliedTextChanged(jSliderDisplacement, jFormattedTextFieldDisplacement);
	}// GEN-LAST:event_jFormattedTextFieldSnellAngleVarationMinFocusLost

         */
        
	private void jButtonOkActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonOkActionPerformed

		int nsamples = 0;
		Frequency freq = null;                
        float displacement = 10;

		// protocol = jTabbedPaneWorldPendulum.getSelectedIndex() + 1;
                
            displacement = (float) jSliderDisplacement.getValue();
            nsamples = (int) jSliderNrPoints.getValue();        
            
            final int time = (int) Math.ceil((WorldPendulumCustomizerPanel.PERIOD * nsamples*(1.1) / nsamples));
            freq = new Frequency(time, Multiplier.none, FrequencyDefType.SamplingIntervalType);

		
            getAcquisitionConfig().setTotalSamples(nsamples);
                
            getAcquisitionConfig().getSelectedHardwareParameter("worldpendulum$parameter.displacement").setParameterValue(String.valueOf(displacement));
            getAcquisitionConfig().getSelectedHardwareParameter("worldpendulum$parameter.nrpoints").setParameterValue(String.valueOf(nsamples));        
        
            getAcquisitionConfig().setSelectedFrequency(freq);

		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDefaultConfig;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JFormattedTextField jFormattedTextFieldDisplacement;
    private javax.swing.JFormattedTextField jFormattedTextFieldEstimatedTime;
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

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.

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

	@Override
	public synchronized void removeICustomizerListener(final ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 
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

	private HardwareInfo hardwareInfo = null;
	private HardwareAcquisitionConfig acqConfig = null;

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
	}

	*/
	
	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		super.setHardwareAcquisitionConfig(acqConfig);
		
                // Requires a proper LOGGER output
                if (acqConfig != null) {
			System.out.println("WorldPendulumCustomizer.setHardwareAcquisitionConfig(HardwareAcquisitionConfig  acqConfig)");
			System.out.println("acqConfig: [" + acqConfig + "]");

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

	/*
	@Override
	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	@Override
	public javax.swing.JComponent getCustomizerComponent() {
		return this;
	}
	*/
	
	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/worldpendulum/resources/worldpendulum_iconified.gif"));
	}

	@Override
	public String getCustomizerTitle() {
		return ReCResourceBundle.findStringOrDefault("worldpendulum$rec.exp.worldpendulum.customizer.title","worldpendulum$rec.exp.worldpendulum.customizer.title");
	}

	/* @Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}
	*/
	private void installDecimalFormatter(final JFormattedTextField ftf) {
		/*
		 * DecimalFormat formatDecimal = new DecimalFormat("0.0");
		 * formatDecimal.setDecimalSeparatorAlwaysShown(true);
		 * formatDecimal.setGroupingUsed(false);
		 * formatDecimal.setMinimumFractionDigits(1);
		 * 
		 * NumberFormatter formatter = new NumberFormatter(formatDecimal);
		 * formatter.setCommitsOnValidEdit(true);
		 * formatter.setOverwriteMode(true); formatter.install(ftf);
		 */
	}

	/*
	private void installNaturalFormatter(final JFormattedTextField ftf) {
		/*
		 * DecimalFormat naturalFormat = new DecimalFormat("0");
		 * naturalFormat.setDecimalSeparatorAlwaysShown(false);
		 * naturalFormat.setGroupingUsed(false);
		 * naturalFormat.setMinimumFractionDigits(0);
		 * 
		 * NumberFormatter formatter = new NumberFormatter(naturalFormat);
		 * formatter.setCommitsOnValidEdit(true);
		 * formatter.setOverwriteMode(true); formatter.install(ftf);
		 
	}
	*/
	
	
	private void initComponentsManual() {

            /*
		final Dictionary<Integer, JLabel> slidersAngle0360Labels = new Hashtable<Integer, JLabel>();
		slidersAngle0360Labels.put(new Integer(0), new JLabel("0.0"));
		slidersAngle0360Labels.put(new Integer(900), new JLabel("90.0"));
		slidersAngle0360Labels.put(new Integer(1800), new JLabel("180.0"));
		slidersAngle0360Labels.put(new Integer(2700), new JLabel("270.0"));
		slidersAngle0360Labels.put(new Integer(3600), new JLabel("360.0"));

		final Dictionary<Integer, JLabel> slidersAngle02360Labels = new Hashtable<Integer, JLabel>();
		slidersAngle02360Labels.put(new Integer(2), new JLabel("0.2"));
		slidersAngle02360Labels.put(new Integer(900), new JLabel("90.0"));
		slidersAngle02360Labels.put(new Integer(1800), new JLabel("180.0"));
		slidersAngle02360Labels.put(new Integer(2700), new JLabel("270.0"));
		slidersAngle02360Labels.put(new Integer(3600), new JLabel("360.0"));

		final Dictionary<Integer, JLabel> slidersAngle90Labels = new Hashtable<Integer, JLabel>();
		slidersAngle90Labels.put(new Integer(0), new JLabel("0.0"));
		slidersAngle90Labels.put(new Integer(450), new JLabel("45.0"));
		slidersAngle90Labels.put(new Integer(900), new JLabel("90.0"));

		final Dictionary<Integer, JLabel> slidersDelayLabels = new Hashtable<Integer, JLabel>();
		slidersDelayLabels.put(new Integer(0), new JLabel("0.0"));
		slidersDelayLabels.put(new Integer(10), new JLabel("1.0"));
		slidersDelayLabels.put(new Integer(20), new JLabel("2.0"));
		slidersDelayLabels.put(new Integer(30), new JLabel("3.0"));
		slidersDelayLabels.put(new Integer(40), new JLabel("4.0"));
		slidersDelayLabels.put(new Integer(50), new JLabel("5.0"));

		jSliderDisplacement.setLabelTable(slidersAngle0360Labels);
		jSliderNrPoints.setLabelTable(slidersAngle0360Labels);
		
             * 
             */
	}

	private void validateScreen() {
		boolean valid = true;
		//final int protocol = jTabbedPaneWorldPendulum.getSelectedIndex() + 1;
                        
                jFormattedTextFieldEstimatedTime.setText(Float.toString(((float) jSliderNrPoints.getValue()) * PERIOD));
                
			/* angle variation
			if (jSliderDisplacement.getValue() <= jSliderNumeroPontos.getValue()) {
				jLabelDuracao.setEnabled(false);
			} else {
				jLabelDuracao.setEnabled(true);
				valid = false;
			}
                         * 
                         */

		jButtonOk.setEnabled(valid);
	}

	private void sliderMultipliedChanged(final javax.swing.JSlider slider,
			final javax.swing.JFormattedTextField textField) {
		textField.setText(Float.toString((float) slider.getValue()));
	}

//	private void sliderChanged(final javax.swing.JSlider slider, final javax.swing.JFormattedTextField textField) {
//		textField.setText(Integer.toString(slider.getValue()));
//	}

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
				textField.setValue(Float.toString((float) slider.getValue()));
			}
		} catch (final Exception e) {
			textField.setValue(Float.toString((float) slider.getValue()));
		}
	}

//	private void formattedTextChanged(final javax.swing.JSlider slider, final javax.swing.JFormattedTextField textField) {
//		String strPos1 = textField.getText();
//		if (strPos1.trim().equals("")) {
//			return;
//		}
//		strPos1 = strPos1.replace(",", ".");
//		try {
//			if (pos1 <= slider.getMaximum() && pos1 >= slider.getMinimum()) {
// 				slider.setValue(pos1 - slider.getMinimum());
//				slider.setValue(pos1);
//			} else {
//				textField.setValue(slider.getValue());
//			}
//		} catch (final Exception e) {
//			textField.setValue(slider.getValue());
//		}
//	}
}
