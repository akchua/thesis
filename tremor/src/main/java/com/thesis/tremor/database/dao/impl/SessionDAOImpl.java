package com.thesis.tremor.database.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.dao.SessionDAO;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Repository
public class SessionDAOImpl
		extends AbstractDAO<Session, Long>
		implements SessionDAO {

	@Override
	public Session findSessionByPatientAndDateDone(Long patientId, Date dateDone) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("patient.id", patientId));
		conjunction.add(Restrictions.eq("dateDone", dateDone));
		
		return findUniqueResult(null, null, null, conjunction);
	}
	
	@Override
	public ObjectList<Session> findAllWithPaging(int pageNumber, int resultsPerPage, DateDuration dateDuration, Long patientId) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(dateDuration.getFrom() != null && dateDuration.getTo() != null) {
			conjunction.add(Restrictions.between("dateDone", dateDuration.getFrom(), dateDuration.getTo()));
		}
		
		if(patientId != null) {
			conjunction.add(Restrictions.eq("patient.id", patientId));
		}
		
		return findAllByCriterion(pageNumber, resultsPerPage, null, null, null, null, conjunction);
	}
	
	@Override
	public ObjectList<Session> findAllWithPagingByPatientId(int pageNumber, int resultsPerPage, DateDuration dateDuration, List<Long> patientIds,
			Order[] orders) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(dateDuration.getFrom() != null && dateDuration.getTo() != null) {
			conjunction.add(Restrictions.between("dateDone", dateDuration.getFrom(), dateDuration.getTo()));
		}
		
		if(patientIds.isEmpty()) patientIds.add(0l);
		final Junction disjunction = Restrictions.disjunction();
		for(Long patientId : patientIds) {
			disjunction.add(Restrictions.eq("patient.id", patientId));
		}
		conjunction.add(disjunction);
		
		return findAllByCriterion(pageNumber, resultsPerPage, null, null, null, orders, conjunction);
	}
}
