/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vfermap.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1602949373232221397L;

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

	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		final Animation ferim = new Animation();
		test.getContentPane().add(ferim);
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
			final float x = Float.parseFloat(header.getSelectedHardwareParameterValue("x"));
			final float xDot = Float.parseFloat(header.getSelectedHardwareParameterValue("xDot"));
			final float psi = Float.parseFloat(header.getSelectedHardwareParameterValue("psi"));
			final float wAmp = Float.parseFloat(header.getSelectedHardwareParameterValue("wAmp"));
			final float d = Float.parseFloat(header.getSelectedHardwareParameterValue("d"));

			config(x, xDot, psi, d, wAmp);
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
				// (x,xWall)
				move(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue());
			} else if (model.getValueAt(i, 2) != null) {
				setVel(model.getValueAt(i, 2).getValue().getFloatValue());
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
