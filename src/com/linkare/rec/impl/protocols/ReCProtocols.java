/*
 * ReCProtocols.java
 *
 * Created on 22 de Maio de 2003, 16:56
 */

package com.linkare.rec.impl.protocols;

import java.net.URL;
import java.net.MalformedURLException;

/**
 *
 * @author  jp
 */
public class ReCProtocols 
{
    
   public static URL getURL(String spec) throws MalformedURLException
   {
	if(spec==null || spec.length()==0)
	    throw new MalformedURLException("spec is null or with length 0 - returning Exception from ReCProtocols...");
	
	
	if(spec.startsWith("recresource://"))
	{
	    String protocol="recresource";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.recresource.Handler());
	    return url;
	}
	else if(spec.startsWith("mms://"))
	{
	    String protocol="mms";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.mms.Handler());
	    return url;
	}
	else if(spec.startsWith("mmsu://"))
	{
	   String protocol="mmsu";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.mmsu.Handler());
	    return url;
	}
	else if(spec.startsWith("mmst://"))
	{
	    String protocol="mmst";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.mmst.Handler());
	    return url;
	}
	else if(spec.startsWith("rtsp://"))
	{
	    String protocol="rtsp";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.rtsp.Handler());
	    return url;
	}
	else if(spec.startsWith("rtp://"))
	{
	    String protocol="rtp";
	    int protocolLength=(protocol+"://").length();
	    String file="";
	    String host="localhost";
	    if(spec.indexOf("/",protocolLength)!=-1)
	    {
		host=spec.substring(protocolLength,spec.indexOf("/",protocolLength));
		
		file+=spec.substring(spec.indexOf("/",protocolLength));
	    }
	    else
		host=spec.substring(protocolLength);
	    
	    int port=-1;
	    
	    if(host.indexOf(":")!=-1)
	    {
		port=Integer.parseInt(host.substring(host.indexOf(":")));
		host=host.substring(0,host.indexOf(":")-1);
	    }
		
	    URL url=new URL(protocol,host,port,file,new com.linkare.rec.impl.protocols.rtp.Handler());
	    return url;
	}
	
	
	return new URL(spec);
   }

}
