package com.thesis.tremor.rest.endpoint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.entity.TestImage;
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
	@Path("/getimage/{fileName}")
	@Produces("image/*")
	public Response getTestImageByFileName(@PathParam("fileName") String fileName) throws IOException {
		File testImage = testHandler.findTestImageByFileName(fileName);
		if(testImage.exists())
			return Response.ok(testImage, new MimetypesFileTypeMap().getContentType(testImage))
				.build();
		else return null;
	}
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObjectList<Test> getTestObjectList(@QueryParam("pageNumber") Integer pageNumber, 
				@QueryParam("searchKey") String searchKey,
				@QueryParam("sessionId") Long sessionId) {
		return testHandler.getTestObjectList(pageNumber, searchKey, sessionId);
	}
	
	@GET
	@Path("/imagelist")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TestImage> getTestImageList(@QueryParam("testId") Long testId) {
		return testHandler.getTestImageList(testId);
	}
	
	@POST
	@Path("/uploadimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)	
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultBean uploadTestImage(@FormDataParam("sessionId") Long sessionId,
			@FormDataParam("testName") String testName,
			@FormDataParam("username") String username,
			@FormDataParam("password") String password,
			@FormDataParam("imageFile") InputStream in,
			@FormDataParam("imageFile") FormDataContentDisposition info) throws IOException {
		return testHandler.saveTestImage(sessionId, testName, username, password, in, info);
	}
}
