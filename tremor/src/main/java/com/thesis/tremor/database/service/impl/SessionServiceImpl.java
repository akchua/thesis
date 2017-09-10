package com.thesis.tremor.database.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.dao.SessionDAO;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.service.SessionService;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Service
public class SessionServiceImpl
		extends AbstractService<Session, Long, SessionDAO>
		implements SessionService {

	@Autowired
	protected SessionServiceImpl(SessionDAO dao) {
		super(dao);
	}
	
	@Override
	public boolean isExistsByPatientAndDateDone(Long patientId, Date dateDone) {
		return dao.findSessionByPatientAndDateDone(patientId, dateDone) != null;
	}

	@Override
	public ObjectList<Session> findAllWithPaging(int pageNumber, int resultsPerPage, DateDuration dateDuration, Long patientId) {
		return dao.findAllWithPaging(pageNumber, resultsPerPage, dateDuration, patientId);
	}
}
