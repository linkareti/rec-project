package com.linkare.customizer;

import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.Customizer;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultPropertySheetCustomizer extends JPanel implements Customizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6296122228455871968L;
	private Object object = null;

	public DefaultPropertySheetCustomizer() {
		super();
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		javax.swing.JScrollPane jScrollPane1;

		propertyObjectTableModel = new com.linkare.customizer.PropertyObjectTableModel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblProperty = new JTable(propertyObjectTableModel);
		final TableColumn columnProperty = tblProperty.getColumnModel().getColumn(1);
		columnProperty.setCellEditor(new PropertyObjectTableCellEditor());
		columnProperty.setCellRenderer(new PropertyObjectTableCellRenderer());
		tblProperty.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(150);
		tblProperty.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(250);

		propertyObjectTableModel.addTableModelListener(new javax.swing.event.TableModelListener() {
			@Override
			public void tableChanged(final javax.swing.event.TableModelEvent evt) {
				propertyObjectTableModelTableChanged(evt);
			}
		});

		setLayout(new java.awt.BorderLayout());

		tblProperty.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblProperty.setRowHeight(20);
		tblProperty.setRowSelectionAllowed(false);
		tblProperty.setSurrendersFocusOnKeystroke(true);
		jScrollPane1.setViewportView(tblProperty);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	private void propertyObjectTableModelTableChanged(final javax.swing.event.TableModelEvent evt)// GEN-FIRST:event_propertyObjectTableModelTableChanged
	{// GEN-HEADEREND:event_propertyObjectTableModelTableChanged

		if (updating) {
			return;
		}

		if (evt.getType() == TableModelEvent.UPDATE) {
			final TableModel model = tblProperty.getModel();
			final int startRow = evt.getFirstRow();
			final int endRow = evt.getLastRow();
			BeanInfo bi = null;
			try {
				bi = Introspector.getBeanInfo(object.getClass());
			} catch (final Exception e) {
				return;
			}

			final Hashtable<String, PropertyDescriptor> pds = createHashPropertyDesc(bi.getPropertyDescriptors());

			for (int i = startRow; i <= endRow; i++) {
				final PropertyObject po = (PropertyObject) model.getValueAt(i, 1);
				final PropertyDescriptor pd = getPropertyDescriptor(pds, po.getName());
				if (pd == null) {
					return;
				}

				Object oldValue = null;
				try {
					oldValue = pd.getReadMethod().invoke(object, (Object[]) null);
				} catch (final Exception e) {
					continue;
				}
				final Object oValue = po.getValue();

				try {
					pd.getWriteMethod().invoke(object, new Object[] { oValue });
				} catch (final Exception e) {
					continue;
				}
				firePropertyChange(pd.getName(), oldValue, oValue);
			}

		}

	}// GEN-LAST:event_propertyObjectTableModelTableChanged

	private Hashtable<String, PropertyDescriptor> createHashPropertyDesc(final PropertyDescriptor[] pds) {
		final Hashtable<String, PropertyDescriptor> retVal = new Hashtable<String, PropertyDescriptor>();
		if (pds != null) {
			for (final PropertyDescriptor pd : pds) {
				retVal.put(pd.getName(), pd);
			}
		}
		return retVal;
	}

	private PropertyDescriptor getPropertyDescriptor(final Hashtable<String, PropertyDescriptor> hash, final String name) {
		PropertyDescriptor pd = null;
		final Object opd = hash.get(name);
		if (opd instanceof PropertyDescriptor) {
			pd = (PropertyDescriptor) opd;
		}

		return pd;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */

	private boolean updating = false;

	@Override
	public void setObject(final Object object) {
		updating = true;
		// Object oldObject=this.object;
		this.object = object;

		((DefaultTableModel) tblProperty.getModel()).setRowCount(0);

		if (object == null) {
			updating = false;
			return;
		} else {
			Beans.setDesignTime(true);
			Beans.setGuiAvailable(false);
			BeanInfo bi = null;
			try {
				bi = Introspector.getBeanInfo(object.getClass());
			} catch (final Exception e) {
				updating = false;
				return;
			}

			final PropertyDescriptor[] pds = bi.getPropertyDescriptors();

			for (final PropertyDescriptor desc : pds) {
				if (desc.getReadMethod() != null && desc.getWriteMethod() != null) {
					final String displayName = desc.getDisplayName();
					PropertyEditor editor = null;
					try {
						editor = (PropertyEditor) desc.getPropertyEditorClass().newInstance();
					} catch (final Exception e) {
						editor = PropertyEditorManager.findEditor(desc.getPropertyType());
					}

					final boolean editable = desc.getWriteMethod() != null ? true : false;
					Object oValue = null;
					try {
						oValue = desc.getReadMethod().invoke(object, (Object[]) null);
					} catch (final Exception e) {
						// noop
					}

					final PropertyObject po = new PropertyObject(desc.getName(), editor, desc.getPropertyType(),
							editable, oValue);

					((DefaultTableModel) tblProperty.getModel()).addRow(new Object[] { displayName, po });
				}

			}
		}
		updating = false;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.customizer.PropertyObjectTableModel propertyObjectTableModel;
	private javax.swing.JTable tblProperty;
	// End of variables declaration//GEN-END:variables

}