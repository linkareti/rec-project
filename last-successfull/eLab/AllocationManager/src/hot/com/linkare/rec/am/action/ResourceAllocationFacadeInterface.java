package com.linkare.rec.am.action;

import java.util.Date;

import javax.ejb.Remote;

import com.linkare.rec.am.model.Laboratory;

@Remote
public interface ResourceAllocationFacadeInterface {
	
	public String getReservations(String laboratorio, Date startDate,
			Date endDate) throws Exception;
	
	public void initData();
	

}
