package com.linkare.rec.impl.newface.config.migration;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class MyPropertyUtils extends PropertyUtilsBean {

    private static final Logger log = Logger.getLogger(MyPropertyUtils.class.getName());

    private Map<String, List<String>> unmappedDestProperties;

    public MyPropertyUtils() {
	unmappedDestProperties = new HashMap<String, List<String>>();
    }

    /**
     * Copy properties in deep mode. Handles Array to List<?> conversion.
     * 
     * Works for Beans and ?Maps? as input. TODO Test for Maps.
     * 
     * @param dest
     * @param orig
     * @param discardDestProperties
     * 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * 
     * @see org.apache.commons.beanutils.PropertyUtilsBean#copyProperties(java.lang.Object, java.lang.Object)
     */
    public void deepCopyProperties(Object dest, Object orig, Set<String> discardDestProperties)
	    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
	    NoSuchFieldException, InstantiationException {

	deepCopyPropertiesRecursive(dest, orig, discardDestProperties);
	unmappedDestProperties.clear();
    }

    private void deepCopyPropertiesRecursive(Object dest, Object orig, Set<String> discardDestProperties)
	    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
	    NoSuchFieldException, InstantiationException {

	// Copy matching properties and get the unmapped list 
	super.copyProperties(dest, orig);

	List<String> unmappedList = unmappedDestProperties.get(getPropertyKey(dest));
	if (unmappedList == null) {
	    return; // No more unmapped properties to process
	}

	for (String propName : unmappedList) {
	    log.info("Mapping property " + propName);

	    // Get info for origin and destination properties
	    Object origProperty = getProperty(orig, propName);
	    Object destProperty = getProperty(dest, propName);
	    PropertyDescriptor destPropertyDesc = getPropertyDescriptor(dest, propName);
	    Type destPropertyType = destPropertyDesc.getReadMethod().getGenericReturnType();

	    // Perform Array to List<?> mapping
	    convertArrayToList(destProperty, origProperty, destPropertyType, discardDestProperties);
	}
    }

    /**
     * Array to List<?> conversion handler.
     * 
     * @param origProperty
     *            The origin Array reference.
     * @param destProperty
     *            The destination List<?> reference.
     * @param destPropertyType
     * @param discardDestProperties
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     */
    private void convertArrayToList(Object destProperty, Object origProperty, Type destPropertyType,
	    Set<String> discardDestProperties) throws InstantiationException, IllegalAccessException,
	    InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
	ParameterizedType destParameterizedType;

	if (!(origProperty.getClass().isArray() && destProperty instanceof List<?>)) {
	    log.warning("Not converting from Array to List");
	    return;
	}

	if (destPropertyType instanceof ParameterizedType) {
	    destParameterizedType = (ParameterizedType) destPropertyType;
	    // TODO Check this cast
	    Class<?> destListParameterizedClass = (Class<?>) destParameterizedType.getActualTypeArguments()[0];
	    log.info("Handling parameterized type conversion: " + destListParameterizedClass);

	    int origArrayLength = Array.getLength(origProperty);
	    // Iterate origin Array elements
	    for (int i = 0; i < origArrayLength; i++) {
		Object origArrayProperty = Array.get(origProperty, i);
		Object newDestObject = destListParameterizedClass.newInstance();
		log.info("Value copy: Array -> List " + origArrayProperty + " => " + newDestObject);

		deepCopyPropertiesRecursive(newDestObject, origArrayProperty, discardDestProperties);

		// Add to the desitnation
		((List) destProperty).add(newDestObject);
	    }

	} else {
	    log.warning("Cannot infer destination value type.");
	    // TODO get the mapping type from a given list...
	}
    }

    /*
     * TODO Check if the override should be on setProperty instead
     * 
     * @see org.apache.commons.beanutils.PropertyUtilsBean#setSimpleProperty(java.lang .Object, java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setSimpleProperty(Object bean, String name, Object value) throws IllegalAccessException,
	    InvocationTargetException, NoSuchMethodException {
	try {
	    super.setSimpleProperty(bean, name, value);

	} catch (IllegalArgumentException e) {
	    // Thrown when the property has the same name on origin and destination
	    // But with different types
	    log.warning(e.getMessage());
	    String propertyKey = getPropertyKey(bean);
	    if (!unmappedDestProperties.containsKey(propertyKey)) {
		unmappedDestProperties.put(propertyKey, new ArrayList<String>());
	    }
	    unmappedDestProperties.get(propertyKey).add(name);
	}
    }

    private String getPropertyKey(Object value) {
	return value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
    }

}
