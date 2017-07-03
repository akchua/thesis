package com.thesis.tremor.rest.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thesis.tremor.beans.UserBean;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public interface SecurityHandler {

	void logout(HttpServletRequest request, HttpServletResponse response);
	
	UserBean getUser();
}
