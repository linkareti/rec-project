package com.linkare.rec.jmf.media.protocol.function;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SineFunctorControlComponent extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 9020402813301420731L;
    
    private SineFunctorControl sineFunctorControl;

    public SineFunctorControlComponent(SineFunctorControl sineFunctorControl) {
	this.sineFunctorControl = sineFunctorControl;
	this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	
	this.add(new JLabel("Frequency"));
	final JSlider frequencyRangeSlider = new JSlider(20, 20000, (int) sineFunctorControl.getFrequency());
	this.add(frequencyRangeSlider);
	
	frequencyRangeSlider.getModel().addChangeListener(new ChangeListener() {
	    
	    @Override
	    public void stateChanged(ChangeEvent e) {
		int frequency = frequencyRangeSlider.getValue();
		SineFunctorControlComponent.this.sineFunctorControl.setFrequency(frequency);
	    }
	});
	
	this.add(new JLabel("Phase"));
	final JSlider phaseRangeSlider = new JSlider(0, 360, (int) (sineFunctorControl.getPhase() * 180. / Math.PI));
	this.add(phaseRangeSlider);
	
	phaseRangeSlider.getModel().addChangeListener(new ChangeListener() {
	    
	    @Override
	    public void stateChanged(ChangeEvent e) {
		int phase = frequencyRangeSlider.getValue();
		SineFunctorControlComponent.this.sineFunctorControl.setPhase(((double)phase)*Math.PI/180.);
		
	    }
	});
    }

}
