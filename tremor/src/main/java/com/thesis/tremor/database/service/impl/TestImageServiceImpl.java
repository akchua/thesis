package com.thesis.tremor.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.TestImageDAO;
import com.thesis.tremor.database.entity.TestImage;
import com.thesis.tremor.database.service.TestImageService;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Service
public class TestImageServiceImpl
		extends AbstractService<TestImage, Long, TestImageDAO>
		implements TestImageService {

	@Autowired
	protected TestImageServiceImpl(TestImageDAO dao) {
		super(dao);
	}

	@Override
	public List<TestImage> findAllByTestId(Long testId) {
		return dao.findAllByTestId(testId);
	}
}
