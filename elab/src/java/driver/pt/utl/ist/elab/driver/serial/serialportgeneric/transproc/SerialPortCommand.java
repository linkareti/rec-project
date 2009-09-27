/*
 * SerialPortCommand.java
 *
 * Created on 11 de Novembro de 2002, 15:34
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

import java.util.HashMap;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SerialPortCommand implements java.io.Serializable {
	static final long serialVersionUID = -2953294044465039790L;
	private String commandIdentifier = null;
	private String command = null;
	private HashMap<Object, Object> commandDataMap = null;
	private boolean isData = false;

	/**
	 * Creates a new instance of SerialPortCommand
	 * 
	 * @param commandIdentifier
	 */
	public SerialPortCommand(String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommandIdentifier(String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}

	public String getCommandIdentifier() {
		return this.commandIdentifier;
	}

	public void addCommandData(Object commandDataKey, Object commandDataObject) {
		if (this.commandDataMap == null)
			this.commandDataMap = new HashMap<Object, Object>(1);

		if (this.commandDataMap.containsKey(commandDataKey))
			this.commandDataMap.remove(commandDataKey);

		this.commandDataMap.put(commandDataKey, commandDataObject);
	}

	public Object getCommandData(Object commandDataKey) {
		return this.commandDataMap.get(commandDataKey);
	}

	public void setData(boolean isData) {
		this.isData = isData;
	}

	public boolean isData() {
		return this.isData;
	}

	/*
	 * private SerialPortProcessor processor = null;
	 * 
	 * public SerialPortProcessor getProcessor() { if (processor == null)
	 * processor = SerialPortTranslatorProcessorManager.getProcessor(this);
	 * 
	 * return processor; }
	 */
}
