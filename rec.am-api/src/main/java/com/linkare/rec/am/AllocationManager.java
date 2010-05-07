package com.linkare.rec.am;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface AllocationManager extends Remote {

    public List<AllocationDTO> getBy(final Calendar begin, final Calendar end, final String laboratoryID) throws RemoteException, ParameterException;
}