/*
 * ReCBaseUI.java
 *
 * Created on July 20, 2004, 12:18 PM
 */

package com.linkare.rec.impl.baseUI;
/**
 *
 * @author  andre
 */

import com.linkare.rec.impl.baseUI.utils.*;
import com.linkare.rec.impl.baseUI.mdi.*;
import com.linkare.rec.impl.baseUI.config.*;
import com.linkare.rec.impl.baseUI.labsTree.*;
import com.linkare.rec.impl.baseUI.control.*;
import com.linkare.rec.impl.baseUI.display.*;
import com.linkare.rec.impl.client.*;
import com.linkare.rec.impl.client.lab.*;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.impl.client.apparatus.*;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.impl.client.lab.*;
import com.linkare.rec.impl.client.apparatus.*;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.protocols.ReCProtocols;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Point;
import java.util.logging.*;
import java.util.prefs.*;
import com.linkare.rec.impl.logging.*;


public class ReCBaseUI extends javax.swing.JFrame implements ICustomizerListener, ExpHistoryDisplayFactory
{
    private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
        }
    }
    
    private ReCBaseUIConfig recBaseUI = null;
    
    /*Utility class to display tips*/
    private TipFactory tips = null;
    
    /*Have we already displayed the apparatus customize tip*/
    private boolean showedMessageConnectApparatus=false;
    
    private HardwareAcquisitionConfig config = null;
    
    private ICustomizer currentCustomizer = null;
    
    private com.linkare.rec.impl.client.apparatus.Apparatus currentApparatus = null;
    private com.linkare.rec.impl.baseUI.config.Apparatus currentApparatusConfig = null;
    
    private java.util.TimerTask lockerTask = null;
    
    private java.util.Timer timerLock = new java.util.Timer();
    
    //Stores the already added Offline Apparatus Displays
    private java.util.ArrayList<JInternalFrame> offlineApparatusDisplaysList = null;
    
    private Object synch = new Object();
    
    public static final String USER_NAME_PREF = "rec.user.name";
    public static final String USER_PASS_PREF = "rec.user.pass";
    
    private static final String LS = System.getProperty("line.separator");
    
    private Preferences preferences = null;
    
    /** Creates new form ReCBaseUI */
    public ReCBaseUI()
    {
        /*java.util.Locale loc = new java.util.Locale("pt","PT");
        java.util.Locale.setDefault(loc);*/
        
        recBaseUI = ReCBaseUIConfig.sharedInstance();
        
        initComponents();
        
        //Check the user preferences...
        preferences = Preferences.userRoot().userNodeForPackage(ReCBaseUI.class);
        
        String username = preferences.get(USER_NAME_PREF, System.getProperty("user.name"));
        String pass = preferences.get(USER_PASS_PREF, System.getProperty("user.name"));
        loginFrame.setUsername(username);
        loginFrame.setPassword(pass);
        //end users prefs
        
        
        tips = new TipFactory();
        
        if(recBaseUI.getDesktopLocationBundleKey() != null)
            mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(recBaseUI.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/about_box.png"))).getImage(), true);
        setIconImage(ReCResourceBundle.findImageIconOrDefault(recBaseUI.getIconLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/ReCIconHand16.gif"))).getImage());
        reCSplash.setVisible(true);
        reCSplash.requestFocus();
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.commPlat", "Initializing Communication Platform..."));
        
        ORBBean.getORBBean();
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.labWindow", "Initializing Main Laboratory Window..."));
        JDialog.setDefaultLookAndFeelDecorated(true);
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.readPref", "Reading preferences..."));
        
        
        setVideoFrameEnabled(recBaseUI.isEnableVideoFrame());
        setChatFrameEnabled(recBaseUI.isEnableChatFrame());
        setUsersListEnabled(recBaseUI.isEnableUsersList());
        setApparatusListEnabled(recBaseUI.isEnterApparatusChatRoom());
        
        setEnterApparatusRoom(recBaseUI.isEnterApparatusChatRoom());
        
        /*if(recBaseUI.getUsername()!=null)
            loginFrame.setUsername(recBaseUI.getUsername());
        if(recBaseUI.getPassword()!=null)
            loginFrame.setPassword(recBaseUI.getPassword());*/
        
        
        if(recBaseUI.getFrameTitle() != null)
            setTitle(recBaseUI.getFrameTitle());
        
        if(recBaseUI.getHelpPageURL()!=null)
            menuBar.setEnableContents(true);
        
        if(recBaseUI.isShowChatFrame())
        {
            setShowChatFrame(true);
        }
        if(recBaseUI.isShowUserList())
        {
            setShowUsersList(true);
            setShowHistory(true);
        }
        if(recBaseUI.isShowVideoFrame())
        {
            setShowVideoFrame(true);
        }
        setShowApparatusFrame(true);
        
        apparatusClientBean.setUsersListRefreshPeriod(recBaseUI.getUsersListRefreshRateMs());
        labClientBean.setUsersListRefreshPeriod(recBaseUI.getUsersListRefreshRateMs());
        
        usersListPanel.getModel().setAutoRefresh(recBaseUI.getUsersListRefreshRateMs());
        usersListPanel.getModel().setExpUsersListSource(apparatusClientBean);
        
        chatFrame.setChatServer(labClientBean);
        chatFrame.setUser(new com.linkare.rec.acquisition.UserInfo(loginFrame.getUsername()));
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.webresource", "Loading Web Resources..."));
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.ui", "Starting User Interface..."));
        
        reCSplash.setStatusMessage(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.init.done", "All Done!"));
        
        reCSplash.closeDialog(new java.awt.event.WindowEvent(this, 0));
        
        laboratoryTree.populateTree(recBaseUI);
        
        if(recBaseUI.isAutoConnectLab())
        {
            setAutoConnectLab(true);
            currentLab = recBaseUI.getLab()[0];
        }
    }
    
    
    
    
    
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        if(visible){
        if(!recBaseUI.isAutoConnectLab())
        {
            try
            {
                java.net.URL url = getClass().getResource(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.icon.url.connecthelp", "/com/linkare/rec/impl/baseUI/resources/earth16.gif"));
                Point locationConnect = toolBarPanel.getLocationOnScreen();
                locationConnect.y = locationConnect.y + toolBarPanel.getHeight()/2;
                tips.showTipWithTimeout(toolBarPanel, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.helpConnectBefore", "<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>Click the Connect button<br><img src='") + url.toExternalForm() + ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.helpConnectAfter", "'><br>double click the apparatus<br>you wish to connect to!</font></center></body></html>"), 5000, locationConnect);
            }
            catch(Exception e2)
            {
                LoggerUtil.logThrowable(e2.getMessage(), e2, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        else
        {
            loginFrame.setVisible(true, recBaseUI.isEnableLoginPassword());
        }
	}
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        labClientBean = new com.linkare.rec.impl.client.LabClientBean();
        apparatusClientBean = new com.linkare.rec.impl.client.ApparatusClientBean();
        reCSplash = new ReCSplash(this, false);
        loginFrame = new com.linkare.rec.impl.baseUI.LoginFrame();
        aboutDialog = new com.linkare.rec.impl.baseUI.AboutDialog();
        toolBarPanel = new com.linkare.rec.impl.baseUI.control.ToolBarPanel();
        jDockPanel1 = new com.linkare.rec.impl.baseUI.JDockPanel();
        jPanel4 = new javax.swing.JPanel();
        mDIDesktopPane = new com.linkare.rec.impl.baseUI.mdi.MDIDesktopPane();
        jInternalFrameLabTree = new javax.swing.JInternalFrame();
        laboratoryTree = new com.linkare.rec.impl.baseUI.labsTree.LaboratoryTree();
        jDockPanel2 = new com.linkare.rec.impl.baseUI.JDockPanel();
        jInternalFrameTabs = new javax.swing.JInternalFrame();
        jTabbedPaneInfoUsers = new javax.swing.JTabbedPane();
        expHistoryPanelNew = new com.linkare.rec.impl.baseUI.ExpHistoryPanelNew();
        usersListPanel = new com.linkare.rec.impl.baseUI.UsersListPanel();
        chatFrame = new com.linkare.rec.impl.baseUI.ChatFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        statusPanelApparatus = new com.linkare.rec.impl.baseUI.StatusPanel();
        controllerPanel = new com.linkare.rec.impl.baseUI.control.ControllerPanel();
        countDownProgressPanel = new com.linkare.rec.impl.baseUI.control.CountDownProgressPanel();
        jPanel3 = new javax.swing.JPanel();
        statusPanelLab = new com.linkare.rec.impl.baseUI.StatusPanel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new com.linkare.rec.impl.baseUI.control.MenuBar();

        labClientBean.addApparatusListSourceListener(new com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener()
        {
            public void apparatusListChanged(com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent evt)
            {
                labClientBeanApparatusListChanged(evt);
            }
        });
        labClientBean.addLabConnectorListener(new com.linkare.rec.impl.client.lab.LabConnectorListener()
        {
            public void labStatusChanged(com.linkare.rec.impl.client.lab.LabConnectorEvent evt)
            {
                labClientBeanLabStatusChanged(evt);
            }
        });

        apparatusClientBean.addApparatusConnectorListener(new com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener()
        {
            public void apparatusConnected(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusConnected(evt);
            }
            public void apparatusConnecting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusConnecting(evt);
            }
            public void apparatusDisconnected(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusDisconnected(evt);
            }
            public void apparatusDisconnecting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusDisconnecting(evt);
            }
            public void apparatusIncorrectState(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusIncorrectState(evt);
            }
            public void apparatusLockable(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusLockable(evt);
            }
            public void apparatusLocked(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusLocked(evt);
            }
            public void apparatusMaxUsers(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusMaxUsers(evt);
            }
            public void apparatusNotAuthorized(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusNotAuthorized(evt);
            }
            public void apparatusNotOwner(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusNotOwner(evt);
            }
            public void apparatusNotRegistered(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusNotRegistered(evt);
            }
            public void apparatusStateConfigError(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateConfigError(evt);
            }
            public void apparatusStateConfigured(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateConfigured(evt);
            }
            public void apparatusStateConfiguring(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateConfiguring(evt);
            }
            public void apparatusStateReseted(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateReseted(evt);
            }
            public void apparatusStateReseting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateReseting(evt);
            }
            public void apparatusStateStarted(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateStarted(evt);
            }
            public void apparatusStateStarting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateStarting(evt);
            }
            public void apparatusStateStoped(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateStoped(evt);
            }
            public void apparatusStateStoping(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateStoping(evt);
            }
            public void apparatusStateUnknow(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusStateUnknow(evt);
            }
            public void apparatusUnreachable(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)
            {
                apparatusClientBeanApparatusUnreachable(evt);
            }
        });

        loginFrame.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                ReCBaseUI.this.propertyChange(evt);
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });

        toolBarPanel.setEnableVideo(false);
        toolBarPanel.setSelectApparatusTree(true);
        toolBarPanel.setSelectChat(true);
        toolBarPanel.setSelectHistory(true);
        toolBarPanel.setSelectUsersList(true);
        toolBarPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                ReCBaseUI.this.propertyChange(evt);
            }
        });

        getContentPane().add(toolBarPanel, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(500, 500));
        mDIDesktopPane.setPreferredSize(new java.awt.Dimension(320, 240));
        jPanel4.add(mDIDesktopPane, java.awt.BorderLayout.CENTER);

        jDockPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jInternalFrameLabTree.setBorder(null);
        jInternalFrameLabTree.setClosable(true);
        jInternalFrameLabTree.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameLabTree.setTitle(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.apparatuslist", "Apparatus List"));
        jInternalFrameLabTree.setFrameIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.tree", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree16.gif"))));
        jInternalFrameLabTree.setPreferredSize(new java.awt.Dimension(210, 35));
        jInternalFrameLabTree.setVisible(true);
        jInternalFrameLabTree.addInternalFrameListener(new javax.swing.event.InternalFrameListener()
        {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt)
            {
                jInternalFrameLabTreeInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt)
            {
            }
        });

        laboratoryTree.addTreeSelectionChangeListener(new com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener()
        {
            public void defaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent evt)
            {
                laboratoryTreeDefaultConfigSelectionChange(evt);
            }
            public void displaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent evt)
            {
                laboratoryTreeDisplaySelectionChange(evt);
            }
            public void webResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent evt)
            {
                laboratoryTreeWebResourceSelectionChange(evt);
            }
            public void apparatusSelectionChange(com.linkare.rec.impl.baseUI.labsTree.ApparatusSelectionEvent evt)
            {
                laboratoryTreeApparatusSelectionChange(evt);
            }
            public void labSelectionChange(com.linkare.rec.impl.baseUI.labsTree.LabSelectionEvent evt)
            {
                laboratoryTreeLabSelectionChange(evt);
            }
        });

        jInternalFrameLabTree.getContentPane().add(laboratoryTree, java.awt.BorderLayout.CENTER);

        jDockPanel1.add(jInternalFrameLabTree, java.awt.BorderLayout.WEST);

        jInternalFrameTabs.setBorder(null);
        jInternalFrameTabs.setClosable(true);
        jInternalFrameTabs.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameTabs.setTitle(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.historyAndUser", "History & Users List"));
        jInternalFrameTabs.setMinimumSize(new java.awt.Dimension(10, 10));
        jInternalFrameTabs.setPreferredSize(new java.awt.Dimension(250, 250));
        jInternalFrameTabs.setVisible(true);
        jInternalFrameTabs.addInternalFrameListener(new javax.swing.event.InternalFrameListener()
        {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt)
            {
                jInternalFrameTabsInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt)
            {
            }
        });

        jTabbedPaneInfoUsers.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneInfoUsers.setPreferredSize(new java.awt.Dimension(250, 250));
        expHistoryPanelNew.setPreferredSize(new java.awt.Dimension(150, 200));
        jTabbedPaneInfoUsers.addTab(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.expHistory", "Experiment History"), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.experiment", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Experiment16.gif"))), expHistoryPanelNew, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.expHistory", "Experiment History gives you access to all the experiments that have been made since you were connected to the system"));

        usersListPanel.setPreferredSize(new java.awt.Dimension(150, 200));
        jTabbedPaneInfoUsers.addTab(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.apparatusUsersList", "Apparatus Users List"), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.users", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif"))), usersListPanel, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.users", "Shows you the current users of the apparatus and an estimate of the control timings"));

        jInternalFrameTabs.getContentPane().add(jTabbedPaneInfoUsers, java.awt.BorderLayout.CENTER);

        jDockPanel2.add(jInternalFrameTabs, java.awt.BorderLayout.CENTER);

        chatFrame.setBorder(null);
        chatFrame.setPreferredSize(new java.awt.Dimension(250, 150));
        chatFrame.setVisible(true);
        chatFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener()
        {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt)
            {
                chatFrameInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt)
            {
            }
        });

        jDockPanel2.add(chatFrame, java.awt.BorderLayout.SOUTH);

        jDockPanel1.add(jDockPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jDockPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setLayout(new java.awt.GridBagLayout());

        statusPanelApparatus.setLblStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.apparatus", "Apparatus Status:"));
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.unknown", "unknown"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 8.0;
        jPanel2.add(statusPanelApparatus, gridBagConstraints);

        controllerPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                ReCBaseUI.this.propertyChange(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(controllerPanel, gridBagConstraints);

        countDownProgressPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                ReCBaseUI.this.propertyChange(evt);
            }
        });

        jPanel2.add(countDownProgressPanel, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        statusPanelLab.setLblStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.lab", "Laboratory Status:"));
        statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnected", "disconnected"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel3.add(statusPanelLab, gridBagConstraints);

        jLabel1.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.sponsor", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/RecLogo2Gray.png"))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel1, gridBagConstraints);

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        menuBar.setEnableVideo(false);
        menuBar.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                ReCBaseUI.this.propertyChange(evt);
            }
        });
        menuBar.addTreeSelectionChangeListener(new com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener()
        {
            public void defaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent evt)
            {
                menuBarDefaultConfigSelectionChange(evt);
            }
            public void displaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent evt)
            {
                menuBarDisplaySelectionChange(evt);
            }
            public void webResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent evt)
            {
                menuBarWebResourceSelectionChange(evt);
            }
            public void apparatusSelectionChange(com.linkare.rec.impl.baseUI.labsTree.ApparatusSelectionEvent evt)
            {
            }
            public void labSelectionChange(com.linkare.rec.impl.baseUI.labsTree.LabSelectionEvent evt)
            {
            }
        });

        setJMenuBar(menuBar);

        pack();
    }//GEN-END:initComponents
    
    private void menuBarWebResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent evt)//GEN-FIRST:event_menuBarWebResourceSelectionChange
    {//GEN-HEADEREND:event_menuBarWebResourceSelectionChange
        laboratoryTreeWebResourceSelectionChange(evt);
    }//GEN-LAST:event_menuBarWebResourceSelectionChange
    
    private void menuBarDisplaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent evt)//GEN-FIRST:event_menuBarDisplaySelectionChange
    {//GEN-HEADEREND:event_menuBarDisplaySelectionChange
        laboratoryTreeDisplaySelectionChange(evt);
    }//GEN-LAST:event_menuBarDisplaySelectionChange
    
    private void menuBarDefaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent evt)//GEN-FIRST:event_menuBarDefaultConfigSelectionChange
    {//GEN-HEADEREND:event_menuBarDefaultConfigSelectionChange
        laboratoryTreeDefaultConfigSelectionChange(evt);
    }//GEN-LAST:event_menuBarDefaultConfigSelectionChange
    
    private void propertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_propertyChange
    {//GEN-HEADEREND:event_propertyChange
        
        if(evt.getPropertyName().equals("users"))
        {
            setShowUsersList(!isShowUsersList());
        }
        else if(evt.getPropertyName().equals("history"))
        {
            setShowHistory(!isShowHistory());
        }
        else if(evt.getPropertyName().equals("chat"))
        {
            setShowChatFrame(!chatFrame.isVisible());
        }
        else if(evt.getPropertyName().equals("tree"))
        {
            setShowApparatusFrame(!jInternalFrameLabTree.isVisible());
        }
        else if(evt.getPropertyName().equals("connect"))
        {
            if(toolBarPanel.isSelectConnect())
            {
                setConnectLab(false);
                return;
            }
            DisplayNode node = laboratoryTree.getCurrentSelectedNode();
            if(node == null || !(node instanceof Lab))
            {
                String errorMessage = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.error.lab", "Please select a laboratory first!");
                String title = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.info", "Info");
                /*JOptionPane.showMessageDialog(this, errorMessage, title, JOptionPane.ERROR_MESSAGE);
                toolBarPanel.setSelectConnect(false);*/
                Lab[] availableLabs = recBaseUI.getLab();
                String[] options = new String[availableLabs.length];
                for(int i = 0 ; i<availableLabs.length; i++)
                {
                    options[i] = new String(availableLabs[i].getText());
                }
                Object answer = JOptionPane.showInputDialog(this, errorMessage, title, JOptionPane.INFORMATION_MESSAGE, availableLabs[0].getIcon(), options, options[0]);
                if(answer != null)
                {
                    for(int i = 0 ; i<availableLabs.length; i++)
                    {
                        if(availableLabs[i].getText().equals(answer.toString()))
                            currentLab = availableLabs[i];
                    }
                }
                else
                {
                    toolBarPanel.setSelectConnect(false);
                    return;
                }
            }
            else
            {
                currentLab = (Lab)node;
            }
            
            
            loginFrame.setVisible(true, recBaseUI.isEnableLoginPassword());
        }
        else if(evt.getPropertyName().equals("autoplay"))
        {
            setAutoPlay(!autoPlay);
        }
        else if(evt.getPropertyName().equals("contents"))
        {
            openURL(recBaseUI.getHelpPageURL(), false);
        }
        else if(evt.getPropertyName().equals("about"))
        {
            aboutDialog.setVisible(true);
            aboutDialog.requestFocus();
        }
        else if(evt.getPropertyName().equals("customize"))
        {
            if(currentCustomizer != null)
            {
                if(config == null)
                    config = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
                
                CustomizerUIUtil.InitCustomizer(currentCustomizer, currentApparatus.getHardwareInfo(), config, currentCustomizer).addICustomizerListener(this);
            }
        }
        else if(evt.getPropertyName().equals("progressEnd"))
        {
            if(started)
                controllerPanel.setEnableStop(false);
            else if(customizeDoneOnce)
                controllerPanel.setEnablePlay(false);
            
            if(lockerTask!=null)
            {
                lockerTask.cancel();
                lockerTask=null;
            }
        }
        else if(evt.getPropertyName().equals("play"))
        {
            if(currentApparatus!=null)
            {
                apparatusClientBean.lock();
                controllerPanel.setEnablePlay(false);
                locktried=true;
            }
        }
        else if(evt.getPropertyName().equals("stop"))
        {
            if(locked)
            {
                controllerPanel.setEnableStop(false);
                apparatusClientBean.stop();
            }
        }
        else if(evt.getPropertyName().equals("login"))
        {
            if(evt.getNewValue().toString().equals("true"))
            {
                setConnectLab(!connectLab);
                chatFrame.setUser(new com.linkare.rec.acquisition.UserInfo(loginFrame.getUsername()));
                
                //save the new preferences...
                preferences.put(USER_NAME_PREF, loginFrame.getUsername());
                preferences.put(USER_PASS_PREF, loginFrame.getPassword());
                
                try
                {
                    preferences.flush();
                }
                catch(BackingStoreException bse)
                {
                    bse.printStackTrace();
                }
            }
            else
            {
                toolBarPanel.setSelectConnect(false);
            }
        }
        else if(evt.getPropertyName().equals("ToolbarLab"))
        {
            toolBarPanel.setShowLabToolBar(!toolBarPanel.isShowLabToolBar());
        }
        else if(evt.getPropertyName().equals("ToolbarView"))
        {
            toolBarPanel.setShowViewToolBar(!toolBarPanel.isShowViewToolBar());
        }
        else if(evt.getPropertyName().equals("cascade"))
        {
            mDIDesktopPane.cascadeFrames();
        }
        else if(evt.getPropertyName().equals("tile"))
        {
            mDIDesktopPane.tileFrames();
        }
    }//GEN-LAST:event_propertyChange
    
    private void labClientBeanLabStatusChanged(com.linkare.rec.impl.client.lab.LabConnectorEvent evt)//GEN-FIRST:event_labClientBeanLabStatusChanged
    {//GEN-HEADEREND:event_labClientBeanLabStatusChanged
        if(evt.getStatusCode() == evt.STATUS_UNREACHABLE)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.unreachable", "Unreachable"));
            setConnectLab(false);
        }
        else if(evt.getStatusCode() == evt.STATUS_CONNECTED)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.connected", "Connected"));
            if(currentLab != null)
                currentLab.setEnabled(true);
            if(waitDialog != null)
                waitDialog.setVisible(false);
            if(currentLab.getDesktopLocationBundleKey() != null)
                mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(currentLab.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), false);
        }
        else if(evt.getStatusCode() == evt.STATUS_CONNECTING)
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.connecting", "Connecting"));
        else if(evt.getStatusCode() == evt.STATUS_DISCONNECTED)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnected", "Disconnected"));
            setConnectLab(false);
        }
        else if(evt.getStatusCode() == evt.STATUS_DISCONNECTING)
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnecting", "Disconnecting"));
        else if(evt.getStatusCode() == evt.STATUS_MAX_USERS)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.maxUsers", "Sorry, the lab is full. Please try again later..."));
            setConnectLab(false);
        }
        else if(evt.getStatusCode() == evt.STATUS_NOT_AUTHORIZED)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notAuthorized", "Not authorized, please confirm your login/password and try again!"));
            setConnectLab(false);
        }
        else if(evt.getStatusCode() == evt.STATUS_NOT_REGISTERED)
        {
            statusPanelLab.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notRegistered", "Not registered..."));
            setConnectLab(false);
        }
    }//GEN-LAST:event_labClientBeanLabStatusChanged
    
    private void jInternalFrameTabsInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_jInternalFrameTabsInternalFrameClosing
    {//GEN-HEADEREND:event_jInternalFrameTabsInternalFrameClosing
        setShowHistory(false);
        setShowUsersList(false);
    }//GEN-LAST:event_jInternalFrameTabsInternalFrameClosing
    
    private void labClientBeanApparatusListChanged(com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent evt)//GEN-FIRST:event_labClientBeanApparatusListChanged
    {//GEN-HEADEREND:event_labClientBeanApparatusListChanged
        if(evt!=null)
        {
            if(evt.getApparatus()!=null)
            {
                if(currentLab != null)
                    DisplayTreeNodeUtils.disableAllApparatus(currentLab);
                
                if(getApparatusAutoConnectID()!=null )
                {
                    for(int i=0;i<evt.getApparatus().length;i++)
                    {
                        if(evt.getApparatus()[i].getHardwareInfo().getHardwareUniqueID().equals(getApparatusAutoConnectID()))
                        {
                            com.linkare.rec.impl.baseUI.config.Apparatus app = laboratoryTree.getApparatus(getApparatusAutoConnectID());
                            app.setEnabled(true);
                            laboratoryTreeApparatusSelectionChange(new ApparatusSelectionEvent(this, app));
                        }
                    }
                }
                else
                {
                    for(int i=0;i<evt.getApparatus().length;i++)
                    {
                        com.linkare.rec.impl.baseUI.config.Apparatus app = laboratoryTree.getApparatus(evt.getApparatus()[i].getHardwareInfo().getHardwareUniqueID());
                        if(app == null)
                            continue;
                        
                        if(!app.isEnabled())
                            app.setEnabled(true);
                    }
                    
                    if(!showedMessageConnectApparatus)
                    {
                        try
                        {
                            java.net.URL url = getClass().getResource(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.icon.url.labhelp", "/com/linkare/rec/impl/baseUI/resources/tree_experiment.gif"));
                            Point locationTreeApparatus = laboratoryTree.getLocationOnScreen();
                            locationTreeApparatus.x = locationTreeApparatus.x + laboratoryTree.getWidth()-10;
                            locationTreeApparatus.y = locationTreeApparatus.y + laboratoryTree.getHeight()/2;
                            tips.showTipWithTimeout(laboratoryTree, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.helpSelectApparatusBefore", "<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>In the Apparatus List <img src='") + url.toExternalForm() + ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.helpSelectApparatusAfter", "'><br>double click the apparatus<br>you wish to connect to!</font></center></body></html>"), 5000, locationTreeApparatus);
                            showedMessageConnectApparatus=true;
                        }
                        catch(Exception e2)
                        {
                            LoggerUtil.logThrowable(e2.getMessage(), e2, Logger.getLogger(UI_CLIENT_LOGGER));
                        }
                    }
                }
                
                if(currentApparatusConfig != null && apparatusClientBean.isConnected())
                    DisplayTreeNodeUtils.enableAllApparatusContents(currentApparatusConfig);
                else if(currentApparatusConfig != null)
                    menuBar.removeApparatus();
                
            }
        }
    }//GEN-LAST:event_labClientBeanApparatusListChanged
    
    private void apparatusClientBeanApparatusUnreachable(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusUnreachable
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusUnreachable
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.unreachable", "Unreachable..."));
        if(waitDialog != null)
            waitDialog.setVisible(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusUnreachable
    
    private void apparatusClientBeanApparatusStateUnknow(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateUnknow
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateUnknow
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.unknown", "Unknown..."));
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        if(waitDialog != null)
            waitDialog.setVisible(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusStateUnknow
    
    private void apparatusClientBeanApparatusStateStoping(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStoping
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStoping
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.stoping", "Stopping..."));
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusStateStoping
    
    private void apparatusClientBeanApparatusStateStoped(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStoped
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStoped
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.stoped", "Stopped..."));
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        locked=false;
        started=false;
    }//GEN-LAST:event_apparatusClientBeanApparatusStateStoped
    
    private void apparatusClientBeanApparatusStateStarting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStarting
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStarting
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.starting", "Starting..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusStateStarting
    
    private void apparatusClientBeanApparatusStateStarted(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStarted
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStarted
        started=true;
        
        ExpHistory expHistory = new ExpHistory(this, evt.getDataSource(), apparatusClientBean.getApparatus(), currentApparatusConfig);
        expHistory.setLocallyOwned(locked);
        
        expHistoryPanelNew.addExpHistory(expHistory);
        
        if(locked)
        {
            startExperiment(expHistory);
            controllerPanel.setEnablePlay(false);
            controllerPanel.setEnableStop(true);
        }
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.started", "Started..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusStateStarted
    
    private void apparatusClientBeanApparatusStateReseting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateReseting
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateReseting
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.reseting", "Reseting..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusStateReseting
    
    private void apparatusClientBeanApparatusStateReseted(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateReseted
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateReseted
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.reseted", "Reseted..."));
        locked = false;
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        started = false;
    }//GEN-LAST:event_apparatusClientBeanApparatusStateReseted
    
    private void apparatusClientBeanApparatusStateConfiguring(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfiguring
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfiguring
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.configuring", "Configuring..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusStateConfiguring
    
    private void apparatusClientBeanApparatusStateConfigured(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfigured
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfigured
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.configured", "Configured..."));
        if(locked)
        {
            controllerPanel.setEnablePlay(false);
            apparatusClientBean.start();
        }
    }//GEN-LAST:event_apparatusClientBeanApparatusStateConfigured
    
    private void apparatusClientBeanApparatusStateConfigError(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfigError
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfigError
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.config.error", "Configuration error..."));
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusStateConfigError
    
    private void apparatusClientBeanApparatusNotRegistered(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotRegistered
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusNotRegistered
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notRegistered", "Not registered..."));
        controllerPanel.setEnableCustomize(false);
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        if(waitDialog != null)
            waitDialog.setVisible(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusNotRegistered
    
    private void apparatusClientBeanApparatusNotOwner(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotOwner
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusNotOwner
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notOwner", "You're not the current owner..."));
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusNotOwner
    
    private void apparatusClientBeanApparatusNotAuthorized(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotAuthorized
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusNotAuthorized
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.notAuthorized", "Not authorized, please confirm your login/password and try again..."));
        controllerPanel.setEnableCustomize(false);
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        loginFrame.setVisible(true, recBaseUI.isEnableLoginPassword());
    }//GEN-LAST:event_apparatusClientBeanApparatusNotAuthorized
    
    private void apparatusClientBeanApparatusMaxUsers(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusMaxUsers
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusMaxUsers
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.roomFull", "The room is full, please try again later..."));
        controllerPanel.setEnableCustomize(false);
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        if(waitDialog != null)
            waitDialog.setVisible(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusMaxUsers
    
    private void apparatusClientBeanApparatusLocked(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusLocked
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusLocked
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.locked", "Locked..."));
        locked=true;
        if(config == null)
            config = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
        
        apparatusClientBean.configure(config);
    }//GEN-LAST:event_apparatusClientBeanApparatusLocked
    
    private void apparatusClientBeanApparatusLockable(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusLockable
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusLockable
        
        if(!countDownProgressPanel.isStop())
        {
            countDownProgressPanel.setStop(true);
        }
        
        countDownProgressPanel.setCountDownFrom((int)(evt.getMillisToLockSuccess()/1000));
        countDownProgressPanel.setCountDownTo(0);
        countDownProgressPanel.setIndeterminate(false);
        
        if(customizeDoneOnce)
        {
            countDownProgressPanel.startCounting();
            controllerPanel.setEnablePlay(true);
        }
        
        
        locktried = false;
        lockable = true;
        
        if(lockerTask != null)
        {
            lockerTask.cancel();
            lockerTask = null;
            countDownProgressPanel.setStop(true);
        }
        
        timerLock.scheduleAtFixedRate(
        (lockerTask=new java.util.TimerTask()
        {
            public void run()
            {
                if(locktried)
                {
                    cancel();
                    
                    if(customizeDoneOnce)
                        countDownProgressPanel.setStop(true);
                    
                    lockerTask=null;
                    
                    lockable=false;
                }
                else if(customizeDoneOnce)
                {
                    if(!controllerPanel.isEnablePlay())
                        controllerPanel.setEnablePlay(true);
                }
            }
        })
        ,0, countDownProgressPanel.getUpdateRate() * 1000);
        
        if(isAutoPlay() && customizeDoneOnce)
        {
            setAutoPlay(false);
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    propertyChange(new java.beans.PropertyChangeEvent(this, "play", Boolean.FALSE, Boolean.TRUE));
                }
            });
        }
    }//GEN-LAST:event_apparatusClientBeanApparatusLockable
    
    private void apparatusClientBeanApparatusIncorrectState(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusIncorrectState
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusIncorrectState
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.incorrectState", "Incorrect state for the operation..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusIncorrectState
    
    private void apparatusClientBeanApparatusDisconnecting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusDisconnecting
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusDisconnecting
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnecting", "Disconnecting..."));
        //disableApparatus(currentApparatusConfig);
    }//GEN-LAST:event_apparatusClientBeanApparatusDisconnecting
    
    private void apparatusClientBeanApparatusDisconnected(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusDisconnected
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusDisconnected
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnected", "Disconnected..."));
        controllerPanel.setEnableCustomize(false);
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
    }//GEN-LAST:event_apparatusClientBeanApparatusDisconnected
    
    private void apparatusClientBeanApparatusConnecting(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusConnecting
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusConnecting
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.connecting", "Connecting..."));
    }//GEN-LAST:event_apparatusClientBeanApparatusConnecting
    
    private void apparatusClientBeanApparatusConnected(com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent evt)//GEN-FIRST:event_apparatusClientBeanApparatusConnected
    {//GEN-HEADEREND:event_apparatusClientBeanApparatusConnected
        statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.connected", "Connected..."));
        try
        {
            if(config==null)
                config = currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
            
            com.linkare.rec.impl.baseUI.config.Apparatus app = laboratoryTree.getApparatus(currentApparatus.getHardwareInfo().getHardwareUniqueID());
            if(app == null)
                return;
            
            DisplayTreeNodeUtils.enableAllApparatusContents(app);
            
            currentCustomizer = CustomizerUIUtil.loadCustomizer(ReCResourceBundle.findString(app.getCustomizerClassLocationBundleKey()));
            
            if(currentCustomizer instanceof ICustomizerSecurity)
                ((ICustomizerSecurity)currentCustomizer).setUserInfo(new com.linkare.rec.acquisition.UserInfo(loginFrame.getUsername()));
            
            if(currentCustomizer!=null)
            {
                if(waitDialog != null)
                    waitDialog.setVisible(false);
                controllerPanel.setEnableCustomize(true);
                if(!showedMessageConfig)
                {
                    java.net.URL url = getClass().getResource(ReCResourceBundle.findStringOrDefault("reb.bui.tip.icon.url.apparatushelp", "/com/linkare/rec/impl/baseUI/resources/Preferences16.gif"));
                    String message = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.helpSelectConfigBefore", "<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>Click the configure button <img src='") + url.toExternalForm() + ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.helpSelectConfigAfter", "'><br>to access the experiment <br>configuration utility!</font></center></body></html>");
                    controllerPanel.showCustomizerTip(tips, message, 5000);
                    showedMessageConfig=true;
                }
                
                menuBar.addApparatusMenu(currentLab, currentApparatusConfig);
                
                if(currentApparatusConfig.getDesktopLocationBundleKey() != null)
                    mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(currentApparatusConfig.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), false);
                else if(currentLab.getDesktopLocationBundleKey() != null)
                    mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(currentLab.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), false);
                else if(recBaseUI.getDesktopLocationBundleKey() != null)
                    mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(recBaseUI.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), false);
                else
                    mDIDesktopPane.setBackgroundImage(null, false);
                currentCustomizer.addICustomizerListener(this);
            }
        }
        catch(Exception e)
        {
            LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
        }
        
        //apparatusClientBean.startAutoRefresh(recBaseUI.getUsersListRefreshRateMs());
    }//GEN-LAST:event_apparatusClientBeanApparatusConnected
    
    private void laboratoryTreeWebResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent evt)//GEN-FIRST:event_laboratoryTreeWebResourceSelectionChange
    {//GEN-HEADEREND:event_laboratoryTreeWebResourceSelectionChange
        try
        {
            openURL(evt.getWebResource().getURL(), evt.getWebResource().isInternalBrowser());
        }
        catch(Exception e2)
        {
            LoggerUtil.logThrowable(e2.getMessage(), e2, Logger.getLogger(UI_CLIENT_LOGGER));
        }
    }//GEN-LAST:event_laboratoryTreeWebResourceSelectionChange
    
    private void laboratoryTreeDisplaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent evt)//GEN-FIRST:event_laboratoryTreeDisplaySelectionChange
    {//GEN-HEADEREND:event_laboratoryTreeDisplaySelectionChange
        if(!evt.getDisplay().isEnabled() || !evt.getDisplay().getOfflineCapable())
            return;
        
        ExpDataDisplay display = null;
        String displayLocation = ReCResourceBundle.findStringOrDefault(evt.getDisplay().getClassLocationBundleKey(), null);
        try
        {
            Object displayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), displayLocation);
            if(java.beans.Beans.isInstanceOf(displayTemp, ExpDataDisplay.class))
                display = (ExpDataDisplay)displayTemp;
        }
        catch(Exception e)
        {
            LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            return;
        }
        
        
        if(offlineApparatusDisplaysList == null)
            offlineApparatusDisplaysList = new ArrayList<JInternalFrame>();
        
        OfflineInternalFrame offInternalFrame = null;
        
        for(int i=0; i<offlineApparatusDisplaysList.size(); i++)
        {
            if(((OfflineInternalFrame)offlineApparatusDisplaysList.get(i)).getHardwareID().equals(evt.getApparatus().getLocation()))
            {
                offInternalFrame = (OfflineInternalFrame)offlineApparatusDisplaysList.get(i);
            }
        }
        
        if(offInternalFrame == null)
            offInternalFrame = new OfflineInternalFrame(evt.getApparatus().getLocation());
        
        offInternalFrame.addOfflineDisplay(display);
        
        if(offlineApparatusDisplaysList.contains(offInternalFrame))
        {
            offInternalFrame.setVisible(true);
            offInternalFrame.requestFocus();
            return;
        }
        offlineApparatusDisplaysList.add(offInternalFrame);
        
        offInternalFrame.setFrameIcon(evt.getApparatus().getIcon());
        
        offInternalFrame.setTitle(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.offline", "Offline Displays") + " ["+ evt.getApparatus().getText() +"]");
        
        offInternalFrame.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.experiment", "Experiment") + " ["+ evt.getApparatus().getText() +"]");
        
        mDIDesktopPane.add(offInternalFrame);
        
        offInternalFrame.setSize(mDIDesktopPane.getSize().width-10, mDIDesktopPane.getSize().height-10);
        
        offInternalFrame.setVisible(true);
    }//GEN-LAST:event_laboratoryTreeDisplaySelectionChange
    
    private void laboratoryTreeDefaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent evt)//GEN-FIRST:event_laboratoryTreeDefaultConfigSelectionChange
    {//GEN-HEADEREND:event_laboratoryTreeDefaultConfigSelectionChange
        if(!evt.getDefaultConfig().isEnabled())
            return;
        
        String configLocation = ReCResourceBundle.findStringOrDefault(evt.getDefaultConfig().getClassLocationBundleKey(), null);
        
        HardwareAcquisitionConfig defaultConfig = null;
        
        if(configLocation == null)
        {
            return;
        }
        else
        {
            
            try
            {
                java.io.ObjectInputStream ois = new java.io.ObjectInputStream(getClass().getResourceAsStream(configLocation));
                defaultConfig = (HardwareAcquisitionConfig)ois.readObject();
                ois.close();
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        if(defaultConfig == null)
            return;
        
        config = defaultConfig;
        customizeDoneOnce = true;
        if(controllerPanel.isEnablePlay())
            propertyChange(new java.beans.PropertyChangeEvent(this, "play", Boolean.FALSE, Boolean.TRUE));
        else
            setAutoPlay(true);
        
    }//GEN-LAST:event_laboratoryTreeDefaultConfigSelectionChange
    
    private void laboratoryTreeApparatusSelectionChange(com.linkare.rec.impl.baseUI.labsTree.ApparatusSelectionEvent evt)//GEN-FIRST:event_laboratoryTreeApparatusSelectionChange
    {//GEN-HEADEREND:event_laboratoryTreeApparatusSelectionChange
        
        if(!evt.getApparatus().isEnabled())
            return;
        
        if(currentApparatusConfig != null)
        {
            DisplayTreeNodeUtils.disableAllApparatusContents(currentApparatusConfig);
            menuBar.removeApparatus();
        }
        
        
        if(currentApparatus != null)
        {
            apparatusClientBean.disconnect();
        }
        
        controllerPanel.setEnableCustomize(false);
        controllerPanel.setEnablePlay(false);
        controllerPanel.setEnableStop(false);
        
        customizeDoneOnce=false;
        
        currentApparatusConfig = evt.getApparatus();
        currentApparatus = labClientBean.getApparatusByID(currentApparatusConfig.getLocation());
        
        if(currentApparatus == null)
            return;
        
        currentCustomizer = null;
        
        config = null;
        lockable = false;
        locktried = false;
        
        if(lockerTask!=null)
        {
            lockerTask.cancel();
            lockerTask=null;
            countDownProgressPanel.setStop(true);
        }
        
        apparatusClientBean.getUserInfo().setUserName(loginFrame.getUsername());
        apparatusClientBean.getUserInfo().setPassword(loginFrame.getPassword());
        
        apparatusClientBean.setApparatus(currentApparatus);
        
        if(isEnterApparatusRoom())
        {
            chatFrame.setChatServer(apparatusClientBean);
            chatFrame.setUser(new com.linkare.rec.acquisition.UserInfo(loginFrame.getUsername()));
        }
        
        new Thread()
        {
            public void run()
            {
                apparatusClientBean.connect();
            }
        }.start();
        
        waitDialog = new WaitDialog(this, true, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.loadingCustomizer", "Loading customizer..."), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.customize", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Preferences16.gif"))));
        waitDialog.setVisible(true);                        
    }//GEN-LAST:event_laboratoryTreeApparatusSelectionChange
    
    private void laboratoryTreeLabSelectionChange(com.linkare.rec.impl.baseUI.labsTree.LabSelectionEvent evt)//GEN-FIRST:event_laboratoryTreeLabSelectionChange
    {//GEN-HEADEREND:event_laboratoryTreeLabSelectionChange
        //Ok, I've changed this because some users didn't like this feature, from now on it will change state if it connected...
        if(!evt.getLab().isEnabled())
            propertyChange(new java.beans.PropertyChangeEvent(this, "connect", Boolean.FALSE, Boolean.TRUE));
    }//GEN-LAST:event_laboratoryTreeLabSelectionChange
    
    private void chatFrameInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_chatFrameInternalFrameClosing
    {//GEN-HEADEREND:event_chatFrameInternalFrameClosing
        setShowChatFrame(false);
    }//GEN-LAST:event_chatFrameInternalFrameClosing
    
    private void jInternalFrameLabTreeInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_jInternalFrameLabTreeInternalFrameClosing
    {//GEN-HEADEREND:event_jInternalFrameLabTreeInternalFrameClosing
        setShowApparatusFrame(false);
    }//GEN-LAST:event_jInternalFrameLabTreeInternalFrameClosing
    
    private boolean shuttingDown = false;
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        shuttingDown = true;
        
        setVisible(false);
        
        apparatusClientBean.disconnect();
        labClientBean.disconnect();
        countDownProgressPanel.setStop(true);
        
        try
        {
            synchronized(this)
            {
                //Wait that in the server side they realize I'm gone!
                wait(5000);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }//GEN-LAST:event_exitForm
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        new ReCBaseUI().setVisible(true);
    }
    
    /**
     * Getter for property apparatusAutoConnectID.
     * @return Value of property apparatusAutoConnectID.
     */
    public String getApparatusAutoConnectID()
    {
        return this.apparatusAutoConnectID;
    }
    
    /**
     * Setter for property apparatusAutoConnectID.
     * @param apparatusAutoConnectID New value of property apparatusAutoConnectID.
     */
    public void setApparatusAutoConnectID(String apparatusAutoConnectID)
    {
        this.apparatusAutoConnectID = apparatusAutoConnectID;
        if(this.apparatusAutoConnectID!=null)
            this.setApparatusListEnabled(false);
        else
            this.setApparatusListEnabled(true);
    }
    
    /**
     * Getter for property apparatusListEnabled.
     * @return Value of property apparatusListEnabled.
     */
    public boolean isApparatusListEnabled()
    {
        return this.apparatusListEnabled;
    }
    
    /**
     * Setter for property apparatusListEnabled.
     * @param apparatusListEnabled New value of property apparatusListEnabled.
     */
    public void setApparatusListEnabled(boolean apparatusListEnabled)
    {
        this.apparatusListEnabled = apparatusListEnabled;
    }
    
    /**
     * Getter for property videoFrameEnabled.
     * @return Value of property videoFrameEnabled.
     */
    public boolean isVideoFrameEnabled()
    {
        return this.videoFrameEnabled;
    }
    
    /**
     * Setter for property videoFrameEnabled.
     * @param videoFrameEnabled New value of property videoFrameEnabled.
     */
    public void setVideoFrameEnabled(boolean videoFrameEnabled)
    {
        this.videoFrameEnabled = videoFrameEnabled;
        toolBarPanel.setEnableVideo(videoFrameEnabled);
    }
    
    /**
     * Getter for property chatFrameEnabled.
     * @return Value of property chatFrameEnabled.
     */
    public boolean isChatFrameEnabled()
    {
        return this.chatFrameEnabled;
    }
    
    /**
     * Setter for property chatFrameEnabled.
     * @param chatFrameEnabled New value of property chatFrameEnabled.
     */
    public void setChatFrameEnabled(boolean chatFrameEnabled)
    {
        this.chatFrameEnabled = chatFrameEnabled;
        toolBarPanel.setEnableChat(chatFrameEnabled);
    }
    
    /**
     * Getter for property usersListEnable.
     * @return Value of property usersListEnable.
     */
    public boolean isUsersListEnabled()
    {
        return this.usersListEnabled;
    }
    
    /**
     * Setter for property usersListEnable.
     * @param usersListEnable New value of property usersListEnable.
     */
    public void setUsersListEnabled(boolean usersListEnabled)
    {
        this.usersListEnabled = usersListEnabled;
        toolBarPanel.setEnableUsersList(usersListEnabled);
    }
    
    /**
     * Getter for property showChatFrame.
     * @return Value of property showChatFrame.
     */
    public boolean isShowChatFrame()
    {
        return this.showChatFrame;
    }
    
    /**
     * Setter for property showChatFrame.
     * @param showChatFrame New value of property showChatFrame.
     */
    public void setShowChatFrame(boolean showChatFrame)
    {
        this.showChatFrame = showChatFrame;
        if(showChatFrame)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    chatFrame.setVisible(true);
                    toolBarPanel.setSelectChat(true);
                }
            });
        }
        else
        {
            try
            {
                //This way we lock the swing thread and it has to wait for us to finish first
                Thread t=new Thread()
                {
                    public void run()
                    {
                        chatFrame.setVisible(false);
                    }
                };
                
                t.start();
                t.join();
            }
            catch(InterruptedException ignored)
            {}
            toolBarPanel.setSelectChat(false);
        }
    }
    
    /**
     * Getter for property showUsersList.
     * @return Value of property showUsersList.
     */
    public boolean isShowUsersList()
    {
        return this.showUsersList;
    }
    
    /**
     * Setter for property showUsersList.
     * @param showUsersList New value of property showUsersList.
     */
    public void setShowUsersList(boolean showUsersList)
    {
        int found = -1;
        java.awt.Component[] comps = jTabbedPaneInfoUsers.getComponents();
        for(int i=0; i<comps.length; i++)
        {
            if(comps[i].equals(usersListPanel))
            {
                found = i;
                break;
            }
        }
        
        this.showUsersList = showUsersList;
        if(showUsersList)
        {
            if(found > -1)
                return;
            
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    if(!jInternalFrameTabs.isVisible())
                        jInternalFrameTabs.setVisible(true);
                    jTabbedPaneInfoUsers.addTab(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.apparatusUsersList", "Apparatus Users List"), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.users", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif"))), usersListPanel, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.users", "Shows you the current users of the apparatus and an estimate of the control timings"));
                    jTabbedPaneInfoUsers.setSelectedComponent(usersListPanel);
                    toolBarPanel.setSelectUsersList(true);
                }
            });
        }
        else
        {
            if(found < 0)
                return;
            
            jTabbedPaneInfoUsers.remove(usersListPanel);
            toolBarPanel.setSelectUsersList(false);
            
            //It wasn't working well with SwingUtilities.invokeLater
            if(jTabbedPaneInfoUsers.getTabCount() == 0)
            {
                try
                {
                    //This way we lock the swing thread and it has to wait for us to finish first
                    Thread t=new Thread()
                    {
                        public void run()
                        {
                            jInternalFrameTabs.setVisible(false);
                        }
                    };
                    
                    t.start();
                    t.join();
                }
                catch(InterruptedException ignored)
                {}
            }
        }
    }
    
    /**
     * Getter for property showVideoFrame.
     * @return Value of property showVideoFrame.
     */
    public boolean isShowVideoFrame()
    {
        return this.showVideoFrame;
    }
    
    /**
     * Setter for property showVideoFrame.
     * @param showVideoFrame New value of property showVideoFrame.
     */
    public void setShowVideoFrame(boolean showVideoFrame)
    {
        this.showVideoFrame = showVideoFrame;
    }
    
    /**
     * Getter for property autoConnectLab.
     * @return Value of property autoConnectLab.
     */
    public boolean isAutoConnectLab()
    {
        return this.autoConnectLab;
    }
    
    /**
     * Setter for property autoConnectLab.
     * @param autoConnectLab New value of property autoConnectLab.
     */
    public void setAutoConnectLab(boolean autoConnectLab)
    {
        //TODO solve the autoConnectLab
        this.autoConnectLab = autoConnectLab;
    }
    
    /**
     * Getter for property showApparatusFrame.
     * @return Value of property showApparatusFrame.
     */
    public boolean isShowApparatusFrame()
    {
        return this.showApparatusFrame;
    }
    
    /**
     * Setter for property showApparatusFrame.
     * @param showApparatusFrame New value of property showApparatusFrame.
     */
    public void setShowApparatusFrame(boolean showApparatusFrame)
    {
        this.showApparatusFrame = showApparatusFrame;
        if(showApparatusFrame)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    jInternalFrameLabTree.setVisible(true);
                    toolBarPanel.setSelectApparatusTree(true);
                }
            });
        }
        else
        {
            try
            {
                //This way we lock the swing thread and it has to wait for us to finish first
                Thread t=new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            jInternalFrameLabTree.setVisible(false);
                            
                        }
                        catch(Exception e2)
                        {
                            LoggerUtil.logThrowable(e2.getMessage(), e2, Logger.getLogger(UI_CLIENT_LOGGER));
                        }
                    }
                };
                t.start();
                t.join();
            }
            catch(InterruptedException ignored)
            {}
            toolBarPanel.setSelectApparatusTree(false);
        }
    }
    
    /**
     * Getter for property autoPlay.
     * @return Value of property autoPlay.
     */
    public boolean isAutoPlay()
    {
        return this.autoPlay;
    }
    
    /**
     * Setter for property autoPlay.
     * @param autoPlay New value of property autoPlay.
     */
    public void setAutoPlay(boolean autoPlay)
    {
        this.autoPlay = autoPlay;
        toolBarPanel.setSelectAutoPlay(autoPlay);
        menuBar.setSelectAutoPlay(autoPlay);
    }
    
    private void openURL(java.net.URL url, boolean internalBrowser)
    {
        if(url == null)
            return;
        
        if(internalBrowser)
        {
            InternalBrowser browser = new InternalBrowser(url);
            mDIDesktopPane.add(browser);
            return;
        }
        
        try
        {
            javax.jnlp.BasicService bs=(javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");
            if(bs!=null)
                try
                {
                    //System.out.println("bs.isWebBrowserSupported() ? " + bs.isWebBrowserSupported());
                    System.out.println("bs.isOffline() ? " + bs.isOffline());
                    boolean ok = bs.showDocument(url);
                    
                    //System.out.println("Showed the document ? " + ok);
                    
                }
                catch(Exception e2)
                {
                    LoggerUtil.logThrowable(e2.getMessage(), e2, Logger.getLogger(UI_CLIENT_LOGGER));
                }
            //else
            //System.out.println("bs is null...WHY?");
        }
        catch(Exception e)
        {
            LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
        }
    }
    
    
    public void canceled()
    {//silent noop - Customizer canceled...
    }
    
    public void done()
    {
        config = currentCustomizer.getAcquisitionConfig();
        customizeDoneOnce = true;
        
        if(isAutoPlay() && customizeDoneOnce && lockable)
        {
            setAutoPlay(false);
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    propertyChange(new java.beans.PropertyChangeEvent(this, "play", Boolean.FALSE, Boolean.TRUE));
                }
            });
        }
    }
    
    /**
     * Getter for property customizeDoneOnce.
     * @return Value of property customizeDoneOnce.
     */
    public boolean isCustomizeDoneOnce()
    {
        return this.customizeDoneOnce;
    }
    
    /**
     * Setter for property customizeDoneOnce.
     * @param customizeDoneOnce New value of property customizeDoneOnce.
     */
    public void setCustomizeDoneOnce(boolean customizeDoneOnce)
    {
        this.customizeDoneOnce = customizeDoneOnce;
    }
    
    /**
     * Getter for property lockable.
     * @return Value of property lockable.
     */
    public boolean isLockable()
    {
        return this.lockable;
    }
    
    /**
     * Setter for property lockable.
     * @param lockable New value of property lockable.
     */
    public void setLockable(boolean lockable)
    {
        this.lockable = lockable;
    }
    
    /**
     * Getter for property locked.
     * @return Value of property locked.
     */
    public boolean isLocked()
    {
        return this.locked;
    }
    
    /**
     * Setter for property locked.
     * @param locked New value of property locked.
     */
    public void setLocked(boolean locked)
    {
        this.locked = locked;
    }
    
    /**
     * Getter for property started.
     * @return Value of property started.
     */
    public boolean isStarted()
    {
        return this.started;
    }
    
    /**
     * Setter for property started.
     * @param started New value of property started.
     */
    public void setStarted(boolean started)
    {
        this.started = started;
    }
    
    public void showExperimentHeader(ExpHistory expHistory)
    {
        HardwareAcquisitionConfig config=null;
        try
        {
            config=expHistory.getProducerWrapper().getAcquisitionHeader();
        }catch(Exception ignored)
        {
            LoggerUtil.logThrowable("Couldn't show Experiment Info...",ignored,Logger.getLogger(UI_CLIENT_LOGGER));
            return;
        }
        
        javax.swing.JFrame frame = new javax.swing.JFrame("Experiment [" + config.getFamiliarName() +"] - "+config.getTimeStart()+" Info");
        frame.setIconImage((new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/hwinfo16.gif"))).getImage());
                
        javax.swing.JTextArea tainfo = new javax.swing.JTextArea(/*ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.expOwner", "Started by:") + " " + expHistory.getOwnerUserName()*/);
        tainfo.append(LS + config.toString());
        tainfo.setColumns(40);
        tainfo.setRows(20);
        frame.getContentPane().add(new javax.swing.JScrollPane(tainfo));
        frame.pack();
        frame.setLocation((getLocation().x+getWidth())/2-frame.getWidth()/2,(getLocation().y+getHeight())/2-frame.getHeight()/2);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void startExperiment(ExpHistory expHistory)
    {
        ExpDataDisplay[] displays = null;
        DisplayFactory factory = null;
        
        //Was the user smart enough to make is own DisplayFactory?
        String factoryLocation = null;
        try
        {
            ///factoryLocation = ReCResourceBundle.findStringOrDefault(currentApparatusConfig.getDisplayFactoryClassLocationBundleKey(), null);
            factoryLocation = ReCResourceBundle.findStringOrDefault(expHistory.getApparatusConfig().getDisplayFactoryClassLocationBundleKey(), null);
        }
        catch(Exception ignored)
        {
            //don't print the not found exception please...
        }
        //Of course not... maybe he didn't want to...
        if(factoryLocation == null)
        {
            //Load default
            factory = new DefaultDisplayFactory();
        }
        //Ok the user wants to load his own Factory
        else
        {
            try
            {
                Object displayFactoryTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), factoryLocation);
                if(java.beans.Beans.isInstanceOf(displayFactoryTemp, DisplayFactory.class))
                    factory = (DisplayFactory)displayFactoryTemp;
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        
        if(factory != null)
        {
            //I will only give the selected displays :)
            Vector<Display> selectedDisplays = new Vector<Display>();
            Display[] availableDisplays = expHistory.getApparatusConfig().getDisplay();
            for(int i=0; i<availableDisplays.length; i++)
            {
                if(availableDisplays[i].isSelected())
                    selectedDisplays.add(availableDisplays[i]);
            }
            
            factory.init((Display[])selectedDisplays.toArray(new Display[0]));
            factory.setAcquisitionInfo(expHistory.getApparatus().getHardwareInfo());
            try
            {
                factory.setAcquisitionConfig(expHistory.getProducerWrapper().getAcquisitionHeader());
            }
            catch(Exception e)
            {
            }
            displays = factory.getDisplays();
        }
        
        //Couldn't read from xml or from user
        if(displays==null)
        {
            try
            {
                displays = new ExpDataDisplay[1];
                Object dataDisplayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), "com.linkare.rec.impl.baseUI.DefaultExperimentDataTable");
                if(java.beans.Beans.isInstanceOf(dataDisplayTemp, ExpDataDisplay.class))
                    displays[0] = (ExpDataDisplay)dataDisplayTemp;
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        
        ExpDataModel dataModel = null;
        //Did the user defined is own datamodel?
        String dataModelLocation = null;
        try
        {
            dataModelLocation = ReCResourceBundle.findStringOrDefault(expHistory.getApparatusConfig().getDataModelClassLocationBundleKey(), null);
        }
        catch(Exception ignored)
        {
            //don't print the not found exception please...
        }
        
        if(dataModelLocation != null)
        {
            try
            {
                Object expDataModelTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), dataModelLocation);
                if(java.beans.Beans.isInstanceOf(expDataModelTemp, ExpDataModel.class))
                    dataModel = (ExpDataModel)expDataModelTemp;
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        //if the user didn't defined is data model, then use the default one
        if(dataModel == null)
            dataModel = new DefaultExpDataModel();
        
        try
        {
            dataModel.setDpwDataSource(expHistory.getProducerWrapper());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            statusPanelApparatus.setStatus(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.linkare.rec.status.failedDataoutputConnection", "Failed data output connection..."));
            return;
        }
        
        final ExperimentInternalFrame experimentInternalFrame = new ExperimentInternalFrame();
        
        experimentInternalFrame.setExpDataModel(dataModel);
        
        //The experimentInternalFrame takes care of adding the expdatamodels to the displays
        for(int i=0;i<displays.length;i++)
        {
            experimentInternalFrame.addExpDataDisplay(displays[i]);
        }
        
        experimentInternalFrame.setTitle(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.experiment", "Experiment") + expHistory.getExpCount() + " ["+ expHistory.getApparatusName() +"]");
        
        experimentInternalFrame.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.experiment", "Experiment") + expHistory.getExpCount() + " ["+ expHistory.getApparatusName() +"]");
        
        mDIDesktopPane.add(experimentInternalFrame);
        
        experimentInternalFrame.setSize(mDIDesktopPane.getSize().width-10, mDIDesktopPane.getSize().height-10);
        
        experimentInternalFrame.setVisible(true);
    }
    
    /**
     * Getter for property showedMessageConfig.
     * @return Value of property showedMessageConfig.
     */
    public boolean isShowedMessageConfig()
    {
        return this.showedMessageConfig;
    }
    
    /**
     * Setter for property showedMessageConfig.
     * @param showedMessageConfig New value of property showedMessageConfig.
     */
    public void setShowedMessageConfig(boolean showedMessageConfig)
    {
        this.showedMessageConfig = showedMessageConfig;
    }
    
    /**
     * Getter for property enterApparatusRoom.
     * @return Value of property enterApparatusRoom.
     */
    public boolean isEnterApparatusRoom()
    {
        return this.enterApparatusRoom;
    }
    
    /**
     * Setter for property enterApparatusRoom.
     * @param enterApparatusRoom New value of property enterApparatusRoom.
     */
    public void setEnterApparatusRoom(boolean enterApparatusRoom)
    {
        this.enterApparatusRoom = enterApparatusRoom;
    }
    
    /**
     * Getter for property showHistory.
     * @return Value of property showHistory.
     */
    public boolean isShowHistory()
    {
        return this.showHistory;
    }
    
    /**
     * Setter for property showHistory.
     * @param showHistory New value of property showHistory.
     */
    public void setShowHistory(boolean showHistory)
    {
        int found = -1;
        java.awt.Component[] comps = jTabbedPaneInfoUsers.getComponents();
        for(int i=0; i<comps.length; i++)
        {
            if(comps[i].equals(expHistoryPanelNew))
            {
                found = i;
                break;
            }
        }
        
        this.showHistory = showHistory;
        if(showHistory)
        {
            if(found > -1)
                return;
            
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    if(!jInternalFrameTabs.isVisible())
                        jInternalFrameTabs.setVisible(true);
                    jTabbedPaneInfoUsers.addTab(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.expHistory", "Experiment History"), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.experiment", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Experiment16.gif"))), expHistoryPanelNew, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.expHistory", "Experiment History gives you access to all the experiments that have been made since you were connected to the system"));
                    toolBarPanel.setSelectHistory(true);
                    jTabbedPaneInfoUsers.setSelectedComponent(expHistoryPanelNew);
                }
            });
        }
        else
        {
            if(found < 0)
                return;
            
            jTabbedPaneInfoUsers.remove(expHistoryPanelNew);
            toolBarPanel.setSelectHistory(false);
            
            //It wasn't working well with SwingUtilities.invokeLater
            if(jTabbedPaneInfoUsers.getTabCount() == 0)
            {
                try
                {
                    //This way we lock the swing thread and it has to wait for us to finish first
                    Thread t=new Thread()
                    {
                        public void run()
                        {
                            jInternalFrameTabs.setVisible(false);
                        }
                    };
                    
                    t.start();
                    t.join();
                }
                catch(InterruptedException ignored)
                {}
            }
        }
    }
    
    /*private void disableApparatus(com.linkare.rec.impl.baseUI.config.Apparatus app)
    {
        app.setEnabled(false);
        Display[] dps = app.getDisplay();
        for(int i=0; i<dps.length; i++)
        {
            if(!dps[i].getOfflineCapable())
                dps[i].setEnabled(false);
        }
     
        DefaultAcquisitionConfig[] dfac = app.getDefaultAcquisitionConfig();
        for(int j=0; j<dfac.length; j++)
        {
            dfac[j].setEnabled(false);
        }
    }*/
    
    /**
     * Getter for property connectLab.
     * @return Value of property connectLab.
     */
    public boolean isConnectLab()
    {
        return this.connectLab;
    }
    
    private Lab currentLab = null;
    private boolean alreadyDisconnected = false;
    private boolean finishedDisconnection = false;
    
    /**
     * Setter for property connectLab.
     * @param connectLab New value of property connectLab.
     */
    public void setConnectLab(boolean connectLab)
    {
        if(shuttingDown)
            return;
        
        if(!connectLab && !alreadyDisconnected)// && currentLab != null && currentLab.isEnabled())
        {
            alreadyDisconnected = true;
            DisplayTreeNodeUtils.disableLab(currentLab);
            if(currentApparatusConfig != null)
            {
                DisplayTreeNodeUtils.disableAllApparatusContents(currentApparatusConfig);
            }
            apparatusClientBean.disconnect();
            labClientBean.disconnect();
            currentLab.setEnabled(false);
            countDownProgressPanel.setStop(true);
            controllerPanel.setEnablePlay(false);
            controllerPanel.setEnableStop(false);
            
            finishedDisconnection = true;
            
            if(waitDialog != null)
                waitDialog.setVisible(false);
            
            if(recBaseUI.getDesktopLocationBundleKey() != null)
                mDIDesktopPane.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault(recBaseUI.getDesktopLocationBundleKey(), new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/desktopback.png"))).getImage(), true);
            else
                mDIDesktopPane.setBackgroundImage(null, false);
            
        }
        else if(connectLab)
        {
            /*DisplayNode node = laboratoryTree.getCurrentSelectedNode();
            currentLab = (Lab)node;*/
            
            if(loginFrame.getUsername() == null || loginFrame.getPassword() == null)
                return;
            
            waitDialog = new WaitDialog(this, false, ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.connectingToLab", "Connecting to lab"), ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.connect", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif"))));
            //waitDialog.setBackgroundImage(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.image.waitConnectLab", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/connectlab.jpg"))).getImage(), true);
            waitDialog.setVisible(true  );
            
            labClientBean.getUserInfo().setUserName(loginFrame.getUsername());
            labClientBean.getUserInfo().setPassword(loginFrame.getPassword());
            
            labClientBean.connect(currentLab.getLocation());
            alreadyDisconnected = false;
            finishedDisconnection = false;
        }
        
        toolBarPanel.setSelectConnect(connectLab);
        this.connectLab = connectLab;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.baseUI.AboutDialog aboutDialog;
    private com.linkare.rec.impl.client.ApparatusClientBean apparatusClientBean;
    private com.linkare.rec.impl.baseUI.ChatFrame chatFrame;
    private com.linkare.rec.impl.baseUI.control.ControllerPanel controllerPanel;
    private com.linkare.rec.impl.baseUI.control.CountDownProgressPanel countDownProgressPanel;
    private com.linkare.rec.impl.baseUI.ExpHistoryPanelNew expHistoryPanelNew;
    private com.linkare.rec.impl.baseUI.JDockPanel jDockPanel1;
    private com.linkare.rec.impl.baseUI.JDockPanel jDockPanel2;
    private javax.swing.JInternalFrame jInternalFrameLabTree;
    private javax.swing.JInternalFrame jInternalFrameTabs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPaneInfoUsers;
    private com.linkare.rec.impl.client.LabClientBean labClientBean;
    private com.linkare.rec.impl.baseUI.labsTree.LaboratoryTree laboratoryTree;
    private com.linkare.rec.impl.baseUI.LoginFrame loginFrame;
    private com.linkare.rec.impl.baseUI.mdi.MDIDesktopPane mDIDesktopPane;
    private com.linkare.rec.impl.baseUI.control.MenuBar menuBar;
    private com.linkare.rec.impl.baseUI.ReCSplash reCSplash;
    private com.linkare.rec.impl.baseUI.StatusPanel statusPanelApparatus;
    private com.linkare.rec.impl.baseUI.StatusPanel statusPanelLab;
    private com.linkare.rec.impl.baseUI.control.ToolBarPanel toolBarPanel;
    private com.linkare.rec.impl.baseUI.UsersListPanel usersListPanel;
    // End of variables declaration//GEN-END:variables
    
    private WaitDialog waitDialog = null;
    
    /**
     * Holds value of property apparatusAutoConnectID.
     */
    private String apparatusAutoConnectID;
    
    /**
     * Holds value of property apparatusListEnabled.
     */
    private boolean apparatusListEnabled;
    
    /**
     * Holds value of property videoFrameEnabled.
     */
    private boolean videoFrameEnabled;
    
    /**
     * Holds value of property chatFrameEnabled.
     */
    private boolean chatFrameEnabled;
    
    /**
     * Holds value of property usersListEnabled.
     */
    private boolean usersListEnabled;
    
    /**
     * Holds value of property showChatFrame.
     */
    private boolean showChatFrame;
    
    /**
     * Holds value of property showUsersList.
     */
    private boolean showUsersList;
    
    /**
     * Holds value of property showVideoFrame.
     */
    private boolean showVideoFrame;
    
    /**
     * Holds value of property autoConnectLab.
     */
    private boolean autoConnectLab;
    
    /**
     * Holds value of property showApparatusFrame.
     */
    private boolean showApparatusFrame;
    
    /**
     * Holds value of property autoPlay.
     */
    private boolean autoPlay;
    
    /**
     * Holds value of property customizeDoneOnce.
     */
    private boolean customizeDoneOnce = false;
    
    /**
     * Holds value of property lockable.
     */
    private boolean lockable;
    
    /**
     * Holds value of property locked.
     */
    private boolean locked;
    
    /**
     * Holds value of property started.
     */
    private boolean started;
    
    /**
     * Holds value of property started.
     */
    private boolean locktried = false;
    
    /**
     * Holds value of property showedMessageConfig.
     */
    private boolean showedMessageConfig;
    
    /**
     * Holds value of property enterApparatusRoom.
     */
    private boolean enterApparatusRoom;
    
    /**
     * Holds value of property showHistory.
     */
    private boolean showHistory;
    
    /**
     * Holds value of property connectLab.
     */
    private boolean connectLab;
    
}
