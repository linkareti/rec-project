/*
 * Animation.java
 *
 * Created on 20 de Fevereiro de 2005, 22:10
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 *
 * @author n0dP2
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

public class Animation extends pt.utl.ist.elab.client.vm3.DrawableMolas
                                    implements ExpDataDisplay, ExpDataModelListener {
    
    public Animation(){
        super(null);
    }
    
    public static void main (String[] args){
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.getContentPane().add(new Animation());
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.show();
    }
    
    //Chegaram novas amostras!
    public void newSamples(NewExpDataEvent evt) {
        //Esta � a maneira cl�ssica de tirar as amostras dos canais que nos interessam!
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            //sample, canal
            if(model.getValueAt(i,3) != null && model.getValueAt(i,4) != null) {
                float x=model.getValueAt(i,3).getValue().getFloatValue();
                float y=model.getValueAt(i,4).getValue().getFloatValue();
                desenha(x,y);
            }
        }
    }
    
    private void desenha(float x,float y){
        bola.setXY(x,y);
        mola1.setSizeX(x-5);
        mola1.setSizeY(y-10);
        mola2.setSizeX(x);
        mola2.setSizeY(y);
        mola3.setSizeX(x-10);
        mola3.setSizeY(y);
        repaint();
    }
    
    //Queremos fazer alguma coisa quandos os dados acabarem?
    public void dataModelEnded() {
    }
    
    //Queremos fazer alguma coisa quandos acontecer um erro?
    public void dataModelError() {
    }
    
    //Queremos fazer alguma coisa quando for dado o start e existirem dados?
    public void dataModelStarted() {
    }
    
    //Queremos fazer alguma coisa quando for dado o start e ainda n�o existirem dados?
    //Eu garanto que quando chegamos a este estado, j� existe o header da experi�ncia!
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        //vamos l� ver o que o utilizador escolheu, para colocar a anima��o nas posi��es iniciais correctas!
        float x = Float.parseFloat(header.getSelectedHardwareParameterValue("x0"));
        float y = Float.parseFloat(header.getSelectedHardwareParameterValue("y0"));
        desenha(x,y);
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
    
    //Este c�digo � SEMPRE igual e tem de existir!
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
}
