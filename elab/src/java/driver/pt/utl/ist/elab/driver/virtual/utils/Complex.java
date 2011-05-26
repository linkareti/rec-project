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

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301752841947664972L;
	private double re, im;

	/** Creates a new instance of Complex */
	public Complex(final double _re, final double _im) {
		re = _re;
		im = _im;
	}

	/*
	 * Verifica se e um numero valido
	 */
	public boolean isNumber() {
		if (Double.isNaN(re) || Double.isNaN(im) || Double.isInfinite(re) || Double.isInfinite(im)) {
			return false;
		}

		return true;
	}

	public double abs() {
		final double reMod = Math.abs(re);
		final double imMod = Math.abs(im);
		double ratio = 0;

		if (reMod == 0) {
			return imMod;
		} else if (imMod == 0) {
			return reMod;
		} else if (reMod >= imMod) {
			ratio = im / re;
			return reMod * Math.sqrt(1 + ratio * ratio);
		} else {
			ratio = re / im;
			return imMod * Math.sqrt(1 + ratio * ratio);
		}
	}

	public double square() {
		return re * re + im * im;
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public void conjugate() {
		im = -im;
	}

	public static Complex conjugate(final Complex a) {
		return new Complex(a.getRe(), -a.getIm());
	}

	public void plus(final Complex a) {
		re += a.getRe();
		im += a.getIm();
	}

	public static Complex plus(final Complex a, final Complex b) {
		return new Complex(a.getRe() + b.getRe(), a.getIm() + b.getIm());
	}

	public void minus(final Complex a) {
		re -= a.getRe();
		im -= a.getIm();
	}

	public static Complex minus(final Complex a, final Complex b) {
		return new Complex(a.getRe() - b.getRe(), a.getIm() - b.getIm());
	}

	public void times(final double a) {
		re *= a;
		im *= a;
	}

	public void times(final Complex a) {
		re = re * a.getRe() - im * a.getIm();
		im = re * a.getIm() + im * a.getRe();
	}

	public static Complex times(final Complex a, final Complex b) {
		return new Complex((a.getRe() * b.getRe() - a.getIm() * b.getIm()), (a.getRe() * b.getIm() + a.getIm()
				* b.getRe()));
	}

	public void expI() {
		final double r = re;
		final double i = im;

		if (i == 0) {
			re = Math.exp(r);
		} else if (r == 0) {
			re = Math.cos(i);
			im = Math.sin(i);
		} else {
			re = Math.cos(i) * Math.exp(r);
			im = Math.sin(i) * Math.exp(r);
		}
	}

	public static Complex expI(final Complex a) {
		final double r = a.getRe();
		final double i = a.getIm();

		if (i == 0) {
			return new Complex(Math.exp(r), 0);
		} else if (r == 0) {
			return new Complex(Math.cos(i), Math.sin(i));
		} else {
			return new Complex(Math.cos(i) * Math.exp(r), Math.sin(i) * Math.exp(r));
		}

	}

}
