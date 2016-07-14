/*
 * Apparatus.java
 *
 * Created on 08 May 2003, 22:55
 */

package com.linkare.rec.impl.client.apparatus;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.wrappers.MultiCastHardwareWrapper;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class Apparatus {

	/** Holds value of property mCHardware. */
	private MultiCastHardwareWrapper mCHardware;

	/** Holds value of property hardwareInfo. */
	private HardwareInfo hardwareInfo;

	/** Creates a new instance of Apparatus */
	public Apparatus() {
	}

	/**
	 * Creates a new instance of Apparatus
	 * 
	 * @param mCHardware
	 * @param hardwareInfo
	 */
	public Apparatus(final MultiCastHardwareWrapper mCHardware, final HardwareInfo hardwareInfo) {
		setMultiCastHardware(mCHardware);
		setHardwareInfo(hardwareInfo);
	}

	/**
	 * Getter for property mCHardware.
	 * 
	 * @return Value of property mCHardware.
	 */
	public MultiCastHardwareWrapper getMultiCastHardware() {
		return mCHardware;
	}

	/**
	 * Setter for property mCHardware.
	 * 
	 * @param mCHardware New value of property mCHardware.
	 */
	public void setMultiCastHardware(final MultiCastHardwareWrapper mCHardware) {
		this.mCHardware = mCHardware;
	}

	/**
	 * Getter for property hardwareInfo.
	 * 
	 * @return Value of property hardwareInfo.
	 */
	public HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	/**
	 * Setter for property hardwareInfo.
	 * 
	 * @param hardwareInfo New value of property hardwareInfo.
	 */
	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	@Override
	public String toString() {
		if (hardwareInfo != null) {
			if (hardwareInfo.getFamiliarName() != null) {
				return hardwareInfo.getFamiliarName();
			}
		}

		return "Unknown apparatus";
	}

}
