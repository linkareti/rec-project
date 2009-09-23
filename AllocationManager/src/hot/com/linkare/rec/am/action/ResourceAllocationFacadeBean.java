package com.linkare.rec.am.action;

import java.util.Date;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.linkare.rec.am.model.Laboratory;

@Stateless
@Name("resourceallocator")
public class ResourceAllocationFacadeBean
		implements
			ResourceAllocationFacadeInterface {

	@Logger
	private Log log;

	public void getReservations(Laboratory laboratorio, Date startDate,
			Date endDate) {

	}
}
