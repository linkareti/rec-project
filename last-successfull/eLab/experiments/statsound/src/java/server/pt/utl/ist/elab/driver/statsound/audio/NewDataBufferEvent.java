/*
 * NewDataBufferEvent.java
 *
 * Created on 18 October 2003, 01:41
 */

package pt.utl.ist.elab.driver.statsound.audio;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class NewDataBufferEvent {

	javax.media.Buffer buffer;

	/** Creates a new instance of NewDataBufferEvent */
	public NewDataBufferEvent(javax.media.Buffer buffer) {
		this.buffer = buffer;
	}

	public javax.media.Buffer getBuffer() {
		return buffer;
	}
}
