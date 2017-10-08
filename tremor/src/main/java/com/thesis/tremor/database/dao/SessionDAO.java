package com.thesis.tremor.database.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.prototype.SessionPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface SessionDAO extends DAO<Session, Long>, SessionPrototype {

	ObjectList<Session> findAllWithPaging(int pageNumber, int resultsPerPage, DateDuration dateDuration, Long patientId);

	ObjectList<Session> findAllWithPagingByPatientId(int pageNumber, int resultsPerPage, DateDuration dateDuration, List<Long> patientIds, Order[] orders);
	
	Session findSessionByPatientAndDateDone(Long patientId, Date dateDone);
}
