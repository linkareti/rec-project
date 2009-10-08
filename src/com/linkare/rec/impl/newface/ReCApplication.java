/*
 * ReCApplication.java
 */

package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.NavigationWorkflow.*;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.*;

import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Window;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;

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
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.client.customizer.ICustomizerSecurity;
import com.linkare.rec.impl.client.experiment.DefaultExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpHistory;
import com.linkare.rec.impl.client.experiment.ExpHistoryDisplayFactory;
import com.linkare.rec.impl.client.experiment.ExperimentHistory;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.exceptions.ExceptionCode;
import com.linkare.rec.impl.exceptions.ReCConfigurationException;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.component.ApparatusComboBoxModel;
import com.linkare.rec.impl.newface.component.DefaultDialog;
import com.linkare.rec.impl.newface.component.UnexpectedErrorPane;
import com.linkare.rec.impl.newface.config.Apparatus;
import com.linkare.rec.impl.newface.config.Display;
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
import com.linkare.rec.impl.newface.display.DefaultDisplayFactory;
import com.linkare.rec.impl.newface.display.DisplayFactory;

/**
 * The main class of the application.
 */
public class ReCApplication extends SingleFrameApplication 
	implements ApparatusListSourceListener, LabConnectorListener, ApparatusConnectorListener, ICustomizerListener, ExpHistoryDisplayFactory {

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
        if (mrl.equals("")) {
            log.info("There is not a valid media to play for this " +
                    "experience. Proceding without video.");
            return;
        }
        
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
     * Temporary flag that enables video development.
     * 
     * FIXME Remove IS_VIDEO_DEVELOPMENT_ENABLED flag after video testing.
     */
    public static boolean IS_VIDEO_DEVELOPMENT_ENABLED = 
    	"yes".equals(System.getProperty(ReCSystemProperty.VIDEO_DEVELOPMENT_ENABLED.getName())); 

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
    
    private Apparatus currentApparatusConfig;
    
    private ApparatusComboBoxModel apparatusComboBoxModel;
    
    private ICustomizer currentCustomizer;
    
    private HardwareAcquisitionConfig currentHardwareAcquisitionConfig;
	
    private HardwareAcquisitionConfig userAcquisitionConfig;
    
    // TODO ExpHistory Refactoring
	private ExperimentHistory experimentHistory;

	private ExpDataModel experimentDataModel;
	
    private List<ExpDataDisplay> experimentDataDisplays;

	private boolean experimentAutoplay = false;


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
		return ReCResourceBundle.findStringOrDefault(currentLab.getDisplayStringBundleKey(), "");
	}
    
    public String getCurrentApparatusHardwareFamiliarName() {
    	return currentApparatus.getHardwareInfo().getFamiliarName();
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
	
	private com.linkare.rec.impl.client.apparatus.Apparatus updateCurrentApparatusFromComboModel() {	
		setSelectedApparatusConfig((Apparatus) apparatusComboBoxModel.getSelectedItem());
    	currentApparatus = labClientBean.getApparatusByID(currentApparatusConfig.getLocation());
    	return currentApparatus;
	}
	
	public void setSelectedApparatusConfig(Apparatus apparatus) {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Selected apparatus is " + apparatus);
		}
		this.currentApparatusConfig = apparatus;
		
		// Notify the view
        fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SELECTED_APPARATUS_CHANGE));
	}
	
	public Apparatus getSelectedApparatusConfig() {
		return currentApparatusConfig;
	}
	
	public ICustomizer getCurrentCustomizer() {
		return currentCustomizer;
	}

    public VideoViewerController getMediaController() {
        return mediaController;
    }
    
    public ExperimentHistory getExperimentHistory() {
		return experimentHistory;
	}
    
    public ExpDataModel getExperimentDataModel() {
		return experimentDataModel;
	}

	public List<ExpDataDisplay> getExperimentDataDisplays() {
		return experimentDataDisplays;
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
            //TODO Check other platforms
        }
    }

	/**
     * Startup
     */
    @Override
    protected void startup() {
    	
        // TODO The right Aplication exit handler!
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
    	if (log.isLoggable(Level.FINE)) {
			log.fine("Launching view on " + Thread.currentThread());
		}
        ReCFrameView recView = new ReCFrameView(this);
        getAppListeners().add(recView);
        recView.getFrame().pack();
        recView.getFrame().setLocationRelativeTo(null);
        show(recView);
        
        // Ask the view to show login box
        fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_LOGIN));
    }
    
    @Override
    public void show(View view) {
    	// Do not load the session state for the View
    	// TODO Add to ReCFaceConfig
    	
        RootPaneContainer c = (RootPaneContainer) view.getRootPane().getParent();
        ((Window) c).setVisible(true);
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
    	// FIXME This stage is not reached. Why?
    }

    @Override
    protected void shutdown() {
//      Save session state for the component hierarchy rooted by
//      the mainFrame.  SingleFrameApplication subclasses that override
//      shutdown need to remember call {@code super.shutdown()}.
    	super.shutdown();
    	
    	if (log.isLoggable(Level.FINE)) {
			log.fine("Shutting down and saving session state");
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

			// TODO Verify if this is the best place to initializeMediaController
    		if (IS_VIDEO_DEVELOPMENT_ENABLED) {
    			initializeMediaController();
    		}
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
    }
    
    @Action
    public void toggleApparatusState() {
    	if (currentState.canGoTo(APPARATUS_CONNECT_PERFORMED)) {
    		
    		setCurrentState(APPARATUS_CONNECT_PERFORMED);
            
    		apparatusClientBean.getUserInfo().setUserName(getUsername());
            apparatusClientBean.getUserInfo().setPassword(getUsername());
            
            apparatusClientBean.setApparatus(updateCurrentApparatusFromComboModel());
            
            // CRITICAL Check this background task implmentation
            new Thread() {
                public void run() {
                    apparatusClientBean.connect(); // Background task
                }
            }.start();
                    
    	} else if (currentState.canGoTo(APPARATUS_DISCONNECT_PERFORMED)) {
    		
    		apparatusClientBean.disconnect();
    	}
    }

    /**
     * Apparatus lock and play action
     */
    @Action
    public void play() {
    	if (currentApparatus != null) {
    		if (log.isLoggable(Level.FINE)) {
				log.fine("play");
			}
    		apparatusClientBean.lock();
    	}
    }

    public void setExperimentAutoplay(boolean enabled) {
    	if (log.isLoggable(Level.FINE)) {
			log.fine("Auto-play enabled = " + enabled);
		}
    	this.experimentAutoplay = enabled;
    }
    
    public boolean getExperimentAutoplay() {
    	return experimentAutoplay;
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
        	
        	updateCurrentApparatusFromComboModel();
        	
        	// Forward events to the view
            fireApparatusListChanged(evt);
        }
    }

    // -------------------------------------------------------------------------
    // Video events

	//Bruno ver se é necessário mais algum evento e tratar os eventos no cliente adequadamente
    public enum VideoEvent {
        NOTCONNECTED,
        STOPPED,
        TIMECHANGED
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
		
		// Forward event to the view
        fireApparatusStateChanged(CONNECTING, evt);
	}
    
    @Override
	public void apparatusConnected(ApparatusConnectorEvent evt) {
    	if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt);
        }
		
		// Load customizer
		currentCustomizer = CustomizerUIUtil.loadCustomizer(
				ReCResourceBundle.findString(currentApparatusConfig.getCustomizerClassLocationBundleKey()));
		
		// Set user info
		if(currentCustomizer instanceof ICustomizerSecurity) {
            ((ICustomizerSecurity)currentCustomizer).setUserInfo(new com.linkare.rec.acquisition.UserInfo(getUsername()));
		}
		
		// Set current base hardware aquisition config 
		currentHardwareAcquisitionConfig = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
		
		// Init customizer
		currentCustomizer.setHardwareInfo(currentApparatus.getHardwareInfo());
		currentCustomizer.setHardwareAcquisitionConfig(currentHardwareAcquisitionConfig);
		
		// Listen to current customizer events (done/canceled)
		currentCustomizer.addICustomizerListener(this);
		
        if (IS_VIDEO_DEVELOPMENT_ENABLED) {
            playMedia(ReCResourceBundle.findString(currentApparatusConfig.getMediaConfig().getVideoLocation()));
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
		
		// Forward event to the view
        fireApparatusStateChanged(DISCONNECTING, evt);
	}

	@Override
	public void apparatusDisconnected(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }

        if (IS_VIDEO_DEVELOPMENT_ENABLED) {
            stopMedia();
      	}
        
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
		
		if (currentState.matches(APPARATUS_CONFIGURED)) {
			// Forward event to the view
			fireApparatusStateChanged(LOCKABLE, evt);
		}
	}

	@Override
	public void apparatusLocked(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		setCurrentState(APPARATUS_LOCKED);
		
        if(currentHardwareAcquisitionConfig == null) {
        	// TODO Check if this is really needed.
        	log.severe("SETTING currentBaseHardwareAcquisitionConfig again!");
        	currentHardwareAcquisitionConfig = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
        }
        apparatusClientBean.configure(currentHardwareAcquisitionConfig);
		
		// Forward event to the view
        fireApparatusStateChanged(LOCKED, evt);
	}
	
	@Override
	public void apparatusStateConfiguring(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGURING, evt);
	}
	
	@Override
	public void apparatusStateConfigured(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
        if(currentState.matches(APPARATUS_LOCKED)){
            apparatusClientBean.start();
        } else {
        	// TODO Check if this is really needed. 
        	log.severe("Appartus SHOULD be locked?");
        }
        
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGURED, evt);
	}
	
	@Override
	public void apparatusIncorrectState(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(INCORRECTSTATE, evt);
	}

	@Override
	public void apparatusMaxUsers(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(MAXUSERS, evt);
	}

	@Override
	public void apparatusNotAuthorized(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(NOTAUTHORIZED, evt);
	}

	@Override
	public void apparatusNotOwner(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(NOTOWNER, evt);
	}

	@Override
	public void apparatusNotRegistered(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(NOTREGISTERED, evt);
	}

	@Override
	public void apparatusStateConfigError(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATECONFIGERROR, evt);
	}

	@Override
	public void apparatusStateReseting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATERESETING, evt);
	}
	
	@Override
	public void apparatusStateReseted(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATERESETED, evt);
	}
	
	@Override
	public void apparatusStateStarting(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATESTARTING, evt);
	}

	@Override
	public void apparatusStateStarted(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
        
        ExperimentHistory expHistory = new ExperimentHistory(this, evt.getDataSource(), apparatusClientBean.getApparatus(), currentApparatusConfig);
        // CRITICAL Confirmar se o estado APPARATUS_LOCKED assegura que a experiment é mesmo minha!
        expHistory.setLocallyOwned(currentState.matches(APPARATUS_LOCKED));
        
        if(currentState.matches(APPARATUS_LOCKED)) {
            startExperiment(expHistory);
            
            // Forward event to the view
            fireApparatusStateChanged(STATESTARTED, evt);
        }
	}
	
	@Override
	public void startExperiment(ExpHistory expHistory) {
		
		setCurrentState(APPARATUS_STARTED);
		
		experimentHistory = (ExperimentHistory) expHistory;
        DisplayFactory factory = new DefaultDisplayFactory();
        
        // Was the user smart enough to make is own DisplayFactory?
		String factoryLocation = null;
		try {
			factoryLocation = ReCResourceBundle.findStringOrDefault(
					experimentHistory.getNewApparatusConfig()
							.getDisplayFactoryClassLocationBundleKey(), null);
		} catch (Exception ignored) {
			// don't print the not found exception please...
		}
		// Of course not... maybe he didn't want to...
		if (factoryLocation == null) {
			// Load default
			factory = new DefaultDisplayFactory();
			
		} else { // Ok the user wants to load his own Factory
			try {
				Object displayFactoryTemp = java.beans.Beans.instantiate(this
						.getClass().getClassLoader(), factoryLocation);
				if (java.beans.Beans.isInstanceOf(displayFactoryTemp,
						DisplayFactory.class))
					factory = (DisplayFactory) displayFactoryTemp;
			} catch (Exception e) {
				log.log(Level.SEVERE,
						"Could not instantiate the display factory", e);
			}
		}
        
		if (factory != null) {
			// I will only give the selected displays :)
			List<Display> selectedDisplays = new ArrayList<Display>();
			
			List<Display> availableDisplays = experimentHistory.getNewApparatusConfig().getDisplay();
			
			for (Display display : availableDisplays) {
				if (display.isSelected()) {
					selectedDisplays.add(display);
				}
			}
			factory.init(selectedDisplays);
			factory.setAcquisitionInfo(experimentHistory.getApparatus()
					.getHardwareInfo());
			try {
				factory.setAcquisitionConfig(experimentHistory.getProducerWrapper()
						.getAcquisitionHeader());
			} catch (Exception e) {
				log.log(Level.SEVERE, "Could not set aquisition config", e);
			}
			experimentDataDisplays = factory.getDisplays();
		}
		
		// Couldn't read from xml or from user
		if (experimentDataDisplays == null) {
			try {
				experimentDataDisplays = new ArrayList<ExpDataDisplay>();
				Object dataDisplayTemp = java.beans.Beans
						.instantiate(this.getClass().getClassLoader(),
								"com.linkare.rec.impl.baseUI.DefaultExperimentDataTable"); // CRITICAL Check default datatable
				if (java.beans.Beans.isInstanceOf(dataDisplayTemp,
						ExpDataDisplay.class))
					experimentDataDisplays.set(0, (ExpDataDisplay) dataDisplayTemp);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Could not instantiate default datatable", e);
			}
		}
		
		// Did the user defined is own datamodel?
		String dataModelLocation = null;
		try {
			dataModelLocation = ReCResourceBundle.findStringOrDefault(
					expHistory.getApparatusConfig()
							.getDataModelClassLocationBundleKey(), null);
		} catch (Exception ignored) {
			// don't print the not found exception please...
		}

		if (dataModelLocation != null) {
			try {
				Object expDataModelTemp = java.beans.Beans.instantiate(this
						.getClass().getClassLoader(), dataModelLocation);
				if (java.beans.Beans.isInstanceOf(expDataModelTemp,
						ExpDataModel.class))
					experimentDataModel = (ExpDataModel) expDataModelTemp;
			} catch (Exception e) {
				log.log(Level.SEVERE, "Could not instantiate datamodel", e);
			}
		}
		// if the user didn't defined is data model, then use the default one
		if (experimentDataModel == null) {
			experimentDataModel = new DefaultExpDataModel();
		}
		
		try {
			experimentDataModel.setDpwDataSource(expHistory.getProducerWrapper());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed data output connection...", e);
			
			// TODO statusPanelApparatus Failed data output connection
//			statusPanelApparatus
//					.setStatus(ReCResourceBundle
//							.findStringOrDefault(
//									"ReCBaseUI$rec.bui.linkare.rec.status.failedDataoutputConnection",
//									"Failed data output connection..."));
			return;
		}
	}
	
	@Override
	public void showExperimentHeader(ExpHistory history) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void apparatusStateStoping(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATESTOPING, evt);
	}

	@Override
	public void apparatusStateStoped(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
	
		// Forward event to the view
        fireApparatusStateChanged(STATESTOPED, evt);
	}

	@Override
	public void apparatusStateUnknow(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(STATEUNKNOW, evt);
	}

	@Override
	public void apparatusUnreachable(ApparatusConnectorEvent evt) {
		if (log.isLoggable(Level.FINE)) {
            log.fine("ApparatusConnectorEvent " + evt.getMessage());
        }
		
		// Forward event to the view
        fireApparatusStateChanged(UNREACHABLE, evt);
	}
	
	// -------------------------------------------------------------------------
	// Customizer events
	
	@Override
	public void done() {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Customizer Done");
		}
		
		// Store user's last acquisition configuration
		userAcquisitionConfig = getCurrentCustomizer().getAcquisitionConfig();
		
		setCurrentState(APPARATUS_CONFIGURED);
		
		if (experimentAutoplay) {
			play();
		} else {
			// Forward event to the view
			fireApplicationEvent(new ReCAppEvent(this, ReCCommand.APPARATUS_CONFIGURED));
		}
	}
	
	@Override
	public void canceled() {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Customizer Canceled");
		}
		
		// TODO Auto-generated method stub
		
		// Forward event to the view
        //fireApplicationEvent(new ReCAppEvent(this, ReCCommand.CUSTOMIZER_CANCELED));
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
