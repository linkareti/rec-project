package com.linkare.rec.web.experiment;

import com.linkare.rec.web.RepositoryFacade;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.Assert;

import org.junit.Test;

import com.linkare.rec.web.model.BooleanVal;
import com.linkare.rec.web.model.ByteArrayVal;
import com.linkare.rec.web.model.ByteArrayValue;
import com.linkare.rec.web.model.ByteVal;
import com.linkare.rec.web.model.ChannelAcquisitionConfig;
import com.linkare.rec.web.model.DataProducer;
import com.linkare.rec.web.model.DateTime;
import com.linkare.rec.web.model.DoubleVal;
import com.linkare.rec.web.model.FloatVal;
import com.linkare.rec.web.model.Frequency;
import com.linkare.rec.web.model.HardwareAcquisitionConfig;
import com.linkare.rec.web.model.IntVal;
import com.linkare.rec.web.model.LongVal;
import com.linkare.rec.web.model.ParameterConfig;
import com.linkare.rec.web.model.PhysicsVal;
import com.linkare.rec.web.model.Scale;
import com.linkare.rec.web.model.ShortVal;
import com.linkare.rec.web.model.util.converter.DozerBeanMapperSingletonWrapper;
import com.linkare.rec.web.repository.ByteArrayValueDTO;
import com.linkare.rec.web.repository.ChannelAcquisitionConfigDTO;
import com.linkare.rec.web.repository.DataProducerDTO;
import com.linkare.rec.web.repository.DateTimeDTO;
import com.linkare.rec.web.repository.FrequencyDTO;
import com.linkare.rec.web.repository.FrequencyDefTypeEnum;
import com.linkare.rec.web.repository.HardwareAcquisitionConfigDTO;
import com.linkare.rec.web.repository.MultiplierEnum;
import com.linkare.rec.web.repository.ParameterConfigDTO;
import com.linkare.rec.web.repository.PhysicsValDTO;
import com.linkare.rec.web.repository.PhysicsValueDTO;
import com.linkare.rec.web.repository.PhysicsValueTypeEnum;
import com.linkare.rec.web.repository.RowPhysicsValueDTO;
import com.linkare.rec.web.repository.SamplesPacketDTO;
import com.linkare.rec.web.repository.ScaleDTO;

public class DozzerMappingTest {

    @Test
    public void convertToDatetimeTest() {

	final DateTimeDTO dto = getDateTimeDTO();

	final DateTime entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, DateTime.class);

