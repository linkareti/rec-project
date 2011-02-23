/*
 * ReCView.java
 */

package com.linkare.rec.impl.newface;

import static com.linkare.rec.impl.newface.NavigationWorkflow.CONNECTED_TO_LAB;
import static com.linkare.rec.impl.newface.NavigationWorkflow.LAB_CONNECT_PERFORMED;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.GREEN;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.RED;
import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.YELLOW;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import com.linkare.rec.impl.newface.component.InfoPopup;
import com.linkare.rec.impl.newface.component.LabLoginBox;
import com.linkare.rec.impl.newface.component.LayoutContainerPane;
import com.linkare.rec.impl.newface.component.ResultsPane;
import com.linkare.rec.impl.newface.component.StatusActionBar;
import com.linkare.rec.impl.newface.component.UndecoratedDialog;
import com.linkare.rec.impl.newface.component.UnexpectedErrorPane;
import com.linkare.rec.impl.newface.component.VideoBox;
import com.linkare.rec.impl.newface.component.GlassLayer.CatchEvents;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.config.Apparatus;
import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.PreferencesUtils;
import com.linkare.rec.impl.newface.utils.TimeUtils;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;

/**
 * The application's main frame.
 */
public class ReCFrameView extends FrameView implements ReCApplicationListener, ItemListener {

	private static final Logger log = Logger.getLogger(ReCFrameView.class.getName());

	private static final int ONE_SECOND = 1000; // ms

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

	// For now, application model is unique. So there is no need for abstraction here.
	private final ReCApplication recApplication = ReCApplication.getApplication();

	private List<AbstractContentPane> interactiveBoxes;

	private boolean firstSelectedApparatusChange = true;

	private long apparatusLockInitialTimeMs;

	private long millisToLockSuccess;

	private InfoPopup infoPopup;

	private ResultsPane resultsPane;

	public ReCFrameView(SingleFrameApplication app) {
		super(app);

		ResourceMap resourceMap = getResourceMap();

		// Get status resources
		// Icon idleIcon, final Icon[] busyIcons, int busyAnimationRate
		idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
		for (int i = 0; i < busyIcons.length; i++) {
			busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
		}
		busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");

		// Initialize components
		initComponents();

		// Collect boxes that are enabled/disabled between lab connect/disconnect
		collectInterativeBoxes();

		// Set frame properties
		getFrame().setGlassPane(glassPane);
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrame().addWindowListener(new WindowListenerAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ApplicationActionMap actionMap = getActionMap();
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
		//lblTaskMessage.setVisible(false);
		//progressCicleTask.setVisible(false);

		// Init timers
		apparatusLockTimer = new Timer(ONE_SECOND, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				apparatusLockTimerTick();
			}
		});

		// Hide bottom status pane
		statusPanel.setVisible(false);

