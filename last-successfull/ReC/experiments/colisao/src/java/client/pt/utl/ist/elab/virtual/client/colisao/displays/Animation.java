/*
 * Animation.java
 *
 * Created on October 16, 2004, 8:48 PM
 */

package pt.utl.ist.elab.virtual.client.colisao.displays;

/**
 *
 * @author  Emanuel A.
 */

import javax.swing.JPanel;

import org.opensourcephysics.display.Arrow;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DatasetManager;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.PlottingPanel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {

	private PlottingPanel panel = new PlottingPanel("X (m)", "Y (m)", ReCResourceBundle.findStringOrDefault(
			"ReCExpColisao$rec.exp.customizer.title.graph", "Trajectory"));
	private DatasetManager dataset = null;
	Arrow[] vects = new Arrow[2];
	DrawableShape[] circulos = new DrawableShape[2];
	private float m1, m2;// x1, y1, x2, y2, v1x, v1y, v2x, v2y, r1, r2;

	/** Creates a new instance of Animation */
	public Animation() {

		this.setLayout(new java.awt.BorderLayout());
		this.add(panel);

		dataset = new DatasetManager(true, false, true, Dataset.NO_MARKER);

		panel.setSquareAspect(true);

		panel.addDrawable(dataset);
	}

	public static void main(String args[]) {
		ReCResourceBundle.loadResourceBundle("ReCExpColisao",
				"recresource:///pt/utl/ist/elab/virtual/client//resources/ReCExpColisao");
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new Animation());
		dummy.pack();
		dummy.show();
		dummy.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}

	// Chegaram novas amostras!
	public void newSamples(NewExpDataEvent evt) {
		// Esta é a maneira clássica de tirar as amostras dos canais que nos
		// interessam!
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 1) != null) {
				circulos[0].setXY(model.getValueAt(i, 1).getValue().getFloatValue(), model.getValueAt(i, 2).getValue()
						.getFloatValue());
				circulos[1].setXY(model.getValueAt(i, 3).getValue().getFloatValue(), model.getValueAt(i, 4).getValue()
						.getFloatValue());

				vects[0].setXY(model.getValueAt(i, 1).getValue().getFloatValue(), model.getValueAt(i, 2).getValue()
						.getFloatValue());
				vects[1].setXY(model.getValueAt(i, 3).getValue().getFloatValue(), model.getValueAt(i, 4).getValue()
						.getFloatValue());

				vects[0].setXlength(model.getValueAt(i, 5).getValue().getFloatValue() / m1);
				vects[0].setYlength(model.getValueAt(i, 6).getValue().getFloatValue() / m1);
				vects[1].setXlength(model.getValueAt(i, 7).getValue().getFloatValue() / m2);
				vects[1].setYlength(model.getValueAt(i, 8).getValue().getFloatValue() / m2);

				dataset.append(0, model.getValueAt(i, 1).getValue().getFloatValue(), model.getValueAt(i, 2).getValue()
						.getFloatValue());
				dataset.append(1, model.getValueAt(i, 3).getValue().getFloatValue(), model.getValueAt(i, 4).getValue()
						.getFloatValue());

				panel.render();
			}
		}
	}

	// Queremos fazer alguma coisa quandos os dados acabarem?
	public void dataModelEnded() {
	}

	// Queremos fazer alguma coisa quandos acontecer um erro?
	public void dataModelError() {
	}

	// Queremos fazer alguma coisa quando for dado o start e existirem dados?
	public void dataModelStarted() {
	}

	// Queremos fazer alguma coisa quando for dado o start e ainda não existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, já existe o header da
	// experiência!
	public void dataModelStartedNoData() {

		HardwareAcquisitionConfig header = model.getAcquisitionConfig();
		m1 = Float.parseFloat(header.getSelectedHardwareParameterValue("m1"));
		m2 = Float.parseFloat(header.getSelectedHardwareParameterValue("m2"));
		float v1 = Float.parseFloat(header.getSelectedHardwareParameterValue("v1"));
		float r1 = Float.parseFloat(header.getSelectedHardwareParameterValue("r1"));
		float v2 = Float.parseFloat(header.getSelectedHardwareParameterValue("v1"));
		float r2 = Float.parseFloat(header.getSelectedHardwareParameterValue("r1"));
		int a = Integer.parseInt(header.getSelectedHardwareParameterValue("a"));
		int elasticCollision = Integer.parseInt(header.getSelectedHardwareParameterValue("elasticCollision"));
		int tbs = (int) header.getSelectedFrequency().getFrequency();
		int nSamples = header.getTotalSamples();

		double dt = nSamples / 2;
		dt = dt * tbs / 1000;
		double xf = (r1 + r2) * Math.cos(Math.toRadians(a));
		double yf = (r1 + r2) * -Math.sin(Math.toRadians(a));

		circulos[0] = DrawableShape.createCircle(-v1 * dt, 0, 2 * r1);
		circulos[1] = DrawableShape.createCircle(xf - v2 * (-Math.cos(Math.toRadians(a))) * dt, yf - v2
				* (Math.sin(Math.toRadians(a))) * dt, 2 * r2);
		circulos[1].setMarkerColor((new java.awt.Color(0, 0, 255, 90)), (new java.awt.Color(0, 0, 255)));
		vects[0] = new Arrow(-v1 * dt, 0, v1, 0);
		vects[1] = new Arrow(xf - v2 * (-Math.cos(Math.toRadians(a))) * dt, yf - v2 * (Math.sin(Math.toRadians(a)))
				* dt, v2 * (-Math.cos(Math.toRadians(a))), v2 * (Math.sin(Math.toRadians(a))));
		vects[1].setColor(java.awt.Color.BLUE);
		vects[0].setColor(java.awt.Color.RED);
		panel.addDrawable(circulos[0]);
		panel.addDrawable(circulos[1]);
		panel.addDrawable(vects[0]);
		panel.addDrawable(vects[1]);
		double x_ = Math.max(Math.abs(-v1 * dt), Math.abs(xf - v2 * (-Math.cos(Math.toRadians(a))) * dt));
		double y_ = Math.abs(yf - v2 * (Math.sin(Math.toRadians(a))) * dt);
		panel.setPreferredMinMax(-x_, x_, -y_, y_);
		panel.render();
		/*
		 * double dt = 0.01; boolean colisao=false; int count=1; double time=0;
		 * int tbs = 50; int nSamples = 500; double dt_= nSamples/2; dt_ =
		 * dt_*dt;
		 * 
		 * // dt=tbs/1000;
		 * 
		 * double xf =
		 * (esferas[0].raio+esferas[1].raio)*Math.cos(Math.toRadians(a)); double
		 * yf =
		 * (esferas[0].raio+esferas[1].raio)*(-Math.sin(Math.toRadians(a))); //
		 * esferas[1].x = xf - esferas[1].vX * dt_; // esferas[1].y = yf -
		 * esferas[1].vY * dt_;
		 * 
		 * //esferas[0].x = - esferas[0].vX * dt; //esferas[0].y = -
		 * esferas[0].vY * dt; System.out.println("xi1 "+ (- esferas[0].vX *
		 * dt_) +" vx "+esferas[0].vX +"  xi2 "+ (xf - esferas[1].vX * dt_)
		 * +", "+ (yf - esferas[1].vY * dt_) +" v2 "+ esferas[1].vX+", "+
		 * esferas[1].vY+""); esferas[0]= new Esfera( - esferas[0].vX * dt_, -
		 * esferas[0].vY * dt_, esferas[0].vX, esferas[0].vY, esferas[0].raio,
		 * esferas[0].m); esferas[1]= new Esfera(xf - esferas[1].vX * dt_, yf -
		 * esferas[1].vY * dt_, esferas[1].vX, esferas[1].vY, esferas[1].raio,
		 * esferas[1].m); double x_ =
		 * Math.max(Math.abs(esferas[1].x),Math.abs(esferas[0].x)); double y_ =
		 * Math.max(Math.abs(esferas[1].y),Math.abs(esferas[0].y));
		 * //panel.setPreferredMinMax(-esferas[0].x-1, 1+esferas[1].x,
		 * -Math.abs(esferas[1].y)-1 , 1 + Math.abs(esferas[1].y));
		 * panel.setPreferredMinMax(-x_,x_, -y_,y_); circulos[0] =
		 * DrawableShape.createCircle(esferas[0].x, esferas[0].y,
		 * esferas[0].raio*2); circulos[1] =
		 * DrawableShape.createCircle(esferas[1].x, esferas[1].y,
		 * esferas[1].raio*2);
		 * 
		 * circulos[1].setMarkerColor((new java.awt.Color(0,0,255,90)), (new
		 * java.awt.Color(0,0,255))); panel.addDrawable(data);
		 * panel.addDrawable(circulos[0]); panel.addDrawable(circulos[1]);
		 * 
		 * panel.render();
		 */
	}

	/**
	 *Arredonda um double para int de acordo com as casas decimais
	 */
	public static int roundToInt(double number) {
		int rounded = 0;
		if (number % (int) number < 0.5) {
			rounded = (int) number;
		} else if (number % (int) number >= 0.5) {
			rounded = (int) number + 1;
		}
		return rounded;
	}

	// Queremos fazer alguma coisa quando for dado parado?
	public void dataModelStoped() {
	}

	// Queremos fazer alguma coisa em estado de espera?
	public void dataModelWaiting() {
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	// O icon associado a este painel!
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	// Este código é SEMPRE igual e tem de existir!
	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}
}
