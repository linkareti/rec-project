/*
 * Potential.java
 *
 * Created on 28 de Mar�o de 2005, 22:03
 */

package pt.utl.ist.elab.virtual.client.quantum;

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
	private double dx = 1e-2;
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

	private boolean enabled;
	private boolean isMedio;

	public Potential(double xI, double vWidth, String function, boolean _enabled, boolean _isMedio) {
		xIPotential = xI;
		potentialWidth = vWidth;
		try {
			vFunction = new ParsedFunction(function);
		} catch (ParserException e) {
		}
		enabled = _enabled;
		isMedio = _isMedio;
		buildPath();
	}

	public Potential(double xI, double vWidth, String function, boolean _enabled, boolean _isMedio, Color _fillColor,
			Color _highlightColor) {
		xIPotential = xI;
		potentialWidth = vWidth;
		try {
			vFunction = new ParsedFunction(function);
		} catch (ParserException e) {
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
			if (Double.isNaN(y) || Double.isInfinite(y))
				y = 0;// return;
			// else {

			if (y < 0) {
				positivePath.moveTo((float) x, (float) y);
				if (negativePath.getCurrentPoint() == null)
					negativePath.moveTo((float) x, (float) y);
				else
					negativePath.lineTo((float) x, (float) y);
			} else {
				negativePath.moveTo((float) x, (float) y);
				if (positivePath.getCurrentPoint() == null)
					positivePath.moveTo((float) x, (float) y);
				else
					positivePath.lineTo((float) x, (float) y);
			}
			ymaxHere = Math.max(y, ymaxHere);
			yminHere = Math.min(y, yminHere);

			xmax = Math.max(x, xmax);
			xmin = Math.min(x, xmin);
			// }
			x += dx;
		}
		if (y >= 0)
			positivePath.lineTo((float) (xIPotential + potentialWidth), (float) 0);
		if (y <= 0)
			negativePath.lineTo((float) (xIPotential + potentialWidth), (float) 0);

		positivePath.transform(new AffineTransform(1, 0, 0, Math.abs(5e-1 / Math.max(Math.abs(yminHere), Math
				.abs(ymaxHere))), 0, 0));
		negativePath.transform(new AffineTransform(1, 0, 0, Math.abs(5e-1 / Math.max(Math.abs(yminHere), Math
				.abs(ymaxHere))), 0, 0));

		if (ymaxHere != 0)
			ymaxHere *= Math.abs(5e-1 / ymaxHere);
		if (yminHere != 0)
			yminHere *= Math.abs(5e-1 / yminHere);
		ymax = Math.max(ymaxHere, ymax);
		ymin = Math.min(yminHere, ymin);
	}

	public void draw(DrawingPanel panel, Graphics g) {
		positiveShape = positivePath.createTransformedShape(panel.getPixelTransform());
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativeShape = negativePath.createTransformedShape(panel.getPixelTransform());
		if (negativePaintColor == null)
			g2.setPaint(panel.getBackground());
		else
			g2.setPaint(negativePaintColor);
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	public Interactive findInteractive(DrawingPanel panel, int _xpix, int _ypix) {
		if (enabled) {
			GeneralPath tmpPath = (GeneralPath) positivePath.clone();
			tmpPath.closePath();
			GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
			ntmpPath.closePath();
			if (positiveShape != null
					&& negativeShape != null
					&& (tmpPath.createTransformedShape(panel.getPixelTransform()).contains(_xpix, _ypix) || ntmpPath
							.createTransformedShape(panel.getPixelTransform()).contains(_xpix, _ypix)))
				return this;
		}
		return null;
	}

	public void setColor(Color _fillColor) {
		fillColor = _fillColor;
	}

	public Color getColor() {
		return fillColor;
	}

	public void setHighLightColor(Color _highlight) {
		highLightColor = _highlight;
	}

	public void setWidth(double vWidth) {
		potentialWidth = vWidth;
		buildPath();
	}

	public double getWidth() {
		return potentialWidth;
	}

	public double getX() {
		return xIPotential;
	}

	public double getXMax() {
		return xmax;
	}

	public double getXMin() {
		return xmin;
	}

	public double getY() {
		return 0;
	}

	public double getYMax() {
		return ymax;
	}

	public double getYMin() {
		return ymin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isMeasured() {
		return false;
	}

	public void setEnabled(boolean enabled) {
		enabled = enabled;
	}

	// move para o ponto x
	public void setX(double x) {
		if (enabled) {
			positivePath.transform(AffineTransform.getTranslateInstance(x - xIPotential, 0));
			negativePath.transform(AffineTransform.getTranslateInstance(x - xIPotential, 0));
			xIPotential = x;
		}
	}

	// translacao (x,0) -> Usado para o arrasto do rato
	public void setXY(double x, double y) {
		if (enabled) {
			xIPotential += x;
			positivePath.transform(AffineTransform.getTranslateInstance(x, 0));
			negativePath.transform(AffineTransform.getTranslateInstance(x, 0));
		}
	}

	public void setY(double y) {
	}

	public void mouseOver(DrawingPanel panel) {
		positivePaintColor = highLightColor;
		Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativePaintColor = highLightColor;
		g2.setPaint(negativePaintColor);
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	public void mouseOut(DrawingPanel panel) {
		positivePaintColor = fillColor;
		Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(positivePaintColor);
		g2.draw(positiveShape);
		g2.fill(positiveShape);

		negativePaintColor = panel.getBackground();
		g2.setPaint(negativePaintColor);
		g2.draw(negativeShape);
		g2.fill(negativeShape);
	}

	public boolean intersect(Rectangle2D rect) {
		GeneralPath tmpPath = (GeneralPath) positivePath.clone();
		tmpPath.closePath();
		GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
		ntmpPath.closePath();

		if (positiveShape != null
				&& negativeShape != null
				&& (tmpPath.createTransformedShape(null).getBounds2D().intersects(rect) || ntmpPath
						.createTransformedShape(null).getBounds2D().intersects(rect)))
			return true;
		return false;
	}

	public Rectangle2D getBounds(double x) {
		GeneralPath tmpPath = (GeneralPath) positivePath.clone();
		tmpPath.closePath();
		GeneralPath ntmpPath = (GeneralPath) negativePath.clone();
		ntmpPath.closePath();

		tmpPath.transform(AffineTransform.getTranslateInstance(x, 0));
		ntmpPath.transform(AffineTransform.getTranslateInstance(x, 0));

		return tmpPath.createTransformedShape(null).getBounds2D().createUnion(
				ntmpPath.createTransformedShape(null).getBounds2D());
	}

	public String getFunction() {
		return vFunction.toString();
	}

	public void setFunction(String func) throws ParserException {
		vFunction = new ParsedFunction(func);
		buildPath();
	}

	public String toString() {
		return xIPotential + ":" + potentialWidth + ":" + getFunction() + ":" + isMedio;
	}

	public boolean isMedio() {
		return isMedio;
	}

	public void setMedio(boolean _isMedio) {
		isMedio = _isMedio;
	}

	public Color getHighLightColor() {
		return highLightColor;
	}

	public Color getPaintColor() {
		return positivePaintColor;
	}

}
