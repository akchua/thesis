package com.thesis.tremor.rest.endpoint;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.tremor.beans.PasswordFormBean;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.UserFormBean;
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
	@Path("/patientlist")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObjectList<User> getPatientObjectListByDoctor(@QueryParam("pageNumber") Integer pageNumber, @QueryParam("searchKey") String searchKey,
			@QueryParam("doctorId") Long doctorId) {
		return userHandler.getPatientObjectListByDoctorId(pageNumber, searchKey, doctorId);
	}
	
	@POST
	@Path("/save")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean saveUser(@FormParam("userFormData") String userFormData) throws IOException {
		final ResultBean result;
		System.out.println(userFormData);
		final UserFormBean userForm = new ObjectMapper().readValue(userFormData, UserFormBean.class);
		if(userForm.getId() != null) {
			result = userHandler.updateUser(userForm);
		} else {
			result = userHandler.createUser(userForm);
		}
		
		return result;
	}
	
	@POST
	@Path("/addpatient")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean addPatient(@FormParam("username") String username, @FormParam("password") String password) {
		return userHandler.addPatient(username, password);
	}
	
	@POST
	@Path("/remove")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean removeUser(@FormParam("userId") Long userId) {
		return userHandler.removeUser(userId);
	}
	
	@POST
	@Path("/removepatient")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean removePatient(@FormParam("patientId") Long patientId) {
		return userHandler.removePatient(patientId);
	}
	
	@POST
	@Path("/changepassword")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean changePassword(@FormParam("passwordFormData") String passwordFormData) throws IOException {
		final PasswordFormBean passwordForm = new ObjectMapper().readValue(passwordFormData, PasswordFormBean.class);
		return userHandler.changePassword(passwordForm);
	}
	
	@POST
	@Path("/resetpassword")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean resetPassword(@FormParam("userId") Long userId) {
		return userHandler.resetPassword(userId);
	}
	
	@GET
	@Path("/usertype")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<UserType> getUserTypeList() {
		return userHandler.getUserTypeList();
	}
}
