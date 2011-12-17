package com.linkare.rec.data.acquisition;

public final class ByteArrayValue implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1206377612112792782L;
	private byte data[] = null;
	private String mimeType = null;

	/** Default Constructor */
	public ByteArrayValue() {
	}

	public ByteArrayValue(final byte[] _data, final String _mime_type) {
		data = _data;
		mimeType = _mime_type;
	}

	/** Copy Constructor */
	public ByteArrayValue(final ByteArrayValue value) {
		data = new byte[value.getData().length];
		System.arraycopy(value.getData(), 0, data, 0, data.length);
		mimeType = new String(value.getMimeType());
	}

	public void setData(final byte[] _data) {
		data = _data;
	}

	public void setData(final int index, final byte byteValue) {
		if (data != null && index < data.length) {
			data[index] = byteValue;
		} else {
			byte[] temp = new byte[index + 1];
			if (data != null) {
				System.arraycopy(data, 0, temp, 0, data.length);
			}

			temp[index] = byteValue;
			data = temp;
			temp = null;
		}
	}

	public byte[] getData() {
		return data;
	}

	public byte getData(final int index) {
		return data[index];
	}

	public void setMimeType(final String _mime_type) {
		mimeType = _mime_type;
	}

	public String getMimeType() {
		return mimeType;
	}

	public int getSize() {
		return data.length;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof ByteArrayValue)) {
			return false;
		}

		final ByteArrayValue other = (ByteArrayValue) obj;

		if (!other.getMimeType().equals(getMimeType())) {
			return false;
		}

		if (other.getData() == null && getData() == null) {
			return true;
		}
		if (other.getData() == null) {
			return false;
		}
		if (getData() == null) {
			return false;
		}

		if (other.getData().length != getData().length) {
			return false;
		}

		boolean retVal = true;
		for (int i = 0; i < getData().length && retVal; i++) {
			if (getData()[i] != other.getData()[i]) {
				retVal = false;
			}
		}

		return retVal;

	}

} // class ByteArrayValue
