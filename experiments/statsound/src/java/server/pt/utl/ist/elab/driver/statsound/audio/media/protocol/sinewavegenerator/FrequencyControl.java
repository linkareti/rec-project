/*
 * FrequencyControl.java
 *
 * Created on 27 de Marco de 2003, 16:39
 */

package pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FrequencyControl extends javax.swing.JPanel implements javax.media.Control {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3648232040006266998L;
	private InterLacedSineWaveStream2 baseStream = null;

	public FrequencyControl(final InterLacedSineWaveStream2 baseStream) {
		this.baseStream = baseStream;
		setLayout(new BorderLayout());
		final JSlider sliderleft = new JSlider(SwingConstants.VERTICAL, 10, 800, (int) baseStream.getWaveLeftFreq());
		sliderleft.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent evt) {
				stateChangedSliderLeft(evt);
			}
		});

		final JSlider sliderright = new JSlider(SwingConstants.VERTICAL, 10, 800, (int) baseStream.getWaveRightFreq());
		sliderright.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent evt) {
				stateChangedSliderRight(evt);
			}
		});

		sliderleft.setName("Left Channel Sine Wave Frequency");
		sliderright.setName("Right Channel Sine Wave Frequency");
		sliderleft.setPaintLabels(true);
		sliderleft.setPaintTicks(true);
		sliderright.setPaintLabels(true);
		sliderright.setPaintTicks(true);

		sliderleft.setMajorTickSpacing((sliderleft.getMaximum() - sliderleft.getMinimum()) / 10);
		sliderright.setMajorTickSpacing((sliderright.getMaximum() - sliderright.getMinimum()) / 10);

		setLayout(new FlowLayout());

		this.add(sliderleft);
		this.add(sliderright);

	}

	@Override
	public java.awt.Component getControlComponent() {
		return this;
	}

	public void stateChangedSliderLeft(final ChangeEvent evt) {
		baseStream.setWaveLeftFreq(((JSlider) evt.getSource()).getValue());
	}

	public void stateChangedSliderRight(final ChangeEvent evt) {
		baseStream.setWaveRightFreq(((JSlider) evt.getSource()).getValue());
	}

}
