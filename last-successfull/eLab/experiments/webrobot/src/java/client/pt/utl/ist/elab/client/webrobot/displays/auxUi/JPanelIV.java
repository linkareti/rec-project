/*
 * JPanelIV.java
 *
 * Created on 9 de Abril de 2003, 19:21
 */

package pt.utl.ist.elab.client.webrobot.displays.auxUi;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author Andr�
 */
public class JPanelIV extends javax.swing.JPanel implements ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1165099135144688708L;

	/** Creates new form JPanelIV */
	public JPanelIV() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		jProgressBarIR0 = new javax.swing.JProgressBar();
		jProgressBarIR1 = new javax.swing.JProgressBar();
		jProgressBarIR2 = new javax.swing.JProgressBar();
		jProgressBarIR3 = new javax.swing.JProgressBar();
		jProgressBarIR4 = new javax.swing.JProgressBar();
		jProgressBarIR5 = new javax.swing.JProgressBar();
		jProgressBarIR6 = new javax.swing.JProgressBar();
		jProgressBarIR7 = new javax.swing.JProgressBar();
		jLabelIR0 = new javax.swing.JLabel();
		jLabelIR1 = new javax.swing.JLabel();
		jLabelIR2 = new javax.swing.JLabel();
		jLabelIR3 = new javax.swing.JLabel();
		jLabelIR4 = new javax.swing.JLabel();
		jLabelIR5 = new javax.swing.JLabel();
		jLabelIR6 = new javax.swing.JLabel();
		jLabelIR7 = new javax.swing.JLabel();

		setLayout(new java.awt.GridBagLayout());

		jProgressBarIR0.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR0.setMaximum(255);
		jProgressBarIR0.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR0.setMinimumSize(new java.awt.Dimension(10, 21));
		jProgressBarIR0.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR0.setString(new Integer(IR0).toString());
		jProgressBarIR0.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 8, 0, 0);
		add(jProgressBarIR0, gridBagConstraints);

		jProgressBarIR1.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR1.setMaximum(255);
		jProgressBarIR1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR1.setMinimumSize(new java.awt.Dimension(10, 21));
		jProgressBarIR1.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR1.setString(new Integer(IR1).toString());
		jProgressBarIR1.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR1, gridBagConstraints);

		jProgressBarIR2.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR2.setMaximum(255);
		jProgressBarIR2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR2.setMinimumSize(new java.awt.Dimension(10, 21));
		jProgressBarIR2.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR2.setString(new Integer(IR2).toString());
		jProgressBarIR2.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipady = -1;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR2, gridBagConstraints);

		jProgressBarIR3.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR3.setMaximum(255);
		jProgressBarIR3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR3.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR3.setString(new Integer(IR3).toString());
		jProgressBarIR3.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 8, 0, 0);
		add(jProgressBarIR3, gridBagConstraints);

		jProgressBarIR4.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR4.setMaximum(255);
		jProgressBarIR4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR4.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR4.setString(new Integer(IR4).toString());
		jProgressBarIR4.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR4, gridBagConstraints);

		jProgressBarIR5.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR5.setMaximum(255);
		jProgressBarIR5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR5.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR5.setString(new Integer(IR5).toString());
		jProgressBarIR5.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR5, gridBagConstraints);

		jProgressBarIR6.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR6.setMaximum(255);
		jProgressBarIR6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR6.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR6.setString(new Integer(IR6).toString());
		jProgressBarIR6.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR6, gridBagConstraints);

		jProgressBarIR7.setForeground(new java.awt.Color(0, 51, 204));
		jProgressBarIR7.setMaximum(255);
		jProgressBarIR7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153)));
		jProgressBarIR7.setPreferredSize(new java.awt.Dimension(300, 21));
		jProgressBarIR7.setString(new Integer(IR7).toString());
		jProgressBarIR7.setStringPainted(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 14;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 8, 0, 0);
		add(jProgressBarIR7, gridBagConstraints);

		jLabelIR0.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR0.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR0.setText("IR0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
		add(jLabelIR0, gridBagConstraints);

		jLabelIR1.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR1.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR1.setText("IR1");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR1, gridBagConstraints);

		jLabelIR2.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR2.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR2.setText("IR2");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR2, gridBagConstraints);

		jLabelIR3.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR3.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR3.setText("IR3");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
		add(jLabelIR3, gridBagConstraints);

		jLabelIR4.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR4.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR4.setText("IR4");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR4, gridBagConstraints);

		jLabelIR5.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR5.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR5.setText("IR5");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR5, gridBagConstraints);

		jLabelIR6.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR6.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR6.setText("IR6");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR6, gridBagConstraints);

		jLabelIR7.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
		jLabelIR7.setForeground(new java.awt.Color(0, 51, 153));
		jLabelIR7.setText("IR7");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 14;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
		add(jLabelIR7, gridBagConstraints);

	}// GEN-END:initComponents

	/**
	 * Getter for property IR0.
	 * 
	 * @return Value of property IR0.
	 */
	public int getIR0() {
		return IR0;
	}

	/**
	 * Setter for property IR0.
	 * 
	 * @param IR0 New value of property IR0.
	 */
	public void setIR0(final int IR0) {
		this.IR0 = IR0;
		jProgressBarIR0.setValue(IR0);
		jProgressBarIR0.setString(new Integer(IR0).toString());
	}

	/**
	 * Getter for property IR1.
	 * 
	 * @return Value of property IR1.
	 */
	public int getIR1() {
		return IR1;
	}

	/**
	 * Setter for property IR1.
	 * 
	 * @param IR1 New value of property IR1.
	 */
	public void setIR1(final int IR1) {
		this.IR1 = IR1;
		jProgressBarIR1.setValue(IR1);
		jProgressBarIR1.setString(new Integer(IR1).toString());
	}

	/**
	 * Getter for property IR2.
	 * 
	 * @return Value of property IR2.
	 */
	public int getIR2() {
		return IR2;
	}

	/**
	 * Setter for property IR2.
	 * 
	 * @param IR2 New value of property IR2.
	 */
	public void setIR2(final int IR2) {
		this.IR2 = IR2;
		jProgressBarIR2.setValue(IR2);
		jProgressBarIR2.setString(new Integer(IR2).toString());
	}

	/**
	 * Getter for property IR3.
	 * 
	 * @return Value of property IR3.
	 */
	public int getIR3() {
		return IR3;
	}

	/**
	 * Setter for property IR3.
	 * 
	 * @param IR3 New value of property IR3.
	 */
	public void setIR3(final int IR3) {
		this.IR3 = IR3;
		jProgressBarIR3.setValue(IR3);
		jProgressBarIR3.setString(new Integer(IR3).toString());
	}

	/**
	 * Getter for property IR4.
	 * 
	 * @return Value of property IR4.
	 */
	public int getIR4() {
		return IR4;
	}

	/**
	 * Setter for property IR4.
	 * 
	 * @param IR4 New value of property IR4.
	 */
	public void setIR4(final int IR4) {
		this.IR4 = IR4;
		jProgressBarIR4.setValue(IR4);
		jProgressBarIR4.setString(new Integer(IR4).toString());
	}

	/**
	 * Getter for property IR5.
	 * 
	 * @return Value of property IR5.
	 */
	public int getIR5() {
		return IR5;
	}

	/**
	 * Setter for property IR5.
	 * 
	 * @param IR5 New value of property IR5.
	 */
	public void setIR5(final int IR5) {
		this.IR5 = IR5;
		jProgressBarIR5.setValue(IR5);
		jProgressBarIR5.setString(new Integer(IR5).toString());
	}

	/**
	 * Getter for property IR6.
	 * 
	 * @return Value of property IR6.
	 */
	public int getIR6() {
		return IR6;
	}

	/**
	 * Setter for property IR6.
	 * 
	 * @param IR6 New value of property IR6.
	 */
	public void setIR6(final int IR6) {
		this.IR6 = IR6;
		jProgressBarIR6.setValue(IR6);
		jProgressBarIR6.setString(new Integer(IR6).toString());
	}

	/**
	 * Getter for property IR7.
	 * 
	 * @return Value of property IR7.
	 */
	public int getIR7() {
		return IR7;
	}

	/**
	 * Setter for property IR7.
	 * 
	 * @param IR7 New value of property IR7.
	 */
	public void setIR7(final int IR7) {
		this.IR7 = IR7;
		jProgressBarIR7.setValue(IR7);
		jProgressBarIR7.setString(new Integer(IR7).toString());
	}

	public void dataModelRunning() {
	}

	@Override
	public void dataModelStoped() {
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		final int lastSample = evt.getSamplesEndIndex();
		setIR0(model.getValueAt(lastSample, 0).getValue().getIntValue());
		setIR1(model.getValueAt(lastSample, 1).getValue().getIntValue());
		setIR2(model.getValueAt(lastSample, 2).getValue().getIntValue());
		setIR3(model.getValueAt(lastSample, 3).getValue().getIntValue());
		setIR4(model.getValueAt(lastSample, 4).getValue().getIntValue());
		setIR5(model.getValueAt(lastSample, 5).getValue().getIntValue());
		setIR6(model.getValueAt(lastSample, 6).getValue().getIntValue());
		setIR7(model.getValueAt(lastSample, 7).getValue().getIntValue());
	}

	public void setExpDataModel(final ExpDataModel model) {
		if (this.model != null) {
			model.removeExpDataModelListener(this);
		}

		this.model = model;

		if (this.model != null) {
			this.model.addExpDataModelListener(this);
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
		headerAvailable(model.getAcquisitionConfig());
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelWaiting() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JProgressBar jProgressBarIR1;
	private javax.swing.JLabel jLabelIR0;
	private javax.swing.JLabel jLabelIR7;
	private javax.swing.JProgressBar jProgressBarIR6;
	private javax.swing.JProgressBar jProgressBarIR0;
	private javax.swing.JLabel jLabelIR4;
	private javax.swing.JLabel jLabelIR5;
	private javax.swing.JProgressBar jProgressBarIR5;
	private javax.swing.JProgressBar jProgressBarIR2;
	private javax.swing.JLabel jLabelIR1;
	private javax.swing.JProgressBar jProgressBarIR7;
	private javax.swing.JLabel jLabelIR6;
	private javax.swing.JProgressBar jProgressBarIR3;
	private javax.swing.JLabel jLabelIR2;
	private javax.swing.JProgressBar jProgressBarIR4;
	private javax.swing.JLabel jLabelIR3;
	// End of variables declaration//GEN-END:variables

	private ExpDataModel model = null;

	/** Holds value of property IR0. */
	private int IR0 = 0;

	/** Holds value of property IR1. */
	private int IR1 = 0;

	/** Holds value of property IR2. */
	private int IR2 = 0;

	/** Holds value of property IR3. */
	private int IR3 = 0;

	/** Holds value of property IR4. */
	private int IR4 = 0;

	/** Holds value of property IR5. */
	private int IR5 = 0;

	/** Holds value of property IR6. */
	private int IR6 = 0;

	/** Holds value of property IR7. */
	private int IR7 = 0;
}