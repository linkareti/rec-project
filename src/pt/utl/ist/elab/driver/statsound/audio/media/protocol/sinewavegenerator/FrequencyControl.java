/*
 * FrequencyControl.java
 *
 * Created on 27 de Marco de 2003, 16:39
 */

package pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class FrequencyControl extends javax.swing.JPanel implements javax.media.Control
{
	
	private InterLacedSineWaveStream2 baseStream=null;
	
	public FrequencyControl(InterLacedSineWaveStream2 baseStream)
	{
		this.baseStream=baseStream;
		this.setLayout(new BorderLayout());
		JSlider sliderleft=new JSlider(JSlider.VERTICAL,10,800,(int)baseStream.getWaveLeftFreq());
		sliderleft.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
			{
				stateChangedSliderLeft(evt);
			}
		}
		);
		
		JSlider sliderright=new JSlider(JSlider.VERTICAL,10,800,(int)baseStream.getWaveRightFreq());
		sliderright.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
			{
				stateChangedSliderRight(evt);
			}
		}
		);
		
		sliderleft.setName("Left Channel Sine Wave Frequency");
		sliderright.setName("Right Channel Sine Wave Frequency");
		sliderleft.setPaintLabels(true);
		sliderleft.setPaintTicks(true);
		sliderright.setPaintLabels(true);
		sliderright.setPaintTicks(true);
		
		sliderleft.setMajorTickSpacing((sliderleft.getMaximum()-sliderleft.getMinimum())/10);
		sliderright.setMajorTickSpacing((sliderright.getMaximum()-sliderright.getMinimum())/10);
		
		this.setLayout(new FlowLayout());
		
		this.add(sliderleft);
		this.add(sliderright);
		
	}
	
	public java.awt.Component getControlComponent()
	{
		return this;
	}
	
	public void stateChangedSliderLeft(ChangeEvent evt)
	{
		baseStream.setWaveLeftFreq((double)((JSlider)evt.getSource()).getValue());
	}
	
	public void stateChangedSliderRight(ChangeEvent evt)
	{
		baseStream.setWaveRightFreq((double)((JSlider)evt.getSource()).getValue());
	}
	
}
