/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.meteo.displays;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class LumXYGraphDisplay extends MeteoTimeExperimentGraph {

	/** Creates a new instance of PVXYGraphDisplay */
	public LumXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(7);
	}

	public String getName() {
		return "Luminosidade";
	}
}
