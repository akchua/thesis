package com.thesis.tremor.rest.endpoint;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.beans.DateDuration;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.beans.SessionFormBean;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.SessionHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   7 Aug 2017
 */
@Path("session")
public class SessionEndpoint {

	@Autowired
	private SessionHandler sessionHandler;
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public Session getSession(@QueryParam("sessionId") Long sessionId) {
		return sessionHandler.getSession(sessionId);
	}
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObjectList<Session> getSessionObjectList(@QueryParam("pageNumber") Integer pageNumber, 
				@QueryParam("from") String from,
				@QueryParam("to") String to,
				@QueryParam("patientId") Long patientId) {
		final DateDuration dateDuration = new DateDuration();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateDuration.setFrom(sdf.parse(from));
			dateDuration.setTo(sdf.parse(to));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sessionHandler.getSessionObjectList(pageNumber, dateDuration, patientId);
	}
	
	@POST
	@Path("/save")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean saveSession(@FormParam("sessionFormData") String sessionFormData,
					@FormParam("username") String username,
					@FormParam("password") String password) throws IOException {
		System.out.println(sessionFormData);
		System.out.println(username);
		System.out.println(password);
		return sessionHandler.saveSession(new SessionFormBean(), username, password);
	}
	
}
