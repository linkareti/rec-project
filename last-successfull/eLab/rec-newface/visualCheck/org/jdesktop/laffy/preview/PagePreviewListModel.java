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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.laffy.Page;

/**
 * A simple list model which is based on a List of components. Each item returned from the model is an Item type, which
 * includes a preview image.
 * 
 * @author Richard Bair
 */
public class PagePreviewListModel extends AbstractListModel implements ComboBoxModel {
	private List<Page> pages;
	private JList pagePreviewList;

	public PagePreviewListModel(JList pagePreviewList) {
		this.pagePreviewList = pagePreviewList;
		this.pages = new ArrayList<Page>();
		pagePreviewList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				fireContentsChanged(this, -1, -1);
			}
		});
	}

	// =================================================================================================================
	// ComboBoxModel Methods

	/**
	 * Returns the selected item
	 * 
	 * @return The selected item or <code>null</code> if there is no selection
	 */
	public Object getSelectedItem() {
		return pagePreviewList.getSelectedValue();
	}

	/**
	 * Set the selected item. The implementation of this method should notify all registered
	 * <code>ListDataListener</code>s that the contents have changed.
	 * 
	 * @param anItem
	 *            the list object to select or <code>null</code> to clear the selection
	 */
	public void setSelectedItem(Object anItem) {
		pagePreviewList.setSelectedValue(anItem, true);
		fireContentsChanged(this, -1, -1);
	}

	// =================================================================================================================
	// AbstractListModel Methods

	private PropertyChangeListener previewListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			int index = pages.indexOf(evt.getSource());
			if (index > 0) {
				fireContentsChanged(PagePreviewListModel.this, index, index);
			}
		}
	};

	public void addPage(Page p) {
		p.addPropertyChangeListener("previewIcon", previewListener);
		pages.add(p);
		fireContentsChanged(p, pages.size() - 2, pages.size() - 1);
	}

	public int getSize() {
		return pages.size();
	}

	public Object getElementAt(int index) {
		return pages.get(index);
	}
}
