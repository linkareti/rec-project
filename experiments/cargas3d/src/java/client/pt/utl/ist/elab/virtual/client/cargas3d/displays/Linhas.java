

package pt.utl.ist.elab.virtual.client.cargas3d.displays;

/**
 *
 * @author  n0dP2
 */

import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;
import pt.utl.ist.elab.virtual.client.cargas3d.Sistema;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

public class Linhas extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener
{
    java.util.ArrayList sist;
    Painel painel=new Painel();
    public Linhas(){
        add(painel);
//        org.opensourcephysics.displayejs.InteractiveParticle part = new org.opensourcephysics.displayejs.InteractiveParticle();
//        part.setSizeXYZ(1,1,1);
//        part.setXYZ(1,2,3);
//        painel.addDrawable(part);
    }
    
    public static void main(String args[])
    {
        javax.swing.JFrame dummy = new javax.swing.JFrame();
        dummy.getContentPane().add(new Linhas());
        dummy.setDefaultCloseOperation(dummy.EXIT_ON_CLOSE);
        dummy.pack();
        dummy.show();
    }
    
    //Chegaram novas amostras! 
    public void newSamples(NewExpDataEvent evt)
    {
        painel.clear();
        setCargasHeader();
        addCargas();
        //Esta ? a maneira cl?ssica de tirar as amostras dos canais que nos interessam!
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
        {
     
            //sample, canal
            if(model.getValueAt(i,0) != null)
            {
                java.util.ArrayList linhas = 
                        (java.util.ArrayList)ByteUtil.byteArrayToObject(model.getValueAt(i,0).getValue().getByteArrayValue().getData());
                toPanelLinhas(painel,linhas);
                painel.repaint();
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
    
    //Queremos fazer alguma coisa quando for dado o start e ainda n?o existirem dados?
    //Eu garanto que quando chegamos a este estado, j? existe o header da experi?ncia!
    public void dataModelStartedNoData()
    {
        setCargasHeader();
        addCargas();
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
    
    //Este c?digo ? SEMPRE igual e tem de existir!
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model)
    {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
    
     private void setCargasHeader(){
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        sist=Sistema.stringToSistema(header.getSelectedHardwareParameterValue("Sistema"));
    }
    
    private void addCargas(){
        for(int i=0;i<sist.size();i++){
            painel.addDrawable((org.opensourcephysics.displayejs.InteractiveCharge)sist.get(i));
        }
        painel.repaint();
    }
    
    private void toPanelLinhas(org.opensourcephysics.displayejs.DrawingPanel3D panel_, java.util.ArrayList linhas_){
        for(int i=0;i<linhas_.size();i++){
            org.opensourcephysics.displayejs.InteractiveTrace linha_ = new org.opensourcephysics.displayejs.InteractiveTrace();
            String Q_ = (String)((java.util.ArrayList)linhas_.get(i)).get(0);
            if(Q_=="neg"){
                linha_.getStyle().setEdgeColor(new java.awt.Color(140,140,255));
            }
            if(Q_=="pos"){
                linha_.getStyle().setEdgeColor(new java.awt.Color(255,180,180));
            }
            if(Q_=="nul"){
                linha_.getStyle().setEdgeColor(java.awt.Color.white);
            }
            
            for(int j=1;j<((java.util.ArrayList)linhas_.get(i)).size();j++){
                Float[] pontos_ =(Float[]) (((java.util.ArrayList)linhas_.get(i)).get(j));
                linha_.addPoint((double)pontos_[0].floatValue(),(double)pontos_[1].floatValue(),(double)pontos_[2].floatValue());
            }
            
            panel_.addDrawable(linha_);
        }
    }
}
