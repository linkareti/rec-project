package com.linkare.rec.web.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import com.linkare.rec.web.bean.LaboratoriesSynchronizerSchedulerBean;
import com.linkare.rec.web.config.Lab;
import com.linkare.rec.web.config.ReCFaceConfig;
import com.linkare.rec.web.model.Laboratory;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.service.LaboratoryService;
import com.linkare.rec.web.service.LaboratoryServiceBean;
import com.linkare.rec.web.service.LaboratoryServiceLocal;
import com.linkare.rec.web.service.RecFaceConfigClientCache;
import com.linkare.rec.web.util.ConstantUtils;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.Strings;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "laboratoryController")
@RequestScoped
public class LaboratoryController extends AbstractController<Long, Laboratory, LaboratoryService> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(LaboratoryController.class.getName());

    @EJB(beanInterface = LaboratoryServiceLocal.class)
    private LaboratoryService service;

    @EJB
    private ExperimentServiceLocal experimentService;

    @Inject
    RecFaceConfigClientCache recClient;

    @Inject
    LaboratoriesSynchronizerSchedulerBean synchronizer;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * Method for showing the uploaded image
     *
     * @return StreamedContent
     */
    public StreamedContent getImageUploaded() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String laboratoryId = externalContext.getRequestParameterMap().get("laboratory");
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

    @Override
    public final Laboratory getSelected() {
        if (getCurrent() == null) {
            setCurrent(new Laboratory());
        }
        return getCurrent();
    }

    @Override
    protected LaboratoryService getService() {
        return service;
    }

    @Override
    public final String prepareCreate() {
        setCurrent(new Laboratory());
        return ConstantUtils.CREATE;
    }

    @Override
    public String create() {
        Laboratory laboratory = getCurrent();
        if (!checkCreateForm(laboratory)) {
            return ConstantUtils.CREATE;
        }

        updateValuesFromRecConfig(laboratory);

        if (file != null && file.getContents().length > 0) {
            laboratory.setImage(file.getContents());
        }
        checkCoordinates(laboratory);
        String result = super.create();

        createAndUpdateExperiments(laboratory);

        if (getCurrent().getState().isActive()) {
            LaboratoriesMonitor.getInstance().addOrUpdateLaboratory(getCurrent());
        }

        return result;
    }

    private void createAndUpdateExperiments(Laboratory laboratory) {
        if (Strings.isNullOrEmpty(laboratory.getRecFaceConfigUrl())) {
            LOG.info("Nothing to update, the REC Face Config URL is null for laboratory {} {}",
                    laboratory.getIdInternal(), laboratory);
            return;
        }

        ReCFaceConfig config = recClient.getConfig(laboratory.getRecFaceConfigUrl());
        if (config == null) {
            errorMessage("Something went wrong", "Could not update the experiments");
            return;
        }

        Optional<Lab> lab = LaboratoryServiceBean.findCorrespondingLab(laboratory, config).stream().findFirst();
        if (lab.isPresent()) {
            experimentService.createOrUpdateFromLab(laboratory, lab.get());
        } else {
            errorMessage("Something is wrong", "Could not find the laboratory in remove configuration file");
        }
    }

    private boolean checkCreateForm(Laboratory lab) {
        if (!checkRecFaceConfigUrl(lab.getRecFaceConfigUrl())) {
            return false;
        }
        return checkValidName(lab.getName(), lab.getRecFaceConfigUrl());
    }

    private boolean checkRecFaceConfigUrl(String recFaceConfigUrl) {
        if (Strings.isNullOrEmpty(recFaceConfigUrl)) {
            errorMessage("Missing or empty field", "Rec face config url is required!");
            return false;
        }
        ReCFaceConfig config = recClient.getConfig(recFaceConfigUrl);
        if (config == null) {
            errorMessage("Rec Face Config URL Problem", "Could not read information from URL");
        }
        return true;
    }

    private boolean checkValidName(String name, String recFaceConfigUrl) {

        if (service.findByName(name) != null) {
            errorMessage("Invalid name", "Name " + name + " is already being used");
            return false;
        }
        List<String> allowedNames = getRecFaceConfigNamesFromUrl(recFaceConfigUrl);
        if (allowedNames.contains(name)) {
            return true;
        }
        errorMessage("Invalid name", "Name is invalid, possible options are " + allowedNames);
        LOG.info("Name {} not found in {}", name, allowedNames);
        return false;
    }

    private void errorMessage(String summary, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                summary, message));
    }

    private void updateValuesFromRecConfig(Laboratory laboratory) {
        if (Strings.isNullOrEmpty(laboratory.getRecFaceConfigUrl())) {
            LOG.info("No recFaceConfigUrl provided for laboratory {}", laboratory);
            return;
        }

        ReCFaceConfig config = recClient.getConfig(laboratory.getRecFaceConfigUrl());

        Optional<Lab> labOp = LaboratoryServiceBean.findCorrespondingLab(laboratory, config);
        if (labOp.isPresent()) {
            laboratory.getState().setUrl(labOp.get().getLocation());
        } else {
            laboratory.getState().setActive(false);
        }
    }

    @Override
    public String update() {

        Laboratory laboratory = getCurrent();

        if (file != null && file.getContents().length > 0) {
            laboratory.setImage(file.getContents());
        }else{
            //TODO find a better way to ensure that the image is not deleted when updating a Laboratory
            laboratory.setImage(service.find(laboratory.getIdInternal()).getImage());
        }

        checkCoordinates(laboratory);
        updateValuesFromRecConfig(laboratory);
        final String result = super.update();

        laboratory = service.findByName(laboratory.getName());
        createAndUpdateExperiments(laboratory);

        if (laboratory.getState().isActive()) {
            LaboratoriesMonitor.getInstance().addOrUpdateLaboratory(laboratory);
        } else if (!laboratory.getState().isActive()) {
            LaboratoriesMonitor.getInstance().removeLaboratory(laboratory);
        }

        return result;
    }

    @Override
    public String destroy() {
        final String result = super.destroy();
        LaboratoriesMonitor.getInstance().removeLaboratory(getCurrent());
        return result;
    }

    @Override
    public void destroy(ActionEvent event) {
        super.destroy(event);
        LaboratoriesMonitor.getInstance().removeLaboratory(getCurrent());
    }



    /**
     * For rendering the image
     *
     * @return true if there is an image present to be displayed
     */
    public boolean isImageAvailable() {
        if (getCurrent() != null) {
            return getCurrent().getImage() != null && getCurrent().getImage().length > 0;
        }
        return false;
    }

    private List<String> getRecFaceConfigNamesFromUrl(String recConfigUrl) {
        return recClient.getConfig(recConfigUrl).getLab().stream()
                .map(Lab::getLabId)
                .collect(Collectors.toList());
    }

    private void checkCoordinates(Laboratory laboratory){
        if (laboratory.getGpsLocation().getLatitude() == null || laboratory.getGpsLocation().getLongitude() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Gps Location", "Laboratories with invalid gps locations will not be shown on the map"));
        }
    }

    /**
     * Force synchronize all laboratories and experiments from the Remote rec face configs
     */
    public void syncAll(){
        synchronizer.forceSynchronization();
    }

    @FacesConverter(value = "laboratoryConverter", forClass = Laboratory.class)
    public static class LaboratoryConverter implements Converter {

        @Override
        public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LaboratoryController controller =
                    (LaboratoryController)facesContext.getApplication().getELResolver().getValue(
                            facesContext.getELContext(), null,
                            "laboratoryController");
            return controller.service.find(getKey(value));
        }

        private Long getKey(String value) {
            return Long.valueOf(value);
        }

        private String getStringKey(Long value) {
            return value.toString();
        }

        @Override
        public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Laboratory) {
                Laboratory o = (Laboratory)object;
                return getStringKey(o.getIdInternal());
            } else {
                throw new IllegalArgumentException(
                        "object " + object + " is of type " + object.getClass().getName() + "; expected type: "
                                + Laboratory.class.getName());
            }
        }

    }
}