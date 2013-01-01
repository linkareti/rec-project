package com.linkare.rec.jmf.media.protocol.function;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TriangularFunctorControlComponent extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 9020402813301420731L;

	private TriangularFunctorControl triangularFunctorControl;

	public TriangularFunctorControlComponent(TriangularFunctorControl triangularFunctorControl) {
		this.triangularFunctorControl = triangularFunctorControl;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(new JLabel("Frequency Triangular"));
		final JSlider frequencyRangeSlider = new JSlider(20, 20000, 20);
		frequencyRangeSlider.setValue((int) triangularFunctorControl.getFrequency());
		this.add(frequencyRangeSlider);

		frequencyRangeSlider.getModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int frequency = frequencyRangeSlider.getValue();
				TriangularFunctorControlComponent.this.triangularFunctorControl.setFrequency(frequency);
			}
		});

	}

}
