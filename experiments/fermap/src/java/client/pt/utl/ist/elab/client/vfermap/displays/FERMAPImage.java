/*
 * FERMAPImage.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.client.vfermap.displays;

/*
 * @author  Antonio Jose Rodrigues Figueiredo
 */

import java.awt.image.BufferedImage;

import pt.utl.ist.elab.client.vstdmap.displays.MAPanel;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class FERMAPImage extends MAPanel implements ExpDataDisplay, ExpDataModelListener {

	private double uMax = 0;

	/** Creates a new instance of Animation */
	public FERMAPImage() {
		super("Psi", "U", java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString("rec.exp.title.fermap"), 0,
				2 * Math.PI, 0, 0);

		javax.swing.JMenuItem item = new javax.swing.JMenuItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.displays.mapanel.menu.title.5"));
		item.setToolTipText(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.displays.mapanel.menu.tip.5"));
		item.addActionListener(this);
		popupmenu.add(item);
		item = new javax.swing.JMenuItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.displays.mapanel.menu.title.6"));
		item.setToolTipText(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
				"rec.exp.displays.mapanel.menu.tip.6"));
		item.addActionListener(this);
		popupmenu.add(item);
		popupmenu.setEnabled(false);
		// this.setPreferredSize(new java.awt.Dimension(500,500));
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.3"))) {
			xminPreferred = 0;
			xmaxPreferred = xMaxBound;
			yminPreferred = 0;
			ymaxPreferred = yMaxBound;
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.1"))) {
			snapshot(null, java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
					"rec.exp.displays.mapanel.menu.title.1"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.2"))) {
			snapshot(mapImg, java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
					"rec.exp.displays.mapanel.menu.title.2"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.4"))) {
			updateImage();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.5"))) {
			double tmpUMax = (double) pt.utl.ist.elab.client.virtual.guipack.PopupMenu.dialog(java.util.ResourceBundle
					.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
							"rec.exp.displays.mapanel.menu.title.7"), java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP").getString(
					"rec.exp.displays.mapanel.menu.tip.7"), "Ok", (int) Math.round(getPreferredYMax() * 10), new int[] {
					200, 1001, 1, 20 }) / 10d;
			if (tmpUMax != getPreferredYMax()) {
				setPreferredMinMaxY(0, tmpUMax);
				updateImage();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/fermap/resources/ReCExpFERMAP")
						.getString("rec.exp.displays.mapanel.menu.title.6"))) {
			if (uMax == 0)
				setUMax();
			if (uMax != getPreferredYMax()) {
				setPreferredMinMaxY(0, uMax);
				updateImage();
			}
		}
	}

	private void setUMax() {
		for (int i = 1; i < mData.length; i += 2)
			uMax = Math.max(mData[i], uMax);
	}

	// TESTE - FORA DO CONTEXTO DO e-Lab
	public void start() {
		int nPsi = 5, nU = 25;

		this.nn = nPsi * nU;
		this.iter = 20000;

		int w = 800;
		int h = 800;

		// config(false, (short)1, w, h, nTheta*nI, iter);
		mapImg = new BufferedImage(800, 800, BufferedImage.TYPE_BYTE_INDEXED);// getWidth()-leftGutter-rightGutter,
		// getHeight()-topGutter-bottomGutter,BufferedImage.TYPE_INT_RGB);

		ymaxPreferred = ymaxImg = 1000;

		// Map Histogram
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 1,
		// 0, 1, 10000, .5f, 800, 800, (short)1, 10, false).startM(this);
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null,
		// 250, 0, 1, 10000, .5f, 800, 800, (short)1, 10, false).startM(this);

		// Map Static
		/*
		 * staticImg = true;
		 * //pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer fer = new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 250,
		 * 0, nPsi, 2.1f, 1, nU, .21f, iter, .2f, w, h, pixSize, 0, staticImg);
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer fer = new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 10, 0,
		 * nPsi, 2.1f, 1, nU, .21f, iter, .2f, w, h, pixSize, 20, staticImg);
		 * popupmenu.getComponent(2).setEnabled(false);
		 * popupmenu.getComponent(3).setEnabled(false);
		 * popupmenu.getComponent(4).setEnabled(false);
		 * popupmenu.getComponent(5).setEnabled(false);
		 * makeImage(fer.getMapaPixs()); setPreferredMinMaxY(0, fer.getUMax());
		 * statusStr = ""; repaint();
		 */

		// Map Anima
		g = mapImg.getGraphics();
		this.pcor = Math.PI;

		float psi = (float) Math.PI;
		float xDot = 200;

		float c1 = (float) Math.abs((psi + xDot) % 1);
		float c2 = (float) Math.abs((xDot + pcor * Math.sin(psi)) % 1);
		float c3 = (c1 + c2) % 1;

		g.setColor(new java.awt.Color(c1, c2, c3));

		this.nn = 1;
		this.iter = 10000;

		mData = new float[10000 * 2];
		// new pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 2,
		// 100, 0, 10.1, 9, 100, 1, 1000).startM(this);
		new pt.utl.ist.elab.driver.vfermap.FERMAPDataProducer(null, 2, xDot, psi, 0.1f, 9, 20, 1, 1000).startM(this);

		statusStr = "";
		repaint();

		// Map Non-Static
		/*
		 * mData = new float[nn*iter*2]; staticImg = false; this.pcor = Math.PI;
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer fer = new
		 * pt.utl.ist.elab.virtual.driver.fermap.FERMAPDataProducer(null, 10, 0,
		 * nPsi, 2.1f, 1, nU, .21f, iter, .2f, w, h, pixSize, 20, staticImg);
		 * mData = ByteUtil.byteArrayToFloatArray(fer.getMapaData());
		 * setPreferredMinMaxY(0, fer.getUMax()); statusStr = ""; updateImage();
		 */

	}

	public static void main(String args[]) {
		javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		FERMAPImage ferim = new FERMAPImage();
		test.getContentPane().add(ferim);
		test.pack();
		test.show();

		ferim.start();
	}

	// Chegaram novas amostras!
	public void newSamples(NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, model.getChannelIndex("imgPixs")) != null) { // static
				makeImage(model.getValueAt(i, model.getChannelIndex("imgPixs")).getValue().getByteArrayValue()
						.getData());
				repaint();
			} else if (model.getValueAt(i, model.getChannelIndex("mData")) != null) { // non-static
				mData = ByteUtil.byteArrayToFloatArray(model.getValueAt(i, model.getChannelIndex("mData")).getValue()
						.getByteArrayValue().getData());
				updateImage();
			} else if (model.getValueAt(i, 0) != null && model.getValueAt(i, 2) != null) { // (psi,velocidade)
				double tempPsi = Math.abs((model.getValueAt(i, 0).getValue().getFloatValue() + 3 * Math.PI / 2d)
						% (2 * Math.PI));
				double tempXDot = Math.abs(model.getValueAt(i, 2).getValue().getFloatValue());

				// ymaxPreferred = Math.max(ymaxPreferred,tempXDot);

				// g.drawOval((int)
				// Math.round(tempPsi*mapImg.getWidth()/(2*Math.PI)),
				// mapImg.getHeight()-(int)
				// Math.round(tempXDot*mapImg.getHeight()/(ymaxPreferred-yminPreferred)),
				// pixSize,pixSize);

				// if (!staticImg){
				mData[dataCounter++] = (float) tempPsi;
				mData[dataCounter++] = (float) tempXDot;
				// }
				// repaint();
				uMax = Math.max(uMax, tempXDot);
				setPreferredMinMaxY(0, uMax);
				updateImage();
			} else if (model.getValueAt(i, 0) != null && model.getValueAt(i, 2) == null) // uMax
				this.ymaxPreferred = ymaxImg = model.getValueAt(i, 0).getValue().getFloatValue();
		}
	}

	// Queremos fazer alguma coisa quandos os dados acabarem?
	public void dataModelEnded() {
		statusStr = "";
		repaint();
	}

	// Queremos fazer alguma coisa quandos acontecer um erro?
	public void dataModelError() {
	}

	// Queremos fazer alguma coisa quando for dado o start e existirem dados?
	public void dataModelStarted() {
		statusStr = "";
		repaint();
	}

	// Queremos fazer alguma coisa quando for dado o start e ainda não existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, já existe o header da
	// experiência!
	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		byte simulType = Byte.parseByte(header.getSelectedHardwareParameterValue("simulType"));
		this.pcor = Float.parseFloat(header.getSelectedHardwareParameterValue("pcor"));
		float uMax = Float.parseFloat(header.getSelectedHardwareParameterValue("uMax"));
		this.staticImg = header.getSelectedHardwareParameterValue("staticImg").trim().equals("1") ? true : false;

		if (uMax != -1)
			this.ymaxPreferred = ymaxImg = uMax;

		if (simulType == 2) { // anima
			this.staticImg = false;
			this.setYLabel("dX/dt");
			mapImg = new BufferedImage(getWidth() - leftGutter - rightGutter, getHeight() - topGutter - bottomGutter,
					BufferedImage.TYPE_INT_RGB);
			g = mapImg.getGraphics();

			float psi = Float.parseFloat(header.getSelectedHardwareParameterValue("psi"));
			float xDot = Float.parseFloat(header.getSelectedHardwareParameterValue("xDot"));

			float c1 = (float) Math.abs((psi + xDot) % 1);
			float c2 = (float) Math.abs((xDot + pcor * Math.sin(psi)) % 1);
			float c3 = (c1 + c2) % 1;

			g.setColor(new java.awt.Color(c1, c2, c3));
		} else
			mapImg = new BufferedImage(Integer.parseInt(header.getSelectedHardwareParameterValue("w")), Integer
					.parseInt(header.getSelectedHardwareParameterValue("h")), BufferedImage.TYPE_INT_RGB);

		this.pixSize = Byte.parseByte(header.getSelectedHardwareParameterValue("pixSize"));

		if (staticImg) {
			popupmenu.getComponent(2).setEnabled(false);
			popupmenu.getComponent(3).setEnabled(false);
			popupmenu.getComponent(4).setEnabled(false);
			popupmenu.getComponent(5).setEnabled(false);
		} else {
			if (simulType == 3) {
				this.nn = 1;
				this.iter = header.getTotalSamples();
			} else {
				this.nn = Integer.parseInt(header.getSelectedHardwareParameterValue("nPsi"))
						* Integer.parseInt(header.getSelectedHardwareParameterValue("nUMapa"));
				this.iter = Integer.parseInt(header.getSelectedHardwareParameterValue("iter"));
			}
			mData = new float[nn * iter * 2];
		}
		popupmenu.setEnabled(true);
		setZoom(!staticImg);
		repaint();
	}

	// Queremos fazer alguma coisa quando for dado parado?
	public void dataModelStoped() {
		statusStr = "";
		repaint();
	}

	// Queremos fazer alguma coisa em estado de espera?
	public void dataModelWaiting() {
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	// O icon associado a este painel!
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}

}