/*
 * SerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 15:51
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

public interface StampProcessor {
	public String getCommandIdentifier();

	public boolean process(StampCommand command);

	public boolean isData();
}
