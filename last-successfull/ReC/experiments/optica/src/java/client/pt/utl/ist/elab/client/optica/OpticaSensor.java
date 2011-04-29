
package pt.utl.ist.elab.client.optica;

import java.awt.Color;
import java.awt.Dimension;
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
public class OpticaSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/** Generated UID */
	private static final long serialVersionUID = -2818554520798772975L;
	
	private BufferedImage imgTube1 = new BufferedImage(50, 200, BufferedImage.TYPE_INT_ARGB);
	private Icon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));

	/** Creates new form SeringeSensor */
	public OpticaSensor() {
		initComponents();
		setPreferredSize(new Dimension(imgTube1.getWidth() + 2 * 10, imgTube1.getHeight() + 2 * 10));
		setMinimumSize(getPreferredSize());
		setMaximumSize(getPreferredSize());

		Graphics2D g2D = (Graphics2D) imgTube1.getGraphics();
		g2D.setColor(Color.white);
		g2D.drawRect(0, 0, imgTube1.getWidth() - 1, imgTube1.getHeight() - 1);
		g2D.setColor(Color.cyan);
		g2D.drawRect(2, 2, imgTube1.getWidth() - 3, imgTube1.getHeight() - 3);
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
	public void paint(Graphics g) {
		super.paint(g);
		if (height == null)
			return;

		Graphics2D g2D = (Graphics2D) g;

		double height = this.height.getValue().toDouble();

		int imgWidth = (int) getBounds().getWidth();
		int imgHeight = (int) getBounds().getHeight();
		int x_start = imgWidth / 2 - imgTube1.getWidth() / 2;
		int y_start = imgHeight / 2 - imgTube1.getHeight() / 2;

		g2D.drawImage(imgTube1, x_start, y_start, imgTube1.getWidth(), imgTube1.getHeight(), null);

		int radius = 5;
		int heightPos = radius
				+ (int) Math.floor((float) (imgTube1.getHeight() - 2 * radius) * (height - HEIGHT_MIN)
						/ (HEIGHT_MAX - HEIGHT_MIN));

		g2D.setPaint(new Color(240, 240, 0, 255));
		g2D.fillOval(x_start + imgTube1.getWidth() / 2 - radius, y_start + heightPos - radius, 2 * radius, 2 * radius);
	}

	public double HEIGHT_MAX = 2.;
	public double HEIGHT_MIN = 0.;
	private PhysicsValue height = null;

	public void setHeight(PhysicsValue height) {
		this.height = height;
		repaint();
	}

	public javax.swing.JComponent getDisplay() {
		return this;
	}

	public Icon getIcon() {
		return icon;
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}

	public void dataModelWaiting() {
	}

	public void dataModelStoped() {
	}

	public void headerAvailable(HardwareAcquisitionConfig header) {
		acqHeaderInited = true;
		this.header = header;
		
		System.out.println("OpticaSensor.headerAvailable(HardwareAcquisitionConfig  header)");
		System.out.println("header ["+header+"]");

		// TODO 
//		HEIGHT_MAX = this.header.getChannelsConfig(0).getSelectedScale().getMaximumValue().toDouble();
//		HEIGHT_MIN = this.header.getChannelsConfig(0).getSelectedScale().getMinimumValue().toDouble();

	}

	private HardwareAcquisitionConfig header = null;
	private boolean acqHeaderInited = false;

	public void newSamples(NewExpDataEvent evt) {
		if (!acqHeaderInited)
			headerAvailable(model.getAcquisitionConfig());

		int lastsample = evt.getSamplesEndIndex();
		setHeight(model.getValueAt(lastsample, 0));
	}

	public String getName() {
		return "Laser Sensor";
	}

	public JMenuBar getMenuBar() {
		return null;
	}

	public JToolBar getToolBar() {
		return null;
	}

	public void dataModelEnded() {
	}

	public void dataModelError() {
	}

	public void dataModelStarted() {
	}

	public void dataModelStartedNoData() {
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

}
