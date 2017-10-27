/*
 * SeringeSensor.java
 *
 * Created on 2 de Junho de 2003, 14:18
 */

package pt.utl.ist.elab.client.hidrostat;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
public class HidrostatSensors extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2976008858081776558L;
	private final BufferedImage imgTube1 = new BufferedImage(50, 200, BufferedImage.TYPE_INT_ARGB);
	private final BufferedImage imgTube2 = new BufferedImage(imgTube1.getWidth(), imgTube1.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
	private final BufferedImage imgTube3 = new BufferedImage(imgTube1.getWidth(), imgTube1.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
	private final BufferedImage imgTube4 = new BufferedImage(imgTube1.getWidth(), imgTube1.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
	private final Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));

	/** Creates new form SeringeSensor */
	public HidrostatSensors() {
		initComponents();
		setPreferredSize(new Dimension(imgTube1.getWidth() * 4 + 5 * 10, imgTube1.getHeight() + 2 * 10));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());

		Graphics2D g2D = (Graphics2D) imgTube1.getGraphics();
		Color baseColor = Color.cyan;
		g2D.setPaint(new GradientPaint(imgTube1.getWidth() / 2.F, 0.F, baseColor.brighter().brighter(), imgTube1
				.getWidth() / 2.F, imgTube1.getHeight(), baseColor.darker().darker()));
		g2D.fillRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());
		g2D.setColor(Color.white);
		g2D.drawRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());

		g2D = (Graphics2D) imgTube2.getGraphics();
		baseColor = Color.green;
		g2D.setPaint(new GradientPaint(imgTube1.getWidth() / 2.F, 0.F, baseColor.brighter().brighter(), imgTube1
				.getWidth() / 2.F, imgTube1.getHeight(), baseColor.darker().darker()));
		g2D.fillRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());
		g2D.setColor(Color.white);
		g2D.drawRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());

		g2D = (Graphics2D) imgTube3.getGraphics();
		baseColor = Color.yellow;
		g2D.setPaint(new GradientPaint(imgTube1.getWidth() / 2.F, 0.F, baseColor.brighter().brighter(), imgTube1
				.getWidth() / 2.F, imgTube1.getHeight(), baseColor.darker().darker()));
		g2D.fillRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());
		g2D.setColor(Color.white);
		g2D.drawRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());

		g2D = (Graphics2D) imgTube4.getGraphics();
		baseColor = Color.pink;
		g2D.setPaint(new GradientPaint(imgTube1.getWidth() / 2.F, 0.F, baseColor.brighter().brighter(), imgTube1
				.getWidth() / 2.F, imgTube1.getHeight(), baseColor.darker().darker()));
		g2D.fillRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());
		g2D.setColor(Color.white);
		g2D.drawRect(0, 0, imgTube1.getWidth(), imgTube1.getHeight());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()//GEN-BEGIN:initComponents
	{

		setLayout(new java.awt.BorderLayout());

		setBackground(new java.awt.Color(0, 0, 0));
		setForeground(new java.awt.Color(51, 51, 51));
	}//GEN-END:initComponents

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
		if (pressure1 == null || deepness == null) {
			return;
		}

		final Graphics2D g2D = (Graphics2D) g;

		final double deepness = this.deepness.getValue().toDouble();
		final double pressure1 = this.pressure1.getValue().toDouble();
		final double pressure2 = this.pressure2.getValue().toDouble();
		final double pressure3 = this.pressure3.getValue().toDouble();
		final double pressure4 = this.pressure4.getValue().toDouble();

		final int imgWidth = (int) getBounds().getWidth();
		final int imgHeight = (int) getBounds().getHeight();
		final int x_start = imgWidth / 2 - imgTube1.getWidth() * 2 - 25;
		final int y_start = imgHeight / 2 - imgTube1.getHeight() / 2;

		g2D.drawImage(imgTube1, x_start + 10, y_start, imgTube1.getWidth(), imgTube1.getHeight(), null);
		g2D.drawImage(imgTube2, x_start + 2 * 10 + imgTube1.getWidth(), y_start, imgTube2.getWidth(),
				imgTube2.getHeight(), null);
		g2D.drawImage(imgTube3, x_start + 3 * 10 + 2 * imgTube1.getWidth(), y_start, imgTube3.getWidth(),
				imgTube3.getHeight(), null);
		g2D.drawImage(imgTube4, x_start + 4 * 10 + 3 * imgTube1.getWidth(), y_start, imgTube4.getWidth(),
				imgTube4.getHeight(), null);

		final int max_radius = (imgTube1.getWidth() - 2) / 2;
		final int min_radius = 5;

		final int deeppos = (int) Math.floor((imgTube1.getHeight() - max_radius * 2) * (deepness - DEEP_MIN)
				/ (DEEP_MAX - DEEP_MIN));
		final int radius1 = max_radius
				- (int) Math.ceil(((float) max_radius - min_radius)
						* ((pressure1 - PRESS_MIN) / (PRESS_MAX - PRESS_MIN)));
		final int radius2 = max_radius
				- (int) Math.ceil(((float) max_radius - min_radius)
						* ((pressure2 - PRESS_MIN) / (PRESS_MAX - PRESS_MIN)));
		final int radius3 = max_radius
				- (int) Math.ceil(((float) max_radius - min_radius)
						* ((pressure3 - PRESS_MIN) / (PRESS_MAX - PRESS_MIN)));
		final int radius4 = max_radius
				- (int) Math.ceil(((float) max_radius - min_radius)
						* ((pressure4 - PRESS_MIN) / (PRESS_MAX - PRESS_MIN)));

		g2D.setPaint(new Color(255, 0, 0, 100 + 155 * (int) Math.floor((pressure1 - PRESS_MIN)
				/ (PRESS_MAX - PRESS_MIN))));
		g2D.fillOval(x_start + 10 + imgTube1.getWidth() / 2 - radius1, y_start + deeppos + max_radius - radius1,
				2 * radius1, 2 * radius1);

		g2D.setPaint(new Color(255, 0, 0, 100 + 155 * (int) Math.floor((pressure2 - PRESS_MIN)
				/ (PRESS_MAX - PRESS_MIN))));
		g2D.fillOval(x_start + 2 * 10 + imgTube1.getWidth() + imgTube1.getWidth() / 2 - radius2, y_start + deeppos
				+ max_radius - radius2, 2 * radius2, 2 * radius2);

		g2D.setPaint(new Color(255, 0, 0, 100 + 155 * (int) Math.floor((pressure3 - PRESS_MIN)
				/ (PRESS_MAX - PRESS_MIN))));
		g2D.fillOval(x_start + 3 * 10 + 2 * imgTube1.getWidth() + imgTube1.getWidth() / 2 - radius3, y_start + deeppos
				+ max_radius - radius3, 2 * radius3, 2 * radius3);

		g2D.setPaint(new Color(255, 0, 0, 100 + 155 * (int) Math.floor((pressure4 - PRESS_MIN)
				/ (PRESS_MAX - PRESS_MIN))));
		g2D.fillOval(x_start + 4 * 10 + 3 * imgTube1.getWidth() + imgTube1.getWidth() / 2 - radius4, y_start + deeppos
				+ max_radius - radius4, 2 * radius4, 2 * radius4);

		// g2D.setFont(new Font("Verdana",Font.PLAIN,12));
		// g2D.setColor(new Color(255,255,230));
		// g2D.drawString("P ["+header.getChannelsConfig(0).getSelectedScale().getMultiplier()+header.getChannelsConfig(0).getSelectedScale().getPhysicsUnitSymbol()+"] = "
		// +
		// (int)pressure,center_x+imgSeringe.getWidth()+10,center_y+imgSeringe.getHeight());
		// g2D.drawString("V ["+header.getChannelsConfig(1).getSelectedScale().getMultiplier()+header.getChannelsConfig(1).getSelectedScale().getPhysicsUnitSymbol()+"] = "
		// +
		// (int)volume,center_x+imgSeringe.getWidth()+10,center_y+imgSeringe.getHeight()+g2D.getFontMetrics().getHeight()+8);

	}

	public double DEEP_MAX = 500.;
	public double DEEP_MIN = 100.;
	public double PRESS_MAX = 10.;
	public double PRESS_MIN = 1.;
	private PhysicsValue deepness = null;
	private PhysicsValue pressure1 = null;
	private PhysicsValue pressure2 = null;
	private PhysicsValue pressure3 = null;
	private PhysicsValue pressure4 = null;

	public void setPressuresAndDeepness(final PhysicsValue deepness, final PhysicsValue pressure1, final PhysicsValue pressure2,
			final PhysicsValue pressure3, final PhysicsValue pressure4) {
		this.deepness = deepness;
		this.pressure1 = pressure1;
		this.pressure2 = pressure2;
		this.pressure3 = pressure3;
		this.pressure4 = pressure4;
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
		System.out.println("***********************************************");
		System.out.println("***********************************************");
		System.out.println("INIT headerAvailable");
		System.out.println("***********************************************");
		System.out.println("***********************************************");

		if (header != null) {
			acqHeaderInited = true;

			this.header = header;

			DEEP_MAX = header.getChannelsConfig(0).getSelectedScale().getMaximumValue().toDouble();
			DEEP_MIN = header.getChannelsConfig(0).getSelectedScale().getMinimumValue().toDouble();

			PRESS_MAX = header.getChannelsConfig(1).getSelectedScale().getMaximumValue().toDouble();
			PRESS_MIN = header.getChannelsConfig(1).getSelectedScale().getMinimumValue().toDouble();
		}

		System.out.println("***********************************************");
		System.out.println("***********************************************");
		System.out.println("END headerAvailable");
		System.out.println("***********************************************");
		System.out.println("***********************************************");
	}

	private HardwareAcquisitionConfig header = null;
	private boolean acqHeaderInited = false;

	@Override
	public void newSamples(final NewExpDataEvent evt) {
		System.out.println("***********************************************");
		System.out.println("***********************************************");
		System.out.println("NEW SAMPLES ! COOL!");
		System.out.println("***********************************************");
		System.out.println("***********************************************");
		if (!acqHeaderInited) {
			headerAvailable(model.getAcquisitionConfig());
		}

		final int lastsample = evt.getSamplesEndIndex();
		setPressuresAndDeepness(model.getValueAt(lastsample, 0), model.getValueAt(lastsample, 1),
				model.getValueAt(lastsample, 2), model.getValueAt(lastsample, 3), model.getValueAt(lastsample, 4));
	}

	@Override
	public String getName() {
		return "Deepness Sensors";
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
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.SENSOR;
    }

}