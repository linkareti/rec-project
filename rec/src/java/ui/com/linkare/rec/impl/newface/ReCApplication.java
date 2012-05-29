/*
 * ReCApplication.java
 */

package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.NavigationWorkflow.APPARATUS_CONFIGURED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.APPARATUS_CONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.APPARATUS_DISCONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.APPARATUS_LOCKED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.APPARATUS_STARTED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.CONNECTED_TO_APPARATUS;
import static com.linkare.rec.impl.newface.NavigationWorkflow.CONNECTED_TO_LAB;
import static com.linkare.rec.impl.newface.NavigationWorkflow.DISCONNECTED_OFFLINE;
import static com.linkare.rec.impl.newface.NavigationWorkflow.LAB_CONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.NavigationWorkflow.LAB_DISCONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.CONNECTED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.CONNECTING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.DISCONNECTED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.DISCONNECTING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.INCORRECTSTATE;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.LOCKABLE;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.LOCKED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.MAXUSERS;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.NOTAUTHORIZED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.NOTOWNER;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.NOTREGISTERED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATECONFIGERROR;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATECONFIGURED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATECONFIGURING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATERESETED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATERESETING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATESTARTED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATESTARTING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATESTOPED;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATESTOPING;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.STATEUNKNOW;
import static com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent.UNREACHABLE;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.linkare.rec.am.config.Apparatus;
import com.linkare.rec.am.config.Display;
import com.linkare.rec.am.config.Lab;
import com.linkare.rec.am.config.LocalizationBundle;
import com.linkare.rec.am.config.ReCFaceConfig;
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
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.exceptions.ExceptionCode;
import com.linkare.rec.impl.exceptions.ReCConfigurationException;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.ReCAppEvent.ReCCommand;
import com.linkare.rec.impl.newface.component.ApparatusComboBoxModel;
import com.linkare.rec.impl.newface.component.ExperimentHistoryUINode;
import com.linkare.rec.impl.newface.component.LabComboBoxModel;
import com.linkare.rec.impl.newface.component.media.MediaSetup;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.component.media.events.MediaApplicationEventListener;
import com.linkare.rec.impl.newface.component.media.events.MediaNotConnectedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import com.linkare.rec.impl.newface.display.DefaultDisplayFactory;
import com.linkare.rec.impl.newface.display.DisplayFactory;
import com.linkare.rec.impl.newface.utils.OS;
import com.linkare.rec.impl.newface.utils.PreferencesUtils;
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * The main class of the application.
 */
