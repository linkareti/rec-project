package com.linkare.rec.impl.baseUI.mdi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Menu component that handles the functionality expected of a standard
 * "Windows" menu for MDI applications.
 */
public class WindowMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3632140703550353877L;
	private MDIDesktopPane desktop;
	private final JMenuItem cascade = new JMenuItem("Cascade");
	private final JMenuItem tile = new JMenuItem("Tile");

	public WindowMenu() {
		setText("Window");
		setMnemonic('W');
	}

	public void setMDIDesktopPane(final MDIDesktopPane desktop) {
		this.desktop = desktop;
		cascade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				WindowMenu.this.desktop.cascadeFrames();
			}
		});
		tile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				WindowMenu.this.desktop.tileFrames();
			}
		});
		addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(final MenuEvent e) {
			}

			@Override
			public void menuDeselected(final MenuEvent e) {
				removeAll();
			}

			@Override
			public void menuSelected(final MenuEvent e) {
				buildChildMenus();
			}
		});
	}

	public MDIDesktopPane getMDIDesktopPane() {
		return desktop;
	}

	/* Sets up the children menus depending on the current desktop state */
	private void buildChildMenus() {
		int i;
		ChildMenuItem menu;
		final JInternalFrame[] array = desktop.getAllFrames();

		add(cascade);
		add(tile);
		if (array.length > 0) {
			addSeparator();
		}
		cascade.setEnabled(array.length > 0);
		tile.setEnabled(array.length > 0);

		for (i = 0; i < array.length; i++) {
			menu = new ChildMenuItem(array[i]);
			menu.setState(i == 0);
			menu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent ae) {
					final JInternalFrame frame = ((ChildMenuItem) ae.getSource()).getFrame();
					frame.moveToFront();
					try {
						frame.setSelected(true);
					} catch (final PropertyVetoException e) {
						e.printStackTrace();
					}
				}
			});
			menu.setIcon(array[i].getFrameIcon());
			add(menu);
		}
	}

	/*
	 * This JCheckBoxMenuItem descendant is used to track the child frame that
	 * corresponds to a give menu.
	 */
	class ChildMenuItem extends JCheckBoxMenuItem {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2464339688464806598L;
		private final JInternalFrame frame;

		public ChildMenuItem(final JInternalFrame frame) {
			super(frame.getTitle());
			this.frame = frame;
		}

		public JInternalFrame getFrame() {
			return frame;
		}
	}
}