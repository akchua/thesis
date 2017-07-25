package com.thesis.tremor.rest.handler.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.UserHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   25 Jul 2017
 */
@Transactional
@Component
public class UserHandlerImpl implements UserHandler {

	@Autowired
	private UserService userService;

	@Override
	public User getUser(Long userId) {
		return userService.find(userId);
	}
	
	@Override
	public ObjectList<User> getUserObjectList(Integer pageNumber, String searchKey) {
		return userService.findAllWithPagingOrderByName(pageNumber, UserContextHolder.getItemsPerPage(), searchKey);
	}
	
	@Override
	public List<UserType> getUserTypeList() {
		return Stream.of(UserType.values())
				.collect(Collectors.toList());
	}
}
