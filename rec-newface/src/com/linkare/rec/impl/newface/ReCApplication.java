/*
 * ReCApplication.java
 */

package com.linkare.rec.impl.newface;

import com.linkare.rec.impl.client.LabClientBean;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.exceptions.ExceptionCode;
import com.linkare.rec.impl.exceptions.ReCConfigurationException;
import com.linkare.rec.impl.newface.component.DefaultDialog;
import com.linkare.rec.impl.newface.component.UnexpectedErrorPane;
import com.linkare.rec.impl.newface.config.Lab;
import com.linkare.rec.impl.newface.config.ReCFaceConfig;
import com.linkare.rec.impl.newface.utils.OS;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.utils.ORBBean;
import java.awt.Component;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;

/**
 * The main class of the application.
 */
public class ReCApplication extends SingleFrameApplication implements ApparatusListSourceListener, LabConnectorListener {

    private static final Logger log = Logger.getLogger(ReCApplication.class.getName());
    
    static {
        // Handler for application uncaught exceptions
        // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4714232
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
    protected boolean connectedToLab = false;
    protected LabClientBean labClientBean;
    protected Lab currentLab;
    protected DefaultComboBoxModel apparatusComboBoxModel;



	/** Creates a new <code>ReCApplication</code> */
	public ReCApplication() {
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

    public boolean isConnectedToLab() {
        return connectedToLab;
    }

    public void setConnectedToLab(boolean connectedToLab) {
        this.connectedToLab = connectedToLab;
    }

    public ReCFaceConfig getReCFaceConfig() {
        return recFaceConfig;
    }

    protected void setReCFaceConfig(ReCFaceConfig reCFaceConfig) {
        this.recFaceConfig = reCFaceConfig;
    }

    
    @Override
    protected void initialize(String[] args) {
        super.initialize(args);

        resourceMap = getContext().getResourceMap();

        if (log.isLoggable(Level.FINE)) {
            log.fine("Initializing system properties...");
            //log.fine("Platform: " + OS.getOSName());
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
                //TODO handle exit
                if (labClientBean != null) {
                    labClientBean.disconnect();
                    log.info("Lab client has been disconnected");
                }

//                if (apparatusClientBean != null) {
//                    apparatusClientBean.disconnect();
//                    log.info("Apparatus has been client disconnected");
//                }
                log.fine("Will exit ReC");
            }
        };
        addExitListener(appExitHandler);

        if (log.isLoggable(Level.FINE)) {
            log.fine("Starting ReC");
            log.fine("Running on EDT? " + (SwingUtilities.isEventDispatchThread() ? "YES" : "NO"));
        }

        //TODO Enable and check assertions
        assert SwingUtilities.isEventDispatchThread() == true : "This should run on EDT";

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

            // LabClient setup
            labClientBean = new LabClientBean();
            labClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
            labClientBean.addApparatusListSourceListener(this);
            labClientBean.addLabConnectorListener(this);

            // Current Lab setup
//            if(recFaceConfig.isAutoConnectLab()) {
                currentLab = recFaceConfig.getLab().get(0);
//            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("recFaceConfig.isAutoConnectLab() = " + recFaceConfig.isAutoConnectLab());
                log.fine("currentLab = " + currentLab);
            }

            // Show view
            log.info("Starting user interface...");
            showView();

       } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Some error occured.", e);
            show(getUnexpectedErrorBox(e));
            //System.exit(ExceptionCode.THE_FAMOUS_UNKNOWN_ERROR.getId());
//        } catch (ReCException e) {
//            log.log(Level.SEVERE, "Some error occured.", e);
//            show(getUnexpectedErrorBox(e));
//            System.exit(e.getCode().getId());

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
    }

    @Override
    protected void shutdown() {
        log.fine("Shutting down");
        super.shutdown();
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

    @Action
    public void connect() {
        log.info("Connect user " + labClientBean.getUserInfo().getUserName());
        labClientBean.connect(currentLab.getLocation());
    }

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
                break;
            case LabConnectorEvent.STATUS_DISCONNECTING:
                log.fine("STATUS_DISCONNECTING");
                break;
            case LabConnectorEvent.STATUS_DISCONNECTED:
                log.fine("STATUS_DISCONNECTED");
                break;
            case LabConnectorEvent.STATUS_UNREACHABLE:
                log.fine("STATUS_UNREACHABLE");
                break;
            case LabConnectorEvent.STATUS_NOT_AUTHORIZED:
                log.fine("STATUS_NOT_AUTHORIZED");
                break;
            case LabConnectorEvent.STATUS_NOT_REGISTERED:
                log.fine("STATUS_NOT_REGISTERED");
                break;
            default:
                log.warning("Unknown lab status!");
        }
        
        // Forward event to the view
        fireLabStatusChanged(evt);
        
    }

    @Override
    public void apparatusListChanged(ApparatusListChangeEvent evt) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Apparatus list change event: " + evt);
        }

        if(evt!=null && evt.getApparatus()!=null) {
            // TODO
        }
    }

    

    // -------------------------------------------------------------------------
    // App Listeners

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

    public void fireLabStatusChanged(LabConnectorEvent evt) {
        for (ReCApplicationListener listener : getAppListeners()) {
            listener.labStatusChanged(evt);
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
