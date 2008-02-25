/*
 * DataBufferListener.java
 *
 * Created on 18 October 2003, 01:22
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound.audio;

/**
 *
 * @author  Andr�
 */
public interface DataSoundListener extends java.util.EventListener
{
    public void bufferAvailable(NewDataBufferEvent evt);
    public void rmsAvailable();
}
