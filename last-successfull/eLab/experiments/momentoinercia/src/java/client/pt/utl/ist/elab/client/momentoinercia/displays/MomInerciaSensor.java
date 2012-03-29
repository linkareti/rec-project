/*
 * SeringeSensor.java
 *
 * Created on 2 de Junho de 2003, 14:18
 */

package pt.utl.ist.elab.client.momentoinercia.displays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MomInerciaSensor extends javax.swing.JPanel implements
		com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6674978256154227101L;
	private final BufferedImage disk1 = new BufferedImage(300, 46, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage disk2 = null;
	private final Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));

	private int imgHeight = 0;
	private int imgWidth = 0;
	private final int y_disk2 = 0;
	private int interval = 50;
	private int d1Pos = 0;
	private int d2Pos = 0;
	private int x_start = 0;

	private final int DISK_A = disk1.getWidth() / 2;
	private final int DISK_B = 15;

	/** Creates new form SeringeSensor */
	public MomInerciaSensor() {
		d1Pos = DISK_A;
		initComponents();
		setPreferredSize(new Dimension(disk1.getWidth() + 2 * 50, 2 * disk1.getHeight() + 2 * 50));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());

		final Graphics2D g2D1 = (Graphics2D) disk1.getGraphics();

		g2D1.setColor(Color.black);

		g2D1.drawOval(0, DISK_B, 2 * DISK_A, 2 * DISK_B);

		final GradientPaint gp1 = new GradientPaint(0, 0, Color.blue.darker(), DISK_A, DISK_B, Color.blue.brighter(),
				true);
		g2D1.setPaint(gp1);

		g2D1.fillOval(0, 0, 2 * DISK_A, 2 * DISK_B);

		g2D1.setStroke(new BasicStroke(DISK_B));

		g2D1.drawOval(0, DISK_B / 2, 2 * DISK_A, 2 * DISK_B);

		g2D1.setStroke(new BasicStroke(1.5f));

		g2D1.setColor(Color.black);

		g2D1.drawOval(0, 0, 2 * DISK_A, 2 * DISK_B);

		disk2 = disk1;
	}

	private boolean rotate = false;
	private boolean started = false;
	private int speed = 300;

	private class DiskThread extends Thread {
		@Override
		public void run() {
			while (rotate) {
				if (started) {
					d1Pos++;
				}

				if (interval == 0) {
					d2Pos++;
				}
				if (d2Pos >= 4 * DISK_A) {
					d2Pos = 0;
				}
				if (d1Pos >= 4 * DISK_A) {
					d1Pos = 0;
				}
				if (speed > 0) {
					repaint();
				}

				try {
					Thread.sleep(speed);
				} catch (final InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			try {
				join(200);
			} catch (final InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents

		setLayout(new java.awt.BorderLayout());

		setBackground(new java.awt.Color(204, 204, 255));
		setForeground(new java.awt.Color(51, 51, 51));
	}// GEN-END:initComponents

	/**
	 * Invoked by Swing to draw components. Applications should not invoke
	 * <code>paint</code> directly, but should instead use the
	 * <code>repaint</code> method to schedule the component for redrawing.
	 * <p>
	 * This method actually delegates the work of painting to three protected
	 * methods: <code>paintComponent</code>, <code>paintBorder</code>, and
	 * <code>paintChildren</code>. They're called in the order listed to ensure
	 * that children appear on top of component itself. Generally speaking, the
	 * component and its children should not paint in the insets area allocated
	 * to the border. Subclasses can just override this method, as always. A
	 * subclass that just wants to specialize the UI (look and feel) delegate's
	 * <code>paint</code> method should just override
	 * <code>paintComponent</code>.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 * @see #paintComponent
	 * @see #paintBorder
	 * @see #paintChildren
	 * @see #getComponentGraphics
	 * @see #repaint
	 */
	@Override
	public void paint(final Graphics g) {
		super.paint(g);

		imgWidth = (int) getBounds().getWidth();
		imgHeight = (int) getBounds().getHeight();

		x_start = imgWidth / 2 - disk1.getWidth() / 2;
		final int y_start = imgHeight / 2 + disk1.getHeight() / 2;

		final int y_start2 = y_start - interval - disk1.getHeight() / 2 + 6;

		final Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(disk1, x_start, y_start, disk1.getWidth(), disk1.getHeight(), null);

		g2D.setColor(Color.yellow);

		int d1PosE = 0;

		int cY = 0;

		if (d1Pos <= 2 * DISK_A) {
			d1PosE = d1Pos - DISK_A;
			cY = DISK_B + (int) (Math.sqrt(Math.pow(DISK_A * DISK_B, 2) - Math.pow(d1PosE * DISK_B, 2)) / DISK_A);
			g2D.drawLine(x_start + d1Pos, y_start + cY, x_start + d1Pos, y_start + cY + DISK_B);
			g2D.drawLine(x_start + DISK_A, y_start + DISK_B, x_start + d1Pos, y_start + cY);
		} else {
			d1PosE = 3 * DISK_A - d1Pos;
			cY = (int) (Math.sqrt(Math.pow(DISK_A * DISK_B, 2) - Math.pow(d1PosE * DISK_B, 2)) / DISK_A);
			g2D.drawLine(x_start + DISK_A, y_start + DISK_B, x_start + 4 * DISK_A - d1Pos, y_start + DISK_B - cY);
		}

		g2D.drawImage(disk2, x_start, y_start2, disk2.getWidth(), disk2.getHeight(), null);

		int d2PosE = 0;

		if (d2Pos <= 2 * DISK_A) {
			d2PosE = d2Pos - DISK_A;
			cY = DISK_B + (int) (Math.sqrt(Math.pow(DISK_A * DISK_B, 2) - Math.pow(d2PosE * DISK_B, 2)) / DISK_A);
			g2D.drawLine(x_start + d2Pos, y_start2 + cY, x_start + d2Pos, y_start2 + cY + DISK_B);
			g2D.drawLine(x_start + DISK_A, y_start2 + DISK_B, x_start + d2Pos, y_start2 + cY);
		} else {
			d2PosE = 3 * DISK_A - d2Pos;
			cY = (int) (Math.sqrt(Math.pow(DISK_A * DISK_B, 2) - Math.pow(d2PosE * DISK_B, 2)) / DISK_A);
			g2D.drawLine(x_start + DISK_A, y_start2 + DISK_B, x_start + 4 * DISK_A - d2Pos, y_start2 + DISK_B - cY);
		}
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return icon;
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

	public void dataModelRunning() {
	}

	@Override
	public void dataModelStoped() {
		rotate = false;
	}

	private float drop = 120;

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		if (header == null) {
			return;
		}

		final int lIter = Integer.parseInt(header.getSelectedHardwareParameterValue("Launch Iteration"));
		final float cFreq = (float) (header.getSelectedFrequency().getFrequency());

		final String iteration = header.getSelectedHardwareParameterValue("Iteration");

		if (iteration.equalsIgnoreCase("Both") || iteration.equalsIgnoreCase("Launch")) {
			drop = lIter * cFreq / 1000f;
		} else {
			drop = 100000;
		}

		System.out.println("Drop = " + drop);

		this.header = header;

		rotate = true;
		new DiskThread().start();
	}

	private HardwareAcquisitionConfig header = null;
	private final boolean acqHeaderInited = false;

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		started = true;
		final int rpmSpeed = model.getValueAt(evt.getSamplesStartIndex(), 0).getValueNumber().intValue();
		if (rpmSpeed < 1) {
			rotate = false;
			started = false;
		} else {
			speed = 3600 / rpmSpeed * 2;
		}

		if (drop < model.getValueAt(evt.getSamplesStartIndex(), 2).getValueNumber().floatValue()) {
			interval = 0;
			repaint();
		}
	}

	@Override
	public String getName() {
		return "Discos";
	}

	@Override
	public JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public JToolBar getToolBar() {
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

	private void s() {
		new DiskThread().start();
	}

	public static void main(final String args[]) {
		final MomInerciaSensor mm = new MomInerciaSensor();
		final javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.getContentPane().add(mm);
		jf.pack();
		jf.show();

		mm.rotate = true;
		mm.s();

		for (int i = 7200; i > -1; i -= 100) {
			mm.speed = 7200 / i * 2;
			// System.out.println(mm.speed);
			try {
				Thread.currentThread();
				Thread.sleep(500);
			} catch (final Exception e) {
			}

			if (i < 3000) {
				mm.interval = 0;
			}
		}
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public void dataModelStarted() {
		headerAvailable(model.getAcquisitionConfig());
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

}
