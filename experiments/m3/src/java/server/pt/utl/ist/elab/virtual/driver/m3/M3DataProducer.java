/*
 * M3DataProducer.java
 *
 * Created on 20 de Fevereiro de 2005, 19:10
 */

package pt.utl.ist.elab.virtual.driver.m3;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.virtual.driver.*;
import org.opensourcephysics.numerics.*;
import java.util.logging.*;

/**
 *
 * @author n0dP2
 */
public class M3DataProducer extends VirtualBaseDataSource implements ODE {
    
    private int NUM_CHANNELS = 5;
    private double a = 10;  //lado da caixa
    private double l1 = 5, l2 = Math.sqrt(50), l3 = Math.sqrt(50);   //comprimento natural das molas
    private double[] state = new double[5];
    private double k1;
    private double k2;
    private double k3;
    private double massa;
    private double x0;
    private double y0;
    private int tbs;
    private int nSamples;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    private ODESolver odeSolver = null;
    
    public double[] getState()
    {
        return state;
    }
    
    public void getRate(double[] state, double[] rate )
    {
        rate[0]=state[1];
        rate[1] = (k1*(a/2-state[0])*(-l1*Math.pow((Math.pow((a-state[2]),2)+Math.pow((a/2-state[0]),2)),-0.5)+1.0)+
                  k2*state[0]*(l2*Math.pow((Math.pow(state[0],2)+Math.pow(state[2],2)),-0.5)-1.0)+
                  k3*(a-state[0])*(-l3*Math.pow((Math.pow((state[2]),2)+Math.pow((a-state[0]),2)),-0.5)+1.0))/massa;
        rate[2]=state[3];
        rate[3] = (k1*(a-state[2])*(-l1*Math.pow((Math.pow((a-state[2]),2)+Math.pow((a/2-state[0]),2)),-0.5)+1.0)+
                  k2*state[2]*(l2*Math.pow((Math.pow(state[0],2)+Math.pow(state[2],2)),-0.5)-1.0)+
                  k3*(state[2])*(l3*Math.pow((Math.pow((state[2]),2)+Math.pow((a-state[0]),2)),-0.5)-1.0))/massa;
        rate[4]=1;
    }
    
    //Aproveitamos para inicializar todas as variáveis logo no construtor...
    public M3DataProducer(VirtualBaseDriver driver, float massa, float k1, float k2, float k3, float x0, float y0,int tbs, int nSamples)
    {
        this.driver = driver;
        this.massa=massa;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.x0 = x0;
        this.y0 = y0;
        this.tbs = tbs;
        this.nSamples = nSamples;
        
        state[0] = x0;
        state[1] = 0;
        state[2] = y0;
        state[3] = 0;
        state[4] = 0;
        
        odeSolver = new RK45(this);
        odeSolver.initialize(tbs / 1000D);
    }
    
    //Este é o processo que nos vai simular e criar as amostras para enviar ao cliente!
    private class ProducerThread extends Thread
    {
        private int currentSample = 0;
        private float time = 0;
        
        public void run()
        {
            try
            {
                sleep(1000);
                
                //Enquanto a amostra actual for menor do que o numero de amostras pedido pelo cliente E ninguém tiver parado a exp...produz dados
                while(currentSample < nSamples && !stopped)
                {
                    //envia as amostra calculadas!
                    //1- cria um array com o numero de canais existentes!
                    PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
                    
                    //envia no canal CORRESPONDENTE!!! o valor 
                    //envie-se o tempo
                    value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                    );
                    //envie-se a posicao
                    value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float)Math.sqrt(state[0]*state[0]+state[2]*state[2])),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //envie-se a velocidade
                    value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float)Math.sqrt(state[1]*state[1]+state[3]*state[3])),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                    );
                    //envie-se x
                    value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float)state[0]),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                    );
                    //envie-se y
                    value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float)state[2]),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                    );
                    
                    addDataRow(value);
                    
                    //incrementa o tempo
                    time += tbs / 1000F;
                    odeSolver.step();
                    
                    //dorme o tbs que o utilizador pediu...                                                                                                                                                                                                                                                         
                    sleep(tbs);
                    
                    currentSample++;
                }
                
                join(100);
                endProduction();
                
                driver.stopVirtualHardware();
                
            }
            catch(InterruptedException ie)
            {
            }
            
        }
    }
    
    public void startProduction()
    {
        stopped = false;
        new ProducerThread().start();
    }
    
    public void endProduction()
    {
        stopped = true;
        setDataSourceEnded();
    }
    
    public static void main(String args[])
    {
       
    }
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
