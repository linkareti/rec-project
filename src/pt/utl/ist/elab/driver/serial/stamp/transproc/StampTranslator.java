/*
 * SerialPortTranslator.java
 *
 * Created on 11 de Novembro de 2002, 15:50
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

public interface StampTranslator
{
    public String getCommandIdentifier();	
    public boolean translate(StampCommand command);
}