		// connecting action tasks to status bar via TaskMonitor
		TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				log.finer(propertyName + " = " + evt.getNewValue());
				if ("started".equals(propertyName)) {
					progressCicleTask.start();

				} else if ("done".equals(propertyName)) {
					progressCicleTask.stop();
					lblTaskMessage.setText("");

				} else if ("message".equals(propertyName)) {
					String taskMessage = (String) (evt.getNewValue());
					lblTaskMessage.setText(taskMessage);
				}
			}
		});

	}

	/**
	 * @return The <code>ReCFrameView</code> action map.
	 */
	private ApplicationActionMap getActionMap() {
		return org.jdesktop.application.Application.getInstance(ReCApplication.class).getContext().getActionMap(ReCFrameView.class,
				ReCFrameView.this);
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

	public void setInteractiveBoxesEnabled(boolean enabled) {
		for (AbstractContentPane box : interactiveBoxes) {
			log.fine("Setting box " + box.getName() + " enabled = " + enabled);
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
		ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
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
	 * @param cause
	 *            The unexpected error cause
	 * @return The <code>NewUnexpectedErrorPane</code>
	 */
	public static DefaultDialog<UnexpectedErrorPane> getUnexpectedErrorBox(Throwable cause) {
		if (unexpectedErrorBox == null) {
			unexpectedErrorBox = new DefaultDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(cause));
			unexpectedErrorBox.setLocationRelativeTo(null);
		}
		unexpectedErrorBox.getContent().setErrorCause(cause);
		unexpectedErrorBox.pack();
		return unexpectedErrorBox;
	}

	public DefaultDialog<ApparatusTabbedHistoryPane> getNewExperimentHistoryDialogBox(ExperimentUIData experimentUIData) {
		ApparatusTabbedHistoryPane apparatusTabbedPane = new ApparatusTabbedHistoryPane(experimentUIData);
		// Set description
		apparatusTabbedPane.getDescriptionPane().setApparatusConfig(experimentUIData.getHistoryUINode().getApparatusConfig());
		// Set results
		ResultsPane historyResultsPane = new ResultsPane();
		historyResultsPane.setExperimentResults(experimentUIData.getHistoryUINode(), experimentUIData.getDataModel(), experimentUIData
				.getDataDisplays());
		apparatusTabbedPane.getResultsHolderPane().add(historyResultsPane);
		// Select the results panel by default
		apparatusTabbedPane.setSelectedTabIndex(ApparatusTabbedHistoryPane.TAB_RESULTS);
		// Return dialog
		DefaultDialog<ApparatusTabbedHistoryPane> dialog = new DefaultDialog<ApparatusTabbedHistoryPane>(getFrame(), experimentUIData
				.getHistoryUINode().getApparatusName(), apparatusTabbedPane);
		dialog.pack();
		dialog.setLocationRelativeTo(getFrame());
		dialog.setModalityType(ModalityType.MODELESS);
		return dialog;
	}

	public UndecoratedDialog<LabLoginBox> getLoginBox() {
		if (loginBox == null) {
			LabLoginBox loginBoxPane = new LabLoginBox();
			loginBoxPane.setIdleIcon(idleIcon);
			loginBoxPane.setBusyIcons(busyIcons);
			loginBox = new UndecoratedDialog<LabLoginBox>(getFrame(), loginBoxPane);
		}
		loginBox.getContent().setLoginProgressVisible(false);
		loginBox.pack();
		loginBox.setLocationRelativeTo(getFrame());
		return loginBox;
	}

	public UndecoratedDialog<ExperimentHeaderInfoBox> getExperimentHeaderInfoBox(String info) {
		if (experimentInfoBox == null) {
			ExperimentHeaderInfoBox experimentInfoBoxPane = new ExperimentHeaderInfoBox();
			experimentInfoBox = new UndecoratedDialog<ExperimentHeaderInfoBox>(getFrame(), experimentInfoBoxPane);
			experimentInfoBox.getContent().addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
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
			JFrame mainFrame = recApplication.getMainFrame();
//			aboutBox = new ReCAboutBox(mainFrame);
//			aboutBox = new AboutDialog();
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

	public void setGlassPaneVisible(boolean visible) {
		getFrame().getGlassPane().setVisible(visible);
	}

	public void updateStatus(String msg) {
		lblTaskMessage.setText(msg);
	}

	public void setStatusMessageVisible(boolean visible) {
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
	}

	private javax.swing.Action toggleConnectionStateActionData(boolean connected) {
		javax.swing.Action toggleConnectionStateAction = getContext().getActionMap(ReCFrameView.class, this).get("toggleConnectionState");

		toggleConnectionStateAction.putValue(javax.swing.Action.NAME, getResourceMap().getString(
				"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.text"));
		toggleConnectionStateAction.putValue(javax.swing.Action.SHORT_DESCRIPTION, getResourceMap().getString(
				"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.shortDescription"));
		toggleConnectionStateAction.putValue(javax.swing.Action.SMALL_ICON, getResourceMap().getImageIcon(
				"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.smallIcon"));
		toggleConnectionStateAction.putValue(javax.swing.Action.LARGE_ICON_KEY, getResourceMap().getImageIcon(
				"toggleConnectionState" + (connected ? "Disconnect" : "") + ".Action.icon"));

		return toggleConnectionStateAction;
	}

	// Listen to user input
	@Override
	public void itemStateChanged(ItemEvent evt) {
		if (evt == null) {
			return;
		}
		// Listen to ApparatusCombo selection change
		if (getApparatusCombo() == evt.getSource()) {
			if (ItemEvent.SELECTED == evt.getStateChange()) {
				Apparatus apparatus = (Apparatus) evt.getItem();
				recApplication.setSelectedApparatusConfig(apparatus);
			}
		}
	}

	// -------------------------------------------------------------------------
	// Response to application model events

	@Override
	public void applicationEvent(ReCAppEvent evt) {

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
		case ASK_FOR_VLC:
			askForVLC();
			break;
		}
	}

	private void showExperimentHistory(ExperimentUIData experimentUIData) {
		getNewExperimentHistoryDialogBox(experimentUIData).setVisible(true);
	}

	private void showExperimentHistoryHeaderInfo(HardwareAcquisitionConfig config) {
		if (config != null) {
			getLoginBox().getContent().reset();
			setGlassPaneVisible(true);
			getExperimentHeaderInfoBox(config.toString()).setVisible(true);
		}
	}

	private void askForVLC() {

		final String LINE_SEPARATOR = System.getProperty("line.separator");
		String key = VideoViewerController.VLC_PATH_KEY;

		String vlcPath = PreferencesUtils.readUserPreference(key);
		if (vlcPath == null) {

			int result = JOptionPane.showConfirmDialog(this.getFrame(), "Não foi possível iniciar a reprodução de vídeo. " + LINE_SEPARATOR
					+ "No entanto, pode visualizar a experiência instalando " + LINE_SEPARATOR
					+ "o VLC. Deseja especificar a directoria de instalação do VLC?", "", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {

				File executable = getVLCExecutableFile();
				log.info("Selected file was: " + executable.getAbsolutePath());
				PreferencesUtils.writeUserPreference(key, executable.getAbsolutePath());
			}
		}
	}
	
	private File getVLCExecutableFile() {
		
		boolean proceed;
		File selected = null;
		do {
		
			proceed = true;
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(this.getFrame());
			selected = chooser.getSelectedFile();
			
			if (!isVLCExecutable(selected)) {
				if (JOptionPane.showConfirmDialog(this.getFrame(),
						"Não seleccionou um executável de VLC válido. Deseja prosseguir sem vídeo?", "", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					proceed = false;
				}
			}
		} while (!proceed);
		
		return selected;
	}

	/**
	 * Verifies if the specified file is in fact the vlc executable. Guarantees that no other executable file of other
	 * application is selected and launched by eLab.
	 * 
	 * @return true if the specified file is vlc executable. false otherwise.
	 */
	private boolean isVLCExecutable(File vlcExec) {

		if (vlcExec == null) {
			return false;
		}

		if (!vlcExec.getName().substring(0, 3).equalsIgnoreCase("vlc")) {
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
	public void labStateChanged(LabConnectorEvent evt) {

		//setStatusMessageVisible(true);

		switch (evt.getStatusCode()) {
		case LabConnectorEvent.STATUS_CONNECTING:
			// Nothing to do. The progress indicator is displayed
			// on LoginBox
			break;

		case LabConnectorEvent.STATUS_CONNECTED:
			connectToLaboratory();
			break;

		case LabConnectorEvent.STATUS_NOT_AUTHORIZED:
			updateStatus(getResourceMap().getString("lblTaskMessage.notAuthorized.text"));
			getLoginBox().setVisible(false);
			setGlassPaneVisible(false);
			break;

		case LabConnectorEvent.STATUS_UNREACHABLE:
			updateStatus(getResourceMap().getString("lblTaskMessage.unreachable.text"));
			getLoginBox().setVisible(false);
			setGlassPaneVisible(false);
			break;

		case LabConnectorEvent.STATUS_DISCONNECTING:
			progressCicleTask.start();
			updateStatus(getResourceMap().getString("lblTaskMessage.disconnecting.text"));
			break;

		case LabConnectorEvent.STATUS_DISCONNECTED:
			disconnectFromLaboratory();
			break;

		}
	}

	private void connectToLaboratory() {
		toolBtnConnect.setAction(toggleConnectionStateActionData(true));
		updateStatus(getResourceMap().getString("lblTaskMessage.connected.text", recApplication.getUsername(),
				recApplication.getCurrentLabName()));
		setInteractiveBoxesEnabled(true);
		getLoginBox().setVisible(false);
		setGlassPaneVisible(false);
		firstSelectedApparatusChange = true;
	}

	private void disconnectFromLaboratory() {
		toolBtnConnect.setAction(toggleConnectionStateActionData(false));
		updateStatus(getResourceMap().getString("lblTaskMessage.disconnected.text"));
		disconnectFromApparatus();
		getLayoutContainerPane().getApparatusDescriptionPane().setFieldsVisible(false);
		setInteractiveBoxesEnabled(false);
		progressCicleTask.stop();
	}

	@Override
	public void apparatusListChanged(ApparatusListChangeEvent evt) {
		if (recApplication.getCurrentState().matches(CONNECTED_TO_LAB)) {
			getApparatusCombo().setEnabled(true);
		}
	}

	@Override
	public void apparatusStateChanged(ApparatusEvent eventSelector, ApparatusConnectorEvent evt) {

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
			//clearLastExperimentResults();
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
		ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
		if (apparatusTabbedPane != null) {
			apparatusTabbedPane.setSelectedTabIndex(ApparatusTabbedPane.TAB_DESCRIPTION);
			apparatusTabbedPane.setTabIndexEnabled(ApparatusTabbedPane.TAB_RESULTS, false);
		}
	}

	/*
	 * Connection to Apparatus. Adds and shows Costumizer.
	 */
	private void connectToApparatus() {

		// Video
		if (recApplication.isApparatusVideoEnabled() && recApplication.getMediaController() != null) {
			log.info("Video is enable for the selected apparatus.");
			getVideoBox().initializeVideoOutput();
			recApplication.setMediaToPlay(recApplication.getCurrentApparatusVideoLocation());
			recApplication.setVideoOutput(getVideoBox().getVideoOutput());
			recApplication.playMedia();
		} else {
			log.info("Video isn't enable for the selected apparatus.");
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
		log.fine("Users List source took @ " + (System.currentTimeMillis() - timeStart) / 1000 + "s to start...");
		timeStart = System.currentTimeMillis();
		getApparatusUserListPane().getModel().setAutoRefresh(recApplication.getReCFaceConfig().getUsersListRefreshRateMs());

		log.fine("Auto Refresh set took @ " + (System.currentTimeMillis() - timeStart) / 1000 + "s to do!");
		//getApparatusUserListPane().getModel().chechRefresh();
	}

	private void disconnectFromApparatus() {
		if (recApplication.isApparatusVideoEnabled() && recApplication.getMediaController() != null) {
			getVideoBox().destroyVideoOutput();
		}

		// Toggle apparatus connected UI state 
		StatusActionBar experimentStatusActionBar = getExperimentStatusActionBar();
		if (experimentStatusActionBar != null) {
			experimentStatusActionBar.setActionStateLabelVisible(false);
		}
		getApparatusSelectBox().toggleApparatusStateActionData(true);
		getApparatusCombo().setEnabled(true);
		getLayoutContainerPane().disableApparatusTabbedPane();
		updateStatus(getResourceMap().getString("lblTaskMessage.connectedToLab.text", recApplication.getCurrentLabName()));
	}
	
	private void maxUsers() {
		String errorMessage = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.maxUsers",
				"Sorry, the lab is full. Please try again later...");
		errorConnectingToApparatus(errorMessage);
	}

	private void notAuthorized() {
		String errorMessage = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notAuthorized",
				"Not authorized, please confirm your login/password and try again!");
		errorConnectingToApparatus(errorMessage);
	}
	
	private void errorConnectingToApparatus(String errorMessage) {
		getApparatusSelectBox().getProgressCicle().stop();
		JOptionPane.showMessageDialog(null, errorMessage);
		disconnectFromApparatus();
	}

	/**
	 * @param evt
	 */
	private void lockableApparatus(ApparatusConnectorEvent evt) {
		
		// if it is in auto-play mode doesn't show the countdown because it will play now
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
				getStatusActionBarResourceMap().getString("lblActionState.apparatusConfiguring.text"),
				GREEN);
		lockApparatus();
		setExperimentAutoplay(false);
		setPlayButtonEnabled(false);
		setStopButtonEnabled(true);
	}

	private void configuredApparatus() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusConfigured.text"),
				GREEN);
	}

	private void startingExperiment() {
		getExperimentStatusActionBar().setActionStateText(
				getStatusActionBarResourceMap().getString("lblActionState.apparatusStarting.text"),
				YELLOW);
	}

	private ResourceMap getStatusActionBarResourceMap() {
		return recApplication.getContext().getResourceMap(StatusActionBar.class);
	}

	private void startedExperiment() {
		// Goto results tab
		getApparatusTabbedPane().setSelectedTabIndex(ApparatusTabbedPane.TAB_RESULTS);
		getExperimentStatusActionBar()
				.setActionStateText(
						getStatusActionBarResourceMap().getString("lblActionState.apparatusStarted.text"),
				YELLOW);
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

		// showing this message can be necessary because the status bar message is going to be hidden with stopped message
		String errorMessage = ReCResourceBundle.findStringOrDefault(
				"ReCBaseUI$rec.bui.status.apparatus.config.error.state",
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

		String errorMessage = ReCResourceBundle.findStringOrDefault(
				"ReCBaseUI$rec.bui.status.apparatus.incorrect.state",
				"The experiment is in an incorrect state! Please contact the administrator.");
		errorConnectingToApparatus(errorMessage);
	}
	
	private void setStopButtonEnabled(boolean enabled) {
		ApparatusTabbedPane apparatusTabbedPane = getApparatusTabbedPane();
		if (apparatusTabbedPane != null) {
			apparatusTabbedPane.setStopButtonEnabled(enabled);
			// the play button is enabled by the lock cycle
//			apparatusTabbedPane.getExperimentActionBar().setPlayStopButtonEnabled(!enabled);
		}
	}
	
	private void setExperimentAutoplay(boolean enabled) {
		getExperimentActionBar().unCheckExperimentAutoplay();
		recApplication.setExperimentAutoplay(false);
	}
	
	private void setPlayButtonEnabled(boolean enabled) {
		getExperimentActionBar().setPlayStopButtonEnabled(enabled);
	}

	private void clearLastExperimentResults() {
		getResultsPane().clearExperimentResults();
	}

	private void showExperimentResults(ExperimentUIData experimentUIData) {
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
		long lockCountDown = TimeUtils.msToSeconds(millisToLockSuccess - (TimeUtils.getSystemCurrentTimeMs() - apparatusLockInitialTimeMs));

		boolean lockCountDownGreaterThanZero = lockCountDown > 0;

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
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        toolBtnConnect = new javax.swing.JButton();
        toolBarCenterSpace = new com.linkare.rec.impl.newface.component.Spacer();
        lblTaskMessage = new javax.swing.JLabel();
        progressCicleTask = new com.linkare.rec.impl.newface.component.ProgressCicle(idleIcon, busyIcons, busyAnimationRate);
        layoutContainerPane = new com.linkare.rec.impl.newface.component.LayoutContainerPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu menuLab = new javax.swing.JMenu();
        menuItemConnect = new javax.swing.JMenuItem();
        sep1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem menuItemSair = new javax.swing.JMenuItem();
        menuLanguage = new javax.swing.JMenu();
        javax.swing.JMenu menuHelp = new javax.swing.JMenu();
        javax.swing.JMenuItem menuItemAbout = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();

        mainPanel.setAutoscrolls(true);
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                onResize(evt);
            }
        });
        mainPanel.setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setName("toolBar"); // NOI18N
        toolBar.setPreferredSize(new java.awt.Dimension(100, 31));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(ReCFrameView.class, this);
        toolBtnConnect.setAction(actionMap.get("toggleConnectionState")); // NOI18N
        toolBtnConnect.setBackground(null);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(ReCFrameView.class);
        toolBtnConnect.setText(resourceMap.getString("toolBtnConnect.text")); // NOI18N
        toolBtnConnect.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        toolBtnConnect.setBorderPainted(false);
        toolBtnConnect.setFocusPainted(false);
        toolBtnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBtnConnect.setName("toolBtnConnect"); // NOI18N
        toolBtnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBtnConnect.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                toolBtnConnectPropertyChange(evt);
            }
        });
        toolBar.add(toolBtnConnect);
        toolBtnConnect.getAccessibleContext().setAccessibleName(resourceMap.getString("toolBtnConnect.AccessibleContext.accessibleName")); // NOI18N

        toolBarCenterSpace.setName("toolBarCenterSpace"); // NOI18N

        javax.swing.GroupLayout toolBarCenterSpaceLayout = new javax.swing.GroupLayout(toolBarCenterSpace);
        toolBarCenterSpace.setLayout(toolBarCenterSpaceLayout);
        toolBarCenterSpaceLayout.setHorizontalGroup(
            toolBarCenterSpaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 794, Short.MAX_VALUE)
        );
        toolBarCenterSpaceLayout.setVerticalGroup(
            toolBarCenterSpaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

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

        menuLanguage.setText(resourceMap.getString("menuLanguage.text")); // NOI18N
        menuLanguage.setEnabled(false);
        menuLanguage.setName("menuLanguage"); // NOI18N
        menuBar.add(menuLanguage);

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

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

	private void toolBtnConnectPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_toolBtnConnectPropertyChange
		if ("text".equals(evt.getPropertyName())) {
			// Disable text display on toggle connection state button from toolbar
			((JButton) evt.getSource()).setText("");
		}
	}//GEN-LAST:event_toolBtnConnectPropertyChange

	private void onResize(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_onResize
		if (log.isLoggable(Level.FINER)) {
			log.finer("ReC Resize: Navigation=" + layoutContainerPane.getNavigationPane().getSize() + " Media="
					+ layoutContainerPane.getMediaPane().getSize() + " Center="
					+ layoutContainerPane.getApparatusDescriptionPane().getSize() + " LayoutContainer=" + layoutContainerPane.getSize()
					+ " Frame=" + getFrame().getSize());
		}
	}//GEN-LAST:event_onResize

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.newface.component.LayoutContainerPane layoutContainerPane;
    private javax.swing.JLabel lblTaskMessage;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemConnect;
    private javax.swing.JMenu menuLanguage;
    private com.linkare.rec.impl.newface.component.ProgressCicle progressCicleTask;
    private javax.swing.JSeparator sep1;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JToolBar toolBar;
    private com.linkare.rec.impl.newface.component.Spacer toolBarCenterSpace;
    private javax.swing.JButton toolBtnConnect;
    // End of variables declaration//GEN-END:variables

	//    private final Timer messageTimer;
	private final Timer apparatusLockTimer;
	public static Icon idleIcon;
	public static Icon[] busyIcons = new Icon[15];
	private final int busyAnimationRate;

	private JDialog aboutBox;
	private UndecoratedDialog<LabLoginBox> loginBox;
	private UndecoratedDialog<ExperimentHeaderInfoBox> experimentInfoBox;
	private static DefaultDialog<UnexpectedErrorPane> unexpectedErrorBox;
	private final GlassLayer glassPane = new GlassLayer(CatchEvents.NONE);

}
