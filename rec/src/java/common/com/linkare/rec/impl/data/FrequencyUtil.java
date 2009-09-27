/*
 * FrequencyUtil.java
 *
 * Created on 20 de Agosto de 2002, 16:31
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.metadata.FrequencyScale;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.SamplesNumScale;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.impl.utils.MathUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FrequencyUtil {

	/** Creates a new instance of FrequencyUtil */
	private FrequencyUtil() {
	}

	public static long getMaximumExperimentTime(HardwareInfo hInfo) {
		FrequencyScale[] freqs = hInfo.getHardwareFrequencies();
		SamplesNumScale numSamples = hInfo.getSamplingScale();

		double freqs_slower = Double.MAX_VALUE;

		/*
		 * if(freqs[0].getMaximumFrequency().getFrequencyDefType()==FrequencyDefType
		 * .FrequencyType) {
		 * freqs_slower=freqs[0].getMaximumFrequency().getFrequency
		 * ()*freqs[0].getMaximumFrequency().getMultiplier().getExpValue(); }
		 */
		if (freqs[0].getMinimumFrequency().getFrequencyDefType() == FrequencyDefType.FrequencyType) {
			freqs_slower = freqs[0].getMinimumFrequency().getFrequency()
					* freqs[0].getMinimumFrequency().getMultiplier().getExpValue();
		}
		if (freqs[0].getMaximumFrequency().getFrequencyDefType() == FrequencyDefType.SamplingIntervalType) {
			freqs_slower = 1. / (freqs[0].getMaximumFrequency().getFrequency() * freqs[0].getMaximumFrequency()
					.getMultiplier().getExpValue());
		}
		/*
		 * if(freqs[0].getMinimumFrequency().getFrequencyDefType()==FrequencyDefType
		 * .SamplingIntervalType) {
		 * freqs_slower=1./freqs[0].getMinimumFrequency(
		 * ).getFrequency()*freqs[0]
		 * .getMinimumFrequency().getMultiplier().getExpValue(); }
		 */

		for (int i = 0; i < freqs.length; i++) {
			/*
			 * if(freqs[i].getMaximumFrequency().getFrequencyDefType()==FrequencyDefType
			 * .FrequencyType) {
			 * if(freqs_slower<freqs[i].getMaximumFrequency().getFrequency
			 * ()*freqs[i].getMaximumFrequency().getMultiplier().getExpValue())
			 * freqs_slower
			 * =freqs[i].getMaximumFrequency().getFrequency()*freqs[i
			 * ].getMaximumFrequency().getMultiplier().getExpValue(); }
			 */
			if (freqs[i].getMinimumFrequency().getFrequencyDefType() == FrequencyDefType.FrequencyType) {
				if (freqs_slower < freqs[i].getMinimumFrequency().getFrequency()
						* freqs[i].getMinimumFrequency().getMultiplier().getExpValue())
					freqs_slower = freqs[i].getMinimumFrequency().getFrequency()
							* freqs[i].getMinimumFrequency().getMultiplier().getExpValue();
			}
			if (freqs[i].getMaximumFrequency().getFrequencyDefType() == FrequencyDefType.SamplingIntervalType) {
				if (freqs_slower < 1. / (freqs[i].getMaximumFrequency().getFrequency() * freqs[i].getMaximumFrequency()
						.getMultiplier().getExpValue()))
					freqs_slower = 1. / (freqs[i].getMaximumFrequency().getFrequency() * freqs[i].getMaximumFrequency()
							.getMultiplier().getExpValue());
			}
			/*
			 * if(freqs[i].getMinimumFrequency().getFrequencyDefType()==FrequencyDefType
			 * .SamplingIntervalType) {
			 * if(freqs_slower<1./freqs[i].getMinimumFrequency
			 * ().getFrequency()*freqs
			 * [i].getMinimumFrequency().getMultiplier().getExpValue())
			 * freqs_slower
			 * =1./freqs[i].getMinimumFrequency().getFrequency()*freqs
			 * [i].getMinimumFrequency().getMultiplier().getExpValue(); }
			 */
		}

		long msecs = (long) (numSamples.getMaxSamples() * 1000. / freqs_slower);

		return msecs;

	}

	public static boolean isLessThan(Frequency f1, Frequency f2) {
		double related_multiplier = f1.getMultiplier().getExpValue() / f2.getMultiplier().getExpValue();
		return f1.getFrequency() * related_multiplier < f2.getFrequency();
	}

	public static boolean isEqual(Frequency f1, Frequency f2) {
		double related_multiplier = f1.getMultiplier().getExpValue() / f2.getMultiplier().getExpValue();
		return f1.getFrequency() * related_multiplier == f2.getFrequency();
	}

	public static boolean isLessThanOrEqual(Frequency f1, Frequency f2) {
		return (isLessThan(f1, f2) || isEqual(f1, f2));
	}

	public static boolean isMoreThan(Frequency f1, Frequency f2) {
		return (isMoreThanOrEqual(f1, f2) && !isEqual(f1, f2));
	}

	public static boolean isMoreThanOrEqual(Frequency f1, Frequency f2) {
		return !isLessThan(f1, f2);
	}

	public static boolean isBetween(Frequency value, Frequency min, Frequency max) {
		return (isMoreThanOrEqual(value, min) && isLessThanOrEqual(value, max));
	}

	public static boolean isInScale(Frequency value, FrequencyScale scale) {

		return MathUtil.isValueInScale(scale.getMinimumFrequency().getFrequency()
				* scale.getMinimumFrequency().getMultiplier().getExpValue(), scale.getMaximumFrequency().getFrequency()
				* scale.getMaximumFrequency().getMultiplier().getExpValue(), scale.getStepFrequency().getFrequency()
				* scale.getStepFrequency().getMultiplier().getExpValue(), value.getFrequency()
				* value.getMultiplier().getExpValue());
	}

}
