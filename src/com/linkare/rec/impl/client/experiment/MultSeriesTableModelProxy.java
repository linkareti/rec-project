/*
 * DefaultTableModelProxy.java
 *
 * Created on 7 de Maio de 2003, 17:17
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import javax.swing.event.TableModelListener;
/**
 *
 * @author  Andr�
 */
public class MultSeriesTableModelProxy extends javax.swing.table.DefaultTableModel implements ExpDataModelListener
{
    /** Holds value of property expDataModel. */
    private ExpDataModel expDataModel=null;
    private java.text.DecimalFormat df=new java.text.DecimalFormat("###0.0");
    DateTime firstSampleTime=new DateTime();
    
    private int[] colArray;
    
    public void dataModelStoped()
    {
        //BIG SILENT NOOP
    }
    
    public void dataModelEnded()
    {
        //BIG SILENT NOOP
    }
    
    public void dataModelError()
    {
        //BIG SILENT NOOP
    }
    
    public void dataModelStarted()
    {
        fireTableStructureChanged();
        fireTableDataChanged();
    }
    
    public void dataModelStartedNoData()
    {
        fireTableStructureChanged();
        fireTableDataChanged();
    }
    
    public void dataModelWaiting()
    {
        //BIG SILENT NOOP
    }
    
    /** Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     */
    public Class getColumnClass(int columnIndex)
    {
        if(columnIndex>0)
        {
            return Integer.class;
        }
        else
        {
            return String.class;
        }
    }
    
    /** Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount()
    {
        if(colArray==null)
        {
            return 1;
        }
        return colArray.length * 2;
    }
    
    /** Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column
     */
    public String getColumnName(int columnIndex)
    {
        if(expDataModel==null || !expDataModel.isDataAvailable())
        {
            if(columnIndex==0)
            {
                return ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.nodata", "No data available...");
            }
            else
            {
                return null;
            }
        }
        
        if(columnIndex % 2 == 0)
        {
            Scale scale = expDataModel.getChannelConfig(getColAtArray(columnIndex/2)).getSelectedScale();
            return expDataModel.getChannelConfig(getColAtArray(columnIndex/2)).getChannelName()+"[" + scale.getMultiplier().toString() + scale.getPhysicsUnitSymbol() + "]";
        }
        else
        {
            if((columnIndex/2)>=0 && expDataModel.getChannelConfig(getColAtArray(columnIndex/2))!=null)
            {
                return "\u03B5 " + expDataModel.getChannelConfig(getColAtArray(columnIndex/2)).getChannelName();
            }
        }
        
        return null;
    }
    
    /** Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    
    public int getRowCount()
    {
        if(expDataModel==null || !expDataModel.isDataAvailable())
        {
            return 0;
        }
        if(expDataModel.getTotalSamples()==-1)
        {
            return 0;
        }
        //return lastnewsamples;
        return expDataModel.getTotalSamples();
    }
    
    /** Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(expDataModel==null || !expDataModel.isDataAvailable())
        {
            return null;
        }
        
        PhysicsValue value = expDataModel.getValueAt(rowIndex,getColAtArray(columnIndex/2));
        if(value == null)
        {
            //removeRow(rowIndex);
            return null;
        }
        
        if(columnIndex % 2 == 0)
        {
            return value.getValue().toString();
        }
        else
        {
            return value.getError().toString();
        }
    }
    
    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     */
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
    
    private int lastnewsamples = 0;
    public void newSamples(NewExpDataEvent evt)
    {
        fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(),lastnewsamples),evt.getSamplesEndIndex());
        lastnewsamples = evt.getSamplesEndIndex();
        
        /*for(int i=evt.getSamplesStartIndex(); i<evt.getSamplesEndIndex(); i++)
        {
            for(int j=0; j<colArray.length; j++)
            {
                if(expDataModel.getValueAt(i, colArray[j]) != null)
                {
                    fireTableRowsInserted(lastnewsamples, lastnewsamples);
                    lastnewsamples++;
                    break;
                }
            }
        }*/
    }
    
    
    /** Getter for property expDataModel.
     * @return Value of property expDataModel.
     */
    public ExpDataModel getExpDataModel()
    {
        return this.expDataModel;
    }
    
    /** Setter for property expDataModel.
     * @param expDataModel New value of property expDataModel.
     */
    public void setExpDataModel(ExpDataModel expDataModel)
    {
        if(expDataModel!=null)
            expDataModel.removeExpDataModelListener(this);
        
        this.expDataModel = expDataModel;
        
        if(this.expDataModel!=null)
        {
            this.expDataModel.addExpDataModelListener(this);
            fireTableStructureChanged();
            fireTableDataChanged();
        }
    }
    
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public int getColAtArray(int col)
    {
        return colArray[col];
    }
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public int[] getColArray()
    {
        return colArray;
    }
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public void setColArray(int[] colArray)
    {
        this.colArray = colArray;
    }
    
}
