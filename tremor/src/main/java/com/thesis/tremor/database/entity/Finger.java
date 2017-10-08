package com.thesis.tremor.database.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thesis.tremor.beans.FloatingPoint;
import com.thesis.tremor.database.entity.base.BaseObject;
import com.thesis.tremor.enums.FingerType;
import com.thesis.tremor.serializer.json.HandSerializer;

@Entity(name = "Finger")
@Table(name = Finger.TABLE_NAME)
public class Finger extends BaseObject {

	private static final long serialVersionUID = 6948608663630945722L;

	public static final String TABLE_NAME = "finger";
	
	@JsonSerialize(using = HandSerializer.class)
	private Hand hand;
	
	private Float averageAmplitude;
	
	private Float averageFrequency;
	
	private String fingerPoints;

	private FingerType fingerType;
	
	@ManyToOne(targetEntity = Hand.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "hand_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

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
	@Column(name = "finger_points", length = 600)
	public String getFingerPoints() {
		return fingerPoints;
	}
	
	@Transient
	public List<FloatingPoint> getFingerPointsList() {
		return Arrays.stream(fingerPoints.split(";"))
					.map(s -> {
						String[] magnitudes = s.split(",");
						return new FloatingPoint(Float.valueOf(magnitudes[0]), Float.valueOf(magnitudes[1]));
					})
					.collect(Collectors.toList());
	}

	public void setFingerPoints(String fingerPoints) {
		this.fingerPoints = fingerPoints;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "finger_type", length = 50)
	public FingerType getFingerType() {
		return fingerType;
	}

	public void setFingerType(FingerType fingerType) {
		this.fingerType = fingerType;
	}
}
