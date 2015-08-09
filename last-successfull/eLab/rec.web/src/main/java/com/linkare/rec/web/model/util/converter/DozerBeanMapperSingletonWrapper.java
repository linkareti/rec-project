package com.linkare.rec.web.model.util.converter;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

/**
 * 
 * Singleton. Encalpsulate all dozer dependencies, so all access to dozer API or dozer configuration should be done in this singleton(if possible)
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public final class DozerBeanMapperSingletonWrapper {

    private static final DozerBeanMapperSingletonWrapper INSTANCE = new DozerBeanMapperSingletonWrapper();

    private final DozerBeanMapper mapper;

    private DozerBeanMapperSingletonWrapper() {

	mapper = new DozerBeanMapper(Arrays.asList(new String[] { DozerBeanMapperSingletonWrapper.class.getPackage().getName().replaceAll("\\.", "/")
		+ "/dozer-global-configuration.xml" }));

    }

    public static DozerBeanMapperSingletonWrapper getInstance() {
	return INSTANCE;
    }

    public <T> T map(Object arg0, Class<T> arg1) {
	try {
	    return mapper.map(arg0, arg1);
	} catch (MappingException e) {
	    throw new RecMappingException(e);
	}
    }

}
