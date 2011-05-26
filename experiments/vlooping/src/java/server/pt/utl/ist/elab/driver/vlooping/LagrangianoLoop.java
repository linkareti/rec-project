/*
 * LagrangianoLoop.java
 *
 * Created on 6 de Abril de 2005, 5:11
 */

package pt.utl.ist.elab.driver.vlooping;

import org.opensourcephysics.numerics.ODE;

/**
 * 
 * @author Emanuel Antunes
 */
public class LagrangianoLoop implements ODE {

	private final double raio;
	private final double g; // valor a tomar para a acceleracao gravitacional
	private final double[] state;

	/**
	 * Contrutores da classe Contrutor por omissao
	 */
	public LagrangianoLoop() {

		raio = 4.0;
		g = 9.8;
		state = new double[] { 0, 0, 0 };
	}

	/* Construtor com argumentos */
	public LagrangianoLoop(final double anguloInic, final double omegaInic, final double raio, final double g) {
		this.raio = raio;
		this.g = g;
		state = new double[] { anguloInic, omegaInic, 0 }; // respectivamente
		// {theta, dtheta,
		// tempo]
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {

		/* variacao do theta */
		rate[0] = state[1];

		/* variacao do dtheta */
		rate[1] = -g * Math.sin(state[0]) / raio;

		/* a derivada do tempo e' 1 */
		rate[2] = 1;
	}

	@Override
	public double[] getState() {
		return state;
	}

}
