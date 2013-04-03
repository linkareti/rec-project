/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component.tools;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * 
 * @author Henrique Fernandes
 * @param <C> 
 */
public class FadeEffectSupport<C extends JComponent> implements FadeEffect, ActionListener {

	public static final float OPAQUE = 1.0f;

	public static final float TRANSLUCENT = 0.0f;

	private final C component;

	private State currentState;

	private float alpha = 1.0f; // current opacity
	private final Timer timer = new Timer(30, this); // for later start/stop
														// actions
	private final int animationDuration = 450; // each animation duration (ms)
	private long animationStartTime; // start time for each animation
	private BufferedImage componentImage = null;

	public FadeEffectSupport(final C component) {
		this.component = component;
		this.currentState = State.OPAQUE;
	}

	@Override
	public void fadeIn() {
		alpha = FadeEffectSupport.TRANSLUCENT;
		animationStartTime = System.nanoTime() / 1000000;
		currentState = State.FADING_IN;
		timer.start();
		// Continues on timer event
	}

	@Override
	public void fadeOut() {
		alpha = FadeEffectSupport.OPAQUE;
		animationStartTime = System.nanoTime() / 1000000;
		currentState = State.FADING_OUT;
		timer.start();
		// Continues on timer event
	}

	/**
	 * Timer animation event
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// calculate the elapsed fraction
		final long currentTime = System.nanoTime() / 1000000;
		final long totalTime = currentTime - animationStartTime;
		if (totalTime > animationDuration) {
			animationStartTime = currentTime;
			timer.stop();
		}
		float fraction = (float) totalTime / animationDuration;
		fraction = Math.min(1.0f, fraction);
		// This calculation will cause alpha to go from 1 to 0 and back to 1
		// as the fraction goes from 0 to 1
		// alpha = Math.abs(1 - (2 * fraction));

		switch (currentState) {
		case FADING_IN:
			alpha = Math.abs(fraction);
			if (alpha >= 1.0f) {
				currentState = State.OPAQUE;
			}
			break;
		case FADING_OUT:
			alpha = Math.abs(1 - fraction);
			if (alpha <= 0.0f) {
				currentState = State.TRANSLUCENT;
			}
			break;
		}
		component.repaint();
	}

	public Graphics getComponentGraphics(final Graphics g) {
		componentImage = component.getGraphicsConfiguration().createCompatibleImage(component.getWidth(),
				component.getHeight());

		final Graphics gComponent = componentImage.getGraphics();

		return gComponent;
	}

	void paint(final Graphics g) {
		// Make the graphics translucent
		final Graphics2D g2d = (Graphics2D) g;
		final AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(newComposite);

		// Copy the component's image to the destination graphics, translucently
		g2d.drawImage(componentImage, 0, 0, null);
	}

	@Override
	public State getFadeEffectState() {
		return currentState;
	}
}
