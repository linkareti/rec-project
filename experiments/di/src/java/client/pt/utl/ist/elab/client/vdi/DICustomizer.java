/*
 * DICustomizer.java
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

package pt.utl.ist.elab.client.vdi;


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
import org.opensourcephysics.display.*;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class DICustomizer extends JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer {
    
    private VariablePanel r1i, r1e, r2i, r2e, m1, m2, inc, tbs, nSamples;
    
    private JButton buttonOK, buttonCancel, buttonReset;
    
    private JPanel panelBotoes, panelTopo, panelBaixo;
    
    private DrawingPanel panelDisco1, panelDisco2;
    private DrawingPanel panelPlano;
    private DrawableShape d1i, d1e, d2i, d2e;
    private Dataset plano;
    private TitledBorder title;
    
    public DICustomizer() {
        r1i = new VariablePanel(0, 0.15, 0.15, 3, 3,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.r1i","R Interno 1 (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.r1i","Raio interno do disco 1"));
        
        r1i.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        r1e = new VariablePanel(0.15, 0.30, 0.15, 3, 3,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.r1e","R Externo 1 (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.r1e","Raio externo do disco 1"));
        
        r1e.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        r2i = new VariablePanel(0, 0.15, 0, 3, 3,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.r2i","R Interno 2 (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.r2i","Raio interno do disco 2"));
        
        r2i.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        r2e = new VariablePanel(0.15, 0.30, 0.15, 3, 3,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.r2e","R Externo 2 (m)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.r2e","Raio externo do disco 2"));
        
        r2e.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        m1 = new VariablePanel(0.1, 1.0, 0.5, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.m1","Massa 1 (kg)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.m1","Massa do disco 1"));
        
        m2 = new VariablePanel(0.1, 1.0, 0.5, 3, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.m2","Massa 2 (kg)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.m2","Massa do disco 2"));
        
        inc = new VariablePanel(5, 35, 20, 1, 5, 
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.inc","Inclinacao (graus)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.inc","Inclinacao do plano"));
        
        inc.addExecutor(new VariableExecutor() {
            public void execute() {
                actualizar();
            }
        });
        
        tbs = new VariablePanel(20, 50, 30, 0, 5, 
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.tbs","dt (ms)"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.tbs","Tempo entre Amostras"));
        
        nSamples = new VariablePanel(10, 150, 150, 0, 2,
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.nsamps","Numero de Amostras"),
        ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.tip.nsamps","Numero de Amostras"));
        
        buttonOK = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.ok","Correr"));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //OK o utilizador quer enviar as informacoes, vamos colocar os valores nos canais!!!
                acqConfig.setTotalSamples((int)nSamples.getCurrentValue());
                
                acqConfig.setSelectedFrequency(
                new Frequency(tbs.getCurrentValue(),
                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),
                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
                acqConfig.getSelectedHardwareParameter("r1i").setParameterValue("" + r1i.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("r1e").setParameterValue("" + r1e.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("r2i").setParameterValue("" + r2i.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("r2e").setParameterValue("" + r2e.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("m1").setParameterValue("" + m1.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("m2").setParameterValue("" + m2.getCurrentValue());
                acqConfig.getSelectedHardwareParameter("inc").setParameterValue("" + inc.getCurrentValue());
                fireICustomizerListenerDone();
            }
        });
        
        buttonCancel = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.cancel","Cancelar"));
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Sempre igual
                fireICustomizerListenerCanceled();
            }
        });
        
        buttonReset = new JButton(ReCResourceBundle.findStringOrDefault("ReCExpDI$rec.exp.customizer.title.dfc","Restaurar"));
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                r1i.reset();
                r1e.reset();
                r2i.reset();
                r2e.reset();
                m1.reset();
                m2.reset();
                inc.reset();
                nSamples.reset();
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
        panelBaixo.add(r1i);
        panelBaixo.add(r1e);
        panelBaixo.add(r2i);
        panelBaixo.add(r2e);
        panelBaixo.add(m1);
        panelBaixo.add(m2);
        panelBaixo.add(inc);
        panelBaixo.add(nSamples);
        panelBaixo.add(tbs);
        panelBaixo.add(panelBotoes);
        
        d1i = DrawableShape.createCircle(0, 0, 2*0.15);
        d1e = DrawableShape.createCircle(0, 0, 2*0.15);
        d2i = DrawableShape.createCircle(0, 0, 2*0);
        d2e = DrawableShape.createCircle(0, 0, 2*0.15);
        
        d1i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        d1e.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
        d2i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
        d2e.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
        
        plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
        plano.append(-5, -5*Math.tan(-Math.toRadians(20)));
        plano.append(5, 5*Math.tan(-Math.toRadians(20)));
        
        panelDisco1 = new DrawingPanel();
        
        title = BorderFactory.createTitledBorder("1");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelDisco1.setBorder(title);
        
        panelDisco1.setPreferredSize(new java.awt.Dimension(110,110));
        panelDisco1.setPreferredMinMax(-.5, .5, -.5, .5);
        panelDisco1.addDrawable(d1e);
        panelDisco1.addDrawable(d1i);
        
        panelDisco2 = new DrawingPanel();
        
        panelDisco2.setPreferredSize(new java.awt.Dimension(110,110));
        title = BorderFactory.createTitledBorder("2");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelDisco2.setBorder(title);
        
        panelDisco2.setPreferredMinMax(-.5, .5, -.5, .5);
        panelDisco2.addDrawable(d2e);
        panelDisco2.addDrawable(d2i);
        
        panelPlano = new DrawingPanel();
        panelPlano.setPreferredSize(new java.awt.Dimension(110,110));
        panelPlano.setPreferredMinMax(-4, 4, -4, 4);
        panelPlano.addDrawable(plano);
        
        panelTopo = new JPanel();
        panelTopo.setPreferredSize(new java.awt.Dimension(330,130));
        panelTopo.setBackground(java.awt.Color.white);
        panelTopo.setLayout(new FlowLayout());
        panelTopo.add(panelDisco1);
        panelTopo.add(panelDisco2);
        panelTopo.add(panelPlano);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panelTopo);
        this.add(panelBaixo);
    }
    
    public static void main(String args[]) {
        JFrame dummy = new JFrame();
        dummy.getContentPane().add(new DICustomizer());
        dummy.pack();
        dummy.show();
    }
    
    public double arredondar(double a, int x) {
        double b, c, d;
        d = Math.pow(10, x);
        b = (a * d);
        c = (Math.rint(b)) / d;
        return c;
    }
    
    private void actualizar() {
        double rint1 = r1i.getCurrentValue();
        
        double rext1 = r1e.getCurrentValue();
        
        double rint2 = r2i.getCurrentValue();;
        
        double rext2 = r2e.getCurrentValue();;
        
        double ang = inc.getCurrentValue();;
        
    	d1i = DrawableShape.createCircle(0, 0, 2*rint1);
    	d1e = DrawableShape.createCircle(0, 0, 2*rext1);
    	
    	d2i = DrawableShape.createCircle(0, 0, 2*rint2);
    	d2e = DrawableShape.createCircle(0, 0, 2*rext2);
    	
    	d1i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	d1e.setMarkerColor(java.awt.Color.blue, java.awt.Color.black);
    	d2i.setMarkerColor(java.awt.Color.white, java.awt.Color.black);
    	d2e.setMarkerColor(java.awt.Color.red, java.awt.Color.black);
    	
        double m = Math.tan(Math.toRadians(-ang));
    	plano = new Dataset(java.awt.Color.black, java.awt.Color.black, true);
    	plano.append(-5, -5*m);
    	plano.append(5, 5*m);
        
        panelPlano.clear();
        panelPlano.addDrawable(plano);
    	
        panelDisco1.clear();
    	panelDisco1.addDrawable(d1e);
    	panelDisco1.addDrawable(d1i);
    	
        panelDisco2.clear();
    	panelDisco2.addDrawable(d2e);
    	panelDisco2.addDrawable(d2i);
        
        panelPlano.repaint();
        panelDisco1.repaint();
        panelDisco2.repaint();
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
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r1i")));
            r1i.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r1e")));
            r1e.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r2i")));
            r2i.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("r2e")));
            r2e.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("m1")));
            m1.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("m2")));
            m2.setCurrentValue(value);
            
            value = (Double.parseDouble(acqConfig.getSelectedHardwareParameterValue("inc")));
            inc.setCurrentValue(value);
            
            nSamples.setCurrentValue(acqConfig.getTotalSamples());
            
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
    
    public javax.swing.ImageIcon getCustomizerIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/virtual/client/di/resources/di_iconified.png"));
    }
    
    //ESTE E' PARA ALTERAR
    public String getCustomizerTitle() {
        return "Inertial Discs Experiment Configuration Utility";
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
}