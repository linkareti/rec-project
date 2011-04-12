package com.linkare.rec.am.model.util.converter;

import org.dozer.BeanFactory;

import com.linkare.rec.am.experiment.PhysicsValDTO;
import com.linkare.rec.am.model.BooleanVal;
import com.linkare.rec.am.model.ByteArrayVal;
import com.linkare.rec.am.model.ByteArrayValue;
import com.linkare.rec.am.model.ByteVal;
import com.linkare.rec.am.model.DoubleVal;
import com.linkare.rec.am.model.FloatVal;
import com.linkare.rec.am.model.IntVal;
import com.linkare.rec.am.model.LongVal;
import com.linkare.rec.am.model.ShortVal;

public final class PhysicsValFactory implements BeanFactory {

    @Override
    public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
	if (source != null) {

	    if (!(sourceClass == PhysicsValDTO.class)) {
		throw new IllegalArgumentException("invalid sourceClass");
	    }

	    final PhysicsValDTO dto = (PhysicsValDTO) source;

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
		result.setValue(DozerBeanMapperSingletonWrapper.getInstance().map(dto.getValue(), ByteArrayValue.class));
		return result;
	    }
	    default:
		throw new IllegalArgumentException("invalida valueType");
	    }

	} else {
	    return null;
	}
    }
}
