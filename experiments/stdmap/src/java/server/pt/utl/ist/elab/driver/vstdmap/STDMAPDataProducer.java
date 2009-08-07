/*
 * STDMAPDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vstdmap;

/**
 *
 * @author  nomead
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class STDMAPDataProducer extends VirtualBaseDataSource implements Runnable
{
    //O numero de canais(de dados) que existem!
    private int NUM_CHANNELS = 5;
    
    private int tbs = 1000; // = forceDt ; anima
    private int nSamples;
    
    private double k;
    private double theta, dTheta;
    private double iMapa, dIMapa;
    private double pcor;
    private int nTheta, nIMapa, iter;
    
    private byte pixSize = 1;
    private int w = 0, h = 0;
    private boolean staticImg;
    
    private double mass = 0, thetaDot, length, force;
    
    private boolean stopped = false;
    private VirtualBaseDriver driver = null;
    
    //HIST + MAP
    public STDMAPDataProducer(VirtualBaseDriver driver, float k, float theta, float iMapa, int iter, float pcor, int w, int h, byte pixSize, boolean staticImg)
    {
        this.driver = driver;
        this.nSamples = 1;
        
        this.k = k;
        this.theta = theta;
        this.dTheta = 0;
        this.iMapa = iMapa;
        this.dIMapa = 0;
        this.pcor = pcor;
        this.nTheta = 1;
        this.nIMapa = 1;
        this.iter = iter;
        
        this.w = w;
        this.h = h;
        this.pixSize = pixSize;
        this.staticImg = staticImg;
    }
    //ANIMA + MAP
    public STDMAPDataProducer(VirtualBaseDriver driver, float theta, float thetaDot, float length, float mass, float force, int forceDt, int nSamples)
    {
        this.driver = driver;
        this.nSamples = nSamples;
        System.out.println("Animation with " + nSamples + "nsamples");
        this.theta = theta;
        this.thetaDot = thetaDot;
        this.length = length;
        this.mass = mass;
        this.force = force;
        this.tbs = forceDt;
    }
    //JUST MAP
    public STDMAPDataProducer(VirtualBaseDriver driver, float k, float theta, int nTheta, float dTheta, float iMapa, int nIMapa, float dIMapa, int iter, float pcor, int w, int h, byte pixSize, boolean staticImg)
    {
        this.driver = driver;
        this.nSamples = 1;
        
        this.k = k;
        this.theta = theta;
        this.dTheta = dTheta;
        this.iMapa = iMapa;
        this.dIMapa = dIMapa;
        this.pcor = pcor;
        this.nTheta = nTheta;
        this.nIMapa = nIMapa;
        this.iter = iter;
        
        this.w = w;
        this.h = h;
        this.pixSize = pixSize;
        this.staticImg = staticImg;
    }
    //TESTE
    public byte [] getMapaPixs()
    {
        BufferedImage tempImg = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = tempImg.getGraphics();
        
        for (int t = 0; t < nTheta; t++)
        {
            for (int i = 0; i < nIMapa; i++)
            {
                float c1 = (float) Math.abs((theta+iMapa)%1);
                float c2 = (float) Math.abs((iMapa+pcor*Math.sin(theta))%1);
                float c3 = (float) Math.abs((c1+c2)%1);
                
                g.setColor(new java.awt.Color(c1, c2, c3));
                
                for (int j = 0; j < iter; j++)
                {
                    
                    iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
                    theta = (theta + iMapa)%(2*Math.PI);
                    
                    if (iMapa < 0)
                        iMapa += 2*Math.PI;
                    if (theta  < 0)
                        theta += 2*Math.PI;
                    
                    g.drawOval((int) Math.round(theta*w/(2*Math.PI)), h-(int)Math.round(iMapa*h/(2*Math.PI)), this.pixSize, this.pixSize);
                    
                }
                iMapa += dIMapa;
            }
            theta += dTheta;
        }
        
        
        g.dispose();
        
        PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);
        
        try
        {
            pg.grabPixels();
        } catch (InterruptedException e)
        {}
        
        return (byte[]) pg.getPixels();
    }
    //TESTE
    public byte [] getIMapaData()
    {
        float [] iMapaData = new float[iter];
        int dataCounter = 0;
        
        for (int j = 0; j < iter; j++)
        {
            
            iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
            theta = (theta + iMapa)%(2*Math.PI);
            
            if (iMapa < 0)
                iMapa += 2*Math.PI;
            if (theta  < 0)
                theta += 2*Math.PI;
            
            iMapaData[dataCounter++] = (float)iMapa;
        }
        
        
        return ByteUtil.floatArrayToByteArray(iMapaData);
    }
    //TESTE
    private Thread animaThread;
    private pt.utl.ist.elab.client.vstdmap.displays.Animation stdAn;
    //TESTE
    public void startAnima(pt.utl.ist.elab.client.vstdmap.displays.Animation stdAn)
    {
        this.stdAn = stdAn;
        animaThread = new Thread(this);
        animaThread.start();
    }
    //TESTE
    private pt.utl.ist.elab.client.vstdmap.displays.STDHistogram stdHist;
    public void startHist(pt.utl.ist.elab.client.vstdmap.displays.STDHistogram stdHist)
    {
        this.stdHist = stdHist;
        animaThread = new Thread(this);
        animaThread.start();
    }
    //TESTE
    private pt.utl.ist.elab.client.vstdmap.displays.STDMAPImage stdIm;
    public void startM(pt.utl.ist.elab.client.vstdmap.displays.STDMAPImage stdIm)
    {
        this.stdIm = stdIm;
        animaThread = new Thread(this);
        animaThread.start();
    }
    //TESTE
    public byte[] getMapaData()
    {
        
        int dataCounter = 0;
        float [] mData = new float[nTheta*nIMapa*iter*2];
        
        for (int t = 0; t < nTheta; t++)
        {
            for (int i = 0; i < nIMapa; i++)
            {
                for (int j = 0; j < iter; j++)
                {
                    iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
                    theta = (theta + iMapa)%(2*Math.PI);
                    
                    if (iMapa < 0)
                        iMapa += 2*Math.PI;
                    if (theta  < 0)
                        theta += 2*Math.PI;
                    
                    mData[dataCounter++] = (float) theta;
                    mData[dataCounter++] = (float) iMapa;
                    
                }
                iMapa += dIMapa;
            }
            theta += dTheta;
        }
        
        return ByteUtil.floatArrayToByteArray(mData);
    }
    
    //TESTE
    public void run()
    {
        int currentSample = 0;
        int counter = 0;
        
        while (animaThread == Thread.currentThread() && currentSample < nSamples)
        {
            if (mass != 0)
            {
                
                theta += (thetaDot/10d);
                
                if ((counter*100)%tbs == 0)
                {
                    thetaDot += (force*Math.sin(theta)/(mass*length*length));
                    currentSample++;
                    
                    if (stdHist != null)
                    {
                        double tempTd = thetaDot;
                        if (tempTd  < 0)
                            tempTd = Math.abs(tempTd + 2*Math.PI);
                        
                        stdHist.append(Math.abs(thetaDot%(2*Math.PI)));
                    }
                    else if (stdIm != null)
                    {
                        double tempT = theta;
                        double tempTd = thetaDot;
                        if (tempTd  < 0)
                            tempTd = Math.abs(tempTd + 2*Math.PI);
                        if (tempT < 0)
                            tempT = Math.abs(tempT + 2*Math.PI);
                        
                        stdIm.setData((float)tempT,(float)tempTd);
                        stdIm.drawImageNonStatic((float)(tempT%(2*Math.PI)),(float)(tempTd%(2*Math.PI)));
                        stdIm.repaint();
                    }
                    if (stdAn != null)
                        stdAn.move(theta,thetaDot);
                }
                else if (stdAn != null)
                    stdAn.setTheta(theta);
                
                counter++;
                try
                {
                    animaThread.sleep(100);
                } catch (InterruptedException e)
                {}
            }
            if (stdHist != null)
            {stdHist.repaint();}
        }
    }
    
    
    private class ProducerThread extends Thread
    {
        private int currentSample = 0;
        
        public void run()
        {
            try
            {
                sleep(1000);
                
                PhysicsValue[] value;
                
                if (mass != 0)
                { //Anima
                    System.out.println("nsamples = " + nSamples);
                    while(currentSample < nSamples && !stopped)
                    {
                        System.out.println("1");
                        theta += (thetaDot/10d);
                        if ((currentSample*100)%tbs == 0)
                        {
                            System.out.println("1a");
                            thetaDot += force*Math.sin(theta)/(mass*length*length);
                            
                            value = new PhysicsValue[NUM_CHANNELS];
                            
                            value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) theta),
                            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                            );
                            value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) thetaDot),
                            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                            );
                            
                            addDataRow(value);
                            currentSample++;
                        }
                        else
                        {
                            value = new PhysicsValue[NUM_CHANNELS];
                            
                            value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) theta),
                            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                            );
                            
                            System.out.println("1b");
                            addDataRow(value);
                            currentSample++;
                        }
                        sleep(100);
                    }
                }
                else if (staticImg)
                {
                    
                    if (nTheta*nIMapa == 1)
                    { //histogram
                        int dataCounter = 0;
                        float [] iMapaData = new float[iter];
                        
                        while(currentSample < nSamples && !stopped)
                        {
                            System.out.println("2");
                            BufferedImage tempImg = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
                            Graphics g = tempImg.getGraphics();
                            
                            
                            float c1 = (float) Math.abs((theta+iMapa)%1);
                            float c2 = (float) Math.abs((iMapa+pcor*Math.sin(theta))%1);
                            float c3 = (float) Math.abs((c1+c2)%1);
                            
                            g.setColor(new java.awt.Color(c1, c2, c3));
                            for (int j = 0; j < iter; j++)
                            {
                                
                                iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
                                theta = (theta + iMapa)%(2*Math.PI);
                                
                                if (iMapa < 0)
                                    iMapa += 2*Math.PI;
                                if (theta  < 0)
                                    theta += 2*Math.PI;
                                
                                iMapaData[dataCounter++] = (float)iMapa;
                                g.drawOval((int) Math.round(theta*w/(2*Math.PI)), h-(int)Math.round(iMapa*h/(2*Math.PI)), pixSize, pixSize);
                            }
                            
                            
                            g.dispose();
                            
                            PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);
                            
                            try
                            {
                                pg.grabPixels();
                            } catch (InterruptedException e)
                            {}
                            
                            value = new PhysicsValue[NUM_CHANNELS];
                            
                            value[2] = new PhysicsValue(PhysicsValFactory.fromByteArray((byte[]) pg.getPixels(), "data/raw"), null, com.linkare.rec.data.Multiplier.none);
                            value[3] = new PhysicsValue(PhysicsValFactory.fromByteArray(ByteUtil.floatArrayToByteArray(iMapaData), "data/raw"), null, com.linkare.rec.data.Multiplier.none);
                            
                            addDataRow(value);
                            currentSample++;
                        }
                    }
                    else
                    {
                        while(currentSample < nSamples && !stopped)
                        {
                            System.out.println("3");
                            BufferedImage tempImg = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
                            Graphics g = tempImg.getGraphics();
                            
                            for (int t = 0; t < nTheta; t++)
                            {
                                for (int i = 0; i < nIMapa; i++)
                                {
                                    float c1 = (float) Math.abs((theta+iMapa)%1);
                                    float c2 = (float) Math.abs((iMapa+pcor*Math.sin(theta))%1);
                                    float c3 = (float) Math.abs((c1+c2)%1);
                                    
                                    g.setColor(new java.awt.Color(c1, c2, c3));
                                    for (int j = 0; j < iter; j++)
                                    {
                                        
                                        iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
                                        theta = (theta + iMapa)%(2*Math.PI);
                                        
                                        if (iMapa < 0)
                                            iMapa += 2*Math.PI;
                                        if (theta  < 0)
                                            theta += 2*Math.PI;
                                        
                                        g.drawOval((int) Math.round(theta*w/(2*Math.PI)), h-(int)Math.round(iMapa*h/(2*Math.PI)), pixSize, pixSize);
                                    }
                                    iMapa += dIMapa;
                                }
                                theta += dTheta;
                            }
                            
                            g.dispose();
                            
                            PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);
                            
                            try
                            {
                                pg.grabPixels();
                            } catch (InterruptedException e)
                            {}
                            
                            value = new PhysicsValue[NUM_CHANNELS];
                            
                            value[2] = new PhysicsValue(PhysicsValFactory.fromByteArray((byte[]) pg.getPixels(), "data/raw"), null, com.linkare.rec.data.Multiplier.none);
                            
                            addDataRow(value);
                            currentSample++;
                        }
                    }
                }
                else
                {
                    while(currentSample < nSamples && !stopped)
                    {
                        int dataCounter = 0;
                        float [] mData = new float[nTheta*nIMapa*iter*2];
                        
                        for (int t = 0; t < nTheta; t++)
                        {
                            for (int i = 0; i < nIMapa; i++)
                            {
                                for (int j = 0; j < iter; j++)
                                {
                                    
                                    iMapa = (iMapa + k*Math.sin(theta))%(2*Math.PI);
                                    theta = (theta + iMapa)%(2*Math.PI);
                                    
                                    if (iMapa < 0)
                                        iMapa += 2*Math.PI;
                                    if (theta  < 0)
                                        theta += 2*Math.PI;
                                    
                                    mData[dataCounter++] = (float) theta;
                                    mData[dataCounter++] = (float) iMapa;
                                }
                                iMapa += dIMapa;
                            }
                            theta += dTheta;
                        }
                        value = new PhysicsValue[NUM_CHANNELS];
                        
                        PhysicsVal val = PhysicsValFactory.fromByteArray(ByteUtil.floatArrayToByteArray(mData), "data/raw");
                        value[4] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
                        
                        System.out.println("Length  = " + value[4].getValue().getByteArrayValue().getData().length);
                        
                        addDataRow(value);
                        currentSample++;
                    }
                }
                
                join(100);
                endProduction();
                
                driver.stopVirtualHardware();
            }
            catch(InterruptedException ie)
            {}
            catch(java.lang.OutOfMemoryError ome)
            {
                ome.printStackTrace();
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
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
    
}
