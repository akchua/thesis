package com.thesis.tremor.database.service;

import java.util.List;

import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.database.prototype.FingerPrototype;

public interface FingerService extends Service<Finger, Long>, FingerPrototype {

	List<Finger> findAllByHandId(Long handId);
}
