package com.linkare.rec.data;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class MultiplierBeanInfo extends SimpleBeanInfo {

	// Bean descriptor//GEN-FIRST:BeanDescriptor
	private static BeanDescriptor beanDescriptor = new BeanDescriptor(Multiplier.class,
			com.linkare.customizer.DefaultPropertySheetCustomizer.class);

	private static BeanDescriptor getBdescriptor() {
		return MultiplierBeanInfo.beanDescriptor;
	}

	static {
		MultiplierBeanInfo.beanDescriptor.setDisplayName("Multiplicador");
		MultiplierBeanInfo.beanDescriptor.setShortDescription("Define um multiplicador para valores fisicos...");// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

	}// GEN-LAST:BeanDescriptor

	// Property identifiers//GEN-FIRST:Properties
	private static final int PROPERTY_expValue = 0;
	private static final int PROPERTY_value = 1;

	// Property array
	private static PropertyDescriptor[] properties = new PropertyDescriptor[2];

	private static PropertyDescriptor[] getPdescriptor() {
		return MultiplierBeanInfo.properties;
	}

	static {
		try {
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_expValue] = new PropertyDescriptor("expValue",
					Multiplier.class, "getExpValue", null);
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_value] = new PropertyDescriptor("value",
					Multiplier.class, "getValue", "setValue");
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_value].setPreferred(true);
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_value].setDisplayName("Multiplicador");
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_value]
					.setShortDescription("O valor do multiplicador...");
			MultiplierBeanInfo.properties[MultiplierBeanInfo.PROPERTY_value]
					.setPropertyEditorClass(com.linkare.rec.data.MultiplierValueEditor.class);
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

	}// GEN-LAST:Properties

	// EventSet identifiers//GEN-FIRST:Events

	// EventSet array
	private static EventSetDescriptor[] eventSets = new EventSetDescriptor[0];

	private static EventSetDescriptor[] getEdescriptor() {
		return MultiplierBeanInfo.eventSets;
	}

	// GEN-HEADEREND:Events

	// Here you can add code for customizing the event sets array.

	// GEN-LAST:Events

	// Method identifiers//GEN-FIRST:Methods
	private static final int METHOD_equals0 = 0;
	private static final int METHOD_toExpString1 = 1;
	private static final int METHOD_toString2 = 2;

	// Method array
	private static MethodDescriptor[] methods = new MethodDescriptor[3];

	private static MethodDescriptor[] getMdescriptor() {
		return MultiplierBeanInfo.methods;
	}

	static {
		try {
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_equals0] = new MethodDescriptor(
					com.linkare.rec.data.Multiplier.class.getMethod("equals", new Class[] { java.lang.Object.class }));
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_equals0].setDisplayName("");
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_toExpString1] = new MethodDescriptor(
					com.linkare.rec.data.Multiplier.class.getMethod("toExpString", new Class[] {}));
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_toExpString1].setDisplayName("");
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_toString2] = new MethodDescriptor(
					com.linkare.rec.data.Multiplier.class.getMethod("toString", new Class[] {}));
			MultiplierBeanInfo.methods[MultiplierBeanInfo.METHOD_toString2].setDisplayName("");
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
		return MultiplierBeanInfo.getBdescriptor();
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
		return MultiplierBeanInfo.getPdescriptor();
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
		return MultiplierBeanInfo.getEdescriptor();
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
		return MultiplierBeanInfo.getMdescriptor();
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
		return MultiplierBeanInfo.defaultPropertyIndex;
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
		return MultiplierBeanInfo.defaultEventIndex;
	}
}
