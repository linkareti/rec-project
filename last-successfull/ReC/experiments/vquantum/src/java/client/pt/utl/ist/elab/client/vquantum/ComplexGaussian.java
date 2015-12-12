/*
 * ComplexGaussian.java
 *
 * Created on 29 de Mar�o de 2005, 15:00
 */

package pt.utl.ist.elab.client.vquantum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

import pt.utl.ist.elab.common.virtual.utils.Complex;
import pt.utl.ist.elab.common.virtual.utils.FFT;

/**
 * 
 * @author nomead
 */
public class ComplexGaussian implements Bounded {

	public static byte DISPLAY_PROBABILITY = 0;
	public static byte DISPLAY_REAL = 1;
	public static byte DISPLAY_IMAGINARY = 2;
	private int display;

	private static final double h = 7.627549007;
	private static final double hbar = 6.58195389e-16; // constante planck
	// radiana (eV)

	private GeneralPath generalPath;
	private GeneralPath psiKSPath;
	private Complex[] psi;
	// private Complex [] psiKS;

	private double dx = 1e-2;

	private Shape gaussShape;
	private Shape psiKShape;
	private Color color = Color.ORANGE;
	private final Color highLightColor = Color.RED;
	private Color paintColor = color;
	private final Color paintKSColor = Color.GREEN;

	private double xmax, xmin;
	private double ymax, ymin;

	private boolean enabled;

	private int N;
	private double x0;
	private double dX0;
	private double deltaX;
	private double k0;

	private boolean isKSPathNew;
	private boolean showingKS;
	private double dk;
	private double ymaxKS;
	private double yminKS;
	private double xmaxKS;
	private double xminKS;

	/** Creates a new instance of ComplexGaussian 
	 * @param _dX0 
	 * @param _x0 
	 * @param _energy 
	 * @param log2N 
	 * @param _deltaX 
	 * @param _display 
	 * @param _enabled */
	public ComplexGaussian(final double _dX0, final double _x0, final double _energy, final int log2N,
			final double _deltaX, final int _display, final boolean _enabled) {
		enabled = _enabled;
		N = (int) Math.round(Math.pow(2, log2N));
		dX0 = _dX0;
		dx = dX0 / (N - 1);
		x0 = _x0;
		deltaX = _deltaX;
		k0 = Math.sqrt(2 * _energy / ComplexGaussian.h);
		dk = 2 * Math.PI / ((N - 1) * dx);

		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		display = _display;
		buildPath(display);
	}

	public void configGaussian(final double _dX0, final double _x0, final double _energy, final int log2N,
			final double _deltaX, final int _display, final boolean _enabled) {
		enabled = _enabled;
		N = (int) Math.round(Math.pow(2, log2N));
		dX0 = _dX0;
		dx = dX0 / (N - 1);
		x0 = _x0;
		deltaX = _deltaX;
		k0 = Math.sqrt(2 * _energy / ComplexGaussian.h);
		dk = 2 * Math.PI / ((N - 1) * dx);

		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		display = _display;
		buildPath(display);
	}

	public Complex getInitialPsi(final double x) {
		final Complex _psi = Complex.expI(new Complex(-Math.pow((x - x0), 2) / (4 * deltaX * deltaX), k0 * x));
		_psi.times((1 / Math.pow((2 * Math.PI * deltaX * deltaX), 1 / 4)));

		return _psi;
	}

	private void buildPath(final int psiType) {
		generalPath = new GeneralPath();
		double y = 0;
		ymax = 0;
		ymin = 0;
		xmax = 0;
		xmin = 0;
		generalPath.moveTo((float) (x0 - dX0 / 2), 0);
		for (int i = 0; i < psi.length; i++) {
			if (!psi[i].isNumber()) {
				return;
			} else {
				final double x = dx * i + x0 - dX0 / 2;
				if (psiType == ComplexGaussian.DISPLAY_PROBABILITY) {
					y = Math.pow(psi[i].abs(), 2);
				} else if (psiType == ComplexGaussian.DISPLAY_REAL) {
					y = psi[i].getRe();
				} else if (psiType == ComplexGaussian.DISPLAY_IMAGINARY) {
					y = psi[i].getIm();
				}

				ymax = Math.max(y, ymax);
				ymin = Math.min(y, ymin);
				ymin = Math.min(ymin, -ymax);

				xmax = Math.max(x, xmax);
				xmin = Math.min(x, xmin);

				// if (Math.abs(y) > 1e-4)
				// if (generalPath.getCurrentPoint() == null)
				// generalPath.moveTo((float) x, (float) y);
				// else
				generalPath.lineTo((float) x, (float) y);
			}
		}
		generalPath.lineTo((float) (x0 + dX0 / 2), 0);
	}

