/*
 * DisplayNodeTreeCellRenderer.java
 *
 * Created on 22 de Janeiro de 2004, 1:02
 */

package com.linkare.rec.impl.baseUI.labsTree;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.DisplayNode;
import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DisplayNodeTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3964424867938028448L;
	private final ReCBaseUIConfig recBaseUIConfig = null;
	private final JPanel panel = null;
	private final JCheckBox checkBox = null;
	private final JLabel labelIcon = null;
	private final JLabel label = null;
	private DisplayNode dtn = null;
	private final java.awt.Color unSelectedColor = UIManager.getColor("Tree.textBackground");
	private final java.awt.Color selectedColor = UIManager.getColor("Tree.selectionBackground");

	/** Creates a new instance of DisplayNodeTreeCellRenderer */
	public DisplayNodeTreeCellRenderer() {
		setMinimumSize(new Dimension(18, 18));
	}

	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object valuet, final boolean sel,
			final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, valuet, sel, expanded, leaf, row, hasFocus);

		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) valuet;
		final Object o = node.getUserObject();

		if (o instanceof DisplayNode) {
			dtn = (DisplayNode) o;

			final JPanel panel = new JPanel(new java.awt.BorderLayout());
			panel.setBackground(UIManager.getColor("Tree.textBackground"));

			final JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(UIManager.getColor("Tree.textBackground"));

			if (o instanceof Display) {
				if (dtn.getIcon() != null) {
					final JLabel labelIcon = new JLabel();
					labelIcon.setBackground(UIManager.getColor("Tree.textBackground"));
					panel.add(labelIcon, java.awt.BorderLayout.WEST);
					labelIcon.setIcon(dtn.getIcon());
					labelIcon.setEnabled(dtn.isEnabled());
				}

				if (dtn.getText() != null) {
					final JLabel label = new JLabel();
					if (!dtn.isConnected()) {
						label.setFont(new java.awt.Font("Dialog", 0, 12));
					} else {
						label.setFont(new java.awt.Font("Dialog", 3, 12));
					}

					label.setBackground(UIManager.getColor("Tree.textBackground"));
					label.setText(dtn.getText());
					label.setEnabled(dtn.isEnabled());
					panel.add(label, java.awt.BorderLayout.EAST);
				}

				panel.add(checkBox, java.awt.BorderLayout.CENTER);
				checkBox.setVisible(true);
				checkBox.setSelected(dtn.isSelected());

				final JCheckBox checkBoxf = checkBox;
				dtn.addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener() {
					@Override
					public void propertyChange(final java.beans.PropertyChangeEvent event) {
						if (event.getPropertyName().equals("selected")) {
							if (event.getNewValue().toString().equals("true")) {
								checkBoxf.setSelected(true);
							} else {
								checkBoxf.setSelected(false);
							}
						}
					}
				});
				/*
				 * checkBox.addItemListener(new ItemListener() { public void
				 * itemStateChanged(ItemEvent evt) { if(dtn.isSelected() !=
				 * (evt.getStateChange()==ItemEvent.SELECTED))
				 * dtn.setSelected(evt.getStateChange()==ItemEvent.SELECTED); }
				 * });
				 */
			} else {
				final JLabel label = new JLabel();

				final int minHeight = 0;

				if (dtn.getIcon() != null) {
					label.setBackground(UIManager.getColor("Tree.textBackground"));
					label.setIcon(dtn.getIcon());
				}

				if (dtn.getText() != null) {
					label.setFont(new java.awt.Font("Dialog", 0, 12));
					label.setText(dtn.getText());
					if (!dtn.isConnected()) {
						label.setFont(new java.awt.Font("Dialog", 0, 12));
					} else {
						label.setFont(new java.awt.Font("Dialog", 3, 12));
					}
					label.setEnabled(dtn.isEnabled());
				}
				label.setMinimumSize(new java.awt.Dimension((int) label.getPreferredSize().getWidth(), (int) label
						.getPreferredSize().getHeight() + 2));
				label.setPreferredSize(new java.awt.Dimension((int) label.getPreferredSize().getWidth(), (int) label
						.getPreferredSize().getHeight() + 2));
				panel.add(label, java.awt.BorderLayout.CENTER);
			}
			if (sel) {
				panel.setBackground(selectedColor);
			} else {
				panel.setBackground(unSelectedColor);
			}

			if (dtn.getToolTipText() != null) {
				panel.setToolTipText("<html>" + dtn.getToolTipText() + "</html>");
				ToolTipManager.sharedInstance().registerComponent(panel);
			}

			return panel;
		} else {
			final JLabel labelReC = new JLabel();
			labelReC.setFont(new java.awt.Font("Dialog", 0, 12));
			if (sel) {
				labelReC.setBackground(selectedColor);
			} else {
				labelReC.setBackground(unSelectedColor);
			}
			labelReC.setText("ReC");
			labelReC.setEnabled(true);
			labelReC.setOpaque(true);
			labelReC.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.ReC", new ImageIcon(
					getClass().getResource("/com/linkare/rec/impl/baseUI/resources/ReCIconHand16.gif"))));
			return labelReC;
		}
	}

	private String toolTipText = "";

	@Override
	public void setToolTipText(final String text) {
		toolTipText = text;
	}

	@Override
	public String getToolTipText(final java.awt.event.MouseEvent evt) {
		return toolTipText;
	}
}
