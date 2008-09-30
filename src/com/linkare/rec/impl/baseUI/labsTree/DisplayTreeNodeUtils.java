/*
 * DisplayTreeNodeUtils.java
 *
 * Created on August 11, 2004, 1:18 AM
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.Lab;

public abstract class DisplayTreeNodeUtils
{
    /**Disable all the nodes in a lab*/
    public static void disableLab(Lab lab)
    {
        if(lab == null)
            return;
        
        lab.setEnabled(false);
        Apparatus[] apps = lab.getApparatus();
        for(int i=0; i < apps.length; i++)
        {
            apps[i].setEnabled(false);
            apps[i].setConnected(false); 
            
            Display[] displays = apps[i].getDisplay();
            for(int d = 0; d<displays.length; d++)
            {
                if(!displays[d].getOfflineCapable())
                    displays[d].setEnabled(false);
            }
            
            DefaultAcquisitionConfig[] dfacq = apps[i].getDefaultAcquisitionConfig();
            for(int a = 0; a<dfacq.length; a++)
            {
                dfacq[a].setEnabled(false);
            }
        }
    }
    
    /**Disable all the nodes in a lab*/
    public static void disableAllApparatus(Lab lab)
    {
        if(lab == null)
            return;
        
        Apparatus[] apps = lab.getApparatus();
        for(int i=0; i < apps.length; i++)
        {
            Display[] displays = apps[i].getDisplay();
            for(int d = 0; d<displays.length; d++)
            {
                if(!displays[d].getOfflineCapable())
                    displays[d].setEnabled(false);
            }
            
            DefaultAcquisitionConfig[] dfacq = apps[i].getDefaultAcquisitionConfig();
            for(int a = 0; a<dfacq.length; a++)
            {
                dfacq[a].setEnabled(false);
            }
            
            apps[i].setEnabled(false);                        
            apps[i].setConnected(false); 
        }
    }
    
    public static void enableAllApparatusContents(Apparatus app)
    {
        com.linkare.rec.impl.baseUI.config.Display[] dps = app.getDisplay();
        app.setConnected(true);
        for(int j=0; j<dps.length; j++)
        {
            dps[j].setEnabled(true);
        }
        
        com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] dftacq = app.getDefaultAcquisitionConfig();
        for(int z=0; z<dftacq.length; z++)
        {
            dftacq[z].setEnabled(true);
        }
    }
    
    public static void disableAllApparatusContents(Apparatus app)
    {
        com.linkare.rec.impl.baseUI.config.Display[] dps = app.getDisplay();
        app.setConnected(false);
        for(int j=0; j<dps.length; j++)
        {
            dps[j].setEnabled(false);
        }
        
        com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] dftacq = app.getDefaultAcquisitionConfig();
        for(int z=0; z<dftacq.length; z++)
        {
            dftacq[z].setEnabled(false);
        }
    }
}
