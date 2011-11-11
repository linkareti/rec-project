package com.linkare.rec.impl.baseUI.mdi;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * class used to replace the standard DesktopManager for JDesktopPane. Used to
 * provide scrollbar functionality.
 */
public class MDIDesktopManager extends DefaultDesktopManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7740499883402011710L;
	private final MDIDesktopPane desktop;

	public MDIDesktopManager(final MDIDesktopPane desktop) {
		this.desktop = desktop;
	}

	@Override
	public void endResizingFrame(final JComponent f) {
		super.endResizingFrame(f);
		resizeDesktop();
	}

	@Override
	public void endDraggingFrame(final JComponent f) {
		super.endDraggingFrame(f);
		resizeDesktop();
	}

	public void setNormalSize() {
		final JScrollPane scrollPane = getScrollPane();
		final int x = 0;
		final int y = 0;
		final Insets scrollInsets = getScrollPaneInsets();

		if (scrollPane != null) {
			final Dimension d = scrollPane.getVisibleRect().getSize();
			if (scrollPane.getBorder() != null) {
				d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top
						- scrollInsets.bottom);
			}

			d.setSize(d.getWidth() - 20, d.getHeight() - 20);
			desktop.setAllSize(x, y);
			scrollPane.invalidate();
			scrollPane.validate();
		}
	}

	private Insets getScrollPaneInsets() {
		final JScrollPane scrollPane = getScrollPane();
		if (scrollPane == null) {
			return new Insets(0, 0, 0, 0);
		} else {
			return getScrollPane().getBorder().getBorderInsets(scrollPane);
		}
	}

	private JScrollPane getScrollPane() {
		if (desktop.getParent() instanceof JViewport) {
			final JViewport viewPort = (JViewport) desktop.getParent();
			if (viewPort.getParent() instanceof JScrollPane) {
				return (JScrollPane) viewPort.getParent();
			}
		}
		return null;
	}

	protected void resizeDesktop() {
		int x = 0;
		int y = 0;
		final JScrollPane scrollPane = getScrollPane();
		final Insets scrollInsets = getScrollPaneInsets();

		if (scrollPane != null) {
			final JInternalFrame allFrames[] = desktop.getAllFrames();
			for (final JInternalFrame allFrame : allFrames) {
				if (allFrame.getX() + allFrame.getWidth() > x) {
					x = allFrame.getX() + allFrame.getWidth();
				}
				if (allFrame.getY() + allFrame.getHeight() > y) {
					y = allFrame.getY() + allFrame.getHeight();
				}
			}
			final Dimension d = scrollPane.getVisibleRect().getSize();
			if (scrollPane.getBorder() != null) {
				d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top
						- scrollInsets.bottom);
			}

			if (x <= d.getWidth()) {
				x = ((int) d.getWidth()) - 20;
			}
			if (y <= d.getHeight()) {
				y = ((int) d.getHeight()) - 20;
			}
			desktop.setAllSize(x, y);
			scrollPane.invalidate();
			scrollPane.validate();
		}
	}
}