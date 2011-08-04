/*
 * Disco.java
 *
 * Created on 24 de Mar�o de 2005, 1:41
 */

/**
 *
 * @author  The Godfather
 */

package pt.utl.ist.elab.driver.vdi;

/** Imports
 */
import org.opensourcephysics.numerics.ODE;

public class Disco implements ODE {
	private final double ri, re, inc, aT;
	static public final double g = 9.8;
	private final double[] state = new double[7];

	/** Creates a new instance of Disco */
	public Disco(final double raioInt, final double raioExt, final double angulo) {
		ri = raioInt;
		re = raioExt;
		inc = angulo;
		aT = (2 * Disco.g * re * re * Math.sin(inc)) / ((3 * re * re) + (ri * ri));

		final double beta = (Math.PI / 2.0) - angulo;

		state[0] = re * Math.cos(beta);
		state[1] = 0;
		state[2] = re * Math.sin(beta);
		state[3] = 0;
		state[4] = 0;
		state[5] = 0;
		state[6] = 0;
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {
		rate[0] = state[1];
		rate[1] = aT * Math.cos(inc);
		rate[2] = state[3];
		rate[3] = -aT * Math.sin(inc);
		rate[4] = state[5];
		rate[5] = aT / re;
		rate[6] = 1;
	}

	public double getModuloVelocidade() {
		return Math.sqrt(state[1] * state[1] + state[3] * state[3]);
	}

	public double getEspacoPercorrido() {
		return calcDist(re * Math.sin(inc), re * Math.cos(inc), state[0], state[2]);
	}

	private double calcDist(final double _x1, final double _y1, final double _x2, final double _y2) {
		return Math.sqrt(Math.pow((_x2 - _x1), 2) + Math.pow((_y2 - _y1), 2));
	}

	public double getInclinacao() {
		return inc;
	}

	public double getRaioInt() {
		return ri;
	}

	public double getRaioExt() {
		return re;
	}

	@Override
	public double[] getState() {
		return state;
	}

	// public void setInclinacao(double angulo) { inc = angulo; }

	// public void setRaioInt(double raioInt) { ri = raioInt; }

	// public void setRaioExt(double raioExt) { ri = raioExt; }
}