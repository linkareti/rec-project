/*
 * MenuBar.java
 *
 * Created on July 30, 2004, 5:29 PM
 */

package com.linkare.rec.impl.baseUI.control;

/**
 *
 * @author André Neto - LEFT - IST
 */


import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.Lab;
import com.linkare.rec.impl.baseUI.config.WebResource;
import com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent;
import com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent;
import com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class MenuBar extends javax.swing.JMenuBar
{
    /** Creates a new instance of MenuBar */
    public MenuBar()
    {
        super();
        initComponents();
    }
    
    private void initComponents()
    {
        jLabel1 = new javax.swing.JLabel();
        jMenuLab = new javax.swing.JMenu();
        jMenuItemConnect = new javax.swing.JMenuItem();
        jCheckBoxAutoPlay = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jMenuItemApparatus = new javax.swing.JMenuItem();
        jMenuItemUsers = new javax.swing.JMenuItem();
        jMenuItemChat = new javax.swing.JMenuItem();
        jMenuItemVideo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuToolBars = new javax.swing.JMenu();
        jMenuItemToolLab = new javax.swing.JMenuItem();
        jMenuItemToolView = new javax.swing.JMenuItem();
        jMenuWindow = new javax.swing.JMenu();
        jMenuItemCascade = new javax.swing.JMenuItem();
        jMenuItemTile = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemContents = new javax.swing.JMenuItem();
        jMenuItemAbout = new javax.swing.JMenuItem();
        
        
        jMenuLab.setMnemonic(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.mnemo.lab", "L").charAt(0));
        jMenuLab.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.lab", "Laboratory"));
        
        jMenuItemConnect.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.connect", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/earth16.gif"))));
        jMenuItemConnect.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.connect", "Connect"));
        jMenuItemConnect.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.connect", "Connect to the laboratory"));
        jMenuItemConnect.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "connect", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuLab.add(jMenuItemConnect);
        
        jCheckBoxAutoPlay.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.autoplay", "Auto Play"));
        jCheckBoxAutoPlay.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "autoplay", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuLab.add(jCheckBoxAutoPlay);
        
        jMenuLab.add(jSeparator1);
        
        jMenuItemExit.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.exit", "Exit"));
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "exit", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuLab.add(jMenuItemExit);
        
        add(jMenuLab);
        
        jMenuView.setMnemonic(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.mnemo.view", "V").charAt(0));
        jMenuView.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.view", "View"));
        
        jMenuItemApparatus.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.tree", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/tree16.gif"))));
        jMenuItemApparatus.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.apparatuslist", "Apparatus List"));
        jMenuItemApparatus.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.apparatus", "Toggle apparatus list"));
        jMenuItemApparatus.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "tree", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuView.add(jMenuItemApparatus);
        
        jMenuItemUsers.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.users", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/UserList16_2.gif"))));
        jMenuItemUsers.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.userslist", "UsersList"));
        jMenuItemUsers.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.userslist", "Toggle users list"));
        jMenuItemUsers.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "users", Boolean.FALSE, Boolean.TRUE));
            }
        });
        
        jMenuView.add(jMenuItemUsers);
        
        jMenuItemChat.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.chat", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Chat16.gif"))));
        jMenuItemChat.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.chat", "Chat"));
        jMenuItemChat.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.userslist", "Toggles users list"));
        jMenuItemChat.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "chat", Boolean.FALSE, Boolean.TRUE));
            }
        });
        
        jMenuView.add(jMenuItemChat);
        
        jMenuItemVideo.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.movie", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Movie16.gif"))));
        jMenuItemVideo.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.video", "Video & audio"));
        jMenuItemVideo.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.video", "Toggle video"));
        jMenuItemVideo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "video", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuView.add(jMenuItemVideo);
        
        jMenuView.add(jSeparator2);
        
        jMenuToolBars.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.toolbars", "Toolbars"));
        
        jMenuItemToolLab.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.toolbar", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/toolbar16.gif"))));
        jMenuItemToolLab.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.toolbarLab", "Laboratory Toolbar"));
        jMenuItemToolLab.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "ToolbarLab", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuToolBars.add(jMenuItemToolLab);
        
        jMenuItemToolView.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.toolbar", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/toolbar16.gif"))));
        jMenuItemToolView.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.toolbarView", "View Toolbar"));
        jMenuItemToolView.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "ToolbarView", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuToolBars.add(jMenuItemToolView);
        
        jMenuView.add(jMenuToolBars);
        
        add(jMenuView);
        
        jMenuWindow.setMnemonic(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.mnemo.window", "W").charAt(0));
        jMenuWindow.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.window", "Window"));
        
        jMenuItemCascade.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.cascade", "Cascade"));
        jMenuItemCascade.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "cascade", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuWindow.add(jMenuItemCascade);
        
        jMenuItemTile.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.tile", "Tile"));
        jMenuItemTile.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "tile", Boolean.FALSE, Boolean.TRUE));
            }
        });
        
        jMenuWindow.add(jMenuItemTile);
        
        add(jMenuWindow);
        
        jMenuHelp.setMnemonic(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.mnemo.help", "H").charAt(0));
        jMenuHelp.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.help", "Help"));
        
        jMenuItemContents.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.information", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Information16.gif"))));
        jMenuItemContents.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.contents", "Contents"));
        jMenuItemContents.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "contents", Boolean.FALSE, Boolean.TRUE));
            }
        });
        jMenuHelp.add(jMenuItemContents);
        
        jMenuItemAbout.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.about", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/About16.gif"))));
        jMenuItemAbout.setText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.about", "About"));
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                firePropertyChangeListenerPropertyChange(new PropertyChangeEvent(this, "about", Boolean.FALSE, Boolean.TRUE));
            }
        });
        
        jMenuHelp.add(jMenuItemAbout);
        
        add(jMenuHelp);
    }
    
    private JMenu jMenuApp = null;
    public void addApparatusMenu(Lab lab, Apparatus apparatusConfig)
    {
        if(jMenuApp != null)
            remove(jMenuApp);
        remove(jMenuHelp);
        
        jMenuApp = new JMenu(apparatusConfig.getText());
        
        final Lab labf = lab;
        final Apparatus appf = apparatusConfig;
        
        
        WebResource[] wr = apparatusConfig.getWebResource();
        for(int wri = 0; wri < wr.length; wri++)
        {
            JMenuItem writem = new JMenuItem();
            if(wr[wri].getText() != null)
                writem.setText(wr[wri].getText());
            if(wr[wri].getIcon() != null)
                writem.setIcon(wr[wri].getIcon());
            
            final WebResource wrf = wr[wri];
            
            writem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    fireTreeSelectionChangeListenerWebResourceSelectionChange(new WebResourceSelectionEvent(this, wrf, appf, labf));
                }
            });
            
            jMenuApp.add(writem);
        }
        jMenuApp.add(new javax.swing.JSeparator());
        
        DefaultAcquisitionConfig[] dfacq = apparatusConfig.getDefaultAcquisitionConfig();
        for(int dfacqi = 0; dfacqi < dfacq.length; dfacqi++)
        {
            JMenuItem dfacqitem = new JMenuItem();
            if(dfacq[dfacqi].getText() != null)
                dfacqitem.setText(dfacq[dfacqi].getText());
            if(dfacq[dfacqi].getIcon() != null)
                dfacqitem.setIcon(dfacq[dfacqi].getIcon());
            
            final DefaultAcquisitionConfig dfacqf = dfacq[dfacqi];
            
            dfacqitem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    fireTreeSelectionChangeListenerDefaultConfigSelectionChange(new DefaultConfigSelectionEvent(this, dfacqf, appf, labf));
                }
            });
            
            
            jMenuApp.add(dfacqitem);
        }
        jMenuApp.add(new javax.swing.JSeparator());
        
        Display[] dsp = apparatusConfig.getDisplay();
        for(int dspi = 0; dspi < dsp.length; dspi++)
        {
            JCheckBoxMenuItem dspitem = new JCheckBoxMenuItem();
            if(dsp[dspi].getText() != null)
                dspitem.setText(dsp[dspi].getText());
            if(dsp[dspi].getIcon() != null)
                dspitem.setIcon(dsp[dspi].getIcon());
            
            dspitem.setSelected(dsp[dspi].isSelected());
            
            final JCheckBoxMenuItem dspitemf = dspitem;
            final Display dspf = dsp[dspi];
            
            dspitem.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    dspf.setSelected(dspitemf.isSelected());
                }
            });
            
            
            dsp[dspi].addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener()
            {
                public void propertyChange(java.beans.PropertyChangeEvent event)
                {
                    if(event.getPropertyName().equals("selected"))
                    {
                        if(event.getNewValue().toString().equals("true"))
                            dspitemf.setSelected(true);
                        else
                            dspitemf.setSelected(false);
                    }
                }
            });
            
            jMenuApp.add(dspitem);
            
            if(dspf.getOfflineCapable())
            {
                JMenuItem dspItemOffline = new JMenuItem();
                if(dspf.getText() != null)
                    dspItemOffline.setText(dspf.getText());
                if(dspf.getIcon() != null)
                    dspItemOffline.setIcon(dspf.getIcon());
                
                dspItemOffline.addActionListener(new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        fireTreeSelectionChangeListenerDisplaySelectionChange(new DisplaySelectionEvent(this, dspf, appf, labf));                        
                    }
                });      
                jMenuApp.add(dspItemOffline);
            }                        
        }
        jMenuApp.add(new javax.swing.JSeparator());
        
        add(jMenuApp);
        add(jMenuHelp);
        validate();
    }
    
    public void removeApparatus()
    {
        remove(jMenuHelp);
        if(jMenuApp != null)
            remove(jMenuApp);
        jMenuApp = null;
        add(jMenuHelp);
        validate();
    }
    
    /**
     * Registers PropertyChangeListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(java.beans.PropertyChangeListener.class, listener);
    }
    
    /**
     * Removes PropertyChangeListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        listenerList.remove(java.beans.PropertyChangeListener.class, listener);
    }
    
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void firePropertyChangeListenerPropertyChange(java.beans.PropertyChangeEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==java.beans.PropertyChangeListener.class)
            {
                ((java.beans.PropertyChangeListener)listeners[i+1]).propertyChange(event);
            }
        }
    }
    
    /**
     * Getter for property enableConnect.
     * @return Value of property enableConnect.
     */
    public boolean isEnableConnect()
    {
        return this.enableConnect;
    }
    
    /**
     * Setter for property enableConnect.
     * @param enableConnect New value of property enableConnect.
     */
    public void setEnableConnect(boolean enableConnect)
    {
        this.enableConnect = enableConnect;
        jMenuItemConnect.setEnabled(enableConnect);
    }
    
    /**
     * Getter for property enableAbout.
     * @return Value of property enableAbout.
     */
    public boolean isEnableAbout()
    {
        return this.enableAbout;
    }
    
    /**
     * Setter for property enableAbout.
     * @param enableAbout New value of property enableAbout.
     */
    public void setEnableAbout(boolean enableAbout)
    {
        this.enableAbout = enableAbout;
        jMenuItemAbout.setEnabled(enableAbout);
    }
    
    /**
     * Getter for property enableApparatus.
     * @return Value of property enableApparatus.
     */
    public boolean isEnableApparatus()
    {
        return this.enableApparatus;
    }
    
    /**
     * Setter for property enableApparatus.
     * @param enableApparatus New value of property enableApparatus.
     */
    public void setEnableApparatus(boolean enableApparatus)
    {
        this.enableApparatus = enableApparatus;
        jMenuItemApparatus.setEnabled(enableApparatus);
    }
    
    /**
     * Getter for property enableCascade.
     * @return Value of property enableCascade.
     */
    public boolean isEnableCascade()
    {
        return this.enableCascade;
    }
    
    /**
     * Setter for property enableCascade.
     * @param enableCascade New value of property enableCascade.
     */
    public void setEnableCascade(boolean enableCascade)
    {
        this.enableCascade = enableCascade;
        jMenuItemCascade.setEnabled(enableCascade);
    }
    
    /**
     * Getter for property enableChat.
     * @return Value of property enableChat.
     */
    public boolean isEnableChat()
    {
        return this.enableChat;
    }
    
    /**
     * Setter for property enableChat.
     * @param enableChat New value of property enableChat.
     */
    public void setEnableChat(boolean enableChat)
    {
        this.enableChat = enableChat;
        jMenuItemChat.setEnabled(enableChat);
    }
    
    /**
     * Getter for property enableContents.
     * @return Value of property enableContents.
     */
    public boolean isEnableContents()
    {
        return this.enableContents;
    }
    
    /**
     * Setter for property enableContents.
     * @param enableContents New value of property enableContents.
     */
    public void setEnableContents(boolean enableContents)
    {
        this.enableContents = enableContents;
        jMenuItemContents.setEnabled(enableContents);
    }
    
    /**
     * Getter for property enableExit.
     * @return Value of property enableExit.
     */
    public boolean isEnableExit()
    {
        return this.enableExit;
    }
    
    /**
     * Setter for property enableExit.
     * @param enableExit New value of property enableExit.
     */
    public void setEnableExit(boolean enableExit)
    {
        this.enableExit = enableExit;
        jMenuItemExit.setEnabled(enableExit);
    }
    
    /**
     * Getter for property enableTile.
     * @return Value of property enableTile.
     */
    public boolean isEnableTile()
    {
        return this.enableTile;
    }
    
    /**
     * Setter for property enableTile.
     * @param enableTile New value of property enableTile.
     */
    public void setEnableTile(boolean enableTile)
    {
        this.enableTile = enableTile;
        jMenuItemTile.setEnabled(enableTile);
    }
    
    /**
     * Getter for property enableToolLab.
     * @return Value of property enableToolLab.
     */
    public boolean isEnableToolLab()
    {
        return enableToolLab;
    }
    
    /**
     * Setter for property enableToolLab.
     * @param enableToolLab New value of property enableToolLab.
     */
    public void setEnableToolLab(boolean enableToolLab)
    {
        this.enableToolLab = enableToolLab;
        jMenuItemToolLab.setEnabled(enableToolLab);
    }
    
    /**
     * Getter for property enableToolView.
     * @return Value of property enableToolView.
     */
    public boolean isEnableToolView()
    {
        return this.enableToolView;
    }
    
    /**
     * Setter for property enableToolView.
     * @param enableToolView New value of property enableToolView.
     */
    public void setEnableToolView(boolean enableToolView)
    {
        this.enableToolView = enableToolView;
        jMenuItemToolView.setEnabled(true);
    }
    
    /**
     * Getter for property enableUsers.
     * @return Value of property enableUsers.
     */
    public boolean isEnableUsers()
    {
        return this.enableUsers;
    }
    
    /**
     * Setter for property enableUsers.
     * @param enableUsers New value of property enableUsers.
     */
    public void setEnableUsers(boolean enableUsers)
    {
        this.enableUsers = enableUsers;
        jMenuItemUsers.setEnabled(true);
    }
    
    /**
     * Getter for property enableVideo.
     * @return Value of property enableVideo.
     */
    public boolean isEnableVideo()
    {
        return this.enableVideo;
    }
    
    /**
     * Setter for property enableVideo.
     * @param enableVideo New value of property enableVideo.
     */
    public void setEnableVideo(boolean enableVideo)
    {
        this.enableVideo = enableVideo;
        jMenuItemVideo.setEnabled(enableVideo);
    }
    
    /**
     * Getter for property enableAutoPlay.
     * @return Value of property enableAutoPlay.
     */
    public boolean isEnableAutoPlay()
    {
        return this.enableAutoPlay;
    }
    
    /**
     * Setter for property enableAutoPlay.
     * @param enableAutoPlay New value of property enableAutoPlay.
     */
    public void setEnableAutoPlay(boolean enableAutoPlay)
    {
        this.enableAutoPlay = enableAutoPlay;
        jCheckBoxAutoPlay.setEnabled(enableAutoPlay);
    }
    
    /**
     * Getter for property selectAutoPlay.
     * @return Value of property selectAutoPlay.
     */
    public boolean isSelectAutoPlay()
    {
        return this.selectAutoPlay;
    }
    
    /**
     * Setter for property selectAutoPlay.
     * @param selectAutoPlay New value of property selectAutoPlay.
     */
    public void setSelectAutoPlay(boolean selectAutoPlay)
    {
        this.selectAutoPlay = selectAutoPlay;
        jCheckBoxAutoPlay.setSelected(selectAutoPlay);
    }
    
    /**
     * Registers TreeSelectionChangeListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addTreeSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
    {
        if (listenerListTree == null )
        {
            listenerListTree = new javax.swing.event.EventListenerList();
        }
        listenerListTree.add(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
    }
    
    /**
     * Removes TreeSelectionChangeListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeTreeSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
    {
        listenerListTree.remove(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerDefaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent event)
    {
        if (listenerListTree == null) return;
        Object[] listeners = listenerListTree.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).defaultConfigSelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerDisplaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent event)
    {
        if (listenerListTree == null) return;
        Object[] listeners = listenerListTree.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).displaySelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerWebResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent event)
    {
        if (listenerListTree == null) return;
        Object[] listeners = listenerListTree.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).webResourceSelectionChange(event);
            }
        }
    }
    
    
    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemApparatus;
    private javax.swing.JMenuItem jMenuItemCascade;
    private javax.swing.JMenuItem jMenuItemChat;
    private javax.swing.JMenuItem jMenuItemConnect;
    private javax.swing.JMenuItem jMenuItemContents;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemTile;
    private javax.swing.JMenuItem jMenuItemToolLab;
    private javax.swing.JMenuItem jMenuItemToolView;
    private javax.swing.JMenuItem jMenuItemUsers;
    private javax.swing.JMenuItem jMenuItemVideo;
    private javax.swing.JMenu jMenuLab;
    private javax.swing.JMenu jMenuToolBars;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JMenu jMenuWindow;
    private javax.swing.JCheckBoxMenuItem jCheckBoxAutoPlay;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    
    /**
     * Utility field used by event firing mechanism.
     */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    /**
     * Utility field used by event firing mechanism.
     */
    private javax.swing.event.EventListenerList listenerListTree =  null;
    
    /**
     * Holds value of property enableConnect.
     */
    private boolean enableConnect = true;
    
    /**
     * Holds value of property enableAbout.
     */
    private boolean enableAbout = true;
    
    /**
     * Holds value of property enableApparatus.
     */
    private boolean enableApparatus = true;
    
    /**
     * Holds value of property enableCascade.
     */
    private boolean enableCascade = true;
    
    /**
     * Holds value of property enableChat.
     */
    private boolean enableChat = true;
    
    /**
     * Holds value of property enableContents.
     */
    private boolean enableContents = true;
    
    /**
     * Holds value of property enableExit.
     */
    private boolean enableExit = true;
    
    /**
     * Holds value of property enableTile.
     */
    private boolean enableTile = true;
    
    /**
     * Holds value of property enableToolView.
     */
    private boolean enableToolView = true;
    
    /**
     * Holds value of property enableToolLab.
     */
    private boolean enableToolLab = true;
    
    /**
     * Holds value of property enableUsers.
     */
    private boolean enableUsers = true;
    
    /**
     * Holds value of property enableVideo.
     */
    private boolean enableVideo = true;
    
    /**
     * Holds value of property enableAutoPlay.
     */
    private boolean enableAutoPlay = true;
    
    /**
     * Holds value of property selectAutoPlay.
     */
    private boolean selectAutoPlay = false;
    
    // End of variables declaration
}
