/*
 * IRChartDisplay.java
 *
 * Created on 6 de Junho de 2003, 19:51
 */

package pt.utl.ist.elab.client.webrobot.displays;

import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.data.config.*;
import pt.utl.ist.elab.client.webrobot.utils.*;
import pt.utl.ist.elab.client.webrobot.displays.proxys.*;

/**
 *
 * @author  Andr�
 */
public class IRChartDisplay extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener
{
    
    /** Creates new form IRChartDisplay */
    public IRChartDisplay() {
        initComponents();
        dataIR = new DefaultChartModelProxy(0,8,false);                
        org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createXYLineChart(name, "Tempo(s)", 
                                                                                        "Valor", dataIR, org.jfree.chart.plot.PlotOrientation.VERTICAL , true, true, false
                                                                                        );
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        add(chartPanel,java.awt.BorderLayout.CENTER);                        
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jToolBarChart = new javax.swing.JToolBar();
        jButtonIR0 = new javax.swing.JButton();
        jButtonIR1 = new javax.swing.JButton();
        jButtonIR2 = new javax.swing.JButton();
        jButtonIR3 = new javax.swing.JButton();
        jButtonIR4 = new javax.swing.JButton();
        jButtonIR5 = new javax.swing.JButton();
        jButtonIR6 = new javax.swing.JButton();
        jButtonIR7 = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jButtonIR0.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR0.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR0.setText("IR0");
        jButtonIR0.setToolTipText("Ver IR0?");
        jButtonIR0.setEnabled(false);
        jButtonIR0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR0MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR0);

        jButtonIR1.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR1.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR1.setText("IR1");
        jButtonIR1.setToolTipText("Ver IR1?");
        jButtonIR1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR1MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR1);

        jButtonIR2.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR2.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR2.setText("IR2");
        jButtonIR2.setToolTipText("Ver IR2?");
        jButtonIR2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR2MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR2);

        jButtonIR3.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR3.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR3.setText("IR3");
        jButtonIR3.setToolTipText("Ver IR3?");
        jButtonIR3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR3MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR3);

        jButtonIR4.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR4.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR4.setText("IR4");
        jButtonIR4.setToolTipText("Ver IR4?");
        jButtonIR4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR4MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR4);

        jButtonIR5.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR5.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR5.setText("IR5");
        jButtonIR5.setToolTipText("Ver IR5?");
        jButtonIR5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR5MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR5);

        jButtonIR6.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR6.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR6.setText("IR6");
        jButtonIR6.setToolTipText("Ver IR6?");
        jButtonIR6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR6MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR6);

        jButtonIR7.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jButtonIR7.setForeground(new java.awt.Color(0, 51, 153));
        jButtonIR7.setText("IR7");
        jButtonIR7.setToolTipText("Ver IR7?");
        jButtonIR7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonIR7MousePressed(evt);
            }
        });

        jToolBarChart.add(jButtonIR7);

        jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/displays/resources/Print16.gif")));
        jButtonPrint.setToolTipText("Imprimir gr\u00e1fico...");
        jButtonPrint.setMaximumSize(new java.awt.Dimension(30, 28));
        jButtonPrint.setMinimumSize(new java.awt.Dimension(30, 28));
        jButtonPrint.setPreferredSize(new java.awt.Dimension(30, 28));
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        jToolBarChart.add(jButtonPrint);

        add(jToolBarChart, java.awt.BorderLayout.NORTH);

    }//GEN-END:initComponents

    private void jButtonIR7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR7MousePressed
        if(jButtonIR7.isEnabled())
        {
            dataIR.setSeriesVisible(true,7);
            jButtonIR7.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,7);
            jButtonIR7.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR7MousePressed

    private void jButtonIR6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR6MousePressed
        if(jButtonIR6.isEnabled())
        {
            dataIR.setSeriesVisible(true,6);
            jButtonIR6.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,6);
            jButtonIR6.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR6MousePressed

    private void jButtonIR5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR5MousePressed
        if(jButtonIR5.isEnabled())
        {
            dataIR.setSeriesVisible(true,5);
            jButtonIR5.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,5);
            jButtonIR5.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR5MousePressed

    private void jButtonIR4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR4MousePressed
        if(jButtonIR4.isEnabled())
        {
            dataIR.setSeriesVisible(true,4);
            jButtonIR4.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,4);
            jButtonIR4.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR4MousePressed

    private void jButtonIR3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR3MousePressed
        if(jButtonIR3.isEnabled())
        {
            dataIR.setSeriesVisible(true,3);
            jButtonIR3.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,3);
            jButtonIR3.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR3MousePressed

    private void jButtonIR2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR2MousePressed
        if(jButtonIR2.isEnabled())
        {
            dataIR.setSeriesVisible(true,2);
            jButtonIR2.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,2);
            jButtonIR2.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR2MousePressed

    private void jButtonIR1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR1MousePressed
        if(jButtonIR1.isEnabled())
        {
            dataIR.setSeriesVisible(true,1);
            jButtonIR1.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,1);
            jButtonIR1.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR1MousePressed

    private void jButtonIR0MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonIR0MousePressed
        if(jButtonIR0.isEnabled())
        {
            dataIR.setSeriesVisible(true,0);
            jButtonIR0.setEnabled(false);            
        }
        else
        {
            dataIR.setSeriesVisible(false,0);
            jButtonIR0.setEnabled(true);            
        }
    }//GEN-LAST:event_jButtonIR0MousePressed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        printchart();
    }//GEN-LAST:event_jButtonPrintActionPerformed
        
    public void printchart()
    {
        new PrintComponent(chartPanel);                       
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
    
    public void setExpDataModel(ExpDataModel expDataModel) 
    {
	if(expDataModel!=null)
	    expDataModel.removeExpDataModelListener(this);
	
	this.expDataModel = expDataModel;
	
	if(this.expDataModel!=null)
	{
	    this.expDataModel.addExpDataModelListener(this);
            dataIR.setExpDataModel(expDataModel);
	}
    } 
    
    public javax.swing.JMenuBar getMenuBar() 
    {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar() 
    {
        return null;
    }
    
    public void dataModelRunning() 
    {
        dataIR.dataModelRunning();
    }
    
    public void dataModelStoped() 
    {
        dataIR.dataModelStoped();
    }
    
    public void headerAvailable(com.linkare.rec.data.config.HardwareAcquisitionConfig hardwareAcquisitionConfig) 
    {
        dataIR.headerAvailable(hardwareAcquisitionConfig);
    }
    
    public void newSamples(com.linkare.rec.impl.client.experiment.NewExpDataEvent newExpDataEvent) 
    {
        dataIR.newSamples(newExpDataEvent);
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIR4;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonIR5;
    private javax.swing.JButton jButtonIR3;
    private javax.swing.JButton jButtonIR7;
    private javax.swing.JButton jButtonIR0;
    private javax.swing.JButton jButtonIR6;
    private javax.swing.JButton jButtonIR1;
    private javax.swing.JButton jButtonIR2;
    private javax.swing.JToolBar jToolBarChart;
    // End of variables declaration//GEN-END:variables
    private ExpDataModel expDataModel;
    private javax.swing.ImageIcon icon=new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/displays/resources/ChartIV16.gif"));
    private String name="Sensores Infravermelhos";       
    private DefaultChartModelProxy dataIR;
    private org.jfree.chart.ChartPanel chartPanel;        
}
