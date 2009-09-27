/*
 * DataBufferListener.java
 *
 * Created on 18 October 2003, 01:22
 */

package pt.utl.ist.elab.driver.statsound.audio;

/**
 * 
 * @author andre
 */
public interface DataBufferListener extends java.util.EventListener {
	public void bufferAvailable(NewDataBufferEvent evt);
}
