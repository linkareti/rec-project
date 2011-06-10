/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.export.ejbinterfaces;

import com.linkare.rec.export.domain.EReCMultiCastDataProducer;
import javax.ejb.Remote;

/**
 *
 * @author artur
 */
@Remote
public interface DMLRecRemote {

    void persistRecDataProducer(final EReCMultiCastDataProducer parameter);
    
    String testBusinessMethod(String name);
    
}
