/*
 * CustomizerUtil.java
 *
 * Created on 8 de Maio de 2003, 15:05
 */

package com.linkare.rec.impl.client.customizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.logging.LoggerUtil;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class CustomizerUIUtil
{
    private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
	}
    }
    /** Creates a new instance of CustomizerUtil */
    public CustomizerUIUtil()
    {
    }
    
    public static ICustomizer loadCustomizer(String url)
    {
	try
	{
	    String className=url;
	    Class c=Class.forName(className);
	    
	    Object  o=c.newInstance();
	    
	    if(o instanceof ICustomizer)
		return (ICustomizer)o;
	    else
		return null;
	    
	}catch(Exception e2)
	{
	    LoggerUtil.logThrowable("Couldn't load Customizer from URL :"+ url,e2,Logger.getLogger(UI_CLIENT_LOGGER));
	    return null;
	}
	
	/*try
	{
	    DownloadService ds = (DownloadService)ServiceManager.lookup("javax.jnlp.DownloadService");
	    
	    DownloadServiceListener dsl=ds.getDefaultProgressWindow();
	    
	    String jarURL=url.substring("jar:".length(),url.indexOf("!")-1);
	    
	    ds.loadResource(new URL(jarURL),"1.0", dsl);
	    
	    String className=url.substring(url.indexOf("!")+1);
	    className=className.replaceAll("/",".").replaceAll(".class","");
	    Class c=className.getClass().getClassLoader().loadClass(className);
	    
	    Object  o=c.newInstance();
	    
	    if(o instanceof ICustomizer)
		return (ICustomizer)o;
	    else
		return null;
	    
	}catch(Exception e2)
	{
	    LoggerUtil.logThrowable("Couldn't load Customizer (using DownloadService) from URL :"+ url,e2,Logger.getLogger(UI_CLIENT_LOGGER));
	    return null;
	}*/
	
	/*try
	{
	    URL fulljarURL=new URL(url);
	 
	    JarURLConnection con=(JarURLConnection)fulljarURL.openConnection();
	 
	    URLClassLoader loader=new URLClassLoader(new URL[]
	    {con.getJarFileURL()});
	 
	    String className=con.getEntryName();
	 
	    className=className.replaceAll("/",".").replaceAll(".class","");
	 
	    Logger.getLogger(UI_CLIENT_LOGGER).log(Level.INFO,"ClassName to load: "+ className);
	 
	    Class c=loader.loadClass(className);
	 
	    java.security.cert.Certificate[] certs=con.getJarEntry().getCertificates();
	    System.out.println("Certificates:" + certs);
	    if(certs!=null && certs.length>0)
	    {
		java.security.cert.X509Certificate cer=(java.security.cert.X509Certificate)certs[0];
		String message="Do you want to run the configuration utility signed by:";
		message+="\n\rSigned by: "+cer.getSubjectDN().getName();
		message+="\n\rCertified by: "+cer.getIssuerDN().getName();
		try
		{
		    cer.checkValidity();
		    message+="\n\rand with a valid date!";
		}catch(java.security.cert.CertificateExpiredException e)
		{
		    message+="\n\r[Note : Certificate has expired...]!";
		}catch(java.security.cert.CertificateNotYetValidException e)
		{
		    message+="\n\r[Note : Certificate is not yet valid...]!";
		}
	 
		if(JOptionPane.showConfirmDialog(null,message,"Loading configuration utility...",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION)
		    return null;
	    }
	 
	    Object  o=c.newInstance();
	 
	    if(o instanceof ICustomizer)
		return (ICustomizer)o;
	    else
		return null;
	 
	}catch(Exception e)
	{
	    LoggerUtil.logThrowable("Couldn't load Customizer from URL "+ url,e,Logger.getLogger(UI_CLIENT_LOGGER));
	 
	    try
	    {
		DownloadService ds = (DownloadService)ServiceManager.lookup("javax.jnlp.DownloadService");
	 
		DownloadServiceListener dsl=ds.getDefaultProgressWindow();
	 
		String jarURL=url.substring("jar://".length(),url.indexOf("!")-1);
	 
		ds.loadResource(new URL(jarURL),"1.0", dsl);
	 
		String className=url.substring(url.indexOf("!")+1);
		className=className.replaceAll("/",".").replaceAll(".class","");
		Class c=className.getClass().getClassLoader().loadClass(className);
	 
		Object  o=c.newInstance();
	 
		if(o instanceof ICustomizer)
		    return (ICustomizer)o;
		else
		    return null;
	 
	    }catch(Exception e2)
	    {
		LoggerUtil.logThrowable("Couldn't load Customizer (using DownloadService) from URL :"+ url,e2,Logger.getLogger(UI_CLIENT_LOGGER));
		return null;
	    }
	}*/
    }
    
    public static boolean customizerExists(String url)
    {
	try
	{
	    String className=url;
	    Class c=Class.forName(className);
	    return (c!=null);
	    
	}catch(Exception e2)
	{
	    LoggerUtil.logThrowable("Couldn't load Customizer from URL :"+ url,e2,Logger.getLogger(UI_CLIENT_LOGGER));
	    return false;
	}
    }
    
    public static ICustomizer InitCustomizer(ICustomizer customizer,
    HardwareInfo hardwareInfo,
    HardwareAcquisitionConfig acqConfig,CustomizerInfo info)
    {
	try
	{
	    if(customizer==null) return null;
	    customizer.setHardwareInfo(hardwareInfo);
	    customizer.setHardwareAcquisitionConfig(acqConfig);
	    JComponent display=customizer.getCustomizerComponent();
	    if(display==null) return null;
	    
	    final JFrame customizerFrame=new JFrame(info.getCustomizerTitle());
            if(info.getCustomizerIcon() != null)
                customizerFrame.setIconImage(info.getCustomizerIcon().getImage());
	    customizerFrame.getContentPane().setLayout(new BorderLayout());
	    customizerFrame.getContentPane().add(display,BorderLayout.CENTER);
	    JMenuBar menuBar=customizer.getMenuBar();
	    if(menuBar!=null)
		customizerFrame.setJMenuBar(menuBar);
	    
	    customizer.addICustomizerListener( new ICustomizerListener()
	    {
		public void canceled()
		{
		    customizerFrame.dispose();
		}
		public void done()
		{
		    customizerFrame.dispose();
		}
	    }
	    );
	    
	    customizerFrame.setVisible(true);
	    customizerFrame.pack();
	    int width=customizerFrame.getWidth();
	    int height=customizerFrame.getHeight();
	    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	    customizerFrame.setLocation((d.width-width)/2, (d.height-height)/2);
	    return customizer;
	    
	}
	catch(Exception e)
	{
	    LoggerUtil.logThrowable("Couldn't init Customizer for Experiment "+ hardwareInfo.getFamiliarName(),e,Logger.getLogger(UI_CLIENT_LOGGER));
	    return null;
	}
    }
}
