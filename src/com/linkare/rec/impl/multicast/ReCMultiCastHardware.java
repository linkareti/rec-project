/*
 * MultiCastHardwareProxy.java
 *
 * Created on 30 de Outubro de 2002, 10:27
 */

package com.linkare.rec.impl.multicast;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientHelper;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.MultiCastHardwareHelper;
import com.linkare.rec.acquisition.MultiCastHardwareOperations;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.NotOwnerException;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.events.HardwareChangeListener;
import com.linkare.rec.impl.events.HardwareLockEvent;
import com.linkare.rec.impl.events.HardwareRemoveEvt;
import com.linkare.rec.impl.events.LockCountDown;
import com.linkare.rec.impl.exceptions.IncorrectStateExceptionConstants;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.exceptions.NotAvailableExceptionConstants;
import com.linkare.rec.impl.exceptions.NotOwnerExceptionConstants;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.security.DefaultOperation;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.ConditionChecker;
import com.linkare.rec.impl.data.FrequencyUtil;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.HardwareWrapper;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;




/**
 *
 * @author  jp
 */
public class ReCMultiCastHardware implements MultiCastHardwareOperations
{
    
    public static String MC_HARDWARE_LOGGER="MultiCastHardware.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(MC_HARDWARE_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(MC_HARDWARE_LOGGER));
        }
    }
    
    public static final String SYSPROP_MULTICASTHARDWARE_LOCK_PERIOD="MultiCastHardware.LockPeriod";
    private static final long LOCK_PERIOD=Defaults.defaultIfEmpty(System.getProperty(SYSPROP_MULTICASTHARDWARE_LOCK_PERIOD),10*1000);
    //private static final long HARDWAREINFO_REFRESH_PERIOD=Defaults.defaultIfEmpty(System.getProperty("HARDWAREINFO_REFRESH_PERIOD"),60*1000);
    //private static final long HARDWARESTATE_REFRESH_PERIOD=Defaults.defaultIfEmpty(System.getProperty("HARDWARESTATE_REFRESH_PERIOD"),60*1000);
    
    private ClientQueue clientQueue=null;
    private HardwareWrapper hardware=null;
    
    //is it free (locking=false && ownerDataClient=null), lock available (locking=true && locked=false)
    // locking (locking=true & locked=true) or allready locked (locking=false & locked=true)
    private boolean locking=false;
    private boolean locked=false;
    private boolean startCalled=false;
    //... and by whom?
    private DataClientForQueue ownerDataClient=null;
    //if the poor guy just had a chance to stop others experiments give'm the lock again! Just inform him!
    private static final int OWNER_CHANGE=0;
    private static final int OWNER_REMOVED_GIVE_STOP_LOCK=1;
    private static final int OWNER_ONLY_HAD_STOP_LOCK=2;
    private int ChangeOwnerInNextLockCycle=OWNER_CHANGE;
    
    private boolean shuttingDown=false;
    
    //TODO - put the LockCycler in the constructor...
    private LockCycler currentLocker=new LockCycler();
    private ConditionChecker checkerStart=null;
    
    
    /*Proxy State Information*/
    private HardwareState proxyHardwareState=HardwareState.UNKNOWN;
    private HardwareInfo proxyHardwareInfo=null;
    private ReCMultiCastDataProducer recMultiCastDataProducer=null;
    private String proxyHardwareUniqueId=null;
    
    /**Remote references for myself*/
    private transient MultiCastHardware _this=null;
    private ObjectID objectID=null;
    
    private DefaultResource resource=null;
    private MultiCastHardwareDataClient dataClient=null;
    
    private ClientQueue mainQueue=null;
    
    private int maximumClients=1;
    /** Creates a new instance of MultiCastHardwareImpl */
    public ReCMultiCastHardware(DefaultResource resource,Hardware hardware, int maximumClients,ClientQueue mainQueue) throws Exception
    {
        this.mainQueue=mainQueue;
        setResource(resource);
        dataClient=new MultiCastHardwareDataClient();
        
        this.maximumClients=maximumClients;
        
        clientQueue=new ClientQueue(new ClientQueueAdapter(),maximumClients);
        
        setHardware(hardware);
        
        try
        {
            this.hardware.registerDataClient(dataClient._this());
        }catch(NotAuthorized e)
        {
            logThrowable("Couldn't register this MultiCastHardware as a DataClient for the Hardware - NotAuthorized! Rethrowing Exception...",e);
            throw e;
        }
        
        //Initializing CORBA Object Implementations
        _this();
        
    }
    
    
    
    public MultiCastHardware _this()
    {
        if(_this!=null)
            return _this;
        
        try
        {
            return (_this=MultiCastHardwareHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(ORBBean.getORBBean().registerAutoIdRootPOAServant(MultiCastHardware.class,this,(objectID=new ObjectID())))));
        }catch(Exception e)
        {
            logThrowable("Couldn't register as a MultiCastHardware in the ORB!",e);
            return null;
        }
        
    }
    
    
    
    //Remote Methods implementation
    
    public void requireLock(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException, NotAuthorized
    {
        DefaultOperation op=new DefaultOperation(IOperation.OP_LOCK);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        synchronized(this)
        {
            verifyOwnership(user);
            //Se rebentar na linha anterior - bye bye!
            verifyShuttingDown();
            //Se rebentar na linha anterior - sorry! Going down! check with System Admin!
            locked=true;
            currentLocker.stopCountDown();
        }
        
        //if we got here, then the ownerDataClient!=null && ownerDataClient=currentDataClient
        synchronized(ownerDataClient)
        {
            //just verify state
            if(!proxyHardwareState.equals(HardwareState.UNKNOWN))
            {
                if(ChangeOwnerInNextLockCycle==OWNER_REMOVED_GIVE_STOP_LOCK)
                    ChangeOwnerInNextLockCycle=OWNER_ONLY_HAD_STOP_LOCK;
                
                startCalled=false;
                
                locking=false;
                locked=true;
                
                ownerDataClient.getUserInfo().setLockedTime(new DateTime());
                
                checkerStart=new ConditionChecker(LOCK_PERIOD*10,LOCK_PERIOD,
                new AbstractConditionDecisor()
                {
                    public int getConditionResult()
                    {
                        if(startCalled)
                        {
                            checkerStart=null;
                            return CONDITION_MET_TRUE;
                        }
                        return CONDITION_NOT_MET;
                    }
                    public void onConditionTimeOut()
                    {
                        log(Level.INFO,"DataClient has Locked the Hardware but has not started it in " +LOCK_PERIOD*10/1000 +" s, so I'm cycling the Queue...");
                        checkerStart=null;
                        dataClientGone(ownerDataClient);
                    }
                });
            }
            else
            {
                locked=false;
                throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.STOPED);
            }
        }
        
        
    }
    
    
    public void configure(UserInfo user, HardwareAcquisitionConfig configuration) throws IncorrectStateException, NotAvailableException, WrongConfigurationException, NotOwnerException, NotAuthorized
    {
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_CONFIG);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        op.getProperties().put(IOperation.PROPKEY_ACQ_CONFIG, configuration);
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        verifyShuttingDown();
        verifyOwnership(user);
        //se nao rebentar na linha anteriro podemos dar lock se o estado for ok e nao estivermos a meio de um shutDown...
        
        //verify state
        if(
        proxyHardwareState.equals(HardwareState.STOPED)
        ||
        proxyHardwareState.equals(HardwareState.RESETED)
        )
        {
            hardware.configure(configuration);
        }
        else
        {
            log(Level.INFO,"Multicast: Configure Request Received.... State is wrong for request: " + proxyHardwareState);
            throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.CONFIGURING);
        }
        
    }
    
    public DataProducer start(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException, NotAuthorized
    {
        
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_START);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        
        startCalled=true;
        verifyShuttingDown();
        verifyOwnership(user);
        //se nao rebentar na linha anterior podemos dar lock se o estado for ok e nao estivermos a meio de um shutDown...
        
        if(
        proxyHardwareState.equals(HardwareState.STOPED)
        ||
        proxyHardwareState.equals(HardwareState.CONFIGURED)
        )
        {
            //jobEnded(proxyDataProducer);
            DefaultResource dataProducerResource=getResource().createChildResource();
            recMultiCastDataProducer=ReCMultiCastDataProducerFactory.createReCMultiCastDataProducer(dataProducerResource,new DataProducerAdapter(),getHardwareUniqueId(),maximumClients);
            recMultiCastDataProducer.setRemoteDataProducer(hardware.start(recMultiCastDataProducer.getDataReceiver()));
            
            return recMultiCastDataProducer._this();
        }
        else
            throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.CONFIGURING);
    }
    
    public DataProducer startOutput(UserInfo user, DataProducer data_source) throws IncorrectStateException, NotAvailableException, NotOwnerException, NotAuthorized
    {
        DefaultOperation op=new DefaultOperation(IOperation.OP_START_OUTPUT);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        op.getProperties().put(IOperation.PROPKEY_REMOTE_DATAPRODUCER, data_source);
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        startCalled=true;
        verifyShuttingDown();
        verifyOwnership(user);
        //se nao rebentar na linha anteriro podemos dar lock se o estado for ok e nao estivermos a meio de um shutDown...
        
        if(
        proxyHardwareState.equals(HardwareState.STOPED)
        ||
        proxyHardwareState.equals(HardwareState.CONFIGURED)
        )
        {
            //jobEnded(proxyDataProducer);
            
            DefaultResource dataProducerResource=getResource().createChildResource();
            recMultiCastDataProducer=ReCMultiCastDataProducerFactory.createReCMultiCastDataProducer(dataProducerResource,new DataProducerAdapter(),getHardwareUniqueId(),maximumClients);
            recMultiCastDataProducer.setRemoteDataProducer(hardware.startOutput(recMultiCastDataProducer.getDataReceiver(),data_source));
            return recMultiCastDataProducer._this();
        }
        else
            throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.CONFIGURING);
        
    }
    
    public void stop(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,NotAuthorized
    {
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_STOP);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        verifyOwnership(user);
        verifyShuttingDown();
        
        //se nao rebentar nas linha anteriro podemos dar lock a outros!
        if(!proxyHardwareState.equals(HardwareState.STARTED))
            throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.CONFIGURING);
        
        hardware.stop();
    }
    
    
    public void reset(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException, NotAuthorized
    {
        DefaultOperation op=new DefaultOperation(IOperation.OP_RESET);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        verifyOwnership(user);
        verifyShuttingDown();
        //se nao rebentar nas linhas anteriores podemos dar lock a outro...
        
        //verify state
        if(proxyHardwareState.equals(HardwareState.STARTED) ||
        proxyHardwareState.equals(HardwareState.STOPED) ||
        proxyHardwareState.equals(HardwareState.CONFIGURED)	)
            hardware.reset();
        else
            throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,proxyHardwareState,HardwareState.CONFIGURING);
        
    }
    
    public DataProducer getDataProducer(UserInfo user) throws IncorrectStateException, NotAvailableException, NotAuthorized
    {
        
        verifyShuttingDown();
        
        ReCMultiCastDataProducer producer=recMultiCastDataProducer;
        if(producer==null)
            throw new NotAvailableException(NotAvailableExceptionConstants.NO_DATA_PRODUCER_AT_THIS_MOMENT);
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_GET_DATAPRODUCER);
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(producer.getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        return producer._this();
    }
    
    public void sendMessage(UserInfo user, String clientTo, String message)
    throws NotRegistered,NotAuthorized
    {
        clientQueue.sendChatMessage(user,clientTo,message,resource);
    }
    
    public HardwareInfo getHardwareInfo(UserInfo user)throws NotAuthorized,NotRegistered
    {
        /*TODO check with JP. The labClientBean needs to enumerate the hardware...
         *I  don't want to be in the MulticastHardware client queue just to enumerate,
         *so I'm going to ask in the mainQueue, OK?*/
        /*if(!clientQueue.contains(user))
            throw new NotRegistered();*/
        if(!mainQueue.contains(user))
            throw new NotRegistered();
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_GET_HARDWAREINFO);
        op.getProperties().put(IOperation.PROPKEY_HARDWAREINFO, getHardwareInfo());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        return getHardwareInfo();
    }
    
    public HardwareState getHardwareState(UserInfo user) throws NotAuthorized,NotRegistered
    {
        /*TODO check with JP. The labClientBean needs to enumerate the hardware...
         *I  don't want to be in the MulticastHardware client queue just to enumerate,
         *so I'm going to ask in the mainQueue, OK?*/
        /*if(!clientQueue.contains(user))
            throw new NotRegistered();*/
        if(!mainQueue.contains(user))
            throw new NotRegistered();
        
        DefaultOperation op=new DefaultOperation(IOperation.OP_GET_HARDWARESTATE);
        op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
        DefaultUser securityUser=new DefaultUser(user);
        
        if(!SecurityManagerFactory.authorize(getResource(), securityUser, op))
        {
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
        }
        
        return getHardwareState();
    }
    //end remote methods implementation
    
    
    
    
    private void setHardware(Hardware hardware) throws Exception
    {
        if(hardware==null)
        {
            this.hardware=null;
            dataClient.hardwareStateChange(HardwareState.UNKNOWN);
            return;
        }
        
        if(this.hardware!=null && this.hardware.isSameDelegate(hardware))
            return;
        
        this.hardware=new HardwareWrapper(hardware);
        try
        {
            proxyHardwareInfo=this.hardware.getHardwareInfo();
            proxyHardwareUniqueId=proxyHardwareInfo.getHardwareUniqueID();
            
            getResource().getProperties().put(IResource.PROPKEY_MCHARDWARE_ID, proxyHardwareUniqueId);
            
            dataClient.hardwareStateChange(this.hardware.getHardwareState());
            log(Level.INFO,"Hardware State is now " + proxyHardwareState);
        }catch(Exception e)
        {
            logThrowable("MultiCastHardware " + getHardwareUniqueId() + ": Couldn't get Remote Hardware properties! Rethrowing exception...",e);
            throw e;
        }
    }
    
    
    public String getHardwareUniqueId()
    {
        if(proxyHardwareUniqueId!=null)
            return proxyHardwareUniqueId;
        
        return "[unknow HardwareUniqueId]";
    }
    
    
    private void verifyOwnership(UserInfo user) throws NotOwnerException
    {
        //TODO CHECK with JP..this wasn't working...only works if I compare the usernames, I think that is the right to do it
        //if(ownerDataClient==null ||  !ownerDataClient.getUserInfo().equals(user))
        if(ownerDataClient==null ||  !ownerDataClient.getUserInfo().getUserName().equals(user.getUserName()))
        {
            if(locked && !locking)
                throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_LOCKED_TO_ANOTHER,ownerDataClient.getUserName());
            else if(locked && locking)
                throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER,ownerDataClient.getUserName());
            else if(!locked && locking)
                throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_LOCK_AVAILABLE_TO_ANOTHER,ownerDataClient.getUserName());
            else if(!locked && !locking)
                throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_NOT_LOCKABLE_TO_ANYONE,ownerDataClient.getUserName());
        }
    }
    
    private void verifyShuttingDown() throws NotAvailableException
    {
        if(shuttingDown)
            throw new NotAvailableException(NotAvailableExceptionConstants.HARDWARE_SHUTTING_DOWN);
    }
    
    
    public void shutdown()
    {
        if(shuttingDown) return;
        
        log(Level.INFO,"Shutting down!");
        
        shuttingDown=true;
        
        if(checkerStart!=null)
            checkerStart.cancelCheck();
        
        clientQueue.shutdown();
        
        if(recMultiCastDataProducer!=null)
            recMultiCastDataProducer.shutdown();
        
        if(currentLocker!=null)
        {
            currentLocker.exit();currentLocker=null;
        }
        
        log(Level.INFO,"Shut down completed!");
        
        
        try
        {
            ORBBean.getORBBean().deactivateServant(objectID.getOid());
        }catch(Exception e)
        {
            logThrowable("Error deactivating in POA...",e);
        }
        
        dataClient.shutdown();
        
        shuttingDown=false;
        System.gc();
    }
    
    /** Utility field holding the MCHardwareChangeListener. */
    //private transient MCHardwareChangeListener listenerMCHardwareChangeListener =  null;
    
    private void cycleLockHardware()
    {
        
        synchronized(this)
        {
            if(locking && locked)
                return;//In locking process
            else if(!locking && locked)
                return;//Allready Locked
            
            if(!locked)
            {
                if(clientQueue.first()==null)
                    return; //the queue was empty
                
                if(ChangeOwnerInNextLockCycle==OWNER_CHANGE)
                {
                    log(Level.INFO,"Rotating DataClient Queue for lock...");
                    clientQueue.moveFirstToLast();
                }
                
                if(ChangeOwnerInNextLockCycle==OWNER_ONLY_HAD_STOP_LOCK)
                {
                    log(Level.INFO,"Current owner had a stop lock! Now give him the lock, next time give it to another!");
                    ChangeOwnerInNextLockCycle=OWNER_CHANGE;
                }
                
                
                ownerDataClient=clientQueue.first();
                locking=true;
                
                if(ownerDataClient!=null)
                {
                    clientQueue.hardwareLockable(new HardwareLockEvent(currentLocker,LOCK_PERIOD,ownerDataClient));
                }
                
                if(ownerDataClient!=null && ownerDataClient.getUserInfo().getLockedTime() != null && ownerDataClient.getUserInfo().getLockedTime().getMilliSeconds()!=0)
                    timeStartMin=ownerDataClient.getUserInfo().getLockedTime();
                else
                    timeStartMin=new DateTime();
                
            }
        }
        
    }
    private void dataClientGone(DataClientForQueue dcfq)
    {
        if(dcfq==null || ownerDataClient==null) return;
        
        if(dcfq.equals(ownerDataClient))
        {
            locked=false;locking=false;
            
            //Very very stupid thing... Client is gone| Why set it's lock time? who cares?
            //ownerDataClient.getUserInfo().setLockedTime(null);
            
            ownerDataClient=null;
            
            currentLocker.stopCountDown();
            //if(currentLocker!=null)
            //currentLocker.exit();
            
            //ChangeOwnerInNextLockCycle=OWNER_REMOVED_GIVE_STOP_LOCK;
            if(clientQueue.isEmpty())
            {
                locked=false;locking=false;
                ownerDataClient=null;
                currentLocker.stopCountDown();
            }
            else
                cycleLockHardware();
        }
        
    }
    
    DateTime timeStartMin=null;
    
    public UserInfo[] getClientList(UserInfo user) throws NotRegistered,NotAuthorized
    {
        UserInfo[] retVal=clientQueue.getUsers(user,resource);
        
        DateTime timeStartMax=null;
        
        //TODO CHECK WITH JP && ownerDataClient.getUserInfo().getLockedTime()!=null
        //Para que esta informação seja igual para todos os utilizadores ligados, vou
        //mover isto para o cycleLockHardware, assim o timeStartMin é igual para todos...
        
        timeStartMax=new DateTime(timeStartMin);
        
        long experimentMaxTimeShift=FrequencyUtil.getMaximumExperimentTime(getHardwareInfo());
        
        //TESTING
        if(retVal!=null)
        {
            for(int i=0;i<retVal.length;i++)
            {
                if(i==0 && retVal[0].getUserName().equals(ownerDataClient.getUserName()) && ownerDataClient.getUserInfo().getLockedTime() != null && ownerDataClient.getUserInfo().getLockedTime().getMilliSeconds() != 0)
                {
                    DateTime dateTimeCycleTotalMin=new DateTime(timeStartMin);
                    DateTime dateTimeCycleTotalMax=new DateTime(timeStartMax);
                    
                    dateTimeCycleTotalMin.addMillis(LOCK_PERIOD*retVal.length);
                    dateTimeCycleTotalMax.addMillis(experimentMaxTimeShift*retVal.length);
                    
                    retVal[0].setNextLockTime(dateTimeCycleTotalMin,dateTimeCycleTotalMax);
                }
                else if(ownerDataClient.getUserInfo().getLockedTime() != null && ownerDataClient.getUserInfo().getLockedTime().getMilliSeconds() != 0)
                {
                    timeStartMin.addMillis(LOCK_PERIOD);
                    timeStartMax.addMillis(experimentMaxTimeShift);
                    retVal[i].setNextLockTime(new DateTime(timeStartMin),new DateTime(timeStartMax));
                }
                //TESTING
                else
                {
                    timeStartMin.addMillis(i * LOCK_PERIOD);
                    retVal[i].setNextLockTime(new DateTime(timeStartMin),new DateTime(timeStartMin));
                }
                
                //TODO CHECK WITH JP, IS THIS THE BEST PLACE TO TO THIS???
                /*if(retVal[i] != null && getHardwareInfo() != null)
                {
                    //retVal[i].setHardwaresConnectedTo(getHardwareInfo().getFamiliarName());
                    //retVal[i].setHardwaresConnectedTo(getHardwareInfo().getHardwareUniqueID());
                }*/
            }
            
        }
        return retVal;
    }
    
    
    public void registerDataClient(DataClient data_client) throws NotAuthorized,NotAvailableException, MaximumClientsReached
    {
        log(Level.INFO,"Hardware " + getHardwareUniqueId() + " : registering DataClient!");
        verifyShuttingDown();
        log(Level.INFO,"Hardware " + getHardwareUniqueId() + " : Verified Shutting down!");
        boolean startupLockCycler=clientQueue.isEmpty();
        log(Level.INFO,"clientQueue is empty? " + startupLockCycler);
        
        //clientQueue.add(mainQueue.getWrapperForDataClient(data_client),resource);
        UserInfo userInfo=null;
        try
        {
            userInfo=data_client.getUserInfo();
        }
        catch(Exception e)
        {
            logThrowable("Hardware " + getHardwareUniqueId() + " : Got an exception trying to read UserInfo for DataClient... I'm not registering him!",e);
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE);
        }
        
        if(mainQueue.contains(userInfo))
        {
            boolean addok = clientQueue.add(data_client,resource);
            
            if(addok)
            {//A unica maneira que consegui arranjar de registar na mainqueue o hardware connected to
                try
                {
                    UserInfo[] users = mainQueue.getUsers(userInfo, resource);
                    if(users != null)
                    {
                        for(int i=0; i<users.length; i++)
                        {
                            if(users[i] != null && users[i].getUserName() != null && userInfo != null && userInfo.getUserName() != null)
                            {
                                if(users[i].getUserName().equals(userInfo.getUserName()))
                                {
                                    users[i].setHardwaresConnectedTo(getHardwareUniqueId());
                                }
                            }
                        /*else if(users[i] != null)
                            users[i].setHardwaresConnectedTo(null);*/
                            
                        }
                    }
                }
                catch(Exception e)
                {
                    logThrowable("Hardware " + getHardwareUniqueId() + " : Error setting hardware connected to ",e);
                }
            }
        }
        else
        {
            log(Level.INFO,"Hardware " + getHardwareUniqueId() + " : Data Client: " + data_client.getUserInfo().getUserName() + " is not registered to MultiCastController... How did he ever get to me? Sending NotAuthorized!");
            throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST);
        }
        log(Level.INFO,"Hardware " + getHardwareUniqueId() + " : Added a Data Client: " + data_client.getUserInfo().getUserName() + " to clienQueue!");
        if(startupLockCycler)
            cycleLockHardware();
    }
    
    
    
    
    /** Getter for property resource.
     * @return Value of property resource.
     *
     */
    public DefaultResource getResource()
    {
        return resource;
    }
    
    /** Setter for property resource.
     * @param resource New value of property resource.
     *
     */
    public void setResource(DefaultResource resource)
    {
        this.resource = resource;
    }
    
    
    private HardwareInfo getHardwareInfo()
    {
        return proxyHardwareInfo;
    }
    
    private HardwareState getHardwareState()
    {
        return proxyHardwareState;
    }
    
    private void log(Level debugLevel, String message)
    {
        Logger.getLogger(MC_HARDWARE_LOGGER).log(debugLevel,"MultiCastHardware "+getHardwareUniqueId()+" - "+message);
    }
    
    private void logThrowable(String message,Throwable t)
    {
        LoggerUtil.logThrowable("MultiCastHardware "+getHardwareUniqueId()+" - "+message, t,Logger.getLogger(MC_HARDWARE_LOGGER));
    }
    
    /** Getter for property hardware.
     * @return Value of property hardware.
     *
     */
    public HardwareWrapper getHardware()
    {
        return hardware;
    }
    
    
    /*Inner class - ClientQueueListener implementation*/
    private class ClientQueueAdapter implements IClientQueueListener
    {
        
        public void dataClientForQueueIsGone(DataClientForQueue dcfq)
        {
            dataClientGone(dcfq);
        }
        
        /*
         *Proxy loggging methods for ClientQueue
         */
        public void log(Level debugLevel, String message)
        {
            ReCMultiCastHardware.this.log(debugLevel, message);
        }
        
        public void logThrowable(String message, Throwable t)
        {
            ReCMultiCastHardware.this.logThrowable(message,t);
        }
        
    }
    /*Inner class - ClientQueueListener implementation*/
    
    
    
    
    /*Inner Class - DataClient Implementation*/
    private class MultiCastHardwareDataClient implements DataClientOperations
    {
        private transient DataClient _this=null;
        private ObjectID objectID=null;
        private UserInfo userInfo=new UserInfo("MultiCastHardwareUser");
        
        public DataClient _this()
        {
            if(_this!=null)
                return _this;
            
            try
            {
                return (_this=DataClientHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class,this,(objectID=new ObjectID())))));
            }catch(Exception e)
            {
                logThrowable("Couldn't register as a DataClient in the ORB!",e);
                return null;
            }
            
        }
        
        public UserInfo getUserInfo()
        {
            return userInfo;
        }
        
        
        public void hardwareStateChange(HardwareState newState)
        {
            if(newState.equals(proxyHardwareState))
                return;
            
            proxyHardwareState=newState;
            clientQueue.hardwareStateChange(proxyHardwareState);
            
            try
            {
                if(proxyHardwareState.equals(HardwareState.STOPED) || proxyHardwareState.equals(HardwareState.RESETED))
                {
                    locked=false;locking=false;
                    if(ownerDataClient!=null)
                        //Since passing null DateTime is such a pain in the ass, because of the way the Date and Time helpers(write and read) are implemented, I'll consider null = 0 milliseconds
                        ownerDataClient.getUserInfo().setLockedTime(new DateTime(0));
                    ownerDataClient=null;
                    
                    currentLocker.stopCountDown();
                    cycleLockHardware();
                    recMultiCastDataProducer=null;
                }
            }catch(Exception e)
            {
                logThrowable("Error cycling lock...",e);
            }
            
        }
        
        /***BIG SILENT NOOPS***/
        
        public void hardwareChange()
        {/*Nope - hardwares won't call this method*/}
        public void hardwareLockable(long millisecs_to_lock_success)
        {/*Nope - hardwares won't call this method*/}
        public void receiveMessage(String clientFrom, String clientTo, String message)
        {/*Nope - hardwares won't call this method*/}
        
        /***END BIG SILENT NOOPS***/
        
        
        public void shutdown()
        {
            try
            {
                ORBBean.getORBBean().deactivateServant(objectID.getOid());
            }catch(Exception e)
            {
                logThrowable("Error deactivating DataClient in POA...",e);
            }
        }
        
        
    }
    /*End Inner Class - DataClient Implementation*/
    
    
    
    
    /*Inner Class - ProxyDataProducerCallBack Implementation*/
    private class DataProducerAdapter implements ReCMultiCastDataProducerListener
    {
        
        public void oneDataReceiverGone()
        {
            try
            {
                if(ownerDataClient==null) return;
                //test if DataClientOwner is still there...
                if(!ownerDataClient.getDataClient().isConnected())
                {
                    log(Level.INFO,"Current owner is gone!");
                    dataClientGone(ownerDataClient);
                }
            }catch(Exception e)
            {
                logThrowable("Error checking connection to owner client ", e);
            }
        }
        
        public void log(Level debugLevel, String message)
        {
            ReCMultiCastHardware.this.log(debugLevel, message);
        }
        
        public void logThrowable(String message, Throwable t)
        {
            ReCMultiCastHardware.this.logThrowable(message,t);
        }
        
    }
    /*End Inner Class - ProxyDataProducerCallBack Implementation*/
    
    
    
    
    
    /*Inner Class - LockCycler -  manages the cycling of locks*/
    private class LockCycler extends Thread implements LockCountDown
    {
        private long endTime=0;
        private boolean exit=false;
        private Thread currentThread=null;
        private Object synch=new Object();
        private boolean starting=false;
        
        public LockCycler()
        {
            super("Lock Cycler Thread for Hardware " + getHardwareUniqueId());
            synchronized(this)
            {
                start();
                try
                {wait();}catch(Exception e)
                {}
            }
        }
        
        public void stopCountDown()
        {
            
            synchronized(synch)
            {
                endTime=0;
                synch.notifyAll();
                //wait for pause entered...
                try
                {synch.wait();}catch(Exception e)
                {}
                
            }
        }
        
        
        public void startCountDown(long milliseconds_to_lock_success)
        {
            synchronized(synch)
            {
                endTime=System.currentTimeMillis()+milliseconds_to_lock_success;
                starting=true;
                synch.notifyAll();
                //wait for counting entered...
                try
                {synch.wait();}catch(Exception e)
                {}
            }
        }
        
        public void run()
        {
            currentThread=Thread.currentThread();
            
            synchronized(this)
            {
                this.notifyAll();//notify that I got a signal - entering pause
            }
            synchronized(synch)
            {
                while(!exit)
                {
                    synch.notifyAll();
                    try
                    {
                        synch.wait(Math.max(endTime-System.currentTimeMillis(),0));
                    }catch(Exception ignored)
                    {}
                    
                    if(exit || endTime==0 || locked)
                    {
                        continue;
                    }
                    
                    if(starting)
                    {
                        starting=false;
                        continue;
                    }
                    
                    endTime=0;
                    cycleLockHardware();
                }
            }
            
        }
        
        public void exit()
        {
            endTime=0;
            exit=true;
            synchronized(synch)
            {
                synch.notifyAll();
            }
            if(currentThread!=null)
            {
                try
                {
                    
                    currentThread.join();}catch(Exception ignored)
                    {}
                currentThread=null;
            }
        }
    }
    /*End Inner Class - LockCycler -  manages the cycling of locks*/
}
