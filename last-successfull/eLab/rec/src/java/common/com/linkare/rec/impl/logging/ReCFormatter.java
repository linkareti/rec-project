/*
 * ReCFormatter.java created on Mar 4, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A Simple Formatter.
 * 
 * @author Henrique Fernandes
 */
public class ReCFormatter extends Formatter {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final Date date = new Date();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/*
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public synchronized String format(final LogRecord record) {
		final StringBuffer str = new StringBuffer();
		date.setTime(record.getMillis());
		// String sourceClassName = record.getSourceClassName();
		final String sourceMethodName = record.getSourceMethodName();
		// String loggerName = record.getLoggerName();

		str.append(dateFormat.format(date)).append(" ");
		str.append("T-").append(record.getThreadID()).append(" ");
		str.append(record.getLevel().getName()).append(" ");
		// str.append(sourceClassName != null ? sourceClassName :
		// record.getLoggerName());
		// Use always the logger name
		str.append(ReCFormatter.getSimpleName(record.getLoggerName()));
		// str.append(record.getLoggerName());
		if (sourceMethodName != null) {
			str.append(".").append(sourceMethodName);
		}
		str.append(" ");
		str.append(record.getMessage());
		str.append(ReCFormatter.LINE_SEPARATOR);

		if (record.getThrown() != null) {
			try {
				final StringWriter sw = new StringWriter();
				final PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				str.append(sw.toString());
			} catch (final Exception ex) {
				// best effort
			}
		}
		return str.toString();
	}

	public static String getSimpleName(final String loggerName) {
		final int lastIndex = loggerName.lastIndexOf(".");
		if (lastIndex != -1) {
			return loggerName.substring(lastIndex + 1);
		}
		return loggerName;
	}

}
