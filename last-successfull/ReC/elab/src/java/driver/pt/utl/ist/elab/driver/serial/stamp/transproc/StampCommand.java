/*
 * SerialPortCommand.java
 *
 * Created on 11 de Novembro de 2002, 15:34
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

import java.util.HashMap;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class StampCommand implements java.io.Serializable {
	static final long serialVersionUID = -2953294044465039790L;
	private String commandIdentifier = null;
	private String command = null;
	private HashMap<Object, Object> commandDataMap = null;
	private boolean isData = false;

	/** Creates a new instance of SerialPortCommand */
	public StampCommand(final String commandIdentifier) {
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

	public void addCommandData(final Object commandDataKey, final Object commandDataObject) {
		if (commandDataMap == null) {
			commandDataMap = new HashMap<Object, Object>(1);
		}

		if (commandDataMap.containsKey(commandDataKey)) {
			commandDataMap.remove(commandDataKey);
		}

		commandDataMap.put(commandDataKey, commandDataObject);
	}

	public Object getCommandData(final Object commandDataKey) {
		return commandDataMap.get(commandDataKey);
	}

	public void setData(final boolean isData) {
		this.isData = isData;
	}

	public boolean isData() {
		return isData;
	}

	private StampProcessor processor = null;

	public StampProcessor getProcessor() {
		if (processor == null) {
			processor = StampTranslatorProcessorManager.getProcessor(this);
		}

		return processor;
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
