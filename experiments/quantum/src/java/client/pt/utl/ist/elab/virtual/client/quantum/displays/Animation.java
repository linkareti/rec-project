/*
 * Animation.java
 *
 * Created on 1 de Março de 2005, 5:52
 */

package pt.utl.ist.elab.virtual.client.quantum.displays;

import pt.utl.ist.elab.virtual.utils.*;
import javax.swing.*;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

import pt.utl.ist.elab.virtual.client.quantum.*;
import pt.utl.ist.elab.virtual.driver.quantum.QuantumDataProducer;
/**
 *
 * @author  nomead
 */
public class Animation extends Quantum implements ExpDataDisplay, ExpDataModelListener {
    
    /** Creates a new instance of Animation */
    public Animation() {
        super();
    }
    
    //TESTE
    public void start(){
        clearPotentials();
        //configPotentials("0.0:5.0:f(x) = 100:false#6.0:1.0:f(x) = 50:false#",false);
        configPotentials("0.0:4.0:f(x) = -cos(x)*5:false#5.0:5.0:f(x) = sin(x)*5:false#10.5:2.0:f(x) = exp(-x*25)+3:false#-3.0:2.0:f(x) = 255:false#-5.0:1.5:f(x) = 90+x^3+4*x^2+5+cos(3*x):false",false);
        configGaussian(100,-10,100,11,2,ComplexGaussian.DISPLAY_PROBABILITY,true);
        setPreferredMinMax(-60,40,-1,1);
        repaint();
        
        QuantumDataProducer q = new QuantumDataProducer(null,100,-10,100,11,2,1e-5,5e-20,5e-18,200,false,true,true);
        //q.configPotentials("0.0:5.0:f(x) = 100:false#6.0:1.0:f(x) = 50:false#");
        q.configPotentials("0.0:4.0:f(x) = -cos(x)*5:false#5.0:5.0:f(x) = sin(x)*5:false#10.5:2.0:f(x) = exp(-x*25)+3:false#-3.0:2.0:f(x) = 255:false#-5.0:1.5:f(x) = 90+x^3+4*x^2+5+cos(3*x):false");
        q.start(this);
    }
    
    public void moves(byte [] mv){
        setPsi((Complex[])ByteUtil.byteArrayToObject(mv));
        repaint();
    }
    
    public static void main(String args[]) {
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        Animation stdim = new Animation();
        test.getContentPane().add(stdim);
        test.pack();
        test.show();
        stdim.start();
    }
    
    public void dataModelEnded() {
    }
    
    public void dataModelError() {
    }
    
    public void dataModelStarted() {
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        double x0 = Double.parseDouble(header.getSelectedHardwareParameterValue("x0"));
        int deltaX = Integer.parseInt(header.getSelectedHardwareParameterValue("deltaX"));
        int log2N = Byte.parseByte(header.getSelectedHardwareParameterValue("log2N"));
        int dX0 = Integer.parseInt(header.getSelectedHardwareParameterValue("dX0"));

        String xE = header.getSelectedHardwareParameterValue("xEnergy");
        String nE = header.getSelectedHardwareParameterValue("nEnergy");
        
        double energy = Double.parseDouble(xE+"e"+nE);
        
        clearPotentials();
        configPotentials(header.getSelectedHardwareParameterValue("potentials"),false);
        configGaussian(dX0,x0,energy,log2N,deltaX,ComplexGaussian.DISPLAY_PROBABILITY,true);
    }
    
    public void dataModelStoped() {
    }
    
    public void dataModelWaiting() {
    }
    
    public javax.swing.JComponent getDisplay() {
        return this;
    }
    
    public javax.swing.Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar() {
        return null;
    }
    
    public void newSamples(NewExpDataEvent evt) {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            //sample, canal
            if (model.getValueAt(i,0) != null)
                moves(model.getValueAt(i,0).getValue().getByteArrayValue().getData());
            else if (model.getValueAt(i,7) != null)
                messageDialog(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.customizer.title.35"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.3"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.3"),
                null,
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.1"), 
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.1"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.2"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.2")
                );
            else if (model.getValueAt(i,8) != null)
                messageDialog(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.customizer.title.36"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.4"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.3"),
                null,
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.1"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.1"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.title.2"),
                java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/quantum/resources/ReCExpQuantum").getString("rec.exp.displays.dialog.tip.2")
                );
        }
    }
    
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
    
    
    public void messageDialog(String message, String messageTooltip, String title, String toolTip, String continueTitle, String continueToolTip, String stopTitle, String stopToolTip){
        pause();
        java.awt.GridBagConstraints gridBagConstraints;
        
        JDialog tempDialog = new JDialog(new JFrame(), title, true);
        
        JPanel dialogPanel = new JPanel();
        JLabel messageLabel = new JLabel(message);
        JButton stopButton = new JButton();
        JButton continueButton = new JButton();
        
        tempDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogPanel.setLayout(new java.awt.GridBagLayout());
        
        messageLabel.setToolTipText(messageTooltip);
        messageLabel.setHorizontalAlignment(messageLabel.CENTER);
        messageLabel.setMinimumSize(new java.awt.Dimension(300, 47));
        messageLabel.setPreferredSize(new java.awt.Dimension(300, 47));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridwidth = 2;
        dialogPanel.add(messageLabel, gridBagConstraints);
        dialogPanel.setToolTipText(toolTip);
        
        continueButton.setText(continueTitle);
        continueButton.setToolTipText(continueToolTip);
        continueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ((JDialog) ((JButton) evt.getSource()).getTopLevelAncestor()).dispose();
                resume();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        dialogPanel.add(continueButton, gridBagConstraints);
        
        stopButton.setText(stopTitle);
        stopButton.setToolTipText(stopToolTip);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ((JDialog) ((JButton) evt.getSource()).getTopLevelAncestor()).dispose();
                stop();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        dialogPanel.add(stopButton, gridBagConstraints);
        
        
        tempDialog.getContentPane().add(dialogPanel, java.awt.BorderLayout.CENTER);
        
        tempDialog.pack();
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension tempSize =  tempDialog.getSize();
        tempDialog.setLocation((int) ((screenSize.width-tempSize.getWidth())/2d), (int) ((screenSize.height-tempSize.getHeight())/2d));
        
        tempDialog.show();
    }
    
    public void pause(){
        //if (model != null)
          //  model.pause();
    }
    
    public void stop(){
        //if (model != null)
          //  model.stopNow();
    }
    
    public void resume(){
        //if (model != null)
          //  model.play();
    }
    
}
