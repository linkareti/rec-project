package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

public enum SerialPortCommandList {
	IDS("IDS"), CFG("CFG"), CFGOK("CFGOK"), CUR("CUR"), STR("STR"), STROK("STROK"), DAT("DAT"), END("END"), BIN("BIN"), STP("STP"), STPOK(
			"STPOK"), RST("RST"), RSTOK("RSTOK"), ERR("ERR");

	private String command = null;

	SerialPortCommandList(String str) {
		this.command = str;
	}

	public String toString() {
		return this.command;
	}
	
	public static boolean exists(String str) {
		for (SerialPortCommandList commandList : SerialPortCommandList.values()) {
			if (commandList.toString().equals(str))
				return true;
		}
		return false;
	}

}
