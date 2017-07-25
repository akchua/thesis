package com.thesis.tremor.rest.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.enums.UserType;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.UserHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   25 Jul 2017
 */
@Path("/user")
public class UserEndpoint {

	@Autowired
	private UserHandler userHandler;
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public User getUser(@QueryParam("userId") Long userId) {
		return userHandler.getUser(userId);
	}
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObjectList<User> getUserObjectList(@QueryParam("pageNumber") Integer pageNumber, @QueryParam("searchKey") String searchKey) {
		return userHandler.getUserObjectList(pageNumber, searchKey);
	}
	
	@GET
	@Path("/usertype")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<UserType> getUserTypeList() {
		return userHandler.getUserTypeList();
	}
}
