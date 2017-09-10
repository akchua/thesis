package com.thesis.tremor.database.prototype;

import java.util.List;

import com.thesis.tremor.database.entity.TestImage;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
public interface TestImagePrototype extends Prototype<TestImage, Long> {

	List<TestImage> findAllByTestId(Long testId);
}
