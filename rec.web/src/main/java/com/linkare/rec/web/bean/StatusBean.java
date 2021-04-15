package com.linkare.rec.web.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.ClientInfoDTO;
import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.model.Laboratory;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.service.LaboratoryService;
import com.linkare.rec.web.service.LaboratoryServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;
import com.linkare.rec.web.util.Strings;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    
    @EJB(beanInterface = LaboratoryServiceLocal.class)
    private LaboratoryService service;
    
    private Map<String, List<Experiment>> activeExperiment = new HashMap<String, List<Experiment>>();
    
    private static final Logger LOG = LoggerFactory.getLogger(StatusBean.class.getName());

	public List<MultiThreadLaboratoryWrapper> getLabs() {
		
		List<MultiThreadLaboratoryWrapper> returnableLabs = new ArrayList<MultiThreadLaboratoryWrapper>(); 
		
        if (labs == null) {
            labs = LaboratoriesMonitor.getInstance().getActiveLabs();
        }
        returnableLabs.addAll(labs);
        
        return returnableLabs;
    }

    private String laboratory;
 
    public String getLaboratory() {
        return laboratory;
    }
    
    public void setLaboratory(String laboratory) {
    	this.laboratory= laboratory;
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
    
    
    public String openExperiment() {
        String param1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("laboratory");
        return "experiencesLogin?faces-redirect=true&includeViewParams=true&laboratory=" + param1;
    }
    
    
    public StreamedContent getLaboratoryImage() {
        
    	  FacesContext context = FacesContext.getCurrentInstance();

          if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
              // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
              return new DefaultStreamedContent();
          }
    	
    	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String laboratoryId = externalContext.getRequestParameterMap().get("labId");
        if (Strings.isNullOrEmpty(laboratoryId)) {
        	LOG.warn("Something went wrong, no parameter laboratory in image request");
            return null;
        }

        Laboratory laboratory = service.find(Long.valueOf(laboratoryId));

        if (laboratory.getImage() == null || laboratory.getImage().length < 1) {
            return null;
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(laboratory.getImage()));
    }

    public void onTabChange(TabChangeEvent event) {
        selectedLab = (MultiThreadLaboratoryWrapper) event.getData();
        refreshExperiments();
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