package com.linkare.rec.acquisition;

//Version 7.0 Change - added state to DataProducer... simpler to maintain
public class DataProducerState implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2349755726936461169L;

	private byte value = 0;

	public static final byte _DP_WAITING = 0;
	public static final DataProducerState DP_WAITING = new DataProducerState(DataProducerState._DP_WAITING);
	public static final byte _DP_STARTED_NODATA = 1;
	public static final DataProducerState DP_STARTED_NODATA = new DataProducerState(
			DataProducerState._DP_STARTED_NODATA);
	public static final byte _DP_STARTED = 2;
	public static final DataProducerState DP_STARTED = new DataProducerState(DataProducerState._DP_STARTED);
	public static final byte _DP_ENDED = 3;
	public static final DataProducerState DP_ENDED = new DataProducerState(DataProducerState._DP_ENDED);
	public static final byte _DP_STOPED = 4;
	public static final DataProducerState DP_STOPED = new DataProducerState(DataProducerState._DP_STOPED);
	public static final byte _DP_ERROR = 5;
	public static final DataProducerState DP_ERROR = new DataProducerState(DataProducerState._DP_ERROR);

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
		throw new org.omg.CORBA.BAD_OPERATION();
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
		if (value == DataProducerState._DP_WAITING || value == DataProducerState._DP_STARTED_NODATA
				|| value == DataProducerState._DP_STARTED || value == DataProducerState._DP_STOPED
				|| value == DataProducerState._DP_ENDED || value == DataProducerState._DP_ERROR) {
			this.value = value;
		} else {
			throw new org.omg.CORBA.BAD_OPERATION();
		}

	}

	public DataProducerState() {
		setValue(DataProducerState._DP_WAITING);
	}

	public DataProducerState(final byte value) {
		setValue(value);
	}

	public DataProducerState(final DataProducerState other) {
		if (other != null) {
			setValue(other.getValue());
		} else {
			setValue(DataProducerState._DP_WAITING);
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof DataProducerState)) {
			return false;
		}

		return ((DataProducerState) other).getValue() == getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int retVal = 35;
		retVal += 17 * getValue();
		return retVal;
	}
} // class DataProducerState
