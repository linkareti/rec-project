/*
 * Potential.java
 *
 * Created on 28 de Mar�o de 2005, 22:03
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
import org.opensourcephysics.numerics.ParsedFunction;
import org.opensourcephysics.numerics.ParserException;

/**
 * 
 * @author nomead
 */
public class Potential implements Bounded {

	private double xIPotential;
	private double potentialWidth;
	private final double dx = 1e-2;
	private ParsedFunction vFunction;

	private GeneralPath positivePath;
	private GeneralPath negativePath;
	private Shape positiveShape;
	private Shape negativeShape;

	private Color fillColor = Color.DARK_GRAY;
	private Color highLightColor = Color.GRAY;
	private Color positivePaintColor = fillColor;
	private Color negativePaintColor;

	private double xmax, xmin;
	private double ymax, ymin;

	private final boolean enabled;
	private boolean isMedio;

	public Potential(final double xI, final double vWidth, final String function, final boolean _enabled,
			final boolean _isMedio) {
		xIPotential = xI;
		potentialWidth = vWidth;
		try {
			vFunction = new ParsedFunction(function);
		} catch (final ParserException e) {
		}
		enabled = _enabled;
		isMedio = _isMedio;
		buildPath();
	}

	public Potential(final double xI, final double vWidth, final String function, final boolean _enabled,
			final boolean _isMedio, final Color _fillColor, final Color _highlightColor) {
		xIPotential = xI;
		potentialWidth = vWidth;
		try {
			vFunction = new ParsedFunction(function);
		} catch (final ParserException e) {
		}
		enabled = _enabled;
		isMedio = _isMedio;
		fillColor = positivePaintColor = negativePaintColor = _fillColor;
		highLightColor = _highlightColor;
		buildPath();
	}

	private void buildPath() {
		double ymaxHere = 0, yminHere = 0;
		double x = xIPotential;
		double y = 0;
		positivePath = new GeneralPath();
		negativePath = new GeneralPath();
		negativePath.moveTo((float) x, (float) y);
		positivePath.moveTo((float) x, (float) y);
		while (x < xIPotential + potentialWidth) {
			y = vFunction.evaluate(x - xIPotential);
			if (Double.isNaN(y) || Double.isInfinite(y)) {
				y = 0;// return;
				// else {
			}

			if (y < 0) {
				positivePath.moveTo((float) x, (float) y);
				if (negativePath.getCurrentPoint() == null) {
					negativePath.moveTo((float) x, (float) y);
				} else {
					negativePath.lineTo((float) x, (float) y);
				}
			} else {
				negativePath.moveTo((float) x, (float) y);
				if (positivePath.getCurrentPoint() == null) {
					positivePath.moveTo((float) x, (float) y);
				} else {
					positivePath.lineTo((float) x, (float) y);
				}
			}
			ymaxHere = Math.max(y, ymaxHere);
			yminHere = Math.min(y, yminHere);

			xmax = Math.max(x, xmax);
			xmin = Math.min(x, xmin);
			// }
			x += dx;
		}
		if (y >= 0) {
			positivePath.lineTo((float) (xIPotential + potentialWidth), 0);
		}
		if (y <= 0) {
			negativePath.lineTo((float) (xIPotential + potentialWidth), 0);
		}

		positivePath.transform(new AffineTransform(1, 0, 0, Math.abs(5e-1 / Math.max(Math.abs(yminHere),
				Math.abs(ymaxHere))), 0, 0));
		negativePath.transform(new AffineTransform(1, 0, 0, Math.abs(5e-1 / Math.max(Math.abs(yminHere),
				Math.abs(ymaxHere))), 0, 0));

		if (ymaxHere != 0) {
			ymaxHere *= Math.abs(5e-1 / ymaxHere);
		}
		if (yminHere != 0) {
			yminHere *= Math.abs(5e-1 / yminHere);
		}
		ymax = Math.max(ymaxHere, ymax);
		ymin = Math.min(yminHere, ymin);
	}

