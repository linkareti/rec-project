/*
 * DefaultTableModelProxy.java
 *
 * Created on 7 de Maio de 2003, 17:17
 */

package pt.utl.ist.elab.client.webrobot.displays.proxys;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
/**
 *
 * @author  Andr�
 */
public class DigitalTableModelProxy extends javax.swing.table.DefaultTableModel implements ExpDataModelListener
{
	/** Holds value of property expDataModel. */
	private ExpDataModel expDataModel=null;	
        private int numChannels=8;
        private java.text.DecimalFormat df=new java.text.DecimalFormat("###0.0");
        DateTime firstSampleTime=new DateTime();
	
	//BIG SILENT NOOP
	public void dataModelRunning()
	{
	    fireTableStructureChanged();
	}

	//BIG SILENT NOOP
	public void dataModelStoped()
	{
	    //fireTableDataChanged();
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
            return numChannels+1;
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
            String[] colNames={"Tempo (s)","b0","b1","b2","b3","b4","b5","b6","b7"};
            return colNames[columnIndex];
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

            if(columnIndex==0) 
            {
                if(rowIndex==0)
                {
                    firstSampleTime=expDataModel.getTimeStamp(rowIndex);
                }
                double elapsedMilis=(double)(expDataModel.getTimeStamp(rowIndex).getMilliSeconds()-firstSampleTime.getMilliSeconds())/1000;        
                return df.format(elapsedMilis);
            }
            else 
            {
                PhysicsValue value=expDataModel.getValueAt(rowIndex,columnIndex-1+8);
                return new Integer(convertBooleanInt(value.getValue().isBooleanValue()));
            }
	}

        private int convertBooleanInt(boolean toConvert)
        {
            if(toConvert)
            {
                return 1;
            }
            else
            {
                return 0;
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

	private int lastnewsamples=0;
	public void newSamples(NewExpDataEvent evt)
	{
	    fireTableRowsInserted(Math.min(evt.getSamplesStartIndex(),lastnewsamples),evt.getSamplesEndIndex());
	    lastnewsamples=evt.getSamplesEndIndex();
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
                {
                    expDataModel.removeExpDataModelListener(this);
                }

		this.expDataModel = expDataModel;

		if(this.expDataModel!=null)
		{
                    this.expDataModel.addExpDataModelListener(this);
                    fireTableStructureChanged();
                    fireTableDataChanged();
		}
	}

	public void headerAvailable(HardwareAcquisitionConfig header)
	{
            fireTableStructureChanged();
	}	
        
        public void dataModelWaiting()
        {
        }
        
        public void dataModelStarted()
        {
            headerAvailable(expDataModel.getAcquisitionConfig());
        }
        
        public void dataModelStartedNoData()
        {
        }
        
        public void dataModelEnded()
        {
        }
        
        public void dataModelError()
        {
        }
        
}
