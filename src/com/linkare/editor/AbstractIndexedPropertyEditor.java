 /*
 * IndexedPropertyEditor.java
 *
 * Created on 4 de Dezembro de 2003, 1:19
 */

package com.linkare.editor;

import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.regex.*;
import java.util.*;

/**
 *
 * @author  jp
 */
public abstract class AbstractIndexedPropertyEditor extends PropertyEditorSupport
{
    private PropertyEditor componentPropertyEditor=null;
    private Class baseArrayClassDefined=null;
    DefaultIndexedPropertyCustomEditor customEditor=null;
    /** Creates a new instance of IndexedPropertyEditor */
    protected AbstractIndexedPropertyEditor()
    {
	super();
    }
    public AbstractIndexedPropertyEditor(PropertyEditor componentPropertyEditor)
    {
	super();
	this.componentPropertyEditor=componentPropertyEditor;
	customEditor=new DefaultIndexedPropertyCustomEditor (this.componentPropertyEditor);
    }
    
    public AbstractIndexedPropertyEditor(PropertyEditor componentPropertyEditor,Class baseArrayClassDefined)
    {
	this(componentPropertyEditor);
	this.baseArrayClassDefined=baseArrayClassDefined;
	customEditor.setBaseArrayClass(baseArrayClassDefined);
    }
    /** Getter for property componentPropertyEditor.
     * @return Value of property componentPropertyEditor.
     *
     */
    public java.beans.PropertyEditor getComponentPropertyEditor()
    {
	return componentPropertyEditor;
    }
        
    /** Getter for property baseArrayClassDefined.
     * @return Value of property baseArrayClassDefined.
     *
     */
    public java.lang.Class getBaseArrayClassDefined()
    {
	return baseArrayClassDefined;
    }
    
    
    public String getAsText()
    {
	componentPropertyEditor.setValue(null);

	if(componentPropertyEditor.getAsText()==null)
	    return null;
	
	if(getValue()==null) return "null";
	
	StringBuffer retVal=new StringBuffer("{");
	int length=Array.getLength(getValue());
	
	for(int i=0;i<length;i++)
	{
	    componentPropertyEditor.setValue(Array.get(getValue(),i));
	    retVal.append(escapeIndexedString(componentPropertyEditor.getAsText()));
	    if(i<length-1)
		retVal.append(",");
	}
	
	retVal.append("}");
	
	return retVal.toString();
    }
    
    
    public void setValue(Object value)
    {
	if(value!=null)
	{
	    if(value.getClass().isArray())
		super.setValue(value);
	    else
	    {
		JOptionPane.showMessageDialog(null,"This editor is being set a value that doesn't represent an array!","Error",JOptionPane.ERROR_MESSAGE);
		return;
	    }
	}
	else
	    super.setValue(value);
    }
    
    public String getJavaInitializationString()
    {
	componentPropertyEditor.setValue(null);

	if(componentPropertyEditor.getJavaInitializationString()==null)
	    return null;
	
	if(getValue()==null) return "null";
	
	StringBuffer retVal=new StringBuffer("{");
	int length=Array.getLength(getValue());
	
	for(int i=0;i<length;i++)
	{
	    componentPropertyEditor.setValue(Array.get(getValue(),i));
	    retVal.append(componentPropertyEditor.getJavaInitializationString());
	    if(i<length-1)
		retVal.append(",");
	}
	
	retVal.append("}");
	
	Class baseArrayClass=getBaseArrayClass();
	if(baseArrayClass==null)
	    throw new IllegalArgumentException("As neither componentPropertyEditor as a non null value neither do I, I can't determine Base Array Class... Please set it explicitly!");
	
	if(baseArrayClass!=null)
	{
	    String indexedArrayDeepness="[]";
	    Class c=baseArrayClass;
	    while(c.isArray())
	    {
		c=c.getComponentType();
		indexedArrayDeepness+="[]";
	    }
	    
	    return "new "+c.getName()+indexedArrayDeepness+retVal.toString();
	}
	    
	    
	return retVal.toString();
    }
    
    
    private Class getBaseArrayClass() throws IllegalArgumentException
    {
	Class baseArrayClass=null;
	if(baseArrayClassDefined!=null)
	    baseArrayClass=baseArrayClassDefined;
	else if(getValue()!=null)
	    baseArrayClass=getValue().getClass().getComponentType();
	else if(componentPropertyEditor.getValue()!=null)
	    baseArrayClass=componentPropertyEditor.getValue().getClass();
	    
	return baseArrayClass;
    }
    public void setAsText(String text) throws java.lang.IllegalArgumentException
    {
	componentPropertyEditor.setValue(null);
	
	if(componentPropertyEditor.getAsText()==null)
	    throw new IllegalArgumentException("Unable to set indexed value property in String format! Base Editor doesn't support it!");

	
	if(text==null || text.equals("") || text.equals("null"))
	{
	    setValue(null);
	    return;
	}
	text=text.trim();
	if(!text.startsWith("{") || !text.endsWith("}"))
	    throw new IllegalArgumentException("Unable to set indexed value property in String format! Should be in format {value1,value2,value3,...}! Please include the brackets!");
	
	text=text.substring(1,text.length()-1);
	String[] splitValues=splitArrayString(text);
	
	Class baseArrayClass=getBaseArrayClass();
	if(baseArrayClass==null)
	    throw new IllegalArgumentException("As neither componentPropertyEditor as a non null value neither do I, I can't determine Base Array Class... Please set it explicitly!");
	    
	
	
	if(baseArrayClass!=null)
	{
	    Object oNewValue=Array.newInstance(baseArrayClass,splitValues.length);
	    for(int i=0;i<splitValues.length;i++)
	    {
		try
		{
		    componentPropertyEditor.setAsText(unEscapeIndexedString(splitValues[i]));
		    Array.set(oNewValue,i,componentPropertyEditor.getValue());
		}
		catch(IllegalArgumentException e)
		{
		    throw new IllegalArgumentException("Couldn't set Indexed Value at index " + i + " because:\n\r"+e.getMessage());
		}
		catch(ArrayIndexOutOfBoundsException e2)
		{
		    throw new IllegalArgumentException("Couldn't set Indexed Value at index " + i + " because:\n\r"+e2.getMessage());
		}
	    }
	    
	    setValue(oNewValue);
	}
	else
	    throw new IllegalArgumentException("Couldn't set Array Value because base Array Class is unknown");
	
    }
    
