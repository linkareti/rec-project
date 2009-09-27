package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

import java.util.HashMap;

public class SerialPortRs232Config {

	private static String uniqueId = null;
	private static Integer numChannels = null;
	private static Double minFrequency = null;
	private static Double maxFrequency = null;

	private static Integer baud = null;
	private static Integer stopBits = null;
	private static Integer parityBits = null;
	private static Integer numBits = null;
	private static String[] portsRestrict = new String[64];

	private static HashMap<Integer, SerialPortRs232Parameter> parameters = new HashMap<Integer, SerialPortRs232Parameter>();
	private static HashMap<Integer, SerialPortRs232Channel> channels = new HashMap<Integer, SerialPortRs232Channel>();
	private static HashMap<String, Integer> timouts = new HashMap<String, Integer>();
	private static HashMap<Integer, SerialPortRs232Error> errors = new HashMap<Integer, SerialPortRs232Error>();

	public SerialPortRs232Config() throws Exception {

		uniqueId = getUniqueId();
		numChannels = getNumChannels();
		minFrequency = getMinFrequency();
		maxFrequency = getMaxFrequency();
		baud = getBaud();
		stopBits = getStopBits();
		parityBits = getParityBits();
		numBits = getNumBits();
		portsRestrict = getPortsRestrict();

	}

	public static String getUniqueId() {
		return uniqueId;
	}

	private static void setUniqueId(String value) {
		uniqueId = value;
	}

	public static Integer getNumChannels() {
		return numChannels;
	}

	private static void setNumChannels(Integer value) {
		numChannels = value;
	}

	public static Double getMinFrequency() {
		return minFrequency;
	}

	private static void setMinFrequency(Double value) {
		minFrequency = value;
	}

	public static Double getMaxFrequency() {
		return maxFrequency;

	}

	private static void setMaxFrequency(Double value) {
		maxFrequency = value;
	}

	public static Integer getBaud() {
		return baud;
	}

	private static void setBaud(Integer value) {
		baud = value;
	}

	public static Integer getStopBits() {
		return stopBits;
	}

	private static void setStopBits(Integer value) {
		stopBits = value;
	}

	public static Integer getParityBits() {
		return parityBits;
	}

	private static void setParityBits(Integer value) {
		parityBits = value;
	}

	public static Integer getNumBits() {
		return numBits;
	}

	private static void setNumBits(Integer value) {
		numBits = value;
	}

	public static String[] getPortsRestrict() {
		return portsRestrict;
	}

	private static void setPortsRestrict(String[] value) {
		portsRestrict = value;
	}

	public static Double applyTranferFunctionForParameter(Integer order, Double value, String io) {
		if (io.equalsIgnoreCase("IN")) {
			return parameters.get(order).applyInputTransferFunction(value);
		} else if (io.equalsIgnoreCase("OUT")) {
			return parameters.get(order).applyOutputTransferFunction(value);
		} else
			return null;
	}

	public static Double applyTranferFunctionForChannel(Integer order, Double value) {
		return parameters.get(order).applyOutputTransferFunction(value);
	}

	public static SerialPortRs232Parameter getParameter(int i) {
		return parameters.get(i);
	}

	public static SerialPortRs232Channel getChannel(int i) {
		return channels.get(i);
	}

	class XmlDocument {

	}

}
