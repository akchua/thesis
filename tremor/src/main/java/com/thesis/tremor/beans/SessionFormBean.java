package com.thesis.tremor.beans;

import java.util.Date;
import java.util.List;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
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
