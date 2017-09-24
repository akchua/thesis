package com.thesis.tremor.database.dao.impl;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.FingerDAO;
import com.thesis.tremor.database.entity.Finger;

@Repository
public class FingerDAOImpl 
		extends AbstractDAO<Finger, Long>
		implements FingerDAO {

	@Override
	public List<Finger> findAllByHandId(Long handId) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("hand.id", handId));
		
		return findAllByCriterionList(null, null, null, null, conjunction);
	}
}
