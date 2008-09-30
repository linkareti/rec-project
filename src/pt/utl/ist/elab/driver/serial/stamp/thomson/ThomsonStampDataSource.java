/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.thomson;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.thomson.processors.StampThomsonProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ThomsonStampDataSource extends AbstractStampDataSource
{
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public ThomsonStampDataSource()
    {
    }
    
    private final int NUM_CHANNELS = 5;
    
    public void processDataCommand(StampCommand cmd)
    {
        if(stopped)
            return;
        
        if(stopped || cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(StampThomsonProcessor.COMMAND_IDENTIFIER))
        {
            
            int corrente;
            int tensaoacel;
            int tensaodef;
            PhysicsValue[] values=new PhysicsValue[NUM_CHANNELS];
            try
            {
                corrente = ((Integer)cmd.getCommandData(StampThomsonProcessor.CORRENTE)).intValue();
                tensaoacel = ((Integer)cmd.getCommandData(StampThomsonProcessor.TENSAO)).intValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            if(defmag)
                tensaodef = 0;
            else
                tensaodef = tensaoacel;
            
            values[0] = new PhysicsValue(PhysicsValFactory.fromInt(corrente),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
            );
            values[1] = new PhysicsValue(PhysicsValFactory.fromInt(tensaoacel),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
            );
            values[2] = new PhysicsValue(PhysicsValFactory.fromInt(tensaodef),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
            );
            super.addDataRow(values);
        }
    }
    
    public void sendImages(java.awt.Image[] images)
    {
        if(video)
        {
            //The last video frame is sent in the same sample of the image
            for(int i=0; i<images.length - 1; i++)
            {
                PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];
                byte[] dataImage=image2ByteArray(images[i]);
                PhysicsVal val = PhysicsValFactory.fromByteArray(dataImage,"image/jpeg");
                values[4] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
                super.addDataRow(values);
            }
        }
        
        PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];
        byte[] dataImage = image2ByteArray(images[images.length - 1]);
        PhysicsVal val = PhysicsValFactory.fromByteArray(dataImage, "image/jpeg");
        values[3] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
        if(video)
            values[4] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
        
        super.addDataRow(values);
        
        try
        {
            Thread.currentThread().sleep(1500);
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        
        setDataSourceEnded();
    }
    
    public byte[] image2ByteArray(java.awt.Image image)
    {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        javax.imageio.stream.MemoryCacheImageOutputStream mcios = new javax.imageio.stream.MemoryCacheImageOutputStream(baos);
        if(image==null)
            return null;
        int imageWidth = image.getWidth(null), imageHeight = image.getHeight(null);
        java.awt.image.BufferedImage bImage = new java.awt.image.BufferedImage(imageWidth,imageHeight,java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = bImage.createGraphics();
        while( !g2d.drawImage(image,0,0,imageWidth, imageHeight, null) )
        {
        }//wait for the full image to be available
        try
        {
            javax.imageio.ImageIO.write(bImage, "jpg", mcios);
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
        catch(com.sun.image.codec.jpeg.ImageFormatException e)
        {
            e.printStackTrace();
        }
        //create the ByteArrayInputStream and get the Byte[]
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        byte[] byteArray = new byte[bais.available()];
        
        try
        {
            bais.read(byteArray);
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
        
        return byteArray;
    }//image2ByteArray(Image image)
    
    private boolean defmag = false;
    private boolean video = false;
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        if(config.getSelectedHardwareParameterValue("modo").equalsIgnoreCase("defmag"))
            defmag = true;
        if(Integer.parseInt(config.getSelectedHardwareParameterValue("video")) == 1)
            video = true;
        
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        //TODO CHECK THIS OUT. Stopping the data source while sending images might be a huge prob.
        //setDataSourceStoped();
    }
}
