package com.thesis.tremor.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   7 Aug 2017
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TestType {

	POSTURAL("Postural"),
	
	RESTING("Resting"),
	
	TASK_SPECIFIC("Task Specific");
	
	private final String displayName;
	
	TestType(final String displayName) {
		this.displayName = displayName;
	}
	
	public String getName() {
		return toString();
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
