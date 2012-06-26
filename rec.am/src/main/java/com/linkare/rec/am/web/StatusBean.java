package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.LaboratoryService;
import com.linkare.rec.am.service.LaboratoryServiceLocal;
import com.linkare.rec.am.util.LaboratoriesMonitor;
import com.linkare.rec.am.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.am.util.MultiThreadLaboratoryWrapper;

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

    private List<Laboratory> labs;
    private Laboratory selectedLab;
    private List<MultiThreadDeployedExperimentWrapper> selectedLabExperiments;

    private LaboratoriesMonitor laboratoriesMonitor = LaboratoriesMonitor.getInstance();

    public List<Laboratory> getLabs() {
	if (labs == null) {
	    labs = labService.findAllActive();
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

    public List<MultiThreadDeployedExperimentWrapper> getLabExperiments() {
	refreshExperiments();
	return selectedLabExperiments;
    }

    public void updateLabStatus() {
	final Map<Long, Boolean> liveLabs = laboratoriesMonitor.getLiveLabs();
	for (final Laboratory lab : labs) {
	    final Boolean labStatus = liveLabs.get(lab.getIdInternal());
	    if (labStatus != null) {
		lab.setAvailable(labStatus);
	    }
	}
    }

    public void refreshExperiments() {

	if (selectedLab != null) {
	    final MultiThreadLaboratoryWrapper laboratory = laboratoriesMonitor.getLaboratory(selectedLab.getName());

	    if (laboratory != null) {
		final Map<String, MultiThreadDeployedExperimentWrapper> liveExperiments = laboratory.getLiveExperiments();
		selectedLabExperiments = new ArrayList<MultiThreadDeployedExperimentWrapper>(liveExperiments.values());
	    }
	}
    }

}