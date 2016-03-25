/*
 * MarchAtras.java
 *
 * Created on 27 de Janeiro de 2003, 16:07
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class MarchAtras extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3731782463122885583L;
	private final int WIDTH = 77;
	private final int HEIGHT = 45;
	private final int LINEWIDTH = 6;
	private final java.awt.Dimension dimension = new java.awt.Dimension(WIDTH, HEIGHT);
	private final int TIPO = 4;
	private final javax.swing.ImageIcon image = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/marchatras.gif"));
	private pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras model;
	private pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfMarchAtras confMarchAtras;
	private final java.awt.Color backgroundColor = new java.awt.Color(204, 204, 204);
	private String text;
	private final String fullNameDescription = "Segue para tras";

	/** Holds value of property paintBottom. */
	private boolean paintBottom = false;

	/** Holds value of property paintLeft. */
	private boolean paintLeft = false;

	/** Holds value of property cancel. */
	private boolean cancel = false;

	/**
	 * Creates a new instance of CompInt
	 * 
	 * @param parent
	 */
	public MarchAtras(final javax.swing.JFrame parent) {
		super();
		setCancel(false);
		model = new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras();
		confMarchAtras = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfMarchAtras(parent, true,
				model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, confMarchAtras);
		confMarchAtras.setVisible(true);
		if (confMarchAtras.isCancel()) {
			setCancel(true);
			return;
		}
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setBorderPainted(false);
		setBackground(new java.awt.Color(0, 0, 0, 0));// background transparente
		setOpaque(false);
		text = "";
	}

	public MarchAtras(final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras model) {
		super();
		setCancel(false);
		this.model = new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras();
		this.model.setBaixo(model.getBaixo());
		this.model.setColuna(model.getColuna());
		this.model.setD1(model.getD1());
		this.model.setD2(model.getD2());
		this.model.setD3(model.getD3());
		this.model.setEsquerda(model.getEsquerda());
		this.model.setFlag(model.getFlag());
		this.model.setNivel(model.getNivel());
		this.model.setValor(model.getValor());
		this.model.setValor2(model.getValor2());
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setBorderPainted(false);
		setBackground(new java.awt.Color(0, 0, 0, 0));// background transparente
		setOpaque(false);
		text = "";
	}

	@Override
	public void paintComponent(final java.awt.Graphics g) {
		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setStroke(new java.awt.BasicStroke(3f));
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		g.drawImage(image.getImage(), 0, 0, image.getIconWidth(), image.getIconHeight(), null);
		g.setColor(java.awt.Color.black);
		if (paintBottom) {
			g2.drawLine(image.getIconWidth() / 2, image.getIconHeight(), image.getIconWidth() / 2,
					image.getIconHeight() + LINEWIDTH);
		}
		if (paintLeft) {
			g2.drawLine(image.getIconWidth(), image.getIconHeight() / 2, image.getIconWidth() + LINEWIDTH,
					image.getIconHeight() / 2);
		}
		g.setColor(java.awt.Color.black);
		text = "" + model.getValor();
		g.drawString(text, ((image.getIconWidth() - text.length() * g.getFont().getSize() * 1 / 3) / 2),
				image.getIconWidth() / 3);
		super.paintComponent(g);
	}

	@Override
	public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel() {
		return model;
	}

	public void setDataModel(final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelMarchAtras model) {
		this.model = model;
	}

	@Override
	public void edit(final javax.swing.JFrame parent) {
		confMarchAtras = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfMarchAtras(parent, true,
				model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, confMarchAtras);
		confMarchAtras.setVisible(true);
	}

	@Override
	public int getTipo() {
		return TIPO;
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
