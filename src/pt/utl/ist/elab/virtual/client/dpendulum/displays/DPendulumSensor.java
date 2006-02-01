/*
 * DPendulumSensor.java
 *
 * Created on February 13, 2004, 4:01 PM
 */

package pt.utl.ist.elab.virtual.client.dpendulum.displays;

/**
 *
 * @author  andre
 */

import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.config.*;

import java.awt.*;
import javax.swing.*;

public class DPendulumSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay, com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    private int length1 = 50;
    private int length2 = 150;
    private float mass1 = 0.3f;
    private float mass2 = 0.5f;
    private float theta1 = (float)(2*Math.PI);
    private float theta2 = (float)Math.PI/4;
    
    private boolean started = false;
    private Icon icon = new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));        
    
    /** Creates new form DPendulumSensor */
    public DPendulumSensor() 
    {        
        initComponents();
        setPreferredSize(new Dimension(320, 320));
        setMinimumSize(new Dimension(320, 320));                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        
        setLayout(new java.awt.BorderLayout());
        
    }//GEN-END:initComponents
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(!started)
        {
            return;
        }
        
        Graphics2D g2D = (Graphics2D)g;
        
        int x_start = this.getWidth() / 2;
        
        int y_start = this.getHeight() / 2 - 20;
        
        int x1 = (int)(length1 * Math.cos(theta1));
        int y1 = (int)(length1 * Math.sin(theta1));
        int x2 = (int)(length2 * Math.cos(theta2));
        int y2 = (int)(length2 * Math.sin(theta2));
        
        int d1 = (int)(mass1 * 50);
        int d2 = (int)(mass2 * 50);
        
        g2D.setColor(Color.RED.darker());        
        g2D.drawLine(x_start, y_start, x_start + x1, y_start + y1);

        g2D.drawLine(x_start + x1,  y_start + y1, x_start + x1 + x2, y_start + y1 + y2);        
        
        g2D.setColor(Color.BLUE.darker());        
        g2D.fillOval(x_start + x1 - d1/2, y_start + y1 - d1/2, d1, d1);
        
        g2D.setColor(Color.YELLOW);
        g2D.fillOval(x_start + x1 + x2 - d2/2, y_start + y1 + y2 - d2/2, d2, d2);
    }
    
    private void setLength1(int lenght1)
    {
        this.length1 = length1;
    }

    private void setLength2(int lenght2)
    {
        this.length2 = length2;
    }
    
    private void setMass1(float mass1)
    {
        this.mass1 = mass1;
    }
    
    private void setMass2(float mass2)
    {
        this.mass2 = mass2;
    }    
    
    private void setTheta1(float theta1)
    {
        this.theta1 = (float)(Math.PI/2 - theta1);
    }

    private void setTheta2(float theta2)
    {
        this.theta2 = (float)(Math.PI/2 - theta2);
    }
    
    
    public javax.swing.JComponent getDisplay()
    {
	return this;
    }
    
    public Icon getIcon()
    {
	return icon;
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model)
    {
	if(this.model!=null)
	    this.model.removeExpDataModelListener(this);
	this.model=model;
	if(this.model!=null)
	    this.model.addExpDataModelListener(this);
	
    }    
    
    public void headerAvailable(HardwareAcquisitionConfig header)
    {
        this.header = header;
        if(header != null)
        {
            setLength1(Integer.parseInt(header.getSelectedHardwareParameterValue("P1 length")));
            setLength2(Integer.parseInt(header.getSelectedHardwareParameterValue("P2 length")));
            setMass1(Float.parseFloat(header.getSelectedHardwareParameterValue("P1 Mass"))/1000f);
            setMass2(Float.parseFloat(header.getSelectedHardwareParameterValue("P2 Mass"))/1000f);
        }
    }
    
    private HardwareAcquisitionConfig header=null;
    private boolean acqHeaderInited=false;
    public void newSamples(NewExpDataEvent evt)
    {        
        started = true;
        for(int i = evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
        {
            setTheta1(model.getValueAt(i, 1).getValueNumber().floatValue());
            setTheta2(model.getValueAt(i, 2).getValueNumber().floatValue());
            repaint();
        }
    }
    
    public String getName()
    {
	return "P�ndulo";
    }
    
    public JMenuBar getMenuBar()
    {
	return null;
    }
    
    public JToolBar getToolBar()
    {
	return null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    
    public static void main(String args[])
    {
        javax.swing.JFrame jf = new javax.swing.JFrame();
        DPendulumSensor dps = new DPendulumSensor();
        dps.setTheta1(0);
        dps.setTheta2((float)(4*Math.PI/2));
        jf.getContentPane().add(dps);       
        jf.pack();
        jf.show();
    }
    
    public void dataModelEnded()
    {
    }
    
    public void dataModelError()
    {
    }
    
    public void dataModelStarted()
    {
    }
    
    public void dataModelStartedNoData()
    {
    }
    
    public void dataModelWaiting()
    {
    }
        
    public void dataModelStoped()
    {     
    }    
    
}
