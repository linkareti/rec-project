package com.linkare.rec.am.model.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import com.linkare.rec.am.experiment.ChannelAcquisitionConfigDTO;
import com.linkare.rec.am.experiment.DateTimeDTO;
import com.linkare.rec.am.experiment.FrequencyDTO;
import com.linkare.rec.am.experiment.HardwareAcquisitionConfigDTO;
import com.linkare.rec.am.experiment.ParameterConfigDTO;
import com.linkare.rec.am.experiment.PhysicsValDTO;
import com.linkare.rec.am.model.ChannelAcquisitionConfig;
import com.linkare.rec.am.model.DateTime;
import com.linkare.rec.am.model.Frequency;
import com.linkare.rec.am.model.HardwareAcquisitionConfig;
import com.linkare.rec.am.model.ParameterConfig;
import com.linkare.rec.am.model.PhysicsVal;

/**
 * 
 * Singleton. Encalpsulate all dozer dependencies, so all access to dozer API or dozer configuration should be done in this singleton(if possible)
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public final class DozerBeanMapperSingletonWrapper implements Mapper {

    private static final Mapper INSTANCE = new DozerBeanMapperSingletonWrapper();

    private final DozerBeanMapper mapper;

    private DozerBeanMapperSingletonWrapper() {
	mapper = new DozerBeanMapper();
	//make available all builders for complex type conversion
	// dozer need this information to mapp complex types
	mapper.setMappings(DozerBuildersRegistry.getDozerBuilders());
    }

    public static Mapper getInstance() {
	return INSTANCE;
    }

    @Override
    public <T> T map(Object arg0, Class<T> arg1) throws MappingException {
	return mapper.map(arg0, arg1);
    }

    @Override
    public void map(Object arg0, Object arg1) throws MappingException {
	mapper.map(arg0, arg1);
    }

    @Override
    public <T> T map(Object arg0, Class<T> arg1, String arg2) throws MappingException {
	return mapper.map(arg0, arg1);
    }

    @Override
    public void map(Object arg0, Object arg1, String arg2) throws MappingException {
	mapper.map(arg0, arg1);
    }

    private final static class DozerBuildersRegistry {

	private static final List<BeanMappingBuilder> builders;

	static {

	    builders = new ArrayList<BeanMappingBuilder>(10);

	    BeanMappingBuilder aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(HardwareAcquisitionConfigDTO.class, HardwareAcquisitionConfig.class);
		}

	    };

	    builders.add(aux);

	    aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(DateTimeDTO.class, DateTime.class);
		}

	    };

	    builders.add(aux);

	    aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(FrequencyDTO.class, Frequency.class);
		}

	    };

	    builders.add(aux);

	    aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(ParameterConfigDTO.class, ParameterConfig.class);
		}

	    };

	    builders.add(aux);

	    aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(ChannelAcquisitionConfigDTO.class, ChannelAcquisitionConfig.class);
		}

	    };

	    builders.add(aux);

	    aux = new BeanMappingBuilder() {

		@Override
		protected void configure() {
		    mapping(PhysicsValDTO.class, PhysicsVal.class, TypeMappingOptions.beanFactory(PhysicsValFactory.class.getName()));
		}

	    };

	    builders.add(aux);

	}

	private static List<BeanMappingBuilder> getDozerBuilders() {
	    return builders;
	}

    }

}
