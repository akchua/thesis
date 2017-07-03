package com.thesis.tremor.database.dao;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.prototype.UserPrototype;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface UserDAO extends DAO<User, Long>, UserPrototype {

}
