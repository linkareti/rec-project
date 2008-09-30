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
public class ColorArrayPropertyEditor extends com.linkare.editor.AbstractIndexedPropertyEditor
{
    
    /** Creates a new instance of IntArrayPropertyEditor */
    public ColorArrayPropertyEditor()
    {
	super(java.beans.PropertyEditorManager.findEditor(java.awt.Color.class),java.awt.Color.class);
	
    }
    
}
