package com.linkare.rec.data.metadata;

import com.linkare.rec.impl.utils.MathUtil;

public final class ChannelParameter implements org.omg.CORBA.portable.IDLEntity {
	/** Holds value of property parameterName. */
	private String parameterName;

	/** Holds value of property parameterType. */
	private com.linkare.rec.data.metadata.ParameterType parameterType;

	/** Holds value of property parameterSelectionList. */
	private String[] parameterSelectionList;

	/** Holds value of property selectedParameterIndex. */
	private String selectedParameterValue;

	//
	// Default constructor
	//
	public ChannelParameter() {
	}

	//
	// Constructor with fields initialization
	// @param parameter_name parameter_name struct member
	// @param parameter_type parameter_type struct member
	// @param parameter_selection_list parameter_selection_list struct member
	// @param actual_selected_parameter_index actual_selected_parameter_index
	// struct member
	//
	public ChannelParameter(String parameter_name, com.linkare.rec.data.metadata.ParameterType parameter_type,
			String[] parameter_selection_list, String actual_selected_parameter_value) {
		this.setParameterName(parameter_name);
		this.setParameterType(parameter_type);
		this.setParameterSelectionList(parameter_selection_list);
		this.setSelectedParameterValue(actual_selected_parameter_value);
	}

	//
	// Copy Constructor
	//
	public ChannelParameter(ChannelParameter other) {
		this.setParameterName(new String(other.getParameterName()));
		this.setParameterType(new com.linkare.rec.data.metadata.ParameterType(other.getParameterType()));
		String[] temp = null;
		if (other.getParameterSelectionList() != null) {
			temp = new String[other.getParameterSelectionList().length];
			System.arraycopy(other.getParameterSelectionList(), 0, temp, 0, temp.length);
		}
		this.setParameterSelectionList(temp);
		temp = null;
		System.gc();
		this.setSelectedParameterValue(other.getSelectedParameterValue());
	}

	/**
	 * Getter for property parameterName.
	 * 
	 * @return Value of property parameterName.
	 */
	public String getParameterName() {
		return this.parameterName;
	}

	/**
	 * Setter for property parameterName.
	 * 
	 * @param parameterName New value of property parameterName.
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Getter for property parameterType.
	 * 
	 * @return Value of property parameterType.
	 */
	public com.linkare.rec.data.metadata.ParameterType getParameterType() {
		return this.parameterType;
	}

	/**
	 * Setter for property parameterType.
	 * 
	 * @param parameterType New value of property parameterType.
	 */
	public void setParameterType(com.linkare.rec.data.metadata.ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * Indexed getter for property parameterSelectionList.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public String getParameterSelectionList(int index) {
		if (this.parameterSelectionList == null || index >= this.parameterSelectionList.length)
			throw new RuntimeException("No ParamaterSelection at that index in the ParameterSelectionList...");

		return this.parameterSelectionList[index];
	}

	/**
	 * Getter for property parameterSelectionList.
	 * 
	 * @return Value of property parameterSelectionList.
	 */
	public String[] getParameterSelectionList() {
		return this.parameterSelectionList;
	}

	/**
	 * Indexed setter for property parameterSelectionList.
	 * 
	 * @param index Index of the property.
	 * @param parameterSelectionList New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setParameterSelectionList(int index, String parameterSelectionList) {
		if (this.parameterSelectionList != null && index < this.parameterSelectionList.length)
			this.parameterSelectionList[index] = parameterSelectionList;
		else {
			String[] temp = new String[index + 1];
			if (this.parameterSelectionList != null)
				System.arraycopy(this.parameterSelectionList, 0, temp, 0, this.parameterSelectionList.length);

			temp[index] = parameterSelectionList;
			this.parameterSelectionList = temp;
			temp = null;
			System.gc();
		}
	}

	/**
	 * Setter for property parameterSelectionList.
	 * 
	 * @param parameterSelectionList New value of property
	 *            parameterSelectionList.
	 */
	public void setParameterSelectionList(String[] parameterSelectionList) {
		this.parameterSelectionList = parameterSelectionList;
	}

	/**
	 * Getter for property selectedParameterIndex.
	 * 
	 * @return Value of property selectedParameterIndex.
	 */
	public String getSelectedParameterValue() {
		return this.selectedParameterValue;
	}

	/**
	 * Setter for property selectedParameterIndex.
	 * 
	 * @param selectedParameterIndex New value of property
	 *            selectedParameterIndex.
	 */
	public void setSelectedParameterValue(String selectedParameterValue) {
		this.selectedParameterValue = selectedParameterValue;
	}

	public boolean isSelectedValueValid(String selectedParameterValue) {
		switch (this.parameterType.getValue()) {
		case ParameterType._ContinuousValue: {
			if (parameterSelectionList.length < 3)
				return true;
			try {
				double min_value = Double.parseDouble(parameterSelectionList[0]);
				double max_value = Double.parseDouble(parameterSelectionList[1]);
				double step_value = Double.parseDouble(parameterSelectionList[2]);

				double value = Double.parseDouble(selectedParameterValue);

				return MathUtil.isValueInScale(min_value, max_value, step_value, value);

			} catch (NumberFormatException e) {
				return true;
			}
		}

		case ParameterType._OnOffValue:
		case ParameterType._SelectionListValue: {
			boolean outReturn = false;
			for (int i = 0; i < parameterSelectionList.length && !outReturn; i++) {
				outReturn = outReturn || parameterSelectionList[i].equals(selectedParameterValue);
			}
			return outReturn;
		}

		case ParameterType._BlackBoxValue: {
			return true;
		}
		default:
			return true;
		}
	}

} // class ChannelParameter
