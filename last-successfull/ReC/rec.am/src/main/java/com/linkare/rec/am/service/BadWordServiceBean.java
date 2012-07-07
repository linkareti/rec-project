/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.service;

import com.linkare.rec.am.model.BadWord;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
@Local(BadWordServiceLocal.class)
@Stateless(name = "BadWordService")
public class BadWordServiceBean extends BusinessServiceBean<BadWord, Long> implements BadWordService {

    @Override
    public BadWord find(Long id) {
        return getEntityManager().find(BadWord.class, id);
    }
    @Override
    public List<BadWord> findAll() {
        return find(true, -1, -1);
    }
    @Override
    public List<BadWord> findRange(int[] range) {
        return find(false, range[0], range[1]);
    }
    @Override
    public int count() {
        final Query query = getEntityManager().createNamedQuery(BadWord.COUNT_ALL_QUERYNAME);
        return ((Long) query.getSingleResult()).intValue();
    }
    @SuppressWarnings("unchecked")
    public List<BadWord> find(final boolean all, final int firstResult, final int maxResults) {
        Query q = getEntityManager().createNamedQuery(BadWord.FIND_ALL_QUERYNAME);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }
    @Override
    public void create(final BadWord badWord) {
        getEntityManager().persist(badWord);
    }
    @Override
    public BadWord edit(final BadWord dadWord) {
        return getEntityManager().merge(dadWord);
    }
}
