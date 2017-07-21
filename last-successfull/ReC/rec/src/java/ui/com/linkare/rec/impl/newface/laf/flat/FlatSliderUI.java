/* 
 * FlatSliderUI.java created on 2009/05/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSliderUI;

/**
 * 
 * @author João Florindo
 */
public class FlatSliderUI extends MetalSliderUI {

	private static final Color COLOR_TRACK_ENABLE = new Color(0x517DA8);
	private static final Color COLOR_TRACK_DISABLE = Color.gray;// new
																// Color(0x517DA8);

	public static ComponentUI createUI(final JComponent x) {
		return new FlatSliderUI();
	}

	@Override
	public void paintThumb(final Graphics g) {
		final Rectangle knobBounds = thumbRect;
		final Icon sliderH = new javax.swing.ImageIcon(FlatSliderUI.class.getResource("resources/sliderHead.png"));
		final Icon sliderV = new javax.swing.ImageIcon(FlatSliderUI.class.getResource("resources/sliderHeadV.png"));

		g.translate(knobBounds.x, knobBounds.y);

		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			sliderH.paintIcon(slider, g, 0, 0);
		} else {
			sliderV.paintIcon(slider, g, 0, 0);
		}

		g.translate(-knobBounds.x, -knobBounds.y);
	}

	@Override
	public void paintTrack(final Graphics g) {

		g.translate(trackRect.x, trackRect.y);

		final int trackLeft = 0;
		final int trackTop = 0;
		final int trackPos = 7;

		// Draw the track
		if (slider.isEnabled()) {
			g.setColor(FlatSliderUI.COLOR_TRACK_ENABLE);
			if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
				g.drawLine(trackLeft, trackTop + trackPos, trackRect.width, trackTop + trackPos);
				g.drawLine(trackLeft, trackTop + 8, trackRect.width, trackTop + 8);
			} else {
				g.drawLine(trackLeft + trackPos, trackTop, trackLeft + trackPos, trackRect.height);
				g.drawLine(trackLeft + trackPos + 1, trackTop, trackLeft + trackPos + 1, trackRect.height);
			}
		} else {
			g.setColor(FlatSliderUI.COLOR_TRACK_DISABLE);
			if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
				g.drawLine(trackLeft, trackTop + trackPos, trackRect.width, trackTop + trackPos);
				g.drawLine(trackLeft, trackTop + 8, trackRect.width, trackTop + 8);
			} else {
				g.drawLine(trackLeft + trackPos, trackTop, trackLeft + trackPos, trackRect.height);
				g.drawLine(trackLeft + trackPos + 1, trackTop, trackLeft + trackPos + 1, trackRect.height);
			}
		}
		g.translate(-trackRect.x, -trackRect.y);
	}
}