	public void buildPsiKS() {
		final Complex[] psiKS = getPsiKS();
		psiKSPath = new GeneralPath();

		ymaxKS = 0;
		yminKS = 0;
		xmaxKS = 0;
		xminKS = 0;

		for (int i = 0; i < psi.length; i++) {
			if (!psiKS[i].isNumber()) {
				return;
			} else {
				final double x = dk * i;
				final double y = Math.pow(psiKS[i].abs(), 2);

				// if (Math.abs(y) > 1e-4)
				if (psiKSPath.getCurrentPoint() == null) {
					psiKSPath.moveTo((float) x, (float) y);
				} else {
					psiKSPath.lineTo((float) x, (float) y);
				}

				ymaxKS = Math.max(y, ymaxKS);
				yminKS = Math.min(y, yminKS);

				xmaxKS = Math.max(x, xmaxKS);
				xminKS = Math.min(x, xminKS);
			}
		}
		isKSPathNew = true;
	}

	public void setShowingKS(final boolean showing) {
		showingKS = showing;
	}

	public double[] getMinMaxYKS() {
		return new double[] { yminKS, ymaxKS };
	}

	public double[] getMinMaxXKS() {
		return new double[] { xminKS, xmaxKS };
	}

	private Complex[] getPsiKS() {
		final Complex[] psiKS = FFT.calculateFFT(psi, (int) (Math.log(N) / Math.log(2)), 1);
		final Complex[] psiKSArranged = new Complex[psiKS.length];
		for (int i = psiKS.length / 2; i < psiKS.length; i++) {
			psiKSArranged[i - psiKS.length / 2] = psiKS[i];
			psiKSArranged[i] = psiKS[i - psiKS.length / 2];
		}
		return psiKSArranged;
	}

	public void drawPsiKS(final DrawingPanel panel, final Graphics g) {
		if (isKSPathNew) {
			panel.setPreferredMinMax(xminKS, xmaxKS, yminKS, ymaxKS);
			isKSPathNew = false;
		}
		panel.setPixelScale();
		psiKShape = psiKSPath.createTransformedShape(panel.getPixelTransform());
		final Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(paintKSColor);
		g2.draw(psiKShape);
		g2.fill(psiKShape);
		g2.setPaint(Color.BLACK);
		g2.drawLine(panel.xToPix(dk * (N - (int) (N * 0.05))), panel.yToPix(yminKS),
				panel.xToPix(dk * (N - (int) (N * 0.05))), panel.yToPix(ymaxKS));
		g2.drawLine(panel.xToPix(dk * ((int) (N * 0.05))), panel.yToPix(yminKS), panel.xToPix(dk * ((int) (N * 0.05))),
				panel.yToPix(ymaxKS));
	}

	@Override
	public void draw(final DrawingPanel panel, final Graphics g) {
		gaussShape = generalPath.createTransformedShape(panel.getPixelTransform());
		final Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(paintColor);
		g2.draw(gaussShape);
		g2.fill(gaussShape);
		g2.setPaint(Color.BLACK);
		g2.drawLine(panel.xToPix(dx * (N - (int) (N * 0.05)) + x0 - dX0 / 2), panel.yToPix(ymin),
				panel.xToPix(dx * (N - (int) (N * 0.05)) + x0 - dX0 / 2), panel.yToPix(ymax));
		g2.drawLine(panel.xToPix(dx * ((int) (N * 0.05)) + x0 - dX0 / 2), panel.yToPix(ymin),
				panel.xToPix(dx * ((int) (N * 0.05)) + x0 - dX0 / 2), panel.yToPix(ymax));
	}

