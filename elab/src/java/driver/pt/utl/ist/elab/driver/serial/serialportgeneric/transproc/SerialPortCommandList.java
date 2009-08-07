package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

public enum SerialPortCommandList {
	IDS("ids"), CFG("cfg"), CFGOK("cfgok"), CUR("cur"), STR("str"), DAT("dat"), END("end"), BIN("bin"), STP("stp"), STPOK("stpok"), RST("rst"), RSTOK("rstok"), ERR("err");
	
	private String command = null; 
	
	SerialPortCommandList(String str) {
		this.command = str;
	}
	
	public String toString() {
		return this.command;
	}

}
