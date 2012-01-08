package com.linkare.rec.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *This class will load some configuaration properties. This properties are loaded when 
 * the first thread try to invoke getProperty method.  
 * 
 * @author artur
 */
public class Configuration {

    private static final Log log = LogFactory.getLog(Configuration.class);
    private static final Properties configProperties = new Properties();

    private Configuration() {
    }
    

    static {
        initialize();
    }

    /**
     * Load properties from file. The file's path should be passed to JVM
     * When starting the application server
     * example : -DReC.config=djfjsdfdsjf
     * 
     * 
     */
    private static void initialize() {
        FileInputStream fis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(System.getProperty("ReC.config"));
            br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            configProperties.load(br);
        } catch (FileNotFoundException ex) {
            log.error("File Not Found. Can not load the ReC.config file", ex);
        } catch (IOException ex) {
            log.error("IOException reading file " + System.getProperty("ReC.config"), ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                log.error("IOException closing the stream", ex);
            }
        }

    }

    /**
     * This method will be the interface to outside 
     * 
     * 
     * 
     * @param propertyName
     * @return the property or null if property dont exist or if we have problems loading 
     * the property file
     */
    public static String getProperty(String propertyName) {
        if (propertyName == null) {
            return null;
        } else {
            return configProperties.getProperty(propertyName);
        }
    }
}
