package com.thesis.tremor.rest.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.thesis.tremor.beans.ResultBean;
import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.entity.TestImage;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface TestHandler {

	Test getTest(Long testId);

	File findTestImageByFileName(String fileName);
	
	ObjectList<Test> getTestObjectList(Integer pageNumber, String searchKey, Long sessionId);
	
	List<TestImage> getTestImageList(Long testId);
	
	ResultBean saveTestImage(Long testId, String username, String password, InputStream in, FormDataContentDisposition info) throws IOException;
}
