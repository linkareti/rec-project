package com.linkare.rec.data.base;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class MultiplierLabelBeanInfo extends SimpleBeanInfo

{

	// Bean descriptor //GEN-FIRST:BeanDescriptor
	private static BeanDescriptor beanDescriptor = new BeanDescriptor(MultiplierLabel.class,
			com.linkare.customizer.DefaultPropertySheetCustomizer.class);

	private static BeanDescriptor getBdescriptor() {
		return MultiplierLabelBeanInfo.beanDescriptor;
	}

	static {
		MultiplierLabelBeanInfo.beanDescriptor.setDisplayName("LblMultiplier");
		MultiplierLabelBeanInfo.beanDescriptor.setShortDescription("Uma label para multipliers");// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

	}// GEN-LAST:BeanDescriptor

	// Property identifiers //GEN-FIRST:Properties
	private static final int PROPERTY_multiplier = 0;
	private static final int PROPERTY_text = 1;

	// Property array
	private static PropertyDescriptor[] properties = new PropertyDescriptor[2];

	private static PropertyDescriptor[] getPdescriptor() {
		return MultiplierLabelBeanInfo.properties;
	}

	static {
		try {
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_multiplier] = new PropertyDescriptor(
					"multiplier", MultiplierLabel.class, "getMultiplier", "setMultiplier");
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_multiplier].setPreferred(true);
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_multiplier]
					.setDisplayName("Multiplicador");
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_multiplier]
					.setPropertyEditorClass(com.linkare.rec.data.MultiplierEditor.class);
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_text] = new PropertyDescriptor("text",
					MultiplierLabel.class, "getText", null);
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_text].setHidden(true);
			MultiplierLabelBeanInfo.properties[MultiplierLabelBeanInfo.PROPERTY_text].setPropertyEditorClass(null);
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

	}// GEN-LAST:Properties

	// Event set information will be obtained from
	// introspection.//GEN-FIRST:Events
	private static EventSetDescriptor[] eventSets = null;

	private static EventSetDescriptor[] getEdescriptor() {// GEN-HEADEREND:Events

		// Here you can add code for customizing the event sets array.

		return MultiplierLabelBeanInfo.eventSets;
	} // GEN-LAST:Events

	// Method information will be obtained from
	// introspection.//GEN-FIRST:Methods
	private static MethodDescriptor[] methods = null;

	private static MethodDescriptor[] getMdescriptor() {// GEN-HEADEREND:Methods

		// Here you can add code for customizing the methods array.

		return MultiplierLabelBeanInfo.methods;
	} // GEN-LAST:Methods

	private static java.awt.Image iconColor16 = null; // GEN-BEGIN:IconsDef

	private static java.awt.Image iconColor32 = null;

	private static java.awt.Image iconMono16 = null;

	private static java.awt.Image iconMono32 = null; // GEN-END:IconsDef

	private static String iconNameC16 = "/com/linkare/rec/Data/base/MultiplierLabelIcon16.gif";// GEN-BEGIN:Icons
	private static String iconNameC32 = "/com/linkare/rec/Data/base/MultiplierLabelIcon32.gif";
	private static String iconNameM16 = null;
	private static String iconNameM32 = null;// GEN-END:Icons

	private static final int defaultPropertyIndex = -1;// GEN-BEGIN:Idx
	private static final int defaultEventIndex = -1;// GEN-END:Idx

	@Override
	public BeanInfo[] getAdditionalBeanInfo() {// GEN-FIRST:Superclass
		final Class<?> superclass = MultiplierLabel.class.getSuperclass();
		BeanInfo sbi = null;
		try {
			sbi = Introspector.getBeanInfo(superclass);// GEN-HEADEREND:Superclass

			// Here you can add code for customizing the Superclass BeanInfo.

		} catch (final IntrospectionException ex) {
		}
		return new BeanInfo[] { sbi };
	}// GEN-LAST:Superclass

	/**
	 * 
	 * Gets the bean's <code>BeanDescriptor</code>s.
	 * 
	 * 
	 * 
	 * @return BeanDescriptor describing the editable
	 * 
	 *         properties of this bean. May return null if the
	 * 
	 *         information should be obtained by automatic analysis.
	 */

	@Override
	public BeanDescriptor getBeanDescriptor()

	{

		return MultiplierLabelBeanInfo.getBdescriptor();

	}

	/**
	 * 
	 * Gets the bean's <code>PropertyDescriptor</code>s.
	 * 
	 * 
	 * 
	 * @return An array of PropertyDescriptors describing the editable
	 * 
	 *         properties supported by this bean. May return null if the
	 * 
	 *         information should be obtained by automatic analysis.
	 * 
	 *         <p>
	 * 
	 *         If a property is indexed, then its entry in the result array will
	 * 
	 *         belong to the IndexedPropertyDescriptor subclass of
	 *         PropertyDescriptor.
	 * 
	 *         A client of getPropertyDescriptors can use "instanceof" to check
	 * 
	 *         if a given PropertyDescriptor is an IndexedPropertyDescriptor.
	 */

	@Override
	public PropertyDescriptor[] getPropertyDescriptors()

	{

		return MultiplierLabelBeanInfo.getPdescriptor();

	}

	/**
	 * 
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 * 
	 * 
	 * 
	 * @return An array of EventSetDescriptors describing the kinds of
	 * 
	 *         events fired by this bean. May return null if the information
	 * 
	 *         should be obtained by automatic analysis.
	 */

	@Override
	public EventSetDescriptor[] getEventSetDescriptors()

	{

		return MultiplierLabelBeanInfo.getEdescriptor();

	}

	/**
	 * 
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 * 
	 * 
	 * 
	 * @return An array of MethodDescriptors describing the methods
	 * 
	 *         implemented by this bean. May return null if the information
	 * 
	 *         should be obtained by automatic analysis.
	 */

	@Override
	public MethodDescriptor[] getMethodDescriptors()

	{

		return MultiplierLabelBeanInfo.getMdescriptor();

	}

	/**
	 * 
	 * A bean may have a "default" property that is the property that will
	 * 
	 * mostly commonly be initially chosen for update by human's who are
	 * 
	 * customizing the bean.
	 * 
	 * @return Index of default property in the PropertyDescriptor array
	 * 
	 *         returned by getPropertyDescriptors.
	 * 
	 *         <P>
	 *         Returns -1 if there is no default property.
	 */

	@Override
	public int getDefaultPropertyIndex()

	{

		return MultiplierLabelBeanInfo.defaultPropertyIndex;

	}

	/**
	 * 
	 * A bean may have a "default" event that is the event that will
	 * 
	 * mostly commonly be used by human's when using the bean.
	 * 
	 * @return Index of default event in the EventSetDescriptor array
	 * 
	 *         returned by getEventSetDescriptors.
	 * 
	 *         <P>
	 *         Returns -1 if there is no default event.
	 */

	@Override
	public int getDefaultEventIndex()

	{

		return MultiplierLabelBeanInfo.defaultEventIndex;

	}

	/**
	 * 
	 * This method returns an image object that can be used to
	 * 
	 * represent the bean in toolboxes, toolbars, etc. Icon images
	 * 
	 * will typically be GIFs, but may in future include other formats.
	 * 
	 * <p>
	 * 
	 * Beans aren't required to provide icons and may return null from
	 * 
	 * this method.
	 * 
	 * <p>
	 * 
	 * There are four possible flavors of icons (16x16 color,
	 * 
	 * 32x32 color, 16x16 mono, 32x32 mono). If a bean choses to only
	 * 
	 * support a single icon we recommend supporting 16x16 color.
	 * 
	 * <p>
	 * 
	 * We recommend that icons have a "transparent" background
	 * 
	 * so they can be rendered onto an existing background.
	 * 
	 * 
	 * 
	 * @param iconKind The kind of icon requested. This should be
	 * 
	 *            one of the constant values ICON_COLOR_16x16, ICON_COLOR_32x32,
	 * 
	 *            ICON_MONO_16x16, or ICON_MONO_32x32.
	 * 
	 * @return An image object representing the requested icon. May
	 * 
	 *         return null if no suitable icon is available.
	 */

	@Override
	public java.awt.Image getIcon(final int iconKind)

	{

		switch (iconKind)

		{

		case ICON_COLOR_16x16:

			if (MultiplierLabelBeanInfo.iconNameC16 == null) {
				return null;
			} else

			{

				if (MultiplierLabelBeanInfo.iconColor16 == null) {
					MultiplierLabelBeanInfo.iconColor16 = loadImage(MultiplierLabelBeanInfo.iconNameC16);
				}

				return MultiplierLabelBeanInfo.iconColor16;

			}

		case ICON_COLOR_32x32:

			if (MultiplierLabelBeanInfo.iconNameC32 == null) {
				return null;
			} else

			{

				if (MultiplierLabelBeanInfo.iconColor32 == null) {
					MultiplierLabelBeanInfo.iconColor32 = loadImage(MultiplierLabelBeanInfo.iconNameC32);
				}

				return MultiplierLabelBeanInfo.iconColor32;

			}

		case ICON_MONO_16x16:

			if (MultiplierLabelBeanInfo.iconNameM16 == null) {
				return null;
			} else

			{

				if (MultiplierLabelBeanInfo.iconMono16 == null) {
					MultiplierLabelBeanInfo.iconMono16 = loadImage(MultiplierLabelBeanInfo.iconNameM16);
				}

				return MultiplierLabelBeanInfo.iconMono16;

			}

		case ICON_MONO_32x32:

			if (MultiplierLabelBeanInfo.iconNameM32 == null) {
				return null;
			} else

			{

				if (MultiplierLabelBeanInfo.iconMono32 == null) {
					MultiplierLabelBeanInfo.iconMono32 = loadImage(MultiplierLabelBeanInfo.iconNameM32);
				}

				return MultiplierLabelBeanInfo.iconMono32;

			}

		default:
			return null;

		}

	}

}