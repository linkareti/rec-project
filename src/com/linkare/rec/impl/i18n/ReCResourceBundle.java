/*
 * ReCResourceBundle.java
 *
 * Created on 21 de Janeiro de 2004, 3:16
 */

package com.linkare.rec.impl.i18n;

import java.util.*;
import com.linkare.rec.impl.protocols.ReCProtocols;
import java.net.*;
import java.io.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.logging.*;
import com.linkare.rec.impl.logging.LoggerUtil;
/**
 *
 * @author  jp
 */
public abstract class ReCResourceBundle extends ResourceBundle
{
    
    public static String REC_RESBUNDLE_LOGGER="ReCResourceBundle.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(REC_RESBUNDLE_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(REC_RESBUNDLE_LOGGER));
	}
    }
    
    private static void log(Level debugLevel, String message)
    {
	Logger.getLogger(REC_RESBUNDLE_LOGGER).log(debugLevel,"ReCResourceBundle - "+message);
    }
    
    private static void logThrowable(String message, Throwable t)
    {
	//LoggerUtil.logThrowable("ReCResourceBundle - "+message,t, Logger.getLogger(REC_RESBUNDLE_LOGGER));
        System.out.println(message);
    }
    
    private static HashMap bundles=new HashMap();
    
    public static String findString(String bundleName,String key)
    throws MissingResourceException
    {
	
	ArrayList bundleNames=calculateLanguageVariants(bundleName, Locale.getDefault());
	
	ResourceBundle bundle=null;
	
	for(int i=0;i<bundleNames.size() && bundle==null;i++)
	{
	    bundle=(ResourceBundle)bundles.get(bundleNames.get(i));
	}
	
	if(bundle==null)
	    throw new MissingResourceException("No bundle here by the name " + bundleName, ReCResourceBundle.class.getName(), key);
	
	
	return bundle.getString(key);
    }
    
    
    public static ImageIcon findImageIcon(String bundleName,String key)
    throws MissingResourceException,MalformedURLException
    {
	String location=findString(bundleName,key);
	URL url=ReCProtocols.getURL(location);
	ImageIcon iconImage=new ImageIcon(url);
	return iconImage;
    }
    
    
    public static Object findObject(String bundleName,String key)
    throws MissingResourceException,IOException, ClassNotFoundException
    {
	String location=findString(bundleName,key);
	Object o=java.beans.Beans.instantiate(null,location);
	return o;
    }
    
    
    public static String findStringOrDefault(String bundleName,String key,String defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findString(bundleName,key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on bundle " + bundleName,e);
	    return defaultValue;
	}
    }
    
    public static ImageIcon findImageIconOrDefault(String bundleName,String key,ImageIcon defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findImageIcon(bundleName,key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on bundle " + bundleName,e);
            return defaultValue;
	}
    }


    public static Object findObjectOrDefault(String bundleName,String key,Object defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findObject(bundleName,key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on bundle " + bundleName,e);
            return defaultValue;
	}
    }
    
    
    public static ImageIcon findImageIcon(String key)
    throws MissingResourceException,MalformedURLException
    {
	String location=findString(key);
	URL url=ReCProtocols.getURL(location);
	ImageIcon iconImage=new ImageIcon(url);
	return iconImage;
    }
    
    
    public static Object findObject(String key)
    throws MissingResourceException,IOException, ClassNotFoundException
    {
	String location=findString(key);
	Object o=java.beans.Beans.instantiate(null,location);
	return o;
    }
    
    
    public static String findString(String key)
    throws MissingResourceException
    {
	ResourceBundle bundle=null;
	
	if(key!=null && key.indexOf('$')!=-1)
	{
	    int loc=key.indexOf('$');
	    String bundleName=key.substring(0,loc);
	    String bundleKey=key.substring(loc+1);
	    return findString(bundleName,bundleKey);
	}
	
	
	Iterator iter=bundles.values().iterator();
	while(iter.hasNext())
	{
	    bundle=(ResourceBundle)iter.next();
	    try
	    {
		return bundle.getString(key);
	    }
	    catch(Exception e)
	    {}
	}
	
	throw new MissingResourceException("Key not found in any bundle here" , ReCResourceBundle.class.getName(), key);
    }
    
            
    public static String findStringOrDefault(String key,String defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findString(key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on any bundle!",e);
            return defaultValue;
	}
    }
    
    
    public static ImageIcon findImageIconOrDefault(String key,ImageIcon defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findImageIcon(key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on any bundle!",e);
            return defaultValue;
	}
    }


    public static Object findObjectOrDefault(String key,Object defaultValue)
    throws MissingResourceException
    {
	try
	{
	    return findObject(key);
	}catch(Exception e)
	{
	    logThrowable("Couldn't find key "+key+" on any bundle!",e);
            return defaultValue;
	}
    }
    
    
    public static ReCResourceBundle loadResourceBundle(String bundleName,String bundleLocation)
    {
	return loadResourceBundle(bundleName, bundleLocation, Locale.getDefault());
    }
    
    public static ReCResourceBundle loadResourceBundle(String bundleName,String bundleLocation,Locale locale)
    {
	//first try to locate the bundle in the cache
	ArrayList bundleKeys=calculateLanguageVariants(bundleName,locale);
	String bundleNameKey=(String)bundleKeys.get(0);
	if(bundles.containsKey(bundleNameKey))
	    return (ReCResourceBundle)bundles.get(bundleNameKey);
	
	//next calculate the languageVariants
	ArrayList bundleLocations=calculateLanguageVariants(bundleLocation, locale);
	
	for(int i=0;i<bundleLocations.size();i++)
	{
	    String bundleLocationCurrent=(String)bundleLocations.get(i);
	    ReCResourceBundle bundle=loadFromClassName(bundleLocationCurrent);
	    if(bundle!=null)
	    {
		bundles.put(bundleKeys.get(i), bundle);
		propagateBundle(bundle,bundleLocationCurrent,(String)bundleKeys.get(i));
		return bundle;
	    }
	    else
	    {
		bundle=loadFromURL(bundleLocationCurrent);
		if(bundle!=null)
		{
		    bundles.put(bundleKeys.get(i),bundle);
		    propagateBundle(bundle,bundleLocationCurrent,(String)bundleKeys.get(i));
		    return bundle;
		}
	    }
	}
	return null;
    }
    
    private static void propagateBundle(ReCResourceBundle bundle,String bundleLocation,String bundleName)
    {
	ReCResourceBundle childBundle=bundle;
	String temp=bundleLocation;
	int fileExtLoc=temp.lastIndexOf(".properties");
	if(fileExtLoc!=-1)
	    temp=temp.substring(0,fileExtLoc);
	
	int loc=temp.lastIndexOf("_");
	
	
	String bundleNameTemp=bundleName;
	
	while(loc!=-1)
	{
	    temp=temp.substring(0,loc);
	    bundleNameTemp=bundleNameTemp.substring(0,bundleNameTemp.lastIndexOf("_"));
	    ReCResourceBundle bundleParent=loadFromClassName(temp);
	    if(bundleParent!=null)
	    {
		bundles.put(bundleNameTemp,bundleParent);
		childBundle.parent=bundleParent;
		childBundle=bundleParent;
	    }
	    else
	    {
		bundleParent=loadFromURL(temp);
		if(bundleParent!=null)
		{
		    bundles.put(bundleNameTemp,bundleParent);
		    childBundle.parent=bundleParent;
		    childBundle=bundleParent;
		}
	    }
	    
	    loc=temp.lastIndexOf("_");
	}
    }
    
    
    private static ReCResourceBundle loadFromClassName(String className)
    {
	try
	{
	    Object oBundle=ClassLoader.getSystemClassLoader().loadClass(className).newInstance();
	    if(oBundle!=null && oBundle instanceof ReCResourceBundle)
		return (ReCResourceBundle)oBundle;
	}catch(Exception e)
	{
	    
	}
	
	return null;
    }
    private static ReCResourceBundle loadFromURL(String strUrl)
    {
	try
	{
	    if(strUrl.indexOf(".properties")==-1)
		strUrl+=".properties";
	    
	    URL url=ReCProtocols.getURL(strUrl);
	    if(url!=null)
	    {
		URLConnection con=url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(false);
		con.connect();
		
		InputStream is=con.getInputStream();
		ReCPropertyResourceBundle bundle=new ReCPropertyResourceBundle(is);
		return bundle;
	    }
	    
	}catch(Exception e)
	{
	}
	
	return null;
    }
    
    private static ArrayList calculateLanguageVariants(String base,Locale locale)
    {
	String language1=locale.getLanguage();
	String country1=locale.getCountry();
	String variant1=locale.getVariant();
	
	Locale defLocale=Locale.getDefault();
	String language2=defLocale.getLanguage();
	String country2=defLocale.getCountry();
	String variant2=defLocale.getVariant();
	
	ArrayList retVal=new ArrayList(7);
	
	String temp="";
	if(language1!=null && !language1.equals(""))
	{
	    temp+="_"+language1;
	    retVal.add(base+temp);
	    if(country1!=null && !country1.equals(""))
	    {
		temp+="_"+country1;
		retVal.add(0, base+temp);
		
		if(variant1!=null && !variant1.equals(""))
		{
		    temp+="_"+variant1;
		    retVal.add(0, base+temp);
		}
	    }
	}
	
	if( !( language1!=null && language2!=null
	&& language1.equals(language2)
	&&
	country1!=null && country2!=null
	&& country1.equals(country2)
	&&
	variant1!=null && variant2!=null
	&& variant1.equals(variant2)
	))
	{
	    
	    temp="";
	    int locationInsert=retVal.size();
	    if(language2!=null && !language2.equals(""))
	    {
		temp+="_"+language2;
		
		retVal.add(base+temp);
		if(country2!=null && !country2.equals(""))
		{
		    temp+="_"+country2;
		    retVal.add(locationInsert, base+temp);
		    
		    if(variant2!=null && !variant2.equals(""))
		    {
			temp+="_"+variant2;
			retVal.add(locationInsert, base+temp);
		    }
		}
	    }
	}
	retVal.add(base);
	
	return retVal;
    }
    
    
}
