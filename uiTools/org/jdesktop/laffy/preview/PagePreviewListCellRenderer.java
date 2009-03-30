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

package org.jdesktop.laffy.preview;

import org.jdesktop.laffy.Page;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PagePreviewListCellRenderer extends DefaultListCellRenderer {

    public PagePreviewListCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);
        setVerticalTextPosition(BOTTOM);
        setHorizontalTextPosition(CENTER);
        setIconTextGap(0);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
        setFont(getFont().deriveFont(Font.BOLD, 16f));
        setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        setForeground(Color.WHITE);
        if (value instanceof Page) {
            Page page = (Page) value;
            setIcon(page.getPreviewIcon());
            setText(page.getName());
        }

        return this;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(0, 0, getWidth(), getHeight());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
    }
}
