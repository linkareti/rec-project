/*
 * STDMAPImage.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.client.vstdmap.displays;

/*
 * @author  nomead
 */
import java.awt.image.BufferedImage;

import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class STDMAPImage extends MAPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7450881172848139332L;

	/** Creates a new instance of Animation */
	public STDMAPImage() {
		super("Theta", "I", java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vstdmap/resources/messages")
				.getString("rec.exp.title.stdmap"), 0, 2 * Math.PI, 0, 2 * Math.PI);
		// this.setPreferredSize(new java.awt.Dimension(500,500));
	}

	// TESTE - FORA DO CONTEXTO DO e-Lab
	public void start() {
		final int nTheta = 5, nI = 25;

		nn = nTheta * nI;
		iter = 20000;

		final int w = getWidth() - leftGutter - rightGutter;
		final int h = getHeight() - topGutter - bottomGutter;

		// config(false, (short)1, w, h, nTheta*nI, iter);
		mapImg = new BufferedImage(getWidth() - leftGutter - rightGutter, getHeight() - topGutter - bottomGutter,
				BufferedImage.TYPE_INT_RGB);

		// Map Non-Static
		staticImg = true;
		// FIXME - client should never depend on driver part
		// pt.utl.ist.elab.driver.vstdmap.STDMAPDataProducer std = new
		// pt.utl.ist.elab.driver.vstdmap.STDMAPDataProducer(
		// null, 1.1f, 0, nTheta, 2.1f, 0, nI, .21f, iter, (float) Math.PI, w,
		// h, pixSize, staticImg);
		popupmenu.getComponent(2).setEnabled(false);
		popupmenu.getComponent(3).setEnabled(false);
		// makeImage(std.getMapaPixs());
		statusStr = "";
		repaint();

		// Map Anima
		/*
		 * g = mapImg.getGraphics(); this.pcor = Math.PI;
		 * 
		 * float theta = 0.5f; float thetaDot = 1;
		 * 
		 * float c1 = (float)Math.abs((theta+thetaDot)%1); float c2 =
		 * (float)Math.abs((thetaDot+pcor*Math.sin(theta))%1); float c3 =
		 * (c1+c2)%1;
		 * 
		 * g.setColor(new java.awt.Color(c1,c2,c3));
		 * 
		 * this.nn = 1; this.iter = 10000;
		 * 
		 * mData = new float[10000*2]; new
		 * pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null,(float)
		 * 1, theta, thetaDot, 1, -.5f, 1000,10000).startM(this);
		 * 
		 * statusStr = ""; repaint();
		 */

		// Map Non-Static
		/*
		 * mData = new float[nn*iter*2]; staticImg = false; this.pcor = Math.PI;
		 * pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer std = new
		 * pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null, 1.1f,
		 * 0, nTheta, 2.1f, 0, nI, .21f, iter, .2f, w, h, pixSize, staticImg);
		 * 
		 * mData = ByteUtil.byteArrayToFloatArray(std.getMapaData()); statusStr
		 * = ""; updateImage();
		 */

	}

	public static void main(final String args[]) {
		final javax.swing.JFrame test = new javax.swing.JFrame();
		test.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		final STDMAPImage stdim = new STDMAPImage();
		test.getContentPane().add(stdim);
		test.pack();
		test.setVisible(true);

		stdim.start();
	}

	// Chegaram novas amostras!
	@Override
	public void newSamples(final NewExpDataEvent evt) {
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
			} else if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null) { // Anima
				double tempTheta = model.getValueAt(i, 0).getValue().getFloatValue();
				double tempThetaDot = model.getValueAt(i, 1).getValue().getFloatValue();

				if (tempTheta < 0) {
					tempTheta = Math.abs(tempTheta + 2 * Math.PI);
				}
				if (tempThetaDot < 0) {
					tempThetaDot = Math.abs(tempThetaDot + 2 * Math.PI);
				}

				tempTheta = tempTheta % (2 * Math.PI);
				tempThetaDot = tempThetaDot % (2 * Math.PI);

				g.drawOval((int) Math.round(tempTheta * mapImg.getWidth() / (2 * Math.PI)), mapImg.getHeight()
						- (int) Math.round(tempThetaDot * mapImg.getHeight() / (2 * Math.PI)), pixSize, pixSize);

				if (!staticImg) {
					mData[dataCounter++] = (float) tempTheta;
					mData[dataCounter++] = (float) tempThetaDot;
				}
				repaint();
			}
		}
	}

	// Queremos fazer alguma coisa quandos os dados acabarem?
	@Override
	public void dataModelEnded() {
	}

	// Queremos fazer alguma coisa quandos acontecer um erro?
	@Override
	public void dataModelError() {
	}

	// Queremos fazer alguma coisa quando for dado o start e existirem dados?
	@Override
	public void dataModelStarted() {
		statusStr = "";
		repaint();
	}

	// Queremos fazer alguma coisa quando for dado o start e ainda não existirem
	// dados?
	// Eu garanto que quando chegamos a este estado, já existe o header da
	// experiência!
	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		final byte simulType = Byte.parseByte(header.getSelectedHardwareParameterValue("simulType"));
		pcor = Float.parseFloat(header.getSelectedHardwareParameterValue("pcor"));

		if (simulType == 2) { // anima
			this.setYLabel("dTheta/dt");
			mapImg = new BufferedImage(getWidth() - leftGutter - rightGutter, getHeight() - topGutter - bottomGutter,
					BufferedImage.TYPE_INT_RGB);
			g = mapImg.getGraphics();

			final float theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
			final float thetaDot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetaDot"));

			final float c1 = Math.abs((theta + thetaDot) % 1);
			final float c2 = (float) Math.abs((thetaDot + pcor * Math.sin(theta)) % 1);
			final float c3 = (c1 + c2) % 1;

			g.setColor(new java.awt.Color(c1, c2, c3));
		} else {
			mapImg = new BufferedImage((int) Float.parseFloat(header.getSelectedHardwareParameterValue("w")),
					(int) Float.parseFloat(header.getSelectedHardwareParameterValue("h")), BufferedImage.TYPE_INT_RGB);
		}

		staticImg = header.getSelectedHardwareParameterValue("staticImg").trim().equals("1") ? true : false;
		pixSize = Byte.parseByte(header.getSelectedHardwareParameterValue("pixSize"));

		if (staticImg) {
			popupmenu.getComponent(2).setEnabled(false);
			popupmenu.getComponent(3).setEnabled(false);
		} else {
			if (simulType == 3) {
				nn = 1;
				iter = header.getTotalSamples();
			} else {
				nn = (int) Float.parseFloat(header.getSelectedHardwareParameterValue("nTheta"))
						* (int) Float.parseFloat(header.getSelectedHardwareParameterValue("nIMapa"));
				iter = (int) Float.parseFloat(header.getSelectedHardwareParameterValue("iter"));
			}
			mData = new float[nn * iter * 2];
		}

		setZoom(!staticImg);
	}

	// Queremos fazer alguma coisa quando for dado parado?
	@Override
	public void dataModelStoped() {
		statusStr = "";
		repaint();
	}

	// Queremos fazer alguma coisa em estado de espera?
	@Override
	public void dataModelWaiting() {
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	// O icon associado a este painel!
	@Override
	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
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

}