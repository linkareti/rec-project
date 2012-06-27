package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.am.util.LaboratoriesMonitor;
import com.linkare.rec.am.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.am.util.MultiThreadLaboratoryWrapper;

/**
 * Defines of the operations to retrieve the existing laboratories and it's status, and also which experiments are in each laboratory, their status, number of
 * connected users and other management information.
 * 
 * @author Bruno Catarino - Linkare TI
 * @author Artur Correia - Linkare TI
 * 
 */
@ManagedBean(name = "statusBean")
@ViewScoped
public class StatusBean implements Serializable {

    private static final long serialVersionUID = 9112911612244451634L;

    private List<MultiThreadLaboratoryWrapper> labs;
    private MultiThreadLaboratoryWrapper selectedLab;
    private List<MultiThreadDeployedExperimentWrapper> selectedLabExperiments;

    private LaboratoriesMonitor laboratoriesMonitor = LaboratoriesMonitor.getInstance();

    public List<MultiThreadLaboratoryWrapper> getLabs() {
	if (labs == null) {
	    labs = LaboratoriesMonitor.getInstance().getActiveLabs();
	}
	return labs;
    }

    public MultiThreadLaboratoryWrapper getSelectedLab() {
	return selectedLab;
    }

    public void setSelectedLab(MultiThreadLaboratoryWrapper selectedLab) {
	this.selectedLab = selectedLab;
	this.selectedLabExperiments = null;
    }

    public List<MultiThreadDeployedExperimentWrapper> getLabExperiments() {
	refreshExperiments();
	return selectedLabExperiments;
    }

    public void updateLabStatus() {
	labs = LaboratoriesMonitor.getInstance().getActiveLabs();
    }

    public void refreshExperiments() {

	if (selectedLab != null && selectedLab.isAvailable()) {
	    final MultiThreadLaboratoryWrapper laboratory = laboratoriesMonitor.getLaboratory(selectedLab.getName());

	    if (laboratory != null) {
		final Map<String, MultiThreadDeployedExperimentWrapper> liveExperiments = laboratory.getLiveExperiments();
		selectedLabExperiments = new ArrayList<MultiThreadDeployedExperimentWrapper>(liveExperiments.values());
	    }
	} else {
	    selectedLabExperiments = Collections.emptyList();
	}
    }

}