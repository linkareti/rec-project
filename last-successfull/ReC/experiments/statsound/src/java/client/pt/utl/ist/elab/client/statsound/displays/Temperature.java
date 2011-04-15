/*
 * Temperature.java
 *
 * Created on 02 February 2004, 23:26
 */

package pt.utl.ist.elab.client.statsound.displays;

/**
 *
 * @author  Andr�
 */

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Temperature extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {

	/** Creates new form Temperature */
	public Temperature() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		jLabel1 = new javax.swing.JLabel();
		jTextFieldTempIni = new javax.swing.JTextField();
		jLabel11 = new javax.swing.JLabel();
		jTextFieldFinalTemp = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();

		setLayout(new java.awt.GridBagLayout());

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setText("Initial temperature:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		add(jLabel1, gridBagConstraints);

		jTextFieldTempIni.setColumns(5);
		jTextFieldTempIni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldTempIni.setText("-");
		add(jTextFieldTempIni, new java.awt.GridBagConstraints());

		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel11.setText("Final temperature:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		add(jLabel11, gridBagConstraints);

		jTextFieldFinalTemp.setColumns(5);
		jTextFieldFinalTemp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldFinalTemp.setText("-");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		add(jTextFieldFinalTemp, gridBagConstraints);

		jLabel2.setText("\u00baC");
		add(jLabel2, new java.awt.GridBagConstraints());

		jLabel21.setText("\u00baC");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		add(jLabel21, gridBagConstraints);

	}// GEN-END:initComponents

	public void dataModelRunning() {
	}

	public void dataModelStoped() {
	}

	private boolean firstTime = false;

	public void headerAvailable(HardwareAcquisitionConfig header) {
		jTextFieldFinalTemp.setText("-");
		jTextFieldTempIni.setText("-");
		firstTime = true;
	}

	public void newSamples(NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			if (model.getValueAt(i, model.getChannelIndex("temp")) != null) {
				System.out.println(model.getValueAt(i, model.getChannelIndex("temp")));
				if (firstTime) {
					jTextFieldTempIni.setText("" + model.getValueAt(i, model.getChannelIndex("temp")).getValueNumber());
					firstTime = false;
				} else {
					jTextFieldFinalTemp.setText(""
							+ model.getValueAt(i, model.getChannelIndex("temp")).getValueNumber());
				}
			}
		}
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/table16.gif"));
	}

	public String getName() {
		return "Temperature";
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	private ExpDataModel model = null;

	/**
	 * Getter for property expDataModel.
	 * 
	 * @return Value of property expDataModel.
	 */
	public ExpDataModel getExpDataModel() {
		return this.model;
	}

	/**
	 * Setter for property expDataModel.
	 * 
	 * @param expDataModel New value of property expDataModel.
	 */
	public void setExpDataModel(ExpDataModel model) {
		if (model != null)
			model.removeExpDataModelListener(this);

		this.model = model;

		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
		if (model != null)
			headerAvailable(model.getAcquisitionConfig());
	}

	public void dataModelStartedNoData() {
	}

	public void dataModelWaiting() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JTextField jTextFieldTempIni;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JTextField jTextFieldFinalTemp;
	// End of variables declaration//GEN-END:variables

}
