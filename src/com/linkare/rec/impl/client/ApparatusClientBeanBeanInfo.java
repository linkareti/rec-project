/*
 * ApparatusClientBeanBeanInfo.java
 *
 * Created on August 10, 2004, 12:06 PM
 */

package com.linkare.rec.impl.client;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author andre
 */
public class ApparatusClientBeanBeanInfo extends SimpleBeanInfo
{
    
    // Bean descriptor //GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( ApparatusClientBean.class , null );//GEN-HEADEREND:BeanDescriptor
        
        // Here you can add code for customizing the BeanDescriptor.
        
        return beanDescriptor;         }//GEN-LAST:BeanDescriptor
    
    
    // Property identifiers //GEN-FIRST:Properties

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[0];//GEN-HEADEREND:Properties
        
        // Here you can add code for customizing the properties array.
        
        return properties;         }//GEN-LAST:Properties
    
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_apparatusConnectorListener = 0;
    private static final int EVENT_chatMessageListener = 1;
    private static final int EVENT_expUsersListChangeListener = 2;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[3];
    
            try {
            eventSets[EVENT_apparatusConnectorListener] = new EventSetDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class, "apparatusConnectorListener", com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener.class, new String[] {"apparatusConnected", "apparatusConnecting", "apparatusDisconnected", "apparatusDisconnecting", "apparatusIncorrectState", "apparatusLockable", "apparatusLocked", "apparatusMaxUsers", "apparatusNotAuthorized", "apparatusNotOwner", "apparatusNotRegistered", "apparatusStateConfigError", "apparatusStateConfigured", "apparatusStateConfiguring", "apparatusStateReseted", "apparatusStateReseting", "apparatusStateStarted", "apparatusStateStarting", "apparatusStateStoped", "apparatusStateStoping", "apparatusStateUnknow", "apparatusUnreachable"}, "addApparatusConnectorListener", "removeApparatusConnectorListener" );
            eventSets[EVENT_chatMessageListener] = new EventSetDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class, "chatMessageListener", com.linkare.rec.impl.client.chat.IChatMessageListener.class, new String[] {"connectionChanged", "newChatMessage", "roomChanged"}, "addChatMessageListener", "removeChatMessageListener" );
            eventSets[EVENT_expUsersListChangeListener] = new EventSetDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class, "expUsersListChangeListener", com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener.class, new String[] {"usersListChanged"}, "addExpUsersListChangeListener", "removeExpUsersListChangeListener" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Events
        
        // Here you can add code for customizing the event sets array.
        
        return eventSets;         }//GEN-LAST:Events
    
    // Method identifiers //GEN-FIRST:Methods
    private static final int METHOD_configure0 = 0;
    private static final int METHOD_connect1 = 1;
    private static final int METHOD_disconnect2 = 2;
    private static final int METHOD_hardwareChange3 = 3;
    private static final int METHOD_hardwareLockable4 = 4;
    private static final int METHOD_hardwareStateChange5 = 5;
    private static final int METHOD_lock6 = 6;
    private static final int METHOD_receiveMessage7 = 7;
    private static final int METHOD_reset8 = 8;
    private static final int METHOD_sendMessage9 = 9;
    private static final int METHOD_start10 = 10;
    private static final int METHOD_startAutoRefresh11 = 11;
    private static final int METHOD_stop12 = 12;
    private static final int METHOD_stopAutoRefresh13 = 13;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[14];
    
        try {
            methods[METHOD_configure0] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("configure", new Class[] {com.linkare.rec.data.config.HardwareAcquisitionConfig.class}));
            methods[METHOD_configure0].setDisplayName ( "" );
            methods[METHOD_connect1] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("connect", new Class[] {}));
            methods[METHOD_connect1].setDisplayName ( "" );
            methods[METHOD_disconnect2] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("disconnect", new Class[] {}));
            methods[METHOD_disconnect2].setDisplayName ( "" );
            methods[METHOD_hardwareChange3] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("hardwareChange", new Class[] {}));
            methods[METHOD_hardwareChange3].setDisplayName ( "" );
            methods[METHOD_hardwareLockable4] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("hardwareLockable", new Class[] {Long.TYPE}));
            methods[METHOD_hardwareLockable4].setDisplayName ( "" );
            methods[METHOD_hardwareStateChange5] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("hardwareStateChange", new Class[] {com.linkare.rec.acquisition.HardwareState.class}));
            methods[METHOD_hardwareStateChange5].setDisplayName ( "" );
            methods[METHOD_lock6] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("lock", new Class[] {}));
            methods[METHOD_lock6].setDisplayName ( "" );
            methods[METHOD_receiveMessage7] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("receiveMessage", new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class}));
            methods[METHOD_receiveMessage7].setDisplayName ( "" );
            methods[METHOD_reset8] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("reset", new Class[] {}));
            methods[METHOD_reset8].setDisplayName ( "" );
            methods[METHOD_sendMessage9] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("sendMessage", new Class[] {com.linkare.rec.impl.client.chat.ChatMessageEvent.class}));
            methods[METHOD_sendMessage9].setDisplayName ( "" );
            methods[METHOD_start10] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("start", new Class[] {}));
            methods[METHOD_start10].setDisplayName ( "" );
            methods[METHOD_startAutoRefresh11] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("startAutoRefresh", new Class[] {Long.TYPE}));
            methods[METHOD_startAutoRefresh11].setDisplayName ( "" );
            methods[METHOD_stop12] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("stop", new Class[] {}));
            methods[METHOD_stop12].setDisplayName ( "" );
            methods[METHOD_stopAutoRefresh13] = new MethodDescriptor ( com.linkare.rec.impl.client.ApparatusClientBean.class.getMethod("stopAutoRefresh", new Class[] {}));
            methods[METHOD_stopAutoRefresh13].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        
        // Here you can add code for customizing the methods array.
        
        return methods;         }//GEN-LAST:Methods
    
    
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx
    
    
 //GEN-FIRST:Superclass
    
    // Here you can add code for customizing the Superclass BeanInfo.
    
 //GEN-LAST:Superclass
    
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable
     * properties of this bean.  May return null if the
     * information should be obtained by automatic analysis.
     */
    public BeanDescriptor getBeanDescriptor()
    {
        return getBdescriptor();
    }
    
    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean.  May return null if the
     * information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will
     * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
     * A client of getPropertyDescriptors can use "instanceof" to check
     * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        return getPdescriptor();
    }
    
    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return  An array of EventSetDescriptors describing the kinds of
     * events fired by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public EventSetDescriptor[] getEventSetDescriptors()
    {
        return getEdescriptor();
    }
    
    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return  An array of MethodDescriptors describing the methods
     * implemented by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public MethodDescriptor[] getMethodDescriptors()
    {
        return getMdescriptor();
    }
    
    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     * @return  Index of default property in the PropertyDescriptor array
     * 		returned by getPropertyDescriptors.
     * <P>	Returns -1 if there is no default property.
     */
    public int getDefaultPropertyIndex()
    {
        return defaultPropertyIndex;
    }
    
    /**
     * A bean may have a "default" event that is the event that will
     * mostly commonly be used by human's when using the bean.
     * @return Index of default event in the EventSetDescriptor array
     *		returned by getEventSetDescriptors.
     * <P>	Returns -1 if there is no default event.
     */
    public int getDefaultEventIndex()
    {
        return defaultEventIndex;
    }
}