	@Override
	public void draw(final DrawingPanel panel, final Graphics g) {
		positiveShape = positivePath.createTransformedShape(panel.getPixelTransform());
		final Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativeShape = negativePath.createTransformedShape(panel.getPixelTransform());
		if (negativePaintColor == null) {
			g2.setPaint(panel.getBackground());
		} else {
			g2.setPaint(negativePaintColor);
		}
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	@Override
	public Interactive findInteractive(final DrawingPanel panel, final int _xpix, final int _ypix) {
		if (enabled) {
			final GeneralPath tmpPath = (GeneralPath) positivePath.clone();
			tmpPath.closePath();
			final GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
			ntmpPath.closePath();
			if (positiveShape != null
					&& negativeShape != null
					&& (tmpPath.createTransformedShape(panel.getPixelTransform()).contains(_xpix, _ypix) || ntmpPath
							.createTransformedShape(panel.getPixelTransform()).contains(_xpix, _ypix))) {
				return this;
			}
		}
		return null;
	}

	public void setColor(final Color _fillColor) {
		fillColor = _fillColor;
	}

	public Color getColor() {
		return fillColor;
	}

	public void setHighLightColor(final Color _highlight) {
		highLightColor = _highlight;
	}

	public void setWidth(final double vWidth) {
		potentialWidth = vWidth;
		buildPath();
	}

	public double getWidth() {
		return potentialWidth;
	}

	@Override
	public double getX() {
		return xIPotential;
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

	// move para o ponto x
	@Override
	public void setX(final double x) {
		if (enabled) {
			positivePath.transform(AffineTransform.getTranslateInstance(x - xIPotential, 0));
			negativePath.transform(AffineTransform.getTranslateInstance(x - xIPotential, 0));
			xIPotential = x;
		}
	}

	// translacao (x,0) -> Usado para o arrasto do rato
	@Override
	public void setXY(final double x, final double y) {
		if (enabled) {
			xIPotential += x;
			positivePath.transform(AffineTransform.getTranslateInstance(x, 0));
			negativePath.transform(AffineTransform.getTranslateInstance(x, 0));
		}
	}

	@Override
	public void setY(final double y) {
	}

	@Override
	public void mouseOver(final DrawingPanel panel) {
		positivePaintColor = highLightColor;
		final Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativePaintColor = highLightColor;
		g2.setPaint(negativePaintColor);
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	@Override
	public void mouseOut(final DrawingPanel panel) {
		positivePaintColor = fillColor;
		final Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativePaintColor = panel.getBackground();
		g2.setPaint(negativePaintColor);
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	@Override
	public boolean intersect(final Rectangle2D rect) {
		final GeneralPath tmpPath = (GeneralPath) positivePath.clone();
		tmpPath.closePath();
		final GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
		ntmpPath.closePath();

		if (positiveShape != null
				&& negativeShape != null
				&& (tmpPath.createTransformedShape(null).getBounds2D().intersects(rect) || ntmpPath
						.createTransformedShape(null).getBounds2D().intersects(rect))) {
			return true;
		}
		return false;
	}

	@Override
	public Rectangle2D getBounds(final double x) {
		final GeneralPath tmpPath = (GeneralPath) positivePath.clone();
		tmpPath.closePath();
		final GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
		ntmpPath.closePath();

		tmpPath.transform(AffineTransform.getTranslateInstance(x, 0));
		ntmpPath.transform(AffineTransform.getTranslateInstance(x, 0));

		return tmpPath.createTransformedShape(null).getBounds2D()
				.createUnion(ntmpPath.createTransformedShape(null).getBounds2D());
	}

	public String getFunction() {
		return vFunction.toString();
	}

	public void setFunction(final String func) throws ParserException {
		vFunction = new ParsedFunction(func);
		buildPath();
	}

	@Override
	public String toString() {
		return xIPotential + ":" + potentialWidth + ":" + getFunction() + ":" + isMedio;
	}

	public boolean isMedio() {
		return isMedio;
	}

	public void setMedio(final boolean _isMedio) {
		isMedio = _isMedio;
	}

	@Override
	public Color getHighLightColor() {
		return highLightColor;
	}

	@Override
	public Color getPaintColor() {
		return positivePaintColor;
	}

}
