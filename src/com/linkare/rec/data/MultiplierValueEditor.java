/*
 * MultiplierEditor.java
 *
 * Created on 27 de Junho de 2002, 12:18
 */

package com.linkare.rec.data;

import com.linkare.rec.data.*;
import com.linkare.editor.AbstractSelectionListPropertyEditor;
/**
 *
 * @author  jp
 */
public class MultiplierValueEditor extends AbstractSelectionListPropertyEditor
{

	/** Creates new MultiplierEditor */
	public MultiplierValueEditor()
	{
	    String pack=this.getClass().getPackage().getName()+".";
	    addTableValue(new Byte(Multiplier.fento.getValue()),
			  "f (1E-15)",
			  pack+"Multiplier.fento.getValue()");
	    addTableValue(new Byte(Multiplier.pico.getValue()),
			  "p (1E-12)",
			  pack+"Multiplier.pico.getValue()");
	    addTableValue(new Byte(Multiplier.nano.getValue()),
			  "n (1E-9)",
			  pack+"Multiplier.nano.getValue()");
	    addTableValue(new Byte(Multiplier.micro.getValue()),
			  "\u03BC (1E-6)",
			  pack+"Multiplier.micro.getValue()");
	    addTableValue(new Byte(Multiplier.mili.getValue()),
			  "m (1E-3)",
			  pack+"Multiplier.mili.getValue()");
	    addTableValue(new Byte(Multiplier.none.getValue()),
			  "none (1E0)",
			  pack+"Multiplier.none.getValue()");
	    addTableValue(new Byte(Multiplier.kilo.getValue()),
			  "k (1E3)",
			  pack+"Multiplier.kilo.getValue()");
	    addTableValue(new Byte(Multiplier.mega.getValue()),
			  "M (1E6)",
			  pack+"Multiplier.mega.getValue()");
	    addTableValue(new Byte(Multiplier.giga.getValue()),
			  "G (1E9)",
			  pack+"Multiplier.giga.getValue()");
	    addTableValue(new Byte(Multiplier.tera.getValue()),
			  "T (1E12)",
			  pack+"Multiplier.tera.getValue()");
			
	}

	public void setAsText(String text) throws IllegalArgumentException
	{
	    if(tagToObject(text)!=null)
		setValueFromTag(text);
	    else
		throw new IllegalArgumentException("That value isn't allowed! Possible values are:"+getTagsStringDesc());
	}
	
	public java.awt.Component getCustomEditor()
	{
		return getBaseRecCustomEditor();
	}

}
