package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusRenderer extends JLabel implements ListCellRenderer {

		
	/**
	 * 
	 * @author jpereira - Linkare TI
	 */
	public class ImageIconCache {
		private ImageIcon imageIcon=null;
	}

	private Map<String, ImageIconCache> apparatusIconMap=new HashMap<String, ImageIconCache>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3255888693182744820L;

	private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 2, 1, 2);

	private Apparatus apparatus;

	public ApparatusRenderer() {
		super();
	//	setOpaque(true);
		setBorder(ApparatusRenderer.NO_FOCUS_BORDER);
	}

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {

		// Value must be an Apparatus
		apparatus = (Apparatus) value;

		if (apparatus != null) {

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			// Font
			setFont(list.getFont());

			ImageIconCache cache=apparatusIconMap.get(apparatus.getIconLocationBundleKey());
			if(cache==null) {
				cache=new ImageIconCache();
				cache.imageIcon=ReCResourceBundle.findImageIconOrDefault(apparatus.getIconLocationBundleKey(), null);
				apparatusIconMap.put(apparatus.getIconLocationBundleKey(),cache);
			}
			// Icon
			setIcon(cache.imageIcon);

			// Text
			String displayStringBundleKey = apparatus.getDisplayStringBundleKey();
			setText(ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));

			// State
			setEnabled(apparatus.isEnabled());
		}

		return this;
	}

}
