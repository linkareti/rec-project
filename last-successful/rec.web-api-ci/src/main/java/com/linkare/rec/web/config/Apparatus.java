package com.linkare.rec.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Apparatus extends DisplayNode {

	/**
	 * Experiments are not virtual by default. A virtual experiment does not
	 * have hardware and generates samples by means of a formula
	 */
	private boolean virtual = false;

	/**
	 * The title of the experiment - defined as a key in a ReCResourceBundle
	 */
	private String displayStringBundleKey = "";

	/**
	 * The description text to appear in the experiment background in the
	 * experiment content pane on connect
	 */
	private String descriptionStringBundleKey = "";

	/**
	 * The icon for the experiment, defined as a ReCResourceBundle bundle key
	 */
	private String iconLocationBundleKey = "";

	/**
	 * The background image of the experiment description pane, defined as a
	 * ReCResourceBundle bundle key
	 */
	private String desktopLocationBundleKey = "";

	/**
	 * The tooltip for the experiment, defined as a ReCResourceBundle bundle
	 * key. Currently not used by the new interface
	 */
	private String toolTipBundleKey = "";

	/**
	 * The class that implements ExpDataModel in elab, defined as a
	 * ReCResourceBundle bundle key This is not required and it is considered an
	 * extension point
	 */
	private String dataModelClassLocationBundleKey = "";

	/**
	 * The class for a header display to put on experiments, defined as a
	 * ReCResourceBundle bundle key Currently not used, not required and should
	 * be considered as an extension point
	 */
	private String headerDisplayClassLocationBundleKey = "";

	/**
	 * The class that should implement DisplayFactory interface defined as a
	 * ReCResourceBundle bundle key This is not required, and should be regarded
	 * as an extension point
	 */
	private String displayFactoryClassLocationBundleKey = "";

	/**
	 * The class that should implement ICustomizer interface defined as a
	 * ReCResourceBundle bundle key This is required!
	 */
	private String customizerClassLocationBundleKey = "";

	/**
	 * The unique ID of the experiment as defined in the HardwareInfo data
	 * structure in ReC
	 */
	private String location = "";

	/**
	 * Substructure defining the configuration for media integration in the
	 * experiment, mainly the video
	 */
	private MediaConfig mediaConfig = null;

	/**
	 * The default acquisition configurations. Currently not used!
	 */
	private List<DefaultAcquisitionConfig> defaultAcquisitionConfig = new ArrayList<DefaultAcquisitionConfig>();

	/**
	 * The list of displays to use in the experiment
	 */
	private List<Display> display = new ArrayList<Display>();

	/**
	 * The list of URL's to present in the apparatus description pane. For now,
	 * the GUI only shows the first element, although this is expected to
	 * change.
	 */
	private List<WebResource> webResource = new ArrayList<WebResource>();

	/**
	 * The localization ReCResourceBundles to use. Defined by a name that should
	 * follow some naming conventions to avoid collision with other bundles in
	 * the ReC client
	 */
	private List<LocalizationBundle> localizationBundle = new ArrayList<LocalizationBundle>();

	public Apparatus() {
		super();
	}

	/**
	 * @return the displayStringBundleKey
	 */
	@XmlAttribute
	public String getDisplayStringBundleKey() {
		return displayStringBundleKey;
	}

	@XmlAttribute
	public String getDescriptionStringBundleKey() {
		return descriptionStringBundleKey;
	}

	/**
	 * @return the iconLocationBundleKey
	 */
	@XmlAttribute
	public String getIconLocationBundleKey() {
		return iconLocationBundleKey;
	}

	/**
	 * @return the desktopLocationBundleKey
	 */
	@XmlAttribute
	public String getDesktopLocationBundleKey() {
		return desktopLocationBundleKey;
	}

	/**
	 * @return the toolTipBundleKey
	 */
	@XmlAttribute
	public String getToolTipBundleKey() {
		return toolTipBundleKey;
	}

	/**
	 * @return the dataModelClassLocationBundleKey
	 */
	@XmlAttribute
	public String getDataModelClassLocationBundleKey() {
		return dataModelClassLocationBundleKey;
	}

	/**
	 * @return the headerDisplayClassLocationBundleKey
	 */
	@XmlAttribute
	public String getHeaderDisplayClassLocationBundleKey() {
		return headerDisplayClassLocationBundleKey;
	}

	/**
	 * @return the displayFactoryClassLocationBundleKey
	 */
	@XmlAttribute
	public String getDisplayFactoryClassLocationBundleKey() {
		return displayFactoryClassLocationBundleKey;
	}

	/**
	 * @return the customizerClassLocationBundleKey
	 */
	@XmlAttribute
	public String getCustomizerClassLocationBundleKey() {
		return customizerClassLocationBundleKey;
	}

	/**
	 * @return the location
	 */
	@XmlAttribute
	public String getLocation() {
		return location;
	}

	/**
	 * @return the mediaConfig
	 */
	@XmlElement
	public MediaConfig getMediaConfig() {
		return mediaConfig;
	}

	/**
	 * @return the defaultAcquisitionConfig
	 */
	@XmlElement
	public List<DefaultAcquisitionConfig> getDefaultAcquisitionConfig() {
		return defaultAcquisitionConfig;
	}

	/**
	 * @return the display
	 */
	@XmlElement
	public List<Display> getDisplay() {
		return display;
	}

	/**
	 * @return the webResource
	 */
	@XmlElement
	public List<WebResource> getWebResource() {
		return webResource;
	}

	/**
	 * @return the localizationBundle
	 */
	@XmlElement
	public List<LocalizationBundle> getLocalizationBundle() {
		return localizationBundle;
	}

	/**
	 * @param displayStringBundleKey
	 *            the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
	}

	/**
	 * @param descriptionStringBundleKey
	 *            the descriptionStringBundleKey to set
	 */
	public void setDescriptionStringBundleKey(
			final String descriptionStringBundleKey) {
		this.descriptionStringBundleKey = descriptionStringBundleKey;
	}

	/**
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param desktopLocationBundleKey
	 *            the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(
			final String desktopLocationBundleKey) {
		this.desktopLocationBundleKey = desktopLocationBundleKey;
	}

	/**
	 * @param toolTipBundleKey
	 *            the toolTipBundleKey to set
	 */
	public void setToolTipBundleKey(final String toolTipBundleKey) {
		this.toolTipBundleKey = toolTipBundleKey;
	}

	/**
	 * @param dataModelClassLocationBundleKey
	 *            the dataModelClassLocationBundleKey to set
	 */
	public void setDataModelClassLocationBundleKey(
			final String dataModelClassLocationBundleKey) {
		this.dataModelClassLocationBundleKey = dataModelClassLocationBundleKey;
	}

	/**
	 * @param headerDisplayClassLocationBundleKey
	 *            the headerDisplayClassLocationBundleKey to set
	 */
	public void setHeaderDisplayClassLocationBundleKey(
			final String headerDisplayClassLocationBundleKey) {
		this.headerDisplayClassLocationBundleKey = headerDisplayClassLocationBundleKey;
	}

	/**
	 * @param displayFactoryClassLocationBundleKey
	 *            the displayFactoryClassLocationBundleKey to set
	 */
	public void setDisplayFactoryClassLocationBundleKey(
			final String displayFactoryClassLocationBundleKey) {
		this.displayFactoryClassLocationBundleKey = displayFactoryClassLocationBundleKey;
	}

	/**
	 * @param customizerClassLocationBundleKey
	 *            the customizerClassLocationBundleKey to set
	 */
	public void setCustomizerClassLocationBundleKey(
			final String customizerClassLocationBundleKey) {
		this.customizerClassLocationBundleKey = customizerClassLocationBundleKey;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @param mediaConfig
	 *            the mediaConfig to set
	 */
	public void setMediaConfig(final MediaConfig mediaConfig) {
		this.mediaConfig = mediaConfig;
	}

	/**
	 * @param defaultAcquisitionConfig
	 *            the defaultAcquisitionConfig to set
	 */
	public void setDefaultAcquisitionConfig(
			final List<DefaultAcquisitionConfig> defaultAcquisitionConfig) {
		this.defaultAcquisitionConfig = defaultAcquisitionConfig;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(final List<Display> display) {
		this.display = display;
	}

	/**
	 * @param webResource
	 *            the webResource to set
	 */
	public void setWebResource(final List<WebResource> webResource) {
		this.webResource = webResource;
	}

	/**
	 * @param localizationBundle
	 *            the localizationBundle to set
	 */
	public void setLocalizationBundle(
			final List<LocalizationBundle> localizationBundle) {
		this.localizationBundle = localizationBundle;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Apparatus other = (Apparatus) obj;

		if (virtual != other.virtual) {
			return false;
		}

		if ((displayStringBundleKey == null) ? (other.displayStringBundleKey != null)
				: !displayStringBundleKey.equals(other.displayStringBundleKey)) {
			return false;
		}
		if ((iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null)
				: !iconLocationBundleKey.equals(other.iconLocationBundleKey)) {
			return false;
		}
		if ((desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null)
				: !desktopLocationBundleKey
						.equals(other.desktopLocationBundleKey)) {
			return false;
		}
		if ((toolTipBundleKey == null) ? (other.toolTipBundleKey != null)
				: !toolTipBundleKey.equals(other.toolTipBundleKey)) {
			return false;
		}
		if ((dataModelClassLocationBundleKey == null) ? (other.dataModelClassLocationBundleKey != null)
				: !dataModelClassLocationBundleKey
						.equals(other.dataModelClassLocationBundleKey)) {
			return false;
		}
		// Delete
		// if ((this.videoLocation == null) ? (other.videoLocation != null) :
		// !this.videoLocation.equals(other.videoLocation)) {
		// return false;
		// }
		if ((headerDisplayClassLocationBundleKey == null) ? (other.headerDisplayClassLocationBundleKey != null)
				: !headerDisplayClassLocationBundleKey
						.equals(other.headerDisplayClassLocationBundleKey)) {
			return false;
		}
		if ((displayFactoryClassLocationBundleKey == null) ? (other.displayFactoryClassLocationBundleKey != null)
				: !displayFactoryClassLocationBundleKey
						.equals(other.displayFactoryClassLocationBundleKey)) {
			return false;
		}
		if ((customizerClassLocationBundleKey == null) ? (other.customizerClassLocationBundleKey != null)
				: !customizerClassLocationBundleKey
						.equals(other.customizerClassLocationBundleKey)) {
			return false;
		}
		if ((location == null) ? (other.location != null) : !location
				.equals(other.location)) {
			return false;
		}
		if (mediaConfig != other.mediaConfig
				&& (mediaConfig == null || !mediaConfig
						.equals(other.mediaConfig))) {
			return false;
		}
		if (defaultAcquisitionConfig != other.defaultAcquisitionConfig
				&& (defaultAcquisitionConfig == null || !defaultAcquisitionConfig
						.equals(other.defaultAcquisitionConfig))) {
			return false;
		}
		if (display != other.display
				&& (display == null || !display.equals(other.display))) {
			return false;
		}
		if (webResource != other.webResource
				&& (webResource == null || !webResource
						.equals(other.webResource))) {
			return false;
		}
		if (localizationBundle != other.localizationBundle
				&& (localizationBundle == null || !localizationBundle
						.equals(other.localizationBundle))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + (virtual ? 1 : 0);

		hash = 83
				* hash
				+ (displayStringBundleKey != null ? displayStringBundleKey
						.hashCode() : 0);
		hash = 83
				* hash
				+ (iconLocationBundleKey != null ? iconLocationBundleKey
						.hashCode() : 0);
		hash = 83
				* hash
				+ (desktopLocationBundleKey != null ? desktopLocationBundleKey
						.hashCode() : 0);
		hash = 83 * hash
				+ (toolTipBundleKey != null ? toolTipBundleKey.hashCode() : 0);
		hash = 83
				* hash
				+ (dataModelClassLocationBundleKey != null ? dataModelClassLocationBundleKey
						.hashCode() : 0);
		// Delete
		// hash = 83 * hash + (this.videoLocation != null ?
		// this.videoLocation.hashCode() : 0);
		hash = 83
				* hash
				+ (headerDisplayClassLocationBundleKey != null ? headerDisplayClassLocationBundleKey
						.hashCode() : 0);
		hash = 83
				* hash
				+ (displayFactoryClassLocationBundleKey != null ? displayFactoryClassLocationBundleKey
						.hashCode() : 0);
		hash = 83
				* hash
				+ (customizerClassLocationBundleKey != null ? customizerClassLocationBundleKey
						.hashCode() : 0);
		hash = 83 * hash + (location != null ? location.hashCode() : 0);
		hash = 83 * hash + (mediaConfig != null ? mediaConfig.hashCode() : 0);
		hash = 83
				* hash
				+ (defaultAcquisitionConfig != null ? defaultAcquisitionConfig
						.hashCode() : 0);
		hash = 83 * hash + (display != null ? display.hashCode() : 0);
		hash = 83 * hash + (webResource != null ? webResource.hashCode() : 0);
		hash = 83
				* hash
				+ (localizationBundle != null ? localizationBundle.hashCode()
						: 0);
		return hash;
	}

	/**
	 * @return the virtual
	 */
	@XmlAttribute(required = false)
	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * @param virtual
	 *            the virtual to set
	 */
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

}
