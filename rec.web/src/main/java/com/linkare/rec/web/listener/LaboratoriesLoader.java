package com.linkare.rec.web.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Apparatus;
import com.linkare.rec.web.config.Lab;
import com.linkare.rec.web.config.LocalizationBundle;
import com.linkare.rec.web.config.ReCFaceConfig;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.model.Laboratory;
import com.linkare.rec.web.model.State;
import com.linkare.rec.web.service.ExperimentServiceLocal;
import com.linkare.rec.web.service.LaboratoryServiceLocal;
import com.linkare.rec.web.util.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaboratoriesLoader implements ServletContextListener {

    public static class CompositeURLStreamHandlerFactory implements URLStreamHandlerFactory {

        private final List<URLStreamHandlerFactory> delegates = new LinkedList<>();

        public CompositeURLStreamHandlerFactory(URLStreamHandlerFactory... delegates) {
            if (delegates != null) {
                for (URLStreamHandlerFactory handlerFactory : delegates) {
                    if (handlerFactory != null) {
                        this.delegates.add(handlerFactory);
                    }
                }
            }
        }

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            for (URLStreamHandlerFactory handlerFactory : this.delegates) {
                try {
                    URLStreamHandler handler = handlerFactory.createURLStreamHandler(protocol);
                    if (handler != null) {
                        return handler;
                    }
                } catch (Exception e) {
                    // continue
                    LOGGER.info("Exception {} will be ignored", e.getMessage());
                }
            }

            return null;
        }

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LaboratoriesLoader.class);
    private static final String READ_REC_FACE_CONFIG_INIT_PARAM_KEY = "read.rec.face.config";

    private static final String DEFAULT_JMX_PASS = "recControl";
    private static final String DEFAULT_JMX_USER = "controlRole";
    private static final int DELTA_JMX_PORT_FROM_CORBA_PORT = 16000;
    private static final Pattern CORBA_URL_PATTERN = Pattern.compile("[^@]*@([^:]*):([^/]*)/.*");
    private static final int MATCH_GROUP_HOSTNAME = 1;
    private static final int MATCH_GROUP_PORT = 2;

    @EJB
    private LaboratoryServiceLocal labService;

    @EJB
    private ExperimentServiceLocal experimentService;

    public void readFromReCFaceConfig(ServletContext ctx) throws FileNotFoundException, JAXBException {
        String recFaceConfigFilePath = ctx
                .getRealPath("/client/etc/ReCFaceConfig.xml");
        LOGGER.info("recFaceConfigFilePath={}", recFaceConfigFilePath);
        ReCFaceConfig faceConfig = ReCFaceConfig
                .unmarshall(new FileInputStream(recFaceConfigFilePath));
        LOGGER.info("faceConfig233={}", faceConfig);

        for (LocalizationBundle l10nBundle : faceConfig.getLocalizationBundle()) {
            ReCResourceBundle.loadResourceBundle(l10nBundle.getName(),
                    l10nBundle.getLocation());
        }

        List<Laboratory> dbLabs = labService.findAll();

        final Map<String, Laboratory> changedLabs = new HashMap<>();

        for (Lab lab : faceConfig.getLab()) {
            String labId = lab.getLabId();
            LOGGER.info("labId={}", labId);
            Laboratory dbLab = findByLabName(dbLabs, labId);
            LOGGER.info("dbLab={}", dbLab);
            if (dbLab == null) {
                createLaboratoryFromLab(lab);
                LOGGER.info("Lab={}", lab);
            } else if (Strings.isNullOrEmpty(dbLab.getRecFaceConfigUrl())) {
                changedLabs.put(labId, dbLab);
                updateLaboratoryFromLab(dbLab, lab);
            } else {
                LOGGER.info("Will not update dbLab={} because it has a RecFaceConfigUrl configured", dbLab);
            }
        }

        for (Laboratory laboratory : dbLabs) {
            if (!changedLabs.containsKey(laboratory.getName()) && Strings.isNullOrEmpty(
                    laboratory.getRecFaceConfigUrl())) {
                inactivateLaboratory(laboratory);
            }
        }

        synchronizeApparatusToLabs(faceConfig.getLab());
    }

    private void synchronizeApparatusToLabs(List<Lab> labs) {
        final List<Laboratory> dbLabs = labService.findAll();
        Map<String, List<Apparatus>> apparatusesPerLaboratory = new HashMap<>(dbLabs.size());
        Map<String, Apparatus> allApparatuses = new HashMap<>();
        Map<String, Laboratory> dbLabsMap = new HashMap<>();

        for (Lab lab : labs) {
            LOGGER.info("dbLabs={}", dbLabs);
            LOGGER.info("lab.getLabId()={}", lab.getLabId());
            Laboratory laboratory = findByLabName(dbLabs, lab.getLabId());
            if (laboratory == null) {
                LOGGER.warn("Could not find Lab {} in dbLabs list", lab.getLabId());
                continue;
            }
            apparatusesPerLaboratory.put(laboratory.getName(), lab.getApparatus());

            for (Apparatus apparatus : lab.getApparatus()) {
                allApparatuses.put(apparatus.getLocation(), apparatus);
                for (LocalizationBundle locationBundle : apparatus.getLocalizationBundle()) {
                    ReCResourceBundle.loadResourceBundle(locationBundle.getName(), locationBundle.getLocation());
                }
            }
        }
        for (Laboratory laboratory : dbLabs) {
            dbLabsMap.put(laboratory.getName(), laboratory);
            LOGGER.info("dbLabsMap={}", dbLabsMap);
        }

        final List<Experiment> experiments = experimentService.findAll();
        final Map<String, Experiment> experimentsMap = new HashMap<>();
        // Have to do several things...
        // 1. inactivate experiments not found
        // 2. activate experiments found
        // 3. change experiment to the correct lab
        for (Experiment experiment : experiments) {
            LOGGER.info("Entra no For ");
            Apparatus correspondingApparatus = allApparatuses.get(experiment.getExternalId());
            experiment.getState().setActive(correspondingApparatus != null);
            if (correspondingApparatus != null) {
                allApparatuses.remove(experiment.getExternalId());
                String labId = findLabIdForApparatusId(apparatusesPerLaboratory, correspondingApparatus.getLocation());
                LOGGER.info("labId={}", labId);

                if (experiment.getState().getHelpMessage() == null) {
                    String toolTipBundleKey = correspondingApparatus.getToolTipBundleKey();
                    experiment.getState().setHelpMessage(toolTipBundleKey);
                    LOGGER.info("toolTipBundleKey={}", toolTipBundleKey);
                }
                if (experiment.getState().getLabel() == null) {
                    experiment.getState().setLabel(correspondingApparatus.getDisplayStringBundleKey());
                    LOGGER.info("Experiment Label={}", experiment.getState().getLabel());
                }
                if (experiment.getState().getUrl() == null) {
                    experiment.getState().setUrl(correspondingApparatus.getLocation());
                    LOGGER.info("Experiment Label={}", experiment.getState().getUrl());
                }
                if (labId != null) {
                    Laboratory laboratory = dbLabsMap.get(labId);
                    experiment.setLaboratory(laboratory);
                    LOGGER.info("Laboratory={}", laboratory);
                }
            }
            experiment = experimentService.edit(experiment);
            experimentsMap.put(experiment.getExternalId(), experiment);

        }

        // 4. Create experiments that are still not known
        for (Entry<String, Apparatus> apparatusIdEntry : allApparatuses.entrySet()) {
            Experiment experimentBeingCreated = new Experiment();
            Apparatus apparatusToCreate = apparatusIdEntry.getValue();

            String labId = findLabIdForApparatusId(apparatusesPerLaboratory, apparatusToCreate.getLocation());

            String toolTipBundleKey = apparatusToCreate.getToolTipBundleKey();
            experimentBeingCreated.getState().setHelpMessage(toolTipBundleKey);

            String displayStringBundleKey = apparatusToCreate.getDisplayStringBundleKey();
            experimentBeingCreated.getState().setLabel(displayStringBundleKey);
            experimentBeingCreated.getState().setUrl(apparatusToCreate.getLocation());

            experimentBeingCreated.getState().setActive(true);

            experimentBeingCreated.setDescription(
                    ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));
            experimentBeingCreated.setName(
                    ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));
            experimentBeingCreated.setExternalId(apparatusIdEntry.getKey());

            Laboratory laboratory = dbLabsMap.get(labId);
            experimentBeingCreated.setLaboratory(laboratory);

            experimentService.create(experimentBeingCreated);
        }
    }

    private String findLabIdForApparatusId(Map<String, List<Apparatus>> apparatusesPerLaboratory, String location) {
        for (Entry<String, List<Apparatus>> apparatusesOnCurrentLab : apparatusesPerLaboratory.entrySet()) {
            for (Apparatus apparatus : apparatusesOnCurrentLab.getValue()) {
                if (apparatus.getLocation().equals(location)) {
                    return apparatusesOnCurrentLab.getKey();
                }
            }
        }
        return null;
    }

    private Laboratory inactivateLaboratory(Laboratory laboratory) {
        laboratory.getState().setActive(false);
        LOGGER.info("Laboratory={}", laboratory);
        return labService.edit(laboratory);
    }

    private Laboratory updateLaboratoryFromLab(Laboratory dbLab, Lab lab) {
        for (LocalizationBundle l10nBundle : lab.getLocalizationBundle()) {
            ReCResourceBundle.loadResourceBundle(l10nBundle.getName(),
                    l10nBundle.getLocation());
        }

        final String descriptionKey = lab.getDisplayStringBundleKey();
        if (descriptionKey != null && isDefinedInReCResourceBundle(descriptionKey)) {
            dbLab.setDescription(ReCResourceBundle.findString(descriptionKey));
        }

        State stateInfo = dbLab.getState();
        stateInfo.setUrl(lab.getLocation());
        stateInfo.setLabel(descriptionKey);
        stateInfo.setActive(true);

        final String tooltipBundleKey = lab.getToolTipBundleKey();
        if (stateInfo.getHelpMessage() == null) {
            stateInfo.setHelpMessage(ReCResourceBundle.findStringOrDefault(tooltipBundleKey, tooltipBundleKey));
        }

        dbLab.setState(stateInfo);
        if (dbLab.getJmxURL() == null) {
            dbLab.setJmxURL(inferFromCorbaString(lab.getLocation()));
        }

        return labService.edit(dbLab);
    }

    private void createLaboratoryFromLab(Lab lab) {

        for (LocalizationBundle l10nBundle : lab.getLocalizationBundle()) {
            ReCResourceBundle.loadResourceBundle(l10nBundle.getName(), l10nBundle.getLocation());
        }

        Laboratory newLab = new Laboratory();

        final String descriptionKey = lab.getDisplayStringBundleKey();
        newLab.setDescription(ReCResourceBundle.findStringOrDefault(descriptionKey, descriptionKey));

        final String name = lab.getLabId();
        newLab.setName(name);
        LOGGER.info("newLab.getname()={}", newLab.getName());

        State stateInfo = new State();
        stateInfo.setUrl(lab.getLocation());
        stateInfo.setLabel(descriptionKey);
        stateInfo.setActive(true);

        final String tooltipBundleKey = lab.getToolTipBundleKey();
        stateInfo.setHelpMessage(ReCResourceBundle.findStringOrDefault(tooltipBundleKey, tooltipBundleKey));

        newLab.setState(stateInfo);
        newLab.setJmxURL(inferFromCorbaString(lab.getLocation()));
        newLab.setJmxUser(DEFAULT_JMX_USER);
        newLab.setJmxPass(DEFAULT_JMX_PASS);

        LOGGER.info("newLab={}", newLab);
        labService.create(newLab);
    }

    private boolean isDefinedInReCResourceBundle(String bundleKey) {
        try {
            String value = ReCResourceBundle.findString(bundleKey);
            return value != null && value.trim().length() != 0;
        } catch (Exception ignored) {
            LOGGER.info("Bundle not defined {}, {}", bundleKey, ignored.getMessage());
            return false;
        }
    }

    private String inferFromCorbaString(String corbaLocation) {
        // corbaLocation has format:
        // corbaloc:iiop:1.2@host:port/MultiCastController
        // and JMX url has format:
        // service:jmx:rmi:///jndi/rmi://host:(port+16000)/jmxrmi
        final Matcher matcher = CORBA_URL_PATTERN.matcher(corbaLocation);
        String host = "127.0.0.1";
        int port = 9001;
        if (matcher.matches()) {
            host = matcher.group(MATCH_GROUP_HOSTNAME);
            try {
                port = Integer.parseInt(matcher.group(MATCH_GROUP_PORT));
                LOGGER.info("port={}", port);
            } catch (Exception ignored) {
                LOGGER.info("Exception {} will be ignored", ignored.getMessage());
            }
        }

        return "service:jmx:rmi:///jndi/rmi://" + host + ":" + (port + DELTA_JMX_PORT_FROM_CORBA_PORT) + "/jmxrmi";
    }

    private Laboratory findByLabName(List<Laboratory> dbLabs, String labId) {
        return dbLabs.stream()
                .filter(laboratory -> laboratory.getName().equals(labId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            initURLStreamHandlerFromReCResourceProtolos();
        } catch (Exception e) {
            LOGGER.error("**** UNABLE TO INIT URL HANDLER FACTORY ****** : {}", e.getMessage(), e);
        }

        String readFaceConfig = servletContextEvent.getServletContext()
                .getInitParameter(READ_REC_FACE_CONFIG_INIT_PARAM_KEY);
        if (readFaceConfig != null && readFaceConfig.equalsIgnoreCase("true")) {
            try {
                readFromReCFaceConfig(servletContextEvent.getServletContext());
            } catch (Exception e) {
                LOGGER.error("**** UNABLE TO READ DATA FROM REC FACE CONFIG FILE ****** : {}", e.getMessage(), e);
            }
        }
    }

    private void initURLStreamHandlerFromReCResourceProtolos()
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Class<URL> urlClass = URL.class;
        Field field = urlClass.getDeclaredField("factory");
        boolean oldAccessibility = field.isAccessible();
        field.setAccessible(true);
        URLStreamHandlerFactory currentURLStreamHandlerFactory = (URLStreamHandlerFactory)field
                .get(null);
        if (currentURLStreamHandlerFactory != null) {
            LOGGER.info("Current factory {} of type {}", currentURLStreamHandlerFactory,
                    currentURLStreamHandlerFactory.getClass());
            // Clear the field, so eLab can do it's things
            field.set(null, null);
            try {
                // Just load the Protocols class from ReCResource, because it
                // tries to register a new URLStreamHandlerFactory
                Class.forName("com.linkare.net.protocols.Protocols");
            } catch (Exception e) {
                LOGGER.warn("Could not load the Protocols class from the ReCResource", e);
            }

            URLStreamHandlerFactory newFactoryElab = (URLStreamHandlerFactory)field.get(null);
            // Clear the field, because we are going to create a composite
            // URLStreamHandlerFactory now...
            field.set(null, null);

            URLStreamHandlerFactory compositeURLStreamHandlerFactory = new CompositeURLStreamHandlerFactory(
                    currentURLStreamHandlerFactory, newFactoryElab);
            URL.setURLStreamHandlerFactory(compositeURLStreamHandlerFactory);

        }
        field.setAccessible(oldAccessibility);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
