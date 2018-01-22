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
public class DirWindXYGraphDisplay extends MeteoTimeExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4681168056634185544L;

	/** Creates a new instance of PVXYGraphDisplay */
	public DirWindXYGraphDisplay() {
		super();
		setChannelTime(0);
		setChannelDisplayY(3);
	}

	@Override
	public String getName() {
		return "Direccao do vento";
	}
}