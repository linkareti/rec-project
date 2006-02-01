/*
 * RecBaseUI.java
 *
 * Created on 28 April 2003, 22:20
 */

package old;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.beans.*;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.impl.client.lab.*;
import com.linkare.rec.impl.client.apparatus.*;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.impl.baseUI.mdi.*;
import java.util.*;
import com.linkare.rec.impl.baseUI.config.*;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class RecBaseUI extends javax.swing.JFrame implements ExpHistoryDisplayFactory, ICustomizerListener
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
    
    private int chatLastDividerLocation=0;
    private int tabCountBefore=0;
    private boolean locked=false;
    private HardwareAcquisitionConfig config=null;
    private boolean connected=false;
    private boolean started=false;
    
    //private ReCUIConfig uiConfig=ReCUIConfig.getReCUIConfig();
    private ReCBaseUIConfig uiConfig = ReCBaseUIConfig.sharedInstance();
    //private Hashtable acq_displays=new Hashtable();
    
    
    /** Creates new form RecBaseUI */
    public RecBaseUI()
    {
	ReCSplash splash=new ReCSplash(this, false);
	
	setIconImage(uiConfig.getIcon());
	splash.show();
	splash.requestFocus();
	
	splash.setStatusMessage("Initializing Communication Platform...");
	ORBBean.getORBBean();
	
	splash.setStatusMessage("Initializing Main Laboratory Window...");
	initComponents();
	JDialog.setDefaultLookAndFeelDecorated(true);
	tabPaneUserExperiment.setIconAt(1,new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif")));
	tabPaneUserExperiment.setIconAt(0,new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Experiment16.gif")));
	
	splash.setStatusMessage("Reading preferences...");

	lblimplOutSidePub.setIcon(uiConfig.getLogoSponsor());
	
	
	setVideoFrameEnabled(uiConfig.isEnableVideoFrame());
	//setApparatusListEnabled(false);
	setChatFrameEnabled(uiConfig.isEnableChatFrame());
	setUsersListEnabled(uiConfig.isEnableUsersList());
	
	setEnterApparatusRoom(uiConfig.isEnterApparatusChatRoom());
	
	if(uiConfig.getUsername()!=null)
	    loginFrame.setUsername(uiConfig.getUsername());
	if(uiConfig.getPassword()!=null)
	    loginFrame.setPassword(uiConfig.getPassword());
	
	
	if(uiConfig.getFrameTitle()!=null)
	    setTitle(uiConfig.getFrameTitle());
	
	if(uiConfig.getHelpURL()!=null)
	    contentMenuItem.setEnabled(true);
	
	if(!uiConfig.isShowChatFrame())
	{
	    tglBtnChat.setSelected(false);
	    tglBtnChatActionPerformed(new ActionEvent(this,0,tglBtnChat.getActionCommand()));
	}
	if(!uiConfig.isShowUsersList())
	{
	    tglBtnUsersList.setSelected(false);
	    tglBtnUsersListActionPerformed(new ActionEvent(this,0,tglBtnUsersList.getActionCommand()));
	}
	if(!uiConfig.isShowVideoFrame())
	{
	    tglBtnVideo.setSelected(false);
	    tglBtnVideoActionPerformed(new ActionEvent(this,0,tglBtnVideo.getActionCommand()));
	}
	
	apparatusClientBean.setUsersListRefreshPeriod((int)uiConfig.getUsersListRefreshRate()/1000);
	
	splash.setStatusMessage("Loading Web Resources...");
	
	if(uiConfig.recWebResources.getJMenu()!=null)
	{
	    menuWebResources.add(uiConfig.recWebResources.getJMenu());
	    menuWebResources.setEnabled(true);
	}
		
	
	splash.setStatusMessage("Starting User Interface...");
	
	try
	{
	    videoFrame.setMaximum(true);
	}catch(java.beans.PropertyVetoException ignored)
	{}
	try
	{
	    chatFrame.setMaximum(true);
	}catch(java.beans.PropertyVetoException ignored)
	{}
	try
	{
	    labTreeFrame.setMaximum(true);
	} catch (java.beans.PropertyVetoException e1)
	{
	    e1.printStackTrace();
	}
	
	splitPaneChatOthers.setDividerLocation(0.5);
	splitPaneVertical.setDividerLocation(0.70);
	chatLastDividerLocation=splitPaneChatOthers.getDividerLocation();
	
	defaulExpUsersListTableModel.setAutoRefresh(uiConfig.getUsersListRefreshRate());
	
	splash.setStatusMessage("All Done!");
	
	splash.closeDialog(new WindowEvent(this,0));
	
	if(uiConfig.isAutoConectLab())
	{
	    SwingUtilities.invokeLater(
	    new Runnable()
	    {
		public void run()
		{
		    btnConnectActionPerformed(new ActionEvent(this,0,btnConnect.getActionCommand()));
		}
	    }
	    );
	}
	
    }
    
    public void show()
    {
	super.show();
	if(!uiConfig.isAutoConectLab())
	{
	    Point locationConnect=btnConnect.getLocationOnScreen();
	    locationConnect.x=locationConnect.x+btnConnect.getWidth();
	    locationConnect.y=locationConnect.y+btnConnect.getHeight();
	    try
	    {
		java.net.URL url=getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif");
		showTipWithTimeout(btnConnect,"<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>Click the Connect button<br><img src='"+url.toExternalForm()+"'><br>to enter the Lab</font></center></body></html>",5000,locationConnect);
	    }catch(Exception e)
	    {
		e.printStackTrace();
	    }
	}
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPaneltoolBarsAndImage = new javax.swing.JPanel();
        jPanelToolBars = new javax.swing.JPanel();
        toolBarMain = new javax.swing.JToolBar();
        btnConnect = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        checkAutoPlay = new javax.swing.JCheckBox();
        toolBarView = new javax.swing.JToolBar();
        tglBtnUsersList = new javax.swing.JToggleButton();
        tglBtnChat = new javax.swing.JToggleButton();
        tglBtnVideo = new javax.swing.JToggleButton();
        tglBtnApparatusList = new javax.swing.JToggleButton();
        lblimplOutSidePub = new javax.swing.JLabel();
        splitPaneVertical = new javax.swing.JSplitPane();
        baseDeskTopPanel = new javax.swing.JPanel();
        splitPaneDeskTopLabTree = new javax.swing.JSplitPane();
        panelLabTree = new javax.swing.JPanel();
        labTreeFrame = new javax.swing.JInternalFrame();
        scrollPaneExperience = new javax.swing.JScrollPane();
        splitPaneChatOthers = new javax.swing.JSplitPane();
        panelVideoUsersExperimentList = new javax.swing.JPanel();
        splitPaneUserExperimentsVideo = new javax.swing.JSplitPane();
        tabPaneUserExperiment = new javax.swing.JTabbedPane();
        scrollPaneUsersList = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        panelVideo = new javax.swing.JPanel();
        videoFrame = new javax.swing.JInternalFrame();
        panelChat = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnConfig = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        mainProgressBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        lblApparatusStatus = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblLabStatus = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        labMenu = new javax.swing.JMenu();
        connectMenuItem = new javax.swing.JMenuItem();
        autoPlayMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        apparatusListMenuItem = new javax.swing.JMenuItem();
        usersListMenuItem = new javax.swing.JMenuItem();
        chatMenuItem = new javax.swing.JMenuItem();
        videoMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        toolBarsMenu = new javax.swing.JMenu();
        toolBarMainMenuItem = new javax.swing.JMenuItem();
        toolBarViewMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        menuWebResources = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ReC Experiment");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPaneltoolBarsAndImage.setLayout(new java.awt.BorderLayout());

        jPanelToolBars.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        btnConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif")));
        btnConnect.setToolTipText("Connect...");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        toolBarMain.add(btnConnect);

        jLabel2.setText("  ");
        toolBarMain.add(jLabel2);

        checkAutoPlay.setText("Auto Play");
        checkAutoPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAutoPlayActionPerformed(evt);
            }
        });

        toolBarMain.add(checkAutoPlay);

        jPanelToolBars.add(toolBarMain);

        tglBtnUsersList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif")));
        tglBtnUsersList.setSelected(true);
        tglBtnUsersList.setToolTipText("View Users List Window");
        tglBtnUsersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglBtnUsersListActionPerformed(evt);
            }
        });

        toolBarView.add(tglBtnUsersList);

        tglBtnChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Chat16.gif")));
        tglBtnChat.setSelected(true);
        tglBtnChat.setToolTipText("View Chat Window");
        tglBtnChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglBtnChatActionPerformed(evt);
            }
        });

        toolBarView.add(tglBtnChat);

        tglBtnVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Movie16.gif")));
        tglBtnVideo.setSelected(true);
        tglBtnVideo.setToolTipText("View Audio & Video Window");
        tglBtnVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglBtnVideoActionPerformed(evt);
            }
        });

        toolBarView.add(tglBtnVideo);

        tglBtnApparatusList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree16.gif")));
        tglBtnApparatusList.setSelected(true);
        tglBtnApparatusList.setToolTipText("View Apparatus List Window");
        tglBtnApparatusList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglBtnApparatusListActionPerformed(evt);
            }
        });

        toolBarView.add(tglBtnApparatusList);

        jPanelToolBars.add(toolBarView);

        jPaneltoolBarsAndImage.add(jPanelToolBars, java.awt.BorderLayout.CENTER);

        lblimplOutSidePub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/resources/ElabLogoGray.gif")));
        jPaneltoolBarsAndImage.add(lblimplOutSidePub, java.awt.BorderLayout.EAST);

        getContentPane().add(jPaneltoolBarsAndImage, java.awt.BorderLayout.NORTH);

        splitPaneVertical.setDividerLocation(475);
        splitPaneVertical.setDividerSize(5);
        splitPaneVertical.setContinuousLayout(true);
        baseDeskTopPanel.setLayout(new java.awt.BorderLayout());

        baseDeskTopPanel.setMinimumSize(new java.awt.Dimension(100, 0));
        baseDeskTopPanel.setPreferredSize(new java.awt.Dimension(450, 0));
        splitPaneDeskTopLabTree.setDividerLocation(135);
        splitPaneDeskTopLabTree.setDividerSize(5);
        panelLabTree.setLayout(new java.awt.BorderLayout());

        labTreeFrame.setBorder(null);
        labTreeFrame.setClosable(true);
        labTreeFrame.setTitle("Apparatus List");
        labTreeFrame.setDefaultCloseOperation(0);
        labTreeFrame.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree16.gif")));
        labTreeFrame.setVisible(true);
        labTreeFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                labTreeFrameInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panelLabTree.add(labTreeFrame, java.awt.BorderLayout.CENTER);

        splitPaneDeskTopLabTree.setLeftComponent(panelLabTree);

        splitPaneDeskTopLabTree.setRightComponent(scrollPaneExperience);

        baseDeskTopPanel.add(splitPaneDeskTopLabTree, java.awt.BorderLayout.CENTER);

        splitPaneVertical.setLeftComponent(baseDeskTopPanel);

        splitPaneChatOthers.setBorder(null);
        splitPaneChatOthers.setDividerLocation(200);
        splitPaneChatOthers.setDividerSize(5);
        splitPaneChatOthers.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPaneChatOthers.setContinuousLayout(true);
        panelVideoUsersExperimentList.setLayout(new java.awt.BorderLayout());

        splitPaneUserExperimentsVideo.setBorder(null);
        splitPaneUserExperimentsVideo.setDividerLocation(65);
        splitPaneUserExperimentsVideo.setDividerSize(5);
        splitPaneUserExperimentsVideo.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPaneUserExperimentsVideo.setContinuousLayout(true);
        tabPaneUserExperiment.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabPaneUserExperiment.setName("");
        scrollPaneUsersList.setViewportView(tableUsers);

        tabPaneUserExperiment.addTab("Apparatus Users List", new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif")), scrollPaneUsersList, "Shows you the current users of the apparatus and an estimate of the control timings");

        splitPaneUserExperimentsVideo.setTopComponent(tabPaneUserExperiment);

        panelVideo.setLayout(new java.awt.BorderLayout());

        videoFrame.setBorder(null);
        videoFrame.setClosable(true);
        videoFrame.setTitle("Audio and Video Window");
        videoFrame.setDefaultCloseOperation(0);
        videoFrame.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Movie16.gif")));
        videoFrame.setVisible(true);
        videoFrame.setEnabled(false);
        videoFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                videoFrameInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panelVideo.add(videoFrame, java.awt.BorderLayout.CENTER);

        splitPaneUserExperimentsVideo.setRightComponent(panelVideo);

        panelVideoUsersExperimentList.add(splitPaneUserExperimentsVideo, java.awt.BorderLayout.CENTER);

        splitPaneChatOthers.setTopComponent(panelVideoUsersExperimentList);

        panelChat.setLayout(new java.awt.BorderLayout());

        splitPaneChatOthers.setBottomComponent(panelChat);

        splitPaneVertical.setRightComponent(splitPaneChatOthers);

        getContentPane().add(splitPaneVertical, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Preferences16.gif")));
        btnConfig.setBorder(null);
        btnConfig.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnConfig.setEnabled(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel4.add(btnConfig, gridBagConstraints);

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Play16.gif")));
        btnPlay.setBorder(null);
        btnPlay.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnPlay.setEnabled(false);
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel4.add(btnPlay, gridBagConstraints);

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Stop16.gif")));
        btnStop.setBorder(null);
        btnStop.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnStop.setEnabled(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel4.add(btnStop, gridBagConstraints);

        mainProgressBar.setBorder(new javax.swing.border.EtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel4.add(mainProgressBar, gridBagConstraints);

        jLabel1.setText("Apparatus Status: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel4.add(jLabel1, gridBagConstraints);

        lblApparatusStatus.setText("No Status");
        lblApparatusStatus.setBorder(new javax.swing.border.EtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        jPanel4.add(lblApparatusStatus, gridBagConstraints);

        jPanel3.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Laboratory Status: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        jPanel5.add(jLabel3, gridBagConstraints);

        lblLabStatus.setText("Disconnected");
        lblLabStatus.setToolTipText("disconnected");
        lblLabStatus.setBorder(new javax.swing.border.EtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 100.0;
        jPanel5.add(lblLabStatus, gridBagConstraints);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/RecLogo2Gray.png")));
        jPanel5.add(jLabel4, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel5);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        labMenu.setMnemonic('L');
        labMenu.setText("Laboratory");
        connectMenuItem.setText("Connect");
        connectMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif")));
        connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectMenuItemActionPerformed(evt);
            }
        });

        labMenu.add(connectMenuItem);

        autoPlayMenuItem.setText("Auto Play");
        autoPlayMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayMenuItemActionPerformed(evt);
            }
        });

        labMenu.add(autoPlayMenuItem);

        labMenu.add(jSeparator1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        labMenu.add(exitMenuItem);

        menuBar.add(labMenu);

        viewMenu.setMnemonic('V');
        viewMenu.setText("View");
        apparatusListMenuItem.setText("Apparatus List");
        apparatusListMenuItem.setToolTipText("Hide or show the Apparatus List");
        apparatusListMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree16.gif")));
        apparatusListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apparatusListMenuItemActionPerformed(evt);
            }
        });

        viewMenu.add(apparatusListMenuItem);

        usersListMenuItem.setText("Users List");
        usersListMenuItem.setToolTipText("Hide or show the users List");
        usersListMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif")));
        usersListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersListMenuItemActionPerformed(evt);
            }
        });

        viewMenu.add(usersListMenuItem);

        chatMenuItem.setText("Chat");
        chatMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Chat16.gif")));
        chatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatMenuItemActionPerformed(evt);
            }
        });

        viewMenu.add(chatMenuItem);

        videoMenuItem.setText("Video & Audio");
        videoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Movie16.gif")));
        videoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoMenuItemActionPerformed(evt);
            }
        });

        viewMenu.add(videoMenuItem);

        viewMenu.add(jSeparator2);

        toolBarsMenu.setText("ToolBars");
        toolBarMainMenuItem.setText("Laboratory ToolBar");
        toolBarMainMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/toolbar16.gif")));
        toolBarMainMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolBarMainMenuItemActionPerformed(evt);
            }
        });

        toolBarsMenu.add(toolBarMainMenuItem);

        toolBarViewMenuItem.setText("View ToolBar");
        toolBarViewMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/toolbar16.gif")));
        toolBarViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolBarViewMenuItemActionPerformed(evt);
            }
        });

        toolBarsMenu.add(toolBarViewMenuItem);

        viewMenu.add(toolBarsMenu);

        menuBar.add(viewMenu);

        WindowMenu win_menu=new WindowMenu();
        win_menu.setMDIDesktopPane(desktopExperience);
        menuBar.add(win_menu);
        helpMenu.setMnemonic('H');
        helpMenu.setText("Help");
        helpMenu.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        contentMenuItem.setText("Contents");
        contentMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Information16.gif")));
        contentMenuItem.setEnabled(false);
        contentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(contentMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/About16.gif")));
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        menuWebResources.setMnemonic('R');
        menuWebResources.setText("Web Resources");
        menuWebResources.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif")));
        menuWebResources.setEnabled(false);
        helpMenu.add(menuWebResources);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-748)/2, (screenSize.height-530)/2, 748, 530);
    }//GEN-END:initComponents
    
    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contentMenuItemActionPerformed
    {//GEN-HEADEREND:event_contentMenuItemActionPerformed
	if(uiConfig.getHelpURL()!=null)
	{
	    try
	    {
		javax.jnlp.BasicService bs=(javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");
		if(bs!=null)
		    try
		    {
			bs.showDocument(uiConfig.getHelpURL());
		    }catch(Exception e2)
		    {
		    }
		
	    }catch(Exception e)
	    {
	    }
	}
    }//GEN-LAST:event_contentMenuItemActionPerformed
    
    private void labClientBeanApparatusListChanged(com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent evt)//GEN-FIRST:event_labClientBeanApparatusListChanged
    {//GEN-HEADEREND:event_labClientBeanApparatusListChanged
	if(evt!=null)
	{
	    if(evt.getApparatus()!=null)
	    {
		if(getApparatusAutoConnectId()!=null )
		{
		    for(int i=0;i<evt.getApparatus().length;i++)
		    {
			if(evt.getApparatus()[i].getHardwareInfo().getHardwareUniqueID().equals(getApparatusAutoConnectId()))
			{
			    laboratoryApparatusTree1ApparatusSelectionChange(evt.getApparatus()[i]);
			    return;
			}
		    }
		}
		else if(!showedMessageConnectApparatus)
		{
		    Point locationTreeApparatus=labTreeFrame.getLocationOnScreen();
		    locationTreeApparatus.x=locationTreeApparatus.x+labTreeFrame.getWidth()-10;
		    locationTreeApparatus.y=locationTreeApparatus.y+labTreeFrame.getHeight()/2;
		    try
		    {
			java.net.URL url=getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree_experiment.gif");
			showTipWithTimeout(labTreeFrame,"<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>In the Apparatus List <br><img src='"+url.toExternalForm()+"'><br>double click the apparatus<br>you wish to connect to!</font></center></body></html>",5000,locationTreeApparatus);
		    }catch(Exception ignored)
		    {}
		
		    showedMessageConnectApparatus=true;
		}
	    }
	}
	
    }//GEN-LAST:event_labClientBeanApparatusListChanged
    
    private boolean showedMessageConnectApparatus=false;
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aboutMenuItemActionPerformed
    {//GEN-HEADEREND:event_aboutMenuItemActionPerformed
	aboutDialog.setVisible(true);
	aboutDialog.requestFocus();
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    private void labClientBeanApparatusAdded(com.linkare.rec.impl.client.apparatus.Apparatus evt)//GEN-FIRST:event_labClientBeanApparatusAdded
    {//GEN-HEADEREND:event_labClientBeanApparatusAdded
	if(getApparatusAutoConnectId()!=null)
	    if(evt.getHardwareInfo().getHardwareUniqueID().equals(getApparatusAutoConnectId()))
		laboratoryApparatusTree1ApparatusSelectionChange(evt);
    }//GEN-LAST:event_labClientBeanApparatusAdded
    
	private void btnConfigActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnConfigActionPerformed
	{//GEN-HEADEREND:event_btnConfigActionPerformed
	    if(currentCustomizer!=null)
	    {
		if(config==null)
		    config=currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
		
		CustomizerUIUtil.InitCustomizer(currentCustomizer,currentApparatus.getHardwareInfo(),config).addICustomizerListener(this);
	    }
	}//GEN-LAST:event_btnConfigActionPerformed
	
	private void apparatusClientBeanApparatusIncorrectState(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusIncorrectState
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusIncorrectState
	    setApparatusStatus("Incorrect state for the operation...");
	}//GEN-LAST:event_apparatusClientBeanApparatusIncorrectState
	
	
	private void setApparatusStatus(final String message)
	{
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    lblApparatusStatus.setText(message);
		/*}
	    }
	    );*/
	}
	
	private void setLabStatus(final String message)
	{
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    lblLabStatus.setText(message);
		/*}
	    }
	    );*/
	}
	
	private void apparatusClientBeanApparatusStateReseted(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateReseted
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateReseted
	    setApparatusStatus("Reseted!");
	    
	    locked=false;
	    btnPlay.setEnabled(false);
	    btnStop.setEnabled(false);
	    started=false;
	}//GEN-LAST:event_apparatusClientBeanApparatusStateReseted
	
	private void apparatusClientBeanApparatusStateReseting(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateReseting
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateReseting
	    setApparatusStatus("Reseting...");
	}//GEN-LAST:event_apparatusClientBeanApparatusStateReseting
	
	private void apparatusClientBeanApparatusStateStoped(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStoped
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStoped
	    setApparatusStatus("Stoped!");
	    locked=false;
	    started=false;
	}//GEN-LAST:event_apparatusClientBeanApparatusStateStoped
	
	private void apparatusClientBeanApparatusStateStoping(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStoping
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStoping
	    setApparatusStatus("Stoping...");
	    btnPlay.setEnabled(false);
	    btnStop.setEnabled(false);
	}//GEN-LAST:event_apparatusClientBeanApparatusStateStoping
	
	private void apparatusClientBeanApparatusStateStarted(com.linkare.rec.impl.wrappers.DataProducerWrapper evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStarted
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStarted
	    
	    started=true;
	    ICustomizer customizer=CustomizerUIUtil.loadCustomizer(apparatusClientBean.getApparatus().getHardwareInfo().getURLCustomizerClass());
	    ExpHistory expHistory=new ExpHistory(this,evt,apparatusClientBean.getApparatus().getHardwareInfo().getHardwareUniqueID(),apparatusClientBean.getApparatus().getHardwareInfo().getFamiliarName(),customizer!=null?customizer.getCustomizerIcon():null);
	    expHistory.setLocallyOwned(locked);
	    
	    expHistoryPanel.addExpHistory(expHistory);
	    
	    if(locked)
	    {
		startExperiment(expHistory);
		btnPlay.setEnabled(false);
		btnStop.setEnabled(true);
	    }
	    setApparatusStatus("Started!");
	    
	}//GEN-LAST:event_apparatusClientBeanApparatusStateStarted
	
	private void apparatusClientBeanApparatusStateStarting(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateStarting
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateStarting
	    setApparatusStatus("Starting...");
	}//GEN-LAST:event_apparatusClientBeanApparatusStateStarting
	
	private void apparatusClientBeanApparatusStateConfigError(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfigError
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfigError
	    setApparatusStatus("Configuration Error...");
	    btnPlay.setEnabled(false);
	    btnStop.setEnabled(false);
	}//GEN-LAST:event_apparatusClientBeanApparatusStateConfigError
	
	private void apparatusClientBeanApparatusStateConfigured(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfigured
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfigured
	    setApparatusStatus("Configured!");
	    if(locked)
	    {
		
		/*SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {*/
		btnPlay.setEnabled(false);
		//btnStop.setEnabled(true);
		    /*}
		});*/
		
		apparatusClientBean.start();
	    }
	}//GEN-LAST:event_apparatusClientBeanApparatusStateConfigured
	
	private void apparatusClientBeanApparatusStateConfiguring(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateConfiguring
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateConfiguring
	    setApparatusStatus("Configuring...");
	}//GEN-LAST:event_apparatusClientBeanApparatusStateConfiguring
	
	private void apparatusClientBeanApparatusStateUnknow(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusStateUnknow
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusStateUnknow
	    setApparatusStatus("Unknown State...");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    //btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	    
	}//GEN-LAST:event_apparatusClientBeanApparatusStateUnknow
	
	private void apparatusClientBeanApparatusNotOwner(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotOwner
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusNotOwner
	    setApparatusStatus("You're not the current owner... Please wait your turn!");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    //btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	    
	}//GEN-LAST:event_apparatusClientBeanApparatusNotOwner
	
	private void apparatusClientBeanApparatusLocked(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusLocked
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusLocked
	    
	    setApparatusStatus("Locked!");
	    locked=true;
	    if(config==null)
		config=currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
	    
	    apparatusClientBean.configure(config);
	}//GEN-LAST:event_apparatusClientBeanApparatusLocked
	
	private boolean locktried=false;
	java.util.Timer timerLock=new java.util.Timer();
	java.util.TimerTask lockerTask=null;
	private boolean lockable=false;
	private void apparatusClientBeanApparatusLockable(long evt)//GEN-FIRST:event_apparatusClientBeanApparatusLockable
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusLockable
	    
	    
	    if(lockerTask!=null)
	    {
		lockerTask.cancel();
		lockerTask=null;
	    }
	    
	    if(customize_done_once)
	    {
		setApparatusStatus("Lockable for "+evt/1000+"s !");
		
	    }
	    
	    mainProgressBar.setMaximum((int)(evt/1000));
	    mainProgressBar.setMinimum(0);
	    mainProgressBar.setValue(mainProgressBar.getMinimum());
	    mainProgressBar.setIndeterminate(false);
	    
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    if(customize_done_once)
		btnPlay.setEnabled(true);
		/*}
	    });*/
	    
	    /*}*/
	    
	    //started=true;
	    //btnConfig.setEnabled(true);
	    //btnStop.setEnabled(false);
	    
	    locktried=false;
	    final int counterLockSecsStart=(int)(evt/1000);
	    lockable=true;
	    
	    timerLock.scheduleAtFixedRate(
	    (lockerTask=new java.util.TimerTask()
	    {
		private int counterLockSecs=counterLockSecsStart;
		public void run()
		{
		    if(locktried)
		    {
			cancel();
			
			if(customize_done_once)
			    mainProgressBar.setValue(mainProgressBar.getMinimum());
			
			lockerTask=null;
			
			lockable=false;
		    }
		    else
		    {
			if(counterLockSecs==0)
			{
			    cancel();
			    lockable=false;
			    lockerTask=null;
			    if(customize_done_once)
			    {
				mainProgressBar.setValue(mainProgressBar.getMinimum());
				setApparatusStatus("You didn't lock it!");
			    }
			    try
			    {
				if(started)
				    /*SwingUtilities.invokeLater(new Runnable()
				    {
					public void run()
					{*/
				    btnStop.setEnabled(false);
					/*}
				    });*/
				else
				    /*SwingUtilities.invokeLater(new Runnable()
				    {
					public void run()
					{*/
				    if(customize_done_once)
					btnPlay.setEnabled(false);
					/*}
				    });*/
			    }catch(Exception ignored)
			    {}
			}
			else
			{
			    if(customize_done_once)
			    {
				setApparatusStatus("Lockable for "+counterLockSecs+"s !");
				mainProgressBar.setValue(counterLockSecs);
				/*SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {*/
				btnPlay.setEnabled(true);
				    /*}
				});*/
			    }
			}
			counterLockSecs--;
		    }
		}
	    }),0,1000);
	    
	    if(checkAutoPlay.isSelected() && customize_done_once)
	    {
		checkAutoPlay.setSelected(false);
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
			btnPlayActionPerformed(new ActionEvent(btnPlay,1,btnPlay.getActionCommand()));
		    }
		});
	    }
	    
	}//GEN-LAST:event_apparatusClientBeanApparatusLockable
	
	private void apparatusClientBeanApparatusNotRegistered(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotRegistered
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusNotRegistered
	    setApparatusStatus("You're not registered...");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	}//GEN-LAST:event_apparatusClientBeanApparatusNotRegistered
	
	private void apparatusClientBeanApparatusMaxUsers(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusMaxUsers
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusMaxUsers
	    setApparatusStatus("The room is full - Try again later...");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	}//GEN-LAST:event_apparatusClientBeanApparatusMaxUsers
	
	private void apparatusClientBeanApparatusNotAuthorized(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusNotAuthorized
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusNotAuthorized
	    setApparatusStatus("Not Authorized!");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	    loginFrame.setVisible(true);
	}//GEN-LAST:event_apparatusClientBeanApparatusNotAuthorized
	
	private void apparatusClientBeanApparatusUnreachable(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusUnreachable
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusUnreachable
	    setApparatusStatus("Unreachable!");
	}//GEN-LAST:event_apparatusClientBeanApparatusUnreachable
	
	private void apparatusClientBeanApparatusDisconnected(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusDisconnected
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusDisconnected
	    setApparatusStatus("Disconnected!");
	    /*SwingUtilities.invokeLater(new Runnable()
	    {
		public void run()
		{*/
	    btnPlay.setEnabled(false);
	    btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
		/*}
	    });*/
	}//GEN-LAST:event_apparatusClientBeanApparatusDisconnected
	
	private void apparatusClientBeanApparatusDisconnecting(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusDisconnecting
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusDisconnecting
	    if(currentApparatusWebResources!=null)
	    {
		menuWebResources.remove(currentApparatusWebResources);
		currentApparatusWebResources=null;
	    }
	    
	    setApparatusStatus("Disconnecting...");
	}//GEN-LAST:event_apparatusClientBeanApparatusDisconnecting
	
	private void apparatusClientBeanApparatusConnecting(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusConnecting
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusConnecting
	    setApparatusStatus("Connecting...");
	}//GEN-LAST:event_apparatusClientBeanApparatusConnecting
	
	private ICustomizer currentCustomizer=null;
	private boolean customize_done_once=false;
	private boolean showedMessageConfig=false;
	private void apparatusClientBeanApparatusConnected(java.lang.String evt)//GEN-FIRST:event_apparatusClientBeanApparatusConnected
	{//GEN-HEADEREND:event_apparatusClientBeanApparatusConnected
	    setApparatusStatus("Connected...");
	    
	    if(config==null)
		config=currentApparatus.getHardwareInfo().createBaseHardwareAcquisitionConfig();
	    
	    currentCustomizer=CustomizerUIUtil.loadCustomizer(currentApparatus.getHardwareInfo().getURLCustomizerClass());
	    
	    if(currentCustomizer!=null)
	    {
		btnConfig.setEnabled(true);
		if(!showedMessageConfig)
		{
		    Point locationBtnConfig=btnConfig.getLocationOnScreen();
		    locationBtnConfig.x=locationBtnConfig.x+btnConfig.getWidth()-5;
		    locationBtnConfig.y=locationBtnConfig.y-btnConfig.getHeight()+5-80;
		    try
		    {
			java.net.URL url=getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Preferences16.gif");
			showTipWithTimeout(btnConfig,"<html><body bgcolor='#ffffe6' style='padding:5px;'><center><font color='#5a8183'>Click the configure button <br><img src='"+url.toExternalForm()+"'><br>to access the experiment <br>configuration utility!</font></center></body></html>",5000,locationBtnConfig);
		    }catch(Exception e)
		    {
			e.printStackTrace();
		    }
		    
		    showedMessageConfig=true;
		}
		
		
		currentCustomizer.addICustomizerListener(this);
	    }
	    
	    ApparatusConfig appConfig=uiConfig.apparatusConfig.getApparatusConfig(currentApparatus.getHardwareInfo().getHardwareUniqueID());
	    
	    //if(currentApparatusWebResources!=null)
	    //menuWebResources.remove(currentApparatusWebResources);
	    if(appConfig!=null)
		currentApparatusWebResources=appConfig.getWebResourcesMenu();
	    
	    if(currentApparatusWebResources!=null)
	    {
		if(currentCustomizer!=null)
		    currentApparatusWebResources.setIcon(currentCustomizer.getCustomizerIcon());
		
		menuWebResources.add(currentApparatusWebResources);
		menuWebResources.setEnabled(true);
	    }
	    
	}//GEN-LAST:event_apparatusClientBeanApparatusConnected
	
	private JMenu currentApparatusWebResources = null;
	private com.linkare.rec.impl.client.apparatus.Apparatus currentApparatus = null;
	
	private void laboratoryApparatusTree1ApparatusSelectionChange(com.linkare.rec.impl.client.apparatus.Apparatus evt)//GEN-FIRST:event_laboratoryApparatusTree1ApparatusSelectionChange
	{//GEN-HEADEREND:event_laboratoryApparatusTree1ApparatusSelectionChange
	    //if(evt.equals(currentApparatus)) return;
	    
	    /*int result=JOptionPane.showConfirmDialog(this,"Are you sure you want to connect to apparatus " + evt.getHardwareInfo().getFamiliarName() + "?" + (currentApparatus==null?"":"\n\r This will disconnect you from the previous apparatus : " + currentApparatus.getHardwareInfo().getFamiliarName() ) );
	    if(result==JOptionPane.OK_OPTION)
	    {*/
	    if(currentApparatus!=null)
	    {
		apparatusClientBean.disconnect();
	    }
	    
	    btnPlay.setEnabled(false);
	    btnConfig.setEnabled(false);
	    btnStop.setEnabled(false);
	    
	    customize_done_once=false;
	    currentApparatus=evt;
	    currentCustomizer=null;
	    customize_done_once=false;
	    config=null;
	    lockable=false;
	    locktried=false;
	    if(lockerTask!=null)
	    {
		lockerTask.cancel();
		lockerTask=null;
	    }
	    
	    apparatusClientBean.setUser(loginFrame.getUsername());
	    apparatusClientBean.setPassword(loginFrame.getPassword());
	    
	    apparatusClientBean.setApparatus(currentApparatus);
	    
	    if(isEnterApparatusRoom())
	    {
		chatFrame.setChatServer(apparatusClientBean);
		chatFrame.setUser(new com.linkare.rec.impl.client.chat.User(loginFrame.getUsername(),loginFrame.getUsername()));
	    }
	    
	    (new Thread()
	    {
		public void run()
		{
		    apparatusClientBean.connect();
		}
	    }).start();
	    
	    //}
	    
	}//GEN-LAST:event_laboratoryApparatusTree1ApparatusSelectionChange
	
	
	private void setConnected(boolean connected)
	{
	    this.connected=connected;
	    if(connected)
	    {
		connectMenuItem.setText("Disconnect...");
		connectMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16_cuted.gif")));
		btnConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16_cuted.gif")));
		btnConnect.setToolTipText("Disconnect...");
		btnConnect.setEnabled(true);
		connectMenuItem.setEnabled(true);
	    }
	    else
	    {
		connectMenuItem.setText("Connect...");
		connectMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif")));
		btnConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif")));
		btnConnect.setToolTipText("Connect...");
		btnConnect.setEnabled(true);
		connectMenuItem.setEnabled(true);
	    }
	    
	}
	
	private JMenu labWebResourcesMenu=null;
	
	private void labClientBeanLabStatusChanged(int evt)//GEN-FIRST:event_labClientBeanLabStatusChanged
	{//GEN-HEADEREND:event_labClientBeanLabStatusChanged
	    switch(evt)
	    {
		case LabConnectorListener.STATUS_CONNECTING:
		    setLabStatus("Entering the Laboratory...");
		    mainProgressBar.setIndeterminate(true);
		    break;
		case LabConnectorListener.STATUS_CONNECTED:
		    setLabStatus("Entered the Laboratory...");
		    labWebResourcesMenu=uiConfig.labConfiguration.getLabConfig(0).getWebResources().getJMenu();
		    if(labWebResourcesMenu!=null)
		    {
			menuWebResources.add(labWebResourcesMenu);
			menuWebResources.setEnabled(true);
		    }
		    
		    mainProgressBar.setIndeterminate(false);
		    setConnected(true);
		    break;
		case LabConnectorListener.STATUS_DISCONNECTING:
		    setLabStatus("Leaving the Laboratory...");
		    mainProgressBar.setIndeterminate(true);
		    if(labWebResourcesMenu!=null)
		    {
			menuWebResources.remove(labWebResourcesMenu);
			labWebResourcesMenu=null;
		    }
		    
		    break;
		case LabConnectorListener.STATUS_DISCONNECTED:
		    setLabStatus("Out of the Laboratory...");
		    mainProgressBar.setIndeterminate(false);
		    apparatusClientBean.disconnect();
		    setConnected(false);
		    break;
		case LabConnectorListener.STATUS_MAX_USERS:
		    setLabStatus("Laboratory is now full! Try later...");
		    setConnected(false);
		    break;
		case LabConnectorListener.STATUS_NOT_AUTHORIZED:
		    setLabStatus("Username or password incorrect - Try again!");
		    mainProgressBar.setIndeterminate(false);
		    setConnected(false);
		    btnConnectActionPerformed(new ActionEvent(btnConnect,1,btnConnect.getActionCommand()));
		    break;
		case LabConnectorListener.STATUS_NOT_REGISTERED:
		    labClientBean.disconnect();
		    setLabStatus("A problem occured while registering you in the Laboratory Users List - Please try again!");
		    mainProgressBar.setIndeterminate(false);
		    setConnected(false);
		    btnConnectActionPerformed(new ActionEvent(btnConnect,1,btnConnect.getActionCommand()));
		    break;
		case LabConnectorListener.STATUS_UNREACHABLE:
		    labClientBean.disconnect();
		    setLabStatus("A problem occured while entering the Laboratory - Verify your network connections!");
		    mainProgressBar.setIndeterminate(false);
		    setConnected(false);
		    btnConnectActionPerformed(new ActionEvent(btnConnect,1,btnConnect.getActionCommand()));
		    break;
		default:
		    setLabStatus("Laboratory unknow status...");
		    break;
	    }
	}//GEN-LAST:event_labClientBeanLabStatusChanged
	
	private void labTreeFrameInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_labTreeFrameInternalFrameClosing
	{//GEN-HEADEREND:event_labTreeFrameInternalFrameClosing
	    tglBtnApparatusList.setSelected(false);
	    tglBtnApparatusListActionPerformed(new ActionEvent(tglBtnApparatusList,2,tglBtnApparatusList.getActionCommand()));
	}//GEN-LAST:event_labTreeFrameInternalFrameClosing
	
	private void tglBtnApparatusListActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tglBtnApparatusListActionPerformed
	{//GEN-HEADEREND:event_tglBtnApparatusListActionPerformed
	    int verticalSplitLocation=splitPaneVertical.getDividerLocation();
	    if(tglBtnApparatusList.isSelected())
	    {
		baseDeskTopPanel.remove(scrollPaneExperience);
		splitPaneDeskTopLabTree.setRightComponent(scrollPaneExperience);
		baseDeskTopPanel.add(splitPaneDeskTopLabTree);
		
			/*splitPaneChatOthers.setTopComponent(panelVideoUsersExperimentList);
			splitPaneVertical.setRightComponent(splitPaneChatOthers);
			if(splitPaneChatOthers.getLastDividerLocation()==0)
				splitPaneChatOthers.setDividerLocation(chatLastDividerLocation==0?150:chatLastDividerLocation);
			else
				splitPaneChatOthers.setDividerLocation(splitPaneChatOthers.getLastDividerLocation());*/
		
	    }
	    else
	    {
		baseDeskTopPanel.remove(splitPaneDeskTopLabTree);
		splitPaneDeskTopLabTree.remove(scrollPaneExperience);
		baseDeskTopPanel.add(scrollPaneExperience,BorderLayout.CENTER);
		
			/*splitPaneChatOthers.setDividerLocation(0);
			splitPaneVertical.remove(splitPaneChatOthers);
			splitPaneChatOthers.remove(panelVideoUsersExperimentList);
			splitPaneVertical.setRightComponent(panelVideoUsersExperimentList);*/
	    }
	    
	    splitPaneVertical.setDividerLocation(verticalSplitLocation);
	    panelChat.updateUI();
	    repaint();
	}//GEN-LAST:event_tglBtnApparatusListActionPerformed
	
	private void apparatusListMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_apparatusListMenuItemActionPerformed
	{//GEN-HEADEREND:event_apparatusListMenuItemActionPerformed
	    //forward it to the toolbar impl
	    tglBtnApparatusList.setSelected(!tglBtnApparatusList.isSelected());
	    tglBtnApparatusListActionPerformed(evt);
	}//GEN-LAST:event_apparatusListMenuItemActionPerformed
	
	private void autoPlayMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_autoPlayMenuItemActionPerformed
	{//GEN-HEADEREND:event_autoPlayMenuItemActionPerformed
	    checkAutoPlay.setSelected(autoPlayMenuItem.isSelected());
	    if(checkAutoPlay.isSelected() && customize_done_once && lockable)
	    {
		checkAutoPlay.setSelected(false);
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
			btnPlayActionPerformed(new ActionEvent(btnPlay,1,btnPlay.getActionCommand()));
		    }
		});
	    }
	}//GEN-LAST:event_autoPlayMenuItemActionPerformed
	
	private void checkAutoPlayActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkAutoPlayActionPerformed
	{//GEN-HEADEREND:event_checkAutoPlayActionPerformed
	    autoPlayMenuItem.setSelected(checkAutoPlay.isSelected());
	}//GEN-LAST:event_checkAutoPlayActionPerformed
	
	private void connectMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_connectMenuItemActionPerformed
	{//GEN-HEADEREND:event_connectMenuItemActionPerformed
	    btnConnectActionPerformed(evt);
	}//GEN-LAST:event_connectMenuItemActionPerformed
	
	private void toolBarViewMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_toolBarViewMenuItemActionPerformed
	{//GEN-HEADEREND:event_toolBarViewMenuItemActionPerformed
	    toolBarView.setVisible(!toolBarView.isVisible());
	}//GEN-LAST:event_toolBarViewMenuItemActionPerformed
	
	private void tglBtnUsersListActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tglBtnUsersListActionPerformed
	{//GEN-HEADEREND:event_tglBtnUsersListActionPerformed
	    tabCountBefore=tabPaneUserExperiment.getTabCount();
	    
	    if(tglBtnUsersList.isSelected())
		tabPaneUserExperiment.insertTab("Users List",new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif")),scrollPaneUsersList,"Users List",tabPaneUserExperiment.getTabCount()==0?0:1);
	    else
		tabPaneUserExperiment.removeTabAt(tabPaneUserExperiment.indexOfComponent(scrollPaneUsersList));
	    
	    //checkTabUsersListExperimentHistoryShow();
	}//GEN-LAST:event_tglBtnUsersListActionPerformed
	
	private void usersListMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_usersListMenuItemActionPerformed
	{//GEN-HEADEREND:event_usersListMenuItemActionPerformed
	    //forward it to the toolbar impl
	    tglBtnUsersList.setSelected(!tglBtnUsersList.isSelected());
	    tglBtnUsersListActionPerformed(evt);
	}//GEN-LAST:event_usersListMenuItemActionPerformed
	
	private void chatFrameInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_chatFrameInternalFrameClosing
	{//GEN-HEADEREND:event_chatFrameInternalFrameClosing
	    tglBtnChat.setSelected(false);
	    tglBtnChatActionPerformed(new ActionEvent(tglBtnChat,1,tglBtnChat.getActionCommand()));
	}//GEN-LAST:event_chatFrameInternalFrameClosing
	
	private void videoFrameInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_videoFrameInternalFrameClosing
	{//GEN-HEADEREND:event_videoFrameInternalFrameClosing
	    tglBtnVideo.setSelected(false);
	    tglBtnVideoActionPerformed(new ActionEvent(tglBtnVideo,2,tglBtnVideo.getActionCommand()));
	}//GEN-LAST:event_videoFrameInternalFrameClosing
	
	private void toolBarMainMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_toolBarMainMenuItemActionPerformed
	{//GEN-HEADEREND:event_toolBarMainMenuItemActionPerformed
	    toolBarMain.setVisible(!toolBarMain.isVisible());
	}//GEN-LAST:event_toolBarMainMenuItemActionPerformed
	
	private void btnStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStopActionPerformed
	{//GEN-HEADEREND:event_btnStopActionPerformed
	    if(locked)
	    {
		apparatusClientBean.stop();
	    }
	}//GEN-LAST:event_btnStopActionPerformed
	
	private void btnPlayActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPlayActionPerformed
	{//GEN-HEADEREND:event_btnPlayActionPerformed
	    if(currentApparatus!=null)
	    {
		apparatusClientBean.lock();
		btnPlay.setEnabled(false);
		locktried=true;
	    }
	}//GEN-LAST:event_btnPlayActionPerformed
	
	private void loginFramePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_loginFramePropertyChange
	{//GEN-HEADEREND:event_loginFramePropertyChange
	    if(evt==null || evt.getPropertyName()==null) return;
	    
	    if("login".equals(evt.getPropertyName()) && !loginFrame.isCanceled())
	    {
		btnConnect.setEnabled(false);
		connectMenuItem.setEnabled(false);
		labClientBean.setUser(loginFrame.getUsername());
		labClientBean.setPassword(loginFrame.getPassword());
		chatFrame.setUser(new com.linkare.rec.impl.client.chat.User(loginFrame.getUsername(),loginFrame.getUsername()));
		(new Thread()
		{
		    public void run()
		    {
			labClientBean.connect();
		    }
		}).start();
	    }
	}//GEN-LAST:event_loginFramePropertyChange
	
	private void formComponentHidden(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentHidden
	{//GEN-HEADEREND:event_formComponentHidden
	    
	}//GEN-LAST:event_formComponentHidden
	
	
	
	
	private void btnConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnConnectActionPerformed
	{//GEN-HEADEREND:event_btnConnectActionPerformed
	    if(!connected)
		if(!loginFrame.isVisible())
		    loginFrame.setVisible(true);
		else
		    loginFrame.requestFocus();
	    
	    if(connected)
		labClientBean.disconnect();
	    
	}//GEN-LAST:event_btnConnectActionPerformed
	
	private void videoMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_videoMenuItemActionPerformed
	{//GEN-HEADEREND:event_videoMenuItemActionPerformed
	    //forward it to the toolbar impl
	    tglBtnVideo.setSelected(!tglBtnVideo.isSelected());
	    tglBtnVideoActionPerformed(evt);
	}//GEN-LAST:event_videoMenuItemActionPerformed
	
	private void chatMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chatMenuItemActionPerformed
	{//GEN-HEADEREND:event_chatMenuItemActionPerformed
	    //forward it to the toolbar impl
	    tglBtnChat.setSelected(!tglBtnChat.isSelected());
	    tglBtnChatActionPerformed(evt);
	}//GEN-LAST:event_chatMenuItemActionPerformed
	
	
	private void tglBtnVideoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tglBtnVideoActionPerformed
	{//GEN-HEADEREND:event_tglBtnVideoActionPerformed
	    if(tglBtnVideo.isSelected())
	    {
		panelVideoUsersExperimentList.remove(tabPaneUserExperiment);
		panelVideoUsersExperimentList.add(splitPaneUserExperimentsVideo,BorderLayout.CENTER);
		splitPaneUserExperimentsVideo.setTopComponent(tabPaneUserExperiment);
		if(splitPaneUserExperimentsVideo.getLastDividerLocation()==0)
		    splitPaneUserExperimentsVideo.setDividerLocation(150);
		else
		    splitPaneUserExperimentsVideo.setDividerLocation(splitPaneUserExperimentsVideo.getLastDividerLocation());
		
	    }
	    else
	    {
		splitPaneUserExperimentsVideo.setDividerLocation(0);
		splitPaneUserExperimentsVideo.remove(tabPaneUserExperiment);
		panelVideoUsersExperimentList.remove(splitPaneUserExperimentsVideo);
		panelVideoUsersExperimentList.add(tabPaneUserExperiment,BorderLayout.CENTER);
	    }
	    
	    
	    
	    panelVideoUsersExperimentList.updateUI();
	    panelVideo.updateUI();
	    
	}//GEN-LAST:event_tglBtnVideoActionPerformed
	
	private void tglBtnChatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tglBtnChatActionPerformed
	{//GEN-HEADEREND:event_tglBtnChatActionPerformed
	    int verticalSplitLocation=splitPaneVertical.getDividerLocation();
	    if(tglBtnChat.isSelected())
	    {
		splitPaneVertical.remove(panelVideoUsersExperimentList);
		splitPaneChatOthers.setTopComponent(panelVideoUsersExperimentList);
		splitPaneVertical.setRightComponent(splitPaneChatOthers);
		if(splitPaneChatOthers.getLastDividerLocation()==0)
		    splitPaneChatOthers.setDividerLocation(chatLastDividerLocation==0?150:chatLastDividerLocation);
		else
		    splitPaneChatOthers.setDividerLocation(splitPaneChatOthers.getLastDividerLocation());
	    }
	    else
	    {
		splitPaneChatOthers.setDividerLocation(0);
		splitPaneVertical.remove(splitPaneChatOthers);
		splitPaneChatOthers.remove(panelVideoUsersExperimentList);
		splitPaneVertical.setRightComponent(panelVideoUsersExperimentList);
	    }
	    
	    splitPaneVertical.setDividerLocation(verticalSplitLocation);
	    panelChat.updateUI();
	    repaint();
	    
	}//GEN-LAST:event_tglBtnChatActionPerformed
	
	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_exitMenuItemActionPerformed
	ORBBean.getORBBean().killORB();
	System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
	
	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt)
    {//GEN-FIRST:event_exitForm
	ORBBean.getORBBean().killORB();
	System.exit(0);
    }//GEN-LAST:event_exitForm
	
	
	/** Getter for property videoFrameEnabled.
	 * @return Value of property videoFrameEnabled.
	 */
	public boolean isVideoFrameEnabled()
	{
	    return this.videoFrameEnabled;
	}
	
	/** Setter for property videoFrameEnabled.
	 * @param videoFrameEnabled New value of property videoFrameEnabled.
	 */
	public void setVideoFrameEnabled(boolean videoFrameEnabled)
	{
	    this.videoFrameEnabled = videoFrameEnabled;
	    if(!videoFrameEnabled)
	    {
		tglBtnVideo.setSelected(false);
		tglBtnVideoActionPerformed(new ActionEvent(this,1,tglBtnVideo.getActionCommand()));
		tglBtnVideo.setEnabled(false);
		videoMenuItem.setEnabled(false);
	    }
	    else
	    {
		videoMenuItem.setEnabled(true);
		tglBtnVideo.setEnabled(true);
		tglBtnVideo.setSelected(true);
		tglBtnVideoActionPerformed(new ActionEvent(this,1,tglBtnVideo.getActionCommand()));
	    }
	}
	
	private boolean usersListEnabled=true;
	
	public void setUsersListEnabled(boolean usersListEnabled )
	{
	    this.usersListEnabled = usersListEnabled ;
	    if(!usersListEnabled )
	    {
		tglBtnUsersList.setSelected(false);
		tglBtnUsersListActionPerformed(new ActionEvent(this,1,tglBtnUsersList.getActionCommand()));
		tglBtnUsersList.setEnabled(false);
		usersListMenuItem.setEnabled(false);
	    }
	    else
	    {
		usersListMenuItem.setEnabled(true);
		tglBtnUsersList.setEnabled(true);
		tglBtnUsersList.setSelected(true);
		tglBtnUsersListActionPerformed(new ActionEvent(this,1,tglBtnUsersList.getActionCommand()));
	    }
	}
	
	public boolean isUsersListEnabled()
	{
	    return usersListEnabled;
	}
	
	/** Getter for property charFrameEnabled.
	 * @return Value of property charFrameEnabled.
	 */
	public boolean isChatFrameEnabled()
	{
	    return this.chatFrameEnabled;
	}
	
	/** Setter for property charFrameEnabled.
	 * @param charFrameEnabled New value of property charFrameEnabled.
	 */
	public void setChatFrameEnabled(boolean chatFrameEnabled)
	{
	    this.chatFrameEnabled = chatFrameEnabled;
	    if(!chatFrameEnabled)
	    {
		tglBtnChat.setSelected(false);
		tglBtnChatActionPerformed(new ActionEvent(this,1,tglBtnChat.getActionCommand()));
		tglBtnChat.setEnabled(false);
		chatMenuItem.setEnabled(false);
	    }
	    else
	    {
		chatMenuItem.setEnabled(true);
		tglBtnChat.setEnabled(true);
		tglBtnChat.setSelected(true);
		tglBtnChatActionPerformed(new ActionEvent(this,1,tglBtnChat.getActionCommand()));
	    }
	    
	}
	
	public void startExperiment(ExpHistory expHistory)
	{
	    String[] displays=uiConfig.apparatusConfig.getApparatusConfig(expHistory.getApparatusID()).getDisplays();
	    
	    //Object acq_display_listObj=acq_displays.get(expHistory.getApparatusID());
	    
	    
	    //Vector v=null;
	    //if(acq_display_listObj!=null)
	    //{
	    //v=(Vector)acq_display_listObj;
	    //}
	    //else
	    //{
	    //v=new Vector();
	    //v.add("com.linkare.rec.impl.baseUI.DefaultExperimentDataTable");
	    //}
	    if(displays==null)
		displays=new String[]
		{"com.linkare.rec.impl.baseUI.DefaultExperimentDataTable"};
		
		final ExperimentInternalFrame experimentInternalFrame=new ExperimentInternalFrame();
		
	    /*for(int i=0;i<v.size();i++)
	    {
		Object o=v.get(i);
		if(o instanceof String)
		    experimentInternalFrame.addExpDataDisplayBean((String)o);
		if(o instanceof ExpDataDisplay)
		    experimentInternalFrame.addExpDataDisplay((ExpDataDisplay)o);
	    }*/
		
		for(int i=0;i<displays.length;i++)
		{
		    experimentInternalFrame.addExpDataDisplayBean(displays[i]);
		}
		
		
		experimentInternalFrame.setTitle("Experiment " + expHistory.getExpCount() + " ["+ expHistory.getApparatusName() +"]");
		
		experimentInternalFrame.setToolTipText("Experiment " + expHistory.getExpCount() + " ["+ expHistory.getApparatusName() +"]");
		
		desktopExperience.add(experimentInternalFrame);
		
		experimentInternalFrame.setSize(desktopExperience.getSize().width-10,desktopExperience.getSize().height-10);
		
		experimentInternalFrame.setVisible(true);
		
		
		/*try
		{
		    experimentInternalFrame.setMaximum(true);
		}catch(Exception e)
		{
		    e.printStackTrace();
		}*/
		
		try
		{
		    experimentInternalFrame.getExpDataModel().setDpwDataSource(expHistory.getProducerWrapper());
		    
		}catch(MaximumClientsReached e)
		{
		    LoggerUtil.logThrowable(null,e,Logger.getLogger(UI_CLIENT_LOGGER));
		    setApparatusStatus("Failed to connect to Data Ouput");
		    return;
		}catch(Exception e)
		{
		    LoggerUtil.logThrowable(null,e,Logger.getLogger(UI_CLIENT_LOGGER));
		    setApparatusStatus("Failed to connect to Data Ouput");
		    return;
		}
		
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
	    
	    JFrame frame=new JFrame("Experiment [" + config.getFamiliarName() +"] - "+config.getTimeStart()+" Info");
	    frame.setIconImage((new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/hwinfo16.gif"))).getImage());
	    
	    JTextArea tainfo=new JTextArea(config.toString());
	    tainfo.setColumns(40);
	    tainfo.setRows(20);
	    frame.getContentPane().add(new JScrollPane(tainfo));
	    frame.pack();
	    frame.setLocation((getLocation().x+getWidth())/2-frame.getWidth()/2,(getLocation().y+getHeight())/2-frame.getHeight()/2);
	    frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
	    //desktopExperience.add(frame);
	    frame.setVisible(true);
	}
	
	/** Getter for property apparatusListEnabled.
	 * @return Value of property apparatusListEnabled.
	 */
	public boolean isApparatusListEnabled()
	{
	    return this.apparatusListEnabled;
	}
	
	/** Setter for property apparatusListEnabled.
	 * @param apparatusListEnabled New value of property apparatusListEnabled.
	 */
	public void setApparatusListEnabled(boolean apparatusListEnabled)
	{
	    this.apparatusListEnabled = apparatusListEnabled;
	    
	    if(!apparatusListEnabled)
	    {
		tglBtnApparatusList.setSelected(false);
		tglBtnApparatusListActionPerformed(new ActionEvent(this,1,tglBtnApparatusList.getActionCommand()));
		tglBtnApparatusList.setEnabled(false);
		apparatusListMenuItem.setEnabled(false);
	    }
	    else
	    {
		apparatusListMenuItem.setEnabled(true);
		tglBtnApparatusList.setEnabled(true);
		tglBtnApparatusList.setSelected(true);
		tglBtnApparatusListActionPerformed(new ActionEvent(this,1,tglBtnApparatusList.getActionCommand()));
	    }
	    
	    
	    
	}
	
	public void canceled()
	{//silent noop - Customizer canceled...
	}
	
	public void done()
	{
	    config=currentCustomizer.getAcquisitionConfig();
	    customize_done_once=true;
	    
	    if(checkAutoPlay.isSelected() && customize_done_once && lockable)
	    {
		checkAutoPlay.setSelected(false);
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
			btnPlayActionPerformed(new ActionEvent(btnPlay,1,btnPlay.getActionCommand()));
		    }
		});
	    }
	}
	
	/** Getter for property enterApparatusRoom.
	 * @return Value of property enterApparatusRoom.
	 */
	public boolean isEnterApparatusRoom()
	{
	    return this.enterApparatusRoom;
	}
	
	/** Setter for property enterApparatusRoom.
	 * @param enterApparatusRoom New value of property enterApparatusRoom.
	 */
	public void setEnterApparatusRoom(boolean enterApparatusRoom)
	{
	    this.enterApparatusRoom = enterApparatusRoom;
	}
	
	/** Getter for property apparatusAutoConnectId.
	 * @return Value of property apparatusAutoConnectId.
	 */
	public String getApparatusAutoConnectId()
	{
	    return this.apparatusAutoConnectId;
	}
	
	/** Setter for property apparatusAutoConnectId.
	 * @param apparatusAutoConnectId New value of property apparatusAutoConnectId.
	 */
	public void setApparatusAutoConnectId(String apparatusAutoConnectId)
	{
	    this.apparatusAutoConnectId = apparatusAutoConnectId;
	    if(this.apparatusAutoConnectId!=null)
		this.setApparatusListEnabled(false);
	    else
		this.setApparatusListEnabled(true);
	}
	
	/*public void addAcqDisplay(java.lang.String hardwareUniqueId, java.lang.String beanName)
	{
	    Object acq_display_listObj=acq_displays.get(hardwareUniqueId);
	    if(acq_display_listObj!=null)
	    {
		Vector v=(Vector)acq_display_listObj;
		v.add(beanName);
	    }
	    else
	    {
		Vector v=new Vector();
		v.add(beanName);
		acq_displays.put(hardwareUniqueId,v);
	    }
	}*/
	
	/*public void addAcqDisplay(java.lang.String hardwareUniqueId, ExpDataDisplay display)
	{
	    Object acq_display_listObj=acq_displays.get(hardwareUniqueId);
	    if(acq_display_listObj!=null)
	    {
		Vector v=(Vector)acq_display_listObj;
		v.add(display);
	    }
	    else
	    {
		Vector v=new Vector();
		v.add(display);
		acq_displays.put(hardwareUniqueId,v);
	    }
	}*/
	
	
	/*public void removeDataDisplay(java.lang.String hardwareUniqueId, java.lang.String beanName)
	{
	}*/
	
	
	private void showTipWithTimeout(JComponent component,String tooltipText,int timeoutHide,Point location)
	{
	    showTipWindow(component,tooltipText,location);
	    timerPopup=new javax.swing.Timer(timeoutHide,new ActionListener()
	    {
		public void actionPerformed(ActionEvent evt)
		{
		    hideTipWindow();
		}
	    });
	    timerPopup.setRepeats(false);
	    timerPopup.start();
	}
	
	javax.swing.Timer timerPopup=null;
	private void showTipWindow(JComponent component,String tooltipText,Point location)
	{
	    if(component== null || !component.isShowing())
		return;
	    
	    Dimension size;
	    Point screenLocation = component.getLocationOnScreen();
	    Rectangle sBounds = component.getBounds();
	    
	    // Just to be paranoid
	    hideTipWindow();
	    
	    JToolTip tip = component.createToolTip();
	    tip.setTipText(tooltipText);
	    size = tip.getPreferredSize();
	    
	    Rectangle popupRect = new Rectangle();
	    popupRect.setBounds(location.x,location.y,
	    size.width,size.height);
	    
	    PopupFactory popupFactory = PopupFactory.getSharedInstance();
	    tipWindow = popupFactory.getPopup(component, tip,location.x,location.y);
	    tipWindow.show();
	    
	}
	
	private Popup tipWindow =null;
	private void hideTipWindow()
	{
	    if(tipWindow!=null)
	    {
		tipWindow.hide();
		tipWindow=null;
	    }
	    if(timerPopup!=null)
		timerPopup.stop();
	}
	
	
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem apparatusListMenuItem;
    private javax.swing.JCheckBoxMenuItem autoPlayMenuItem;
    private javax.swing.JPanel baseDeskTopPanel;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnStop;
    private javax.swing.JMenuItem chatMenuItem;
    private javax.swing.JCheckBox checkAutoPlay;
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelToolBars;
    private javax.swing.JPanel jPaneltoolBarsAndImage;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JMenu labMenu;
    private javax.swing.JInternalFrame labTreeFrame;
    private javax.swing.JLabel lblApparatusStatus;
    private javax.swing.JLabel lblLabStatus;
    private javax.swing.JLabel lblimplOutSidePub;
    private javax.swing.JProgressBar mainProgressBar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuWebResources;
    private javax.swing.JPanel panelChat;
    private javax.swing.JPanel panelLabTree;
    private javax.swing.JPanel panelVideo;
    private javax.swing.JPanel panelVideoUsersExperimentList;
    private javax.swing.JScrollPane scrollPaneExperience;
    private javax.swing.JScrollPane scrollPaneUsersList;
    private javax.swing.JSplitPane splitPaneChatOthers;
    private javax.swing.JSplitPane splitPaneDeskTopLabTree;
    private javax.swing.JSplitPane splitPaneUserExperimentsVideo;
    private javax.swing.JSplitPane splitPaneVertical;
    private javax.swing.JTabbedPane tabPaneUserExperiment;
    private javax.swing.JTable tableUsers;
    private javax.swing.JToggleButton tglBtnApparatusList;
    private javax.swing.JToggleButton tglBtnChat;
    private javax.swing.JToggleButton tglBtnUsersList;
    private javax.swing.JToggleButton tglBtnVideo;
    private javax.swing.JToolBar toolBarMain;
    private javax.swing.JMenuItem toolBarMainMenuItem;
    private javax.swing.JToolBar toolBarView;
    private javax.swing.JMenuItem toolBarViewMenuItem;
    private javax.swing.JMenu toolBarsMenu;
    private javax.swing.JMenuItem usersListMenuItem;
    private javax.swing.JInternalFrame videoFrame;
    private javax.swing.JMenuItem videoMenuItem;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
	
	/** Holds value of property videoFrameEnabled. */
	private boolean videoFrameEnabled;
	
	/** Holds value of property charFrameEnabled. */
	private boolean chatFrameEnabled;
	
	/** Holds value of property apparatusListEnabled. */
	private boolean apparatusListEnabled;
	
	/** Holds value of property enterApparatusRoom. */
	private boolean enterApparatusRoom;
	
	/** Holds value of property apparatusAutoConnectId. */
	private String apparatusAutoConnectId;
	
}
