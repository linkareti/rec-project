/*
 * DateEditor.java
 *
 * Created on 3 de Julho de 2002, 18:03
 */

package com.linkare.rec.data.synch;

import java.awt.Component;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class TimeEditor extends PropertyEditorSupport {

	private Time the_time;

	/** Creates new DateEditor */
	public TimeEditor() {
	}

	@Override
	public void setValue(final Object timeObject) {
		if (Beans.isInstanceOf(timeObject, Time.class)) {
			the_time = (Time) Beans.getInstanceOf(timeObject, Time.class);
			firePropertyChange();
		}
	}

	@Override
	public Object getValue() {
		return the_time;
	}

	@Override
	public boolean supportsCustomEditor() {
		return true;
	}

	@Override
	public Component getCustomEditor() {
		final TimeCustomizer customizer = new TimeCustomizer();
		customizer.setObject(the_time);
		customizer.addPropertyChangeListener("object", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				setValue(((TimeCustomizer) evt.getSource()).getTime());
			}
		});
		return customizer;

	}

	@Override
	public String getAsText() {
		return the_time.toString();
	}

}
