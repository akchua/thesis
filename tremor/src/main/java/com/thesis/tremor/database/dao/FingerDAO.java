package com.thesis.tremor.database.dao;

import java.util.List;

import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.database.prototype.FingerPrototype;

public interface FingerDAO extends DAO<Finger, Long>, FingerPrototype {

	List<Finger> findAllByHandId(Long handId);
}
