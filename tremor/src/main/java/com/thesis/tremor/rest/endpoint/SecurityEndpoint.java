package com.thesis.tremor.rest.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.beans.UserBean;
import com.thesis.tremor.rest.handler.SecurityHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
@Path("/security")
public class SecurityEndpoint {

	@Autowired
	private SecurityHandler securityHandler;
	
	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		securityHandler.logout(request, response);
	}
	
	@GET
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserBean getUser() {
		return securityHandler.getUser();
	}
	
	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean ping() {
		return true;
	}
}
