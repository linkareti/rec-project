package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.ExperimentService;
import com.linkare.rec.am.service.ExperimentServiceLocal;
import com.linkare.rec.am.service.LaboratoryService;
import com.linkare.rec.am.service.LaboratoryServiceLocal;

/**
 * Defines of the operations to retrieve the existing laboratories and it's status, and also which experiments are in each laboratory, their status, number of
 * connected users and other management information.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@ManagedBean(name = "statusBean")
@ViewScoped
public class StatusBean implements Serializable {

    private static final long serialVersionUID = 9112911612244451634L;

    @EJB(beanInterface = LaboratoryServiceLocal.class)
    private LaboratoryService labService;

    @EJB(beanInterface = ExperimentServiceLocal.class)
    private ExperimentService experimentService;
    
//    @Inject
//    private Logger log;

    private List<Laboratory> labs;
    private Laboratory selectedLab;
    private List<Experiment> selectedLabExperiments;

    //XXX is it necessary to create some kind of cache or the jmx component already does that?
    public List<Laboratory> getLabs() {
	if (labs == null) {
	    labs = labService.findAll();
	}
	return labs;
    }

    public Laboratory getSelectedLab() {
	return selectedLab;
    }

    public void setSelectedLab(Laboratory selectedLab) {
	this.selectedLab = selectedLab;
	this.selectedLabExperiments = null;
    }

    public List<Experiment> getLabExperiments() {

	System.out.println("Selected lab: " + selectedLab);
	System.out.println("Entrei no getLabExperiments: " + selectedLabExperiments);
	if (selectedLabExperiments == null) {
	    if (selectedLab == null) {
		return selectedLabExperiments;
	    }
	    selectedLabExperiments = experimentService.findExperimentsByLaboratory(selectedLab);
	}
	System.out.println("Before return: " + selectedLabExperiments);
	return selectedLabExperiments;
    }
}