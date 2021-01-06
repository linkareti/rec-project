/*
 * ReCFrameView.java
 */
package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.NavigationWorkflow.CONNECTED_TO_LAB;
import static com.linkare.rec.impl.newface.NavigationWorkflow.LAB_CONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.GREEN;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.RED;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.YELLOW;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent;
import com.linkare.rec.impl.newface.ReCApplication.ExperimentUIData;
import com.linkare.rec.impl.newface.component.AbstractContentPane;
import com.linkare.rec.impl.newface.component.ApparatusCombo;
import com.linkare.rec.impl.newface.component.ApparatusDescriptionPane;
import com.linkare.rec.impl.newface.component.ApparatusSelectBox;
import com.linkare.rec.impl.newface.component.ApparatusTabbedHistoryPane;
import com.linkare.rec.impl.newface.component.ApparatusTabbedPane;
import com.linkare.rec.impl.newface.component.ApparatusUserList;
import com.linkare.rec.impl.newface.component.ChatBox;
import com.linkare.rec.impl.newface.component.DefaultDialog;
import com.linkare.rec.impl.newface.component.ExperimentActionBar;
import com.linkare.rec.impl.newface.component.ExperimentHeaderInfoBox;
import com.linkare.rec.impl.newface.component.ExperimentHistoryBox;
import com.linkare.rec.impl.newface.component.FlatButton;
import com.linkare.rec.impl.newface.component.GlassLayer;
import com.linkare.rec.impl.newface.component.GlassLayer.CatchEvents;
import com.linkare.rec.impl.newface.component.InfoPopup;
import com.linkare.rec.impl.newface.component.LabLoginBox;
import com.linkare.rec.impl.newface.component.LayoutContainerPane;
import com.linkare.rec.impl.newface.component.ResultsPane;
import com.linkare.rec.impl.newface.component.StatusActionBar;
import com.linkare.rec.impl.newface.component.UndecoratedDialog;
import com.linkare.rec.impl.newface.component.UnexpectedErrorPane;
import com.linkare.rec.impl.newface.component.media.VideoBox;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;
import com.linkare.rec.impl.newface.utils.PreferencesUtils;
import com.linkare.rec.impl.newface.utils.TimeUtils;
import com.linkare.rec.web.config.Apparatus;

/**
 * The application's main frame.
 */
public class ReCFrameView extends FrameView implements ReCApplicationListener, ItemListener {

	private static final Logger LOGGER = Logger.getLogger(ReCFrameView.class.getName());
	private static final int ONE_SECOND = 1000; // ms

	// For now, application model is unique. So there is no need for abstraction
	// here.
	private final ReCApplication recApplication = ReCApplication.getApplication();
	private List<AbstractContentPane> interactiveBoxes;
	private boolean firstSelectedApparatusChange = true;
	private long apparatusLockInitialTimeMs;
	private long millisToLockSuccess;
	private InfoPopup infoPopup;
	private ResultsPane resultsPane;

