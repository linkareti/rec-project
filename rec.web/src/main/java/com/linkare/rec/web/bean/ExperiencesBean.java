package com.linkare.rec.web.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.MultiThreadDeployedExperimentWrapper;
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;

/**
*
* @author Nuno Simões - Linkare TI
*/
@ManagedBean(name = "experiencesBean")
@ViewScoped
public class ExperiencesBean {
	
	private MultiThreadLaboratoryWrapper laboratory;
	private List<DeployedExperiment> selectedLabExperiments;
	private Map<String, List<Experiment>> activeExperiment = new HashMap<String, List<Experiment>>();
	@EJB
    private ExperimentServiceLocal experimentService; 
	
	private static final Logger LOG = LoggerFactory.getLogger(ExperiencesBean.class.getName());

	
    public MultiThreadLaboratoryWrapper getLaboratory(){
		if(laboratory ==null) {
			Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String labName = params.get("laboratory");
		
			if(StringUtils.isNotEmpty(labName)) {
				laboratory = LaboratoriesMonitor.getInstance().getLaboratory(labName);
				refreshExperiments();
				
			}
		}
		return laboratory;
    }
    
    
    
	
	public void setLaboratory(MultiThreadLaboratoryWrapper laboratory) {
		this.laboratory = laboratory;
	}




	public List<DeployedExperiment> getExperiments() {
		getLaboratory();
		
		List<DeployedExperiment> returnableSelectedLabExperiments = new ArrayList<DeployedExperiment>();
				
		returnableSelectedLabExperiments.addAll(selectedLabExperiments);
		
		return returnableSelectedLabExperiments;

	}
	
	
	
	private void refreshExperiments() {

        if (laboratory != null && laboratory.isAvailable()) {
            initActiveExperimentMap();
            selectedLabExperiments = getDeployedExperiment(activeExperiment.get(laboratory.getName()));

            if (laboratory != null) {

                final Map<String, MultiThreadDeployedExperimentWrapper> liveExperiments = laboratory.getLiveExperiments();
                
                if (liveExperiments.size() > 0) {
                	
                	String prefix = getClientPrefix();
                    
                	
                    for (DeployedExperiment experiment : selectedLabExperiments) {
                        String experimentName = experiment.getExperiment().getExternalId();
                        MultiThreadDeployedExperimentWrapper mt = liveExperiments.get(experimentName);
                        if (mt != null) {
                            experiment.setState(mt.getState());
                            experiment.setUsersConnected(mt.getUsersConnected());
                            experiment.setUrlExperiment(prefix+experiment.getExperiment().getExternalId().toLowerCase());
                            
                            
                        }
                    }
                }
            }

        } else {
            selectedLabExperiments = Collections.emptyList();
        }
    }
	
	 public void updateExperiments() {
		 	laboratory = LaboratoriesMonitor.getInstance().getLaboratory(laboratory.getName());
	        refreshExperiments();
	    }
	

	private String getClientPrefix() {
		String recFaceConfig = selectedLabExperiments.get(0).getExperiment().getLaboratory().getRecFaceConfigUrl();
		String prefix;
		if(StringUtils.isNotEmpty(recFaceConfig)) {
			prefix = recFaceConfig.substring(0,recFaceConfig.indexOf("rec.web"));
		}else {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String requestUrl= request.getRequestURL().toString();
			prefix = requestUrl.substring(0,requestUrl.indexOf("rec.web"));
		}
		return prefix;
	}

    private void initActiveExperimentMap() {

        if (laboratory != null) {
            final List<Experiment> labExperiments = activeExperiment.get(laboratory.getName());
            if (labExperiments == null) {
                activeExperiment.put(laboratory.getName(), experimentService.findExperimentsActiveByLaboratory(laboratory.getName()));
            }
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
    
    public String backingAction() {
    	return "logout";
    }
}
