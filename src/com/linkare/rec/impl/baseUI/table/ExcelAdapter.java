package com.linkare.rec.impl.baseUI.table;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;
/**
 * ExcelAdapter enables Copy-Paste Clipboard functionality on JTables.
 * The clipboard data format used by the adapter is compatible with
 * the clipboard format used by Excel. This provides for clipboard
 * interoperability between enabled JTables and Excel.
 */
public class ExcelAdapter implements ActionListener
{
    private String rowstring,value;
    private Clipboard system;
    private StringSelection stsel;
    private JTable jTable1 ;
    /**
     * The Excel Adapter is constructed with a
     * JTable on which it enables Copy-Paste and acts
     * as a Clipboard listener.
     */
    
    public ExcelAdapter(JTable myJTable)
    {
	jTable1 = myJTable;
	system = Toolkit.getDefaultToolkit().getSystemClipboard();
	KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
	jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);

    }
    /**
     * Public Accessor methods for the Table on which this adapter acts.
     */
    public JTable getJTable()
    {return jTable1;}
    public void setJTable(JTable jTable1)
    {this.jTable1=jTable1;}

    
    public void copyToClipBoard()
    {
	jTable1.getTransferHandler().exportToClipboard(jTable1,system,TransferHandler.COPY);
	/*
	StringBuffer sbf=new StringBuffer();
	// Check to ensure we have selected only a contiguous block of
	// cells
	int numcols=jTable1.getSelectedColumnCount();
	int numrows=jTable1.getSelectedRowCount();
	int[] rowsselected=jTable1.getSelectedRows();
	int[] colsselected=jTable1.getSelectedColumns();
	
	if(rowsselected==null || colsselected==null)
	{
	    JOptionPane.showMessageDialog(null, "Invalid Copy Selection","Invalid Copy Selection",JOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	if (!(  (numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0]  &&  numrows==rowsselected.length  )
	&&
	(numcols-1==colsselected[colsselected.length-1]-colsselected[0]  &&  numcols==colsselected.length  )
	)
	)
	{
	    JOptionPane.showMessageDialog(null, "Invalid Copy Selection","Invalid Copy Selection",JOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	for (int j=0;j<numcols;j++)
	{
	    sbf.append(jTable1.getColumnName(colsselected[j]));
	    if (j<numcols-1) sbf.append("\t");
	}
	sbf.append("\n");
	for (int i=0;i<numrows;i++)
	{
	    for (int j=0;j<numcols;j++)
	    {
		sbf.append(jTable1.getValueAt(rowsselected[i],colsselected[j]));
		if (j<numcols-1) sbf.append("\t");
	    }
	    sbf.append("\n");
	}
	stsel  = new StringSelection(sbf.toString());
	system = Toolkit.getDefaultToolkit().getSystemClipboard();
	system.setContents(stsel,stsel);
	*/
    }
    
    /** Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
	if (e.getActionCommand().compareTo("Copy")==0)
	    copyToClipBoard();
    }
    
}

