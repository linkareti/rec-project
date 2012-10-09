/*
 * ConfCompBin.java
 *
 * Created on 28 de Janeiro de 2003, 19:06
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs;

/**
 * 
 * @author Andr�
 */
public class ConfAddNewElement extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1992710608092283689L;
	private final Object[] returnObj = new Object[3];

	/** Creates new form ConfCompBin 
	 * @param parent 
	 * @param modal 
	 * @param i0State 
	 * @param i1State 
	 * @param i2State 
	 * @param i3State 
	 * @param i4State 
	 * @param i5State 
	 * @param i6State 
	 * @param i7State 
	 * @param row */
	public ConfAddNewElement(final javax.swing.JDialog parent, final boolean modal, final boolean i0State,
			final boolean i1State, final boolean i2State, final boolean i3State, final boolean i4State,
			final boolean i5State, final boolean i6State, final boolean i7State, final Object[] row) {
		super(parent, modal);
		initComponents();
		jRadioButtonI0.setEnabled(i0State);
		jRadioButtonI1.setEnabled(i1State);
		jRadioButtonI2.setEnabled(i2State);
		jRadioButtonI3.setEnabled(i3State);
		jRadioButtonI4.setEnabled(i4State);
		jRadioButtonI5.setEnabled(i5State);
		jRadioButtonI6.setEnabled(i6State);
		jRadioButtonI7.setEnabled(i7State);
		final String selected = (String) row[0];
		final Integer PWM1 = (Integer) row[1];
		final Integer PWM2 = (Integer) row[2];
		if (jRadioButtonI0.isEnabled() && selected.charAt(7) == '1') {
			jRadioButtonI0.setSelected(true);
		} else {
			jRadioButtonI0.setSelected(false);
		}
		if (jRadioButtonI1.isEnabled() && selected.charAt(6) == '1') {
			jRadioButtonI1.setSelected(true);
		} else {
			jRadioButtonI1.setSelected(false);
		}
		if (jRadioButtonI2.isEnabled() && selected.charAt(5) == '1') {
			jRadioButtonI2.setSelected(true);
		} else {
			jRadioButtonI2.setSelected(false);
		}
		if (jRadioButtonI3.isEnabled() && selected.charAt(4) == '1') {
			jRadioButtonI3.setSelected(true);
		} else {
			jRadioButtonI3.setSelected(false);
		}
		if (jRadioButtonI4.isEnabled() && selected.charAt(3) == '1') {
			jRadioButtonI4.setSelected(true);
		} else {
			jRadioButtonI4.setSelected(false);
		}
		if (jRadioButtonI5.isEnabled() && selected.charAt(2) == '1') {
			jRadioButtonI5.setSelected(true);
		} else {
			jRadioButtonI5.setSelected(false);
		}
		if (jRadioButtonI6.isEnabled() && selected.charAt(1) == '1') {
			jRadioButtonI6.setSelected(true);
		} else {
			jRadioButtonI6.setSelected(false);
		}
		if (jRadioButtonI7.isEnabled() && selected.charAt(0) == '1') {
			jRadioButtonI7.setSelected(true);
		} else {
			jRadioButtonI7.setSelected(false);
		}
		jTextFieldPWM1.setText("" + PWM1.intValue());
		jTextFieldPWM2.setText("" + PWM2.intValue());
		jButtonOk.requestFocus();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		buttonGroup = new javax.swing.ButtonGroup();
		jPanelOpts = new javax.swing.JPanel();
		jPanelChooseIs = new javax.swing.JPanel();
		jLabelI7 = new javax.swing.JLabel();
		jRadioButtonI7 = new javax.swing.JRadioButton();
		jLabelI6 = new javax.swing.JLabel();
		jRadioButtonI6 = new javax.swing.JRadioButton();
		jLabelI5 = new javax.swing.JLabel();
		jRadioButtonI5 = new javax.swing.JRadioButton();
		jRadioButtonI4 = new javax.swing.JRadioButton();
		jRadioButtonI3 = new javax.swing.JRadioButton();
		jRadioButtonI2 = new javax.swing.JRadioButton();
		jRadioButtonI1 = new javax.swing.JRadioButton();
		jRadioButtonI0 = new javax.swing.JRadioButton();
		jLabelI4 = new javax.swing.JLabel();
		jLabelI3 = new javax.swing.JLabel();
		jLabelI2 = new javax.swing.JLabel();
		jLabelI1 = new javax.swing.JLabel();
		jLabelI0 = new javax.swing.JLabel();
		jLabelPWM1 = new javax.swing.JLabel();
		jTextFieldPWM1 = new javax.swing.JTextField();
		jLabelPWM2 = new javax.swing.JLabel();
		jTextFieldPWM2 = new javax.swing.JTextField();
		jPanelOkCancel = new javax.swing.JPanel();
		jButtonClean = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jButtonOk = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();

		setTitle("Adicionar/editar elemento");
		setResizable(false);
		addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(final java.awt.event.KeyEvent evt) {
				formKeyPressed(evt);
			}
		});

		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(final java.awt.event.MouseEvent evt) {
				formMouseClicked(evt);
			}
		});

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		jPanelOpts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanelOpts.setForeground(new java.awt.Color(51, 0, 102));
		jPanelOpts.setMinimumSize(new java.awt.Dimension(335, 90));
		jPanelOpts.setPreferredSize(new java.awt.Dimension(335, 90));
		jPanelChooseIs.setLayout(new java.awt.GridBagLayout());

		jLabelI7.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI7.setText("I7");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
		jPanelChooseIs.add(jLabelI7, gridBagConstraints);

		jRadioButtonI7.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
		jPanelChooseIs.add(jRadioButtonI7, gridBagConstraints);

		jLabelI6.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI6.setText("I6");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI6, gridBagConstraints);

		jRadioButtonI6.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI6, gridBagConstraints);

		jLabelI5.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI5.setText("I5");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI5, gridBagConstraints);

		jRadioButtonI5.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI5, gridBagConstraints);

		jRadioButtonI4.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI4, gridBagConstraints);

		jRadioButtonI3.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI3, gridBagConstraints);

		jRadioButtonI2.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI2, gridBagConstraints);

		jRadioButtonI1.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 11;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI1, gridBagConstraints);

		jRadioButtonI0.setForeground(new java.awt.Color(51, 0, 102));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 13;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 4, 0, 0);
		jPanelChooseIs.add(jRadioButtonI0, gridBagConstraints);

		jLabelI4.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI4.setText("I4");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI4, gridBagConstraints);

		jLabelI3.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI3.setText("I3");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI3, gridBagConstraints);

		jLabelI2.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI2.setText("I2");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 10;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI2, gridBagConstraints);

		jLabelI1.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI1.setText("I1");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 12;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI1, gridBagConstraints);

		jLabelI0.setForeground(new java.awt.Color(51, 0, 102));
		jLabelI0.setText("I0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 14;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanelChooseIs.add(jLabelI0, gridBagConstraints);

		jPanelOpts.add(jPanelChooseIs);

		jLabelPWM1.setForeground(new java.awt.Color(51, 0, 102));
		jLabelPWM1.setText("PWM1");
		jPanelOpts.add(jLabelPWM1);

		jTextFieldPWM1.setColumns(3);
		jTextFieldPWM1.setForeground(new java.awt.Color(51, 0, 102));
		jPanelOpts.add(jTextFieldPWM1);

		jLabelPWM2.setForeground(new java.awt.Color(51, 0, 102));
		jLabelPWM2.setText("PWM2");
		jPanelOpts.add(jLabelPWM2);

		jTextFieldPWM2.setColumns(3);
		jTextFieldPWM2.setForeground(new java.awt.Color(51, 0, 102));
		jPanelOpts.add(jTextFieldPWM2);

		getContentPane().add(jPanelOpts, java.awt.BorderLayout.CENTER);

		jPanelOkCancel.setLayout(new java.awt.BorderLayout());

		jPanelOkCancel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanelOkCancel.setForeground(new java.awt.Color(51, 0, 102));
		jButtonClean.setForeground(new java.awt.Color(51, 0, 102));
		jButtonClean.setText("Apagar");
		jButtonClean.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonCleanMousePressed(evt);
			}
		});

		jPanelOkCancel.add(jButtonClean, java.awt.BorderLayout.WEST);

		jButtonOk.setForeground(new java.awt.Color(51, 0, 102));
		jButtonOk.setText("Ok");
		jButtonOk.setPreferredSize(new java.awt.Dimension(73, 26));
		jButtonOk.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(final java.awt.event.KeyEvent evt) {
				jButtonOkKeyReleased(evt);
			}
		});

		jButtonOk.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonOkMousePressed(evt);
			}
		});

		jPanel1.add(jButtonOk);

		jButtonCancel.setForeground(new java.awt.Color(51, 0, 102));
		jButtonCancel.setText("Cancel");
		jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonCancelMousePressed(evt);
			}
		});

		jPanel1.add(jButtonCancel);

		jPanelOkCancel.add(jPanel1, java.awt.BorderLayout.CENTER);

		getContentPane().add(jPanelOkCancel, java.awt.BorderLayout.SOUTH);

		pack();
	}// GEN-END:initComponents

	private void jButtonOkKeyReleased(final java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jButtonOkKeyReleased
		if (evt.getKeyCode() == 10) {
			jButtonOkMousePressed(null);
		} else if (evt.getKeyCode() == 27) {
			jButtonCancelMousePressed(null);
		}
	}// GEN-LAST:event_jButtonOkKeyReleased

	private void jButtonCleanMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonCleanMousePressed
		clean = true;
		closeDialog(null);
	}// GEN-LAST:event_jButtonCleanMousePressed

	private void formKeyPressed(final java.awt.event.KeyEvent evt) {// GEN-FIRST:event_formKeyPressed
		if (evt.getKeyCode() == 10) {
			jButtonOkMousePressed(null);
		} else if (evt.getKeyCode() == 27) {
			jButtonCancelMousePressed(null);
		}
	}// GEN-LAST:event_formKeyPressed

	private void formMouseClicked(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_formMouseClicked
		this.requestFocus();
	}// GEN-LAST:event_formMouseClicked

	private void jButtonCancelMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonCancelMousePressed
		closeDialog(null);
	}// GEN-LAST:event_jButtonCancelMousePressed

	private void jButtonOkMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonOkMousePressed
		if (checkTextValues.isOK(jTextFieldPWM1) && checkTextValues.isOK(jTextFieldPWM2)) {
			final StringBuffer temp = new StringBuffer();
			if (jRadioButtonI7.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI6.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI5.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI4.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI3.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI2.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI1.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			if (jRadioButtonI0.isSelected()) {
				temp.append('1');
			} else {
				temp.append('0');
			}
			returnObj[0] = temp.toString();
			returnObj[1] = new Integer(jTextFieldPWM1.getText());
			returnObj[2] = new Integer(jTextFieldPWM2.getText());
			closeDialog(null);
		}
	}// GEN-LAST:event_jButtonOkMousePressed

	/** Closes the dialog */
	private void closeDialog(final java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
		setVisible(false);
		dispose();
	}// GEN-LAST:event_closeDialog

	// DEBUG!!!!!!!!!!!
	/**
	 * @param args the command line arguments
	 * 
	 *            public static void main(String args[]) { //new
	 *            ConfAddNewElement(new javax.swing.JFrame(), true).show(); }
	 */

	public boolean getClean() {
		return clean;
	}

	public Object[] getReturnObj() {
		return returnObj;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel jPanelOpts;
	private javax.swing.JLabel jLabelI7;
	private javax.swing.JPanel jPanelOkCancel;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonClean;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JRadioButton jRadioButtonI4;
	private javax.swing.JLabel jLabelI4;
	private javax.swing.JTextField jTextFieldPWM1;
	private javax.swing.JRadioButton jRadioButtonI2;
	private javax.swing.JLabel jLabelI5;
	private javax.swing.JRadioButton jRadioButtonI1;
	private javax.swing.JRadioButton jRadioButtonI7;
	private javax.swing.JLabel jLabelI6;
	private javax.swing.JLabel jLabelI3;
	private javax.swing.JRadioButton jRadioButtonI0;
	private javax.swing.JLabel jLabelI2;
	private javax.swing.JButton jButtonOk;
	private javax.swing.JLabel jLabelI0;
	private javax.swing.JTextField jTextFieldPWM2;
	private javax.swing.JLabel jLabelPWM1;
	private javax.swing.JRadioButton jRadioButtonI3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel jLabelPWM2;
	private javax.swing.JRadioButton jRadioButtonI5;
	private javax.swing.JRadioButton jRadioButtonI6;
	private javax.swing.JPanel jPanelChooseIs;
	private javax.swing.JLabel jLabelI1;
	// End of variables declaration//GEN-END:variables
	// My variables
	private final pt.utl.ist.elab.client.webrobot.customizer.Utils.CheckTextValues checkTextValues = new pt.utl.ist.elab.client.webrobot.customizer.Utils.CheckTextValues(
			this);
	private boolean clean = false;
}
