package pt.utl.ist.elab.client.vbs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class VariablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3695718463130301615L;
	private VariableExecutor executor = null;
	private final JSlider slider;
	private final JFormattedTextField text;
	private static Dimension textDim = new Dimension(30, 10);
	private static Dimension panelDim = new Dimension(180, 70);
	private final TitledBorder title;
	private final double min, max, init;
	private final String pTitle;

	public VariablePanel(final double newMin, final double newMax, final double newInit, final int multiplier,
			final int divs, final String newTitle, final String toolTip) {
		super();

		min = newMin;
		max = newMax;
		init = newInit;
		pTitle = newTitle;

		final int sliderMin = (int) (min * Math.pow(10, multiplier));
		final int sliderMax = (int) (max * Math.pow(10, multiplier));
		final int sliderInit = (int) (init * Math.pow(10, multiplier));

		// Novo conjunto sliders/textfields
		slider = new JSlider(SwingConstants.HORIZONTAL, sliderMin, sliderMax, sliderInit);

		// Hashtables para os sliders
		final Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(Integer.valueOf(sliderMin), new JLabel("" + min));
		labelTable.put(Integer.valueOf((sliderMin + sliderMax) / 2),
				new JLabel("" + arredondar((min + max) / 2.0, multiplier)));
		labelTable.put(Integer.valueOf(sliderMax), new JLabel("" + max));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);

		// Ticks para os sliders
		slider.setMajorTickSpacing((sliderMax - sliderMin) / divs);
		slider.setMinorTickSpacing((sliderMax - sliderMin) / (2 * divs));
		slider.setPaintTicks(true);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final JSlider source = (JSlider) e.getSource();
				final double valor = source.getValue();
				if (!source.getValueIsAdjusting()) { // Se o utilizador acabou
					// de ajustar o valor
					text.setValue(Double.valueOf(valor / (Math.pow(10, multiplier)))); // Actualiza-se
					// o
					// valor
				} else { // Se nao, mostramos apenas o valor no ecran
					if (executor != null) {
						executor.execute();
					}
					text.setText(String.valueOf(valor / (Math.pow(10, multiplier))));
				}
			}
		});

		// Formatadores para as formatted text fields
		final NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "US"));
		format.setMaximumFractionDigits(multiplier);
		format.setMinimumFractionDigits(0);
		final NumberFormatter formatador = new NumberFormatter(format);
		formatador.setMinimum(Double.valueOf(min));
		formatador.setMaximum(Double.valueOf(max));
		text = new JFormattedTextField(formatador);
		text.setValue(Double.valueOf(init));
		text.setColumns(5);
		text.setPreferredSize(VariablePanel.textDim);

		text.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
		text.getActionMap().put("check", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4224759733555026702L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (!text.isEditValid()) { // O texto e' invalido,
					Toolkit.getDefaultToolkit().beep();
					text.selectAll(); // entao avisa-se.
				} else {
					try { // O texto e' valido,
						text.commitEdit(); // entao usa-se.
					} catch (final java.text.ParseException exc) {
					}
				}
			}
		});

		text.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent e) {
				if ("value".equals(e.getPropertyName())) {
					final Number value = (Number) e.getNewValue();
					if (value != null) {
						if (executor != null) {
							executor.execute();
						}
						slider.setValue((int) (value.doubleValue() * (Math.pow(10, multiplier))));
					}
				}
			}
		});

		setToolTipText(toolTip);
		slider.setToolTipText(toolTip);

		title = BorderFactory.createTitledBorder(pTitle);
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitlePosition(TitledBorder.TOP);
		setBorder(title);
		setPreferredSize(VariablePanel.panelDim);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(slider);
		this.add(text);
	}

	public double arredondar(final double a, final int x) {
		double b, c, d;
		d = Math.pow(10, x);
		b = (a * d);
		c = (Math.rint(b)) / d;
		return c;
	}

	public void addExecutor(final VariableExecutor newExecutor) {
		executor = newExecutor;
	}

	public double getCurrentValue() {
		return Double.parseDouble(text.getText());
	}

	public void setCurrentValue(final double newValue) {
		text.setValue(Double.valueOf(newValue));
	}

	public void reset() {
		setCurrentValue(init);
	}
}