/*
 * RobotServer.java
 *
 * Created on 14 de Dezembro de 2002, 14:52
 */

package pt.utl.ist.elab.driver.meteo;

import com.linkare.rec.impl.data.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import java.util.logging.*;

import java.sql.*;

import pt.utl.ist.cfn.serial.*;
import pt.utl.ist.cfn.sql.*;
import pt.utl.ist.cfn.math.*;

/**
 *
 * @author  Andr�
*/


public class MeteoDataProducer extends BaseDataSource
{
    public static final int HOURLY = 0;
    public static final int DAILY = 1;
    public static final int MONTHLY = 2;
    public static final int YEARLY = 3;
    private static final int NUM_CHANNELS = 9; 
    private int resolution = 0;
    
    
    private String startDate = "";
    private String stopDate = "";
    
    private boolean stopProduction = false;
    
    private boolean[] selected = new boolean[NUM_CHANNELS-1];
    
    private SerialComm sc = null;
    
    /**sql*/
    private Connection conn = null;
    
    private MeteoDriver driver = null;
        
    public MeteoDataProducer(MeteoDriver driver)
    {
        conn = driver.getSQLConnection();
        this.driver = driver;
    }
    
    private class ProducerThread extends Thread
    {
        public void run()
        {
            try
            {   
                sleep(1000);
                sendSamples(0, new float[8]);
                
                String resString = "";
                if(resolution == HOURLY)
                {
                    resString = "HOUR";
                }
                else if(resolution == DAILY)
                {
                    resString = "DAYOFMONTH";
                }
                else if(resolution == MONTHLY)
                {
                    resString = "MONTH";
                }
                else
                {
                    resString = "YEAR";
                }
                
                PreparedStatement ps = null;
                ps = conn.prepareStatement("select *," + resString + "(time_of_acq) as delta, HOUR(time_of_acq) as delta_hour, DAYOFMONTH(time_of_acq) as delta_day, MONTH(time_of_acq) as delta_month, YEAR(time_of_acq) as delta_year from meteo where time_of_acq >='"
                                                + startDate + "' AND time_of_acq<='" + stopDate + "' order by time_of_acq;");
                
                ResultSet rs = ps.executeQuery();
                
                String lastValue = "";
                
                long time = 0;
                
                float prec = 0;
                float temp = 0;
                float humid = 0;
                float dirVento = 0;
                float velVento = 0;
                float cond = 0;
                float pressao = 0;
                float lum = 0;
                
                int counter = 1;
                int cSample = 1;
                
                if(rs.next())
                {
                    lastValue = rs.getString("delta").trim();
                    prec = rs.getFloat("prec");
                    temp = rs.getFloat("temp");
                    humid = rs.getFloat("humid");
                    dirVento = rs.getFloat("dirVento");
                    velVento = rs.getFloat("velVento");
                    cond = rs.getFloat("cond");
                    pressao = rs.getFloat("pressao");
                    lum = rs.getFloat("lum");                                       
                }
                                
                while(rs.next() && !stopProduction && cSample < nSamples)
                {
                    System.out.println(cSample + "<" + nSamples);

                    if(!lastValue.equalsIgnoreCase(rs.getString("delta").trim()))
                        //|| rs.isLast())
                    {          
                        cSample++;
                        
                        lastValue = rs.getString("delta").trim();
                        //prec /= counter;
                        temp /= counter;
                        humid /= counter;
                        dirVento /= counter;
                        velVento /= counter;
                        cond /= counter;
                        pressao /= counter;
                        //lum /= counter;
                        lum = getLumValue(lum/counter);
                                  
                        rs.previous();
                        
                        int hour = Integer.parseInt(rs.getString("delta_hour"));
                        int day = Integer.parseInt(rs.getString("delta_day"));                         
                        int month = Integer.parseInt(rs.getString("delta_month")) - 1;
                        int year = Integer.parseInt(rs.getString("delta_year"));

                        if(resolution == HOURLY)
                        {
                            time = new java.util.GregorianCalendar(year, month, day, hour, 0).getTime().getTime();
                        }
                        else if(resolution == DAILY)
                        {
                            time = new java.util.GregorianCalendar(year, month, day, 0, 0).getTime().getTime();
                        }
                        else if(resolution == MONTHLY)
                        {
                            time = new java.util.GregorianCalendar(year, month, 1, 0, 0).getTime().getTime();
                        }                            
                        else
                        {
                            time = new java.util.GregorianCalendar(year, 1, 1, 0, 0).getTime().getTime();
                        }
                        
                        rs.next();
                        
                        float[] vals = new float[NUM_CHANNELS-1];
                        vals[0] = temp;                        
                        vals[1] = prec;                       
                        vals[2] = dirVento;
                        vals[3] = velVento;
                        vals[4] = humid;
                        vals[5] = cond;
                        vals[6] = lum;
                        vals[7] = pressao;
                        
                        sendSamples(time, vals);
                                                                        
                        prec = 0;
                        temp = 0;
                        humid = 0;
                        dirVento = 0;
                        velVento = 0;
                        cond = 0;
                        pressao = 0;
                        lum = 0;                        
                        counter = 0;
                        
                        sleep(200);
                    }    

                    prec += rs.getFloat("prec");
                    temp += rs.getFloat("temp");
                    humid += rs.getFloat("humid");
                    dirVento += rs.getFloat("dirVento");
                    velVento += rs.getFloat("velVento");
                    cond += rs.getFloat("cond");
                    pressao += rs.getFloat("pressao");
                    lum += rs.getFloat("lum");
                    counter++;
                    
                    if(rs.isLast())
                    {
                        //prec /= counter;
                        temp /= counter;
                        humid /= counter;
                        dirVento /= counter;
                        velVento /= counter;
                        cond /= counter;
                        pressao /= counter;
                        //lum /= counter;
                        lum = getLumValue(lum/counter);
                                                
                        int hour = Integer.parseInt(rs.getString("delta_hour"));
                        int day = Integer.parseInt(rs.getString("delta_day"));                         
                        int month = Integer.parseInt(rs.getString("delta_month")) - 1;
                        int year = Integer.parseInt(rs.getString("delta_year"));

                        if(resolution == HOURLY)
                        {
                            time = new java.util.GregorianCalendar(year, month, day, hour, 0).getTime().getTime();
                        }
                        else if(resolution == DAILY)
                        {
                            time = new java.util.GregorianCalendar(year, month, day, 0, 0).getTime().getTime();
                        }
                        else if(resolution == MONTHLY)
                        {
                            time = new java.util.GregorianCalendar(year, month, 1, 0, 0).getTime().getTime();
                        }                            
                        else
                        {
                            time = new java.util.GregorianCalendar(year, 1, 1, 0, 0).getTime().getTime();
                        }
                        
                        float[] vals = new float[NUM_CHANNELS-1];
                        vals[0] = temp;                        
                        vals[1] = prec;                        
                        vals[2] = dirVento;
                        vals[3] = velVento;
                        vals[4] = humid;
                        vals[5] = cond;
                        vals[6] = lum;
                        vals[7] = pressao;
                        
                        sendSamples(time, vals);                        
                    }                    
                }
                rs.close();
                ps.close();
            }
            catch(SQLException sqle)
            {
                System.out.println(sqle.getMessage());
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
            
            try
            {                
                /**sleep a little bit...before setting run to false*/
                sleep(1000);
                setDataEnded();
                driver.stop(null);
                join(100);
            }
            catch(com.linkare.rec.acquisition.IncorrectStateException ise)
            {
                ise.printStackTrace();
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }
    
    private void setDataEnded()
    {
        super.setDataSourceEnded();
    }
    
    private void sendSamples(long time, float[] vals)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];        
        
        if(time == 0)
        {
            try
            {                
                /**Try to get last acquisition values from current_meteo*/
                ps = conn.prepareStatement("select * from current_meteo;");                
                rs = ps.executeQuery();            
                /**if it can't get it, try to get from meteo*/
                if(!rs.last())
                {
                    ps = conn.prepareStatement("select * from meteo;");                
                    rs = ps.executeQuery();                             
                }                
                if(rs.last())
                {
                    vals[0] = rs.getFloat("temp");                    
                    vals[1] = MathUtils.getArraySumValue(driver.getRainArray()).floatValue();
                    vals[2] = rs.getFloat("dirVento");
                    vals[3] = rs.getFloat("velVento");
                    vals[4] = rs.getFloat("humid");                    
                    vals[5] = rs.getFloat("cond");
                    vals[6] = getLumValue(rs.getFloat("lum"));
                    vals[7] = rs.getFloat("pressao");                                         
                }
            }
            catch(SQLException sqle)
            {
                sqle.printStackTrace();
            }
        }
        else
        {
            values[0] = new PhysicsValue(PhysicsValFactory.fromLong(time),
                                      getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),                                                          
                                      getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                                    );
        
        }
        
