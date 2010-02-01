package com.linkare.rec.am.action;

import java.util.ArrayList;
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

	public List<String> getReservations(String laboratorio, Date startDate,
			Date endDate) throws Exception {

		Laboratory lab = null;
		//lab = (Laboratory) em.createNamedQuery("findByName").setParameter(
		//		"name", laboratorio).getSingleResult();

		lab=Laboratory.findByName(laboratorio,em);
		
		
		// método de segurança: laboratório not null e laboratório existe.
		// startDate not null and valid, endDate not null and valid e endDate >
		// startDate
		//	
		validaDados(lab, startDate, endDate);
		// obtenção dos dados:
		// select experiment from laboratory where laboratory.id=laboratorio.id
		List<Experiment> listaExperiencias = Laboratory.findExperiments(laboratorio, em);
		// for each experperiment exper:
		// select reservations from experiment where experiment.id = exper.id
		// for each reservation obtain users
		// criar entidade com startDate,endDate,listaUsers, ownerUser,
		// experiment
		// adicionar entidade a listaEntidades
		//            
		// retorno listaEntidades
		List<String> listaNomesExperiencias = new ArrayList<String>();
		
		if(listaExperiencias.size()>0){
		for(Experiment exp:listaExperiencias){
			listaNomesExperiencias.add(exp.getName());
		}
		}
		else{
			listaNomesExperiencias.add("sem experiencias");
		}
		return listaNomesExperiencias;

	}

	private void validaDados(Laboratory laboratorio, Date startDate,
			Date endDate) throws Exception {

		if (laboratorio == null) {
			throw new Exception("Não foi referenciado laboratorio");
		}

		if (startDate == null) {
			throw new Exception("Não foi indicada data de inicio");
		}

		if (endDate == null) {
			throw new Exception("Não foi indicada data de inicio");
		}

		if(endDate.before(startDate)){
			throw new Exception("data de inicio é posterior à data de fim");
		}
		// Query pesquisaLaboratorio =
		// laboratorio.createNamedQuery("findAllEmployeesByFirstName");

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
		
	}
}
