package com.linkare.rec.acquisition;

import org.omg.CORBA.Any;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.WStringValueHelper;
import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.VTDateTimeHelper;

//Version 7.0 Addition

public final class UserInfo implements IDLEntity

{

	private String userName = null;

	private com.linkare.rec.acquisition.Property[] userProps = null;

	public static final int MAX_TIME_LOCK = 1;

	public static final int MIN_TIME_LOCK = 0;

	public UserInfo()

	{

		setUserName("");

	} // ctor

	public UserInfo(String userName)

	{

		setUserName(userName);

	} // ctor

	public UserInfo(String userName,
			com.linkare.rec.acquisition.Property[] userProps)

	{

		setUserName(userName);

		setUserProps(userProps);

	}

	public UserInfo(UserInfo other)

	{

		setUserName(new String(other.userName));

		if (other.userProps != null)

		{

			Property[] props = new Property[other.userProps.length];

			for (int i = 0; i < props.length; i++)

				props[i] = new Property(other.userProps[i]);

			setUserProps(props);

		}

	}

	/**
	 * Getter for property userName.
	 * 
	 * @return Value of property userName.
	 * 
	 * 
	 * 
	 */

	public java.lang.String getUserName()

	{

		return userName;

	}

	/**
	 * Setter for property userName.
	 * 
	 * @param userName
	 *            New value of property userName.
	 * 
	 * 
	 * 
	 */

	public void setUserName(java.lang.String userName)

	{

		this.userName = userName;

	}

	/**
	 * Getter for property userProps.
	 * 
	 * @return Value of property userProps.
	 * 
	 * 
	 * 
	 */

	public com.linkare.rec.acquisition.Property[] getUserProps()

	{

		return this.userProps;

	}

	/**
	 * Setter for property userProps.
	 * 
	 * @param userProps
	 *            New value of property userProps.
	 * 
	 * 
	 * 
	 */

	public void setUserProps(com.linkare.rec.acquisition.Property[] userProps)

	{

		this.userProps = userProps;

	}

	public void addUserProp(Property userProp)

	{

		if (userProp == null)
			return;

		if (userProps == null)

			userProps = new Property[0];

		Property[] tempProps = new Property[userProps.length + 1];

		System.arraycopy(userProps, 0, tempProps, 0, userProps.length);

		tempProps[userProps.length] = userProp;

		userProps = tempProps;
		tempProps = null;
		System.gc();

	}

	public Any getPropertyValue(String propName)

	{

		if (getUserProps() == null)
			return null;

		for (int i = 0; i < getUserProps().length; i++)

		{

			if (getUserProps()[i] != null)

				if ((propName == null && getUserProps()[i].getName() == null)
						|| (propName != null && propName
								.equals(getUserProps()[i].getName()))) {
					return getUserProps()[i].getValue();
				}

		}

		return null;

	}

	public void setPassword(String pass)

	{

		Property newProp = new Property();

		removeUserProp(PROPKEY_USERINFO_PASS.value);

		newProp.setName(PROPKEY_USERINFO_PASS.value);

		WStringValueHelper.insert(newProp.getValue(), pass);

		addUserProp(newProp);

	}

	public void setCertificate(byte[] certificate)

	{

		Property newProp = new Property();

		removeUserProp(PROPKEY_USERINFO_CERTIFICATE.value);

		newProp.setName(PROPKEY_USERINFO_CERTIFICATE.value);

		OctetSeqHelper.insert(newProp.getValue(), certificate);

		addUserProp(newProp);

	}

	public void setLockedTime(DateTime dateTime)

	{

		Property newProp = new Property();

		removeUserProp(PROPKEY_USERINFO_LOCKED_DATETIME.value);

		newProp.setName(PROPKEY_USERINFO_LOCKED_DATETIME.value);

		// if(dateTime!=null)
		VTDateTimeHelper.insert(newProp.getValue(), dateTime);
		addUserProp(newProp);

	}

	public void setNextLockTime(DateTime dateTimeMin, DateTime dateTimeMax)

	{

		// TODO - If it works for nulls with LockedTime... do it here also
		Property newProp = new Property();

		removeUserProp(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MIN.value);

		newProp.setName(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MIN.value);

		VTDateTimeHelper.insert(newProp.getValue(), dateTimeMin);

		addUserProp(newProp);

		Property newProp2 = new Property();

		removeUserProp(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MAX.value);

		newProp2.setName(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MAX.value);

		VTDateTimeHelper.insert(newProp2.getValue(), dateTimeMax);

		addUserProp(newProp2);

	}

	public String getPassword()

	{

		Any value = getPropertyValue(PROPKEY_USERINFO_PASS.value);

		if (value != null)

			return WStringValueHelper.extract(value);

		return null;

	}

	public byte[] getCertificate()

	{

		Any value = getPropertyValue(PROPKEY_USERINFO_CERTIFICATE.value);

		if (value != null)

			return OctetSeqHelper.extract(value);

		return null;

	}

	public DateTime getLockedTime()

	{

		Any value = getPropertyValue(PROPKEY_USERINFO_LOCKED_DATETIME.value);

		if (value != null)

			return VTDateTimeHelper.extract(value);

		return null;

	}

	public void setHardwaresConnectedTo(String hardwareIdsCSV)

	{
		Property newProp = new Property();

		removeUserProp(PROPKEY_USERINFO_HARDWARES_CONNECTED_TO.value);

		newProp.setName(PROPKEY_USERINFO_HARDWARES_CONNECTED_TO.value);

		WStringValueHelper.insert(newProp.getValue(), hardwareIdsCSV);

		addUserProp(newProp);

	}

	public String getHardwaresConnectedTo()

	{
		Any value = getPropertyValue(PROPKEY_USERINFO_HARDWARES_CONNECTED_TO.value);

		if (value != null)
			return WStringValueHelper.extract(value);

		return null;
	}

	public DateTime[] getNextLockTime()

	{

		DateTime min = null;

		DateTime max = null;

		Any valueMin = getPropertyValue(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MIN.value);

		if (valueMin != null)

			min = VTDateTimeHelper.extract(valueMin);

		Any valueMax = getPropertyValue(PROPKEY_USERINFO_NEXTLOCK_DATETIME_MAX.value);

		if (valueMax != null)

			max = VTDateTimeHelper.extract(valueMax);

		// removeUserProp(valueMax);

		return new DateTime[]

		{ min, max };

	}

	public void removeUserProp(String propName)

	{

		if (propName == null)
			return;

		if (userProps == null)
			return;

		int found_index = -1;

		for (int i = 0; i < userProps.length; i++)

		{

			if (propName.equals(userProps[i].getName()))

			{

				found_index = i;

				break;

			}

		}

		if (found_index != -1)

		{

			Property[] tempProps = new Property[userProps.length - 1];

			System.arraycopy(userProps, 0, tempProps, 0, found_index);

			System.arraycopy(userProps, found_index + 1, tempProps,
					found_index, tempProps.length - found_index);

			userProps = tempProps;
			tempProps = null;
			System.gc();

		}

	}

	public String toString() {
		String retVal = getUserName();
		if (getHardwaresConnectedTo() != null
				&& !getHardwaresConnectedTo().equals("")) {
			retVal += " - " + getHardwaresConnectedTo();
		}
		return retVal;
	}

} // class UserInfo

