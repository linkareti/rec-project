package com.linkare.irn.nascimento.model.ext.mapping.statistic;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.MappingException;

public class StatisticDTO implements Serializable {

	private static final long serialVersionUID = -7212976777083611383L;

	private int pendingForefather;

	private int elaborating;

	private int expired;

	private int rejected;

	private int concluded;

	private int female;

	private int male;

	private int undetermined;
	
	private Date date;

	public StatisticDTO(final Object[] object, final Date date) {

		if (object.length == 8) {
			this.pendingForefather = Integer.parseInt(object[0].toString());
			this.elaborating = Integer.parseInt(object[1].toString());
			this.expired = Integer.parseInt(object[2].toString());
			this.rejected = Integer.parseInt(object[3].toString());
			this.concluded = Integer.parseInt(object[4].toString());
			this.female = Integer.parseInt(object[5].toString());
			this.male = Integer.parseInt(object[6].toString());
			this.undetermined = Integer.parseInt(object[7].toString());
			this.date = date;
		} else {
			throw new MappingException("The received entity could not be mapped to StatisticDTO");
		}

	}

	public int getPendingForefather() {
		return pendingForefather;
	}

	public void setPendingForefather(int pendingForefather) {
		this.pendingForefather = pendingForefather;
	}

	public int getElaborating() {
		return elaborating;
	}

	public void setElaborating(int elaborating) {
		this.elaborating = elaborating;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public int getConcluded() {
		return concluded;
	}

	public void setConcluded(int concluded) {
		this.concluded = concluded;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getUndetermined() {
		return undetermined;
	}

	public void setUndetermined(int undetermined) {
		this.undetermined = undetermined;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
