/*
 * Pend2MDataProducer.java
 *
 * Created on 27 de Fevereiro de 2005, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.pend2m;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */

import org.opensourcephysics.numerics.*;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;

public class Pend2MDataProducer extends VirtualBaseDataSource implements ODE {
    
    private int NUM_CHANNELS = 7;
    
    private int tbs = 1000;
    private int nSamples;
    
    private double l1;
    private double l2;
    private double m1;
    private double m2;
    private double w;
    private double fase;
    private double a;
    
    private double g;
    
    private double [] state;
    private ODESolver rk4;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    public Pend2MDataProducer(VirtualBaseDriver driver, float theta, float phi, float thetaDot, float phiDot, float l1, float l2, float m1, float m2, float w, float fase, float a, float g, int tbs, int nSamples){
        this.driver = driver;
        this.nSamples = nSamples;
        this.tbs = tbs;
        
        this.l1 = l1;
        this.l2 = l2;
        this.m1 = m1;
        this.m2 = m2;
        this.w = w;
        this.fase = fase;
        this.a = a;
        this.g = g;
        
        this.state = new double[]{theta,thetaDot,phi,phiDot,0};
        
        rk4 = new RK4(this);
        rk4.initialize(1e-3);
    }
    
    
    private class ProducerThread extends Thread {
        private int currentSample = 0;
        
        public void run() {
            try {
                sleep(1000);
                
                PhysicsValue[] value;
                int counter = 0;
                
                while(currentSample < nSamples && !stopped){
                    
                    rk4.step();
                    if (counter%tbs == 0){
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
                        value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[4]),
                        getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
                        getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
                        );
                        value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) ((-a*m2*w*w*Math.cos(state[2])*Math.cos(state[2]-state[0])*Math.sin(fase+state[4]*w)+a*(m1+m2)*w*w*Math.cos(state[0])*Math.sin(fase+state[4]*w)+g*m2*Math.cos(state[2]-state[0])*Math.sin(state[2])-g*(m1+m2)*Math.sin(state[0])+m2*Math.sin(state[2]-state[0])*(l2*state[3]*state[3]+l1*Math.cos(state[2]-state[0])*state[1]*state[1]))/(l1*(m1+m2-m2*Math.pow(Math.cos(state[2]-state[0]),2))))),
                        getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
                        getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                        );
                        value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (-(Math.sin(state[2]-state[0])*(l2*m2*Math.cos(state[2]-state[0])*state[3]*state[3]+(m1+m2)*(g*Math.cos(state[0])+a*w*w*Math.sin(fase+state[4]*w)*Math.sin(state[0])+l1*state[1]*state[1])))/(l2*(m1+m2-m2*Math.pow(Math.cos(state[2]-state[0]),2))))),
                        getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
                        getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier()
                        );
                        
                        addDataRow(value);
                        
                        sleep(tbs);
                        currentSample++;
                    }
                    counter++;
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
        setDataSourceEnded();
    }
    
    public void stopNow(){
        stopped = true;
        setDataSourceStoped();
    }
    
    public void getRate(double[] state, double[] rate) {
        //theta
        rate[0] = state[1];
        rate[1] = (-a*m2*w*w*Math.cos(state[2])*Math.cos(state[2]-state[0])*Math.sin(fase+state[4]*w)+a*(m1+m2)*w*w*Math.cos(state[0])*Math.sin(fase+state[4]*w)+g*m2*Math.cos(state[2]-state[0])*Math.sin(state[2])-g*(m1+m2)*Math.sin(state[0])+m2*Math.sin(state[2]-state[0])*(l2*state[3]*state[3]+l1*Math.cos(state[2]-state[0])*state[1]*state[1]))/(l1*(m1+m2-m2*Math.pow(Math.cos(state[2]-state[0]),2)));
        
        //phi
        rate[2] = state[3];
        rate[3] = -(Math.sin(state[2]-state[0])*(l2*m2*Math.cos(state[2]-state[0])*state[3]*state[3]+(m1+m2)*(g*Math.cos(state[0])+a*w*w*Math.sin(fase+state[4]*w)*Math.sin(state[0])+l1*state[1]*state[1])))/(l2*(m1+m2-m2*Math.pow(Math.cos(state[2]-state[0]),2)));
        
        //t
        rate[4] = 1;
    }
    
    public double[] getState() {
        return state;
    }
    
}
