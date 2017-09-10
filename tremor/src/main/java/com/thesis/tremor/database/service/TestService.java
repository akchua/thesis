package com.thesis.tremor.database.service;

import com.thesis.tremor.database.entity.Test;
import com.thesis.tremor.database.prototype.TestPrototype;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   4 Sep 2017
 */
public interface TestService extends Service<Test, Long>, TestPrototype {

	ObjectList<Test> findAllWithPaging(int pageNumber, int resultsPerPage, String searchKey, Long sessionId);
}
