package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.HashMap;

public class SerialPortHardwareInfo {

	private static Object[] dataKeys;
	private static String uniqueId = null;
	private static String familiarName = null;
	private static String name = null;
	private static String version = null;
	private static String manufacturer = null;
	private static String driverVersion = null;
	private static String description = null;

	private static HashMap<Integer, Parameter> parameterList = new HashMap<Integer, Parameter>();

	private static Integer samplingScaleMin = null;
	private static Integer samplingScaleMax = null;
	private static Integer samplingScaleStep = null;

	public SerialPortHardwareInfo() {

	}

	private class Parameter {

		private String name = null;
		private String type = null;
		private String value = null;
		private HashMap<Integer, String> selectionList = new HashMap<Integer, String>();

		public Parameter() {

		}

		public void addSelection(Integer order, String value) {
			selectionList.put(order, value);
		}

		public boolean hasOrder(Integer order) {
			return selectionList.containsKey(order);
		}

		public String getSelection(Integer order) {
			if (hasOrder(order)) {
				return selectionList.get(order);
			} else
				return null;
		}

	}

	public static Object[] getDataKeys() {
		return dataKeys;
	}

}
