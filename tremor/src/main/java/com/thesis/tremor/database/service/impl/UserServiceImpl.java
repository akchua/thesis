package com.thesis.tremor.database.service.impl;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesis.tremor.database.dao.UserDAO;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
@Service
public class UserServiceImpl
		extends AbstractService<User, Long, UserDAO> 
		implements UserService {

	@Autowired
	protected UserServiceImpl(UserDAO dao) {
		super(dao);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return dao.findByUsernameAndPassword(username, password);
	}
	
	@Override
	public ObjectList<User> findAllWithPagingOrderByName(int pageNumber, int resultsPerPage, String searchKey) {
		return dao.findAllWithPagingAndOrder(pageNumber, resultsPerPage, searchKey, new Order[] { Order.asc("lastName"), Order.asc("firstName") });
	}
}
