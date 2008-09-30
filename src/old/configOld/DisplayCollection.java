/*
 * DisplayCollection.java
 *
 * Created on 1 de Julho de 2003, 17:25
 */

package old.configOld;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class DisplayCollection extends java.util.Vector
{
    
    /** Creates a new instance of DisplayCollection */
    public DisplayCollection()
    {
	super();
    }
    
    public String[] getDisplays()
    {
	Object[] oDisplays=toArray();
	if(oDisplays==null) return null;
	
	String[] displayBeans=new String[oDisplays.length];
	for(int i=0;i<oDisplays.length;i++)
	    displayBeans[i]=((Display)oDisplays[i]).getBean();
	
	return displayBeans;
    }
}
