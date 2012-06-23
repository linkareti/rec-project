/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am;

import com.linkare.rec.am.repository.BadWordDTO;
import java.rmi.RemoteException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
@WebService(name = "RecServiceWS", serviceName = "rec-services", portName = "recservice", targetNamespace = "http://webservices.linkare.com/rec")
public interface RecServiceRemote {
    
    @WebMethod
    List<BadWordDTO> getBadWordRegexByLocale(String locale) throws RemoteException;
    
    @WebMethod
    List<BadWordDTO> getAllBadWordRegex() throws RemoteException;
}
