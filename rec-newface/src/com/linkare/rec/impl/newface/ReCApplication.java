/*
 * ReCApplication.java
 */

package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.ReCApplication.NavigationWorkflow.*;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.*;

import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import java.awt.Canvas;
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
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.ApparatusClientBean;
import com.linkare.rec.impl.client.LabClientBean;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.client.customizer.CustomizerUIUtil;
import com.linkare.rec.impl.client.customizer.ICustomizer;
import com.linkare.rec.impl.client.customizer.ICustomizerSecurity;
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
import com.linkare.rec.impl.newface.component.media.MediaSetup;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.component.media.events.MediaApplicationEventListener;

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
     * Sets the video output for this application controller.
     * @param videoOutput
     */
    void setVideoOutput(Canvas videoOutput) {
        mediaController.setVideoOutput(videoOutput);
    }

    /**
     * Initializes the mediaController with default parameters for vlc, if it
     * hasn't been initialized yet.
     */
    private void initializeMediaController() {

        if (mediaController == null) {
            //TODO local mais adequado para fazer o setup?
            MediaSetup.setup();
            String[] defaultVlcParams = MediaSetup.getDefaultMediaParameters();
            mediaController = VideoViewerController.getInstance(defaultVlcParams);
            mediaController.addMediaApplicationEventListener(new MediaApplicationEventListener() {
                @Override
                public void timeChanged(MediaTimeChangedEvent evt) {
                    log.fine("Handling time changed!!!!!!!");
                    //TODO lançar evento para a view para colocar slider com time actual do controller.
                }

                @Override
                public void notConnected(MediaStoppedEvent evt) {
                    log.fine("Handling not connected!!!!!!!");
                    //TODO em streaming, não se conectou porque deixou de receber ESs. Deve começar de novo, n fazer nd ou mostrar msg ao utilizador?
                }

                @Override
                public void stopped(MediaStoppedEvent evt) {
                    log.fine("Handling stopped!!!!!!!");
                    //TODO lançar evento para a view para colocar slider a 0.
                }
            });
        }
    }

    /**
     * Plays the media identified by the given mrl.
     * @param mrl URL for the media to play.
     */
    public void playMedia(String mrl) {

        log.info("Playing media: " + mrl);
        mediaController.setMediaToPlay(mrl);
        mediaController.play();
    }

    /**
     * Stops the media played. Releases the media resources.
     */
    private void stopMedia() {
        log.info("Stopping media...");
        mediaController.stop();
        mediaController.releaseMedia();
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
		ORG_OMG_CORBA_ORBSINGLETONCLASS("org.omg.CORBA.ORBSingletonClass", true),
        REC_VIDEO_ENABLED("video.enabled", false),
        VLC_PLUGINS_FILENAME("vlc.plugins.filename", true),
        VLC_PLUGINS_DESTDIR("vlc.plugins.destdir", true);


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
		LAB_CONNECT_PERFORMED,
		CONNECTED_TO_LAB,
		APPARATUS_CONNECT_PERFORMED,
		CONNECTED_TO_APPARATUS,
		APPARATUS_DISCONNECT_PERFORMED,
		LAB_DISCONNECT_PERFORMED;
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
					transitions(LAB_CONNECT_PERFORMED));
			
			availableTransitions.put(CONNECTED_TO_LAB, 
					transitions(APPARATUS_CONNECT_PERFORMED, LAB_DISCONNECT_PERFORMED));
			
			availableTransitions.put(CONNECTED_TO_APPARATUS, 
					transitions(LAB_DISCONNECT_PERFORMED, 
							APPARATUS_DISCONNECT_PERFORMED /*returns to CONNECTED_TO_LAB*/ ));
			
			availableTransitions.put(LAB_DISCONNECT_PERFORMED, 
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
    private BasicService basicService;

    /** ResourceMap shortcut */
    private ResourceMap resourceMap;

    /** Holds the application shared UnexpectedErrorBox Dialog */
    private static DefaultDialog<UnexpectedErrorPane> unexpectedErrorBox;

    /** Holds the listeners to the ReC Application underlying model changes */
    private List<ReCApplicationListener> appListeners;

    /**
     * Controller for the video module.
     */
    private VideoViewerController mediaController;

    // ReC Application state model

    /** Holds the ReC Configuration */
    private ReCFaceConfig recFaceConfig;
    private NavigationWorkflow currentState;
    private LabClientBean labClientBean;
    private ApparatusClientBean apparatusClientBean;
    private Lab currentLab;
    private com.linkare.rec.impl.client.apparatus.Apparatus currentApparatus;
    private Apparatus selectedApparatusConfig;
    private ApparatusComboBoxModel apparatusComboBoxModel;
    private ICustomizer currentCustomizer;
    private HardwareAcquisitionConfig hardwareAcquisitionConfig;


	/** Creates a new <code>ReCApplication</code> */
	public ReCApplication() {
		setCurrentState(DISCONNECTED_OFFLINE);
		apparatusComboBoxModel = new ApparatusComboBoxModel();
    }
	
	public ReCFaceConfig getRecFaceConfig(){
		return recFaceConfig;
	}
	
	public ApparatusClientBean getApparatusClientBean(){
		return apparatusClientBean;
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
	
	public HardwareAcquisitionConfig getHardwareAcquisitionConfig() {
		return hardwareAcquisitionConfig;
	}
	
	public void setHardwareAcquisitionConfig(
			HardwareAcquisitionConfig hardwareAcquisitionConfig) {
		this.hardwareAcquisitionConfig = hardwareAcquisitionConfig;
	}

	public void setSelectedApparatusConfig(Apparatus apparatus) {
		this.selectedApparatusConfig = apparatus;
		
		// Notify the view
        fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SELECTED_APPARATUS_CONFIG_CHANGE));
	}
	
	private com.linkare.rec.impl.client.apparatus.Apparatus getCurrentApparatus() {	
    	setSelectedApparatusConfig((Apparatus) apparatusComboBoxModel.getSelectedItem());
    	currentApparatus = labClientBean.getApparatusByID(selectedApparatusConfig.getLocation());
		return currentApparatus;
	}
    
	
	public Apparatus getSelectedApparatusConfig() {
		return selectedApparatusConfig;
	}
	
	public ICustomizer getCurrentCustomizer() {
		return currentCustomizer;
	}

    public VideoViewerController getMediaController() {
        return mediaController;
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
            
            // User List
            apparatusClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
            labClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
      
            // TODO Current Lab setup
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
        
        // Ask the view to show login box
        fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_LOGIN));
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
    	// FIXME This stage is not reached
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
    	if (currentState.canGoTo(LAB_CONNECT_PERFORMED)) {
    		setCurrentState(LAB_CONNECT_PERFORMED);
    		
    		log.info("Connect user " + getUsername());
    		labClientBean.connect(currentLab.getLocation());

            initializeMediaController();
    	}
    }

    @Action
    public void disconnect() {
    	if (currentState.canGoTo(LAB_DISCONNECT_PERFORMED)) {
    		setCurrentState(LAB_DISCONNECT_PERFORMED);
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
    public void toggleApparatusState() {
    	if (currentState.canGoTo(APPARATUS_CONNECT_PERFORMED)) {
    		setCurrentState(APPARATUS_CONNECT_PERFORMED);
    		
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

    	} else if (currentState.canGoTo(APPARATUS_DISCONNECT_PERFORMED)) {
    		
    		apparatusClientBean.disconnect();

    	}
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
        	
        	// Forward events to the view
            fireApparatusListChanged(evt);
        }
    }

    // -------------------------------------------------------------------------
    // Video events

    public enum VideoEvent {
        
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
		
		// Load customizer
		currentCustomizer = CustomizerUIUtil.loadCustomizer(
				ReCResourceBundle.findString(selectedApparatusConfig.getCustomizerClassLocationBundleKey()));
		
		// Set user info
		if(currentCustomizer instanceof ICustomizerSecurity) {
            ((ICustomizerSecurity)currentCustomizer).setUserInfo(new com.linkare.rec.acquisition.UserInfo(getUsername()));
		}
		
		// Set Hardware 
		setHardwareAcquisitionConfig(currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig());
		
		// Init customizer
		currentCustomizer.setHardwareInfo(getCurrentApparatus().getHardwareInfo());
		currentCustomizer.setHardwareAcquisitionConfig(getHardwareAcquisitionConfig());

        if ("S".equals(System.getProperty(ReCSystemProperty.REC_VIDEO_ENABLED.getName())))
//            playMedia(ReCResourceBundle.findString(selectedApparatusConfig.getVideoLocation()));
//            playMedia("rtsp://elabmc.ist.utl.pt/radiare.sdp");
            playMedia("/home/bcatarino/Documentos/NetBeansProjects/xpto.avi");
		
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

        if ("S".equals(System.getProperty(ReCSystemProperty.REC_VIDEO_ENABLED.getName())))
            stopMedia();
        
		// Disconnect from apparatus but remain connected to laboratory
		setCurrentState(CONNECTED_TO_LAB);

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
