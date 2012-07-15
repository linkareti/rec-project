package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.am.model.DeployedExperiment;
import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.service.ExperimentServiceLocal;
import com.linkare.rec.am.util.LaboratoriesMonitor;
import com.linkare.rec.am.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.am.util.MultiThreadLaboratoryWrapper;
import org.primefaces.event.TabChangeEvent;

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
    private List<DeployedExperiment> selectedLabExperiments;
    private LaboratoriesMonitor laboratoriesMonitor = LaboratoriesMonitor.getInstance();
    @EJB
    private ExperimentServiceLocal experimentService;
    private Map<String, List<Experiment>> activeExperiment = new HashMap<String, List<Experiment>>();

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

    public List<DeployedExperiment> getLabExperiments() {
        getSelectedLab();
        refreshExperiments();
        return selectedLabExperiments;
    }

    public void updateLabStatus() {
        labs = LaboratoriesMonitor.getInstance().getActiveLabs();
        refreshExperiments();
    }

    public void refreshExperiments() {

        if (selectedLab != null && selectedLab.isAvailable()) {
            initActiveExperimentMap();
            selectedLabExperiments = getDeployedExperiment(activeExperiment.get(selectedLab.getName()));

            final MultiThreadLaboratoryWrapper laboratory = laboratoriesMonitor.getLaboratory(selectedLab.getName());

            if (laboratory != null) {

                final Map<String, MultiThreadDeployedExperimentWrapper> liveExperiments = laboratory.getLiveExperiments();
                if (liveExperiments.size() > 0) {
                    for (DeployedExperiment experiment : selectedLabExperiments) {
                        String experimentName = experiment.getExperiment().getExternalId();
                        MultiThreadDeployedExperimentWrapper mt = liveExperiments.get(experimentName);
                        if (mt != null) {
                            experiment.setState(mt.getState());
                            experiment.setUsersConnected(mt.getUsersConnected());
                        }
                    }
                }

            }

        } else {
            selectedLabExperiments = Collections.emptyList();
        }
    }

    private List<DeployedExperiment> getDeployedExperiment(final List<Experiment> experiment) {
        List<DeployedExperiment> result = Collections.emptyList();

        if (!experiment.isEmpty()) {
            result = new ArrayList<DeployedExperiment>(experiment.size());
            for (final Experiment exp : experiment) {
                DeployedExperiment deployed = new DeployedExperiment();
                deployed.setExperiment(exp);
                result.add(deployed);
            }
        }

        return result;
    }

    public void initActiveExperimentMap() {

        if (selectedLab != null) {
            final List<Experiment> labExperiments = activeExperiment.get(selectedLab.getName());
            if (labExperiments == null) {
                activeExperiment.put(selectedLab.getName(), experimentService.findExperimentsActiveByLaboratory(selectedLab.getName()));
            }
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        selectedLab = (MultiThreadLaboratoryWrapper) event.getData();
    }
}