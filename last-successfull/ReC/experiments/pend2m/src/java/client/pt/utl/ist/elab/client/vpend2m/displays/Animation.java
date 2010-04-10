/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vpend2m.displays;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import org.opensourcephysics.displayejs.InteractiveTrace;

import pt.utl.ist.elab.client.vpend2m.Pend2M;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */
public class Animation extends Pend2M implements ExpDataDisplay, ExpDataModelListener, ComponentListener {

	private java.awt.image.BufferedImage pastImage;
	private boolean isTrailing = false, isConnectedTrail = false;
	private InteractiveTrace[] rasto;
	private String actionStr = java.util.ResourceBundle.getBundle(
			"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
			"rec.exp.displays.animation.actionStr");
	private double a, w;

	/** Creates a new instance of Animation */
	public Animation() {
		super();

		addComponentListener(this);
		rasto = new InteractiveTrace[] { new InteractiveTrace(), new InteractiveTrace() };

		viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.title.7"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.tip.7"), true);
		viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.title.8"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.tip.8"));
		viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.title.9"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.tip.9"));
		viewPopMenu.addItem(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.title.10"), java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
				"rec.exp.customizer.viewMenu.tip.10"));

		viewPopMenu.getComponent(6).setEnabled(false);
	}

	public void moves(double theta, double phi, double thetaDot, double phiDot, double point, double thetaDot2,
			double phiDot2) {
		if (isTrailing && this.getDisplayMode() == this.DISPLAY_PLANAR_YZ) {
			if (pastImage == null) {
				pastImage = new java.awt.image.BufferedImage(getWidth(), getHeight(),
						java.awt.image.BufferedImage.TYPE_INT_RGB);

				java.awt.Graphics ggg = pastImage.getGraphics();
				ggg.setColor(getBackground());
				ggg.fillRect(0, 0, getWidth(), getHeight());
				ggg.setColor(java.awt.Color.BLACK);
			}

			java.awt.Graphics gg = pastImage.getGraphics();

			bola[0].draw(this, gg);
			bola[1].draw(this, gg);
			fio[0].draw(this, gg);
			fio[1].draw(this, gg);
			this.point.draw(this, gg);
		} else if (isConnectedTrail && rasto[0].getSize() == 0) {
			rasto[0].addPoint(0, bola[0].getY() + bola[0].getGroup().getY(), bola[0].getZ());
			rasto[1].addPoint(0, bola[1].getY() + bola[1].getGroup().getY(), bola[1].getZ());
		}

		move(theta, phi, thetaDot, phiDot, point, thetaDot2, phiDot2);

		if (isConnectedTrail) {
			rasto[0].addPoint(0, bola[0].getY() + bola[0].getGroup().getY(), bola[0].getZ());
			rasto[1].addPoint(0, bola[1].getY() + bola[1].getGroup().getY(), bola[1].getZ());
		}
		repaint();
	}

	protected void paintEverything(java.awt.Graphics g) {
		if (dimensionSetter != null) {
			java.awt.Dimension interiorDimension = dimensionSetter.getInterior(this);
			if (interiorDimension != null) {
				squareAspect = false;
				leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
				topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
			}
		}
		java.util.ArrayList tempList = getDrawables();
		scale(tempList);
		setPixelScale();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(java.awt.Color.black);
		if (isTrailing && pastImage != null)
			g.drawImage(pastImage, 0, 0, java.awt.Color.WHITE, this);

		if (actionStr != null) {
			g.setColor(new java.awt.Color(.6f, .12f, .3f));
			g.drawString(actionStr, 5, getHeight() - 10);
		}
		paintDrawableList(g, tempList);
	}

	public void clearTrail() {
		if (pastImage != null) {
			java.awt.Graphics ggg = pastImage.getGraphics();
			ggg.setColor(getBackground());
			ggg.fillRect(0, 0, getWidth(), getHeight());
			ggg.setColor(java.awt.Color.BLACK);
		}
		repaint();
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
	}

	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		float theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
		float phi = Float.parseFloat(header.getSelectedHardwareParameterValue("phi"));
		float thetaDot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetaDot"));
		float phiDot = Float.parseFloat(header.getSelectedHardwareParameterValue("phiDot"));
		float l1 = Float.parseFloat(header.getSelectedHardwareParameterValue("l1"));
		float l2 = Float.parseFloat(header.getSelectedHardwareParameterValue("l2"));
		a = Float.parseFloat(header.getSelectedHardwareParameterValue("a"));
		float fase = Float.parseFloat(header.getSelectedHardwareParameterValue("fase"));
		w = Float.parseFloat(header.getSelectedHardwareParameterValue("w"));

		config(theta, phi, thetaDot, phiDot, l1, l2, a, fase);
	}

	public void dataModelStoped() {
	}

	public void dataModelWaiting() {
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public javax.swing.Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
	}

	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	public void newSamples(NewExpDataEvent evt) {
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal
			if (model.getValueAt(i, 0) != null && model.getValueAt(i, 1) != null && model.getValueAt(i, 2) != null
					&& model.getValueAt(i, 3) != null && model.getValueAt(i, 4) != null
					&& model.getValueAt(i, 5) != null && model.getValueAt(i, 6) != null)
				moves(model.getValueAt(i, 0).getValue().getFloatValue(), model.getValueAt(i, 1).getValue()
						.getFloatValue(), model.getValueAt(i, 2).getValue().getFloatValue(), model.getValueAt(i, 3)
						.getValue().getFloatValue(), a
						* Math.sin(w * model.getValueAt(i, 4).getValue().getFloatValue() + fase), model
						.getValueAt(i, 5).getValue().getFloatValue(), model.getValueAt(i, 6).getValue().getFloatValue());

		}
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		if (pastImage != null) {
			pastImage = new java.awt.image.BufferedImage(getWidth(), getHeight(),
					java.awt.image.BufferedImage.TYPE_INT_RGB);

			java.awt.Graphics ggg = pastImage.getGraphics();
			ggg.setColor(getBackground());
			ggg.fillRect(0, 0, getWidth(), getHeight());
			ggg.setColor(java.awt.Color.BLACK);
		}
		repaint();
	}

	public void componentShown(ComponentEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.1"))) {
			setAlphaAndBeta(0, 0);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.2"))) {
			if (getZoom() != 1) {
				setZoom(1);
				clearTrail();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.3"))) {
			if (getZoom() != .5) {
				setZoom(.5);
				clearTrail();
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.4"))) {
			this.setDisplayMode(this.DISPLAY_PLANAR_YZ);
			repaint();
			viewPopMenu.getComponent(6).setEnabled(true);
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
					"rec.exp.customizer.viewMenu.title.6"));
			((javax.swing.JMenuItem) e.getSource()).setToolTipText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
					"rec.exp.customizer.viewMenu.tip.6"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.6"))) {
			this.setDisplayMode(this.DISPLAY_NO_PERSPECTIVE);
			clearTrail();
			viewPopMenu.getComponent(6).setEnabled(false);
			((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
					"rec.exp.customizer.viewMenu.title.4"));
			((javax.swing.JMenuItem) e.getSource()).setToolTipText(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString(
					"rec.exp.customizer.viewMenu.tip.4"));
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.5")))
			snapshot();
		else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.7"))) {
			vel[0].setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
			vel[1].setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
			acel[0].setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
			acel[1].setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.8"))) {
			isTrailing = ((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.9"))) {
			isConnectedTrail = ((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected();

			if (!((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected()) {
				removeDrawable(rasto[0]);
				removeDrawable(rasto[1]);
			} else {
				addDrawable(rasto[0]);
				addDrawable(rasto[1]);
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M")
						.getString("rec.exp.customizer.viewMenu.title.10"))) {
			rasto[0].clear();
			rasto[1].clear();
			clearTrail();
		}
	}

	protected void zooming() {
		clearTrail();
	}

	protected void movingViewPoint() {
		clearTrail();
	}

	public void mouseClicked(MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
			if (actionStr != null) {
				actionStr = null;
				repaint();
			}
		}
	}

}
