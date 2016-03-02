package com.linkare.rec.web.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class LocalizationBundle extends AbstractConfigBean {

	private String location;

	private String name;

	public LocalizationBundle() {
		super();
	}

	/**
	 * @return the location
	 */
	@XmlAttribute
	public java.lang.String getLocation() {
		return location;
	}

	/**
	 * @return the name
	 */
	@XmlAttribute
	public java.lang.String getName() {
		return name;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final java.lang.String location) {
		this.location = location;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final java.lang.String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		boolean retVal = true;
		if (obj == null || getClass() != obj.getClass()) {
			retVal = false;
		} else {
			final LocalizationBundle other = (LocalizationBundle) obj;
			retVal = retVal && nullSafeObjectEquals(location, other.location);
			retVal = retVal && nullSafeObjectEquals(name, other.name);
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + nullObjectSafeHash(location);
		hash = 97 * hash + nullObjectSafeHash(name);
		return hash;
	}

}
