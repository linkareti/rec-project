package pt.utl.ist.elab.client.wppucrio2;

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
import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class WorldPendulumSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
        com.linkare.rec.impl.client.experiment.ExpDataModelListener {

    /** Generated UID */
    private static final long serialVersionUID = -2818554520798772975L;

    private final BufferedImage imgTube1 = new BufferedImage(50, 200, BufferedImage.TYPE_INT_ARGB);

    private final Icon icon = new javax.swing.ImageIcon(getClass().getResource(
            "/com/linkare/rec/impl/newface/resources/legacy/sensor16.gif"));

    /** Creates new form SeringeSensor */
    public WorldPendulumSensor() {
        initComponents();
        setPreferredSize(new Dimension(imgTube1.getWidth() + 2 * 10, imgTube1.getHeight() + 2 * 10));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());

        final Graphics2D g2D = (Graphics2D) imgTube1.getGraphics();
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
    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        if (height == null) {
            return;
        }

        final Graphics2D g2D = (Graphics2D) g;

        final double height = this.height.getValue().toDouble();

        final int imgWidth = (int) getBounds().getWidth();
        final int imgHeight = (int) getBounds().getHeight();
        final int x_start = imgWidth / 2 - imgTube1.getWidth() / 2;
        final int y_start = imgHeight / 2 - imgTube1.getHeight() / 2;

        g2D.drawImage(imgTube1, x_start, y_start, imgTube1.getWidth(), imgTube1.getHeight(), null);

        final int radius = 5;
        final int heightPos = radius
                + (int) Math.floor((imgTube1.getHeight() - 2 * radius) * (height - HEIGHT_MIN)
                / (HEIGHT_MAX - HEIGHT_MIN));

        g2D.setPaint(new Color(240, 240, 0, 255));
        g2D.fillOval(x_start + imgTube1.getWidth() / 2 - radius, y_start + heightPos - radius, 2 * radius, 2 * radius);
    }

    public double HEIGHT_MAX = 2.;

    public double HEIGHT_MIN = 0.;

    private PhysicsValue height = null;

    public void setHeight(final PhysicsValue height) {
        this.height = height;
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
        this.header = header;

        System.out.println("OpticaSensor.headerAvailable(HardwareAcquisitionConfig  header)");
        System.out.println("header [" + header + "]");

        // TODO
        // HEIGHT_MAX =
        // this.header.getChannelsConfig(0).getSelectedScale().getMaximumValue().toDouble();
        // HEIGHT_MIN =
        // this.header.getChannelsConfig(0).getSelectedScale().getMinimumValue().toDouble();

    }

    private HardwareAcquisitionConfig header = null;

    private boolean acqHeaderInited = false;

    @Override
    public void newSamples(final NewExpDataEvent evt) {
        if (!acqHeaderInited) {
            headerAvailable(model.getAcquisitionConfig());
        }

        final int lastsample = evt.getSamplesEndIndex();
        setHeight(model.getValueAt(lastsample, 0));
    }

    @Override
    public String getName() {
        return "Laser Sensor";
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
