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

    /**
     * Marks the processor as a data processor or not. 
     * 
     * @return true if this processor is data and false otherwise.
     */
    public boolean isData();
}
