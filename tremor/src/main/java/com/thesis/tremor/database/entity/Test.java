package com.thesis.tremor.database.entity;

import java.time.Duration;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thesis.tremor.database.entity.base.BaseObject;
import com.thesis.tremor.enums.TestType;
import com.thesis.tremor.serializer.json.HandSerializer;
import com.thesis.tremor.serializer.json.SessionSerializer;
import com.thesis.tremor.utility.format.DurationFormatter;

/**
 * @author Adrian Jasper K. Chua
 * @version 1.0
 * @since 7 Aug 2017
 */
@Entity(name = "Test")
@Table(name = Test.TABLE_NAME)
public class Test extends BaseObject {

	private static final long serialVersionUID = -1038328793621228235L;

	public static final String TABLE_NAME = "test";
	
	@JsonSerialize(using = SessionSerializer.class)
	private Session session;
	
	private TestType testType;
	
	private String name;
	
	private Long duration;
	
	@JsonSerialize(using = HandSerializer.class)
	private Hand leftHand;
	
	@JsonSerialize(using = HandSerializer.class)
	private Hand rightHand;

	@ManyToOne(targetEntity = Session.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "test_type", length = 50)
	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "duration")
	public Long getDuration() {
		return duration;
	}
	
	@Transient
	public String getFormattedDuration() {
		return DurationFormatter.formatDuration(Duration.ofSeconds(getDuration() * 1000l));
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@OneToOne(targetEntity = Hand.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Hand getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(Hand leftHand) {
		this.leftHand = leftHand;
	}

	@OneToOne(targetEntity = Hand.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public Hand getRightHand() {
		return rightHand;
	}

	public void setRightHand(Hand rightHand) {
		this.rightHand = rightHand;
	}
}
