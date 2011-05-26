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
public class PressionXYGraphDisplay extends MeteoTimeExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7750145647376390541L;

	/** Creates a new instance of PVXYGraphDisplay */
	public PressionXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(8);
	}

	@Override
	public String getName() {
		return "Pressao";
	}
}
