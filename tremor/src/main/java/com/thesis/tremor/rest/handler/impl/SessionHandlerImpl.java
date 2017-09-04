package com.thesis.tremor.rest.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.service.SessionService;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.SessionHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Transactional
@Component
public class SessionHandlerImpl implements SessionHandler {

	@Autowired
	private SessionService sessionService;
	
	@Override
	public Session getSession(Long sessionId) {
		return sessionService.find(sessionId);
	}

	@Override
	public ObjectList<Session> getSessionObjectList(Integer pageNumber, DateDuration dateDuration, Long patientId) {
		return sessionService.findAllWithPaging(pageNumber, UserContextHolder.getItemsPerPage(), dateDuration, patientId);
	}
}
