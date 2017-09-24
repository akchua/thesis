package com.thesis.tremor.beans;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thesis.tremor.deserializer.json.HandDeserializer;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@JsonDeserialize(using = HandDeserializer.class)
public class HandFormBean extends FormBean {

	private List<FingerFormBean> fingers;

	public List<FingerFormBean> getFingers() {
		return fingers;
	}

	public void setFingers(List<FingerFormBean> fingers) {
		this.fingers = fingers;
	}
}
