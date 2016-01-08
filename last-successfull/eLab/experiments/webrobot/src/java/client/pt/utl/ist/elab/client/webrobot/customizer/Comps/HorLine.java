/*
 * HorLine.java
 *
 * Created on 22 de Dezembro de 2002, 0:35
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class HorLine extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4576565510207461431L;
	private final int WIDTH = 77;
	private final int HEIGHT = 45;
	private final java.awt.Dimension dimension = new java.awt.Dimension(WIDTH, HEIGHT);
	private final java.awt.Color backgroundColor = new java.awt.Color(204, 204, 204);
	private javax.swing.ImageIcon image;
	private final String fullNameDescription = "ligacao";

	/** Creates a new instance of HorLine */
	public HorLine() {
		super();
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setBorderPainted(false);
		setBackground(new java.awt.Color(0, 0, 0, 0));// background transparente
		setOpaque(false);
	}

	@Override
	public void paintComponent(final java.awt.Graphics g) {
		image = new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/atribui.gif"));
		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setStroke(new java.awt.BasicStroke(3f));
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		g.setColor(java.awt.Color.black);
		g2.drawLine(0, image.getIconHeight() / 2, this.getSize().width, image.getIconHeight() / 2);
		super.paintComponent(g);
	}

	@Override
	public String getFullNameDescription() {
		return fullNameDescription;
	}

	@Override
	public void setPaintBottom(final boolean paintLeft) {
	}

	@Override
	public boolean isPaintBottom() {
		return false;
	}

	@Override
	public boolean isPaintLeft() {
		return true;
	}
}
