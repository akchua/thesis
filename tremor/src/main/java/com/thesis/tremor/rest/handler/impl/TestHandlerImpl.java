package com.thesis.tremor.rest.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.service.TestService;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.TestHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Transactional
@Component
public class TestHandlerImpl implements TestHandler {

	@Autowired
	private TestService testService;
	
	@Override
	public Test getTest(Long testId) {
		return testService.find(testId);
	}

	@Override
	public ObjectList<Test> getTestObjectList(Integer pageNumber, String searchKey, Long sessionId) {
		return testService.findAllWithPaging(pageNumber, UserContextHolder.getItemsPerPage(), searchKey, sessionId);
	}
}
