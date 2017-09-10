package com.thesis.tremor.database.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.thesis.tremor.database.entity.base.BaseObject;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   8 Sep 2017
 */
@Entity(name = "Hand")
@Table(name = Hand.TABLE_NAME)
public class Hand extends BaseObject {

	private static final long serialVersionUID = 4744965978317121380L;

	public static final String TABLE_NAME = "hand";
	
	private Float averageAmplitude;
	
	private Float averageFrequency;
	
	private String handPoints;

	@Basic
	@Column(name = "average_amplitude")
	public Float getAverageAmplitude() {
		return averageAmplitude;
	}

	public void setAverageAmplitude(Float averageAmplitude) {
		this.averageAmplitude = averageAmplitude;
	}

	@Basic
	@Column(name = "average_frequency")
	public Float getAverageFrequency() {
		return averageFrequency;
	}

	public void setAverageFrequency(Float averageFrequency) {
		this.averageFrequency = averageFrequency;
	}

	@Basic
	@Column(name = "hand_points")
	public String getHandPoints() {
		return handPoints;
	}
	
	@Transient
	public List<Float> getHandPointsList() {
		return Arrays.stream(handPoints.split(","))
					.map(Float::valueOf)
					.collect(Collectors.toList());
	}

	public void setHandPoints(String handPoints) {
		this.handPoints = handPoints;
	}
}
