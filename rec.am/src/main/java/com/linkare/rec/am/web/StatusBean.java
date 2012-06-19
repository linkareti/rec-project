package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.am.model.DeployedExperiment;
import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.HardwareState;
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
    
    private List<Laboratory> labs;
    private Laboratory selectedLab;
    private List<DeployedExperiment> selectedLabExperiments;

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

    public List<DeployedExperiment> getLabExperiments() {

	System.out.println("Selected lab: " + selectedLab);
	System.out.println("Entrei no getLabExperiments: " + selectedLabExperiments);
	if (selectedLabExperiments == null) {
	    if (selectedLab == null) {
		return selectedLabExperiments;
	    }
	    List<Experiment> experiments = experimentService.findExperimentsByLaboratory(selectedLab);
	    selectedLabExperiments = new ArrayList<DeployedExperiment>();
	    for (Experiment experiment : experiments) {
		DeployedExperiment deployedExperiment = new DeployedExperiment();
		deployedExperiment.setExperiment(experiment);
		selectedLabExperiments.add(deployedExperiment);
	    }
	    updateExperimentStatus();
	}
	return selectedLabExperiments;
    }
    
    public void updateLabStatus() {
	//TODO implement to get this from the Singleton component developed by Artur
	for (Laboratory lab : labs) {
	    if (Math.random() > 0.95d) {
		lab.setAvailable(!lab.isAvailable());
	    }
	}
    }
    
    public void updateExperimentStatus() {
	if (selectedLabExperiments == null) {
	    return;
	}
	//TODO implement to get this from the Singleton component developed by Artur
	Random randState = new Random();
	Random randUsers = new Random();
	for (DeployedExperiment experiment : selectedLabExperiments) {
	    if (Math.random() > 0.95d) {
		int newStateVal = randState.nextInt(HardwareState.values().length);
		HardwareState newState = HardwareState.valueOfCode((byte) newStateVal);
		experiment.setState(newState);
	    }
	    if (Math.random() > 0.50d) {
		experiment.setNumberOfUsers(randUsers.nextInt(101));
	    }
	}
    }
}