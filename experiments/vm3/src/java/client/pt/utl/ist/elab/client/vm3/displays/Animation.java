/*
 * Animation.java
 *
 * Created on 20 de Fevereiro de 2005, 22:10
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 *
 * @author n0dP2
 */

import javax.swing.JFrame;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Animation extends pt.utl.ist.elab.client.vm3.DrawableMolas implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067157229156571315L;

	public Animation() {
		super(null);
	}

	public static void main(final String[] args) {
		final javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.getContentPane().add(new Animation());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}

	// Chegaram novas amostras!
	@Override
	public void newSamples(final NewExpDataEvent evt) {
		// Esta � a maneira cl�ssica de tirar as amostras dos canais que nos
		// interessam!
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 3) != null && model.getValueAt(i, 4) != null) {
				final float x = model.getValueAt(i, 3).getValue().getFloatValue();
				final float y = model.getValueAt(i, 4).getValue().getFloatValue();
				desenha(x, y);
			}
		}
	}

	private void desenha(final float x, final float y) {
		bola.setXY(x, y);
		mola1.setSizeX(x - 5);
		mola1.setSizeY(y - 10);
		mola2.setSizeX(x);
		mola2.setSizeY(y);
		mola3.setSizeX(x - 10);
		mola3.setSizeY(y);
		repaint();
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

	// Queremos fazer alguma coisa quando for dado o start e ainda n�o existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, j� existe o header da
	// experi�ncia!
	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();
		// vamos l� ver o que o utilizador escolheu, para colocar a anima��o nas
		// posi��es iniciais correctas!
		final float x = Float.parseFloat(header.getSelectedHardwareParameterValue("x0"));
		final float y = Float.parseFloat(header.getSelectedHardwareParameterValue("y0"));
		desenha(x, y);
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

	// Este c�digo � SEMPRE igual e tem de existir!
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
