/*
 * LoppingCustomizer.java
 *
 * Created on 5 de Abril de 2005, 0:45
 */

package pt.utl.ist.elab.client.vlooping;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.PlottingPanel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.client.customizer.AbstractCustomizer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Emanuel Antunes
 */
public class LoopingCustomizer extends AbstractCustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7794775730472913782L;

	/** Creates new form LoppingCustomizer */
	public LoopingCustomizer() {

		initComponents();
		initTrajectory();

		java.util.Hashtable<Integer, JLabel> ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 980; i += 200) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		ht.put(Integer.valueOf(980), new javax.swing.JLabel("98"));
		jSliderG.setLabelTable(ht);

		ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 200; i += 50) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderVi.setLabelTable(ht);

		ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 1000; i += 200) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderH1.setLabelTable(ht);
		jSliderH2.setLabelTable(ht);

		ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 500; i += 100) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderR.setLabelTable(ht);

		ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 500; i += 100) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderR.setLabelTable(ht);

		ht = new java.util.Hashtable<Integer, JLabel>(7);
		for (int i = 0; i <= 150; i += 25) {
			ht.put(Integer.valueOf(i), new javax.swing.JLabel("" + i / 10F));
		}
		jSliderXi.setLabelTable(ht);

		jSliderH1.setValue(150);

		updateVars();
		updateTimer();
		paintTracks();
	}

	private void initTrajectory() {

		x = 0;
		y = 0;
		h1 = 7;
		h2 = 6;
		r1 = 4;
		dx = 0.01;

		dpanel = new PlottingPanel("x (m)", "y (m)", ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.graph", "Trajectory"));// new
		// DrawingPanel();
		dpanel.setPreferredSize(new java.awt.Dimension(267, 100));
		panelPreview.add(dpanel);

		dataset = new Dataset(Color.BLACK, Color.BLACK, true);

		dataset.setMarkerShape(Dataset.NO_MARKER);
		dpanel.setSquareAspect(true);

		circle = new Circle(0d, h1, 5);
		dpanel.addDrawable(dataset);
		dpanel.addDrawable(circle);
	}

	public void moveBall() {
		final double xc = jSliderXi.getValue() / 10d;
		double yc = 0;
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
		dpanel.render();
	}

	public void updateVars() {
		h1 = jSliderH1.getValue() / 10f;
		h2 = jSliderH2.getValue() / 10f;
		r1 = jSliderR.getValue() / 10f;
	}

	public void updateTimer() {
		float expDuration = jSliderSamples.getValue() * jSliderTBS.getValue();
		expDuration /= 1000;
		jLabelExpTime.setText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.title.timer",
				"Experiment Duration: ") + expDuration + " s");
	}

	public void paintTracks() {
		x = 0;
		dataset.clear();

		if (Math.max(h1, h2) > Math.max(h1, r1)) {
			dpanel.setPreferredMinMaxY(-1, Math.max(h1, h2) + 5);
		} else {
			if (Math.max(h1, r1) == r1) {
				dpanel.setPreferredMinMaxY(-1, Math.max(h1, r1) * 2 + 5);
			} else {
				dpanel.setPreferredMinMaxY(-1, Math.max(h1, r1) + 5);
			}
		}

		moveBall();

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
			y = 0;
			dataset.append(x, y);
			x += dx;
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
		dpanel.render();
	}

	public static void main(final String args[]) {
		ReCResourceBundle.loadResourceBundle("looping",
				"recresource:///pt/utl/ist/elab/client/vlooping/resources/messages");
		final javax.swing.JFrame dummy = new javax.swing.JFrame();
		dummy.getContentPane().add(new LoopingCustomizer());
		dummy.pack();
		dummy.setVisible(true);
		dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		panelRoot = new javax.swing.JPanel();
		panelRoot2 = new javax.swing.JPanel();
		panelPreview = new javax.swing.JPanel();
		panelTime = new javax.swing.JPanel();
		jPanelTBSNS = new javax.swing.JPanel();
		jPanelTBS = new javax.swing.JPanel();
		jSliderTBS = new javax.swing.JSlider();
		jTextFieldTBS = new javax.swing.JTextField();
		jPanelNS = new javax.swing.JPanel();
		jSliderSamples = new javax.swing.JSlider();
		jTextFieldSamples = new javax.swing.JTextField();
		jLabelExpTime = new javax.swing.JLabel();
		tabVars = new javax.swing.JTabbedPane();
		panelVars1 = new javax.swing.JPanel();
		jPanelG = new javax.swing.JPanel();
		jSliderG = new javax.swing.JSlider();
		jTextFieldG = new javax.swing.JTextField();
		jPanelVi = new javax.swing.JPanel();
		jSliderVi = new javax.swing.JSlider();
		jTextFieldVi = new javax.swing.JTextField();
		jPanelXi = new javax.swing.JPanel();
		jSliderXi = new javax.swing.JSlider();
		jTextFieldXi = new javax.swing.JTextField();
		panelVars2 = new javax.swing.JPanel();
		jPanelH2 = new javax.swing.JPanel();
		jSliderH2 = new javax.swing.JSlider();
		jTextFieldH2 = new javax.swing.JTextField();
		jPanelH1 = new javax.swing.JPanel();
		jSliderH1 = new javax.swing.JSlider();
		jTextFieldH1 = new javax.swing.JTextField();
		jPanelR = new javax.swing.JPanel();
		jSliderR = new javax.swing.JSlider();
		jTextFieldR = new javax.swing.JTextField();
		jPanelBtns = new javax.swing.JPanel();
		jPanelOKCnl = new javax.swing.JPanel();
		jButtonOK = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jPanelDfC = new javax.swing.JPanel();
		jButtonDefaultConfig = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		panelRoot.setLayout(new java.awt.BorderLayout());

		panelRoot2.setLayout(new java.awt.BorderLayout());

		panelPreview.setLayout(new java.awt.BorderLayout());

		panelPreview.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.1", "System Preview")));
		panelRoot2.add(panelPreview, java.awt.BorderLayout.CENTER);

		panelTime.setLayout(new java.awt.BorderLayout());

		jPanelTBSNS.setLayout(new java.awt.GridLayout(1, 0));

		jPanelTBS.setLayout(new java.awt.BorderLayout());

		jPanelTBS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.tbs", "TBS")));
		jPanelTBS.setToolTipText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.tip.tbs",
				"Time between samples"));
		jPanelTBS.setVerifyInputWhenFocusTarget(false);
		jSliderTBS.setPaintLabels(true);
		jSliderTBS.setOrientation(SwingConstants.VERTICAL);
		jSliderTBS.setPaintTicks(true);
		jSliderTBS.setMinorTickSpacing(2);
		jSliderTBS.setMajorTickSpacing(10);
		jSliderTBS.setMinimum(5);
		jSliderTBS.setToolTipText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.tip.tbs",
				"Time between samples"));
		jSliderTBS.setMaximum(55);
		jSliderTBS.setSnapToTicks(true);
		jSliderTBS.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderTBSStateChanged(evt);
			}
		});

		jPanelTBS.add(jSliderTBS, java.awt.BorderLayout.CENTER);

		jTextFieldTBS.setColumns(4);
		jTextFieldTBS.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldTBS.setText("80");
		jTextFieldTBS.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldTBSFocusLost(evt);
			}
		});

		jPanelTBS.add(jTextFieldTBS, java.awt.BorderLayout.SOUTH);

		jPanelTBSNS.add(jPanelTBS);

		jPanelNS.setLayout(new java.awt.BorderLayout());

		jPanelNS.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.samples", "N Samples")));
		jPanelNS.setToolTipText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.tip.samples",
				"Number of samples"));
		jPanelNS.setVerifyInputWhenFocusTarget(false);
		jSliderSamples.setPaintLabels(true);
		jSliderSamples.setOrientation(SwingConstants.VERTICAL);
		jSliderSamples.setPaintTicks(true);
		jSliderSamples.setMinorTickSpacing(20);
		jSliderSamples.setMajorTickSpacing(100);
		jSliderSamples.setValue(100);
		jSliderSamples.setMinimum(10);
		jSliderSamples.setToolTipText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.tip.samples",
				"Number of samples"));
		jSliderSamples.setMaximum(1110);
		jSliderSamples.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderSamplesStateChanged(evt);
			}
		});

		jPanelNS.add(jSliderSamples, java.awt.BorderLayout.CENTER);

		jTextFieldSamples.setColumns(4);
		jTextFieldSamples.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldSamples.setText("100");
		jTextFieldSamples.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldSamplesFocusLost(evt);
			}
		});

		jPanelNS.add(jTextFieldSamples, java.awt.BorderLayout.SOUTH);

		jPanelTBSNS.add(jPanelNS);

		panelTime.add(jPanelTBSNS, java.awt.BorderLayout.CENTER);

		jLabelExpTime.setText("jLabel1");
		panelTime.add(jLabelExpTime, java.awt.BorderLayout.SOUTH);

		panelRoot2.add(panelTime, java.awt.BorderLayout.EAST);

		panelRoot.add(panelRoot2, java.awt.BorderLayout.CENTER);

		panelVars1.setLayout(new java.awt.BorderLayout());

		jPanelG.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.4", "Gravitational acceleration")));
		jSliderG.setPaintLabels(true);
		jSliderG.setPaintTicks(true);
		jSliderG.setMinorTickSpacing(50);
		jSliderG.setMajorTickSpacing(200);
		jSliderG.setValue(98);
		jSliderG.setMaximum(980);
		jSliderG.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderGStateChanged(evt);
			}
		});

		jPanelG.add(jSliderG);

		jTextFieldG.setColumns(4);
		jTextFieldG.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldG.setText("9.8");
		jTextFieldG.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldGFocusLost(evt);
			}
		});

		jPanelG.add(jTextFieldG);

		panelVars1.add(jPanelG, java.awt.BorderLayout.SOUTH);

		jPanelVi.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.3", "Initial velocity")));
		jPanelVi.setEnabled(false);
		jSliderVi.setPaintLabels(true);
		jSliderVi.setPaintTicks(true);
		jSliderVi.setMinorTickSpacing(10);
		jSliderVi.setMajorTickSpacing(50);
		jSliderVi.setValue(0);
		jSliderVi.setMaximum(200);
		jSliderVi.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderViStateChanged(evt);
			}
		});

		jPanelVi.add(jSliderVi);

		jTextFieldVi.setColumns(4);
		jTextFieldVi.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldVi.setText("0");
		jTextFieldVi.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldViFocusLost(evt);
			}
		});

		jPanelVi.add(jTextFieldVi);

		panelVars1.add(jPanelVi, java.awt.BorderLayout.CENTER);

		jPanelXi.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.2", "Initial position")));
		jSliderXi.setPaintLabels(true);
		jSliderXi.setPaintTicks(true);
		jSliderXi.setMinorTickSpacing(5);
		jSliderXi.setMajorTickSpacing(25);
		jSliderXi.setValue(1);
		jSliderXi.setMaximum(150);
		jSliderXi.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderXiStateChanged(evt);
			}
		});

		jPanelXi.add(jSliderXi);

		jTextFieldXi.setColumns(4);
		jTextFieldXi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldXi.setText("0.1");
		jTextFieldXi.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldXiFocusLost(evt);
			}
		});

		jPanelXi.add(jTextFieldXi);

		panelVars1.add(jPanelXi, java.awt.BorderLayout.NORTH);

		tabVars.addTab(
				ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.title.8", "Particle Settings"),
				panelVars1);

		panelVars2.setLayout(new java.awt.BorderLayout());

		jPanelH2.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.6", "Second Height (m)")));
		jSliderH2.setPaintLabels(true);
		jSliderH2.setPaintTicks(true);
		jSliderH2.setMinorTickSpacing(50);
		jSliderH2.setMajorTickSpacing(200);
		jSliderH2.setValue(100);
		jSliderH2.setMaximum(1000);
		jSliderH2.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderH2StateChanged(evt);
			}
		});

		jPanelH2.add(jSliderH2);

		jTextFieldH2.setColumns(4);
		jTextFieldH2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldH2.setText("10");
		jTextFieldH2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldH2FocusLost(evt);
			}
		});

		jPanelH2.add(jTextFieldH2);

		panelVars2.add(jPanelH2, java.awt.BorderLayout.CENTER);

		jPanelH1.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.5", "First Height (m)")));
		jSliderH1.setPaintLabels(true);
		jSliderH1.setPaintTicks(true);
		jSliderH1.setMinorTickSpacing(50);
		jSliderH1.setMajorTickSpacing(200);
		jSliderH1.setValue(150);
		jSliderH1.setMaximum(1000);
		jSliderH1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderH1StateChanged(evt);
			}
		});

		jPanelH1.add(jSliderH1);

		jTextFieldH1.setColumns(4);
		jTextFieldH1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldH1.setText("15");
		jTextFieldH1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldH1FocusLost(evt);
			}
		});

		jPanelH1.add(jTextFieldH1);

		panelVars2.add(jPanelH1, java.awt.BorderLayout.NORTH);

		jPanelR.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault(
				"looping$rec.exp.customizer.title.7", "Looping Radius (m)")));
		jSliderR.setPaintLabels(true);
		jSliderR.setPaintTicks(true);
		jSliderR.setMinorTickSpacing(25);
		jSliderR.setMajorTickSpacing(100);
		jSliderR.setValue(40);
		jSliderR.setMinimum(30);
		jSliderR.setMaximum(500);
		jSliderR.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(final javax.swing.event.ChangeEvent evt) {
				jSliderRStateChanged(evt);
			}
		});

		jPanelR.add(jSliderR);

		jTextFieldR.setColumns(4);
		jTextFieldR.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		jTextFieldR.setText("4.0");
		jTextFieldR.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(final java.awt.event.FocusEvent evt) {
				jTextFieldRFocusLost(evt);
			}
		});

		jPanelR.add(jTextFieldR);

		panelVars2.add(jPanelR, java.awt.BorderLayout.SOUTH);

		tabVars.addTab(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.title.9", "Track Settings"),
				panelVars2);

		panelRoot.add(tabVars, java.awt.BorderLayout.SOUTH);

		add(panelRoot, java.awt.BorderLayout.CENTER);

		jPanelBtns.setLayout(new java.awt.GridBagLayout());

		jButtonOK.setText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.title.ok", "OK"));
		jButtonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonOKActionPerformed(evt);
			}
		});

		jPanelOKCnl.add(jButtonOK);

		jButtonCancel.setText(ReCResourceBundle
				.findStringOrDefault("looping$rec.exp.customizer.title.cancel", "Cancel"));
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonCancelActionPerformed(evt);
			}
		});

		jPanelOKCnl.add(jButtonCancel);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		jPanelBtns.add(jPanelOKCnl, gridBagConstraints);

		jButtonDefaultConfig.setText(ReCResourceBundle.findStringOrDefault("looping$rec.exp.customizer.title.dfc",
				"Default Configuration"));
		jButtonDefaultConfig.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				jButtonDefaultConfigActionPerformed(evt);
			}
		});

		jPanelDfC.add(jButtonDefaultConfig);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanelBtns.add(jPanelDfC, gridBagConstraints);

		add(jPanelBtns, java.awt.BorderLayout.SOUTH);

	}// GEN-END:initComponents

	private void jTextFieldXiFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldXiFocusLost
		adjustSlider1(jSliderXi, jTextFieldXi);
	}// GEN-LAST:event_jTextFieldXiFocusLost

	private void jSliderXiStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderXiStateChanged
		jTextFieldXi.setText("" + jSliderXi.getValue() / 10f);
		moveBall();
	}// GEN-LAST:event_jSliderXiStateChanged

	private void jTextFieldSamplesFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldSamplesFocusLost
		adjustSlider(jSliderSamples, jTextFieldSamples);
	}// GEN-LAST:event_jTextFieldSamplesFocusLost

	private void jSliderSamplesStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderSamplesStateChanged
		jTextFieldSamples.setText("" + jSliderSamples.getValue());
		updateTimer();
	}// GEN-LAST:event_jSliderSamplesStateChanged

	private void jTextFieldTBSFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldTBSFocusLost
		adjustSlider(jSliderTBS, jTextFieldTBS);
	}// GEN-LAST:event_jTextFieldTBSFocusLost

	private void jSliderTBSStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderTBSStateChanged
		jTextFieldTBS.setText("" + jSliderTBS.getValue());
		updateTimer();
	}// GEN-LAST:event_jSliderTBSStateChanged

	private void jTextFieldRFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldRFocusLost
		adjustSlider1(jSliderR, jTextFieldR);
	}// GEN-LAST:event_jTextFieldRFocusLost

	private void jSliderRStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderRStateChanged
		jTextFieldR.setText("" + jSliderR.getValue() / 10f);
		updateVars();
		paintTracks();
	}// GEN-LAST:event_jSliderRStateChanged

	private void jTextFieldH2FocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldH2FocusLost
		adjustSlider1(jSliderH2, jTextFieldH2);
	}// GEN-LAST:event_jTextFieldH2FocusLost

	private void jSliderH2StateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderH2StateChanged
		jTextFieldH2.setText("" + jSliderH2.getValue() / 10f);
		updateVars();
		paintTracks();
	}// GEN-LAST:event_jSliderH2StateChanged

	private void jTextFieldH1FocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldH1FocusLost
		adjustSlider1(jSliderH1, jTextFieldH1);
	}// GEN-LAST:event_jTextFieldH1FocusLost

	private void jTextFieldGFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldGFocusLost
		adjustSlider1(jSliderG, jTextFieldG);
	}// GEN-LAST:event_jTextFieldGFocusLost

	private void jSliderGStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderGStateChanged
		jTextFieldG.setText("" + jSliderG.getValue() / 10f);
	}// GEN-LAST:event_jSliderGStateChanged

	private void jTextFieldViFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextFieldViFocusLost
		adjustSlider1(jSliderVi, jTextFieldVi);
	}// GEN-LAST:event_jTextFieldViFocusLost

	private void jSliderViStateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderViStateChanged
		jTextFieldVi.setText("" + jSliderVi.getValue() / 10f);
	}// GEN-LAST:event_jSliderViStateChanged

	private void jSliderH1StateChanged(final javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderH1StateChanged
		jTextFieldH1.setText("" + jSliderH1.getValue() / 10f);
		updateVars();
		paintTracks();
	}// GEN-LAST:event_jSliderH1StateChanged

	private void jButtonDefaultConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDefaultConfigActionPerformed
		jSliderG.setValue(98);
		jSliderXi.setValue(1);
		jSliderVi.setValue(0);
		jSliderH1.setValue(150);
		jSliderH2.setValue(100);
		jSliderR.setValue(40);
	}// GEN-LAST:event_jButtonDefaultConfigActionPerformed

	private void jButtonCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCancelActionPerformed
		fireICustomizerListenerCanceled();
	}// GEN-LAST:event_jButtonCancelActionPerformed

	private void jButtonOKActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonOKActionPerformed
		getAcquisitionConfig().setTotalSamples(jSliderSamples.getValue());

		getAcquisitionConfig().setSelectedFrequency(new Frequency((double) jSliderTBS.getValue(), getHardwareInfo()
				.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(), getHardwareInfo()
				.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));

		getAcquisitionConfig().getSelectedHardwareParameter("xini").setParameterValue("" + jSliderXi.getValue() / 10f);
		getAcquisitionConfig().getSelectedHardwareParameter("vini").setParameterValue("" + jSliderXi.getValue() / 10F);
		getAcquisitionConfig().getSelectedHardwareParameter("h1").setParameterValue("" + jSliderH1.getValue() / 10F);
		getAcquisitionConfig().getSelectedHardwareParameter("h2").setParameterValue("" + jSliderH2.getValue() / 10F);
		getAcquisitionConfig().getSelectedHardwareParameter("r").setParameterValue("" + jSliderR.getValue() / 10F);
		getAcquisitionConfig().getSelectedHardwareParameter("g").setParameterValue("" + jSliderG.getValue() / 10F);

		fireICustomizerListenerDone();
	}// GEN-LAST:event_jButtonOKActionPerformed

	// Metodos que verificam a validade do que foi introduzido na text field

	private void adjustSlider(final javax.swing.JSlider slider, final javax.swing.JTextField field) {
		int num = 0;
		try {
			num = (int) (Float.parseFloat(field.getText().trim()));
		} catch (final NumberFormatException nfe) {
			field.setText("" + slider.getValue());
			return;
		}
		if (num > slider.getMaximum() || num < slider.getMinimum()) {
			field.setText("" + slider.getValue());
		} else {
			slider.setValue(num);
		}
	}

	private void adjustSlider1(final javax.swing.JSlider slider, final javax.swing.JTextField field) {
		int num = 0;
		try {
			num = (int) (Float.parseFloat(field.getText().trim()) * 10);
		} catch (final NumberFormatException nfe) {
			field.setText("" + slider.getValue() / 10f);
			return;
		}
		if (num > slider.getMaximum() || num < slider.getMinimum()) {
			field.setText("" + slider.getValue() / 10f);
		} else {
			slider.setValue(num);
		}
	}

	// ****************************REC********************************************/

	

	// ESTE É PARA ALTERAR
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		// Aqui são fornecidos parametros do ultimo utilizador que fez a exp, e'
		// bom manter!
		super.setHardwareAcquisitionConfig(acqConfig);
		if (acqConfig != null) {

			try {
				final int xini = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("xini")));
				jSliderXi.setValue(xini);
				final int vini = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("vini")));
				jSliderVi.setValue(vini);
				final int h1 = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("h1")));
				jSliderH1.setValue(h1);
				final int h2 = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("h2")));
				jSliderH2.setValue(h2);
				final int g = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("g")));
				jSliderG.setValue(g);
				final int r = (int) (10 * Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("r")));
				jSliderR.setValue(r);

				final int tbs = (int) acqConfig.getSelectedFrequency().getFrequency();
				jSliderTBS.setValue(tbs);
				final int nSamples = acqConfig.getTotalSamples();
				jSliderSamples.setValue(nSamples);
			} catch (final NumberFormatException e) {
				jSliderG.setValue(98);
				jSliderXi.setValue(1);
				jSliderVi.setValue(0);
				jSliderH1.setValue(150);
				jSliderH2.setValue(100);
				jSliderR.setValue(40);
			}
		}
	}


	public javax.swing.ImageIcon getCustomizerIcon() {
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/vlooping/resources/looping_iconified.png"));
	}

	// ESTE É PARA ALTERAR
	public String getCustomizerTitle() {
		return "Looping Experiment Configuration Utility";
	}


	private PlottingPanel dpanel;
	private Dataset dataset;
	private Circle circle;
	private double x, y, h1, h2, r1, dx;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonDefaultConfig;
	private javax.swing.JButton jButtonOK;
	private javax.swing.JLabel jLabelExpTime;
	private javax.swing.JPanel jPanelBtns;
	private javax.swing.JPanel jPanelDfC;
	private javax.swing.JPanel jPanelG;
	private javax.swing.JPanel jPanelH1;
	private javax.swing.JPanel jPanelH2;
	private javax.swing.JPanel jPanelNS;
	private javax.swing.JPanel jPanelOKCnl;
	private javax.swing.JPanel jPanelR;
	private javax.swing.JPanel jPanelTBS;
	private javax.swing.JPanel jPanelTBSNS;
	private javax.swing.JPanel jPanelVi;
	private javax.swing.JPanel jPanelXi;
	private javax.swing.JSlider jSliderG;
	private javax.swing.JSlider jSliderH1;
	private javax.swing.JSlider jSliderH2;
	private javax.swing.JSlider jSliderR;
	private javax.swing.JSlider jSliderSamples;
	private javax.swing.JSlider jSliderTBS;
	private javax.swing.JSlider jSliderVi;
	private javax.swing.JSlider jSliderXi;
	private javax.swing.JTextField jTextFieldG;
	private javax.swing.JTextField jTextFieldH1;
	private javax.swing.JTextField jTextFieldH2;
	private javax.swing.JTextField jTextFieldR;
	private javax.swing.JTextField jTextFieldSamples;
	private javax.swing.JTextField jTextFieldTBS;
	private javax.swing.JTextField jTextFieldVi;
	private javax.swing.JTextField jTextFieldXi;
	private javax.swing.JPanel panelPreview;
	private javax.swing.JPanel panelRoot;
	private javax.swing.JPanel panelRoot2;
	private javax.swing.JPanel panelTime;
	private javax.swing.JPanel panelVars1;
	private javax.swing.JPanel panelVars2;
	private javax.swing.JTabbedPane tabVars;
	// End of variables declaration//GEN-END:variables

}
