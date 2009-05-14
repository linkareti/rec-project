/*
 * DebugDateTimeGetters.java
 *
 * Created on 2 de Maio de 2003, 17:42
 */

package pt.utl.ist.elab.driver.webrobot.debug;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class DebugDateTimeGetters {
    
    /** Creates a new instance of DebugDateTimeGetters */
    public DebugDateTimeGetters() 
    {
        java.util.GregorianCalendar gst=new java.util.GregorianCalendar(2002,java.util.GregorianCalendar.DECEMBER,31);
        com.linkare.rec.data.synch.DateTime dtst=new com.linkare.rec.data.synch.DateTime(gst);        
        dtst.setTime(new com.linkare.rec.data.synch.Time((short)345,(short)479,(short)599,(short)949,(byte)59,(byte)59,(byte)23));
        java.util.GregorianCalendar gct=new java.util.GregorianCalendar(2003,java.util.GregorianCalendar.JANUARY,1);
        com.linkare.rec.data.synch.DateTime dtct=new com.linkare.rec.data.synch.DateTime(gct);
        dtct.setTime(new com.linkare.rec.data.synch.Time((short)459,(short)433,(short)644,(short)729,(byte)0,(byte)0,(byte)0));
        System.out.println("Total milis="+getTotalMilis(dtst,dtct));
        System.out.println("Total micros="+getTotalMicros(dtst,dtct));
        System.out.println("Total nanos="+getTotalNanos(dtst,dtct));
        System.out.println("Total picos="+getTotalPicos(dtst,dtct));
    }
    
    public long getTotalPicos(com.linkare.rec.data.synch.DateTime start, com.linkare.rec.data.synch.DateTime current)
    {
        return (getTotalNanos(start,current)*1000+current.getTime().getPicos()-start.getTime().getPicos());        
    }
    
    public long getTotalNanos(com.linkare.rec.data.synch.DateTime start, com.linkare.rec.data.synch.DateTime current)
    {
        return (getTotalMicros(start,current)*1000+current.getTime().getNanos()-start.getTime().getNanos());        
    }
    
    public long getTotalMicros(com.linkare.rec.data.synch.DateTime start, com.linkare.rec.data.synch.DateTime current)
    {
        return (getTotalMilis(start,current)*1000+current.getTime().getMicros()-start.getTime().getMicros());        
    }
    
    public long getTotalMilis(com.linkare.rec.data.synch.DateTime start, com.linkare.rec.data.synch.DateTime current)
    {                
        short startYear=start.getDate().getYear();
        int startYearTotalDays=startYear;
        short startDay=getDayOfYear(start.getDate().getDay(),start.getDate().getMonth(),start.getDate().getYear());
        byte startHour=start.getTime().getHours();
        byte startMinute=start.getTime().getMinutes();
        byte startSecond=start.getTime().getSeconds();
        short startMilis=start.getTime().getMilis();
                
        short currentYear=current.getDate().getYear();
        int currentYearTotalDays=currentYear;
        short currentDay=getDayOfYear(current.getDate().getDay(),current.getDate().getMonth(),current.getDate().getYear());
        byte currentHour=current.getTime().getHours();
        byte currentMinute=current.getTime().getMinutes();
        byte currentSecond=current.getTime().getSeconds();
        short currentMilis=current.getTime().getMilis();
            
        /**If the start and the current year are the same, don't lose time*/
        if(startYearTotalDays!=currentYearTotalDays)
        {
            currentYearTotalDays=getTotalDays(currentYear);
            startYearTotalDays=getTotalDays(startYear);
        }
        int currentLongTime=(currentYearTotalDays+currentDay-1)*24*3600*1000+currentHour*3600*1000+currentMinute*60*1000+currentSecond*1000+currentMilis;
        int startLongTime=(startYearTotalDays+startDay-1)*24*3600*1000+startHour*3600*1000+startMinute*60*1000+startSecond*1000+startMilis;
        return (currentLongTime-startLongTime);
    }
        
    /**Returns the number of days, until this day in this year*/
    public short getDayOfYear(byte day, byte month, short year)
    {
        short dayYear=day;
        /**Remember december=11 and january=0*/        
        byte[] monthNumDays={31,28,31,30,31,30,31,31,30,31,30,31};
        if(isLeapYear(year))
        {
            monthNumDays[1]=29;
        }
        for(int i=0;i<month;i++)
        {
            dayYear+=monthNumDays[i];
        }
        return dayYear;
    }
    
    /**Returns the number of days until this year! (This year days are not counted!)*/
    public int getTotalDays(short year)
    {
        int totalDays=0;
        for(short i=0;i<year+1;i++)
        {
            if(isLeapYear(i))
            {
                totalDays+=366;
            }
            else
            {
                totalDays+=365;
            }
        }
        return totalDays;
    }
    
    public boolean isLeapYear(short year)
    {
        return ((year%4==0&&year%100!=0)||(year%4==0&&year%400==0))?true:false;
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DebugDateTimeGetters();
    }
    
}
