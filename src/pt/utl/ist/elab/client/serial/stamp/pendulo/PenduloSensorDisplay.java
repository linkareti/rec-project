/*
 * PenduloSensorDisplay.java
 *
 * Created on 7 de Julho de 2003, 12:13
 */

package pt.utl.ist.elab.client.serial.stamp.pendulo;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class PenduloSensorDisplay extends JPanel implements ExpDataDisplay
{
    
    /** Creates new form PenduloSensorDisplay */
    public PenduloSensorDisplay()
    {
	initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
	jScrollPane1 = new javax.swing.JScrollPane();
	sensorDisplay1 = new com.linkare.rec.impl.baseUI.display.SensorDisplay();
	
	setLayout(new java.awt.BorderLayout());
	
	sensorDisplay1.setBackground(new java.awt.Color(204, 255, 255));
	sensorDisplay1.setForeground(new java.awt.Color(0, 0, 51));
	sensorDisplay1.setToolTipText("Sensor");
	sensorDisplay1.setName("Sensor");
	jScrollPane1.setViewportView(sensorDisplay1);
	
	add(jScrollPane1, java.awt.BorderLayout.CENTER);
	
    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private com.linkare.rec.impl.baseUI.display.SensorDisplay sensorDisplay1;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JComponent getDisplay()
    {
	return this;
    }    
    
    public Icon getIcon()
    {
	return sensorDisplay1.getIcon();
    }
    
    public javax.swing.JMenuBar getMenuBar()
    {
	return sensorDisplay1.getMenuBar();
    }
    
    public javax.swing.JToolBar getToolBar()
    {
	return sensorDisplay1.getToolBar();
    }
    
    public void setExpDataModel(ExpDataModel model)
    {
	sensorDisplay1.setExpDataModel(model);
    }
    
}
