package com.linkare.irn.nascimento.dao.security;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.model.security.User_;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class UserDAO extends GenericDAO<User> {

    private Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * @param entityManager
     */
    public UserDAO(EntityManager entityManager) {
	super(entityManager);
    }

    public User findByUsernameAndPassword(final String username, final String password) {
	try {
	    final TypedQuery<User> q = getEntityManager().createNamedQuery(User.QUERY_NAME_FIND_BY_USERNAME_AND_PASSWORD, User.class);
	    q.setParameter(User.QUERY_PARAM_USERNAME, username);
	    q.setParameter(User.QUERY_PARAM_PASSWORD, password);
	    return q.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    // do nothing. It's ok
	} catch (final Exception e) {
	    log.log(Level.SEVERE, "Unexpected exception on method execution for username " + username, e);
	}
	return null;
    }

    public User findByUsername(final String username) {
	try {
	    final TypedQuery<User> q = getEntityManager().createNamedQuery(User.QUERY_NAME_FIND_BY_USERNAME, User.class);
	    q.setParameter(User.QUERY_PARAM_USERNAME, username);
	    return q.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    // do nothing. It's ok
	} catch (final Exception e) {
	    log.log(Level.SEVERE, "Unexpected exception on method execution for username " + username, e);
	}
	return null;
    }

    @Override
    protected List<Order> getOrderBy(final Path<User> root, final CriteriaBuilder cb) {
	return Collections.singletonList(cb.asc(root.get(User_.username)));
    }

    @Override
    protected EntityGraph<? extends DomainObject> getFetchGraph() {
	final EntityGraph<User> fetchGraph = getEntityManager().createEntityGraph(User.class);
	fetchGraph.addSubgraph(User_.organization);
	return fetchGraph;
    }
}