/*
 * CartPole.java
 *
 * Created on 16 de Fevereiro de 2005, 23:36
 */

package pt.utl.ist.elab.virtual.driver.cartpole;

import org.opensourcephysics.numerics.*;
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
public class CartPoleDataProducer extends VirtualBaseDataSource implements ODE {
    
    private double uCart, uPole;
    private double mCart, mPole;
    private double g;
    private double l;
    
    private double act;
    
    private double time;
    
    private double[] state; //theta, dtheta/dt, x, dx/dt, t
    private ODESolver rk4;
    
    private int NUM_CHANNELS = 8;
    
    private int tbs = 1;
    private int nSamples;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    private double kp = 12d;
    private double ki = 5d;
    private double kd = 5d;
    private double sucAngle;
    
    private boolean failureActive = false;
    private boolean successActive = false;
    
    private int allowedFailures;
    private int failures;
    private double xMax;
    private double failureTime;
    private double failureLaps;
    
    private double timeForSuc;
    private double sucTime;
    private double sucTimeBest;
    
    public CartPoleDataProducer(VirtualBaseDriver driver, double x, double x_dot, double theta, double theta_dot, double [] u, double [] m, double g, double l, double action, int tbs, int nSamples){
        this.driver = driver;
        this.uCart = u[0];
        this.uPole = u[1];
        
        this.mCart = m[0];
        this.mPole = m[1];
        
        this.g = g;
        this.l = l/2d;
        
        this.tbs = tbs;
        this.nSamples = nSamples;
        
        state = new double[]{x, x_dot, theta, theta_dot, 0};
        
        time = 0;
        act = action;
        
        rk4 = new RK4(this);
        rk4.initialize((double) tbs/1000d);
    }
    
    public void initializePID(double _kp, double _ki, double _kd){
        kp = _kp;
        ki = _ki;
        kd = _kd;
    }
    
    public void initializeSuccess(double _sucAngle, double _timeForSuc){
        successActive = true;
        sucAngle = _sucAngle;
        timeForSuc = _timeForSuc;
        sucTime = 0;
        sucTimeBest = 0;
    }
    
    public void initializeFailure(int _allowedFailures, double _xMax, double _failureTime, double _failureLaps){
        failureActive = true;
        allowedFailures = _allowedFailures;
        xMax = _xMax;
        failureTime = _failureTime;
        failureLaps = _failureLaps;
        failures = 0;
    }
    
    public void getRate(double[] state, double[] rate) {
        rate[0] = state[1]; //dx/dt
        rate[1] = (act+mPole*l*(Math.pow(state[3],2)*Math.sin(state[2])-((Math.cos(state[2])*((-act-mPole*l*Math.pow(state[3],2)*Math.sin(state[2])+uCart*sgn(state[1]))/(mCart+mPole))+g*Math.sin(state[2])+uPole*state[3]/(mPole*l))/(l*(4d/3d-mPole*Math.pow(Math.cos(state[2]),2)/(mCart+mPole))))*Math.cos(state[2]))-uCart*sgn(state[1]))/(mCart+mPole); //d2x/dt2
        
        rate[2] = state[3]; //dtheta/dt
        rate[3] = (Math.cos(state[2])*((-act-mPole*l*Math.pow(state[3],2)*Math.sin(state[2])+uCart*sgn(state[1]))/(mCart+mPole))+g*Math.sin(state[2])+uPole*state[3]/(mPole*l))/(l*(4d/3d-mPole*Math.pow(Math.cos(state[2]),2)/(mCart+mPole))); //d2theta/dt2
        
        rate[4] = 1; //t
    }
    
    private int sgn(double val){
        return (int) (val/Math.abs(val));
    }
    
    public double[] getState() {
        return state;
    }
    
    public double step(double action){
        act = action;
        time += rk4.step();
        return time;
    }
    
    //TESTE
    /*public void start(pt.utl.ist.elab.virtual.client.cartpole.displays.Animation an){
        this.an = an;
        animaThread = new Thread(this);
        animaThread.start();
    }
    
    
    //TESTE
    pt.utl.ist.elab.virtual.client.cartpole.displays.Animation an;
    private Thread animaThread;
    public void run() {
        int currentSample = 0;
        double mem = 0;
        
        while (animaThread == Thread.currentThread() && !stopped && currentSample < nSamples){
            
            mem += (Math.toRadians(0)-state[2])*(double) tbs/1000d;
            double action = -(kp*(Math.toRadians(0) -state[2])+kd*(-state[3])+ki*mem);
            step(action);
            
            //ENVIAR (x,theta,dx/dt,dtheta/dt,action)
            //cartPole.move(state[0]*10, state[2], state[1]*10, state[3]*10,action*10);
            an.move(state[0], state[2], state[1], state[3],action,state[4]);
            currentSample++;
            
            //update();
            
            if (failureActive && (Math.abs(state[0])*100 > xMax || state[2]/(2*Math.PI) > failureLaps*(failures+1) || failureTime*(failures+1) < state[4])){
                failures++;
                
                if (allowedFailures <= failures){
                    System.out.println("FALHOU");
                }
            }
            else if (successActive){
                if (Math.abs(state[2]%(2*Math.PI)) < Math.toRadians(sucAngle) || 2*Math.PI-Math.abs(state[2]%(2*Math.PI)) < Math.toRadians(sucAngle))
                    sucTime += (double) tbs/1000d;
                else
                    sucTime = 0;
                
                sucTimeBest = Math.max(sucTime,sucTimeBest);
                
                if (sucTime >= timeForSuc){
                    System.out.println("SUCESSO");
                }
            }
            
            try{
                animaThread.sleep(tbs);
            } catch (InterruptedException e){}
        }
        
    }*/
    
    private class ProducerThread extends Thread {
        private int currentSample = 0;
        
        public void run() {
            try {
                sleep(1000);
                
                PhysicsValue[] value;
                double mem = 0;
                
                while (!stopped && currentSample < nSamples){
                    
                    mem += (Math.toRadians(0)-state[2])*(double) tbs/1000d;
                    double action = -(kp*(Math.toRadians(0) -state[2])+kd*(-state[3])+ki*mem);
                    step(action);
                    
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
                    value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) action),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                    );
                    
                    addDataRow(value);
                    sleep(tbs);
                    currentSample++;
                    
                    if (failureActive && (Math.abs(state[0]) > xMax || state[2]/(2*Math.PI) > failureLaps*(failures+1) || failureTime*(failures+1) < state[4])){
                        failures++;
                        
                        if (allowedFailures <= failures){
                            value = new PhysicsValue[NUM_CHANNELS];
                            value[7] = new PhysicsValue(PhysicsValFactory.fromBoolean(true),
                            getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
                            getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier()
                            );
                            addDataRow(value);
                            currentSample++;
                            successActive = false;
                            failureActive = false;
                        }
                    }
                    else if (successActive){
                        if (Math.abs(state[2]%(2*Math.PI)) < Math.toRadians(sucAngle) || 2*Math.PI-Math.abs(state[2]%(2*Math.PI)) < Math.toRadians(sucAngle))
                            sucTime += (double) tbs/1000d;
                        else
                            sucTime = 0;
                        
                        sucTimeBest = Math.max(sucTime,sucTimeBest);
                        
                        if (sucTime >= timeForSuc){
                            value = new PhysicsValue[NUM_CHANNELS];
                            value[6] = new PhysicsValue(PhysicsValFactory.fromBoolean(true),
                            getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
                            getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier()
                            );
                            addDataRow(value);
                            currentSample++;
                            successActive = false;
                            failureActive = false;
                        }
                    }
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
    
}