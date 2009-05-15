package com.linkare.rec.impl.exceptions;
import com.linkare.rec.acquisition.WrongConfigurationException;

public class WrongConfigurationExceptionConstants
{

	public static final int FREQUENCY_NOT_IN_SCALES=0;
	public static final String FREQUENCY_NOT_IN_SCALES_MSG="The selected Frequency is not within any existing Frequency Scale";
	public static final int PARAMETER_INVALID=1;
	public static final String PARAMETER_INVALID_MSG="The selected Parameter value is invalid";
	public static final int SAMPLING_SCALE_INVALID=2;
	public static final String SAMPLING_SCALE_INVALID_MSG="The selected number of samples is not allowed!";
	public static final int SCALE_INVALID=3;
	public static final String SCALE_INVALID_MSG="The selected scale is not any of the allowed scales!";
	

	public static String getTranslatedMessage(WrongConfigurationException e)
	{
		switch(e.errorCode)
		{
			case FREQUENCY_NOT_IN_SCALES:
				return FREQUENCY_NOT_IN_SCALES_MSG;
			case PARAMETER_INVALID:
				return PARAMETER_INVALID_MSG;
			case SAMPLING_SCALE_INVALID:
				return SAMPLING_SCALE_INVALID_MSG;
			case SCALE_INVALID:
				return SCALE_INVALID_MSG;
		}

		return e.getMessage();
	}

}
