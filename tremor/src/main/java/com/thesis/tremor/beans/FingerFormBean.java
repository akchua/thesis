package com.thesis.tremor.beans;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thesis.tremor.deserializer.json.FingerDeserializer;
import com.thesis.tremor.enums.FingerType;

@JsonDeserialize(using = FingerDeserializer.class)
public class FingerFormBean extends FormBean {
	
	private Float averageAmplitude;
	
	private Float averageFrequency;
	
	private List<FloatingPoint> fingerPoints;
	
	private FingerType fingerType;

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

	public List<FloatingPoint> getFingerPoints() {
		return fingerPoints;
	}

	public void setFingerPoints(List<FloatingPoint> fingerPoints) {
		this.fingerPoints = fingerPoints;
	}

	public FingerType getFingerType() {
		return fingerType;
	}

	public void setFingerType(FingerType fingerType) {
		this.fingerType = fingerType;
	}
}
