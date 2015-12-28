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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Represents a page of controls. The page "name" is special, and should be localized as it may appear in descriptive
 * contexts. Each page also contains a "preview" icon.
 * 
 * @author Richard Bair
 */
public class Page extends JPanel {
	/** Icon used when, for some reason, a preview icon could not be generated. */
	private static Icon NO_PREVIEW_ICON;

	/**
	 * The preview icon. Should never be null, as it will either be loading, or in the NO_PREVIEW_ICON state. The
	 * preview is regenerated whenever the component is reparented.
	 */
	private Icon preview;

	private int sectionCount = 0;

	public Page(String name, Section... sections) {
		this(name, null, sections);
	}

	public Page(String name, Icon previewIcon, Section... sections) {
		super(new GridBagLayout());
		setName(name);
		setBackground(Color.WHITE);
		for (Section section : sections) {
			add(section, new GridBagConstraints(0, sectionCount++, 1, 1, 1.0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(10, 10, 0, 10), 0, 0));
		}
		JPanel fill = new JPanel();
		fill.setOpaque(false);
		add(fill, new GridBagConstraints(0, sectionCount, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
				10, 0, 0, 0), 0, 0));
		this.preview = previewIcon == null ? getNoPreviewIcon() : previewIcon;
	}

	public void setPreviewIcon(Icon icon) {
		Icon old = preview;
		preview = icon;
		firePropertyChange("previewIcon", old, preview);
	}

	public Icon getPreviewIcon() {
		return preview;
	}

	private synchronized static Icon getNoPreviewIcon() {
		if (NO_PREVIEW_ICON == null) {
			try {
				NO_PREVIEW_ICON = new ImageIcon(ImageIO.read(Page.class.getResource("icons/nopreview.png")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return NO_PREVIEW_ICON;
	}

	@Override
	public String toString() {
		return getName();
	}
}