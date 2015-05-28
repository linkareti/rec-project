/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo Vaz - Linkare TI
 */
public final class ClassUtils {

    public static Object beansInstantiate(ClassLoader classLoader, String factoryLocation) throws IOException, ClassNotFoundException {
        boolean instantiated = false;
        Object retVal = null;
        try {
            retVal = java.beans.Beans.instantiate(Thread.currentThread().getContextClassLoader(), factoryLocation);
            instantiated = true;
        } catch (IOException ex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (!instantiated) {
                retVal = java.beans.Beans.instantiate(classLoader, factoryLocation);
            }
        }

        return retVal;
    }

    private ClassUtils() {
    }

    public static Class<?> findClass(final String className, final ClassLoader contextClassLoader) throws ClassNotFoundException {
        boolean foundClass = false;
        Class<?> c = null;
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

        return c;

    }
}
