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
public class IntArrayPropertyEditor extends com.linkare.editor.AbstractIndexedPropertyEditor
{
    
    /** Creates a new instance of IntArrayPropertyEditor */
    public IntArrayPropertyEditor()
    {
	super(java.beans.PropertyEditorManager.findEditor(int.class),int.class);
	
    }
    
}
