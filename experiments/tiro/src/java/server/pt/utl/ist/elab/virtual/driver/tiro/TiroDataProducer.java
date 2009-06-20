/*
 * TiroDataProducer.java
 *
 * Created on 16 de Fevereiro de 2005, 23:36
 */

package pt.utl.ist.elab.virtual.driver.tiro;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;

/**
 *
 * @author  nomead
 */
public class TiroDataProducer extends VirtualBaseDataSource implements Runnable {
    
    
    private double g;
    private double w,h;
    
    private double[] state; //theta, dtheta/dt, x, dx/dt, t
    
    private int NUM_CHANNELS = 7;
    
    private double dt = .1;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    
    public TiroDataProducer(VirtualBaseDriver driver, double _w, double _h, double v, double theta, double _g){
        this.driver = driver;
        
        g = _g;
        w = _w;
        h = _h;
        
        state = new double[]{0,v*Math.cos(theta),0,v*Math.sin(theta),0,h,0};
    }
    
    public void step(){
        state[4] += dt;
        state[0] += state[1]*dt;
        
        state[2] += state[3]*dt-g*dt*dt/2;
        state[3] -= g*dt;
        
        state[5] += state[6]*dt-g*dt*dt/2;
        state[6] -= g*dt;
    }

    //TESTE
    public void start(pt.utl.ist.elab.virtual.client.tiro.displays.Animation an){
        this.an = an;
        animaThread = new Thread(this);
        animaThread.start();
    }
    
    
    //TESTE
    pt.utl.ist.elab.virtual.client.tiro.displays.Animation an;
    private Thread animaThread;
    public void run() {
        while (animaThread == Thread.currentThread() && !stopped && state[0] < w){
            step();
            an.move(state[0],state[2],state[1],state[3],state[5]);
            System.out.println(state[4]);
            try{
                animaThread.sleep(Math.round(dt*1000));
            } catch (InterruptedException e){}
        }
    }
    
    private class ProducerThread extends Thread {
        private int currentSample = 0;
        
        public void run() {
            try {
                sleep(1000);
                
                PhysicsValue[] value;
                
                while (!stopped && state[0] < w && currentSample < 3000){
                    
                    if (state[0]+state[1]*dt > w)
                        dt /= 10;
                    step();
                    value = new PhysicsValue[NUM_CHANNELS];
                    
                    value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                    );
                    value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                    );
                    value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[3]),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
                    );
                    value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[5]),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
                    );
                    value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(Math.pow(state[0]-w,2)+Math.pow(state[2]-state[5],2))),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                    );
                    value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[4]),
                    getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier()
                    );
                    
                    addDataRow(value);
                    sleep(Math.round(dt*1000));
                    currentSample++;
                    
                }
                
                
                join(100);
                endProduction();
                
                driver.stopVirtualHardware();
            }
            catch(InterruptedException ie){}
        }
    }
    
    public void startProduction(){
        stopped = false;
        new ProducerThread().start();
    }
    
    public void endProduction(){
        stopped = true;
        setDataSourceStoped();
    }
    
    public void stopNow(){
        stopped = true;
        setDataSourceStoped();
    }
    
}