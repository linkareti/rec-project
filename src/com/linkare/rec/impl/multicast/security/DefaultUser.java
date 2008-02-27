/*
 * DefaultUser.java
 *
 * Created on 2 de Janeiro de 2004, 16:02
 */

package com.linkare.rec.impl.multicast.security;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.Property;
import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.Any;
/**
 *
 * @author  Administrator
 */
public class DefaultUser implements IUser
{
    private String userName;
    private byte[] auth;
    private HashMap<String, Any> properties;
    
    /** Creates a new instance of DefaultUser */
    public DefaultUser()
    {
	setUserName("<empty user name>");
	setAuth("<empty pass>".getBytes());
	setProperties(new HashMap<String, Any>());
    }
    
    public DefaultUser(UserInfo userInfo)
    {
	setUserName(userInfo.getUserName());
	String pass=userInfo.getPassword();
	if(pass!=null)
	    setAuth(pass.getBytes());
	else
	{
	    setAuth(userInfo.getCertificate());
	}
	
	Property[] props=userInfo.getUserProps();
	
	setProperties(new HashMap<String, Any>());
	
	for(int i=0;i<props.length;i++)
	{
	    getProperties().put(props[i].getName(), props[i].getValue());
	}
	
    }
    /** Getter for property userName.
     * @return Value of property userName.
     *
     */
    public java.lang.String getUserName()
    {
	return userName;
    }    
   
    /** Setter for property userName.
     * @param userName New value of property userName.
     *
     */
    public void setUserName(java.lang.String userName)
    {
	this.userName = userName;
    }    
    
    /** Getter for property auth.
     * @return Value of property auth.
     *
     */
    public byte[] getAuth()
    {
	return this.auth;
    }
    
    /** Setter for property auth.
     * @param auth New value of property auth.
     *
     */
    public void setAuth(byte[] auth)
    {
	this.auth = auth;
    }
    
    /** Getter for property properties.
     * @return Value of property properties.
     *
     */
    public Map<String, Any> getProperties()
    {
	return properties;
    }
    
    /** Setter for property properties.
     * @param properties New value of property properties.
     *
     */
    public void setProperties(Map<String, Any> properties)
    {
	this.properties = new HashMap<String, Any>(properties);
    }
    
    
    public String toString()
    {
	return getUserName();
    }
    
    public boolean equals(Object other)
    {
	if(other==null || !(other instanceof IUser))
	    return false;
	
	IUser otherUser=(IUser) other;
	
	return (getUserName()==null?otherUser.getUserName()==null:getUserName().equals(otherUser.getUserName()) );
	
    }
    
}
