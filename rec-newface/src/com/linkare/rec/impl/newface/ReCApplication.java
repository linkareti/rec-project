/*
 * ReCApplication.java
 */

package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.ReCApplication.NavigationWorkflow.*;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.*;

import java.awt.Component;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.client.ApparatusClientBean;
import com.linkare.rec.impl.client.LabClientBean;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.exceptions.ExceptionCode;
import com.linkare.rec.impl.exceptions.ReCConfigurationException;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.component.ApparatusComboBoxModel;
import com.linkare.rec.impl.newface.component.DefaultDialog;
import com.linkare.rec.impl.newface.component.UnexpectedErrorPane;
import com.linkare.rec.impl.newface.config.Apparatus;
import com.linkare.rec.impl.newface.config.Lab;
import com.linkare.rec.impl.newface.config.LocalizationBundle;
import com.linkare.rec.impl.newface.config.ReCFaceConfig;
import com.linkare.rec.impl.newface.utils.OS;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.newface.ReCAppEvent.ReCCommand;

/**
 * The main class of the application.
 */
public class ReCApplication extends SingleFrameApplication 
	implements ApparatusListSourceListener, LabConnectorListener, ApparatusConnectorListener {

    private static final Logger log = Logger.getLogger(ReCApplication.class.getName());
    
    // Handler for application uncaught exceptions
    // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4714232
    static {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable t) {
                log.log(Level.SEVERE, "An uncaught exception occurred in thread " + thread, t);
                getUnexpectedErrorBox(t).setVisible(true);
            }
        });
    }

	/**
	 * Holds the ReC System properties. Maps the property name and the
	 * required flag.
	 */
	public enum ReCSystemProperty {

		//RECBASEUICONFIG("ReCBaseUIConfig", true),
		RECFACECONFIG("ReCFaceConfig", true),
		REC_MULTICASTCONTROLLER_BINDNAME("ReC.MultiCastController.BindName", true),
		REC_MULTICASTCONTROLLER_INITREF("ReC.MultiCastController.InitRef", true),
		OPENORB_CONFIG("openorb.config", true),
		OPENORB_PROFILE("openorb.profile", true),
		ORG_OMG_CORBA_ORBCLASS("org.omg.CORBA.ORBClass", true),
		ORG_OMG_CORBA_ORBSINGLETONCLASS("org.omg.CORBA.ORBSingletonClass", true);

		String name;
		boolean required;

		ReCSystemProperty(String name, boolean required) {
			this.name = name;
			this.required = required;
		}

		public String getName() {
			return name;
		}

		public boolean isRequired() {
			return required;
		}

	}

	/**
	 * Defines application Navigation Workflow 
	 * 
	 * @author hfernandes
	 */
	public enum NavigationWorkflow {
		
		// State declaration
		DISCONNECTED_OFFLINE,
		CONNECT_PERFORMED,
		CONNECTED_TO_LAB,
		APPARATUS_ENTER_PERFORMED,
		CONNECTED_TO_APPARATUS,
		DISCONNECT_PERFORMED;
		// State declaration end
		
		private static Map<NavigationWorkflow, Set<NavigationWorkflow>> availableTransitions;
		
		private static Set<NavigationWorkflow> transitions(NavigationWorkflow ... transitions) {
			HashSet<NavigationWorkflow> result = new HashSet<NavigationWorkflow>();
			for (NavigationWorkflow transition : transitions)
				result.add(transition);
			return result;
		}
		
		// Workflow definition
		static {
			availableTransitions = new HashMap<NavigationWorkflow, Set<NavigationWorkflow>>();
			availableTransitions.put(DISCONNECTED_OFFLINE, 
					transitions(CONNECT_PERFORMED));
			
			availableTransitions.put(CONNECTED_TO_LAB, 
					transitions(APPARATUS_ENTER_PERFORMED, DISCONNECT_PERFORMED));
			
			availableTransitions.put(CONNECTED_TO_APPARATUS, 
					transitions(DISCONNECT_PERFORMED));
			
			availableTransitions.put(DISCONNECT_PERFORMED, 
					transitions(DISCONNECTED_OFFLINE));
		}
		// Workflow definition end
		
		public boolean canGoTo(NavigationWorkflow newState) {
			boolean result = true;
			Set<NavigationWorkflow> currentStateTransitions = availableTransitions.get(this);
			if (currentStateTransitions == null) {
				if (log.isLoggable(Level.WARNING)) {
					log.warning(this + " was not configured on available transitions map.");
				}
				result = false;
			}
			
			result = (result == false) ? false : currentStateTransitions.contains(newState);
			
			if (log.isLoggable(Level.FINE)) {
				log.fine("Transition " + this + " => " + newState + " allowed? " + result);
			}
			
			
			return result;
		}
		
		public boolean matches(NavigationWorkflow state) {
			return this == state;
		}
	}

    /** Holds the jws basic service context */
    protected BasicService basicService;

    /** ResourceMap shortcut */
    protected ResourceMap resourceMap;

    /** Holds the application shared UnexpectedErrorBox Dialog */
    protected static DefaultDialog<UnexpectedErrorPane> unexpectedErrorBox;

    /** Holds the listeners to the ReC Application underlying model changes */
    protected List<ReCApplicationListener> appListeners;

    // ReC Application state model

    /** Holds the ReC Configuration */
	protected ReCFaceConfig recFaceConfig;
	protected NavigationWorkflow currentState;
    protected LabClientBean labClientBean;
    protected ApparatusClientBean apparatusClientBean;
    protected Lab currentLab;
    private com.linkare.rec.impl.client.apparatus.Apparatus currentApparatus = null;
    private Apparatus currentApparatusConfig = null;
    protected ApparatusComboBoxModel apparatusComboBoxModel = new ApparatusComboBoxModel();



	/** Creates a new <code>ReCApplication</code> */
	public ReCApplication() {
		this.currentState = DISCONNECTED_OFFLINE;
    }

    /**
     * @param cause The unexpected error cause
     * @return The <code>NewUnexpectedErrorPane</code>
     */
    public static DefaultDialog<UnexpectedErrorPane> getUnexpectedErrorBox(Throwable cause) {
        if (unexpectedErrorBox == null) {
            unexpectedErrorBox = new DefaultDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(cause));
            unexpectedErrorBox.pack();
            unexpectedErrorBox.setLocationRelativeTo(null);
        }
        unexpectedErrorBox.getContent().setErrorCause(cause);
        return unexpectedErrorBox;
    }

    public BasicService getBasicService() throws UnavailableServiceException {
        if (basicService == null) {
            basicService = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
        }
        return basicService;
    }

    /**
     * @return The web context codebase if available, otherwise returns an empty string
     */
    public String getCodeBase() {
        String codeBase = "";
        try {
            codeBase = getBasicService().getCodeBase().toString();
        } catch (UnavailableServiceException ex) {
            log.warning("Codebase is not available");
        }
        return codeBase;
    }

    public void setUserInfo(String username) {
        labClientBean.getUserInfo().setUserName(username);
        labClientBean.getUserInfo().setPassword(username);
    }

    public String getUsername() {
    	String result = "";
    	if (labClientBean != null && labClientBean.getUserInfo() != null) {
    		result = labClientBean.getUserInfo().getUserName(); 
    	}
		return result;
	}
    
    public UserInfo getUserInfo() {
    	UserInfo result = null;
    	if (labClientBean != null) {
    		result = labClientBean.getUserInfo();
    	}
    	return result;
	}

    public String getCurrentLabName() {
		return ReCResourceBundle.findStringOrDefault(currentLab.getDisplayStringBundleKey(), "<empty>");
	}
    
	public NavigationWorkflow getCurrentState() {
		return currentState;
	}

	public void setCurrentState(NavigationWorkflow newState) {
        this.currentState = newState;
    }

    public ReCFaceConfig getReCFaceConfig() {
        return recFaceConfig;
    }

    protected void setReCFaceConfig(ReCFaceConfig reCFaceConfig) {
        this.recFaceConfig = reCFaceConfig;
    }

	public ApparatusComboBoxModel getApparatusComboBoxModel() {
		return apparatusComboBoxModel;
	}
	
	public IChatServer getChatServer() {
		return labClientBean;
	}
	
	// -------------------------------------------------------------------------
	// Application Startup Workflow

	@Override
    protected void initialize(String[] args) {
        super.initialize(args);

        resourceMap = getContext().getResourceMap();

        if (log.isLoggable(Level.FINE)) {
            log.fine("Initializing system properties...");
        }

        // TODO Move to jnlp ?
        if (OS.isMacOSX()) {
            System.setProperty("apple.laf.useScreenMenuBar", "false");
            System.setProperty("apple.awt.textantialiasing", "on");

        } else if (OS.isWindows()) {
            System.setProperty("swing.aatext", "true");

        } else {
            //TODO Check other platforms...
        }
    }

	/**
     * Startup
     */
    @Override
    protected void startup() {

        // TODO The right exit handler!
        ExitListener appExitHandler = new ExitListener() {
            public boolean canExit(EventObject e) {
                Object source = (e != null) ? e.getSource() : null;
                Component owner = (source instanceof Component) ? (Component) source : null;
                int option = JOptionPane.showConfirmDialog(owner, resourceMap.getString("Application.exitListener.message"));
                return option == JOptionPane.YES_OPTION;
            }

            public void willExit(EventObject e) {
                // TODO handle app exit

                if (apparatusClientBean != null) {
                    apparatusClientBean.disconnect();
                    log.info("Apparatus has been client disconnected");
                }
                if (labClientBean != null) {
                    labClientBean.disconnect();
                    log.info("Lab client has been disconnected");
                }
                
                log.fine("Will exit ReC");
            }
        };
        addExitListener(appExitHandler);

        if (log.isLoggable(Level.FINE)) {
            log.fine("Starting ReC");
            log.fine("Running on EDT? " + (SwingUtilities.isEventDispatchThread() ? "YES" : "NO"));
        }

        try {
            // TODO Launch Splash Screen (AppFramework?)
            log.warning("TODO Launch Splash Screen");

            // Check System Properties Availability
            checkSystemProperties();
            log.info("ReC System Properties are checked.");

            // Unmarshal xml configuration
            String configLocationUrl = getCodeBase() + System.getProperty(ReCSystemProperty.RECFACECONFIG.getName());
            if (log.isLoggable(Level.FINE)) {
                log.fine("Unmarshalling ReCFaceConfig from input stream location = " + configLocationUrl);
            }
            InputStream is = ReCProtocols.getURL(configLocationUrl).openConnection().getInputStream();
            recFaceConfig = ReCFaceConfig.unmarshall(is);
            log.info("ReCFaceConfig is unmarshalled.");

            // ORB initialization
            ORBBean.getORBBean();
            log.info("ORBBean is initialized.");

            // Lab Client setup
            labClientBean = new LabClientBean();
            labClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
            labClientBean.addApparatusListSourceListener(this);
            labClientBean.addLabConnectorListener(this);
            
            // Apparatus Client setup
            apparatusClientBean = new ApparatusClientBean();
            apparatusClientBean.addApparatusConnectorListener(this);
            
            // Current Lab setup
//            if(recFaceConfig.isAutoConnectLab()) {
                currentLab = recFaceConfig.getLab().get(0);
//            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("recFaceConfig.isAutoConnectLab() = " + recFaceConfig.isAutoConnectLab());
                log.fine("currentLab = " + currentLab);
            }
            
            // Load Localization Bundles
            for (LocalizationBundle bundle : recFaceConfig.getLocalizationBundle()) {
            	ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
            }
            apparatusComboBoxModel = new ApparatusComboBoxModel(currentLab.getApparatus());

            // Show view
            log.info("Starting user interface...");
            showView();

        } catch (Exception e) {
            log.log(Level.SEVERE, "Some error occured.", e);
            show(getUnexpectedErrorBox(e));
            System.exit(ExceptionCode.THE_FAMOUS_UNKNOWN_ERROR.getId());
        }

    }

    protected void showView() {
        ReCFrameView recView = new ReCFrameView(this);
        getAppListeners().add(recView);
        recView.getFrame().pack();
        recView.getFrame().setLocationRelativeTo(null);
        show(recView);
    }

    /*
     * This method runs after startup has completed and the GUI is
     * visible and ready.  If there are tasks that are worth doing at
     * startup time, but not worth delaying showing the initial GUI,
     * do them here.
     */
    @Override
    protected void ready() {
    	super.ready();
    	if (log.isLoggable(Level.FINE)) {
    		log.fine("Ready");
    	}
        
        // Forward event to the view
        fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_LOGIN));
    }

    @Override
    protected void shutdown() {
    	super.shutdown();
    	if (log.isLoggable(Level.FINE)) {
			log.fine("Shutting down");
		}
        
    }


    /**
     * Check for the ReC System Properties Availability.
     *
     * @throws ReCConfigurationException If some required property is missing.
     */
    public void checkSystemProperties() throws ReCConfigurationException {
        List<String> missingRequiredProperties = new ArrayList<String>();

        for (ReCSystemProperty property : ReCSystemProperty.values()) {
            String propertyValue = System.getProperty(property.getName());
            log.fine(property.getName() + "=" + propertyValue);

            if (property.isRequired()) { // Required Property
                if (propertyValue == null || (propertyValue != null && propertyValue.isEmpty())) {
                    missingRequiredProperties.add(property.getName());
                }
            } else { // Optional Property
                log.warning("Optinal ReC system property is missing: " + property);
            }
        }

        if (!missingRequiredProperties.isEmpty()) {
            log.severe("Required ReC system properties are missing: " + missingRequiredProperties);
            throw new ReCConfigurationException(ExceptionCode.MISSING_SYSTEM_PROPERTIES,
                    "Please check the required system properties before run. " + missingRequiredProperties);
        }
    }

    // -------------------------------------------------------------------------
	// Actions
  
    @Action
    public void connect() {
    	if (currentState.canGoTo(CONNECT_PERFORMED)) {
    		setCurrentState(CONNECT_PERFORMED);
    		
    		log.info("Connect user " + getUsername());
    		labClientBean.connect(currentLab.getLocation());
    		
    	}
    }

    @Action
    public void disconnect() {
    	if (currentState.canGoTo(DISCONNECT_PERFORMED)) {
    		setCurrentState(DISCONNECT_PERFORMED);
    		log.info("Disconnect user " + labClientBean.getUserInfo().getUserName());
    		
            apparatusClientBean.disconnect();
    		labClientBean.disconnect();
            apparatusComboBoxModel.setAllApparatusEnabled(false);
    	}
        
//        if(!connectLab && !alreadyDisconnected)// && currentLab != null && currentLab.isEnabled())
//        {
//            alreadyDisconnected = true;
//            DisplayTreeNodeUtils.disableLab(currentLab);
//            if(currentApparatusConfig != null)
//            {
//                DisplayTreeNodeUtils.disableAllApparatusContents(currentApparatusConfig);
//            }
//            apparatusClientBean.disconnect();
//            labClientBean.disconnect();
//            currentLab.setEnabled(false);
//            countDownProgressPanel.setStop(true);
//            controllerPanel.setEnablePlay(false);
//            controllerPanel.setEnableStop(false);
//            
//            finishedDisconnection = true;
//            
//            if(waitDialog != null)
//                waitDialog.setVisible(false);
//            
//            if(recBaseUI.getDesktopLocationBundleKey() != null)
//                mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(recBaseUI.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), true);
//            else
//                mDIDesktopPane.setBackgroundImage(null, false);
//            
//        }
    }
    
    @Action
    public void enterApparatus() {
    	if (currentState.canGoTo(APPARATUS_ENTER_PERFORMED)) {
    		setCurrentState(APPARATUS_ENTER_PERFORMED);
    		
    		apparatusClientBean.getUserInfo().setUserName(getUsername());
            apparatusClientBean.getUserInfo().setPassword(getUsername());
            
            apparatusClientBean.setApparatus(getCurrentApparatus());
            
//            if(isEnterApparatusRoom())
//            {
//                chatFrame.setChatServer(apparatusClientBean);
//                chatFrame.setUser(new com.linkare.rec.acquisition.UserInfo(loginFrame.getUsername()));
//            }
            
//            new Thread()
//            {
//                public void run()
//                {
                    apparatusClientBean.connect();
//                }
//            }.start();
    	}
    }
    
    private com.linkare.rec.impl.client.apparatus.Apparatus getCurrentApparatus() {	
    	currentApparatusConfig = (Apparatus) apparatusComboBoxModel.getSelectedItem();
    	currentApparatus = labClientBean.getApparatusByID(currentApparatusConfig.getLocation());
		return currentApparatus;
	}
    
    // -------------------------------------------------------------------------
	// Event handling

	@Override
    public void labStatusChanged(LabConnectorEvent evt) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Lab status changed to " + evt.getStatusCode());
        }
        switch (evt.getStatusCode()) {
            case LabConnectorEvent.STATUS_CONNECTING:
                log.fine("STATUS_CONNECTING");
                break;
            case LabConnectorEvent.STATUS_CONNECTED:
                log.fine("STATUS_CONNECTED");
                setCurrentState(CONNECTED_TO_LAB);
                break;
            case LabConnectorEvent.STATUS_DISCONNECTING:
                log.fine("STATUS_DISCONNECTING");
                break;
            case LabConnectorEvent.STATUS_DISCONNECTED:
                log.fine("STATUS_DISCONNECTED");
                setCurrentState(DISCONNECTED_OFFLINE);
                break;
            case LabConnectorEvent.STATUS_UNREACHABLE:
                log.fine("STATUS_UNREACHABLE");
                setCurrentState(DISCONNECTED_OFFLINE);
                break;
            case LabConnectorEvent.STATUS_NOT_AUTHORIZED:
                log.fine("STATUS_NOT_AUTHORIZED");
                setCurrentState(DISCONNECTED_OFFLINE);
                break;
            case LabConnectorEvent.STATUS_NOT_REGISTERED:
                log.fine("STATUS_NOT_REGISTERED");
                setCurrentState(DISCONNECTED_OFFLINE);
                break;
            default:
                log.warning("Unknown lab status!");
        }
        
        // Forward event to the view
        fireLabStateChanged(evt);
        
    }

    @Override
    public void apparatusListChanged(ApparatusListChangeEvent evt) {
    	
        if(evt!=null && evt.getApparatus()!=null) {
        	
        	if (log.isLoggable(Level.FINE)) {
                log.fine("Apparatus list change event: " + Arrays.deepToString(evt.getApparatus()));
                log.fine("Total available apparatus: " + evt.getApparatus().length);
            }
        	
        	// Enable apparatus combo box list
        	for (com.linkare.rec.impl.client.apparatus.Apparatus clientApparatus : evt.getApparatus()) {
        		Apparatus apparatus = apparatusComboBoxModel.getApparatus(clientApparatus.getHardwareInfo().getHardwareUniqueID());
        		
        		if (apparatus != null && !apparatus.isEnabled()) {
        			apparatus.setEnabled(true);
        		}
        	}
        	
        	// Update view
        	apparatusComboBoxModel.fireContentsChanged(this);
        	
        	// Forward event to the view
            fireApparatusListChanged(evt);
        }
    }

    // -------------------------------------------------------------------------
	// Apparatus events
    
    public enum ApparatusEvent {
    	CONNECTING,
    	CONNECTED,
    	DISCONNECTING,
    	DISCONNECTED,
    	LOCKABLE,
    	LOCKED,
    	STATECONFIGURING,
    	STATECONFIGURED,
    	INCORRECTSTATE,
    	MAXUSERS,
    	NOTAUTHORIZED,
    	NOTOWNER,
    	NOTREGISTERED,
    	STATECONFIGERROR,
    	STATERESETING,
    	STATERESETED,
    	STATESTARTING,
    	STATESTARTED,
    	STATESTOPING,
    	STATESTOPED,
    	STATEUNKNOW,
    	UNREACHABLE;
    }
    
    @Override
	public void apparatusConnecting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt);
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(CONNECTING, evt);
	}
    
    @Override
	public void apparatusConnected(ApparatusConnectorEvent evt) {
    	if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt);
        }
		setCurrentState(CONNECTED_TO_APPARATUS);
		
		// Forward event to the view
        fireApparatusStateChanged(CONNECTED, evt);
	}
    
    @Override
	public void apparatusDisconnecting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(DISCONNECTING, evt);
	}

	@Override
	public void apparatusDisconnected(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(DISCONNECTED, evt);
	}

	@Override
	public void apparatusLockable(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt);
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(LOCKABLE, evt);
	}

	@Override
	public void apparatusLocked(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(LOCKED, evt);
	}
	
	@Override
	public void apparatusStateConfiguring(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGURING, evt);
	}
	
	@Override
	public void apparatusStateConfigured(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGURED, evt);
	}
	
	@Override
	public void apparatusIncorrectState(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(INCORRECTSTATE, evt);
	}

	@Override
	public void apparatusMaxUsers(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(MAXUSERS, evt);
	}

	@Override
	public void apparatusNotAuthorized(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(NOTAUTHORIZED, evt);
	}

	@Override
	public void apparatusNotOwner(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(NOTOWNER, evt);
	}

	@Override
	public void apparatusNotRegistered(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(NOTREGISTERED, evt);
	}

	@Override
	public void apparatusStateConfigError(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGERROR, evt);
	}

	@Override
	public void apparatusStateReseting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATERESETING, evt);
	}
	
	@Override
	public void apparatusStateReseted(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATERESETED, evt);
	}
	
	@Override
	public void apparatusStateStarting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATESTARTING, evt);
	}

	@Override
	public void apparatusStateStarted(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATESTARTED, evt);
	}
	
	@Override
	public void apparatusStateStoping(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATESTOPING, evt);
	}

	@Override
	public void apparatusStateStoped(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
	
		// Forward event to the view
        fireApparatusStateChanged(STATESTOPED, evt);
	}

	@Override
	public void apparatusStateUnknow(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(STATEUNKNOW, evt);
	}

	@Override
	public void apparatusUnreachable(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		// TODO Auto-generated method stub
		
		// Forward event to the view
        fireApparatusStateChanged(UNREACHABLE, evt);
	}

    // -------------------------------------------------------------------------
    // Application Listeners

    public List<ReCApplicationListener> getAppListeners() {
        if (appListeners == null) {
            appListeners = new ArrayList<ReCApplicationListener>();
        }
        return appListeners;
    }

    public void addReCApplicationListener(ReCApplicationListener listener) {
        getAppListeners().add(listener);
    }

    public void removeReCApplicationListener(ReCApplicationListener listener) {
        getAppListeners().remove(listener);
    }

    public void fireLabStateChanged(LabConnectorEvent evt) {
        for (ReCApplicationListener listener : getAppListeners()) {
            listener.labStateChanged(evt);
        }
    }
    
    public void fireApparatusListChanged(ApparatusListChangeEvent evt) {
        for (ReCApplicationListener listener : getAppListeners()) {
            listener.apparatusListChanged(evt);
        }
    }
    
    public void fireApparatusStateChanged(ApparatusEvent evtSelector, ApparatusConnectorEvent evt) {
        for (ReCApplicationListener listener : getAppListeners()) {
            listener.apparatusStateChanged(evtSelector, evt);
        }
    }
    
    public void fireApplicationEvent(ReCAppEvent evt) {
        for (ReCApplicationListener listener : getAppListeners()) {
            listener.applicationEvent(evt);
        }
    }

    // -------------------------------------------------------------------------
    // Static

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ReCApplication
     */
    public static ReCApplication getApplication() {
        return Application.getInstance(ReCApplication.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        Application.launch(ReCApplication.class, args);
    }

}
