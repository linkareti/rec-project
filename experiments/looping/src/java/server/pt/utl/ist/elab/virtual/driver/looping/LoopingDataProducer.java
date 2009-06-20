/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.looping;

/**
 *
 * @author  Emanuel Antunes
 */

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.virtual.driver.*;
import org.opensourcephysics.numerics.*;
import java.util.logging.*;

public class LoopingDataProducer extends VirtualBaseDataSource implements ODE {
    //O numero de canais(de dados) que existem!
    private int NUM_CHANNELS = 6;
    
    /*Fields Onde estas? :*/
    public static final int TAG_DROP =0;
    public static final int TAG_WAVE =1;
    public static final int TAG_PLANE =2;
    public static final int TAG_LOOP_UP =3;
    public static final int TAG_LOOP_TOP =4;
    public static final int TAG_LOOP_DOWN =5;
    public int where_tag=0;
    
    private double[] state = new double[]
    {0d, 0d, 0d};//x,v,t
    private double x_,y_;
    private double xi = 0;
    private double h1 = 7;
    private double h2 = 1.0;
    private double r1 = 3;
    private double vi = 0;
    private double g = 9.8;
    private int tbs = 10;
    private int nSamples = 1000;
    private boolean turningBack=false;
    private boolean passLoop;
    private boolean passVertical = false;
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    private ODESolver odeSolver = null;
    LagrangianoLoop loop;
    private ODESolver odeSolver2 = null;
    
    // double time =0;
    
    //Aproveitamos para inicializar todas as variaveis logo no construtor...
    public LoopingDataProducer(VirtualBaseDriver driver, float xi, float h1, float h2, float r1, float vi, float g, int tbs, int nSamples) {
        this.driver = driver;
        this.h1=h1;
        this.h2=h2;
        this.r1=r1;
        this.g=g;
        this.tbs = tbs;
        this.nSamples = nSamples;
        
        passLoop = false;
        state[0] = xi;
        state[1]= vi;
        
        odeSolver = new RK45(this);
        odeSolver.initialize(tbs / 1000D);
    }
    
    public int getWhereTag(){return where_tag;}
    
    public double[] gimmeEnergy(){
        
        double energiaCin, energiaPot, energiaMec;
        
        double aux = 0;
        double aux_=0;
        
        if(getWhereTag()==TAG_DROP){
            aux = h1*0.5*Math.sin(state[0])*state[1];
            aux_=h1;
        }
        else if(getWhereTag()==TAG_WAVE){
            aux = h2*0.5*Math.sin(state[0])*state[1];
            aux_=h2;
        }
        else{
            aux=0;
            aux_=0;
        }
        
        energiaCin=0.5*1*(state[1]*state[1]+(aux*aux));
        energiaPot=1*g*aux_*0.5*(Math.cos(state[0])+1);
        energiaMec = energiaCin + energiaPot;
        
        return new double[]{energiaCin, energiaPot, energiaMec};
    }
    
    public double[] gimmeEnergyLoop(){
        
        double energiaCin, energiaPot, energiaMec;
        
        
        energiaCin=0.5*1*r1*r1*loop.getState()[1]*loop.getState()[1];
        energiaPot=1*g*r1*(1-Math.cos(loop.getState()[0]));
        energiaMec = energiaCin + energiaPot;
        
        energiaMec = energiaCin + energiaPot;
        return new double[]{energiaCin, energiaPot, energiaMec};
    }
    
    public double[] getState(){  return state; }
    
    public void setState(double[]state){this.state=state;}
    
    public void getRate(double[] state, double[] rate ) {
        
        rate[0] = state[1];//dx/dt
        
        if(state[0]<=Math.PI){ //primeira descida
            rate[1]=(  2*h1*g*Math.sin(state[0]) - h1*h1*Math.cos(state[0])*Math.sin(state[0])*state[1]*state[1] )/
            ( 4+h1*h1*Math.sin(state[0])*Math.sin(state[0]) );
            where_tag= TAG_DROP;}
        else  if(state[0]>Math.PI && state[0]<=3*Math.PI){ //primeira lomba
            rate[1]=(  2*h2*g*Math.sin(state[0]) - h2*h2*Math.cos(state[0])*Math.sin(state[0])*state[1]*state[1] )/
            ( 4+h2*h2*Math.sin(state[0])*Math.sin(state[0]) );
            where_tag= TAG_WAVE;}
        else if(state[0]>3*Math.PI && state[0]<=3*Math.PI+r1){ //zona recta depois da lomba e antes do looping
            rate[1]=0;
            where_tag= TAG_PLANE;}
        else if(passLoop){rate[1]=0;}
        
        rate[2] = 1;//dt/dt
    }
    
    //Este e' o processo que nos vai simular e criar as amostras para enviar ao cliente!
    private class ProducerThread extends Thread {
        private int currentSample = 0;
        private float time = 0;
        
