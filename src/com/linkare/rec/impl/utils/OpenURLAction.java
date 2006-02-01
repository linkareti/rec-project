/*
 * OpenURLAction.java
 *
 * Created on 19 de Dezembro de 2002, 16:42
 */

package com.linkare.rec.impl.utils;

import javax.swing.*;
import javax.jnlp.*;
import java.awt.event.ActionEvent;
import java.net.*;
/**
 *
 * @author  bruno
 */
public class OpenURLAction extends javax.swing.AbstractAction
{
  
  private BasicService bs = null;
  private URL url=null;
  /** Creates a new instance of OpenURLAction */
  public OpenURLAction(final String strName,final String strURLResource)
  {
	this.putValue(this.NAME,strName);
	  
    try
    {
      url = new URL(strURLResource);
    }
    catch(MalformedURLException e)
    {
      this.setEnabled(false);
	  e.printStackTrace();
      return;
    }
	
	try
    {
      bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
    }
    catch(UnavailableServiceException e)
    {
      e.printStackTrace();
    }
  }
 
  /** Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent evt)
  {
    if(bs==null || url==null)
      return;
    
    if(!bs.isOffline())
    {
      //if(bs.showDocument(url))
        //System.out.println("A abrir o URL...");
      //else
        //System.out.println("N�o foi poss�vel abrir o URL.");
    }
    else
    {
      //System.out.println("Est� off-line... N�o � poss�vel abrir o URL.");
    }
  }
  
}