	public ReCFrameView(final SingleFrameApplication app) {
		super(app);
		if (Preferences.userRoot().getBoolean("ElabPrivateComputer", true)) {
			Locale.setDefault(new Locale(Preferences.userRoot().get("ElabUserLocale", Locale.getDefault().toString())));
		}
		final ResourceMap resourceMap = getResourceMap();

		// Get status resources
		// Icon idleIcon, final Icon[] busyIcons, int busyAnimationRate
		ReCFrameView.idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
		for (int i = 0; i < ReCFrameView.busyIcons.length; i++) {
			ReCFrameView.busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
		}

		busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");

		// Initialize components
		initComponents();

		// Collect boxes that are enabled/disabled between lab
		// connect/disconnect
		collectInterativeBoxes();

		recApplication.setMediaPanel(getVideoBox().getMediaPanel());
		
		// Set frame properties
		getFrame().setGlassPane(glassPane);
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrame().addWindowListener(new WindowListenerAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				final ApplicationActionMap actionMap = getActionMap();
				actionMap.get("quit").actionPerformed(new ActionEvent(this, 0, "close"));
			}
		});

		// Add Apparatus Combo Item listener
		getApparatusCombo().addItemListener(this);

		// Hide apparatus description fields
		getApparatusDescriptionPane().setFieldsVisible(false);

		// Chat
		getChatBox().getChat().setChatServer(recApplication.getChatServer());
		getChatBox().getChat().setUserInfo(recApplication.getUserInfo());

		// Hide status indicators
		// lblTaskMessage.setVisible(false);
		// progressCicleTask.setVisible(false);

		// Init timers
		apparatusLockTimer = new Timer(ReCFrameView.ONE_SECOND, new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				apparatusLockTimerTick();
			}
		});

		// Hide bottom status pane
		statusPanel.setVisible(false);

		// connecting action tasks to status bar via TaskMonitor
		final TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

			public void propertyChange(final java.beans.PropertyChangeEvent evt) {
				final String propertyName = evt.getPropertyName();
				LOGGER.finer(propertyName + " = " + evt.getNewValue());
				if ("started".equals(propertyName)) {
					progressCicleTask.start();

				} else if ("done".equals(propertyName)) {
					progressCicleTask.stop();
					lblTaskMessage.setText("");

				} else if ("message".equals(propertyName)) {
					final String taskMessage = (String) (evt.getNewValue());
					lblTaskMessage.setText(taskMessage);
				}
			}
		});
	}

	/**
	 * @return The <code>ReCFrameView</code> action map.
	 */
	private ApplicationActionMap getActionMap() {
		return org.jdesktop.application.Application.getInstance(ReCApplication.class).getContext()
				.getActionMap(ReCFrameView.class, ReCFrameView.this);
	}

	/**
	 * Init boxes that are enabled/disabled between lab connect/disconnect
	 */
	private void collectInterativeBoxes() {
		interactiveBoxes = new ArrayList<AbstractContentPane>();
		interactiveBoxes.add(getApparatusSelectBox());
		interactiveBoxes.add(getExperimentHistoryBox().getHistoryListPane());
		interactiveBoxes.add(getApparatusDescriptionPane());
		interactiveBoxes.add(getVideoBox());
		interactiveBoxes.add(getChatBox());
	}

	public void setInteractiveBoxesEnabled(final boolean enabled) {
		for (final AbstractContentPane box : interactiveBoxes) {
			LOGGER.fine("Setting box " + box.getName() + " enabled = " + enabled);
			box.setEnabled(enabled);
			box.setChildComponentsEnabled(enabled);
		}
	}

	private LayoutContainerPane getLayoutContainerPane() {
		return layoutContainerPane;
	}

	private ApparatusUserList getApparatusUserListPane() {
		return getLayoutContainerPane().getApparatusUserList();
	}

	private ChatBox getChatBox() {
		return getLayoutContainerPane().getMediaPane().getChatBox();
	}

	private VideoBox getVideoBox() {
		return getLayoutContainerPane().getMediaPane().getVideoBox();
	}

	private ApparatusDescriptionPane getApparatusDescriptionPane() {
		return getLayoutContainerPane().getApparatusDescriptionPane();
	}

	private ApparatusTabbedPane getApparatusTabbedPane() {
		return getLayoutContainerPane().getApparatusTabbedPane();
	}

	private ExperimentActionBar getExperimentActionBar() {
		return getApparatusTabbedPane().getExperimentActionBar();
	}

	private StatusActionBar getExperimentStatusActionBar() {
		StatusActionBar result = null;
		final ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
		if (apparatusTabbedPane != null) {
			result = apparatusTabbedPane.getExperimentStatusActionBar();
		}
		return result;
	}

	private ExperimentHistoryBox getExperimentHistoryBox() {
		return getLayoutContainerPane().getNavigationPane().getExperimentHistoryBox();
	}

	private ApparatusSelectBox getApparatusSelectBox() {
		return getLayoutContainerPane().getNavigationPane().getApparatusSelectBox();
	}

	/**
	 * @param cause The unexpected error cause
	 * @return The <code>NewUnexpectedErrorPane</code>
	 */
	public static DefaultDialog<UnexpectedErrorPane> getUnexpectedErrorBox(final Throwable cause) {
		if (ReCFrameView.unexpectedErrorBox == null) {
			ReCFrameView.unexpectedErrorBox = new DefaultDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(cause));
			ReCFrameView.unexpectedErrorBox.setLocationRelativeTo(null);
		}
		ReCFrameView.unexpectedErrorBox.getContent().setErrorCause(cause);
		ReCFrameView.unexpectedErrorBox.pack();
		return ReCFrameView.unexpectedErrorBox;
	}

	public DefaultDialog<ApparatusTabbedHistoryPane> getNewExperimentHistoryDialogBox(
			final ExperimentUIData experimentUIData) {
		final ApparatusTabbedHistoryPane apparatusTabbedPane = new ApparatusTabbedHistoryPane(experimentUIData);
		// Set description
		apparatusTabbedPane.getDescriptionPane().setApparatusConfig(
				experimentUIData.getHistoryUINode().getApparatusConfig());
		// Set results
		final ResultsPane historyResultsPane = new ResultsPane();
		historyResultsPane.setExperimentResults(experimentUIData.getHistoryUINode(), experimentUIData.getDataModel(),
				experimentUIData.getDataDisplays());
		apparatusTabbedPane.getResultsHolderPane().add(historyResultsPane);
		// Select the results panel by default
		apparatusTabbedPane.setSelectedTabIndex(ApparatusTabbedHistoryPane.TAB_RESULTS);
		// Return dialog
		final DefaultDialog<ApparatusTabbedHistoryPane> dialog = new DefaultDialog<ApparatusTabbedHistoryPane>(
				getFrame(), experimentUIData.getHistoryUINode().getApparatusName(), apparatusTabbedPane);
		dialog.pack();
		dialog.setLocationRelativeTo(getFrame());
		dialog.setModalityType(ModalityType.MODELESS);
		return dialog;
	}

	public UndecoratedDialog<LabLoginBox> getLoginBox() {
		if (loginBox == null) {
			final LabLoginBox loginBoxPane = new LabLoginBox();
			loginBoxPane.setIdleIcon(ReCFrameView.idleIcon);
			loginBoxPane.setBusyIcons(ReCFrameView.busyIcons);
			loginBox = new UndecoratedDialog<LabLoginBox>(getFrame(), loginBoxPane);
		}
		loginBox.getContent().setLoginProgressVisible(false);
		loginBox.pack();
		loginBox.setLocationRelativeTo(getFrame());
		return loginBox;
	}

	public UndecoratedDialog<ExperimentHeaderInfoBox> getExperimentHeaderInfoBox(final String info) {
		if (experimentInfoBox == null) {
			final ExperimentHeaderInfoBox experimentInfoBoxPane = new ExperimentHeaderInfoBox();
			experimentInfoBox = new UndecoratedDialog<ExperimentHeaderInfoBox>(getFrame(), experimentInfoBoxPane);
			experimentInfoBox.getContent().addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(final PropertyChangeEvent evt) {
					if (evt != null) {
						if (ExperimentHeaderInfoBox.CLOSE_ME == evt.getPropertyName()) {
							setGlassPaneVisible(false);
							experimentInfoBox.setVisible(false);
						}
					}
				}
			});
		}
		experimentInfoBox.getContent().setText(info);
		experimentInfoBox.pack();
		experimentInfoBox.setLocationRelativeTo(getFrame());
		return experimentInfoBox;
	}

	public JDialog getAboutBox() {
		if (aboutBox == null) {
			final JFrame mainFrame = recApplication.getMainFrame();
			aboutBox = new AboutBoxDialog(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		return aboutBox;
	}

	public ApparatusCombo getApparatusCombo() {
		return getLayoutContainerPane().getNavigationPane().getApparatusSelectBox().getApparatusCombo();
	}

	public FlatButton getButtonToggleEnter() {
		return getLayoutContainerPane().getNavigationPane().getApparatusSelectBox().getButtonToggleEnter();
	}

	public void setGlassPaneVisible(final boolean visible) {
		getFrame().getGlassPane().setVisible(visible);
	}

	public void updateStatus(final String msg) {
		lblTaskMessage.setText(msg);
	}

	public void setStatusMessageVisible(final boolean visible) {
		lblTaskMessage.setVisible(visible);
	}

	@Action
	public void showAboutBox() {
		recApplication.show(getAboutBox());
	}

	@Action
	public void toggleConnectionState() {
		if (recApplication.getCurrentState().canGoTo(LAB_CONNECT_PERFORMED)) {
			// Perform connect
			showLoginBox();

		} else {
			// Perform disconnect
			recApplication.disconnect();
		}
	}

	private void showLoginBox() {
		getLoginBox().getContent().reset();
		setGlassPaneVisible(true);

		// Get username
		getLoginBox().getContent().setLoginProgressVisible(false);
		getLoginBox().setVisible(true);
		getLoginBox().getContent().updateStatus("");
	}

	private javax.swing.Action toggleConnectionStateActionData(final boolean connected) {
		final javax.swing.Action toggleConnectionStateAction = getContext().getActionMap(ReCFrameView.class, this).get(
				"toggleConnectionState");

		toggleConnectionStateAction.putValue(javax.swing.Action.NAME,
				getResourceMap().getString("toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.text"));
		toggleConnectionStateAction.putValue(
				javax.swing.Action.SHORT_DESCRIPTION,
				getResourceMap().getString(
						"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.shortDescription"));
		toggleConnectionStateAction.putValue(
				javax.swing.Action.SMALL_ICON,
				getResourceMap().getImageIcon(
						"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.smallIcon"));
		toggleConnectionStateAction.putValue(
				javax.swing.Action.LARGE_ICON_KEY,
				getResourceMap().getImageIcon(
						"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.icon"));

		return toggleConnectionStateAction;
	}

	// Listen to user input
	@Override
	public void itemStateChanged(final ItemEvent evt) {
		if (evt == null) {
			return;
		}
		// Listen to ApparatusCombo selection change
		if (getApparatusCombo() == evt.getSource()) {
			if (ItemEvent.SELECTED == evt.getStateChange()) {
				final Apparatus apparatus = (Apparatus) evt.getItem();
				recApplication.setSelectedApparatusConfig(apparatus);
			}
		}
	}

	// -------------------------------------------------------------------------
	// Response to application model events
	@Override
	public void applicationEvent(final ReCAppEvent evt) {

		switch (evt.getCommand()) {
		case SHOW_LOGIN:
			showLoginBox();
			break;
		case SELECTED_APPARATUS_CHANGE:
			selectedApparatusChange();
			break;
		case CUSTOMIZER_DONE:
			customizerDone();
			break;
		case CUSTOMIZER_CANCELED:
			// noop
			break;
		case EXPERIMENT_HISTORY_ADDED:
			addExperimentHistory();
			break;
		case SHOW_EXPERIMENT_HISTORY_HEADER_INFO:
			showExperimentHistoryHeaderInfo((HardwareAcquisitionConfig) evt.getValue());
			break;
		case SHOW_EXPERIMENT_HISTORY:
			showExperimentHistory((ExperimentUIData) evt.getValue());
			break;
		case CHOOSE_VLC:
			askForVLC((Boolean) evt.getValue());
			break;
		case REFRESH_VIEW:
			setUserLocale((String) evt.getValue());
			break;
		}
	}

	private void showExperimentHistory(final ExperimentUIData experimentUIData) {
		getNewExperimentHistoryDialogBox(experimentUIData).setVisible(true);
	}

	private void showExperimentHistoryHeaderInfo(final HardwareAcquisitionConfig config) {
		if (config != null) {
			getLoginBox().getContent().reset();
			setGlassPaneVisible(true);
			getExperimentHeaderInfoBox(HardwareAcquisitionConfig.translatePropertyBundles(config).toString())
					.setVisible(true);
		}
	}

	private void askForVLC(boolean ask) {

		final String LINE_SEPARATOR = ReCSystemProperty.LINE_SEPARATOR.getValue();
		final String key = VideoViewerController.VLC_PATH_KEY;

		final String vlcPath = PreferencesUtils.readUserPreference(key);
		if (ask) {
			if (vlcPath == null) {

				final int result = JOptionPane.showConfirmDialog(getFrame(),
						"Não foi possível iniciar a reprodução de vídeo. " + LINE_SEPARATOR
								+ "No entanto, pode visualizar a experiência instalando " + LINE_SEPARATOR
								+ "o VLC. Deseja especificar a directoria de instalação do VLC?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {
					defineVlcExecutable(key);
				}
			}
		} else {
			defineVlcExecutable(key);
		}
	}

	private void defineVlcExecutable(String key) {
		final File executable = getVLCExecutableFile();
		LOGGER.info("Selected file was: " + executable.getAbsolutePath());
		PreferencesUtils.writeUserPreference(key, executable.getAbsolutePath());
	}

	private File getVLCExecutableFile() {

		boolean proceed;
		File selected = null;
		do {

			proceed = true;
			final JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(getFrame());
			selected = chooser.getSelectedFile();

			if (!isVLCExecutable(selected)) {
				if (JOptionPane.showConfirmDialog(getFrame(), getResourceMap().getString("vlcexecutablefile.text"), "",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					proceed = false;
				}
			}
		} while (!proceed);

		return selected;
	}

	/**
	 * Verifies if the specified file is in fact the vlc executable. Guarantees
	 * that no other executable file of other application is selected and
	 * launched by eLab.
	 * 
	 * @return true if the specified file is vlc executable. false otherwise.
	 */
	private boolean isVLCExecutable(final File vlcExec) {

		if (vlcExec == null) {
			return false;
		}

		if (!vlcExec.getName().toLowerCase().startsWith("vlc")) {
			return false;
		}

		return true;
	}

	private void addExperimentHistory() {
		getExperimentHistoryBox().addExperimentHistory(recApplication.getLastExperimentHistory());
	}

	private void customizerDone() {
		getExperimentStatusActionBar().setActionStateLabelVisible(true);
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.customizerDone.text"), YELLOW);
	}

	/**
	 * Reached when the selected Apparatus changes.
	 */
	private void selectedApparatusChange() {
		if (firstSelectedApparatusChange) {
			getApparatusDescriptionPane().setFieldsVisible(true);
			firstSelectedApparatusChange = false;
		}
		// Update Apparatus Description Pane info
		getApparatusDescriptionPane().setApparatusConfig(recApplication.getSelectedApparatusConfig());
	}

	@Override
	public void labStateChanged(final LabConnectorEvent evt) {

		// setStatusMessageVisible(true);

		switch (evt.getStatusCode()) {
		case LabConnectorEvent.STATUS_CONNECTING:
			// Nothing to do. The progress indicator is displayed
			// on LoginBox
			getLoginBox().getContent().setLoginProgressVisible(true);
			break;

		case LabConnectorEvent.STATUS_CONNECTED:
			getLoginBox().getContent().updateStatus(getResourceMap().getString("lblTaskMessage.loadApparatus.text"));
			getLoginBox().getContent().setLoginProgressVisible(true);
			if (recApplication.isAutoConnectApparatus()) {
				// check if apparatus is available
				recApplication.getLabClientBean();
				boolean apparatusAvailable = checkAvailableApparatus();
				if (!apparatusAvailable) {
					getLoginBox().getContent().setLoginProgressVisible(false);
					getLoginBox().getContent().updateStatus(
							getResourceMap().getString("lblTaskMessage.apparatusNoAvailable.text"));
					recApplication.getLabClientBean().disconnect();
					recApplication.setCurrentState(NavigationWorkflow.DISCONNECTED_OFFLINE);
					break;
				}
			}
			connectedToLaboratory();
			break;

		case LabConnectorEvent.STATUS_NOT_AUTHORIZED:
			getLoginBox().getContent().updateStatus(getResourceMap().getString("lblTaskMessage.notAuthorized.text"));
			setGlassPaneVisible(true);
			break;

		case LabConnectorEvent.STATUS_UNREACHABLE:
			getLoginBox().getContent().updateStatus(getResourceMap().getString("lblTaskMessage.unreachable.text"));
			recApplication.disconnect();
			setGlassPaneVisible(true);
			break;

		case LabConnectorEvent.STATUS_DISCONNECTING:
			progressCicleTask.start();
			updateStatus(getResourceMap().getString("lblTaskMessage.disconnecting.text"));
			break;

		case LabConnectorEvent.STATUS_DISCONNECTED:
			disconnectedFromLaboratory();
			break;

		}
	}

	/**
	 * 
	 * @return If the apparatus selected is available
	 */
	private boolean checkAvailableApparatus() {
		boolean apparatusAvailable = false;
		long initialTimer = System.currentTimeMillis();
		while ((System.currentTimeMillis() - initialTimer) < 60000 && !apparatusAvailable) {
			apparatusAvailable = (recApplication.getLabClientBean().getApparatusByID(
					recApplication.getApparatusAutoConnectID()) == null) ? false : true;
			try {
				Thread.sleep(5000);
				recApplication.getLabClientBean().hardwareChange();
			} catch (InterruptedException ex) {
				Logger.getLogger(ReCFrameView.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return apparatusAvailable;
	}

	private void connectedToLaboratory() {
		toolBtnConnect.setAction(toggleConnectionStateActionData(true));
		updateStatus(getResourceMap().getString("lblTaskMessage.connected.text", recApplication.getUsername(),
				recApplication.getCurrentLabName()));
		setInteractiveBoxesEnabled(true);
		getLoginBox().setVisible(false);
		setGlassPaneVisible(false);
		firstSelectedApparatusChange = true;
		if (recApplication.isAutoConnectApparatus()) {
			recApplication.toggleApparatusState();
		}
	}

	private void disconnectedFromLaboratory() {
		toolBtnConnect.setAction(toggleConnectionStateActionData(false));
		if (!recApplication.isAutoConnectApparatus()) {
			updateStatus(getResourceMap().getString("lblTaskMessage.disconnected.text"));
		}
		disconnectFromApparatus();
		getLayoutContainerPane().getApparatusDescriptionPane().setFieldsVisible(false);
		setInteractiveBoxesEnabled(false);
		progressCicleTask.stop();
	}

	@Override
	public void apparatusListChanged(final ApparatusListChangeEvent evt) {
		if (recApplication.getCurrentState().matches(CONNECTED_TO_LAB)) {
			getApparatusCombo().setEnabled(true);
		}
	}

	@Override
	public void apparatusStateChanged(final ApparatusEvent eventSelector, final ApparatusConnectorEvent evt) {

		switch (eventSelector) {
		case CONNECTING:
			getApparatusSelectBox().getProgressCicle().start();
			connectingToApparatus();
			break;
		case CONNECTED:
			connectToApparatus();
			getApparatusSelectBox().getProgressCicle().stop();
			break;
		case DISCONNECTING:
			// TODO
			break;
		case DISCONNECTED:
			disconnectFromApparatus();
			break;
		case LOCKABLE:
			lockableApparatus(evt);
			break;
		case LOCKED:
			lockApparatus();
			break;
		case STATECONFIGURING:
			configuringApparatus();
			break;
		case STATECONFIGURED:
			configuredApparatus();
			break;
		case INCORRECTSTATE:
			incorrectStateExperiment();
			break;
		case MAXUSERS:
			maxUsers();
			break;
		case NOTAUTHORIZED:
			notAuthorized();
			break;
		case NOTOWNER:
			break;
		case NOTREGISTERED:
			break;
		case STATECONFIGERROR:
			configError();
			break;
		case STATESTARTING:
			progressCicleTask.start();
			startingExperiment();
			// clearLastExperimentResults();
			break;
		case STATESTARTED:
			startedExperiment();
			clearLastExperimentResults();
			showExperimentResults((ExperimentUIData) evt.getValue());
			break;
		case STATESTOPING:
			break;
		case STATERESETING:
			break;
		case STATERESETED:
			// do the same as stop
		case STATESTOPED:
			stopedExperiment();
			progressCicleTask.stop();
			break;
		case STATEUNKNOW:
			progressCicleTask.stop();
			break;
		case UNREACHABLE:
			progressCicleTask.stop();
			break;
		}
	}

	/*
	 * Select Description and disable Results
	 */
	private void connectingToApparatus() {
		final ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
		if (apparatusTabbedPane != null) {
			apparatusTabbedPane.setSelectedTabIndex(ApparatusTabbedPane.TAB_DESCRIPTION);
			apparatusTabbedPane.setTabIndexEnabled(ApparatusTabbedPane.TAB_RESULTS, false);
		}
	}

	private void setMediaToPlay(String mrl) {
		try {
			recApplication.setMediaToPlay(mrl);
		} catch (UnsatisfiedLinkError e) {
			askForVLC(true);
		}
	}

	/*
	 * Connection to Apparatus. Adds and shows Costumizer.
	 */
	private void connectToApparatus() {

		// Video
		if (recApplication.isApparatusVideoEnabled() && recApplication.getMediaController() != null) {
			LOGGER.info("Video is enable for the selected apparatus.");
			//getVideoBox().initializeVideoOutput();
			setMediaToPlay(recApplication.getCurrentApparatusVideoLocation());
			
			recApplication.playMedia();
		} else {
			LOGGER.info("Video isn't enable for the selected apparatus.");
		}

		// Toggle apparatus connected UI state
		getApparatusSelectBox().toggleApparatusStateActionData(false);
		getApparatusCombo().setEnabled(false);
		getLayoutContainerPane().enableApparatusTabbedPane();
		updateStatus(getResourceMap().getString("lblTaskMessage.connectedToApparatus.text",
				recApplication.getCurrentApparatusHardwareFamiliarName()));
		getApparatusDescriptionPane().setApparatusConfig(recApplication.getSelectedApparatusConfig());
		getApparatusTabbedPane().getExperimentActionBar().setPlayStopButtonEnabled(false);

		// Add customizer component
		getApparatusTabbedPane().addCustomizerComponent(recApplication.getCurrentCustomizer().getCustomizerComponent());

		// Show Info Message
		getInfoPopup().getMessagePane().setPreferredSize(getInfoPopupSizeFromApparatusTabbedPane());
		getInfoPopup().show(getApparatusTabbedPane(), 0, getInfoPopupYFromApparatusTabbedPane());

		// Goto customizer tab
		getApparatusTabbedPane().setSelectedTabIndex(ApparatusTabbedPane.TAB_CUSTOMIZER);
//		if (recApplication.getCurrentApparatusVideoLocation() != null) {
//			getLayoutContainerPane().getMediaPane().getClickHereLabel().setVisible(true);
//		}

		// Update UserList Pane
		updateUserListPane();
	}

	private int getInfoPopupYFromApparatusTabbedPane() {
		return getApparatusTabbedPane().getHeight() - getApparatusTabbedPane().getExperimentActionBar().getHeight()
				- getInfoPopup().getMessagePane().getPreferredSize().height;
	}

	private Dimension getInfoPopupSizeFromApparatusTabbedPane() {
		return new Dimension(getApparatusTabbedPane().getWidth() - 2, 60);
	}

	private InfoPopup getInfoPopup() {
		if (infoPopup == null) {
			infoPopup = new InfoPopup();
		}
		return infoPopup;
	}

	private void updateUserListPane() {
		long timeStart = System.currentTimeMillis();
		// CRITICAL João: Verificar tempo e carga de execução destas chamadas e
		// identificar se é uma situção com que temos mesmo de viver.
		// TODO Carregar em background
		getApparatusUserListPane().getModel().setExpUsersListSource(recApplication.getApparatusClientBean());
		LOGGER.fine("Users List source took @ " + (System.currentTimeMillis() - timeStart) / 1000 + "s to start...");
		timeStart = System.currentTimeMillis();
		getApparatusUserListPane().getModel().setAutoRefresh(
				recApplication.getReCFaceConfig().getUsersListRefreshRateMs());

		LOGGER.fine("Auto Refresh set took @ " + (System.currentTimeMillis() - timeStart) / 1000 + "s to do!");
		// getApparatusUserListPane().getModel().chechRefresh();
	}

	private void disconnectFromApparatus() {
		if (recApplication.isApparatusVideoEnabled() && recApplication.getMediaController() != null) {
			//getVideoBox().destroyVideoOutput();
		}

		// Reset apparatus actions
		apparatusLockTimer.stop();
		progressCicleTask.stop();
		setStopButtonEnabled(false);
		getApparatusTabbedPane().getExperimentActionBar().setPlayStopButtonEnabled(false);

		// Toggle apparatus connected UI state
		final StatusActionBar experimentStatusActionBar = getExperimentStatusActionBar();
		if (experimentStatusActionBar != null) {
			experimentStatusActionBar.setActionStateLabelVisible(false);
		}
		getApparatusSelectBox().toggleApparatusStateActionData(true);
		getApparatusCombo().setEnabled(true);
		getLayoutContainerPane().disableApparatusTabbedPane();
//		getLayoutContainerPane().getMediaPane().getClickHereLabel().setVisible(false);
		updateStatus(getResourceMap().getString("lblTaskMessage.connectedToLab.text",
				recApplication.getCurrentLabName()));
	}

	private void maxUsers() {
		final String errorMessage = ReCResourceBundle.findStringOrDefault("ReCUI$rec.ui.status.maxUsers",
				"Sorry, the lab is full. Please try again later...");
		errorConnectingToApparatus(errorMessage);
	}

	private void notAuthorized() {
		final String errorMessage = ReCResourceBundle.findStringOrDefault("ReCUI$rec.ui.status.notAuthorized",
				"Not authorized, please confirm your login/password and try again!");
		errorConnectingToApparatus(errorMessage);
	}

	private void errorConnectingToApparatus(final String errorMessage) {
		getApparatusSelectBox().getProgressCicle().stop();
		JOptionPane.showMessageDialog(null, errorMessage);
		disconnectFromApparatus();
	}

	/**
	 * @param evt
	 */
	private void lockableApparatus(final ApparatusConnectorEvent evt) {

		// if it is in auto-play mode doesn't show the countdown because it will
		// play now
		if (!recApplication.getExperimentAutoplay()) {

			apparatusLockInitialTimeMs = TimeUtils.getSystemCurrentTimeMs();
			millisToLockSuccess = evt.getMillisToLockSuccess();

			getExperimentStatusActionBar().setActionStateText(
					getStatusActionBarResourceMap().getString("lblActionState.apparatusLockable.text",
							TimeUtils.msToSeconds(millisToLockSuccess)), GREEN);
			getExperimentActionBar().setPlayStopButtonEnabled(true);

			getExperimentStatusActionBar().setActionStateLabelVisible(true);

			apparatusLockTimer.setInitialDelay(1); // ready, set
			apparatusLockTimer.start(); // go
		}
	}

	private void lockApparatus() {
		apparatusLockTimer.stop();
	}

	private void configuringApparatus() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusConfiguring.text"), GREEN);
		lockApparatus();
		setExperimentAutoplay(false);
		setPlayButtonEnabled(false);
		setStopButtonEnabled(true);
	}

	private void configuredApparatus() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusConfigured.text"), GREEN);
	}

	private void startingExperiment() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusStarting.text"), YELLOW);
	}

	private ResourceMap getStatusActionBarResourceMap() {
		return recApplication.getContext().getResourceMap(StatusActionBar.class);
	}

	private void startedExperiment() {
		// Goto results tab
		getApparatusTabbedPane().setSelectedTabIndex(ApparatusTabbedPane.TAB_RESULTS);
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusStarted.text"), YELLOW);
		setExperimentAutoplay(false);
		setPlayButtonEnabled(false);
		setStopButtonEnabled(true);
	}

	private void stopedExperiment() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusStoped.text"), RED);
		setStopButtonEnabled(false);
	}

	private void configError() {
		apparatusLockTimer.stop();
		progressCicleTask.stop();
		setStopButtonEnabled(false);
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusConfigErrorState.text"), RED);
		getApparatusTabbedPane().getExperimentActionBar().setPlayStopButtonEnabled(false);

		// showing this message can be necessary because the status bar message
		// is going to be hidden with stopped message
		final String errorMessage = ReCResourceBundle.findStringOrDefault(
				"ReCUI$rec.ui.status.apparatus.config.error.state",
				"The experiment has a wrong configuration! Please check the customizer.");
		JOptionPane.showMessageDialog(null, errorMessage);
	}

	private void incorrectStateExperiment() {
		apparatusLockTimer.stop();
		progressCicleTask.stop();
		setStopButtonEnabled(false);
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusIncorrectState.text"), RED);
		getApparatusTabbedPane().getExperimentActionBar().setPlayStopButtonEnabled(false);

		final String errorMessage = ReCResourceBundle.findStringOrDefault(
				"ReCUI$rec.ui.status.apparatus.incorrect.state",
				"The experiment is in an incorrect state! Please contact the administrator.");
		errorConnectingToApparatus(errorMessage);
	}

	private void setStopButtonEnabled(final boolean enabled) {
		final ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
		if (apparatusTabbedPane != null) {
			apparatusTabbedPane.setStopButtonEnabled(enabled);
			// the play button is enabled by the lock cycle
			// apparatusTabbedPane.getExperimentActionBar().setPlayStopButtonEnabled(!enabled);
		}
	}

	private void setExperimentAutoplay(final boolean enabled) {
		getExperimentActionBar().unCheckExperimentAutoplay();
		recApplication.setExperimentAutoplay(false);
	}

	private void setPlayButtonEnabled(final boolean enabled) {
		getExperimentActionBar().setPlayStopButtonEnabled(enabled);
	}

	private void clearLastExperimentResults() {
		getResultsPane().clearExperimentResults();
	}

	private void showExperimentResults(final ExperimentUIData experimentUIData) {
		getLayoutContainerPane().getApparatusTabbedPane().setDataDisplays(experimentUIData.getDataDisplays());

		getResultsPane().setExperimentResults(experimentUIData.getHistoryUINode(), experimentUIData.getDataModel(),
				experimentUIData.getDataDisplays());

		getApparatusTabbedPane().setTabIndexEnabled(ApparatusTabbedPane.TAB_RESULTS, true);
	}

	private ResultsPane getResultsPane() {
		if (resultsPane == null) {
			resultsPane = new ResultsPane();
			getApparatusTabbedPane().addResultsComponent(resultsPane);
		}
		return resultsPane;
	}

	private void apparatusLockTimerTick() {
		final long lockCountDown = TimeUtils.msToSeconds(millisToLockSuccess
				- (TimeUtils.getSystemCurrentTimeMs() - apparatusLockInitialTimeMs));

		final boolean lockCountDownGreaterThanZero = lockCountDown > 0;

		if (lockCountDownGreaterThanZero) {
			getExperimentStatusActionBar().setActionStateText(
					getStatusActionBarResourceMap().getString("lblActionState.apparatusLockable.text", lockCountDown),
					GREEN);
		}

		if (getExperimentActionBar().isPlayStopButtonEnabled() != lockCountDownGreaterThanZero) {
			getExperimentStatusActionBar().setActionStateText(
					getStatusActionBarResourceMap().getString("lblActionState.apparatusLockable.text", 0), GREEN);
			getExperimentActionBar().setPlayStopButtonEnabled(lockCountDownGreaterThanZero);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		mainPanel = new javax.swing.JPanel();
		toolBar = new javax.swing.JToolBar();
		toolBtnConnect = new javax.swing.JButton();
		toolBarCenterSpace = new com.linkare.rec.impl.newface.component.Spacer();
		lblTaskMessage = new javax.swing.JLabel();
		progressCicleTask = new com.linkare.rec.impl.newface.component.ProgressCicle(ReCFrameView.idleIcon,
				ReCFrameView.busyIcons, busyAnimationRate);
		layoutContainerPane = new com.linkare.rec.impl.newface.component.LayoutContainerPane();
		menuBar = new javax.swing.JMenuBar();
		final javax.swing.JMenu menuLab = new javax.swing.JMenu();
		menuItemConnect = new javax.swing.JMenuItem();
		sep1 = new javax.swing.JSeparator();
		final javax.swing.JMenuItem menuItemSair = new javax.swing.JMenuItem();
		final javax.swing.JMenu menuHelp = new javax.swing.JMenu();
		final javax.swing.JMenuItem menuItemAbout = new javax.swing.JMenuItem();
		statusPanel = new javax.swing.JPanel();
		final javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();

		mainPanel.setAutoscrolls(true);
		mainPanel.setName("mainPanel"); // NOI18N
		mainPanel.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
			public void ancestorMoved(final java.awt.event.HierarchyEvent evt) {
			}

			public void ancestorResized(final java.awt.event.HierarchyEvent evt) {
				onResize(evt);
			}
		});
		mainPanel.setLayout(new java.awt.BorderLayout());

		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setName("toolBar"); // NOI18N
		toolBar.setPreferredSize(new java.awt.Dimension(100, 31));

		final javax.swing.ActionMap actionMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getActionMap(ReCFrameView.class, this);
		toolBtnConnect.setAction(actionMap.get("toggleConnectionState")); // NOI18N
		toolBtnConnect.setBackground(null);
		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ReCFrameView.class);
		toolBtnConnect.setText(resourceMap.getString("toolBtnConnect.text")); // NOI18N
		toolBtnConnect.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		toolBtnConnect.setBorderPainted(false);
		toolBtnConnect.setFocusPainted(false);
		toolBtnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		toolBtnConnect.setName("toolBtnConnect"); // NOI18N
		toolBtnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		toolBtnConnect.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(final java.beans.PropertyChangeEvent evt) {
				toolBtnConnectPropertyChange(evt);
			}
		});
		toolBar.add(toolBtnConnect);
		toolBtnConnect.getAccessibleContext().setAccessibleName(
				resourceMap.getString("toolBtnConnect.AccessibleContext.accessibleName")); // NOI18N

		toolBarCenterSpace.setName("toolBarCenterSpace"); // NOI18N

		final javax.swing.GroupLayout toolBarCenterSpaceLayout = new javax.swing.GroupLayout(toolBarCenterSpace);
		toolBarCenterSpace.setLayout(toolBarCenterSpaceLayout);
		toolBarCenterSpaceLayout.setHorizontalGroup(toolBarCenterSpaceLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 794, Short.MAX_VALUE));
		toolBarCenterSpaceLayout.setVerticalGroup(toolBarCenterSpaceLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 27, Short.MAX_VALUE));

		toolBar.add(toolBarCenterSpace);

		lblTaskMessage.setForeground(LAFConnector.getColor(SpecialELabProperties.DEFAULT_WHITE));
		lblTaskMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTaskMessage.setText(resourceMap.getString("lblTaskMessage.text")); // NOI18N
		lblTaskMessage.setFocusable(false);
		lblTaskMessage.setName("lblTaskMessage"); // NOI18N
		toolBar.add(lblTaskMessage);

		progressCicleTask.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		progressCicleTask.setIcon(resourceMap.getIcon("progressCicleTask.icon")); // NOI18N
		progressCicleTask.setText(resourceMap.getString("progressCicleTask.text")); // NOI18N
		progressCicleTask.setFocusable(false);
		progressCicleTask.setName("progressCicleTask"); // NOI18N
		toolBar.add(progressCicleTask);

		mainPanel.add(toolBar, java.awt.BorderLayout.PAGE_START);

		layoutContainerPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
		layoutContainerPane.setName("layoutContainerPane"); // NOI18N
		mainPanel.add(layoutContainerPane, java.awt.BorderLayout.CENTER);

		menuBar.setName("menuBar"); // NOI18N
		menuBar.setPreferredSize(new java.awt.Dimension(291, 31));

		menuLab.setText(resourceMap.getString("menuLab.text")); // NOI18N
		menuLab.setName("menuLab"); // NOI18N

		menuItemConnect.setAction(actionMap.get("toggleConnectionState")); // NOI18N
		menuItemConnect.setName("menuItemConnect"); // NOI18N
		menuLab.add(menuItemConnect);

		sep1.setName("sep1"); // NOI18N
		menuLab.add(sep1);

		menuItemSair.setAction(actionMap.get("quit")); // NOI18N
		menuItemSair.setName("menuItemSair"); // NOI18N
		menuLab.add(menuItemSair);

		menuBar.add(menuLab);

		menuHelp.setText(resourceMap.getString("menuHelp.text")); // NOI18N
		menuHelp.setName("menuHelp"); // NOI18N

		menuItemAbout.setAction(actionMap.get("showAboutBox")); // NOI18N
		menuItemAbout.setName("menuItemAbout"); // NOI18N
		menuHelp.add(menuItemAbout);

		menuBar.add(menuHelp);

		statusPanel.setEnabled(false);
		statusPanel.setName("statusPanel"); // NOI18N
		statusPanel.setOpaque(false);

		statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

		final javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout.setHorizontalGroup(statusPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(statusPanelSeparator,
				javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE));
		statusPanelLayout.setVerticalGroup(statusPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				statusPanelLayout
						.createSequentialGroup()
						.addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2,
								javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(25, Short.MAX_VALUE)));

		setComponent(mainPanel);
		setMenuBar(menuBar);
		setStatusBar(statusPanel);
	}// </editor-fold>//GEN-END:initComponents

	private void toolBtnConnectPropertyChange(final java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_toolBtnConnectPropertyChange
		if ("text".equals(evt.getPropertyName())) {
			// Disable text display on toggle connection state button from
			// toolbar
			((JButton) evt.getSource()).setText("");
		}
	}// GEN-LAST:event_toolBtnConnectPropertyChange

	private void onResize(final java.awt.event.HierarchyEvent evt) {// GEN-FIRST:event_onResize
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.finest("ReC Resize: Navigation=" + layoutContainerPane.getNavigationPane().getSize() + " Media="
					+ layoutContainerPane.getMediaPane().getSize() + " Center="
					+ layoutContainerPane.getApparatusDescriptionPane().getSize() + " LayoutContainer="
					+ layoutContainerPane.getSize() + " Frame=" + getFrame().getSize());
		}
	}// GEN-LAST:event_onResize
		// Variables declaration - do not modify//GEN-BEGIN:variables

	private com.linkare.rec.impl.newface.component.LayoutContainerPane layoutContainerPane;
	private javax.swing.JLabel lblTaskMessage;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem menuItemConnect;
	private com.linkare.rec.impl.newface.component.ProgressCicle progressCicleTask;
	private javax.swing.JSeparator sep1;
	private javax.swing.JPanel statusPanel;
	private javax.swing.JToolBar toolBar;
	private com.linkare.rec.impl.newface.component.Spacer toolBarCenterSpace;
	private javax.swing.JButton toolBtnConnect;
	// End of variables declaration//GEN-END:variables
	// private final Timer messageTimer;
	private final Timer apparatusLockTimer;
	public static Icon idleIcon;
	public static Icon[] busyIcons = new Icon[15];
	private final int busyAnimationRate;
	private JDialog aboutBox;
	private UndecoratedDialog<LabLoginBox> loginBox;
	private UndecoratedDialog<ExperimentHeaderInfoBox> experimentInfoBox;
	private static DefaultDialog<UnexpectedErrorPane> unexpectedErrorBox;
	private final GlassLayer glassPane = new GlassLayer(CatchEvents.NONE);

	public void setUserLocale(String locale) {
		Locale.setDefault(new Locale(locale));
		//getVideoBox().refreshView();
		getChatBox().refreshView();
		getApparatusSelectBox().refreshView();
		getLoginBox().getContent().refreshView();
		aboutBox = null;
		refreshView();
		if (Preferences.userRoot().getBoolean("ElabPrivateComputer", true)) {
			Preferences.userRoot().put("ElabUserLocale", locale);
		}

	}

	public void refreshView() {
		toolBtnConnect.setText(getResourceMap().getString("toolBtnConnect.text"));
		toolBtnConnect.getAccessibleContext().setAccessibleName(
				getResourceMap().getString("toolBtnConnect.AccessibleContext.accessibleName"));
		progressCicleTask.setText(getResourceMap().getString("progressCicleTask.text"));
		updateMenu(menuBar);
	}

	public void updateMenu(JMenuBar menuBar) {
		List<javax.swing.JMenu> menuFather = new ArrayList<javax.swing.JMenu>();
		List<javax.swing.JMenuItem> menuChild = new ArrayList<javax.swing.JMenuItem>();
		javax.swing.MenuElement[] jMenuElement = menuBar.getSubElements();

		for (int i = 0; i < jMenuElement.length; i++) {
			menuFather.add((JMenu) jMenuElement[i]);
			getMenuChild((JMenu) jMenuElement[i], menuChild);
		}

		for (JMenu jMenu : menuFather) {
			jMenu.setText(ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.ReCFrameView",java.util.Locale.getDefault(),Thread.currentThread().getContextClassLoader()).getString(
					jMenu.getName() + ".text"));
		}

		for (JMenuItem jMenuItem : menuChild) {
			jMenuItem.setText(ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.ReCFrameView",java.util.Locale.getDefault(),Thread.currentThread().getContextClassLoader())
					.getString(jMenuItem.getName() + ".text"));
		}

	}

	public void getMenuChild(JMenu jMenu, List<JMenuItem> menuChild) {
		java.awt.Component[] componentsChild;
		componentsChild = jMenu.getMenuComponents();
		for (int i = 0; i < componentsChild.length; i++) {
			if (componentsChild[i] instanceof javax.swing.JMenuItem) {
				menuChild.add((JMenuItem) componentsChild[i]);
			}
		}
	}
}
