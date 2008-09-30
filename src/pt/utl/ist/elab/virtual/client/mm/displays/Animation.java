/*
 * Animation.java
 *
 * Created on October 16, 2004, 8:48 PM
 */

package pt.utl.ist.elab.virtual.client.mm.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */

import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.displayejs.InteractiveSpring;
import org.opensourcephysics.displayejs.Resolution;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;

public class Animation extends DrawingPanel implements ExpDataDisplay, ExpDataModelListener
{
    private InteractiveSpring spring = null;
    private DrawableShape rectangle = null;
    private DrawableShape wall = null;
    private DrawableShape ground = null;
    
    /** Creates a new instance of Animation */
    public Animation()
    {
        setPreferredMinMax(0, 40, -10, 10);
        
        wall = DrawableShape.createRectangle(0, 0, 4, 8);
        wall.setMarkerColor(java.awt.Color.GRAY, java.awt.Color.GRAY);
        ground = DrawableShape.createRectangle(20, -2, 40, 1);
        ground.setMarkerColor(java.awt.Color.GRAY, java.awt.Color.GRAY);
        spring = new InteractiveSpring(1);
        spring.setXY(2, 0);
        spring.setSizeXY(20, 0);
        spring.setResolution(new Resolution(12,12));
        rectangle = DrawableShape.createRectangle(24.5, 0, 5, 3);
        
        addDrawable(wall);
        addDrawable(ground);
        addDrawable(spring);
        addDrawable(rectangle);
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
        //Esta é a maneira clássica de tirar as amostras dos canais que nos interessam!
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
        {
            //sample, canal
            if(model.getValueAt(i,1) != null)
            {
                spring.setSizeX(20 + model.getValueAt(i,1).getValue().getFloatValue());
                rectangle.setX(24.5 + model.getValueAt(i,1).getValue().getFloatValue());                
                repaint();
            }
        }
    }        
    
    //Queremos fazer alguma coisa quandos os dados acabarem?
    public void dataModelEnded()
    {
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
        //vamos lá ver o que o utilizador escolheu, para colocar a animação nas posições iniciais correctas!
        float xini = Float.parseFloat(header.getSelectedHardwareParameterValue("xini"));
        spring.setSizeX(xini);
        rectangle.setX(24.5 + xini);
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
    
    //Este código é SEMPRE igual e tem de existir!
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model)
    {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
}
