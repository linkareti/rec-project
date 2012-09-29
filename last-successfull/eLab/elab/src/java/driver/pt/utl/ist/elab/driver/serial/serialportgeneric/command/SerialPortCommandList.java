package pt.utl.ist.elab.driver.serial.serialportgeneric.command;

public enum SerialPortCommandList {
	IDS("IDS"), CFG("CFG"), CFGOK("CFGOK"), CUR("CUR"), STR("STR"), STROK("STROK"), DAT("DAT"), END("END"), BIN("BIN"), STP(
			"STP"), STPOK("STPOK"), RST("RST"), RSTOK("RSTOK"), ERR("ERR");

	private String command = null;

	SerialPortCommandList(final String str) {
		command = str;
	}

	@Override
	public String toString() {
		return command;
	}

	public static boolean exists(final String str) {
		for (final SerialPortCommandList commandList : SerialPortCommandList.values()) {
			if (commandList.toString().equals(str)) {
				return true;
			}
		}
		return false;
	}

}
