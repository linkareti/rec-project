/*
 
 * MultiplierEditor.java
 
 *
 
 * Created on 27 de Junho de 2002, 12:18
 
 */

package com.linkare.rec.data;

import com.linkare.editor.AbstractSelectionListPropertyEditor;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */

public class MultiplierEditor extends AbstractSelectionListPropertyEditor

{

	/** Creates new MultiplierEditor */

	public MultiplierEditor()

	{
		String pack = this.getClass().getPackage().getName() + ".";

		addTableValue(Multiplier.fento,

		"f (1E-15)", pack + "Multiplier.fento");

		addTableValue(Multiplier.pico,

		"p (1E-12)",

		pack + "Multiplier.pico");

		addTableValue(Multiplier.nano,

		"n (1E-9)",

		pack + "Multiplier.nano");

		addTableValue(Multiplier.micro,

		"\u03BC (1E-6)",

		pack + "Multiplier.micro");

		addTableValue(Multiplier.mili,

		"m (1E-3)",

		pack + "Multiplier.mili");

		addTableValue(Multiplier.none,

		"none (1E0)",

		pack + "Multiplier.none");

		addTableValue(Multiplier.kilo,

		"k (1E3)",

		pack + "Multiplier.kilo");

		addTableValue(Multiplier.mega,

		"M (1E6)",

		pack + "Multiplier.mega");

		addTableValue(Multiplier.giga,

		"G (1E9)",

		pack + "Multiplier.giga");

		addTableValue(Multiplier.tera,

		"T (1E12)",

		pack + "Multiplier.tera");

	}

	public void setAsText(String text) throws IllegalArgumentException

	{

		if (tagToObject(text) != null)

			setValueFromTag(text);

		else

			throw new IllegalArgumentException("That value isn't allowed! Possible values are:" + getTagsStringDesc());

	}

	public java.awt.Component getCustomEditor()

	{

		return getBaseRecCustomEditor();

	}

}
