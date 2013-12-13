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

	/**
	 * 
	 */
	private static final long serialVersionUID = -3156105637080167063L;

	/** Creates a new instance of PVXYGraphDisplay */
	public LumXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(7);
	}

	@Override
	public String getName() {
		return "Luminosidade";
	}
}
