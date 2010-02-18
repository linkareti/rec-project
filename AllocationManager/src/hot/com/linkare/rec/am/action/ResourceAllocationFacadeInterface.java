package com.linkare.rec.am.action;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.Laboratory;

@Remote
public interface ResourceAllocationFacadeInterface {
	
	public List<String> getReservations(String laboratorio,String nomeExperiencia, Date startDate,
			Date endDate) throws Exception;
	
	public void initData();
	

}
