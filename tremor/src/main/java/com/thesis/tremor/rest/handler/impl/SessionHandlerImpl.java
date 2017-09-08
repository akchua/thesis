package com.thesis.tremor.rest.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.SessionFormBean;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.SessionService;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.enums.Color;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.SessionHandler;
import com.thesis.tremor.utility.Html;

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
	
	@Autowired
	private UserService userService;
	
	@Override
	public Session getSession(Long sessionId) {
		return sessionService.find(sessionId);
	}

	@Override
	public ObjectList<Session> getSessionObjectList(Integer pageNumber, DateDuration dateDuration, Long patientId) {
		return sessionService.findAllWithPaging(pageNumber, UserContextHolder.getItemsPerPage(), dateDuration, patientId);
	}

	@Override
	public ResultBean saveSession(SessionFormBean sessionFormBean, String username, String password) {
		final ResultBean result;
		final User patient = userService.findByUsernameAndPassword(username, password);
		
		if(patient != null) {
			result = new ResultBean(Boolean.TRUE, "Success");
		} else {
			result = new ResultBean(Boolean.FALSE, Html.line(Html.text(Color.RED, "Failed") + " to load patient. Please re-enter correct credentials."));
		}
		
		return result;
	}
}
