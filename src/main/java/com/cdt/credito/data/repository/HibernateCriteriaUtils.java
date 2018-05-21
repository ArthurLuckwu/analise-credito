package com.cdt.credito.data.repository;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

public class HibernateCriteriaUtils {

	public static Long getCount(Criteria criteria) {
		// backup properties to restore after count
		Projection projection = null;
		ResultTransformer resultTransformer = Criteria.ROOT_ENTITY;
		if (criteria instanceof CriteriaImpl) {
			CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
			projection = criteriaImpl.getProjection();
			resultTransformer = criteriaImpl.getResultTransformer();
		}

		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();

		// restore properties values 
		criteria.setProjection(projection);
		criteria.setResultTransformer(resultTransformer);

		return count;
	}

	public static Criteria applyPageableParams(Criteria criteria, Pageable pageable) {
		// Sets pagination
		criteria.setFirstResult(Math.toIntExact(pageable.getOffset()));
		criteria.setFetchSize(pageable.getPageSize());
		criteria.setMaxResults(pageable.getPageSize());

		// Sets the ordering
		if (pageable.getSort() != null) {
			Iterator<Order> orderIterator = pageable.getSort().iterator();
			while (orderIterator.hasNext()) {
				Order order = orderIterator.next();
				addOrder(criteria, order);
			}
		}

		return criteria;
	}

	private static void addOrder(Criteria criteria, Order order) {
		String property = order.getProperty();
		int dotIndex = property.indexOf('.');
		if (dotIndex >= 0) {
			String associationPath = property.substring(0, dotIndex);
			String alias = getAlias(criteria, associationPath);
			property = alias + property.substring(dotIndex);
		}
		if (order.isAscending()) {
			criteria.addOrder(org.hibernate.criterion.Order.asc(property).ignoreCase());
		} else {
			criteria.addOrder(org.hibernate.criterion.Order.desc(property).ignoreCase());
		}
	}

	private static String getAlias(Criteria criteria, String associationPath) {
		String alias = associationPath;
		boolean aliasCreated = false;
		if (criteria instanceof CriteriaImpl) {
			CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

			Iterator<Subcriteria> subcriteriaIterator = criteriaImpl.iterateSubcriteria();
			while (subcriteriaIterator.hasNext()) {
				Subcriteria subcriteria = subcriteriaIterator.next();
				if (subcriteria.getPath().equals(associationPath)) {
					alias = subcriteria.getAlias();
					aliasCreated = true;
					break;
				}
			}
		}
		if (!aliasCreated) {
			criteria.createAlias(associationPath, alias);
		}

		return alias;
	}

}
