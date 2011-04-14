/* 
 * DTOMapper.java created on Apr 8, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.linkare.rec.am.repository.ByteArrayValueDTO;
import com.linkare.rec.am.repository.ChannelAcquisitionConfigDTO;
import com.linkare.rec.am.repository.ColumnPhysicsValueDTO;
import com.linkare.rec.am.repository.DataProducerDTO;
import com.linkare.rec.am.repository.DateTimeDTO;
import com.linkare.rec.am.repository.FrequencyDTO;
import com.linkare.rec.am.repository.FrequencyDefTypeEnum;
import com.linkare.rec.am.repository.HardwareAcquisitionConfigDTO;
import com.linkare.rec.am.repository.MultiplierEnum;
import com.linkare.rec.am.repository.ParameterConfigDTO;
import com.linkare.rec.am.repository.PhysicsValDTO;
import com.linkare.rec.am.repository.PhysicsValueDTO;
import com.linkare.rec.am.repository.PhysicsValueTypeEnum;
import com.linkare.rec.am.repository.SamplesPacketDTO;
import com.linkare.rec.am.repository.ScaleDTO;
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
 * Utils methods to convert from rec model to DTO and DTO to rec model.
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
	 * @throws DTOMappingException
	 * 
	 */
	public static DataProducerDTO mapToDataProducerDTO(final ReCMultiCastDataProducer recMultiCastDataProducer,
			final String username) throws DTOMappingException {

		try {

			DataProducerDTO result = null;

			if (recMultiCastDataProducer != null) {
				result = new DataProducerDTO();
				result.setAcqHeader(getHardwareAcquisitionConfigDTO(recMultiCastDataProducer.getAcquisitionHeader()));
				result.setDataProducerName(recMultiCastDataProducer.getDataProducerName());
				result.setOid(recMultiCastDataProducer.getOID());

				if (recMultiCastDataProducer.getSamplesPacketSource().getLargestNumPacket() == -1) {
					List<SamplesPacketDTO> emptyList = Collections.emptyList();
					result.setSamplesPacketMatrixSerialized(getSamplesPacketAsByteArray(emptyList));
				} else {
					result.setSamplesPacketMatrixSerialized(getSamplesPacketAsByteArray(getSamplesPacketDTO(recMultiCastDataProducer
							.getSamples(0, recMultiCastDataProducer.getSamplesPacketSource().getLargestNumPacket()))));
				}
				result.setUser(username);
			}

			return result;

		} catch (Exception e) {
			throw new DTOMappingException(e);
		}

	}

	private static byte[] getSamplesPacketAsByteArray(final List<SamplesPacketDTO> samplesPacketMatrix)
			throws IOException {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(samplesPacketMatrix);
		objectOutputStream.flush();
		objectOutputStream.close();
		return byteArrayOutputStream.toByteArray();
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
	private static PhysicsValDTO getPhysicsValDTO(PhysicsVal physicsVal) {
		PhysicsValDTO result = null;

		if (physicsVal != null) {
			result = new PhysicsValDTO();
			result.setValueType(PhysicsValueTypeEnum.fromInt(physicsVal.getDiscriminator().getValue()));
			result.setValue(getPhysicsValValue(physicsVal, result.getValueType()));
		}

		return result;
	}

	private static Object getPhysicsValValue(final PhysicsVal physicsVal, final PhysicsValueTypeEnum valueType) {
		Object result = null;

		switch (valueType) {
		case BOOLEAN_VAL:
			result = physicsVal.isBooleanValue();
			break;
		case BYTE_VAL:
			result = physicsVal.getByteValue();
			break;
		case SHORT_VAL:
			result = physicsVal.getShortValue();
			break;
		case INT_VAL:
			result = physicsVal.getIntValue();
			break;
		case LONG_VAL:
			result = physicsVal.getLongValue();
			break;
		case FLOAT_VAL:
			result = physicsVal.getFloatValue();
			break;
		case DOUBLE_VAL:
			result = physicsVal.getDoubleValue();
			break;
		case BYTEARRAY_VAL:
			ByteArrayValueDTO dto = null;
			if (physicsVal.getByteArrayValue() != null) {
				dto = new ByteArrayValueDTO();
				dto.setData(physicsVal.getByteArrayValue().getData());
				dto.setMimeType(physicsVal.getByteArrayValue().getMimeType());
			}
			result = dto;
			break;
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

	/**
	 * @param experimentResultByOID
	 * @return
	 */
	public static ReCMultiCastDataProducer mapToRecMultiCastDataProducer(DataProducerDTO experimentResultByOID) {
		return null;
	}

	// Only for test purposes
	// public static void main(String[] args) throws Exception {
	//
	// final ExperimentResultsManager lookup = lookup();
	// final DataProducerDTO experimentResultByID =
	// lookup.getExperimentResultByID(1L);
	// System.out.println(experimentResultByID);
	// System.out.println("experimentResultByID.getSamplesPacketMatrixSerialized().length: "
	// + experimentResultByID.getSamplesPacketMatrixSerialized().length);
	//
	// final List<SamplesPacketDTO> samples =
	// getSamplesPacket(experimentResultByID.getSamplesPacketMatrixSerialized());
	// System.out.println(samples.size());
	//
	// // testExperimentResultsPersistence();
	//
	// }
	//
	// private static ExperimentResultsManager lookup() throws NamingException {
	// final InitialContext ic = new InitialContext();
	//
	// return (ExperimentResultsManager)
	// ic.lookup("java:global/rec.am/ExperimentResultsManagerBean");
	// }
	//
	// private static void testExperimentResultsPersistence() {
	// try {
	//
	// final File f = new
	// File("/home/elab/ReC7.0/multicast/ELAB_OPTICA_DSPIC_V1.0");
	//
	// final ExperimentResultsManager ejb = lookup();
	//
	// final List<ReCMultiCastDataProducer> recMultiCastDataProducers =
	// getRecMultiCastDataProducers(f);
	//
	// for (final ReCMultiCastDataProducer reCMultiCastDataProducer2 :
	// recMultiCastDataProducers) {
	//
	// final DataProducerDTO dataProducerDTO =
	// DTOMapperUtils.getDataProducerDTO(reCMultiCastDataProducer2,
	// "username");
	// System.out.println("sending dataproducer:  " +
	// reCMultiCastDataProducer2.getOID());
	// System.out
	// .println("samples byte[] leght: " +
	// dataProducerDTO.getSamplesPacketMatrixSerialized().length);
	// System.out.println("dataProducerDTO.getAcqHeader()" +
	// dataProducerDTO.getAcqHeader());
	// try {
	// ejb.persistExperimentResults(dataProducerDTO);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private static List<ReCMultiCastDataProducer>
	// getRecMultiCastDataProducers(final File f) throws Exception {
	//
	// try {
	//
	// List<ReCMultiCastDataProducer> result = Collections.emptyList();
	// if (f.isFile()) {
	// result = new ArrayList<ReCMultiCastDataProducer>(1);
	// result.add(getRecMultiCastDataProducer(f));
	// } else if (f.isDirectory()) {
	// File[] listFiles = f.listFiles();
	// result = new ArrayList<ReCMultiCastDataProducer>(listFiles.length);
	// for (final File file : listFiles) {
	// if (file.isFile() && file.getName().indexOf("Samples") == -1
	// && file.getName().startsWith("DataBuffer")) {
	// result.add(getRecMultiCastDataProducer(file));
	// }
	// }
	// }
	// return result;
	//
	// } catch (Exception e) {
	// throw e;
	// }
	// }
	//
	// private static ReCMultiCastDataProducer getRecMultiCastDataProducer(final
	// File f) throws Exception {
	//
	// if (!f.isFile()) {
	// throw new IllegalArgumentException("invalid file.");
	// }
	//
	// ObjectInputStream objectInputStream = null;
	// try {
	// objectInputStream = new ObjectInputStream(new FileInputStream(f));
	//
	// return (ReCMultiCastDataProducer) objectInputStream.readObject();
	// } catch (Exception e) {
	// System.out.println(f);
	// System.out.println(e);
	// throw e;
	// } finally {
	// try {
	// if (objectInputStream != null) {
	// objectInputStream.close();
	// }
	// } catch (IOException io) {
	// io.printStackTrace();
	// }
	// }
	// }
	//
	// private static List<SamplesPacketDTO> getSamplesPacket(final byte[]
	// samples) throws Exception {
	//
	// ObjectInputStream objectInputStream = null;
	// try {
	// objectInputStream = new ObjectInputStream(new
	// ByteArrayInputStream(samples));
	//
	// return (List<SamplesPacketDTO>) objectInputStream.readObject();
	// } finally {
	// try {
	// if (objectInputStream != null) {
	// objectInputStream.close();
	// }
	// } catch (IOException io) {
	// io.printStackTrace();
	// }
	// }
	// }

}
