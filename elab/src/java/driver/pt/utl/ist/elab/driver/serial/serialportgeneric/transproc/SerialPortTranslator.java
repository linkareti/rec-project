/*
 * SerialPortTranslator.java
 *
 * Created on 11 de Novembro de 2002, 15:50
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

public interface SerialPortTranslator {
	public String getCommandIdentifier();

	public boolean translate(SerialPortCommand command);
}
