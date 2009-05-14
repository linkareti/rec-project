/*
 * IntArrayPropertyEditor.java
 *
 * Created on 11 de Dezembro de 2003, 18:55
 */

package test;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ColorMatrixCustomEditor extends com.linkare.editor.AbstractIndexedPropertyEditor
{
    
    /** Creates a new instance of IntArrayPropertyEditor */
    public ColorMatrixCustomEditor()
    {
	super(new ColorArrayPropertyEditor(),java.awt.Color[].class);
	
    }
    
}
