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
public class VelWindXYGraphDisplay extends MeteoTimeExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5418568452573056899L;

	/** Creates a new instance of PVXYGraphDisplay */
	public VelWindXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(4);
	}

	@Override
	public String getName() {
		return "Velocidade do vento";
	}
}