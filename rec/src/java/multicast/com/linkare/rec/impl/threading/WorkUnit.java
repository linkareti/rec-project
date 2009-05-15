package com.linkare.rec.impl.threading;

import com.linkare.rec.impl.threading.util.EnumPriority;

public abstract class WorkUnit implements Runnable{

    private EnumPriority priority = null;
    Object Emiter;
    Object Receiver;
    
    public WorkUnit(EnumPriority priority){
	this.priority = priority;
    }
    
    public EnumPriority getPriority(){
	return this.priority;
    }
}
