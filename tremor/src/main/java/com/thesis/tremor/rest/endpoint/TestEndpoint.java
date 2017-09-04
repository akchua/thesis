package com.thesis.tremor.rest.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.TestHandler;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Path("/test")
public class TestEndpoint {

	@Autowired
	private TestHandler testHandler;
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public Test getTest(@QueryParam("testId") Long testId) {
		return testHandler.getTest(testId);
	}
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObjectList<Test> getTestObjectList(@QueryParam("pageNumber") Integer pageNumber, 
				@QueryParam("searchKey") String searchKey,
				@QueryParam("patientId") Long patientId) {
		return testHandler.getTestObjectList(pageNumber, searchKey, patientId);
	}
}
