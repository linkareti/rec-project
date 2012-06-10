/*
 * SeringeSensor.java
 *
 * Created on 2 de Junho de 2003, 14:18
 */

package pt.utl.ist.elab.client.pv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SeringeSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1207470508728415849L;

	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private final BufferedImage imgEmbolo = new BufferedImage(30, 57, BufferedImage.TYPE_INT_ARGB);
	private final BufferedImage imgSeringe = new BufferedImage(36, 65, BufferedImage.TYPE_INT_ARGB);
	private final Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));

	/** Creates new form SeringeSensor */
	public SeringeSensor() {
		initComponents();
		setPreferredSize(new Dimension(36, 117));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());

		Graphics2D g2D = (Graphics2D) imgEmbolo.getGraphics();
		g2D.setPaint(Color.gray);
		g2D.fillRoundRect(0, 0, 30, 5, 5, 2);
		g2D.setPaint(new Color(255, 255, 230));
		g2D.fillRect(12, 5, 6, 50);
		g2D.fillRoundRect(5, 55, 20, 2, 5, 1);

		g2D = (Graphics2D) imgSeringe.getGraphics();
		g2D.setColor(new Color(255, 255, 230));
		g2D.drawLine(0, 60, 2, 60);
		g2D.drawLine(2, 60, 2, 10);
		g2D.drawLine(2, 10, 17, 10);
		g2D.drawLine(17, 10, 17, 0);
		g2D.setColor(new Color(255, 255, 215));
		g2D.drawLine(19, 0, 19, 10);
		g2D.drawLine(19, 10, 34, 10);
		g2D.drawLine(34, 10, 34, 60);
		g2D.drawLine(34, 60, 36, 60);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{

		setLayout(new java.awt.BorderLayout());

		setBackground(new java.awt.Color(0, 0, 0));
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
		if (pressure == null || volume == null) {
			return;
		}

		final Graphics2D g2D = (Graphics2D) g;

		final double pressure = this.pressure.getValue().toDouble();
		final double volume = this.volume.getValue().toDouble();

		final int imgWidth = (int) getBounds().getWidth();
		final int imgHeight = (int) getBounds().getHeight();
		final int center_x = imgWidth / 2 - imgSeringe.getWidth() / 2;
		final int center_y = imgHeight / 2 - imgSeringe.getHeight() / 2 - imgEmbolo.getHeight() / 2;

		g2D.drawImage(imgSeringe, center_x, center_y, imgSeringe.getWidth(), imgSeringe.getHeight(), null);
		final int volumepos = (int) Math.floor(40. * (volume - VOL_MIN) / (VOL_MAX - VOL_MIN));
		int pressureColor = 155 * (int) Math.floor((pressure - PRESS_MIN) / (PRESS_MAX - PRESS_MIN));
		if (pressureColor > 155) {
			pressureColor = 155;
		} else if (pressureColor < -100) {
			pressureColor = -100;
		}
		g2D.drawImage(imgEmbolo, center_x + 4, center_y + 20 + volumepos, imgEmbolo.getWidth(), imgEmbolo.getHeight(),
				null);
		g2D.setColor(new Color(0, 255, 255, 100 + pressureColor));
		g2D.fillRect(center_x + 3, center_y + 11, 31, volumepos + 10 - 1);
		g2D.setFont(new Font("Verdana", Font.PLAIN, 12));
		g2D.setColor(new Color(255, 255, 230));
		g2D.drawString("P [" + header.getChannelsConfig(0).getSelectedScale().getMultiplier()
				+ header.getChannelsConfig(0).getSelectedScale().getPhysicsUnitSymbol() + "] = " + (int) pressure,
				center_x + imgSeringe.getWidth() + 10, center_y + imgSeringe.getHeight());
		g2D.drawString("V [" + header.getChannelsConfig(1).getSelectedScale().getMultiplier()
				+ header.getChannelsConfig(1).getSelectedScale().getPhysicsUnitSymbol() + "] = "
				+ SeringeSensor.decimalFormat.format(volume), center_x + imgSeringe.getWidth() + 10, center_y
				+ imgSeringe.getHeight() + g2D.getFontMetrics().getHeight() + 8);

	}

	public double VOL_MAX = 500.;
	public double VOL_MIN = 100.;
	public double PRESS_MAX = 10.;
	public double PRESS_MIN = 1.;
	private PhysicsValue pressure = null;
	private PhysicsValue volume = null;

	public void setPressureAndVolume(final PhysicsValue pressure, final PhysicsValue volume) {
		this.pressure = pressure;
		this.volume = volume;
		repaint();
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

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public void dataModelStoped() {
	}

	public void headerAvailable(final HardwareAcquisitionConfig header) {
		acqHeaderInited = true;

		if (header != null) {
			this.header = header;
			VOL_MAX = header.getChannelsConfig(1).getSelectedScale().getMaximumValue().toDouble();
			VOL_MIN = header.getChannelsConfig(1).getSelectedScale().getMinimumValue().toDouble();

			PRESS_MAX = header.getChannelsConfig(0).getSelectedScale().getMaximumValue().toDouble();
			PRESS_MIN = header.getChannelsConfig(0).getSelectedScale().getMinimumValue().toDouble();
		}
	}

	private HardwareAcquisitionConfig header = null;
	private boolean acqHeaderInited = false;

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		if (!acqHeaderInited) {
			headerAvailable(model.getAcquisitionConfig());
		}

		final int lastsample = evt.getSamplesEndIndex();
		setPressureAndVolume(model.getValueAt(lastsample, 0), model.getValueAt(lastsample, 1));
	}

	@Override
	public String getName() {
		return "Sensor";
	}

	@Override
	public JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public JToolBar getToolBar() {
		return null;
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

}
