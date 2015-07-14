/*
 * DICustomizer.java
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

package pt.utl.ist.elab.client.vdi;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.DrawingPanel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class DICustomizer  extends AbstractCustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3505735736072304856L;

	private final VariablePanel r1i, r1e, r2i, r2e, m1, m2, inc, tbs, nSamples;

	private final JButton buttonOK, buttonCancel, buttonReset;

	private final JPanel panelBotoes, panelTopo, panelBaixo;

	private final DrawingPanel panelDisco1, panelDisco2;
	private final DrawingPanel panelPlano;
	private DrawableShape d1i, d1e, d2i, d2e;
	private Dataset plano;
	private TitledBorder title;

	public DICustomizer() {
		r1i = new VariablePanel(0, 0.15, 0.15, 3, 3, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.r1i", "R Interno 1 (m)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.r1i", "Raio interno do disco 1"));

		r1i.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		r1e = new VariablePanel(0.15, 0.30, 0.15, 3, 3, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.r1e", "R Externo 1 (m)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.r1e", "Raio externo do disco 1"));

		r1e.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		r2i = new VariablePanel(0, 0.15, 0, 3, 3, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.r2i", "R Interno 2 (m)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.r2i", "Raio interno do disco 2"));

		r2i.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		r2e = new VariablePanel(0.15, 0.30, 0.15, 3, 3, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.r2e", "R Externo 2 (m)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.r2e", "Raio externo do disco 2"));

		r2e.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		m1 = new VariablePanel(0.1, 1.0, 0.5, 3, 2, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.m1", "Massa 1 (kg)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.m1", "Massa do disco 1"));

		m2 = new VariablePanel(0.1, 1.0, 0.5, 3, 2, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.m2", "Massa 2 (kg)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.m2", "Massa do disco 2"));

		inc = new VariablePanel(5, 35, 20, 1, 5, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.inc", "Inclinacao (graus)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.inc", "Inclinacao do plano"));

		inc.addExecutor(new VariableExecutor() {
			@Override
			public void execute() {
				actualizar();
			}
		});

		tbs = new VariablePanel(20, 50, 30, 0, 5, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.tbs", "dt (ms)"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.tbs", "Tempo entre Amostras"));

		nSamples = new VariablePanel(10, 150, 150, 0, 2, ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.title.nsamps", "Numero de Amostras"), ReCResourceBundle.findStringOrDefault(
				"di$rec.exp.customizer.tip.nsamps", "Numero de Amostras"));

		buttonOK = new JButton(ReCResourceBundle.findStringOrDefault("di$rec.exp.customizer.title.ok", "Correr"));
		buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				// OK o utilizador quer enviar as informacoes, vamos colocar os
				// valores nos canais!!!
				getAcquisitionConfig().setTotalSamples((int) nSamples.getCurrentValue());

				getAcquisitionConfig().setSelectedFrequency(new Frequency(tbs.getCurrentValue(), getHardwareInfo()
						.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), getHardwareInfo()
						.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
				getAcquisitionConfig().getSelectedHardwareParameter("r1i").setParameterValue("" + r1i.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("r1e").setParameterValue("" + r1e.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("r2i").setParameterValue("" + r2i.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("r2e").setParameterValue("" + r2e.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("m1").setParameterValue("" + m1.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("m2").setParameterValue("" + m2.getCurrentValue());
				getAcquisitionConfig().getSelectedHardwareParameter("inc").setParameterValue("" + inc.getCurrentValue());
				fireICustomizerListenerDone();
			}
		});

		buttonCancel = new JButton(ReCResourceBundle.findStringOrDefault("di$rec.exp.customizer.title.cancel",
				"Cancelar"));
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				// Sempre igual
				fireICustomizerListenerCanceled();
			}
		});

		buttonReset = new JButton(ReCResourceBundle.findStringOrDefault("di$rec.exp.customizer.title.dfc", "Restaurar"));
		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				r1i.reset();
				r1e.reset();
				r2i.reset();
				r2e.reset();
				m1.reset();
				m2.reset();
				inc.reset();
				nSamples.reset();
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
		panelBaixo.add(r1i);
		panelBaixo.add(r1e);
		panelBaixo.add(r2i);
		panelBaixo.add(r2e);
		panelBaixo.add(m1);
		panelBaixo.add(m2);
		panelBaixo.add(inc);
		panelBaixo.add(nSamples);
		panelBaixo.add(tbs);
		panelBaixo.add(panelBotoes);

		d1i = DrawableShape.createCircle(0, 0, 2 * 0.15);
		d1e = DrawableShape.createCircle(0, 0, 2 * 0.15);
		d2i = DrawableShape.createCircle(0, 0, 2 * 0);
		d2e = DrawableShape.createCircle(0, 0, 2 * 0.15);

		d1i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
		d1e.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
		d2i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
		d2e.setMarkerColor(java.awt.Color.red, java.awt.Color.black);

		plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
		plano.append(-5, -5 * Math.tan(-Math.toRadians(20)));
		plano.append(5, 5 * Math.tan(-Math.toRadians(20)));

		panelDisco1 = new DrawingPanel();

		title = BorderFactory.createTitledBorder("1");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitlePosition(TitledBorder.TOP);
		panelDisco1.setBorder(title);

		panelDisco1.setPreferredSize(new java.awt.Dimension(110, 110));
		panelDisco1.setPreferredMinMax(-.5, .5, -.5, .5);
		panelDisco1.addDrawable(d1e);
		panelDisco1.addDrawable(d1i);

		panelDisco2 = new DrawingPanel();

		panelDisco2.setPreferredSize(new java.awt.Dimension(110, 110));
		title = BorderFactory.createTitledBorder("2");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitlePosition(TitledBorder.TOP);
		panelDisco2.setBorder(title);

		panelDisco2.setPreferredMinMax(-.5, .5, -.5, .5);
		panelDisco2.addDrawable(d2e);
		panelDisco2.addDrawable(d2i);

		panelPlano = new DrawingPanel();
		panelPlano.setPreferredSize(new java.awt.Dimension(110, 110));
		panelPlano.setPreferredMinMax(-4, 4, -4, 4);
		panelPlano.addDrawable(plano);

		panelTopo = new JPanel();
		panelTopo.setPreferredSize(new java.awt.Dimension(330, 130));
		panelTopo.setBackground(java.awt.Color.white);
		panelTopo.setLayout(new FlowLayout());
		panelTopo.add(panelDisco1);
		panelTopo.add(panelDisco2);
		panelTopo.add(panelPlano);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panelTopo);
		this.add(panelBaixo);
	}

	public static void main(final String args[]) {
		final JFrame dummy = new JFrame();
		dummy.getContentPane().add(new DICustomizer());
		dummy.pack();
		dummy.setVisible(true);
	}

	public double arredondar(final double a, final int x) {
		double b, c, d;
		d = Math.pow(10, x);
		b = (a * d);
		c = (Math.rint(b)) / d;
		return c;
	}

	private void actualizar() {
		final double rint1 = r1i.getCurrentValue();

		final double rext1 = r1e.getCurrentValue();

		final double rint2 = r2i.getCurrentValue();

		final double rext2 = r2e.getCurrentValue();

		final double ang = inc.getCurrentValue();

		d1i = DrawableShape.createCircle(0, 0, 2 * rint1);
		d1e = DrawableShape.createCircle(0, 0, 2 * rext1);

		d2i = DrawableShape.createCircle(0, 0, 2 * rint2);
		d2e = DrawableShape.createCircle(0, 0, 2 * rext2);

		d1i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
		d1e.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
		d2i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
		d2e.setMarkerColor(java.awt.Color.red, java.awt.Color.black);

		final double m = Math.tan(Math.toRadians(-ang));
		plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
		plano.append(-5, -5 * m);
		plano.append(5, 5 * m);

		panelPlano.clear();
		panelPlano.addDrawable(plano);

		panelDisco1.clear();
		panelDisco1.addDrawable(d1e);
		panelDisco1.addDrawable(d1i);

		panelDisco2.clear();
		panelDisco2.addDrawable(d2e);
		panelDisco2.addDrawable(d2i);

		panelPlano.repaint();
		panelDisco1.repaint();
		panelDisco2.repaint();
	}

	
	// ESTE E' PARA ALTERAR
	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		// Aqui sao fornecidos parametros do ultimo utilizador que fez a exp, e'
		// bom manter!
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {
			double value;

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r1i")));
			r1i.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r1e")));
			r1e.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r2i")));
			r2i.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r2e")));
			r2e.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("m1")));
			m1.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("m2")));
			m2.setCurrentValue(value);

			value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("inc")));
			inc.setCurrentValue(value);

			nSamples.setCurrentValue(acqConfig.getTotalSamples());

			final int freq = (int) acqConfig.getSelectedFrequency().getFrequency();
			tbs.setCurrentValue(freq);
		}
	}

	

	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/vdi/resources/di_iconified.png"));
	}

	// ESTE E' PARA ALTERAR
	@Override
	public String getCustomizerTitle() {
		return "Inertial Discs Experiment Configuration Utility";
	}

}