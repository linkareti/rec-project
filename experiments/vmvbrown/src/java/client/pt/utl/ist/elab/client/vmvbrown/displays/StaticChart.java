/*
 * StaticCharts.java
 *
 * Created on 26 de Mar�o de 2005, 0:35
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.ui.ExtensionFileFilter;

/**
 * 
 * @author nomead
 */
public class StaticChart extends javax.swing.JPanel implements Printable, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1904281096188351110L;

	protected pt.utl.ist.elab.client.virtual.guipack.PopupMenu viewPopMenu;

	protected BufferedImage image;
	private final int width, height;

	/** Creates a new instance of Chart1 
	 * @param w 
	 * @param h */
	public StaticChart(final int w, final int h) {
		super();
		width = w;
		height = h;
	}

	private void buildPopupMenu() {
		viewPopMenu = new pt.utl.ist.elab.client.virtual.guipack.PopupMenu(this);
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save.tip"));
		viewPopMenu.addItem(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print"),
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print.tip"));
	}

	public void makeImage(final byte[] b) {
		addMouseListener(this);
		buildPopupMenu();
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
		image.getGraphics().drawImage(
				java.awt.Toolkit.getDefaultToolkit().createImage(
						new java.awt.image.MemoryImageSource(image.getWidth(), image.getHeight(), new BufferedImage(
								image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED).getColorModel(),
								b, 0, image.getWidth())), 0, 0, null);
		repaint();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		} else {
			final String statusStr = java.util.ResourceBundle.getBundle(
					"pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
					"rec.exp.displays.statusStr.recImage");
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

	public void doSaveAs() throws IOException {

		final JFileChooser fileChooser = new JFileChooser();
		final ExtensionFileFilter filter = new ExtensionFileFilter(java.util.ResourceBundle.getBundle(
				"pt/utl/ist/elab/client/vmvbrown/resources/messages").getString("PNG_Image_Files"), ".png");
		fileChooser.addChoosableFileFilter(filter);

		final int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getPath();

			if (!filename.endsWith(".png")) {
				filename = filename + ".png";
			}

			final OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));

			EncoderUtil.writeBufferedImage(image, ImageFormat.PNG, out);
			out.close();
		}

	}

	public void createChartPrintJob() {
		final PrinterJob job = PrinterJob.getPrinterJob();
		final PageFormat pf = job.defaultPage();
		final PageFormat pf2 = job.pageDialog(pf);
		if (pf2 != pf) {
			job.setPrintable(this, pf2);
			if (job.printDialog()) {
				try {
					job.print();
				} catch (final PrinterException e) {
					JOptionPane.showMessageDialog(this, e);
				}
			}
		}
	}

	@Override
	public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
		if (pageIndex >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		if (g == null) {
			return Printable.NO_SUCH_PAGE;
		}
		final Graphics2D g2 = (Graphics2D) g;
		final double scalex = pageFormat.getImageableWidth() / getWidth();
		final double scaley = pageFormat.getImageableHeight() / getHeight();
		final double scale = Math.min(scalex, scaley);
		g2.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
		g2.scale(scale, scale);
		g2.drawImage(image, 0, 0, null);
		return Printable.PAGE_EXISTS;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.save"))) {
			try {
				doSaveAs();
			} catch (final IOException io) {
			}
		} else if (e.getActionCommand().equalsIgnoreCase(
				java.util.ResourceBundle.getBundle("pt/utl/ist/elab/client/vmvbrown/resources/messages").getString(
						"rec.exp.displays.print"))) {
			createChartPrintJob();
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

}
