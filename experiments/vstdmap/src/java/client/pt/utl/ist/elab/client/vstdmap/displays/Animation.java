/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vstdmap.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import pt.utl.ist.elab.client.vstdmap.STDMAPAnima;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class Animation extends STDMAPAnima implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2338604711820208802L;

	/** Creates a new instance of Animation */
	public Animation() {
		super();
		// TESTE
		// setTheta(1);
		// setThetaVecVel(3);
		// setForce(5);
		// repaint();
		// new
		// pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null,(float)
		// 1, -5, 1, 1, 5, 1000,10000).startAnima(this);

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

		if (Byte.parseByte(header.getSelectedHardwareParameterValue("simulType")) != 2) {
			setVisible(false);
		} else {
			final float theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
			final float thetaDot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetaDot"));
			final float length = Float.parseFloat(header.getSelectedHardwareParameterValue("length"));
			final float force = Float.parseFloat(header.getSelectedHardwareParameterValue("force"));
			final int forceDt = (int) header.getSelectedFrequency().getFrequency();

			config(length * 10, theta, thetaDot, 0, force, forceDt);
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
			if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null) {
				move(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue());
			} else if (model.getValueAt(i, 0) != null) {
				setTheta(model.getValueAt(i, 0).getValue().getFloatValue());
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
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.ANIMATION;
    }

}
