package com.thesis.tremor.rest.handler;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface SessionHandler {

	Session getSession(Long sessionId);
	
	ObjectList<Session> getSessionObjectList(Integer pageNumber, DateDuration dateDuration, Long patientId);
}
