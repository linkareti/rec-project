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
public class CondXYGraphDisplay extends MeteoTimeExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7583299662421005389L;

	/** Creates a new instance of PVXYGraphDisplay */
	public CondXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(6);
	}

	@Override
	public String getName() {
		return "Condensacao";
	}
}
