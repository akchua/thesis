package com.thesis.tremor.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.SessionCommentDAO;
import com.thesis.tremor.database.entity.SessionComment;
import com.thesis.tremor.database.service.SessionCommentService;

@Service
public class SessionCommentServiceImpl
		extends AbstractService<SessionComment, Long, SessionCommentDAO>
		implements SessionCommentService {

	@Autowired
	protected SessionCommentServiceImpl(SessionCommentDAO dao) {
		super(dao);
	}

	@Override
	public List<SessionComment> findAllBySession(Long sessionId) {
		return dao.findAllBySession(sessionId);
	}
}
