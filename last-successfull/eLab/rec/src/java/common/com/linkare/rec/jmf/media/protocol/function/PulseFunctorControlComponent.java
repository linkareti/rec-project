package com.linkare.rec.jmf.media.protocol.function;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PulseFunctorControlComponent extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 9020402813301420731L;

	private PulseFunctorControl pulseFunctorControl;

	public PulseFunctorControlComponent(PulseFunctorControl pulseFunctorControl) {
		this.pulseFunctorControl = pulseFunctorControl;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(new JLabel("Frequency Pulse"));
		final JSlider frequencyRangeSlider = new JSlider(20, 20000, 20);
		this.add(frequencyRangeSlider);

		frequencyRangeSlider.getModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int frequency = frequencyRangeSlider.getValue();
				PulseFunctorControlComponent.this.pulseFunctorControl.setFrequency(frequency);
			}
		});

	}

}
