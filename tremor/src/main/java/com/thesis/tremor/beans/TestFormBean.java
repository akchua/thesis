package com.thesis.tremor.beans;

import com.thesis.tremor.enums.TestType;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
public class TestFormBean extends FormBean {

	private TestType testType;
	
	private String name;
	
	private Long duration;
	
	private HandFormBean leftHand;
	
	private HandFormBean rightHand;

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public HandFormBean getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(HandFormBean leftHand) {
		this.leftHand = leftHand;
	}

	public HandFormBean getRightHand() {
		return rightHand;
	}

	public void setRightHand(HandFormBean rightHand) {
		this.rightHand = rightHand;
	}
}