        public void setData(){
            //envia as amostra calculadas!
            //1- cria um array com o numero de canais existentes!
            PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
            
            //envia no canal CORRESPONDENTE!!! o valor
            value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
            );
            value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float)x_),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
            );
            value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float)y_),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
            );
            value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergy()[0]),
            getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
            );
            value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergy()[1]),
            getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
            );
            value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergy()[2]),
            getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
            );
            
            
            addDataRow(value);
            
        }
        
        public void run() {
            try {
                sleep(1000);
                
                //Enquanto a amostra actual for menor do que o numero de amostras pedido pelo cliente E ninguém tiver parado a exp...produz dados
                //while(currentSample < nSamples && !stopped) {
                //envia as amostra calculadas!
                //1- cria um array com o numero de canais existentes!
                //        PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
                
                do{
                    whereAreYou();
                    
                    if(x_<0){stopped = true;}
                    
                    setData();
                    
                    //incrementa o tempo
                    time += tbs / 1000F;
                    odeSolver.step();
                    
                    //dorme o tbs que o utilizador pediu...
                    sleep(tbs);
                    
                    currentSample++;
                    
                    
                }while(x_<=3*Math.PI+r1 && currentSample <= nSamples && !stopped);
                
                loop = new LagrangianoLoop(0d, state[1]/r1, r1, g);
                odeSolver2= new RK45(loop);
                odeSolver2.initialize(odeSolver.getStepSize());
                
                do{
                    x_= 3*Math.PI +r1 + r1*Math.sin(loop.getState()[0]);
                    y_= r1*(1-Math.cos(loop.getState()[0]));
                    
                    if(loop.getState()[1]<0){turningBack=true;}
                    
                    //envia as amostra calculadas!
                    //1- cria um array com o numero de canais existentes!
                    PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
                    
                    //envia no canal CORRESPONDENTE!!! o valor
                    value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                    );
                    value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float)x_),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float)y_),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                    );
                    value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergyLoop()[0]),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
                    );
                    value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergyLoop()[1]),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
                    );
                    value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float)gimmeEnergyLoop()[2]),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                    );
                    
                    
                    addDataRow(value);
                    
                    
                    //incrementa o tempo
                    time += tbs / 1000F;
                    odeSolver.step();
                    
                    //dorme o tbs que o utilizador pediu...
                    sleep(tbs);
                    
                    currentSample++;
                    
                }  while(loop.getState()[0]<=2*Math.PI && loop.getState()[0]>0 && currentSample <= nSamples && !stopped);
                
                if(turningBack){  getState()[1]=-getState()[1];}
                passLoop= true;
                
                while(x_<3*Math.PI+3*r1 && currentSample < nSamples && !stopped){
                    whereAreYou();
                    
                    //envia as amostra calculadas!
                    setData();
                    
                    //incrementa o tempo
                    time += tbs / 1000F;
                    odeSolver.step();
                    
                    //dorme o tbs que o utilizador pediu...
                    sleep(tbs);
                    
                    currentSample++;
                }
                
                //  }
                
                join(100);
                endProduction();
                
                driver.stopVirtualHardware();
                
            }
            catch(InterruptedException ie) {
            }
            
        }
    }
    
    public void whereAreYou(){
        
        double a = x_-(3*Math.PI+r1);
        int n =0;
        
        x_= this.getState()[0];
        n = this.getWhereTag();
        
        switch(n){
            
            case TAG_DROP:{ y_=h1*0.5*(Math.cos(x_)+1);
            break;}
            
            case TAG_WAVE:{ y_=h2*0.5*(Math.cos(x_)+1);
            break;}
            
            case TAG_LOOP_UP:{
                a = x_-(3*Math.PI+r1);
                y_= r1-Math.sqrt(r1*r1 - a*a );
                break;}
            
            case TAG_LOOP_DOWN:{
                a = x_-(3*Math.PI+r1);
                y_= r1-Math.sqrt(r1*r1 - a*a );
                break;}
            
            case TAG_LOOP_TOP:{
                a = x_-(3*Math.PI+r1);
                y_= r1+Math.sqrt(r1*r1 - a*a );
                break;}
            
            case TAG_PLANE:{ y_=0;
            break;}
        }
    }
    
    public void startProduction() {
        stopped = false;
        new ProducerThread().start();
    }
    
    public void endProduction() {
        stopped = true;
        setDataSourceEnded();
    }
    
    /*public static void main(String args[]) {
        LoopingDataProducer dp = new LoopingDataProducer(null, 0f,10f,5f,3f,0.1f, 9.8f, 80,10000 );
        dp.startProduction();
    }*/
    
    public void stopNow() {
        stopped = true;
        setDataSourceStoped();
    }
}