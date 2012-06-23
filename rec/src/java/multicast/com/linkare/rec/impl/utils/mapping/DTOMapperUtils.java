/* 
 * DTOMapper.java created on Apr 8, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.RegisteredHardwareDTO;
import com.linkare.rec.am.RepositoryFacade;
import com.linkare.rec.am.repository.ByteArrayValueDTO;
import com.linkare.rec.am.repository.ChannelAcquisitionConfigDTO;
import com.linkare.rec.am.repository.DataProducerDTO;
import com.linkare.rec.am.repository.DataProducerStateEnum;
import com.linkare.rec.am.repository.DateTimeDTO;
import com.linkare.rec.am.repository.FrequencyDTO;
import com.linkare.rec.am.repository.FrequencyDefTypeEnum;
import com.linkare.rec.am.repository.HardwareAcquisitionConfigDTO;
import com.linkare.rec.am.repository.MultiplierEnum;
import com.linkare.rec.am.repository.ParameterConfigDTO;
import com.linkare.rec.am.repository.PhysicsValDTO;
import com.linkare.rec.am.repository.PhysicsValueDTO;
import com.linkare.rec.am.repository.PhysicsValueTypeEnum;
import com.linkare.rec.am.repository.RowPhysicsValueDTO;
import com.linkare.rec.am.repository.SamplesPacketDTO;
import com.linkare.rec.am.repository.ScaleDTO;
import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.acquisition.ByteArrayValue;
import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.data.synch.Time;
import com.linkare.rec.impl.data.SamplesPacketMatrix;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.multicast.repository.IRepository;
import com.linkare.rec.impl.multicast.repository.RepositoryFactory;
import com.linkare.rec.impl.utils.DataCollectorState;
import com.linkare.rec.impl.utils.RegisteredHardwareInfo;

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
	public static DataProducerDTO mapToDataProducerDTO(final ReCMultiCastDataProducer recMultiCastDataProducer)
			throws DTOMappingException {

		try {

			DataProducerDTO result = null;

			if (recMultiCastDataProducer != null) {
				result = new DataProducerDTO();
				result.setAcqHeader(getHardwareAcquisitionConfigDTO(recMultiCastDataProducer.getAcquisitionHeader()));
				result.setDataProducerName(recMultiCastDataProducer.getDataProducerName());
				result.setOid(recMultiCastDataProducer.getOID());
				result.setDataCollectorState(DataProducerStateEnum.fromByte(recMultiCastDataProducer
						.getDataCollectorState().getValue()));

				final int largestNumPacket = recMultiCastDataProducer.getMaxPacketNum();

				if (largestNumPacket == -1) {
					final List<SamplesPacketDTO> emptyList = Collections.emptyList();
					result.setSamplesPacketMatrixSerialized(getSamplesPacketAsByteArray(emptyList));
				} else {
					result.setSamplesPacketMatrixSerialized(getSamplesPacketAsByteArray(getSamplesPacketDTO(recMultiCastDataProducer
							.getSamplesPacketSource().getSamplesPackets(0, largestNumPacket))));
				}
				result.setUser(recMultiCastDataProducer.getUser());
			}

			return result;

		} catch (IOException e) {
			throw new DTOMappingException(e);
		} catch (NotAvailableException e) {
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

	@SuppressWarnings("unchecked")
	private static List<SamplesPacketDTO> getListFromByteArray(final byte[] samples) throws IOException,
			ClassNotFoundException {
		List<SamplesPacketDTO> result = Collections.emptyList();
		if (samples != null) {
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(samples);
			final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			result = (List<SamplesPacketDTO>) objectInputStream.readObject();
			objectInputStream.close();
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
	private static List<RowPhysicsValueDTO> getListOfColumnPhysicsValueDTO(final PhysicsValue[][] data) {
		List<RowPhysicsValueDTO> result = Collections.emptyList();

		if (data != null && data.length > 0) {
			result = new ArrayList<RowPhysicsValueDTO>(data.length);
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
	private static RowPhysicsValueDTO getColumnPhysicsValueDTO(final PhysicsValue[] physicsValues) {
		RowPhysicsValueDTO result = null;

		if (physicsValues != null && physicsValues.length > 0) {
			result = new RowPhysicsValueDTO();
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
			result.setHardwareUniqueID(hardwareAcquisitionConfig.getHardwareUniqueID());
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
	 * @throws DTOMappingException
	 */
	public static ReCMultiCastDataProducer mapToRecMultiCastDataProducer(final DataProducerDTO experimentResultByOID)
			throws DTOMappingException {

		ReCMultiCastDataProducer result = null;
		if (experimentResultByOID != null) {

			List<SamplesPacketDTO> listOfSamples = Collections.emptyList();
			try {
				listOfSamples = getListFromByteArray(experimentResultByOID.getSamplesPacketMatrixSerialized());
			} catch (IOException e) {
				throw new DTOMappingException(e);
			} catch (ClassNotFoundException e) {
				throw new DTOMappingException(e);
			}

			result = new ReCMultiCastDataProducer(getHardwareAcquisitionConfig(experimentResultByOID.getAcqHeader()),
					experimentResultByOID.getDataProducerName(), experimentResultByOID.getOid(),
					new DataCollectorState(experimentResultByOID.getDataCollectorState().getCode()),
					new SamplesPacketMatrix(getSamplesPacket(listOfSamples)), experimentResultByOID.getUser());
		}

		return result;
	}

	private static SamplesPacket[] getSamplesPacket(final List<SamplesPacketDTO> listOfDtos) {
		List<SamplesPacket> result = Collections.emptyList();
		if (listOfDtos != null && !listOfDtos.isEmpty()) {
			result = new ArrayList<SamplesPacket>(listOfDtos.size());
			for (final SamplesPacketDTO samplesPacketDTO : listOfDtos) {
				result.add(getSamplesPacket(samplesPacketDTO));
			}
		}
		return result.toArray(new SamplesPacket[0]);

	}

	/**
	 * @param samplesPacketDTO
	 * @return
	 */
	private static SamplesPacket getSamplesPacket(final SamplesPacketDTO samplesPacketDTO) {
		SamplesPacket result = null;
		if (samplesPacketDTO != null) {
			result = new SamplesPacket();
			result.setData(getPhysicsValues(samplesPacketDTO.getData()));
			result.setPacketNumber(samplesPacketDTO.getPacketNumber());
			result.setTimeStart(getDateTime(samplesPacketDTO.getTimeStart()));
			result.setTotalPackets(samplesPacketDTO.getTotalPackets());
		}

		return result;
	}

	private static PhysicsValue[][] getPhysicsValues(final List<RowPhysicsValueDTO> dtos) {
		PhysicsValue[][] result = new PhysicsValue[0][0];
		if (dtos != null && !dtos.isEmpty() && dtos.get(0) != null && dtos.get(0).getColumnValues() != null) {

			result = new PhysicsValue[dtos.size()][dtos.get(0).getColumnValues().size()];

			for (int i = 0; i < dtos.size(); i++) {
				result[i] = getPhysicsValuesColumnAsArray(dtos.get(i).getColumnValues());
			}
		}
		return result;
	}

	/**
	 * @param columnValues
	 * @return
	 */
	private static PhysicsValue[] getPhysicsValuesColumnAsArray(final List<PhysicsValueDTO> columnValues) {
		List<PhysicsValue> result = Collections.emptyList();

		if (columnValues != null && columnValues.size() > 0) {
			result = new ArrayList<PhysicsValue>(columnValues.size());
			for (final PhysicsValueDTO physicsValueDTO : columnValues) {
				result.add(getPhysicValue(physicsValueDTO));
			}
		}

		return result.toArray(new PhysicsValue[0]);
	}

	/**
	 * @param physicsValueDTO
	 * @return
	 */
	private static PhysicsValue getPhysicValue(final PhysicsValueDTO physicsValueDTO) {
		PhysicsValue result = null;
		if (physicsValueDTO != null) {
			result = new PhysicsValue();
			result.setAppliedMultiplier(new Multiplier(physicsValueDTO.getAppliedMultiplier().getCode()));
			result.setError(getPhysicsVal(physicsValueDTO.getError()));
			result.setValue(getPhysicsVal(physicsValueDTO.getValue()));
		}

		return result;
	}

	/**
	 * @param acqHeader
	 * @return
	 */
	private static HardwareAcquisitionConfig getHardwareAcquisitionConfig(final HardwareAcquisitionConfigDTO acqHeader) {
		HardwareAcquisitionConfig result = null;
		if (acqHeader != null) {
			result = new HardwareAcquisitionConfig();
			result.setChannelsConfig(getChannelsAcquistionConfigs(acqHeader.getChannelsConfig()));
			result.setFamiliarName(acqHeader.getFamiliarName());
			result.setSelectedFrequency(getFrequency(acqHeader.getFrequency()));
			result.setSelectedHardwareParameters(getParameterConfig(acqHeader.getHardwareParameters()));
			result.setHardwareUniqueID(acqHeader.getHardwareUniqueID());
			result.setTimeStart(getDateTime(acqHeader.getTimeStart()));
			result.setTotalSamples(acqHeader.getTotalSamples());
		}

		return result;
	}

	/**
	 * @param timeStart
	 * @return
	 */
	private static DateTime getDateTime(final DateTimeDTO timeStart) {

		DateTime result = null;
		if (timeStart != null) {
			result = new DateTime();

			if (timeStart.getDate() != null) {
				final Calendar calendarAux = Calendar.getInstance();
				calendarAux.setTime(timeStart.getDate());
				com.linkare.rec.data.synch.Date recDate = new com.linkare.rec.data.synch.Date(calendarAux);
				result.setDate(recDate);
			}

			final Time time = new Time(timeStart.getPicos(), timeStart.getNanos(), timeStart.getMicros(),
					timeStart.getMilis(), timeStart.getSeconds(), timeStart.getMinutes(), timeStart.getHours());
			result.setTime(time);

		}

		return result;
	}

	/**
	 * @param hardwareParameters
	 * @return
	 */
	private static ParameterConfig[] getParameterConfig(final List<ParameterConfigDTO> hardwareParameters) {
		List<ParameterConfig> result = Collections.emptyList();
		if (hardwareParameters != null && hardwareParameters.size() > 0) {
			result = new ArrayList<ParameterConfig>(hardwareParameters.size());
			for (final ParameterConfigDTO parameterConfigDTO : hardwareParameters) {
				result.add(getParameterConfig(parameterConfigDTO));
			}
		}

		return result.toArray(new ParameterConfig[0]);
	}

	/**
	 * @param parameterConfigDTO
	 * @return
	 */
	private static ParameterConfig getParameterConfig(final ParameterConfigDTO parameterConfigDTO) {
		ParameterConfig result = null;
		if (parameterConfigDTO != null) {
			result = new ParameterConfig();
			result.setParameterName(parameterConfigDTO.getParameterName());
			result.setParameterValue(parameterConfigDTO.getParameterValue());
		}
		return result;
	}

	/**
	 * @param frequency
	 * @return
	 */
	private static Frequency getFrequency(FrequencyDTO frequency) {
		Frequency result = null;

		if (frequency != null) {
			result = new Frequency();
			result.setFrequency(frequency.getFrequency());

			final FrequencyDefType frequencyDefType = new FrequencyDefType();
			frequencyDefType.setValue(frequency.getFrequencyDefType().getCode());
			result.setFrequencyDefType(frequencyDefType);

			result.setMultiplier(new Multiplier(frequency.getAppliedMultiplier().getCode()));
		}

		return result;
	}

	/**
	 * @param channelsConfig
	 * @return
	 */
	private static ChannelAcquisitionConfig[] getChannelsAcquistionConfigs(
			List<ChannelAcquisitionConfigDTO> channelsConfig) {
		List<ChannelAcquisitionConfig> result = Collections.emptyList();
		if (channelsConfig != null && channelsConfig.size() > 0) {
			result = new ArrayList<ChannelAcquisitionConfig>(channelsConfig.size());
			for (final ChannelAcquisitionConfigDTO channelAcquisitionConfigDTO : channelsConfig) {
				result.add(getChannelAcquisitionConfig(channelAcquisitionConfigDTO));
			}
		}

		return result.toArray(new ChannelAcquisitionConfig[0]);
	}

	/**
	 * @param channelAcquisitionConfigDTO
	 * @return
	 */
	private static ChannelAcquisitionConfig getChannelAcquisitionConfig(
			final ChannelAcquisitionConfigDTO channelAcquisitionConfigDTO) {

		ChannelAcquisitionConfig result = null;
		if (channelAcquisitionConfigDTO != null) {
			result = new ChannelAcquisitionConfig();
			result.setChannelName(channelAcquisitionConfigDTO.getChannelName());
			result.setSelectedChannelParameters(getParameterConfig(channelAcquisitionConfigDTO.getChannelParameters()));
			result.setSelectedFrequency(getFrequency(channelAcquisitionConfigDTO.getFrequency()));
			result.setSelectedScale(getScale(channelAcquisitionConfigDTO.getScale()));
			result.setTimeStart(getDateTime(channelAcquisitionConfigDTO.getTimeStart()));
			result.setTotalSamples(channelAcquisitionConfigDTO.getTotalSamples());
		}

		return result;
	}

	/**
	 * @param scale
	 * @return
	 */
	private static Scale getScale(ScaleDTO scale) {
		Scale result = null;
		if (scale != null) {
			result = new Scale();
			result.setDefaultErrorValue(getPhysicsVal(scale.getDefaultError()));
			result.setMaximumValue(getPhysicsVal(scale.getMaxValue()));
			result.setMinimumValue(getPhysicsVal(scale.getMinValue()));
			result.setMultiplier(new Multiplier(scale.getMultiplier().getCode()));
			result.setPhysicsUnitName(scale.getPhysicsUnitName());
			result.setPhysicsUnitSymbol(scale.getPhysicsUnitSymbol());
			result.setScaleLabel(scale.getScaleLabel());
			result.setStepValue(getPhysicsVal(scale.getStep()));
		}
		return result;
	}

	/**
	 * @param defaultError
	 * @return
	 */
	private static PhysicsVal getPhysicsVal(PhysicsValDTO dto) {
		PhysicsVal result = null;

		if (dto != null) {

			result = new PhysicsVal();

			switch (dto.getValueType()) {

			case BOOLEAN_VAL: {
				result.setBooleanValue((Boolean) dto.getValue());
				break;
			}
			case BYTE_VAL: {
				result.setByteValue((Byte) dto.getValue());
				break;
			}
			case SHORT_VAL: {
				result.setShortValue((Short) dto.getValue());
				break;
			}
			case INT_VAL: {
				result.setIntValue((Integer) dto.getValue());
				break;
			}
			case LONG_VAL: {
				result.setLongValue((Long) dto.getValue());
				break;
			}
			case FLOAT_VAL: {
				result.setFloatValue((Float) dto.getValue());
				break;
			}
			case DOUBLE_VAL: {
				result.setDoubleValue((Double) dto.getValue());
				break;
			}
			case BYTEARRAY_VAL: {
				result.setByteArrayValue(getByteArrayValue((ByteArrayValueDTO) dto.getValue()));
				break;
			}
			default:
				throw new IllegalArgumentException("invalid valueType");
			}
		}
		return result;
	}

	/**
	 * @param value
	 * @return
	 */
	private static ByteArrayValue getByteArrayValue(final ByteArrayValueDTO dto) {
		ByteArrayValue result = null;
		if (dto != null) {
			result = new ByteArrayValue();
			result.setData(dto.getData());
			result.setMimeType(dto.getMimeType());
		}
		return result;
	}

	// Only for test purposes
	public static void main(String[] args) throws Exception {

		// final RepositoryFacade repositoryFacade = lookup();
		// final DataProducerDTO experimentResultByID = repositoryFacade
		// .getExperimentResultByOID("ELAB_OPTICA_DSPIC_V1.0/Fri_Apr_01_09_18_22_GMT_2011");
		// System.out.println(experimentResultByID);
		// System.out.println("experimentResultByID.getSamplesPacketMatrixSerialized().length: "
		// + experimentResultByID.getSamplesPacketMatrixSerialized().length);
		//
		// final List<SamplesPacketDTO> samples =
		// getSamplesPacket(experimentResultByID.getSamplesPacketMatrixSerialized());
		// System.out.println(samples.size());
		//
		// final ReCMultiCastDataProducer mapToRecMultiCastDataProducer =
		// mapToRecMultiCastDataProducer(experimentResultByID);
		//
		// System.out.println("getDataProducerName: " +
		// mapToRecMultiCastDataProducer.getDataProducerName());
		// System.out.println("getOID: " +
		// mapToRecMultiCastDataProducer.getOID());

		// testExperimentResultsPersistence();

		// testRepositoryFactory();

		testRepositoryFactoryGet();

	}

	private static void testRepositoryFactory() throws Exception {
		final String expOptica = "ELAB_OPTICA_DSPIC_V1.0";

		final File f = new File("/home/elab/ReC7.0/multicast/" + expOptica);
		final List<ReCMultiCastDataProducer> recMultiCastDataProducers = getRecMultiCastDataProducers(f);

		System.setProperty("ReC.MultiCast.Repository",
				"com.linkare.rec.impl.multicast.repository.RepositoryFactory$RemoteRepository");

		IRepository repository = RepositoryFactory.getRepository();
		repository.persistExperimentResult(recMultiCastDataProducers.get(0), expOptica);

	}

	private static void testRepositoryFactoryGet() throws Exception {
		final String oid = "ELAB_OPTICA_DSPIC_V1.0/Fri_Apr_01_09_18_22_GMT_2011";

		System.setProperty("ReC.MultiCast.Repository",
				"com.linkare.rec.impl.multicast.repository.RepositoryFactory$RemoteRepository");

		IRepository repository = RepositoryFactory.getRepository();
		final ReCMultiCastDataProducer experimentResult = (ReCMultiCastDataProducer) repository
				.getExperimentResult(oid);
		System.out.println(experimentResult);

		final HardwareAcquisitionConfig acquisitionHeader = experimentResult.getAcquisitionHeader();

		System.out.println("getFamiliarName(): " + acquisitionHeader.getFamiliarName());
		System.out.println("getHardwareUniqueID(): " + acquisitionHeader.getHardwareUniqueID());
		System.out.println("getTotalSamples(): " + acquisitionHeader.getTotalSamples());
		System.out.println("getSelectedFrequency(): " + acquisitionHeader.getSelectedFrequency());

		for (final ChannelAcquisitionConfig channelAcquisitionConfig : acquisitionHeader.getChannelsConfig()) {
			System.out.println("channelAcquisitionConfig.getChannelName(): "
					+ channelAcquisitionConfig.getChannelName());
			System.out.println("channelAcquisitionConfig.getFrequency(): "
					+ channelAcquisitionConfig.getSelectedFrequency().getFrequency());
			System.out.println("channelAcquisitionConfig.getChannelName(): "
					+ channelAcquisitionConfig.getSelectedScale());
		}

	}

	private static RepositoryFacade lookup() throws NamingException {
		final InitialContext ic = new InitialContext();

		return (RepositoryFacade) ic
				.lookup("java:global/rec.am/RepositoryFacadeBean!com.linkare.rec.am.RepositoryFacade");
	}

	private static void testExperimentResultsPersistence() {
		try {

			final File f = new File("/home/elab/ReC7.0/multicast/ELAB_OPTICA_DSPIC_V1.0");

			final RepositoryFacade ejb = lookup();

			final List<ReCMultiCastDataProducer> recMultiCastDataProducers = getRecMultiCastDataProducers(f);

			for (final ReCMultiCastDataProducer reCMultiCastDataProducer2 : recMultiCastDataProducers) {

				final DataProducerDTO dataProducerDTO = DTOMapperUtils.mapToDataProducerDTO(reCMultiCastDataProducer2);
				System.out.println("sending dataproducer:  " + reCMultiCastDataProducer2.getOID());
				System.out
						.println("samples byte[] leght: " + dataProducerDTO.getSamplesPacketMatrixSerialized().length);
				System.out.println("dataProducerDTO.getAcqHeader()" + dataProducerDTO.getAcqHeader());
				try {
					ejb.persistExperimentResults(dataProducerDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}

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
					if (file.isFile() && file.getName().indexOf("Samples") == -1
							&& file.getName().startsWith("DataBuffer")) {
						result.add(getRecMultiCastDataProducer(file));
					}
				}
			}
			return result;

		} catch (Exception e) {
			throw e;
		}
	}

	private static ReCMultiCastDataProducer getRecMultiCastDataProducer(final File f) throws Exception {

		if (!f.isFile()) {
			throw new IllegalArgumentException("invalid file.");
		}

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(f));

			return (ReCMultiCastDataProducer) objectInputStream.readObject();
		} catch (Exception e) {
			System.out.println(f);
			System.out.println(e);
			throw e;
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static List<SamplesPacketDTO> getSamplesPacket(final byte[] samples) throws Exception {

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new ByteArrayInputStream(samples));

			return (List<SamplesPacketDTO>) objectInputStream.readObject();
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	public static ClientInfoDTO mapToClientInfoDTO(final UserInfo userInfo) {
		ClientInfoDTO result = null;
		if (userInfo != null) {
			result = new ClientInfoDTO(userInfo.getUserName());
		}
		return result;
	}

	public static HardwareInfoDTO mapToHardwareInfoDTO(final HardwareInfo hardwareInfo) {
		HardwareInfoDTO result = null;
		if (hardwareInfo != null) {
			result = new HardwareInfoDTO(hardwareInfo.getDriverVersion(), hardwareInfo.getHardwareName(),
					hardwareInfo.getHardwareVersion(), hardwareInfo.getHardwareManufacturer(),
					hardwareInfo.getDescriptionText(), hardwareInfo.getHardwareUniqueID(),
					hardwareInfo.getFamiliarName());
		}
		return result;
	}

	public static List<HardwareInfoDTO> mapToHardwareInfoDTOList(final List<HardwareInfo> hardwares) {
		List<HardwareInfoDTO> result = Collections.emptyList();

		if (hardwares != null && !hardwares.isEmpty()) {

			result = new ArrayList<HardwareInfoDTO>(hardwares.size());

			for (final HardwareInfo hardware : hardwares) {
				result.add(mapToHardwareInfoDTO(hardware));
			}
		}
		return result;
	}

	public static List<ClientInfoDTO> mapToClientInfoDTOList(final List<UserInfo> users) {
		List<ClientInfoDTO> result = Collections.emptyList();

		if (users != null && !users.isEmpty()) {

			result = new ArrayList<ClientInfoDTO>(users.size());

			for (final UserInfo user : users) {
				result.add(mapToClientInfoDTO(user));
			}
		}
		return result;
	}

	public static RegisteredHardwareDTO mapToRegisteredHardwareInfoDTO(
			final RegisteredHardwareInfo registeredHardwareInfo) {
		RegisteredHardwareDTO result = null;

		if (registeredHardwareInfo != null) {
			result = new RegisteredHardwareDTO(registeredHardwareInfo.getHardwareUniqueID(),
					registeredHardwareInfo.getState(), new HashSet<String>(registeredHardwareInfo.getUsersConnected()));
		}

		return result;
	}

	public static List<RegisteredHardwareDTO> mapToRegisteredHardwareInfoDTOList(
			final List<RegisteredHardwareInfo> hardwareInfoList) {
		List<RegisteredHardwareDTO> result = Collections.emptyList();

		if (hardwareInfoList != null && !hardwareInfoList.isEmpty()) {

			result = new ArrayList<RegisteredHardwareDTO>(hardwareInfoList.size());

			for (final RegisteredHardwareInfo registeredHardwareInfo : hardwareInfoList) {
				result.add(mapToRegisteredHardwareInfoDTO(registeredHardwareInfo));
			}
		}
		return result;
	}

}
