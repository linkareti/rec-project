/*
 * Animation.java
 *
 * Created on April 03, 2005, 3:45 PM
 */

package pt.utl.ist.elab.client.vbs.displays;

/**
 *
 * @author  Queiro'
 */

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import javax.swing.JPanel;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display2d.GridPointData;
import org.opensourcephysics.display2d.VectorPlot;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8758703409527846537L;
	private final DrawingPanel drawingPanel;
	private DrawableShape f1 = null;
	private DrawableShape f2 = null;
	private DrawableShape pto = null;
	private final VectorPlot campo;
	private final GridPointData data;

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

	public static void main(final String args[]) {
		final javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new Animation());
		dummy.pack();
		dummy.show();
	}

	// Chegaram novas amostras!
	@Override
	public void newSamples(final NewExpDataEvent evt) {
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

	// Queremos fazer alguma coisa quando for dado o start e ainda nao existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, ja' existe o header da
	// experiencia!
	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();
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

	// Este codigo e' SEMPRE igual e tem de existir!
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