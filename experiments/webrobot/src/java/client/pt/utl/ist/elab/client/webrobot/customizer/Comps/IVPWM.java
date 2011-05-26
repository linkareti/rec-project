/*
 * IvPWM.java
 *
 * Created on 27 de Janeiro de 2003, 16:03
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class IVPWM extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8924517921431375671L;
	private static int WIDTH = 77;
	private static int HEIGHT = 45;
	private static int LINEWIDTH = 6;
	private static java.awt.Dimension dimension = new java.awt.Dimension(IVPWM.WIDTH, IVPWM.HEIGHT);
	private static int TIPO = 14;
	private final javax.swing.ImageIcon image = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/ivpwm.gif"));
	private static pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model;
	private static pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM confIVPWM;
	private static java.awt.Color backgroundColor = new java.awt.Color(204, 204, 204);
	private String text;
	private static String fullNameDescription = "Configuracao dos sensores IV";

	/** Holds value of property paintBottom. */
	private boolean paintBottom = false;

	/** Holds value of property paintLeft. */
	private boolean paintLeft = false;

	/** Holds value of property cancel. */
	private boolean cancel = false;

	/** Creates a new instance of CompInt */
	public IVPWM(final javax.swing.JFrame parent,
			final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model) {
		super();
		IVPWM.model = model;
		setCancel(false);
		IVPWM.confIVPWM = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM(parent, true, model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, IVPWM.confIVPWM);
		IVPWM.confIVPWM.show();
		if (IVPWM.confIVPWM.isCancel()) {
			setCancel(true);
			return;
		} else {
			setCancel(false);
		}
		setMinimumSize(IVPWM.dimension);
		setPreferredSize(IVPWM.dimension);
		setMaximumSize(IVPWM.dimension);
		setBorderPainted(false);
		setBackground(new java.awt.Color(0, 0, 0, 0));// background transparente
		setOpaque(false);
		text = "";
	}

	public IVPWM(final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model) {
		super();
		IVPWM.model = model;
		setMinimumSize(IVPWM.dimension);
		setPreferredSize(IVPWM.dimension);
		setMaximumSize(IVPWM.dimension);
		setBorderPainted(false);
		setBackground(new java.awt.Color(0, 0, 0, 0));// background transparente
		setOpaque(false);
		text = "";
	}

	@Override
	public void paintComponent(final java.awt.Graphics g) {
		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setStroke(new java.awt.BasicStroke(3f));
		g.setColor(IVPWM.backgroundColor);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		g.drawImage(image.getImage(), 0, 0, image.getIconWidth(), image.getIconHeight(), null);
		g.setColor(java.awt.Color.black);
		if (paintBottom) {
			g2.drawLine(image.getIconWidth() / 2, image.getIconHeight(), image.getIconWidth() / 2,
					image.getIconHeight() + IVPWM.LINEWIDTH);
		}
		if (paintLeft) {
			g2.drawLine(image.getIconWidth(), image.getIconHeight() / 2, image.getIconWidth() + IVPWM.LINEWIDTH,
					image.getIconHeight() / 2);
		}
		super.paintComponent(g);
	}

	@Override
	public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel() {
		return IVPWM.model;
	}

	public void setDataModel(final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model) {
		IVPWM.model = model;
	}

	@Override
	public void edit(final javax.swing.JFrame parent) {
		IVPWM.confIVPWM = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM(parent, true,
				IVPWM.model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, IVPWM.confIVPWM);
		IVPWM.confIVPWM.show();
	}

	@Override
	public int getTipo() {
		return IVPWM.TIPO;
	}

	@Override
	public String getFullNameDescription() {
		return fullNameDescription;
	}

	/**
	 * Getter for property paintBottom.
	 * 
	 * @return Value of property paintBottom.
	 */
	@Override
	public boolean isPaintBottom() {
		return paintBottom;
	}

	/**
	 * Setter for property paintBottom.
	 * 
	 * @param paintBottom New value of property paintBottom.
	 */
	@Override
	public void setPaintBottom(final boolean paintBottom) {
		this.paintBottom = paintBottom;
		repaint();
	}

	/**
	 * Getter for property paintLeft.
	 * 
	 * @return Value of property paintLeft.
	 */
	@Override
	public boolean isPaintLeft() {
		return paintLeft;
	}

	/**
	 * Setter for property paintLeft.
	 * 
	 * @param paintLeft New value of property paintLeft.
	 */
	@Override
	public void setPaintLeft(final boolean paintLeft) {
		this.paintLeft = paintLeft;
		repaint();
	}

	/**
	 * Getter for property cancel.
	 * 
	 * @return Value of property cancel.
	 */
	@Override
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * Setter for property cancel.
	 * 
	 * @param cancel New value of property cancel.
	 */
	@Override
	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}
}
