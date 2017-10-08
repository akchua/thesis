package com.thesis.tremor.database.dao.impl;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.SessionCommentDAO;
import com.thesis.tremor.database.entity.SessionComment;

@Repository
public class SessionCommentDAOImpl
		extends AbstractDAO<SessionComment, Long>
		implements SessionCommentDAO {

	@Override
	public List<SessionComment> findAllBySession(Long sessionId) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("session.id", sessionId));
		
		return findAllByCriterionList(null, null, null, null, conjunction);
	}
}
