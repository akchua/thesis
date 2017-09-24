package com.thesis.tremor.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.FingerDAO;
import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.database.service.FingerService;

@Service
public class FingerServiceImpl
		extends AbstractService<Finger, Long, FingerDAO>
		implements FingerService{

	@Autowired
	protected FingerServiceImpl(FingerDAO dao) {
		super(dao);
	}

	@Override
	public List<Finger> findAllByHandId(Long handId) {
		return dao.findAllByHandId(handId);
	}
}
