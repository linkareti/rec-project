/*
 * BSCustomizer.java
 *
 * Created on April 2, 2005, 19:59 PM
 */

/**
 *
 * @author  Pedro Queiro'
 *
 *  1- Desenhar o GUI! Nao esquecer de ir logo internacionalizando...
 *
 *
 */

package pt.utl.ist.elab.virtual.client.bs;

import com.linkare.rec.impl.i18n.*;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.customizer.ICustomizer;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.data.synch.Frequency;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//<<<<<<< BSCustomizer.java
import pt.utl.ist.elab.virtual.client.bs.VariablePanel;
//=======
import org.opensourcephysics.display.*;
//>>>>>>> 1.11

public class BSCustomizer extends JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {
    
    private VariablePanel f1_Iini, f1_Ifin, f2_Iini, f2_Ifin, distFios, numSamples, xPto, yPto, tbs;
    
    private JButton buttonOK, buttonCancel, buttonReset;
    
    private JPanel panelBotoes, panelTopo, panelBaixo;
    private DrawingPanel drawingPanel;
    private DrawableShape f1, f2, pto;
    
    public BSCustomizer() {
        f1_Iini = new VariablePanel(-1, 1, 0.6, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.i1ini","I Inicial Fio 1 (A)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.i1ini","Intensidade de corrente inicial do fio 1"));
        
        f1_Ifin = new VariablePanel(-1, 1, -0.2, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.i1fin","I Final Fio 1 (A)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.i1fin","Intensidade de corrente final do fio 1"));
        
        f2_Iini = new VariablePanel(-1, 1, -0.2, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.i2ini","I Inicial Fio 2 (A)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.i2ini","Intensidade de corrente inicial do fio 2"));
        
        f2_Ifin = new VariablePanel(-1, 1, 0.6, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.i2fin","I Final Fio 2 (A)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.i2fin","Intensidade de corrente final do fio 2"));
        
        distFios = new VariablePanel(0.01, 0.25, 0.1, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.dist","Distancia (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.dist","Distancia entre os dois fios"));
        
        distFios.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        numSamples = new VariablePanel(10, 500, 250, 0, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.samples","Numero de Amostras"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.samples","Numero de amostras"));
        
        xPto = new VariablePanel(-0.15, 0.15, 0, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.xpto","X Ponto (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.xpto","Coordenada X do ponto a observar"));
        
        xPto.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        yPto = new VariablePanel(-0.15, 0.15, 0, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.ypto","Y Ponto (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.ypto","Coordenada Y do ponto a observar"));
        
        yPto.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        tbs = new VariablePanel(100, 500, 300, 0, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.tbs","dt (ms)"),
        ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.tip.tbs","Tempo entre Amostras"));
        
        buttonOK = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.ok","Correr"));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //OK o utilizador quer enviar as informacoes, vamos colocar os valores nos canais!!!
                acqConfig.setTotalSamples((int)numSamples.getCurrentValue());
                
                acqConfig.setSelectedFrequency(
                new Frequency(tbs.getCurrentValue(),
                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),
                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
                acqConfig.getSelectedHardwareParameter("i1_ini").setParameterValue("" + f1_Iini.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("i1_fin").setParameterValue("" + f1_Ifin.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("i2_ini").setParameterValue("" + f2_Iini.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("i2_fin").setParameterValue("" + f2_Ifin.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("dist").setParameterValue("" + distFios.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("xpto").setParameterValue("" + xPto.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("ypto").setParameterValue("" + yPto.getCurrentValue());
                fireICustomizerListenerDone();
            }
        });
        
        buttonCancel = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.cancel","Cancelar"));
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Sempre igual
                fireICustomizerListenerCanceled();
            }
        });
        
        buttonReset = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpBS$rec.exp.customizer.title.dfc","Restaurar"));
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                f1_Iini.reset();
                f1_Ifin.reset();
                f2_Iini.reset();
                f2_Ifin.reset();
                distFios.reset();
                numSamples.reset();
                xPto.reset();
                yPto.reset();
                tbs.reset();
            }
        });
        
        panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(1, 0));
        panelBotoes.add(buttonOK);
        panelBotoes.add(buttonReset);
        panelBotoes.add(buttonCancel);
        
        panelBaixo = new JPanel();
        panelBaixo.setLayout(new GridLayout(0, 2));
        panelBaixo.add(f1_Iini);
        panelBaixo.add(f1_Ifin);
        panelBaixo.add(f2_Iini);
        panelBaixo.add(f2_Ifin);
        panelBaixo.add(distFios);
        panelBaixo.add(numSamples);
        panelBaixo.add(xPto);
        panelBaixo.add(yPto);
        panelBaixo.add(tbs);
        panelBaixo.add(panelBotoes);
        
        panelTopo = new JPanel();
        panelTopo.setLayout(new FlowLayout());
        
        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredMinMax(-0.3, 0.3, -0.3, 0.3);
        drawingPanel.setPreferredSize(new java.awt.Dimension(150, 150));
        
        f1 = DrawableShape.createCircle(-0.05, 0, 0.01);
        f2 = DrawableShape.createCircle(0.05, 0, 0.01);
        pto = DrawableShape.createCircle(0, 0, 0.01);
        
        f1.setMarkerColor(java.awt.Color.gray, java.awt.Color.gray);
        f2.setMarkerColor(java.awt.Color.gray, java.awt.Color.gray);
        pto.setMarkerColor(java.awt.Color.black, java.awt.Color.black);
        
        drawingPanel.addDrawable(f1);
        drawingPanel.addDrawable(f2);
        drawingPanel.addDrawable(pto);
        
        panelTopo.add(drawingPanel);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panelTopo);
        this.add(panelBaixo);
    }
    
    public void actualizar() {
        drawingPanel.clear();
        
        f1.setX(-distFios.getCurrentValue()/2.0);
        f2.setX(distFios.getCurrentValue()/2.0);
        pto.setXY(xPto.getCurrentValue(), yPto.getCurrentValue());
        
        drawingPanel.addDrawable(f1);
        drawingPanel.addDrawable(f2);
        drawingPanel.addDrawable(pto);
        
        drawingPanel.repaint();
    }
    
    public static void main(String args[]) {
        JFrame dummy = new JFrame();
        dummy.getContentPane().add(new BSCustomizer());
        dummy.pack();
        dummy.show();
    }
    
    //****************************REC********************************************/
    
    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    /** Registers ICustomizerListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addICustomizerListener(ICustomizerListener listener) {
        if (listenerList == null ) {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(ICustomizerListener.class, listener);
    }
    
    /** Removes ICustomizerListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeICustomizerListener(ICustomizerListener listener) {
        listenerList.remove(ICustomizerListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerCanceled() {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ICustomizerListener.class) {
                ((ICustomizerListener)listeners[i+1]).canceled();
            }
        }
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerDone() {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ICustomizerListener.class) {
                
                ((ICustomizerListener)listeners[i+1]).done();
            }
        }
    }
    
    private HardwareInfo hardwareInfo=null;
    private HardwareAcquisitionConfig acqConfig=null;
    
    public HardwareAcquisitionConfig getAcquisitionConfig() {
        return acqConfig;
    }
    
    //ESTE E' PARA ALTERAR
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
        //Aqui sao fornecidos parametros do ultimo utilizador que fez a exp, e' bom manter!
        this.acqConfig=acqConfig;
        if(acqConfig!=null) {
            double value;
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i1_ini")));
            f1_Iini.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i1_fin")));
            f1_Ifin.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i2_ini")));
            f2_Iini.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("i2_fin")));
            f2_Ifin.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("dist")));
            distFios.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("xpto")));
            xPto.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("ypto")));
            yPto.setCurrentValue(value);
            
            numSamples.setCurrentValue(acqConfig.getTotalSamples());
            
            int freq = (int)acqConfig.getSelectedFrequency().getFrequency();
            tbs.setCurrentValue(freq);
        }
    }
    
    public void setHardwareInfo(HardwareInfo hardwareInfo) {
        this.hardwareInfo=hardwareInfo;
    }
    
    protected HardwareInfo getHardwareInfo() {
        return this.hardwareInfo;
    }
    
    public javax.swing.JComponent getCustomizerComponent() {
        return this;
    }
    
    public javax.swing.ImageIcon getCustomizerIcon() 
    {
        java.net.URL url = getClass().getResource("/pt/utl/ist/elab/virtual/client/bs/resources/bs_iconified.png");
        if(url != null)
            return new javax.swing.ImageIcon(url);
        
        return null;
    }
    
    //ESTE E' PARA ALTERAR
    public String getCustomizerTitle() {
        return "Magnetic Field Experiment Configuration Utility";
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
}