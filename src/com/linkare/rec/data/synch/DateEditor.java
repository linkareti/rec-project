/*
 * DateEditor.java
 *
 * Created on 3 de Julho de 2002, 18:03
 */

package com.linkare.rec.data.synch;

import java.beans.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
/**
 *
 * @author  Administrator
 */
public class DateEditor extends PropertyEditorSupport
{
	
	/** Creates new DateEditor */
	public DateEditor()
	{
	}
	
	public boolean supportsCustomEditor()
	{return true;}
	
	public Component getCustomEditor()
	{
	    DateCustomizer customizer=new DateCustomizer();
	    customizer.addPropertyChangeListener("date",
		new PropertyChangeListener()
		{
		    public void propertyChange(PropertyChangeEvent evt)
		    {
    	    		setValue( evt.getNewValue() );
		    }
		}
		);
	    customizer.setObject(getValue());
	    return customizer;
	}
	
	
	public String getAsText()
	{
	    return getValue().toString();
	}
	
}
