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
public class PrecXYGraphDisplay extends MeteoTimeExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6176102905374049861L;

	/** Creates a new instance of PVXYGraphDisplay */
	public PrecXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(2);
	}

	@Override
	public String getName() {
		return "Precipitacao";
	}
}
