/*
 * STDMAPImage.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.virtual.client.stdmap.displays;

/*
 * @author  nomead
 */
import java.awt.image.BufferedImage;
import pt.utl.ist.elab.virtual.utils.ByteUtil;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class STDMAPImage extends MAPanel implements ExpDataDisplay, ExpDataModelListener {
    
    /** Creates a new instance of Animation */
    public STDMAPImage() {
        super("Theta", "I", java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.title.stdmap"), 0, 2*Math.PI, 0, 2*Math.PI);
        //this.setPreferredSize(new java.awt.Dimension(500,500));
    }
    
    // TESTE - FORA DO CONTEXTO DO e-Lab
    public void start(){
        int nTheta = 5, nI = 25;
        
        this.nn = nTheta*nI;
        this.iter = 20000;
        
        int w = getWidth()-leftGutter-rightGutter;
        int h = getHeight()-topGutter-bottomGutter;
        
        
        //config(false, (short)1, w, h, nTheta*nI, iter);
        mapImg = new BufferedImage(getWidth()-leftGutter-rightGutter, getHeight()-topGutter-bottomGutter,BufferedImage.TYPE_INT_RGB);
        
        
        //Map Non-Static
        staticImg = true;
        pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer std = new pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null, 1.1f, 0, nTheta, 2.1f, 0, nI, .21f, iter, (float)Math.PI, w, h, pixSize, staticImg);
        popupmenu.getComponent(2).setEnabled(false);
        popupmenu.getComponent(3).setEnabled(false);
        makeImage(std.getMapaPixs());
        statusStr = "";
        repaint();
        
        //Map Anima
        /*g = mapImg.getGraphics();
        this.pcor = Math.PI;
        
        float theta = 0.5f;
        float thetaDot = 1;
        
        float c1 = (float)Math.abs((theta+thetaDot)%1);
        float c2 = (float)Math.abs((thetaDot+pcor*Math.sin(theta))%1);
        float c3 = (c1+c2)%1;
        
        g.setColor(new java.awt.Color(c1,c2,c3));
        
        this.nn = 1;
        this.iter = 10000;
        
        mData = new float[10000*2];
        new pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null,(float) 1, theta, thetaDot, 1, -.5f, 1000,10000).startM(this);
        
        statusStr = "";
        repaint();*/
        
        //Map Non-Static
        /*mData = new float[nn*iter*2];
        staticImg = false;
        this.pcor = Math.PI;
        pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer std = new pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null, 1.1f, 0, nTheta, 2.1f, 0, nI, .21f, iter, .2f, w, h, pixSize, staticImg);
         
        mData = ByteUtil.byteArrayToFloatArray(std.getMapaData());
        statusStr = "";
        updateImage();*/
         
        
    }
    
    public static void main(String args[]) {
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        STDMAPImage stdim = new STDMAPImage();
        test.getContentPane().add(stdim);
        test.pack();
        test.show();
        
        stdim.start();
    }
    
    //Chegaram novas amostras!
    public void newSamples(NewExpDataEvent evt) {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            //sample, canal
            
            if(model.getValueAt(i, model.getChannelIndex("imgPixs")) != null){ //static
                makeImage(model.getValueAt(i, model.getChannelIndex("imgPixs")).getValue().getByteArrayValue().getData());
                repaint();
            }
            else if (model.getValueAt(i, model.getChannelIndex("mData")) != null){ //non-static
                mData = ByteUtil.byteArrayToFloatArray(model.getValueAt(i, model.getChannelIndex("mData")).getValue().getByteArrayValue().getData());
                updateImage();
            }
            else if (model.getValueAt(i,0) != null && model.getValueAt(i,1) != null){ //Anima
                double tempTheta = model.getValueAt(i,0).getValue().getFloatValue();
                double tempThetaDot = model.getValueAt(i,1).getValue().getFloatValue();
                
                if (tempTheta  < 0)
                    tempTheta = Math.abs(tempTheta + 2*Math.PI);
                if (tempThetaDot < 0)
                    tempThetaDot = Math.abs(tempThetaDot + 2*Math.PI);
                
                tempTheta = tempTheta%(2*Math.PI);
                tempThetaDot = tempThetaDot%(2*Math.PI);
                
                g.drawOval((int) Math.round(tempTheta*mapImg.getWidth()/(2*Math.PI)), mapImg.getHeight()-(int) Math.round(tempThetaDot*mapImg.getHeight()/(2*Math.PI)), pixSize,pixSize);

                if (!staticImg){
                    mData[dataCounter++] = (float)tempTheta;
                    mData[dataCounter++] = (float)tempThetaDot;
                }
                repaint();
            }
        }
    }
    
    //Queremos fazer alguma coisa quandos os dados acabarem?
    public void dataModelEnded() {
    }
    
    //Queremos fazer alguma coisa quandos acontecer um erro?
    public void dataModelError() {
    }
    
    //Queremos fazer alguma coisa quando for dado o start e existirem dados?
    public void dataModelStarted() {
        statusStr = "";
        repaint();
    }
    
    //Queremos fazer alguma coisa quando for dado o start e ainda não existirem dados?
    //Eu garanto que quando chegamos a este estado, já existe o header da experiência!
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        byte simulType = Byte.parseByte(header.getSelectedHardwareParameterValue("simulType"));
        this.pcor = Float.parseFloat(header.getSelectedHardwareParameterValue("pcor"));
        
        if (simulType == 2){ //anima
            this.setYLabel("dTheta/dt");
            mapImg = new BufferedImage(getWidth()-leftGutter-rightGutter, getHeight()-topGutter-bottomGutter,BufferedImage.TYPE_INT_RGB);
            g = mapImg.getGraphics();
            
            float theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
            float thetaDot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetaDot"));
            
            float c1 = (float)Math.abs((theta+thetaDot)%1);
            float c2 = (float)Math.abs((thetaDot+pcor*Math.sin(theta))%1);
            float c3 = (c1+c2)%1;
            
            g.setColor(new java.awt.Color(c1,c2,c3));
        }
        else
            mapImg = new BufferedImage((int)Float.parseFloat(header.getSelectedHardwareParameterValue("w")), (int)Float.parseFloat(header.getSelectedHardwareParameterValue("h")),BufferedImage.TYPE_INT_RGB);

        this.staticImg = header.getSelectedHardwareParameterValue("staticImg").trim().equals("1")?true:false;
        this.pixSize = Byte.parseByte(header.getSelectedHardwareParameterValue("pixSize"));
        
        if (staticImg){
            popupmenu.getComponent(2).setEnabled(false);
            popupmenu.getComponent(3).setEnabled(false);
        }
        else {
            if (simulType == 3){
                this.nn = 1;
                this.iter = header.getTotalSamples();
            }
            else {
                this.nn = (int)Float.parseFloat(header.getSelectedHardwareParameterValue("nTheta"))*(int)Float.parseFloat(header.getSelectedHardwareParameterValue("nIMapa"));
                this.iter = (int)Float.parseFloat(header.getSelectedHardwareParameterValue("iter"));
            }
            mData = new float[nn*iter*2];
        }
        
        setZoom(!staticImg);
    }
    
    //Queremos fazer alguma coisa quando for dado parado?
    public void dataModelStoped() {
        statusStr = "";
        repaint();
    }
    
    //Queremos fazer alguma coisa em estado de espera?
    public void dataModelWaiting() {
    }
    
    public javax.swing.JComponent getDisplay() {
        return this;
    }
    
    //O icon associado a este painel!
    public javax.swing.Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar() {
        return null;
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
    
}