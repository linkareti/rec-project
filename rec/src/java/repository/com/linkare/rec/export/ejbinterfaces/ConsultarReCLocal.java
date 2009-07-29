/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.export.ejbinterfaces;

import javax.ejb.Local;

/**
 *
 * @author artur
 */
@Local
public interface ConsultarReCLocal {
    

    void sendMessage(final String virtualPath, byte[] data);
    
}
