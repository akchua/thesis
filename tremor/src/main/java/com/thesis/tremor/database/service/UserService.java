package com.thesis.tremor.database.service;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.prototype.UserPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface UserService extends Service<User, Long>, UserPrototype {

	ObjectList<User> findAllWithPagingOrderByName(int pageNumber, int resultsPerPage, String searchKey);
}
