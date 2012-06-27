/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.service;

import com.linkare.rec.am.RecServiceRemote;
import com.linkare.rec.am.model.BadWord;
import com.linkare.rec.am.repository.BadWordDTO;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Gedsimon Pereira
 */
@Stateless(name = "RecService")
@Remote(value = RecServiceRemote.class)
@WebService(endpointInterface = "com.linkare.rec.am.RecServiceRemote", name = "RecServiceWS", serviceName = "rec-services", portName = "recservice", targetNamespace = "http://webservices.linkare.com/rec")
public class RecServiceBean implements RecServiceRemote {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager entityManager;

    @Override
    public List<BadWordDTO> getBadWordRegexByLocale(String locale) throws RemoteException {
        if (locale == null) {
            throw new NullPointerException("locale cannot be null");
        }
        final List<BadWordDTO> result = new ArrayList<BadWordDTO>();
        final List<BadWord> badWords = entityManager.createNamedQuery(BadWord.FIND_FOR_LOCALE_QUERYNAME).setParameter(BadWord.QUERY_PARAM_LOCALE, locale).getResultList();

        for (final BadWord badword : badWords) {
            result.add(new BadWordDTO(badword.getBadWordId(), badword.getLocale(), badword.getRegex()));
        }
        return result;
    }

    @Override
    public List<BadWordDTO> getAllBadWordRegex() throws RemoteException {
        final List<BadWordDTO> result = new ArrayList<BadWordDTO>();
        final List<BadWord> badWords = entityManager.createNamedQuery(BadWord.FIND_ALL_QUERYNAME).getResultList();

        for (final BadWord badword : badWords) {
            result.add(new BadWordDTO(badword.getBadWordId(), badword.getLocale(), badword.getRegex()));
        }
        return result;
    }
}
