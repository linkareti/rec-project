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
public class HumXYGraphDisplay extends MeteoTimeExperimentGraph {

	/** Creates a new instance of PVXYGraphDisplay */
	public HumXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(5);
	}

	public String getName() {
		return "Humidade";
	}
}
