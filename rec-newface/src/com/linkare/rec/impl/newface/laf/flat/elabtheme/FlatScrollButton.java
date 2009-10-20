/* 
 * FlatScrollButton.java created on 2009/04/15
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.plaf.metal.MetalScrollButton;

/**
 * 
 * @author JOE
 */
public class FlatScrollButton extends MetalScrollButton {

	//FIXME: Corrigir esta forma de obter as cores. Tem de ser por intermédio de propriedades
	//ENABLED COLORS
	public static final Color COLOR_BORDER = new Color(0x848485);
	public static final Color COLOR_BACKGROUND = new Color(0xE4EEED);
	//OTHER COLORS
	public static final Color COLOR_DIS_BACKGROUND = new Color(0xE4EEED);
	public static final Color COLOR_PRESS_BACKGROUND = new Color(0xE4EEED);

	private boolean isFreeStanding = true;

	/**
	 * Creates the <code>FlatScrollButton</code>.
	 * 
	 * @param direction
	 * @param width
	 * @param freeStanding
	 */
	public FlatScrollButton(int direction, int width, boolean freeStanding) {
		super(direction, width, freeStanding);

	}

	@Override
	public Dimension getPreferredSize() {
		if (getDirection() == NORTH) {
			return new Dimension(getButtonWidth(), getButtonWidth() - 2);
		} else if (getDirection() == SOUTH) {
			return new Dimension(getButtonWidth(), getButtonWidth() - (isFreeStanding ? 1 : 2));
		} else if (getDirection() == EAST) {
			return new Dimension(getButtonWidth() - (isFreeStanding ? 1 : 2), getButtonWidth());
		} else if (getDirection() == WEST) {
			return new Dimension(getButtonWidth() - 2, getButtonWidth());
		} else {
			return new Dimension(0, 0);
		}
	}

	@Override
	public Dimension getMinimumSize() {
		//FIXME: Quais são os tamanhos dos botões
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		//FIXME: Quais são os tamanhos dos botões
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public void paint(Graphics g) {
		//        boolean leftToRight = MetalUtils.isLeftToRight(this);
		boolean isEnabled = getParent().isEnabled();

		//FIXME: Em vez das cores têm de ser propriedades
		Color arrowColor = isEnabled ? COLOR_BORDER : COLOR_BORDER;
		boolean isPressed = getModel().isPressed();
		int width = getWidth();
		int height = getHeight();
		int w = width;
		int h = height;
		int arrowHeight = (height + 1) / 4;
		int arrowWidth = (height + 1) / 2;

		//FIXME: Decidir se as cores mudam com o click 
		if (isPressed) {
			g.setColor(COLOR_PRESS_BACKGROUND);
		} else {
			g.setColor(COLOR_BACKGROUND);
		}

		g.fillRect(0, 0, width, height);

		if (getDirection() == NORTH) {

			// Draw the arrow
			g.setColor(arrowColor);
			int startY = ((h + 1) - arrowHeight) / 2;
			int startX = (w / 2);
			for (int line = 0; line < arrowHeight; line++) {
				g.drawLine(startX - line, startY + line, startX + line, startY + line);
			}

			// Draw the Button
			if (isEnabled) {
				g.setColor(COLOR_BORDER);
				// 		FIXME: Decidir como ficam as cores quando há PRESS	
				if (!isPressed) {
					//		 			g.drawLine( 1, 1, width - 3, 1 );
					//		 			g.drawLine( 1, 1, 1, height - 1 );
				}

				g.drawLine(0, height - 1, width - 1, height - 1); //BOTTOM
				g.drawLine(width - 1, 1, width - 1, height - 1); //RIGHT
				g.drawLine(0, 0, width - 1, 0); //UP	
				g.drawLine(0, 0, 0, height - 1); //LEFT

			} else { // 		FIXME: Decidir como ficam as cores quando há disable
				g.setColor(COLOR_DIS_BACKGROUND);
			}

		} else if (getDirection() == SOUTH) {
			// Draw the arrow
			g.setColor(arrowColor);

			int startY = (((h + 1) - arrowHeight) / 2) + arrowHeight - 1;
			int startX = (w / 2);
			for (int line = 0; line < arrowHeight; line++) {
				g.drawLine(startX - line, startY - line, startX + line, startY - line);
			}

			if (isEnabled) {
				g.setColor(COLOR_BORDER);

				if (!isPressed) {
					//		 			g.drawLine( 1, 0, width - 3, 0 );
					//		 			g.drawLine( 1, 0, 1, height - 3 );
				}
				g.drawLine(0, height - 1, width - 1, height - 1); //BOTTOM
				g.drawLine(width - 1, 1, width - 1, height - 1); //RIGHT
				g.drawLine(0, 0, width - 1, 0); //UP	
				g.drawLine(0, 0, 0, height - 1); //LEFT
			} else {
				g.setColor(COLOR_DIS_BACKGROUND);
			}

		} else if (getDirection() == EAST) {

			g.setColor(arrowColor);
			int startX = (((w + 1) - arrowHeight) / 2) + arrowHeight - 1;
			int startY = (h / 2);
			for (int line = 0; line < arrowHeight; line++) {
				g.drawLine(startX - line, startY - line, startX - line, startY + line + 1);
			}

			if (isEnabled) {
				g.setColor(COLOR_BORDER);

				if (!isPressed) {
					//	 		        g.drawLine( 0, 1, width - 3, 1 );
					//	 		        g.drawLine( 0, 1, 0, height - 3 );
				}
				g.drawLine(0, height - 1, width - 1, height - 1); //BOTTOM
				g.drawLine(width - 1, 1, width - 1, height - 1); //RIGHT
				g.drawLine(0, 0, width - 1, 0); //UP	
				g.drawLine(0, 0, 0, height - 1); //LEFT
			} else {
				g.setColor(COLOR_DIS_BACKGROUND);
			}

		} else if (getDirection() == WEST) {
			// Draw the arrow
			g.setColor(arrowColor);
			int startX = (((w + 1) - arrowHeight) / 2);
			int startY = (h / 2);
			for (int line = 0; line < arrowHeight; line++) {
				g.drawLine(startX + line, startY - line, startX + line, startY + line + 1);
			}

			if (isEnabled) {
				g.setColor(COLOR_BORDER);

				if (!isPressed) {
					//	 		        g.drawLine( 1, 1, width - 1, 1 );
					//	 		        g.drawLine( 1, 1, 1, height - 3 );
				}
				g.drawLine(0, height - 1, width - 1, height - 1); //BOTTOM
				g.drawLine(width - 1, 1, width - 1, height - 1); //RIGHT
				g.drawLine(0, 0, width - 1, 0); //UP	
				g.drawLine(0, 0, 0, height - 1); //LEFT
			} else {
				g.setColor(COLOR_DIS_BACKGROUND);
			}
		}
	}
}
