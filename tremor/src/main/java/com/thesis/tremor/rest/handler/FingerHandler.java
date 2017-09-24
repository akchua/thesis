package com.thesis.tremor.rest.handler;

import java.util.List;

import com.thesis.tremor.database.entity.Finger;

public interface FingerHandler {

	Finger getFinger(Long id);
	
	List<Finger> getFingerListByHandId(Long handId);
}
