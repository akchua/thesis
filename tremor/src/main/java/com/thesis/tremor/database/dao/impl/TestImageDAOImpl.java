package com.thesis.tremor.database.dao.impl;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.TestImageDAO;
import com.thesis.tremor.database.entity.TestImage;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Repository
public class TestImageDAOImpl
		extends AbstractDAO<TestImage, Long>
		implements TestImageDAO {

	@Override
	public List<TestImage> findAllByTestId(Long testId) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("test.id", testId));
		
		return findAllByCriterionList(null, null, null, null, conjunction);
	}
}
