package com.linkare.rec.am.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.Role;
import com.linkare.rec.am.model.State;
import com.linkare.rec.am.model.User;

@Stateless
@Name("resourceallocator")
public class ResourceAllocationFacadeBean implements
		ResourceAllocationFacadeInterface {

	@Logger
	private Log log;

	// Persistence

	@PersistenceContext(unitName = "AllocationManager")
	private EntityManager em;

	public List<String> getReservations(String laboratorio, String nomeExperiencia,Date startDate,
			Date endDate) throws Exception {


		Laboratory lab = null;
		Experiment exp =null;

		
		// método de segurança: laboratório not null e laboratório exists.
		// startDate not null and valid, endDate not null and valid, endDate >
		// startDate and experiment exists in Laboratorio
		//	
		validaDados(laboratorio, nomeExperiencia, startDate, endDate);
		
		// obtenção dos dados:
		List<Reservation> validReserves = Reservation.findReservationsInLab(laboratorio, nomeExperiencia, startDate, endDate, em);
		// TODO:for each reservation obtain users


		List<String> listaNomesExperiencias = new ArrayList<String>();
		for(Reservation reserv:validReserves){
		listaNomesExperiencias.add(reserv.toString());
		}
		// TODO:create return Data
		

		return listaNomesExperiencias;

	}

	private void validaDados(String laboratorio, String experiencia, Date startDate,
			Date endDate) throws Exception {
		
		Experiment exp =null;
		Laboratory lab = null;
		
		lab=Laboratory.findByName(laboratorio,em);

		if (lab == null) {
			throw new Exception("Não foi referenciado laboratorio");
		}

		if (startDate == null) {
			throw new Exception("Não foi indicada data de inicio");
		}

		if (endDate == null) {
			throw new Exception("Não foi indicada data de fim");
		}

		if(endDate.before(startDate)){
			throw new Exception("data de inicio é posterior à data de fim");
		}
		
		try{
		exp = Laboratory.findExperiment(laboratorio, experiencia, em);
		}catch (IndexOutOfBoundsException e){
			throw new Exception("O laboratório não tem a experiência pretendida");
		}	

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void initData() {
		User user=new User();
		user.setPassword("admin");
		user.setUsername("admin");
		em.persist(user);
		
		Role role=new Role();
		role.setType("administrator");
		role.setDescription("The bosses");
		
		em.persist(role);
		
		role.addUser(user);
		
		role=em.merge(role);

		Laboratory lab=new Laboratory();
		lab.setName("ola");
		lab.setState(new State());
		lab.getState().setActive(true);
		lab.getState().setHelpMessage("help");
		lab.getState().setLabel("ola2");
		lab.getState().setUrl("url");
		
		em.persist(lab);
		
		Experiment exp=new Experiment();
		exp.setName("experienciaTeste");
		exp.setDescription("Esta é uma expereincia teste");
		exp.setLaboratory(lab);
		exp.setState(new State());
		
		em.persist(exp);
		
		Reservation res = new Reservation();
		res.setExperiment(exp);
		Calendar begining = Calendar.getInstance();
		begining.set(2005, 11, 10);
		Calendar end = Calendar.getInstance();
		end.set(2005, 11, 11);
		res.setStartDate(begining.getTime());
		res.setStopDate(end.getTime());
		
		em.persist(res);
	}
}
