package pt.utl.ist.elab.driver.serial.serialportgeneric.rs232;

public class SerialPortRs232Error {

	private Integer code;
	private String key;
	private String message;

	public SerialPortRs232Error(Integer code, String key, String message) throws Exception {
		if ((code == null && key == null) || message == null)
			throw new Exception("invalid.parameters.for.an.error.code");
		this.code = code;
		this.key = key;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
