package pt.utl.ist.elab.driver.virtual.utils;

/*
 * Complex.java
 *
 * Created on May 1, 2004, 3:43 PM
 */

/**
 * 
 * @author nomead
 */
public class Complex implements java.io.Serializable {

	private double re, im;

	/** Creates a new instance of Complex */
	public Complex(double _re, double _im) {
		re = _re;
		im = _im;
	}

	/*
	 * Verifica se e um numero valido
	 */
	public boolean isNumber() {
		if (Double.isNaN(this.re) || Double.isNaN(this.im) || Double.isInfinite(this.re) || Double.isInfinite(this.im))
			return false;

		return true;
	}

	public double abs() {
		double reMod = Math.abs(this.re);
		double imMod = Math.abs(this.im);
		double ratio = 0;

		if (reMod == 0)
			return imMod;
		else if (imMod == 0)
			return reMod;
		else if (reMod >= imMod) {
			ratio = this.im / this.re;
			return reMod * Math.sqrt(1 + ratio * ratio);
		} else {
			ratio = this.re / this.im;
			return imMod * Math.sqrt(1 + ratio * ratio);
		}
	}

	public double square() {
		return this.re * this.re + this.im * this.im;
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public void conjugate() {
		this.im = -this.im;
	}

	public static Complex conjugate(Complex a) {
		return new Complex(a.getRe(), -a.getIm());
	}

	public void plus(Complex a) {
		this.re += a.getRe();
		this.im += a.getIm();
	}

	public static Complex plus(Complex a, Complex b) {
		return new Complex(a.getRe() + b.getRe(), a.getIm() + b.getIm());
	}

	public void minus(Complex a) {
		this.re -= a.getRe();
		this.im -= a.getIm();
	}

	public static Complex minus(Complex a, Complex b) {
		return new Complex(a.getRe() - b.getRe(), a.getIm() - b.getIm());
	}

	public void times(double a) {
		this.re *= a;
		this.im *= a;
	}

	public void times(Complex a) {
		this.re = this.re * a.getRe() - this.im * a.getIm();
		this.im = this.re * a.getIm() + this.im * a.getRe();
	}

	public static Complex times(Complex a, Complex b) {
		return new Complex((a.getRe() * b.getRe() - a.getIm() * b.getIm()), (a.getRe() * b.getIm() + a.getIm()
				* b.getRe()));
	}

	public void expI() {
		double r = this.re;
		double i = this.im;

		if (i == 0)
			this.re = Math.exp(r);
		else if (r == 0) {
			this.re = Math.cos(i);
			this.im = Math.sin(i);
		} else {
			this.re = Math.cos(i) * Math.exp(r);
			this.im = Math.sin(i) * Math.exp(r);
		}
	}

	public static Complex expI(Complex a) {
		double r = a.getRe();
		double i = a.getIm();

		if (i == 0)
			return new Complex(Math.exp(r), 0);
		else if (r == 0)
			return new Complex(Math.cos(i), Math.sin(i));
		else
			return new Complex(Math.cos(i) * Math.exp(r), Math.sin(i) * Math.exp(r));

	}

}