	@Override
	public Interactive findInteractive(final DrawingPanel panel, final int _xpix, final int _ypix) {
		if (enabled) {
			final GeneralPath tmpPath = (GeneralPath) generalPath.clone();
			tmpPath.closePath();
			if (gaussShape != null && tmpPath.createTransformedShape(panel.getPixelTransform()).contains(_xpix, _ypix)) {
				return this;
			}
		}
		return null;
	}

	public void setDisplay(final int _display) {
		display = _display;
		buildPath(display);
	}

	public int getDisplay() {
		return display;
	}

	public void setColor(final java.awt.Color _color) {
		color = _color;
	}

	public void setEnergy(final double _energy) {
		k0 = Math.sqrt(2 * _energy / ComplexGaussian.h);
		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		buildPath(display);
		if (showingKS) {
			buildPsiKS();
		}
	}

	public double getEnergy() {
		return k0 * k0 * ComplexGaussian.h / 2;
	}

	public void setLog2N(final int n) {
		N = (int) Math.pow(2, n);
		dx = dX0 / (N - 1);
		dk = 2 * Math.PI / ((N - 1) * dx);
		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		buildPath(display);
		if (showingKS) {
			buildPsiKS();
		}
	}

	public void setDX0(final double _dX0) {
		dX0 = _dX0;
		if (dX0 / 8 < deltaX) {
			deltaX = dX0 / 8;
		}
		dx = dX0 / (N - 1);
		dk = 2 * Math.PI / ((N - 1) * dx);
		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		buildPath(display);
		if (showingKS) {
			buildPsiKS();
		}
	}

	public double getDX0() {
		return dX0;
	}

	public void setDeltaX(final double _deltaX) {
		deltaX = _deltaX;
		psi = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - dX0 / 2);
		}
		buildPath(display);
		if (showingKS) {
			buildPsiKS();
		}
	}

	public double getDeltaX() {
		return deltaX;
	}

	@Override
	public double getX() {
		return x0;
	}

	@Override
	public double getXMax() {
		return xmax;
	}

	@Override
	public double getXMin() {
		return xmin;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double getYMax() {
		return ymax;
	}

	@Override
	public double getYMin() {
		return ymin;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isMeasured() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		enabled = enabled;
	}

	@Override
	public void setX(final double x) {
		if (enabled) {
			generalPath.transform(AffineTransform.getTranslateInstance(x - x0, 0));
			x0 = x;
		}
	}

	@Override
	public void setXY(final double x, final double y) {
		if (enabled) {
			x0 += x;
			generalPath.transform(AffineTransform.getTranslateInstance(x, 0));
		}
	}

	@Override
	public void setY(final double y) {
	}

	@Override
	public Rectangle2D getBounds(final double x) {
		return new Rectangle2D.Double(0, 0, 0, 0);// new
		// Rectangle2D.Double(x0+x-deltaX*3.5,-1,7*deltaX,2);
	}

	@Override
	public void mouseOver(final DrawingPanel panel) {
		paintColor = highLightColor;
		final Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(paintColor);
		g2.draw(gaussShape);
		g2.fill(gaussShape);
	}

	@Override
	public void mouseOut(final DrawingPanel panel) {
		paintColor = color;
		final Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(paintColor);
		g2.draw(gaussShape);
		g2.fill(gaussShape);
	}

	@Override
	public boolean intersect(final Rectangle2D rect) {
		// if (gaussShape != null &&
		// rect.intersects(x0-deltaX*3.5,-1,7*deltaX,2))
		// return true;
		return false;
	}

	@Override
	public Color getPaintColor() {
		return paintColor;
	}

	@Override
	public Color getHighLightColor() {
		return highLightColor;
	}

	public void setPsi(final Complex[] _psi) {
		psi = _psi;
		buildPath(display);
		if (showingKS) {
			buildPsiKS();
		}
	}

}