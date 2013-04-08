/*
 * BSCustomizer.java
 *
 * Created on April 2, 2005, 19:59 PM
 */

/**
 *
 * @author  Pedro Queiro'
 *
 *  1- Desenhar o GUI! Nao esquecer de ir logo internacionalizando...
 *
 *
 */

package pt.utl.ist.elab.client.vbs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.DrawingPanel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;


public class BSCustomizer  extends AbstractCustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6498867012261009804L;

	private final VariablePanel f1_Iini, f1_Ifin, f2_Iini, f2_Ifin, distFios, numSamples, xPto, yPto, tbs;

	private final JButton buttonOK, buttonCancel, buttonReset;

	private final JPanel panelBotoes, panelTopo, panelBaixo;
	private final DrawingPanel drawingPanel;
	private final DrawableShape f1, f2, pto;

	public BSCustomizer() {
		f1_Iini = new VariablePanel(-1, 1, 0.6, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.i1ini", "I Inicial Fio 1 (A)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.i1ini", "Intensidade de corrente inicial do fio 1"));

		f1_Ifin = new VariablePanel(-1, 1, -0.2, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.i1fin", "I Final Fio 1 (A)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.i1fin", "Intensidade de corrente final do fio 1"));

		f2_Iini = new VariablePanel(-1, 1, -0.2, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.i2ini", "I Inicial Fio 2 (A)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.i2ini", "Intensidade de corrente inicial do fio 2"));

		f2_Ifin = new VariablePanel(-1, 1, 0.6, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.i2fin", "I Final Fio 2 (A)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.i2fin", "Intensidade de corrente final do fio 2"));

		distFios = new VariablePanel(0.01, 0.25, 0.1, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.dist", "Distancia (m)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.dist", "Distancia entre os dois fios"));

		distFios.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		numSamples = new VariablePanel(10, 500, 250, 0, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.samples", "Numero de Amostras"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.samples", "Numero de amostras"));

		xPto = new VariablePanel(-0.15, 0.15, 0, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.xpto", "X Ponto (m)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.xpto", "Coordenada X do ponto a observar"));

		xPto.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		yPto = new VariablePanel(-0.15, 0.15, 0, 3, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.ypto", "Y Ponto (m)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.ypto", "Coordenada Y do ponto a observar"));

		yPto.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		tbs = new VariablePanel(100, 500, 300, 0, 2, ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.title.tbs", "dt (ms)"), ReCResourceBundle.findStringOrDefault(
				"bs$rec.exp.customizer.tip.tbs", "Tempo entre Amostras"));

		buttonOK = new JButton(ReCResourceBundle.findStringOrDefault("bs$rec.exp.customizer.title.ok", "Correr"));
		buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				// OK o utilizador quer enviar as informacoes, vamos colocar os
				// valores nos canais!!!
				getAcquisitionConfig().setTotalSamples((int) numSamples.getCurrentValue());

				getAcquisitionConfig().setSelectedFrequency(new Frequency(tbs.getCurrentValue(), getHardwareInfo()
						.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), getHardwareInfo()
						.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
				getAcquisitionConfig().getSelectedHardwareParameter("i1_ini").setParameterValue("" + f1_Iini.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("i1_fin").setParameterValue("" + f1_Ifin.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("i2_ini").setParameterValue("" + f2_Iini.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("i2_fin").setParameterValue("" + f2_Ifin.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("dist").setParameterValue("" + distFios.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("xpto").setParameterValue("" + xPto.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("ypto").setParameterValue("" + yPto.getCurrentValue());
				fireICustomizerListenerDone();
			}
		});

		buttonCancel = new JButton(ReCResourceBundle.findStringOrDefault("bs$rec.exp.customizer.title.cancel",
				"Cancelar"));
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				// Sempre igual
				fireICustomizerListenerCanceled();
			}
		});

		buttonReset = new JButton(ReCResourceBundle.findStringOrDefault("bs$rec.exp.customizer.title.dfc", "Restaurar"));
		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				f1_Iini.reset();
				f1_Ifin.reset();
				f2_Iini.reset();
				f2_Ifin.reset();
				distFios.reset();
				numSamples.reset();
				xPto.reset();
				yPto.reset();
				tbs.reset();
			}
		});

		panelBotoes = new JPanel();
		panelBotoes.setLayout(new GridLayout(1, 0));
		panelBotoes.add(buttonOK);
		panelBotoes.add(buttonReset);
		panelBotoes.add(buttonCancel);

		panelBaixo = new JPanel();
		panelBaixo.setLayout(new GridLayout(0, 2));
		panelBaixo.add(f1_Iini);
		panelBaixo.add(f1_Ifin);
		panelBaixo.add(f2_Iini);
		panelBaixo.add(f2_Ifin);
		panelBaixo.add(distFios);
		panelBaixo.add(numSamples);
		panelBaixo.add(xPto);
		panelBaixo.add(yPto);
		panelBaixo.add(tbs);
		panelBaixo.add(panelBotoes);

		panelTopo = new JPanel();
		panelTopo.setLayout(new FlowLayout());

		drawingPanel = new DrawingPanel();
		drawingPanel.setPreferredMinMax(-0.3, 0.3, -0.3, 0.3);
		drawingPanel.setPreferredSize(new java.awt.Dimension(150, 150));

		f1 = DrawableShape.createCircle(-0.05, 0, 0.01);
		f2 = DrawableShape.createCircle(0.05, 0, 0.01);
		pto = DrawableShape.createCircle(0, 0, 0.01);

		f1.setMarkerColor(java.awt.Color.gray, java.awt.Color.gray);
		f2.setMarkerColor(java.awt.Color.gray, java.awt.Color.gray);
		pto.setMarkerColor(java.awt.Color.black, java.awt.Color.black);

		drawingPanel.addDrawable(f1);
		drawingPanel.addDrawable(f2);
		drawingPanel.addDrawable(pto);

		panelTopo.add(drawingPanel);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panelTopo);
		this.add(panelBaixo);
	}

	public void actualizar() {
		drawingPanel.clear();

		f1.setX(-distFios.getCurrentValue() / 2.0);
		f2.setX(distFios.getCurrentValue() / 2.0);
		pto.setXY(xPto.getCurrentValue(), yPto.getCurrentValue());

		drawingPanel.addDrawable(f1);
		drawingPanel.addDrawable(f2);
		drawingPanel.addDrawable(pto);

		drawingPanel.repaint();
	}

	public static void main(final String args[]) {
		final JFrame dummy = new JFrame();
		dummy.getContentPane().add(new BSCustomizer());
		dummy.pack();
		dummy.setVisible(true);
	}

	

	
	// ESTE E' PARA ALTERAR
	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		// Aqui sao fornecidos parametros do ultimo utilizador que fez a exp, e'
		// bom manter!
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			double value;

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i1_ini")));
			f1_Iini.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i1_fin")));
			f1_Ifin.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i2_ini")));
			f2_Iini.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i2_fin")));
			f2_Ifin.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("dist")));
			distFios.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("xpto")));
			xPto.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("ypto")));
			yPto.setCurrentValue(value);

			numSamples.setCurrentValue(acqConfig.getTotalSamples());

			final int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			tbs.setCurrentValue(freq);
		}
	}

	
	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		final java.net.URL url = getClass().getResource("/pt/utl/ist/elab/client/vbs/resources/bs_iconified.png");
		if (url != null) {
			return new javax.swing.ImageIcon(url);
		}

		return null;
	}

	// ESTE E' PARA ALTERAR
	@Override
	public String getCustomizerTitle() {
		return "Magnetic Field Experiment Configuration Utility";
	}

}