/*
 * Chart1.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

import pt.utl.ist.elab.virtual.driver.mvbrown.*;

/**
 *
 * @author  nomead
 */
public class Chart1 extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    
    private StaticChart chart;
    
    /** Creates a new instance of Chart1 */
    public Chart1(){
        super();
        //setPreferredSize(new java.awt.Dimension(700,500));
    }
    
    //TESTE
    /*public void start(){
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight= 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        
        boolean media = false;
        
        if (!media){
            
            StaticChart chart = new StaticChart(getWidth(),getHeight());
            
            removeAll();
            add(chart, gridBagConstraints);
            updateUI();
            
            MvBrownDataProducer mv = new MvBrownDataProducer(null,200,(byte)3,getWidth(),getHeight(),false,false,1,500);
            mv.initializeGraphs(new String[]{"x vs y","t vs | v |^2","| r | vs t","y vs t"}, new boolean[]{false,false,true,false});
            mv.initializeGenPosGraph(true, true, true, true, false, false, false);
            mv.initializeLangevin(1,new double[]{0.1,0.01},new double[]{0.1,0.25},new double[]{0,0,0},false);
            mv.startChart(chart);
        }
        else {
            String name = "t vs | r |";
            
            byte [] channels = nameToChannels(name);
            System.out.println(channels[0]-1); //acerto para o XML
            System.out.println(channels[1]-1); //acerto para o XML
        }
    }
    */
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        String graph1 = header.getSelectedHardwareParameterValue("graph1");
        
        if (!graph1.equalsIgnoreCase("")){ //grafico activo
            if (header.getSelectedHardwareParameterValue("graph1Med").trim().equals("1")?true:false){
                byte [] channels = nameToChannels(graph1);
                setChannelDisplayX(channels[0]-1); //acerto para o XML
                setChannelDisplayY(channels[1]-1); //acerto para o XML
                super.dataModelStartedNoData();
            }
            else {
                setLayout(new java.awt.GridBagLayout());
                java.awt.GridBagConstraints gridBagConstraints;
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridheight= 1;
                gridBagConstraints.gridwidth = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 1;
                gridBagConstraints.weighty = 1;
                
                chart = new StaticChart(Integer.parseInt(header.getSelectedHardwareParameterValue("w")),Integer.parseInt(header.getSelectedHardwareParameterValue("h")));
                removeAll();
                add(chart, gridBagConstraints);
                updateUI();
            }
        }
        else {
            setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints;
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight= 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            
            javax.swing.JLabel label = new javax.swing.JLabel(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.inactive"),javax.swing.JLabel.CENTER);
            removeAll();
            add(label, gridBagConstraints);
            updateUI();
        }
    }
    
    public void newSamples(NewExpDataEvent evt) {
        if (chart != null){
            for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
                if (model.getValueAt(i,18) != null)
                    chart.makeImage(model.getValueAt(i,18).getValue().getByteArrayValue().getData());
        }
        else
            super.newSamples(evt);
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        super.setExpDataModel(model);
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
    /*
    public static void main(String args[]) {
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        Chart1 stdim = new Chart1();
        test.getContentPane().add(stdim);
        test.pack();
        test.show();
        stdim.start();
    }
    */
    public static byte[] nameToChannels(String name){
        String [] str = name.split(" vs ");
        byte [] axis = new byte[2];
        
        for (int i = 0; i < 2; i++){
            if (str[i].equalsIgnoreCase("X"))
                axis[i] = 2;
            if (str[i].equalsIgnoreCase("Y"))
                axis[i] = 4;
            if (str[i].equalsIgnoreCase("Z"))
                axis[i] = 6;
            else if (str[i].equalsIgnoreCase("X^2"))
                axis[i] = 8;
            else if (str[i].equalsIgnoreCase("Y^2"))
                axis[i] = 10;
            else if (str[i].equalsIgnoreCase("Z^2"))
                axis[i] = 12;
            else if (str[i].equalsIgnoreCase("| r |^2"))
                axis[i] = 14;
            else if (str[i].equalsIgnoreCase("Vx"))
                axis[i] = 3;
            else if (str[i].equalsIgnoreCase("Vy"))
                axis[i] = 5;
            else if (str[i].equalsIgnoreCase("Vz"))
                axis[i] = 7;
            else if (str[i].equalsIgnoreCase("Vx^2"))
                axis[i] = 9;
            else if (str[i].equalsIgnoreCase("Vy^2"))
                axis[i] = 11;
            else if (str[i].equalsIgnoreCase("Vz^2"))
                axis[i] = 13;
            else if (str[i].equalsIgnoreCase("| v |^2"))
                axis[i] = 15;
            else if (str[i].equalsIgnoreCase("t"))
                axis[i] = 18;
            else if (str[i].equalsIgnoreCase("| r |"))
                axis[i] = 16;
            else if (str[i].equalsIgnoreCase("| v |"))
                axis[i] = 17;
        }
        return axis;
    }
    
}
