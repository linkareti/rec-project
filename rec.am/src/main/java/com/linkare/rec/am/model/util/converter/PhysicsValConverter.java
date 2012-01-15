package com.linkare.rec.am.model.util.converter;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.DozerConverter;

import com.linkare.rec.am.model.BooleanVal;
import com.linkare.rec.am.model.ByteArrayVal;
import com.linkare.rec.am.model.ByteArrayValue;
import com.linkare.rec.am.model.ByteVal;
import com.linkare.rec.am.model.DoubleVal;
import com.linkare.rec.am.model.FloatVal;
import com.linkare.rec.am.model.IntVal;
import com.linkare.rec.am.model.LongVal;
import com.linkare.rec.am.model.PhysicsVal;
import com.linkare.rec.am.model.ShortVal;
import com.linkare.rec.am.repository.ByteArrayValueDTO;
import com.linkare.rec.am.repository.PhysicsValDTO;
import com.linkare.rec.am.repository.PhysicsValueTypeEnum;

public final class PhysicsValConverter extends DozerConverter<PhysicsValDTO, PhysicsVal> {

    public PhysicsValConverter() {
	super(PhysicsValDTO.class, PhysicsVal.class);
    }

    @Override
    public PhysicsValDTO convertFrom(PhysicsVal entity, PhysicsValDTO dto) {

	if (entity != null) {
	    final PhysicsValDTO result = new PhysicsValDTO();

	    if (entity instanceof BooleanVal) {
		result.setValue(((BooleanVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.BOOLEAN_VAL);
	    } else if (entity instanceof ByteVal) {
		result.setValue(((ByteVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.BYTE_VAL);
	    } else if (entity instanceof ShortVal) {
		result.setValue(((ShortVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.SHORT_VAL);
	    } else if (entity instanceof IntVal) {
		result.setValue(((IntVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.INT_VAL);
	    } else if (entity instanceof LongVal) {
		result.setValue(((LongVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.LONG_VAL);
	    } else if (entity instanceof FloatVal) {
		result.setValue(((FloatVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.FLOAT_VAL);
	    } else if (entity instanceof DoubleVal) {
		result.setValue(((DoubleVal) entity).getValue());
		result.setValueType(PhysicsValueTypeEnum.DOUBLE_VAL);
	    } else if (entity instanceof ByteArrayVal) {
		result.setValue(DozerBeanMapperSingletonWrapper.getInstance().map(((ByteArrayVal) entity).getValue(), ByteArrayValueDTO.class));
		result.setValueType(PhysicsValueTypeEnum.BYTEARRAY_VAL);
	    } else {
		throw new IllegalArgumentException("invalid PhysicsVal entity type");
	    }

	    return result;
	} else {
	    return null;
	}

    }

    @Override
    public PhysicsVal convertTo(PhysicsValDTO dto, PhysicsVal entity) {

	if (dto != null) {

	    switch (dto.getValueType()) {

	    case BOOLEAN_VAL: {
		final BooleanVal result = new BooleanVal();
		result.setValue((Boolean) dto.getValue());
		return result;
	    }
	    case BYTE_VAL: {
		final ByteVal result = new ByteVal();
		result.setValue((Byte) dto.getValue());
		return result;
	    }
	    case SHORT_VAL: {
		final ShortVal result = new ShortVal();
		result.setValue((Short) dto.getValue());
		return result;
	    }
	    case INT_VAL: {
		final IntVal result = new IntVal();
		result.setValue((Integer) dto.getValue());
		return result;
	    }
	    case LONG_VAL: {
		final LongVal result = new LongVal();
		result.setValue((Long) dto.getValue());
		return result;
	    }
	    case FLOAT_VAL: {
		final FloatVal result = new FloatVal();
		result.setValue((Float) dto.getValue());
		return result;
	    }
	    case DOUBLE_VAL: {
		final DoubleVal result = new DoubleVal();
		result.setValue((Double) dto.getValue());
		return result;
	    }
	    case BYTEARRAY_VAL: {
		final ByteArrayVal result = new ByteArrayVal();
		//workaround
		final ByteArrayValue aux = new ByteArrayValue();
		aux.setData(((ByteArrayValueDTO) dto.getValue()).getData());
		aux.setMimeType(((ByteArrayValueDTO) dto.getValue()).getMimeType());
		result.setValue(aux);
		//should work!!!!
		//		result.setValue(DozerBeanMapperSingletonWrapper.getInstance().map(dto.getValue(), ByteArrayValue.class));
		return result;
	    }
	    default:
		throw new IllegalArgumentException("invalid valueType");
	    }

	} else {
	    return null;
	}

    }
}
