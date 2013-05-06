/*
 * DefaultConfigBuilder.java
 *
 * Created on September 10, 2004, 3:53 PM
 */

package com.linkare.editor;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.awt.image.ImageObserver;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.CustomizerUIUtil;
import com.linkare.rec.impl.client.customizer.ICustomizer;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.utils.HardwareInfoXMLReader;

public class DefaultConfigBuilder extends javax.swing.JFrame implements ICustomizerListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -687470796173092986L;

	private ICustomizer customizer = null;

	/** Creates new form DefaultConfigBuilder */
	public DefaultConfigBuilder() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jDialog1 = new javax.swing.JDialog();
		jPanel2 = new javax.swing.JPanel();
		jButtonOK = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jTextFieldURL = new javax.swing.JTextField();
		jTextFieldHInfoXML = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jButtonBrowse = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jButtonOpen = new javax.swing.JButton();

		jDialog1.setTitle("Customizer Properties...");
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonOKActionPerformed(evt);
			}
		});

		jPanel2.add(jButtonOK);

		jButtonCancel.setText("Cancel");
		jPanel2.add(jButtonCancel);

		jDialog1.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

		jPanel3.setLayout(new java.awt.GridBagLayout());

		jTextFieldURL.setColumns(50);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		jPanel3.add(jTextFieldURL, gridBagConstraints);

		jTextFieldHInfoXML.setColumns(50);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel3.add(jTextFieldHInfoXML, gridBagConstraints);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setText("Customizer URL");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel3.add(jLabel1, gridBagConstraints);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel2.setText("HardwareInfo XML");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel3.add(jLabel2, gridBagConstraints);

		jButtonBrowse.setText("Browse");
		jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonBrowseActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		jPanel3.add(jButtonBrowse, gridBagConstraints);

		jDialog1.getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});

		jButtonOpen.setText("Open Customizer");
		jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonOpenActionPerformed(evt);
			}
		});

		jPanel1.add(jButtonOpen);

		getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

		pack();
	}// GEN-END:initComponents

	private void jButtonBrowseActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonBrowseActionPerformed
	{// GEN-HEADEREND:event_jButtonBrowseActionPerformed
		final JFileChooser jfc = new JFileChooser();
		final int answer = jfc.showOpenDialog(this);
		if (answer != JFileChooser.APPROVE_OPTION) {
			return;
		}

		jTextFieldHInfoXML.setText(jfc.getSelectedFile().getAbsolutePath());
	}// GEN-LAST:event_jButtonBrowseActionPerformed

	private void jButtonOpenActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonOpenActionPerformed
	{// GEN-HEADEREND:event_jButtonOpenActionPerformed
		jDialog1.pack();
		jDialog1.setVisible(true);
	}// GEN-LAST:event_jButtonOpenActionPerformed

	private void jButtonOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonOKActionPerformed
	{// GEN-HEADEREND:event_jButtonOKActionPerformed
		try {
			customizer = CustomizerUIUtil.loadCustomizer(jTextFieldURL.getText().trim());
			final HardwareInfo hinfo = HardwareInfoXMLReader.readHardwareInfo(jTextFieldHInfoXML.getText().trim());
			customizer.setHardwareAcquisitionConfig(hinfo.createBaseHardwareAcquisitionConfig());
			customizer.setHardwareInfo(hinfo);
			customizer.addICustomizerListener(this);
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(this, "An error occurred while loading the customizer..." + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		getContentPane().add(customizer.getCustomizerComponent(), java.awt.BorderLayout.CENTER);
		jDialog1.setVisible(true);
		pack();

		JOptionPane.showMessageDialog(this, "To serialize just press the OK button of the customizer...", "How to...",
				JOptionPane.INFORMATION_MESSAGE);
	}// GEN-LAST:event_jButtonOKActionPerformed

	/** Exit the Application */
	private void exitForm(final java.awt.event.WindowEvent evt)// GEN-FIRST:event_exitForm
	{
		System.exit(0);
	}// GEN-LAST:event_exitForm

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String args[]) {
		new DefaultConfigBuilder().setVisible(true);
	}

	@Override
	public void canceled() {
		getContentPane().remove(customizer.getCustomizerComponent());
		pack();
	}

	@Override
	public void done() {
		final JFileChooser jfc = new JFileChooser();
		final int answer = jfc.showSaveDialog(this);
		if (answer != JFileChooser.APPROVE_OPTION) {
			return;
		}

		try {
			final java.io.File f = jfc.getSelectedFile();
			final java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
			final java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
			oos.writeObject(customizer.getAcquisitionConfig());
			fos.close();
			oos.close();
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(this, "There was an error serializing the customizer acquisition config..."
					+ e.getMessage(), "Error", ImageObserver.ERROR);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonBrowse;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonOK;
	private javax.swing.JButton jButtonOpen;
	private javax.swing.JDialog jDialog1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JTextField jTextFieldHInfoXML;
	private javax.swing.JTextField jTextFieldURL;
	// End of variables declaration//GEN-END:variables

}
