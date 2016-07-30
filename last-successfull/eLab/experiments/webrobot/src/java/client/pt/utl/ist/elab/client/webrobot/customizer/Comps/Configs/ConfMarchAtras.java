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
public class ConfMarchAtras extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1543816988717888575L;

	/**
	 * Creates new form ConfCompBin
	 * 
	 * @param parent
	 * @param modal
	 * @param model
	 */
	public ConfMarchAtras(final java.awt.Frame parent, final boolean modal,
			final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras model) {
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
		jPanelTop = new javax.swing.JPanel();
		jTextFieldSecs = new javax.swing.JTextField();
		jScrollBarSecs = new javax.swing.JScrollBar();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jPanelBottom = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jTextFieldTotalSecs = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jPanelOkCancel = new javax.swing.JPanel();
		jButtonOk = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();

		setTitle("Anda durante (per\u00eddos x 0.25s)");
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

		jPanelOpts.setLayout(new java.awt.BorderLayout());

		jPanelOpts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanelOpts.setForeground(new java.awt.Color(51, 0, 102));
		jPanelOpts.setMinimumSize(new java.awt.Dimension(270, 80));
		jPanelOpts.setPreferredSize(new java.awt.Dimension(270, 80));
		jPanelTop.setLayout(new java.awt.GridBagLayout());

		jTextFieldSecs.setColumns(3);
		jTextFieldSecs.setForeground(new java.awt.Color(51, 0, 102));
		jTextFieldSecs.setText("" + model.getValor());
		jTextFieldSecs.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(final java.awt.event.KeyEvent evt) {
				jTextFieldSecsKeyReleased(evt);
			}
		});

		jPanelTop.add(jTextFieldSecs, new java.awt.GridBagConstraints());

		jScrollBarSecs.setBlockIncrement(1);
		jScrollBarSecs.setForeground(new java.awt.Color(51, 0, 102));
		jScrollBarSecs.setMaximum(255);
		jScrollBarSecs.setMinimum(1);
		jScrollBarSecs.setUnitIncrement(-1);
		jScrollBarSecs.setValue(model.getValor());
		jScrollBarSecs.setVisibleAmount(0);
		jScrollBarSecs.setPreferredSize(new java.awt.Dimension(17, 25));
		jScrollBarSecs.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(final java.awt.event.AdjustmentEvent evt) {
				jScrollBarSecsAdjustmentValueChanged(evt);
			}
		});

		jPanelTop.add(jScrollBarSecs, new java.awt.GridBagConstraints());

		jLabel2.setForeground(new java.awt.Color(51, 0, 102));
		jLabel2.setText("x Per\u00edodos de 0,25s");
		jPanelTop.add(jLabel2, new java.awt.GridBagConstraints());

		jPanelOpts.add(jPanelTop, java.awt.BorderLayout.CENTER);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/marchatras.gif")));
		jPanelOpts.add(jLabel1, java.awt.BorderLayout.EAST);

		jLabel3.setForeground(new java.awt.Color(51, 0, 102));
		jLabel3.setText("Tempo");
		jPanelBottom.add(jLabel3);

		jTextFieldTotalSecs.setColumns(4);
		jTextFieldTotalSecs.setEditable(false);
		jTextFieldTotalSecs.setText("" + df.format((double) model.getValor() / 4));
		jPanelBottom.add(jTextFieldTotalSecs);

		jLabel4.setForeground(new java.awt.Color(51, 0, 102));
		jLabel4.setText("s (0 a 64 segundos)");
		jPanelBottom.add(jLabel4);

		jPanelOpts.add(jPanelBottom, java.awt.BorderLayout.SOUTH);

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

	private void jTextFieldSecsKeyReleased(final java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldSecsKeyReleased
		try {
			if (new Integer(jTextFieldSecs.getText()).intValue() < 1) {
				jTextFieldSecs.setText("" + 1);
			} else if (new Integer(jTextFieldSecs.getText()).intValue() > 255) {
				jTextFieldSecs.setText("" + 255);
			}
			jScrollBarSecs.setValue(new Integer(jTextFieldSecs.getText()).intValue());
			jTextFieldTotalSecs.setText("" + df.format((double) jScrollBarSecs.getValue() / 4));
		} catch (final NumberFormatException nfe) {
		}
	}// GEN-LAST:event_jTextFieldSecsKeyReleased

	private void jScrollBarSecsAdjustmentValueChanged(final java.awt.event.AdjustmentEvent evt) {// GEN-FIRST:event_jScrollBarSecsAdjustmentValueChanged
		jTextFieldSecs.setText("" + jScrollBarSecs.getValue());
		jTextFieldTotalSecs.setText("" + df.format((double) jScrollBarSecs.getValue() / 4));
	}// GEN-LAST:event_jScrollBarSecsAdjustmentValueChanged

	private void formMouseClicked(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_formMouseClicked
		this.requestFocus();
	}// GEN-LAST:event_formMouseClicked

	private void jButtonCancelMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonCancelMousePressed
		setCancel(true);
		closeDialog(null);
	}// GEN-LAST:event_jButtonCancelMousePressed

	private void jButtonOkMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonOkMousePressed
		if (checkTextValues.isOK(jTextFieldSecs)) {
			model.setValor(new Integer(jTextFieldSecs.getText()).intValue());
			closeDialog(null);
		}
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
	 *            public static void main(String args[]) { new
	 *            ConfMarchAtras(new javax.swing.JFrame(), true).show(); }
	 */

	public void readModel() {
		jTextFieldSecs.setText("" + model.getValor());
		jTextFieldTotalSecs.setText("" + df.format((double) model.getValor() / 4));
	}

	public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras getModel() {
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
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanelOpts;
	private javax.swing.JPanel jPanelOkCancel;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JPanel jPanelBottom;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JTextField jTextFieldTotalSecs;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JButton jButtonOk;
	private javax.swing.JScrollBar jScrollBarSecs;
	private javax.swing.JTextField jTextFieldSecs;
	private javax.swing.JPanel jPanelTop;
	// End of variables declaration//GEN-END:variables
	// My variables
	private final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras model;
	private final pt.utl.ist.elab.client.webrobot.customizer.Utils.CheckTextValues checkTextValues = new pt.utl.ist.elab.client.webrobot.customizer.Utils.CheckTextValues(
			this);
	private final java.text.DecimalFormat df = new java.text.DecimalFormat("##0.00");

	/** Holds value of property cancel. */
	private boolean cancel = false;
}
