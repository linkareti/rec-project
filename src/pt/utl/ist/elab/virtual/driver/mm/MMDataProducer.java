/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.mm;

/**
 *
 * @author  andre
 */

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.virtual.driver.*;
import org.opensourcephysics.numerics.*;
import java.util.logging.*;

public class MMDataProducer extends VirtualBaseDataSource implements ODE
{
    //O numero de canais(de dados) que existem!
    private int NUM_CHANNELS = 3;
    
    private double[] state = new double[]
    {0.05, 0.0, 0.0};//x,v,t
    private double amp = 1.0;
    private double kmola = 1;
    private double massa = 1.0;
    private double atrito = 0;
    private double xini = 0;
    private int tbs = 100;
    private int nSamples = 10;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    private ODESolver odeSolver = null;
    
    public double[] getState()
    {
        return state;
    }
    
    public void getRate(double[] state, double[] rate )
    {
        rate[0] = state[1];//dx/dt
        rate[1] = - kmola/massa * state[0] - atrito * state[1];//dv/dt=dx2/dt2
        rate[2] = 1;//dt/dt
    }
    
    //Aproveitamos para inicializar todas as variáveis logo no construtor...
    public MMDataProducer(VirtualBaseDriver driver, float kmola, float massa, float atrito, float xini, int tbs, int nSamples)
    {
        this.driver = driver;
        this.kmola = kmola;
        this.massa = massa;
        this.atrito = atrito;
        this.xini = xini;
        this.tbs = tbs;
        this.nSamples = nSamples;
        
        state[0] = xini;
        
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
                    value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                    );
                    value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float)state[0]),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float)state[1]),
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
        /*DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10, 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000);
        dp.startProduction();*/
    }
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
