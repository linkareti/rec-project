/*
 * analasysPanel.java
 *
 * Created on 27 de Setembro de 2004, 4:02
 */

package pt.utl.ist.elab.client.thomson.displays;

/**
 *
 * @author  ivo
 */

import java.awt.Image;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.utils.Defaults;

public class AnalasysPanel extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {
	public static final String THOMSON_N_ESP = "Thomson.N.Esp";
	private static final double N_ESP = Double.parseDouble(Defaults.defaultIfEmpty(System.getProperty(THOMSON_N_ESP),
			"640"));
	public static final String THOMSON_D_ESP = "Thomson.D.Esp";
	private static final double D_ESP = Double.parseDouble(Defaults.defaultIfEmpty(System.getProperty(THOMSON_D_ESP),
			"0.07"));

	private java.text.DecimalFormat df = new java.text.DecimalFormat("###0.0");
	private java.text.DecimalFormat ndf = new java.text.DecimalFormat("0.0E0");
	protected boolean transf_done = false;
	protected java.awt.Image[] im_analiz = new java.awt.Image[6]; // [0]->
	// original,
	// [1]->BW,
	// [2]->rectas,
	// [3]->alinhada,
	// [4]->feixe,
	// [5]->
	// imagem
	// final
	protected boolean values_done = false;
	protected double[] values = new double[6]; // [0]->X0 , [1]->Y0 , [2]-> R ,

	// [3]->intensidade,
	// [4]->tensao, [5]->q_m

	/** Creates new form analasysPanel */
	public AnalasysPanel() {
		initComponents();
		/*
		 * values[0] = 450; values[1] = 244; values[2] = 96; values[3] = 800;
		 * values[4] = 3000; values[5] = 1.8;
		 */
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jScrollPane1 = new javax.swing.JScrollPane();
		jPanelImage = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jTextArea1 = new javax.swing.JTextArea();
		jTextArea2 = new javax.swing.JTextArea();
		jTextArea3 = new javax.swing.JTextArea();
		jTextArea4 = new javax.swing.JTextArea();
		jTextArea5 = new javax.swing.JTextArea();
		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JTextField();
		jTextField3 = new javax.swing.JTextField();
		jTextField4 = new javax.swing.JTextField();
		jTextField5 = new javax.swing.JTextField();
		jTextField6 = new javax.swing.JTextField();

		setLayout(new java.awt.BorderLayout());

		jPanelImage.setMinimumSize(new java.awt.Dimension(640, 480));
		jPanelImage.setPreferredSize(new java.awt.Dimension(640, 480));
		jScrollPane1.setViewportView(jPanelImage);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jPanel2.setBorder(new javax.swing.border.TitledBorder(""));
		jButton1.setText("original");
		jButton1.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton1.setEnabled(false);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jPanel2.add(jButton1, new java.awt.GridBagConstraints());

		jButton2.setText("edge");
		jButton2.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton2.setEnabled(false);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jButton2, gridBagConstraints);

