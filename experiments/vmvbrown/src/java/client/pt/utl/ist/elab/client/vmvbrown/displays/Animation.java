/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.opensourcephysics.displayejs.DrawingPanel3D;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author nomead
 */
public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6475653665018521985L;
	private BrownMovement brown;

	/** Creates a new instance of Animation */
	public Animation() {
		super();
		// setPreferredSize(new java.awt.Dimension(700,500));
	}

	// TESTE
	/*
	 * public void start(){ boolean anima = true; if (anima){ //brown = new
	 * Animation2D(); brown = new
	 * Animation3D(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE); setLayout(new
	 * java.awt.GridBagLayout());
	 * 
	 * java.awt.GridBagConstraints gridBagConstraints;
	 * 
	 * gridBagConstraints = new java.awt.GridBagConstraints();
	 * gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
	 * gridBagConstraints.gridheight= 1; gridBagConstraints.gridwidth = 1;
	 * gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	 * gridBagConstraints.weightx = 1; gridBagConstraints.weighty = 1;
	 * add((JPanel)brown, gridBagConstraints);
	 * 
	 * updateUI();
	 * 
	 * brown.config(30,(byte)3,java.awt.Color.BLACK); MvBrownDataProducer mv =
	 * new
	 * MvBrownDataProducer(null,30,(byte)3,getWidth(),getHeight(),true,true,100
	 * ,2000); mv.initializeLangevin(1,new double[]{0.1,0.01},new
	 * double[]{0.1,0.25},new double[]{0,0,0},false); mv.start(this); } else {
	 * setLayout(new java.awt.GridBagLayout()); java.awt.GridBagConstraints
	 * gridBagConstraints; gridBagConstraints = new
	 * java.awt.GridBagConstraints(); gridBagConstraints.gridx = 0;
	 * gridBagConstraints.gridy = 0; gridBagConstraints.gridheight= 1;
	 * gridBagConstraints.gridwidth = 1; gridBagConstraints.fill =
	 * java.awt.GridBagConstraints.BOTH; gridBagConstraints.weightx = 1;
	 * gridBagConstraints.weighty = 1;
	 * 
	 * javax.swing.JLabel label = new
	 * javax.swing.JLabel(java.util.ResourceBundle
	 * .getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages
	 * ).getString("rec.exp.displays.inactive"),javax.swing.JLabel.CENTER);
	 * removeAll(); add(label, gridBagConstraints); updateUI(); } }
	 * 
	 * //TESTE public void moves(byte [] mv){ brown.moves(mv); }
	 * 
	 * public static void main(String args[]) { javax.swing.JFrame test = new
	 * javax.swing.JFrame(); test.addWindowListener(new
	 * java.awt.event.WindowAdapter() { public void
	 * windowClosing(java.awt.event.WindowEvent e) { System.exit(0); }; });
	 * Animation stdim = new Animation(); test.getContentPane().add(stdim);
	 * test.pack(); test.setVisible(true); stdim.start(); }
	 */

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

		if (header.getSelectedHardwareParameterValue("animaAct").trim().equals("1") ? true : false) {
			final byte dim = Byte.parseByte(header.getSelectedHardwareParameterValue("dim"));

			if (dim == 3) {
				brown = new Animation3D(DrawingPanel3D.DISPLAY_NO_PERSPECTIVE);
			} else {
				brown = new Animation2D();
			}

			setLayout(new java.awt.GridBagLayout());
			java.awt.GridBagConstraints gridBagConstraints;
			gridBagConstraints = new java.awt.GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;
			add((JPanel) brown, gridBagConstraints);
			updateUI();
			brown.config(Integer.parseInt(header.getSelectedHardwareParameterValue("nPart")),
					Byte.parseByte(header.getSelectedHardwareParameterValue("radiusAnima")), java.awt.Color.BLACK);
		} else {
			setLayout(new java.awt.GridBagLayout());
			java.awt.GridBagConstraints gridBagConstraints;
			gridBagConstraints = new java.awt.GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.weighty = 1;

			final javax.swing.JLabel label = new javax.swing.JLabel(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vmvbrown/resources/messages").getString("rec.exp.displays.inactive"),
					SwingConstants.CENTER);
			removeAll();
			add(label, gridBagConstraints);
			updateUI();
		}
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
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
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
			if (model.getValueAt(i, 0) != null) {
				brown.moves(model.getValueAt(i, 0).getValue().getByteArrayValue().getData());
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

}
