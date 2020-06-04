package com.linkare.irn.nascimento.dao.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.core.BirthRegistration_;
import com.linkare.irn.nascimento.model.ext.mapping.statistic.StatisticDTO;
import com.linkare.irn.nascimento.model.ext.search.core.BirthRegistrationSearch;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.model.identification.Parent_;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.Organization_;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BirthRegistrationDAO extends GenericDAO<BirthRegistration> {

	private static final int MAX_REGISTRATIONS_TO_EXPIRE = 10;

	/**
	 * @param entityManager
	 */
	public BirthRegistrationDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public List<BirthRegistration> findByExample(final BirthRegistrationSearch example, final SelectionRange range) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<BirthRegistration> criteriaQuery = cb.createQuery(getEntityTypeClass());

		final Root<BirthRegistration> root = criteriaQuery.from(BirthRegistration.class);

		final Predicate[] predicates = getSearchPredicates(example, cb, root);
		criteriaQuery.where(predicates);

		final TypedQuery<BirthRegistration> query = getEntityManager().createQuery(criteriaQuery);

		if (range != null) {
			if (range.getStart() < 0) {
				range.setStart(0);
			}
			if (range.getCount() < 0) {
				range.setCount(DEFAULT_COUNT_RECORDS);
			}
			query.setMaxResults(range.getCount());
			query.setFirstResult(range.getStart());
		}
		return query.getResultList();
	}

	public long countByExample(final BirthRegistrationSearch example) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

		final Root<BirthRegistration> root = criteriaQuery.from(BirthRegistration.class);
		final Predicate[] predicates = getSearchPredicates(example, cb, root);

		criteriaQuery.select(cb.countDistinct(root));
		criteriaQuery.where(predicates);

		final TypedQuery<Long> query = getEntityManager().createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	/**
	 * @param example
	 * @param cb
	 * @param root
	 * @return
	 */
	private Predicate[] getSearchPredicates(final BirthRegistrationSearch example, final CriteriaBuilder cb,
			final Root<BirthRegistration> root) {
		final List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.isNotNull(root.get(BirthRegistration_.id))); // force a where clause: id is not null

		if (example.getStart() != null) {
			final Predicate startPredicate = cb.greaterThanOrEqualTo(root.get(BirthRegistration_.creationDate),
					example.getStart());
			predicates.add(startPredicate);
		}

		if (example.getEnd() != null) {
			final Predicate endPredicate = cb.lessThanOrEqualTo(root.get(BirthRegistration_.creationDate),
					example.getEnd());
			predicates.add(endPredicate);
		}

		if (example.getStatus() != null) {
			final Predicate statusPredicate = cb.equal(root.get(BirthRegistration_.birthRegistrationStatus),
					example.getStatus());
			predicates.add(statusPredicate);
		}

		if (example.getHospitalUnitId() != null) {

			Predicate hospitalUnitPredicate = null;

			if (example.getIsOtherHospital()) {

				if (example.getOtherHospitalUnit() != null) {
					hospitalUnitPredicate = cb.like(root.get(BirthRegistration_.otherHospitalUnit),
							"%" + example.getOtherHospitalUnit() + "%");
					predicates.add(hospitalUnitPredicate);
				}
			} else {

				final Join<BirthRegistration, Organization> hospitalUnit = root.join(BirthRegistration_.hospitalUnit);
				hospitalUnitPredicate = cb.equal(hospitalUnit.get(Organization_.id), example.getHospitalUnitId());
				predicates.add(hospitalUnitPredicate);

			}

		}

		if (example.getBabyName() != null) {

			Expression<String> babyNameExpression = cb.concat(root.get(BirthRegistration_.givenName), " ");
			babyNameExpression = cb.concat(babyNameExpression, root.get(BirthRegistration_.familyName));
			String input = example.getBabyName();

			int quotations = 0;
			int spaces = 0;

			for (char ch : input.toCharArray()) {
				if (ch == '"') {
					quotations++;
				} else if (ch == ' ') {
					spaces++;
				}
			}

			String babyName = (quotations == 2) ? input.replace("\"", "") : (spaces == 0) ? input : input.replace(" ", "%");

			final Predicate babyNamePredicate = cb.like(babyNameExpression, "%" + babyName + "%");
			predicates.add(babyNamePredicate);
		}

		if (StringUtils.isNotBlank(example.getFirstParentIdentificationCardNumber())) {
			final Join<BirthRegistration, Parent> firstParent = root.join(BirthRegistration_.firstParent,
					JoinType.LEFT);
			final Predicate firstParentIdentificationCardNumberPredicate = cb.equal(
					firstParent.get(Parent_.identificationCardNumber),
					example.getFirstParentIdentificationCardNumber());
			predicates.add(firstParentIdentificationCardNumberPredicate);
		}

		if (StringUtils.isNotBlank(example.getRegistrationNumber())) {

			final Predicate registrationNumberPredicate = cb.equal(root.get(BirthRegistration_.registrationNumber),
					example.getRegistrationNumber());
			predicates.add(registrationNumberPredicate);
		}

		return (Predicate[]) predicates.toArray(new Predicate[predicates.size()]);
	}

	public List<BirthRegistration> findBirthRegistrationsToExpire(final Date startDate) {
		try {
			final TypedQuery<BirthRegistration> q = getEntityManager().createNamedQuery(
					BirthRegistration.QUERY_NAME_FIND_REGISTRATIONS_TO_EXPIRE, BirthRegistration.class);
			q.setParameter(BirthRegistration.QUERY_PARAM_CREATION_DATE, startDate);
			q.setFirstResult(0);
			q.setMaxResults(MAX_REGISTRATIONS_TO_EXPIRE);
			return q.getResultList();
		} catch (final Exception e) {
			return Collections.emptyList();
		}
	}

	public List<String> findOtherHospitalUnits(final String name, final SelectionRange range) {

		final TypedQuery<String> q = getEntityManager().createNamedQuery(BirthRegistration.FIND_OTHER_HOSPITAL_UNITS,
				String.class);

		q.setParameter(BirthRegistration.PARAM_OTHER_HOSPITAL_UNIT,
				"%" + StringUtils.defaultString(name).toLowerCase() + "%");

		if (range != null) {

			if (range.getStart() < 0) {
				range.setStart(0);
			}

			if (range.getCount() < 0) {
				range.setCount(DEFAULT_COUNT_RECORDS);
			}

			q.setMaxResults(range.getCount());
			q.setFirstResult(range.getStart());
		}
		return q.getResultList();
	}

	public StatisticDTO findYesterdayStatistics() {

		Query query = getEntityManager().createNativeQuery(getStatisticQuery());

		Date startDate = getOneDayInterval(false);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", getOneDayInterval(true));

		return new StatisticDTO((Object[]) query.getSingleResult(), startDate);

	}

	private Date getOneDayInterval(final boolean todaysDate) {

		Calendar calendar = Calendar.getInstance();

		if (!todaysDate) {
			calendar.add(Calendar.DATE, -1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	private String getStatisticQuery() {

		StringBuilder sb = new StringBuilder();

		sb.append("select ");
		sb.append(
				"(select count(*) from birth_registration where birth_registration_status = 'REQUIRES_2ND_FOREFATHER_CONFIRMATION' and  last_birth_registration_status_date BETWEEN :startDate AND :endDate) as pendingForefather")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where birth_registration_status = 'ELABORATING' and  last_birth_registration_status_date BETWEEN :startDate AND :endDate) as elaborating")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where birth_registration_status = 'EXPIRED' and  last_birth_registration_status_date BETWEEN :startDate AND :endDate) as expired")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where birth_registration_status = 'REJECTED' and  last_birth_registration_status_date BETWEEN :startDate AND :endDate) as rejected")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where birth_registration_status = 'CONCLUDED' and  last_birth_registration_status_date BETWEEN :startDate AND :endDate) as concluded")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where gender='FEMALE' and creation_date BETWEEN :startDate AND :endDate) as females")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where gender='MALE' and creation_date BETWEEN :startDate AND :endDate) as males")
				.append(", ");
		sb.append(
				"(select count(*) from birth_registration where gender='UNDETERMINED' and creation_date BETWEEN :startDate AND :endDate) as undetermined ");
		sb.append("from dual");

		return sb.toString();
	}

	@Override
	protected List<Order> getOrderBy(final Path<BirthRegistration> root, final CriteriaBuilder cb) {
		return Collections.singletonList(cb.desc(root.get(BirthRegistration_.creationDate)));
	}
}