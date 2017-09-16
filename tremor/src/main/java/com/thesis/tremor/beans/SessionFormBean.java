package com.thesis.tremor.beans;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thesis.tremor.deserializer.json.SessionDeserializer;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@JsonDeserialize(using = SessionDeserializer.class)
public class SessionFormBean extends FormBean {

	private Date dateDone;
	
	private List<TestFormBean> tests;

	public Date getDateDone() {
		return dateDone;
	}

	public void setDateDone(Date dateDone) {
		this.dateDone = dateDone;
	}

	public List<TestFormBean> getTests() {
		return tests;
	}

	public void setTests(List<TestFormBean> tests) {
		this.tests = tests;
	}
}
