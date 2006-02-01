/*
 * Deactivatable.java
 *
 * Created on 5 de Novembro de 2002, 14:52
 */

package com.linkare.rec.impl.utils;

import org.omg.PortableServer.POA;
/**
 *
 * @author  jp
 */
public interface Deactivatable 
{    
    public boolean alreadySerialized();
    public boolean isDeactivationPossible();
    public String getOID();
    public POA getPOA();
}
