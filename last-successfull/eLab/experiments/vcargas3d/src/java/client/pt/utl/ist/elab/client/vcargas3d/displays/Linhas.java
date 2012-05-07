package pt.utl.ist.elab.client.vcargas3d.displays;

/**
 *
 * @author  n0dP2
 */

import java.util.ArrayList;

import javax.swing.JFrame;

import org.opensourcephysics.displayejs.InteractiveCharge;

import pt.utl.ist.elab.client.vcargas3d.Sistema;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Linhas extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1443999347122115168L;
	java.util.ArrayList<InteractiveCharge> sist;
	Painel painel = new Painel();

	public Linhas() {
		add(painel);
		// org.opensourcephysics.displayejs.InteractiveParticle part = new
		// org.opensourcephysics.displayejs.InteractiveParticle();
		// part.setSizeXYZ(1,1,1);
		// part.setXYZ(1,2,3);
		// painel.addDrawable(part);
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new Linhas());
		dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dummy.pack();
		dummy.setVisible(true);
	}

	// Chegaram novas amostras!
	@Override
	public void newSamples(final NewExpDataEvent evt) {
		painel.clear();
		setCargasHeader();
		addCargas();
		// Esta ? a maneira cl?ssica de tirar as amostras dos canais que nos
		// interessam!
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {

			// sample, canal
			if (model.getValueAt(i, 0) != null) {
				@SuppressWarnings("unchecked")
				final java.util.ArrayList<ArrayList<? extends Object>> linhas = (java.util.ArrayList<ArrayList<? extends Object>>) ByteUtil
						.byteArrayToObject(model.getValueAt(i, 0).getValue().getByteArrayValue().getData());
				toPanelLinhas(painel, linhas);
				painel.repaint();
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

	// Queremos fazer alguma coisa quando for dado o start e ainda n?o existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, j? existe o header da
	// experi?ncia!
	@Override
	public void dataModelStartedNoData() {
		setCargasHeader();
		addCargas();
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

	// Este c?digo ? SEMPRE igual e tem de existir!
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

	private void setCargasHeader() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();
		sist = Sistema.stringToSistema(header.getSelectedHardwareParameterValue("Sistema"));
	}

	private void addCargas() {
		for (int i = 0; i < sist.size(); i++) {
			painel.addDrawable(sist.get(i));
		}
		painel.repaint();
	}

	private void toPanelLinhas(final org.opensourcephysics.displayejs.DrawingPanel3D panel_,
			final java.util.ArrayList<ArrayList<? extends Object>> linhas_) {
		for (int i = 0; i < linhas_.size(); i++) {
			final org.opensourcephysics.displayejs.InteractiveTrace linha_ = new org.opensourcephysics.displayejs.InteractiveTrace();
			final String Q_ = (String) linhas_.get(i).get(0);
			if (Q_ == "neg") {
				linha_.getStyle().setEdgeColor(new java.awt.Color(140, 140, 255));
			}
			if (Q_ == "pos") {
				linha_.getStyle().setEdgeColor(new java.awt.Color(255, 180, 180));
			}
			if (Q_ == "nul") {
				linha_.getStyle().setEdgeColor(java.awt.Color.white);
			}

			for (int j = 1; j < linhas_.get(i).size(); j++) {
				final Float[] pontos_ = (Float[]) (linhas_.get(i).get(j));
				linha_.addPoint(pontos_[0].floatValue(), pontos_[1].floatValue(), pontos_[2].floatValue());
			}

			panel_.addDrawable(linha_);
		}
	}
}
