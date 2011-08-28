/*
 * Animation.java
 *
 * Created on October 16, 2004, 8:48 PM
 */

package pt.utl.ist.elab.client.vlooping.displays;

/**
 *
 * @author  Emanuel A.
 */

import java.awt.Color;

import javax.swing.JPanel;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.PlottingPanel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992144087813134429L;
	private final PlottingPanel panel = new PlottingPanel("X (m)", "Y (m)", ReCResourceBundle.findStringOrDefault(
			"looping$rec.exp.customizer.title.graph", "Trajectory"));
	private Dataset dataset = null;
	private Circle circle = null;
	private double xini, h1, h2, r1, xc, yc;

	/** Creates a new instance of Animation */
	public Animation() {

		setLayout(new java.awt.BorderLayout());
		this.add(panel);

		ReCResourceBundle.loadResourceBundle("looping",
				"recresource:///pt/utl/ist/elab/client/vlooping/resources/messages");

		dataset = new Dataset(Color.BLACK, Color.BLUE, true);
		dataset.setMarkerShape(Dataset.NO_MARKER);

		panel.setSquareAspect(true);

		circle = new Circle(0d, 0d, 5);
		panel.addDrawable(dataset);
		panel.addDrawable(circle);
	}

	public void moveBall(final double xc_, final double yc_) {

		yc = yc_;
		xc = xc_;

		if (xc < Math.PI) {
			yc = h1 * 0.5 * (Math.cos(xc) + 1);
		} else if (xc < 3 * Math.PI) {
			yc = h2 * 0.5 * (Math.cos(xc) + 1);
		} else if (xc < 3 * Math.PI + r1) {
			yc = 0;
		} else if (xc < 3 * Math.PI + 3 * r1) {
			final double a = xc - (3 * Math.PI + r1);
			yc = r1 - Math.sqrt(r1 * r1 - a * a);
		} else if (xc > 3 * Math.PI) {
			final double a = xc - (3 * Math.PI + r1);
			yc = r1 + Math.sqrt(r1 * r1 - a * a);
		} else if (xc < 3 * Math.PI + r1) {
			final double a = xc - (3 * Math.PI + r1);
			yc = r1 - Math.sqrt(r1 * r1 - a * a);
		} else if (xc < 3 * Math.PI + 3 * r1) {
			yc = 0;
		}

		circle.setXY(xc, yc);
		panel.render();
	}

	public void paintTracks(final double h1, final double h2, final double r1) {

		double x = 0;
		double y = 0;
		final double dx = 0.01;
		dataset.clear();

		if (Math.max(h1, h2) > Math.max(h1, r1)) {
			panel.setPreferredMinMaxY(-1, Math.max(h1, h2) + 2);
		} else {
			if (Math.max(h1, r1) == r1) {
				panel.setPreferredMinMaxY(-1, Math.max(h1, r1) * 2 + 2);
			} else {
				panel.setPreferredMinMaxY(-1, Math.max(h1, r1) + 2);
			}
		}

		while (x < Math.PI) {
			y = h1 * 0.5 * (Math.cos(x) + 1);
			dataset.append(x, y);
			x += dx;
		}
		while (x < 3 * Math.PI) {
			y = h2 * 0.5 * (Math.cos(x) + 1);
			dataset.append(x, y);
			x += dx;
		}
		while (x < 3 * Math.PI + r1) {
			x += dx;
			y = 0;
			dataset.append(x, y);
		}
		while (x < 3 * Math.PI + 3 * r1) {
			final double a = x - (3 * Math.PI + r1);
			dataset.append(x, r1 - Math.sqrt(r1 * r1 - a * a));
			x += dx;
		}
		while (x > 3 * Math.PI) {
			final double a = x - (3 * Math.PI + r1);
			dataset.append(x, r1 + Math.sqrt(r1 * r1 - a * a));
			x -= dx;
		}
		while (x < 3 * Math.PI + r1) {
			final double a = x - (3 * Math.PI + r1);
			dataset.append(x, r1 - Math.sqrt(r1 * r1 - a * a));
			x += dx;
		}
		while (x < 3 * Math.PI + 3 * r1) {
			y = 0;
			dataset.append(x, y);
			x += dx;
		}
		panel.render();
	}

	public static void main(final String args[]) {
		ReCResourceBundle.loadResourceBundle("looping",
				"recresource:///pt/utl/ist/elab/client/vlooping/resources/messages");
		final javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new Animation());
		dummy.pack();
		dummy.setVisible(true);
		dummy.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}

	// Chegaram novas amostras!
	@Override
	public void newSamples(final NewExpDataEvent evt) {
		// Esta é a maneira clássica de tirar as amostras dos canais que nos
		// interessam!
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 1) != null) {

				moveBall(model.getValueAt(i, 1).getValue().getFloatValue(), model.getValueAt(i, 2).getValue()
						.getFloatValue());

				panel.render();
			}
		}
	}

	// Queremos fazer alguma coisa quandos os dados acabarem?
	@Override
	public void dataModelEnded() {
	}

	// Queremos fazer alguma coisa quandos acontecer um erro?
	@Override
	public void dataModelError() {
	}

	// Queremos fazer alguma coisa quando for dado o start e existirem dados?
	@Override
	public void dataModelStarted() {
	}

	// Queremos fazer alguma coisa quando for dado o start e ainda não existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, já existe o header da
	// experiência!
	@Override
	public void dataModelStartedNoData() {

		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();
		// vamos lá ver o que o utilizador escolheu, para colocar a animação nas
		// posições iniciais correctas!
		xini = Float.parseFloat(header.getSelectedHardwareParameterValue("xini"));
		h1 = Float.parseFloat(header.getSelectedHardwareParameterValue("h1"));
		h2 = Float.parseFloat(header.getSelectedHardwareParameterValue("h2"));
		r1 = Float.parseFloat(header.getSelectedHardwareParameterValue("r"));
		paintTracks(h1, h2, r1);
		// circle.setXY(xini, h1);
		moveBall(xini, 0);
	}

	// Queremos fazer alguma coisa quando for dado parado?
	@Override
	public void dataModelStoped() {
	}

	// Queremos fazer alguma coisa em estado de espera?
	@Override
	public void dataModelWaiting() {
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	// O icon associado a este painel!
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

	// Este código é SEMPRE igual e tem de existir!
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
