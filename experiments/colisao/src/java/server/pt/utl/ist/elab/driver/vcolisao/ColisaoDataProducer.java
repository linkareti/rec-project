/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vcolisao;

/**
 *
 * @author  Emanuel Antunes
 */
//import utils.Esfera;
import pt.utl.ist.elab.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class ColisaoDataProducer extends VirtualBaseDataSource {
    //O numero de canais(de dados) que existem!
    private int NUM_CHANNELS = 14;
    
    private float x1, y1, x2, y2, v1, v2, r1, r2, m1, m2;
    private int a, elasticCollision;
    
    private int tbs = 100;
    private int nSamples = 10;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    Esfera[] esferas = new Esfera[2];
    //Aproveitamos para inicializar todas as variáveis logo no construtor...
    public ColisaoDataProducer(VirtualBaseDriver driver, float v1, float v2, float r1, float r2, float m1, float m2, int a, int elasticCollision , int tbs, int nSamples) {
        this.driver = driver;
        this.x1=0;
        this.y1=0;
        this.x2=0;
        this.y2=0;
        this.v1 = v1;
        this.v2 = v2;
        this.m1=m1;
        this.m2=m2;
        this.r1=r1;
        this.r2=r2;
        this.a=a;
        this.elasticCollision = elasticCollision;
        this.tbs = tbs;
        this.nSamples = nSamples;
        
        esferas[0]= new Esfera(x1,y1,v1,0,r1,m1);
        esferas[1]= new Esfera(x2,y2,v2*(-Math.cos(Math.toRadians(a))),v2*(Math.sin(Math.toRadians(a))),r2,m2);
    }
    
    public Esfera computeInitialPosition1(Esfera esfera){
        double dt= roundToInt(nSamples/2);
        dt = dt*tbs;
        double xf = (r1+r2)*Math.cos(Math.toRadians(a));
        double yf = (r1+r2)*-Math.sin(Math.toRadians(a));
        esfera.x = xf - esfera.vX * dt;
        esfera.y = yf - esfera.vY * dt;
        return esfera;   }
    
    
    public Esfera computeInitialPosition(Esfera esfera){
        double dt= roundToInt(nSamples/2);
        dt = dt*tbs;
        esfera.x = - esfera.vX * dt;
        esfera.y = - esfera.vY * dt;
        return esfera;   }
    
    
    public boolean checkColision(Esfera[] spheres){
        /*if( Esfera.magnitude(new double[] {spheres[0].x - spheres[1].x , spheres[0].y - spheres[1].y })
        <( spheres[0].raio + spheres[1].raio )){return true;}
        else{return false;}*/
        return (  Esfera.magnitude(new double[] {spheres[0].x - spheres[1].x , spheres[0].y - spheres[1].y })
        <=( spheres[0].raio + spheres[1].raio )  );
    }
    
    public Esfera[] advance(Esfera[] espheras, double dt ){
        
        espheras[0]= new Esfera(espheras[0].x + espheras[0].vX*dt , espheras[0].y + espheras[0].vY*dt, espheras[0].vX, espheras[0].vY, espheras[0].raio, espheras[0].m);
        espheras[1]= new Esfera(espheras[1].x + espheras[1].vX*dt , espheras[1].y + espheras[1].vY*dt, espheras[1].vX, espheras[1].vY, espheras[1].raio, espheras[1].m);
        
        return espheras; }
    
    public Esfera[] colidir(Esfera[] esferas_){
        switch(elasticCollision){
            case(0):{
                double vY1 = esferas_[0].vY;
                double vY2 = esferas_[1].vY;
                double vX1 = esferas_[0].vX;
                double vX2 = esferas_[1].vX;
                
                esferas_[0] = new Esfera(esferas_[0].x,esferas_[0].y,(esferas_[0].m*vX1 + esferas_[1].m*vX2)/(esferas_[0].m + esferas_[1].m)
                ,(esferas_[0].m*vY1 + esferas_[1].m*vY2)/(esferas_[0].m + esferas_[1].m), esferas_[0].raio, esferas_[0].m);
                
                esferas_[1] = new Esfera(esferas_[1].x,esferas_[1].y,(esferas_[0].m*vX1 + esferas_[1].m*vX2)/(esferas_[0].m + esferas_[1].m)
                ,(esferas_[0].m*vY1 + esferas_[1].m*vY2)/(esferas_[0].m + esferas_[1].m), esferas_[1].raio, esferas_[1].m);
                break;}
            
            case(1):{
                double[] dR = new double[] {esferas_[0].x-esferas_[1].x, esferas_[0].y -esferas_[1].y};
                dR = Esfera.normalize(dR);
                double a1 =  Esfera.dot( new double[] {esferas_[0].vX, esferas_[0].vY}, dR);
                double a2 =  Esfera.dot( new double[] {esferas_[1].vX, esferas_[1].vY}, dR);
                double optP = (2*(a1-a2)) / (esferas_[0].m + esferas_[1].m);
                
                esferas_[0] = new Esfera(esferas_[0].x,esferas_[0].y, (esferas_[0].vX - (optP * esferas_[1].m *dR[0])),
                (esferas_[0].vY - (optP * esferas_[1].m *dR[1])), esferas_[0].raio, esferas_[0].m);
                
                esferas_[1] = new Esfera(esferas_[1].x,esferas_[1].y, (esferas_[1].vX + (optP * esferas_[0].m *dR[0])),
                (esferas_[1].vY + (optP * esferas_[0].m *dR[1])), esferas_[1].raio, esferas_[1].m);
                break;}  }
        
        return esferas_;   }
    
    /**
     *Arredonda um double para int de acordo com as casas decimais
     */
    public static int roundToInt(double number){
        int rounded=0;
        if ( number % (int)number < 0.5 ) {rounded = (int)number;}
        else if (number % (int)number >= 0.5 ){rounded = (int)number+1;}  return rounded;}
    
    //Este é o processo que nos vai simular e criar as amostras para enviar ao cliente!
    private class ProducerThread extends Thread {
        private int currentSample = 0;
        private float time = 0;
        
        public void run() {
            try {
                sleep(1000);
                boolean colisao=false;
                //   computeInitialPosition(esferas[0]);
                //     computeInitialPosition1(esferas[1]);
                
                double dt = tbs/1000;
                double moduloV1=0;
                double moduloV2=0;
                
                int count=1;
            //    int tbs = 50;
                //                int nSamples = nSamples;
                double dt_= nSamples/2;
                dt_ = dt_*dt;
                
                // Determinar as posi��es iniciais:
                double xf = (esferas[0].raio+esferas[1].raio)*Math.cos(Math.toRadians(a));
                double yf = (esferas[0].raio+esferas[1].raio)*(-Math.sin(Math.toRadians(a)));
                esferas[0]= new Esfera(   - esferas[0].vX * dt_,    - esferas[0].vY * dt_, esferas[0].vX,  esferas[0].vY, esferas[0].raio, esferas[0].m);
                esferas[1]= new Esfera(xf - esferas[1].vX * dt_, yf - esferas[1].vY * dt_, esferas[1].vX,  esferas[1].vY, esferas[1].raio, esferas[1].m);
                
                //Enquanto a amostra actual for menor do que o numero de amostras pedido pelo cliente E ninguém tiver parado a exp...produz dados
                while(currentSample <= nSamples && !stopped) {
                    
                    if(!colisao && checkColision(esferas)){
                        esferas =  colidir(esferas);
                        colisao = true;}
                    
                    esferas = advance(esferas, dt);
                    
                    moduloV1 = Esfera.magnitude(new double[] { esferas[0].vX , esferas[0].vY });
                    moduloV2 = Esfera.magnitude(new double[] { esferas[1].vX , esferas[1].vY });
                    
                    //envia as amostra calculadas!
                    //1- cria um array com o numero de canais existentes!
                    PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];
                    
                    //envia no canal CORRESPONDENTE!!! o valor
                    //tempo
                    value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                    );
                    //x1
                    value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float)esferas[0].x),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //y1
                    value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float)esferas[0].y),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //x2
                    value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float)esferas[1].x),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //y2
                    value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float)esferas[1].y),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //px1
                    value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[0].vX*esferas[0].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //py1
                    value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[0].vY*esferas[0].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //px2
                    value[7] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[1].vX*esferas[1].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //py2
                    value[8] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[1].vY*esferas[1].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //pxt
                    value[9] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[0].vX*esferas[0].m+esferas[1].vX*esferas[1].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //pyt
                    value[10] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(esferas[0].vY*esferas[0].m+esferas[1].vY*esferas[1].m)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //Ec1
                    value[11] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(0.5*esferas[0].m*moduloV1*moduloV1)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //Ec2
                    value[12] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(0.5*esferas[1].m*moduloV2*moduloV2)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    //Ect
                    value[13] = new PhysicsValue(PhysicsValFactory.fromFloat((float)(0.5*esferas[1].m*moduloV2*moduloV2 + 0.5*esferas[0].m*moduloV1*moduloV1)),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                    );
                    
                    addDataRow(value);
                    
                    //incrementa o tempo
                    time += dt;
                    
                    //dorme o tbs que o utilizador pediu...
                    sleep(tbs);
                    
                    currentSample++;
                }
                
                join(100);
                endProduction();
                
                driver.stopVirtualHardware();
                
            }
            catch(InterruptedException ie) {
            }
            
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
    
    public static void main(String args[]) {
        /*DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10, 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000);
        dp.startProduction();*/
    }
    
    public void stopNow() {
        stopped = true;
        setDataSourceStoped();
    }
}
