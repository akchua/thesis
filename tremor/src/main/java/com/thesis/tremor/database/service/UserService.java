package com.thesis.tremor.database.service;

import java.util.List;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.prototype.UserPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface UserService extends Service<User, Long>, UserPrototype {

	boolean isExistByUsername(String username);
	
	User findPatientByUsernameAndPassword(String username, String password);
	
	ObjectList<User> findAllWithPagingOrderByName(int pageNumber, int resultsPerPage, String searchKey);
	
	List<User> findAllAdministrators();
}
