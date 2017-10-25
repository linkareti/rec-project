package com.linkare.rec.impl.newface.component;

import javax.swing.JDialog;

import com.linkare.rec.impl.newface.ReCApplication;

/**
 * 
 * @author bcatarino
 */
public class VideoErrorPanel extends AbstractContentPane {

	private static final long serialVersionUID = -865357167158613899L;

	private JDialog dialog;

	/**
	 * Creates new form VideoErrorPanel
	 * 
	 * @param dialog
	 */
	public VideoErrorPanel(JDialog dialog) {
		initComponents();
		this.dialog = dialog;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		explanationLabel = new javax.swing.JLabel();
		mrlText = new javax.swing.JTextField();
		chooserButton = new javax.swing.JButton();
		mrlLabel = new javax.swing.JLabel();
		playButton = new javax.swing.JButton();
		closeButton = new javax.swing.JButton();

		setName("Form"); // NOI18N
		setPreferredSize(new java.awt.Dimension(371, 165));

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(VideoErrorPanel.class);
		explanationLabel.setFont(resourceMap.getFont("explanationLabel.font")); // NOI18N
		explanationLabel.setText(resourceMap.getString("explanationLabel.text")); // NOI18N
		explanationLabel.setName("explanationLabel"); // NOI18N

		mrlText.setEditable(false);
		mrlText.setText(ReCApplication.getApplication().getCurrentApparatusVideoLocation());
		mrlText.setName("mrlText"); // NOI18N

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getActionMap(VideoErrorPanel.class, this);
		chooserButton.setAction(actionMap.get("askForVlcAction")); // NOI18N
		chooserButton.setText(resourceMap.getString("chooserButton.text")); // NOI18N
		chooserButton.setName("chooserButton"); // NOI18N

		mrlLabel.setFont(resourceMap.getFont("mrlLabel.font")); // NOI18N
		mrlLabel.setText(resourceMap.getString("mrlLabel.text")); // NOI18N
		mrlLabel.setName("mrlLabel"); // NOI18N

		playButton.setText(resourceMap.getString("playButton.text")); // NOI18N
		playButton.setName("playButton"); // NOI18N
		playButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playButtonActionPerformed(evt);
			}
		});

		closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
		closeButton.setName("closeButton"); // NOI18N
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(explanationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 347,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(mrlLabel)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(mrlText, javax.swing.GroupLayout.DEFAULT_SIZE,
																268, Short.MAX_VALUE))
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												layout.createSequentialGroup()
														.addComponent(closeButton,
																javax.swing.GroupLayout.DEFAULT_SIZE, 114,
																Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(chooserButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 114,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(playButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 107,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(explanationLabel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(mrlLabel)
												.addComponent(mrlText, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(playButton).addComponent(closeButton)
												.addComponent(chooserButton))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonActionPerformed
		closeWindow();
	}// GEN-LAST:event_closeButtonActionPerformed

	private void closeWindow() {
		if (dialog != null) {
			dialog.dispose();
		} else {
			this.setVisible(false);
		}
	}

	private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_playButtonActionPerformed
		ReCApplication.getApplication().playMediaExternalAction(mrlText.getText());
		closeWindow();
	}// GEN-LAST:event_playButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton chooserButton;
	private javax.swing.JButton closeButton;
	private javax.swing.JLabel explanationLabel;
	private javax.swing.JLabel mrlLabel;
	private javax.swing.JTextField mrlText;
	private javax.swing.JButton playButton;
	// End of variables declaration//GEN-END:variables
}