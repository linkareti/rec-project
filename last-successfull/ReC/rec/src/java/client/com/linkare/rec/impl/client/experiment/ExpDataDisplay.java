/*
 * ExpDataDisplay.java
 *
 * Created on 7 de Maio de 2003, 12:44
 */

package com.linkare.rec.impl.client.experiment;

import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 * Any Display should implement this interface... Don't forget that the display
 * is going to be reflection based instantiated and this requires you to create
 * a public no-arg constructor on your class
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ExpDataDisplay {

    /**
     * Method to obtain a i18n independent name for the display This method may
     * be called several number of times, even before any {@link ExpDataModel}
     * is set through {@link #setExpDataModel(ExpDataModel)}
     * 
     * @return The i18n independent name for the display.
     */
    public String getName();

    /**
     * Method to obtain an icon to show for the display
     * 
     * @return The icon to show for the display
     */
    public Icon getIcon();

    /**
     * This is a way for the framework to give a reference to the shared
     * {@link ExpDataModel} to each and every registered display for the
     * experiment in question
     * 
     * This is the first method to be called after the instantiation and
     * possible calls to {@link #getName()} or {@link #getIcon()}
     * 
     * @param model The current data model
     */
    public void setExpDataModel(ExpDataModel model);

    public javax.swing.JComponent getDisplay();

    /**
     * Optional method... If the display wants to install an additional menubar
     * on the interface
     * 
     * This method is called everytime the display is "activated" in the
     * interface, so please "cache" the component and do not create new
     * instances everytime
     * 
     * @return null if not required or a {@link JMenuBar} if the interface is to
     *         be complemented with an additional menubar for this display
     */
    public javax.swing.JMenuBar getMenuBar();

    /**
     * Optional method... If the display wants to install an additional toolbar
     * on the interface
     * 
     * This method is called everytime the display is "activated" in the
     * interface, so please "cache" the component and do not create new
     * instances everytime
     * 
     * @return null if not required or a {@link JToolBar} if the interface is to
     *         be complemented with an additional toolbar for this display
     */
    public javax.swing.JToolBar getToolBar();

    /**
     * Method to obtain the display type
     * 
     * @return The display type
     */
    public com.linkare.rec.impl.client.experiment.DataDisplayEnum getDisplayType();

}
