package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class LocalizationBundle extends AbstractConfigBean {

    private String location;

    private String name;

    public LocalizationBundle() {
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
    public void setLocation(java.lang.String location) {
	this.location = location;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(java.lang.String name) {
	this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final LocalizationBundle other = (LocalizationBundle) obj;
	if ((this.location == null) ? (other.location != null) : !this.location.equals(other.location)) {
	    return false;
	}
	if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 97 * hash + (this.location != null ? this.location.hashCode() : 0);
	hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
	return hash;
    }

}
