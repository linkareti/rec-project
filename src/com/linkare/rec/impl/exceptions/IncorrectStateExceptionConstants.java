package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.*;

public class IncorrectStateExceptionConstants
{
    private static final java.util.ResourceBundle incorrectStateExceptionRB = java.util.ResourceBundle.getBundle("com/linkare/rec/impl/exceptions/resources/IncorrectStateException");

    public static final int UNKNOWN_ERROR=0;
    public static final String UNKNOWN_ERROR_MSG=incorrectStateExceptionRB.getString("unknown_error_occured");
    public static final int WRONG_HARDWARE_STATE=1;
    public static final String WRONG_HARDWARE_STATE_MSG =incorrectStateExceptionRB.getString("wrong_state_in_hardware");

	public static String getTranslatedMessage(IncorrectStateException e)
	{
		switch(e.errorCode)
		{
			case UNKNOWN_ERROR:
				return UNKNOWN_ERROR_MSG;
			case WRONG_HARDWARE_STATE:
				return WRONG_HARDWARE_STATE_MSG;
		}

		return e.getMessage();
	}
}
