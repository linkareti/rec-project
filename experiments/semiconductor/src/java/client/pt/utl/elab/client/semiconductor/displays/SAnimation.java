/*
 * SAnimation.java
 *
 * Created on February 13, 2004, 4:01 PM
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  Pedro Queiro e Nuno Fernandes
 */

import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.config.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.text.*;
import java.util.*;

public class SAnimation extends JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay, com.linkare.rec.impl.client.experiment.ExpDataModelListener {
    private boolean started = false;
    private Icon icon = new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    
    private int rectHeight = 50;
    private int pWidth, nWidth, lNodo;
    
    private int hCentro, wCentro;
    
    private int dWidth;
    
    private double w, e, eG, vBI;
    private double nA, nD, t, v;
    
    private Color corP = Color.RED;
    private Color corN = Color.BLUE;
    private Color corW = Color.lightGray;
    private Color corV = Color.BLACK;
    
    private JLabel labelW, labelE, labelEG, labelVBI;
    private JLabel labelNA, labelND, labelT, labelV;
    private JPanel painelLabels;
    private PanelAnimacao painelAnimacao;
    private JPanel painelParametros, painelVariaveis;
    
    private TitledBorder title;
    DecimalFormatSymbols formatoDeltas = new DecimalFormatSymbols(new Locale("en", "US"));
    DecimalFormat formatadorDeltas = new DecimalFormat("#0.####E00", formatoDeltas);
    
    public SAnimation() {
        setPreferredSize(new Dimension(340, 400));
        setMinimumSize(new Dimension(340, 400));
        
        painelAnimacao = new PanelAnimacao();
        title = BorderFactory.createTitledBorder("Animacao");	// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);		// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        painelAnimacao.setBorder(title);
        painelAnimacao.setPreferredSize(new Dimension(320, 200));
        
        labelW = new JLabel("W = 0 cm");
        labelE = new JLabel("E = 0 V / cm");
        labelEG = new JLabel("<html>E<sub>g</sub> = 0 eV");
        labelVBI = new JLabel("<html>V<sub>bi</sub> = 0 V");
        
        painelVariaveis = new JPanel();
        title = BorderFactory.createTitledBorder("Variaveis");	// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);		// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        painelVariaveis.setBorder(title);
        painelVariaveis.setLayout(new GridLayout(2,2));
        painelVariaveis.add(labelW);
        painelVariaveis.add(labelE);
        painelVariaveis.add(labelEG);
        painelVariaveis.add(labelVBI);
        
        labelNA = new JLabel("<html>nA = 0 cm<sup>-3</sup>");
        labelND = new JLabel("<html>nD = 0 cm<sup>-3</sup>");
        labelT = new JLabel("T = 0 K");
        labelV = new JLabel("V = 0 V");
        
        painelParametros = new JPanel();
        title = BorderFactory.createTitledBorder("Parametros");	// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);		// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        painelParametros.setBorder(title);
        painelParametros.setLayout(new GridLayout(2,2));
        painelParametros.add(labelNA);
        painelParametros.add(labelND);
        painelParametros.add(labelT);
        painelParametros.add(labelV);
        
        painelLabels = new JPanel();
        painelLabels.setLayout(new GridLayout(0,1));
        painelLabels.add(painelParametros);
        painelLabels.add(painelVariaveis);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(painelAnimacao);
        add(painelLabels);
        
        hCentro = painelAnimacao.getHeight()/2;
        wCentro = painelAnimacao.getWidth()/2;
        
        pWidth = wCentro - 50;
        nWidth = wCentro - 50;
        dWidth = 0;
        lNodo = 20;
    }
    
    private void setW(double w) { this.w = w; dWidth = (int)(w*((pWidth + nWidth - 10)/(0.01))); }
    
    private void setE(double e) { this.e = e; }
    
    private void setVBI(double vBI) { this.vBI = vBI; }
    
    private void setEG(double eG) { this.eG = eG; }
    
    private void setNA(double nA) { this.nA = nA; }
    
    private void setND(double nD) { this.nD = nD; }
    
    private void setV(double v) { this.v = v; }
    
    private void setT(double t) { this.t = t; }
    
    public javax.swing.JComponent getDisplay() { return this; }
    
    public Icon getIcon() { return icon; }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        pWidth = wCentro - 50;
        nWidth = wCentro - 50;
        dWidth = 0;
        lNodo = 20;
	
