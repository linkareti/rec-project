/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.virtual.client.cartpole.displays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pt.utl.ist.elab.client.vcartpole.CartPole;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author nomead
 */
public class Animation extends CartPole implements ExpDataDisplay, ExpDataModelListener {

	/** Creates a new instance of Animation */
	public Animation() {
		super();
	}

	protected void config() {
	}

	// TESTE
	/*
	 * public void start(){ CartPoleDataProducer data = new
	 * CartPoleDataProducer(0,0,1e-3,0,new double[]{5e-4,2e-6},new
	 * double[]{1,1e-1},9.8,1,0,100,500); data.initializePID(0,0,0);
	 * data.initializeFailure(1,300,20,5); data.initializeSuccess(12, 5);
	 * data.start(this); }
	 */

	public void move(double x, double theta, double xdot, double thetadot, double action, double time) {
		statusStr = "t : " + pt.utl.ist.elab.client.virtual.guipack.GUtils.trimDecimalN(time, 5) + " s";
		move(x * 10, theta, xdot * 10, thetadot, action * 10);
	}

	/*
	 * public static void main(String args[]) { javax.swing.JFrame test = new
	 * javax.swing.JFrame(); test.addWindowListener(new
	 * java.awt.event.WindowAdapter() { public void
	 * windowClosing(java.awt.event.WindowEvent e) { System.exit(0); }; });
	 * Animation stdim = new Animation(); test.getContentPane().add(stdim);
	 * test.pack(); test.show(); stdim.start(); }
	 */

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
	}

	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		double mCart = Float.parseFloat(header.getSelectedHardwareParameterValue("mCart"));
		double mPole = Float.parseFloat(header.getSelectedHardwareParameterValue("mPole"));

		double x = Float.parseFloat(header.getSelectedHardwareParameterValue("x")) * 10;
		double xdot = Float.parseFloat(header.getSelectedHardwareParameterValue("xdot")) * 10;
		double theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
		double thetadot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetadot")) * 10;
		double poleLength = Float.parseFloat(header.getSelectedHardwareParameterValue("poleLength")) * 10d;
		double action = Float.parseFloat(header.getSelectedHardwareParameterValue("action")) * 10d;
		double xMax = Float.parseFloat(header.getSelectedHardwareParameterValue("xMax")) * 10d;

		setXLimit(xMax);
		config(x, theta, xdot, thetadot, poleLength, mCart, mPole, action, Math.toRadians(Integer.parseInt(header
				.getSelectedHardwareParameterValue("sucAngle"))));
		measure();
	}

	public void dataModelStoped() {
	}

	public void dataModelWaiting() {
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void newSamples(NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null && model.getValueAt(i, 2) != null
					&& model.getValueAt(i, 3) != null && model.getValueAt(i, 4) != null
					&& model.getValueAt(i, 5) != null)
				move(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue(), model.getValueAt(i, 2).getValue().getFloatValue(), model.getValueAt(i, 3)
						.getValue().getFloatValue(), model.getValueAt(i, 5).getValue().getFloatValue(), model
						.getValueAt(i, 4).getValue().getFloatValue());
			else if (model.getValueAt(i, 6) != null)
				messageDialog(java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.customizer.title.35"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.3"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.3"), null, java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.1"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.1"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.2"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.2"));
			else if (model.getValueAt(i, 7) != null)
				messageDialog(java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.customizer.title.36"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.4"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.3"), null, java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.1"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.1"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.title.2"), java.util.ResourceBundle.getBundle(
						"pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString(
						"rec.exp.displays.dialog.tip.2"));
		}
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);
	}

	public void messageDialog(String message, String messageTooltip, String title, String toolTip,
			String continueTitle, String continueToolTip, String stopTitle, String stopToolTip) {
		pause();
		java.awt.GridBagConstraints gridBagConstraints;

		JDialog tempDialog = new JDialog(new JFrame(), title, true);

		JPanel dialogPanel = new JPanel();
		JLabel messageLabel = new JLabel(message);
		JButton stopButton = new JButton();
		JButton continueButton = new JButton();

		tempDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		dialogPanel.setLayout(new java.awt.GridBagLayout());

		messageLabel.setToolTipText(messageTooltip);
		messageLabel.setHorizontalAlignment(messageLabel.CENTER);
		messageLabel.setMinimumSize(new java.awt.Dimension(300, 47));
		messageLabel.setPreferredSize(new java.awt.Dimension(300, 47));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridwidth = 2;
		dialogPanel.add(messageLabel, gridBagConstraints);
		dialogPanel.setToolTipText(toolTip);

		continueButton.setText(continueTitle);
		continueButton.setToolTipText(continueToolTip);
		continueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				((JDialog) ((JButton) evt.getSource()).getTopLevelAncestor()).dispose();
				resume();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		dialogPanel.add(continueButton, gridBagConstraints);

		stopButton.setText(stopTitle);
		stopButton.setToolTipText(stopToolTip);
		stopButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				((JDialog) ((JButton) evt.getSource()).getTopLevelAncestor()).dispose();
				stop();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		dialogPanel.add(stopButton, gridBagConstraints);

		tempDialog.getContentPane().add(dialogPanel, java.awt.BorderLayout.CENTER);

		tempDialog.pack();

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Dimension tempSize = tempDialog.getSize();
		tempDialog.setLocation((int) ((screenSize.width - tempSize.getWidth()) / 2d),
				(int) ((screenSize.height - tempSize.getHeight()) / 2d));

		tempDialog.show();
	}

	public void pause() {
		// if (model != null)
		// model.pause();
	}

	public void stop() {
		// if (model != null)
		// model.stopNow();
	}

	public void resume() {
		// if (model != null)
		// model.play();
	}

}
