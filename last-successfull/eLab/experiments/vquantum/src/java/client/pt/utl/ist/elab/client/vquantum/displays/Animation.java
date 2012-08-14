/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vquantum.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pt.utl.ist.elab.client.vquantum.ComplexGaussian;
import pt.utl.ist.elab.client.vquantum.Quantum;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author nomead
 */
public class Animation extends Quantum implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5018781901552435877L;

	/** Creates a new instance of Animation */
	public Animation() {
		super();
	}

	// TESTE
	public void start() {
		clearPotentials();
		// configPotentials("0.0:5.0:f(x) = 100:false#6.0:1.0:f(x) = 50:false#",false);
		configPotentials(
				"0.0:4.0:f(x) = -cos(x)*5:false#5.0:5.0:f(x) = sin(x)*5:false#10.5:2.0:f(x) = exp(-x*25)+3:false#-3.0:2.0:f(x) = 255:false#-5.0:1.5:f(x) = 90+x^3+4*x^2+5+cos(3*x):false",
				false);
		configGaussian(100, -10, 100, 11, 2, ComplexGaussian.DISPLAY_PROBABILITY, true);
		setPreferredMinMax(-60, 40, -1, 1);
		repaint();

		// FIXME - client should never depend on driver part
		// QuantumDataProducer q = new QuantumDataProducer(null, 100, -10, 100,
		// 11, 2, 1e-5, 5e-20, 5e-18, 200, false,
		// true, true);
		// q.configPotentials("0.0:5.0:f(x) = 100:false#6.0:1.0:f(x) = 50:false#");
		// q
		// .configPotentials("0.0:4.0:f(x) = -cos(x)*5:false#5.0:5.0:f(x) = sin(x)*5:false#10.5:2.0:f(x) = exp(-x*25)+3:false#-3.0:2.0:f(x) = 255:false#-5.0:1.5:f(x) = 90+x^3+4*x^2+5+cos(3*x):false");
		// q.start(this);
	}

	public void moves(final byte[] mv) {
		// FIXME - client should never depend on driver part
		// setPsi((Complex[]) ByteUtil.byteArrayToObject(mv));
		repaint();
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		final Animation stdim = new Animation();
		test.getContentPane().add(stdim);
		test.pack();
		test.setVisible(true);
		stdim.start();
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
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		final double x0 = Double.parseDouble(header.getSelectedHardwareParameterValue("x0"));
		final int deltaX = Integer.parseInt(header.getSelectedHardwareParameterValue("deltaX"));
		final int log2N = Byte.parseByte(header.getSelectedHardwareParameterValue("log2N"));
		final int dX0 = Integer.parseInt(header.getSelectedHardwareParameterValue("dX0"));

		final String xE = header.getSelectedHardwareParameterValue("xEnergy");
		final String nE = header.getSelectedHardwareParameterValue("nEnergy");

		final double energy = Double.parseDouble(xE + "e" + nE);

		clearPotentials();
		configPotentials(header.getSelectedHardwareParameterValue("potentials"), false);
		configGaussian(dX0, x0, energy, log2N, deltaX, ComplexGaussian.DISPLAY_PROBABILITY, true);
	}

	@Override
	public void dataModelStoped() {
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 0) != null) {
				moves(model.getValueAt(i, 0).getValue().getByteArrayValue().getData());
			} else if (model.getValueAt(i, 7) != null) {
				messageDialog(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
						.getString("rec.exp.customizer.title.35"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.3"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.3"), null,
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.1"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.1"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.2"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.2"));
			} else if (model.getValueAt(i, 8) != null) {
				messageDialog(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
						.getString("rec.exp.customizer.title.36"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.4"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.3"), null,
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.1"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.1"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.title.2"),
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
								.getString("rec.exp.displays.dialog.tip.2"));
			}
		}
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}

	}

	public void messageDialog(final String message, final String messageTooltip, final String title,
			final String toolTip, final String continueTitle, final String continueToolTip, final String stopTitle,
			final String stopToolTip) {
		pause();
		java.awt.GridBagConstraints gridBagConstraints;

		final JDialog tempDialog = new JDialog(new JFrame(), title, true);

		final JPanel dialogPanel = new JPanel();
		final JLabel messageLabel = new JLabel(message);
		final JButton stopButton = new JButton();
		final JButton continueButton = new JButton();

		tempDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		dialogPanel.setLayout(new java.awt.GridBagLayout());

		messageLabel.setToolTipText(messageTooltip);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
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
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
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

		final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		final java.awt.Dimension tempSize = tempDialog.getSize();
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
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.ANIMATION;
    }

}
