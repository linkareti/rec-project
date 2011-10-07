/*
 * StatusPanel.java
 *
 * Created on July 27, 2004, 9:42 AM
 */

package com.linkare.rec.impl.baseUI;

/**
 * 
 * @author André Neto - LEFT - IST
 */

public class StatusPanel extends javax.swing.JPanel {
	/*
	 * private String STATUS_STR =
	 * Defaults.defaultIfEmpty(ReCResourceBundle.findString
	 * ("ReCBaseUI$lbl_apparatus"), "Apparatus status"); private String status =
	 * Defaults
	 * .defaultIfEmpty(ReCResourceBundle.findString("ReCBaseUI$lbl_unknown"),
	 * "Unknown");
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -3052779638392783714L;
	private String status = "";
	private String lblStatus = "";

	/** Creates new form StatusPanel */
	public StatusPanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		jLabelStatus = new javax.swing.JLabel();
		jLabelStatusState = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		jLabelStatus.setText(lblStatus);
		add(jLabelStatus, java.awt.BorderLayout.WEST);

		jLabelStatusState.setText(status);
		jLabelStatusState.setBorder(new javax.swing.border.EtchedBorder());
		add(jLabelStatusState, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	/**
	 * Getter for property status.
	 * 
	 * @return Value of property status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter for property status.
	 * 
	 * @param status New value of property status.
	 */
	public void setStatus(final String status) {
		this.status = status;
		jLabelStatusState.setText(status);
	}

	/**
	 * Getter for property lblStatus.
	 * 
	 * @return Value of property lblStatus.
	 */
	public String getLblStatus() {
		return lblStatus;
	}

	/**
	 * Setter for property lblStatus.
	 * @param lblStatus 
	 * 
	 * @param status New value of property lblStatus.
	 */
	public void setLblStatus(final String lblStatus) {
		this.lblStatus = lblStatus;
		jLabelStatus.setText(lblStatus);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabelStatus;
	private javax.swing.JLabel jLabelStatusState;
	// End of variables declaration//GEN-END:variables

}
