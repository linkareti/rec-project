/*
 * CCSensor.java
 *
 * Created on 03 February 2004, 19:26
 */

package pt.utl.ist.elab.client.serial.stamp.condensadorCilindrico;

/**
 *
 * @author  Andr�
 */

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.config.*;

public class CCSensor extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay, com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    private BufferedImage imgTube1 = new BufferedImage(290, 30, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage imgPiston = new BufferedImage(50, 28, BufferedImage.TYPE_INT_ARGB);
    private Icon icon=new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
        
    private double freq = 500;
    private double SOUND_VEL = 345;
    
    private java.text.DecimalFormat df = new java.text.DecimalFormat();
    
    /** Creates new form CCSensor */
    public CCSensor() 
    {
        df.setMaximumFractionDigits(1);
        initComponents();
	setPreferredSize(new Dimension(imgTube1.getWidth()+2*25,imgTube1.getHeight()+2*100));
	setMinimumSize(getPreferredSize());
	setMaximumSize(getPreferredSize());
	
	Graphics2D g2D=(Graphics2D)imgTube1.getGraphics();
	g2D.setColor(new java.awt.Color(51, 51, 255));
        g2D.setStroke(new BasicStroke(1.5f));
	g2D.drawRect(0,0,imgTube1.getWidth()-1,imgTube1.getHeight()-1);
        
        g2D=(Graphics2D)imgPiston.getGraphics();
        g2D.setStroke(new BasicStroke(1.5f));
	g2D.setColor(Color.red);
        g2D.drawLine(0, imgPiston.getHeight()/2, imgPiston.getWidth(), imgPiston.getHeight()/2);
	g2D.drawLine(imgPiston.getWidth()-1, 0, imgPiston.getWidth()-1, imgPiston.getHeight());
        
        //g2D=(Graphics2D)imgSin.getGraphics();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 255));
        setForeground(new java.awt.Color(51, 51, 255));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    
    private int cpos = 0;
    double L = 1450;
    private boolean expStarted = false;
    
    private void setCPos(int cpos)
    {
        if(cpos>1450)
        {
            this.L = 1450;
        }       
        else
        {
            this.L = cpos;
        }
        
        this.cpos = Math.abs(cpos - 1480)/10;
        repaint();
    }            
    
    private boolean tubeOpened = false;
    
    public void paint(Graphics g)
    {
	super.paint(g);
	
	Graphics2D g2D=(Graphics2D)g;
	
	int imgWidth = (int)getBounds().getWidth();
	int imgHeight = (int)getBounds().getHeight();
	int x_start = imgWidth / 2 - imgTube1.getWidth() / 2;
	int y_start = imgHeight / 2 - imgTube1.getHeight() / 2;
        
        int x2_start = x_start - imgPiston.getWidth() - 6 + cpos * 2;
	int y2_start = imgHeight / 2 - imgTube1.getHeight() / 2 + 2;
	        
        if(!expStarted)
        {
            return;
        }
        
        int cY = 0;
        
        Color yel = Color.yellow;
        
        Color bgColor = new Color(yel.getRed(), yel.getGreen(), yel.getBlue(), 80);               
        
        g2D.setColor(bgColor);     
        
        
        if((x2_start + imgPiston.getWidth()) < x_start)
        {
            tubeOpened = true;
            g2D.fillRect(x_start, y_start, imgTube1.getWidth(), imgTube1.getHeight());            
        }
        else
        {
            int length = x_start + imgTube1.getWidth() - (x2_start + imgPiston.getWidth());       
            g2D.fillRect(x2_start + imgPiston.getWidth(), y_start, length, imgTube1.getHeight());            
            tubeOpened = false;
        }        

        
        g2D.setStroke(new BasicStroke(1.2f));
        
        int oldX = x_start + imgTube1.getWidth() - 1;
        int oldY1 = y_start + imgTube1.getHeight();
        int oldY2 = y_start;
    
        double division = 0;
        int alpha = 0;
        
        if(!tubeOpened)
        {
            division = (2 * freq * L/1000) / SOUND_VEL;             
            alpha = (int)((0.5-Math.abs(division - (int)Math.round(division)))*255);
        }
        else
        {
            division = (4 * freq * L/1000) / SOUND_VEL;             
            if((int)(Math.round(division))%2==0 && (int)(Math.round(division))!=1)
            {
                alpha = 0;
            }
            else
            {
                alpha = (int)((0.5-Math.abs(division - (int)Math.round(division)))*255);
            }
        }       
        
        g2D.setColor(new Color(yel.getRed(), yel.getGreen(), yel.getBlue(), alpha));        
                                
        for(int i=oldX; i > x2_start + imgPiston.getWidth(); i--)
        {
            int cY1 = 0;
            int cY2 = 0;
            
            if(i < x_start)
            {
                break;
            }
            
            /**Cos(2 * Pi * x/lambda) => Cos(2 * Pi * x * f/vel_som)*/ 
            cY1 =  (int)(imgTube1.getHeight()/2 * Math.cos(freq/(SOUND_VEL*200)*Math.abs(x_start + imgTube1.getWidth() - i) * 2 * Math.PI));                                    
            cY2 =  (int)(imgTube1.getHeight()/2 * Math.cos(Math.PI + freq/(SOUND_VEL*200)*Math.abs(x_start + imgTube1.getWidth() - i)*2*Math.PI));                        

            cY1 += y_start + imgTube1.getHeight()/2;            
            cY2 += y_start + imgTube1.getHeight()/2;            
            
            g2D.drawLine(oldX, oldY1, i, cY1);
            g2D.drawLine(oldX, oldY2, i, cY2);
            oldX = i;
            oldY1 = cY1;                   
            oldY2 = cY2;                   
        }        
        
	g2D.drawImage(imgTube1, x_start, y_start, imgTube1.getWidth(), imgTube1.getHeight(),null);
        g2D.drawImage(imgPiston, x2_start, y2_start, imgPiston.getWidth(), imgPiston.getHeight(),null);	        
        
        g2D.setColor(Color.red);
        g2D.drawString("Frequ�ncia(Hz) = " + df.format(freq), x_start, y2_start + imgTube1.getHeight() + 20);
        g2D.drawString("Posi��o(mm) = " + df.format(L), x_start, y2_start + imgTube1.getHeight() + 40);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
                    
    
    public static void main(String args[])
    {        
        CCSensor sss = new CCSensor();
        sss.expStarted= true;
        javax.swing.JFrame jf = new javax.swing.JFrame();
        jf.getContentPane().add(sss);
        jf.pack();
        jf.show();
    
        
        /*sss.setCPos(1300);
        sss.freq = 533;
        sss.repaint();
        
        for(int i=1450; i>1260; i--)
        {
            try
            {
                Thread.currentThread().sleep(50);
            }
            catch(InterruptedException ie){
            ie.printStackTrace();
            }           
            
            sss.setCPos(i);
            sss.repaint();
        }
        */
        sss.setCPos(1300);

        for(int i=50; i<2000; i+=20)
        {
            try
            {
                Thread.currentThread().sleep(500);
            }
            catch(InterruptedException ie){
            ie.printStackTrace();
            }           
            
            sss.freq=i;
            sss.repaint();
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
	expStarted=true;
	
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
                setCPos(model.getValueAt(i, 0).getValueNumber().intValue());
            }
            if(model.getValueAt(i,1)!=null)
            {
                this.freq = model.getValueAt(i,1).getValueNumber().doubleValue();
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
