package com.thesis.tremor.rest.handler.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesis.tremor.UserContextHolder;
import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.constants.FileConstants;
import com.thesis.tremor.database.entity.Session;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.entity.TestImage;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.database.service.SessionService;
import com.thesis.tremor.database.service.TestImageService;
import com.thesis.tremor.database.service.TestService;
import com.thesis.tremor.database.service.UserService;
import com.thesis.tremor.objects.ObjectList;
import com.thesis.tremor.rest.handler.TestHandler;
import com.thesis.tremor.utility.EncryptionUtil;
import com.thesis.tremor.utility.StringHelper;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
@Transactional
@Component
public class TestHandlerImpl implements TestHandler {

	@Autowired
	private TestService testService;
	
	@Autowired
	private TestImageService testImageService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileConstants fileConstants;
	
	@Override
	public Test getTest(Long testId) {
		return testService.find(testId);
	}
	
	@Override
	public File findTestImageByFileName(String fileName) {
		return new File(fileConstants.getTestImageHome() + fileName);
	}

	@Override
	public ObjectList<Test> getTestObjectList(Integer pageNumber, String searchKey, Long sessionId) {
		return testService.findAllWithPaging(pageNumber, UserContextHolder.getItemsPerPage(), searchKey, sessionId);
	}

	@Override
	public List<TestImage> getTestImageList(Long testId) {
		return testImageService.findAllByTestId(testId);
	}

	@Override
	public ResultBean saveTestImage(Long sessionId, String testName, String username, String password, 
			InputStream in, FormDataContentDisposition info) throws IOException {
		final ResultBean result;
		final Session session = sessionService.find(sessionId);
		final User patient = userService.findPatientByUsernameAndPassword(username, EncryptionUtil.getMd5(password));
		 
		if(patient != null && patient.getId().equals(session.getPatient().getId())) {
			final String fileName = UUID.randomUUID().toString() + "." + StringHelper.getFileExtension(info.getFileName());
			
			File imageFile = new File(fileConstants.getTestImageHome() + fileName);
			if(imageFile.getParentFile() != null) imageFile.getParentFile().mkdirs();
			
			if(!imageFile.exists()) {
				Files.copy(in, imageFile.toPath());
				
				final Test test = testService.findBySessionAndName(sessionId, testName);
				if(test != null) {
					result = new ResultBean();
					
					final TestImage testImage = new TestImage();
					testImage.setTest(test);
					testImage.setFileName(fileName);
					
					result.setSuccess(testImageService.insert(testImage) != null);
					if(result.getSuccess()) {
						result.setMessage("Upload Successful.");
					} else {
						result.setMessage("Server Error. Please try again later.");
					}
				} else {
					result = new ResultBean(Boolean.FALSE, "Failed to load test. Please check the id.");
				}
			} else {
				result = new ResultBean(Boolean.FALSE, "Error please try uploading again.");
			}
		} else {
			result = new ResultBean(Boolean.FALSE, "Failed to load patient. Please re-enter correct credentials.");
		}
		
		return result;
	}
}
