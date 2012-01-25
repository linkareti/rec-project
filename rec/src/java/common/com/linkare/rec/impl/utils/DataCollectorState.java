package com.linkare.rec.impl.utils;

import com.linkare.rec.acquisition.DataProducerState;

//Version 7.0 Change - added state to DataProducer... simpler to maintain
public class DataCollectorState implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3583154368813945489L;

	private byte value = 0x0;

	public static final byte _DP_WAITING = DataProducerState._DP_WAITING;
	public static final DataCollectorState DP_WAITING = new DataCollectorState(DataCollectorState._DP_WAITING);
	public static final byte _DP_STARTED_NODATA = DataProducerState._DP_STARTED_NODATA;
	public static final DataCollectorState DP_STARTED_NODATA = new DataCollectorState(
			DataCollectorState._DP_STARTED_NODATA);
	public static final byte _DP_STARTED = DataProducerState._DP_STARTED;
	public static final DataCollectorState DP_STARTED = new DataCollectorState(DataCollectorState._DP_STARTED);
	public static final byte _DP_ENDED = DataProducerState._DP_ENDED;
	public static final DataCollectorState DP_ENDED = new DataCollectorState(DataCollectorState._DP_ENDED);
	public static final byte _DP_STOPED = DataProducerState._DP_STOPED;
	public static final DataCollectorState DP_STOPED = new DataCollectorState(DataCollectorState._DP_STOPED);
	public static final byte _DP_ERROR = DataProducerState._DP_ERROR;
	public static final DataCollectorState DP_ERROR = new DataCollectorState(DataCollectorState._DP_ERROR);

	/**
	 * Return a string representation
	 * 
	 * @return a string representation of the enumeration
	 */
	@Override
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
	public void setValue(final byte value) {
		if (value == DataCollectorState._DP_WAITING || value == DataCollectorState._DP_STARTED_NODATA
				|| value == DataCollectorState._DP_STARTED || value == DataCollectorState._DP_STOPED
				|| value == DataCollectorState._DP_ENDED || value == DataCollectorState._DP_ERROR) {
			this.value = value;
		} else {
			throw new org.omg.CORBA.BAD_OPERATION();
		}

	}

	public DataCollectorState() {
		setValue(DataCollectorState._DP_WAITING);
	}

	public DataCollectorState(final byte value) {
		setValue(value);
	}

	public DataCollectorState(final DataCollectorState other) {
		if (other != null) {
			setValue(other.getValue());
		} else {
			setValue(DataCollectorState._DP_WAITING);
		}
	}

	public DataCollectorState(final DataProducerState other) {
		setValue(other.getValue());
	}

	public DataProducerState toDataProducerState() {
		return new DataProducerState(getValue());
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof DataCollectorState)) {
			return false;
		}

		return ((DataCollectorState) other).getValue() == getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return getValue();
	}
} // class DataCollectorState
