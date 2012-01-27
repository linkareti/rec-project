/*
 * SerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 15:51
 */
package pt.utl.ist.elab.driver.serial.stamp.transproc;

public interface StampProcessor {

    public String getCommandIdentifier();

    /**
     * Process the given command 
     * 
     * @param command the command that this processor will have to process
     * @return boolean - true if the command was correctly processed false otherwise
     * 
     */
    public boolean process(final StampCommand command);

    public boolean isData();
}
