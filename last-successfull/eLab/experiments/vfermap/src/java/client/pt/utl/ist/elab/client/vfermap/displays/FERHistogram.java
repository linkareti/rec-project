/*
 * FERHistogram.java
 *
 * Created on 1 de Mar�o de 2005, 5:55
 */

package pt.utl.ist.elab.client.vfermap.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import org.opensourcephysics.display.Dimensioned;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.Histogram;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.axes.XYAxis;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */
public class FERHistogram extends PlottingPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6746114326158796680L;
	private String statusStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages")
			.getString("rec.exp.displays.statusStr.recData");
	private Histogram hist;

	/** Creates a new instance of Histogram */
	public FERHistogram() {
		super("", "", "", XYAxis.LINEAR, XYAxis.LINEAR);
		// setPreferredMinMaxX(0,4);
		/*
		 * hist = new Histogram(); hist.setNormalizedToOne(true);
		 * 
		 * //double uMax = 7;
		 * 
		 * hist.setBinWidth(0.05);
		 * 
		 * //Anima+Hist+Map //new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2, 10,
		 * 0, 10.1, 9, 20, 1, 1000).startHist(this);
		 * 
		 * //MAP+Hist static //float [] c = ByteUtil.byteArrayToFloatArray(new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 1, 0,
		 * 1, 10000, .5f, 800, 800, (byte)1, 10, false).getIMapaData()); /*float
		 * [] c = ByteUtil.byteArrayToFloatArray(new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 250,
		 * 0, 1, 10000, .5f, 800, 800, (byte)1, 10, false).getIMapaData());
		 * 
		 * for (int i = 0; i < c.length; i++) hist.append(c[i]);
		 */

		// MAP+Hist non-static
		/*
		 * float [] mData = ByteUtil.byteArrayToFloatArray(new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 250,
		 * 0, 1, 10000, .5f, 800, 800, (byte)1, 10, false).getMapaData());
		 * //float [] mData = ByteUtil.byteArrayToFloatArray(new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 1, 0,
		 * 1, 10000, .5f, 800, 800, (byte)1, 10, false).getMapaData());
		 * 
		 * for (int k = 1; k < mData.length; k+=2) hist.append(mData[k]);
		 */
		/*
		 * statusStr = "";
		 * 
		 * 
		 * addDrawable(hist); repaint();
		 */
		// statusStr =
		// java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString("rec.exp.displays.inactive");

	}

	@Override
	protected void paintEverything(final Graphics g) {
		// find the cliping rectangle within a scroll pane viewport
		viewRect = null;
		Container c = getParent();
		while (c != null) {
			if (c instanceof javax.swing.JViewport) {
				viewRect = ((javax.swing.JViewport) c).getViewRect();
				break;
			}
			c = c.getParent();
		}
		Dimension interiorDimension = null;
		// A single drawable object can set the dimension
		if (dimensionSetter != null) {
			interiorDimension = dimensionSetter.getInterior(this);
		}
		// Give the axes a chance to set the gutter and the dimension.
		if (axes instanceof Dimensioned) {
			interiorDimension = ((Dimensioned) axes).getInterior(this);
		}
		if (interiorDimension != null) {
			squareAspect = false;
			leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
			topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
		}
		// brMessageBox.checkLocation(this);
		java.util.ArrayList<Drawable> tempList;
		synchronized (this) {
			tempList = (java.util.ArrayList<Drawable>) drawableList.clone();
		}
		scale(tempList); // set the x and y scale based on the autoscale values
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight()); // fill the compponent with
		// the background color
		g.setColor(Color.black); // restore the default drawing color
		setPixelScale();
		axes.draw(this, g);
		paintDrawableList(g, tempList);

		if (!statusStr.equalsIgnoreCase("")) {
			g.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 26));

			final int x = (int) Math.round(getWidth() / 2d)
					- (int) Math.round((double) g.getFontMetrics().stringWidth(statusStr) / 2);
			final int y = (int) Math.round(getHeight() / 2d) - g.getFontMetrics().getHeight() + 5;

			g.setColor(new java.awt.Color(.6f, .12f, .3f));
			g.drawString(statusStr, x, (int) Math.round(getHeight() / 2d));

			g.setColor(new java.awt.Color(0, 0, 0, .4f));
			g.fillRect(0, y, getWidth(), g.getFontMetrics().getHeight());

			g.setColor(java.awt.Color.WHITE);
			g.drawLine(0, y + 2, getWidth(), y + 2);
			g.drawLine(0, y + g.getFontMetrics().getHeight() - 2, getWidth(), y + g.getFontMetrics().getHeight() - 2);
		}
	}

	// TESTE
	public void append(final double i) {
		hist.append(Math.abs(i));
		// repaint();
	}

	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		final FERHistogram ferim = new FERHistogram();
		test.getContentPane().add(ferim);
		test.pack();
		test.setVisible(true);
	}

	@Override
	public void dataModelEnded() {
		if (statusStr.equalsIgnoreCase(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vfermap/resources/messages").getString("rec.exp.displays.statusStr.recData"))) {
			statusStr = "";
			repaint();
		}
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
		if (statusStr.equalsIgnoreCase(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vfermap/resources/messages").getString("rec.exp.displays.statusStr.recData"))) {
			statusStr = "";
			repaint();
		}
	}

	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		final byte simulType = Byte.parseByte(header.getSelectedHardwareParameterValue("simulType"));

		// if (simulType == 1)
		// setVisible(false);
		if (simulType != 1) {
			final float uMax = Float.parseFloat(header.getSelectedHardwareParameterValue("uMax"));
			if (uMax != -1) {
				setPreferredMinMaxX(0, uMax);
			}
			setTitle(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages").getString(
					"rec.exp.display.fermap.title.2"));
			hist = new Histogram();
			hist.setBinWidth(.4 / uMax);
			if (simulType == 2) {
				setXLabel("dX/dt");
				hist.setXYColumnNames("dX/dt",
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages")
								.getString("rec.exp.displays.histogram.title.1"));
			} else {
				setXLabel("U");
				hist.setXYColumnNames("U",
						java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages")
								.getString("rec.exp.displays.histogram.title.1"));
			}
			setYLabel(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages")
					.getString("rec.exp.displays.histogram.title.1"));
			addDrawable(hist);
		} else {
			statusStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vfermap/resources/messages")
					.getString("rec.exp.displays.inactive");
		}
		repaint();
	}

	@Override
	public void dataModelStoped() {
		if (statusStr.equalsIgnoreCase(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vfermap/resources/messages").getString("rec.exp.displays.statusStr.recData"))) {
			statusStr = "";
			repaint();
		}
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		if (hist != null) {
			for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
				// sample, canal
				if (model.getValueAt(i, model.getChannelIndex("uMapaBytes")) != null) { // static
					final float[] tmp = ByteUtil.byteArrayToFloatArray(model
							.getValueAt(i, model.getChannelIndex("uMapaBytes")).getValue().getByteArrayValue()
							.getData());

					for (final float element : tmp) {
						hist.append(element);
					}

					repaint();
				} else if (model.getValueAt(i, model.getChannelIndex("mData")) != null) { // non-static
					final float[] mData = ByteUtil.byteArrayToFloatArray(model
							.getValueAt(i, model.getChannelIndex("mData")).getValue().getByteArrayValue().getData());

					for (int k = 1; k < mData.length; k += 2) {
						hist.append(mData[k]);
					}

					repaint();
				} else if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) == null
						&& model.getValueAt(i, 2) != null) {
					hist.append(Math.abs(model.getValueAt(i, 2).getValue().getFloatValue()));
					repaint();
				}
				// uMax
				else if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) == null
						&& model.getValueAt(i, 2) == null) {
					setPreferredMinMaxX(0, model.getValueAt(i, 0).getValue().getFloatValue());
					hist.setBinWidth(.4 / model.getValueAt(i, 0).getValue().getFloatValue());
				}
			}
		}
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}

	}
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.ANIMATION;
    }
}