	assertDatetime(dto, entity);

    }

    private void assertDatetime(final DateTimeDTO dto, final DateTime destObject) {
	Assert.assertNotNull(destObject);

	Assert.assertEquals(dto.getDate(), destObject.getDate());
	Assert.assertEquals(dto.getHours(), destObject.getHours());
	Assert.assertEquals(dto.getMicros(), destObject.getMicros());
	Assert.assertEquals(dto.getMilis(), destObject.getMilis());
	Assert.assertEquals(dto.getMinutes(), destObject.getMinutes());
	Assert.assertEquals(dto.getNanos(), destObject.getNanos());
	Assert.assertEquals(dto.getPicos(), destObject.getPicos());
	Assert.assertEquals(dto.getSeconds(), destObject.getSeconds());
    }

    @Test
    public void convertToFrequencyTest() {

	final FrequencyDTO dto = getFrequencyDTO();

	final Frequency entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, Frequency.class);

	assertFrequency(dto, entity);

    }

    private void assertFrequency(final FrequencyDTO dto, final Frequency destObject) {
	Assert.assertNotNull(destObject);

	Assert.assertEquals(dto.getFrequency(), destObject.getFrequency());
	Assert.assertEquals(dto.getFrequencyDefType(), destObject.getFrequencyDefType());
	Assert.assertEquals(dto.getAppliedMultiplier(), destObject.getAppliedMultiplier());
    }

    @Test
    public void convertToByteArrayValueTest() {
	final ByteArrayValueDTO dto = getByteArrayValueDTO();

	final ByteArrayValue entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, ByteArrayValue.class);

	assertByteArrayValue(dto, entity);
    }

    private void assertByteArrayValue(final ByteArrayValueDTO dto, final ByteArrayValue destObject) {
	Assert.assertNotNull(destObject);
	Assert.assertEquals(dto.getMimeType(), destObject.getMimeType());
	Assert.assertTrue(Arrays.equals(dto.getData(), destObject.getData()));
    }

    @Test
    public void convertToPhysicsValTest() {

	PhysicsValDTO dto = getBooleanPhysicsValDTO();
	PhysicsVal entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, BooleanVal.class);
	Assert.assertEquals(dto.getValue(), ((BooleanVal) entity).getValue());

	dto = getBytePhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, ByteVal.class);
	Assert.assertEquals(dto.getValue(), ((ByteVal) entity).getValue());

	dto = getShortPhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, ShortVal.class);
	Assert.assertEquals(dto.getValue(), ((ShortVal) entity).getValue());

	dto = getIntPhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, IntVal.class);
	Assert.assertEquals(dto.getValue(), ((IntVal) entity).getValue());

	dto = getLongPhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, LongVal.class);
	Assert.assertEquals(dto.getValue(), ((LongVal) entity).getValue());

	dto = getFloatPhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, FloatVal.class);
	Assert.assertEquals(dto.getValue(), ((FloatVal) entity).getValue());

	dto = getDoublePhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, DoubleVal.class);
	Assert.assertEquals(dto.getValue(), ((DoubleVal) entity).getValue());

	dto = getByteArrayPhysicsValDTO();
	entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, PhysicsVal.class);
	assertPhysicalValType(entity, ByteArrayVal.class);
	assertByteArrayValue((ByteArrayValueDTO) dto.getValue(), ((ByteArrayVal) entity).getValue());

    }

    @Test
    public void convertToScaleTest() {
	final ScaleDTO dto = getScaleDTO();
	final Scale entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, Scale.class);

	assertScale(dto, entity);

    }

    private void assertScale(final ScaleDTO dto, final Scale destObject) {

	Assert.assertNotNull(destObject);
	Assert.assertEquals(dto.getPhysicsUnitName(), destObject.getPhysicsUnitName());
	Assert.assertEquals(dto.getPhysicsUnitSymbol(), destObject.getPhysicsUnitSymbol());
	Assert.assertEquals(dto.getScaleLabel(), destObject.getScaleLabel());
	Assert.assertEquals(dto.getMultiplier(), destObject.getMultiplier());

	Assert.assertEquals(dto.getDefaultError().getValue(), ((ByteVal) destObject.getDefaultError()).getValue());
	Assert.assertEquals(dto.getMaxValue().getValue(), ((ShortVal) destObject.getMaxValue()).getValue());
	Assert.assertEquals(dto.getMinValue().getValue(), ((IntVal) destObject.getMinValue()).getValue());
	Assert.assertEquals(dto.getStep().getValue(), ((ByteVal) destObject.getStep()).getValue());
    }

    @Test
    public void convertToHardwareAcquisitionConfigTest() {
	final HardwareAcquisitionConfigDTO dto = getHardwareAcquisitionConfigDTO();

	final HardwareAcquisitionConfig entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, HardwareAcquisitionConfig.class);

	assertHardwareAcquisitionTest(dto, entity);

    }

    private void assertHardwareAcquisitionTest(final HardwareAcquisitionConfigDTO dto, final HardwareAcquisitionConfig entity) {
	Assert.assertNotNull(entity);
	Assert.assertEquals(dto.getFamiliarName(), entity.getFamiliarName());
	Assert.assertEquals(dto.getHardwareUniqueID(), entity.getHardwareUniqueID());
	Assert.assertEquals(dto.getTotalSamples(), entity.getTotalSamples());
	assertChannelAcquisitionConfigs(dto.getChannelsConfig(), entity.getChannelsConfig());
	assertFrequency(dto.getFrequency(), entity.getFrequency());
	assertParameterConfigs(dto.getHardwareParameters(), entity.getHardwareParameters());
	assertDatetime(dto.getTimeStart(), entity.getTimeStart());
    }

    @Test
    public void convertToChannelAcquisitionConfig() {
	final ChannelAcquisitionConfigDTO dto = getChannelAcquisitionConfigDTO();

	final ChannelAcquisitionConfig entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, ChannelAcquisitionConfig.class);

	assertChannelAcquisitionConfig(dto, entity);

    }

    @Test
    public void convertToParameterConfigTest() {
	final ParameterConfigDTO dto = getChannelParameterConfigDTO();

	final ParameterConfig entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, ParameterConfig.class);

	assertParameterConfig(dto, entity);

    }

    //    private Collection<HardwareParameterConfigDTO> getHardwareParameterConfigDTOs() {
    //	return Arrays.asList(new HardwareParameterConfigDTO[] { getHardwareAcquisitionConfigDTO() });
    //    }

    private void assertChannelAcquisitionConfigs(final List<ChannelAcquisitionConfigDTO> dtos, final List<ChannelAcquisitionConfig> entities) {
	Assert.assertEquals(dtos.size(), entities.size());

	for (int i = 0; i < dtos.size(); i++) {
	    assertChannelAcquisitionConfig(dtos.get(i), entities.get(i));
	}

    }

    private void assertChannelAcquisitionConfig(final ChannelAcquisitionConfigDTO dto, final ChannelAcquisitionConfig entity) {
	Assert.assertNotNull(entity);
	Assert.assertEquals(dto.getChannelName(), entity.getChannelName());
	assertParameterConfigs(dto.getChannelParameters(), entity.getChannelParameters());
	assertFrequency(dto.getFrequency(), entity.getFrequency());
	assertScale(dto.getScale(), entity.getScale());
	assertDatetime(dto.getTimeStart(), entity.getTimeStart());
	Assert.assertEquals(dto.getTotalSamples(), entity.getTotalSamples());
    }

    @Test
    public void convertToChannelParameterConfig() {
	final ParameterConfigDTO dto = getChannelParameterConfigDTO();

	final ParameterConfig entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, ParameterConfig.class);

	assertParameterConfig(dto, entity);

    }

    private void assertParameterConfigs(final List<? extends ParameterConfigDTO> dto, final List<? extends ParameterConfig> entities) {

	Assert.assertEquals(dto.size(), entities.size());

	for (int i = 0; i < dto.size(); i++) {
	    assertParameterConfig(dto.get(i), entities.get(i));
	}
    }

    private void assertParameterConfig(final ParameterConfigDTO dto, final ParameterConfig entity) {
	Assert.assertNotNull(entity);
	Assert.assertEquals(dto.getParameterName(), entity.getParameterName());
	Assert.assertEquals(dto.getParameterValue(), entity.getParameterValue());
    }

    @Test
    public void convertToDataProducerTest() {
	final DataProducerDTO dto = getDataProducerDTO();

	final DataProducer entity = DozerBeanMapperSingletonWrapper.getInstance().map(dto, DataProducer.class);

	assertDataProducer(dto, entity);

    }

    private void assertDataProducer(final DataProducerDTO dto, DataProducer entity) {
	Assert.assertNotNull(entity);
	assertHardwareAcquisitionTest(dto.getAcqHeader(), entity.getAcqHeader());
	Assert.assertEquals(dto.getOid(), entity.getOid());
	Assert.assertEquals(dto.getDataProducerName(), entity.getDataProducerName());
	//	assertSamplesPackets(dto.getSamplesPacketMatrix(), entity.getSamplesPacketMatrix());
    }

    private DataProducerDTO getDataProducerDTO() {
	final DataProducerDTO dto = new DataProducerDTO();
	dto.setAcqHeader(getHardwareAcquisitionConfigDTO());
	dto.setDataProducerName("dataProducerName");
	dto.setOid("oid");
	dto.setSamplesPacketMatrix(getSamplesPacketDTOs());
	return dto;
    }

    private SamplesPacketDTO getSamplesPacketDTO() {
	final SamplesPacketDTO dto = new SamplesPacketDTO();
	dto.setData(getColumnPhysicsValueDTOs());
	dto.setPacketNumber(15);
	dto.setTimeStart(getDateTimeDTO());
	dto.setTotalPackets(1500);
	return dto;
    }

    private List<SamplesPacketDTO> getSamplesPacketDTOs() {
	return Arrays.asList(new SamplesPacketDTO[] { getSamplesPacketDTO() });
    }

    private RowPhysicsValueDTO getColumnPhysicsValueDTO() {
	final RowPhysicsValueDTO dto = new RowPhysicsValueDTO();
	dto.setColumnValues(getPhysicsValueDTOs());
	return dto;
    }

    private List<RowPhysicsValueDTO> getColumnPhysicsValueDTOs() {
	return Arrays.asList(new RowPhysicsValueDTO[] { getColumnPhysicsValueDTO() });
    }

    private PhysicsValueDTO getPhysicsValueDTO() {
	final PhysicsValueDTO dto = new PhysicsValueDTO();
	dto.setAppliedMultiplier(MultiplierEnum.GIGA);
	dto.setError(getBytePhysicsValDTO());
	dto.setValue(getBytePhysicsValDTO());
	return dto;
    }

    private List<PhysicsValueDTO> getPhysicsValueDTOs() {
	return Arrays.asList(new PhysicsValueDTO[] { getPhysicsValueDTO() });
    }

    private HardwareAcquisitionConfigDTO getHardwareAcquisitionConfigDTO() {
	final HardwareAcquisitionConfigDTO dto = new HardwareAcquisitionConfigDTO();
	dto.setChannelsConfig(getChannelAcquisitionConfigDTOs());
	dto.setFamiliarName("familiarName");
	dto.setHardwareUniqueID("hardwareUniqueID");
	dto.setFrequency(getFrequencyDTO());
	dto.setHardwareParameters(getHardwareParameterConfigDTOs());
	dto.setTimeStart(getDateTimeDTO());
	dto.setTotalSamples(1000);
	return dto;
    }

    private List<ParameterConfigDTO> getHardwareParameterConfigDTOs() {
	final ParameterConfigDTO dto = new ParameterConfigDTO();
	dto.setParameterName("hparameterName");
	dto.setParameterValue("hparameterValue");
	return Arrays.asList(new ParameterConfigDTO[] { dto });
    }

    private List<ChannelAcquisitionConfigDTO> getChannelAcquisitionConfigDTOs() {
	return Arrays.asList(new ChannelAcquisitionConfigDTO[] { getChannelAcquisitionConfigDTO() });
    }

    private ChannelAcquisitionConfigDTO getChannelAcquisitionConfigDTO() {
	ChannelAcquisitionConfigDTO dto1 = new ChannelAcquisitionConfigDTO();
	dto1.setChannelName("channelName");
	dto1.setChannelParameters(getChannelParameterConfigDTOs());
	dto1.setFrequency(getFrequencyDTO());
	dto1.setScale(getScaleDTO());
	dto1.setTotalSamples(100);
	dto1.setTimeStart(getDateTimeDTO());
	return dto1;
    }

    private List<ParameterConfigDTO> getChannelParameterConfigDTOs() {
	final ParameterConfigDTO dto = new ParameterConfigDTO();
	dto.setParameterName("parameterName");
	dto.setParameterValue("parameterValue");
	return Arrays.asList(new ParameterConfigDTO[] { getChannelParameterConfigDTO() });
    }

    private ParameterConfigDTO getChannelParameterConfigDTO() {
	final ParameterConfigDTO dto = new ParameterConfigDTO();
	dto.setParameterName("parameterName");
	dto.setParameterValue("parameterValue");
	return dto;
    }

    private ScaleDTO getScaleDTO() {
	final ScaleDTO dto = new ScaleDTO();
	dto.setMultiplier(MultiplierEnum.KILO);
	dto.setDefaultError(getBytePhysicsValDTO());
	dto.setMaxValue(getShortPhysicsValDTO());
	dto.setMinValue(getIntPhysicsValDTO());
	dto.setPhysicsUnitName("physicsUnitName");
	dto.setPhysicsUnitSymbol("physicsUnitSymbol");
	dto.setScaleLabel("scaleLabel");
	dto.setStep(getBytePhysicsValDTO());
	return dto;
    }

    private static void assertPhysicalValType(final PhysicsVal obj, final Class<? extends PhysicsVal> clazz) {
	if (!clazz.isInstance(obj)) {
	    Assert.fail("not a valid type: " + obj.getClass().getName());
	}
    }

    private ByteArrayValueDTO getByteArrayValueDTO() {
	final ByteArrayValueDTO dto = new ByteArrayValueDTO();
	dto.setData(new byte[0]);
	dto.setMimeType("mimeType");
	return dto;
    }

    private PhysicsValDTO getBooleanPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.BOOLEAN_VAL);
	dto.setValue(Boolean.TRUE);
	return dto;
    }

    private PhysicsValDTO getBytePhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.BYTE_VAL);
	dto.setValue((byte) 10);
	return dto;
    }

    private PhysicsValDTO getShortPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.SHORT_VAL);
	dto.setValue((short) 10);
	return dto;
    }

    private PhysicsValDTO getIntPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.INT_VAL);
	dto.setValue(10);
	return dto;
    }

    private PhysicsValDTO getLongPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.LONG_VAL);
	dto.setValue((long) 10);
	return dto;
    }

    private PhysicsValDTO getFloatPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.FLOAT_VAL);
	dto.setValue((float) 10);
	return dto;
    }

    private PhysicsValDTO getDoublePhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.DOUBLE_VAL);
	dto.setValue((double) 10);
	return dto;
    }

    private PhysicsValDTO getByteArrayPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	dto.setValueType(PhysicsValueTypeEnum.BYTEARRAY_VAL);
	dto.setValue(getByteArrayValueDTO());
	return dto;
    }

    //    private PhysicsValDTO getByteArrayPhysicsValDTO() {
    //	final PhysicsValDTO dto = new PhysicsValDTO();
    //	//dto.setDiscriminatorValue(2);
    //	dto.setValue((int) 10);
    //	return dto;
    //    }

    private FrequencyDTO getFrequencyDTO() {
	final FrequencyDTO dto = new FrequencyDTO();
	dto.setFrequency(2.5);
	dto.setFrequencyDefType(FrequencyDefTypeEnum.FREQUENCY_TYPE);
	dto.setAppliedMultiplier(MultiplierEnum.KILO);
	return dto;
    }

    private DateTimeDTO getDateTimeDTO() {

	final DateTimeDTO dto = new DateTimeDTO();
	dto.setDate(new Date());
	dto.setHours((byte) 20);
	dto.setMicros((short) 10);
	dto.setMilis((short) 10);
	dto.setMinutes((byte) 10);
	dto.setNanos((short) 10);
	dto.setPicos((short) 10);
	dto.setSeconds((byte) 10);
	return dto;
    }

    public static void main(String[] args) throws NamingException, RemoteException {
	InitialContext ic = new InitialContext();

	RepositoryFacade ejb = (RepositoryFacade) ic.lookup("java:global/rec.web/ExperimentResultsManagerBean");

	try {
	    DataProducerDTO dto = new DozzerMappingTest().getDataProducerDTO();
	    ejb.persistExperimentResults(dto);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
