package com.linkare.rec.web.service;

import static com.linkare.rec.web.model.Laboratory.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.web.model.Laboratory.FIND_ALL_ACTIVE_QUERYNAME;
import static com.linkare.rec.web.model.Laboratory.FIND_ALL_QUERYNAME;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Lab;
import com.linkare.rec.web.config.LocalizationBundle;
import com.linkare.rec.web.config.ReCFaceConfig;
import com.linkare.rec.web.model.Laboratory;
import com.linkare.rec.web.model.State;

/**
 * @author Joao
 */
@Local(LaboratoryServiceLocal.class)
@Stateless(name = "LaboratoryService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LaboratoryServiceBean extends BusinessServiceBean<Laboratory, Long> implements LaboratoryService {

    private static final int DELTA_JMX_PORT_FROM_CORBA_PORT = 16000;
    private static final Pattern CORBA_URL_PATTERN = Pattern.compile("[^@]*@([^:]*):([^/]*)/.*");
    private static final int MATCH_GROUP_HOSTNAME = 1;
    private static final int MATCH_GROUP_PORT = 2;

    @Override
    public void create(final Laboratory laboratory) {
        getEntityManager().persist(laboratory);
        getEntityManager().flush();
    }

    @Override
    public Laboratory find(final Long id) {
        return getEntityManager().find(Laboratory.class, id);
    }

    @Override
    public List<Laboratory> findRange(final int[] range) {
        return find(false, range[0], range[1]);
    }

    @Override
    public List<Laboratory> findAll() {
        return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Laboratory> find(final boolean all, final int firstResult, final int maxResults) {
        Query q = getEntityManager().createNamedQuery(FIND_ALL_QUERYNAME);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    @Override
    public int count() {
        final Query query = getEntityManager().createNamedQuery(COUNT_ALL_QUERYNAME);
        return ((Long)query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Laboratory> findAllActive() {
        final Query q = getEntityManager().createNamedQuery(FIND_ALL_ACTIVE_QUERYNAME);
        return q.getResultList();
    }

    @Override
    public Laboratory findByName(String name) {
        TypedQuery<Laboratory> query = getEntityManager().createNamedQuery(Laboratory.FIND_BY_NAME_QUERYNAME,
                Laboratory.class);
        query.setParameter("name", name);
        return getSingleOrNullResult(query);
    }

    @Override
    public List<Laboratory> findAllLaboratoriesWithRecConfigUrl() {
        TypedQuery<Laboratory> query = getEntityManager()
                .createNamedQuery(Laboratory.FIND_BY_ALL_WITH_REC_FACE_CONFIG_URLNAME, Laboratory.class);
        return query.getResultList();
    }

    @Override
    public Laboratory updateFromRecFaceConfig(Laboratory laboratory, Lab lab) {
        updateLaboratoryFromLab(laboratory, lab);
        return edit(laboratory);
    }

    public static Optional<Lab> findCorrespondingLab(Laboratory laboratory, ReCFaceConfig recFaceConfig) {
        return recFaceConfig.getLab().stream().filter(x -> matchesId(x, laboratory.getName())).findFirst();
    }

    private static boolean matchesId(Lab lab, String id) {
        if (id == null || lab.getLabId() == null) {
            return false;
        }
        return lab.getLabId().equals(id);
    }

    private void updateLaboratoryFromLab(Laboratory laboratory, Lab lab) {
        for (LocalizationBundle l10nBundle : lab.getLocalizationBundle()) {
            ReCResourceBundle.loadResourceBundle(l10nBundle.getName(), l10nBundle.getLocation());
        }

        final String descriptionKey = lab.getDisplayStringBundleKey();
        if (descriptionKey != null && isDefinedInReCResourceBundle(descriptionKey)) {
            laboratory.setDescription(ReCResourceBundle.findString(descriptionKey));
        }

        State stateInfo = laboratory.getState();
        stateInfo.setUrl(lab.getLocation());
        stateInfo.setLabel(descriptionKey);
        stateInfo.setActive(true);

        final String tooltipBundleKey = lab.getToolTipBundleKey();
        if (stateInfo.getHelpMessage() == null) {
            stateInfo.setHelpMessage(ReCResourceBundle.findStringOrDefault(tooltipBundleKey, tooltipBundleKey));
        }

        laboratory.setState(stateInfo);
        if (laboratory.getJmxURL() == null) {
            laboratory.setJmxURL(inferFromCorbaString(lab.getLocation()));
        }
    }

    private boolean isDefinedInReCResourceBundle(String bundleKey) {
        try {
            String value = ReCResourceBundle.findString(bundleKey);
            return value != null && value.trim().length() != 0;
        } catch (Exception ignored) {
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
                System.out.println("port="+port);
            } catch (Exception ignored) {

            }
        }
        return "service:jmx:rmi:///jndi/rmi://" + host + ":"
                + (port + DELTA_JMX_PORT_FROM_CORBA_PORT) + "/jmxrmi";
    }

    private <T> T getSingleOrNullResult(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}