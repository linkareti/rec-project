/*
 * StopEvent.java
 *
 * Created on 5 de Junho de 2003, 17:53
 */

package pt.utl.ist.elab.driver.serial.stamp;

import com.linkare.rec.impl.events.Prioritazible;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class StopEvent implements Prioritazible {

	/** Creates a new instance of StopEvent */
	public StopEvent() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MAXIMUM;
	}

}