    private String escapeIndexedString(String strIn)
    {
	if(strIn==null) return "null";
	return strIn.replaceAll(",","\\\\,");
    }
    
    private String unEscapeIndexedString(String strIn)
    {
	if(strIn==null) return "null";
	return strIn.replaceAll("\\\\,",",");
    }
    
    private String[] splitArrayString(String strCSArray)
    {
	
	ArrayList<String> splitStrList=new ArrayList<String>();
	int locatePos=0;
	while(strCSArray.length()>0)
	{
	    locatePos=strCSArray.indexOf(",",locatePos);
	    if(locatePos==-1)
	    {
		splitStrList.add(strCSArray);
		break;
	    }
	    else if(locatePos==0)
	    {
		splitStrList.add("");
		strCSArray=strCSArray.substring(1);
		locatePos=0;
		
	    }
	    else
	    {
		if(strCSArray.charAt(locatePos-1)=='\\')
		{
		    locatePos+=1;
		    continue;
		}
		else
		{
		    splitStrList.add(strCSArray.substring(0,locatePos));
		    strCSArray=strCSArray.substring(locatePos+1);
		    locatePos=0;
		}
	    }
	}
	
	Object[] splitStrObj=splitStrList.toArray();
	String[] splitStr=new String[splitStrObj.length];
	System.arraycopy(splitStrObj,0,splitStr,0,splitStrObj.length);
	
	return splitStr;
    }
    
    public boolean isPaintable()
    {
	//return componentPropertyEditor.isPaintable() && !componrntPropertyEditor.getAsText()!=null)
	return componentPropertyEditor.isPaintable();
    }
    
    public void paintValue(java.awt.Graphics g, java.awt.Rectangle r)
    {
	if(!isPaintable())
	    return; 
	
	FontMetrics fm=g.getFontMetrics();
	int descent=fm.getDescent();
	int ypos=(int)((double)r.y+((double)r.height+fm.getStringBounds("{",g).getHeight())/2.)-fm.getDescent();
	//g.setClip(r.x,r.y,r.width,r.height);   
	if(getValue()==null)
	{
	    g.drawString("null",r.x+1, ypos);
	    return;
	}
	int length=Array.getLength(getValue());
	if(length==0)
	{
	    g.drawString("{}",r.x+1, ypos);
	    return;
	}
	g.drawString("{",r.x+1, ypos); 
	int leftpos=(int)Math.ceil(fm.getStringBounds("{",g).getWidth())+1+r.x;
	int commaDiscount=(int)Math.ceil(fm.getStringBounds(",",g).getWidth());
	int discountRight=(int)Math.ceil(fm.getStringBounds("}",g).getWidth());
	int widthTotal=r.width+r.x-leftpos-discountRight-(length-1)*commaDiscount-2*(length-1);
	int displaceX=widthTotal/length;
	
	if(displaceX<=0)
	{
	    g.drawString("...}", leftpos, ypos);
	    return;
	}
	
	for(int i=0;i<length;i++)
	{
	    componentPropertyEditor.setValue(Array.get(getValue(),i));
	    if(i>0) 
	    {
		g.setClip(r.x, r.y, r.width, r.height);
		g.drawString(",", leftpos+1, ypos);
		leftpos+=commaDiscount+2;
	    } 
	    Rectangle rValue=new Rectangle(leftpos,r.y,displaceX,r.height);
	    g.setClip(rValue.x, rValue.y, rValue.width, rValue.height);
	    componentPropertyEditor.paintValue(g, rValue);
	    leftpos+=displaceX;
	}
	g.setClip(r.x, r.y, r.width, r.height);
	g.drawString("}", leftpos,ypos);
    }
    
    public boolean supportsCustomEditor()
    {
	return getCustomEditor()!=null;
    }
    
    public java.awt.Component getCustomEditor()
    {
	customEditor.setValue(getValue());
	customEditor.addPropertyChangeListener("value", 
	    new PropertyChangeListener()
	    {
		public void propertyChange(PropertyChangeEvent evt)
		{
		    setValue(evt.getNewValue());
		}
	    }
	);
	
	return customEditor;
    }
    
}
