package com.linkare.rec.impl.baseUI.mdi;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.linkare.rec.impl.baseUI.ImageBorder;

/**
 * An extension of WDesktopPane that supports often used MDI functionality. This
 * class also handles setting scroll bars for when windows move too far to the
 * left or bottom, providing the MDIDesktopPane is in a ScrollPane.
 */
public class MDIDesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1228124322622128589L;
	private static int FRAME_OFFSET = 20;
	private final MDIDesktopManager manager;

	public MDIDesktopPane() {
		manager = new MDIDesktopManager(this);
		setDesktopManager(manager);
		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}

	public void setBackgroundImage(final java.awt.Image img, final boolean fill) {
		setBorder(new ImageBorder(img, fill));
	}

	@Override
	public void setBounds(final int x, final int y, final int w, final int h) {
		super.setBounds(x, y, w, h);
		checkDesktopSize();
	}

	public Component add(final JInternalFrame frame) {
		final JInternalFrame[] array = getAllFrames();
		Point p;
		int w;
		int h;

		final Component retval = super.add(frame);
		checkDesktopSize();

		if (array.length > 0) {
			p = array[0].getLocation();
			p.x = p.x + MDIDesktopPane.FRAME_OFFSET;
			p.y = p.y + MDIDesktopPane.FRAME_OFFSET;
		} else {
			p = new Point(0, 0);
		}
		frame.setLocation(p);
		if (frame.isResizable()) {
			frame.setLocation(p.x, p.y);
			w = getWidth() - (getWidth() / 3);
			h = getHeight() - (getHeight() / 3);
			if (w < frame.getMinimumSize().getWidth()) {
				w = (int) frame.getMinimumSize().getWidth();
			}
			if (h < frame.getMinimumSize().getHeight()) {
				h = (int) frame.getMinimumSize().getHeight();
			}
			frame.setSize(w, h);
		}
		moveToFront(frame);
		frame.setVisible(true);
		try {
			frame.setSelected(true);
		} catch (final PropertyVetoException e) {
			frame.toBack();
		}
		return retval;
	}

	@Override
	public void remove(final Component c) {
		super.remove(c);
		checkDesktopSize();
	}

	/**
	 * Cascade all internal frames
	 */
	public void cascadeFrames() {
		int x = 0;
		int y = 0;
		final JInternalFrame allFrames[] = getAllFrames();

		manager.setNormalSize();
		final int frameHeight = (getBounds().height - 5) - allFrames.length * MDIDesktopPane.FRAME_OFFSET;
		final int frameWidth = (getBounds().width - 5) - allFrames.length * MDIDesktopPane.FRAME_OFFSET;
		for (int i = allFrames.length - 1; i >= 0; i--) {
			allFrames[i].setSize(frameWidth, frameHeight);
			allFrames[i].setLocation(x, y);
			x = x + MDIDesktopPane.FRAME_OFFSET;
			y = y + MDIDesktopPane.FRAME_OFFSET;
		}
	}

	/**
	 * Tile all internal frames
	 */
	public void tileFrames() {
		final java.awt.Component allFrames[] = getAllFrames();
		manager.setNormalSize();
		final int frameHeight = getBounds().height / allFrames.length;
		int y = 0;
		for (final Component allFrame : allFrames) {
			allFrame.setSize(getBounds().width, frameHeight);
			allFrame.setLocation(0, y);
			y = y + frameHeight;
		}
	}

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given dimension.
	 */
	public void setAllSize(final Dimension d) {
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
	}

	/**
	 * Sets all component size properties ( maximum, minimum, preferred) to the
	 * given width and height.
	 */
	public void setAllSize(final int width, final int height) {
		setAllSize(new Dimension(width, height));
	}

	private void checkDesktopSize() {
		if (getParent() != null && isVisible()) {
			manager.resizeDesktop();
		}
	}
}