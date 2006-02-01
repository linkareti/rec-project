/*
 * DefaultDisplayFactory.java
 *
 * Created on August 2, 2004, 5:07 PM
 */

package com.linkare.rec.impl.baseUI.display;

/**
 *
 * @author  andre
 */

import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;

public class DefaultDisplayFactory extends AbstractDisplayFactory
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
    
    
    /** Creates a new instance of DefaultDisplayFactory */
    public DefaultDisplayFactory()
    {
    }
    
    public void destroyDisplays()
    {//Big silent noop
    }
    
    public com.linkare.rec.impl.client.experiment.ExpDataDisplay[] getDisplays()
    {
        Display[] displays = getInitDisplays();
        java.util.Vector tempDisplays = new java.util.Vector(displays.length);
        
        for(int i=0; i<displays.length; i++)
        {
            try
            {
                String beanName = ReCResourceBundle.findStringOrDefault(displays[i].getClassLocationBundleKey(), null);
                if(beanName == null)
                    continue;
                Object dataDisplayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), beanName.trim());
                if(java.beans.Beans.isInstanceOf(dataDisplayTemp, ExpDataDisplay.class))
                    tempDisplays.add(new ExpDataDisplayTreeIconAndName((ExpDataDisplay)dataDisplayTemp, displays[i]));
            }
            catch(Throwable e)
            {
                LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(UI_CLIENT_LOGGER));
            }
        }
        
        tempDisplays.trimToSize();
        return (ExpDataDisplay[])tempDisplays.toArray(new ExpDataDisplay[0]);
    }
    
    //Hacked... this is the way I found to, without changing to source code, set
    //tree image and label in the experiment tabs... i think this is the logic way..
    private class ExpDataDisplayTreeIconAndName implements ExpDataDisplay
    {
        private ExpDataDisplay expDisplay = null;
        private Display treeDisplay = null;
        
        public  ExpDataDisplayTreeIconAndName(ExpDataDisplay expDisplay, Display treeDisplay)
        {
            this.expDisplay = expDisplay;
            this.treeDisplay = treeDisplay;
        }

        public javax.swing.JComponent getDisplay()
        {
            return expDisplay.getDisplay();
        }
        
        public javax.swing.Icon getIcon()
        {
            return ReCResourceBundle.findImageIconOrDefault(treeDisplay.getIconLocationBundleKey(), null);
        }
        
        public javax.swing.JMenuBar getMenuBar()
        {
            return expDisplay.getMenuBar();
        }
        
        public String getName()
        {
            return ReCResourceBundle.findStringOrDefault(treeDisplay.getDisplayNameBundleKey(), expDisplay.getName());
        }
        
        public javax.swing.JToolBar getToolBar()
        {
            return expDisplay.getToolBar();
        }
        
        public void setExpDataModel(com.linkare.rec.impl.client.experiment.ExpDataModel model)
        {
            expDisplay.setExpDataModel(model);
        }        
    }
}
