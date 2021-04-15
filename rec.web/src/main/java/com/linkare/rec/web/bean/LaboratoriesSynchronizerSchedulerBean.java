package com.linkare.rec.web.bean;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.linkare.rec.web.config.Lab;
import com.linkare.rec.web.config.ReCFaceConfig;
import com.linkare.rec.web.model.Laboratory;
import com.linkare.rec.web.service.ExperimentService;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.service.LaboratoryService;
import com.linkare.rec.web.service.LaboratoryServiceBean;
import com.linkare.rec.web.service.LaboratoryServiceLocal;
import com.linkare.rec.web.service.RecFaceConfigClientCache;
import com.linkare.rec.web.util.LaboratoriesMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class LaboratoriesSynchronizerSchedulerBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaboratoriesSynchronizerSchedulerBean.class);
    private static final Object block = new Object();

    @EJB(beanInterface = LaboratoryServiceLocal.class)
    private LaboratoryService service;

    @EJB(beanInterface = ExperimentServiceLocal.class)
    private ExperimentService experimentService;

    @Inject
    private RecFaceConfigClientCache recClient;

    /**
     * Method that will run every two hours, has the responsibility of fetching a list of laboratories and synchronize
     * the information with the remote rec face config
     */
    @Schedule(hour = "*/2", persistent = false, info = "Every two hours")
    public void synchronizeLaboratories() {
        LOGGER.info("RefreshConfigSchedulerBean is up looking for laboratories to synchronize...");
        handleSynchronization();
    }

    public void forceSynchronization() {
        handleSynchronization();
    }

    private void handleSynchronization(){
        synchronized (block) {
            List<Laboratory> laboratories = service.findAllLaboratoriesWithRecConfigUrl();
            if (laboratories.isEmpty()) {
                LOGGER.info("No laboratories to synchronize...");
                return;
            }
            LOGGER.info("Found {} laboratories to synchronize. Start synchronizing...", laboratories.size());
            laboratories.forEach(this::handleUpdateLaboratory);
        }
    }

    private void handleUpdateLaboratory(Laboratory laboratory) {
        ReCFaceConfig config = recClient.getConfig(laboratory.getRecFaceConfigUrl());
        if(config == null){
            LOGGER.warn("Could not retrieve remote RecFaceConfig for Laboratory {} with remote url {}", laboratory,
                    laboratory.getRecFaceConfigUrl());
            return;
        }

        Optional<Lab> lab = LaboratoryServiceBean.findCorrespondingLab(laboratory, config);
        if(lab.isPresent()){
            var updatedLaboratory = service.updateFromRecFaceConfig(laboratory, lab.get());
            LOGGER.info("Laboratory {} was successfully updated", laboratory);
            experimentService.createOrUpdateFromLab(laboratory, lab.get());
            var dbLab = service.find(updatedLaboratory.getIdInternal());
            LaboratoriesMonitor.getInstance().updateLaboratory(dbLab);
        } else {
            LOGGER.info("Could not find corresponding remote Lab for Laboratory {}, inactivating it", laboratory);
            laboratory.getState().setActive(false);
            service.edit(laboratory);
            experimentService.inactivateAll(laboratory);
            LaboratoriesMonitor.getInstance().removeLaboratory(laboratory);
        }
    }
}
