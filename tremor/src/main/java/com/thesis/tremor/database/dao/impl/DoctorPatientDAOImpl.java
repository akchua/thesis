package com.thesis.tremor.database.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.DoctorPatientDAO;
import com.thesis.tremor.database.entity.DoctorPatient;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   18 Aug 2017
 */
@Repository
public class DoctorPatientDAOImpl
		extends AbstractDAO<DoctorPatient, Long>
		implements DoctorPatientDAO {

	@Override
	public ObjectList<DoctorPatient> findAllByDoctorWithPaging(int pageNumber, int resultsPerPage, String searchKey, Long doctorId) {
		return findAllByDoctorWithPagingAndOrder(pageNumber, resultsPerPage, searchKey, doctorId, null);
	}

	@Override
	public ObjectList<DoctorPatient> findAllByDoctorWithPagingAndOrder(int pageNumber, int resultsPerPage,
			String searchKey, Long doctorId, Order[] orders) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("doctor.id", doctorId));
		
		if(StringUtils.isNotBlank(searchKey))
		{
			conjunction.add(Restrictions.disjunction()
					.add(Restrictions.ilike("patientz.firstName", searchKey, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("patientz.lastName", searchKey, MatchMode.ANYWHERE)));
		}
		
		String[] associatedPaths = { "patient" };
		String[] aliasNames = { "patientz" };
		JoinType[] joinTypes = { JoinType.INNER_JOIN };
		
		return findAllByCriterion(pageNumber, resultsPerPage, associatedPaths, aliasNames, joinTypes, orders, conjunction);
	}

	@Override
	public DoctorPatient findByDoctorAndPatientId(Long doctorId, Long patientId) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("doctor.id", doctorId));
		conjunction.add(Restrictions.eq("patient.id", patientId));
		
		return findUniqueResult(null, null, null, conjunction);
	}
}
