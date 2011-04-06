package com.linkare.rec.am.experiment;

import java.util.Date;

import junit.framework.Assert;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.junit.Test;

import com.linkare.rec.am.model.DateTime;

public class TestDozzerMapping {

    @Test
    public void convertToDatetime() {

	final DateTimeDTO dto = getDateTimeDTO();

	final DateTime destObject = DozerBeanMapperSingletonWrapper.getInstance().map(dto, DateTime.class);

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

    //    @Test
    //    public void convertToFrequency() {
    //
    //	final FrequencyDTO dto = getFrequencyDTO();
    //
    //	final Frequency destObject = DozerBeanMapperSingletonWrapper.getInstance().map(dto, Frequency.class);
    //
    //	Assert.assertNotNull(destObject);
    //
    //	Assert.assertEquals(dto.getFrequency(), destObject.getFrequency());
    //	Assert.assertEquals(dto.getFrequencyDefType(), destObject.getFrequencyDefType());
    //	Assert.assertEquals(dto.getAppliedMultiplier(), destObject.getAppliedMultiplier());
    //
    //    }
    //
    //    @Test
    //    public void convertToByteArrayValue() {
    //	final ByteArrayValueDTO dto = getByteArrayValueDTO();
    //
    //	final ByteArrayValue destObject = DozerBeanMapperSingletonWrapper.getInstance().map(dto, ByteArrayValue.class);
    //
    //	Assert.assertEquals(dto.getMimeType(), destObject.getMimeType());
    //	Assert.assertEquals(dto.getVirtualPath(), destObject.getVirtualPath());
    //	Assert.assertTrue(Arrays.equals(dto.getData(), destObject.getData()));
    //
    //	System.out.println(dto);
    //    }
    //
    //    @Test
    //    public void convertToPhysicsVal() {
    //
    //	PhysicsValDTO dto = getBooleanPhysicsValDTO();
    //
    //	final PhysicsValConverter converter = new PhysicsValConverter();
    //
    //	BooleanVal destObject = (BooleanVal) converter.convertTo(dto);
    //
    //	Assert.assertNotNull(destObject);
    //	Assert.assertEquals(dto.getValue(), destObject.getValue());
    //
    //	dto = getBytePhysicsValDTO();
    //
    //	ByteVal byteObject = (ByteVal) converter.convertTo(dto);
    //
    //	Assert.assertNotNull(byteObject);
    //	Assert.assertEquals(dto.getValue(), byteObject.getValue());
    //
    //    }

    private ByteArrayValueDTO getByteArrayValueDTO() {
	final ByteArrayValueDTO dto = new ByteArrayValueDTO();
	dto.setData(new byte[0]);
	dto.setMimeType("mimeType");
	dto.setVirtualPath("virtualPath");
	return dto;
    }

    private PhysicsValDTO getBooleanPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//	dto.setDiscriminatorValue(2);
	dto.setValue(Boolean.TRUE);
	return dto;
    }

    private PhysicsValDTO getBytePhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((byte) 10);
	return dto;
    }

    private PhysicsValDTO getShortPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((short) 10);
	return dto;
    }

    private PhysicsValDTO getIntPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((int) 10);
	return dto;
    }

    private PhysicsValDTO getLongPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((int) 10);
	return dto;
    }

    private PhysicsValDTO getFloatPhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((int) 10);
	return dto;
    }

    private PhysicsValDTO getDoublePhysicsValDTO() {
	final PhysicsValDTO dto = new PhysicsValDTO();
	//dto.setDiscriminatorValue(2);
	dto.setValue((int) 10);
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

}