	if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
    }
    
    public void dataModelRunning() { }
    
    public void dataModelStoped() { }
    
    private void headerAvailable(HardwareAcquisitionConfig header) {
        this.header = header;
        if(header != null) {
            // Valores iniciais
            /*setLength1(Integer.parseInt(header.getSelectedHardwareParameterValue("P1 length")));
            setLength2(Integer.parseInt(header.getSelectedHardwareParameterValue("P2 length")));
            setMass1(Float.parseFloat(header.getSelectedHardwareParameterValue("P1 Mass"))/1000f);
            setMass2(Float.parseFloat(header.getSelectedHardwareParameterValue("P2 Mass"))/1000f);*/
            setT(Double.parseDouble(header.getSelectedHardwareParameterValue("Temp INI")));
            setND(Double.parseDouble(header.getSelectedHardwareParameterValue("N Dadores INI")));
            setNA(Double.parseDouble(header.getSelectedHardwareParameterValue("N Aceitadores INI")));
            setV(Double.parseDouble(header.getSelectedHardwareParameterValue("V ext INI")));
            labelNA.setText("<html>nA = " + nA + " cm<sup>-3</sup>");
            labelND.setText("<html>nD = " + nD + " cm<sup>-3</sup>");
            labelT.setText("T = " + t + " K");
            labelV.setText("V = " + v + " V");
        }
    }
    
    private HardwareAcquisitionConfig header=null;
    private boolean acqHeaderInited=false;
    public void newSamples(NewExpDataEvent evt) {
        started = true;
        for(int i = evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            if(model.getValueAt(i, 0) != null) setW(model.getValueAt(i, 0).getValueNumber().doubleValue());
            if(model.getValueAt(i, 1) != null) setEG(model.getValueAt(i, 1).getValueNumber().doubleValue());
            if(model.getValueAt(i, 2) != null) setVBI(model.getValueAt(i, 2).getValueNumber().doubleValue());
            if(model.getValueAt(i, 3) != null) setE(model.getValueAt(i, 3).getValueNumber().doubleValue());
            if(model.getValueAt(i, 4) != null) setT(model.getValueAt(i, 4).getValueNumber().doubleValue());
            if(model.getValueAt(i, 6) != null) setND(model.getValueAt(i, 6).getValueNumber().doubleValue());
            if(model.getValueAt(i, 7) != null) setNA(model.getValueAt(i, 7).getValueNumber().doubleValue());
            if(model.getValueAt(i, 8) != null) setV(model.getValueAt(i, 8).getValueNumber().doubleValue());
            
            repaint();
        }
        actualizar();
    }
    
    /** Arredonda um dado numero com as casas decimais desejadas.
     * O numero e' arredondado 'a x-esima casa.
     * <pre> arredondar(double a, int x); </pre>
     * Devolve um double que e' o double <i>a</i> fornecido arredondado a <i>x</i> casas.
     * @param a numero a arredondar.
     * @param x numero de casas desejadas.
     * @return O numero a arredondado as casas desejadas.
     */
    public static double arredondar(double a) {
        int x = 4;
        return (Math.rint(Math.pow(10, x)*a)/Math.pow(10, x));
    }
    
    private void actualizar() {
        
        labelW.setText("W = " + formatadorDeltas.format(w) + " cm");
        labelE.setText("E = " + arredondar(e) + " V / cm");
        labelEG.setText("<html>E<sub>g</sub> = " + arredondar(eG) + " eV");
        labelVBI.setText("<html>V<sub>bi</sub> = " + arredondar(vBI) + " V");
        labelNA.setText("<html>nA = " + formatadorDeltas.format(nA) + " cm<sup>-3</sup>");
        labelND.setText("<html>nD = " + formatadorDeltas.format(nD) + " cm<sup>-3</sup>");
        labelT.setText("T = " + arredondar(t) + " K");
        labelV.setText("V = " + arredondar(v) + " V");
    }
    
    public String getName() { return "Semicondutores"; }
    
    public JMenuBar getMenuBar() { return null; }
    
    public JToolBar getToolBar() { return null; }
    
    
    
    public static void main(String args[]) {
        javax.swing.JFrame jf = new javax.swing.JFrame();
        SAnimation app = new SAnimation();
        app.started = true;
        jf.getContentPane().add(app);
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
	header = model.getAcquisitionConfig();
	headerAvailable(header);
    }
    
    public void dataModelWaiting()
    {
    }
    
    class PanelAnimacao extends JPanel {
        
        public PanelAnimacao(){
            super();
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            hCentro = this.getHeight()/2;
            wCentro = this.getWidth()/2;
            
            pWidth = wCentro - 50;
            nWidth = wCentro - 50;
            
            if(!started) { return; }
            
            Graphics2D g3D = (Graphics2D)g;
            
            System.out.println((float)(0.5 + (0.5)/(Math.pow(10, 19) - Math.pow(10, 13)) * (nA - Math.pow(10, 13))));
            corP = new Color(corP.getRed()/255f, corP.getGreen()/255f, corP.getBlue()/255f, (float)(0.5 + (0.5)/(Math.pow(10, 19) - Math.pow(10, 13)) * (nA - Math.pow(10, 13))));
            corN = new Color(corN.getRed()/255f, corN.getGreen()/255f, corN.getBlue()/255f, (float)(0.5 + (0.5)/(Math.pow(10, 19) - Math.pow(10, 13)) * (nD - Math.pow(10, 13))));
            corV = new Color(corV.getRed()/255f, corV.getGreen()/255f, corV.getBlue()/255f, (float)(0.5 + (0.5)/(20) * (v + 10)));
            
            g3D.setColor(Color.white);
            g3D.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            g3D.setColor(corP);
            g3D.fill3DRect((wCentro - pWidth), (hCentro - rectHeight/2), pWidth, rectHeight, true);
            
            g3D.setColor(corN);
            g3D.fill3DRect((wCentro), (hCentro - rectHeight/2), nWidth, rectHeight, true);
            
            g3D.setColor(corW);
            g3D.fill3DRect((wCentro - dWidth/2), (hCentro - rectHeight/2), dWidth, rectHeight, true);
            
            g3D.setColor(corV);
            g3D.drawLine((wCentro + nWidth), hCentro, (wCentro + nWidth + 20), hCentro);
            g3D.drawLine((wCentro + nWidth + 20), hCentro, (wCentro + nWidth + 20), hCentro + 30);
            g3D.drawLine((wCentro + nWidth + 8), (hCentro + 30), (wCentro + nWidth + 32), hCentro + 30);
            g3D.drawLine((wCentro + nWidth + 12), (hCentro + 35), (wCentro + nWidth + 28), hCentro + 35);
            g3D.drawLine((wCentro + nWidth + 16), (hCentro + 40), (wCentro + nWidth + 24), hCentro + 40);
            g3D.drawLine((wCentro - pWidth), hCentro, (wCentro - pWidth - 20), hCentro);
        }
    }
}
