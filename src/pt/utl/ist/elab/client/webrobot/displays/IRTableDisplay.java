/*
 * IVTableDisplay.java
 *
 * Created on 6 de Junho de 2003, 18:25
 */

package pt.utl.ist.elab.client.webrobot.displays;

import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.data.config.*;
import pt.utl.ist.elab.client.webrobot.utils.*;

/**
 *
 * @author  Andre
 */
public class IRTableDisplay extends javax.swing.JPanel implements ExpDataDisplay
{
    
    /** Creates new form IVTableDisplay */
    public IRTableDisplay() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jFileChooserSave = new javax.swing.JFileChooser();
        iRTableModelProxy = new pt.utl.ist.elab.client.webrobot.displays.proxys.IRTableModelProxy();
        jScrollPaneData = new javax.swing.JScrollPane();
        jTableData = new javax.swing.JTable();
        jToolBarData = new javax.swing.JToolBar();
        jButtonSave = new javax.swing.JButton();
        jButtonDataPrint = new javax.swing.JButton();

        textExtension.setDescription("Ficheiros de texto");
        jFileChooserSave.setAcceptAllFileFilterUsed(false);
        jFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooserSave.setFileFilter(textExtension);
        iRTableModelProxy.addTableModelListener(new javax.swing.event.TableModelListener() {
            public void tableChanged(javax.swing.event.TableModelEvent evt) {
                iRTableModelProxyTableChanged(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        jScrollPaneData.setPreferredSize(new java.awt.Dimension(453, 403));
        jTableData.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        jTableData.setModel(iRTableModelProxy);
        jTableData.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTableData.setPreferredScrollableViewportSize(new java.awt.Dimension(453, 400));
        javax.swing.table.TableColumnModel tableColumnModelData = jTableData.getColumnModel();
        tableColumnModelData.getColumn(0).setMinWidth(70);
        tableColumnModelData.getColumn(0).setMaxWidth(70);
        jScrollPaneData.setViewportView(jTableData);

        add(jScrollPaneData, java.awt.BorderLayout.CENTER);

        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/displays/resources/Save16.gif")));
        jButtonSave.setToolTipText("Guardar dados...");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jToolBarData.add(jButtonSave);

        jButtonDataPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/displays/resources/Print16.gif")));
        jButtonDataPrint.setToolTipText("Imprimir tabela...");
        jButtonDataPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDataPrintActionPerformed(evt);
            }
        });

        jToolBarData.add(jButtonDataPrint);

        add(jToolBarData, java.awt.BorderLayout.NORTH);

    }//GEN-END:initComponents

    private void iRTableModelProxyTableChanged(javax.swing.event.TableModelEvent evt) {//GEN-FIRST:event_iRTableModelProxyTableChanged
        javax.swing.SwingUtilities.invokeLater( new Runnable()
	{
            public void run()
            {
                jTableData.scrollRectToVisible(jTableData.getCellRect(jTableData.getRowCount()+1,0,true));
            }
        });
    }//GEN-LAST:event_iRTableModelProxyTableChanged

    private void jButtonDataPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDataPrintActionPerformed
        printTable();
    }//GEN-LAST:event_jButtonDataPrintActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        int returnValue = jFileChooserSave.showSaveDialog(this);
        String extension=null;
        if(returnValue==jFileChooserSave.APPROVE_OPTION)
        {
            extension = textExtension.getExtension();
        }
        String path = jFileChooserSave.getSelectedFile().getPath();
        if(path.endsWith(".txt"))
        {
            path = path.substring(0,path.length()-4);
        }
        java.io.File saveFile = new java.io.File(path+"."+extension);
        saveTable(saveFile,false);        
    }//GEN-LAST:event_jButtonSaveActionPerformed

    public void saveTable(java.io.File saveFile, boolean append)
    {
        try
        {
            fileWriter = new java.io.FileWriter(saveFile, append);      
            for(int headerCol=0;headerCol<jTableData.getColumnCount();headerCol++)
            {
                fileWriter.write(jTableData.getColumnName(headerCol));
                fileWriter.write(delimiter);
            }
            fileWriter.write(System.getProperty("line.separator"));
            for(int row=0;row<jTableData.getRowCount();row++)
            {
                for(int col=0;col<jTableData.getColumnCount();col++)
                {
                    fileWriter.write(new String().valueOf(jTableData.getValueAt(row,col)));
                    fileWriter.write(delimiter);
                }            
                fileWriter.write(System.getProperty("line.separator"));
            }                
            fileWriter.close();            
        }
        catch(java.io.IOException ioe)
        {
            System.out.println("Erro ao tentar escrever no ficheiro..."+ioe);
        }                
    }
    
    public void printTable()
    {
        new pt.utl.ist.elab.client.webrobot.utils.PrintComponent(jTableData);
    }
        
    public javax.swing.JComponent getDisplay() 
    {
        return this;
    }
    
    public javax.swing.Icon getIcon() 
    {
        return icon;
    }
    
    public String getName()
    {
        return name;
    }    
    
    public void setExpDataModel(ExpDataModel model) 
    {
        iRTableModelProxy.setExpDataModel(model);
    }        
    
    public javax.swing.JMenuBar getMenuBar() 
    {
        return null;
    }

    public javax.swing.JToolBar getToolBar()
    {
        return null;
    }                
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar jToolBarData;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JScrollPane jScrollPaneData;
    private javax.swing.JButton jButtonDataPrint;
    private javax.swing.JFileChooser jFileChooserSave;
    private javax.swing.JTable jTableData;
    private pt.utl.ist.elab.client.webrobot.displays.proxys.IRTableModelProxy iRTableModelProxy;
    // End of variables declaration//GEN-END:variables
    //my variables
    private java.io.FileWriter fileWriter;
    private String delimiter = ",";   
    private javax.swing.ImageIcon icon=new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/displays/resources/JTableIV16.gif"));
    private String name="Sensores Infravermelhos";    
    private ExtensionFilter textExtension = new ExtensionFilter("txt","ext");        
}
