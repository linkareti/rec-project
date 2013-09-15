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
public class ConfPasteRightClick extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059892826084796698L;

	/**
	 * Creates new form ConfCompBin
	 * 
	 * @param parent
	 * @param modal
	 */
	public ConfPasteRightClick(final java.awt.Frame parent, final boolean modal) {
		super(parent, modal);
		initComponents();
		setCancel(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		buttonGroup = new javax.swing.ButtonGroup();
		jPanelOpts = new javax.swing.JPanel();
		jButtonPaste = new javax.swing.JButton();
		jPanelOkCancel = new javax.swing.JPanel();
		jButtonCancel = new javax.swing.JButton();

		setTitle("Colar");
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
		jPanelOpts.setMinimumSize(new java.awt.Dimension(100, 40));
		jPanelOpts.setPreferredSize(new java.awt.Dimension(100, 40));
		jButtonPaste.setForeground(new java.awt.Color(51, 0, 102));
		jButtonPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/customizer/Icons/Paste16.gif")));
		jButtonPaste.setText("Colar");
		jButtonPaste.setToolTipText("Colar nesta posi\u00e7\u00e3o");
		jButtonPaste.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				jButtonPasteMousePressed(evt);
			}
		});

		jPanelOpts.add(jButtonPaste);

		getContentPane().add(jPanelOpts, java.awt.BorderLayout.CENTER);

		jPanelOkCancel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanelOkCancel.setForeground(new java.awt.Color(51, 0, 102));
		jPanelOkCancel.setMinimumSize(new java.awt.Dimension(100, 38));
		jPanelOkCancel.setPreferredSize(new java.awt.Dimension(100, 38));
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

	private void jButtonPasteMousePressed(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonPasteMousePressed
		setPaste(true);
		setCancel(false);
		closeDialog(null);
	}// GEN-LAST:event_jButtonPasteMousePressed

	private void formKeyPressed(final java.awt.event.KeyEvent evt) {// GEN-FIRST:event_formKeyPressed
		if (evt.getKeyCode() == 10) {
			jButtonPasteMousePressed(null);
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
	 *            public static void main(String args[]) { new ConfAtribui(new
	 *            javax.swing.JFrame(), true).show(); }
	 */

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

	/**
	 * Getter for property paste.
	 * 
	 * @return Value of property paste.
	 */
	public boolean isPaste() {
		return paste;
	}

	/**
	 * Setter for property paste.
	 * 
	 * @param paste New value of property paste.
	 */
	public void setPaste(final boolean paste) {
		this.paste = paste;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel jPanelOpts;
	private javax.swing.JPanel jPanelOkCancel;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JButton jButtonPaste;
	// End of variables declaration//GEN-END:variables
	// My variables
	/** Holds value of property cancel. */
	private boolean cancel = false;

	/** Holds value of property paste. */
	private boolean paste = false;

}
