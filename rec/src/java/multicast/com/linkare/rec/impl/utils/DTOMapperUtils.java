/* 
 * DTOMapper.java created on Apr 8, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.am.ExperimentResultsManager;
import com.linkare.rec.am.experiment.ChannelAcquisitionConfigDTO;
import com.linkare.rec.am.experiment.ColumnPhysicsValueDTO;
import com.linkare.rec.am.experiment.DataProducerDTO;
import com.linkare.rec.am.experiment.DateTimeDTO;
import com.linkare.rec.am.experiment.FrequencyDTO;
import com.linkare.rec.am.experiment.FrequencyDefTypeEnum;
import com.linkare.rec.am.experiment.HardwareAcquisitionConfigDTO;
import com.linkare.rec.am.experiment.MultiplierEnum;
import com.linkare.rec.am.experiment.ParameterConfigDTO;
import com.linkare.rec.am.experiment.PhysicsValDTO;
import com.linkare.rec.am.experiment.PhysicsValueDTO;
import com.linkare.rec.am.experiment.PhysicsValueTypeEnum;
import com.linkare.rec.am.experiment.SamplesPacketDTO;
import com.linkare.rec.am.experiment.ScaleDTO;
import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;

/**
 * Utils methods to convert form rec model to DTO.
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class DTOMapperUtils {

	private DTOMapperUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Map ReCMultiCastDataProducer to DataProducerDTO
	 * 
	 * 
	 * @param recMultiCastDataProducer
	 * @param username
	 * @return
	 * @throws NotAvailableException
	 * @throws NotAnAvailableSamplesPacketException
	 * 
	 */
	public static DataProducerDTO getDataProducerDTO(final ReCMultiCastDataProducer recMultiCastDataProducer,
			final String username) throws NotAvailableException, NotAnAvailableSamplesPacketException {

		DataProducerDTO result = null;

		if (recMultiCastDataProducer != null) {
			result = new DataProducerDTO();
			result.setAcqHeader(getHardwareAcquisitionConfigDTO(recMultiCastDataProducer.getAcquisitionHeader()));
			result.setDataProducerName(recMultiCastDataProducer.getDataProducerName());
			result.setOid(recMultiCastDataProducer.getOID());
			result.setSamplesPacketMatrix(getSamplesPacketDTO(recMultiCastDataProducer.getSamples(0,
					recMultiCastDataProducer.getMaxPacketNum())));
			result.setUser(username);

		}

		return result;
	}

	/**
	 * @param samples
	 * @return
	 */
	private static List<SamplesPacketDTO> getSamplesPacketDTO(final SamplesPacket[] samples) {
		List<SamplesPacketDTO> result = Collections.emptyList();

		if (samples != null && samples.length > 0) {
			result = new ArrayList<SamplesPacketDTO>(samples.length);
			for (final SamplesPacket samplesPacket : samples) {
				result.add(getSamplesPacketDTO(samplesPacket));
			}
		}

		return result;
	}

	/**
	 * @param samplesPacket
	 * @return
	 */
	private static SamplesPacketDTO getSamplesPacketDTO(final SamplesPacket samplesPacket) {
		SamplesPacketDTO result = null;

		if (samplesPacket != null) {
			result = new SamplesPacketDTO();
			result.setData(getListOfColumnPhysicsValueDTO(samplesPacket.getData()));
			result.setPacketNumber(samplesPacket.getPacketNumber());
			result.setTimeStart(getDateTimeDTO(samplesPacket.getTimeStart()));
			result.setTotalPackets(samplesPacket.getTotalPackets());
		}

		return result;
	}

	/**
	 * @param data
	 * @return
	 */
	private static List<ColumnPhysicsValueDTO> getListOfColumnPhysicsValueDTO(final PhysicsValue[][] data) {
		List<ColumnPhysicsValueDTO> result = Collections.emptyList();

		if (data != null && data.length > 0) {
			result = new ArrayList<ColumnPhysicsValueDTO>(data.length);
			for (final PhysicsValue[] physicsValues : data) {
				result.add(getColumnPhysicsValueDTO(physicsValues));
			}
		}

		return result;
	}

	/**
	 * @param physicsValues
	 * @return
	 */
	private static ColumnPhysicsValueDTO getColumnPhysicsValueDTO(final PhysicsValue[] physicsValues) {
		ColumnPhysicsValueDTO result = null;

		if (physicsValues != null && physicsValues.length > 0) {
			result = new ColumnPhysicsValueDTO();
			result.setColumnValues(getPhysicsValueDTO(physicsValues));
		}

		return result;
	}

	/**
	 * 
	 * @param physicsValues
	 * @return
	 */
	private static List<PhysicsValueDTO> getPhysicsValueDTO(final PhysicsValue[] physicsValues) {
		List<PhysicsValueDTO> result = Collections.emptyList();

		if (physicsValues != null && physicsValues.length > 0) {
			result = new ArrayList<PhysicsValueDTO>(physicsValues.length);
			for (final PhysicsValue physicsValue : physicsValues) {
				result.add(getPhysicsValueDTO(physicsValue));
			}
		}

		return result;
	}

	/**
	 * Map PhysicsValue to PhysicsValueDTO
	 * 
	 * @param physicsValue
	 * @return
	 */
	private static PhysicsValueDTO getPhysicsValueDTO(final PhysicsValue physicsValue) {
		PhysicsValueDTO result = null;

		if (physicsValue != null) {
			result = new PhysicsValueDTO();
			result.setAppliedMultiplier(MultiplierEnum.fromByte(physicsValue.getAppliedMultiplier().getValue()));
			result.setError(getPhysicsValDTO(physicsValue.getError()));
			result.setValue(getPhysicsValDTO(physicsValue.getValue()));
		}

		return result;
	}

	/**
	 * 
	 * Map HardwareAcquisitionConfig to HardwareAcquisitionConfigDTO
	 * 
	 * @param hardwareAcquisitionConfig
	 * @return
	 */
	private static HardwareAcquisitionConfigDTO getHardwareAcquisitionConfigDTO(
			final HardwareAcquisitionConfig hardwareAcquisitionConfig) {
		HardwareAcquisitionConfigDTO result = null;

		if (hardwareAcquisitionConfig != null) {
			result = new HardwareAcquisitionConfigDTO();
			result.setChannelsConfig(getListOfChannelAcquisitionConfigDTOs(hardwareAcquisitionConfig
					.getChannelsConfig()));
			result.setFamiliarName(hardwareAcquisitionConfig.getFamiliarName());
			result.setFrequency(getFrequencyDTO(hardwareAcquisitionConfig.getSelectedFrequency()));
			result.setHardwareParameters(getListOfHardwareParameterConfigDTO(hardwareAcquisitionConfig
					.getSelectedHardwareParameters()));
			result.setTimeStart(getDateTimeDTO(hardwareAcquisitionConfig.getTimeStart()));
			result.setTotalSamples(hardwareAcquisitionConfig.getTotalSamples());
		}

		return result;
	}

	/**
	 * 
	 * Map Frequency to FrequencyDTO
	 * 
	 * @param selectedFrequency
	 * @return
	 */
	private static FrequencyDTO getFrequencyDTO(final Frequency selectedFrequency) {
		FrequencyDTO result = null;

		if (selectedFrequency != null) {
			result = new FrequencyDTO();
			result.setFrequency(selectedFrequency.getFrequency());
			result.setFrequencyDefType(FrequencyDefTypeEnum.fromInt(selectedFrequency.getFrequencyDefType().getValue()));
			result.setAppliedMultiplier(MultiplierEnum.fromByte(selectedFrequency.getMultiplier().getValue()));
		}

		return result;
	}

	/**
	 * 
	 * Map DateTime to DateTimeDTO
	 * 
	 * @param timeStart
	 * @return
	 */
	private static DateTimeDTO getDateTimeDTO(final DateTime timeStart) {
		DateTimeDTO result = null;

		if (timeStart != null) {
			result = new DateTimeDTO();
			result.setDate(getUtilDate(timeStart.getDate()));
			result.setHours(timeStart.getTime().getHours());
			result.setMicros(timeStart.getTime().getMicros());
			result.setMilis(timeStart.getTime().getMilis());
			result.setMinutes(timeStart.getTime().getMinutes());
			result.setNanos(timeStart.getTime().getNanos());
			result.setPicos(timeStart.getTime().getPicos());
			result.setSeconds(timeStart.getTime().getSeconds());
		}

		return result;
	}

	/**
	 * 
	 * Map com.linkare.rec.data.synch.Date to java.util.Date
	 * 
	 * @param date
	 * @return
	 */
	private static Date getUtilDate(final com.linkare.rec.data.synch.Date date) {
		Date result = null;

		if (date != null) {
			Calendar c = Calendar.getInstance();
			// clean time information
			c.clear();

			// set only this fields
			c.set(Calendar.DAY_OF_MONTH, date.getDay());
			c.set(Calendar.MONTH, date.getMonth());
			c.set(Calendar.YEAR, date.getYear());
			result = c.getTime();
		}

		return result;
	}

	/**
	 * Map com.linkare.rec.data.synch.Date to java.util.Date
	 * 
	 * @param selectedHardwareParameters
	 * @return list with ParameterConfigDTO. Never return null
	 */
	private static List<ParameterConfigDTO> getListOfHardwareParameterConfigDTO(
			final ParameterConfig[] selectedHardwareParameters) {
		// avoidind null for collections. this make easy foreach loops on client
		// side(avoid NullPointerException :P). Off course we have a
		// little penalty on serialization.
		List<ParameterConfigDTO> result = Collections.emptyList();

		if (selectedHardwareParameters != null && selectedHardwareParameters.length > 0) {
			result = new ArrayList<ParameterConfigDTO>(selectedHardwareParameters.length);
			for (final ParameterConfig parameterConfig : selectedHardwareParameters) {
				result.add(getParameterConfigDTO(parameterConfig));
			}
		}

		return result;
	}

	/**
	 * Map ParameterConfig to ParameterConfigDTO
	 * 
	 * @param parameterConfig
	 * @return
	 */
	private static ParameterConfigDTO getParameterConfigDTO(final ParameterConfig parameterConfig) {
		ParameterConfigDTO result = null;

		if (parameterConfig != null) {
			result = new ParameterConfigDTO();
			result.setParameterName(parameterConfig.getParameterName());
			result.setParameterValue(parameterConfig.getParameterValue());
		}

		return result;
	}

	/**
	 * @param channelsConfig
	 * @return
	 */
	private static List<ChannelAcquisitionConfigDTO> getListOfChannelAcquisitionConfigDTOs(
			final ChannelAcquisitionConfig[] channelsConfig) {
		List<ChannelAcquisitionConfigDTO> result = Collections.emptyList();

		if (channelsConfig != null && channelsConfig.length > 0) {
			result = new ArrayList<ChannelAcquisitionConfigDTO>(channelsConfig.length);
			for (final ChannelAcquisitionConfig channelAcquisitionConfig : channelsConfig) {
				result.add(getChannelAcquisitionConfigDTO(channelAcquisitionConfig));
			}
		}

		return result;
	}

	/**
	 * @param channelAcquisitionConfig
	 * @return
	 */
	private static ChannelAcquisitionConfigDTO getChannelAcquisitionConfigDTO(
			final ChannelAcquisitionConfig channelAcquisitionConfig) {
		ChannelAcquisitionConfigDTO result = null;

		if (channelAcquisitionConfig != null) {
			result = new ChannelAcquisitionConfigDTO();
			result.setChannelName(channelAcquisitionConfig.getChannelName());
			result.setChannelParameters(getParameterConfigDTO(channelAcquisitionConfig.getSelectedChannelParameters()));
			result.setFrequency(getFrequencyDTO(channelAcquisitionConfig.getSelectedFrequency()));
			result.setScale(getScaleDTO(channelAcquisitionConfig.getSelectedScale()));
			result.setTimeStart(getDateTimeDTO(channelAcquisitionConfig.getTimeStart()));
			result.setTotalSamples(channelAcquisitionConfig.getTotalSamples());

		}

		return result;
	}

	/**
	 * @param selectedScale
	 * @return
	 */
	private static ScaleDTO getScaleDTO(final Scale selectedScale) {
		ScaleDTO result = null;

		if (selectedScale != null) {
			result = new ScaleDTO();
			result.setMultiplier(MultiplierEnum.fromByte(selectedScale.getMultiplier().getValue()));
			result.setDefaultError(getPhysicsValDTO(selectedScale.getDefaultErrorValue()));
			result.setMaxValue(getPhysicsValDTO(selectedScale.getMaximumValue()));
			result.setMinValue(getPhysicsValDTO(selectedScale.getMinimumValue()));
			result.setPhysicsUnitName(selectedScale.getPhysicsUnitName());
			result.setPhysicsUnitSymbol(selectedScale.getPhysicsUnitSymbol());
			result.setScaleLabel(selectedScale.getScaleLabel());
			result.setStep(getPhysicsValDTO(selectedScale.getStepValue()));
		}

		return result;
	}

	/**
	 * @param defaultErrorValue
	 * @return
	 */
	private static PhysicsValDTO getPhysicsValDTO(PhysicsVal value) {
		PhysicsValDTO result = null;

		if (value != null) {
			result = new PhysicsValDTO();
			result.setValue(value);
			result.setValueType(PhysicsValueTypeEnum.fromInt(value.getDiscriminator().getValue()));
		}

		return result;
	}

	/**
	 * @param selectedChannelParameters
	 * @return
	 */
	private static List<ParameterConfigDTO> getParameterConfigDTO(final ParameterConfig[] selectedChannelParameters) {
		List<ParameterConfigDTO> result = Collections.emptyList();

		if (selectedChannelParameters != null && selectedChannelParameters.length > 0) {
			result = new ArrayList<ParameterConfigDTO>(selectedChannelParameters.length);
			for (final ParameterConfig parameterConfig : selectedChannelParameters) {
				result.add(getParameterConfigDTO(parameterConfig));
			}
		}

		return result;
	}

	// Only for test purposes
	public static void main(String[] args) throws Exception {

		try {

			final File f = new File(
					"/home/elab/ReC7.0/multicast/DataProducers/EXP_ANGULAR_STAMP_V1.0/DataBuffer_Wed_Nov_21_18_28_57_GMT_2007.ser");

			final InitialContext ic = new InitialContext();

			final ExperimentResultsManager ejb = (ExperimentResultsManager) ic
					.lookup("java:global/rec.am/ExperimentResultsManagerBean");

			final List<ReCMultiCastDataProducer> recMultiCastDataProducers = getRecMultiCastDataProducers(f);

			for (final ReCMultiCastDataProducer reCMultiCastDataProducer2 : recMultiCastDataProducers) {
				ejb.mergeExperimentResults(DTOMapperUtils.getDataProducerDTO(reCMultiCastDataProducer2, "username"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<ReCMultiCastDataProducer> getRecMultiCastDataProducers(final File f) throws Exception {

		try {

			List<ReCMultiCastDataProducer> result = Collections.emptyList();
			if (f.isFile()) {
				result = new ArrayList<ReCMultiCastDataProducer>(1);
				result.add(getRecMultiCastDataProducer(f));
			} else if (f.isDirectory()) {
				File[] listFiles = f.listFiles();
				result = new ArrayList<ReCMultiCastDataProducer>(listFiles.length);
				for (final File file : listFiles) {
					if (file.isFile() && file.getName().endsWith(".ser")) {
						result.add(getRecMultiCastDataProducer(file));
					}
				}
			}
			return result;

		} catch (Exception e) {
			throw e;
		}
	}

	private static ReCMultiCastDataProducer getRecMultiCastDataProducer(final File f) throws FileNotFoundException,
			IOException, ClassNotFoundException {

		if (!f.isFile()) {
			throw new IllegalArgumentException("invalid file.");
		}

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(f));
			return (ReCMultiCastDataProducer) objectInputStream.readObject();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

}
