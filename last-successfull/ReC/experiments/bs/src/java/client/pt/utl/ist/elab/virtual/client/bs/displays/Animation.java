/*
 * Animation.java
 *
 * Created on April 03, 2005, 3:45 PM
 */

package pt.utl.ist.elab.virtual.client.bs.displays;

/**
 *
 * @author  Queiro'
 */

import javax.swing.JPanel;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display2d.GridPointData;
import org.opensourcephysics.display2d.VectorPlot;

import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {
	private DrawingPanel drawingPanel;
	private DrawableShape f1 = null;
	private DrawableShape f2 = null;
	private DrawableShape pto = null;
	private VectorPlot campo;
	private GridPointData data;

	/** Creates a new instance of Animation */
	public Animation() {
		drawingPanel = new DrawingPanel();
		drawingPanel.setPreferredMinMax(-.35, .35, -.35, .35);
		drawingPanel.setPreferredSize(new java.awt.Dimension(425, 425));
		f1 = DrawableShape.createCircle(-3, 0, 0.005);
		f1.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
		f2 = DrawableShape.createCircle(3, 0, 0.005);
		f2.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
		pto = DrawableShape.createCircle(0, 0, 0.005);
		pto.setMarkerColor(java.awt.Color.gray, java.awt.Color.black);

		data = new GridPointData(10, 10, 3);
		data.setScale(-.30, .30, -.30, .30);
		campo = new VectorPlot(data);

		drawingPanel.addDrawable(f1);
		drawingPanel.addDrawable(f2);
		drawingPanel.addDrawable(pto);
		drawingPanel.addDrawable(campo);
		this.add(drawingPanel);
	}

	public static void main(String args[]) {
		javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new Animation());
		dummy.pack();
		dummy.show();
	}

	// Chegaram novas amostras!
	public void newSamples(NewExpDataEvent evt) {
		// Esta e' a maneira classica de tirar as amostras dos canais que nos
		// interessam!
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 1) != null) {
				byte[] campobytes;
				campobytes = model.getValueAt(i, model.getChannelIndex("data2d")).getValue().getByteArrayValue()
						.getData();
				data.setData((double[][][]) ByteUtil.byteArrayToObject(campobytes));
				repaint();
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

	// Queremos fazer alguma coisa quando for dado o start e ainda nao existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, ja' existe o header da
	// experiencia!
	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();
		// vamos la' ver o que o utilizador escolheu, para colocar a animacao
		// nas posicoes iniciais correctas!
		double value;

		value = (Double.parseDouble(header.getSelectedHardwareParameterValue("dist")));
		f1.setX(-(value * 100 / 2));
		f2.setX((value * 100 / 2));

		value = (Double.parseDouble(header.getSelectedHardwareParameterValue("xpto")));
		pto.setX(value * 100);

		value = (Double.parseDouble(header.getSelectedHardwareParameterValue("ypto")));
		pto.setY(value * 100);
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

	// Este codigo e' SEMPRE igual e tem de existir!
	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);
	}
}