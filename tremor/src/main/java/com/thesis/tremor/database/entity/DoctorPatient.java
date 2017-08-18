package com.thesis.tremor.database.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.thesis.tremor.database.entity.base.BaseObject;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   18 Aug 2017
 */
@Entity(name = "DoctorPatient")
@Table(name = DoctorPatient.TABLE_NAME)
public class DoctorPatient extends BaseObject {

	private static final long serialVersionUID = 2552457111203628283L;

	public static final String TABLE_NAME = "doctor_patient";
	
	private User doctor;
	
	private User patient;

	@ManyToOne(targetEntity = Session.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(targetEntity = Session.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	@Where(clause = "valid = 1")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}
}
