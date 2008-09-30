package com.linkare.rec.impl.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple utility class to help in
 * 
 * @author José Pedro Pereira
 */
public class LoggerUtil {

	/**
	 * This utility method writes a logging entry in WARNING mode to the logger
	 * and logs also the full stack trace and info message in FINEST level if
	 * the logger is currenly configured to get that level of detail
	 * 
	 * The warning message printed also includes information at the last class,
	 * method and line where the error really occurred
	 * 
	 * @param info_message
	 *            An additional message to be presented
	 * @param t
	 *            The {@link Throwable} to be logged
	 * @param logger
	 *            The {@link Logger} on which to log the exception and message
	 */
	public static void logThrowable(String info_message, Throwable t,
			Logger logger) {
		if (info_message != null && logger != null
				&& logger.getLevel().intValue() <= Level.WARNING.intValue()) {
			if (t != null) {
				StackTraceElement[] trace = t.getStackTrace();
				info_message = " @class " + trace[0].getClassName()
						+ " ,@method " + trace[0].getMethodName() + " ,@line "
						+ trace[0].getLineNumber() + " " + info_message;
			}

			logger.log(Level.WARNING, info_message);
		}

		if (t != null && logger != null
				&& logger.getLevel().intValue() <= Level.FINEST.intValue()) {
			logger.log(Level.FINEST, info_message, t);
		}
	}

}
