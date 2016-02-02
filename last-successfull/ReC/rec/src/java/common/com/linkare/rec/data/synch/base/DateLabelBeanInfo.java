package com.linkare.rec.data.synch.base;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class DateLabelBeanInfo extends SimpleBeanInfo {

	// Bean descriptor //GEN-FIRST:BeanDescriptor
	private static BeanDescriptor beanDescriptor = new BeanDescriptor(DateLabel.class, null);

	private static BeanDescriptor getBdescriptor() {
		return DateLabelBeanInfo.beanDescriptor;
	}

	static {
		DateLabelBeanInfo.beanDescriptor.setDisplayName("ELAB Date Label");
		DateLabelBeanInfo.beanDescriptor.setShortDescription("Label para Dates");// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

	}// GEN-LAST:BeanDescriptor

	// Property identifiers //GEN-FIRST:Properties
	private static final int PROPERTY_date = 0;
	private static final int PROPERTY_text = 1;

	// Property array
	private static PropertyDescriptor[] properties = new PropertyDescriptor[2];

	private static PropertyDescriptor[] getPdescriptor() {
		return DateLabelBeanInfo.properties;
	}

	static {
		try {
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_date] = new PropertyDescriptor("date",
					DateLabel.class, "getDate", "setDate");
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_date].setPreferred(true);
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_date].setDisplayName("Data");
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_date]
					.setPropertyEditorClass(com.linkare.rec.data.synch.DateEditor.class);
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_text] = new PropertyDescriptor("text",
					DateLabel.class, "getText", null);
			DateLabelBeanInfo.properties[DateLabelBeanInfo.PROPERTY_text].setHidden(true);
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

	}// GEN-LAST:Properties

	// EventSet identifiers//GEN-FIRST:Events

	// EventSet array
	private static EventSetDescriptor[] eventSets = new EventSetDescriptor[0];

	private static EventSetDescriptor[] getEdescriptor() {
		return DateLabelBeanInfo.eventSets;
	}

	// GEN-HEADEREND:Events

	// Here you can add code for customizing the event sets array.

	// GEN-LAST:Events

	// Method identifiers //GEN-FIRST:Methods

	// Method array
	private static MethodDescriptor[] methods = new MethodDescriptor[0];

	private static MethodDescriptor[] getMdescriptor() {
		return DateLabelBeanInfo.methods;
	}

	// GEN-HEADEREND:Methods

	// Here you can add code for customizing the methods array.

	// GEN-LAST:Methods

	private static java.awt.Image iconColor16 = null; // GEN-BEGIN:IconsDef
	private static java.awt.Image iconColor32 = null;
	private static java.awt.Image iconMono16 = null;
	private static java.awt.Image iconMono32 = null; // GEN-END:IconsDef
	private static String iconNameC16 = "/com/linkare/rec/Data/Synch/base/DateLabelIcon16.gif";// GEN-BEGIN:Icons
	private static String iconNameC32 = "/com/linkare/rec/Data/Synch/base/DateLabelIcon32.gif";
	private static String iconNameM16 = null;
	private static String iconNameM32 = null;// GEN-END:Icons

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
		return DateLabelBeanInfo.getBdescriptor();
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
		return DateLabelBeanInfo.getPdescriptor();
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
		return DateLabelBeanInfo.getEdescriptor();
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
		return DateLabelBeanInfo.getMdescriptor();
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
		return DateLabelBeanInfo.defaultPropertyIndex;
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
		return DateLabelBeanInfo.defaultEventIndex;
	}

	/**
	 * This method returns an image object that can be used to represent the
	 * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
	 * may in future include other formats.
	 * <p>
	 * Beans aren't required to provide icons and may return null from this
	 * method.
	 * <p>
	 * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16
	 * mono, 32x32 mono). If a bean choses to only support a single icon we
	 * recommend supporting 16x16 color.
	 * <p>
	 * We recommend that icons have a "transparent" background so they can be
	 * rendered onto an existing background.
	 * 
	 * @param iconKind The kind of icon requested. This should be one of the
	 *            constant values ICON_COLOR_16x16, ICON_COLOR_32x32,
	 *            ICON_MONO_16x16, or ICON_MONO_32x32.
	 * @return An image object representing the requested icon. May return null
	 *         if no suitable icon is available.
	 */
	@Override
	public java.awt.Image getIcon(final int iconKind) {
		switch (iconKind) {
		case ICON_COLOR_16x16:
			if (DateLabelBeanInfo.iconNameC16 == null) {
				return null;
			} else {
				if (DateLabelBeanInfo.iconColor16 == null) {
					DateLabelBeanInfo.iconColor16 = loadImage(DateLabelBeanInfo.iconNameC16);
				}
				return DateLabelBeanInfo.iconColor16;
			}
		case ICON_COLOR_32x32:
			if (DateLabelBeanInfo.iconNameC32 == null) {
				return null;
			} else {
				if (DateLabelBeanInfo.iconColor32 == null) {
					DateLabelBeanInfo.iconColor32 = loadImage(DateLabelBeanInfo.iconNameC32);
				}
				return DateLabelBeanInfo.iconColor32;
			}
		case ICON_MONO_16x16:
			if (DateLabelBeanInfo.iconNameM16 == null) {
				return null;
			} else {
				if (DateLabelBeanInfo.iconMono16 == null) {
					DateLabelBeanInfo.iconMono16 = loadImage(DateLabelBeanInfo.iconNameM16);
				}
				return DateLabelBeanInfo.iconMono16;
			}
		case ICON_MONO_32x32:
			if (DateLabelBeanInfo.iconNameM32 == null) {
				return null;
			} else {
				if (DateLabelBeanInfo.iconMono32 == null) {
					DateLabelBeanInfo.iconMono32 = loadImage(DateLabelBeanInfo.iconNameM32);
				}
				return DateLabelBeanInfo.iconMono32;
			}
		default:
			return null;
		}
	}

}
