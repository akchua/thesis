package com.thesis.tremor.database.dao.impl;

import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.HandDAO;
import com.thesis.tremor.database.entity.Hand;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Repository
public class HandDAOImpl
		extends AbstractDAO<Hand, Long>
		implements HandDAO {

}
