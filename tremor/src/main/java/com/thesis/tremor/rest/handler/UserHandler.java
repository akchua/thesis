package com.thesis.tremor.rest.handler;

import java.util.List;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   25 Jul 2017
 */
public interface UserHandler {
	
	User getUser(Long userId);
	
	ObjectList<User> getUserObjectList(Integer pageNumber, String searchKey);

	List<UserType> getUserTypeList();
}