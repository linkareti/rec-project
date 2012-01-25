/*
 * BaseReCEditor.java
 *
 * Created on 3 de Dezembro de 2003, 18:31
 */

package com.linkare.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author jp
 */
public abstract class AbstractSelectionListPropertyEditor extends PropertyEditorSupport {

	/** Creates a new instance of BaseReCEditor */
	public AbstractSelectionListPropertyEditor() {
		super();
	}

	private List<Object> tagsInitStringsValueList = new Vector<Object>();

	public void addTableValue(final Object value, final String tag, final String initString) {
		tagsInitStringsValueList.add((new Object[] { tag, initString, value }));
	}

	public void initTableValues(final Object[] valueList, final String[] tags, final String[] initStrings)
			throws Exception {
		if (valueList != null) {
			if ((tags != null && tags.length != valueList.length)
					|| (initStrings != null && initStrings.length != valueList.length)) {
				throw new Exception("Tags List , InitStrings List and Values List is not of same length...");
			} else if (tags == null) {
				throw new Exception("Value List is not null and Tags List is null");
			} else if (initStrings == null) {
				throw new Exception("Value List is not null and InitStrings List is null");
			}
		} else {
			tagsInitStringsValueList.clear();
			return;
		}
		tagsInitStringsValueList = new Vector<Object>(valueList.length);
		for (int i = 0; i < valueList.length; i++) {
			tagsInitStringsValueList.set(i, (new Object[] { tags[i], initStrings[i], valueList[i] }));
		}

	}

	public String objectToTag(final Object value) {
		try {
			if (tagsInitStringsValueList != null) {
				for (int i = 0; i < tagsInitStringsValueList.size(); i++) {
					final Object[] row = ((Object[]) tagsInitStringsValueList.get(i));
					final Object thisvalue = row[2];
					final String thistag = (String) row[0];
					if (thisvalue != null && thisvalue.equals(value)) {
						return thistag;
					} else if (thisvalue == null && value == null) {
						return thistag;
					}
				}
			}
		} catch (final Exception e) {
			// silent noop... just return null
		}
		return null;
	}

	public Object tagToObject(final String tag) {
		try {
			if (tagsInitStringsValueList != null) {
				for (int i = 0; i < tagsInitStringsValueList.size(); i++) {
					final Object[] row = ((Object[]) tagsInitStringsValueList.get(i));
					final Object thisvalue = row[2];
					final String thistag = (String) row[0];
					if (thistag != null && thistag.equals(tag)) {
						return thisvalue;
					} else if (thistag == null && tag == null) {
						return thisvalue;
					}
				}
			}
		} catch (final Exception e) {
			// silent noop... just return null
		}
		return null;
	}

	public String objectToInitString(final Object value) {
		try {
			if (tagsInitStringsValueList != null) {
				for (int i = 0; i < tagsInitStringsValueList.size(); i++) {
					final Object[] row = ((Object[]) tagsInitStringsValueList.get(i));
					final Object thisvalue = row[2];
					final String thisInitString = (String) row[1];
					if (thisvalue != null && thisvalue.equals(value)) {
						return thisInitString;
					} else if (thisvalue == null && value == null) {
						return thisInitString;
					}
				}
			}
		} catch (final Exception e) {
			// silent noop... just return null
		}
		return null;
	}

	public String getTag() {
		return objectToTag(getValue());
	}

	public String getInitString() {
		return objectToInitString(getValue());
	}

	public void setValueFromTag(final String tag) {
		setValue(tagToObject(tag));
	}

	@Override
	public String[] getTags() {
		final String[] tags = new String[tagsInitStringsValueList.size()];
		for (int i = 0; i < tags.length; i++) {
			tags[i] = (String) ((Object[]) tagsInitStringsValueList.get(i))[0];
		}
		return tags;
	}

	@Override
	public String getJavaInitializationString() {
		return getInitString();
	}

	@Override
	public String getAsText() {
		return getTag();
	}

	@Override
	public boolean supportsCustomEditor() {
		return getCustomEditor() != null;
	}

	public String getTagsStringDesc() {
		final String[] tags = getTags();
		if (tags == null) {
			return "No possible list of values available...";
		} else {
			final StringBuffer retVal = new StringBuffer("[");
			for (int i = 0; i < tags.length - 1; i++) {
				retVal.append(tags[i]).append(",");
			}
			if (tags.length > 0) {
				retVal.append(tags[tags.length - 1]);
			}

			retVal.append("]");

			return retVal.toString();
		}
	}

	public class BaseRecCustomEditor extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8025966984282759586L;

		public BaseRecCustomEditor() {
			super();
			initComponents();
		}

		public void initComponents() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			final String[] tags = getTags();
			if (tags != null) {
				for (final String tag : tags) {
					comboList.addItem(tag);
				}

				comboList.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent evt) {
						if (evt.getStateChange() == ItemEvent.SELECTED) {
							changeSelection((String) evt.getItem());
						}
					}

				});

				comboList.setMaximumSize(comboList.getPreferredSize());
				add(comboList);
			} else {
				textField.setMaximumSize(textField.getPreferredSize());
				textField.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent evt) {
						changeValue(textField.getText());
					}
				});
				add(textField);

			}

			add(new JLabel(""));
		}

		public void changeSelection(final String newTagSelected) {
			if (updating) {
				return;
			}
			setValue(tagToObject(newTagSelected));
		}

		public void changeValue(final String newTag) {
			if (updating) {
				return;
			}
			try {
				setAsText(newTag);
			} catch (final IllegalArgumentException e) {
				JOptionPane
						.showMessageDialog(this, e.getMessage(), "Error setting value!", JOptionPane.WARNING_MESSAGE);
			}

		}

		private final JComboBox comboList = new JComboBox();
		private final JTextField textField = new JTextField();
		private boolean updating = false;

		private void updateValue() {
			updating = true;
			comboList.setSelectedItem(objectToTag(getValue()));
			textField.setText(getAsText());
			updating = false;
		}

	}

	private BaseRecCustomEditor customEditor = null;

	public java.awt.Component getBaseRecCustomEditor() {
		if (customEditor == null) {
			customEditor = new BaseRecCustomEditor();
		}

		customEditor.updateValue();
		return customEditor;
	}
}
