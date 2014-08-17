/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vtiro.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import pt.utl.ist.elab.client.vtiro.Tiro;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author nomead
 */
public class Animation extends Tiro implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3900695490666757933L;

	/** Creates a new instance of Animation */
	public Animation() {
		super();
	}

	protected void config() {
	}

	// // TESTE
	// public void start() {
	// pt.utl.ist.elab.driver.vtiro.TiroDataProducer data = new
	// pt.utl.ist.elab.driver.vtiro.TiroDataProducer(null,
	// 10, 10, 6.7, Math.PI / 4, 9.8);
	// data.start(this);
	// }
	//
	// public static void main(String args[]) {
	// javax.swing.JFrame test = new javax.swing.JFrame();
	// test.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowClosing(java.awt.event.WindowEvent e) {
	// System.exit(0);
	// };
	// });
	// Animation stdim = new Animation();
	// test.getContentPane().add(stdim);
	// test.pack();
	// test.setVisible(true);
	// stdim.start();
	// }

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

		final double w = Float.parseFloat(header.getSelectedHardwareParameterValue("w"));
		final double h = Float.parseFloat(header.getSelectedHardwareParameterValue("h"));
		final double v = Float.parseFloat(header.getSelectedHardwareParameterValue("v"));
		final double theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));

		config(w, h, v, theta);
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
			if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null && model.getValueAt(i, 2) != null
					&& model.getValueAt(i, 3) != null && model.getValueAt(i, 4) != null) {
				move(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue(), model.getValueAt(i, 2).getValue().getFloatValue(), model.getValueAt(i, 3)
						.getValue().getFloatValue(), model.getValueAt(i, 4).getValue().getFloatValue());
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
