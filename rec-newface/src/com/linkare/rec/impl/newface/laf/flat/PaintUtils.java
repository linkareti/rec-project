/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * 
 * @author Henrique Fernandes
 */
public class PaintUtils {

    /**
     * Draws an image on top of a component by doing a 3x3 grid stretch of the image using the specified insets.
     * 
     * Based on SwingLabs, LGPL
     */
    public static void tileStretchPaint(Graphics g, JComponent comp, BufferedImage img, Insets ins, Point pos) {

	Graphics2D g2d = (Graphics2D) g.create();

	int left = ins.left;
	int right = ins.right;
	int top = ins.top;
	int bottom = ins.bottom;

	//Dimension size = component.getSize();
	//Rectangle rect = component.getVisibleRect();
	//g = g.create();
	//Rectangle clip = SwingUtilities.convertRectangle(component, rect, this);
	//g.clipRect(clip.x, clip.y, clip.width, clip.height);

	g2d.clipRect(pos.x - left, pos.y - top, pos.x + comp.getWidth() + right, pos.y + comp.getHeight() + bottom);

	// top
	g2d.drawImage(img, pos.x - left, pos.y - top, pos.x, pos.y, 0, 0, left, top, null);
	g2d.drawImage(img, pos.x, pos.y - top, pos.x + comp.getWidth(), pos.y, left, 0, img.getWidth() - right, top,
		null);
	g2d.drawImage(img, pos.x + comp.getWidth(), pos.y - top, pos.x + comp.getWidth() + right, pos.y, img.getWidth()
		- right, 0, img.getWidth(), top, null);

	// middle
	g2d.drawImage(img, pos.x - left, pos.y, pos.x, pos.y + comp.getHeight(), 0, top, left,
		img.getHeight() - bottom, null);

	g2d.drawImage(img, pos.x + left, pos.y + top, pos.x + comp.getWidth() - right, pos.y + comp.getHeight()
		- bottom, left, top, img.getWidth() - right, img.getHeight() - bottom, null);

	g2d.drawImage(img, pos.x + comp.getWidth(), pos.y, pos.x + comp.getWidth() + right, pos.y + comp.getHeight(),
		img.getWidth() - right, top, img.getWidth(), img.getHeight() - bottom, null);

	// bottom
	g2d.drawImage(img, pos.x - left, pos.y + comp.getHeight(), pos.x, pos.y + comp.getHeight() + bottom, 0, img
		.getHeight()
		- bottom, left, img.getHeight(), null);
	g2d.drawImage(img, pos.x, pos.y + comp.getHeight(), pos.x + comp.getWidth(), pos.y + comp.getHeight() + bottom,
		left, img.getHeight() - bottom, img.getWidth() - right, img.getHeight(), null);
	g2d.drawImage(img, pos.x + comp.getWidth(), pos.y + comp.getHeight(), pos.x + comp.getWidth() + right, pos.y
		+ comp.getHeight() + bottom, img.getWidth() - right, img.getHeight() - bottom, img.getWidth(), img
		.getHeight(), null);

	g2d.dispose();
    }
}
