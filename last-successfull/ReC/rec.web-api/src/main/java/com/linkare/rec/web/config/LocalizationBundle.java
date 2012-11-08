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
	 * @param location the location to set
	 */
	public void setLocation(final java.lang.String location) {
		this.location = location;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final java.lang.String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LocalizationBundle other = (LocalizationBundle) obj;
		if ((location == null) ? (other.location != null) : !location.equals(other.location)) {
			return false;
		}
		if ((name == null) ? (other.name != null) : !name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (location != null ? location.hashCode() : 0);
		hash = 97 * hash + (name != null ? name.hashCode() : 0);
		return hash;
	}

}
