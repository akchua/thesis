package com.thesis.tremor.database.dao.impl;

import org.hibernate.criterion.Junction;
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
}
