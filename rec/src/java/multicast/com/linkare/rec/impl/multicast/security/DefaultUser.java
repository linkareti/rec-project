/*
 * DefaultUser.java
 *
 * Created on 2 de Janeiro de 2004, 16:02
 */
package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.Any;

import com.linkare.rec.acquisition.Property;
import com.linkare.rec.acquisition.UserInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultUser implements IUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4436155536469038628L;
	private String userName;
	private byte[] auth;
	private HashMap<String, Any> properties;

	/** Creates a new instance of DefaultUser */
	public DefaultUser() {
		setUserName("<empty user name>");
		setAuth("<empty pass>".getBytes());
		setProperties(new HashMap<String, Any>());
	}

	public DefaultUser(final UserInfo userInfo) {
		setUserName(userInfo.getUserName());
		final String pass = userInfo.getPassword();
		if (pass != null) {
			setAuth(pass.getBytes());
		} else {
			setAuth(userInfo.getCertificate());
		}

		final Property[] props = userInfo.getUserProps();

		setProperties(new HashMap<String, Any>());

		for (final Property prop : props) {
			getProperties().put(prop.getName(), prop.getValue());
		}

	}

	/**
	 * Getter for property userName.
	 * 
	 * @return Value of property userName.
	 * 
	 */
	@Override
	public java.lang.String getUserName() {
		return userName;
	}

	/**
	 * Setter for property userName.
	 * 
	 * @param userName New value of property userName.
	 * 
	 */
	public void setUserName(final java.lang.String userName) {
		this.userName = userName;
	}

	/**
	 * Getter for property auth.
	 * 
	 * @return Value of property auth.
	 * 
	 */
	@Override
	public byte[] getAuth() {
		return auth;
	}

	/**
	 * Setter for property auth.
	 * 
	 * @param auth New value of property auth.
	 * 
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public void setAuth(final byte[] auth) {
		this.auth = auth;
	}

	/**
	 * Getter for property properties.
	 * 
	 * @return Value of property properties.
	 * 
	 */
	@Override
	public Map<String, Any> getProperties() {
		return properties;
	}

	/**
	 * Setter for property properties.
	 * 
	 * @param properties New value of property properties.
	 * 
	 */
	public void setProperties(final Map<String, Any> properties) {
		this.properties = new HashMap<String, Any>(properties);
	}

	@Override
	public String toString() {
		return getUserName();
	}

    @Override
    public int hashCode() {
        return getUserName().hashCode() * 13;
    }
    
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof IUser)) {
			return false;
		}

		final IUser otherUser = (IUser) other;

		return (getUserName() == null ? otherUser.getUserName() == null : getUserName().equals(otherUser.getUserName()));

	}

}
