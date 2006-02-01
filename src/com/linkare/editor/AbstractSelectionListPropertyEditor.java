/*
 * BaseReCEditor.java
 *
 * Created on 3 de Dezembro de 2003, 18:31
 */

package com.linkare.editor;

import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author  jp
 */
public abstract class AbstractSelectionListPropertyEditor extends PropertyEditorSupport
{
    
    /** Creates a new instance of BaseReCEditor */
    public AbstractSelectionListPropertyEditor()
    {
	super();
    }
    
    private Vector tagsInitStringsValueList=new Vector();
    
    public void addTableValue(Object value,String tag,String initString)
    {
	tagsInitStringsValueList.add((Object)(new Object[]{tag,initString,value}));
    }
    
    
    public void initTableValues(Object[] valueList,String[] tags,String[] initStrings) throws Exception
    {
	if(valueList!=null)
	{
	    if((tags!=null && tags.length!=valueList.length) || (initStrings!=null && initStrings.length!=valueList.length))
		throw new Exception("Tags List , InitStrings List and Values List is not of same length...");
	    else if(tags==null)
		throw new Exception("Value List is not null and Tags List is null");
	    else if(initStrings==null)
		throw new Exception("Value List is not null and InitStrings List is null");
	}
	else
	{
	    tagsInitStringsValueList.clear();
	    return;
	}
	tagsInitStringsValueList=new Vector(valueList.length);
	for(int i=0;i<valueList.length;i++)
	{
	    tagsInitStringsValueList.set(i,(Object)(new Object[]{tags[i],initStrings[i],valueList[i]}));
	}
	
    }
    
    public String objectToTag(Object value)
    {
	try
	{
	    if(tagsInitStringsValueList!=null)
	    {
		for(int i=0;i<tagsInitStringsValueList.size();i++)
		{
		    Object[] row=((Object[])tagsInitStringsValueList.get(i));
		    Object thisvalue=row[2];
		    String thistag=(String)row[0];
		    if( thisvalue!=null && thisvalue.equals(value))
		    {
			return thistag;
		    }
		    else if(thisvalue==null && value==null)
		    {
			return thistag;
		    }
		}
	    }
	}catch(Exception e)
	{
	    //silent noop... just return null
	}
	return null;
    }
    
    public Object tagToObject(String tag)
    {
	try
	{
	    if(tagsInitStringsValueList!=null)
	    {
		for(int i=0;i<tagsInitStringsValueList.size();i++)
		{
		    Object[] row=((Object[])tagsInitStringsValueList.get(i));
		    Object thisvalue=row[2];
		    String thistag=(String)row[0];
		    if( thistag!=null && thistag.equals(tag))
		    {
			return thisvalue;
		    }
		    else if(thistag==null && tag==null)
		    {
			return thisvalue;
		    }
		}
	    }
	}catch(Exception e)
	{
	    //silent noop... just return null
	}
	return null;
    }
    
    public String objectToInitString(Object value)
    {
	try
	{
	    if(tagsInitStringsValueList!=null)
	    {
		for(int i=0;i<tagsInitStringsValueList.size();i++)
		{
		    Object[] row=((Object[])tagsInitStringsValueList.get(i));
		    Object thisvalue=row[2];
		    String thisInitString=(String)row[1];
		    if( thisvalue!=null && thisvalue.equals(value))
		    {
			return thisInitString;
		    }
		    else if(thisvalue==null && value==null)
		    {
			return thisInitString;
		    }
		}
	    }
	}catch(Exception e)
	{
	    //silent noop... just return null
	}
	return null;
    }
    
    
    public String getTag()
    {
	return objectToTag(getValue());
    }
    
    public String getInitString()
    {
	return objectToInitString(getValue());
    }
    
    public void setValueFromTag(String tag)
    {
	setValue(tagToObject(tag));
    }
    
    public String[] getTags()
    {
	String[] tags=new String[tagsInitStringsValueList.size()];
	for(int i=0;i<tags.length;i++)
	{
	    tags[i]=(String)((Object[])tagsInitStringsValueList.get(i))[0];
	}
	return tags;
    }
    
    public String getJavaInitializationString()
    {
	return getInitString();
    }

    public String getAsText()
    {
	return getTag();
    }
    
    public boolean supportsCustomEditor()
    {return getCustomEditor()!=null;}
    
    
    public String getTagsStringDesc()
    {
	String[] tags=getTags();
	if(tags==null)
	    return "No possible list of values available...";
	else
	{
	    StringBuffer retVal=new StringBuffer("[");
	    for(int i=0;i<tags.length-1;i++)
	    {
		retVal.append(tags[i]).append(",");
	    }
	    if(tags.length>0)
		retVal.append(tags[tags.length-1]);
	    
	    retVal.append("]");
	    
	    return retVal.toString();
	}
    }
    
    public class BaseRecCustomEditor extends JPanel
    {
	
	public BaseRecCustomEditor()
	{
	    super();
	    initComponents();
	}
	
	
	public void initComponents()
	{
	    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	    
	    String[] tags=getTags();
	    if(tags!=null)
	    {
		for(int i=0;i<tags.length;i++)
		    comboList.addItem(tags[i]);
		
		comboList.addItemListener(
		    new ItemListener()
		    {
			public void itemStateChanged(ItemEvent evt)
			{
			    if(evt.getStateChange()==ItemEvent.SELECTED)
				changeSelection((String)evt.getItem());
			}
			
		    }
		);
		
		comboList.setMaximumSize(comboList.getPreferredSize());
		add(comboList);
	    }
	    else
	    {
		textField.setMaximumSize(textField.getPreferredSize());
		textField.addActionListener(
		    new ActionListener()
		    {
			public void actionPerformed(ActionEvent evt)
			{
			    changeValue(textField.getText());
			}
		    }
		);
		add(textField);
		
	    }
	    
	    add(new JLabel(""));
	}
	
	
	public void changeSelection(String newTagSelected)
	{
	    if(updating) return;
	    setValue(tagToObject(newTagSelected));
	}
	
	public void changeValue(String newTag)
	{
	    if(updating) return;
	    try
	    {
		setAsText(newTag);
	    }
	    catch(IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog(this,e.getMessage(),"Error setting value!",JOptionPane.WARNING_MESSAGE);
	    }
	    
	}
	
	private JComboBox comboList=new JComboBox();
	private JTextField textField=new JTextField();
	private boolean updating=false;
	private void updateValue()
	{
	    updating=true;
	    comboList.setSelectedItem(objectToTag(getValue()));
	    textField.setText(getAsText());
	    updating=false;
	}
	
    }
    
    private BaseRecCustomEditor customEditor=null;
    public java.awt.Component getBaseRecCustomEditor()
    {
	if(customEditor==null)
	    customEditor=new BaseRecCustomEditor();
	
	customEditor.updateValue();
	return customEditor;
    }
}
