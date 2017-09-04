package com.thesis.tremor.rest.handler;

import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface TestHandler {

	Test getTest(Long testId);
	
	ObjectList<Test> getTestObjectList(Integer pageNumber, String searchKey, Long sessionId);
}
