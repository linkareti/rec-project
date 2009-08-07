/*
 * Animation.java
 *
 * Created on April 03, 2005, 3:45 PM
 */

package pt.utl.ist.elab.client.vdi.displays;

/**
 *
 * @author  Queiro'
 */

import javax.swing.JPanel;
import org.opensourcephysics.display.*;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.FlowLayout;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener {
    private PlottingPanel plottingPanel1, plottingPanel2, plottingPanelTotal;
    private DrawableShape di1 = null, di1T = null, di1g = null;
    private DrawableShape di2 = null, di2T = null, di2g = null;
    private DrawableShape de1 = null, de1T = null, de1g = null;
    private DrawableShape de2 = null, de2T = null, de2g = null;
    private Dataset plano = null;
    private JPanel panelEsquerda;
    private double distX1, distY1, distX2, distY2;
    
    /** Creates a new instance of Animation */
    public Animation() {
    	double xi = 0.15*Math.cos(Math.toRadians(70));;
    	double yi = 0.15*Math.sin(Math.toRadians(70));
        
    	plottingPanel1 = new PlottingPanel("X (m)", "Y (m)", "1");
        plottingPanel2 = new PlottingPanel("X (m)", "Y (m)", "2");
        plottingPanelTotal = new PlottingPanel("X (m)", "Y (m)", "");
    	
    	di1 = DrawableShape.createCircle(xi, yi, 0.3);
    	de1 = DrawableShape.createCircle(xi, yi, 0.3);
    	di2 = DrawableShape.createCircle(xi, yi, 0);
    	de2 = DrawableShape.createCircle(xi, yi, 0.3);
        
        di1g = DrawableShape.createCircle(xi, yi, 0.3);
        de1g = DrawableShape.createCircle(xi, yi, 0.3);
        di2g = DrawableShape.createCircle(xi, yi, 0);
        de2g = DrawableShape.createCircle(xi, yi, 0.3);
        
    	di1.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	de1.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
    	di2.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	de2.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
    	
        double beta = (Math.PI/2.0) - Math.toRadians(20);
        
        double x1T = 5*0.15*Math.cos(beta);
        double y1T = 5*0.15*Math.sin(beta);
        
        double x2T = 5*0.15*Math.cos(beta);
        double y2T = 5*0.15*Math.sin(beta);
        
        distX1 = x1T - xi;
        distY1 = y1T - yi;
        distX2 = x2T - xi;
        distY2 = y2T - yi;
        
        di1T = DrawableShape.createCircle(x1T, y1T, 10*0.15);
        de1T = DrawableShape.createCircle(x1T, y1T, 10*0.15);
        di2T = DrawableShape.createCircle(x2T, y2T, 10*0);
        de2T = DrawableShape.createCircle(x2T, y2T, 10*0.15);
        
        di1T.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        de1T.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
        di2T.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        de2T.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
        
    	plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
    	plano.append(-5, -5*Math.tan(Math.toRadians(-20)));
    	plano.append(100, 100*Math.tan(Math.toRadians(-20))); 
    	
        di1g.setMarkerColor(new Color(255, 255, 255, 127), new Color(0, 0, 0, 127));
        de1g.setMarkerColor(new Color(0, 0, 255, 127), new Color(0, 0, 0, 127));
        di2g.setMarkerColor(new Color(255, 255, 255, 127), new Color(0, 0, 0, 127));
        de2g.setMarkerColor(new Color(255, 0, 0, 127), new Color(0, 0, 0, 127));
        
        plottingPanel1.addDrawable(de2g);
        plottingPanel1.addDrawable(di2g);
        
        plottingPanel2.addDrawable(de1g);
        plottingPanel2.addDrawable(di1g);
        
    	plottingPanel1.addDrawable(plano);
    	plottingPanel1.addDrawable(de1);
    	plottingPanel1.addDrawable(di1);
    	
    	plottingPanel2.addDrawable(plano);
    	plottingPanel2.addDrawable(de2);
    	plottingPanel2.addDrawable(di2);
    	
        plottingPanelTotal.addDrawable(plano);
        plottingPanelTotal.addDrawable(de1T);
        plottingPanelTotal.addDrawable(di1T);
        plottingPanelTotal.addDrawable(de2T);
        plottingPanelTotal.addDrawable(di2T);
        
        plottingPanel1.setPreferredMinMax(xi - 1, xi + 1, yi - 1, yi + 1);
        plottingPanel2.setPreferredMinMax(xi - 1, xi + 1, yi - 1, yi + 1);
        plottingPanelTotal.setPreferredMinMax(-4, 90, -63, 4);
        
    	plottingPanel1.setPreferredSize(new java.awt.Dimension(200, 200));
    	plottingPanel2.setPreferredSize(new java.awt.Dimension(200, 200));
        plottingPanelTotal.setPreferredSize(new java.awt.Dimension(299,213));

        JPanel panelGraficos = new JPanel();
        panelGraficos.setLayout(new BoxLayout(panelGraficos, BoxLayout.Y_AXIS));
    	panelGraficos.add(plottingPanel1);
    	panelGraficos.add(plottingPanel2);
    	
        panelEsquerda = new JPanel();
        panelEsquerda.setLayout(new BoxLayout(panelEsquerda, BoxLayout.Y_AXIS));
        panelEsquerda.add(plottingPanel1);
        panelEsquerda.add(plottingPanel2);
        
        this.setLayout(new FlowLayout());
        this.add(panelEsquerda);
        this.add(plottingPanelTotal);
    }
    
    public static void main(String args[]) {
        javax.swing.JFrame dummy = new javax.swing.JFrame();
        dummy.getContentPane().add(new Animation());
        dummy.pack();
        dummy.show();
    }
    
    //Chegaram novas amostras! 
    public void newSamples(NewExpDataEvent evt) {
        //Esta e' a maneira classica de tirar as amostras dos canais que nos interessam!
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            //sample, canal
            if(model.getValueAt(i,1) != null) {
                de1.setX(model.getValueAt(i,1).getValue().getFloatValue());
                di1.setX(model.getValueAt(i,1).getValue().getFloatValue());
                de1g.setX(model.getValueAt(i,1).getValue().getFloatValue());
                di1g.setX(model.getValueAt(i,1).getValue().getFloatValue());
                de1T.setX(model.getValueAt(i,1).getValue().getFloatValue() + distX1);
                di1T.setX(model.getValueAt(i,1).getValue().getFloatValue() + distX1);
                plottingPanel1.setPreferredMinMaxX((model.getValueAt(i,1).getValue().getFloatValue()) - 1,
                                (model.getValueAt(i,1).getValue().getFloatValue()) + 1);
            }
            if(model.getValueAt(i,2) != null) {
                de2.setX(model.getValueAt(i,2).getValue().getFloatValue());
                di2.setX(model.getValueAt(i,2).getValue().getFloatValue());
                de2g.setX(model.getValueAt(i,2).getValue().getFloatValue());
                di2g.setX(model.getValueAt(i,2).getValue().getFloatValue());
                de2T.setX(model.getValueAt(i,2).getValue().getFloatValue() + distX2);
                di2T.setX(model.getValueAt(i,2).getValue().getFloatValue() + distX2);
                plottingPanel2.setPreferredMinMaxX((model.getValueAt(i,2).getValue().getFloatValue()) - 1,
                                (model.getValueAt(i,2).getValue().getFloatValue()) + 1);
            }
            if(model.getValueAt(i,3) != null) {
                de1.setY(model.getValueAt(i,3).getValue().getFloatValue());
                di1.setY(model.getValueAt(i,3).getValue().getFloatValue());
                de1g.setY(model.getValueAt(i,3).getValue().getFloatValue());
                di1g.setY(model.getValueAt(i,3).getValue().getFloatValue());
                de1T.setY(model.getValueAt(i,3).getValue().getFloatValue() + distY1);
                di1T.setY(model.getValueAt(i,3).getValue().getFloatValue() + distY1);
                plottingPanel1.setPreferredMinMaxY((model.getValueAt(i,3).getValue().getFloatValue()) - 1,
                                (model.getValueAt(i,3).getValue().getFloatValue()) + 1);
            }
            if(model.getValueAt(i,4) != null) {
                de2.setY(model.getValueAt(i,4).getValue().getFloatValue());
                di2.setY(model.getValueAt(i,4).getValue().getFloatValue());
                de2g.setY(model.getValueAt(i,4).getValue().getFloatValue());
                di2g.setY(model.getValueAt(i,4).getValue().getFloatValue());
                de2T.setY(model.getValueAt(i,4).getValue().getFloatValue() + distY2);
                di2T.setY(model.getValueAt(i,4).getValue().getFloatValue() + distY2);
                plottingPanel2.setPreferredMinMaxY((model.getValueAt(i,4).getValue().getFloatValue()) - 1,
                                (model.getValueAt(i,4).getValue().getFloatValue()) + 1);
            }
            plottingPanelTotal.repaint();
            plottingPanel1.repaint();
            plottingPanel2.repaint();
        }
    }        
    
    //Queremos fazer alguma coisa quandos os dados acabarem?
    public void dataModelEnded() { }
    
    //Queremos fazer alguma coisa quandos acontecer um erro?
    public void dataModelError() { }
    
    //Queremos fazer alguma coisa quando for dado o start e existirem dados?    
    public void dataModelStarted() { }
    
    //Queremos fazer alguma coisa quando for dado o start e ainda nao existirem dados?
    //Eu garanto que quando chegamos a este estado, ja' existe o header da experiencia!
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        //vamos la' ver o que o utilizador escolheu, para colocar a animacao nas posicoes iniciais correctas!
        
        double rint1 = (Double.parseDouble(header.getSelectedHardwareParameterValue("r1i")));
        
        double rext1 = (Double.parseDouble(header.getSelectedHardwareParameterValue("r1e")));
        
        double rint2 = (Double.parseDouble(header.getSelectedHardwareParameterValue("r2i")));
        
        double rext2 = (Double.parseDouble(header.getSelectedHardwareParameterValue("r2e")));
        
        double ang = (Double.parseDouble(header.getSelectedHardwareParameterValue("inc")));
        
        double xi1 = rext1*Math.cos((Math.PI/2.0) - Math.toRadians(ang));
    	double yi1 = rext1*Math.sin((Math.PI/2.0) - Math.toRadians(ang));
        
    	di1 = DrawableShape.createCircle(xi1, yi1, 2*rint1);
    	de1 = DrawableShape.createCircle(xi1, yi1, 2*rext1);
    	
    	double xi2 = rext2*Math.cos((Math.PI/2.0) - Math.toRadians(ang));
    	double yi2 = rext2*Math.sin((Math.PI/2.0) - Math.toRadians(ang));
    	
    	di2 = DrawableShape.createCircle(xi2, yi2, 2*rint2);
    	de2 = DrawableShape.createCircle(xi2, yi2, 2*rext2);
    	
    	di1.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	de1.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
    	di2.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	de2.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
    	
        double m = Math.tan(Math.toRadians(-ang));
    	plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
    	plano.append(-5, -5*m);
    	plano.append(100, 100*m); 
    	
    	di1g = DrawableShape.createCircle(xi1, yi1, 2*rint1);
        de1g = DrawableShape.createCircle(xi1, yi1, 2*rext1);
        di2g = DrawableShape.createCircle(xi2, yi2, 2*rint2);
        de2g = DrawableShape.createCircle(xi2, yi2, 2*rext2);
        
        di1g.setMarkerColor(new Color(255, 255, 255, 127), new Color(0, 0, 0, 127));
        de1g.setMarkerColor(new Color(0, 0, 255, 127), new Color(0, 0, 0, 127));
        di2g.setMarkerColor(new Color(255, 255, 255, 127), new Color(0, 0, 0, 127));
        de2g.setMarkerColor(new Color(255, 0, 0, 127), new Color(0, 0, 0, 127));
        
        plottingPanel1.clear();
    	plottingPanel1.addDrawable(plano);
        plottingPanel1.addDrawable(de2g);
        plottingPanel1.addDrawable(di2g);
    	plottingPanel1.addDrawable(de1);
    	plottingPanel1.addDrawable(di1);
    	
        plottingPanel2.clear();
    	plottingPanel2.addDrawable(plano);
        plottingPanel2.addDrawable(de1g);
        plottingPanel2.addDrawable(di1g);
    	plottingPanel2.addDrawable(de2);
    	plottingPanel2.addDrawable(di2);
        
    	plottingPanel1.setPreferredMinMax(xi1 - 1, xi1 + 1, yi1 - 1, yi1 + 1);
    	plottingPanel2.setPreferredMinMax(xi2 - 1, xi2 + 1, yi2 - 1, yi2 + 1);
        
        double beta = (Math.PI/2.0) - Math.toRadians(ang);
        
        double x1T = 5*rext1*Math.cos(beta);
        double y1T = 5*rext1*Math.sin(beta);
        
        double x2T = 5*rext2*Math.cos(beta);
        double y2T = 5*rext2*Math.sin(beta);
        
        distX1 = x1T - xi1;
        distY1 = y1T - yi1;
        distX2 = x2T - xi2;
        distY2 = y2T - yi2;
        
        di1T = DrawableShape.createCircle(x1T, y1T, 10*rint1);
        de1T = DrawableShape.createCircle(x1T, y1T, 10*rext1);
        di2T = DrawableShape.createCircle(x2T, y2T, 10*rint2);
        de2T = DrawableShape.createCircle(x2T, y2T, 10*rext2);
        
        di1T.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        de1T.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
        di2T.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        de2T.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
        
        plottingPanelTotal.clear();
        plottingPanelTotal.addDrawable(plano);
        plottingPanelTotal.addDrawable(de1T);
        plottingPanelTotal.addDrawable(di1T);
        plottingPanelTotal.addDrawable(de2T);
        plottingPanelTotal.addDrawable(di2T);
    }
    
    //Queremos fazer alguma coisa quando for dado parado?
    public void dataModelStoped() {
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
    
    //Este codigo e' SEMPRE igual e tem de existir!
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
    }
}