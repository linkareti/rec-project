/*
 * SerialPortCommand.java
 *
 * Created on 11 de Novembro de 2002, 15:34
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

import java.util.HashMap;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class StampCommand implements java.io.Serializable
{
        static final long serialVersionUID = -2953294044465039790L;
	private String commandIdentifier=null;
	private String command=null;
	private HashMap commandDataMap=null;
	private boolean isData = false;
        
	/** Creates a new instance of SerialPortCommand */
	public StampCommand(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
	}
	
	public void setCommand(String command){this.command=command;}
	public String getCommand(){return this.command;}
	
	public void setCommandIdentifier(String commandIdentifier){this.commandIdentifier=commandIdentifier;}
	public String getCommandIdentifier(){return this.commandIdentifier;}
	
	public void addCommandData(Object commandDataKey,Object commandDataObject)
	{
		if(this.commandDataMap==null)
			this.commandDataMap=new HashMap(1);
		
		if(this.commandDataMap.containsKey(commandDataKey))
			this.commandDataMap.remove(commandDataKey);
		
		this.commandDataMap.put(commandDataKey,commandDataObject);
	}
	
	public Object getCommandData(Object commandDataKey)
	{
		return this.commandDataMap.get(commandDataKey);
	}
	
	public void setData(boolean isData){this.isData=isData;}
	public boolean isData(){return this.isData;}
	
	private StampProcessor processor=null;
	
	public StampProcessor getProcessor()
	{
	    if(processor==null)
		processor=StampTranslatorProcessorManager.getProcessor(this);
	    
	    return processor;
	}
}
