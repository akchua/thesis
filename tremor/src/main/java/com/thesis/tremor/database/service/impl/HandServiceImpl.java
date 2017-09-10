package com.thesis.tremor.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.HandDAO;
import com.thesis.tremor.database.entity.Hand;
import com.thesis.tremor.database.service.HandService;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Service
public class HandServiceImpl
		extends AbstractService<Hand, Long, HandDAO>
		implements HandService {

	@Autowired
	protected HandServiceImpl(HandDAO dao) {
		super(dao);
	}
}