        if(selected[0])
        {
            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[0]),
                                          getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                                        );
        }
        if(selected[1])
        {
            values[2] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[1]),
                                          getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                                        );                        
        }
        if(selected[2])
        {
            values[3] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[2]),
                                          getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
                                        );
        }
        if(selected[3])
        {
            values[4] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[3]),
                                          getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
                                        );
        }
        if(selected[4])
        {
            values[5] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[4]),
                                          getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                                        );
        }
        if(selected[5])
        {
            values[6] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[5]),
                                          getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier()
                                       );
        }
        if(selected[6])
        {
            values[7] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[6]),
                                          getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier()
                                        );
        }
        if(selected[7])
        {
            values[8] = new PhysicsValue(PhysicsValFactory.fromFloat(vals[7]),
                                          getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),                                                          
                                          getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier()
                                        );                        
        }
        /**Send...............................*/
        addDataRow(values);   
    }
    
    private int nSamples = 0;
    
    public int checkNSamples(String startDate, String stopDate, int resolution)
    {        
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.resolution = resolution;        
        
        PreparedStatement ps = null;
        ResultSet rs = null; 
               
        nSamples = 0;
        try
        {
            if(resolution == HOURLY)
            {
                ps = conn.prepareStatement("select count(time_of_acq) from meteo where time_of_acq>='" + startDate + "' AND time_of_acq<='" + stopDate + "';");
            }
            else if(resolution == DAILY)
            {
                ps = conn.prepareStatement("select count(distinct to_days(time_of_acq)) from meteo where time_of_acq>='" + startDate + "' AND time_of_acq<='" + stopDate + "';");
            }
            else if(resolution == MONTHLY)
            {
                ps = conn.prepareStatement("select count(distinct EXTRACT(YEAR_MONTH from time_of_acq)) from meteo where time_of_acq>='" + startDate + "' AND time_of_acq<='" + stopDate + "';");
            }
            else if(resolution == YEARLY)
            {
                ps = conn.prepareStatement("select count(distinct YEAR(time_of_acq)) from meteo where time_of_acq>='" + startDate + "' AND time_of_acq<='" + stopDate + "';");
            }        
            
            if(ps != null)
            {                
                rs = ps.executeQuery();                    
                if(rs.next())
                {                
                    nSamples = rs.getInt(1);
                }
                ps.close();                
                rs.close();
            }            
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }

        nSamples++;
        
        System.out.println("Number of samples = " + nSamples);  

        return nSamples;
    }
    
    private float getLumValue(float lum)
    {
        float sum = 0;
        int counter = 0;  
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try
        {            
            ps = conn.prepareStatement("select * from lightmax order by light desc;");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                sum += rs.getFloat(1);
                counter++;
            }
            
            if(counter != 0)
            {
                lum = lum / (sum / counter) * 100f;
            }
            
            ps.close();
            rs.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        
        return lum;
    }    
    
    public void startProduction(boolean[] selected) 
    {
        stopProduction = false; 
        this.selected = selected;
        
        new ProducerThread().start();
    }
    
    public void stopProduction() 
    {
        stopProduction = true;
    }
                              
    public void shutdown()
    {
        stopProduction = true;
    }

    public static void main(String args[])
    {           
    }
    
    public void stopNow()
    {
        stopProduction();
        setDataSourceStoped();        
    }
    
}
