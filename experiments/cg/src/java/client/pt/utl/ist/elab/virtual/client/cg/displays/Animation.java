/*
 * Animation.java
 *
 * Created on 2 de Dezembro de 2004, 8:12
 */

package pt.utl.ist.elab.virtual.client.cg.displays;

/**
 *
 * @author  nomead
 */

import pt.utl.ist.elab.client.vcg.BalancaTorcao;
import pt.utl.ist.elab.client.vcg.CGCustomizer;
import pt.utl.ist.elab.client.vcg.PopupMenu;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.axes.XAxis;
import org.opensourcephysics.displayejs.InteractiveText;

import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class Animation extends JPanel implements ExpDataDisplay, ExpDataModelListener, MouseListener, ActionListener
{
    
    private BalancaTorcao balanca;
    private Circle luzCol;
    private DrawingPanel reg;
    private PopupMenu regPopMenu;
    private InteractiveText constTextBox;
    
    /** Creates a new instance of Animation */
    public Animation()
    {
        setLayout(new java.awt.GridBagLayout());
        
        java.awt.GridBagConstraints gridBagConstraints;
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight= 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = .7;
        gridBagConstraints.weighty = .7;
        add(balanca = new BalancaTorcao(), gridBagConstraints);
        
        reg = new DrawingPanel();
        reg.setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.title.14","Target")));
        reg.setToolTipText(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.14","Target"));
        reg.addMouseListener(this);
        reg.setMinimumSize(new java.awt.Dimension(150, 100));
        reg.setPreferredSize(new java.awt.Dimension(150, 100));
        reg.setPreferredMinMaxX(0,10);
        reg.enableInspector(false);
        
        XAxis norm = new XAxis("dm");
        reg.addDrawable(norm);
        
        luzCol = new Circle(5,0,10);
        reg.addDrawable(luzCol);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight= 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = .7;
        gridBagConstraints.weighty = 0;
        add(reg, gridBagConstraints);
        
        regPopMenu = new PopupMenu(this);
        regPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.title.1","Edit"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.tip.1","Edit the selected object"));
    }
    
    public static void main(String args[])
    {
        javax.swing.JFrame dummy = new javax.swing.JFrame();
        dummy.getContentPane().add(new Animation());
        dummy.pack();
        dummy.show();
    }
    
    //Chegaram novas amostras!
    public void newSamples(NewExpDataEvent evt)
    {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
        {
            //sample, canal
            if(model.getValueAt(i,1) != null)
            {
                balanca.updateAngle(model.getValueAt(i,1).getValue().getFloatValue());
                updateRegua();
            }
        }
    }
    
    public void updateRegua()
    {
        luzCol.setX(balanca.getLuzX()/10+balanca.getReguaSize()/20);
        reg.setPreferredMinMaxX(0, balanca.getReguaSize()/10);
        reg.repaint();
    }
    
    //Queremos fazer alguma coisa quandos os dados acabarem?
    public void dataModelEnded()
    {
        constTextBox.getStyle().setEdgeColor(java.awt.Color.BLUE);
        reg.addDrawable(constTextBox);
        constTextBox.setX(reg.getPreferredXMax()/2);
        reg.repaint();
    }
    
    //Queremos fazer alguma coisa quandos acontecer um erro?
    public void dataModelError()
    {
    }
    
    //Queremos fazer alguma coisa quando for dado o start e existirem dados?
    public void dataModelStarted()
    {
    }
    
    //Queremos fazer alguma coisa quando for dado o start e ainda não existirem dados?
    //Eu garanto que quando chegamos a este estado, já existe o header da experiência!
    public void dataModelStartedNoData()
    {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        //boolean expGType = Boolean.getBoolean(header.getSelectedHardwareParameterValue("expGType"));
        boolean expGType = header.getSelectedHardwareParameterValue("expGType").trim().equals("1")?true:false;
        int angInit = Integer.parseInt(header.getSelectedHardwareParameterValue("angInit"));
        int l = Integer.parseInt(header.getSelectedHardwareParameterValue("l"));
        float s0 = Float.parseFloat(header.getSelectedHardwareParameterValue("s0"));
        float d = Float.parseFloat(header.getSelectedHardwareParameterValue("d"));
        int targetSize = Integer.parseInt(header.getSelectedHardwareParameterValue("targetSize"));
        
        constTextBox = new InteractiveText("C : "+CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("c")),3)+" ; K : "+CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("k")),3)+" ; G : "+CGCustomizer.trimDecimalN(Float.parseFloat(header.getSelectedHardwareParameterValue("g")),3));
        
        if (!expGType)
            balanca.cMode();
        
        balanca.config(angInit*Math.PI/180, l, s0*1000, d*1000, targetSize);
        updateRegua();
    }
    
    //Queremos fazer alguma coisa quando for dado parado?
    public void dataModelStoped()
    {
    }
    
    //Queremos fazer alguma coisa em estado de espera?
    public void dataModelWaiting()
    {
    }
    
    public javax.swing.JComponent getDisplay()
    {
        return this;
    }
    
    //O icon associado a este painel!
    public javax.swing.Icon getIcon()
    {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    }
    
    public javax.swing.JMenuBar getMenuBar()
    {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar()
    {
        return null;
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
    
    public void mouseClicked(java.awt.event.MouseEvent e)
    {
        if (javax.swing.SwingUtilities.isRightMouseButton(e))
            regPopMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    public void mouseEntered(java.awt.event.MouseEvent e)
    {
    }
    
    public void mouseExited(java.awt.event.MouseEvent e)
    {
    }
    
    public void mousePressed(java.awt.event.MouseEvent e)
    {
    }
    
    public void mouseReleased(java.awt.event.MouseEvent e)
    {
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (e.getActionCommand().equalsIgnoreCase("Edit"))
        {
            balanca.setReguaSize(PopupMenu.dialog(ReCResourceBundle.findStringOrDefault("ReCExpCG$rec.exp.customizer.balanca.title.15","Edit Target Ruler (cm)"), ReCResourceBundle.findStringOrDefault("ReCExpCG$rec.exp.customizer.balanca.tip.15","Ruler length"), ReCResourceBundle.findStringOrDefault("ReCExpCG$rec.exp.customizer.balanca.tip.16","Accept the configuration"), (int) Math.round(reg.getPreferredXMax()*10), new int[]
            {100,500,50,50}));
            constTextBox.setX(balanca.getReguaSize()/20);
            balanca.repaint();
            updateRegua();
        }
    }
}
