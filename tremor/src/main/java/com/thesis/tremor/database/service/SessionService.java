package com.thesis.tremor.database.service;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface SessionService extends Service<Session, Long> {

	ObjectList<Session> findAllWithPaging(int pageNumber, int resultsPerPage, DateDuration dateDuration, Long patientId);
}