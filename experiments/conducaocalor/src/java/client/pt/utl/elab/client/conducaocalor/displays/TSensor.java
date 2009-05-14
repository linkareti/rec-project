/*
 * StatSoundSensor.java
 *
 * Created on 03 February 2004, 19:26
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

/**
 *
 * @author  Andr�
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class TSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay, com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    private BufferedImage imgS1 = new BufferedImage(320, 20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage imgS2 = new BufferedImage(320, 20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage imgS3 = new BufferedImage(320, 20,BufferedImage.TYPE_INT_ARGB);
    
    private final java.awt.Color BRASS_COLOR = new java.awt.Color(153, 102, 0);
    private final java.awt.Color IRON_COLOR = new java.awt.Color(153, 153, 153);
    private final java.awt.Color COPPER_COLOR = new java.awt.Color(204, 153, 0);
    
    private static final int MIN_TEMPERATURE = 20;
    
    private int t11 = MIN_TEMPERATURE;
    private int t12 = MIN_TEMPERATURE;
    private int t13 = MIN_TEMPERATURE;
    private int t21 = MIN_TEMPERATURE;
    private int t22 = MIN_TEMPERATURE;
    private int t23 = MIN_TEMPERATURE;
    private int t31 = MIN_TEMPERATURE;
    private int t32 = MIN_TEMPERATURE;
    private int t33 = MIN_TEMPERATURE;

    
    private Icon icon = new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    
    
    /** Creates new form StatSoundSensor */
    public TSensor() 
    {
        initComponents();
	setPreferredSize(new Dimension(imgS1.getWidth()+2*50, 10 * imgS1.getHeight()+2*25));
	setMinimumSize(getPreferredSize());
	setMaximumSize(getPreferredSize());               

        Graphics2D g2D = (Graphics2D)imgS1.getGraphics();

        GradientPaint gp1 = new GradientPaint(0,0, BRASS_COLOR, imgS1.getWidth(), imgS1.getHeight(), BRASS_COLOR.brighter());
        
        g2D.setPaint(gp1);

        Ellipse2D.Double s11e1 = new Ellipse2D.Double();        
        s11e1.setFrame(0,0,20,imgS1.getHeight());
        Area area11e1 = new Area(s11e1);        
        
        Rectangle2D.Double s11r = new Rectangle2D.Double();
        s11r.setFrame(10,0,imgS1.getWidth()-20,imgS1.getHeight());
        Area area11r1 = new Area(s11r);  
        
        Ellipse2D.Double s11e2 = new Ellipse2D.Double();        
        s11e2.setFrame(imgS1.getWidth()-20,0,20,imgS1.getHeight());        
        Area area11e2 = new Area(s11e2); 
        
        area11e1.add(area11r1);
        area11e1.add(area11e2);
        
        g2D.fill(area11e1);
        g2D.fill(area11r1);
        g2D.fill(area11e2);        

        Graphics2D g2D2 = (Graphics2D)imgS2.getGraphics();

        GradientPaint gp2 = new GradientPaint(0,0, IRON_COLOR, imgS2.getWidth(), imgS2.getHeight(), IRON_COLOR.brighter());
        
        g2D2.setPaint(gp2);

        Ellipse2D.Double s12e1 = new Ellipse2D.Double();        
        s12e1.setFrame(0,0,20,imgS2.getHeight());
        Area area12e1 = new Area(s12e1);        
        
        Rectangle2D.Double s12r = new Rectangle2D.Double();
        s12r.setFrame(10,0,imgS2.getWidth()-20,imgS2.getHeight());
        Area area12r1 = new Area(s12r);  
        
        Ellipse2D.Double s12e2 = new Ellipse2D.Double();        
        s12e2.setFrame(imgS1.getWidth()-20,0,20,imgS1.getHeight());        
        Area area12e2 = new Area(s12e2); 
        
        area12e1.add(area12r1);
        area12e1.add(area12e2);
        
        g2D2.fill(area12e1);
        g2D2.fill(area12r1);
        g2D2.fill(area12e2);                        
        
        Graphics2D g2D3 = (Graphics2D)imgS3.getGraphics();

        GradientPaint gp3 = new GradientPaint(0,0, COPPER_COLOR, imgS3.getWidth(), imgS3.getHeight(), COPPER_COLOR.brighter());
        
        g2D3.setPaint(gp3);

        Ellipse2D.Double s13e1 = new Ellipse2D.Double();        
        s13e1.setFrame(0,0,20,imgS3.getHeight());
        Area area13e1 = new Area(s13e1);        
        
        Rectangle2D.Double s13r = new Rectangle2D.Double();
        s13r.setFrame(10,0,imgS3.getWidth()-20,imgS3.getHeight());
        Area area13r1 = new Area(s13r);  
        
        Ellipse2D.Double s13e2 = new Ellipse2D.Double();        
        s13e2.setFrame(imgS3.getWidth()-20,0,20,imgS3.getHeight());        
        Area area13e2 = new Area(s13e2); 
        
        area12e1.add(area13r1);
        area12e1.add(area13e2);
        
        g2D3.fill(area13e1);
        g2D3.fill(area13r1);
        g2D3.fill(area13e2);                                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-END:initComponents
    
    public void paint(Graphics g)
    {
	super.paint(g);	
	
	int imgWidth = (int)getBounds().getWidth();
	int imgHeight = (int)getBounds().getHeight();
	int x_start = imgWidth / 2 - imgS1.getWidth()/2;
	int y_start = imgHeight / 2 - 2 * imgS1.getHeight();
        int y_start2 = y_start + 3*imgS1.getHeight()/2;
        int y_start3 = y_start + 3*imgS1.getHeight();
        
        int sensorsL = imgS1.getWidth()/3;
        
        Graphics2D g2D = (Graphics2D)g;
        
	g2D.drawImage(imgS1, x_start, y_start, imgS1.getWidth(), imgS1.getHeight(),null);        
        g2D.drawImage(imgS2, x_start, y_start2, imgS2.getWidth(), imgS2.getHeight(),null);        
        g2D.drawImage(imgS3, x_start, y_start3, imgS2.getWidth(), imgS3.getHeight(),null);        
                        
        /**Sensor 1 bar Brass*/
        Ellipse2D.Double s11e = new Ellipse2D.Double();        
        s11e.setFrame(x_start,y_start,20,imgS1.getHeight());
        Area s11 = new Area(s11e);        
        
        Rectangle2D.Double s11r = new Rectangle2D.Double();
        s11r.setFrame(x_start + 10 , y_start, sensorsL - 10, imgS1.getHeight());
        Area s11a = new Area(s11r);
        
        s11.add(s11a);

        g2D.setColor(new java.awt.Color(255,0,0,(t11-MIN_TEMPERATURE)*120/50));                
        
        /**Sensor 2 bar Brass*/   
        Ellipse2D.Double s12e1 = new Ellipse2D.Double();        
        s12e1.setFrame(x_start + sensorsL - 10 , y_start, 20, imgS1.getHeight());
        Area s12_1 = new Area(s12e1);        
        
        s11.subtract(s12_1);
        g2D.fill(s11);     
        
        Rectangle2D.Double s12r = new Rectangle2D.Double();
        s12r.setFrame(x_start + sensorsL, y_start, sensorsL + 10, imgS1.getHeight());
        Area s12 = new Area(s12r);        
        
        s12.add(s12_1);
        
        g2D.setColor(new java.awt.Color(255,0,0,(t12-MIN_TEMPERATURE)*120/50));        

        /**Sensor 3 bar Brass*/
        Ellipse2D.Double s12e2 = new Ellipse2D.Double();        
        s12e2.setFrame(x_start + 2*sensorsL, y_start, 20, imgS1.getHeight());
        Area s12_2 = new Area(s12e2);        
        
        s12.subtract(s12_2);
        g2D.fill(s12);                
        
        Ellipse2D.Double s13e = new Ellipse2D.Double();        
        s13e.setFrame(x_start + imgS1.getWidth()-20, y_start, 20, imgS1.getHeight());   
        Area s13 = new Area(s13e);        
        
        Rectangle2D.Double s13r = new Rectangle2D.Double();
        s13r.setFrame(x_start + 2 * sensorsL + 10, y_start, sensorsL-20, imgS1.getHeight());       
        Area s13a = new Area(s13r);
        
        s13.add(s13a);
        s13.add(s12_2);
        
        g2D.setColor(new java.awt.Color(255,0,0,(t13-MIN_TEMPERATURE)*120/50));        
        g2D.fill(s13);
        
        g2D.setColor(Color.black);
        g2D.drawOval(x_start + imgS1.getWidth()-20, y_start, 20, imgS1.getHeight());                
             
        g2D.setColor(BRASS_COLOR);
        g2D.drawString("Lat�o", x_start + imgS1.getWidth() + 10, y_start + 15);
        
        /**Sensor 1 bar Iron*/
        Ellipse2D.Double s21e = new Ellipse2D.Double();        
        s21e.setFrame(x_start,y_start2,20,imgS1.getHeight());
        Area s21 = new Area(s21e);        
        
        Rectangle2D.Double s21r = new Rectangle2D.Double();
        s21r.setFrame(x_start + 10 , y_start2, sensorsL - 10, imgS2.getHeight());
        Area s21a = new Area(s21r);
        
        s21.add(s21a);

        g2D.setColor(new java.awt.Color(255,0,0,(t21-MIN_TEMPERATURE)*120/50));        
        
        /**Sensor 2 bar Iron*/        
        Ellipse2D.Double s22e1 = new Ellipse2D.Double();        
        s22e1.setFrame(x_start + sensorsL - 10 , y_start2, 20, imgS2.getHeight());
        Area s22_1 = new Area(s22e1);        
        
        s21.subtract(s22_1);
        g2D.fill(s21);        
        
        Rectangle2D.Double s22r = new Rectangle2D.Double();
        s22r.setFrame(x_start + sensorsL, y_start2, sensorsL + 10, imgS2.getHeight());
        Area s22 = new Area(s22r);        
        
        s22.add(s22_1);
        
        g2D.setColor(new java.awt.Color(255,0,0,(t22-MIN_TEMPERATURE)*120/50));        
        
        /**Sensor 3 bar Iron*/
        Ellipse2D.Double s22e2 = new Ellipse2D.Double();        
        s22e2.setFrame(x_start + 2*sensorsL, y_start2, 20, imgS2.getHeight());
        Area s22_2 = new Area(s22e2);        
        
        s22.subtract(s22_2);
        g2D.fill(s22);        
        
        Ellipse2D.Double s23e = new Ellipse2D.Double();        
        s23e.setFrame(x_start + imgS1.getWidth()-20, y_start2, 20, imgS2.getHeight());   
        Area s23 = new Area(s23e);        
        
        Rectangle2D.Double s23r = new Rectangle2D.Double();
        s23r.setFrame(x_start + 2 * sensorsL + 10, y_start2, sensorsL-20, imgS2.getHeight());       
        Area s23a = new Area(s23r);
        
        s23.add(s23a);
        s23.add(s22_2);

        g2D.setColor(new java.awt.Color(255,0,0,(t23-MIN_TEMPERATURE)*120/50));        
        g2D.fill(s23);
        
        g2D.setColor(Color.black);
        g2D.drawOval(x_start + imgS1.getWidth()-20, y_start2, 20, imgS1.getHeight());        
        
        g2D.setColor(IRON_COLOR);
        g2D.drawString("Ferro", x_start + imgS1.getWidth() + 10, y_start2 + 15);        
        
        /**Sensor 1 bar Copper*/
        Ellipse2D.Double s31e = new Ellipse2D.Double();        
        s31e.setFrame(x_start,y_start3,20,imgS3.getHeight());
        Area s31 = new Area(s31e);        
        
        Rectangle2D.Double s31r = new Rectangle2D.Double();
        s31r.setFrame(x_start + 10 , y_start3, sensorsL - 10, imgS3.getHeight());
        Area s31a = new Area(s31r);
        
        s31.add(s31a);

        g2D.setColor(new java.awt.Color(255,0,0,(t31-MIN_TEMPERATURE)*120/50));        
        
        /**Sensor 2 bar Copper*/
        Ellipse2D.Double s32e1 = new Ellipse2D.Double();        
        s32e1.setFrame(x_start + sensorsL - 10 , y_start3, 20, imgS3.getHeight());
        Area s32_1 = new Area(s32e1);        
        
        s31.subtract(s32_1);
        g2D.fill(s31);
        
        Rectangle2D.Double s32r = new Rectangle2D.Double();
        s32r.setFrame(x_start + sensorsL, y_start3, sensorsL + 10, imgS3.getHeight());
        Area s32 = new Area(s32r);        
        
        s32.add(s32_1);
        
        g2D.setColor(new java.awt.Color(255,0,0,(t32-MIN_TEMPERATURE)*120/50));        

        /**Sensor 3 bar Copper*/
        Ellipse2D.Double s32e2 = new Ellipse2D.Double();        
        s32e2.setFrame(x_start + 2*sensorsL, y_start3, 20, imgS3.getHeight());
        Area s32_2 = new Area(s32e2);        
        
        s32.subtract(s32_2);
        g2D.fill(s32);
        
        Ellipse2D.Double s33e = new Ellipse2D.Double();        
        s33e.setFrame(x_start + imgS1.getWidth()-20, y_start3, 20, imgS3.getHeight());   
        Area s33 = new Area(s33e);        
        
        Rectangle2D.Double s33r = new Rectangle2D.Double();
        s33r.setFrame(x_start + 2 * sensorsL + 10, y_start3, sensorsL-20, imgS3.getHeight());       
        Area s33a = new Area(s33r);
        
        s33.add(s33a);
        s33.add(s32_2);
        
        g2D.setColor(new java.awt.Color(255,0,0,(t33-MIN_TEMPERATURE)*120/50));        
        g2D.fill(s33);
        
        g2D.setColor(Color.black);
        g2D.drawOval(x_start + imgS1.getWidth()-20, y_start3, 20, imgS1.getHeight());                
        
        g2D.setColor(COPPER_COLOR);
        g2D.drawString("Cobre", x_start + imgS1.getWidth() + 10, y_start3 + 15);                
        
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public static void main(String args[])
    {
        TSensor ts = new TSensor();
        javax.swing.JFrame jf = new javax.swing.JFrame();
        jf.getContentPane().add(ts);
        jf.pack();
        jf.show();
       for(int i=25; i<75; i++)
        {
            try
            {
                Thread.currentThread().sleep(250);
                //ts.t11=i;
                ts.t12=i+5;
                //ts.t13=i+10;
                ts.t21=i;
                ts.t22=i*2;
                ts.t23=i;
                //ts.t31=i;
                ts.t32=i+5;
                //ts.t33=i+10;                
                ts.repaint();
            }
            catch(InterruptedException ie){}            
        }
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
    
    public void dataModelRunning()
    {
    }
    
    public void dataModelStoped()
    {
    }
    
    public void headerAvailable(HardwareAcquisitionConfig header)
    {	
	this.header=header;		
    }
    
    private HardwareAcquisitionConfig header=null;
    private boolean acqHeaderInited=false;
    public void newSamples(NewExpDataEvent evt)
    {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
        {
            if(model.getValueAt(i, 0)!=null)
            {
                t11 = (int)model.getValueAt(i, 0).getValue().getFloatValue();                               
                if(t11<MIN_TEMPERATURE)
                {
                    t11 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 1)!=null)
            {
                t12 = (int)model.getValueAt(i, 1).getValue().getFloatValue();
                if(t12<MIN_TEMPERATURE)
                {
                    t12 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 2)!=null)
            {
                t13 = (int)model.getValueAt(i, 2).getValue().getFloatValue();
                if(t13<MIN_TEMPERATURE)
                {
                    t13 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 3)!=null)
            {
                t21 = (int)model.getValueAt(i, 3).getValue().getFloatValue();
                if(t21<MIN_TEMPERATURE)
                {
                    t21 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 4)!=null)
            {
                t22 = (int)model.getValueAt(i, 4).getValue().getFloatValue();
                if(t22<MIN_TEMPERATURE)
                {
                    t22 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 5)!=null)
            {
                t23 = (int)model.getValueAt(i, 5).getValue().getFloatValue();
                if(t23<MIN_TEMPERATURE)
                {
                    t23 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 6)!=null)
            {
                t31 = (int)model.getValueAt(i, 6).getValue().getFloatValue();
                if(t31<MIN_TEMPERATURE)
                {
                    t31 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 7)!=null)
            {
                t32 = (int)model.getValueAt(i, 7).getValue().getFloatValue();
                if(t32<MIN_TEMPERATURE)
                {
                    t32 = MIN_TEMPERATURE;
                }
            }
            if(model.getValueAt(i, 8)!=null)
            {
                t33 = (int)model.getValueAt(i, 8).getValue().getFloatValue();
                if(t33<MIN_TEMPERATURE)
                {
                    t33 = MIN_TEMPERATURE;
                }
            }            
            repaint();
        }
    }
    
    public String getName()
    {
	return "Sensor";
    }
    
    public JMenuBar getMenuBar()
    {
	return null;
    }
    
    public JToolBar getToolBar()
    {
	return null;
    }
    
    public void dataModelWaiting()
    {
    }
    
    public void dataModelStarted()
    {
        if(model != null)
            headerAvailable(model.getAcquisitionConfig());
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
