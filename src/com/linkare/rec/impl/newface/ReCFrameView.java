/*
 * ReCView.java
 */

package com.linkare.rec.impl.newface;

import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.newface.component.GlassLayer;
import com.linkare.rec.impl.newface.component.GlassLayer.CatchEvents;
import com.linkare.rec.impl.newface.component.SimpleLoginBox;
import com.linkare.rec.impl.newface.component.UndecoratedDialog;
import com.linkare.rec.impl.newface.utils.LAFConnector;
import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;
import java.awt.Dimension;
import java.util.logging.Level;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class ReCFrameView extends FrameView implements ReCApplicationListener {

    private static final Logger log = Logger.getLogger(ReCFrameView.class.getName());

    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(848, 478);

    // For now, application model is unique. So there is no need for abstraction here.
    private ReCApplication recApplication = ReCApplication.getApplication();

    
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

        // Set frame properties
        getFrame().setPreferredSize(DEFAULT_FRAME_SIZE);
        getFrame().setResizable(false);
        getFrame().setGlassPane(glassPane);

        // Hide status indicators
        //lblTaskMessage.setVisible(false);
        //progressCicleTask.setVisible(false);
        
        // Hide the status pane
        statusPanel.setVisible(false);

        // status display initialization
//        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
//        messageTimer = new Timer(messageTimeout, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                statusMessageLabel.setText("");
//            }
//        });
//        messageTimer.setRepeats(false);

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
                    String taskMessage = (String)(evt.getNewValue());
                    lblTaskMessage.setText(taskMessage);

                } else if ("progress".equals(propertyName)) {

                }
            }
        });

    }

    public UndecoratedDialog<SimpleLoginBox> getLoginBox() {
        if (loginBox == null) {
            SimpleLoginBox simpleLoginBoxPane = new SimpleLoginBox();
            simpleLoginBoxPane.setIdleIcon(idleIcon);
            simpleLoginBoxPane.setBusyIcons(busyIcons);
            loginBox = new UndecoratedDialog<SimpleLoginBox>(simpleLoginBoxPane, getFrame());
        }
        loginBox.getContent().setLoginProgressVisible(false);
        loginBox.pack();
        return loginBox;
    }

    public JDialog getAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = recApplication.getMainFrame();
            aboutBox = new ReCAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        return aboutBox;
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
        if (!recApplication.isConnectedToLab()) {
            // When disconected...
            setGlassPaneVisible(true);

            // Get username
            getLoginBox().setVisible(true);

        } else {
            recApplication.disconnect();
        }
    }

    private javax.swing.Action toggleConnectionStateActionData(boolean connected) {
        javax.swing.Action toggleConnectionStateAction =
                getContext().getActionMap(ReCFrameView.class, this).get("toggleConnectionState");

        toggleConnectionStateAction.putValue(javax.swing.Action.NAME,
                getResourceMap().getString("toggleConnectionState"+ (connected ? "Disconnect" : "") +".Action.text"));
        toggleConnectionStateAction.putValue(javax.swing.Action.SHORT_DESCRIPTION,
                getResourceMap().getString("toggleConnectionState"+ (connected ? "Disconnect" : "") +".Action.shortDescription"));
        toggleConnectionStateAction.putValue(javax.swing.Action.SMALL_ICON,
                getResourceMap().getImageIcon("toggleConnectionState"+ (connected ? "Disconnect" : "") +".Action.smallIcon"));
        toggleConnectionStateAction.putValue(javax.swing.Action.LARGE_ICON_KEY,
                getResourceMap().getImageIcon("toggleConnectionState"+ (connected ? "Disconnect" : "") +".Action.icon"));

        return toggleConnectionStateAction;
    }

    // -------------------------------------------------------------------------
    // Response to application model events

    @Override
    public void labStatusChanged(LabConnectorEvent evt) {

        //setStatusMessageVisible(true);

        switch (evt.getStatusCode()) {
            case LabConnectorEvent.STATUS_CONNECTING:
                // Nothing to do. The progress indicator is displayed
                // on LoginBox
                break;

            case LabConnectorEvent.STATUS_CONNECTED:
                toolBtnConnect.setAction(toggleConnectionStateActionData(true));
                updateStatus(getResourceMap().getString("lblTaskMessage.connected.text"));
                getLoginBox().setVisible(false);
                setGlassPaneVisible(false);
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
                toolBtnConnect.setAction(toggleConnectionStateActionData(false));
                updateStatus(getResourceMap().getString("lblTaskMessage.disconnected.text"));
                progressCicleTask.stop();
                break;

        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        menuView = new javax.swing.JMenu();
        menuLanguage = new javax.swing.JMenu();
        javax.swing.JMenu menuHelp = new javax.swing.JMenu();
        javax.swing.JMenuItem menuItemAbout = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();

        mainPanel.setAutoscrolls(true);
        mainPanel.setName("mainPanel"); // NOI18N
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
            .addGap(0, 790, Short.MAX_VALUE)
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

        menuView.setText(resourceMap.getString("menuView.text")); // NOI18N
        menuView.setName("menuView"); // NOI18N
        menuBar.add(menuView);

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
            // Disable text display on toggle connection state button
            ((JButton)evt.getSource()).setText("");
        }
    }//GEN-LAST:event_toolBtnConnectPropertyChange




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.newface.component.LayoutContainerPane layoutContainerPane;
    private javax.swing.JLabel lblTaskMessage;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemConnect;
    private javax.swing.JMenu menuLanguage;
    private javax.swing.JMenu menuView;
    private com.linkare.rec.impl.newface.component.ProgressCicle progressCicleTask;
    private javax.swing.JSeparator sep1;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JToolBar toolBar;
    private com.linkare.rec.impl.newface.component.Spacer toolBarCenterSpace;
    private javax.swing.JButton toolBtnConnect;
    // End of variables declaration//GEN-END:variables

//    private final Timer messageTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private final int busyAnimationRate;

    private JDialog aboutBox;
    private UndecoratedDialog<SimpleLoginBox> loginBox;
    private GlassLayer glassPane = new GlassLayer(CatchEvents.NONE);

}
