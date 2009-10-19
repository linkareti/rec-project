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
 * 
 * @author fdias
 * @date 10/2009
 * 
 * 
 */
public class SerialPortCommand implements java.io.Serializable {
	static final long serialVersionUID = -2953294044465039790L;
	private String commandIdentifier = null;
	private String command = null;
	private HashMap<String, String> commandDataMap = null;
	
	private HashMap<Integer,String> returnHashMap = null;
	
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

	public void addCommandData(String commandDataKey, String commandDataObject) {
		if (this.commandDataMap == null)
			this.commandDataMap = new HashMap<String, String>(1);

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

	/**
	 * @author fdias
	 */
	public HashMap<Integer,String> getDataHashMap() {
		// no command, no donut for you
		if (command == null)
			return null;
		// there is a command but no data hashmap? populate it
		if (returnHashMap == null) {
			returnHashMap = new HashMap<Integer,String>();
			String[] commandStringArray = command.split("\t");
			for (int i = 1; i < commandStringArray.length; i++) {
				returnHashMap.put(i, commandStringArray[i]);
			}
			return returnHashMap;
		}
		// but if it is already populated, reuse it and save time
		else
			return returnHashMap;
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
