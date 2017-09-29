/*
 * ConfCompBin.java
 *
 * Created on 28 de Janeiro de 2003, 19:06
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ConfSetReset extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1907252069327434114L;

	/**
	 * Creates new form ConfCompBin
	 * 
	 * @param parent
	 * @param modal
	 * @param model
	 */
	public ConfSetReset(final java.awt.Frame parent, final boolean modal,
			final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelSetReset model) {
		super(parent, modal);
		this.model = model;
		initComponents();
		readModel();
		setCancel(false);
		jButtonOk.requestFocus();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		buttonGroup = new javax.swing.ButtonGroup();
		jPanelOpts = new javax.swing.JPanel();
		jComboBoxD1 = new javax.swing.JComboBox();
		jLabel2 = new javax.swing.JLabel();
		jRadioButton0 = new javax.swing.JRadioButton();
		jRadioButton1 = new javax.swing.JRadioButton();
		jLabel1 = new javax.swing.JLabel();
		jPanelOkCancel = new javax.swing.JPanel();
		jButtonOk = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();

		setTitle("Set/Reset Sa\u00edda digital");
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
		jPanelOpts.setMinimumSize(new java.awt.Dimension(220, 57));
		jPanelOpts.setPreferredSize(new java.awt.Dimension(220, 57));
		jComboBoxD1.setForeground(new java.awt.Color(51, 0, 102));
		jComboBoxD1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "b0", "b1", "b2", "b3", "b4", "b5",
				"b6", "b7", "c0", "c3" }));
		jPanelOpts.add(jComboBoxD1);

		jLabel2.setForeground(new java.awt.Color(51, 0, 102));
		jLabel2.setText("=");
		jPanelOpts.add(jLabel2);

		jRadioButton0.setForeground(new java.awt.Color(51, 0, 102));
		jRadioButton0.setSelected(true);
		jRadioButton0.setText("0");
		buttonGroup.add(jRadioButton0);
		jPanelOpts.add(jRadioButton0);

		jRadioButton1.setForeground(new java.awt.Color(51, 0, 102));
		jRadioButton1.setText("1");
		buttonGroup.add(jRadioButton1);
		jPanelOpts.add(jRadioButton1);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/setreset.gif")));
		jPanelOpts.add(jLabel1);

		getContentPane().add(jPanelOpts, java.awt.BorderLayout.CENTER);

		jPanelOkCancel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanelOkCancel.setForeground(new java.awt.Color(51, 0, 102));
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

		jPanelOkCancel.add(jButtonOk);

		jButtonCancel.setForeground(new java.awt.Color(51, 0, 102));
		jButtonCancel.setText("Cancel");
		jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonCancelMousePressed(evt);
			}
		});

		jPanelOkCancel.add(jButtonCancel);

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
		setCancel(true);
		closeDialog(null);
	}// GEN-LAST:event_jButtonCancelMousePressed

	private void jButtonOkMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonOkMousePressed
		model.setD1(jComboBoxD1.getSelectedItem().toString());
		if (jRadioButton0.isSelected()) {
			model.setValor(0);
		} else {
			model.setValor(1);
		}
		closeDialog(null);
	}// GEN-LAST:event_jButtonOkMousePressed

	/** Closes the dialog */
	private void closeDialog(final java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
		if (evt != null) {
			setCancel(true);
		}
		setVisible(false);
		dispose();
	}// GEN-LAST:event_closeDialog

	// DEBUG!!!!!!!!!!!
	/**
	 * @param args the command line arguments
	 * 
	 *            public static void main(String args[]) { new ConfSetReset(new
	 *            javax.swing.JFrame(), true).show(); }
	 */

	public void readModel() {
		jComboBoxD1.setSelectedItem(model.getD1());
		if (model.getValor() == 0) {
			jRadioButton0.setSelected(true);
			jRadioButton1.setSelected(false);
		} else {
			jRadioButton0.setSelected(false);
			jRadioButton1.setSelected(true);
		}
	}

	public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelSetReset getModel() {
		return model;
	}

	/**
	 * Getter for property cancel.
	 * 
	 * @return Value of property cancel.
	 */
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * Setter for property cancel.
	 * 
	 * @param cancel New value of property cancel.
	 */
	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JComboBox jComboBoxD1;
	private javax.swing.JPanel jPanelOpts;
	private javax.swing.JPanel jPanelOkCancel;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JButton jButtonOk;
	private javax.swing.JRadioButton jRadioButton0;
	// End of variables declaration//GEN-END:variables
	// My variables
	private final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelSetReset model;

	/** Holds value of property cancel. */
	private boolean cancel = false;
}
