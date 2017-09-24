package com.thesis.tremor.rest.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.database.entity.Finger;
import com.thesis.tremor.rest.handler.FingerHandler;

@Path("/finger")
public class FingerEndpoint {

	@Autowired
	private FingerHandler fingerHandler;
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public Finger getFinger(@QueryParam("fingerId") Long fingerId) {
		return fingerHandler.getFinger(fingerId);
	}
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Finger> getFingerListByHandId(@QueryParam("handId") Long handId) {
		return fingerHandler.getFingerListByHandId(handId);
	}
}
