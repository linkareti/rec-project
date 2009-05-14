package com.linkare.rec.impl.baseUI.config;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.w3c.dom.Node;

// 
// This interface is the intersection of all generated methods.
// 

public interface CommonBean 
{
	public void _setPropertyChangeSupport(PropertyChangeSupport listeners);

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public CommonBean[] childBeans(boolean recursive);

	public void childBeans(boolean recursive, List<CommonBean> beans);

	public boolean equals(Object o);

	public int hashCode();

	public void readNode(Node node);

	public void removePropertyChangeListener(PropertyChangeListener listener);

	public String toString();

	public void validate() throws ReCBaseUIConfig.ValidateException;

	public void writeNode(Writer out, String nodeName, String indent) throws IOException;

}
