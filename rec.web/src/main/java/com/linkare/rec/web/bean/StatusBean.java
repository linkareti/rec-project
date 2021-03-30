package com.linkare.rec.web.bean;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.ClientInfoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.ByteArrayInputStream;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.PhaseId;
import javax.faces.context.ExternalContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.util.Strings;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;
import com.linkare.rec.web.service.LaboratoryService;
import com.linkare.rec.web.service.LaboratoryServiceBean;
import javax.faces.context.FacesContext;
import com.linkare.rec.web.service.LaboratoryServiceLocal;
import com.linkare.rec.web.model.Laboratory;
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
@RequestScoped
public class StatusBean implements Serializable {

    private static final long serialVersionUID = 9112911612244451634L;
    private List<MultiThreadLaboratoryWrapper> labs;
    private MultiThreadLaboratoryWrapper selectedLab;
    private List<DeployedExperiment> selectedLabExperiments;
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