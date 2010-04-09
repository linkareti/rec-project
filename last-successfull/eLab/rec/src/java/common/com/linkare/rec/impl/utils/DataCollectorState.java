package com.linkare.rec.impl.utils;

import com.linkare.rec.acquisition.DataProducerState;

//Version 7.0 Change - added state to DataProducer... simpler to maintain
public class DataCollectorState implements java.io.Serializable {
	private byte value;

	public static final byte _DP_WAITING = DataProducerState._DP_WAITING;
	public static final DataCollectorState DP_WAITING = new DataCollectorState(_DP_WAITING);
	public static final byte _DP_STARTED_NODATA = DataProducerState._DP_STARTED_NODATA;
	public static final DataCollectorState DP_STARTED_NODATA = new DataCollectorState(_DP_STARTED_NODATA);
	public static final byte _DP_STARTED = DataProducerState._DP_STARTED;
	public static final DataCollectorState DP_STARTED = new DataCollectorState(_DP_STARTED);
	public static final byte _DP_ENDED = DataProducerState._DP_ENDED;
	public static final DataCollectorState DP_ENDED = new DataCollectorState(_DP_ENDED);
	public static final byte _DP_STOPED = DataProducerState._DP_STOPED;
	public static final DataCollectorState DP_STOPED = new DataCollectorState(_DP_STOPED);
	public static final byte _DP_ERROR = DataProducerState._DP_ERROR;
	public static final DataCollectorState DP_ERROR = new DataCollectorState(_DP_ERROR);

	/**
	 * Return a string representation
	 * 
	 * @return a string representation of the enumeration
	 */
	public java.lang.String toString() {
		switch (getValue()) {
		case _DP_WAITING:
			return "WAITING";
		case _DP_STARTED_NODATA:
			return "STARTED_NODATA";
		case _DP_STARTED:
			return "STARTED";
		case _DP_ENDED:
			return "ENDED";
		case _DP_STOPED:
			return "STOPED";
		case _DP_ERROR:
			return "ERROR";
		}
		throw new RuntimeException("Error - The data collector state value is incorrect... Current value is "
				+ getValue());
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(byte value) {
		if (value == _DP_WAITING || value == _DP_STARTED_NODATA || value == _DP_STARTED || value == _DP_STOPED
				|| value == _DP_ENDED || value == _DP_ERROR)
			this.value = value;
		else
			throw new org.omg.CORBA.BAD_OPERATION();

	}

	public DataCollectorState() {
		this.setValue(_DP_WAITING);
	}

	public DataCollectorState(byte value) {
		this.setValue(value);
	}

	public DataCollectorState(DataCollectorState other) {
		if (other != null)
			setValue(other.getValue());
		else
			setValue(_DP_WAITING);
	}

	public DataCollectorState(DataProducerState other) {
		setValue(other.getValue());
	}

	public DataProducerState toDataProducerState() {
		return new DataProducerState(getValue());
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof DataCollectorState))
			return false;

		return ((DataCollectorState) other).getValue() == getValue();
	}
} // class DataCollectorState
