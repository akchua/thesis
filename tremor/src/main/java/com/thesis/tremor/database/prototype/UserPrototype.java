package com.thesis.tremor.database.prototype;

import com.thesis.tremor.database.entity.User;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface UserPrototype extends Prototype<User, Long> {
	
	User findByUsernameAndPassword(String username, String password);
};
