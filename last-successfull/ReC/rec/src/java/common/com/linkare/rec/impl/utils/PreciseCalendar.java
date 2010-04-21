/*
 * PreciseCalendar.java
 *
 * Created on 13 de Janeiro de 2004, 16:13
 */

package com.linkare.rec.impl.utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PreciseCalendar extends GregorianCalendar {
	private int picos = 0;
	private int nanos = 0;
	private int micros = 0;

	/** Creates a new instance of PreciseCalendar */
	public PreciseCalendar() {
		this(new DateTime());
	}

	public PreciseCalendar(long millis) {
		setTimeInMillis(millis);
	}

	/** Creates a new instance of PreciseCalendar */
	public PreciseCalendar(DateTime dateTime) {
		super((int) dateTime.getDate().getYear(), (int) dateTime.getDate().getMonth(), (int) dateTime.getDate()
				.getDay(), (int) dateTime.getTime().getHours(), (int) dateTime.getTime().getMinutes(), (int) dateTime
				.getTime().getSeconds());
		add(Calendar.MILLISECOND, (int) dateTime.getTime().getMilis());
		addMicros((int) dateTime.getTime().getMicros());
		addNanos((int) dateTime.getTime().getNanos());
		addPicos((int) dateTime.getTime().getPicos());
	}

	public DateTime getDateTime() {
		return null;
	}

	/**
	 * Getter for property picos.
	 * 
	 * @return Value of property picos.
	 * 
	 */
	public int getPicos() {
		return picos;
	}

	/**
	 * Setter for property picos.
	 * 
	 * @param picos New value of property picos.
	 * 
	 */
	public void addPicos(int picos) {
		this.picos += picos;
		int overflow = 0;
		while (picos < 0) {
			picos += 999;
			overflow--;
		}
		while (picos > 999) {
			picos -= 999;
			overflow++;
		}
		addNanos(overflow);
	}

	/**
	 * Getter for property nanos.
	 * 
	 * @return Value of property nanos.
	 * 
	 */
	public int getNanos() {
		return nanos;
	}

	/**
	 * Setter for property nanos.
	 * 
	 * @param nanos New value of property nanos.
	 * 
	 */
	public void addNanos(int nanos) {
		this.nanos += nanos;
		int overflow = 0;
		while (nanos < 0) {
			nanos += 999;
			overflow--;
		}
		while (nanos > 999) {
			nanos -= 999;
			overflow++;
		}
		addMicros(overflow);
	}

	/**
	 * Getter for property micros.
	 * 
	 * @return Value of property micros.
	 * 
	 */
	public int getMicros() {
		return micros;
	}

	/**
	 * Setter for property micros.
	 * 
	 * @param micros New value of property micros.
	 * 
	 */
	public void addMicros(int micros) {
		this.micros += micros;
		int overflow = 0;
		while (micros < 0) {
			micros += 999;
			overflow--;
		}
		while (micros > 999) {
			micros -= 999;
			overflow++;
		}
		add(Calendar.MILLISECOND, overflow);
	}

	public void addDateTime(DateTime dateTime) {
		addPicos((int) dateTime.getTime().getPicos());
		addNanos((int) dateTime.getTime().getNanos());
		addMicros((int) dateTime.getTime().getMicros());
		add(Calendar.MILLISECOND, (int) dateTime.getTime().getMilis());
		add(Calendar.SECOND, (int) dateTime.getTime().getSeconds());
		add(Calendar.MINUTE, (int) dateTime.getTime().getMinutes());
		add(Calendar.HOUR, (int) dateTime.getTime().getHours());
		add(Calendar.DATE, (int) dateTime.getDate().getDay());
		add(Calendar.MONTH, (int) dateTime.getDate().getMonth());
		add(Calendar.YEAR, (int) dateTime.getDate().getYear());
	}

	public String toString() {
		DateFormat format = DateFormat.getInstance();
		java.util.Date d = new java.util.Date(this.getTimeInMillis());
		String dateStr = format.format(d);
		dateStr += "." + getMicros() + "" + getNanos() + "" + getPicos();
		return dateStr;
	}

	public static DateTime calculateDateTime(DateTime timeStart, Frequency f, int sampleNumber) {
		double tbs = f.getFrequency() * f.getMultiplier().getExpValue();
		if (f.getFrequencyDefType().equals(FrequencyDefType.FrequencyType))
			tbs = 1. / tbs;

		double totalDelta = tbs * 1000. * (double) sampleNumber;
		long totalMillisDelta = (long) Math.floor(totalDelta);
		long totalPicosDelta = (long) Math.floor((totalDelta - Math.floor(totalDelta)) * 1E9);
		int microsDelta = (int) Math.floor((double) totalPicosDelta / 1.E6);
		int nanosDelta = (int) Math.floor((totalPicosDelta - microsDelta * 1E6) / 1.E3);
		int picosDelta = (int) (totalPicosDelta - microsDelta * 1E6 - nanosDelta * 1E3);

		PreciseCalendar start = new PreciseCalendar(timeStart);
		PreciseCalendar ret = new PreciseCalendar(start.getTimeInMillis() + totalMillisDelta);
		ret.addPicos(picosDelta);
		ret.addNanos(nanosDelta);
		ret.addMicros(microsDelta);

		return ret.toDateTime();
	}

	public DateTime toDateTime() {
		DateTime ret = new DateTime(getTimeInMillis());
		ret.getTime().setMicros((short) getMicros());
		ret.getTime().setNanos((short) getNanos());
		ret.getTime().setPicos((short) getPicos());
		return ret;
	}

}