public class ReCApplication extends SingleFrameApplication implements ApparatusListSourceListener,
		LabConnectorListener, ApparatusConnectorListener, ICustomizerListener, ExpHistoryDisplayFactory {

	private static final Logger log = Logger.getLogger(ReCApplication.class.getName());

	/**
	 * Sets the video output for this application controller.
	 * 
	 * @param videoOutput
	 */
	void setVideoOutput(final Canvas videoOutput) {
		mediaController.setVideoOutput(videoOutput);
	}

	/**
	 * Initializes the mediaController with default parameters for vlc, if it
	 * hasn't been initialized yet.
	 */
	private void initializeMediaController() {

		try {
			if (mediaController == null) {

				MediaSetup.setup();
				final String[] defaultVlcParams = MediaSetup.getDefaultMediaParameters();
				mediaController = VideoViewerController.getInstance(defaultVlcParams);
				mediaController.addMediaApplicationEventListener(getMediaApplicationEventListener());
			}
		} catch (final UnsatisfiedLinkError e) {
			ReCApplication.log.severe(e.toString());
			fireApplicationEvent(new ReCAppEvent(this, ReCCommand.CHOOSE_VLC, true));
		}
	}

	private MediaApplicationEventListener getMediaApplicationEventListener() {

		return new MediaApplicationEventListener() {
			@Override
			public void timeChanged(final MediaTimeChangedEvent evt) {
				ReCApplication.log.fine("Handling time changed!!!!!!!");
				// TODO lançar evento para a view para colocar slider com time
				// actual do controller.
			}

			@Override
			public void notConnected(final MediaNotConnectedEvent evt) {
				ReCApplication.log.fine("Handling not connected!!!!!!!");
				if (isApparatusVideoEnabled()) {
					ReCApplication.log.info("Video is enable for the selected apparatus.");
					playMedia(ReCResourceBundle.findString(currentApparatusConfig.getMediaConfig().getVideoLocation()));
				} else {
					ReCApplication.log.info("Video isn't enable for the selected apparatus.");
				}
			}

			@Override
			public void stopped(final MediaStoppedEvent evt) {
				ReCApplication.log.fine("Handling stopped!!!!!!!");
				// TODO lançar evento para a view para colocar slider a 0.
			}
		};
	}

	/**
	 * Plays the given media using the native vlc installed on the user's local
	 * machine.
	 * 
	 * @param mrl
	 */
	public void playMediaExternal(final String mrl) {
		try {
			String vlc = PreferencesUtils.readUserPreference("vlcpath");
			if (vlc == null) {
				vlc = "vlc";
			}
			Runtime.getRuntime().exec(vlc + " " + mrl);
		} catch (final IOException e) {
			ReCApplication.log.info("VLC not installed on the specified directory");
		}
	}
        
        @Action
        public void playMediaExternalAction() {
            playMediaExternal(mediaController.getMediaURL());
        }

	/**
	 * Plays the media identified by the given mrl.
	 * 
	 * @param mrl URL for the media to play.
	 */
	public void playMedia(final String mrl) {

		ReCApplication.log.info("Playing media: " + mrl);
		if (mrl == null || mrl.equals("")) {
			ReCApplication.log.info("There is not a valid media to play for this "
					+ "experience. Proceding without video.");
			return;
		}

		if (mediaController != null) {
		    try {
			mediaController.setMediaToPlay(mrl);
			mediaController.play();
		    } catch (UnsatisfiedLinkError e) {
			askForVlcAction(true);
			playMediaExternal(mrl);
		    }
		} else {
			playMediaExternal(mrl);
		}
	}
        
	public void askForVlcAction(boolean ask) {
	    fireApplicationEvent(new ReCAppEvent(this, ReCCommand.CHOOSE_VLC, ask));
	}
	
        @Action
        public void askForVlcAction() {
            askForVlcAction(false);
        }

	/**
	 * Set the media identified by the given mrl.
	 * 
	 * @param mrl URL for the media to play.
	 */
	public void setMediaToPlay(final String mrl) {
		ReCApplication.log.info("Setting to play media: " + mrl);
		if (mrl == null || mrl.equals("")) {
			ReCApplication.log.info("There is not a valid media to play for this experiment. Proceding without video.");
			return;
		}

		if (mediaController != null) {
			mediaController.setMediaToPlay(mrl);
		} else {
			ReCApplication.log.warning("There is no media controller!");
		}
	}

	/**
	 * Plays the initialized media.
	 */
	public void playMedia() {
		if (mediaController != null && mediaController.getMediaURL() != null) {
			ReCApplication.log.info("Playing media: " + mediaController.getMediaURL());
			mediaController.play();
		} else if (mediaController.getMediaURL() != null) {
			ReCApplication.log.info("Playing media with external player: " + mediaController.getMediaURL());
			playMediaExternal(mediaController.getMediaURL());
		} else {
			ReCApplication.log.warning("There is no initialized media.");
		}
	}

	/**
	 * Getter for the media for the current apparatus configuration.
	 * 
	 * @return Video location or null if the current apparatus doesn't have the
	 *         video enabled.
	 */
	public String getCurrentApparatusVideoLocation() {
		if (isApparatusVideoEnabled()) {
			return ReCResourceBundle.findString(currentApparatusConfig.getMediaConfig().getVideoLocation());
		}
		return null;
	}

	/**
	 * Plays the media for the current apparatus configuration.
	 */
	public void playMediaCurrentApparatus() {
		playMedia(ReCResourceBundle.findString(currentApparatusConfig.getMediaConfig().getVideoLocation()));
	}

	/**
	 * Stops the media played. Releases the media resources.
	 */
	private void stopMedia() {
		if (mediaController == null) {
			return;
		}
		ReCApplication.log.info("Stopping media...");
		mediaController.stop();
		mediaController.releaseMedia();
	}

	/**
	 * flag that enables video development.
	 */
	public static boolean IS_VIDEO_ENABLED = "yes"
			.equals(System.getProperty(ReCSystemProperty.VIDEO_ENABLED.getName()));

	/** Holds the jws basic service context */
	private BasicService basicService;

	/** ResourceMap shortcut */
	private ResourceMap resourceMap;

	/** Yes No Options text */
	private Object[] dialogYesNoOptions;

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

	private final ApparatusComboBoxModel apparatusComboBoxModel;

	private final LabComboBoxModel labComboBoxModel;

	private ICustomizer currentCustomizer;

	private HardwareAcquisitionConfig currentHardwareAcquisitionConfig;

	private HardwareAcquisitionConfig userAcquisitionConfig;

	// private ExpDataModel experimentDataModel;
	//
	// private List<ExpDataDisplay> experimentDataDisplays;

	// private final ExperimentUIData experimentData;

	private boolean experimentAutoplay = false;

	private boolean experimentPlayButtonEnabled = false;

	private ExperimentHistoryUINode lastExperimentHistory;

	private ApparatusConnectorEvent apparatusStateStartedEvent;

	/** Creates a new <code>ReCApplication</code> */
	public ReCApplication() {
		setCurrentState(DISCONNECTED_OFFLINE);
		apparatusComboBoxModel = new ApparatusComboBoxModel();
		labComboBoxModel = new LabComboBoxModel();
	}

	public ApparatusClientBean getApparatusClientBean() {
		return apparatusClientBean;
	}

	public BasicService getBasicService() throws UnavailableServiceException {
		if (basicService == null) {
			basicService = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
		}
		return basicService;
	}

	/**
	 * @return The web context codebase if available, otherwise returns an empty
	 *         string
	 */
	public String getCodeBase() {
		String codeBase = "";
		try {
			codeBase = getBasicService().getCodeBase().toString();
		} catch (final UnavailableServiceException ex) {
			ReCApplication.log.warning("Codebase is not available");
		}
		return codeBase;
	}

	public void setCurrentLab(final Lab lab) {
		currentLab = lab;
	}

	public void setUserInfo(final String username, final String password) {
		labClientBean.getUserInfo().setUserName(username);
		labClientBean.getUserInfo().setPassword(password);
	}

	public String getUsername() {
		String result = "";
		if (labClientBean != null && labClientBean.getUserInfo() != null) {
			result = labClientBean.getUserInfo().getUserName();
		}
		return result;
	}

	public String getPassword() {
		String result = null;
		if (labClientBean != null && labClientBean.getUserInfo() != null) {
			result = labClientBean.getUserInfo().getPassword();
		}
		return result;
	}

	public ExperimentHistoryUINode getLastExperimentHistory() {
		return lastExperimentHistory;
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

	public void setCurrentState(final NavigationWorkflow newState) {
		currentState = newState;
	}

	public ReCFaceConfig getReCFaceConfig() {
		return recFaceConfig;
	}

	protected void setReCFaceConfig(final ReCFaceConfig reCFaceConfig) {
		recFaceConfig = reCFaceConfig;
	}

	public ApparatusComboBoxModel getApparatusComboBoxModel() {
		return apparatusComboBoxModel;
	}

	public LabComboBoxModel getLabComboBoxModel() {
		return labComboBoxModel;
	}

	public IChatServer getChatServer() {
		return labClientBean;
	}

	private com.linkare.rec.impl.client.apparatus.Apparatus updateCurrentApparatusFromComboModel() {
		setSelectedApparatusConfig((Apparatus) apparatusComboBoxModel.getSelectedItem());
		currentApparatus = labClientBean.getApparatusByID(currentApparatusConfig.getLocation());
		return currentApparatus;
	}

	public void setSelectedApparatusConfig(final Apparatus apparatus) {
		currentApparatusConfig = apparatus;
		// Notify the view
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SELECTED_APPARATUS_CHANGE));
	}

	public Apparatus getSelectedApparatusConfig() {
		return currentApparatusConfig;
	}

	public boolean isApparatusVideoEnabled() {
		return ReCApplication.IS_VIDEO_ENABLED && currentApparatusConfig != null
				&& currentApparatusConfig.getMediaConfig() != null
				&& currentApparatusConfig.getMediaConfig().getVideoLocation() != null
				&& !currentApparatusConfig.getMediaConfig().getVideoLocation().equals("");
	}

	public ICustomizer getCurrentCustomizer() {
		return currentCustomizer;
	}

	public VideoViewerController getMediaController() {
		return mediaController;
	}

	// public ExpDataModel getExperimentDataModel() {
	// return experimentDataModel;
	// }
	//
	// public List<ExpDataDisplay> getExperimentDataDisplays() {
	// return experimentDataDisplays;
	// }

	// -------------------------------------------------------------------------
	// Application Startup Workflow

	@Override
	protected void initialize(final String[] args) {
		super.initialize(args);

		resourceMap = getContext().getResourceMap();

		// read yes no option text
		final String yesOption = resourceMap.getString("Application.confirmationDialog.option.yes");
		final String noOption = resourceMap.getString("Application.confirmationDialog.option.no");
		dialogYesNoOptions = new Object[2];
		dialogYesNoOptions[0] = yesOption;
		dialogYesNoOptions[1] = noOption;

		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Initializing system properties...");
		}

		// TODO Move to jnlp ?
		if (OS.isMacOSX()) {
			System.setProperty("apple.laf.useScreenMenuBar", "false");
			System.setProperty("apple.awt.textantialiasing", "on");

		} else if (OS.isWindows()) {
			System.setProperty("swing.aatext", "true");

		} 
		/*else {
			// TODO Check other platforms
		}*/
	}

	/**
	 * Startup
	 */
	@Override
	protected void startup() {

		final ExitListener appExitHandler = new ExitListener() {
			@Override
			public boolean canExit(final EventObject e) {
				final Object source = (e != null) ? e.getSource() : null;
				final Component owner = (source instanceof Component) ? (Component) source : null;
				final int option = JOptionPane.showOptionDialog(owner,
						resourceMap.getString("Application.exitListener.message"),
						resourceMap.getString("Application.confirmationDialog.message"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, dialogYesNoOptions, dialogYesNoOptions[0]);
				return option == JOptionPane.YES_OPTION;
			}

			@Override
			public void willExit(final EventObject e) {
				ReCApplication.log.fine("Exiting ReC...");
			}
		};
		addExitListener(appExitHandler);

		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Starting ReC");
			ReCApplication.log.fine("Running on EDT? " + (SwingUtilities.isEventDispatchThread() ? "YES" : "NO"));
		}

		try {
			// TODO Launch Splash Screen (AppFramework?)
			ReCApplication.log.warning("TODO Launch Splash Screen");

			// log.info("Setting default locale " + PORTUGAL);
			// Locale.setDefault(PORTUGAL);

			ReCApplication.log.info("Defined locale is " + java.util.Locale.getDefault());

			// Check System Properties Availability
			checkSystemProperties();
			ReCApplication.log.info("ReC System Properties are checked.");

			// Unmarshal xml configuration
			String configLocationUrl = System.getProperty(ReCSystemProperty.RECFACECONFIG.getName());
			if (!configLocationUrl.contains("://")) {
				configLocationUrl = getCodeBase() + configLocationUrl;
			}
			if (ReCApplication.log.isLoggable(Level.FINE)) {
				ReCApplication.log
						.fine("Unmarshalling ReCFaceConfig from input stream location = " + configLocationUrl);
			}
			final InputStream is = Protocols.getURL(configLocationUrl).openConnection().getInputStream();
			recFaceConfig = ReCFaceConfig.unmarshall(is);
			ReCApplication.log.info("ReCFaceConfig is unmarshalled.");

			// Lab Client setup
			labClientBean = new LabClientBean();
			labClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
			labClientBean.addApparatusListSourceListener(this);
			labClientBean.addLabConnectorListener(this);

			// Lab combobox model
			labComboBoxModel.addLabList(recFaceConfig.getLab());

			// Apparatus Client setup
			apparatusClientBean = new ApparatusClientBean();
			apparatusClientBean.addApparatusConnectorListener(this);

			// User List
			apparatusClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());
			labClientBean.setUsersListRefreshPeriod(recFaceConfig.getUsersListRefreshRateMs());

			// Load Localization Bundles
			for (final LocalizationBundle bundle : recFaceConfig.getLocalizationBundle()) {
				ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
			}
			for (final Lab lab : recFaceConfig.getLab()) {
				for (final LocalizationBundle bundle : lab.getLocalizationBundle()) {
					ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
				}
				for (final Apparatus apparatus : lab.getApparatus()) {
					for (final LocalizationBundle bundle : apparatus.getLocalizationBundle()) {
						ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
					}
				}
			}

			// Show view
			ReCApplication.log.info("Starting user interface...");
			showView();

		} catch (final Exception e) {
			ReCApplication.log.log(Level.SEVERE, "Some error occured.", e);
			// show(getUnexpectedErrorBox(e));
			System.exit(ExceptionCode.THE_FAMOUS_UNKNOWN_ERROR.getId());
		}

	}

	protected void showView() {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Launching view on " + Thread.currentThread());
		}
		final ReCFrameView recView = new ReCFrameView(this);
		getAppListeners().add(recView);

		final Toolkit tk = Toolkit.getDefaultToolkit();
		final Dimension dimension = tk.getScreenSize();
		recView.getFrame().setPreferredSize(dimension);

		recView.getFrame().pack();

		recView.getFrame().setLocationRelativeTo(null);
		show(recView);

		// Ask the view to show login box
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_LOGIN));
	}

	@Override
	public void show(final View view) {
		// Do not load the session state for the View
		// TODO Add to ReCFaceConfig
		final RootPaneContainer c = (RootPaneContainer) view.getRootPane().getParent();
		((Window) c).setVisible(true);
	}

	/*
	 * This method runs after startup has completed and the GUI is visible and
	 * ready. If there are tasks that are worth doing at startup time, but not
	 * worth delaying showing the initial GUI, do them here.
	 */
	@Override
	protected void ready() {
		super.ready();
		// This stage is reached just after login. Login modal dialog blocks
		// this call.
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Ready");
		}
	}

	@Override
	protected void shutdown() {

		// Save session state for the component hierarchy rooted by
		// the mainFrame. SingleFrameApplication subclasses that override
		// shutdown need to remember call {@code super.shutdown()}.

		if (apparatusClientBean != null) {
			apparatusClientBean.disconnect();
			ReCApplication.log.info("Apparatus has been client disconnected");
		}
		if (labClientBean != null) {
			labClientBean.disconnect();
			ReCApplication.log.info("Lab client has been disconnected");
		}
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Shutting down and saving session state");
		}
		super.shutdown();
	}

	/**
	 * Check for the ReC System Properties Availability.
	 * 
	 * @throws ReCConfigurationException If some required property is missing.
	 */
	public void checkSystemProperties() throws ReCConfigurationException {
		final List<String> missingRequiredProperties = new ArrayList<String>();

		for (final ReCSystemProperty property : ReCSystemProperty.values()) {
			final String propertyValue = System.getProperty(property.getName());
			ReCApplication.log.info(property.getName() + "=" + propertyValue);

			if (property.isRequired()) { // Required Property
				if (propertyValue == null || propertyValue.isEmpty()) {
					missingRequiredProperties.add(property.getName());
				}
			} else { // Optional Property
				if (propertyValue == null || propertyValue.isEmpty()) {
					ReCApplication.log.fine("Optional ReC system property is missing: " + property);
				}
			}
		}

		if (!missingRequiredProperties.isEmpty()) {
			ReCApplication.log.severe("Required ReC system properties are missing: " + missingRequiredProperties);
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

			apparatusComboBoxModel.removeAllElements();
			apparatusComboBoxModel.addApparatusList(currentLab.getApparatus());

			// ORB initialization
			ORBBean.getORBBean();
			ReCApplication.log.info("ORBBean is initialized.");

			ReCApplication.log.info("Connect user " + getUsername());
			labClientBean.connect(currentLab.getLocation());

			// TODO Verify if this is the best place to
			// initializeMediaController
			if (isApparatusVideoEnabled()) {
				initializeMediaController();
			}
		}
	}

	@Action
	public void disconnect() {
		if (currentState.canGoTo(LAB_DISCONNECT_PERFORMED)) {
			setCurrentState(LAB_DISCONNECT_PERFORMED);
			ReCApplication.log.info("Disconnect user " + labClientBean.getUserInfo().getUserName());

			apparatusClientBean.disconnect();
			labClientBean.disconnect();
			apparatusComboBoxModel.setAllApparatusEnabled(false);
			apparatusComboBoxModel.removeAllElements();
		}
	}

	@Action
	public void toggleApparatusState() {
		if (currentState.canGoTo(APPARATUS_CONNECT_PERFORMED)) {

			final com.linkare.rec.impl.client.apparatus.Apparatus apparatus = labClientBean
					.getApparatusByID(currentApparatusConfig.getLocation());
			ReCApplication.log.info("Selected apparatus is " + (apparatus != null ? "online" : "offline"));
			if (apparatus != null) {
				setCurrentState(APPARATUS_CONNECT_PERFORMED);

				apparatusClientBean.getUserInfo().setUserName(getUsername());
				apparatusClientBean.getUserInfo().setPassword(getPassword());

				apparatusClientBean.setApparatus(updateCurrentApparatusFromComboModel());

				// FIXME Check this background task implmentation
				new Thread() {
					@Override
					public void run() {
						setName(getName() + " - ReCApplication - connect");
						apparatusClientBean.connect(); // Background task
					}
				}.start();

			}

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
			if (ReCApplication.log.isLoggable(Level.FINE)) {
				ReCApplication.log.fine("play");
			}

			// block interface so that is impossible o repeate play event
			apparatusStateConfiguring(new ApparatusConnectorEvent(this, ""));

			if (SwingUtilities.isEventDispatchThread()) {
				(new Thread() {
					public void run() {
						apparatusClientBean.lock();
					}
				}).start();
			} else {
				apparatusClientBean.lock();
			}
		}
	}

	public void setExperimentAutoplay(final boolean enabled) {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Auto-play enabled = " + enabled);
		}
		experimentAutoplay = enabled;

		if (experimentAutoplay && experimentPlayButtonEnabled
				&& (currentState.equals(APPARATUS_CONFIGURED) || currentState.equals(APPARATUS_STARTED))) {
			play();
		}
	}

	/**
	 * Apparatus stop action
	 */
	@Action
	public void stop() {
		if (currentApparatus != null) {
			if (ReCApplication.log.isLoggable(Level.FINE)) {
				ReCApplication.log.fine("stop");
			}
			apparatusClientBean.stop();
		}
	}

	public boolean getExperimentAutoplay() {
		return experimentAutoplay;
	}

	/**
	 * @return the experimentPlayButtonEnabled
	 */
	public boolean isExperimentPlayButtonEnabled() {
		return experimentPlayButtonEnabled;
	}

	/**
	 * @param enabled the experimentPlayButtonEnabled to set
	 */
	public void setExperimentPlayButtonEnabled(final boolean enabled) {
		experimentPlayButtonEnabled = enabled;
	}

	// -------------------------------------------------------------------------
	// Event handling

	@Override
	public void labStatusChanged(final LabConnectorEvent evt) {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Lab status changed to " + evt.getStatusCode());
		}
		switch (evt.getStatusCode()) {
		case LabConnectorEvent.STATUS_CONNECTING:
			ReCApplication.log.fine("STATUS_CONNECTING");
			break;
		case LabConnectorEvent.STATUS_CONNECTED:
			ReCApplication.log.fine("STATUS_CONNECTED");
			setCurrentState(CONNECTED_TO_LAB);
			break;
		case LabConnectorEvent.STATUS_DISCONNECTING:
			ReCApplication.log.fine("STATUS_DISCONNECTING");
			break;
		case LabConnectorEvent.STATUS_DISCONNECTED:
			ReCApplication.log.fine("STATUS_DISCONNECTED");
			setCurrentState(DISCONNECTED_OFFLINE);
			break;
		case LabConnectorEvent.STATUS_UNREACHABLE:
			ReCApplication.log.fine("STATUS_UNREACHABLE");
			setCurrentState(DISCONNECTED_OFFLINE);
			break;
		case LabConnectorEvent.STATUS_NOT_AUTHORIZED:
			ReCApplication.log.fine("STATUS_NOT_AUTHORIZED");
			setCurrentState(DISCONNECTED_OFFLINE);
			break;
		case LabConnectorEvent.STATUS_NOT_REGISTERED:
			ReCApplication.log.fine("STATUS_NOT_REGISTERED");
			setCurrentState(DISCONNECTED_OFFLINE);
			break;
		default:
			ReCApplication.log.warning("Unknown lab status!");
		}

		// Forward event to the view
		fireLabStateChanged(evt);

	}

	@Override
	public void apparatusListChanged(final ApparatusListChangeEvent evt) {

		if (evt != null && evt.getApparatus() != null) {

			if (ReCApplication.log.isLoggable(Level.FINE)) {
				ReCApplication.log.fine("Apparatus list change event: " + Arrays.deepToString(evt.getApparatus()));
				ReCApplication.log.fine("Total available apparatus: " + evt.getApparatus().length);
			}

			// Enable apparatus combo box list
			final Set<String> activeApparatusUIDs = new HashSet<String>();
			for (final com.linkare.rec.impl.client.apparatus.Apparatus clientApparatus : evt.getApparatus()) {
				activeApparatusUIDs.add(clientApparatus.getHardwareInfo().getHardwareUniqueID());
			}
			for (final String ApparatusUID : apparatusComboBoxModel.getApparatusHardwareUniqueID()) {
				final Apparatus apparatus = apparatusComboBoxModel.getApparatus(ApparatusUID);
				if (apparatus != null) {
					apparatus.setEnabled(activeApparatusUIDs.contains(ApparatusUID));
				}
			}

			// Exit experiment if it was connected but tha driver went offline
			if (currentApparatus != null
					&& apparatusComboBoxModel.getApparatusHardwareUniqueID().contains(
							currentApparatus.getHardwareInfo().getHardwareUniqueID())
					&& !activeApparatusUIDs.contains(currentApparatus.getHardwareInfo().getHardwareUniqueID())) {
				toggleApparatusState();
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

	// Bruno ver se é necessário mais algum evento e tratar os eventos no
	// cliente adequadamente
	public enum VideoEvent {
		NOTCONNECTED, STOPPED, TIMECHANGED
	}

	// -------------------------------------------------------------------------
	// Apparatus events

	public enum ApparatusEvent {
		CONNECTING, CONNECTED, DISCONNECTING, DISCONNECTED, LOCKABLE, LOCKED, STATECONFIGURING, STATECONFIGURED, INCORRECTSTATE, MAXUSERS, NOTAUTHORIZED, NOTOWNER, NOTREGISTERED, STATECONFIGERROR, STATERESETING, STATERESETED, STATESTARTING, STATESTARTED, STATESTOPING, STATESTOPED, STATEUNKNOW, UNREACHABLE;
	}

	@Override
	public void apparatusConnecting(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(CONNECTING, evt);
	}

	@Override
	public void apparatusConnected(final ApparatusConnectorEvent evt) {
		// Load customizer
		currentCustomizer = CustomizerUIUtil.loadCustomizer(ReCResourceBundle.findString(currentApparatusConfig
				.getCustomizerClassLocationBundleKey()));

		// Set user info
		if (currentCustomizer instanceof ICustomizerSecurity) {
			((ICustomizerSecurity) currentCustomizer).setUserInfo(new com.linkare.rec.acquisition.UserInfo(
					getUsername()));
		}

		// Set current base hardware aquisition config
		currentHardwareAcquisitionConfig = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();

		// Init customizer
		currentCustomizer.setHardwareInfo(currentApparatus.getHardwareInfo());
		currentCustomizer.setHardwareAcquisitionConfig(currentHardwareAcquisitionConfig);

		// Listen to current customizer events (done/canceled)
		currentCustomizer.addICustomizerListener(this);

		setCurrentState(CONNECTED_TO_APPARATUS);

		// Forward event to the view
		fireApparatusStateChanged(CONNECTED, evt);
	}

	@Override
	public void apparatusDisconnecting(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(DISCONNECTING, evt);
	}

	@Override
	public void apparatusDisconnected(final ApparatusConnectorEvent evt) {
		if (isApparatusVideoEnabled()) {
			stopMedia();
		}

		// Disconnect from apparatus but remain connected to laboratory
		setCurrentState(CONNECTED_TO_LAB);

		// Forward event to the view
		fireApparatusStateChanged(DISCONNECTED, evt);
	}

	@Override
	public void apparatusLockable(final ApparatusConnectorEvent evt) {
		if (currentState.matches(APPARATUS_CONFIGURED) || currentState.matches(APPARATUS_STARTED)) {
			// Forward event to the view
			fireApparatusStateChanged(LOCKABLE, evt);

			if (experimentAutoplay) {
				play();
			}
		}
	}

	@Override
	public void apparatusLocked(final ApparatusConnectorEvent evt) {
		setCurrentState(APPARATUS_LOCKED);

		if (currentHardwareAcquisitionConfig == null) {
			currentHardwareAcquisitionConfig = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
		}
		apparatusClientBean.configure(currentHardwareAcquisitionConfig);

		// Forward event to the view
		fireApparatusStateChanged(LOCKED, evt);
	}

	@Override
	public void apparatusStateConfiguring(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATECONFIGURING, evt);
	}

	@Override
	public void apparatusStateConfigured(final ApparatusConnectorEvent evt) {
		if (currentState.matches(APPARATUS_LOCKED)) {
			apparatusClientBean.start();
		} else {
			// TODO Check if this is really needed.
			ReCApplication.log.severe("Apparatus SHOULD be locked?");
		}

		// Forward event to the view
		fireApparatusStateChanged(STATECONFIGURED, evt);
	}

	@Override
	public void apparatusIncorrectState(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(INCORRECTSTATE, evt);
	}

	@Override
	public void apparatusMaxUsers(final ApparatusConnectorEvent evt) {
		if (currentState.canGoTo(CONNECTED_TO_LAB)) {
			setCurrentState(CONNECTED_TO_LAB);
		}

		// Forward event to the view
		fireApparatusStateChanged(MAXUSERS, evt);
	}

	@Override
	public void apparatusNotAuthorized(final ApparatusConnectorEvent evt) {
		if (currentState.canGoTo(CONNECTED_TO_LAB)) {
			setCurrentState(CONNECTED_TO_LAB);
		}

		// Forward event to the view
		fireApparatusStateChanged(NOTAUTHORIZED, evt);
	}

	@Override
	public void apparatusNotOwner(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(NOTOWNER, evt);
	}

	@Override
	public void apparatusNotRegistered(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(NOTREGISTERED, evt);
	}

	@Override
	public void apparatusStateConfigError(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATECONFIGERROR, evt);
	}

	@Override
	public void apparatusStateReseting(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATERESETING, evt);
	}

	@Override
	public void apparatusStateReseted(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATERESETED, evt);
	}

	@Override
	public void apparatusStateStarting(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATESTARTING, evt);
	}

	@Override
	public void apparatusStateStarted(final ApparatusConnectorEvent evt) {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("ApparatusConnectorEvent " + evt.getMessage());
		}

		lastExperimentHistory = new ExperimentHistoryUINode(this, evt.getDataSource(),
				apparatusClientBean.getApparatus(), currentApparatusConfig);

		lastExperimentHistory.setLocallyOwned(currentState.matches(APPARATUS_LOCKED));
		lastExperimentHistory.setOwnerUserName(apparatusClientBean.getUserInfo().getUserName());

		// Add history entry on view
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.EXPERIMENT_HISTORY_ADDED));

		if (currentState.matches(APPARATUS_LOCKED)) {
			apparatusStateStartedEvent = evt;
			setCurrentState(APPARATUS_STARTED);
			startNewExperiment(lastExperimentHistory);
		}

	}

	public void startNewExperiment(final ExpHistory expHistory) {
		final ExperimentUIData experimentData = initExperiment(expHistory);
		// Forward event to the view
		apparatusStateStartedEvent.setValue("ExperimentUIData", experimentData);
		fireApparatusStateChanged(STATESTARTED, apparatusStateStartedEvent);
	}

	@Override
	public void startExperiment(final ExpHistory expHistory) {
		final ExperimentUIData experimentData = initExperiment(expHistory);
		// Forward event to the view
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_EXPERIMENT_HISTORY, experimentData));
	}

	private ExperimentUIData initExperiment(final ExpHistory expHistory) {
		final ExperimentHistoryUINode experimentHistory = (ExperimentHistoryUINode) expHistory;
		final ExperimentUIData experimentData = new ExperimentUIData();
		experimentData.setHistoryUINode(experimentHistory);
		DisplayFactory factory = null;

		// Was the user smart enough to make is own DisplayFactory?
		String factoryLocation = null;
		try {
			factoryLocation = ReCResourceBundle.findStringOrDefault(experimentHistory.getApparatusConfig()
					.getDisplayFactoryClassLocationBundleKey(), null);
		} catch (final Exception ignored) {
			// don't print the not found exception please...
		}
		// Of course not... maybe he didn't want to...
		if (factoryLocation == null) {
			// Load default
			factory = new DefaultDisplayFactory();

		} else { // Ok the user wants to load his own Factory
			try {
				final Object displayFactoryTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(),
						factoryLocation);
				if (java.beans.Beans.isInstanceOf(displayFactoryTemp, DisplayFactory.class)) {
					factory = (DisplayFactory) displayFactoryTemp;
				}
			} catch (final Exception e) {
				ReCApplication.log.log(Level.SEVERE, "Could not instantiate the display factory", e);
			}
		}

		if (factory != null) {
			// I will only give the selected displays :)
			final List<Display> selectedDisplays = new ArrayList<Display>();

			final List<Display> availableDisplays = experimentHistory.getApparatusConfig().getDisplay();

			for (final Display display : availableDisplays) {
				if (display.isSelected()) {
					selectedDisplays.add(display);
				}
			}
			factory.init(selectedDisplays);
			factory.setAcquisitionInfo(experimentHistory.getApparatus().getHardwareInfo());
			try {
				factory.setAcquisitionConfig(experimentHistory.getProducerWrapper().getAcquisitionHeader());
			} catch (final Exception e) {
				ReCApplication.log.log(Level.SEVERE, "Could not set aquisition config", e);
			}
			experimentData.setDataDisplays(factory.getDisplays());
		}

		// Couldn't read from xml or from user
		if (experimentData.getDataDisplays() == null) {
			try {
				final ArrayList<ExpDataDisplay> experimentDataDisplays = new ArrayList<ExpDataDisplay>();
				final Object dataDisplayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(),
						"com.linkare.rec.impl.ui.DefaultExperimentDataTable");
				if (java.beans.Beans.isInstanceOf(dataDisplayTemp, ExpDataDisplay.class)) {
					experimentDataDisplays.set(0, (ExpDataDisplay) dataDisplayTemp);
				}
				experimentData.setDataDisplays(experimentDataDisplays);
			} catch (final Exception e) {
				ReCApplication.log.log(Level.SEVERE, "Could not instantiate default datatable", e);
			}
		}

		// Did the user defined is own datamodel?
		String dataModelLocation = null;
		try {
			dataModelLocation = ReCResourceBundle.findStringOrDefault(experimentHistory.getApparatusConfig()
					.getDataModelClassLocationBundleKey(), null);
		} catch (final Exception ignored) {
			// don't print the not found exception please...
		}

		if (dataModelLocation != null) {
			try {
				final Object expDataModelTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(),
						dataModelLocation);
				if (java.beans.Beans.isInstanceOf(expDataModelTemp, ExpDataModel.class)) {
					ReCApplication.log.fine("Instatiating ExpDataModel from " + dataModelLocation);
					experimentData.setDataModel((ExpDataModel) expDataModelTemp);
				}
			} catch (final Exception e) {
				ReCApplication.log.log(Level.SEVERE, "Could not instantiate datamodel", e);
			}
		}
		DefaultExpDataModel experimentDataModel = null;
		// if the user didn't defined is data model, then use the default one
		if (experimentData.getDataModel() == null) {
			ReCApplication.log.fine("Setting default datamodel - DefaultExpDataModel.");
			experimentDataModel = new DefaultExpDataModel();
		}

		try {
			experimentDataModel.setDpwDataSource(experimentHistory.getProducerWrapper());
			experimentDataModel.initAcquisitionThread(); // iniciar a aquisicao
															// de dados do
															// multicast
			experimentData.setDataModel(experimentDataModel);
		} catch (final Exception e) {
			ReCApplication.log.log(Level.SEVERE, "Failed data output connection...", e);
			// TODO statusPanelApparatus Failed data output connection - forward
			// view event
		}

		return experimentData;
	}

	@Override
	public void showExperimentHeader(final ExpHistory history) {
		HardwareAcquisitionConfig config = null;
		try {
			config = history.getProducerWrapper().getAcquisitionHeader();
			// Show info on view
			if (config != null) {
				fireApplicationEvent(new ReCAppEvent(this, ReCCommand.SHOW_EXPERIMENT_HISTORY_HEADER_INFO, config));
			} else {
				ReCApplication.log.severe("Couldn't show Experiment Info... Aquisition Header is null.");
			}
		} catch (final Exception ignored) {
			ReCApplication.log.log(Level.SEVERE, "Couldn't show Experiment Info...", ignored);
		}
	}

	@Override
	public void apparatusStateStoping(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATESTOPING, evt);
	}

	@Override
	public void apparatusStateStoped(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATESTOPED, evt);
	}

	@Override
	public void apparatusStateUnknow(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(STATEUNKNOW, evt);
	}

	@Override
	public void apparatusUnreachable(final ApparatusConnectorEvent evt) {
		// Forward event to the view
		fireApparatusStateChanged(UNREACHABLE, evt);
	}

	// -------------------------------------------------------------------------
	// Customizer events

	@Override
	public void done() {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Customizer Done");
		}

		// Store user's last acquisition configuration
		userAcquisitionConfig = getCurrentCustomizer().getAcquisitionConfig();

		setCurrentState(APPARATUS_CONFIGURED);

		// Forward event to the view
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.CUSTOMIZER_DONE));
	}

	@Override
	public void canceled() {
		if (ReCApplication.log.isLoggable(Level.FINE)) {
			ReCApplication.log.fine("Customizer Canceled");
		}

		// Forward event to the view
		fireApplicationEvent(new ReCAppEvent(this, ReCCommand.CUSTOMIZER_CANCELED));
	}

	// -------------------------------------------------------------------------
	// Application Listeners

	public List<ReCApplicationListener> getAppListeners() {
		if (appListeners == null) {
			appListeners = new ArrayList<ReCApplicationListener>();
		}
		return appListeners;
	}

	public void addReCApplicationListener(final ReCApplicationListener listener) {
		getAppListeners().add(listener);
	}

	public void removeReCApplicationListener(final ReCApplicationListener listener) {
		getAppListeners().remove(listener);
	}

	public void fireLabStateChanged(final LabConnectorEvent evt) {
		for (final ReCApplicationListener listener : getAppListeners()) {
			listener.labStateChanged(evt);
		}
	}

	public void fireApparatusListChanged(final ApparatusListChangeEvent evt) {
		for (final ReCApplicationListener listener : getAppListeners()) {
			listener.apparatusListChanged(evt);
		}
	}

	public void fireApparatusStateChanged(final ApparatusEvent evtSelector, final ApparatusConnectorEvent evt) {
		for (final ReCApplicationListener listener : getAppListeners()) {
			listener.apparatusStateChanged(evtSelector, evt);
		}
	}

	public void fireApplicationEvent(final ReCAppEvent evt) {
		for (final ReCApplicationListener listener : getAppListeners()) {
			listener.applicationEvent(evt);
		}
	}

	// -------------------------------------------------------------------------
	// Static

	/**
	 * A convenient static getter for the application instance.
	 * 
	 * @return the instance of ReCApplication
	 */
	public static ReCApplication getApplication() {
		return Application.getInstance(ReCApplication.class);
	}

	/**
	 * Main method launching the application.
	 * @param args 
	 */
	public static void main(final String[] args) {
		Application.launch(ReCApplication.class, args);
	}

	public static class ExperimentUIData {

		private List<ExpDataDisplay> experimentDataDisplays;

		private ExpDataModel experimentDataModel;

		private ExperimentHistoryUINode experimentHistoryUINode;

		private ExperimentUIData() {
			super();
		}

		public List<ExpDataDisplay> getDataDisplays() {
			return experimentDataDisplays;
		}

		public ExpDataModel getDataModel() {
			return experimentDataModel;
		}

		public void setDataDisplays(final List<ExpDataDisplay> experimentDataDisplays) {
			this.experimentDataDisplays = experimentDataDisplays;
		}

		public void setDataModel(final ExpDataModel experimentDataModel) {
			this.experimentDataModel = experimentDataModel;
		}

		public ExperimentHistoryUINode getHistoryUINode() {
			return experimentHistoryUINode;
		}

		public void setHistoryUINode(final ExperimentHistoryUINode experimentHistoryUINode) {
			this.experimentHistoryUINode = experimentHistoryUINode;
		}

	}
        
        public void refreshView(String locale){
            fireApplicationEvent(new ReCAppEvent(this, ReCCommand.REFRESH_VIEW, locale));
        }

}
