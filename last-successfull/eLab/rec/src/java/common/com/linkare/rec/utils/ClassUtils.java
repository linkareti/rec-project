/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo Vaz - Linkare TI
 */
public class ClassUtils {

    public static Class<?> findClass(final String className, final ClassLoader contextClassLoader) throws ClassNotFoundException {
        try {
            return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.WARNING, "Thread.currentThread().getContextClassLoader() could not load class: " 
                    + className, ex);
            return Class.forName(className, false, contextClassLoader);
        }
    }

}
