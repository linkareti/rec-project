/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.utils;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ricardo Vaz - Linkare TI
 */
public final class ClassUtils {

    private ClassUtils() {
    }

    private static final ClassUtils INSTANCE = new ClassUtils();

    private static ClassLoader ORIGINAL_THREAD_CLASSLOADER;

    public static final ClassUtils getInstance() {
        return INSTANCE;
    }

    public static void setOriginalThreadContextClassLoader(ClassLoader contextClassLoader) {
        ORIGINAL_THREAD_CLASSLOADER = contextClassLoader;
    }

    public static Object beansInstantiate(ClassLoader classLoader, String factoryLocation) throws IOException, ClassNotFoundException {
        boolean instantiated = false;
        Object retVal = null;
        try {
            if (ORIGINAL_THREAD_CLASSLOADER != null) {
                retVal = java.beans.Beans.instantiate(ORIGINAL_THREAD_CLASSLOADER, factoryLocation);
                instantiated = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (!instantiated) {
                try {
                    retVal = java.beans.Beans.instantiate(Thread.currentThread().getContextClassLoader(), factoryLocation);
                    instantiated = true;
                } catch (Exception ex) {
                    Logger.getLogger(ClassUtils.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (!instantiated) {
                        retVal = java.beans.Beans.instantiate(classLoader, factoryLocation);
                    }
                }
            }
        }

        return retVal;
    }

    public static Class<?> findClass(final String className, final ClassLoader contextClassLoader) throws ClassNotFoundException {
        boolean foundClass = false;
        Class<?> c = null;
        
        try {
            if (ORIGINAL_THREAD_CLASSLOADER != null) {
                c = Class.forName(className, false, ORIGINAL_THREAD_CLASSLOADER);
                foundClass = true;
            }
        } catch (ClassNotFoundException cnfex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.WARNING,
                    "ORIGINAL_THREAD_CLASSLOADER could not load class: "
                    + className, cnfex);
        } finally {
            if (!foundClass) {
                try {
                    c = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
                    foundClass = true;
                } catch (ClassNotFoundException cnfex) {
                    Logger.getLogger(ClassUtils.class.getName()).log(Level.WARNING,
                            "Thread.currentThread().getContextClassLoader() could not load class: "
                            + className, cnfex);
                } finally {
                    if (!foundClass) {
                        c = Class.forName(className, false, contextClassLoader);
                    }
                }
            }
        }

        return c;

    }

    public static URL loadResource(final String resourceLocation, final ClassLoader contextClassLoader) {
        URL resourceURL = null;

        if (ORIGINAL_THREAD_CLASSLOADER != null) {
            resourceURL = ORIGINAL_THREAD_CLASSLOADER.getResource(resourceLocation);
        }

        if (resourceURL == null) {
            resourceURL = Thread.currentThread().getContextClassLoader().getResource(resourceLocation);
        }

        if (resourceURL == null) {
            resourceURL = contextClassLoader.getResource(resourceLocation);
        }

        return resourceURL;

    }

}
