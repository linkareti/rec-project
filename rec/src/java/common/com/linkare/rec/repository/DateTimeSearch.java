package com.linkare.rec.repository;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.data.synch.DateTime;

public final class DateTimeSearch implements IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9116778265093134678L;
	private DateTime minDateTime = null;
	private DateTime maxDateTime = null;

	public DateTimeSearch() {
	} // ctor

	public DateTimeSearch(final DateTime _minDateTime, final DateTime _maxDateTime) {
		setMinDateTime(minDateTime);
		setMaxDateTime(maxDateTime);
	}

	/**
	 * Getter for property minDateTime.
	 * 
	 * @return Value of property minDateTime.
	 * 
	 */
	public DateTime getMinDateTime() {
		return minDateTime;
	}

	/**
	 * Setter for property minDateTime.
	 * 
	 * @param minDateTime New value of property minDateTime.
	 * 
	 */
	public void setMinDateTime(final DateTime minDateTime) {
		this.minDateTime = minDateTime;
	}

	/**
	 * Getter for property maxDateTime.
	 * 
	 * @return Value of property maxDateTime.
	 * 
	 */
	public DateTime getMaxDateTime() {
		return maxDateTime;
	}

	/**
	 * Setter for property maxDateTime.
	 * 
	 * @param maxDateTime New value of property maxDateTime.
	 * 
	 */
	public void setMaxDateTime(final DateTime maxDateTime) {
		this.maxDateTime = maxDateTime;
	}

	public boolean isValid(final DateTime dateTime) {
		return (DateTimeSearch.isLaterOrEqual(getMinDateTime(), dateTime) && DateTimeSearch.isSoonerOrEqual(
				getMaxDateTime(), dateTime));
	}

	public static boolean isLaterOrEqual(final DateTime dateTimeStart, final DateTime dateTime) {
		if (dateTimeStart == null || dateTimeStart.getDate() == null || dateTimeStart.getTime() == null) {
			return true;
		}

		if (dateTime == null || dateTime.getDate() == null || dateTime.getTime() == null) {
			return false;
		}

		if (dateTimeStart.getDate().getYear() < dateTime.getDate().getYear()) {
			return true;
		} else if (dateTimeStart.getDate().getYear() == dateTime.getDate().getYear()) {
			if (dateTimeStart.getDate().getMonth() < dateTime.getDate().getMonth()) {
				return true;
			} else if (dateTimeStart.getDate().getMonth() == dateTime.getDate().getMonth()) {
				if (dateTimeStart.getDate().getDay() < dateTime.getDate().getDay()) {
					return true;
				} else if (dateTimeStart.getDate().getDay() == dateTime.getDate().getDay()) {
					if (dateTimeStart.getTime().getHours() < dateTime.getTime().getHours()) {
						return true;
					} else if (dateTimeStart.getTime().getHours() == dateTime.getTime().getHours()) {
						if (dateTimeStart.getTime().getMinutes() < dateTime.getTime().getMinutes()) {
							return true;
						} else if (dateTimeStart.getTime().getMinutes() == dateTime.getTime().getMinutes()) {
							if (dateTimeStart.getTime().getSeconds() < dateTime.getTime().getSeconds()) {
								return true;
							} else if (dateTimeStart.getTime().getSeconds() == dateTime.getTime().getSeconds()) {
								if (dateTimeStart.getTime().getMilis() < dateTime.getTime().getMilis()) {
									return true;
								} else if (dateTimeStart.getTime().getMilis() == dateTime.getTime().getMilis()) {
									if (dateTimeStart.getTime().getMicros() < dateTime.getTime().getMicros()) {
										return true;
									} else if (dateTimeStart.getTime().getMicros() == dateTime.getTime().getMicros()) {
										if (dateTimeStart.getTime().getNanos() < dateTime.getTime().getNanos()) {
											return true;
										} else if (dateTimeStart.getTime().getNanos() == dateTime.getTime().getNanos()) {
											if (dateTimeStart.getTime().getPicos() <= dateTime.getTime().getPicos()) {
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}// end first else ==

		return false;

	}

	public static boolean isSoonerOrEqual(final DateTime dateTimeStart, final DateTime dateTime) {
		if (dateTimeStart == null || dateTimeStart.getDate() == null || dateTimeStart.getTime() == null) {
			return true;
		}

		if (dateTime == null || dateTime.getDate() == null || dateTime.getTime() == null) {
			return false;
		}

		if (dateTimeStart.getDate().getYear() > dateTime.getDate().getYear()) {
			return true;
		} else if (dateTimeStart.getDate().getYear() == dateTime.getDate().getYear()) {
			if (dateTimeStart.getDate().getMonth() > dateTime.getDate().getMonth()) {
				return true;
			} else if (dateTimeStart.getDate().getMonth() == dateTime.getDate().getMonth()) {
				if (dateTimeStart.getDate().getDay() > dateTime.getDate().getDay()) {
					return true;
				} else if (dateTimeStart.getDate().getDay() == dateTime.getDate().getDay()) {
					if (dateTimeStart.getTime().getHours() > dateTime.getTime().getHours()) {
						return true;
					} else if (dateTimeStart.getTime().getHours() == dateTime.getTime().getHours()) {
						if (dateTimeStart.getTime().getMinutes() > dateTime.getTime().getMinutes()) {
							return true;
						} else if (dateTimeStart.getTime().getMinutes() == dateTime.getTime().getMinutes()) {
							if (dateTimeStart.getTime().getSeconds() > dateTime.getTime().getSeconds()) {
								return true;
							} else if (dateTimeStart.getTime().getSeconds() == dateTime.getTime().getSeconds()) {
								if (dateTimeStart.getTime().getMilis() > dateTime.getTime().getMilis()) {
									return true;
								} else if (dateTimeStart.getTime().getMilis() == dateTime.getTime().getMilis()) {
									if (dateTimeStart.getTime().getMicros() > dateTime.getTime().getMicros()) {
										return true;
									} else if (dateTimeStart.getTime().getMicros() == dateTime.getTime().getMicros()) {
										if (dateTimeStart.getTime().getNanos() > dateTime.getTime().getNanos()) {
											return true;
										} else if (dateTimeStart.getTime().getNanos() == dateTime.getTime().getNanos()) {
											if (dateTimeStart.getTime().getPicos() >= dateTime.getTime().getPicos()) {
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}// end first else ==

		return false;

	}
} // class DateTimeSearch
