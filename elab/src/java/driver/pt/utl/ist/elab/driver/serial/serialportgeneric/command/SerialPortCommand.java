/*
 * SerialPortCommand.java
 *
 * Created on 11 de Novembro de 2002, 15:34
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.command;

import java.util.HashMap;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public class SerialPortCommand implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7857767506247594478L;

	private String commandIdentifier = null;
	private String command = null;
	private HashMap<String, String> commandDataMap = null;
	private HashMap<Integer, String> returnHashMap = null;

	private boolean isData = false;

	/**
	 * Creates a new instance of SerialPortCommand
	 * 
	 * @param commandIdentifier
	 */
	public SerialPortCommand(final String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}

	public void setCommand(final String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommandIdentifier(final String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}

	public String getCommandIdentifier() {
		return commandIdentifier;
	}

	public void addCommandData(final String commandDataKey, final String commandDataObject) {
		if (commandDataMap == null) {
			commandDataMap = new HashMap<String, String>(1);
		}

		if (commandDataMap.containsKey(commandDataKey)) {
			commandDataMap.remove(commandDataKey);
		}

		commandDataMap.put(commandDataKey, commandDataObject);
	}

	public String getCommandData(final String commandDataKey) {
		return commandDataMap.get(commandDataKey);
	}

	public void setData(final boolean isData) {
		this.isData = isData;
	}

	public boolean isData() {
		return isData;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getDataHashMap() {
		// no command, no donut for you
		if (command == null) {
			return null;
		}
		// there is a command but no data hashmap? populate it
		if (returnHashMap == null) {
			returnHashMap = new HashMap<Integer, String>();
			final String[] commandStringArray = command.split("\t");
			for (int i = 0; i < commandStringArray.length; i++) {
				returnHashMap.put(i, commandStringArray[i]);
			}
			return returnHashMap;
		}
		// but if it is already populated, reuse it and save time
		else {
			return returnHashMap;
		}
	}

	public static boolean isResponse(final String response, final String sentMessage) {
		if (response == null || sentMessage == null) {
			return false;
		}
		final String[] responseArray = response.split("\t", 2);
		final String[] sentMessageArray = sentMessage.split("\t", 2);
		if (responseArray.length < 1 || sentMessageArray.length < 1) {
			return false;
		}
		if (responseArray[0].isEmpty() || sentMessageArray[0].isEmpty()) {
			return false;
		}
		// both are equivalent but response is upper case and set message is
		// lower case
		if (responseArray[0].equalsIgnoreCase(sentMessageArray[0])
				&& responseArray[0].toUpperCase().equals(responseArray[0])
				&& sentMessageArray[0].toLowerCase().equals(sentMessageArray[0])) {
			// Okay, it can be a valid response!

			if (responseArray.length == 2 && sentMessageArray.length == 2) {
				// command information must be equal
				if (responseArray[1].equals(sentMessageArray[1])) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + " CommandIdentifier [" + commandIdentifier + "] Command [" + command + "] IsData ["
				+ isData + "]";
	}

}