package pt.utl.ist.elab.client.vdi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class VariablePanel extends JPanel {
    private VariableExecutor executor = null;
    private JSlider slider;
    private JFormattedTextField text;
    private static Dimension textDim = new Dimension(30, 10);
    private static Dimension panelDim = new Dimension(180, 70);
    private TitledBorder title;
    private double min, max, init;
    private int mult;
    private String pTitle;
    
    public VariablePanel(double newMin, double newMax, double newInit, final int multiplier, int divs, String newTitle, String toolTip) {
        super();
        
        min = newMin;
        max = newMax;
        init = newInit;
        mult = multiplier;
        pTitle = newTitle;
        
        int sliderMin = (int)(min*Math.pow(10,multiplier));
        int sliderMax = (int)(max*Math.pow(10,multiplier));
        int sliderInit = (int)(init*Math.pow(10,multiplier));
        
        // Novo conjunto sliders/textfields
        slider = new JSlider(JSlider.HORIZONTAL, sliderMin, sliderMax, sliderInit);
        
        // Hashtables para os sliders
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(sliderMin), new JLabel(""+min));
        labelTable.put(new Integer((sliderMin+sliderMax)/2), new JLabel(""+arredondar((min+max)/2.0, multiplier)));
        labelTable.put(new Integer(sliderMax), new JLabel(""+max));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        
        // Ticks para os sliders
        slider.setMajorTickSpacing((sliderMax-sliderMin)/divs);
        slider.setMinorTickSpacing((sliderMax-sliderMin)/(2*divs));
        slider.setPaintTicks(true);
        
        slider.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting()) {            // Se o utilizador acabou de ajustar o valor
                    text.setValue(new Double(valor/(Math.pow(10, multiplier))));     // Actualiza-se o valor
                } else {                                        // Se nao, mostramos apenas o valor no ecran
                    if(executor != null) {executor.execute();}
                    text.setText(String.valueOf(valor/(Math.pow(10, multiplier))));
                }
            }
        });
        
        // Formatadores para as formatted text fields
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "US"));
        format.setMaximumFractionDigits(multiplier);
        format.setMinimumFractionDigits(0);
        NumberFormatter formatador = new NumberFormatter(format);
        formatador.setMinimum(new Double(min));
        formatador.setMaximum(new Double(max));
        text = new JFormattedTextField(formatador);
        text.setValue(new Double(init));
        text.setColumns(5);
        text.setPreferredSize(textDim);
        
        text.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        text.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!text.isEditValid()) {            // O texto e' invalido,
                    Toolkit.getDefaultToolkit().beep();
                    text.selectAll();                 // entao avisa-se.
                } else try {                            // O texto e' valido,
                    text.commitEdit();                // entao usa-se.
                } catch (java.text.ParseException exc) { }
            }
        });
        
        text.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if ("value".equals(e.getPropertyName())) {
                    Number value = (Number)e.getNewValue();
                    if (value != null) {
                        if(executor != null) {executor.execute();}
                        slider.setValue((int)(value.doubleValue()*(Math.pow(10, multiplier))));
                    }
                }
            }
        });
        
        this.setToolTipText(toolTip);
        slider.setToolTipText(toolTip);
        
        title = BorderFactory.createTitledBorder(pTitle);
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        this.setBorder(title);
        this.setPreferredSize(panelDim);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(slider);
        this.add(text);
    }
    
     public double arredondar(double a, int x) {
        double b, c, d;
        d = Math.pow(10, x);
        b = (a * d);
        c = (Math.rint(b)) / d;
        return c;
    }
    
    public void addExecutor(VariableExecutor newExecutor) {
        executor = newExecutor;
    }
    
    public double getCurrentValue() {
        return Double.parseDouble(text.getText());
    }
    
    
    public void setCurrentValue(double newValue) {
        text.setValue(new Double(newValue));
    }
    
    public void reset() {
        this.setCurrentValue(init);
    }
}