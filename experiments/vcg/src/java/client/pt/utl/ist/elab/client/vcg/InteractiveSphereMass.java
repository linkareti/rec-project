/*
 * InteractiveSphereMass.java
 *
 * Created on 3 de Dezembro de 2004, 0:10
 */

package pt.utl.ist.elab.client.vcg;

import org.opensourcephysics.displayejs.InteractiveSphere;

/**
 * 
 * @author Googol
 */
public class InteractiveSphereMass extends InteractiveSphere {

	private double mass;

	/** Creates a new instance of InteractiveSphereMass 
	 * @param m */
	public InteractiveSphereMass(final double m) {
		mass = m;
	}

	public void setMass(final double m) {
		mass = m;
	}

	public double getMass() {
		return mass;
	}

}
