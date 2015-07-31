/*
 * JPanel.java
 *
 * Created on 17 de Dezembro de 2003, 9:42
 */

package pt.utl.ist.elab.client.meteo.displays;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import javax.swing.SwingConstants;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class MeteoRecent extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175705331277347386L;
	java.text.DecimalFormat df = null;

	/** Creates new form JPanel */
	public MeteoRecent() {
		df = new java.text.DecimalFormat();
		df.setMaximumFractionDigits(2);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jTextFieldPression = new javax.swing.JTextField();
		jTextFieldLum = new javax.swing.JTextField();
		jTextFieldCond = new javax.swing.JTextField();
		jTextFieldHum = new javax.swing.JTextField();
		jTextFieldWindVel = new javax.swing.JTextField();
		jTextFieldWindDir = new javax.swing.JTextField();
		jTextFieldRain = new javax.swing.JTextField();
		jTextFieldTemp = new javax.swing.JTextField();
		jLabel8 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel131 = new javax.swing.JLabel();

		setLayout(new java.awt.GridBagLayout());

		setToolTipText("Esta\u00e7\u00e3o Metereol\u00f3gica");
		setName("Esta\u00e7\u00e3o Metereol\u00f3gica");
		jPanel1.setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(new javax.swing.border.TitledBorder("\u00daltima Aquisi\u00e7\u00e3o"));
		jTextFieldPression.setColumns(6);
		jTextFieldPression.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldPression.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldPression, gridBagConstraints);

		jTextFieldLum.setColumns(6);
		jTextFieldLum.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldLum.setText("-");
		jTextFieldLum.setMinimumSize(new java.awt.Dimension(66, 20));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
		jPanel1.add(jTextFieldLum, gridBagConstraints);

		jTextFieldCond.setColumns(6);
		jTextFieldCond.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldCond.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldCond, gridBagConstraints);

		jTextFieldHum.setColumns(6);
		jTextFieldHum.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldHum.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldHum, gridBagConstraints);

		jTextFieldWindVel.setColumns(6);
		jTextFieldWindVel.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldWindVel.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldWindVel, gridBagConstraints);

		jTextFieldWindDir.setColumns(6);
		jTextFieldWindDir.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldWindDir.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldWindDir, gridBagConstraints);

		jTextFieldRain.setColumns(6);
		jTextFieldRain.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldRain.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel1.add(jTextFieldRain, gridBagConstraints);

		jTextFieldTemp.setColumns(6);
		jTextFieldTemp.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldTemp.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
		jPanel1.add(jTextFieldTemp, gridBagConstraints);

		jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/pressure.gif")));
		jLabel8.setText("Press\u00e3o Atmosf\u00e9rica");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel8, gridBagConstraints);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/thermometer.gif")));
		jLabel1.setText("Temperatura");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel1, gridBagConstraints);

		jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/sun.gif")));
		jLabel7.setText("Luminosidade");
		jLabel7.setMinimumSize(new java.awt.Dimension(78, 16));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel7, gridBagConstraints);

		jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/drop.gif")));
		jLabel6.setText("Condensa\u00e7\u00e3o");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel6, gridBagConstraints);

		jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/humidity.gif")));
		jLabel5.setText("Humidade");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel5, gridBagConstraints);

		jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/windvel.gif")));
		jLabel4.setText("Velocidade do Vento");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel4, gridBagConstraints);

		jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/winddir.gif")));
		jLabel3.setText("Direc\u00e7\u00e3o do Vento");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel3, gridBagConstraints);

		jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/meteo/resources/rain.gif")));
		jLabel2.setText("Precipita\u00e7\u00e3o");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		jPanel1.add(jLabel2, gridBagConstraints);

		jLabel9.setText("Celsius");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		jPanel1.add(jLabel9, gridBagConstraints);

		jLabel10.setText("mm / h");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		jPanel1.add(jLabel10, gridBagConstraints);

		jLabel12.setText("Km / h");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		jPanel1.add(jLabel12, gridBagConstraints);

		jLabel13.setText("%");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 6;
		jPanel1.add(jLabel13, gridBagConstraints);

		jLabel14.setText("milibars");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 7;
		jPanel1.add(jLabel14, gridBagConstraints);

		jLabel15.setText("\u00ba");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanel1.add(jLabel15, gridBagConstraints);

		jLabel131.setText("%");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		jPanel1.add(jLabel131, gridBagConstraints);

		add(jPanel1, new java.awt.GridBagConstraints());

	}//GEN-END:initComponents

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon("/com/linkare/rec/impl/newface/resources/legacy/table16.gif");
	}

	private ExpDataModel expDataModel = null;

	@Override
	public void setExpDataModel(final ExpDataModel expDataModel) {
		if (expDataModel != null) {
			expDataModel.removeExpDataModelListener(this);
		}

		this.expDataModel = expDataModel;

		if (this.expDataModel != null) {
			this.expDataModel.addExpDataModelListener(this);
		}
	}

	@Override
	public String getName() {
		return "Valores �ltima aquisi��o";
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void dataModelRunning() {// BIG SILENT NOOP
	}

	@Override
	public void dataModelStoped() {// BIG SILENT NOOP
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		jTextFieldCond.setText("-");
		jTextFieldHum.setText("-");
		jTextFieldLum.setText("-");
		jTextFieldPression.setText("-");
		jTextFieldRain.setText("-");
		jTextFieldTemp.setText("-");
		jTextFieldWindDir.setText("-");
		jTextFieldWindVel.setText("-");
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		System.out.println("getSamplesStartIndex= " + evt.getSamplesStartIndex());
		System.out.println("getSamplesEndIndex= " + evt.getSamplesEndIndex());
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			System.out.println(expDataModel.getValueAt(i, 0));
			if (expDataModel.getValueAt(i, 0) == null) {
				if (expDataModel.getValueAt(i, 1) != null) {
					jTextFieldTemp.setText(df.format(expDataModel.getValueAt(i, 1).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 2) != null) {
					jTextFieldRain.setText(df.format(expDataModel.getValueAt(i, 2).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 3) != null) {
					jTextFieldWindDir.setText(df.format(expDataModel.getValueAt(i, 3).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 4) != null) {
					jTextFieldWindVel.setText(df.format(expDataModel.getValueAt(i, 4).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 5) != null) {
					jTextFieldHum.setText(df.format(expDataModel.getValueAt(i, 5).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 6) != null) {
					final float cond = expDataModel.getValueAt(i, 6).getValueNumber().floatValue();
					if (cond > 0.5) {
						jTextFieldCond.setText("sim");
					} else {
						jTextFieldCond.setText("n�o");
					}
				}
				if (expDataModel.getValueAt(i, 7) != null) {
					jTextFieldLum.setText(df.format(expDataModel.getValueAt(i, 7).getValueNumber().floatValue()));
				}
				if (expDataModel.getValueAt(i, 8) != null) {
					jTextFieldPression.setText(df.format(expDataModel.getValueAt(i, 8).getValueNumber().floatValue()));
				}
			}
		}
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
		headerAvailable(null);
	}

	@Override
	public void dataModelWaiting() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JTextField jTextFieldWindDir;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel131;
	private javax.swing.JTextField jTextFieldLum;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField jTextFieldHum;
	private javax.swing.JTextField jTextFieldWindVel;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JTextField jTextFieldCond;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JTextField jTextFieldRain;
	private javax.swing.JTextField jTextFieldPression;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JTextField jTextFieldTemp;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel5;
	// End of variables declaration//GEN-END:variables
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.DATA_TABLE;
    }

}