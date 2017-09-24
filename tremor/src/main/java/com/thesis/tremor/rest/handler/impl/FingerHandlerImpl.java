package com.thesis.tremor.rest.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.database.service.FingerService;
import com.thesis.tremor.rest.handler.FingerHandler;

@Transactional
@Component
public class FingerHandlerImpl implements FingerHandler {

	@Autowired
	private FingerService fingerService;
	
	@Override
	public Finger getFinger(Long id) {
		return fingerService.find(id);
	}

	@Override
	public List<Finger> getFingerListByHandId(Long handId) {
		return fingerService.findAllByHandId(handId);
	}

}
