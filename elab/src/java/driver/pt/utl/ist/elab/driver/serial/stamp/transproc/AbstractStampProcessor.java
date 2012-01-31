/*
 * AbstractSerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 16:03
 */
package pt.utl.ist.elab.driver.serial.stamp.transproc;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractStampProcessor implements StampProcessor {

    private String commandIdentifier = null;

    /** Creates a new instance of AbstractSerialPortProcessor 
     * @param commandIdentifier */
    public AbstractStampProcessor(final String commandIdentifier) {
        this.commandIdentifier = commandIdentifier;
        StampTranslatorProcessorManager.registerProcessor(this);
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    /**
     * By default we assume the implementing processor is never a data processor
     * @return false
     */
    @Override
    public boolean isData() {
        return false;
    }
}
