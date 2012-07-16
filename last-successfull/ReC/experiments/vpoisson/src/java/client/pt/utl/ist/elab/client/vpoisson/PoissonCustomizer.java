/*
 * MMCustomizer.java
 *
 * Created on October 16, 2004, 11:32 AM
 */

package pt.utl.ist.elab.client.vpoisson;

/**
 *
 * @author  andre
 *
 *  1- Desenhar o GUI! Não esquecer de ir logo internacionalizando...
 *
 *
 */

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.opensourcephysics.numerics.ParsedMultiVarFunction;
import org.opensourcephysics.numerics.ParserException;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class PoissonCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -719952193772195688L;

	/** Creates new form MMCustomizer */
	public PoissonCustomizer() {
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

		jPanel10 = new javax.swing.JPanel();
		jPanel11 = new javax.swing.JPanel();
		jPanel17 = new javax.swing.JPanel();
		jLabel15 = new javax.swing.JLabel();
		jPanel18 = new javax.swing.JPanel();
		jLabel18 = new javax.swing.JLabel();
		jPanel19 = new javax.swing.JPanel();
		jLabel22 = new javax.swing.JLabel();
		jPanel20 = new javax.swing.JPanel();
		jLabel26 = new javax.swing.JLabel();
		jPanel21 = new javax.swing.JPanel();
		jLabel28 = new javax.swing.JLabel();
		jPanel22 = new javax.swing.JPanel();
		jLabel30 = new javax.swing.JLabel();
		jPanel23 = new javax.swing.JPanel();
		jLabel32 = new javax.swing.JLabel();
		jPanel24 = new javax.swing.JPanel();
		jPanel25 = new javax.swing.JPanel();
		jTextFieldFace1 = new javax.swing.JTextField();
		jPanel26 = new javax.swing.JPanel();
		jTextFieldFace2 = new javax.swing.JTextField();
		jPanel27 = new javax.swing.JPanel();
		jTextFieldFace3 = new javax.swing.JTextField();
		jPanel28 = new javax.swing.JPanel();
		jTextFieldFace4 = new javax.swing.JTextField();
		jPanel29 = new javax.swing.JPanel();
		jTextFieldFace5 = new javax.swing.JTextField();
		jPanel30 = new javax.swing.JPanel();
		jTextFieldFace6 = new javax.swing.JTextField();
		jPanel31 = new javax.swing.JPanel();
		jTextFieldRho = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jPanel6 = new javax.swing.JPanel();
		jLabel8 = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		jLabel10 = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jLabel12 = new javax.swing.JLabel();
		jPanel9 = new javax.swing.JPanel();
		jLabel14 = new javax.swing.JLabel();
		jPanel32 = new javax.swing.JPanel();
		jPanel33 = new javax.swing.JPanel();
		jLabel20 = new javax.swing.JLabel();
		jPanel34 = new javax.swing.JPanel();
		jLabel24 = new javax.swing.JLabel();
		jPanel35 = new javax.swing.JPanel();
		jLabel27 = new javax.swing.JLabel();
		jPanel36 = new javax.swing.JPanel();
		jPanel37 = new javax.swing.JPanel();
		jPanel38 = new javax.swing.JPanel();
		jPanel39 = new javax.swing.JPanel();
		jTextFieldNx = new javax.swing.JTextField();
		jPanel40 = new javax.swing.JPanel();
		jTextFieldNy = new javax.swing.JTextField();
		jPanel41 = new javax.swing.JPanel();
		jTextFieldNz = new javax.swing.JTextField();
		jPanel42 = new javax.swing.JPanel();
		jPanel43 = new javax.swing.JPanel();
		jPanel13 = new javax.swing.JPanel();
		jPanel14 = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		jPanel15 = new javax.swing.JPanel();
		btnDefaults = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		setPreferredSize(new java.awt.Dimension(550, 350));
		jPanel10.setLayout(new java.awt.GridBagLayout());

		jPanel10.setPreferredSize(new java.awt.Dimension(800, 223));
		jPanel11.setLayout(new java.awt.GridBagLayout());

		jPanel17.setLayout(new java.awt.GridBagLayout());

		jLabel15.setText("Face 1 V (x,y) =");
		jPanel17.add(jLabel15, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel17, gridBagConstraints);

		jPanel18.setLayout(new java.awt.GridBagLayout());

		jLabel18.setText("Face 2 V (x,y) =");
		jPanel18.add(jLabel18, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel18, gridBagConstraints);

		jPanel19.setLayout(new java.awt.GridBagLayout());

		jLabel22.setText("Face 3 V (x,y) =");
		jPanel19.add(jLabel22, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel19, gridBagConstraints);

		jPanel20.setLayout(new java.awt.GridBagLayout());

		jLabel26.setText("Face 4 V (x,y) =");
		jPanel20.add(jLabel26, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel20, gridBagConstraints);

		jPanel21.setLayout(new java.awt.GridBagLayout());

		jLabel28.setText("Face 5 V (x,y) =");
		jPanel21.add(jLabel28, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel21, gridBagConstraints);

		jPanel22.setLayout(new java.awt.GridBagLayout());

		jLabel30.setText("Face 6 V (x,y) =");
		jPanel22.add(jLabel30, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel22, gridBagConstraints);

		jPanel23.setLayout(new java.awt.GridBagLayout());

		jLabel32.setText("rho (x,y,z) =");
		jPanel23.add(jLabel32, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel11.add(jPanel23, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 2);
		jPanel10.add(jPanel11, gridBagConstraints);

		jPanel24.setLayout(new java.awt.GridBagLayout());

		jPanel24.setMinimumSize(new java.awt.Dimension(280, 203));
		jPanel25.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace1.setColumns(24);
		jTextFieldFace1.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace1.setText("1");
		jTextFieldFace1.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace1FocusLost(evt);
			}
		});

		jPanel25.add(jTextFieldFace1, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel25, gridBagConstraints);

		jPanel26.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace2.setColumns(24);
		jTextFieldFace2.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace2.setText("0");
		jTextFieldFace2.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace2FocusLost(evt);
			}
		});

		jPanel26.add(jTextFieldFace2, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel26, gridBagConstraints);

		jPanel27.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace3.setColumns(24);
		jTextFieldFace3.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace3.setText("2");
		jTextFieldFace3.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace3FocusLost(evt);
			}
		});

		jPanel27.add(jTextFieldFace3, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel27, gridBagConstraints);

		jPanel28.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace4.setColumns(24);
		jTextFieldFace4.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace4.setText("0");
		jTextFieldFace4.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace4FocusLost(evt);
			}
		});

		jPanel28.add(jTextFieldFace4, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel28, gridBagConstraints);

		jPanel29.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace5.setColumns(24);
		jTextFieldFace5.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace5.setText("0");
		jTextFieldFace5.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace5FocusLost(evt);
			}
		});

		jPanel29.add(jTextFieldFace5, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel29, gridBagConstraints);

		jPanel30.setLayout(new java.awt.GridBagLayout());

		jTextFieldFace6.setColumns(24);
		jTextFieldFace6.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldFace6.setText("0");
		jTextFieldFace6.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldFace6FocusLost(evt);
			}
		});

		jPanel30.add(jTextFieldFace6, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel30, gridBagConstraints);

		jPanel31.setLayout(new java.awt.GridBagLayout());

		jTextFieldRho.setColumns(24);
		jTextFieldRho.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldRho.setText("0");
		jTextFieldRho.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldRhoFocusLost(evt);
			}
		});

		jPanel31.add(jTextFieldRho, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel24.add(jPanel31, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 2);
		jPanel10.add(jPanel24, gridBagConstraints);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel3.setLayout(new java.awt.GridBagLayout());

		jLabel2.setText("[V]");
		jPanel3.add(jLabel2, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel3, gridBagConstraints);

		jPanel4.setLayout(new java.awt.GridBagLayout());

		jLabel4.setText("[V]");
		jPanel4.add(jLabel4, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel4, gridBagConstraints);

		jPanel5.setLayout(new java.awt.GridBagLayout());

		jLabel6.setText("[V]");
		jPanel5.add(jLabel6, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel5, gridBagConstraints);

		jPanel6.setLayout(new java.awt.GridBagLayout());

		jLabel8.setText("[V]");
		jPanel6.add(jLabel8, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel6, gridBagConstraints);

		jPanel7.setLayout(new java.awt.GridBagLayout());

		jLabel10.setText("[V]");
		jPanel7.add(jLabel10, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel7, gridBagConstraints);

		jPanel8.setLayout(new java.awt.GridBagLayout());

		jLabel12.setText("[V]");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel8.add(jLabel12, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel8, gridBagConstraints);

		jPanel9.setLayout(new java.awt.GridBagLayout());

		jLabel14.setText("[C.m-3]");
		jPanel9.add(jLabel14, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel2.add(jPanel9, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(10, 2, 10, 10);
		jPanel10.add(jPanel2, gridBagConstraints);

		jPanel32.setLayout(new java.awt.GridBagLayout());

		jPanel33.setLayout(new java.awt.GridBagLayout());

		jLabel20.setText("Nx");
		jPanel33.add(jLabel20, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel32.add(jPanel33, gridBagConstraints);

		jPanel34.setLayout(new java.awt.GridBagLayout());

		jLabel24.setText("Ny");
		jPanel34.add(jLabel24, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel32.add(jPanel34, gridBagConstraints);

		jPanel35.setLayout(new java.awt.GridBagLayout());

		jLabel27.setText("Nz");
		jPanel35.add(jLabel27, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel32.add(jPanel35, gridBagConstraints);

		jPanel36.setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel32.add(jPanel36, gridBagConstraints);

		jPanel37.setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
		jPanel32.add(jPanel37, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
		jPanel10.add(jPanel32, gridBagConstraints);

		jPanel38.setLayout(new java.awt.GridBagLayout());

		jPanel38.setMinimumSize(new java.awt.Dimension(60, 145));
		jPanel39.setLayout(new java.awt.GridBagLayout());

		jTextFieldNx.setColumns(4);
		jTextFieldNx.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldNx.setText("100");
		jTextFieldNx.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldNxFocusLost(evt);
			}
		});

		jPanel39.add(jTextFieldNx, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel38.add(jPanel39, gridBagConstraints);

		jPanel40.setLayout(new java.awt.GridBagLayout());

		jTextFieldNy.setColumns(4);
		jTextFieldNy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldNy.setText("100");
		jTextFieldNy.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldNyFocusLost(evt);
			}
		});

		jPanel40.add(jTextFieldNy, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel38.add(jPanel40, gridBagConstraints);

		jPanel41.setLayout(new java.awt.GridBagLayout());

		jTextFieldNz.setColumns(4);
		jTextFieldNz.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldNz.setText("100");
		jTextFieldNz.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldNzFocusLost(evt);
			}
		});

		jPanel41.add(jTextFieldNz, new java.awt.GridBagConstraints());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel38.add(jPanel41, gridBagConstraints);

		jPanel42.setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel38.add(jPanel42, gridBagConstraints);

		jPanel43.setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel38.add(jPanel43, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
		jPanel10.add(jPanel38, gridBagConstraints);

		add(jPanel10, java.awt.BorderLayout.CENTER);

		jPanel13.setLayout(new java.awt.GridBagLayout());

		jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		jPanel13.setMinimumSize(new java.awt.Dimension(350, 42));
		jPanel13.setPreferredSize(new java.awt.Dimension(350, 42));
		jPanel14.setMinimumSize(new java.awt.Dimension(143, 25));
		btnOK.setText(ReCResourceBundle.findString("poisson$rec.exp.customizer.title.ok"));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		jPanel14.add(btnOK);

		btnCancel.setText(ReCResourceBundle.findString("poisson$rec.exp.customizer.title.cancel"));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		jPanel14.add(btnCancel);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		jPanel13.add(jPanel14, gridBagConstraints);

		jPanel15.setMinimumSize(new java.awt.Dimension(136, 25));
		btnDefaults.setText(ReCResourceBundle.findString("poisson$rec.exp.customizer.title.dfc"));
		btnDefaults.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnDefaultsActionPerformed(evt);
			}
		});

		jPanel15.add(btnDefaults);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel13.add(jPanel15, gridBagConstraints);

		add(jPanel13, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	private void btnDefaultsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnDefaultsActionPerformed
	{// GEN-HEADEREND:event_btnDefaultsActionPerformed
		jTextFieldFace1.setText("1");
		jTextFieldFace2.setText("1");
		jTextFieldFace3.setText("1");
		jTextFieldFace4.setText("1");
		jTextFieldFace5.setText("1");
		jTextFieldFace6.setText("1");
		jTextFieldRho.setText("0");
		jTextFieldNx.setText("60");
		jTextFieldNy.setText("60");
		jTextFieldNz.setText("60");
	}// GEN-LAST:event_btnDefaultsActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnCancelActionPerformed
	{// GEN-HEADEREND:event_btnCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnOKActionPerformed
	{// GEN-HEADEREND:event_btnOKActionPerformed
		// Verificar isto...
		acqConfig.setTotalSamples(1);

		acqConfig.getSelectedHardwareParameter("Nx").setParameterValue(jTextFieldNx.getText());
		acqConfig.getSelectedHardwareParameter("Ny").setParameterValue(jTextFieldNy.getText());
		acqConfig.getSelectedHardwareParameter("Nz").setParameterValue(jTextFieldNz.getText());
		acqConfig.getSelectedHardwareParameter("fnFace1").setParameterValue(jTextFieldFace1.getText());
		acqConfig.getSelectedHardwareParameter("fnFace2").setParameterValue(jTextFieldFace2.getText());
		acqConfig.getSelectedHardwareParameter("fnFace3").setParameterValue(jTextFieldFace3.getText());
		acqConfig.getSelectedHardwareParameter("fnFace4").setParameterValue(jTextFieldFace4.getText());
		acqConfig.getSelectedHardwareParameter("fnFace5").setParameterValue(jTextFieldFace5.getText());
		acqConfig.getSelectedHardwareParameter("fnFace6").setParameterValue(jTextFieldFace6.getText());
		acqConfig.getSelectedHardwareParameter("fnRho").setParameterValue(jTextFieldRho.getText());

		fireICustomizerListenerDone();
	}// GEN-LAST:event_btnOKActionPerformed

	private void jTextFieldRhoFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldRhoFocusLost
	{// GEN-HEADEREND:event_jTextFieldRhoFocusLost
		final String s[] = new String[] { "x", "y", "z" };
		verifyTextParser(jTextFieldRho, s);
	}// GEN-LAST:event_jTextFieldRhoFocusLost

	private void jTextFieldFace6FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace6FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace6FocusLost
		final String s[] = new String[2];
		s[0] = "y";
		s[1] = "z";
		verifyTextParser(jTextFieldFace6, s);
	}// GEN-LAST:event_jTextFieldFace6FocusLost

	private void jTextFieldFace5FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace5FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace5FocusLost
		final String s[] = new String[2];
		s[0] = "y";
		s[1] = "z";
		verifyTextParser(jTextFieldFace5, s);
	}// GEN-LAST:event_jTextFieldFace5FocusLost

	private void jTextFieldFace4FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace4FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace4FocusLost
		final String s[] = new String[2];
		s[0] = "x";
		s[1] = "z";
		verifyTextParser(jTextFieldFace4, s);
	}// GEN-LAST:event_jTextFieldFace4FocusLost

	private void jTextFieldFace3FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace3FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace3FocusLost
		final String s[] = new String[2];
		s[0] = "x";
		s[1] = "y";
		verifyTextParser(jTextFieldFace3, s);
	}// GEN-LAST:event_jTextFieldFace3FocusLost

	private void jTextFieldFace2FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace2FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace2FocusLost
		final String s[] = new String[2];
		s[0] = "x";
		s[1] = "z";
		verifyTextParser(jTextFieldFace2, s);
	}// GEN-LAST:event_jTextFieldFace2FocusLost

	private void jTextFieldFace1FocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldFace1FocusLost
	{// GEN-HEADEREND:event_jTextFieldFace1FocusLost
		final String s[] = new String[2];
		s[0] = "x";
		s[1] = "y";
		verifyTextParser(jTextFieldFace1, s);
	}// GEN-LAST:event_jTextFieldFace1FocusLost

	private void jTextFieldNzFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldNzFocusLost
	{// GEN-HEADEREND:event_jTextFieldNzFocusLost
		verifyText(jTextFieldNz, "50");
		verifyBounds(jTextFieldNz, Integer.parseInt(jTextFieldNz.getText()), 0, 80, 60);
	}// GEN-LAST:event_jTextFieldNzFocusLost

	private void jTextFieldNyFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldNyFocusLost
	{// GEN-HEADEREND:event_jTextFieldNyFocusLost
		verifyText(jTextFieldNy, "50");
		verifyBounds(jTextFieldNy, Integer.parseInt(jTextFieldNy.getText()), 0, 80, 60);
	}// GEN-LAST:event_jTextFieldNyFocusLost

	private void jTextFieldNxFocusLost(final java.awt.event.FocusEvent evt)// GEN-FIRST:event_jTextFieldNxFocusLost
	{// GEN-HEADEREND:event_jTextFieldNxFocusLost
		verifyText(jTextFieldNx, "50");
		verifyBounds(jTextFieldNx, Integer.parseInt(jTextFieldNx.getText()), 0, 80, 60);
	}// GEN-LAST:event_jTextFieldNxFocusLost

	public static void main(final String args[]) {
		ReCResourceBundle.loadResourceBundle("poisson",
				"recresource:///pt/utl/ist/elab/client/vpoisson/resources/messages");
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				final javax.swing.JFrame dummy = new javax.swing.JFrame();
				dummy.getContentPane().add(new PoissonCustomizer());
				dummy.pack();
				dummy.setVisible(true);
			}
		});
		
	}

	private void verifyText(final JTextField field, final String dftValue) {
		try {
			Integer.parseInt(field.getText());
		} catch (final NumberFormatException nfe) {
			field.setText(dftValue);
		}
	}

	private void verifyTextParser(final JTextField field, final String[] s) {
		final String error = ReCResourceBundle.findStringOrDefault("poisson$rec.exp.customizer.title.errorfunc",
				"Wrong function!");
		final String title = ReCResourceBundle.findStringOrDefault("poisson$rec.exp.customizer.title.error", "Error");
		try {
			new ParsedMultiVarFunction(field.getText(), s);
		} catch (final ParserException pe) {
			JOptionPane.showMessageDialog(null, error, title, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void verifyBounds(final JTextField field, final int value, final int min, final int max, final int dft) {
		if (value < min || value > max) {
			field.setText("" + dft);
		}
	}

	// Metodo que verifica a validade do que foi introduzido na text field
	/*
	 * private void adjustSlider(javax.swing.JSlider slider,
	 * javax.swing.JTextField field) { int num = 0; try { num =
	 * Integer.parseInt(field.getText().trim()); } catch(NumberFormatException
	 * nfe) { field.setText("" + slider.getValue()); } if(num >
	 * slider.getMaximum() || num < slider.getMinimum()) field.setText("" +
	 * slider.getValue()); else slider.setValue(num); }
	 * 
	 * private void adjustSlider2(javax.swing.JSlider slider,
	 * javax.swing.JTextField field) { int num = 0; try { num =
	 * (int)(Float.parseFloat(field.getText().trim()) * 10); }
	 * catch(NumberFormatException nfe) { field.setText("" + slider.getValue() /
	 * 10F); return; } if(num > slider.getMaximum() || num <
	 * slider.getMinimum()) field.setText("" + slider.getValue() / 10F); else
	 * slider.setValue(num); }
	 */

	// ****************************REC********************************************/

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
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
	 */
	public synchronized void removeICustomizerListener(final ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
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
	 */
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

	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
	}

	// ESTE É PARA ALTERAR
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		// Aqui são fornecidos parametros do ultimo utilizador que fez a exp, e'
		// bom manter!
		this.acqConfig = acqConfig;
		if (acqConfig != null) {
			jTextFieldNx.setText(acqConfig.getSelectedHardwareParameterValue("Nx"));
			jTextFieldNy.setText(acqConfig.getSelectedHardwareParameterValue("Ny"));
			jTextFieldNz.setText(acqConfig.getSelectedHardwareParameterValue("Nz"));
			jTextFieldFace1.setText(acqConfig.getSelectedHardwareParameterValue("fnFace1"));
			jTextFieldFace2.setText(acqConfig.getSelectedHardwareParameterValue("fnFace2"));
			jTextFieldFace3.setText(acqConfig.getSelectedHardwareParameterValue("fnFace3"));
			jTextFieldFace4.setText(acqConfig.getSelectedHardwareParameterValue("fnFace4"));
			jTextFieldFace5.setText(acqConfig.getSelectedHardwareParameterValue("fnFace5"));
			jTextFieldFace6.setText(acqConfig.getSelectedHardwareParameterValue("fnFace6"));
			jTextFieldRho.setText(acqConfig.getSelectedHardwareParameterValue("fnRho"));
		}
	}

	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	public javax.swing.JComponent getCustomizerComponent() {
		return this;
	}

	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/vpoisson/resources/poisson_iconified.png"));
	}

	// ESTE É PARA ALTERAR
	public String getCustomizerTitle() {
		return "Poisson Experiment Configuration Utility";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnDefaults;
	private javax.swing.JButton btnOK;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel30;
	private javax.swing.JLabel jLabel32;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel17;
	private javax.swing.JPanel jPanel18;
	private javax.swing.JPanel jPanel19;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel20;
	private javax.swing.JPanel jPanel21;
	private javax.swing.JPanel jPanel22;
	private javax.swing.JPanel jPanel23;
	private javax.swing.JPanel jPanel24;
	private javax.swing.JPanel jPanel25;
	private javax.swing.JPanel jPanel26;
	private javax.swing.JPanel jPanel27;
	private javax.swing.JPanel jPanel28;
	private javax.swing.JPanel jPanel29;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel30;
	private javax.swing.JPanel jPanel31;
	private javax.swing.JPanel jPanel32;
	private javax.swing.JPanel jPanel33;
	private javax.swing.JPanel jPanel34;
	private javax.swing.JPanel jPanel35;
	private javax.swing.JPanel jPanel36;
	private javax.swing.JPanel jPanel37;
	private javax.swing.JPanel jPanel38;
	private javax.swing.JPanel jPanel39;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel40;
	private javax.swing.JPanel jPanel41;
	private javax.swing.JPanel jPanel42;
	private javax.swing.JPanel jPanel43;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JTextField jTextFieldFace1;
	private javax.swing.JTextField jTextFieldFace2;
	private javax.swing.JTextField jTextFieldFace3;
	private javax.swing.JTextField jTextFieldFace4;
	private javax.swing.JTextField jTextFieldFace5;
	private javax.swing.JTextField jTextFieldFace6;
	private javax.swing.JTextField jTextFieldNx;
	private javax.swing.JTextField jTextFieldNy;
	private javax.swing.JTextField jTextFieldNz;
	private javax.swing.JTextField jTextFieldRho;
	// End of variables declaration//GEN-END:variables

}
