/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package org.jdesktop.laffy;

import javax.swing.JPanel;
import javax.swing.JWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.image.BufferedImage;

/**
 * SplashWindow - Fun little progress splash screen for laffy
 * 
 * @author Created by Jasper Potts (Apr 16, 2008)
 */
public class SplashWindow extends JWindow {
    private static final Color[] COLORS = new Color[] { new Color(255, 212, 3), new Color(255, 177, 0),
	    new Color(255, 109, 0), new Color(255, 2, 7), new Color(172, 3, 36), new Color(238, 4, 65),
	    new Color(255, 116, 182), new Color(156, 145, 221), new Color(96, 140, 213), new Color(124, 178, 238),
	    new Color(162, 211, 254), new Color(65, 156, 157), new Color(0, 181, 174), new Color(2, 191, 3), };

    private BufferedImage splashText;
    private GraphicsPanel graphicsPanel = new GraphicsPanel();
    private float percentageComplete = 0f;

    public SplashWindow() {
	splashText = Laffy.loadImage("splash.png");
	//            splashText = ImageIO.read(SplashWindow.class.getClassLoader().getResource(
	//                    "org/jdesktop/laffy/icons/splash.png"));
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(graphicsPanel, BorderLayout.CENTER);
	pack();
	setSize(319, 319);
	setLocationRelativeTo(null);
    }

    public void setPercentageComplete(float percentageComplete) {
	this.percentageComplete = Math.min(1, Math.max(0, percentageComplete));
	repaint();
    }

    private class GraphicsPanel extends JPanel {
	private GraphicsPanel() {
	    setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    for (int i = 0, x = 0; i < COLORS.length; i++) {
		g2.setColor(COLORS[i]);
		g2.fillRect(x, 0, 20, 319);
		x += 23;
	    }
	    g2.setPaint(new LinearGradientPaint(0, 0, 319, 319,
		    new float[] { Math.max(0, (percentageComplete * 1.2f) - 0.2001f),
		    // -fudge to stop both being equal at %100
			    Math.min(1, (percentageComplete * 1.2f) + 0.001f) }, // +fudge to stop both being equal at %0
		    new Color[] { new Color(255, 255, 255, 0), Color.WHITE },
		    MultipleGradientPaint.CycleMethod.NO_CYCLE));
	    g2.fillRect(0, 0, 319, 319);
	    g2.drawImage(splashText, 0, 40, this);
	}
    }
}
