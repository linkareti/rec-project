package com.linkare.rec.data.synch;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class DateBeanInfo extends SimpleBeanInfo {

	// Bean descriptor //GEN-FIRST:BeanDescriptor
	private static BeanDescriptor beanDescriptor = new BeanDescriptor(Date.class,
			com.linkare.rec.data.synch.DateCustomizer.class);

	private static BeanDescriptor getBdescriptor() {
		return DateBeanInfo.beanDescriptor;
	}

	static {// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

	}// GEN-LAST:BeanDescriptor

	// Property identifiers //GEN-FIRST:Properties
	private static final int PROPERTY_month = 0;
	private static final int PROPERTY_day = 1;
	private static final int PROPERTY_year = 2;

	// Property array
	private static PropertyDescriptor[] properties = new PropertyDescriptor[3];

	private static PropertyDescriptor[] getPdescriptor() {
		return DateBeanInfo.properties;
	}

	static {
		try {
			DateBeanInfo.properties[DateBeanInfo.PROPERTY_month] = new PropertyDescriptor("month", Date.class,
					"getMonth", "setMonth");
			DateBeanInfo.properties[DateBeanInfo.PROPERTY_day] = new PropertyDescriptor("day", Date.class, "getDay",
					"setDay");
			DateBeanInfo.properties[DateBeanInfo.PROPERTY_year] = new PropertyDescriptor("year", Date.class, "getYear",
					"setYear");
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

	}// GEN-LAST:Properties

	// EventSet identifiers//GEN-FIRST:Events

	// EventSet array
	private static EventSetDescriptor[] eventSets = new EventSetDescriptor[0];

	private static EventSetDescriptor[] getEdescriptor() {
		return DateBeanInfo.eventSets;
	}

	// GEN-HEADEREND:Events

	// Here you can add code for customizing the event sets array.

	// GEN-LAST:Events

	// Method identifiers //GEN-FIRST:Methods
	private static final int METHOD_toString0 = 0;

	// Method array
	private static MethodDescriptor[] methods = new MethodDescriptor[1];

	private static MethodDescriptor[] getMdescriptor() {
		return DateBeanInfo.methods;
	}

	static {
		try {
			DateBeanInfo.methods[DateBeanInfo.METHOD_toString0] = new MethodDescriptor(
					com.linkare.rec.data.synch.Date.class.getMethod("toString", new Class[] {}));
			DateBeanInfo.methods[DateBeanInfo.METHOD_toString0].setDisplayName("");
		} catch (final Exception e) {
		}// GEN-HEADEREND:Methods

		// Here you can add code for customizing the methods array.

	}// GEN-LAST:Methods

	private static final int defaultPropertyIndex = -1;// GEN-BEGIN:Idx
	private static final int defaultEventIndex = -1;// GEN-END:Idx

	// GEN-FIRST:Superclass

	// Here you can add code for customizing the Superclass BeanInfo.

	// GEN-LAST:Superclass

	/**
	 * Gets the bean's <code>BeanDescriptor</code>s.
	 * 
	 * @return BeanDescriptor describing the editable properties of this bean.
	 *         May return null if the information should be obtained by
	 *         automatic analysis.
	 */
	@Override
	public BeanDescriptor getBeanDescriptor() {
		return DateBeanInfo.getBdescriptor();
	}

	/**
	 * Gets the bean's <code>PropertyDescriptor</code>s.
	 * 
	 * @return An array of PropertyDescriptors describing the editable
	 *         properties supported by this bean. May return null if the
	 *         information should be obtained by automatic analysis.
	 *         <p>
	 *         If a property is indexed, then its entry in the result array will
	 *         belong to the IndexedPropertyDescriptor subclass of
	 *         PropertyDescriptor. A client of getPropertyDescriptors can use
	 *         "instanceof" to check if a given PropertyDescriptor is an
	 *         IndexedPropertyDescriptor.
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		return DateBeanInfo.getPdescriptor();
	}

	/**
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 * 
	 * @return An array of EventSetDescriptors describing the kinds of events
	 *         fired by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	@Override
	public EventSetDescriptor[] getEventSetDescriptors() {
		return DateBeanInfo.getEdescriptor();
	}

	/**
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 * 
	 * @return An array of MethodDescriptors describing the methods implemented
	 *         by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	@Override
	public MethodDescriptor[] getMethodDescriptors() {
		return DateBeanInfo.getMdescriptor();
	}

	/**
	 * A bean may have a "default" property that is the property that will
	 * mostly commonly be initially chosen for update by human's who are
	 * customizing the bean.
	 * 
	 * @return Index of default property in the PropertyDescriptor array
	 *         returned by getPropertyDescriptors.
	 *         <P>
	 *         Returns -1 if there is no default property.
	 */
	@Override
	public int getDefaultPropertyIndex() {
		return DateBeanInfo.defaultPropertyIndex;
	}

	/**
	 * A bean may have a "default" event that is the event that will mostly
	 * commonly be used by human's when using the bean.
	 * 
	 * @return Index of default event in the EventSetDescriptor array returned
	 *         by getEventSetDescriptors.
	 *         <P>
	 *         Returns -1 if there is no default event.
	 */
	@Override
	public int getDefaultEventIndex() {
		return DateBeanInfo.defaultEventIndex;
	}
}
