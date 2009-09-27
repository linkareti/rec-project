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
		return beanDescriptor;
	}

	static {
		beanDescriptor.setDisplayName("Multiplicador");
		beanDescriptor.setShortDescription("Define um multiplicador para valores fisicos...");// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

	}// GEN-LAST:BeanDescriptor

	// Property identifiers//GEN-FIRST:Properties
	private static final int PROPERTY_expValue = 0;
	private static final int PROPERTY_value = 1;

	// Property array
	private static PropertyDescriptor[] properties = new PropertyDescriptor[2];

	private static PropertyDescriptor[] getPdescriptor() {
		return properties;
	}

	static {
		try {
			properties[PROPERTY_expValue] = new PropertyDescriptor("expValue", Multiplier.class, "getExpValue", null);
			properties[PROPERTY_value] = new PropertyDescriptor("value", Multiplier.class, "getValue", "setValue");
			properties[PROPERTY_value].setPreferred(true);
			properties[PROPERTY_value].setDisplayName("Multiplicador");
			properties[PROPERTY_value].setShortDescription("O valor do multiplicador...");
			properties[PROPERTY_value].setPropertyEditorClass(com.linkare.rec.data.MultiplierValueEditor.class);
		} catch (IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

	}// GEN-LAST:Properties

	// EventSet identifiers//GEN-FIRST:Events

	// EventSet array
	private static EventSetDescriptor[] eventSets = new EventSetDescriptor[0];

	private static EventSetDescriptor[] getEdescriptor() {
		return eventSets;
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
		return methods;
	}

	static {
		try {
			methods[METHOD_equals0] = new MethodDescriptor(com.linkare.rec.data.Multiplier.class.getMethod("equals",
					new Class[] { java.lang.Object.class }));
			methods[METHOD_equals0].setDisplayName("");
			methods[METHOD_toExpString1] = new MethodDescriptor(com.linkare.rec.data.Multiplier.class.getMethod(
					"toExpString", new Class[] {}));
			methods[METHOD_toExpString1].setDisplayName("");
			methods[METHOD_toString2] = new MethodDescriptor(com.linkare.rec.data.Multiplier.class.getMethod(
					"toString", new Class[] {}));
			methods[METHOD_toString2].setDisplayName("");
		} catch (Exception e) {
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
	public BeanDescriptor getBeanDescriptor() {
		return getBdescriptor();
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
	public PropertyDescriptor[] getPropertyDescriptors() {
		return getPdescriptor();
	}

	/**
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 * 
	 * @return An array of EventSetDescriptors describing the kinds of events
	 *         fired by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	public EventSetDescriptor[] getEventSetDescriptors() {
		return getEdescriptor();
	}

	/**
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 * 
	 * @return An array of MethodDescriptors describing the methods implemented
	 *         by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	public MethodDescriptor[] getMethodDescriptors() {
		return getMdescriptor();
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
	public int getDefaultPropertyIndex() {
		return defaultPropertyIndex;
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
	public int getDefaultEventIndex() {
		return defaultEventIndex;
	}
}
