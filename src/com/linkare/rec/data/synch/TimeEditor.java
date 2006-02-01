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
public class TimeEditor extends PropertyEditorSupport
{
	
	private Time the_time;
	/** Creates new DateEditor */
	public TimeEditor()
	{
	}
	
	
	
	public void setValue(Object timeObject)
	{
		if( Beans.isInstanceOf(timeObject,Time.class) )
		{
			the_time=(Time)Beans.getInstanceOf(timeObject,Time.class);
			firePropertyChange();
		}
	}
	
	public Object getValue()
	{
		return the_time;
	}
	
	public boolean supportsCustomEditor()
	{return true;}
	
	public Component getCustomEditor()
	{
		TimeCustomizer customizer=new TimeCustomizer();
		customizer.setObject(the_time);
		customizer.addPropertyChangeListener("object",
		new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent evt)
			{
				setValue( ((TimeCustomizer)evt.getSource()).getTime());
			}
		}
		);
		return customizer;
		
	}
	
	
	public String getAsText()
	{
		return the_time.toString();
	}
	
}
