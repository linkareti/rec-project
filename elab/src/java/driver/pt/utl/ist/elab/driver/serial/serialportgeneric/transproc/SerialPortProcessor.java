/*
 * SerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 15:51
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

public interface SerialPortProcessor {
	public String getCommandIdentifier();

	public boolean process(SerialPortCommand command);

	public boolean isData();
}
