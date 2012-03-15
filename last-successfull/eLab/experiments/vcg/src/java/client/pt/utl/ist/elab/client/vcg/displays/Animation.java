/*
 * Animation.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.client.vcg.displays;

/**
 *
 * @author  nomead
 */

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.axes.XAxis;
import org.opensourcephysics.displayejs.InteractiveText;

import pt.utl.ist.elab.client.vcg.BalancaTorcao;
import pt.utl.ist.elab.client.vcg.CGCustomizer;
import pt.utl.ist.elab.client.vcg.PopupMenu;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener, MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4055682951725090833L;
	private BalancaTorcao balanca;
	private final Circle luzCol;
	private final DrawingPanel reg;
	private final PopupMenu regPopMenu;
	private InteractiveText constTextBox;

	/** Creates a new instance of Animation */
	public Animation() {
		setLayout(new java.awt.GridBagLayout());

		java.awt.GridBagConstraints gridBagConstraints;

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = .7;
		gridBagConstraints.weighty = .7;
		add(balanca = new BalancaTorcao(), gridBagConstraints);

		reg = new DrawingPanel();
		reg.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"rec.exp.customizer.balanca.title.14", "Target")));
		reg.setToolTipText(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.14", "Target"));
		reg.addMouseListener(this);
		reg.setMinimumSize(new java.awt.Dimension(150, 100));
		reg.setPreferredSize(new java.awt.Dimension(150, 100));
		reg.setPreferredMinMaxX(0, 10);
		reg.enableInspector(false);

		final XAxis norm = new XAxis("dm");
		reg.addDrawable(norm);

		luzCol = new Circle(5, 0, 10);
		reg.addDrawable(luzCol);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = .7;
		gridBagConstraints.weighty = 0;
		add(reg, gridBagConstraints);

		regPopMenu = new PopupMenu(this);
		regPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.title.1", "Edit"),
				ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.tip.1",
						"Edit the selected object"));
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
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 1) != null) {
				balanca.updateAngle(model.getValueAt(i, 1).getValue().getFloatValue());
				updateRegua();
			}
		}
	}

	public void updateRegua() {
		luzCol.setX(balanca.getLuzX() / 10 + balanca.getReguaSize() / 20);
		reg.setPreferredMinMaxX(0, balanca.getReguaSize() / 10);
		reg.repaint();
	}

	// Queremos fazer alguma coisa quandos os dados acabarem?
	@Override
	public void dataModelEnded() {
		constTextBox.getStyle().setEdgeColor(java.awt.Color.BLUE);
		reg.addDrawable(constTextBox);
		constTextBox.setX(reg.getPreferredXMax() / 2);
		reg.repaint();
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

		// boolean expGType =
		// Boolean.getBoolean(header.getSelectedHardwareParameterValue("expGType"));
		final boolean expGType = header.getSelectedHardwareParameterValue("expGType").trim().equals("1") ? true : false;
		final int angInit = Integer.parseInt(header.getSelectedHardwareParameterValue("angInit"));
		final int l = Integer.parseInt(header.getSelectedHardwareParameterValue("l"));
		final float s0 = Float.parseFloat(header.getSelectedHardwareParameterValue("s0"));
		final float d = Float.parseFloat(header.getSelectedHardwareParameterValue("d"));
		final int targetSize = Integer.parseInt(header.getSelectedHardwareParameterValue("targetSize"));

		constTextBox = new InteractiveText("C : "
				+ CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("c")), 3)
				+ " ; K : "
				+ CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("k")), 3)
				+ " ; G : "
				+ CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("g")), 3));

		if (!expGType) {
			balanca.cMode();
		}

		balanca.config(angInit * Math.PI / 180, l, s0 * 1000, d * 1000, targetSize);
		updateRegua();
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
	public void mouseClicked(final java.awt.event.MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			regPopMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(final java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(final java.awt.event.MouseEvent e) {
	}

	@Override
	public void mousePressed(final java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseReleased(final java.awt.event.MouseEvent e) {
	}

	@Override
	public void actionPerformed(final java.awt.event.ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Edit")) {
			balanca.setReguaSize(PopupMenu.dialog(ReCResourceBundle.findStringOrDefault(
					"cg$rec.exp.customizer.balanca.title.15", "Edit Target Ruler (cm)"), ReCResourceBundle
					.findStringOrDefault("cg$rec.exp.customizer.balanca.tip.15", "Ruler length"), ReCResourceBundle
					.findStringOrDefault("cg$rec.exp.customizer.balanca.tip.16", "Accept the configuration"),
					(int) Math.round(reg.getPreferredXMax() * 10), new int[] { 100, 500, 50, 50 }));
			constTextBox.setX(balanca.getReguaSize() / 20);
			balanca.repaint();
			updateRegua();
		}
	}
}
