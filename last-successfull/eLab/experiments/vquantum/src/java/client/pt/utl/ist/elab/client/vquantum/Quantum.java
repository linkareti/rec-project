/*
 * Quantum.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.client.vquantum;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.opensourcephysics.display.Dimensioned;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.axes.XAxis;
import org.opensourcephysics.display.axes.XYAxis;

import pt.utl.ist.elab.driver.virtual.utils.Complex;

/**
 * 
 * @author nomead
 */
public class Quantum extends InteractivePanel implements InteractiveMouseHandler, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130036516414252361L;
	protected String actionStr = java.util.ResourceBundle.getBundle(
			"pt/utl/ist/elab/client/vquantum/resources/messages").getString("rec.exp.displays.animation.actionStr");
	protected pt.utl.ist.elab.client.virtual.guipack.PopupMenu gaussMenu;
	private final XAxis eixo;
	private KSPanel ks;
	private ComplexGaussian gaussian;

	public Quantum() {
		super();
		enableZoom = true;
		removeMouseListener(optionController);
		removeMouseMotionListener(optionController);

		buildPopuMenu();

		gaussMenu = new pt.utl.ist.elab.client.virtual.guipack.PopupMenu(this);
		gaussMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.1"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.1"));
		gaussMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.2"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.2"));
		gaussMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.3"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.3"));
		gaussMenu.addCheckBoxItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.4"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.4"));
		gaussMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.5"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.5"));
		gaussMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.6"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.tip.6"));

		config();

		eixo = new XAxis(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.quantum.space"));
		eixo.setLocationType(XYAxis.DRAW_AT_LOCATION);
		eixo.setY(-.6);
		addDrawable(eixo);
		repaint();
	}

	protected void config() {
		setPreferredMinMax(-35, 15, -1, 1);
		addDrawable(new Potential(0, 4, "-cos(x)*5", true, false));
		addDrawable(new Potential(5, 5, "sin(x)*5", true, false));
		addDrawable(new Potential(10.5, 2, "exp(-x*25)+3", true, false));
		addDrawable(new Potential(-3, 2, "210", true, false));
		addDrawable(new Potential(-5, 1.5, "x^3+4*x^2+5+cos(3*x)", true, false));
		addDrawable(gaussian = new ComplexGaussian(200, -10, 200, 11, 1, ComplexGaussian.DISPLAY_PROBABILITY, true));
	}

	protected void buildPopupmenu() {
	}

	protected void buildPopuMenu() {
		popupmenu.setEnabled(true);
		JMenuItem item = new JMenuItem("Zoom In");
		item.addActionListener(this);
		popupmenu.add(item);
		item = new JMenuItem("Zoom Out");
		item.addActionListener(this);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.displays.save"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.displays.save.tip"));
		item.addActionListener(this);
		popupmenu.add(item);
		item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.displays.print"));
		item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages")
				.getString("rec.exp.displays.print.tip"));
		item.addActionListener(this);
		popupmenu.add(item);
	}

	protected void paintEverything(final Graphics g) {
		if (dimensionSetter != null) {
			final Dimension interiorDimension = dimensionSetter.getInterior(this);
			if (interiorDimension != null) {
				squareAspect = false;
				leftGutter = rightGutter = Math.max(0, getWidth() - interiorDimension.width) / 2;
				topGutter = bottomGutter = Math.max(0, getHeight() - interiorDimension.height) / 2;
			}
		}
		final java.util.ArrayList<Drawable> tempList = getDrawables();
		scale(tempList);
		setPixelScale();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), yToPix(0));
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, yToPix(0), getWidth(), getHeight() - yToPix(0));
		g.setColor(Color.black);
		eixo.setLocationType(eixo.DRAW_AT_LOCATION);
		eixo.setY(-.6);
		paintDrawableList(g, tempList);

		if (actionStr != null) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(2, 5, g.getFontMetrics().stringWidth(actionStr) + 3, g.getFontMetrics().getHeight());

			g.setColor(java.awt.Color.BLACK);
			g.drawString(actionStr, 5, 16);
		}
	}

	public void handleMouseAction(final InteractivePanel panel, final MouseEvent evt) {
		if (actionStr != null && SwingUtilities.isRightMouseButton(evt)) {
			actionStr = null;
			repaint();
		}
		switch (panel.getMouseAction()) {
		case InteractivePanel.MOUSE_PRESSED:
			if ((enableZoom && evt.isShiftDown()) || zoomMode) {
				zoomBox.startZoom(evt.getX(), evt.getY());
			}
			final Interactive inter = getInteractive();
			if (inter == null) {
				if (SwingUtilities.isRightMouseButton(evt) && popupmenu != null && popupmenu.isEnabled()) {
					popupmenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
				if (enableZoom && evt.isControlDown()) {
					setPreferredMinMax(panel.getMouseX() - (xmaxPreferred - xminPreferred) / 2, panel.getMouseX()
							+ (xmaxPreferred - xminPreferred) / 2, panel.getMouseY() - (ymaxPreferred - yminPreferred)
							/ 2, panel.getMouseY() + (ymaxPreferred - yminPreferred) / 2);
					repaint();
				}
				return;
			}
			if (SwingUtilities.isRightMouseButton(evt)) {
				if (inter instanceof ComplexGaussian) {
					gaussMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
			break;
		case InteractivePanel.MOUSE_MOVED:
			if (gaussian != null) {
				if (getInteractive() instanceof ComplexGaussian) {
					if (gaussian.getPaintColor() == java.awt.Color.ORANGE) {
						gaussian.mouseOver(this);
					}
				} else if (gaussian.getPaintColor() == java.awt.Color.RED) {
					gaussian.mouseOut(this);
				}
			}
			break;
		case InteractivePanel.MOUSE_DRAGGED:
			if (zoomBox.isVisible()) {
				zoomBox.drag(evt.getX(), evt.getY());
			}
			break;
		case InteractivePanel.MOUSE_RELEASED:
			if (zoomBox.isVisible()) {
				zoomBox.endZoom(evt.getX(), evt.getY());
			}
			break;
		}
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.5"))) {
			setPreferredMinMax(gaussian.getX() - 25, gaussian.getX() + 25, -1, 1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.6"))) {
			setPreferredMinMax(gaussian.getX() - gaussian.getDX0() / 2, gaussian.getX() + gaussian.getDX0() / 2, -1, 1);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.1"))) {
			gaussian.setDisplay(ComplexGaussian.DISPLAY_PROBABILITY);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.2"))) {
			gaussian.setDisplay(ComplexGaussian.DISPLAY_REAL);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.3"))) {
			gaussian.setDisplay(ComplexGaussian.DISPLAY_IMAGINARY);
			repaint();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.customizer.gaussMenu.title.4"))) {
			if (((JCheckBoxMenuItem) gaussMenu.getComponent(3)).isSelected()) {
				gaussian.setShowingKS(true);
				gaussian.buildPsiKS();
				final JFrame tempFrame = new JFrame("KSpace");
				ks = new KSPanel();
				tempFrame.getContentPane().add(ks);
				tempFrame.setSize(new java.awt.Dimension(300, 200));
				tempFrame.addWindowListener(ks);
				tempFrame.show();
			} else {
				removeKS();
			}
		} else if (e.getActionCommand().equalsIgnoreCase("Zoom In")) {
			zoomIn();
		} else if (e.getActionCommand().equalsIgnoreCase("Zoom Out")) {
			zoomOut();
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.displays.save"))) {
			try {
				doSaveAs(render());
			} catch (final IOException io) {
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
						"rec.exp.displays.print"))) {
			createChartPrintJob(this);
		}
	}

	public double getX0() {
		return gaussian.getX();
	}

	public void setDX0(final double dX0) {
		gaussian.setDX0(dX0);
		repaint();
	}

	public void setDeltaX(final double deltaX) {
		gaussian.setDeltaX(deltaX);
		repaint();
	}

	public double getDeltaX() {
		return gaussian.getDeltaX();
	}

	public void setLog2N(final int n) {
		gaussian.setLog2N(n);
		repaint();
	}

	public void setEnergy(final double E) {
		gaussian.setEnergy(E);
		repaint();
	}

	public void clearPotentials() {
		removeObjectsOfClass(Potential.class);
	}

	public void configGaussian(final double _dX0, final double _x0, final double _energy, final int log2N,
			final double _deltaX, final int _display, final boolean _enabled) {
		gaussian.configGaussian(_dX0, _x0, _energy, log2N, _deltaX, _display, _enabled);
		repaint();
	}

	public void setPsi(final Complex[] psi) {
		gaussian.setPsi(psi);
	}

	public synchronized void addDrawable(final Drawable drawable) {
		if ((drawable != null) && !drawableList.contains(drawable)) {
			if (drawable instanceof ComplexGaussian
					|| (drawable instanceof Potential && ((Potential) drawable).getHighLightColor() == Color.BLUE)) {
				drawableList.add(drawableList.size(), drawable);
			} else {
				drawableList.add(0, drawable);
			}
			validImage = false;
		}
		if (drawable instanceof Dimensioned) {
			dimensionSetter = ((Dimensioned) drawable);
		}
		if (drawable instanceof Interactive) {
			containsInteractive = true;
		}
	}

	// CONFIGURAR OS POTENCIAIS
	public void configPotentials(final String strPotentials, final boolean enabled) {
		final String[] pots = strPotentials.split("#");
		for (final String pot : pots) {
			final String[] potsSet = pot.split(":");
			final double x = Double.parseDouble(potsSet[0]);
			final double width = Double.parseDouble(potsSet[1]);
			final String vF = potsSet[2].substring(potsSet[2].indexOf("=") + 1, potsSet[2].length());
			final boolean isMedio = Boolean.getBoolean(potsSet[3]);

			addDrawable(new Potential(x, width, vF, enabled, isMedio));
		}
		repaint();
	}

	public int getNumberOfPotentials() {
		int n = 0;
		final java.util.Iterator<Drawable> it = drawableList.iterator();
		while (it.hasNext()) {
			final java.lang.Object obj = it.next();
			if (obj instanceof Potential) {
				n++;
			}
		}
		return n;
	}

	public String getPotentials() {
		@SuppressWarnings("unchecked")
		final java.util.ArrayList<Drawable> tmpArray = (java.util.ArrayList<Drawable>) drawableList.clone();
		tmpArray.remove(gaussian);
		tmpArray.remove(eixo);
		final java.util.Iterator<Drawable> it = tmpArray.iterator();

		String potentials = "";

		while (it.hasNext()) {
			final java.lang.Object obj = it.next();
			if (obj instanceof Potential) {
				potentials += ((Potential) obj).getX() + ":" + ((Potential) obj).getWidth() + ":"
						+ ((Potential) obj).getFunction() + ":" + ((Potential) obj).isMedio() + "#";
			}
		}
		return potentials.substring(0, potentials.length() - 1);
	}

	public void doSaveAs(final java.awt.image.BufferedImage img) throws IOException {

		final JFileChooser fileChooser = new JFileChooser();
		final org.jfree.ui.ExtensionFileFilter filter = new org.jfree.ui.ExtensionFileFilter(java.util.ResourceBundle
				.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString("PNG_Image_Files"), ".png");
		fileChooser.addChoosableFileFilter(filter);

		final int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getPath();

			if (!filename.endsWith(".png")) {
				filename = filename + ".png";
			}

			final OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));

			EncoderUtil.writeBufferedImage(img, ImageFormat.PNG, out);
			out.close();
		}

	}

	public void createChartPrintJob(final DrawingPanel printable) {
		final PrinterJob job = PrinterJob.getPrinterJob();
		final PageFormat pf = job.defaultPage();
		final PageFormat pf2 = job.pageDialog(pf);
		if (pf2 != pf) {
			job.setPrintable(printable, pf2);
			if (job.printDialog()) {
				try {
					job.print();
				} catch (final PrinterException e) {
					JOptionPane.showMessageDialog(printable, e);
				}
			}
		}
	}

	public synchronized void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (ks != null) {
			ks.repaint();
		}
	}

	private void removeKS() {
		((JCheckBoxMenuItem) gaussMenu.getComponent(3)).setSelected(false);
		gaussian.setShowingKS(false);
		((JFrame) ks.getTopLevelAncestor()).dispose();
		ks = null;
	}

	/*
	 * public String toString(){ return
	 * "Energia : "+gaussian.getEnergy()+" eV\nN : "
	 * +gaussian.getN()+"\nk0 : "+gaussian
	 * .getK0()+" Angstroms-1\n--------------------------------\nPotenciais : "
	 * +nPotentials
	 * +"\nV0 : "+vFunction.toString()+" eV\nComprimento : "+((xFPotential
	 * -xIPotential)*dx)+" Angstroms\nEspa\u00e7amento : "+(potentialGap*dx)+
	 * " Angstroms\n--------------------------------\ndx : "
	 * +dx+" Angstroms\nX : "
	 * +(dx*N)+" Angstroms\ndk : "+(2*Math.PI/((N-1)*dx))+" Angstroms\nK : "
	 * +(2*Math.PI/dx)+" Angstroms\ndeltaX : "+deltaX+
	 * " Angstroms\nFrequ\u00eancia de Samplagem : "+(1/dx)+
	 * " Hz\n--------------------------------\n--------------------------------"
	 * ; }
	 */

	private class KSPanel extends DrawingPanel implements ActionListener, WindowListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3053621421483331124L;

		/** Creates a new instance of KSPanel */
		public KSPanel() {
			enableZoom = true;
			autoscaleX = false;
			autoscaleY = false;
			buildPopuMenu();
		}

		protected void buildPopupmenu() {
		}

		protected void buildPopuMenu() {
			popupmenu.setEnabled(true);
			JMenuItem item = new JMenuItem("Zoom In");
			item.addActionListener(this);
			popupmenu.add(item);
			item = new JMenuItem("Zoom Out");
			item.addActionListener(this);
			popupmenu.add(item);
			item = new JMenuItem(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vquantum/resources/messages").getString("rec.exp.displays.save"));
			item.setToolTipText(java.util.ResourceBundle
					.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.displays.save.tip"));
			item.addActionListener(this);
			popupmenu.add(item);
			item = new JMenuItem(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vquantum/resources/messages").getString("rec.exp.displays.print"));
			item.setToolTipText(java.util.ResourceBundle
					.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.displays.print.tip"));
			item.addActionListener(this);
			popupmenu.add(item);
			item = new JMenuItem(java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vquantum/resources/messages").getString(
					"rec.exp.customizer.gaussMenu.title.6"));
			item.setToolTipText(java.util.ResourceBundle
					.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.customizer.gaussMenu.tip.6"));
			item.addActionListener(this);
			popupmenu.add(item);
		}

		public void actionPerformed(final ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Zoom In")) {
				zoomIn();
			} else if (e.getActionCommand().equalsIgnoreCase("Zoom Out")) {
				zoomOut();
			} else if (e.getActionCommand().equalsIgnoreCase(
					java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.customizer.gaussMenu.title.6"))) {
				xminPreferred = gaussian.getMinMaxXKS()[0];
				xmaxPreferred = gaussian.getMinMaxXKS()[1];
				yminPreferred = gaussian.getMinMaxYKS()[0];
				ymaxPreferred = gaussian.getMinMaxYKS()[1];
				repaint();
			} else if (e.getActionCommand().equalsIgnoreCase(
					java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.displays.save"))) {
				try {
					doSaveAs(render());
				} catch (final IOException io) {
				}
			} else if (e.getActionCommand().equalsIgnoreCase(
					java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vquantum/resources/messages").getString(
							"rec.exp.displays.print"))) {
				createChartPrintJob(this);
			}
		}

		protected void paintEverything(final java.awt.Graphics g) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			gaussian.drawPsiKS(this, g);
		}

		public void windowActivated(final WindowEvent e) {
		}

		public void windowClosed(final WindowEvent e) {
		}

		public void windowClosing(final WindowEvent e) {
			removeKS();
		}

		public void windowDeactivated(final WindowEvent e) {
		}

		public void windowDeiconified(final WindowEvent e) {
		}

		public void windowIconified(final WindowEvent e) {
		}

		public void windowOpened(final WindowEvent e) {
		}

	}

}
