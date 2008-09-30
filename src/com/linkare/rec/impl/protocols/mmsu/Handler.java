/*
 * Handler.java
 *
 * Created on 22 de Maio de 2003, 16:45
 */

package com.linkare.rec.impl.protocols.mmsu;

import java.net.URL;
import java.net.URLConnection;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class Handler extends java.net.URLStreamHandler
{
   public URLConnection openConnection(URL url)
   {
	if(!url.toExternalForm().startsWith("mmsu://"))
	    return null;
	
	return null;
   }
   
      /** Converts a <code>URL</code> of a specific protocol to a
    * <code>String</code>.
    *
    * @param   u   the URL.
    * @return  a string representation of the <code>URL</code> argument.
    */
   protected String toExternalForm(URL u)
   {
      return u.getProtocol()+"://"+u.getHost()+(u.getPort()!=-1?":"+u.getPort():"")+u.getFile();
   }   
   
}