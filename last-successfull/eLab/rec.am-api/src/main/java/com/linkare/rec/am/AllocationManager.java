package com.linkare.rec.am;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface AllocationManager extends Remote {

    public List<AllocationDTO> getBy(final Date begin, final Date end, final String laboratoryID) throws RemoteException, ParameterException;

    public boolean authenticate(final String username, final String password) throws RemoteException, UnknownDomainException;
}