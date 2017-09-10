package com.thesis.tremor.beans;

import java.util.List;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
public class HandFormBean extends FormBean {

	private Float averageAmplitude;
	
	private Float averageFrequency;
	
	private List<Float> handPoints;

	public Float getAverageAmplitude() {
		return averageAmplitude;
	}

	public void setAverageAmplitude(Float averageAmplitude) {
		this.averageAmplitude = averageAmplitude;
	}

	public Float getAverageFrequency() {
		return averageFrequency;
	}

	public void setAverageFrequency(Float averageFrequency) {
		this.averageFrequency = averageFrequency;
	}

	public List<Float> getHandPoints() {
		return handPoints;
	}

	public void setHandPoints(List<Float> handPoints) {
		this.handPoints = handPoints;
	}
}
