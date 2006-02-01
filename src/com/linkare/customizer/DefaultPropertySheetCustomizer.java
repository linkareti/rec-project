/*
 * AbstractIndexedPropertyCustomEditor.java
 *
 * Created on 14 de Dezembro de 2003, 12:38
 */

package com.linkare.customizer;

import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author  jp
 */
public class DefaultPropertySheetCustomizer extends JPanel implements Customizer
{
    private Object object=null;
    public DefaultPropertySheetCustomizer()
    {
        super();
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        javax.swing.JScrollPane jScrollPane1;

        propertyObjectTableModel = new com.linkare.customizer.PropertyObjectTableModel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProperty = new JTable(propertyObjectTableModel);
        TableColumn columnProperty=tblProperty.getColumnModel().getColumn(1);
        columnProperty.setCellEditor(new PropertyObjectTableCellEditor());
        columnProperty.setCellRenderer(new PropertyObjectTableCellRenderer());
        tblProperty.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(150);
        tblProperty.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(250);

        propertyObjectTableModel.addTableModelListener(new javax.swing.event.TableModelListener() {
            public void tableChanged(javax.swing.event.TableModelEvent evt) {
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

    }//GEN-END:initComponents
    
    private void propertyObjectTableModelTableChanged(javax.swing.event.TableModelEvent evt)//GEN-FIRST:event_propertyObjectTableModelTableChanged
    {//GEN-HEADEREND:event_propertyObjectTableModelTableChanged
        
        if(updating) return;
        
        if(evt.getType()==evt.UPDATE)
        {
            TableModel model=tblProperty.getModel();
            int startRow=evt.getFirstRow();
            int endRow=evt.getLastRow();
            BeanInfo bi=null;
            try
            {
                bi=Introspector.getBeanInfo(object.getClass());
            }
            catch(Exception e)
            {
                return;
            }
            
            Hashtable pds=createHashPropertyDesc(bi.getPropertyDescriptors());
            
            for(int i=startRow;i<=endRow;i++)
            {
                PropertyObject po=(PropertyObject)model.getValueAt(i, 1);
                PropertyDescriptor pd=getPropertyDescriptor(pds, po.getName());
                if(pd==null) return;
                
                Object oldValue=null;
                try
                {
                    oldValue=pd.getReadMethod().invoke(object, (Object)null);
                }
                catch(Exception e)
                {
                    continue;
                }
                Object oValue=po.getValue();
                
                try
                {
                    pd.getWriteMethod().invoke(object, new Object[]{oValue});
                }
                catch(Exception e)
                {
                    continue;
                }
                firePropertyChange(pd.getName(),oldValue,oValue);
            }
            
        }
        
    }//GEN-LAST:event_propertyObjectTableModelTableChanged
    
    private Hashtable createHashPropertyDesc(PropertyDescriptor[] pds)
    {
        Hashtable retVal=new Hashtable();
        if(pds!=null)
        {
            for(int i=0;i<pds.length;i++)
            {
                PropertyDescriptor pd=pds[i];
                retVal.put(pd.getName(), pd);
            }
        }
        return retVal;
    }
    
    private PropertyDescriptor getPropertyDescriptor(Hashtable hash,String name)
    {
        PropertyDescriptor pd=null;
        Object opd=hash.get(name);
        if(opd!=null && opd instanceof PropertyDescriptor)
            pd=(PropertyDescriptor)opd;
        
        return pd;
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     *
     */
    
    private boolean updating=false;
    public void setObject(Object object)
    {
        updating=true;
        Object oldObject=this.object;
        this.object=object;
        
        ((DefaultTableModel)tblProperty.getModel()).setRowCount(0);
        
        if(object==null)
        {
            updating=false;
            return;
        }
        else
        {
            Beans.setDesignTime(true);
            Beans.setGuiAvailable(false);
            BeanInfo bi=null;
            try
            {
                bi=Introspector.getBeanInfo(object.getClass());
            }
            catch(Exception e)
            {
                updating=false;
                return;
            }
            
            PropertyDescriptor[] pds=bi.getPropertyDescriptors();
            
            for(int i=0;i<pds.length;i++)
            {
                PropertyDescriptor desc=pds[i];
                if(desc.getReadMethod()!=null && desc.getWriteMethod()!=null)
                {
                    String displayName=desc.getDisplayName();
                    PropertyEditor editor=null;
                    try
                    {
                        editor=(PropertyEditor)desc.getPropertyEditorClass().newInstance();
                    }
                    catch(Exception e)
                    {
                        editor=PropertyEditorManager.findEditor(desc.getPropertyType());
                    }
                    
                    boolean editable=desc.getWriteMethod()!=null ? true:false;
                    Object oValue=null;
                    try
                    {
                        oValue=desc.getReadMethod().invoke(object, (Object)null);
                    }
                    catch(Exception e)
                    {
                        //noop
                    }
                    
                    PropertyObject po=new PropertyObject(desc.getName(),editor,desc.getPropertyType(),editable,oValue);
                    
                    ((DefaultTableModel)tblProperty.getModel()).addRow(new Object[]
                    {displayName,po});
                }
                
                
            }
        }
        updating=false;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.customizer.PropertyObjectTableModel propertyObjectTableModel;
    private javax.swing.JTable tblProperty;
    // End of variables declaration//GEN-END:variables
    
    
}
