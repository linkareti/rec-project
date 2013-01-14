/*
 * Atribui.java
 *
 * Created on 27 de Janeiro de 2003, 15:31
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Atribui extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9079639736363351528L;
	private final int WIDTH = 77;
	private final int HEIGHT = 45;
	private final int LINEWIDTH = 6;
	private final java.awt.Dimension dimension = new java.awt.Dimension(WIDTH, HEIGHT);
	private final int TIPO = 10;
	private final javax.swing.ImageIcon image = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/atribui.gif"));
	private pt.utl.ist.elab.client.webrobot.customizer.Models.ModelAtribui model;
	private pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfAtribui confAtribui;
	private final java.awt.Color backgroundColor = new java.awt.Color(204, 204, 204);
	private String text;
	private final String fullNameDescription = "Atribuicao de valores as variaveis";

	/** Holds value of property paintBottom. */
	private boolean paintBottom = false;

	/** Holds value of property paintLeft. */
	private boolean paintLeft = false;

	/** Holds value of property cancel. */
	private boolean cancel = false;

	/** Creates a new instance of CompInt */
	public Atribui(final javax.swing.JFrame parent) {
		super();
		setCancel(false);
		model = new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelAtribui();
		confAtribui = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfAtribui(parent, true, model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, confAtribui);
		confAtribui.setVisible(true);
		if (confAtribui.isCancel()) {
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

	public Atribui(final pt.utl.ist.elab.client.webrobot.customizer.Models.ModelAtribui model) {
		super();
		setCancel(false);
		this.model = new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelAtribui();
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
		if (model.getFlag() == 0) {
			// text=model.getD1()+model.getD2()+model.getD3();
			text = model.getD1() + "=" + model.getD3();
		} else {
			// text=model.getD1()+model.getD2()+model.getValor();
			text = model.getD1() + "=" + model.getValor();
		}
		g.drawString(text, ((image.getIconWidth() - text.length() * g.getFont().getSize() * 1 / 2) / 2),
				image.getIconWidth() / 2);
		super.paintComponent(g);
	}

	@Override
	public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel() {
		return model;
	}

	@Override
	public void edit(final javax.swing.JFrame parent) {
		confAtribui = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfAtribui(parent, true, model);
		new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent, confAtribui);
		confAtribui.setVisible(true);
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
