package com.thesis.tremor.database.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.prototype.UserPrototype;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface UserDAO extends DAO<User, Long>, UserPrototype {

	ObjectList<User> findAllWithPaging(int pageNumber, int resultsPerPage, String searchKey);
	
	ObjectList<User> findAllWithPagingAndOrder(int pageNumber, int resultsPerPage, String searchKey, Order[] orders);
	
	List<User> findAllByUserType(UserType userType);
}
