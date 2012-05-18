/* 
 * DataConfigInternationalization.java created on 25 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.utils;

import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author npadriano
 */
public class DataConfigInternationalization {

	private static final String CHANNEL_NAME = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.name");

	private static final String CHANNEL_START_TIME = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.start.time");

	private static final String CHANNEL_FREQUENCY = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.frequency");

	private static final String CHANNEL_SCALE = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.scale");

	private static final String CHANNEL_TOTAL_SAMPLES = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.total.samples");

	private static final String CHANNEL_TOTAL_SAMPLES_UNDETERMINED = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.total.samples.undetermined");

	private static final String CHANNEL_PARAMETERS = ReCResourceBundle
			.findString("ReCUI$rec.ui.channel.acquisition.config.data.channel.parameters");

	private static final String APPARATUS = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.apparatus");

	private static final String EXPERIENCE_START_TIME = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.experience.start.time");

	private static final String FREQUENCY = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.frequency");

	private static final String TOTAL_SAMPLES = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.total.samples");

	private static final String TOTAL_SAMPLES_UNDETERMINED = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.total.samples.undetermined");

	private static final String PARAMETERS = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.parameters");

	private static final String CHANNELS_CONFIGURATION = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.channels.onfiguration");

	private static final String CHANNEL = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.channel");

	private static final String CONFIGURATION = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.configuration");

	private static final String CONFIGURATION_UNDEFINED = ReCResourceBundle
			.findString("ReCUI$rec.ui.hardware.acquisition.config.configuration.undefined");

	private static final String UNDEFINED_PARAMETER_NAME = ReCResourceBundle
			.findString("ReCUI$rec.ui.parameter.config.undefined.parameter.name");

	private static final String UNDEFINED = ReCResourceBundle
			.findString("ReCUI$rec.ui.parameter.config.undefined");

	private DataConfigInternationalization() {
		// Static class
	}

	public static String toString(final Scale scale) {
		if (scale == null) {
			return String.valueOf(scale);
		}

		return (scale.getScaleLabel() == null ? "" : ReCResourceBundle.findString(scale.getScaleLabel()) + " = ") + "["
				+ scale.getMinimumValue().toEngineeringNotation() + " ; "
				+ scale.getMaximumValue().toEngineeringNotation() + " ; \u0394="
				+ scale.getStepValue().toEngineeringNotation() + " ; \u03B4="
				+ scale.getDefaultErrorValue().toEngineeringNotation() + "] " + scale.getMultiplier().toString()
				+ scale.getPhysicsUnitSymbol() + " - " + ReCResourceBundle.findString(scale.getPhysicsUnitName());
	}

	public static String toString(final ChannelAcquisitionConfig config) {
		if (config == null) {
			return String.valueOf(config);
		}

		final StringBuilder strBufOut = new StringBuilder();
		final String linesep = System.getProperty("line.separator");

		if (config.getChannelName() != null) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_NAME)
					.append(ReCResourceBundle.findString(config.getChannelName())).append(linesep);
		}

		if (config.getTimeStart() != null) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_START_TIME)
					.append(config.getTimeStart().toSimpleString()).append(linesep);
		}

		if (config.getSelectedFrequency() != null) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_FREQUENCY)
					.append(config.getSelectedFrequency()).append(linesep);
		}

		if (config.getSelectedScale() != null) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_SCALE)
					.append(DataConfigInternationalization.toString(config.getSelectedScale())).append(linesep);
		}

		if (config.getTotalSamples() != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_TOTAL_SAMPLES)
					.append(config.getTotalSamples()).append(linesep);
		} else if (config.getTotalSamples() == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_TOTAL_SAMPLES_UNDETERMINED).append(linesep);
		}

		if (config.getSelectedChannelParameters() != null) {
			strBufOut.append("\t" + DataConfigInternationalization.CHANNEL_PARAMETERS).append(linesep);
			for (int i = 0; i < config.getSelectedChannelParameters().length; i++) {
				if (config.getSelectedChannelParameters(i) != null) {
					strBufOut.append("\t\t").append(config.getSelectedChannelParameters(i)).append(linesep);
				}
			}
		}

		return strBufOut.toString();
	}

	public static String toString(final HardwareAcquisitionConfig config) {
		if (config == null) {
			return String.valueOf(config);
		}

		final StringBuilder strBufOut = new StringBuilder();
		final String linesep = System.getProperty("line.separator");

		if (config.getFamiliarName() != null) {
			strBufOut.append(DataConfigInternationalization.APPARATUS).append(config.getFamiliarName()).append(linesep);
		}

		if (config.getTimeStart() != null) {
			strBufOut.append(DataConfigInternationalization.EXPERIENCE_START_TIME)
					.append(config.getTimeStart().toSimpleString()).append(linesep);
		}

		if (config.getSelectedFrequency() != null) {
			strBufOut.append(DataConfigInternationalization.FREQUENCY).append(config.getSelectedFrequency())
					.append(linesep);
		}

		if (config.getTotalSamples() != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append(DataConfigInternationalization.TOTAL_SAMPLES).append(config.getTotalSamples())
					.append(linesep);
		} else if (config.getTotalSamples() == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append(DataConfigInternationalization.TOTAL_SAMPLES_UNDETERMINED).append(linesep);
		}

		if (config.getSelectedHardwareParameters() != null) {
			strBufOut.append(DataConfigInternationalization.PARAMETERS).append(linesep);
			for (int i = 0; i < config.getSelectedHardwareParameters().length; i++) {
				if (config.getSelectedHardwareParameters(i) != null) {
					strBufOut.append("\t").append(config.getSelectedHardwareParameters(i)).append(linesep);
				}
			}
		}

		if (config.getChannelsConfig() != null) {
			strBufOut.append(DataConfigInternationalization.CHANNELS_CONFIGURATION).append(linesep);
			for (int i = 0; i < config.getChannelsConfig().length; i++) {
				if (config.getChannelsConfig(i) != null) {
					strBufOut
							.append("\t" + DataConfigInternationalization.CHANNEL + i
									+ DataConfigInternationalization.CONFIGURATION).append(linesep)
							.append(DataConfigInternationalization.toString(config.getChannelsConfig(i)))
							.append(linesep);
				} else {
					strBufOut.append(
							"\t" + DataConfigInternationalization.CHANNEL + i
									+ DataConfigInternationalization.CONFIGURATION_UNDEFINED).append(linesep);
				}
			}
		}

		return strBufOut.toString();
	}

	public static String toString(final ParameterConfig config) {
		if (config == null) {
			return String.valueOf(config);
		}

		return ""
				+ (config.getParameterName() == null ? DataConfigInternationalization.UNDEFINED_PARAMETER_NAME : config
						.getParameterName())
				+ " : "
				+ (config.getParameterValue() == null ? DataConfigInternationalization.UNDEFINED : config
						.getParameterValue());
	}

}
