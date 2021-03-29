package com.linkare.rec.web.bean;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.ClientInfoDTO;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;
import java.util.HashSet;
import java.util.Set;


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
    @EJB
    private ExperimentServiceLocal experimentService;
    private Map<String, List<Experiment>> activeExperiment = new HashMap<String, List<Experiment>>();

	public List<MultiThreadLaboratoryWrapper> getLabs() {
		
		List<MultiThreadLaboratoryWrapper> returnableLabs = new ArrayList<MultiThreadLaboratoryWrapper>(); 
		
        if (labs == null) {
            labs = LaboratoriesMonitor.getInstance().getActiveLabs();
        }
        returnableLabs.addAll(labs);
        
        return returnableLabs;
    }

    public MultiThreadLaboratoryWrapper getSelectedLab() {
        return selectedLab;
    }

    public void setSelectedLab(MultiThreadLaboratoryWrapper selectedLab) {
        this.selectedLab = selectedLab;
        this.selectedLabExperiments = null;
    }

    public void initActiveExperimentMap() {

        if (selectedLab != null) {
            final List<Experiment> labExperiments = activeExperiment.get(selectedLab.getName());
            if (labExperiments == null) {
                activeExperiment.put(selectedLab.getName(), experimentService.findExperimentsActiveByLaboratory(selectedLab.getName()));
            }
        }
    }

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