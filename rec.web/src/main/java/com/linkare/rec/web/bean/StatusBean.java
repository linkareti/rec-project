package com.linkare.rec.web.bean;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.ClientInfoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;
import java.util.HashSet;
import java.util.Set;
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

//    public List<DeployedExperiment> getLabExperiments() {
//        getSelectedLab();
//        refreshExperiments();
//        return selectedLabExperiments;
//    }
//
//    public void updateLabStatus() {
//        labs = LaboratoriesMonitor.getInstance().getActiveLabs();
//        refreshExperiments();
//    }

    
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

//    public void onTabChange(TabChangeEvent event) {
//        selectedLab = (MultiThreadLaboratoryWrapper) event.getData();
//        refreshExperiments();
//    }

    public void listenerMethod(ClientInfoDTO clientInfo) {
        for (DeployedExperiment dpExperiment : selectedLabExperiments) {
            if (dpExperiment.getUsersConnected() != null && dpExperiment.getUsersConnected().contains(clientInfo.getUserName())) {
                Set<String> userNamesToKick = new HashSet<String>();
                userNamesToKick.add(clientInfo.getUserName());
                selectedLab.kickUsers(userNamesToKick, dpExperiment.getExperiment().getExternalId());
                JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_REMOVE_KEY);
            }
        }
    }
}