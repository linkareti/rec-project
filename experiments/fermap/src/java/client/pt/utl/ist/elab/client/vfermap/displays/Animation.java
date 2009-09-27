/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vfermap.displays;

import pt.utl.ist.elab.client.vfermap.FERMAPAnima;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class Animation extends FERMAPAnima implements ExpDataDisplay, ExpDataModelListener {

	/** Creates a new instance of Animation */
	public Animation() {
		super();

		// TESTE
		// config(2, 10, 0, 20, 9);
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2,
		// 5, 0, 10.1, 9, 10, 1, 1000).startAnima(this);
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2,
		// 10, 0, 0.1f, 9, 20, 1, 1000).startAnima(this);
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2,
		// 100, 0, 10.1, 9, 100, 1, 1000).startAnima(this);
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2,
		// 200, 2*Math.PI, 0.1f, 9, 20, 1, 1000).startAnima(this);

	}

	public static void main(String args[]) {
		javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		Animation ferim = new Animation();
		test.getContentPane().add(ferim);
		test.pack();
		test.show();
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
	}

	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		if (Byte.parseByte(header.getSelectedHardwareParameterValue("simulType")) != 2)
			setVisible(false);
		else {
			float x = Float.parseFloat(header.getSelectedHardwareParameterValue("x"));
			float xDot = Float.parseFloat(header.getSelectedHardwareParameterValue("xDot"));
			float psi = Float.parseFloat(header.getSelectedHardwareParameterValue("psi"));
			float wAmp = Float.parseFloat(header.getSelectedHardwareParameterValue("wAmp"));
			float d = Float.parseFloat(header.getSelectedHardwareParameterValue("d"));

			config(x, xDot, psi, d, wAmp);
		}
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
			if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null) // continuidade
				// (x,xWall)
				move(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue());
			else if (model.getValueAt(i, 2) != null) // velocidade
				setVel(model.getValueAt(i, 2).getValue().getFloatValue());
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

}