		jButton3.setText("rectas");
		jButton3.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton3.setEnabled(false);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jButton3, gridBagConstraints);

		jButton4.setText("alinhada");
		jButton4.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton4.setEnabled(false);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jButton4, gridBagConstraints);

		jButton5.setText("feixe BW");
		jButton5.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton5.setEnabled(false);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jButton5, gridBagConstraints);

		jButton6.setText("final");
		jButton6.setPreferredSize(new java.awt.Dimension(80, 25));
		jButton6.setEnabled(false);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 10;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jButton6, gridBagConstraints);

		jTextArea1.setBackground(new java.awt.Color(212, 208, 200));
		jTextArea1.setText("=>");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jTextArea1, gridBagConstraints);

		jTextArea2.setBackground(new java.awt.Color(212, 208, 200));
		jTextArea2.setText("=>");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jTextArea2, gridBagConstraints);

		jTextArea3.setBackground(new java.awt.Color(212, 208, 200));
		jTextArea3.setText("=>");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jTextArea3, gridBagConstraints);

		jTextArea4.setBackground(new java.awt.Color(212, 208, 200));
		jTextArea4.setText("=>");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jTextArea4, gridBagConstraints);

		jTextArea5.setBackground(new java.awt.Color(212, 208, 200));
		jTextArea5.setText("=>");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		jPanel2.add(jTextArea5, gridBagConstraints);

		jTextField1.setBackground(new java.awt.Color(204, 255, 204));
		jTextField1.setColumns(9);
		jTextField1.setEditable(false);
		jTextField1.setText("X0=");
		jTextField1.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField1, gridBagConstraints);

		jTextField2.setBackground(new java.awt.Color(204, 255, 204));
		jTextField2.setColumns(9);
		jTextField2.setEditable(false);
		jTextField2.setText("Y0=");
		jTextField2.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField2, gridBagConstraints);

		jTextField3.setBackground(new java.awt.Color(204, 255, 204));
		jTextField3.setColumns(9);
		jTextField3.setEditable(false);
		jTextField3.setText("R=");
		jTextField3.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField3, gridBagConstraints);

		jTextField4.setBackground(new java.awt.Color(255, 204, 255));
		jTextField4.setColumns(9);
		jTextField4.setEditable(false);
		jTextField4.setText("I=");
		jTextField4.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField4, gridBagConstraints);

		jTextField5.setBackground(new java.awt.Color(255, 204, 255));
		jTextField5.setColumns(9);
		jTextField5.setEditable(false);
		jTextField5.setText("V=");
		jTextField5.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField5, gridBagConstraints);

		jTextField6.setBackground(new java.awt.Color(255, 255, 204));
		jTextField6.setColumns(9);
		jTextField6.setEditable(false);
		jTextField6.setText("q/m=");
		jTextField6.setPreferredSize(new java.awt.Dimension(103, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 10;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel2.add(jTextField6, gridBagConstraints);

		jScrollPane2.setViewportView(jPanel2);

		add(jScrollPane2, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
		// TODO add your handling code here:
		jButton1.setBackground(new java.awt.Color(192, 192, 192));
		jButton2.setBackground(new java.awt.Color(192, 192, 192));
		jButton3.setBackground(new java.awt.Color(192, 192, 192));
		jButton4.setBackground(new java.awt.Color(192, 192, 192));
		jButton5.setBackground(new java.awt.Color(192, 192, 192));
		jButton6.setBackground(new java.awt.Color(255, 192, 255));

		paintImage(im_analiz[5]);
	}// GEN-LAST:event_jButton6ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		// TODO add your handling code here:

		jButton1.setBackground(new java.awt.Color(192, 192, 192));
		jButton2.setBackground(new java.awt.Color(192, 192, 192));
		jButton3.setBackground(new java.awt.Color(192, 192, 192));
		jButton4.setBackground(new java.awt.Color(192, 192, 192));
		jButton5.setBackground(new java.awt.Color(255, 192, 255));
		jButton6.setBackground(new java.awt.Color(192, 192, 192));

		// jButton6.setEnabled(true);
		paintImage(im_analiz[4]);

	}// GEN-LAST:event_jButton5ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		// TODO add your handling code here:

		jButton1.setBackground(new java.awt.Color(192, 192, 192));
		jButton2.setBackground(new java.awt.Color(192, 192, 192));
		jButton3.setBackground(new java.awt.Color(192, 192, 192));
		jButton4.setBackground(new java.awt.Color(255, 192, 255));
		jButton5.setBackground(new java.awt.Color(192, 192, 192));
		jButton6.setBackground(new java.awt.Color(192, 192, 192));
		paintImage(im_analiz[3]);
		// jButton5.setEnabled(true);

	}// GEN-LAST:event_jButton4ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		// TODO add your handling code here:

		jButton1.setBackground(new java.awt.Color(192, 192, 192));
		jButton2.setBackground(new java.awt.Color(192, 192, 192));
		jButton3.setBackground(new java.awt.Color(255, 192, 255));
		jButton4.setBackground(new java.awt.Color(192, 192, 192));
		jButton5.setBackground(new java.awt.Color(192, 192, 192));
		jButton6.setBackground(new java.awt.Color(192, 192, 192));
		paintImage(im_analiz[2]);
		// jButton4.setEnabled(true);

	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:

		jButton1.setBackground(new java.awt.Color(192, 192, 192));
		jButton2.setBackground(new java.awt.Color(255, 192, 255));
		jButton3.setBackground(new java.awt.Color(192, 192, 192));
		jButton4.setBackground(new java.awt.Color(192, 192, 192));
		jButton5.setBackground(new java.awt.Color(192, 192, 192));
		jButton6.setBackground(new java.awt.Color(192, 192, 192));
		paintImage(im_analiz[1]);
		// jButton3.setEnabled(true);

	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		jButton1.setBackground(new java.awt.Color(255, 192, 255));
		jButton2.setBackground(new java.awt.Color(192, 192, 192));
		jButton3.setBackground(new java.awt.Color(192, 192, 192));
		jButton4.setBackground(new java.awt.Color(192, 192, 192));
		jButton5.setBackground(new java.awt.Color(192, 192, 192));
		jButton6.setBackground(new java.awt.Color(192, 192, 192));
		paintImage(im_analiz[0]);
		// jButton2.setEnabled(true);

	}// GEN-LAST:event_jButton1ActionPerformed

	private void setXYR(double X, double Y, double R) {
		values[0] = X;
		values[1] = Y;
		values[2] = R;
	}

	private void setI(double intensidade) {
		values[3] = intensidade;
	}

	private void setV(double tensao) {
		values[4] = tensao;
	}

	// private double miu_0 = 8.85419E-12;
	private double miu_0 = 8.99E-7;

	public void setQM() {
		double b = miu_0 * N_ESP / 2 * (values[3] / 1000) / D_ESP;
		values[5] = 2 * values[4] / (Math.pow(values[2] / 5000, 2) * Math.pow(b, 2));

		/*
		 * System.out.println("N_ESP= " + N_ESP); System.out.println("D_ESP= " +
		 * D_ESP); System.out.println("R= " + values[2]);
		 * System.out.println("I= " + values[3]); System.out.println("V= " +
		 * values[4]); System.out.println("q/m= " + values[5]);
		 */
	}

	public void setTexts() {
		String signI = "";
		double i = Double
				.parseDouble(model.getAcquisitionConfig().getSelectedHardwareParameterValue("correntebobines"));

		String modo = null;

		if (model != null)
			modo = model.getAcquisitionConfig().getSelectedHardwareParameterValue("modo");

		if (i < 0)
			signI = "-";

		jTextField1.setText("X0=" + df.format(values[0]) + "(cm)");
		jTextField2.setText("Y0=" + df.format(values[1]) + "(cm)");
		jTextField3.setText("R=" + df.format(values[2] / 50) + "(cm)");
		jTextField4.setText("I=" + signI + df.format(values[3]) + "(mA)");
		jTextField5.setText("V=" + df.format(values[4]) + "(V)");

		if (modo != null && modo.equals("defmag")) {
			setQM();
			jTextField6.setText("q/m=" + ndf.format(values[5]) + "(C/Kg)");
		} else
			jTextField6.setText("-");
	}

	public boolean setImages(java.awt.Image[] img) {

		if (img.length == 6) {
			im_analiz = img;
		}

		transf_done = true;
		return (transf_done);
	}

	public boolean setImages(java.awt.Image img1, java.awt.Image img2, java.awt.Image img3, java.awt.Image img4,
			java.awt.Image img5, java.awt.Image img6) {
		im_analiz[0] = img1;
		im_analiz[1] = img2;
		im_analiz[2] = img3;
		im_analiz[3] = img4;
		im_analiz[4] = img5;
		im_analiz[5] = img6;

		transf_done = true;
		return (transf_done);
	}

	public void setImageN(java.awt.Image img, int n) {

		if (n < 6 & n >= 0) {
			im_analiz[n] = img;
		}
	}

	private void paintImage(java.awt.Image img) {
		jPanelImage.setBorder(new ImageBorder(img, false));
	}

	public static void main(String args[]) {
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new AnalasysPanel(), java.awt.BorderLayout.CENTER);
		dummy.pack();
		dummy.show();
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
	}

	public void dataModelStartedNoData() {
	}

	public void dataModelStoped() {
	}

	public void dataModelWaiting() {
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/thomson/resources/thomson_iconified.png"));
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void newSamples(com.linkare.rec.impl.client.experiment.NewExpDataEvent evt) {
		if (model == null)
			return;
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			if (model.getValueAt(i, model.getChannelIndex("Image")) != null) {
				buildImages(model.getValueAt(i, model.getChannelIndex("Image")).getValue().getByteArrayValue()
						.getData());
			}
			if (model.getValueAt(i, 0) != null)
				setI(model.getValueAt(i, 0).getValue().getIntValue());
			if (model.getValueAt(i, 1) != null)
				setV(model.getValueAt(i, 1).getValue().getIntValue());
		}
	}

	private void buildImages(byte[] imageBA) {
		AnalizadorImagens analyser = new AnalizadorImagens();

		double corrente = 0;
		if (model != null)
			corrente = Double.parseDouble(model.getAcquisitionConfig().getSelectedHardwareParameterValue(
					"correntebobines"));

		analyser.setIsUp(corrente < 0);

		setImageN(analyser.setOriginalImageAsByteArray(imageBA), 0);
		jButton1.setBackground(new java.awt.Color(255, 192, 255));
		jButton1.setEnabled(true);
		paintImage(analyser.getImagemOriginal());
		Image[] imagesAlinhada = analyser.getImagemAlinhada(null);
		setImageN(imagesAlinhada[0], 1);
		jButton2.setEnabled(true);
		setImageN(imagesAlinhada[1], 2);
		jButton3.setEnabled(true);
		setImageN(imagesAlinhada[2], 3);
		jButton4.setEnabled(true);
		setImageN(imagesAlinhada[3], 4);
		jButton5.setEnabled(true);
		double[] xyr = analyser.getCircunferenciaPontos(imagesAlinhada[3]);
		setXYR(xyr[0], xyr[1], xyr[2]);
		/*
		 * System.out.println("xyr[0]= " + xyr[0]);
		 * System.out.println("xyr[1]= " + xyr[1]);
		 * System.out.println("xyr[2]= " + xyr[2]);
		 */
		setImageN(analyser.getFinalimage(imagesAlinhada[2]), 5);
		jButton6.setEnabled(true);

		setTexts();
	}

	private com.linkare.rec.impl.client.experiment.ExpDataModel model = null;

	public void setExpDataModel(com.linkare.rec.impl.client.experiment.ExpDataModel model) {
		if (this.model != null)
			model.removeExpDataModelListener(this);

		this.model = model;

		if (this.model != null)
			this.model.addExpDataModelListener(this);
	}

	public String getName() {
		return ReCResourceBundle.findStringOrDefault("thomson$rec.exp.display.thomson.title.1", "Analasys Panel");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanelImage;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;
	private javax.swing.JTextArea jTextArea3;
	private javax.swing.JTextArea jTextArea4;
	private javax.swing.JTextArea jTextArea5;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	private javax.swing.JTextField jTextField5;
	private javax.swing.JTextField jTextField6;
	// End of variables declaration//GEN-END:variables

}
