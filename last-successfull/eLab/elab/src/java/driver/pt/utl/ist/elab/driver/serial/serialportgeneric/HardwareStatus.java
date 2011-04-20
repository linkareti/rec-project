package pt.utl.ist.elab.driver.serial.serialportgeneric;

public enum HardwareStatus {
	RESETED, STOPED, CONFIGURED, STARTED, ERROR, UNKNOWN;

	public static boolean isValid(String status) {
		if (status == null || status.equals(""))
			return false;
		for (HardwareStatus hs : HardwareStatus.values()) {
			if (hs.toString().equals(status))
				return true;
		}
		return false;
	}

}
