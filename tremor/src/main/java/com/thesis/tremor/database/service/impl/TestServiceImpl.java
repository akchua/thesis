package com.thesis.tremor.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.TestDAO;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.service.TestService;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Service
public class TestServiceImpl
		extends AbstractService<Test, Long, TestDAO>
		implements TestService {

	@Autowired
	protected TestServiceImpl(TestDAO dao) {
		super(dao);
	}

	@Override
	public ObjectList<Test> findAllWithPaging(int pageNumber, int resultsPerPage, String searchKey, Long sessionId) {
		return dao.findAllWithPaging(pageNumber, resultsPerPage, searchKey, sessionId);
	}
}
